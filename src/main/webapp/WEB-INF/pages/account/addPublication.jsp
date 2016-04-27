<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <%@include file="/WEB-INF/includes/styles.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New publication</title>
    </head>
    <body>
        <div class="container-fluid">

            <%@include file="/WEB-INF/includes/header.jsp" %>

            <div class="row">
                <%@include file="/WEB-INF/includes/user_menu.jsp" %>
                <div class="col-md-10">


                    <div class="row" style="margin-top: 50px">
                        <div class="panel panel-default col-md-10 col-md-offset-1">
                            <div class="panel-body">
                                <h2>Add a new publication</h2>

                                <div class="pull-right">
                                    <a href="/multimif/account/publications">My publications</a> <i class="glyphicon glyphicon-menu-right"></i>
                                    <span>New publication</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="panel panel-default col-md-10 col-md-offset-1">
                            <div class="panel-body">
                                <form id="create-publi-form" action="/multimif/publications/add" method="POST" class="col-md-10 col-md-offset-1 form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="title">Name : </label>
                                        <div class="col-sm-8">
                                            <input type="text" name="title" class="form-control" id="title" value="title"/>
                                            <span class="help-block" style="display:none"></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="desc">Description : </label>
                                        <div class="col-sm-8">
                                            <textarea name="desc" id="desc" class="form-control" rows="5" cols="6" placeholder="Enter a quick description..."></textarea>
                                            <span class="help-block" style="display:none"></span>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group text-center">
                                        <br>
                                        <input type="hidden" name="id" value="${accountid}"/>
                                        <input type="hidden" name="idEvent" value="${event}"/>
                                        <button class="btn btn-info" type="submit" id="submit">Save</button>
                                        <span class="help-block" style="display:none"></span>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <%@include file="/WEB-INF/includes/scripts.jsp" %>
        <script type="text/javascript">
            $(document).ready(function () {

                if (getUrlParameter('error') === 'true') {
                    alertModif("danger", "Your publication hasn't been added !", true, "Sorry, ");
                }

                //activer le lien du menu
                $('aside').find('a[href$="/multimif/account/publications"]').closest('li').addClass('active');


                //test des champs avant la validation du formulaire
                $(document).on("submit", "#create-publi-form", function (event) {


                    if (!formOk()) {
//                        $.post($(this).attr("action"), $(this).serialize(), function (retour) {
//                            alertModif("success", retour, false, "Conference created");
//                        }, "json")
//                                .fail(function () {
//                                    $('#create-conf-form').find("button").hasError("An error has occurred !");
//                                });
                        event.preventDefault();
//                        $(this).submit();
                    }
                });

                function formOk() {
                    var ok = true;
                    var nom = $("#title").val();
                    var desc = $("#desc").val();

                    if (!nom) {
                        $("#title").hasError("Mandatory field");
                        ok = false;
                    }

                    return ok;
                }

                $(document).on('keyup change select', '#create-publi-form input', function (event) {
                    /* Act on the event */
                    if ($(this).val()) {
                        $(this).hasSucces();
                    }
                });

            });
        </script>
    </body>
</html>

