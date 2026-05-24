/*
PROJETO 2 - BST NETFLIX
Disciplina: Estrutura de Dados

Integrantes:
- Vitória Cardoso Alves - 10735648
- Bruno Malagoli - 10736098
*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class Analysis {

    // ====================================================================
    // ANÁLISE 1 - Top 10 títulos por gênero e avaliação
    // PERCURSO: EM ORDEM SIMÉTRICA
    // ====================================================================

    public static void top10ByGenre(
            BST tree,
            String genre,
            int minVotes
    ) {

        ArrayList<ProgramaNetFlix> result = new ArrayList<>();

        collectInOrder(
                tree.getRoot(),
                result,
                genre,
                minVotes
        );

        // ORDENA POR:
        // 1º maior imdb_score
        // 2º maior imdb_votes
        result.sort(

                Comparator

                        .comparingDouble(
                                ProgramaNetFlix::getImdbScore
                        )

                        .reversed()

                        .thenComparing(

                                Comparator
                                        .comparingInt(
                                                ProgramaNetFlix::getImdbVotes
                                        )

                                        .reversed()
                        )
        );

        System.out.println(
                "\n=== TOP 10 TÍTULOS POR GÊNERO ===\n"
        );

        int limit = Math.min(10, result.size());

        if (limit == 0) {

            System.out.println(
                    "Nenhum título encontrado."
            );

            return;
        }

        for (int i = 0; i < limit; i++) {

            ProgramaNetFlix p = result.get(i);

            System.out.println(

                    (i + 1) + ". "

                    + p.getTitle()

                    + "\nGênero: "
                    + p.getGenres()

                    + "\nIMDB Score: "
                    + p.getImdbScore()

                    + "\nIMDB Votes: "
                    + p.getImdbVotes()

                    + "\n"
            );
        }
    }

    // ====================================================================
    // PERCURSO EM ORDEM SIMÉTRICA
    // ESQUERDA -> RAIZ -> DIREITA
    // ====================================================================

    private static void collectInOrder(
            Node current,
            ArrayList<ProgramaNetFlix> result,
            String genre,
            int minVotes
    ) {

        if (current != null) {

            // ESQUERDA
            collectInOrder(
                    current.left,
                    result,
                    genre,
                    minVotes
            );

            // RAIZ
            ProgramaNetFlix p = current.data;

            boolean validGenre =

                    p.getGenres()
                            .toLowerCase()
                            .contains(
                                    genre.toLowerCase()
                            );

            boolean validVotes =

                    p.getImdbVotes() > minVotes;

            if (
                    validGenre
                    &&
                    validVotes
            ) {

                result.add(p);
            }

            // DIREITA
            collectInOrder(
                    current.right,
                    result,
                    genre,
                    minVotes
            );
        }
    }

    // ====================================================================
    // ANÁLISE 2 - Títulos mais recomendados para assistir
    // PERCURSO: PÓS-ORDEM
    // ====================================================================

    public static void top10Recommended(
            BST tree,
            String genre,
            String type,
            String ageCert,
            double minTmdbPop
    ) {

        ArrayList<ProgramaNetFlix> result = new ArrayList<>();

        collectRecommended(
                tree.getRoot(),
                result,
                genre,
                type,
                ageCert,
                minTmdbPop
        );

        // ORDENAÇÃO:
        // 1º maior imdb_score
        // 2º maior imdb_votes
        result.sort(

                Comparator

                        .comparingDouble(
                                ProgramaNetFlix::getImdbScore
                        )

                        .reversed()

                        .thenComparing(

                                Comparator
                                        .comparingInt(
                                                ProgramaNetFlix::getImdbVotes
                                        )

                                        .reversed()
                        )
        );

        System.out.println(
                "\n=== TÍTULOS MAIS RECOMENDADOS ===\n"
        );

        int limit = Math.min(10, result.size());

        if (limit == 0) {

            System.out.println(
                    "Nenhum título encontrado."
            );

            return;
        }

        for (int i = 0; i < limit; i++) {

            ProgramaNetFlix p = result.get(i);

            System.out.println(

                    (i + 1) + ". "

                    + p.getTitle()

                    + "\nTipo: "
                    + p.getShowType()

                    + "\nIMDB Score: "
                    + p.getImdbScore()

                    + "\nIMDB Votes: "
                    + p.getImdbVotes()

                    + "\nTMDB Popularity: "
                    + p.getTmdbPopularity()

                    + "\n"
            );
        }
    }

    // ====================================================================
    // PERCURSO EM PÓS-ORDEM
    // ESQUERDA -> DIREITA -> RAIZ
    // ====================================================================

    private static void collectRecommended(
            Node current,
            ArrayList<ProgramaNetFlix> result,
            String genre,
            String type,
            String ageCert,
            double minTmdbPop
    ) {

        if (current != null) {

            // ESQUERDA
            collectRecommended(
                    current.left,
                    result,
                    genre,
                    type,
                    ageCert,
                    minTmdbPop
            );

            // DIREITA
            collectRecommended(
                    current.right,
                    result,
                    genre,
                    type,
                    ageCert,
                    minTmdbPop
            );

            // RAIZ
            ProgramaNetFlix p = current.data;

            boolean validGenre =

                    p.getGenres()
                            .toLowerCase()
                            .contains(
                                    genre.toLowerCase()
                            );

            boolean validType =

                    p.getShowType()
                            .equalsIgnoreCase(type);

            boolean validAge =

                    p.getAgeCertification()
                            .equalsIgnoreCase(ageCert);

            boolean validPopularity =

                    p.getTmdbPopularity()
                            >= minTmdbPop;

            if (
                    validGenre
                    &&
                    validType
                    &&
                    validAge
                    &&
                    validPopularity
            ) {

                result.add(p);
            }
        }
    }

    // ====================================================================
    // ANÁLISE 3 - Títulos mais populares por país e classificação
    // PERCURSO: EM LARGURA (BFS)
    // ====================================================================

    public static void popularByCountry(
            BST tree,
            String country,
            String ageCert,
            int limit
    ) {

        ArrayList<ProgramaNetFlix> result = new ArrayList<>();

        if (tree.getRoot() == null) {

            System.out.println(
                    "A árvore está vazia."
            );

            return;
        }

        // FILA PARA BFS
        Queue<Node> queue = new LinkedList<>();

        queue.add(tree.getRoot());

        // PERCURSO EM LARGURA
        while (!queue.isEmpty()) {

            Node current = queue.poll();

            ProgramaNetFlix p = current.data;

            // FILTRO DE PAÍS
            boolean validCountry =

                    p.getProductionCountries()
                            .toUpperCase()
                            .contains(
                                    country.toUpperCase()
                            );

            // FILTRO DE CLASSIFICAÇÃO
            boolean validAge =

                    p.getAgeCertification()
                            .equalsIgnoreCase(ageCert);

            if (
                    validCountry
                    &&
                    validAge
            ) {

                result.add(p);
            }

            // FILHO ESQUERDO
            if (current.left != null) {

                queue.add(current.left);
            }

            // FILHO DIREITO
            if (current.right != null) {

                queue.add(current.right);
            }
        }

        // ORDENAÇÃO:
        // 1º maior tmdb_popularity
        // 2º maior imdb_score
        result.sort(

                Comparator

                        .comparingDouble(
                                ProgramaNetFlix::getTmdbPopularity
                        )

                        .reversed()

                        .thenComparing(

                                Comparator
                                        .comparingDouble(
                                                ProgramaNetFlix::getImdbScore
                                        )

                                        .reversed()
                        )
        );

        System.out.println(

                "\n=== MAIS POPULARES | "
                + country
                + " | "
                + ageCert
                + " ===\n"
        );

        limit = Math.min(limit, result.size());

        if (limit == 0) {

            System.out.println(
                    "Nenhum título encontrado."
            );

            return;
        }

        for (int i = 0; i < limit; i++) {

            ProgramaNetFlix p = result.get(i);

            System.out.println(

                    (i + 1) + ". "

                    + p.getTitle()

                    + "\nTMDB Popularity: "
                    + p.getTmdbPopularity()

                    + "\nIMDB Score: "
                    + p.getImdbScore()

                    + "\nPaíses: "
                    + p.getProductionCountries()

                    + "\nClassificação: "
                    + p.getAgeCertification()

                    + "\n"
            );
        }
    }

    // ====================================================================
    // ANÁLISE 4 - Divergência IMDB vs TMDB
    // PERCURSO: PRÉ-ORDEM
    // ====================================================================

    public static void seriesDivergence(
            BST tree,
            int minSeasons,
            int releaseYear,
            int limit
    ) {

        ArrayList<ProgramaNetFlix> result = new ArrayList<>();

        collectPreOrder(
                tree.getRoot(),
                result,
                minSeasons,
                releaseYear
        );

        if (result.isEmpty()) {

            System.out.println(
                    "Nenhuma série encontrada."
            );

            return;
        }

        // CÁLCULO DA MÉDIA DAS DIVERGÊNCIAS
        double totalDifference = 0;

        for (ProgramaNetFlix p : result) {

            totalDifference += Math.abs(

                    p.getImdbScore()
                    -
                    p.getTmdbScore()
            );
        }

        double averageDifference =

                totalDifference / result.size();

        // ORDENA POR MAIOR DIFERENÇA
        result.sort((p1, p2) -> {

            double diff1 = Math.abs(

                    p1.getImdbScore()
                    -
                    p1.getTmdbScore()
            );

            double diff2 = Math.abs(

                    p2.getImdbScore()
                    -
                    p2.getTmdbScore()
            );

            return Double.compare(diff2, diff1);
        });

        System.out.println(
                "\n=== MAIOR DIVERGÊNCIA IMDB vs TMDB ===\n"
        );

        limit = Math.min(limit, result.size());

        for (int i = 0; i < limit; i++) {

            ProgramaNetFlix p = result.get(i);

            double difference = Math.abs(

                    p.getImdbScore()
                    -
                    p.getTmdbScore()
            );

            System.out.printf(

                    "%d. %s\n",
                    (i + 1),
                    p.getTitle()
            );

            System.out.printf(
                    "Ano: %d | Temporadas: %d\n",
                    p.getReleaseYear(),
                    p.getSeasons()
            );

            System.out.printf(
                    "IMDB: %.1f | TMDB: %.1f | Diferença: %.2f\n\n",
                    p.getImdbScore(),
                    p.getTmdbScore(),
                    difference
            );
        }

        System.out.printf(

                "--- Média geral das divergências (%d séries): %.2f ---%n",

                result.size(),
                averageDifference
        );
    }

    // ====================================================================
    // PERCURSO PRÉ-ORDEM
    // RAIZ -> ESQUERDA -> DIREITA
    // ====================================================================

    private static void collectPreOrder(
            Node current,
            ArrayList<ProgramaNetFlix> result,
            int minSeasons,
            int releaseYear
    ) {

        if (current != null) {

            // RAIZ
            ProgramaNetFlix p = current.data;

            boolean validType =

                    p.getShowType()
                            .equalsIgnoreCase("SHOW");

            boolean validSeasons =

                    p.getSeasons() >= minSeasons;

            boolean validYear =

                    p.getReleaseYear() >= releaseYear;

            boolean validScores =

                    p.getImdbScore() > 0
                    &&
                    p.getTmdbScore() > 0;

            if (
                    validType
                    &&
                    validSeasons
                    &&
                    validYear
                    &&
                    validScores
            ) {

                result.add(p);
            }

            // ESQUERDA
            collectPreOrder(
                    current.left,
                    result,
                    minSeasons,
                    releaseYear
            );

            // DIREITA
            collectPreOrder(
                    current.right,
                    result,
                    minSeasons,
                    releaseYear
            );
        }
    }

    // ====================================================================
    // ANÁLISE 5 - Filmes subestimados por palavra-chave
    // PERCURSO: EM ORDEM SIMÉTRICA
    // ====================================================================

    public static void underestimatedMovies(
            BST tree,
            String keyword,
            double maxPopularity,
            double minScore
    ) {

        ArrayList<ProgramaNetFlix> result = new ArrayList<>();

        collectUnderestimated(

                tree.getRoot(),
                result,
                keyword,
                maxPopularity,
                minScore
        );

        // ORDENAÇÃO:
        // 1º maior imdb_score
        // 2º menor tmdb_popularity
        result.sort(

                Comparator

                        .comparingDouble(
                                ProgramaNetFlix::getImdbScore
                        )

                        .reversed()

                        .thenComparing(

                                Comparator
                                        .comparingDouble(
                                                ProgramaNetFlix::getTmdbPopularity
                                        )
                        )
        );

        System.out.println(
                "\n=== FILMES SUBESTIMADOS ===\n"
        );

        int limit = Math.min(10, result.size());

        if (limit == 0) {

            System.out.println(
                    "Nenhum filme encontrado."
            );

            return;
        }

        for (int i = 0; i < limit; i++) {

            ProgramaNetFlix p = result.get(i);

            System.out.println(

                    (i + 1) + ". "

                    + p.getTitle()

                    + "\nIMDB Score: "
                    + p.getImdbScore()

                    + "\nTMDB Popularity: "
                    + p.getTmdbPopularity()

                    + "\nTipo: "
                    + p.getShowType()

                    + "\n"
            );
        }
    }

    // ====================================================================
    // PERCURSO EM ORDEM SIMÉTRICA
    // ESQUERDA -> RAIZ -> DIREITA
    // ====================================================================

    private static void collectUnderestimated(
            Node current,
            ArrayList<ProgramaNetFlix> result,
            String keyword,
            double maxPopularity,
            double minScore
    ) {

        if (current != null) {

            // ESQUERDA
            collectUnderestimated(

                    current.left,
                    result,
                    keyword,
                    maxPopularity,
                    minScore
            );

            // RAIZ
            ProgramaNetFlix p = current.data;

            // APENAS FILMES
            boolean validType =

                    p.getShowType()
                            .equalsIgnoreCase("MOVIE");

            // PALAVRA-CHAVE NO TÍTULO
            boolean validKeyword =

                    p.getTitle()
                            .toLowerCase()
                            .contains(
                                    keyword.toLowerCase()
                            );

            // POPULARIDADE BAIXA
            boolean validPopularity =

                    p.getTmdbPopularity()
                            <= maxPopularity;

            // NOTA ALTA
            boolean validScore =

                    p.getImdbScore()
                            >= minScore;

            // SCORES VÁLIDOS
            boolean validValues =

                    p.getImdbScore() > 0
                    &&
                    p.getTmdbPopularity() > 0;

            if (
                    validType
                    &&
                    validKeyword
                    &&
                    validPopularity
                    &&
                    validScore
                    &&
                    validValues
            ) {

                result.add(p);
            }

            // DIREITA
            collectUnderestimated(

                    current.right,
                    result,
                    keyword,
                    maxPopularity,
                    minScore
            );
        }
    }
}