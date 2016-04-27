<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/includes/styles.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <div class="container-fluid">

            <%@include file="/WEB-INF/includes/header.jsp" %>


            <div class="row text-center">
                <h1>SYMPOZER</h1>
            </div>

            <div class="row">
                <div class="panel panel-default col-md-8 col-md-offset-2">
                    <div class="panel-heading text-center">

                        <form id="search" action="" class="form-inline">
                            <div class="form-group">
                                <input name="search" placeholder="Keywords..." type="text" class="form-control"/>
                                <span class="help-block" style="display:none"></span>
                            </div>
                            <button type="submit" class="btn btn-default glyphicon glyphicon-search"></button>

                            &nbsp
                            <div class="btn-group">
                                <button disabled aria-expanded="false" data-toggle="dropdown" class="btn btn-default dropdown-toggle" type="button">Sort <span class="caret"></span></button>
                                <ul role="menu" class="dropdown-menu dropdown-menu-default">
                                    <li><a href="#">By name</a></li>
                                    <li><a href="#">By starting date</a></li>
                                </ul>
                            </div>
                        </form>

                    </div>

                    <div class="panel-body">
                        <ul class="list-unstyled text-center" id="liste_conf">
                            <c:if test="${list_conf.size() <= 0}">
                                <h4 class="text-danger">There isn't any conferences recorded yet!</h4>
                            </c:if>

                            <c:forEach items="${list_conf}" var="conference">
                                <li class="panel panel-default" tags="${conference.Jena.getName()}${conference.Sql.getTheme()}">
                                    <div class="panel-body text-left">
                                        <span class="pull-right text-success">${conference.Jena.getStartsAt()} - ${conference.Jena.getEndsAt()}</span>
                                        <a href="/multimif/conference/${conference.Sql.getId()}">${conference.Jena.getName()}</a>
                                        <p>${conference.Jena.getDescription()}</p>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>

                </div>
            </div>


        </div>
        <%@include file="/WEB-INF/includes/scripts.jsp" %>
        <script type="text/javascript">
            $(document).ready(function () {

                /*
                 * SEARCH
                 */
                $(document).on("submit", "#search", function (e) {
                    e.preventDefault();

                    var text = $(this).find("input[name='search']").val();
                    if (text === "") {
                        $("#liste_conf li").show();
                    } else {
                        var textTab = text.split(/[\s,]+/);
                        $("#liste_conf li").each(function (index, el) {
                            var ok = false;
                            $(textTab).each(function (index, word) {
                                if (($(el).attr("tags")).indexOf(word) > -1) {
                                    ok=true;
                                }
                            });
                            if(ok){
                                $(el).show();
                            }else{
                                $(el).hide();
                            }
                            
                        });
                    }
                });
                $("input[name='search']").autocomplete({
                    source: tags_list,
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
                        $(this).val(ui.item.value);
                    }
                }).data("ui-autocomplete")._renderItem = function (ul, item) {
                    return $("<li class='involved'>")
                            .append("<a>" + item.value + "</a>")
                            .appendTo(ul);
                };
            });
        </script>    
    </body>
</html>
