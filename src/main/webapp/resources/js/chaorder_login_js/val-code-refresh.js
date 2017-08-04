/*
 * @module 验证码刷新模块
 */

function valCodeRefresh(){
	$.ajax({
		url: 'refresh_val_code',
		method: 'GET',
		dataType: 'Text',
		success: function(resp){
			if(resp == "error"){
				alert("刷新验证码失败，请重试");		
			} else {
				$(".validation_code_img").attr("src","resources/val-code/"+resp+".png");		
			}
		 }
	});
}

