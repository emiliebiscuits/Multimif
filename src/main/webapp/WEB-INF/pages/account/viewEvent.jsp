<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <%@include file="/WEB-INF/includes/styles.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${event.Jena.getName()}</title>
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
                                <h2 id="conf_name">${event.Jena.getName()}</h2>

                                <div class="pull-right">
                                    <a href="/multimif/account/conferences">My conferences</a> <i class="glyphicon glyphicon-menu-right"></i>
                                    <a href="/multimif/account/viewConference/${event.Parent.getId()}">${event.Parent.getName()}</a> <i class="glyphicon glyphicon-menu-right"></i>
                                    <span>${event.Jena.getName()}</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-10 col-md-offset-1">
                            <div class="panel panel-default">
                                <div class="panel-body tab-content text-center">
                                    
                                    <div class="pull-right">
                                        <a disabled href="/multimif/account/publications/newPublication/${event.Sql.getId()}" class="btn btn-sm btn-success">Add a NEW publication</a>
                                    </div>

                                    <h1>General informations</h1>

                                    <form class="form-horizontal col-md-8 col-md-offset-2 text-left">

                                        <div class="form-group" style="margin-top:15px">
                                            <label class="col-sm-3 control-label" style="text-align: left">Name</label>
                                            <div class="col-sm-9">
                                                <p class="form-control-static">
                                                    <a class="xeditable" data-name="title" data-value="${event.Jena.getName()}" data-type="text" data-pk="${event.Sql.getId()}" href=""></a>
                                                </p>
                                            </div>
                                        </div>

                                        <div class="form-group" style="margin-top:15px">
                                            <label class="col-sm-3 control-label" style="text-align: left">Category</label>
                                            <div class="col-sm-9">
                                                <p class="form-control-static">
                                                    <a class="xeditable" data-name="type" data-value="${event.Jena.getCategoriesString()}" data-type="select2" data-pk="${event.Sql.getId()}" href=""></a>
                                                </p>
                                            </div>
                                        </div>

                                        <div class="form-group" style="margin-top:15px">
                                            <label class="col-sm-3 control-label" style="text-align: left">Description</label>
                                            <div class="col-sm-9">
                                                <p class="form-control-static">
                                                    <a class="xeditable" data-name="description" data-value="${event.Jena.getDescription()}" data-type="textarea" data-pk="${event.Sql.getId()}" href=""></a>
                                                </p>
                                            </div>
                                        </div>

                                        <div class="form-group" style="margin-top:15px">
                                            <label class="col-sm-3 control-label" style="text-align: left">Start date</label>
                                            <div class="col-sm-9">
                                                <p class="form-control-static">
                                                    <a class="xeditable" data-name="startdate" data-value="${event.Jena.getStartsAt()}" data-type="date" data-pk="${event.Sql.getId()}" href=""></a>
                                                </p>
                                            </div>
                                        </div>

                                        <div class="form-group" style="margin-top:15px">
                                            <label class="col-sm-3 control-label" style="text-align: left">End date</label>
                                            <div class="col-sm-9">
                                                <p class="form-control-static">
                                                    <a class="xeditable" data-name="enddate" data-value="${event.Jena.getEndsAt()}" data-type="date" data-pk="${event.Sql.getId()}" href=""></a>
                                                </p>
                                            </div>
                                        </div>

                                        <div class="form-group" style="margin-top:15px">
                                            <label class="col-sm-3 control-label" style="text-align: left">Tags</label>
                                            <div class="col-sm-9">
                                                <p class="form-control-static">
                                                    <a class="xeditable" data-name="tags" data-value="${event.Jena.getTopicsString()}" data-type="select2" data-pk="${event.Sql.getId()}" href=""></a>
                                                </p>
                                            </div>
                                        </div>

                                        <div class="form-group" style="margin-top:15px">
                                            <label class="col-sm-3 control-label" style="text-align: left">Website</label>
                                            <div class="col-sm-9">
                                                <p class="form-control-static">
                                                    <a class="xeditable" data-name="website" data-value="${event.Jena.getHomepage()}" data-type="text" data-pk="${event.Sql.getId()}" href=""></a>
                                                </p>
                                            </div>
                                        </div>

                                        <div class="form-group" style="margin-top:15px">
                                            <label class="col-sm-3 control-label" style="text-align: left">Place</label>
                                            <div class="col-sm-9">
                                                <p class="form-control-static">
                                                    <a class="xeditable" data-name="place" data-value="${event.Jena.getLocationsString()}" data-type="select2" data-pk="${event.Sql.getId()}" href=""></a>
                                                </p>
                                            </div>
                                        </div>

                                    </form>
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

                //activer le lien du menu
                $('aside').find('a[href$="/multimif/account/events"]').closest('li').addClass('active');


                /*
                 Edition des données en live (plugin xeditable qui utilise ajax)
                 */
                $.fn.editable.defaults.mode = 'inline';
                var url = "/multimif/account/events/updateEvent"; //url de la servlet
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
                    }else if ($(this).attr("data-name") === "place") {
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
                    } else if ($(this).attr("data-name") === "type") {
                        $(this).editable({
                            select2: {
                                tags: ["Lunch", "Session", "Presentation", "Workshop"],
                                placeholder: 'Category...',
                                allowClear: true,
                                multiple: true
                            },
                            emptytext: emptytext,
                            url: url,
                            success: retourEditable,
                            error: errorEditable
                        });
                    }else {
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

            });
        </script>
    </body>
</html>

