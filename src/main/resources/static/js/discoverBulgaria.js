$(function(){
	$("path").tooltip(
		{
			classes: {
				"ui-tooltip": "ui-corner-all"
			}, 
			hide:false, 
			show:false, 
			track:true, 
			position: { 
				my: "left+15 top-150", 
				at: "right center" 
				},
				content: function() {
					return $( '#' + $(this).attr('id')).html();
				},
			close: function () { $(".ui-helper-hidden-accessible > *:not(:last)").remove(); }
		}
	);
	
});