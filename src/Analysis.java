import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

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

    // ====================================================================
    // ANÁLISE 3 - Títulos mais populares por país e classificação
    // PERCURSO: EM LARGURA (Nível por Nível usando Fila)
    // ====================================================================
    public static void popularByCountry(
            BST tree,
            String country,
            String ageCert
    ) {

        // LISTA QUE ARMAZENARÁ OS TÍTULOS FILTRADOS
        ArrayList<ProgramaNetFlix> result = new ArrayList<>();

        // VERIFICA SE A ÁRVORE ESTÁ VAZIA ANTES DE COMEÇAR
        if (tree.getRoot() == null) {
            System.out.println("A árvore está vazia. Carregue os dados primeiro.");
            return;
        }

        // =====================================================
        // PREPARAÇÃO PARA O PERCURSO EM LARGURA
        // =====================================================
        Queue<Node> queue = new LinkedList<>();
        
        // ADICIONA A RAIZ COMO PONTO DE PARTIDA NA FILA
        queue.add(tree.getRoot());

        // ENQUANTO A FILA NÃO ESTIVER VAZIA, CONTINUA O PERCURSO
        while (!queue.isEmpty()) {

            // RETIRA O PRIMEIRO NÓ DA FILA
            Node current = queue.poll();

            // PEGA O PROGRAMA DO NÓ ATUAL
            ProgramaNetFlix p = current.data;

            // =====================================================
            // FILTRO 1 - PAÍS DE PRODUÇÃO
            // =====================================================
            boolean validCountry =

                    p.getProductionCountries()
                            .toUpperCase()
                            .contains(country.toUpperCase()
                            );

            // =====================================================
            // FILTRO 2 - CLASSIFICAÇÃO INDICATIVA
            // =====================================================
            boolean validAge =

                    p.getAgeCertification()
                            .equalsIgnoreCase(ageCert);

            // =====================================================
            // SE PASSAR NOS FILTROS, ADICIONA NA LISTA
            // =====================================================
            if (
                    validCountry
                    &&
                    validAge
            ) {
                result.add(p);
            }

            // =====================================================
            // ADICIONA OS FILHOS NA FILA (Para serem lidos depois)
            // =====================================================
            if (current.left != null) {
                queue.add(current.left);
            }

            if (current.right != null) {
                queue.add(current.right);
            }
        }

        // =========================================================
        // ORDENAÇÃO DOS RESULTADOS
        // =========================================================
        // Critério único: Ordem decrescente de Popularidade (TMDB)
        result.sort(

                Comparator
                        .comparingDouble(
                                ProgramaNetFlix::getTmdbPopularity
                        )
                        
                        // ORDEM DECRESCENTE (Mais populares primeiro)
                        .reversed()
        );

        // =========================================================
        // EXIBE OS RESULTADOS
        // =========================================================
        System.out.println("\n=== MAIS POPULARES: " + country.toUpperCase() + " (" + ageCert.toUpperCase() + ") ===\n");

        // DEFINE LIMITE MÁXIMO DE 10 PARA NÃO POLUIR A TELA
        int limit = Math.min(10, result.size());

        if (limit == 0) {
            
            System.out.println(
                    "Nenhum título encontrado para este país com essa classificação."
            );
            
            return;
        }

        for (int i = 0; i < limit; i++) {

            ProgramaNetFlix p = result.get(i);

            System.out.println(

                    (i + 1) + ". "

                    + p.getTitle()

                    + "\nPaíses: "
                    + p.getProductionCountries()

                    + "\nClassificação: "
                    + p.getAgeCertification()

                    + "\nTMDB Popularity: "
                    + p.getTmdbPopularity()

                    + "\n"
            );
        }
    }

    // ====================================================================
    // ANÁLISE 4 - Divergência entre avaliações IMDB e TMDB em séries
    // PERCURSO: PRÉ-ORDEM (Raiz -> Esquerda -> Direita)
    // ====================================================================
    
    public static void seriesDivergence(
            BST tree,
            int minSeasons,
            int releaseYear
    ) {

        ArrayList<ProgramaNetFlix> result = new ArrayList<>();

        collectPreOrder(tree.getRoot(), result, minSeasons, releaseYear);

        if (result.isEmpty()) {
            System.out.println("Nenhuma série encontrada com esses critérios.");
            return;
        }

        // =========================================================
        // CÁLCULO DA MÉDIA GERAL (Antes de cortar para o Top 10)
        // =========================================================
        double somaDivergenciasGeral = 0;
        for (ProgramaNetFlix p : result) {
            somaDivergenciasGeral += Math.abs(p.getImdbScore() - p.getTmdbScore());
        }
        double mediaGeral = somaDivergenciasGeral / result.size();

        // =========================================================
        // ORDENAÇÃO: Maior diferença absoluta entre as notas
        // =========================================================
        result.sort((p1, p2) -> {
            
            double diff1 = Math.abs(p1.getImdbScore() - p1.getTmdbScore());
            double diff2 = Math.abs(p2.getImdbScore() - p2.getTmdbScore());
            
            // Ordem decrescente (maior divergência primeiro)
            return Double.compare(diff2, diff1);
        });

        System.out.println("\n=== SÉRIES COM MAIOR DIVERGÊNCIA DE NOTAS ===\n");
        
        int limit = Math.min(10, result.size());

        for (int i = 0; i < limit; i++) {
            
            ProgramaNetFlix p = result.get(i);
            
            double diff = Math.abs(p.getImdbScore() - p.getTmdbScore());
            
            System.out.printf("%d. %s (Ano: %d, Temp: %d)\n", (i+1), p.getTitle(), p.getReleaseYear(), p.getSeasons());
            System.out.printf("   IMDB: %.1f | TMDB: %.1f | Diferença: %.2f\n\n", p.getImdbScore(), p.getTmdbScore(), diff);
        }

        System.out.printf("--- Média GERAL de divergência (%d séries analisadas): %.2f ---\n", result.size(), mediaGeral);
    }

    // ====================================================================
    // PERCURSO PRÉ-ORDEM
    // ====================================================================
    private static void collectPreOrder(
            Node current,
            ArrayList<ProgramaNetFlix> result,
            int minSeasons,
            int releaseYear
    ) {

        if (current != null) {

            // 1. PROCESSA A RAIZ (Nó atual)
            ProgramaNetFlix p = current.data;

            if (
                    p.getShowType().equalsIgnoreCase("SHOW") 
                    && p.getSeasons() >= minSeasons 
                    && p.getReleaseYear() >= releaseYear 
                    && p.getTmdbScore() > 0 
            ) {
                result.add(p);
            }
            
            // 2. DESCER PARA A ESQUERDA
            collectPreOrder(current.left, result, minSeasons, releaseYear);
            
            // 3. DESCER PARA A DIREITA
            collectPreOrder(current.right, result, minSeasons, releaseYear);
        }
    }

}