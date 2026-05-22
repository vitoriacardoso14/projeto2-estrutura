import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        BST tree = new BST();

        Loader.loadData("../data/titles.csv", tree);

        Scanner sc = new Scanner(System.in);

        int option;

        do {

            System.out.println("\n===== MENU =====");

            System.out.println("1 - Top 10 títulos por gênero e avaliação");
            System.out.println("2 - Top 5 títulos por gênero e relevância");
            System.out.println("3 - Top 10 títulos mais populares por país e classificação indicativa");
            System.out.println("4 - Divergência entre avaliações IMDB e TMDB em séries");
            System.out.println("5 - Filmes subestimados por palavra-chave");
            System.out.println("0 - Sair");

            System.out.print("\nEscolha uma opção: ");

            option = sc.nextInt();

            sc.nextLine();

            switch (option) {

                case 1:

                    // EXIBE AS OPÇÕES DE GÊNERO PARA O USUÁRIO
                    // O usuário verá os nomes em português
                    System.out.println("\n=== GÊNEROS ===");

                    System.out.println("(1) - Crime");
                    System.out.println("(2) - Drama");
                    System.out.println("(3) - Comédia");
                    System.out.println("(4) - Ação");
                    System.out.println("(5) - Romance");

                    System.out.print("\nEscolha um gênero: (Informe o número)");

                    // LÊ O NÚMERO DIGITADO
                    int genreOption = sc.nextInt();

                    sc.nextLine();

                    // VARIÁVEL QUE VAI GUARDAR O GÊNERO NO FORMATO USADO NO DATASET
                    String genre = "";

                    // CONVERTE A OPÇÃO ESCOLHA PARA O GÊNERO EM INGLÊS (porque o CSV usa inglês)
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

                        // CASO O USUÁRIO DIGITE UMA OPÇÃO INVÁLIDA
                        default:

                            System.out.println("\nGênero inválido.");
                            break;
                    }

                    // SÓ EXECUTA A ANÁLISE SE O GÊNERO FOR VÁLIDO
                    if (!genre.equals("")) {

                        System.out.print("\nDigite o número mínimo de votos: ");

                        int minVotes = sc.nextInt();

                        // CHAMA A ANÁLISE
                        // PASSANDO:
                        // - A BST
                        // - O GÊNERO ESCOLHIDO
                        // - O NÚMERO MÍNIMO DE VOTOS
                        Analysis.top10ByGenre(
                                tree,
                                genre,
                                minVotes
                        );
                    }

                    break;

                case 0:

                    System.out.print("\nTem certeza que deseja sair? (S/N): ");

                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("S")) {

                        System.out.println("\nEncerrando programa...");

                    } else {

                        option = -1;

                        System.out.println("\nRetornando ao menu...");
                    }

                    break;
            }

        } while (option != 0);

        sc.close();
    }
}