<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <%@include file="/WEB-INF/includes/styles.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Profil</title>
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
                                <h2>Upload files to add conferences</h2>
                                <div class="pull-right">
                                    <span class="btn btn-info btn-file">
                                        <input type="file" id="file" name="file">
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="panel panel-default col-md-10 col-md-offset-1">
                            <div class="panel-heading text-center">

                                

                            </div>

                            <div class="panel-body">
                                
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
                $('aside').find('a[href$="/multimif/account/import"]').closest('li').addClass('active');
                 $('#file').change(function(){
                    var file = this.files[0];
                    var type = file.type;
                    console.log(file);
                    var str;
                    if (file) {
                        var reader = new FileReader();
                        reader.onload = function(){
                          str = reader.result;
                          //console.log(reader.result);
                          var json = {"conferences":[]};
                        $(str).find('conference').each(function(){
                                //console.log($(this).find('director').text());
                                //console.log($(this).find('name').text());
                                //console.log($(this).find('theme').text());
                                //console.log($(this).find('description').text());
                                //console.log($(this).find('date').text());
                                var director = $(this).find('director').text();
                                var name = $(this).find('name').text();
                                var theme = $(this).find('theme').text();
                                var description = $(this).find('description').text();
                                var date = $(this).find('date').text();

                                json.conferences.push({"director":director,"name":name,"theme":theme,"description":description,"date":date});

                            });
                            console.log(json); 
                            sendJson(json);
                             function sendJson(json){
                                 $.ajax({
                                        url: "/multimif/conferences/add/imported", 
                                        data: {content:JSON.stringify(json)},
                                        method: 'POST',
                                        dataType: 'json',
                                        success: function (response) {
                                            console.log(response);
                                        },
                                        error: function (xhr, status, error) {
                                            console.log(xhr.responseText); 
                                        }
                                    });
                             }
                        };
                        reader.readAsText(file);
                    }
                });


            });
        </script>    
    </body>
</html>
