<%@page import="br.sisacademico.security.Usuario"%>
<%@page import="br.sisacademico.security.Usuario"%>
<%@page import="br.sisacademico.DAO.UsuarioDAO"%>
<%@page import="java.util.ArrayList"%>
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

    boolean acessoFull = (TipoUsuario) session.getAttribute("tipoUsuario")
            == TipoUsuario.admin ? true : false;

    if (!acessoFull) {
        response.sendRedirect("404.jsp");
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <%            
            UsuarioDAO uDAO = new UsuarioDAO();
            ArrayList<Usuario> usuarios = uDAO.getUsuarios();
            int count = 1;
        %>

        <script src="../js/trataExclusao.js"></script>
        <div class="mt-5">
            <div style="width: 90%; margin: 0 auto !important;">
                <table class="table table-bordered text-center">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">E-Mail</th>
                            <th scope="col">Tipo de Usuário</th>
                            <th scope="col">Editar</th>
                            <th scope="col">Excluir</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Usuario u : usuarios) { %>
                        <tr>
                            <td><%=count++%></td>
                            <td><%=u.getEmail()%></td>
                            <td><%= (u.getTipo() == TipoUsuario.admin) ? "Administrador" : "Usuário comum" %></td>
                            <td>BOTÃO EDITAR</td>
                            <td>botão excluir</td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
            </div>
        </div>



    </body>
</html>
