$.validator.setDefaults({
    ignore: []
    // any other default options and/or rules
});

$( document ).ready( function () {
    let factForm = $( "#fact-form" );
    factForm.validate( {
        rules: {
            title: {
                required: true,
                minlength: 3,
                maxlength: 100
            },
            //oldTitle: "required",
            type: "required",
            regionId: "required",
            description: {
                required: true,
                minlength: 100,
                maxlength: 500
            },
            imageUrl: "required",
            readMore: {
                pattern: "(^$|^(https?|ftp)://[^\\s/$.?#].[^\\s]*$)"
            }
        },

        messages: {
            title: {
                required: "Моля, въведете заглавие",
                minlength: "Заглавието трябва да бъде между 3 и 100 символа.",
                maxlength: "Заглавието трябва да бъде между 3 и 100 символа."
            },
            //oldTitle: "Моля изберете факт.",
            type: "Моля изберете тип.",
            regionId: "Моля изберете област.",
            description: {
                required: "Моля, добавете описание.",
                minlength: "Описанието трябва да бъде между 100 и 500 символа.",
                maxlength: "Описанието трябва да бъде между 100 и 500 символа."
            },
            imageUrl: "Моля прикачете снимка.",
            readMore: {
                pattern: "Невалидна интернет връзка"
            }
        },

        errorElement: "div",
        errorPlacement: function ( error, element ) {
            // Add the `invalid-feedback` class to the error element
            error.addClass( "invalid-feedback" );

            if ( element.prop( "type" ) === "checkbox" ) {
                error.insertAfter( element.next( "label" ) );
            }
            else if( element.prop( "type" ) === "radio" ){
                error.addClass("text-center").addClass("mt-3");
                error.insertAfter( $("#nature-div") );
            }
            else if(element.attr("id") === "imageUrl"){
                error.insertAfter( $( "div.file-field" ) );
            }
            else if(element.attr("id") === "regionId"){
                error.insertAfter( $( "#select-region" ) );
            }
            else {
                error.insertAfter( element );
            }

            $("div.invalid-feedback").hide();
        },

        highlight: function ( element, errorClass, validClass ) {
            $( element ).addClass( "is-invalid" ).removeClass( "is-valid" );

            if($( element ).attr("id") === "imageUrl"){
                $( "div.file-field" ).addClass( "is-invalid" ).removeClass( "is-valid" );
            }
            else if($( element ).attr("id") === "regionId"){
                $( "#select-region" ).addClass( "is-invalid" ).removeClass( "is-valid" );
            }
        },
        unhighlight: function (element, errorClass, validClass) {
            $( element ).addClass( "is-valid" ).removeClass( "is-invalid" );

            if($( element ).attr("id") === "imageUrl"){
                $( "div.file-field" ).addClass( "is-valid" ).removeClass( "is-invalid" );
            }
            else if($( element ).attr("id") === "regionId"){
                $( "#select-region" ).addClass( "is-valid" ).removeClass( "is-invalid" );
            }
        }
    } );

    let editFactForm = $( "#edit-fact-form" );
    editFactForm.validate( {
        rules: {
            title: {
                required: true,
                minlength: 3,
                maxlength: 100
            },
            oldTitle: "required",
            type: "required",
            regionId: "required",
            description: {
                required: true,
                minlength: 100,
                maxlength: 500
            },
            imageUrl: "required",
            readMore: {
                pattern: "(^$|^(https?|ftp)://[^\\s/$.?#].[^\\s]*$)"
            }
        },

        messages: {
            title: {
                required: "Моля, въведете заглавие",
                minlength: "Заглавието трябва да бъде между 3 и 100 символа.",
                maxlength: "Заглавието трябва да бъде между 3 и 100 символа."
            },
            oldTitle: "Моля изберете факт.",
            type: "Моля изберете тип.",
            regionId: "Моля изберете област.",
            description: {
                required: "Моля, добавете описание.",
                minlength: "Описанието трябва да бъде между 100 и 500 символа.",
                maxlength: "Описанието трябва да бъде между 100 и 500 символа."
            },
            imageUrl: "Моля прикачете снимка.",
            readMore: {
                pattern: "Невалидна интернет връзка"
            }
        },

        errorElement: "div",
        errorPlacement: function ( error, element ) {
            // Add the `invalid-feedback` class to the error element
            error.addClass( "invalid-feedback" );

            if ( element.prop( "type" ) === "checkbox" ) {
                error.insertAfter( element.next( "label" ) );
            }
            else if( element.prop( "type" ) === "radio" ){
                error.addClass("text-center").addClass("mt-3");
                error.insertAfter( $("#nature-div") );
            }
            else if(element.attr("id") === "imageUrl"){
                error.insertAfter( $( "div.file-field" ) );
            }
            else if(element.attr("id") === "regionId"){
                error.insertAfter( $( "#select-region" ) );
            }
            else if(element.attr("id") === "oldTitle"){
                error.insertAfter( $( "#add-edit-select" ) );
            }
            else {
                error.insertAfter( element );
            }

            $("div.invalid-feedback").hide();
        },

        highlight: function ( element, errorClass, validClass ) {
            $( element ).addClass( "is-invalid" ).removeClass( "is-valid" );

            if($( element ).attr("id") === "imageUrl"){
                $( "div.file-field" ).addClass( "is-invalid" ).removeClass( "is-valid" );
            }
            else if($( element ).attr("id") === "regionId"){
                $( "#select-region" ).addClass( "is-invalid" ).removeClass( "is-valid" );
            }
            else if($( element ).attr("id") === "oldTitle"){
                $( "#add-edit-select" ).addClass( "is-invalid" ).removeClass( "is-valid" );
            }
        },
        unhighlight: function (element, errorClass, validClass) {
            $( element ).addClass( "is-valid" ).removeClass( "is-invalid" );

            if($( element ).attr("id") === "imageUrl"){
                $( "div.file-field" ).addClass( "is-valid" ).removeClass( "is-invalid" );
            }
            else if($( element ).attr("id") === "regionId"){
                $( "#select-region" ).addClass( "is-valid" ).removeClass( "is-invalid" );
            }
            else if($( element ).attr("id") === "oldTitle"){
                $( "#add-edit-select" ).addClass( "is-valid" ).removeClass( "is-invalid" );
            }
        }
    } );

    $("form").on("submit", function (e) {
       let isValid = factForm.valid();
       console.log(isValid);
       if(!isValid){
           e.preventDefault();
           return false;
       }
    });
} );