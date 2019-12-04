$( document ).ready( function () {
    let registerForm = $( "#region-form" );
    registerForm.validate( {
        rules: {
            name: "required",
            regionId: {
                required: true,
                pattern: "\\b((BG-0[1-9])|(BG-1[0-9])|(BG-2[0-8]))\\b"
            },
            area: "required",
            population: "required"
        },

        messages: {
            name: "Моля въведете име.",
            regionId: {
                required: "Моля въведете ID номер.",
                pattern: "ID номерът трябва да е попълнен във формат 'BG-{XX}', където XX е число от 01 до 28."
            },
            area: "Моля въведете площ.",
            population: "Моля въведете население."
        },

        errorElement: "div",
        errorPlacement: function ( error, element ) {
            // Add the `invalid-feedback` class to the error element
            error.addClass( "invalid-feedback" );

            error.insertAfter( element );

            $("div.invalid-feedback").hide();
        },

        highlight: function ( element, errorClass, validClass ) {
            $( element ).addClass( "is-invalid" ).removeClass( "is-valid" );
        },
        unhighlight: function (element, errorClass, validClass) {
            $( element ).addClass( "is-valid" ).removeClass( "is-invalid" );
        }
    } );

    $("form").on("submit", function (e) {
        let isValid = $(e.target).valid();
        if(!isValid){
            e.preventDefault();
            return false;
        }
    });
} );