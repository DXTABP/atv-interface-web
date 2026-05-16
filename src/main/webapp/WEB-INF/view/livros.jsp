<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- JSTL usada para percorrer a lista de livros e exibir mensagens condicionais. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Biblioteca Universidade Cesumar</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/estilos.css">
</head>
<body>
<%-- Cabecalho da tela principal com acesso para a pagina JSF. --%>
<header class="topo">
    <div>
        <span class="marca">Universidade Cesumar</span>
        <h1>Biblioteca - Acervo Interno</h1>
    </div>
    <a class="link-jsf" href="${pageContext.request.contextPath}/jsf/acervo.xhtml">Consultar via JSF</a>
</header>

<main class="conteudo">
    <%-- Formulario de cadastro enviado via POST para o LivroServlet. --%>
    <section class="painel formulario">
        <h2>Cadastrar livro</h2>

        <%-- Exibe erros de validacao gravados pelo Servlet na sessao. --%>
        <c:if test="${not empty sessionScope.erro}">
            <div class="alerta erro">${sessionScope.erro}</div>
            <c:remove var="erro" scope="session"/>
        </c:if>
        <%-- Exibe mensagem de sucesso apos cadastro ou exclusao. --%>
        <c:if test="${not empty sessionScope.mensagem}">
            <div class="alerta sucesso">${sessionScope.mensagem}</div>
            <c:remove var="mensagem" scope="session"/>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/livros" novalidate>
            <label>
                Título
                <input type="text" name="titulo" maxlength="120" required>
            </label>
            <label>
                Autor
                <input type="text" name="autor" maxlength="100" required>
            </label>
            <div class="linha">
                <label>
                    Ano de publicação
                    <input type="number" name="anoPublicacao" min="1450" max="${anoAtual}" required>
                </label>
                <label>
                    ISBN
                    <input type="text" name="isbn" maxlength="17" placeholder="10 ou 13 dígitos" required>
                </label>
            </div>
            <button type="submit">Cadastrar</button>
        </form>
    </section>

    <%-- Area de listagem e exclusao dos livros ja cadastrados. --%>
    <section class="painel">
        <div class="cabecalho-lista">
            <h2>Livros cadastrados</h2>
            <span>${livros.size()} item(ns)</span>
        </div>

        <%-- Formulario alternativo para excluir um livro diretamente pelo ISBN. --%>
        <form class="excluir-isbn" method="post" action="${pageContext.request.contextPath}/livros">
            <input type="hidden" name="acao" value="excluir">
            <label>
                Excluir por ISBN
                <input type="text" name="isbnExclusao" placeholder="Digite o ISBN">
            </label>
            <button type="submit" class="secundario">Excluir</button>
        </form>

        <%-- Se nao houver livros, mostra aviso; caso contrario, monta a tabela. --%>
        <c:choose>
            <c:when test="${empty livros}">
                <p class="vazio">Nenhum livro cadastrado até o momento.</p>
            </c:when>
            <c:otherwise>
                <div class="tabela-responsiva">
                    <table>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Título</th>
                            <th>Autor</th>
                            <th>Ano</th>
                            <th>ISBN</th>
                            <th>Ação</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%-- Percorre os livros enviados pelo Servlet no atributo "livros". --%>
                        <c:forEach var="livro" items="${livros}">
                            <tr>
                                <td>${livro.id}</td>
                                <td>${livro.titulo}</td>
                                <td>${livro.autor}</td>
                                <td>${livro.anoPublicacao}</td>
                                <td>${livro.isbn}</td>
                                <td>
                                    <%-- Cada linha possui um formulario para excluir pelo ID do livro. --%>
                                    <form method="post" action="${pageContext.request.contextPath}/livros">
                                        <input type="hidden" name="acao" value="excluir">
                                        <input type="hidden" name="id" value="${livro.id}">
                                        <button type="submit" class="perigo">Excluir</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </section>
</main>
</body>
</html>
