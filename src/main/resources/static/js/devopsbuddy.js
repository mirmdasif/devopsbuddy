$(document).ready(main);

function main() {
    $('.btn-collapse').click(function (e) {
        console.log(e);
        e.preventDefault();
        var $this = $(this);
        var $collapse = $this.closest('.collapse-group').find('.collapse');
        $collapse.collapse('toggle');
    });

    /* Contact form validation */
    $('#contactForm').formValidation({

        framework: 'bootstrap',

        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },

        fields: {

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
            password: {
                validators: {
                    notEmpty: {
                        message: 'Password can not be empty'
                    },

                    identical: {
                        field: 'confirmPassword',
                        message: 'Password and confirm password fields should be same'
                    }
                }
            },

            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: 'Password can not be empty'
                    },

                    identical: {
                        field: 'password',
                        message: 'Password and confirm password fields should be same'
                    }
                }
            }
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
            password: {
                validators: {
                    notEmpty: {
                        message: 'Password can not be empty'
                    }
                }
            },

            username: {
                validators: {
                    notEmpty: {
                        message: 'Username can not be empty'
                    }
                }
            }
        }
    });
}