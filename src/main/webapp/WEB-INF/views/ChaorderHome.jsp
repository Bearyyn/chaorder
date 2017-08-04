<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	
	<!-- meta name="viewport" content="width=device-width, initial-scale=1.0"-->
	<meta name="description" content="查尔德系统 人工智能 智能投顾 机器学习 深度学习">
	<meta name="author" content="深圳爱智慧科技有限公司">
	
	<title>爱智慧科技有限公司</title>
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
	<link rel="stylesheet" href="resources/css/chaorder_home_css/ChaorderHome.css">
	<!-- Jquery -->
	<script src="resources/assets/Xenon/js/jquery-1.11.1.min.js"></script>
	<script src="resources/js/chaorder_home_js/ChaorderHome.js"></script>
</head>

<body class="page-body lockscreen-page">
	<nav class="navbar horizontal-menu navbar-fixed-top">
		<div class="navbar-inner">
			<!-- Brand and Logo -->
			<div class="navbar-brand">
				<a href="javascript:void(0)" class="logo">
					<img src="resources/images/aitech.png" width="85" alt="" class="hidden-xs">
					<img src="resources/images/aitech.png" width="80" alt="" class="visible-xs">
				</a>
				<h2>爱智慧科技</h2>
			</div>
			<!-- Mobile Toggles Links -->
			<div class="nav navbar-mobile">
			
				<!-- This will toggle the mobile menu and will be visible only on mobile devices -->
				<div class="mobile-menu-toggle">
					<!-- This will open the popup with user profile settings, you can use for any purpose, just be creative -->
					<!--
					<a href="#" data-toggle="settings-pane" data-animate="true">
						<i class="linecons-cog"></i>
					</a>
					-->

					<!-- data-toggle="mobile-menu-horizontal" will show horizontal menu links only -->
					<!-- data-toggle="mobile-menu" will show sidebar menu links only -->
					<!-- data-toggle="mobile-menu-both" will show sidebar and horizontal menu links -->
					
					
					<img src="http://img4q.duitang.com/uploads/item/201502/19/20150219182942_ShZnM.jpeg" alt="user-image" class="img-circle img-inline userpic-32" width="28" />
					<div class="btn-group">
						<button type="button" class="btn btn-black dropdown-toggle" data-toggle="dropdown"style="background-color:2C2E2F;">
							${username} <span class="caret"></span>
						</button>
						<ul class="dropdown-menu dropdown-white" role="menu">
							<li>
								<a href="logout"><i class="fa-reply"></i>Logout</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="navbar-mobile-clear"></div>
			
			<div class="nav nav-userinfo navbar-right" id="navbody">
				<img src="http://img4q.duitang.com/uploads/item/201502/19/20150219182942_ShZnM.jpeg" alt="user-image" class="img-circle img-inline userpic-32" width="28" />
			
				<div class="btn-group">
					<button type="button" class="btn btn-black dropdown-toggle" data-toggle="dropdown"style="background-color:2C2E2F;">
						${username} <span class="caret"></span>
					</button>
					<ul class="dropdown-menu dropdown-white" role="menu">
						<li>
							<a href="logout"><i class="fa-reply"></i>Logout</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</nav>
	<div class="main-content">
		<div class="chaorder_logo_huge" id ="huge_log">
			<img src="resources/images/chaorder_white.png" id="huge_log_img"/>
		</div>
		<div class="discriptions" id="disc_content">
			<h2>基于事件驱动的投资分析工具</h2>
			<h4>Event - Driven Investment Analysis Tool</h4>
			<h3>90,000+ 全球事件，6,500,000+ 金融问题的分析与解答</h3>
			<h4>90,000+ Global Events and 6,500,000+ Financial Questions and Answers</h4>
		</div>
		
		<section class="search-env" >
			<div class="row" >
				<div class="col-md-2">
				</div>
				<div class="col-md-8">
					<form method="get" action="javascript:void(0);" enctype="application/x-www-form-urlencoded">
						<input type="text" class="form-control input-lg" placeholder="请输入您感兴趣的事件，支持中英文(Please input events in Chinese or English)" name="s" id="event_input"/>
						<button type="submit" class="btn-unstyled" onclick="eventSearch()" >
							<i class="linecons-search"></i>
						</button>
					</form>
				</div>
				<div class="col-md-2">
				</div>
			</div>
		</section>
		<div class="alert alert-danger" id="warning_1">
			该投资品种只支持中文查询(The investment supports only Chinese Query)
		</div>
		<div class="alert alert-danger" id="warning_2">
			该投资品种只支持英文查询(The investment supports only English Query)
		</div>
		<div class="alert alert-danger" id="warning_3">
			请输入事件(Please input your interested event)
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

	<!-- JavaScripts initializations and stuff -->
	<script src="resources/assets/Xenon/js/xenon-custom.js"></script>
	<script src="resources/js/chaorder_home_js/ChaorderHome.js"></script>


</body>
</html>
