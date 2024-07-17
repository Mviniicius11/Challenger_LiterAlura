package br.com.alura.literAlura.repository;

import br.com.alura.literAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepositorio extends JpaRepository<Livro, Long> {
    Livro obterPeloTitulo(String titulo);
    @Query("SELECT DISTINCT b.idioma FROM Book b ORDER BY b.idioma")
    List<String> idioma();
    @Query("SELECT b FROM Livro b WHERE idioma = :idioma")
    List<Livro> booksByLanguage(String idioma);
}