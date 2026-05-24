/*
PROJETO 2 - BST NETFLIX
Disciplina: Estrutura de Dados

Integrantes:
- Vitória Cardoso Alves - 10735648
- Bruno Malagoli - 10736098
*/

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Loader {

    // =====================================================
    // LEITURA DO DATASET
    // =====================================================
    public static void loadData(String filePath, BST tree) {

        try {

            // =================================================
            // ABRE O ARQUIVO CSV
            // =================================================
            CSVReader reader =
                    new CSVReader(
                            new FileReader(filePath)
                    );

            String[] line;

            // =================================================
            // PULA O CABEÇALHO
            // =================================================
            reader.readNext();

            // =================================================
            // LÊ LINHA POR LINHA
            // =================================================
            while ((line = reader.readNext()) != null) {

                // =============================================
                // VERIFICA SE EXISTEM 15 ATRIBUTOS
                // =============================================
                if (line.length < 15) {

                    continue;
                }

                // =============================================
                // VERIFICA CAMPOS VAZIOS
                // =============================================
                boolean valid = true;

                for (String field : line) {

                    if (
                            field == null
                            ||
                            field.trim().isEmpty()
                    ) {

                        valid = false;

                        break;
                    }
                }

                // =============================================
                // DESCARTA LINHAS INVÁLIDAS
                // =============================================
                if (!valid) {

                    continue;
                }

                // =============================================
                // CRIA OBJETO ProgramaNetFlix
                // =============================================
                ProgramaNetFlix program =
                        new ProgramaNetFlix(

                                // id
                                line[0],

                                // title
                                line[1],

                                // showType
                                line[2],

                                // description
                                line[3],

                                // releaseYear
                                Integer.parseInt(line[4]),

                                // ageCertification
                                line[5],

                                // runtime
                                (int) Double.parseDouble(line[6]),

                                // genres
                                line[7],

                                // productionCountries
                                line[8],

                                // seasons
                                (int) Double.parseDouble(line[9]),

                                // imdbId
                                line[10],

                                // imdbScore
                                Double.parseDouble(line[11]),

                                // imdbVotes
                                (int) Double.parseDouble(line[12]),

                                // tmdbPopularity
                                Double.parseDouble(line[13]),

                                // tmdbScore
                                Double.parseDouble(line[14])
                        );

                // =============================================
                // INSERE NA BST
                // =============================================
                tree.insert(program);
            }

            // =================================================
            // FECHA O ARQUIVO
            // =================================================
            reader.close();

            System.out.println(
                    "\nDataset carregado com sucesso!"
            );

        } catch (Exception e) {

            System.out.println(
                    "\nErro ao carregar dataset:"
            );

            e.printStackTrace();
        }
    }

    // =====================================================
    // SALVAR DADOS DA BST EM CSV
    // =====================================================
    public static void saveData(
            String filePath,
            BST tree
    ) {

        try {

            // =================================================
            // CRIA O ARQUIVO
            // =================================================
            PrintWriter writer =
                    new PrintWriter(
                            new FileWriter(filePath)
                    );

            // =================================================
            // ESCREVE O CABEÇALHO
            // =================================================
            writer.println(
                    "id,title,show_type,description,"
                    + "release_year,age_certification,"
                    + "runtime,genres,production_countries,"
                    + "seasons,imdb_id,imdb_score,"
                    + "imdb_votes,tmdb_popularity,tmdb_score"
            );

            // =================================================
            // SALVA TODOS OS DADOS DA BST
            // =================================================
            saveRecursive(
                    tree.getRoot(),
                    writer
            );

            // =================================================
            // FECHA O ARQUIVO
            // =================================================
            writer.close();

            System.out.println(
                    "\nDados salvos com sucesso!"
            );

        } catch (Exception e) {

            System.out.println(
                    "\nErro ao salvar arquivo:"
            );

            e.printStackTrace();
        }
    }

    // =====================================================
    // PERCORRE A BST E SALVA OS DADOS
    // =====================================================
    private static void saveRecursive(
            Node current,
            PrintWriter writer
    ) {

        if (current != null) {

            // =============================================
            // PERCORRE ESQUERDA
            // =============================================
            saveRecursive(
                    current.left,
                    writer
            );

            // =============================================
            // DADOS DO PROGRAMA
            // =============================================
            ProgramaNetFlix p =
                    current.data;

            // =============================================
            // ESCREVE A LINHA NO CSV
            // =============================================
            writer.println(

                    p.getId() + ","

                    + "\"" + p.getTitle() + "\"" + ","

                    + p.getShowType() + ","

                    + "\"" + p.getDescription() + "\"" + ","

                    + p.getReleaseYear() + ","

                    + p.getAgeCertification() + ","

                    + p.getRuntime() + ","

                    + "\"" + p.getGenres() + "\"" + ","

                    + "\"" + p.getProductionCountries() + "\"" + ","

                    + p.getSeasons() + ","

                    + p.getImdbId() + ","

                    + p.getImdbScore() + ","

                    + p.getImdbVotes() + ","

                    + p.getTmdbPopularity() + ","

                    + p.getTmdbScore()
            );

            // =============================================
            // PERCORRE DIREITA
            // =============================================
            saveRecursive(
                    current.right,
                    writer
            );
        }
    }
}