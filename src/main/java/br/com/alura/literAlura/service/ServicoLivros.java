package br.com.alura.literAlura.service;

import br.com.alura.literAlura.model.Autor;
import br.com.alura.literAlura.model.Livro;
import br.com.alura.literAlura.repository.AutorRepositorio;
import br.com.alura.literAlura.repository.LivroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;


@Service
public class ServicoLivros {
    @Autowired
    AutorRepositorio autorRepositorio;
    @Autowired
    LivroRepositorio livroRepositorio;
    public void salvaLivro(Livro livro) {
        Livro livroSalvo = livroRepositorio.obterPeloTitulo(livro.getTitulo());

        if(livroSalvo == null) {
            Autor autor = livro.getAutor();

            Autor autorJaSalvo = autorRepositorio.procurarPeloNome(autor.getNome());
            if(autorJaSalvo != null) {
                livro.setAutor(autorJaSalvo);
                autorJaSalvo.setLivro(livro);
            } else {
                Autor autorSalvo = autorRepositorio.save(autor);
                livro.setAutor(autorSalvo);
                autorSalvo.setLivro(livro);
            }

            livroRepositorio.save(livro);
            System.out.println("Livro adicionado com sucesso!");
        } else {
            System.out.println("Livro já cadastrado.");
        }
    }

    public List<Livro> obterTodosLivros() {
        return livroRepositorio.findAll();
    }

    public List<Autor> obterTodosAutores() {
        return autorRepositorio.findAll();
    }

    public List<Autor> obterAutoresVivos(String ano) {
        return autorRepositorio.anoNascimento(ano);
    }

    public List<Livro> obterLivrosPorIdioma() {
        Scanner scanner = new Scanner(System.in);
        List<String> idioma = livroRepositorio.idioma();
        System.out.print("\nIdiomas: { ");
        if(idioma.size() == 1) {
            idioma.forEach(System.out::print);
        } else {
            idioma.forEach(l -> System.out.print(l + ", "));
        }
        System.out.println(" }");
        System.out.println("\nEscolha o idioma para pesquisar os livros: ");
        String linguagem = scanner.nextLine();
        if(!idioma.contains(linguagem)) {
            System.out.println("No registered book uses this linguagem");
        }
        return livroRepositorio.booksByLanguage(linguagem);
    }
}
