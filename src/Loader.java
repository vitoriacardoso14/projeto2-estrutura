import com.opencsv.CSVReader;

import java.io.FileReader;

public class Loader {

    public static void loadData(String filePath, BST tree) {

        try {

            CSVReader reader = new CSVReader(new FileReader(filePath));

            String[] line;

            // Pula o cabeçalho
            reader.readNext();

            while ((line = reader.readNext()) != null) {

                // Verifica se existem os 15 atributos
                if (line.length < 15) {
                    continue;
                }

                boolean valid = true;

                // Verifica campos vazios
                for (String field : line) {

                    if (field == null || field.trim().isEmpty()) {

                        valid = false;
                        break;
                    }
                }

                // Descarta linhas inválidas
                if (!valid) {
                    continue;
                }

                ProgramaNetFlix program = new ProgramaNetFlix(

                        line[0], // id
                        line[1], // title
                        line[2], // showType
                        line[3], // description
                        Integer.parseInt(line[4]), // releaseYear
                        line[5], // ageCertification
                        (int) Double.parseDouble(line[6]), // runtime
                        line[7], // genres
                        line[8], // productionCountries
                        (int) Double.parseDouble(line[9]), // seasons
                        line[10], // imdbId
                        Double.parseDouble(line[11]), // imdbScore
                        (int) Double.parseDouble(line[12]), // imdbVotes
                        Double.parseDouble(line[13]), // tmdbPopularity
                        Double.parseDouble(line[14]) // tmdbScore
                );

                // Insere na BST
                tree.insert(program);
            }

            reader.close();

            System.out.println("Dataset carregado com sucesso!");

        } catch (Exception e) {

            System.out.println("Erro ao carregar dataset:");
            e.printStackTrace();
        }
    }
}