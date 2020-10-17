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
    <div class="row">
        <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div class="card card-signin my-5">
                <div class="card-body">
                    <h5 class="card-title text-center"><fmt:message key="login.signIn"/></h5>
                    <form class="form-signin" action="controller" method="POST">
                        <input type="hidden" name="command" value="login" />
                        <div class="form-label-group">
                            <input type="email" name="email" id="inputEmail" class="form-control" placeholder="<fmt:message key="login.email"/>" required autofocus>
                            <label for="inputEmail"><fmt:message key="login.email"/></label>
                        </div>

                        <div class="form-label-group">
                            <input type="password" name="password" id="inputPassword" class="form-control" minlength="4" placeholder="<fmt:message key="login.password"/>" required autofocus>
                            <label for="inputPassword"><fmt:message key="login.password"/></label>
                        </div>

                        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit"><fmt:message key="login.signIn"/></button>
                        <hr class="my-4">
                        
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>