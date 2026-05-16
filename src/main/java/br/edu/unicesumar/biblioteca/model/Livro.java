package br.edu.unicesumar.biblioteca.model;

import java.io.Serializable;
import java.util.Objects;

// Classe de modelo: representa um livro cadastrado no acervo da biblioteca.
public class Livro implements Serializable {
    // Atributos principais exigidos pela atividade, mais um ID para facilitar a exclusao.
    private final long id;
    private final String titulo;
    private final String autor;
    private final int anoPublicacao;
    private final String isbn;

    // Construtor usado pelo repositorio para criar um livro ja validado.
    public Livro(long id, String titulo, String autor, int anoPublicacao, String isbn) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.isbn = isbn;
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public String getIsbn() {
        return isbn;
    }

    // Dois livros sao considerados iguais quando possuem o mesmo ID interno.
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Livro)) {
            return false;
        }
        Livro livro = (Livro) o;
        return id == livro.id;
    }

    // Mantem o contrato com o metodo equals, usando o ID como chave.
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
