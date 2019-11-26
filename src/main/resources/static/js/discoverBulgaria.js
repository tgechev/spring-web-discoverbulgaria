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
	loadCards(event.target);
});

let registerPageLinkClick = function () {
	$(".page-link").on("click", function (event) {
		event.preventDefault();
		loadCards(event.target);
	})
};

let loadCards = function(target){
	let regionId = null;
	let pageToShow = 1;

	let element;

	if($(target).hasClass("fas")){
		element = $(target).parent();
	}
	else{
		element = $(target);
	}

	if(element.attr("id") !== undefined){
		regionId = element.attr("id");
		sessionStorage.setItem("regionId", regionId);

	}

	else{
		regionId = sessionStorage.getItem("regionId");
		if(element.hasClass("next")){
			pageToShow = parseInt($(".active.page-item").children("a").html()) + 1;
		}
		else if(element.hasClass("prev")){
			pageToShow = parseInt($(".active.page-item").children("a").html()) - 1;
		}
		else{
			pageToShow = element.html();
		}
	}

	let cat = $(".cat.active").attr("id");
	let type = $(".type.active").attr("id");

	let url = "/home/" + regionId + "/" + cat + "/" + type + "?size=4&page=" + pageToShow;

	$.ajax({
		url : url,
		type : 'get',
		success : function( response ) {
			$("#deck").html(response);
			registerPageLinkClick();
		}
	});
};
//Home ajax end

let editRegionFunc = function(){
	$.get("/regions/json", function (data) {
		const theId = $("#select-region option:selected").attr("id");
		let res = data.find(({regionId}) => regionId === theId);
		if(res !== undefined) {

			$("#name").val(res.name);
			$("#area").val(res.area);
			$("#population").val(res.population);
			$("#regionId").val(res.regionId);
			$("#theId").val(res.regionId);

			//Make input fields active to trigger CSS animation
			$(".label-edit").addClass("active");
		}
	});
};

let editFactPoiFunc = function(isPoi){
	$.get("/poi/json", function (data) {
		const theTitle = $("#add-edit-select option:selected").html();
		let res = data.find(({title}) => title === theTitle);
		if(res !== undefined) {

			$("#title").val(res.title);
			$("#oldTitle").val(res.title);
			$("#description").val(res.description);
			$("#regionId").val(res.regionId);
			$("#readMore").val(res.readMore);

			if(isPoi) {
				$("#address").val(res.address);
				$("#latitude").val(res.latitude);
				$("#longitude").val(res.longitude);
			}

			let selector = "[id=" + res.regionId + "]";

			$(selector).prop("selected", true);

			let type = res.type.toLowerCase();
			if(type === "history"){
				$("#history-check").prop("checked", true);
			}
			else{
				$("#nature-check").prop("checked", true);
			}

			//Make input fields active to trigger CSS animation
			$(".label-edit").addClass("active");
		}
	});
};

let addRegionFunc = function(){
	const regionId = $("#select-region option:selected").attr("id");
	$("#regionId").val(regionId);
};

//Select Poi/Fact in add or adit pages
$("#add-edit-select").on("click", function(){
	switch (window.location.pathname) {
		case "/facts/edit":
			editFactPoiFunc(false);
			break;
		case "/poi/edit":
			editFactPoiFunc(true);
	}
});
//End poi/fact select

$("#select-region").on("click", function () {
	if(window.location.pathname === "/regions/edit"){
		editRegionFunc();
	}
	else{
		addRegionFunc();
	}
});