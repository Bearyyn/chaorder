/**
 * 绘制demo的关系图
 * @param links
 * 		三元数组,给定数据关系
 * @param id
 * 		渲染的div的id
 * @returns
 * 		void
 */
function drawPathChart(links, id){
	d3.selectAll("#"+id+" svg").remove();
	
	var nodes = {};
	
	links.forEach(function(link) {
	  link.source = nodes[link.source] || (nodes[link.source] = {name: link.source});
	  link.target = nodes[link.target] || (nodes[link.target] = {name: link.target});
	});
	var width = $("#"+id).width(),
	    height = $("#"+id).height();
	
	var force = d3.layout.force()//layout将json格式转化为力学图可用的格式
	    .nodes(d3.values(nodes))//设定节点数组
	    .links(links)//设定连线数组
	    .size([width, height])//作用域的大小
	    .linkDistance(140)//连接线长度
	    .charge(-1500)//顶点的电荷数。该参数决定是排斥还是吸引，数值越小越互相排斥
	    .on("tick", tick)//指时间间隔，隔一段时间刷新一次画面
	    .start();//开始转换
	
	var svg = d3.select("#"+id).append("svg")
	    .attr("width", width)
	    .attr("height", height);
	
	//箭头
	var marker=
	    svg.append("marker")
	    //.attr("id", function(d) { return d; })
	    .attr("id", "resolved")
	    //.attr("markerUnits","strokeWidth")//设置为strokeWidth箭头会随着线的粗细发生变化
	    .attr("markerUnits","userSpaceOnUse")
	    .attr("viewBox", "0 -5 10 10")//坐标系的区域
	    .attr("refX",32)//箭头坐标
	    .attr("refY", -1)
	    .attr("markerWidth", 12)//标识的大小
	    .attr("markerHeight", 12)
	    .attr("orient", "auto")//绘制方向，可设定为：auto（自动确认方向）和 角度值
	    .attr("stroke-width",2)//箭头宽度
	    .append("path")
	    .attr("d", "M0,-5L10,0L0,5")//箭头的路径
	    .attr('fill','#000000');//箭头颜色
	
	//设置连接线    
	var edges_line = svg.selectAll(".edgepath")
	    .data(force.links())
	    .enter()
	    .append("path")
	    .attr({
	          'd': function(d) {return 'M '+d.source.x+' '+d.source.y+' L '+ d.target.x +' '+d.target.y},
	          'class':'edgepath',
	          'id':function(d,i) {return 'edgepath'+i;}})
	    .style("stroke",function(d){
	         return "black";
	     })
	    .style("pointer-events", "none")
	    .style("stroke-width",2)//线条粗细
	    .attr("marker-end", "url(#resolved)" );//根据箭头标记的id号标记箭头
	
	var edges_text = svg.append("g").selectAll(".edgelabel")
	.data(force.links())
	.enter()
	.append("text")
	.style("pointer-events", "none")
	.attr({  'class':'edgelabel',
	               'id':function(d,i){return 'edgepath'+i;},
	               'dx':50,
	               'dy':-5
	               });
	
	//设置线条上的文字
	edges_text.append('textPath')
	.attr('xlink:href',function(d,i) {return '#edgepath'+i})
	.style("pointer-events", "none")
	.text(function(d){return d.rela;});
	
	//圆圈
	var circle = svg.append("g").selectAll("circle")
	    .data(force.nodes())//表示使用force.nodes数据
	    .enter().append("circle")
	    .style("fill",function(node){
	        /*var color;//圆圈背景色
	        var link=links[node.index];
	        if(node.name==link.source.name && link.rela=="主营产品"){
	            color="#FF66CC";
	        }else{
	            color="#66CCFF";
	        }*/
	    	for(var i=0;i<links.length;i++){
	    		link = links[i];
	    		if(link.rela == "正相关" || link.rela =="负相关"){
	    			if(link.target.name == node.name)
	    				return "#66CCFF";
	    		}
	    	}
	    	return "#FF66CC";
	    })
	    .style('stroke',function(node){ 
	        return "black";
	    })
	    .attr("r", 28)//设置圆圈半径
	    .call(force.drag);//将当前选中的元素传到drag函数中，使顶点可以被拖动
	    
	var text = svg.append("g").selectAll("text")
	    .data(force.nodes())
	    //返回缺失元素的占位对象（placeholder），指向绑定的数据中比选定元素集多出的一部分元素。
	    .enter()
	    .append("text")
	    .attr("dy", ".35em")  
	    .attr("text-anchor", "middle")//在圆圈中加上数据  
	    .style('fill',function(node){
	        /*var color;//文字颜色
	        var link=links[node.index];
	        if(node.name==link.source.name && link.rela=="主营产品"){
	            color="#B43232";
	        }else{
	            color="#A254A2";
	        }*/
	        return "black";
	    }).attr('x',function(d){
	        var re_en = /[a-zA-Z]+/g;
	        //如果是全英文，不换行
	        if(d.name.match(re_en)){
	             d3.select(this).append('tspan')
	             .attr('x',0)
	             .attr('y',2)
	             .text(function(){return d.name;});
	        }
	        //如果小于四个字符，不换行
	        if(d.name.length<=4){
	             d3.select(this).append('tspan')
	            .attr('x',0)
	            .attr('y',2)
	            .text(function(){return d.name;});
	        }else{
	            var top=d.name.substring(0,4);
	            var bot=d.name.substring(4,d.name.length);
	
	            d3.select(this).text(function(){return '';});
	
	            d3.select(this).append('tspan')
	                .attr('x',0)
	                .attr('y',-7)
	                .text(function(){return top;});
	
	            d3.select(this).append('tspan')
	                .attr('x',0)
	                .attr('y',10)
	                .text(function(){return bot;});
	        }
	        
	    });
	
	
	function tick() {
	  circle.attr("transform", transform1);//圆圈
	  text.attr("transform", transform2);//顶点文字
	
	  edges_line.attr('d', function(d) { 
	      var path='M '+d.source.x+' '+d.source.y+' L '+ d.target.x +' '+d.target.y;
	      return path;
	  });  
	    
	  edges_text.attr('transform',function(d,i){
	        if (d.target.x<d.source.x){
	            bbox = this.getBBox();
	            rx = bbox.x+bbox.width/2;
	            ry = bbox.y+bbox.height/2;
	            return 'rotate(180 '+rx+' '+ry+')';
	        }
	        else {
	            return 'rotate(0)';
	        }
	   });
	}
	
	//设置连接线的坐标,使用椭圆弧路径段双向编码
	function linkArc(d) {
	  return 'M '+d.source.x+' '+d.source.y+' L '+ d.target.x +' '+d.target.y
	}
	//设置圆圈和文字的坐标
	function transform1(d) {
	  return "translate(" + d.x + "," + d.y + ")";
	}
	function transform2(d) {
	      return "translate(" + (d.x) + "," + d.y + ")";
	}
}


/**
 * 贝叶斯推断绘制
 * @param a,b
 */

function bayesNetwork(a,b){
	states[a] = b; 
	string = "";
	for(var i=0;i<states.length;i++){
		string += states[i];
	}
	$.ajax({
		url: "chaorder_bayes_network_demo",
		method: "POST",
		dataType: "json",
		data: {
			states: string
		},
		success: function(resp){
			drawBayesNetworkBarChart( resp.low,resp.high,"BayesNetwork-Chart");
		}
	});
}