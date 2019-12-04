$( document ).ready( function () {
    let registerForm = $( "#register-form" );
    registerForm.validate( {
        rules: {
            username: "required",
            email: {
                required: true,
                pattern: "[a-zA-Z0-9._]+@[a-zA-Z]+\\.[a-z]{2,4}",
                email: true
            },
            password: {
                required: true,
                pattern: "(?=^.{6,}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s)[0-9a-zA-Z!@#$%^&*()]*$"
            },
            confirmPassword: {
                required: true,
                equalTo: "#password"
            }
        },

        messages: {
            username: "Липсва потребителско име.",
            email: {
                required: "Липсва имейл.",
                email: "Невалиден имейл.",
                pattern: "Невалиден имейл."
            },
            password: {
                required: "Липсва парола.",
                pattern: "Паролата трябва да съдържа най-малко 6 символа, поне една главна буква и една цифра."
            },
            confirmPassword: {
                required: "Моля, потвърдете паролата.",
                equalTo: "Паролите не съвпадат."
            }
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