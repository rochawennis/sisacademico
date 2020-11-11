<%@page import="br.sisacademico.security.TipoUsuario"%>
<%@page import="br.sisacademico.pojo.Curso"%>
<%@page import="br.sisacademico.DAO.CursoDAO"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
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
%>
<%
    String operacao = "cadastro";
    String labelBotao = "Cadastrar";
    Curso c = new Curso();
    c.setNomeCurso("");
    c.setTipoCurso("");

    if (request.getParameter("idCurso") != null) {
        operacao = "edicao";
        int idCurso = Integer.parseInt(request.getParameter("idCurso"));
        CursoDAO cDAO = new CursoDAO();
        c = cDAO.getCursos(idCurso).get(0);
        labelBotao = "Salvar";
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <jsp:include page="../menu.jsp"/>

        <div class="container mt-5">
            <div style="width: 50%; margin: 0 auto !important;">
                <form method="post" action="../cursoServlet">
                    <div class="form-group">
                        <label>Nome do Curso:</label>
                        <input type="text" name="nomeCurso" class="form-control"
                               placeholder="Informe o título do curso"
                               value="<%=c.getNomeCurso()%>"/>
                    </div>
                    <div class="form-group" style="padding-top: 25px;">
                        <label>Tipo de Curso:</label>
                        <input type="text" name="tipoCurso" class="form-control"
                               placeholder="Informe o tipo do curso"
                               value="<%=c.getTipoCurso()%>"/>
                    </div>
                    <input type="hidden" name="tipoAcao" value="<%=operacao%>"/>
                    <input type="hidden" name="idCurso" value="<%=c.getIdCurso()%>"/>
                    <div style="padding-top: 25px;">
                        <input type="submit" class="btn btn-outline-info btn-block" 
                               value="<%=labelBotao%>"/>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
