//SVG map hover tooltip
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
				my: "left+15 top-170",
				at: "right center" 
				},
				content: function() {
					return $( '#' + $(this).attr('id')).html();
				},
			close: function () { $(".ui-helper-hidden-accessible > *:not(:last)").remove(); }
		}
	);
});
//Tooltip end

//Navbar update active button
$(function(){
	let url = window.location.pathname;
	let navLinks = $(".nav-link").map(function() {
		return this;
	}).get();

	$(".nav-item.active").removeClass("active");

	$(navLinks).each(function (index) {
		if($(navLinks[index]).attr("href") === url){
			$(navLinks[index]).parent().addClass("active");
		}
	});
});
//Active button end

//Get facts and pois in home ajax
$("path").on("click", function(event){
	event.preventDefault();
	let regionId = $(event.target).attr("id");
	let cat = $(".cat.active").attr("id");
	let type = $(".type.active").attr("id");

	let url = "/home/" + regionId + "/" + cat + "/" + type;

	$.ajax({
		url : url,
		type : 'get',
		success : function( response ) {
			$(".card-deck").html(response);
		}
	});

});
//Home ajax end

//Edit region form update fields on region select
$("#selectRegion").on("click", function(){
    $.get("/regions/json", function (data) {
        const theId = $("#selectRegion option:selected").attr("id");
        let res = data.find(({regionId}) => regionId === theId);
        if(res !== undefined) {
            $("#name").val(res.name);
            $("#regionId").val(res.regionId);
            $("#theId").val(res.regionId);
            $("#area").val(res.area);
            $("#population").val(res.population);
            $(".edit-region-label").addClass("active");
        }
    });
});
//End region select





