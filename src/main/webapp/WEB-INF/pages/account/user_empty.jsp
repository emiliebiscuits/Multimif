<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/includes/styles.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Title</title>
    </head>
    <body>
        <div class="container-fluid">        
        <%@include file="/WEB-INF/includes/header.jsp" %>

        <div class="row">
            <%@include file="/WEB-INF/includes/user_menu.jsp" %>
            <div class="col-md-10">



                <h1>EMPTY</h1>
    


            </div>        
        </div>  

        </div>
        <%@include file="/WEB-INF/includes/scripts.jsp" %>
        <script type="text/javascript">
            $(document).ready(function () {
                //activer le <li> dans le menu
                $('aside').find('a[href$="user_accound.jsp"]').closest('li').addClass('active');
            });
        </script>    
    </body>
</html>
