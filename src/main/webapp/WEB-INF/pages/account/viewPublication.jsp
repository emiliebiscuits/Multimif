<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <%@include file="/WEB-INF/includes/styles.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${publication.getTitle()}</title>
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
                                                <a href="" class="btn btn-default">JSON</a>
                                                <a href="" class="btn btn-default">SparQL</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <h2 id="publi_name">${publication.getName()}</h2>

                                <div class="pull-right">
                                    <a href="/multimif/account/publications">My publications</a> <i class="glyphicon glyphicon-menu-right"></i>
                                    <span>${publication.getName()}</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="panel panel-default col-md-10 col-md-offset-1">
                            <div class="panel-body tab-content text-center">

                                <h1>General informations</h1>

                                <form class="form-horizontal col-md-8 col-md-offset-2 text-left">

                                    <div class="form-group" style="margin-top:15px">
                                        <label class="col-sm-3 control-label" style="text-align: left">Name</label>
                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <a class="xeditable" data-name="title" data-value="${publication.getName()}" data-type="text" data-pk="${publication.getId()}" href=""></a>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group" style="margin-top:15px">
                                        <label class="col-sm-3 control-label" style="text-align: left">Description</label>
                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <a class="xeditable" data-name="description" data-value="${publication.getDescription()}" data-type="textarea" data-pk="${publication.getId()}" href=""></a>
                                            </p>
                                        </div>
                                    </div>

                                </form>



                                <hr>
                                <h1>Authors
                                    <br><small>Add co-authors and allowed them to update this publication.</small>
                                </h1>

                                <p class="text-danger">* People without account will receive an email encouraging them to create one.</p>

                                <div class="row text-left">
                                    <div class="col-md-10 col-md-offset-1">
                                        <div class="form-inline" style="margin-top: 50px">
                                            <div class="form-group">
                                                <label class="control-label" for="newchairs">Email or Pseudo</label>
                                                <input id="newchairs" name="newchairs" class="select2 form-control" multiple="multiple" style="width: 300px"/>
                                                <span class="help-block" style="display:none"></span>                                                
                                            </div>
                                            <button class="btn btn-default">+</button>
                                        </div>

                                        <div style="border: 1px black solid; margin-top: 20px; width:80%; max-height: 200px; overflow-y: auto">
                                            <ul class="list-unstyled" style="padding: 10px">
                                                <li style="margin-top: 10px">
                                                    <a href="" style="margin-right: 20px">bibi.toto@oups.fr</a>
                                                    <span class="text-success" style="margin-right: 20px"> (reconnu)</span>
                                                    <button class="btn btn-sm btn-default glyphicon glyphicon-trash"></button>
                                                </li>
                                                <li style="margin-top: 10px">
                                                    <a href="" style="margin-right: 20px">ok.lala@lalalala.com</a>
                                                    <span class="text-danger" style="margin-right: 20px"> (inconnu)</span>
                                                    <button class="btn btn-sm btn-default glyphicon glyphicon-trash"></button>
                                                </li>
                                                <li style="margin-top: 10px">
                                                    <a href="" style="margin-right: 20px">ok.lala@lalalala.com</a>
                                                    <span class="text-danger" style="margin-right: 20px"> (inconnu)</span>
                                                    <button class="btn btn-sm btn-default glyphicon glyphicon-trash"></button>
                                                </li>
                                                <li style="margin-top: 10px">
                                                    <a href="" style="margin-right: 20px">ok.lala@lalalala.com</a>
                                                    <span class="text-danger" style="margin-right: 20px"> (inconnu)</span>
                                                    <button class="btn btn-sm btn-default glyphicon glyphicon-trash"></button>
                                                </li>
                               
                                            </ul>
                                        </div>
                                    </div>
                                </div>

                                <hr>
                                <h2>Presentations</h2>

                                <ul class="list-unstyled text-center" id="liste_events">

                                    <c:forEach items="${list_events}" var="event">
                                        <li class="panel panel-default">
                                            <div class="panel-body text-left">
                                                <span class="pull-right text-success">${event.getDate()} - ${event.getDate()}</span>
                                                <a href="/multimif/conferences/viewEvent/${event.getId()}">${event.getName()}</a>
                                                <p>${event.getDescription()}</p>
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

        <%@include file="/WEB-INF/includes/scripts.jsp" %>
        <script type="text/javascript">
            $(document).ready(function () {

                //activer le lien du menu
                $('aside').find('a[href$="/multimif/account/publications"]').closest('li').addClass('active');


                /*
                 Edition des données en live (plugin xeditable qui utilise ajax)
                 */
                $.fn.editable.defaults.mode = 'inline';
                var url = "/multimif/account/updatePublication"; //url de la servlet
                var emptytext = "(not specified)";
                $('.xeditable').each(function () {
                    if ($(this).attr("data-type") === "date") {
                        $(this).editable({
                            format: 'mm/dd/yyyy',
                            viewformat: 'mm/dd/yyyy',
                            datepicker: {
                                weekStart: 1
                            },
                            emptytext: emptytext,
                            url: url,
                            success: retourEditable,
                            error: errorEditable
                        });
                    } else if ($(this).attr("data-type") === "select2") {
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
                            $('#publi_name').html(newValue);
                            $('#publi_name').next('div').find('span').html(newValue);
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
                 * Ajout de NEW Chairs
                 */
                $('#newchairs').select2({
                    minimumInputLength: 1,
                    ajax: {
                        url: "/multimif/publications/listUser",
                        dataType: 'json',
                        delay: 250,
                        cache: true,
                        quietMillis: 100,
                        results: function (data) {
                            return {results: data.items};
                        }
                    },
                    tags: true,
                    multiple: false
                });
            });
        </script>
    </body>
</html>

