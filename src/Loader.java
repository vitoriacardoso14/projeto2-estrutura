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

import com.opencsv.CSVWriter;

public class Loader {

    public static void loadData(String filePath, BST tree) {

        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));
            String[] line;
            reader.readNext(); // Pula o cabeçalho

            int filmesSalvos = 0; // Contador só para você ver que funcionou

            while ((line = reader.readNext()) != null) {

                if (line.length < 15) {
                    continue;
                }

                // =============================================
                // O GRANDE TRUQUE PARA SALVAR OS FILMES
                // Se for MOVIE, a coluna 9 (seasons) vem vazia. 
                // Colocamos "0" para não ser descartado!
                // =============================================
                if (line[2].equalsIgnoreCase("MOVIE") && (line[9] == null || line[9].trim().isEmpty())) {
                    line[9] = "0"; 
                }

                boolean valid = true;

                for (String field : line) {
                    if (field == null || field.trim().isEmpty()) {
                        valid = false;
                        break;
                    }
                }

                if (!valid) {
                    continue;
                }

                ProgramaNetFlix program = new ProgramaNetFlix(
                        line[0], line[1], line[2], line[3],
                        Integer.parseInt(line[4]), line[5],
                        (int) Double.parseDouble(line[6]), line[7],
                        line[8], (int) Double.parseDouble(line[9]),
                        line[10], Double.parseDouble(line[11]),
                        (int) Double.parseDouble(line[12]),
                        Double.parseDouble(line[13]),
                        Double.parseDouble(line[14])
                );

                tree.insert(program);
                if (line[2].equalsIgnoreCase("MOVIE")) filmesSalvos++;
            }

            reader.close();
            System.out.println("\nDataset carregado com sucesso!");
            System.out.println("Filmes recuperados com o ajuste de temporada nula: " + filmesSalvos);

        } catch (Exception e) {
            System.out.println("\nErro ao carregar dataset:");
            e.printStackTrace();
        }
    }

        public static void saveData(String filePath, BST tree) {
        try {
                CSVWriter writer = new CSVWriter(new FileWriter(filePath));

                String[] header = {
                        "id",
                        "title",
                        "show_type",
                        "description",
                        "release_year",
                        "age_certification",
                        "runtime",
                        "genres",
                        "production_countries",
                        "seasons",
                        "imdb_id",
                        "imdb_score",
                        "imdb_votes",
                        "tmdb_popularity",
                        "tmdb_score"
                };

                writer.writeNext(header);

                saveRecursive(tree.getRoot(), writer);

                writer.close();

                System.out.println("\nDados salvos com sucesso!");

        } catch (Exception e) {
                System.out.println("\nErro ao salvar arquivo:");
                e.printStackTrace();
        }
        }

        private static void saveRecursive(Node current, CSVWriter writer) {
        if (current != null) {
                saveRecursive(current.left, writer);

                ProgramaNetFlix p = current.data;

                String[] line = {
                        p.getId(),
                        p.getTitle(),
                        p.getShowType(),
                        p.getDescription(),
                        String.valueOf(p.getReleaseYear()),
                        p.getAgeCertification(),
                        String.valueOf(p.getRuntime()),
                        p.getGenres(),
                        p.getProductionCountries(),
                        String.valueOf(p.getSeasons()),
                        p.getImdbId(),
                        String.valueOf(p.getImdbScore()),
                        String.valueOf(p.getImdbVotes()),
                        String.valueOf(p.getTmdbPopularity()),
                        String.valueOf(p.getTmdbScore())
                };

                writer.writeNext(line);

                saveRecursive(current.right, writer);
                }
        }
}