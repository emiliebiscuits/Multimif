$(document).ready(function () {

    /*
     Tentative de connexion
     */
    $(document).on('submit', '#connexion_form', function (event) {
        event.preventDefault();

        var email = $('#connexion_form input[name="email"]').val();
        var mdp = $('#connexion_form input[name="mdp"]').val();
        var ok = true;

        //si les champs sont vides ont affiche des messages d'erreurs
        if (!email) {
            $('#connexion_form input[name="email"]').hasError("Please enter your pseudo or your email.");
            ok = false;
        }
        if (!mdp || mdp.length < 6) {
            $('#connexion_form input[name="mdp"]').hasError("Your password must be at least 6 characters.");
            ok = false;
        }

        if (ok) {
            //on POST les données
            var crypt = $.md5(mdp);
            $('#load_div').show();
            $.post($(this).attr("action"), {email: email, mdp: crypt}, function (retour) {
                $('#load_div').hide();
                if (retour.authentified) {
                    connexionOk(retour.pseudo, retour.accountid);
                } else {
                    $('#connexion_form').find("button").hasError("Incorrect email or password !");
                }
            }, "json")
                    .fail(function () {
                        $('#load_div').hide();
                        $('#connexion_form').find("button").hasError("An error has occurred !");
                    });
        }
    });

    function connexionOk(user, id) {
        $('#account_div a').first().attr("href", "/multimif/account/" + id).find('span').html(user);
        $('#log_div').hide();
        $('#account_div').show();

        //si tout est bon, on est connecté et on ferme le formulaire
        $('#connexion_form').find("button").hasSucces();
        $('#connexion_form').find("input[type=text], input[type=password]").val("");
        $('#connexion_div').modal("hide");
    }

    /*
     Vérif dynamique à la frappe si les champs sont remplis
     */
    $(document).on('keyup', '#connexion_form input[name="email"]', function (event) {
        /* Act on the event */
        $('#connexion_form').find("button").hasSucces();
        if ($(this).val()) {
            $(this).hasSucces();
        } else {
            $(this).hasError("Please enter your pseudo or your email.");
        }
    });
    $(document).on('keyup', '#connexion_form input[name="mdp"]', function (event) {
        /* Act on the event */
        $('#connexion_form').find("button").hasSucces();
        if ($(this).val().length >= 6) {
            $(this).hasSucces();
        } else {
            $(this).hasError("Your password must be at least 6 characters.");
        }
    });
});