/*
PROJETO 2 - BST NETFLIX
Disciplina: Estrutura de Dados

Integrantes:
- Vitória Cardoso Alves - 10735648
- Bruno Malagoli - 10736098
*/

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        BST tree = new BST();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("=========================================");
            System.out.println("       SISTEMA NETFLIX - ÁRVORE BST      ");
            System.out.println("=========================================");
            System.out.println("1 - Ler dataset de arquivo");
            System.out.println("2 - Executar análises de dados (Submenu)");
            System.out.println("3 - Inserir programa");
            System.out.println("4 - Buscar programa por ID");
            System.out.println("5 - Remover programa por ID");
            System.out.println("6 - Exibir altura da árvore");
            System.out.println("7 - Salvar dados em arquivo");
            System.out.println("8 - Encerrar a aplicação");
            System.out.println("=========================================");

            int option = lerOpcao(sc, "Escolha uma opção: ", 1, 8);

            switch (option) {

                case 1: // Ler dataset de arquivo
                    System.out.println();
                    System.out.print("Digite o nome do arquivo (ex: titles.csv): ");
                    String filename = sc.nextLine();
                    Loader.loadData("data/" + filename, tree);
                    break;

                case 2: // Executar análises de dados (Submenu)
                    executarSubmenuAnalises(sc, tree);
                    break;

                case 3: // Inserir programa
                    inserirPrograma(sc, tree);
                    break;

                case 4: // Buscar programa por ID
                    System.out.println();
                    System.out.print("Digite o ID do programa para buscar: ");
                    String idBusca = sc.nextLine().trim();
                    tree.searchWithMetrics(idBusca);
                    break;

                case 5: // Remover programa por ID
                    System.out.println();
                    System.out.print("Digite o ID do programa para remover: ");
                    String idRemover = sc.nextLine().trim();
                    tree.remove(idRemover);
                    break;

                case 6: // Exibir altura da árvore
                    System.out.println();
                    System.out.println("Altura da árvore BST: " + tree.height());
                    break;

                case 7: // Salvar dados em arquivo
                    System.out.println();
                    System.out.println("=== SALVAR DADOS ===");
                    System.out.print("Digite o nome do arquivo de saída (ex: convertido.csv): ");
                    String outputFile = sc.nextLine();
                    Loader.saveData("data/" + outputFile, tree);
                    break;

                case 8: // Encerrar a aplicação
                    System.out.println();
                    System.out.print("Deseja realmente sair? (s/n): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("s")) {
                        tree = null;
                        running = false;
                        System.out.println("Aplicação encerrada.");
                    }
                    break;
            }
        }

        sc.close();
    }

    // ====================================================================
    // INSERÇÃO DE NOVO PROGRAMA
    // ====================================================================
    private static void inserirPrograma(Scanner sc, BST tree) {

        System.out.println();
        System.out.println("=== INSERIR NOVO PROGRAMA ===");

        System.out.print("É filme ou série? (MOVIE/SHOW): ");
        String type = sc.nextLine().trim().toUpperCase();

        while (!type.equals("MOVIE") && !type.equals("SHOW")) {
            System.out.print("Tipo inválido. Digite MOVIE ou SHOW: ");
            type = sc.nextLine().trim().toUpperCase();
        }

        System.out.print("Digite um número único para o ID: ");
        String numId = sc.nextLine().trim();

        String id = (type.equals("SHOW") ? "ts" : "tm") + numId;

        System.out.print("Título: ");
        String title = sc.nextLine().trim();

        System.out.print("Descrição: ");
        String description = sc.nextLine().trim();

        int releaseYear = lerInteiro(sc, "Ano de lançamento: ");

        System.out.print("Classificação indicativa (ex: TV-MA, TV-14, PG-13, R, PG): ");
        String ageCertification = sc.nextLine().trim();

        int runtime = lerInteiro(sc, "Duração em minutos: ");

        System.out.print("Gêneros (ex: ['drama', 'crime']): ");
        String genres = sc.nextLine().trim();

        System.out.print("Países de produção (ex: ['US'], ['BR']): ");
        String productionCountries = sc.nextLine().trim();

        int seasons = 0;

        if (type.equals("SHOW")) {
            seasons = lerInteiro(sc, "Número de temporadas: ");
        }

        System.out.print("IMDB ID (ex: tt1234567): ");
        String imdbId = sc.nextLine().trim();

        double imdbScore = lerDouble(sc, "Nota IMDB: ");

        int imdbVotes = lerInteiro(sc, "Quantidade de votos IMDB: ");

        double tmdbPopularity = lerDouble(sc, "Popularidade TMDB: ");

        double tmdbScore = lerDouble(sc, "Nota TMDB: ");

        ProgramaNetFlix novoProg = new ProgramaNetFlix(
                id,
                title,
                type,
                description,
                releaseYear,
                ageCertification,
                runtime,
                genres,
                productionCountries,
                seasons,
                imdbId,
                imdbScore,
                imdbVotes,
                tmdbPopularity,
                tmdbScore
        );

        tree.insert(novoProg);

        System.out.println();
        System.out.println("Programa inserido com sucesso!");
        System.out.println("ID gerado: " + id);
    }

    // ====================================================================
    // SUBMENU DE ANÁLISES ESTATÍSTICAS (OPÇÃO 2 DO MENU PRINCIPAL)
    // ====================================================================
    private static void executarSubmenuAnalises(Scanner sc, BST tree) {

        int option;

        do {
            System.out.println();
            System.out.println("=========================================");
            System.out.println("            SUBMENU ANÁLISES             ");
            System.out.println("=========================================");
            System.out.println("1 - Top 10 títulos por gênero e avaliação");
            System.out.println("2 - Títulos mais recomendados para assistir");
            System.out.println("3 - Títulos mais populares por país e classificação");
            System.out.println("4 - Divergência entre avaliações IMDB e TMDB em séries");
            System.out.println("5 - Filmes subestimados por palavra-chave");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.println("=========================================");

            option = lerOpcao(sc, "Escolha uma análise: ", 0, 5);

            switch (option) {

                case 1:
                    executarAnalise1(sc, tree);
                    break;

                case 2:
                    executarAnalise2(sc, tree);
                    break;

                case 3:
                    executarAnalise3(sc, tree);
                    break;

                case 4:
                    executarAnalise4(sc, tree);
                    break;

                case 5:
                    executarAnalise5(sc, tree);
                    break;

                case 0:
                    System.out.println();
                    System.out.println("Voltando ao Menu Principal...");
                    break;
            }

        } while (option != 0);
    }

    // ====================================================================
    // ANÁLISE 1
    // ====================================================================
    private static void executarAnalise1(Scanner sc, BST tree) {

        String genre = escolherGenero(sc);

        int minVotes = lerInteiro(sc, "Digite o número mínimo de votos: ");

        Analysis.top10ByGenre(tree, genre, minVotes);
    }

    // ====================================================================
    // ANÁLISE 2
    // ====================================================================
    private static void executarAnalise2(Scanner sc, BST tree) {

        String genre = escolherGenero(sc);

        String type = escolherTipo(sc);

        String ageCert = escolherClassificacao(sc);

        double minPop = lerDouble(sc, "Digite a popularidade mínima no TMDB: ");

        Analysis.top10Recommended(tree, genre, type, ageCert, minPop);
    }

    // ====================================================================
    // ANÁLISE 3
    // ====================================================================
    private static void executarAnalise3(Scanner sc, BST tree) {

        String country = escolherPais(sc);

        String ageCert = escolherClassificacao(sc);

        int limit = lerInteiro(sc, "Quantidade de resultados a exibir: ");

        Analysis.popularByCountry(tree, country, ageCert, limit);
    }

    // ====================================================================
    // ANÁLISE 4
    // ====================================================================
    private static void executarAnalise4(Scanner sc, BST tree) {

        int minSeasons;

        do {
            minSeasons = lerInteiro(sc, "Digite o número mínimo de temporadas (Mínimo: 3): ");

            if (minSeasons < 3) {
                System.out.println("Valor inválido. O mínimo permitido é 3.");
            }

        } while (minSeasons < 3);

        int releaseYear = lerInteiro(sc, "Digite o ano de lançamento limite (ex: 2015): ");

        int limit = lerInteiro(sc, "Quantidade de resultados a exibir: ");

        Analysis.seriesDivergence(tree, minSeasons, releaseYear, limit);
    }

    // ====================================================================
    // ANÁLISE 5
    // ====================================================================
    private static void executarAnalise5(Scanner sc, BST tree) {

        String keyword = escolherPalavraChave(sc);

        double maxPopularity = lerDouble(sc, "Digite a popularidade máxima limite (ex: 20.0): ");

        double minImdb = lerDouble(sc, "Digite a nota mínima IMDB (ex: 7.0): ");

        Analysis.underestimatedMovies(tree, keyword, maxPopularity, minImdb);
    }

    // ====================================================================
    // ESCOLHA DE GÊNERO COM VALIDAÇÃO
    // ====================================================================
    private static String escolherGenero(Scanner sc) {

        System.out.println();
        System.out.println("=== GÊNEROS DISPONÍVEIS ===");
        System.out.println("(1) - Crime");
        System.out.println("(2) - Drama");
        System.out.println("(3) - Comédia");
        System.out.println("(4) - Ação");
        System.out.println("(5) - Romance");
        System.out.println("===========================");

        int option = lerOpcao(sc, "Escolha um gênero: ", 1, 5);

        if (option == 1) {
            return "crime";
        } else if (option == 2) {
            return "drama";
        } else if (option == 3) {
            return "comedy";
        } else if (option == 4) {
            return "action";
        } else {
            return "romance";
        }
    }

    // ====================================================================
    // ESCOLHA DE TIPO COM VALIDAÇÃO
    // ====================================================================
    private static String escolherTipo(Scanner sc) {

        System.out.println();
        System.out.println("=== TIPO DISPONÍVEL ===");
        System.out.println("(1) - Filme");
        System.out.println("(2) - Série");
        System.out.println("=======================");

        int option = lerOpcao(sc, "Escolha o tipo: ", 1, 2);

        if (option == 1) {
            return "MOVIE";
        } else {
            return "SHOW";
        }
    }

    // ====================================================================
    // ESCOLHA DE CLASSIFICAÇÃO INDICATIVA COM VALIDAÇÃO
    // ====================================================================
    private static String escolherClassificacao(Scanner sc) {

        System.out.println();
        System.out.println("=== CLASSIFICAÇÃO INDICATIVA ===");
        System.out.println("(1) - TV-MA (Conteúdo Adulto / +18)");
        System.out.println("(2) - TV-14 (Maiores de 14 anos)");
        System.out.println("(3) - PG-13 (Maiores de 13 anos)");
        System.out.println("(4) - R     (Restrito / +17)");
        System.out.println("(5) - PG    (Orientação dos pais / Livre)");
        System.out.println("================================");

        int option = lerOpcao(sc, "Escolha a classificação: ", 1, 5);

        if (option == 1) {
            return "TV-MA";
        } else if (option == 2) {
            return "TV-14";
        } else if (option == 3) {
            return "PG-13";
        } else if (option == 4) {
            return "R";
        } else {
            return "PG";
        }
    }

    // ====================================================================
    // ESCOLHA DE PAÍS COM VALIDAÇÃO
    // ====================================================================
    private static String escolherPais(Scanner sc) {

        System.out.println();
        System.out.println("=== PAÍSES DE PRODUÇÃO ===");
        System.out.println("(1) - Estados Unidos (US)");
        System.out.println("(2) - Brasil (BR)");
        System.out.println("(3) - Reino Unido (GB)");
        System.out.println("(4) - Japão (JP)");
        System.out.println("(5) - Coreia do Sul (KR)");
        System.out.println("==========================");

        int option = lerOpcao(sc, "Escolha um país: ", 1, 5);

        if (option == 1) {
            return "US";
        } else if (option == 2) {
            return "BR";
        } else if (option == 3) {
            return "GB";
        } else if (option == 4) {
            return "JP";
        } else {
            return "KR";
        }
    }

    // ====================================================================
    // ESCOLHA DE PALAVRA-CHAVE COM VALIDAÇÃO
    // ====================================================================
    private static String escolherPalavraChave(Scanner sc) {

        System.out.println();
        System.out.println("=== PALAVRAS-CHAVE ===");
        System.out.println("(1) - Amor (love)");
        System.out.println("(2) - Guerra (war)");
        System.out.println("(3) - Vida (life)");
        System.out.println("(4) - Morte (death)");
        System.out.println("(5) - Noite (night)");
        System.out.println("(6) - Família (family)");
        System.out.println("(7) - Escola (school)");
        System.out.println("======================");

        int option = lerOpcao(sc, "Escolha uma palavra-chave: ", 1, 7);

        if (option == 1) {
            return "love";
        } else if (option == 2) {
            return "war";
        } else if (option == 3) {
            return "life";
        } else if (option == 4) {
            return "death";
        } else if (option == 5) {
            return "night";
        } else if (option == 6) {
            return "family";
        } else {
            return "school";
        }
    }

    // ====================================================================
    // LEITURA DE OPÇÃO INTEIRA COM VALIDAÇÃO DE INTERVALO
    // ====================================================================
    private static int lerOpcao(
            Scanner sc,
            String mensagem,
            int minimo,
            int maximo
    ) {

        int valor;

        while (true) {
            System.out.print(mensagem);

            if (sc.hasNextInt()) {
                valor = sc.nextInt();
                sc.nextLine();

                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }

                System.out.println(
                        "Opção inválida. Digite um número entre "
                        + minimo
                        + " e "
                        + maximo
                        + "."
                );

            } else {
                System.out.println("Entrada inválida. Digite apenas números.");
                sc.nextLine();
            }
        }
    }

    // ====================================================================
    // LEITURA DE INTEIRO
    // ====================================================================
    private static int lerInteiro(
            Scanner sc,
            String mensagem
    ) {

        int valor;

        while (true) {
            System.out.print(mensagem);

            if (sc.hasNextInt()) {
                valor = sc.nextInt();
                sc.nextLine();
                return valor;
            }

            System.out.println("Entrada inválida. Digite um número inteiro.");
            sc.nextLine();
        }
    }

    // ====================================================================
    // LEITURA DE DOUBLE
    // ====================================================================
    private static double lerDouble(
            Scanner sc,
            String mensagem
    ) {

        double valor;

        while (true) {
            System.out.print(mensagem);

            if (sc.hasNextDouble()) {
                valor = sc.nextDouble();
                sc.nextLine();
                return valor;
            }

            System.out.println("Entrada inválida. Digite um número decimal válido.");
            sc.nextLine();
        }
    }
}