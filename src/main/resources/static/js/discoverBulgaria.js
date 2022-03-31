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
		let theNavLink = $(navLinks[index]);
		if(theNavLink.attr("href") === url){
			theNavLink.parent().addClass("active");
			if(theNavLink.parent().hasClass("dropdown-item")){
				let ddParent = theNavLink.parents(".dropdown");
				ddParent.addClass("active");
			}
		}
	});
});
//Active button end

//Get facts and pois in home ajax
$("g").on("click", "path", function(event){
	event.preventDefault();

	$("path.active").removeClass("active");
	$(event.target).addClass("active");
	loadCards(event.target);

	return false;
});

function registerPageLinkClick() {
	$(".pagination").on("click", ".page-link", function (event) {
		event.preventDefault();
		loadCards(event.target);

		return false;
	});
}

function registerMapLinkClick() {

	let mapLinks = $(".map-link").toArray();
	let poiLats = $(".poiLat").toArray();
	let poiLngs = $(".poiLng").toArray();

	for(let i = 0; i < mapLinks.length; i++){
		$(mapLinks[i]).on("click", function (event) {
			event.preventDefault();

			let poiLat = $(poiLats[i]).val();
			let poiLng = $(poiLngs[i]).val();

			let poiCoords = {lat: poiLat, lng: poiLng};

			debugger
			let marker = new H.map.Marker(poiCoords);
			map.addObject(marker);
			map.getViewModel().setLookAtData(
				{
					position: poiCoords,
					zoom: 17
				},
				true
			);
			return false;
		});
	}
}

function loadCards(target){
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
		beforeSend: function(){
			let loader = $("#loader");
			loader.removeClass("d-none").addClass("d-flex");
			$(".preloader-wrapper").addClass("big");
			$(".card-deck").addClass("d-none");
		},
		success : function( response ) {
			setTimeout(() => {

				let loader = $("#loader");
				loader.removeClass("d-flex").addClass("d-none");
				$(".card-deck").removeClass("d-none");
				$("#pagination").removeClass("d-none");

				registerPageLinkClick();
				registerMapLinkClick();
			}, 2000);

			$("#deck").html(response);

		}
	});
}
//Home ajax end

$(function () {
	$(".all").children(".card-deck").removeClass("d-none");
});

function editRegion(){
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
}

function editFactPoi(isPoi){
	let url = isPoi ? "/poi/json" : "/facts/json";
	$.get(url, function (data) {
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

			//Select the region of the fact/poi from the list
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
}

function addRegion(){
	const regionId = $("#select-region option:selected").attr("id");
	$("#regionId").val(regionId);
}

//Select Poi/Fact in add or adit pages
$("#add-edit-select").on("click", function(){
	switch (window.location.pathname) {
		case "/facts/edit":
			editFactPoi(false);
			break;
		case "/poi/edit":
			editFactPoi(true);
	}
});
//End poi/fact select

$("#select-region").on("click", function () {
	if(window.location.pathname === "/regions/edit"){
		editRegion();
	}
	else{
		addRegion();
	}
});

//Manage user select
$("#select-user").on("click", function () {
	const theUser = $("#select-user option:selected").val();
	let url = "/users/json/" + theUser;

	let username = $("#username");
	username.val(theUser);

	if(theUser !== ""){
		$.get(url, function (data) {
			if(data !== undefined) {
				let isAdmin = $("#isAdmin");
				if(data.admin === true){
					isAdmin.prop("checked", true);
				}
				else{
					isAdmin.prop("checked", false);
				}
			}
		});
	}
});

//Submit button in fact/add & poi/add
$(".btn").on("click", function () {
	switch(window.location.pathname){
		case "/poi/add":
		case "facts/add":
			let theTitle = $("#title").val();
			$("#oldTitle").val(theTitle);
	}
});