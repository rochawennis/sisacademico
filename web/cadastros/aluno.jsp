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
            CursoDAO cDAO = new CursoDAO();
            ArrayList<Curso> cursos = cDAO.getCursos();
            String operacao = "cadastro";
            String labelBotao = "Cadastrar";
            Aluno a = new Aluno();
            a.setNomeAluno("");
            a.setCurso(new Curso(0, "", ""));

            if (request.getParameter("idAluno") != null) {
                int idAluno = Integer.parseInt(request.getParameter("idAluno"));
                AlunoDAO aDAO = new AlunoDAO();
                operacao = "edicao";
                labelBotao = "Salvar";
                a = aDAO.getTodosAluno(Aluno.class, idAluno).get(0);
            }

        %>
        <div class="container mt-5">
            <div style="width: 50%; margin: 0 auto !important;">
                <form method="post" action="../alunoServlet">
                    <div class="form-group" style="padding-top: 25px;">
                        <label>RA</label>
                        <input type="text" name="ra" class="form-control"
                               placeholder="Insira o RA"
                               value="<%= a.getRa() == 0 ? "" : a.getRa()%>"/>
                    </div>
                    <div class="form-group" style="padding-top: 25px;">
                        <label>Nome</label>
                        <input type="text" name="nome" class="form-control"
                               placeholder="Informe o nome"
                               value="<%=a.getNomeAluno()%>"/>
                    </div>
                    <div class="form-group" style="padding-top: 25px;">
                        <label>Selecione o curso</label>
                        <select name="idCurso" class="form-control">
                            <% for (Curso c : cursos) {
                                    String opc = "";
                                    if (a.getCurso().getIdCurso() == c.getIdCurso()) {
                                        opc = "selected";
                                    }
                            %>
                            <option <%=opc%> value="<%=c.getIdCurso()%>"><%=c.getNomeCurso()%> 
                                (<%=c.getTipoCurso()%>)</option>
                            <% }%>
                        </select>
                    </div>
                    <input type="hidden" name="tipoAcao" value="<%=operacao%>"/>
                    <input type="hidden" name="idAluno" value="<%=a.getIdAluno()%>"/>
                    <input type="submit" class="btn btn-outline-info btn-block" 
                           value="<%=labelBotao%>"/>    
                </form>
            </div>
        </div>
    </body>
</html>
