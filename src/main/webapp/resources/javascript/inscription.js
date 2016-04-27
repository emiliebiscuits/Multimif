$(document).ready(function () {

//    function fillSelect() {
//        $.ajax({
//            url: "/multimif/conferences/names.htm", //ressource générale des conférences
//            data: 'listConference',
//            method: 'GET',
//            dataType: 'json',
//            success: function (json) {
//                var tab = json.conferences;
//                for (var i = 0; i < tab.length; i++) {
//                    $('#listConf').append("<option value=" + tab[i] + ">" + tab[i] + "</option>");
//                }
//            },
//            error: function (xhr, status, error) {
//                alert(xhr.responseText);
//            }
//        });
//    }
//
//    fillSelect();


    /*
     Tentative d'inscription
     */
    $(document).on('submit', '#inscription-form', function (event) {

        event.preventDefault();

        var email = $('#inscription-form input[name="email"]').val();
        var mdp = $('#inscription-form input[name="pswd"]').val();
        var mdp2 = $('#inscription-form input[name="pswd2"]').val();

        var conf = $('#listConf').val();

        var ok = true;

        //si les champs sont vides ont affiche des messages d'erreurs
        if (!email || !isEmail(email)) {
            $('#inscription-form input[name="email"]').hasError("Please enter a valid email address.");
            ok = false;
        }
        if (!mdp || mdp.length < 6) {
            $('#inscription-form input[name="pswd"]').hasError("Your password must be at least 6 characters.");
            ok = false;
        }
        if (mdp !== mdp2) {
            $('#inscription-form input[name="pswd2"]').hasError("Passwords do not match!");
            ok = false;
        }

        if (ok) {
            var crypt = $.md5(mdp);

            $('#load_div').show();
            $.post($(this).attr("action"), {email: email, mdp: crypt, conf: conf}, function (retour) {
                $('#load_div').hide();
                if (retour.created === true) {
                    alertModif("success", "A confirmation email will be sent.", false, "Account created !");
                    inscriptionOk(retour.pseudo, retour.accountid);
                } else {
                    $('#inscription-form input[name="email"]').hasError("Cet email est déjà utilisé.");
                }
            }, "json")
                    .fail(function () {
                        alertModif("danger", "An error has occurred !", false);
                        $('#load_div').hide();
                    });
        }
    });
    
    function inscriptionOk(user, id){
        $('#account_div a').first().attr("href","/multimif/account/"+id).find('span').html(user);
        $('#log_div').hide();        
        $('#account_div').show();

        $('#inscription-form').find("input[type=text], input[type=password]").val("");        
        window.location.href = "/multimif/account/"+id;
    }


    $(document).on('keyup', '#inscription-form input[name="email"]', function (event) {
        /* Act on the event */
        $('#inscription-form').find("button").hasSucces();
        if (isEmail($(this).val())) {
            $(this).hasSucces();
        } else {
            $(this).hasError("Please enter a valid email address.");
        }

    });
    $(document).on('keyup', '#inscription-form input[name="pswd"]', function (event) {
        /* Act on the event */
        $('#inscription-form').find("button").hasSucces();
        if ($(this).val().length >= 6) {
            $(this).hasSucces();
        } else {
            $(this).hasError("Your password must be at least 6 characters.");
        }
    });
    $(document).on('keyup', '#inscription-form input[name="pswd2"]', function (event) {
        /* Act on the event */
        $('#inscription-form').find("button").hasSucces();
        if ($('#inscription-form input[name="pswd"]').val() === $(this).val()) {
            $(this).hasSucces();
        }
        else
        {
            $(this).hasError("Passwords do not match!");
        }
    });
});




