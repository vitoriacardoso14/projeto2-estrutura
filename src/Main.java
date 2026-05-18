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
            System.out.println("3 - Divergência entre avaliações IMDB e TMDB em séries");
            System.out.println("4 - Top 10 títulos por gênero e avaliação");
            System.out.println("5 - Filmes subestimados por palavra-chave");
            System.out.println("0 - Sair");

            System.out.print("\nEscolha uma opção: ");

            option = sc.nextInt();

            sc.nextLine();

            switch (option) {

                case 1:

                    System.out.println("\n=== GÊNEROS ===");
                    System.out.println("crime");
                    System.out.println("drama");
                    System.out.println("comedy");
                    System.out.println("action");
                    System.out.println("romance");

                    System.out.print("\nDigite o gênero: ");
                    String genre = sc.nextLine();

                    System.out.print("Digite o número mínimo de votos: ");
                    int minVotes = sc.nextInt();

                    Analysis.top10ByGenre(
                            tree,
                            genre,
                            minVotes
                    );

                    break;

                case 0:

                    System.out.println("\nEncerrando programa...");
                    break;

                default:

                    System.out.println("\nOpção inválida.");
            }

        } while (option != 0);

        sc.close();
    }
}