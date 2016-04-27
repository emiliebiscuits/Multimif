<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <%@include file="/WEB-INF/includes/styles.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Profil</title>
    </head>
    <body>
        <div class="container-fluid">

            <%@include file="/WEB-INF/includes/header.jsp" %>

            <div class="row">
                <%@include file="/WEB-INF/includes/user_menu.jsp" %>
                <div class="col-md-10">



                    <!-- COORDONNEES -->
                    <div class="row" style="margin-top: 60px">

                        <div class="col-md-3 text-center">
                            <!-- PHOTO -->
                            <img src="<c:url value="/resources/images/img_default.png"/>" alt="error image" class="img-circle" style="width:50%; height:auto">
                            <br><br>
                            <a href="">change my picture</a>                       
                        </div>

                        <div class="col-md-6">

                            <h1>Contact</h1>

                            <form class="form-horizontal">

<!--                                <div class="form-group" style="margin-top:40px">
                                    <label class="col-sm-3 control-label" style="text-align: left">Title</label>
                                    <div class="col-sm-9">
                                        <p class="form-control-static">
                                            <a class="xeditable" data-name="civilite" data-value="Mr" data-type="select" data-pk="" href=""></a>
                                        </p>
                                    </div>
                                </div>-->

                                <div class="form-group" style="margin-top:15px">
                                    <label class="col-sm-3 control-label" style="text-align: left">First name</label>
                                    <div class="col-sm-9">
                                        <p class="form-control-static">
                                            <a class="xeditable" data-name="prenom" data-value="${user.getPrenom()}" data-type="text" data-pk="${user.getId()}" href=""></a>
                                        </p>
                                    </div>
                                </div>

                                <div class="form-group" style="margin-top:15px">
                                    <label class="col-sm-3 control-label" style="text-align: left">Last name</label>
                                    <div class="col-sm-9">
                                        <p class="form-control-static">
                                            <a class="xeditable" data-name="nom" data-value="${user.getNom()}" data-type="text" data-pk="${user.getId()}" href=""></a>
                                        </p>
                                    </div>
                                </div>

                                <div class="form-group" style="margin-top:15px">
                                    <label class="col-sm-3 control-label" style="text-align: left">Country</label>
                                    <div class="col-sm-9">
                                        <p class="form-control-static">
                                            <a class="xeditable" data-name="pays" data-value="${user.getPays()}" data-type="select" data-pk="${user.getId()}" href=""></a>
                                        </p>
                                    </div>
                                </div>

                                <hr style="margin-top:15px">

                                <div class="form-group" style="margin-top:15px">
                                    <label class="col-sm-3 control-label" style="text-align: left">Website</label>
                                    <div class="col-sm-9">
                                        <p class="form-control-static">
                                            <a class="xeditable" data-name="site_web" data-value="${user.getpageWeb()}" data-type="text" data-pk="${user.getId()}" href=""></a>
                                        </p>
                                    </div>
                                </div>

                                <div class="form-group" style="margin-top:15px">
                                    <label class="col-sm-3 control-label" style="text-align: left">Affiliation</label>
                                    <div class="col-sm-9">
                                        <p class="form-control-static">
                                            <a class="xeditable" data-name="affiliation" data-value="${user.getAffiliation()}" data-type="text" data-pk="${user.getId()}" href=""></a>
                                        </p>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>


                    <!-- COMPTES FACEBOOK... -->
                    <div class="row text-center" style="border-top: 1px solid darkgray; margin-top: 20px">
                        <h1>Social networks <small><br>Use these accounts to connect faster!</small></h1>

                        <ul class="list-inline" style="margin-top:30px">
                            <li style="margin: 15px">
                                <a disabled class="btn btn-primary btn-lg fa fa-facebook fa-lg socialAuth" href=""></a>
                                <br>
                                <c:choose>
                                    <c:when test="${user.getIdFacebook() != null && user.getIdFacebook() > 0}">
                                        <i class="glyphicon glyphicon-ok-circle text-success btn-lg"></i>
                                        <br>
                                        <a href="" class="deconnect">log-out</a>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="glyphicon glyphicon-ban-circle text-danger btn-lg"></i>
                                        <br>
                                        <a class="deconnect">-</a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                            <li style="margin: 15px">
                                <a disabled class="btn btn-info btn-lg fa fa-twitter fa-lg socialAuth" href=""></a>
                                <br>
                                <c:choose>
                                    <c:when test="${user.getIdTwitter() != null && user.getIdTwitter() > 0}">
                                        <i class="glyphicon glyphicon-ok-circle text-success btn-lg"></i>
                                        <br>
                                        <a href="" class="deconnect">log-out</a>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="glyphicon glyphicon-ban-circle text-danger btn-lg"></i>
                                        <br>
                                        <a class="deconnect">-</a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                            <li style="margin: 15px">
                                <a disabled href="" style="-ms-transform: rotate(-90deg); -webkit-transform: rotate(-90deg); transform: rotate(-90deg);" class="btn btn-primary fa fa-lg socialAuth"><b>G</b></a>
                                <br>
                                <c:choose>
                                    <c:when test="${user.getIdGravatar() != null && user.getIdGravatar() > 0}">
                                        <i class="glyphicon glyphicon-ok-circle text-success btn-lg"></i>
                                        <br>
                                        <a href="" class="deconnect">log-out</a>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="glyphicon glyphicon-ban-circle text-danger btn-lg"></i>
                                        <br>
                                        <a class="deconnect">-</a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </ul>

                        <br><br><br>
                    </div>


                    <!-- PARAMÈTRES DU COMPTE -->
                    <div class="row text-center" style="border-top: 1px solid darkgray; margin-top: 20px;">
                        <h1>Account settings <small><br>These informations are used to connect.</small></h1>

                        <form class="form-horizontal text-left col-md-9 col-md-offset-3">

                            <div class="form-group" style="margin-top:40px">
                                <label class="col-sm-3 control-label" style="text-align: left">Pseudo</label>
                                <div class="col-sm-5">
                                    <p class="form-control-static">
                                        <a class="xeditable" data-name="pseudo" data-value="${user.getPseudo()}" data-type="text" data-pk="${user.getId()}" href=""></a>
                                    </p>
                                </div>
                            </div>


                            <div class="form-group" style="margin-top:40px">
                                <label class="col-sm-3 control-label" style="text-align: left">Email</label>
                                <div class="col-sm-5">
                                    <p class="form-control-static" id="actu_email">${user.getMail()}</p>
                                    <input type="text" class="form-control" placeholder="New email" name="new_email" data-pk="${user.getId()}"/><br>
                                    <span class="help-block" style="display:none"></span>
                                    <button class="btn btn-success" id="submit_email">Save Changes</button>
                                </div>
                            </div>

                            <hr>

                            <div class="form-group" style="margin-top:40px">
                                <label class="col-sm-3 control-label" style="text-align: left">Current Password</label>
                                <div class="col-sm-5">
                                    <input type="password" class="form-control" placeholder="Current Password" name="actu_mdp" data-pk="${user.getId()}"/><br>
                                    <span class="help-block" style="display:none"></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" style="text-align: left">New Password</label>
                                <div class="col-sm-5">
                                    <input type="password" class="form-control" placeholder="At least 6 characters" name="new_mdp"/><br>
                                    <span class="help-block" style="display:none"></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" style="text-align: left">Confirmation</label>
                                <div class="col-sm-5">
                                    <input type="password" class="form-control" placeholder="Confirm your new password" name="confirm_mdp"/>
                                    <span class="help-block" style="display:none"></span>
                                    <br>
                                    <button class="btn btn-success" id="submit_mdp">Save Changes</button>
                                </div>
                            </div>

                        </form>
                    </div>  

                    <br style="margin-top: 50px">

                </div>        
            </div>  

        </div>
        <%@include file="/WEB-INF/includes/scripts.jsp" %>
        <script type="text/javascript">
            $(document).ready(function () {

                //activer le lien du menu
                $('aside').find('a[href$="/multimif/account"]').closest('li').addClass('active');


                /*
                 Edition des données en live (plugin xeditable qui utilise ajax)
                 */
                $.fn.editable.defaults.mode = 'inline';
                var url = "/multimif/account/updateAccount"; //url de la servlet
                var emptytext = "(not specified)";
                $('.xeditable').each(function () {
                    if ($(this).attr("data-name") === "civilite") {
                        $(this).editable({
                            source: [
                                {value: 'Mr', text: 'Mr'},
                                {value: 'Mme', text: 'Mrs'}
                            ],
                            emptytext: emptytext,
                            url: url,
                            success: retourEditable,
                            error: errorEditable
                        });
                    } else if ($(this).attr("data-name") === "pays") {
                        $(this).editable({
                            source: list_pays,
                            emptytext: emptytext,
                            url: url,
                            success: retourEditable,
                            error: errorEditable
                        });
                    } else {
                        $(this).editable({
                            emptytext: emptytext,
                            url: url,
                            success: retourEditable,
                            error: errorEditable
                        });
                    }
                });
                function retourEditable(response, newValue) {
                    if (response.update) {
                        alertModif("success", "Changes saved !", false);
                    } else {
                        alertModif("danger", response.error, false);
                    }
                }
                function errorEditable(response, newValue) {
                    alertModif("danger", "An error has occurred !", false);
                }


                /*
                 Modification de l'email
                 */
                $(document).on('click', '#submit_email', function (event) {
                    event.preventDefault();
                    var new_email = $('input[name="new_email"]').val();
                    if (isEmail(new_email)) {
                        if (confirm("Confirm this new email : " + new_email + " ?")) {
                            $.post("/multimif/account/updateAccount", {name: "email", value: new_email, pk: $('input[name="new_email"]').attr("data-pk")}, function (retour) {
                                if (retour.update) {
                                    $('#actu_email').html(new_email);
                                    $('input[name="new_email"]').val('');
                                    alertModif("success", "New email saved !", false);
                                } else {
                                    alertModif("danger", retour.error, false);
                                }
                            }, "json")
                                    .fail(function () {
                                        alertModif("danger", "An error has occurred !", false);
                                    });
                        }
                    } else {
                        $('input[name="new_email"]').hasError("Please enter a valid email address.");
                    }
                });

                /*
                 Modification du mot de passe 
                 */
                $(document).on('click', '#submit_mdp', function (event) {
                    event.preventDefault();
                    var actu_mdp = $('input[name="actu_mdp"]').val();
                    var new_mdp = $('input[name="new_mdp"]').val();
                    var confirm_mdp = $('input[name="confirm_mdp"]').val();

                    if (!new_mdp) {
                        $('input[name="actu_mdp"]').hasError("Please enter your current password.");
                    } else if (new_mdp.length < 6) {
                        $('input[name="new_mdp"]').hasError("Your password must be at least 6 characters.");
                    } else {

                        if (new_mdp !== confirm_mdp) {
                            $('input[name="confirm_mdp"]').hasError("Passwords do not match!");
                        } else {
                            //on envoie l'ancien et le nouveau mot de passe en crypté ( et vérification coté serveur )
                            $.post("/multimif/account/updateAccount", {name: "mdp", actuMdp: $.md5(actu_mdp), value: $.md5(new_mdp), pk: $('input[name="actu_mdp"]').attr("data-pk")}, function (retour) {
                                if (!retour.update) {
                                    alertModif("danger", retour.error, false);
                                }
                                if (retour.update && retour.mdp === $.md5(new_mdp)) {
                                    $('input[name="actu_mdp"]').val('').hasSucces();
                                    $('input[name="new_mdp"]').val('').hasSucces();
                                    $('input[name="confirm_mdp"]').val('').hasSucces();
                                    alertModif("success", "New password saved !", false);
                                } else {
                                    $('input[name="actu_mdp"]').hasError("Incorrect password");
                                }
                            }, "json")
                                    .fail(function () {
                                        alertModif("danger", "An error has occurred !", false);
                                    });
                        }
                    }
                });

                $(document).on('keyup', 'input[name="new_email"]', function (event) {
                    if (isEmail($(this).val())) {
                        $(this).hasSucces();
                    }
                });

                $(document).on('keyup', 'input[name="confirm_mdp"]', function (event) {
                    var new_mdp = $('input[name="new_mdp"]').val();
                    var confirm_mdp = $('input[name="confirm_mdp"]').val();

                    if (new_mdp && new_mdp === confirm_mdp) {
                        $(this).hasSucces();
                    } else {
                        $(this).hasError("Passwords do not match!");
                    }
                });

                $(document).on('keyup', 'input[name="actu_mdp"]', function (event) {
                    var actu_mdp = $('input[name="actu_mdp"]').val();
                    if (actu_mdp) {
                        $(this).hasSucces();
                    }
                });

                $(document).on('keyup', 'input[name="new_mdp"]', function (event) {
                    var new_mdp = $('input[name="new_mdp"]').val();
                    if (new_mdp.length >= 6) {
                        $(this).hasSucces();
                    }
                });


                /*
                 CONNEXION FACEBOOK....
                 */

                $(document).on('click', '.socialAuth', function (event) {
                    event.preventDefault();
                    var li = $(this).parent();
                    $(li).find('.glyphicon-ban-circle').removeClass('glyphicon-ban-circle').removeClass('text-danger').addClass('glyphicon-ok-circle').addClass('text-success');
                    $(li).find('.deconnect').attr('href', '').html("log-out");
                });

                $(document).on('click', '.deconnect', function (event) {
                    event.preventDefault();
                    var li = $(this).parent();
                    $(li).find('.glyphicon-ok-circle').removeClass('glyphicon-ok-circle').addClass('text-danger').addClass('glyphicon-ban-circle').removeClass('text-success');
                    $(li).find('.deconnect').attr('href', '').html("-");
                });

            });
        </script>    
    </body>
</html>
