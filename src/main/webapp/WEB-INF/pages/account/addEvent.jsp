<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <%@include file="/WEB-INF/includes/styles.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New event</title>
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
                                <h2>Create new event</h2>

                                <div class="pull-right">
                                    <a href="/multimif/account/conferences">My conferences</a> <i class="glyphicon glyphicon-menu-right"></i>
                                    <a href="/multimif/account/viewConference/${conference.getId()}">${conference.getName()}</a> <i class="glyphicon glyphicon-menu-right"></i>
                                    <span>New event</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="panel panel-default col-md-10 col-md-offset-1">
                            <div class="panel-body">
                                <form id="create-conf-form" action="/multimif/account/events/addEvent/${conference.getId()}" method="POST" class="col-md-10 col-md-offset-1 form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="title">Name : </label>
                                        <div class="col-sm-8">
                                            <input type="text" name="title" class="form-control" id="title"/>
                                            <span class="help-block" style="display:none"></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="type">Category : </label>
                                        <div class="col-sm-8">
                                            <input id="type" name="type" class="select2 form-control" multiple="multiple"/>
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
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="startdate">Start date : </label>
                                        <div class="col-sm-6">
                                            <input type="text" name="startdate" id="startdate" class="datepickerInput form-control"/>
                                            <span class="help-block" style="display:none"></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3  control-label" for="enddate">End date : </label>
                                        <div class="col-sm-6">
                                            <input type="text" name="enddate" id="enddate" class="datepickerInput form-control"/>
                                            <span class="help-block" style="display:none"></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3  control-label" for="tags">Tags</label>
                                        <div class="col-sm-6">
                                            <input id="tags" name="tags" class="select2 form-control" multiple="multiple"/>
                                            <span class="help-block" style="display:none"></span>
                                        </div>
                                    </div> 
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="website">Website : </label>
                                        <div class="col-sm-6">
                                            <input type="text" id="website" placeholder="http://" name="website" class="form-control" />
                                            <span class="help-block" style="display:none"></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="place">Place : </label>
                                        <div class="col-sm-6">
                                            <input id="place" name="place" class="select2 form-control" multiple="multiple"/>
                                            <span class="help-block" style="display:none"></span>
                                        </div>
                                    </div>
                                    <div class="form-group text-center">
                                        <br>
                                        <input type="hidden" name="id" value="${accountid}"/>
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
                    alertModif("danger", "Your event hasn't been created !", true, "Sorry, ");
                }

                //activer le lien du menu
                $('aside').find('a[href$="/multimif/account/conferences"]').closest('li').addClass('active');

                /*
                 * init des inputs
                 */
                var REGEX_EMAIL = '([a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&\'*+/=?^_`{|}~-]+)*@' +
                        '(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?)';
                var listChairs;
                $.get("/multimif/account/listUser", function (data) {
                    listChairs = data;
                });

                $("#tags").select2({
                    tags: tags_list
                });
                $("#place").select2({
                    tags: ["Lyon", "Paris", "Marseille"]
                });
                $("#type").select2({
                    tags: ["Lunch", "Session", "Presentation", "Workshop"]
                });

                $('.datepickerInput').datepicker({
                    format: "dd/mm/yyyy",
                    autoclose: true
                });

                //test des champs avant la validation du formulaire
                $(document).on("submit", "#create-conf-form", function (event) {
                    if (!formOk()) {
                        event.preventDefault();
                    }
                });

                function formOk() {
                    var ok = true;
                    var nom = $("#title").val();
                    var sdate = $("#startdate").val();
                    var edate = $("#enddate").val();
                    var desc = $("#desc").val();
                    var place = $("#place").val();
                    var tags = $("#tags").val();
                    var type = $("#type").val();
                    var website = $("#website").val();

                    if (!nom) {
                        $("#title").hasError("Mandatory field");
                        ok = false;
                    }
                    if (!sdate) {
                        $("#startdate").hasError("Mandatory field");
                        ok = false;
                    }
                    if (!edate) {
                        $("#enddate").hasError("Mandatory field");
                        ok = false;
                    }
                    if (sdate && edate && (sdate > edate)) {
                        $("#enddate").hasError("Ending date must be superior than the starting date");
                        ok = false;
                    }
                    if (website && !isWebsite(website)) {
                        $("#website").hasError("Wrong URL format");
                        ok = false;
                    }
                    if (!place) {
                        $("#place").hasError("Mandatory field");
                        ok = false;
                    }
                    if (!tags) {
                        $("#tags").hasError("Mandatory field");
                        ok = false;
                    }
                     if (!type) {
                        $("#type").hasError("Mandatory field");
                        ok = false;
                    }
                    return ok;
                }

                $(document).on('keyup change select', '#create-conf-form input', function (event) {
                    /* Act on the event */
                    if ($(this).val()) {
                        $(this).hasSucces();
                    }
                });

            });
        </script>
    </body>
</html>

