import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // =====================================================
        // CRIA A BST
        // =====================================================

        BST tree = new BST();

        // =====================================================
        // CARREGA O DATASET CSV
        // =====================================================

        Loader.loadData("../data/titles.csv", tree);

        // =====================================================
        // SCANNER PARA LEITURA DO TECLADO
        // =====================================================

        Scanner sc = new Scanner(System.in);

        int option;

        // =====================================================
        // MENU PRINCIPAL
        // =====================================================

        do {

            System.out.println("\n===== MENU =====");

            System.out.println("1 - Top 10 títulos por gênero e avaliação");

            System.out.println("2 - Títulos mais recomendados para assistir");

            System.out.println("3 - Títulos mais populares por país e classificação indicativa");

            System.out.println("4 - Divergência entre avaliações IMDB e TMDB em séries");

            System.out.println("5 - Filmes subestimados por palavra-chave");

            System.out.println("0 - Sair");

            System.out.print("\nEscolha uma opção: ");

            option = sc.nextInt();

            sc.nextLine();

            // =====================================================
            // SWITCH PRINCIPAL
            // =====================================================

            switch (option) {

                // =====================================================
                // ANÁLISE 1
                // =====================================================

                case 1:

                    System.out.println("\n=== GÊNEROS ===");

                    System.out.println("(1) - Crime");
                    System.out.println("(2) - Drama");
                    System.out.println("(3) - Comédia");
                    System.out.println("(4) - Ação");
                    System.out.println("(5) - Romance");

                    System.out.print("\nEscolha um gênero: ");

                    int genreOption = sc.nextInt();

                    sc.nextLine();

                    // GÊNERO NO FORMATO DO DATASET
                    String genre = "";

                    switch (genreOption) {

                        case 1:
                            genre = "crime";
                            break;

                        case 2:
                            genre = "drama";
                            break;

                        case 3:
                            genre = "comedy";
                            break;

                        case 4:
                            genre = "action";
                            break;

                        case 5:
                            genre = "romance";
                            break;

                        default:

                            System.out.println("\nGênero inválido.");
                            break;
                    }

                    // SÓ EXECUTA SE O GÊNERO FOR VÁLIDO
                    if (!genre.equals("")) {

                        System.out.print(
                                "\nDigite o número mínimo de votos: "
                        );

                        int minVotes = sc.nextInt();

                        // CHAMA A ANÁLISE
                        Analysis.top10ByGenre(
                                tree,
                                genre,
                                minVotes
                        );
                    }

                    break;

                // =====================================================
                // ANÁLISE 2
                // =====================================================

                case 2:

                    // ESCOLHA DO GÊNERO

                    System.out.println("\n=== GÊNEROS ===");

                    System.out.println("(1) - Crime");
                    System.out.println("(2) - Drama");
                    System.out.println("(3) - Comédia");
                    System.out.println("(4) - Ação");
                    System.out.println("(5) - Romance");

                    System.out.print("\nEscolha um gênero: ");

                    int genreOption2 = sc.nextInt();

                    sc.nextLine();

                    String genre2 = "";

                    switch (genreOption2) {

                        case 1:
                            genre2 = "crime";
                            break;

                        case 2:
                            genre2 = "drama";
                            break;

                        case 3:
                            genre2 = "comedy";
                            break;

                        case 4:
                            genre2 = "action";
                            break;

                        case 5:
                            genre2 = "romance";
                            break;

                        default:

                            System.out.println("\nGênero inválido.");
                            break;
                    }

                    // ESCOLHA DO TIPO

                    System.out.println("\n=== TIPO ===");

                    System.out.println("(1) - Filme");
                    System.out.println("(2) - Série");

                    System.out.print("\nEscolha o tipo: ");

                    int typeOption = sc.nextInt();

                    sc.nextLine();

                    String type = "";

                    switch (typeOption) {

                        case 1:
                            type = "MOVIE";
                            break;

                        case 2:
                            type = "SHOW";
                            break;

                        default:

                            System.out.println("\nTipo inválido.");
                            break;
                    }

                    // ESCOLHA DA CLASSIFICAÇÃO

                    System.out.println("\n=== CLASSIFICAÇÃO INDICATIVA ===");

                    System.out.println("(1) - TV-MA  | Conteúdo adulto");
                    System.out.println("(2) - TV-14 | Maiores de 14 anos");
                    System.out.println("(3) - PG-13 | Maiores de 13 anos");
                    System.out.println("(4) - R     | Restrito para menores");
                    System.out.println("(5) - PG    | Orientação dos pais");

                    System.out.print("\nEscolha a classificação: ");

                    int ageOption2 = sc.nextInt();

                    sc.nextLine();

                    String ageCert2 = "";

                    switch (ageOption2) {

                        case 1:
                            ageCert2 = "TV-MA";
                            break;

                        case 2:
                            ageCert2 = "TV-14";
                            break;

                        case 3:
                            ageCert2 = "PG-13";
                            break;

                        case 4:
                            ageCert2 = "R";
                            break;

                        case 5:
                            ageCert2 = "PG";
                            break;

                        default:

                            System.out.println("\nClassificação inválida.");
                            break;
                    }

                    // POPULARIDADE MÍNIMA

                    System.out.print(
                            "\nDigite a popularidade mínima no TMDB: "
                    );

                    double minPopularity = sc.nextDouble();

                    // EXECUTA A ANÁLISE

                    if (
                            !genre2.equals("")
                            &&
                            !type.equals("")
                            &&
                            !ageCert2.equals("")
                    ) {

                        Analysis.top10Recommended(

                                tree,
                                genre2,
                                type,
                                ageCert2,
                                minPopularity
                        );
                    }

                    break;

                // =====================================================
                // ANÁLISE 3
                // =====================================================

                case 3: {

                    // ESCOLHA DO PAÍS

                    System.out.println("\n=== PAÍSES DE PRODUÇÃO ===");

                    System.out.println("(1) - Estados Unidos (US)");
                    System.out.println("(2) - Brasil (BR)");
                    System.out.println("(3) - Reino Unido (GB)");
                    System.out.println("(4) - Japão (JP)");
                    System.out.println("(5) - Coreia do Sul (KR)");

                    System.out.print("\nEscolha um país: ");

                    int countryOption = sc.nextInt();

                    sc.nextLine();

                    String country = "";

                    switch (countryOption) {

                        case 1:
                            country = "US";
                            break;

                        case 2:
                            country = "BR";
                            break;

                        case 3:
                            country = "GB";
                            break;

                        case 4:
                            country = "JP";
                            break;

                        case 5:
                            country = "KR";
                            break;

                        default:

                            System.out.println("\nPaís inválido.");
                            break;
                    }

                    // CLASSIFICAÇÃO INDICATIVA

                    System.out.println("\n=== CLASSIFICAÇÃO INDICATIVA ===");

                    System.out.println("(1) - TV-MA  | Conteúdo adulto");
                    System.out.println("(2) - TV-14 | Maiores de 14 anos");
                    System.out.println("(3) - PG-13 | Maiores de 13 anos");
                    System.out.println("(4) - R     | Restrito para menores");
                    System.out.println("(5) - PG    | Orientação dos pais");

                    System.out.print("\nEscolha a classificação: ");

                    int ageOption3 = sc.nextInt();

                    sc.nextLine();

                    String ageCert3 = "";

                    switch (ageOption3) {

                        case 1:
                            ageCert3 = "TV-MA";
                            break;

                        case 2:
                            ageCert3 = "TV-14";
                            break;

                        case 3:
                            ageCert3 = "PG-13";
                            break;

                        case 4:
                            ageCert3 = "R";
                            break;

                        case 5:
                            ageCert3 = "PG";
                            break;

                        default:

                            System.out.println("\nClassificação inválida.");
                            break;
                    }

                    // QUANTIDADE DE RESULTADOS

                    System.out.print(
                            "\nQuantos títulos deseja visualizar? "
                    );

                    int limit3 = sc.nextInt();

                    // EXECUTA A ANÁLISE

                    if (
                            !country.equals("")
                            &&
                            !ageCert3.equals("")
                    ) {

                        Analysis.popularByCountry(

                                tree,
                                country,
                                ageCert3,
                                limit3
                        );
                    }

                    break;
                }

                // =====================================================
                // ANÁLISE 4
                // =====================================================

                case 4: {

                    System.out.println(
                            "\n=== DIVERGÊNCIA IMDB vs TMDB EM SÉRIES ==="
                    );

                    int minSeasons = 0;

                    // VALIDAÇÃO DAS TEMPORADAS

                    while (minSeasons < 3) {

                        System.out.print(
                                "\nDigite o número mínimo de temporadas (mínimo: 3): "
                        );

                        minSeasons = sc.nextInt();

                        if (minSeasons < 3) {

                            System.out.println(
                                    "Erro: a análise exige no mínimo 3 temporadas."
                            );
                        }
                    }

                    // ANO MÍNIMO

                    System.out.print(
                            "\nDigite o ano mínimo de lançamento das séries: "
                    );

                    int releaseYear = sc.nextInt();

                    // QUANTIDADE DE RESULTADOS

                    System.out.print(
                            "\nQuantas séries deseja visualizar? "
                    );

                    int limit4 = sc.nextInt();

                    sc.nextLine();

                    // EXECUTA A ANÁLISE

                    Analysis.seriesDivergence(

                            tree,
                            minSeasons,
                            releaseYear,
                            limit4
                    );

                    break;
                }

                // =====================================================
                // ANÁLISE 5
                // =====================================================

                case 5: {

                    // =====================================================
                    // PALAVRAS-CHAVE DISPONÍVEIS
                    // =====================================================

                    System.out.println("\n=== PALAVRAS-CHAVE ===");

                    System.out.println("(1) - amor");
                    System.out.println("(2) - guerra");
                    System.out.println("(3) - vida");
                    System.out.println("(4) - morte");
                    System.out.println("(5) - noite");
                    System.out.println("(6) - família");
                    System.out.println("(7) - escola");

                    System.out.print("\nEscolha uma palavra-chave: ");

                    int keywordOption = sc.nextInt();

                    sc.nextLine();

                    // =====================================================
                    // CONVERTE OPÇÃO EM PALAVRA
                    // =====================================================

                    String keyword = "";

                    switch (keywordOption) {

                        case 1:
                            keyword = "love";
                            break;

                        case 2:
                            keyword = "war";
                            break;

                        case 3:
                            keyword = "life";
                            break;

                        case 4:
                            keyword = "death";
                            break;

                        case 5:
                            keyword = "night";
                            break;

                        case 6:
                            keyword = "family";
                            break;

                        case 7:
                            keyword = "school";
                            break;

                        default:

                            System.out.println("\nPalavra-chave inválida.");
                            break;
                    }

                    // =====================================================
                    // POPULARIDADE MÁXIMA
                    // =====================================================

                    System.out.print(
                            "\nDigite a popularidade máxima no TMDB: "
                    );

                    double maxPopularity = sc.nextDouble();

                    // =====================================================
                    // NOTA MÍNIMA DO IMDB
                    // =====================================================

                    System.out.print(
                            "\nDigite a nota mínima do IMDB: "
                    );

                    double minScore = sc.nextDouble();

                    // =====================================================
                    // EXECUTA A ANÁLISE
                    // =====================================================

                    if (!keyword.equals("")) {

                        Analysis.underestimatedMovies(

                                tree,
                                keyword,
                                maxPopularity,
                                minScore
                        );
                    }

                    break;
                }

                // =====================================================
                // SAIR
                // =====================================================

                case 0:

                    System.out.print(
                            "\nTem certeza que deseja sair? (S/N): "
                    );

                    String confirm = sc.nextLine();

                    if (
                            confirm.equalsIgnoreCase("S")
                            ||
                            confirm.equalsIgnoreCase("SIM")
                    ) {

                        System.out.println(
                                "\nEncerrando programa..."
                        );

                    } else {

                        option = -1;

                        System.out.println(
                                "\nRetornando ao menu..."
                        );
                    }

                    break;

                // =====================================================
                // OPÇÃO INVÁLIDA
                // =====================================================

                default:

                    System.out.println(
                            "\nOpção inválida."
                    );

                    break;
            }

        } while (option != 0);

        // =====================================================
        // FECHA O SCANNER
        // =====================================================

        sc.close();
    }
}