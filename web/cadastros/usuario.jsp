<%@page import="br.sisacademico.security.TipoUsuario"%>
<%@page import="br.sisacademico.DAO.AlunoDAO"%>
<%@page import="br.sisacademico.pojo.Aluno"%>
<%@page import="br.sisacademico.pojo.Curso"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.sisacademico.DAO.CursoDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if(!session.isNew() && session.getAttribute("autenticado") != null){
        if(!(Boolean)session.getAttribute("autenticado")){
            response.sendRedirect(request.getContextPath() + "/index.jsp?acesso=false");
        }
    }else{
        response.sendRedirect(request.getContextPath() + "/index.jsp?acesso=false");
    }
    
    boolean acessoFull = (TipoUsuario) session.getAttribute("tipoUsuario")
            == TipoUsuario.admin ? true : false;
    if(!acessoFull){
        response.sendRedirect("../404.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <jsp:include page="../menu.jsp"/>

        <%
            //se for modo edição, pegar o idUsuario da URL     
        %>
        <div class="container mt-5">
            <div style="width: 50%; margin: 0 auto !important;">
                <form method="post" action="../usuarioServlet">
                    <div class="form-group" style="padding-top: 25px;">
                        <label>E-Mail</label>
                        <input type="email" name="email" class="form-control"
                               placeholder="E-Mail do usuário"
                               value=""/>
                    </div>
                    <div class="form-group" style="padding-top: 25px;">
                        <label>Senha</label>
                        <input type="password" name="senha" class="form-control"
                               value=""/>
                    </div>
                    <div class="form-group" style="padding-top: 25px;">
                        <label>Confirme a senha:</label>
                        <input type="password" name="senha2" class="form-control"
                               value=""/>
                    </div>
                    <div class="form-group" style="padding-top: 25px;">
                        <label>Selecione o tipo de acesso</label>
                        <select name="idTipoUsuario" class="form-control">
                            <option value="1">Administrador</option>
                            <option value="2">Usuário comum</option>
                        </select>
                    </div>
                    <input type="hidden" name="tipoAcao" value="<%="insere"%>"/>
                    <input type="hidden" name="idUsuario" value="<%=""%>"/>
                    <input type="submit" class="btn btn-outline-info btn-block" 
                           value="<%="Cadastrar"%>"/>    
                </form>
            </div>
        </div>
    </body>
</html>
