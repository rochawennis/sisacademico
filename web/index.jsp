<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SisAcadêmico</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons">
        <link rel="stylesheet" href="https://unpkg.com/bootstrap-material-design@4.1.1/dist/css/bootstrap-material-design.min.css" integrity="sha384-wXznGJNEXNG1NFsbm0ugrLFMQPWswR3lds2VeinahP8N0zJw9VWSopbjv2x7WCvX" crossorigin="anonymous">

        <style>
            .login-card{
                padding: 100px 0 0 0;
                width: 380px;
                margin: 0 auto;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #3f51b5;">
            <a class="navbar-brand" href="#">Sis Academico</a>
        </nav>
        <div class="container">
            <div class="row">
                <div class="login-card">
                    <div class="card">
                        <div class="card-body">
                            <form action="processaLogin" method="post">
                                <div class="form-group">
                                    <input type="email" class="form-control" name="email" placeholder="E-mail"/>
                                </div>
                                <div class="form-group">
                                    <input type="password" class="form-control" name="senha" placeholder="Senha"/>
                                </div>
                                <%
                                    if (!session.isNew() && session.getAttribute("autenticado") != null) {
                                        if (!(Boolean) session.getAttribute("autenticado")) {
                                %>
                                <div class="text-danger">Usuário ou senha inválidos</div>
                                <%
                                        }
                                    }
                                %> 
                                <button class="btn btn-lg btn-outline-info btn-block" type="submit">Entrar</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
