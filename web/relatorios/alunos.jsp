<%@page import="br.sisacademico.security.TipoUsuario"%>
<%@page import="br.sisacademico.pojo.Curso"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.sisacademico.pojo.Aluno"%>
<%@page import="br.sisacademico.DAO.AlunoDAO"%>
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

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="../menu.jsp"/>
        <script src="../js/trataExclusao.js"></script>
        <%            
            AlunoDAO aDAO = new AlunoDAO();
            ArrayList<Aluno> alunos;
            boolean mostraPainelFiltro = false;
            if (request.getParameter("idCurso") != null) {
                int idCurso = Integer.parseInt(request.getParameter("idCurso"));
                alunos = aDAO.getTodosAluno(Curso.class, idCurso);
                mostraPainelFiltro = true;
            } else {
                alunos = aDAO.getTodosAluno(Aluno.class);
            }
            int index = 1; //contador para as linhas da tabela
        %>

        <% if (mostraPainelFiltro) {%>
        <div class="card" style="width: 90%; margin: 30px auto !important;">
            <div class="card-header bg-success">
                <span class="text-white">Filtro aplicado</span>
            </div>
            <div class="card-body">
                <h5>Mostrando apenas alunos do curso: <strong><%=alunos.get(0).getCurso().getNomeCurso()%></strong></h5>
                <a href="alunos.jsp" class="btn btn-outline-success btn-block">Limpar filtro</a>
            </div>
        </div>
        <% } %>
        <div class="mt-5">
            <div style="width: 90%; margin: 0 auto !important;">
                <table class="table table-bordered text-center">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">RA</th>
                            <th scope="col">Nome do Aluno</th>
                            <th scope="col">Curso</th>
                            <th scope="col">Tipo de Curso</th>
                            <!--                            verifica se o usuário logado tem acesso à essas colunas:-->
                            <% if (acessoFull) { %> 
                                <th scope="col">Editar</th>
                                <th scope="col">Excluir</th>
                            <% } %>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Aluno a : alunos) {%>
                        <tr>
                            <td><%=index++%></td>
                            <td><%=a.getRa()%></td>
                            <td><%=a.getNomeAluno()%></td>
                            <td><%=a.getCurso().getNomeCurso()%></td>
                            <td><%=a.getCurso().getTipoCurso()%></td>
                            <% if (acessoFull) {%>
                                <td><a href="../cadastros/aluno.jsp?idAluno=<%=a.getIdAluno()%>" class="btn btn-outline-info">Editar</a></td>
                                <td><a href="../alunoServlet?tipoAcao=delete&idAluno=<%=a.getIdAluno()%>" class="btn btn-outline-danger" id="deleteAluno">Apagar</a></td>
                            <% } %>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
            </div>
        </div>



    </body>
</html>
