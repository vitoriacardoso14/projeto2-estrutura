import java.util.ArrayList;
import java.util.Comparator;

public class Analysis {

    // ANÁLISE 1 - Top 10 títulos por gênero e avaliação
    public static void top10ByGenre(
            BST tree,
            String genre,
            int minVotes
    ) {

        ArrayList<ProgramaNetFlix> result = new ArrayList<>();

        collectTitles(tree.getRoot(), result, genre, minVotes);

        // ordena por imdb_score decrescente
        result.sort(
                Comparator.comparingDouble(
                        ProgramaNetFlix::getImdbScore
                ).reversed()
        );

        System.out.println("\n=== TOP 10 TÍTULOS ===\n");

        int limit = Math.min(10, result.size());

        for (int i = 0; i < limit; i++) {

            ProgramaNetFlix p = result.get(i);

            System.out.println(
                    (i + 1) + ". "
                    + p.getTitle()
                    + " | IMDB: "
                    + p.getImdbScore()
                    + " | Votes: "
                    + p.getImdbVotes()
            );
        }
    }

    // PERCORRE A BST
    private static void collectTitles(
            Node current,
            ArrayList<ProgramaNetFlix> result,
            String genre,
            int minVotes
    ) {

        if (current != null) {

            collectTitles(current.left, result, genre, minVotes);

            ProgramaNetFlix p = current.data;

            if (
                    p.getGenres().toLowerCase().contains(genre.toLowerCase())
                    &&
                    p.getImdbVotes() > minVotes
            ) {

                result.add(p);
            }

            collectTitles(current.right, result, genre, minVotes);
        }
    }
}