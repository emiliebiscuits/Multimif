<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <%@include file="/WEB-INF/includes/styles.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sympozer | ${conference.Jena.getName()}</title>
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

                                <a href="" data-toggle="modal" data-target="#export_div" class="pull-right btn btn-success">Export this dataset</a>
                                <!-- POP UP DE CONNEXION -->
                                <div id="export_div" class="modal fade" role="dialog">
                                    <div class="modal-dialog">
                                        <div class="modal-content text-center">
                                            <div class="modal-header">
                                                <h4>Choose a format for the export</h4>
                                            </div>
                                            <div class="modal-body">
                                                <a href="/multimif/conferences/export/json/${conference.Sql.getId()}" class="exportFile btn btn-default">JSON</a>
                                                <a href="/multimif/conferences/export/rdf/${conference.Sql.getId()}" class="exportFile btn btn-default">RDF</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <h2 id="conf_name" confId="${conference.Sql.getId()}">${conference.Jena.getName()}</h2>

                                <div class="pull-right">
                                    <a href="/multimif/account/conferences">My conferences</a> <i class="glyphicon glyphicon-menu-right"></i>
                                    <span>${conference.Jena.getName()}</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-10 col-md-offset-1">
                            <ul class="nav nav-tabs">
                                <li class="table-bordered active"><a href="#tab-general" data-toggle="tab" aria-expanded="true">General informations</a></li>
                                <li class="table-bordered"><a href="#tab-chairs" data-toggle="tab" aria-expanded="false">Chairs</a></li>
                                <li class="table-bordered"><a href="#tab-events" data-toggle="tab" aria-expanded="false">Events</a></li>
                            </ul>
                            <div class="panel panel-default">
                                <div class="panel-body tab-content text-center">

                                    <div class="tab-pane active" id="tab-general">
                                        <h1>General informations</h1>

                                        <form class="form-horizontal col-md-8 col-md-offset-2 text-left">

                                            <div class="form-group" style="margin-top:15px">
                                                <label class="col-sm-3 control-label" style="text-align: left">Name</label>
                                                <div class="col-sm-9">
                                                    <p class="form-control-static">
                                                        <a class="xeditable" data-name="title" data-value="${conference.Jena.getName()}" data-type="text" data-pk="${conference.Sql.getId()}" href=""></a>
                                                    </p>
                                                </div>
                                            </div>

                                            <div class="form-group" style="margin-top:15px">
                                                <label class="col-sm-3 control-label" style="text-align: left">Description</label>
                                                <div class="col-sm-9">
                                                    <p class="form-control-static">
                                                        <a class="xeditable" data-name="description" data-value="${conference.Jena.getDescription()}" data-type="textarea" data-pk="${conference.Sql.getId()}" href=""></a>
                                                    </p>
                                                </div>
                                            </div>

                                            <div class="form-group" style="margin-top:15px">
                                                <label class="col-sm-3 control-label" style="text-align: left">Start date</label>
                                                <div class="col-sm-9">
                                                    <p class="form-control-static">
                                                        <a class="xeditable" data-name="startdate" data-value="${conference.Jena.getStartsAt()}" data-type="date" data-pk="${conference.Sql.getId()}" href=""></a>
                                                    </p>
                                                </div>
                                            </div>

                                            <div class="form-group" style="margin-top:15px">
                                                <label class="col-sm-3 control-label" style="text-align: left">End date</label>
                                                <div class="col-sm-9">
                                                    <p class="form-control-static">
                                                        <a class="xeditable" data-name="enddate" data-value="${conference.Jena.getEndsAt()}" data-type="date" data-pk="${conference.Sql.getId()}" href=""></a>
                                                    </p>
                                                </div>
                                            </div>

                                            <div class="form-group" style="margin-top:15px">
                                                <label class="col-sm-3 control-label" style="text-align: left">Tags</label>
                                                <div class="col-sm-9">
                                                    <p class="form-control-static">
                                                        <a class="xeditable" data-name="tags" data-value="${conference.Jena.getTopicsString()}" data-type="select2" data-pk="${conference.Sql.getId()}" href=""></a>
                                                    </p>
                                                </div>
                                            </div>

                                            <div class="form-group" style="margin-top:15px">
                                                <label class="col-sm-3 control-label" style="text-align: left">Website</label>
                                                <div class="col-sm-9">
                                                    <p class="form-control-static">
                                                        <a class="xeditable" data-name="website" data-value="${conference.Jena.getHomepage()}" data-type="text" data-pk="${conference.Sql.getId()}" href=""></a>
                                                    </p>
                                                </div>
                                            </div>

                                            <div class="form-group" style="margin-top:15px">
                                                <label class="col-sm-3 control-label" style="text-align: left">Place</label>
                                                <div class="col-sm-9">
                                                    <p class="form-control-static">
                                                        <a class="xeditable" data-name="place" data-value="${conference.Jena.getLocationsString()}" data-type="select2" data-pk="${conference.Sql.getId()}" href=""></a>
                                                    </p>
                                                </div>
                                            </div>


                                        </form>

                                    </div>


                                    <div class="tab-pane" id="tab-chairs">
                                        <h1>Chairs
                                            <br><small>Add chairs and allowed them to update this conference.</small>
                                        </h1>

                                        <p class="text-danger">* People without account will receive an email encouraging them to create one.</p>

                                        <div class="row text-left">
                                            <div class="col-md-10 col-md-offset-1">
                                                <div class="form-inline" style="margin-top: 50px">
                                                    <div class="form-group">
                                                        <label class="control-label" for="newchairs">Email</label>
                                                        <input id="newchairs" name="newchairs" class="select2 form-control" multiple="multiple" style="width: 300px"/>
                                                        <span class="help-block" style="display:none"></span>                                                
                                                    </div>
                                                    <button id="addChair" class="btn btn-default">+</button>
                                                </div>

                                                <div style="border: 1px black solid; margin-top: 20px; width:80%; max-height: 200px; overflow-y: auto">
                                                    <ul id="list_chairs" class="list-unstyled" style="padding: 10px">
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="tab-pane" id="tab-events">
                                        <a href="/multimif/account/events/addEvent/${conference.Sql.getId()}" class="btn btn-lg btn-default">add an event</a>

                                        <ul class="list-unstyled text-center" id="liste_conf">

                                            <c:forEach items="${conference.Events}" var="event">
                                                <li class="panel panel-default">
                                                    <div class="panel-body text-left">
                                                        <span class="pull-right text-success">${event.Jena.getStartsAt()} - ${event.Jena.getEndsAt()}</span>
                                                        <a href="/multimif/account/events/viewEvent/${event.Sql.getId()}">${event.Jena.getName()}</a>
                                                        <p>${event.Jena.getDescription()}</p>
                                                    </div>
                                                </li>
                                            </c:forEach>

                                        </ul>

                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <%@include file="/WEB-INF/includes/scripts.jsp" %>
        <script type="text/javascript">
            $(document).ready(function () {
                var idConf = $("#conf_name").attr("confId");

                //activer le lien du menu
                $('aside').find('a[href$="/multimif/account/conferences"]').closest('li').addClass('active');

                /*
                 Edition des données en live (plugin xeditable qui utilise ajax)
                 */
                $.fn.editable.defaults.mode = 'inline';
                var url = "/multimif/account/updateConference"; //url de la servlet
                var emptytext = "(not specified)";
                $('.xeditable').each(function () {
                    if ($(this).attr("data-type") === "date") {
                        $(this).editable({
                            format: 'mm/dd/yyyy',
                            viewformat: 'yyyy-mm-dd',
                            datepicker: {
                                weekStart: 1
                            },
//                            emptytext: emptytext,
                            url: url,
                            success: retourEditable,
                            error: errorEditable
                        });
                    } else if ($(this).attr("data-name") === "tags") {
                        $(this).editable({
                            select2: {
                                tags: tags_list,
                                placeholder: 'Tags...',
                                allowClear: true,
                                multiple: true
                            },
                            emptytext: emptytext,
                            url: url,
                            success: retourEditable,
                            error: errorEditable
                        });
                    }
                    else if ($(this).attr("data-name") === "place") {
                        $(this).editable({
                            select2: {
                                tags: ["Lyon", "Paris", "Marseille"],
                                placeholder: 'Place...',
                                allowClear: true,
                                multiple: true
                            },
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
                        if (response.name === "title") {
                            $('#conf_name').html(newValue);
                            $('#conf_name').next('div').find('span').html(newValue);
                        }
                        alertModif("success", "Changes saved !", false);
                    } else {
                        alertModif("danger", response.error, false);
                    }
                }
                function errorEditable(response, newValue) {
                    alertModif("danger", "An error has occurred !", false);
                }


                /*
                 * Ajout de Chairs
                 */

                function getChairs() {

                    $.get("/multimif/conferences/" + idConf + "/listUser", function (retour) {
                        $("#list_chairs").html("");

                        $.each(retour.items, function (index, user) {
                            var li = '\
                            <li style="margin-top: 10px">\
                                <a href="mailto:' + user.mail + '" style="margin-right: 20px">' + user.mail + '</a>\
                                <button disabled class="btn btn-sm btn-default glyphicon glyphicon-trash"></button>\
                            </li>';

                            $("#list_chairs").append(li);
                        });
                    }, "json");
                }
                getChairs();

                $(document).on("click", "#addChair", function () {
                    if ($("#newchairs").val() && isEmail($("#newchairs").val())) {
                        $.post("/multimif/conferences/" + idConf + "/addChair", {chair: $('#newchairs').val()}, function (retour) {
                            if (retour.ok === 'send') {
                                alertModif("success", "An invitation was sent to this email.", false);
                                getChairs();
                                $("#newchairs").val("");
                            } else if (retour.ok) {
                                alertModif("success", "Chair added!", false);
                                $("#newchairs").val("");
                                getChairs();
                            } else {
                                alertModif("danger", "An error as occured!", false);
                            }
                        }, "json");
                    }
                });

                $("#newchairs").autocomplete({
                    source: "/multimif/account/listUser",
                    dataType: 'json',
                    autoFocus: true,
                    position: {
                        my: "right top",
                        at: "right bottom"
                    },
                    data: {
                        maxRows: 12
                    },
                    select: function (event, ui) {
                        event.preventDefault();
                        //on rempli le champs texte
                        $(this).val(ui.item.mail);
                    }
                }).data("ui-autocomplete")._renderItem = function (ul, item) {
                    var pseudo = "";
                    if (item.pseudo !== "") {
                        pseudo = " <b>(" + item.pseudo + ")</b>";
                    }

                    return $("<li class='involved'>")
                            .append("<a>" + item.mail + pseudo + "</a>")
                            .appendTo(ul);
                };
            });
        </script>
    </body>
</html>

