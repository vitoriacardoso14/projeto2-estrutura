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
  
        Comparator

                // ORDENA PELO IMDB SCORE
                .comparingDouble(
                        ProgramaNetFlix::getImdbScore
                )

                // MAIOR SCORE PRIMEIRO
                .reversed()

                // DESEMPATE:
                // MAIOR QUANTIDADE DE VOTOS
                .thenComparing(
                        Comparator.comparingInt(
                                ProgramaNetFlix::getImdbVotes
                        ).reversed()
                )
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

    // ====================================================================
    // ANÁLISE 2 - Títulos mais recomendados para assistir (Pós-ordem)
    // ====================================================================
    public static void top10Recommended(
        BST tree,
        String genre,
        String type,
        String ageCert,
        double minTmdbPop)
    {// LISTA QUE ARMAZENARÁ OS TÍTULOS FILTRADOS
        ArrayList<ProgramaNetFlix> result = new ArrayList<>();

        // CHAMA O MÉTODO QUE PERCORRE A BST EM PÓS-ORDEM
        collectRecommended(
            tree.getRoot(),
            result,
            genre,
            type,
            ageCert,
            minTmdbPop
        );

    // =========================================================
    // ORDENAÇÃO DOS RESULTADOS
    // =========================================================
    // 1º critério:
    // Maior imdb_score

    // 2º critério (desempate):
    // Maior quantidade de votos

        result.sort(

            Comparator
                    .comparingDouble(
                            ProgramaNetFlix::getImdbScore
                    )

                    // ORDEM DECRESCENTE
                    .reversed()

                    // DESEMPATE POR QUANTIDADE DE VOTOS
                    .thenComparing(

                            Comparator
                                    .comparingInt(
                                            ProgramaNetFlix::getImdbVotes
                                    )

                                    .reversed()
                    )
        );

        // TÍTULO DA ANÁLISE
        System.out.println("\n=== TOP 10 TÍTULOS MAIS RECOMENDADOS ===\n");

        // DEFINE LIMITE MÁXIMO
        int limit = Math.min(10, result.size());

        // CASO NENHUM RESULTADO SEJA ENCONTRADO
        if (limit == 0) {

        System.out.println(
                "Nenhum título encontrado com os filtros selecionados."
        );

        return;
        }

        // =========================================================
        // EXIBE OS RESULTADOS
        // =========================================================
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

                + "\nClassificação: "
                + p.getAgeCertification()

                + "\n"
            );
        }
    }

    // ====================================================================
    // PERCURSO PÓS-ORDEM
    // (Esquerda → Direita → Raiz)
    // ====================================================================
    private static void collectRecommended(
            Node current,
            ArrayList<ProgramaNetFlix> result,
            String genre,
            String type,
            String ageCert,
            double minTmdbPop)
    {

        // VERIFICA SE O NÓ EXISTE
        if (current != null) {

            // =====================================================
            // 1. VISITA A SUBÁRVORE ESQUERDA
            // =====================================================
            collectRecommended(
                    current.left,
                    result,
                    genre,
                    type,
                    ageCert,
                    minTmdbPop
            );

            // =====================================================
            // 2. VISITA A SUBÁRVORE DIREITA
            // =====================================================
            collectRecommended(
                    current.right,
                    result,
                    genre,
                    type,
                    ageCert,
                    minTmdbPop
            );

            // =====================================================
            // 3. PROCESSA O NÓ ATUAL
            // =====================================================

            // PEGA O PROGRAMA DO NÓ
            ProgramaNetFlix p = current.data;

            // =====================================================
            // FILTRO 1 - GÊNERO
            // =====================================================
            boolean validGenre =

                    p.getGenres()
                            .toLowerCase()
                            .contains(
                                    genre.toLowerCase()
                            );

            // =====================================================
            // FILTRO 2 - TIPO
            // =====================================================
            boolean validType =

                    p.getShowType()
                            .equalsIgnoreCase(type);

            // =====================================================
            // FILTRO 3 - CLASSIFICAÇÃO INDICATIVA
            // =====================================================
            boolean validAge =

                    p.getAgeCertification()
                            .equalsIgnoreCase(ageCert);

            // =====================================================
            // FILTRO 4 - POPULARIDADE MÍNIMA
            // =====================================================
            boolean validPopularity =

                    p.getTmdbPopularity()
                            >= minTmdbPop;

            // =====================================================
            // SE TODOS OS FILTROS FOREM VERDADEIROS
            // =====================================================
            if (

                    validGenre
                    &&
                    validType
                    &&
                    validAge
                    &&
                    validPopularity
            ) {

                // ADICIONA O TÍTULO NA LISTA
                result.add(p);
            }
        }
    }

}