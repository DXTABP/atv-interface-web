package br.edu.unicesumar.biblioteca.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

// Camada de dados simples: armazena os livros em memoria durante a execucao do sistema.
public class BibliotecaRepository {
    // Singleton: garante que Servlet e JSF acessem a mesma lista de livros.
    private static final BibliotecaRepository INSTANCE = new BibliotecaRepository();

    // Gera IDs automaticos e guarda os livros cadastrados.
    private final AtomicLong sequencia = new AtomicLong(1);
    private final List<Livro> livros = new CopyOnWriteArrayList<>();

    // Construtor privado para impedir criacao de varios repositorios.
    private BibliotecaRepository() {
    }

    // Ponto de acesso unico ao repositorio.
    public static BibliotecaRepository getInstance() {
        return INSTANCE;
    }

    // Cadastra um livro depois de validar campos obrigatorios, ano e ISBN.
    public Livro cadastrar(String titulo, String autor, String ano, String isbn) throws ValidacaoException {
        // Centraliza as regras de negocio para que Servlet e JSF usem a mesma fonte de dados.
        validarTexto(titulo, "Titulo");
        validarTexto(autor, "Autor");
        validarTexto(ano, "Ano de publicacao");
        validarTexto(isbn, "ISBN");

        int anoPublicacao = converterAno(ano);
        String isbnNormalizado = normalizarIsbn(isbn);
        if (!isbnValido(isbnNormalizado)) {
            throw new ValidacaoException("ISBN deve conter 10 ou 13 digitos.");
        }
        if (buscarPorIsbn(isbnNormalizado) != null) {
            throw new ValidacaoException("Ja existe um livro cadastrado com este ISBN.");
        }

        Livro livro = new Livro(
                sequencia.getAndIncrement(),
                titulo.trim(),
                autor.trim(),
                anoPublicacao,
                isbnNormalizado
        );
        livros.add(livro);
        return livro;
    }

    // Retorna uma copia ordenada da lista para a tela, evitando alteracao direta do acervo.
    public List<Livro> listar() {
        List<Livro> copia = new ArrayList<>(livros);
        copia.sort((primeiro, segundo) -> Long.compare(primeiro.getId(), segundo.getId()));
        return Collections.unmodifiableList(copia);
    }

    // Remove um livro pelo ID gerado automaticamente.
    public boolean excluirPorId(long id) {
        return livros.removeIf(livro -> livro.getId() == id);
    }

    // Remove um livro pelo ISBN informado pelo funcionario.
    public boolean excluirPorIsbn(String isbn) {
        String isbnNormalizado = normalizarIsbn(isbn);
        return livros.removeIf(livro -> livro.getIsbn().equals(isbnNormalizado));
    }

    // Procura um ISBN ja cadastrado para evitar duplicidade.
    private Livro buscarPorIsbn(String isbn) {
        for (Livro livro : livros) {
            if (livro.getIsbn().equals(isbn)) {
                return livro;
            }
        }
        return null;
    }

    // Valida campos de texto obrigatorios.
    private void validarTexto(String valor, String campo) throws ValidacaoException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacaoException(campo + " e obrigatorio.");
        }
    }

    // Converte o ano recebido como texto e verifica se esta em uma faixa aceitavel.
    private int converterAno(String ano) throws ValidacaoException {
        try {
            int anoPublicacao = Integer.parseInt(ano.trim());
            int anoAtual = Year.now().getValue();
            if (anoPublicacao < 1450 || anoPublicacao > anoAtual) {
                throw new ValidacaoException("Ano de publicacao deve estar entre 1450 e " + anoAtual + ".");
            }
            return anoPublicacao;
        } catch (NumberFormatException ex) {
            throw new ValidacaoException("Ano de publicacao deve ser numerico.");
        }
    }

    // Remove espacos e hifens para aceitar ISBN digitado com ou sem formatacao.
    private String normalizarIsbn(String isbn) {
        if (isbn == null) {
            return "";
        }
        return isbn.replace("-", "").replace(" ", "").trim();
    }

    // Regra simples da atividade: ISBN precisa ter 10 ou 13 digitos numericos.
    private boolean isbnValido(String isbn) {
        return isbn.matches("\\d{10}|\\d{13}");
    }
}
