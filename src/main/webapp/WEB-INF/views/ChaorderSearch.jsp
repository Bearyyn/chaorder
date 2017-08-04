<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- meta name="viewport" content="width=device-width, initial-scale=1.0" /-->
    <meta name="description" content="查尔德系统 人工智能 智能投顾 机器学习 深度学习" />
    <meta name="author" content="深圳爱智慧科技有限公司" />

    <title>查尔德—事件搜索引擎</title>
    <link rel="icon" href="resources/images/aitech_icon.ico" type="image/x-icon">
    
    <!-- Xenon Templates -->
	<link rel="stylesheet" href="resources/assets/Xenon/css/fonts/linecons/css/linecons.css">
	<link rel="stylesheet" href="resources/assets/Xenon/css/fonts/fontawesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="resources/assets/Xenon/css/bootstrap.css">
	<link rel="stylesheet" href="resources/assets/Xenon/css/xenon-core.css">
	<link rel="stylesheet" href="resources/assets/Xenon/css/xenon-forms.css">
	<link rel="stylesheet" href="resources/assets/Xenon/css/xenon-components.css">
	<link rel="stylesheet" href="resources/assets/Xenon/css/xenon-skins.css">
	<link rel="stylesheet" href="resources/assets/Xenon/css/custom.css">
	
	<link rel="stylesheet" href="resources/css/chaorder_search_css/ChaorderSearch.css">
	
    <!-- Jquery -->
	<script src="resources/assets/Xenon/js/jquery-1.11.1.min.js"></script>
	
	<!-- EchartJs  -->
	<script src="resources/js/echarts.js"></script>
	<script src="resources/js/chaorder_search_js/DrawQuantChart.js"></script>
	
	<!-- D3Js -->
	<script src="resources/js/d3.v3.min.js"></script>
	<script src="resources/js/chaorder_search_js/DrawTreeChart.js"></script>
	<script src="resources/js/chaorder_search_js/DemoChart.js"></script>
	
	<!-- Event Search / Knowledge Graph Hint -->
    <script src="resources/assets/jquery-ui-1.12.1/jquery-ui.js" ></script>
    <link href="resources/assets/jquery-ui-1.12.1/jquery-ui.css" rel="Stylesheet"></link>
    <script src="resources/js/chaorder_search_js/EventSearch.js"></script>
    
</head>

<body class="page-body">
	<nav class="navbar horizontal-menu navbar-fixed-top">
		<div class="collapse navbar-collapse">
			<div class="navbar-left">
				<header class="logo-env">
					<div class="logo">
						<img src="resources/images/chaorder_white.png" id="logo"/>
					</div>	
				</header>
			</div>
			<!-- Search Input -->
			<section class="search-env">
				<div class="navbar-left">
						<form method="get" action="javascript:void(0);" enctype="application/x-www-form-urlencoded">
							<input type="text" class="form-control input-lg" placeholder="输入您感兴趣的事件,支持中英文" name="s" id="event_input"
							value="${query}"/>
							<!-- EventSearchJS的事件搜索 -->
							<button type="submit" class="btn-unstyled" onclick="eventSearch()" id="nav-search" >
								<i class="linecons-search"></i>
							</button>
						</form>
				</div>
			</section>
			<div class="nav nav-userinfo navbar-right" id="navbody">
				<img src="http://img4q.duitang.com/uploads/item/201502/19/20150219182942_ShZnM.jpeg" alt="user-image" class="img-circle img-inline userpic-32" width="28" />
				
				<div class="btn-group">
					<button type="button" class="btn btn-black dropdown-toggle" data-toggle="dropdown" style="background-color:2C2E2F;">
						${username} <span class="caret"></span>
					</button>
					<ul class="dropdown-menu dropdown-white" role="menu">
						<li>
							<a href="logout"><i class="fa-reply"></i>登出</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		
		
			
		
	</nav>
	
	<div class="page-container">
		<div class="sidebar-menu toggle-others fixed">	
			<div class="sidebar-menu-inner">	
				<!-- Main Menu -->	
				<ul class="main-menu" >					
					<li>
						<a href="#event-list" data-toggle="tab" id="event-list-tab" >
							<i class="fa-list-ul"></i>
							<span class="">事件列表</span>
						</a>
					</li>
					<li>
						<a href="#quantitative-analysis" data-toggle="tab" id="quantitative-analysis-tab">
							<i class="fa fa-line-chart"></i>
							<span class="">收益分析</span>
						</a>
					</li>
					<li id="li-knowledge-graph">
						<a href="#knowledge-graph" data-toggle="tab">
							<i class="fa fa-database"></i>
							<span class="">产业图谱</span>
						</a>
					</li>
					<li>
						<a href="http://www.chaorder.cn:8080/RDF-Parse02/" target="_blank">
							<i class="fa fa-database"></i>
							<span class="">机器阅读文本</span>
						</a>
					</li>
					<!-- Space Row -->
					<li>
						<a href="javascript:void(0)">
						</a>
					</li>
					<!-- Demo List -->
					<li>
						<a href="#black-swan" data-toggle="tab" id="black-swan-tab">
							<i class="fa fa-chain"></i>
							<span class="">黑天鹅推理 -- Demo</span>
						</a>
					</li>
					<li>
						<a href="#bayes-network" data-toggle="tab" id="bayes-network-tab">
							<i class="fa fa-qrcode"></i>
							<span class="">贝叶斯推断 -- Demo</span>
						</a>
					</li>
				</ul>
				<!-- End Menu -->
			</div>
		</div>
		<div class="main-content">
			<div class="row" id="loading-container" style="display:none;">
				<img src="resources/images/waiting.gif" id="loading-process">
			</div>
			<div class="tab-content">
				<!-- Event List Tab -->
				<div class="tab-pane" id="event-list" >
					<ul class="cbp_tmtimeline">			
						<!-- <li>
							<time class="cbp_tmtime" datetime="2014-10-03T03:45"><span>2014-10-03</span> <span>事件时间</span></time>
							
							<div class="cbp_tmicon timeline-bg-success">
								<i class="fa fa-file-o"></i>
							</div>
							
							<div class="cbp_tmlabel">
								<h2><a href="#">特朗普称权力交接进展顺利 新政府人事或将敲定</a> <span>http://official.aitech.xin:8080/chaorder_articles/209de43ac9d29af398406beac1ad4bcc.naf#ev6</span></h2>
								<p>当地时间11月9日凌晨，唐纳德·特朗普宣布，希拉里·克林顿已经致电给他，祝贺他赢得2016美国总统大选。美国共和党总统候选人特朗普成功逆转选前的不利局面，击败民主党候选人希拉里·克林顿。</p>
							</div>
						</li> -->
						
					</ul>
				</div>
				<!-- Quantitative Analysis Charts Tab -->
				<div class="tab-pane" id="quantitative-analysis" >
					<div class="panel panel-default panel-border" id="Risk-Reward-panel">
						<div class="panel-heading">
							事件驱动累积收益风险分析
							<div class="panel-options" >
								<a href="javascript:void(0)" data-toggle="panel" >
									<span class="collapse-icon" >&ndash;</span>
									<span class="expand-icon">+</span>
								</a>
							</div>
						</div>
						<div class="panel-body" id="Risk-Reward">
						</div>
					</div>


					<div class="panel panel-default panel-border" id="Roi-panel">
						<div class="panel-heading"> 
							事件驱动策略: 模拟事件前一天买入，后一天卖出
							<div class="panel-options" >
								<a href="javascript:void(0)" data-toggle="panel" >
									<span class="collapse-icon" >&ndash;</span>
									<span class="expand-icon">+</span>
								</a>
							</div>
						</div>
						<div class="panel-body" id="Roi"> 
						</div>
					</div>
				</div>
				<!-- Knowledge Graph Tree Charts Tab -->
				<div class="tab-pane" id="knowledge-graph" >
					<section class="search-env">
						<div class="row">
							<div class="col-md-12">
								<form method="get" action="javascript:void(0)" enctype="application/x-www-form-urlencoded">
									<input type="text" class="form-control input-lg" placeholder="产业图谱查询" name="s" value='${knowledgeGraphKeyword}' id="knowledgeGraph_input"/>
									<button type="submit" class="btn-unstyled" onclick="knowledgeGraphSearch()">
										<i class="linecons-search"></i>
									</button>
								</form>
							</div>
						</div>
						<div class="panel panel-default panel-border" id="KnowledgeGraph-TreeChart">
						</div>
					</section>
				</div>
				<!-- Black Swan Demo Tab -->
				<div class="tab-pane" id="black-swan" >
					<div class="panel panel-default panel-border">
						<div class="panel-heading">
							查尔德引擎黑天鹅事件推断
						</div>
						<div class="panel-body" id="BlackSwan-ForceChart"> 
						</div>
					</div>
				</div>
				<!-- Bayes Network Tab -->
				<div class="tab-pane" id="bayes-network">
					<div class="panel panel-default panel-border">
						<div class="panel-heading">
							<div class="col-md-3">
								<div class="panel panel-default" style="padding-left:0px;padding-right:0px;">
									<div class="panel-heading" style="padding:0px;">
										恐慌指数VIX
									</div>
									<div class="panel-body">
										<div class="btn-group btn-group-justified">
											<a type="button" class="btn btn-red btn-xs" onclick="bayesNetwork(0,1)">高</a>
											<a type="button" class="btn btn-success btn-xs" onclick="bayesNetwork(0,0)">低</a>
											<a type="button" class="btn btn-warning btn-xs" onclick="bayesNetwork(0,2)">未知</a>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="panel panel-default" style="padding-left:0px;padding-right:0px;">
									<div class="panel-heading" style="padding:0px;">
										通胀指数CPI
									</div>
									<div class="panel-body">
										<div class="btn-group btn-group-justified">
											<a type="button" class="btn btn-red btn-xs" onclick="bayesNetwork(1,1)">高</a>
											<a type="button" class="btn btn-success btn-xs" onclick="bayesNetwork(1,0)">低</a>
											<a type="button" class="btn btn-warning btn-xs" onclick="bayesNetwork(1,2)">未知</a>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="panel panel-default" style="padding-left:0px;padding-right:0px;">
									<div class="panel-heading" style="padding:0px;">
										美元指数USD
									</div>
									<div class="panel-body">
										<div class="btn-group btn-group-justified">
											<a type="button" class="btn btn-red btn-xs" onclick="bayesNetwork(2,1)">高</a>
											<a type="button" class="btn btn-success btn-xs" onclick="bayesNetwork(2,0)">低</a>
											<a type="button" class="btn btn-warning btn-xs" onclick="bayesNetwork(2,2)">未知</a>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="panel panel-default" style="padding-left:0px;padding-right:0px;">
									<div class="panel-heading" style="padding:0px;">
										黄金Gold
									</div>
									<div class="panel-body">
										<div class="btn-group btn-group-justified">
											<a type="button" class="btn btn-red btn-xs" onclick="bayesNetwork(3,1)">高</a>
											<a type="button" class="btn btn-success btn-xs" onclick="bayesNetwork(3,0)">低</a>
											<a type="button" class="btn btn-warning btn-xs" onclick="bayesNetwork(3,2)">未知</a>
										</div>
									</div>
								</div>
							</div>
						</div>
						
							
						<div class="panel-body" id="BayesNetwork-Chart" style="height:600px;"> 
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="modal-company-products">
		<div class="modal-dialog">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">相关的上市公司</h4>
				</div>
				<div class="modal-body">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>上市公司名称</th>
								<th>主营占比（%）</th>
							</tr>
						</thead>
						<tbody id="modal-company-products-tbody">
						</tbody>
					</table>
					<div class="panel panel-default panel-border">
						<div class="panel-heading" id="modal-company-products-graph-title">
						</div>
						<div class="panel-body" id="modal-company-products-graph-body" style="height:250px;"> 
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Bottom Scripts -->
	<script src="resources/assets/Xenon/js/bootstrap.min.js"></script>
	<script src="resources/assets/Xenon/js/TweenMax.min.js"></script>
	<script src="resources/assets/Xenon/js/resizeable.js"></script>
	<script src="resources/assets/Xenon/js/joinable.js"></script>
	<script src="resources/assets/Xenon/js/xenon-api.js"></script>
	<script src="resources/assets/Xenon/js/xenon-toggles.js"></script>
	<script src="resources/assets/Xenon/js/jquery-validate/jquery.validate.min.js"></script>
	<script src="resources/assets/Xenon/js/toastr/toastr.min.js"></script>

    <!-- Imported scripts on this page -->
	<script src="resources/assets/Xenon/js/devexpress-web-14.1/js/globalize.min.js"></script>
	<script src="resources/assets/Xenon/js/devexpress-web-14.1/js/dx.chartjs.js"></script>

    <!-- JavaScripts initializations and stuff -->
    <script src="resources/assets/Xenon/js/xenon-custom.js"></script>
    
	<script src="chaorder_main_product_occupation"></script>
	<script src="chaorder_main_product_price"></script>
	<script type="text/javascript">
    	var date_list = ["2016-10-01", "2016-2", "2016-3", "2016-4", "2016-5", "2016-6", "2016-7", "2016-8", "2016-9", "2016-10", "2016-11", "2016-12"];
    	var roi_list = [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 20, 18.2, 48.7, 18.8, 6.0, 2.3];
    	var benchmark_list =[3.9, 5.9, 11.1, 18.7, 48.3, 30, 23.6, 46.6, 55.4, 18.4, 10.3, 0.7];
    	var normal = [[5,5,'000001SZ'],[6,6,'00002SZ'],[3,4,'000003SZ']];
	    var chaorder_advice = [[1,1,'600001SH'],[2,2,'300001SH'],[3,3,'400001SH']];
	    var states = [2,2,2,2];
	    $("#quantitative-analysis").addClass('active');
	    
        roiCurveChart = drawRoiChart('Roi', date_list, roi_list, benchmark_list);
        riskRewardChart = drawRiskRewardChart('Risk-Reward', normal, chaorder_advice);
        $("#quantitative-analysis").removeClass('active');
        // 数据样例
        var linkData = [
        	  {source: "同花顺", target: "IFIND", rela:"主营产品"},
        	  {source: "同花顺", target: "手机金融信息服务",rela:"主营产品"},
        	];
        $("#bayes-network").addClass('active');
        drawBayesNetworkBarChart([0.54,0.96,0.44,0.92], [0.46,0.04,0.56,0.08], "BayesNetwork-Chart");
        $("#bayes-network").removeClass('active');
        $("#event-list").addClass('active');
        $(document).ready(function(){eventSearchNoHint();}());
        //$("#black-swan").addClass('active');
        // drawPathChart(links,"BlackSwan-ForceChart");
    </script>

 	
</body>
</html>