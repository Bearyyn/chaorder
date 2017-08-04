package com.chaorder.KnowledgeGraphSearch;

import org.json.JSONObject;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Relationship;
import org.springframework.aop.IntroductionInterceptor;

import com.blade.route.annotation.Path;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KnowledgeGraphSearch{	
	
	private Driver driver;
	private Session session;
	private static int max_depth = 1;
	private static int keywords_hint_max = 25;
	private static final ResourceBundle SETTING = ResourceBundle.getBundle("Config");
	private String down = "MATCH path = (n:展示行业{name:\"%s\"})-[r{rel:\"下游\"}]->(m:展示行业{})"
						  + "return r.rel as rel ,m.name as name";
	private String up = "MATCH path = (n:展示行业{name:\"%s\"})<-[r{rel:\"下游\"}]-(m:展示行业{})"
			  + "return r.rel as rel ,m.name as name";
	private String match_keyword = "MATCH (n:展示行业{}) where n.name =~'.*%s.*'"
								   + "return n.name as name";	
	private String down_industry = "MATCH path = (n:展示行业{name:\"%s\"})-[r]->(m:展示行业{}) WHERE r.rel<>\"下游\""+
								   "return r.rel as rel ,m.name as name ";
	private String rel_query = "START rel=relationship(%s) MATCH (a)-[rel]->(b) RETURN a.name, rel.rel, b.name";
	private String black_swan = "MATCH (a:事件{}) where a.name =~ '.*%s.*' MATCH(b:事件{}) WITH a,b "+
								 "MATCH path=(a)-[r*..5]->(b) WITH a,r,b RETURN r";
	public KnowledgeGraphSearch() {
		String neo4jAddr = SETTING.getString("neo4j_addr");
		String neo4jUsr = SETTING.getString("neo4j_uname");
		String neo4jPwd = SETTING.getString("neo4j_pwd");
		driver = GraphDatabase.driver(
					String.format("bolt://%s:7687", neo4jAddr),
					AuthTokens.basic( neo4jUsr, neo4jPwd ) 
				 );
		session = driver.session();
	}
	
	public void closesession(){
		session.close();
	}
	/* this function hint keyword input*/
	public List<String> match_keyword(String keyword){
		ArrayList<String> keywordList = new ArrayList<String>();
		StatementResult result = session.run(String.format(match_keyword, keyword));
		while(result.hasNext()){
			Record record = result.next();
			keywordList.add(record.get("name").asString());
		}
		Collections.sort(keywordList);
		int end = keywords_hint_max < keywordList.size() ? keywords_hint_max : keywordList.size();
		return keywordList.subList(0, end);
	}
	
	
	/* this function search the relationship of given keyword */
	public JSONObject searchProduct(String keyword, String rel, int axis, int depth) {
		// match keywords for root node
		if (axis == 0){
			StatementResult result = session.run(String.format(match_keyword, keyword));			
			double sim = 0;
			String obj_keyword = "";
			while(result.hasNext()){
				Record record = result.next();
				String name = record.get("name").asString();				
				if( (keyword.length()*1.0/name.length()) > sim){
					sim = (keyword.length()*1.0/name.length());
					obj_keyword = name;
				}
			}
			if(!obj_keyword.equals(""))
				keyword = obj_keyword;
		}
		JSONObject root = new JSONObject();
		ArrayList<JSONObject> aList = new ArrayList<JSONObject>();
		root.put("name", keyword);
		root.put("rel", rel);
		root.put("axis", axis);
		root.put("children", aList);		
		if(depth > max_depth)
			return root;
		// root node
		if( axis == 0 ){
			StatementResult result = session.run(String.format(down, keyword));
			while(result.hasNext()){
				Record record = result.next();
				aList.add(searchProduct(record.get("name").asString(), 
								 record.get("rel").asString(), 
								 -1, 
								 depth+1)
						 );
			}
			result = session.run(String.format(up, keyword));
			while(result.hasNext()){
				Record record = result.next();
				aList.add(searchProduct(record.get("name").asString(), 
								 record.get("rel").asString(), 
								 1, 
								 depth+1)
						 );
			}
			root.put("children", aList);			
		}else if(axis == -1){
			StatementResult result = session.run(String.format(down, keyword));
			while(result.hasNext()){
				Record record = result.next();
				aList.add(searchProduct(record.get("name").asString(), 
								 record.get("rel").asString(), 
								 -1, 
								 depth+1)
						 );
			}
			root.put("children", aList);
			return root;
		}else if(axis == 1){
			StatementResult result = session.run(String.format(up, keyword));
			while(result.hasNext()){
				Record record = result.next();
				aList.add(searchProduct(record.get("name").asString(), 
								 record.get("rel").asString(), 
								 1, 
								 depth+1)
						 );
			}
			root.put("children", aList);
			return root;
		}
		return root;
	}
	
	/* this function search given keyword and relation */
	public JSONObject searchIndustry(String keyword, String rel, int depth){
		JSONObject root = new JSONObject();
		ArrayList<JSONObject> aList = new ArrayList<JSONObject>();
		root.put("name", keyword);
		root.put("rel", rel);
		root.put("children", aList);		
		if(depth > max_depth)
			return root;		
		StatementResult result = session.run(String.format(down_industry, keyword));
		while(result.hasNext()){
			Record record = result.next();
			aList.add(searchIndustry(record.get("name").asString(), 
							 record.get("rel").asString(),  
							 depth+1)
					 );
		}
		root.put("children", aList);
		return root;
	}
	
	/* following function search the black swan events */
	private JSONObject getNameByRelId(String id){
		StatementResult result = session.run(String.format(rel_query, id));
		JSONObject rel = new JSONObject();
		while(result.hasNext()){
			Record record = result.next();
			String aname = record.get("a.name").asString();
			String relrel = record.get("rel.rel").asString();
			String bname = record.get("b.name").asString();
			rel.put("source",aname);
			rel.put("rela", relrel);
			rel.put("target", bname);
		}
		return rel;
	}
	
	private List<String> getRelCode(String path){
		List<String> relCodeList = new ArrayList<String>();
		Pattern pattern = Pattern.compile("<(.*?)>");
		Matcher matcher = pattern.matcher(path);
		while(matcher.find()){
			for(int i=1; i<=matcher.groupCount(); i++){
				relCodeList.add(matcher.group(i));
			}
		}
		return relCodeList;
	}
	
	public List<JSONObject> blackSwan(String keyword){
		ArrayList<JSONObject> blackSwanList = new ArrayList<JSONObject>();
		StatementResult result = session.run(String.format(black_swan, keyword));
		Set<String> relSet = new HashSet<String>();
		//StatementResult result = session.run("Start a=relationship(29267) return a.start");
		while(result.hasNext()){
			Record record = result.next();
			String path = record.get("r").toString();
			List<String> relCodeList = getRelCode(path);
			for(String rel:relCodeList){
				relSet.add(rel);
			}
		}
		for(String rel:relSet){
			blackSwanList.add(getNameByRelId(rel));
		}
		return blackSwanList;
	}
}