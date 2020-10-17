<%@ include file="/jspf/directive/page.jspf" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="css/bootstrap.min.css"/>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery-3.2.1.min.js"></script>
</head>

<body>
<div class="container">
    <div class="row centered-form justify-content-center">
        <div class="col-xs-12 col-sm-8 col-md-4 col-sm-offset-2 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title text-center"><fmt:message key="registration.title"/></h3>
                </div>
                <div class="panel-body">
                    <form role="form" action="controller" method="POST">
                        <input type="hidden" name="command" value="registration" />
                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <input type="text" name="firstName" id="first_name" class="form-control input-sm" placeholder="<fmt:message key="registration.firstName"/>" required autofocus>
                                </div>
                            </div>
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <input type="text" name="lastName" id="last_name" class="form-control input-sm" placeholder="<fmt:message key="registration.lastName"/>" required autofocus>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <input type="email" name="email" id="email" class="form-control input-sm" placeholder="<fmt:message key="login.email"/>" required autofocus>
                        </div>

                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <input type="password" name="password" id="password" class="form-control input-sm" placeholder="<fmt:message key="registration.password"/>" required autofocus>
                                </div>
                            </div>
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <input type="password" name="password_confirmation" id="password_confirmation" class="form-control input-sm" placeholder="<fmt:message key="registration.password"/>" required autofocus>
                                </div>
                            </div>
                        </div>

                        <input type="submit" value="<fmt:message key="registration.button"/>" class="btn btn-info btn-block">

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>