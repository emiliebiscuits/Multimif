<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <%@include file="/WEB-INF/includes/styles.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Publications</title>
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
                                <h2>My publications list</h2>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="panel panel-default col-md-10 col-md-offset-1">
                            <div class="panel-heading text-center">

                                <form action="" class="form-inline">
                                    <div class="form-group">
                                        <input name="search" placeholder="Keywords..." type="text" class="form-control" disabled/>
                                        <span class="help-block" style="display:none"></span>
                                    </div>
                                    <button type="submit" class="btn btn-default glyphicon glyphicon-search" disabled></button>

                                    &nbsp
                                    <div class="btn-group">
                                        <button aria-expanded="false" data-toggle="dropdown" class="btn btn-default dropdown-toggle" type="button">Sort <span class="caret"></span></button>
                                        <ul role="menu" class="dropdown-menu dropdown-menu-default">
                                            <li><a href="#">By name</a></li>
                                            <li><a href="#">By starting date</a></li>
                                        </ul>
                                    </div>
                                </form>

                            </div>

                            <div class="panel-body">
                                <ul class="list-unstyled text-center" id="liste_publi">

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


            });
        </script>    
    </body>
</html>
