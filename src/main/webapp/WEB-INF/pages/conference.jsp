<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/includes/styles.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Conference</title>
    </head>
    <body>
        <div class="container-fluid">
        <%@include file="/WEB-INF/includes/header.jsp" %>
        
        
        <div class="row" style="margin-top: 50px">
            <div class="panel panel-default col-md-8 col-md-offset-2">
                <div class="panel-body">

                    <button disabled class="pull-right btn btn-default">Export</button>
                    <h2>${conference.Jena.getName()}</h2>

                    <div class="pull-right">
                        <a href="/multimif/">Home</a> <i class="glyphicon glyphicon-menu-right"></i>
                        <span>${conference.Jena.getName()}</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="row" style="margin-top: 20px">
            <div class="panel panel-default col-md-8 col-md-offset-2">
                <div class="panel-body">

                    <p>${conference.Jena.getDescription()}</p>

                    
                    <div style="margin-top: 20px;">
                        <label for="website">Website</label>
                        <a id="website" href="${conference.Jena.getHomepage()}">${conference.Jena.getHomepage()}</a>
                        <br>
                    </div>

                    <div style="margin-top: 20px;">
                        <label for="start">Start</label>
                        <span id="start">${conference.Jena.getStartsAt()}</span>
                        <br>
                    </div>

                    <div style="margin-top: 20px;">
                        <label for="end">End</label>
                        <span id="end">${conference.Jena.getEndsAt()}</span>
                        <br>
                    </div>

<!--                    <div style="margin-top: 20px;">
                        <label for="duration">Duration</label>
                        <span id="duration">A faire...</span>
                        <br>
                    </div>-->

                    <div style="margin-top: 20px;">
                        <label for="location">Location</label>
                        <a id="location" href="">${conference.Jena.getLocationsString()}</a>
                        <br>
                    </div>

                    <hr>

                    <h4>Events</h4>

                    <ul id="list_date" class="nav nav-pills nav-stacked">
                        <c:forEach items="${conference.Events}" var="event">
                            <li role="presentation">
                                <a data-toggle="collapse" data-target="#${event.Sql.getId()}" class="btn btn-primary">${event.Jena.getStartsAt()}</a>
                                <div id="${event.Sql.getId()}" class="collapse">

                                    <ul class="list-unstyled text-center list_event" id="">
                                        <li class="panel panel-default">
                                            <div class="panel-body text-left">
                                                Starts at <span class="start_event">${event.Jena.getStartsAt()}</span>
                                                <a href="/multimif/event/${event.Sql.getId()}">${event.Jena.getCategoriesString()}: ${event.Jena.getName()}</a>
                                            </div>
                                        </li>
                                    </ul>

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

            });
        </script>    
    </body>
</html>
