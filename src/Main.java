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

        // CRIAÇÃO DA ÁRVORE BST
        // Aqui criamos a árvore binária de busca que armazenará
        // todos os programas Netflix carregados do CSV.
        BST tree = new BST();

        // Scanner utilizado para ler dados digitados pelo usuário.
        Scanner sc = new Scanner(System.in);

        // CONTROLE DO MENU
        // Essa variável controla o loop principal do sistema.
        // Enquanto running = true, o menu continua aparecendo.
        boolean running = true;

        // LOOP PRINCIPAL DO SISTEMA

        while (running) {

            // MENU PRINCIPAL

            System.out.println("      SISTEMA NETFLIX - BST");

            System.out.println("1  - Ler dataset");

            System.out.println("\n========== ANÁLISES ==========");

            System.out.println("(2)  - Top 10 títulos por gênero e avaliação");
            System.out.println("(3)  - Títulos mais recomendados para assistir");
            System.out.println("(4)  - Títulos mais populares por país e classificação indicativa");
            System.out.println("(5)  - Divergência entre avaliações IMDB e TMDB em séries");
            System.out.println("(6)  - Filmes subestimados por palavra-chave");

            System.out.println("\n========== BST ==========");

            System.out.println("(7)  - Inserir programa");
            System.out.println("(8)  - Buscar programa");
            System.out.println("(9)  - Remover programa");

            System.out.println("\n========== ÁRVORE ==========");

            System.out.println("(10) - Exibir altura da árvore");

            System.out.println("\n========== ARQUIVOS ==========");

            System.out.println("(11) - Salvar dados em arquivo");

            System.out.println("\n(0)  - Encerrar aplicação");

            System.out.print("\nEscolha uma opção: ");

            int option = sc.nextInt();

            // LIMPA O BUFFER DO TECLADO
            sc.nextLine();

            // SWITCH DAS OPÇÕES

            switch (option) {

                // OPÇÃO 1 - LER DATASET
                case 1:

                    System.out.print(
                            "\nDigite o nome do arquivo CSV: "
                    );

                    String fileName = sc.nextLine();

                    Loader.loadData(
                            "../data/" + fileName,
                            tree
                    );

                    break;


                // OPÇÃO 2 - ANÁLISE 1
                // TOP 10 TÍTULOS POR GÊNERO E AVALIAÇÃO
                case 2: {

                    System.out.println("\n=== GÊNEROS DISPONÍVEIS ===");

                    System.out.println("(1) - Comédia");
                    System.out.println("(2) - Drama");
                    System.out.println("(3) - Crime");
                    System.out.println("(4) - Romance");
                    System.out.println("(5) - Ação");

                    System.out.print("\nEscolha um gênero: ");

                    int genreOption = sc.nextInt();

                    sc.nextLine();

                    String genre = "";

                    switch (genreOption) {

                        case 1:
                            genre = "comedy";
                            break;

                        case 2:
                            genre = "drama";
                            break;

                        case 3:
                            genre = "crime";
                            break;

                        case 4:
                            genre = "romance";
                            break;

                        case 5:
                            genre = "action";
                            break;

                        default:

                            System.out.println(
                                    "\nGênero inválido."
                            );
                    }

                    System.out.print(
                            "\nDigite o número mínimo de votos: "
                    );

                    int minVotes = sc.nextInt();

                    sc.nextLine();

                    // EXECUTA A ANÁLISE
                    if (!genre.equals("")) {

                        Analysis.top10ByGenre(
                                tree,
                                genre,
                                minVotes
                        );
                    }

                    break;
                }

                // OPÇÃO 3 - ANÁLISE 2
                // TÍTULOS MAIS RECOMENDADOS PARA ASSISTIR
                case 3: {

                    // GÊNEROS

                    System.out.println("\n=== GÊNEROS ===");

                    System.out.println("(1) - Comédia");
                    System.out.println("(2) - Drama");
                    System.out.println("(3) - Crime");
                    System.out.println("(4) - Romance");
                    System.out.println("(5) - Ação");

                    System.out.print("\nEscolha um gênero: ");

                    int genreOption = sc.nextInt();

                    sc.nextLine();

                    String genre = "";

                    switch (genreOption) {

                        case 1:
                            genre = "comedy";
                            break;

                        case 2:
                            genre = "drama";
                            break;

                        case 3:
                            genre = "crime";
                            break;

                        case 4:
                            genre = "romance";
                            break;

                        case 5:
                            genre = "action";
                            break;

                        default:

                            System.out.println(
                                    "\nGênero inválido."
                            );
                    }

                    // TIPO

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

                            System.out.println(
                                    "\nTipo inválido."
                            );
                    }

                    // CLASSIFICAÇÃO

                    System.out.println("\n=== CLASSIFICAÇÃO INDICATIVA ===");

                    System.out.println("(1) - Adulto");
                    System.out.println("(2) - Maiores de 14");
                    System.out.println("(3) - Maiores de 13");
                    System.out.println("(4) - Restrito");
                    System.out.println("(5) - Livre supervisionado");

                    System.out.print("\nEscolha a classificação: ");

                    int ageOption = sc.nextInt();

                    sc.nextLine();

                    String ageCert = "";

                    switch (ageOption) {

                        case 1:
                            ageCert = "TV-MA";
                            break;

                        case 2:
                            ageCert = "TV-14";
                            break;

                        case 3:
                            ageCert = "PG-13";
                            break;

                        case 4:
                            ageCert = "R";
                            break;

                        case 5:
                            ageCert = "PG";
                            break;

                        default:

                            System.out.println(
                                    "\nClassificação inválida."
                            );
                    }

                    // POPULARIDADE MÍNIMA

                    System.out.print(
                            "\nDigite a popularidade mínima no TMDB: "
                    );

                    double minPopularity = sc.nextDouble();

                    sc.nextLine();

                    // EXECUTA ANÁLISE

                    if (
                            !genre.equals("")
                            &&
                            !type.equals("")
                            &&
                            !ageCert.equals("")
                    ) {

                        Analysis.top10Recommended(
                                tree,
                                genre,
                                type,
                                ageCert,
                                minPopularity
                        );
                    }

                    break;
                }

                // OPÇÃO 4 - ANÁLISE 3
                case 4: {

                    System.out.println("\n=== PAÍSES ===");

                    System.out.println("(1) - Estados Unidos");
                    System.out.println("(2) - Brasil");
                    System.out.println("(3) - Reino Unido");
                    System.out.println("(4) - Japão");
                    System.out.println("(5) - Coreia do Sul");

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

                            System.out.println(
                                    "\nPaís inválido."
                            );
                    }

                    System.out.println("\n=== CLASSIFICAÇÃO ===");

                    System.out.println("(1) - Adulto");
                    System.out.println("(2) - Maiores de 14");
                    System.out.println("(3) - Maiores de 13");
                    System.out.println("(4) - Restrito");
                    System.out.println("(5) - Livre supervisionado");

                    System.out.print("\nEscolha a classificação: ");

                    int ageOption = sc.nextInt();

                    sc.nextLine();

                    String ageCert = "";

                    switch (ageOption) {

                        case 1:
                            ageCert = "TV-MA";
                            break;

                        case 2:
                            ageCert = "TV-14";
                            break;

                        case 3:
                            ageCert = "PG-13";
                            break;

                        case 4:
                            ageCert = "R";
                            break;

                        case 5:
                            ageCert = "PG";
                            break;

                        default:

                            System.out.println(
                                    "\nClassificação inválida."
                            );
                    }

                    System.out.print(
                            "\nQuantidade de resultados: "
                    );

                    int limit = sc.nextInt();

                    sc.nextLine();

                    if (
                            !country.equals("")
                            &&
                            !ageCert.equals("")
                    ) {

                        Analysis.popularByCountry(
                                tree,
                                country,
                                ageCert,
                                limit
                        );
                    }

                    break;
                }

                // OPÇÃO 5 - ANÁLISE 4
                case 5: {

                    System.out.println(
                            "\n=== DIVERGÊNCIA IMDB vs TMDB ==="
                    );

                    int minSeasons = 0;

                    while (minSeasons < 3) {

                        System.out.print(
                                "\nDigite o mínimo de temporadas (>=3): "
                        );

                        minSeasons = sc.nextInt();

                        if (minSeasons < 3) {

                            System.out.println(
                                    "\nValor inválido."
                            );
                        }
                    }

                    System.out.print(
                            "\nDigite o ano mínimo de lançamento: "
                    );

                    int releaseYear = sc.nextInt();

                    System.out.print(
                            "\nQuantidade de resultados: "
                    );

                    int limit = sc.nextInt();

                    sc.nextLine();

                    Analysis.seriesDivergence(
                            tree,
                            minSeasons,
                            releaseYear,
                            limit
                    );

                    break;
                }

                // OPÇÃO 6 - ANÁLISE 5
                case 6: {

                    System.out.println("\n=== PALAVRAS-CHAVE ===");

                    System.out.println("(1) - Amor");
                    System.out.println("(2) - Guerra");
                    System.out.println("(3) - Vida");
                    System.out.println("(4) - Morte");
                    System.out.println("(5) - Noite");
                    System.out.println("(6) - Família");
                    System.out.println("(7) - Escola");

                    System.out.print(
                            "\nEscolha uma palavra-chave: "
                    );

                    int keywordOption = sc.nextInt();

                    sc.nextLine();

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

                            System.out.println(
                                    "\nPalavra inválida."
                            );
                            break;
                    }

                    System.out.print(
                            "\nDigite a popularidade máxima: "
                    );

                    double maxPopularity = sc.nextDouble();

                    System.out.print(
                            "\nDigite a nota mínima do IMDB: "
                    );

                    double minScore = sc.nextDouble();

                    sc.nextLine();

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

            // OPÇÃO 7 - INSERIR PROGRAMA
            case 7: {

                System.out.println("\n=== INSERIR NOVO PROGRAMA ===");

                // TIPO DO PROGRAMA

                System.out.println("(1) - FILME");
                System.out.println("(2) - SÉRIE");

                System.out.print("\nEscolha o tipo: ");

                int typeOption = sc.nextInt();

                sc.nextLine();

                String showType = "";

                // GERA O PREFIXO DO ID

                if (typeOption == 1) {

                    showType = "MOVIE";

                } else if (typeOption == 2) {

                    showType = "SHOW";

                } else {

                    System.out.println("\nTipo inválido.");

                    break;
                }

                // GERA ID AUTOMÁTICO

                // tm = movie
                // ts = show

                long uniqueNumber = System.currentTimeMillis();

                String id;

                if (showType.equals("MOVIE")) {

                    id = "tm" + uniqueNumber;

                } else {

                    id = "ts" + uniqueNumber;
                }

                // LEITURA DOS DADOS

                System.out.print("\nTítulo: ");
                String title = sc.nextLine();

                System.out.print("Descrição: ");
                String description = sc.nextLine();

                System.out.print("Ano de lançamento: ");
                int releaseYear = sc.nextInt();

                System.out.print("Duração: ");
                int runtime = sc.nextInt();

                sc.nextLine();

                System.out.print("Gêneros: ");
                String genres = sc.nextLine();

                System.out.print("Países de produção: ");
                String countries = sc.nextLine();

                System.out.print("Quantidade de temporadas: ");
                int seasons = sc.nextInt();

                sc.nextLine();

                System.out.print("Classificação indicativa: ");
                String ageCertification = sc.nextLine();

                System.out.print("IMDB ID: ");
                String imdbId = sc.nextLine();

                System.out.print("IMDB Score: ");
                double imdbScore = sc.nextDouble();

                System.out.print("IMDB Votes: ");
                int imdbVotes = sc.nextInt();

                System.out.print("TMDB Popularity: ");
                double tmdbPopularity = sc.nextDouble();

                System.out.print("TMDB Score: ");
                double tmdbScore = sc.nextDouble();

                sc.nextLine();

                // CRIA OBJETO

                ProgramaNetFlix program = new ProgramaNetFlix(

                        id,
                        title,
                        showType,
                        description,
                        releaseYear,
                        ageCertification,
                        runtime,
                        genres,
                        countries,
                        seasons,
                        imdbId,
                        imdbScore,
                        imdbVotes,
                        tmdbPopularity,
                        tmdbScore
                );

                // INSERE NA BST

                tree.insert(program);

                System.out.println(
                        "\nPrograma inserido com sucesso!"
                );

                System.out.println("ID gerado: " + id);

                break;
            }

            // OPÇÃO 8 - BUSCAR PROGRAMA
            case 8: {

                System.out.println("\n=== BUSCAR PROGRAMA ===");

                System.out.print("\nDigite o ID do programa: ");

                String id = sc.nextLine();

                // MONITORAMENTO DE TEMPO

                long startTime = System.nanoTime();

                ProgramaNetFlix result = tree.search(id);

                long endTime = System.nanoTime();

                long executionTime = endTime - startTime;

                // EXIBE RESULTADO

                if (result != null) {

                    System.out.println("\n=== PROGRAMA ENCONTRADO ===\n");

                    System.out.println("ID: " + result.getId());

                    System.out.println("Título: " + result.getTitle());

                    System.out.println("Tipo: " + result.getShowType());

                    System.out.println("Ano: " + result.getReleaseYear());

                    System.out.println("Gêneros: " + result.getGenres());

                    System.out.println("IMDB Score: " + result.getImdbScore());

                    System.out.println("TMDB Score: " + result.getTmdbScore());

                    System.out.println("Países: " + result.getProductionCountries());

                } else {

                    System.out.println(
                            "\nPrograma não encontrado."
                    );
                }

                // EXIBE TEMPO E COMPARAÇÕES

                System.out.println(
                        "\nComparações realizadas: "
                        + tree.getComparisons()
                );

                System.out.println(
                        "Tempo de execução: "
                        + executionTime
                        + " ns"
                );

                break;
            }

            // OPÇÃO 9 - REMOVER PROGRAMA
            case 9: {

                System.out.println("\n=== REMOVER PROGRAMA ===");

                System.out.print("\nDigite o ID do programa: ");

                String id = sc.nextLine();

                // VERIFICA SE EXISTE

                ProgramaNetFlix result = tree.search(id);

                if (result == null) {

                    System.out.println(
                            "\nPrograma não encontrado."
                    );

                } else {

                    tree.remove(id);

                    System.out.println(
                            "\nPrograma removido com sucesso!"
                    );
                }

                break;
            }

            // OPÇÃO 10 - ALTURA DA ÁRVORE
            case 10:

                System.out.println(
                        "\nAltura da árvore BST: "
                        + tree.height()
                );

                break;

            // OPÇÃO 11 - SALVAR EM ARQUIVO
            case 11: {

                System.out.println("\n=== SALVAR DADOS ===");

                System.out.print(
                        "\nDigite o nome do arquivo: "
                );

                String outputFile = sc.nextLine();

                // CHAMA MÉTODO DE SALVAMENTO

                Loader.saveData(
                        "../data/" + outputFile,
                        tree
                );

                break;
            }

                // OPÇÃO 0 - ENCERRAR
                case 0:

                    System.out.print(
                            "\nDeseja realmente sair? (s/n): "
                    );

                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("s")) {

                        // libera referência da árvore
                        tree = null;

                        running = false;

                        System.out.println(
                                "\nAplicação encerrada."
                        );
                    }

                    break;

                // OPÇÃO INVÁLIDA
                default:

                    System.out.println(
                            "\nOpção inválida."
                    );
            }
        }
        sc.close();
    }
}