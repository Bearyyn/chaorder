$(function(){  
	packages = {
		    // Lazily construct the package hierarchy from class names.
		    root: function(classes) {
		      var map = {};

		      function find(name, data) {
		        var node = map[name], i;
		        if (!node) {
		          node = map[name] = data || {name: name, children: []};
		          if (name.length) {
		            node.parent = find(name.substring(0, i = name.lastIndexOf(".")));
		            node.parent.children.push(node);
		            node.key = name.substring(i + 1);
		          }
		        }
		        return node;
		      }

		      classes.forEach(function(d) {
		        find(d.name, d);
		      });

		      return map[""];
		    },

		    // Return a list of imports for the given array of nodes.
		    imports: function(nodes) {
		      var map = {},
		          imports = [];

		      // Compute a map from name to node.
		      nodes.forEach(function(d) {
		        map[d.name] = d;
		      });

		      // For each import, construct a link from the source to target node.
		      nodes.forEach(function(d) {
		        if (d.imports) d.imports.forEach(function(i) {
		          imports.push({source: map[d.name], target: map[i]});
		        });
		      });

		      return imports;
		    }

		  };
	/* keyword hint */
	$("#kgs_keyword").autocomplete({  
		source: function(request, response){
			$.ajax({
				url: "match_keywords",
				method: 'post',
				dataType: "text",
				data:{
					keyword: request.term,
				},
				success: function(resp){
					/*resp.length < 3 the resp is void */
					if(resp.length >= 3){
						var keywordList = resp.substr(1,resp.length-2).split(',');
						response(keywordList);
					}
				}
			});
		},
        minLength:2,
        position: { offset:'-30 0' },  
        select: function(event, ui) { 
          	return;
        }    
	});
	 
})




/* this function draw the product tree graph*/
function kgs(){
	/* active object div */
	d3.selectAll("svg").remove();
	var width = $("#KG-Title").width();
	var height = 700;
	var kgs_keyword = $('#kgs_keyword').val();
	if (kgs_keyword.substr(kgs_keyword.length-2,kgs_keyword.length) != "行业"){
	/* draw product tree graph*/
	$.ajax({
		url: "KgSearchProduct",
		method: "POST",
		dataType: "json",
		data: {
			keyword: $('#kgs_keyword').val(),
		},
		success: function(resp){
			var m = [0, width*0.45, 0, width*0.45],
		    w = width - m[1] - m[3],
		    h = height,
		    i = 0,
		    root;

			var tree = d3.layout.tree()
			    .size([h, w]);

			var diagonal = d3.svg.diagonal()
			    .projection(function(d) { return [d.y, d.x]; });

			var vis = d3.select("#knowledge_graph_product").append("svg:svg")
					    .attr("width", w + m[1] + m[3])
					    .attr("height", h + m[0] + m[2])
					    .append("svg:g")
					    .attr("transform", "translate(" + m[3] + "," + m[0] + ")");
				
			root = resp;
			root.x0 = h / 2;
			root.y0 = 0;

			function toggleAll(d) {
			  if (d.children) {
			     d.children.forEach(toggleAll);
			     toggle(d);
			  }
			}

			// Initialize the display to show a few nodes.
			//root.children.forEach(toggleAll);				
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
					  var str_temp = "<tr><td>{0}</td><td>{1}</td></tr>";
					  var str = "";
					  var data = csvdata[d.name];
					  if(data == undefined){
						  alert("暂未有资料");
						  return;
					  }
					  
					  for(let i=0; i<data.length;i++){
						  str = str+"<tr><td>"+data[i][0]+"</td><td>"+data[i][1]+"</td></tr>";
					  }
					  $("#related_company").html(str);
					  jQuery('#modal-company').modal('show');
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
				  .style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });

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
	});
		$("#product").click();
	}else{
		$.ajax({
			url: "KgSearchIndustry",
			method: "POST",
			dataType: "json",
			data: {
				keyword: $('#kgs_keyword').val(),
			},
			success: function(resp){
				/* this function draw the industry tree graph*/
				
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
	
				var vis = d3.select("#knowledge_graph_industry").append("svg:svg")
				    .attr("width", w)
				    .attr("height", h)
				  .append("svg:g")
				    .attr("transform", "translate(" + rx + "," + ry + ")");
	
				vis.append("svg:path")
				    .attr("class", "arc")
				    .attr("d", d3.svg.arc().innerRadius(ry - 200).outerRadius(ry).startAngle(0).endAngle(2 * Math.PI))
				    .on("mousedown", mousedown);
	
				
					var nodes = cluster.nodes(resp);
	
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
	
				d3.select(window)
				.on("mousemove", mousemove)
				.on("mouseup", mouseup);
	
				function mouse(e) {
				return [e.pageX - rx, e.pageY - ry];
				}
	
				function mousedown() {
				m0 = mouse(d3.event);
				d3.event.preventDefault();
				}
	
				function mousemove() {
				if (m0) {
					var m1 = mouse(d3.event),
					    dm = Math.atan2(cross(m0, m1), dot(m0, m1)) * 180 / Math.PI,
					    tx = "translate3d(0," + (ry - rx) + "px,0)rotate3d(0,0,0," + dm + "deg)translate3d(0," + (rx - ry) + "px,0)";
					svg
					    .style("-moz-transform", tx)
					    .style("-ms-transform", tx)
					    .style("-webkit-transform", tx);
					}
				}
	
				function mouseup() {
				  if (m0) {
				    var m1 = mouse(d3.event),
				        dm = Math.atan2(cross(m0, m1), dot(m0, m1)) * 180 / Math.PI,
				        tx = "rotate3d(0,0,0,0deg)";
	
				    rotate += dm;
				    if (rotate > 360) rotate -= 360;
				    else if (rotate < 0) rotate += 360;
				    m0 = null;
	
				    svg
				        .style("-moz-transform", tx)
				        .style("-ms-transform", tx)
				        .style("-webkit-transform", tx);
	
				    vis
				        .attr("transform", "translate(" + rx + "," + ry + ")rotate(" + rotate + ")")
				      .selectAll("g.node text")
				        .attr("dx", function(d) { return (d.x + rotate) % 360 < 180 ? 8 : -8; })
				        .attr("text-anchor", function(d) { return (d.x + rotate) % 360 < 180 ? "start" : "end"; })
				        .attr("transform", function(d) { return (d.x + rotate) % 360 < 180 ? null : "rotate(180)"; });
				  }
				}
	
				function cross(a, b) {
				  return a[0] * b[1] - a[1] * b[0];
				}
	
				function dot(a, b) {
				  return a[0] * b[0] + a[1] * b[1];
				}	
			}
		});
		$("#industry").click();
	}
}


