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
        <div class="container text-center" style="margin: 0 auto !important; padding-top: 50px;">
            <span class="text-danger display-4">
                <p>A página requisitada não foi encontrada! :(</p>
                <p>Isso é tudo que sabemos!</p>
            </span>
        </div>
    </body>
</html>
