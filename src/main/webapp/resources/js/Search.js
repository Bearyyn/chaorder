$(function(){
	/* String Format Method */
	String.prototype.format = function(args) {
	    var result = this;
	    if (arguments.length < 1) {
	        return result;
	    }
	    var data = arguments; //如果模板参数是数组
	    if (arguments.length == 1 && typeof (args) == "object") {
	        data = args; //如果模板参数是对象
	    }
	    for (var key in data) {
	        var value = data[key];
	        if (undefined != value) {
	            result = result.replace("{" + key + "}", value);
	        }
	    }
	    return result;
	}
})

/* If the input is English Redirect to English Version */
function isEng(str){ 
	var reg = /.*^[A-Za-z0-9]+.*$/; 
	if(!reg.test(str)){ 
		return false; 
	} 
	return true; 
} 

function search(){
	show_loading_bar(30); 
	/* Result Html Template */
	var result_format_string = "<li><h3><a href=\"javascirpt:void(0)\">{0}</a></h3><p>{1}</p><a href=\"{2}\" class=\"link\" target=\"_blank\">{3}</a></li>";
	var search_keywords = $("#search_input").val();
	/* If input is English*/
	if(isEng(search_keywords)){
		var opts = {
			"closeButton": true,
			"debug": false,
			"positionClass": "toast-top-full-width",
			"onclick": null,
			"showDuration": "3000",
			"hideDuration": "1000",
			"timeOut": "10000",
			"extendedTimeOut": "1000",
			"showEasing": "swing",
			"hideEasing": "linear",
			"showMethod": "fadeIn",
			"hideMethod": "fadeOut"
		};
		toastr.info("Input text is in English", "Redirect to English Version in 3 seconds!", opts);
		show_loading_bar(100);
		var code = "window.open(\"http://www.chaorder.cn:8098/results_show/?EventText={0}~2\")".format(search_keywords);
		console.log(code);
		setTimeout(code, 3000);
		return ;
	}
	$.ajax({
		url: "customer/search",
		method: "POST",
		dataType: "json",
		data: {
			search: search_keywords
		},
		success: function(resp){
			show_loading_bar({
				delay: .5,
				pct: 100,
				finish: function(){
					if(resp.states == 1){
						$("#events_tab").click();
						$("#search_keywords").html(search_keywords);
						var string = "";
						resp.event_list.forEach(
							function(item, index, array){
								string += result_format_string.format(
											item.time, 
											item.content, 
											item.url, 
											item.url
										  );
							}
						);
						$("#event_result_list").html(string);
						date_list = resp.roi_date;
						roi_list = resp.roi;
						benchmark_list = resp.benchmark;
						/* rsk rwd */
						var nor = new Array();
						var adv = new Array();
						for(let i=0;i<220;i++){
							var temp = [resp.all[i].final_std, resp.all[i].final_profit, resp.all[i].name];
							if( i < 10){
								adv.push(temp);
							}else{
								nor.push(temp);
							}
						}
						normal = nor;
					    chaorder_advice = adv;
					}else{
						alert("void");
					}
				}
			});
		}
	});
}


