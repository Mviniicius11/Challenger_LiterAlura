package br.com.alura.literAlura.model;
import jakarta.persistence.*;

import java.util.stream.Collectors;

@Entity
@Table(name = "Livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String nomeAutor;
    private String idioma;
    private Integer qtdDownload;

    @ManyToOne
    private Autor autor;

    public Livro(DadosLivro dadosLivro){

        this.titulo = dadosLivro.titulo();
        this.qtdDownload = dadosLivro.quantidade();
        this.idioma = String.join(",", dadosLivro.idiomas());
        this.nomeAutor = dadosLivro.autores().get(0).nome();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getQtdDownload() {
        return qtdDownload;
    }

    public void setQtdDownload(Integer qtdDownload) {
        this.qtdDownload = qtdDownload;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "\nTitulo: " + titulo +
               "\nIdioma: " + idioma +
               "\nQuantidade de downloads: " + qtdDownload +
               "\nAutor: " + autor;
    }
}
