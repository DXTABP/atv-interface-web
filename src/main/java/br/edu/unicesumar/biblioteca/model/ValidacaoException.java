package br.edu.unicesumar.biblioteca.model;

// Excecao usada para avisar erros de entrada, como campo vazio, ano invalido ou ISBN incorreto.
public class ValidacaoException extends Exception {
    public ValidacaoException(String mensagem) {
        super(mensagem);
    }
}
