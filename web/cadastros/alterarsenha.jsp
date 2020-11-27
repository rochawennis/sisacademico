<%@page import="br.sisacademico.security.TipoUsuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (!session.isNew() && session.getAttribute("autenticado") != null) {
        if (!(Boolean) session.getAttribute("autenticado")) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?acesso=false");
        }
    } else {
        response.sendRedirect(request.getContextPath() + "/index.jsp?acesso=false");
    }

    String acao = "alteraSenha";
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="../menu.jsp"/>
        <script src="../js/validaSenha.js"></script>
        <%            if (request.getParameter("acao") != null) {
                if (Boolean.parseBoolean(request.getParameter("acao"))) {
        %>
        <div class="text-center alert alert-success" style="margin: 0 auto !important; margin-top:  30px;">Senha auterada!</div>
        <%
        } else {
        %>
        <div class="text-center alert alert-danger" style="margin: 0 auto !important; margin-top:  30px;">Senha antiga não confere!</div>
        <%
                }
            }
        %>
        <div class="container mt-5">
            <div style="width: 50%; margin: 0 auto !important;">
                <form method="post" action="../usuarioServlet">

                    <div class="form-group" style="padding-top: 25px;">
                        <label>Senha Atual: </label>
                        <input type="password" name="senhaAntiga" id="textBox" class="form-control"/>
                    </div>

                    <div class="form-group" style="padding-top: 25px;">
                        <label>Senha nova: </label>
                        <input type="password" name="senhaNova_1" id="senha" class="form-control"/>
                    </div>

                    <div class="form-group" style="padding-top: 25px;">
                        <label>Confirme: </label>
                        <input type="password" name="senhaNova_2" id="senha_confirm" class="form-control"/>
                    </div>

                    <input type="hidden" name="tipoAcao" value="<%=acao%>"/>  

                    <input type="button" value="Alterar" class="btn btn-outline-info btn-block">
                </form>
            </div>
        </div>
    </body>
</html>
