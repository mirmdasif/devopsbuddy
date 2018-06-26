$(document).ready(main);

function main() {
    $('.btn-collapse').click(function (e) {
        console.log(e);
        e.preventDefault();
        var $this = $(this);
        var $collapse = $this.closest('.collapse-group').find('.collapse');
        $collapse.collapse('toggle');
    });

    var validators = {
        email: {

            validators: {

                notEmpty: {
                    message: 'Email address is required'
                },

                emailAddress: {
                    message: 'Please input a valid email address'
                }
            }

        },

        username: {
            validators: {
                notEmpty: {
                    message: 'Username can not be empty'
                }
            }
        },

        firstName: {
            validators: {
                notEmpty: {
                    message: 'First name is required'
                }
            }
        },

        lastName: {
            validators: {
                notEmpty: {
                    message: 'Last name is required'
                }
            }
        },

        password: {
            validators: {
                notEmpty: {
                    message: 'Password can not be empty'
                }
            }
        },

        confirmPassword: {
            validators: {

                identical: {
                    field: 'password',
                    message: 'The password and its confirm are not the same'
                }
            }
        }
    };

    /* Contact form validation */
    $('#contactForm').formValidation({

        framework: 'bootstrap',

        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },

        fields: {

            email: validators.email,

            firstName: validators.firstName,

            lastName: validators.lastName,

            feedback: {
                validators: {
                    notEmpty: {
                        message: 'Your feedback is valued and required'
                    }
                }
            }

        }
    });

    $('#forgotPasswordForm').formValidation();

    $('#changePasswordForm').formValidation({
        framework: 'bootstrap',

        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },

        fields: {

            password: validators.password,

            passwordConfirm: validators.confirmPassword
        }

    });

    $('#loginForm').formValidation({
        framework: 'bootstrap',

        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },

        fields: {
            password: validators.password,

            username: validators.username
        }
    });

    $('#signupForm').formValidation({

        framework: 'bootstrap',

        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validation: 'glyphicon glyphicon-refresh'
        },

        fields: {

            username: validators.username,

            email: validators.email,

            password: validators.password,

            passwordConfirm: validators.confirmPassword,

            firstName: validators.firstName,

            lastName: validators.lastName,

            description: {
                validators: {
                    stringLength: {
                        message: 'Post content must be less than 300 characters',
                        min: 0,
                        maz: function (value) {
                            return 300 - (value.match(/\r/g) || []).length;
                        }
                    }
                }
            },

            phoneNumber: {
                validators: {
                    notEmpty: {
                        message: 'The phone number is required'
                    },

                    phone: {
                        country: 'country',
                        message: 'The value is not valid %s phone number'
                    }
                }
            }
        }

    }).on('change', ['name="password"'], function () {
        $('#signupForm').formValidation('revalidateField', 'confirmPassword');
    }).on('change', ['name="country"'], function () {
        $('#signupForm').formValidation('revalidateField', 'phoneNumber');
    });

}