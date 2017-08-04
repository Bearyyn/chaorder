package com.chaorder.aitech;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.chaorder.searchKS_old.*;
import com.chaorder.validationCode.ValidationCode;
import com.chaorder.KnowledgeGraphSearch.*;
import com.chaorder.aitech.mapping.ZyzbMapper;
import com.chaorder.aitech.pojo.Zyzb;
import com.chaorder.bayesNetwork.*;

/**
 * Handles requests for the chaorder event search page.
 * 
 * @url chaorder_search_home 搜索主界面
 * @url chaorder_search_post 搜索事件响应的ajax后端
 * @url keywords_hint 提示知识图谱搜索的ajax后端
 * @url knowledgeGraphProduct 搜索产品上下游的ajax后端
 * @url knowledgeGraphIndustry 搜索产业上下游的ajax后端
 * @url chaorder_search_black_swan 黑天鹅事件搜索
 * @url chaorder_main_product_occupation 主营占比搜索
 * @url chaorder_main_product_price 主营价格搜索
 */
@Controller
public class SearchController {
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	@Autowired  
	private ZyzbService zyzbService;  
	
	/* 搜索主界面 */
	@RequestMapping(value = "/chaorder_search_home", method = RequestMethod.GET)
	public String chaorderSearch(HttpServletRequest request, Model model) throws IOException {
		
		////////////////////test use
		request.getSession().setAttribute("username", "aitech");
		//////////////////////////////
		
		String user = (String) request.getSession().getAttribute("username");
		// String query = (String) request.getParameter("query");
		String query = "", knowledgeGraphKeyword="";
		if(request.getParameter("query") !=null)
			query = request.getParameter("query");
		if(request.getParameter("kgk") !=null)
			knowledgeGraphKeyword=request.getParameter("kgk");

		/* 验证session */
		if (user == null || user.length() == 0)
			return "redirect:/login";

		model.addAttribute("query", query);
		model.addAttribute("knowledgeGraphKeyword", knowledgeGraphKeyword);
		return "ChaorderSearch";
	}

	/****************************************************************************************
	 * TODO 事件搜索post (目前还是旧版本，暂时只能中文)
	 * 
	 * @param request
	 *            //请求包含 query: 查询事件的内容 // language: cn(中文) en(英文) 旧版: query查询事件内容
	 * @param response
	 *            写入前端 state: 1成功 0失败 eventList: 事件列表
	 */
	/* search request */
	public static String[] sendGet(String url, String param) {
		String result = "";
		Pattern pattern1 = Pattern.compile("title=\"(.*?)\"");
		Pattern pattern2 = Pattern.compile("CDATA(.*?)。");
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		Matcher matcher1 = pattern1.matcher(result);
		Matcher matcher2 = pattern2.matcher(result);
		String[] Result = new String[2];
		if (matcher1.find()) {
			Result[0] = matcher1.group(1);
		}
		if (matcher2.find()) {
			Result[1] = matcher2.group(1);
		}
		String temp = (result.split("。")[0]);
		String[] temp_list = temp.split("\\[");
		Result[1] = temp_list[temp_list.length - 1];
		return Result;
	}

	@RequestMapping(value = "/chaorder_search_post_old", method = RequestMethod.POST)
	@ResponseBody
	public void search(HttpServletRequest request, HttpServletResponse response) {
		JSONObject response_data = new JSONObject();
		/* Must have an user name */
		if (request.getSession().getAttribute("username") != null) {
			/* Request for RDF event search */
			String customer_input = request.getParameter("query").replaceAll(" ", "");
			if (customer_input != null && customer_input.length() >= 1) {
				RpcSearchKSClient event_search = new RpcSearchKSClient();

				try {
					List<EventPayload> result = event_search.eventSearch(customer_input);
					ArrayList<JSONObject> event_list = new ArrayList<JSONObject>();
					ArrayList<String> timeList = new ArrayList<String>();
					if (result != null && !result.isEmpty()) {
						/* combine time */
						JSONObject time = new JSONObject();
						for (EventPayload rs : result) {
							time.put(rs.timeUrl.substring(rs.timeUrl.length() - 8, rs.timeUrl.length()), false);
						}
						/* only considert different time */
						for (EventPayload rs : result) {
							JSONObject event = new JSONObject();
							String event_time = rs.timeUrl.substring(rs.timeUrl.length() - 8, rs.timeUrl.length());
							if (!(Boolean) time.get(event_time)) {
								/* get title */
								String[] news = sendGet(rs.verbUrl, "");
								event.put("time", event_time.substring(0, 4) + "年" + event_time.substring(4, 6) + "月"
										+ event_time.substring(6, 8) + "日");
								event.put("title", news[0]);
								event.put("content", news[1]);
								event.put("url", rs.verbUrl);
								event_list.add(event);
								time.put(event_time, true);
								timeList.add(event_time);
							} else {

							}

							logger.info(String.format("%s: / %s suburl: %s / %s verurl: %s / %s objurl: %s", rs.timeUrl,
									rs.subjectLabel, rs.subjectUrl, rs.verbLabel, rs.verbUrl, rs.objectLabel,
									rs.objectUrl));
						}
						Collections.sort(event_list, new Comparator<JSONObject>() {

							@Override
							public int compare(JSONObject o1, JSONObject o2) {
								return ((String) o2.get("time")).compareTo((String) o1.get("time"));
							}
						});
						QuantAnalysis qt = new QuantAnalysis(timeList);
						ArrayList<JSONObject> index_list = qt.get_Roi();
						response_data.put("states", 1);
						response_data.put("event_list", event_list);
						response_data.put("roi_date", index_list.get(0).get("trade_date"));
						response_data.put("roi", index_list.get(0).get("profit"));
						response_data.put("benchmark", index_list.get(100).get("profit"));
						response_data.put("all", index_list);
					} else {
						response_data.put("states", 0);
					}
					/* Quant Result */
					response.setContentType("text/html;charset=UTF-8");
					response.getWriter().print(response_data.toString());
					return;
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		} else {
		}
		try {
			response_data.put("states", 0);
			response.getWriter().print(response_data.toString());
		} catch (Exception e) {
			logger.error("search unsuccess:" + e.getMessage());
		}
		return;
	}
	/*
	 *************************************************************************************
	 */

	
	
	/**
	 * 跳转界面新闻页
	 */
	@RequestMapping(value = "/chaorder_Jump_News", method = RequestMethod.GET)
	public @ResponseBody ModelAndView chaorderJumpNews(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("ChaorderJumpNews");
		String address = "";
		String title = "";
		String body = "";
		String time = "";
		if (request.getParameter("address") != null) {
			try {
				address = URLDecoder.decode(request.getParameter("address"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
			if (address.length() != 0) {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				// 创建DocumentBuilder对象
				DocumentBuilder db;
				try {
					db = dbf.newDocumentBuilder();
					Document document = db.parse(address);// 传入文件名可以是相对路径也可以是绝对路径
				    Node node = document.getElementsByTagName("fileDesc").item(0);
				    NamedNodeMap attrs = node.getAttributes();
				    node = attrs.getNamedItem("title");
				    title = node.getNodeValue();
				    node = attrs.getNamedItem("creationtime");
				    time =node.getNodeValue();
				    System.out.println(document.getElementsByTagName("raw").getLength());  
				    node = document.getElementsByTagName("raw").item(0);
				    body = node.getTextContent();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
				} 
				mav.addObject("title", title);
				mav.addObject("time", time);
				mav.addObject("body", body);
				//发送关键字
				JSONArray jsonArray = new JSONArray();	
				List<Zyzb> zyzbs = zyzbService.FindAllProduct();
				for(int i = 0; i<zyzbs.size(); i++)
					jsonArray.put(zyzbs.get(i).getTpdycpmc());
				mav.addObject("keyword", jsonArray);
			}
		}
		return mav;
	}

	/**
	 * TO DO 新版本
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/chaorder_search_post", method = RequestMethod.POST)
	public void chaorderEventSearch(HttpServletRequest request, HttpServletResponse response) {
		JSONObject responseData = new JSONObject();
		String user = (String) request.getSession().getAttribute("username");
		String query = request.getParameter("query");
		String language = request.getParameter("language");
		/* 验证session query 和language */
		if ((user == null || user.length() == 0) || (query == null || query.length() == 0)
				|| (!language.equals("cn") && !language.equals("en"))) {
			responseData.put("state", 0);
			try {
				response.getWriter().print(responseData.toString());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		/* 事件搜索 */
		RpcSearchKSClient eventSearch = new RpcSearchKSClient();
		try {
			List<EventPayload> result = eventSearch.eventSearch(query);
		} catch (Exception e) {
			logger.info(e.getMessage());
		} finally {
		}
		return;
	}

	/**
	 * 知识图谱查询提示
	 * 
	 * @param request
	 *            keyword 输入的关键词
	 * @return 返回前端数据 联想的相应的词的列表keywordsList
	 */
	@RequestMapping(value = "/keywords_hint", method = RequestMethod.POST)
	public void keywordsHint(HttpServletRequest request, HttpServletResponse response) {
		String keyword = request.getParameter("keyword");
		keyword = keyword.replaceAll(" ", "");
		String user = (String) request.getSession().getAttribute("username");
		if (user == null || user.length() < 1 || keyword.length() < 2) {
			try {
				response.getWriter().print("error");
			} catch (Exception e) {
			}
			return;
		}
		KnowledgeGraphSearch kgs = new KnowledgeGraphSearch();
		try {
			List<String> keywordsList = kgs.match_keyword(keyword);
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(keywordsList.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
		}
		return;
	}

	/**
	 * 知识图谱产品查询
	 * 
	 * @param request
	 *            keyword 输入的关键词
	 * @return 返回一个json格式数据用于d3生成树形图 state: 1成功 0失败
	 */
	@RequestMapping(value = "/knowledgeGraphProduct", method = RequestMethod.POST)
	public void knowledgeGraphProduct(HttpServletRequest request, HttpServletResponse response) {
		String keyword = request.getParameter("keyword");
		String user = (String) request.getSession().getAttribute("username");
		keyword = keyword.replaceAll(" ", "");
		JSONObject resp = new JSONObject();
		if (user == null || user.length() < 1 || keyword.length() < 2) {
			try {
				resp.put("state", 0);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print(resp.toString());
			} catch (Exception e) {
			}
		} else {
			KnowledgeGraphSearch kgs = new KnowledgeGraphSearch();
			try {
				JSONObject tree = kgs.searchProduct(keyword, "", 0, 0);
				// 搜索内容是一个类的范畴
				if (tree.getJSONArray("children").length() == 0) {
					knowledgeGraphIndustry(request, response);
					return;
				}
				// System.out.println(tree.toString());
				resp.put("state", 1);
				resp.put("data", tree);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print(resp.toString());
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				kgs.closesession();
			}
		}
		return;
	}

	/**
	 * 知识图谱行业查询
	 * 
	 * @param request
	 *            keyword 输入的关键词
	 * @return 返回一个json格式数据用于d3生成树形图 state: 1成功 0失败
	 */
	@RequestMapping(value = "/knowledgeGraphIndustry", method = RequestMethod.POST)
	public void knowledgeGraphIndustry(HttpServletRequest request, HttpServletResponse response) {
		String keyword = request.getParameter("keyword");
		String user = (String) request.getSession().getAttribute("username");
		keyword = keyword.replaceAll(" ", "");
		JSONObject resp = new JSONObject();
		if (user == null || user.length() < 1 || keyword.length() < 2) {
			try {
				resp.put("state", 0);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print(resp.toString());
			} catch (Exception e) {
			}
		} else {
			KnowledgeGraphSearch kgs = new KnowledgeGraphSearch();
			try {
				JSONObject tree = kgs.searchIndustry(keyword, "", 0);
				// System.out.println(tree.toString());
				resp.put("state", 1);
				resp.put("data", tree);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print(resp.toString());
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				kgs.closesession();
			}
		}
		return;
	}

	/**
	 * 黑天鹅事件查询
	 * 
	 * @param request
	 *            query 查询内容
	 * @return 一个用于生成图表的List
	 */
	@RequestMapping(value = "/chaorder_search_black_swan", method = RequestMethod.POST)
	public void searchBlackSwan(HttpServletRequest request, HttpServletResponse response) {
		JSONObject resp = new JSONObject();
		String keyword = request.getParameter("query");
		keyword = keyword.replaceAll(" ", "");
		String user = (String) request.getSession().getAttribute("username");
		if (user == null || user.length() < 1 || keyword.length() < 2) {
			try {
				resp.put("state", 0);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print(resp.toString());
			} catch (Exception e) {
			}
		} else {
			try {
				KnowledgeGraphSearch kgs = new KnowledgeGraphSearch();
				resp.put("state", 1);
				resp.put("data", kgs.blackSwan(keyword));
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print(resp.toString());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return;
	}

	/**
	 * 主营占比搜索
	 * 
	 * @return 输出JSON变量到前端
	 */
	@RequestMapping(value = "/chaorder_main_product_occupation", method = RequestMethod.GET)
	public void mainProductOccupation(HttpServletRequest request, HttpServletResponse response) {
		QuantAnalysis qAnalysis = new QuantAnalysis();
		try {
			JSONObject mainProductJson = qAnalysis.getMainProductOccupation();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print("var mainProductOccupation = " + mainProductJson.toString() + ";");
			System.out.println(mainProductJson.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 该产品的公司占比搜索
	 * 
	 * @return 输出JSON变量到前端
	 */
	@RequestMapping(value = "/searchOccupationOfCompany", method = RequestMethod.POST)
	public void searchOccupationOfCompany(HttpServletRequest request, HttpServletResponse response) {
		JSONObject resp = new JSONObject();
		resp.put("state", 1);
		resp.put("data", "success");
		response.setContentType("text/html;charset=UTF-8");
		try {
			response.getWriter().print(resp.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 价格序列搜索
	 * 
	 * @return 输出价格序列JSON变量到前端
	 */
	@RequestMapping(value = "/chaorder_main_product_price", method = RequestMethod.GET)
	public void mainProductPrice(HttpServletRequest request, HttpServletResponse response) {
		QuantAnalysis qAnalysis = new QuantAnalysis();
		try {
			JSONObject mainProductPrice = qAnalysis.getMainProductPrice();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print("var mainProductPrice = " + mainProductPrice.toString() + ";");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 黄金demo的贝叶斯网络概率
	 * 
	 * @param request.getParameter(states)
	 *            格式:String xxxx 分别代表贝叶斯网络的观测状态(2:未定, 1:涨, 0:跌)(vix, cpi, usd, gold)
	 * @return 输出贝叶斯网络JSON变量到前端
	 */
	@RequestMapping(value = "/chaorder_bayes_network_demo", method = RequestMethod.POST)
	public void bayesNetworkDemo(HttpServletRequest request, HttpServletResponse response) {
		String states = request.getParameter("states");
		bayesDemo bayesNetwork = new bayesDemo();
		try {
			JSONObject probList = bayesNetwork.goldBayesDemo(states);
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(probList.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return;
	}
}
