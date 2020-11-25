<%@page import="br.sisacademico.security.Usuario"%>
<%@page import="br.sisacademico.DAO.UsuarioDAO"%>
<%@page import="br.sisacademico.security.TipoUsuario"%>
<%@page import="br.sisacademico.DAO.AlunoDAO"%>
<%@page import="br.sisacademico.pojo.Aluno"%>
<%@page import="br.sisacademico.pojo.Curso"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.sisacademico.DAO.CursoDAO"%>
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
        response.sendRedirect("../404.jsp");
    }

    //-------------
    String tipoAcao = "insere";
    String labelBotao = "Cadastrar";
    Usuario u = new Usuario();
    u.setEmail("");
    u.setTipo(TipoUsuario.usuario);
    String desabilitado = "";
    boolean campoSenhaHabilitado = true;
    if (request.getParameter("idUsuario") != null) {
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        UsuarioDAO uDAO = new UsuarioDAO();
        u = uDAO.getUsuario(idUsuario);
        tipoAcao = "edicao";
        labelBotao = "Confirmar alterações";
        campoSenhaHabilitado = false;
        int idUsuarioLogado = (Integer) session.getAttribute("idUsuario");

        desabilitado = (idUsuarioLogado == idUsuario) ? "disabled" : "";

    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script language="javascript">
            function enableDisable(bEnable, textBoxID)
            {
                document.getElementById(textBoxID).disabled = !bEnable;
            }
        </script>
    </head>
    <body>
        <jsp:include page="../menu.jsp"/>
        <div class="container mt-5">
            <div style="width: 50%; margin: 0 auto !important;">
                <form method="post" action="../usuarioServlet">
                    <div class="form-group" style="padding-top: 25px;">
                        <label>E-Mail</label>
                        <input type="email" name="email" class="form-control"
                               placeholder="E-Mail do usuário"
                               value="<%=u.getEmail()%>"/>
                    </div>

                    <div class="form-group" style="padding-top: 25px;">
                        <label>Senha</label>
                        <input type="password" <%= (campoSenhaHabilitado == false) ? "disabled" : ""%> name="senha" id="textBox" class="form-control"
                               value=""/>
                        <% if (!campoSenhaHabilitado) { %>
                        <input type="checkbox" name="alteraSenha" id="checkBox" onclick="enableDisable(this.checked, 'textBox')">
                        Alterar a senha do usuário
                        </input>
                        <% }%>

                    </div>
                    <div class="form-group">
                        <label>Selecione o tipo de acesso</label>
                        <select name="idTipoUsuario" class="form-control" <%=desabilitado%>>
                            <option value="1" <%=(u.getTipo() == TipoUsuario.admin) ? "selected" : ""%> >Administrador</option>
                            <option value="2" <%=(u.getTipo() == TipoUsuario.usuario) ? "selected" : ""%>>Usuário comum</option>
                        </select>
                    </div>
                    <input type="hidden" name="tipoAcao" value="<%=tipoAcao%>"/>
                    <input type="hidden" name="idUsuario" value="<%=u.getIdUsuario()%>"/>
                    <input type="submit" class="btn btn-outline-info btn-block" 
                           value="<%=labelBotao%>"/>    
                </form>
            </div>
        </div>
    </body>
</html>
