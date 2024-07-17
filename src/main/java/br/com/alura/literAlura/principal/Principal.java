package br.com.alura.literAlura.principal;

import br.com.alura.literAlura.model.*;
import br.com.alura.literAlura.repository.AutorRepositorio;
import br.com.alura.literAlura.repository.LivroRepositorio;
import br.com.alura.literAlura.service.ConsumoApi;
import br.com.alura.literAlura.service.ConverteDados;
import br.com.alura.literAlura.service.ServicoLivros;

import java.util.Scanner;

public class Principal {
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConverteDados converteDados = new ConverteDados();
    private ServicoLivros servicoLivros;

    public Principal(ServicoLivros servicoLivros){
        this.servicoLivros = servicoLivros;
    }

    public void exibeMenu(){

        var opcao = 0;

        while (opcao == 0) {
            String menu = """
                    ***********************************************
                    
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livro em um determinado idioma
                    0 - Sair
                    
                    ***********************************************
                    """;

            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    obterLivro();
                    break;
                case 2:
                    obterLivros();
                    break;
                case 3:
                    obterAutores();
                    break;
                case 4:
                    obterAutoresVivos();
                    break;
                case 5:
                    obterLivroPorIdioma();
                    break;
                case 0:
                    System.out.println("Encerrando a aplicação.");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }


    private DadosLivro obterDadosLivro() {
        String apiUrlStart = "https://gutendex.com//books?search=";
        System.out.println("Insert the name of the book: ");
        String bookTitle = scanner.nextLine();

        var bookJson = consumoApi.obterDados(apiUrlStart + bookTitle.toLowerCase().replace(" ", "%20"));
        ResultadoDados resultsData = converteDados.obterDados(bookJson, ResultadoDados.class);
        DadosLivro dadosLivro = resultsData.dadosLivros().get(0);

        return dadosLivro;
    }
    private void obterLivro() {
        DadosLivro dadosLivro = obterDadosLivro();
        Livro livro = new Livro(dadosLivro);

        var autor1 = dadosLivro.autores().get(0);
        DadosAutor dadosAutor = new DadosAutor(autor1.nome(), autor1.anoNascimento(), autor1.anoFalecimento());
        Autor autor = new Autor(dadosAutor);

        livro.setAutor(autor);
        servicoLivros.salvaLivro(livro);
        System.out.println(livro);
    }

    // CASE 2
    private void obterLivros() {
        var livros = servicoLivros.obterTodosLivros();
        if(!livros.isEmpty()) {
            livros.forEach(System.out::println);
        } else {
            System.out.println("Você ainda não cadastrou nenhum livro.");
        }
    }

    // CASE 3
    private void obterAutores() {
        var autor = servicoLivros.obterTodosAutores();
        if(!autor.isEmpty()) {
            autor.forEach(System.out::println);
        } else {
            System.out.println("Você não cadastrou nenhum autor.");
        }
    }

    // CASE 4
    private void obterAutoresVivos() {
        System.out.println("Insira o ano que deseja pesquisar: ");
        String ano = scanner.nextLine();

        var autoresVivoEm = servicoLivros.obterAutoresVivos(ano);
        if(!autoresVivoEm.isEmpty()) {
            autoresVivoEm.forEach(System.out::println);
        } else {
            System.out.println("Nenhum autor registrado estava vivo este ano.");
        }
    }

    // CASE 5
    private void obterLivroPorIdioma() {
        var livrosPorIdioma = servicoLivros.obterLivrosPorIdioma();
        livrosPorIdioma.forEach(System.out::println);
    }
}
