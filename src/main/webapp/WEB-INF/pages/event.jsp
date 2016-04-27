<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/includes/styles.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Event</title>
    </head>
    <body>
        <div class="container-fluid">
        <%@include file="/WEB-INF/includes/header.jsp" %>
        
        
        <div class="row" style="margin-top: 50px">
            <div class="panel panel-default col-md-8 col-md-offset-2">
                <div class="panel-body">

                    <h2>${event.Jena.getName()}</h2>

                    <div class="pull-right">
                        <a href="/multimif/">Home</a> <i class="glyphicon glyphicon-menu-right"></i>
                        <a href="/multimif/conference/${event.Parent.getId()}">${event.Parent.getName()}</a> <i class="glyphicon glyphicon-menu-right"></i>
                        <span>${event.Jena.getName()}</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="row" style="margin-top: ">
            <div class="panel panel-default col-md-8 col-md-offset-2">
                <div class="panel-body">

                    <p>${event.Jena.getDescription()}</p>


                    <div style="margin-top: 20px;">
                        <label for="start">Start</label>
                        <span id="start">${event.Jena.getStartsAt()}</span>
                        <br>
                    </div>

                    <div style="margin-top: 20px;">
                        <label for="end">End</label>
                        <span id="end">${event.Jena.getEndsAt()}</span>
                        <br>
                    </div>

<!--                    <div style="margin-top: 20px;">
                        <label for="duration">Duration</label>
                        <span id="duration">A calculer</span>
                        <br>
                    </div>-->

                    <div style="margin-top: 20px;">
                        <label for="location">Location</label>
                        <a id="location" href="">${event.Jena.getLocationsString()}</a>
                        <br>
                    </div>

                    <div style="margin-top: 20px;">
                        <label for="parent_event">Parent event</label>
                        <a id="parent_event" href="/multimif/conference/${event.Parent.getId()}">${event.Parent.getName()}</a>
                        <br>
                    </div>
                    
                    <hr>

                    <h4>Sub-events</h4>

                    <ul id="list_date" class="nav nav-pills nav-stacked">
                        <li role="presentation">
                            <a data-toggle="collapse" data-target="#date1" class="btn btn-primary">May 31 2015</a>
                            <div id="date1" class="collapse">

                                <ul class="list-unstyled text-center list_event" id="date#1">
                                    <li class="panel panel-default">
                                        <div class="panel-body text-left">
                                            Starts at <span class="start_event">10:30 AM</span>
                                            <a href="/multimif/event.jsp">Reseach paper Crowd Sourcing & Web Science: Ranking Entities in the Age of Two Webs, An Application to Semantic Snippets</a>
                                        </div>
                                    </li>

                                    <li class="panel panel-default">
                                        <div class="panel-body text-left">
                                            Starts at <span class="start_event">12:30 PM</span>
                                            <a href="/multimif/event.jsp">Reseach paper Crowd Sourcing & Web Science: Towards hybrid NER: a study of content and crowdsourcing-related performance factors</a>
                                        </div>
                                    </li>
                                </ul>

                            </div>
                        </li>
                    </ul>

                </div>
            </div>
        </div>

        
        </div>
        <%@include file="/WEB-INF/includes/scripts.jsp" %>
        <script type="text/javascript">
            $(document).ready(function () {
                
                /*
                    ACTUALISATION des données
                */
                var timer = setInterval(actualise_info, 2000);

                var id_conf = "123456";
                function actualise_info(){
                    $.get("#", {id_event: id_conf}, function(retour){

                        $.each(retour, function(index, val) {
                            $("#"+index).html(val);   
                        });

                    }, "json");
                }

            });
        </script>    
    </body>
</html>
