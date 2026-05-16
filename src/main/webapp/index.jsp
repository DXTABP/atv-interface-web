<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- Redireciona a raiz do projeto para o controller principal de livros. --%>
<% response.sendRedirect(request.getContextPath() + "/livros"); %>
