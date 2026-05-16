package br.edu.unicesumar.biblioteca.view;

import br.edu.unicesumar.biblioteca.model.BibliotecaRepository;
import br.edu.unicesumar.biblioteca.model.Livro;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.List;

// ManagedBean JSF: fornece os livros para a pagina acervo.xhtml.
@ManagedBean(name = "livroBean")
@ApplicationScoped
public class LivroBean implements Serializable {
    // Usa o mesmo repositorio do Servlet para mostrar os mesmos dados na tela JSF.
    private final BibliotecaRepository repository = BibliotecaRepository.getInstance();

    // Metodo chamado pela pagina JSF quando ela avalia #{livroBean.livros}.
    public List<Livro> getLivros() {
        return repository.listar();
    }
}
