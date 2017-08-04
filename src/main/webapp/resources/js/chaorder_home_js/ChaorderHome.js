function eventSearch(){
	var event = $("#event_input").val();
	if(event == ""){
		$('#warning_3').fadeIn();
		$('#warning_3').fadeOut(5000);
		return;
	}
	window.location.href = "chaorder_search_home?query="+event;
}


