/*
 * @module 登录模块
 */
jQuery(document).ready(function($){
	/* 延迟1s 显示login表单 */
	setTimeout(function(){ $(".fade-in-effect").addClass('in'); }, 1);

    /* 验证ajax 表单 */
    $("form#login").validate({
        /*验证必填字段*/
        rules: {
            username: {
                required: true
            },
            passwd: {
                required: true
            },
            validatecode: {
                required: true
            },
        },
        /*错误信息*/
        messages: {
            username: {
                required: '请输入用户名'
            },
            passwd: {
                required: '请输入密码'
            },
            validatecode: {
                required: ''
            }
        },

        /* 通过ajax提交表单 */
        submitHandler: function(form)
        {
            show_loading_bar(70); 
            /* Toastr 信息配置 */
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

            $.ajax({
                url: 'login_check',
                method: 'POST',
                dataType: 'json',
                data: {
                    do_login: true,
                    username: $(form).find('#username').val(),
                    password: $(form).find('#passwd').val(),
                    valcode: $(form).find('#validatecode').val(),
    				// IP: returnCitySN["cip"],
                },
                success: function(resp)
                {
                    show_loading_bar({
                        delay: .5,
                        pct: 100,
                        finish: function(){
                            /* 登录后重定向 */
                            if(resp.accessGranted == 1)
                            {
                                window.location.href = "chaorder_home";
                            }
                            else if(resp.accessGranted == 0)
                            {
                                toastr.error("用户或者密码错误!", "登录失败!", opts);
                                ($(form).find('#passwd')).select();
    							
                            }
                            else if(resp.accessGranted == -1)
                            {
                                toastr.error("验证码错误!", "登录失败	!", opts);
                                ($(form).find('#validatecode')).select();
                            }
    						else if(resp.accessGranted == -2)
                            {    
    							login.style.display = "none";
    							login2.style.display = "block";
                            }
                        }
                    });
                }
            });
        }
    });
    /* 设置光标 */
    $("form#login .form-group:has(.form-control):first .form-control").focus();
});
/*
 * TODO 使用微信登录的功能
 */
function loginByWechat(){
	/* Toastr 信息配置 */
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
    toastr.error("抱歉此功能还没有开通", "正在施工中~", opts);
}
