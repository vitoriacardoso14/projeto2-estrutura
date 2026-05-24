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
            System.out.print("Escolha uma opção: ");

            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {

                case 1: //Ler dataset de arquivo
                    System.out.println();
                    System.out.print("Digite o nome do arquivo (ex: titles.csv): ");
                    String filename = sc.nextLine();
                    Loader.loadData("data/" + filename, tree);
                    break;

                case 2: //Executar análises de dados (Submenu)
                    executarSubmenuAnalises(sc, tree);
                    break;

                case 3: //Inserir programa
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

                    System.out.print("Ano de lançamento: ");
                    int releaseYear = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Classificação indicativa (ex: TV-MA, TV-14, PG-13, R, PG): ");
                    String ageCertification = sc.nextLine().trim();

                    System.out.print("Duração em minutos: ");
                    int runtime = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Gêneros (ex: ['drama', 'crime']): ");
                    String genres = sc.nextLine().trim();

                    System.out.print("Países de produção (ex: ['US'], ['BR']): ");
                    String productionCountries = sc.nextLine().trim();

                    int seasons = 0;

                    if (type.equals("SHOW")) {
                        System.out.print("Número de temporadas: ");
                        seasons = sc.nextInt();
                        sc.nextLine();
                    }

                    System.out.print("IMDB ID (ex: tt1234567): ");
                    String imdbId = sc.nextLine().trim();

                    System.out.print("Nota IMDB: ");
                    double imdbScore = sc.nextDouble();

                    System.out.print("Quantidade de votos IMDB: ");
                    int imdbVotes = sc.nextInt();

                    System.out.print("Popularidade TMDB: ");
                    double tmdbPopularity = sc.nextDouble();

                    System.out.print("Nota TMDB: ");
                    double tmdbScore = sc.nextDouble();
                    sc.nextLine();

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
                    break;

                case 4: //Buscar programa por ID
                    System.out.println();
                    System.out.print("Digite o ID do programa para buscar: ");
                    String idBusca = sc.nextLine().trim();
                    tree.searchWithMetrics(idBusca);
                    break;

                case 5: //Remover programa por ID
                    System.out.println();
                    System.out.print("Digite o ID do programa para remover: ");
                    String idRemover = sc.nextLine().trim();
                    tree.remove(idRemover);
                    break;

                case 6: //Exibir altura da árvore
                    System.out.println();
                    System.out.println("Altura da árvore BST: " + tree.height());
                    break;

                case 7: //Salvar dados em arquivo
                    System.out.println();
                    System.out.println("=== SALVAR DADOS ===");
                    System.out.print("Digite o nome do arquivo de saída (ex: convertido.csv): ");
                    String outputFile = sc.nextLine();
                    Loader.saveData("data/" + outputFile, tree);
                    break;

                case 8: //Encerrar a aplicação
                    System.out.println();
                    System.out.print("Deseja realmente sair? (s/n): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("s")) {
                        tree = null;
                        running = false;
                        System.out.println("Aplicação encerrada.");
                    }
                    break;

                default:
                    System.out.println();
                    System.out.println("Opção inválida.");
            }
        }
        sc.close();
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
            System.out.print("Escolha uma análise: ");

            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    System.out.println();
                    System.out.println("=== GÊNEROS DISPONÍVEIS ===");
                    System.out.println("(1) - Crime");
                    System.out.println("(2) - Drama");
                    System.out.println("(3) - Comédia");
                    System.out.println("(4) - Ação");
                    System.out.println("(5) - Romance");
                    System.out.println("===========================");
                    System.out.print("Escolha um gênero: ");
                    int genreOption = sc.nextInt();
                    
                    String genre = "";
                    if (genreOption == 1) {
                        genre = "crime";
                    } else if (genreOption == 2) {
                        genre = "drama";
                    } else if (genreOption == 3) {
                        genre = "comedy";
                    } else if (genreOption == 4) {
                        genre = "action";
                    } else if (genreOption == 5) {
                        genre = "romance";
                    }

                    if (!genre.equals("")) {
                        System.out.print("Digite o número mínimo de votos: ");
                        int minVotes = sc.nextInt();
                        Analysis.top10ByGenre(tree, genre, minVotes);
                    } else {
                        System.out.println("Gênero inválido.");
                    }
                    break;

                case 2:
                    System.out.println();
                    System.out.println("=== GÊNEROS DISPONÍVEIS ===");
                    System.out.println("(1) - Crime");
                    System.out.println("(2) - Drama");
                    System.out.println("(3) - Comédia");
                    System.out.println("(4) - Ação");
                    System.out.println("(5) - Romance");
                    System.out.println("===========================");
                    System.out.print("Escolha um gênero: ");
                    int genreOption2 = sc.nextInt();
                    
                    String genre2 = "";
                    if (genreOption2 == 1) {
                        genre2 = "crime";
                    } else if (genreOption2 == 2) {
                        genre2 = "drama";
                    } else if (genreOption2 == 3) {
                        genre2 = "comedy";
                    } else if (genreOption2 == 4) {
                        genre2 = "action";
                    } else if (genreOption2 == 5) {
                        genre2 = "romance";
                    }

                    System.out.println();
                    System.out.println("=== TIPO DISPONÍVEL ===");
                    System.out.println("(1) - Filme");
                    System.out.println("(2) - Série");
                    System.out.println("=======================");
                    System.out.print("Escolha o tipo: ");
                    int typeOption = sc.nextInt();
                    
                    String type = "MOVIE";
                    if (typeOption == 2) {
                        type = "SHOW";
                    }

                    System.out.println();
                    System.out.println("=== CLASSIFICAÇÃO INDICATIVA ===");
                    System.out.println("(1) - TV-MA (Conteúdo Adulto / +18)");
                    System.out.println("(2) - TV-14 (Maiores de 14 anos)");
                    System.out.println("(3) - PG-13 (Maiores de 13 anos)");
                    System.out.println("(4) - R     (Restrito / +17)");
                    System.out.println("(5) - PG    (Orientação dos pais / Livre)");
                    System.out.println("================================");
                    System.out.print("Escolha a classificação: ");
                    int ageOpt = sc.nextInt();
                    
                    String ageCert = "PG";
                    if (ageOpt == 1) {
                        ageCert = "TV-MA";
                    } else if (ageOpt == 2) {
                        ageCert = "TV-14";
                    } else if (ageOpt == 3) {
                        ageCert = "PG-13";
                    } else if (ageOpt == 4) {
                        ageCert = "R";
                    }

                    System.out.print("Digite a popularidade mínima no TMDB: ");
                    double minPop = sc.nextDouble();

                    if (!genre2.equals("")) {
                        Analysis.top10Recommended(tree, genre2, type, ageCert, minPop);
                    }
                    break;

                case 3:
                    System.out.println();
                    System.out.println("=== PAÍSES DE PRODUÇÃO ===");
                    System.out.println("(1) - Estados Unidos (US)");
                    System.out.println("(2) - Brasil (BR)");
                    System.out.println("(3) - Reino Unido (GB)");
                    System.out.println("(4) - Japão (JP)");
                    System.out.println("(5) - Coreia do Sul (KR)");
                    System.out.println("==========================");
                    System.out.print("Escolha um país: ");
                    int countryOpt = sc.nextInt();
                    
                    String country = "US";
                    if (countryOpt == 2) {
                        country = "BR";
                    } else if (countryOpt == 3) {
                        country = "GB";
                    } else if (countryOpt == 4) {
                        country = "JP";
                    } else if (countryOpt == 5) {
                        country = "KR";
                    }

                    System.out.println();
                    System.out.println("=== CLASSIFICAÇÃO INDICATIVA ===");
                    System.out.println("(1) - TV-MA (Conteúdo Adulto / +18)");
                    System.out.println("(2) - TV-14 (Maiores de 14 anos)");
                    System.out.println("(3) - PG-13 (Maiores de 13 anos)");
                    System.out.println("(4) - R     (Restrito / +17)");
                    System.out.println("(5) - PG    (Orientação dos pais / Livre)");
                    System.out.println("================================");
                    System.out.print("Escolha a classificação: ");
                    int ageOpt3 = sc.nextInt();
                    
                    String ageCert3 = "PG";
                    if (ageOpt3 == 1) {
                        ageCert3 = "TV-MA";
                    } else if (ageOpt3 == 2) {
                        ageCert3 = "TV-14";
                    } else if (ageOpt3 == 3) {
                        ageCert3 = "PG-13";
                    } else if (ageOpt3 == 4) {
                        ageCert3 = "R";
                    }

                    System.out.print("Quantidade de resultados a exibir: ");
                    int limit3 = sc.nextInt();

                    Analysis.popularByCountry(tree, country, ageCert3, limit3);
                    break;

                case 4:
                    int minSeasons = 0;
                    while (minSeasons < 3) {
                        System.out.println();
                        System.out.print("Digite o número mínimo de temporadas (Mínimo: 3): ");
                        minSeasons = sc.nextInt();
                    }
                    System.out.print("Digite o ano de lançamento limite (ex: 2015): ");
                    int releaseYear = sc.nextInt();
                    
                    System.out.print("Quantidade de resultados a exibir: ");
                    int limit4 = sc.nextInt();
                    sc.nextLine();

                    Analysis.seriesDivergence(tree, minSeasons, releaseYear, limit4);
                    break;

                case 5:
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
                    System.out.print("Escolha uma palavra-chave (digite o número): ");
                    int keyOpt = sc.nextInt();
                    
                    String keyword = "school";
                    if (keyOpt == 1) {
                        keyword = "love";
                    } else if (keyOpt == 2) {
                        keyword = "war";
                    } else if (keyOpt == 3) {
                        keyword = "life";
                    } else if (keyOpt == 4) {
                        keyword = "death";
                    } else if (keyOpt == 5) {
                        keyword = "night";
                    } else if (keyOpt == 6) {
                        keyword = "family";
                    }

                    System.out.print("Digite a popularidade máxima limite (ex: 20.0): ");
                    double maxPopularity = sc.nextDouble();
                    
                    System.out.print("Digite a nota mínima IMDB (ex: 7.0): ");
                    double minImdb = sc.nextDouble();
                    sc.nextLine();

                    Analysis.underestimatedMovies(tree, keyword, maxPopularity, minImdb);
                    break;

                case 0:
                    System.out.println();
                    System.out.println("Voltando ao Menu Principal...");
                    break;

                default:
                    System.out.println();
                    System.out.println("Análise inválida.");
            }
        } while (option != 0);
    }
}