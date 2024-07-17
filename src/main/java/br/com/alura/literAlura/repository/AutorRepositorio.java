package br.com.alura.literAlura.repository;

import br.com.alura.literAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepositorio extends JpaRepository<Autor, Long> {
    Autor procurarPeloNome(String nome);
    @Query("SELECT a FROM Autor a WHERE a.anoFalecimento >= :ano AND :ano >= a.anoNascimento")
    List<Autor> anoNascimento(String anoNascimento);
}