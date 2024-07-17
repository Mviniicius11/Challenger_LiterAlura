package br.com.alura.literAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record DadosLivro(@JsonAlias("title") String titulo,
                         @JsonAlias("download_count") Integer quantidade,
                         @JsonAlias("languages") List<String> idiomas,
                         @JsonAlias("authors") List<DadosAutor> autores) {
}
