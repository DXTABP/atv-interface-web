package br.edu.unicesumar.biblioteca.controller;

import br.edu.unicesumar.biblioteca.model.BibliotecaRepository;
import br.edu.unicesumar.biblioteca.model.ValidacaoException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Year;

// Controller MVC: recebe as requisicoes da tela JSP e decide qual acao executar.
@WebServlet(name = "LivroServlet", urlPatterns = {"/livros"})
public class LivroServlet extends HttpServlet {
    // Repositorio compartilhado que guarda e valida os livros.
    private final BibliotecaRepository repository = BibliotecaRepository.getInstance();

    // Requisicoes GET exibem a tela com o formulario e a lista de livros.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // O Servlet prepara os dados da tela e preserva a separacao MVC.
        request.setAttribute("livros", repository.listar());
        request.setAttribute("anoAtual", Year.now().getValue());
        request.getRequestDispatcher("/WEB-INF/view/livros.jsp").forward(request, response);
    }

    // Requisicoes POST tratam cadastro e exclusao enviados pelos formularios.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Garante leitura correta de textos com acentos enviados pelo formulario.
        request.setCharacterEncoding("UTF-8");

        String acao = request.getParameter("acao");
        try {
            if ("excluir".equals(acao)) {
                excluir(request);
                request.getSession().setAttribute("mensagem", "Livro excluido com sucesso.");
            } else {
                cadastrar(request);
                request.getSession().setAttribute("mensagem", "Livro cadastrado com sucesso.");
            }
        } catch (ValidacaoException ex) {
            request.getSession().setAttribute("erro", ex.getMessage());
        }

        // Redireciona apos o POST para evitar reenviar formulario ao atualizar a pagina.
        response.sendRedirect(request.getContextPath() + "/livros");
    }

    // Coleta os parametros do formulario de cadastro e envia ao repositorio.
    private void cadastrar(HttpServletRequest request) throws ValidacaoException {
        repository.cadastrar(
                request.getParameter("titulo"),
                request.getParameter("autor"),
                request.getParameter("anoPublicacao"),
                request.getParameter("isbn")
        );
    }

    // Decide se a exclusao sera pelo ID da tabela ou pelo ISBN digitado.
    private void excluir(HttpServletRequest request) throws ValidacaoException {
        String id = request.getParameter("id");
        String isbn = request.getParameter("isbnExclusao");

        // A tela permite exclusao direta pelo ID da tabela ou por ISBN informado manualmente.
        if (id != null && !id.trim().isEmpty()) {
            try {
                if (!repository.excluirPorId(Long.parseLong(id))) {
                    throw new ValidacaoException("Livro nao encontrado para exclusao.");
                }
                return;
            } catch (NumberFormatException ex) {
                throw new ValidacaoException("ID informado para exclusao e invalido.");
            }
        }

        if (isbn == null || isbn.trim().isEmpty() || !repository.excluirPorIsbn(isbn)) {
            throw new ValidacaoException("Informe um ISBN valido para excluir um livro existente.");
        }
    }
}
