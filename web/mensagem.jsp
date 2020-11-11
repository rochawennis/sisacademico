<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if(!session.isNew() && session.getAttribute("autenticado") != null){
        if(!(Boolean)session.getAttribute("autenticado")){
            response.sendRedirect(request.getContextPath() + "/index.jsp?acesso=false");
        }
    }else{
        response.sendRedirect(request.getContextPath() + "/index.jsp?acesso=false");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <jsp:include page="menu.jsp"/>

        <%
            //parametros da URL:
            String operacao = request.getParameter("op");
            String entidade = request.getParameter("entity");
            boolean resultado = Boolean.parseBoolean(request.getParameter("result"));

            if (operacao.equalsIgnoreCase("inclusao")) {
                operacao = "inclusão";
            }else if(operacao.equalsIgnoreCase("apagar")){
                operacao = "deleção";
            }
        %>

        <div class="container mt-5">
            <div style="width: 80%; margin: 0 auto !important; text-align: center;">
                <%                    
                    if (resultado) {
                        if (entidade.equalsIgnoreCase("curso")) {
                            out.print("<h4>A operação de " + operacao + " do curso foi realizada com sucesso!</h4>");
                        }
                        
                        if (entidade.equalsIgnoreCase("aluno")){
                            out.print("<h4>A operação de " + operacao + " de aluno foi realizada com sucesso!</h4>");
                        }
                    } else {
                        if (entidade.equalsIgnoreCase("curso")) {
                            out.print("<h4>A operação de " + operacao + " do curso não foi bem sucedida!</h4>");
                        }
                        if (entidade.equalsIgnoreCase("aluno")) {
                            out.print("<h4>A operação de " + operacao + " do aluno não foi bem sucedida!</h4>");
                        }
                    }

                %>
            </div>
        </div>
    </body>
</html>
