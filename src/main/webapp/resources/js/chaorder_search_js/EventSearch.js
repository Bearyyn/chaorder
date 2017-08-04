//判断是否全是中文
function isChn(str) {
	var reg = /.*^[\u4E00-\u9FA5]+.*$/;
	if (!reg.test(str)) {
		return false;
	}
	return true;
}

// 知识图谱查询自动补全
jQuery(document).ready(
		function($) {
			/* String Format Method */
			String.prototype.format = function(args) {
				var result = this;
				if (arguments.length < 1) {
					return result;
				}
				var data = arguments; // 如果模板参数是数组
				if (arguments.length == 1 && typeof (args) == "object") {
					data = args; // 如果模板参数是对象
				}
				for ( var key in data) {
					var value = data[key];
					if (undefined != value) {
						result = result.replace("{" + key + "}", value);
					}
				}
				return result;
			}
			packages = {
				// Lazily construct the package hierarchy from class names.
				root : function(classes) {
					var map = {};
					function find(name, data) {
						var node = map[name], i;
						if (!node) {
							node = map[name] = data || {
								name : name,
								children : []
							};
							if (name.length) {
								node.parent = find(name.substring(0, i = name
										.lastIndexOf(".")));
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
				imports : function(nodes) {
					var map = {}, imports = [];
					// Compute a map from name to node.
					nodes.forEach(function(d) {
						map[d.name] = d;
					});

					// For each import, construct a link from the source to
					// target node.
					nodes.forEach(function(d) {
						if (d.imports)
							d.imports.forEach(function(i) {
								imports.push({
									source : map[d.name],
									target : map[i]
								});
							});
					});
					return imports;
				}
			};
			/* keyword hint */
			$("#knowledgeGraph_input").autocomplete(
					{
						source : function(request, response) {
							$.ajax({
								url : "keywords_hint",
								method : 'post',
								dataType : "text",
								data : {
									keyword : request.term,
								},
								success : function(resp) {
									/* resp.length < 3 the resp is void */
									if (resp.length >= 3) {
										var keywordList = resp.substr(1,
												resp.length - 2).split(',');
										response(keywordList);
									}
								}
							});
						},
						minLength : 2,
						position : {
							offset : '-30 0'
						},
						select : function(event, ui) {
							return;
						}
					});
		});

// 查询事件
function eventSearch() {
	var event = $("#event_input").val();
	// 事件列表模板, 用 eventFormat.format(....)格式化
	// 依次需要6个参数 {0} 时间 {1} 时间时刻 {2} 链接
	// {3} 标题 {4} 链接 {5} 内容摘要
	var eventFormat = ""
			+ "<li>"
			+ "<time class=\"cbp_tmtime\" datetime=\"{0}\"><span>{1}</span> <span>事件时间</span></time>"
			+ "<div class=\"cbp_tmicon timeline-bg-success\">"
			+ "<i class=\"fa fa-file-o\"></i>"
			+ "</div>"
			+ "<div class=\"cbp_tmlabel\">"
			+ "<h2><a href=\"{2}\" target=_blank >{3}</a> <span>{4}</span></h2>"
			+ "<p>{5}</p>" + "</div>" + "</li>";
	if (event == "") {
		var opts = {
			"closeButton" : true,
			"debug" : false,
			"positionClass" : "toast-top-full-width",
			"onclick" : null,
			"showDuration" : "300",
			"hideDuration" : "1000",
			"timeOut" : "5000",
			"extendedTimeOut" : "1000",
			"showEasing" : "swing",
			"hideEasing" : "linear",
			"showMethod" : "fadeIn",
			"hideMethod" : "fadeOut"
		};
		toastr.error("内容不能为空!", "请输入相关内容", opts);
		return;
	}
	$("#event-list-tab").click();
	show_loading_bar(40);
	$("#loading-container").fadeIn(5000);
	$.ajax({
		url : "chaorder_search_post_old",
		method : "POST",
		dataType : "json",
		data : {
			query : event
		},
		success : function(resp) {
			// 如果查询到相关事件
			if (resp.states == 1) {
				var string = "<ul class=\"cbp_tmtimeline\">";
				resp.event_list.forEach(function(item, index, array) {
					string += eventFormat.format(item.time, item.time,
							item.url, item.title, item.url, item.content);
				});
				string += "</ul>";
				$("#event-list").html(string);

				// draw graph
				$("#quantitative-analysis-tab").click();
				$("#Risk-Reward-panel").removeClass("collapsed");
				$("#Roi-panel").removeClass("collapsed");

				drawRoiChart('Roi', resp.roi_date, resp.roi, resp.benchmark);

				var nor = new Array();
				var adv = new Array();
				for (let i = 0; i < 220; i++) {
					var temp = [ resp.all[i].final_std,
							resp.all[i].final_profit, resp.all[i].name ];
					if (i < 10) {
						adv.push(temp);
					} else {
						nor.push(temp);
					}
				}
				drawRiskRewardChart('Risk-Reward', nor, adv);
				$("#event-list-tab").click();
				show_loading_bar(100);
				$("#loading-container").fadeOut();
			} else {
				// 查询黑天鹅事件
				var opts = {
					"closeButton" : true,
					"debug" : false,
					"positionClass" : "toast-top-full-width",
					"onclick" : null,
					"showDuration" : "300",
					"hideDuration" : "1000",
					"timeOut" : "5000",
					"extendedTimeOut" : "1000",
					"showEasing" : "swing",
					"hideEasing" : "linear",
					"showMethod" : "fadeIn",
					"hideMethod" : "fadeOut"
				};
				toastr.error("未查询到相关内容，可能为突发事件", "尝试查询黑天鹅事件推演~", opts);
				$.ajax({
					url : "chaorder_search_black_swan",
					method : "POST",
					dataType : "json",
					data : {
						query : event
					},
					success : function(resp) {
						show_loading_bar(100);
						$("#loading-container").fadeOut();
						$("#black-swan-tab").click();
						if (resp.data.length >= 1) {
							drawPathChart(resp.data, "BlackSwan-ForceChart");
						} else {
							toastr.error("未查询到相关内容", "尚未训练于知识图谱中", opts);
						}
					}
				});
			}
		}
	});
}

function eventSearchNoHint() {
	if ($("#knowledgeGraph_input").val() != "") {
		alert($("#knowledgeGraph_input").val());
		window.location.hash='#knowledge-graph';
		//knowledgeGraphSearch();
		
	}
	if ($("#event_input").val() != "")
		eventSearch();
}

// 查询知识图谱
function knowledgeGraphSearch() {
	var keyword = $("#knowledgeGraph_input").val();
	if (keyword == "") {
		var opts = {
			"closeButton" : true,
			"debug" : false,
			"positionClass" : "toast-top-full-width",
			"onclick" : null,
			"showDuration" : "300",
			"hideDuration" : "1000",
			"timeOut" : "5000",
			"extendedTimeOut" : "1000",
			"showEasing" : "swing",
			"hideEasing" : "linear",
			"showMethod" : "fadeIn",
			"hideMethod" : "fadeOut"
		};
		toastr.error("内容不能为空!", "请输入相关内容", opts);
		return;
	}
	// 这里判断查询的是行业还是产品
	if (keyword.substr(keyword.length - 2, keyword.length) != "行业") {
		$
				.ajax({
					url : "knowledgeGraphProduct",
					method : "post",
					dataType : "json",
					data : {
						"keyword" : keyword
					},
					success : function(resp) {
						if (resp.state == 0) {
							var opts = {
								"closeButton" : true,
								"debug" : false,
								"positionClass" : "toast-top-full-width",
								"onclick" : null,
								"showDuration" : "300",
								"hideDuration" : "1000",
								"timeOut" : "5000",
								"extendedTimeOut" : "1000",
								"showEasing" : "swing",
								"hideEasing" : "linear",
								"showMethod" : "fadeIn",
								"hideMethod" : "fadeOut"
							};
							toastr.error("查询失败!", "没有找到相关内容", opts);
						} else {
							if (resp.data.axis == undefined) {
								drawIndustryTreeChart(resp.data,
										"KnowledgeGraph-TreeChart");
								return;
							}
							alert(2);
							drawProductTreeChart(resp.data,
									"KnowledgeGraph-TreeChart");
							alert(3);
						}
					}
				});
	} else {
		$
				.ajax({
					url : "knowledgeGraphIndustry",
					method : "post",
					dataType : "json",
					data : {
						"keyword" : keyword
					},
					success : function(resp) {
						if (resp.state == 0) {
							var opts = {
								"closeButton" : true,
								"debug" : false,
								"positionClass" : "toast-top-full-width",
								"onclick" : null,
								"showDuration" : "300",
								"hideDuration" : "1000",
								"timeOut" : "5000",
								"extendedTimeOut" : "1000",
								"showEasing" : "swing",
								"hideEasing" : "linear",
								"showMethod" : "fadeIn",
								"hideMethod" : "fadeOut"
							};
							toastr.error("查询失败!", "没有找到相关内容", opts);
						} else {
							drawIndustryTreeChart(resp.data,
									"KnowledgeGraph-TreeChart");
						}
					}
				});

	}
}