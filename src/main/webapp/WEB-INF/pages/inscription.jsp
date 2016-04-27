<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <%@include file="/WEB-INF/includes/styles.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration</title>
    </head>
    <body>
        <div class="container-fluid">
        <%@include file="/WEB-INF/includes/header.jsp" %>
            
            <div class="row text-center" style="margin-top: 60px">
                <h1>REGISTRATION</h1>
            </div>
        
            <div class="row" style="margin-top: 60px">
                <form action="#" id="inscription-form" class="col-md-6 col-md-offset-3 form-horizontal">

                    <div class="form-group">
                        <label class="col-sm-4 control-label">Email adress : </label>
                        <div class="col-sm-8">
                            <input type="text" name="email" class="form-control"/>
                            <span class="help-block" style="display:none"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">Password : </label>
                        <div class="col-sm-6">
                            <input type="password" name="pswd" class="form-control"/>
                            <span class="help-block" style="display:none"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">Confirm the password : </label>
                        <div class="col-sm-6">
                            <input type="password" name="pswd2" class="form-control"/>
                            <span class="help-block" style="display:none"></span>
                        </div>
                    </div>
<!--                    <div class="form-group">
                        <label class="col-sm-4  control-label">Conference :</label>
                        <div class="col-sm-6">
                            <select name="conf" id="listConf" class="form-control">
                                <option value="null">None</option>
                            </select>
                        </div>
                    </div>-->
                    <div class="form-group text-center">
                        <br>
                        <input type="hidden" name="idConf" id="idConf" value=""/>
                        <button class="btn btn-info" type="submit">Register</button>
                        <span class="help-block" style="display:none"></span>
                    </div>
                </form>
            </div>

        </div>
        <%@include file="/WEB-INF/includes/scripts.jsp" %>
        <script src="<c:url value="/resources/javascript/inscription.js"/>"></script>
    </body>
</html>
<script type="text/javascript">
    $(document).ready(function(){
        var idconf=getUrlParameter("idConf");
        if(idconf && idconf!=="undefined")
            $("#idConf").val()=idconf;
    });
</script>

