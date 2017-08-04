<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- meta name="viewport" content="width=device-width, initial-scale=1.0" /-->
    <meta name="description" content="查尔德系统 人工智能 智能投顾 机器学习 深度学习" />
    <meta name="author" content="深圳爱智慧科技有限公司" />

    <title>查尔德—登录</title>
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
	
	<link rel="stylesheet" href="resources/css/chaorder_login_css/ChaorderLogin.css">

    <!-- Jquery -->
	<script src="resources/assets/Xenon/js/jquery-1.11.1.min.js"></script>
	<!-- Js -->
	<script src="resources/js/chaorder_login_js/login.js"></script>
	<script src="resources/js/chaorder_login_js/val-code-refresh.js"></script>
</head>
<body class="page-body login-page" style="background-color: #2c2e2f;">
    <!--Page Title Block-->
    <div class="page-title">
        <div class="title-env">
            <img class="chaorder_logo_white" src="resources/images/chaorder_white.png"/>
            <span >中 文</span>
            <span>|</span>
            <span style="color: gray;">English</span>
        </div>
        
        
    </div>
    <div class="row">
        <!--Login Container-->
        <div class="col-sm-4">
            <div class="login-container">
                <div class="row">
                    <div class="col-sm-12">
                        <!-- Add class "fade-in-effect" for login form effect -->
                        <form method="post" role="form" id="login" class="login-form fade-in-effect">
                            <div class="login-header">
                                <a href="javascript:void(0)" class="logo">
                                    <img class="chaorder_logo_login" src="resources/images/chaorder.png"/>
                                    <span>登录</span>
                                </a>
                                <p>超过90,000的宏观事件和6,500,000类问题金融分析与解答</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label" for="username">用户名</label>
                                <input type="text" class="form-control input-dark" name="username" id="username" autocomplete="off" />
                            </div>

                            <div class="form-group">
                                <label class="control-label" for="passwd">密码</label>
                                <input type="password" class="form-control input-dark" name="passwd" id="passwd" autocomplete="off" />
                            </div>


                            <div class="form-group">
                                <label class="control-label" for="validatecode">验证码（区分大小写 单击刷新）</label>
                                <input type="text" class="form-control input-dark validation_code_input" name="validatecode" id="validatecode" autocomplete="off" >
                                
                            </div>
                            
                            <img class="validation_code_img" src="resources/val-code/${valCodeAddress}.png" onclick="valCodeRefresh()" />
	
                            <div class="form-group">	 
                                <button type="submit" class="btn btn-dark  btn-block text-left" style="color: #fff!important;">
                                    <i class="fa-lock"></i>
                                    登录查尔德
                                </button>
                            </div>

                            <div class="login-footer">
                                <a href="#">忘记了您的密码?</a>
                                <div class="info-links">
                                    申请试用查尔德系统，请与我们联系
                                    <a href="mailto:azh@aitech.xin"><strong><u>azh@aitech.xin</u></strong></a>
                                </div>
                            </div>
                        </form>
                        

						<!-- shor message validation -->
						<div id="login2" class="login-form ">
                            <div class="login-header">
                                <a href="javascript:void(0)" class="logo">
                                    <img class="chaorder_logo_login" src="resources/images/chaorder.png"/>
                                    <span>Login</span>
                                </a>
                                <!-- p>90,000+ global events and 6,500,000+ financial questions</p -->
                                <p>超过90,000的宏观事件和6,500,000类问题金融分析与解答</p>
                            </div>
							<div class="Phone-Val" id="phone-val">
								<p>您是首次登录，点击获取验证码</p>
								<!-- <div class="form-group iphone-number"> -->
									 <!-- <label class="control-label" for="phone">Phone Number</label> -->
									<!-- <input type="text" class="form-control input-dark validation_code_input" name="phone" id="validatephone" autocomplete="off" > -->
									<!-- </input> -->
									
								<!-- </div> -->
								

								<div class="form-group iphone-val">
									<label class="control-label" for="vali-passwd">Input verification code</label>
									<input type="text" class="form-control input-dark" name="vali-passwd" id="vali-passwd" autocomplete="off" />
								</div>
								<button  id='iphone-number'>
									获取验证码 
								</button>
								<!-- <input type="button" id='iphone-number'  value="获取验证码" onclick="sendCode(this)"/> -->
								
								<div  id="tips1" style="display:none">
										
										
										 <p>短信发送成功，请注意查收.</p>
								</div>
								<div  id="tips2" style="display:none">
										
										 <p>短信发送失败，请检查号码和网络是否正常或联系客服</p>
								</div>
								<div  id="tips3" style="display:none">

										 <p>验证码错误，请重新输入.</p>
								</div>
								
								<div class="form-group">	 
									<div  class="btn btn-dark  btn-block text-left" id="short_val" style="color: #fff!important;">
										<i class="fa-lock"></i>
										登录查尔德
									</div>
								</div>
							
							
							
							
								<div class="login-footer">
	                                <a href="#">忘记了您的密码?</a>
	                                <div class="info-links">
	                                    申请试用查尔德系统，请与我们联系
	                                    <a href="mailto:azh@aitech.xin"><strong><u>azh@aitech.xin</u></strong></a>
	                                </div>
                            	</div>
							</div>
						</div>
						<!-- External login -->
						<div class="external-login">
							<a href="javascript:void(0)" class="facebook" onclick="loginByWechat()">
								<i class="fa-weixin"></i>
								使用微信登录
							</a>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		
		
		
		
		
		
		
		
        <div class="col-sm-8">
            <script type="text/javascript">
                jQuery(document).ready(function($)
                {
                    var map = $("#sample-map-widget");

                    var gdpData = { "AF": 16.63, "AL": 11.58, "DZ": 158.97, "AO": 85.81, "AG": 1.1, "AR": 351.02, "AM": 8.83, "AU": 1219.72, "AT": 366.26, "AZ": 52.17, "BS": 7.54, "BH": 21.73, "BD": 105.4, "BB": 3.96, "BY": 52.89, "BE": 461.33, "BZ": 1.43, "BJ": 6.49, "BT": 1.4, "BO": 19.18, "BA": 16.2, "BW": 12.5, "BR": 2023.53, "BN": 11.96, "BG": 44.84, "BF": 8.67, "BI": 1.47, "KH": 11.36, "CM": 21.88, "CA": 1563.66, "CV": 1.57, "CF": 2.11, "TD": 7.59, "CL": 199.18, "CN": 5745.13, "CO": 283.11, "KM": 0.56, "CD": 12.6, "CG": 11.88, "CR": 35.02, "CI": 22.38, "HR": 59.92, "CY": 22.75, "CZ": 195.23, "DK": 304.56, "DJ": 1.14, "DM": 0.38, "DO": 50.87, "EC": 61.49, "EG": 216.83, "SV": 21.8, "GQ": 14.55, "ER": 2.25, "EE": 19.22, "ET": 30.94, "FJ": 3.15, "FI": 231.98, "FR": 2555.44, "GA": 12.56, "GM": 1.04, "GE": 11.23, "DE": 3305.9, "GH": 18.06, "GR": 305.01, "GD": 0.65, "GT": 40.77, "GN": 4.34, "GW": 0.83, "GY": 2.2, "HT": 6.5, "HN": 15.34, "HK": 226.49, "HU": 132.28, "IS": 12.77, "IN": 1430.02, "ID": 695.06, "IR": 337.9, "IQ": 84.14, "IE": 204.14, "IL": 201.25, "IT": 2036.69, "JM": 13.74, "JP": 5390.9, "JO": 27.13, "KZ": 129.76, "KE": 32.42, "KI": 0.15, "KR": 986.26, "UNDEFINED": 5.73, "KW": 117.32, "KG": 4.44, "LA": 6.34, "LV": 23.39, "LB": 39.15, "LS": 1.8, "LR": 0.98, "LY": 77.91, "LT": 35.73, "LU": 52.43, "MK": 9.58, "MG": 8.33, "MW": 5.04, "MY": 218.95, "MV": 1.43, "ML": 9.08, "MT": 7.8, "MR": 3.49, "MU": 9.43, "MX": 1004.04, "MD": 5.36, "MN": 5.81, "ME": 3.88, "MA": 91.7, "MZ": 10.21, "MM": 35.65, "NA": 11.45, "NP": 15.11, "NL": 770.31, "NZ": 138, "NI": 6.38, "NE": 5.6, "NG": 206.66, "NO": 413.51, "OM": 53.78, "PK": 174.79, "PA": 27.2, "PG": 8.81, "PY": 17.17, "PE": 153.55, "PH": 189.06, "PL": 438.88, "PT": 223.7, "QA": 126.52, "RO": 158.39, "RU": 1476.91, "RW": 5.69, "WS": 0.55, "ST": 0.19, "SA": 434.44, "SN": 12.66, "RS": 38.92, "SC": 0.92, "SL": 1.9, "SG": 217.38, "SK": 86.26, "SI": 46.44, "SB": 0.67, "ZA": 354.41, "ES": 1374.78, "LK": 48.24, "KN": 0.56, "LC": 1, "VC": 0.58, "SD": 65.93, "SR": 3.3, "SZ": 3.17, "SE": 444.59, "CH": 522.44, "SY": 59.63, "TW": 426.98, "TJ": 5.58, "TZ": 22.43, "TH": 312.61, "TL": 0.62, "TG": 3.07, "TO": 0.3, "TT": 21.2, "TN": 43.86, "TR": 729.05, "TM": 0, "UG": 17.12, "UA": 136.56, "AE": 239.65, "GB": 2258.57, "US": 14624.18, "UY": 40.71, "UZ": 37.72, "VU": 0.72, "VE": 285.21, "VN": 101.99, "YE": 30.02, "ZM": 15.69, "ZW": 5.57 };

                    var vmap = map.vectorMap({
                            map: 'world_mill_en',
                            backgroundColor: '',
                            regionStyle: {
                              initial: {
                                "fill": '#fff',
                                "fill-opacity": 0.2,
                                "stroke": '',
                                "stroke-width": .7,
                                "stroke-opacity": .5
                              },
                              hover: {
                                "fill-opacity": 1,
                                "fill": "#ddd"
                              }
                            },
                            markerStyle: {
                                initial: {
                                    fill: '#fff',
                                    "stroke": "#fff",
                                    "stroke-width": 0,
                                    r: 2.5
                                },
                                selected: {
                                    fill: '#7c38bc',
                                    "stroke-width": 0
                                }
                            },
                            markers: [
                                {latLng: [42.58, 20.88], name: 'Kosovo'},
                                {latLng: [40.71, -74.00], name: 'New York'},
                                {latLng: [36.77, -119.41], name: 'California'},
                                {latLng: [-22.90, -43.19], name: 'Rio De Janiero'},
                                {latLng: [35.68, 139.69], name: 'Tokyo'},
                                {latLng: [59.32, 18.06], name: 'Stockholm'},
                                {latLng: [25.04, 55.18], name: 'Dubai'},
                                {latLng: [51.50, -0.12], name: 'London'},
                                {latLng: [-33.92, 18.42], name: 'Cape Town'},
                                {latLng: [22.37, 113.52], name: 'HongKong'},
                            ]
                        });

                });
            </script>
            <div class="xe-widget xe-map-stats">
                <div class="xe-map">
                    <div id="sample-map-widget"></div>
                </div>
                <div class="xe-details">
                    <div class="xe-label">
                        <h4>世界主要金融市场</h4>
                        <p>新闻地图及指数列表</p>
                    </div>

                    <ul class="list-unstyled" id="index_data_list">
                    <c:forEach items="${indexList}" var="index">
                    	<li class="row">
                        	<div class="xe-map-data">
                            	<span class="xe-label col-sm-4">${index.name}</span>
                                <c:if test="${index.change > '0'}">
                                 	<span class="badge badge-roundless badge-danger col-sm-3">${index.index}</span>
                                    <span class="badge badge-roundless badge-danger col-sm-3">+${index.change}</span>
                                </c:if>
                                <c:if test="${index.change < '0'}">
                                	<span class="badge badge-roundless badge-success col-sm-3">${index.index}</span>
                                    <span class="badge badge-roundless badge-success col-sm-3">${index.change}</span>
                                </c:if>
                        	</div>
                    	</li>    	
                    </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!-- Main Footer -->
    <!-- Choose between footer styles: "footer-type-1" or "footer-type-2" -->
    <!-- Add class "sticky" to  always stick the footer to the end of page (if page contents is small) -->
    <!-- Or class "fixed" to  always fix the footer to the end of page -->
    <footer class="main-footer sticky footer-type-2">
        <div class="footer-inner">
            <!-- Add your copyright text here -->
            <div class="footer-text">
                <a href="http://www.aitech.xin/" target="_blank" title="爱智慧科技">
                &copy; 2016
                <strong>Aitech Co.Ltd 粤ICP备16077995号</strong>
                </a>
            </div>
        </div>
    </footer>

	
	
	
	
    <!-- Java Scripts -->
	<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>  
	<!-- <script type="text/javascript">   -->
	<!-- document.write(returnCitySN["cip"]+','+returnCitySN["cname"])   -->
	<!-- </script> -->
	
    <script type="text/javascript">
        jQuery(document).ready(function($){
		<!-- alert(returnCitySN["cip"]); -->
		var clock = '';
			var nums = 30;
			var btn; 
			function checkNum(str){
				var re =/^1[3|7|5|8]\d{9}$/;
				if (re.test(str)) {
				return true;
				} 
				else {
				return false;
				}
			}
			$("#iphone-number").click(function(){ 
				<!-- phonenum = document.getElementById("validatephone").value; -->
				if (1){	
					btn = $(this);
					btn.attr("disabled",true); //将按钮置为不可点击
					
					btn.text('重新获取(' +nums+')');
					$.ajax({
						url: '/sms/',
						method: 'POST',
						dataType: 'json',
						data: {
							phonenum: '1',
						},
						success: function(resp)
						{
							if (resp=="0"){
								$('#tips1').fadeIn();
								$('#tips1').fadeOut(3000);
							}
							
							else if (resp=="-2"){
								window.location.href = ' ';
							}
							else{
								$('#tips2').fadeIn();
								$('#tips2').fadeOut(3000);	 
								}
						}
					});	
					clock = setInterval(doLoop, 1000); //一秒执行一次
					
				}
				else{
					$('#tips2').fadeIn();
					$('#tips2').fadeOut(3000);	 
				}
			});	 
					
			 function doLoop()
			 {
				 nums--;
				 if(nums > 0){
				  btn.text('重新获取(' +nums+')');
				 }else{
				  clearInterval(clock); //清除js定时器
				  btn.attr("disabled",false);
				  btn.text('获取验证码');
				  nums = 30; //重置时间
				 }
			}
		
		
		
		
		$("#short_val").click(function(){
				$.ajax({
						url: '/val_sms/',
						method: 'POST',
						dataType: 'json',
						data: {
							valnum: $('#vali-passwd').val(),
						},
						success: function(resp)
						{
							if (resp=="-1"){
								$('#tips3').fadeIn();
								$('#tips3').fadeOut(3000);
							}
							else if(resp=="1"){
								window.location.href = '/home/';
							}
							else{
								window.location.href = ' ';
							}
							
							
						}
					});	

			});
		
		
            
        });
    </script>
	
	<!--
	<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>  
		<script type="text/javascript">  
		alert(returnCitySN["cip"]+','+returnCitySN["cname"])  
	</script>
	-->

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
    <script src="resources/assets/Xenon/js/xenon-widgets.js"></script>
    <script src="resources/assets/Xenon/js/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
    <script src="resources/assets/Xenon/js/jvectormap/regions/jquery-jvectormap-world-mill-en.js"></script>

    <!-- JavaScripts initializations and stuff -->
    <script src="resources/assets/Xenon/js/xenon-custom.js"></script>

</body>
</html>