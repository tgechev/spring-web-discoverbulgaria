$.cloudinary.config({
    "cloud_name": "discover-bulgaria",
    "api_key": "345473919759429"
});

$(function() {
    if($.fn.cloudinary_fileupload !== undefined) {
        $("input.cloudinary-fileupload[type=file]").cloudinary_fileupload();
    }
});

$('.cloudinary-fileupload').bind('cloudinarydone', function(e, data) {
    let baseUrl = "https://res.cloudinary.com/discover-bulgaria/image/upload/";
    let secureUrl = data.result.secure_url;
    let imageUrl = secureUrl.substr(baseUrl.length);
    //console.log(data);
    //console.log(data.files[0].name);
    $("input.file-path").attr("value", data.files[0].name);
    $("#imageUrl").attr("value", imageUrl);
    $("#delete_token").attr("value", data.result.delete_token);
});

$(window).on("unload", function() {
    let delete_token = $("#delete_token").attr("value");
    let isSubmit = $("#isSubmit").attr("value");

    if(isSubmit === "false" && $(".file-path").attr("value") !== undefined){

        let dataType = "";

        if (jQuery.support.xhrFileUpload){
            dataType = 'json';
        }else{
            dataType = 'iframe json'
        }

        $.ajax({
            type: 'POST',
            url: 'https://api.cloudinary.com/v1_1/discover-bulgaria/delete_by_token',
            data: { token: delete_token },
            headers: {'X-Requested-With': 'XMLHttpRequest'},
            dataType: dataType,
            async: false
        });
    }

});

$("button").on("click", function () {
    $("#isSubmit").attr("value", "true");
});


