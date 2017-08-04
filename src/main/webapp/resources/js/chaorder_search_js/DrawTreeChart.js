/**
 * 树状产品上下游关系图
 * @param data
 * 		json格式的关系数据
 * @param id
 * 		需要渲染的Div的id
 */
function drawProductTreeChart(data, id){
	d3.selectAll("svg").remove();
	var width = $("#"+id).width(), height = $("#"+id).height();
	/* m的四个数分别是指距离四个方向的边距 */
	var m = [0, width*0.45, 0, width*0.45],
	    w = width - m[1] - m[3],
	    h = height,
	    i = 0,
	    root;
	
	var tree = d3.layout.tree()
    	.size([h, w]);

	var diagonal = d3.svg.diagonal()
    	.projection(function(d) { return [d.y, d.x]; });
	
	var vis = d3.select("#"+id).append("svg:svg")
    	.attr("width", w + m[1] + m[3])
    	.attr("height", h + m[0] + m[2])
    	.append("svg:g")
    	.attr("transform", "translate(" + m[3] + "," + m[0] + ")");
	
	root = data;
	root.x0 = h / 2;
	root.y0 = 0;

	function toggleAll(d) {
	  if (d.children) {
	     d.children.forEach(toggleAll);
	     toggle(d);
	  }
	}
	
	update(root);
	
	function update(source) {
		
		var duration = d3.event && d3.event.altKey ? 5000 : 500;

		// Compute the new tree layout.
		var nodes = tree.nodes(root).reverse();

		// Normalize for fixed-depth.
		nodes.forEach(function(d) { 
			var tmp = 1;
			if(d.axis ==1 ){tmp =-1;}
			d.y = tmp * d.depth * 130; 
		});

		// Update the nodes…
		var node = vis.selectAll("g.node")
		  .data(nodes, function(d) { return d.id || (d.id = ++i); });

		// Enter any new nodes at the parent's previous position.
		var nodeEnter = node.enter().append("svg:g")
		  .attr("class", "node")
		  .attr("transform", function(d) { return "translate(" + source.y0 + "," + source.x0 + ")"; })
		  .on("click", function(d){ 			  	  
				  var str = "";
				  var data = mainProductOccupation[d.name];
				  var productData = mainProductPrice[d.name];
				  if(data == undefined){
					  alert("暂未有资料");
					  return;
				  }
				  
				  for(let i=0; i<data.length;i++){
					  str = str+"<tr><td>"+data[i].company+"</td><td>"+data[i].occupation+"</td></tr>";
				  }
				  $("#modal-company-products-tbody").html(str);
				  jQuery("#modal-company-products").modal('show');
				  if( productData == undefined ){
					  $("#modal-company-products-graph-title").html(d.name+"价格数据暂缺");
					  drawPrice([],[],"modal-company-products-graph-body");
				  }else{ 
					  $("#modal-company-products-graph-title").html(d.name+"价格变化图");
					  $("#modal-company-products").on('shown.bs.modal', function(){
						  drawPrice(productData.price,productData.time,"modal-company-products-graph-body");
					  })
				  }
			  }
		  );
		
		nodeEnter.append("svg:circle")
		  .attr("r", 1e-6)
		  .style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; })	

		nodeEnter.append("svg:text")
		  .attr("x", function(d) { return d.axis==1 ? -10 : 10; })
		  .attr("dy", ".35em")
		  .attr("text-anchor", function(d) { return d.axis==1 ? "end" : "start"; })
		  .text(function(d) { return d.name; })
		  .style("fill-opacity", 1e-6);
		
		// Transition nodes to their new position.
		var nodeUpdate = node.transition()
		  .duration(duration)
		  .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; });

		nodeUpdate.selectAll("circle")
		  .attr("r", 4.5)
		  //.on("click", function(d) { console.log(d.name) })
		  //是否可点击区分
		  .style("fill", function(d) { return mainProductOccupation[d.name] == undefined?"red":"lightsteelblue"; });

		nodeUpdate.select("text")
		  .style("fill-opacity", 1);

		// Transition exiting nodes to the parent's new position.
		var nodeExit = node.exit().transition()
		  .duration(duration)
		  .attr("transform", function(d) { return "translate(" + source.y + "," + source.x + ")"; })
		  .remove();

		nodeExit.select("circle")
		  .attr("r", 1e-6);

		nodeExit.select("text")
		  .style("fill-opacity", 1e-6);

		// Update the links…
		var link = vis.selectAll("path.link")
		  .data(tree.links(nodes), function(d) { return d.target.id; });
		
		// Enter any new links at the parent's previous position.
		link.enter().insert("svg:path", "g")
		  .attr("class", "link")
		  .attr("d", function(d) {
		    var o = {x: source.x0, y: source.y0};
		    return diagonal({source: o, target: o});
		  })
		  .style("stroke",function(d){return "#ccc";})
		  .transition()
		  	.duration(duration)
		  	.attr("d", diagonal)
		  ;
		
		// Transition links to their new position.
		link.transition()
		  .duration(duration)
		  .attr("d", diagonal);
		// Transition exiting nodes to the parent's new position.
		link.exit().transition()
		  .duration(duration)
		  .attr("d", function(d) {
		    var o = {x: source.x, y: source.y};
		    return diagonal({source: o, target: o});
		  })
		  .remove();
		
		// Stash the old positions for transition.
		nodes.forEach(function(d) {
			d.x0 = d.x;
			d.y0 = d.y;
		});	  
		
		// Toggle children.
		function toggle(d) {
		  if (d.children) {
		    d._children = d.children;
		    d.children = null;
		  } else {
		    d.children = d._children;
		    d._children = null;
		  }
		}
	}
}	

/**
 * 环形行业上下游关系图
 * @param data
 * 		json格式的关系数据
 * @param id
 * 		渲染的Div的id
 */
function drawIndustryTreeChart(data, id){
	d3.selectAll("svg").remove();
	var width = $("#"+id).width(), height = $("#"+id).height();
	
	var w = width,
    	h = height,
    	rx = w/2,
    	ry = h/2,
    	m0,
    	rotate = 0;
	
	var cluster = d3.layout.cluster()
    	.size([360, ry - 100])
    	.sort(null);
	
	var diagonal = d3.svg.diagonal.radial()
    	.projection(function(d) { return [d.y, d.x / 180 * Math.PI]; });

	var vis = d3.select("#"+id).append("svg:svg")
    	.attr("width", w)
    	.attr("height", h)
    	.append("svg:g")
    	.attr("transform", "translate(" + rx + "," + ry + ")");

	vis.append("svg:path")
    	.attr("class", "arc")
    	.attr("d", d3.svg.arc().innerRadius(ry - 200).outerRadius(ry).startAngle(0).endAngle(2 * Math.PI));
    	
	var nodes = cluster.nodes(data);
	
	var link = vis.selectAll("path.link")
	  .data(cluster.links(nodes))
	  .enter().append("svg:path")
	  .attr("class", "link")
	  .attr("d", diagonal);

	var node = vis.selectAll("g.node")
	  .data(nodes)
	  .enter().append("svg:g")
	  .attr("class", "node")
	  .attr("transform", function(d) { return "rotate(" + (d.x - 90) + ")translate(" + d.y + ")"; })

	node.append("svg:circle")
	  .attr("r", 3);

	node.append("svg:text")
	  .attr("dx", function(d) { return d.x < 180 ? 8 : -8; })
	  .attr("dy", ".31em")
	  .attr("text-anchor", function(d) { return d.x < 180 ? "start" : "end"; })
	  .attr("transform", function(d) { return d.x < 180 ? null : "rotate(180)"; })
	  .text(function(d) { return d.name; });
	
	node
	  .on("click", function(d){ 	
		  $.ajax({
				url: "searchOccupationOfCompany",
				method: "post",
				dataType: "json",
				data: {
					"product": d.name
				},
				success: function(resp){
					if(resp.state == 0){
						var opts = {
							"closeButton": true,
				            "debug": false,
				            "positionClass": "toast-top-full-width",
				            "onclick": null,
				            "showDuration": "300",
				            "hideDuration": "1000",
				            "timeOut": "5000",
				            "extendedTimeOut": "1000",
				            "showEasing": "swing",
				            "hideEasing": "linear",
				            "showMethod": "fadeIn",
				            "hideMethod": "fadeOut"
						};
						toastr.error("查询失败!", "没有找到相关内容", opts);
					} else {
						alert("正在进行");
					}
				}
			});
	  });
	
}