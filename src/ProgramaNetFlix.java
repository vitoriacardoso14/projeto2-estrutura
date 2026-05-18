public class ProgramaNetFlix {

    // ATRIBUTOS
    private String id;
    private String title;
    private String showType;
    private String description;
    private int releaseYear;
    private String ageCertification;
    private int runtime;
    private String genres;
    private String productionCountries;
    private int seasons;
    private String imdbId;
    private double imdbScore;
    private int imdbVotes;
    private double tmdbPopularity;
    private double tmdbScore;

    // CONSTRUTOR
    public ProgramaNetFlix(
            String id,
            String title,
            String showType,
            String description,
            int releaseYear,
            String ageCertification,
            int runtime,
            String genres,
            String productionCountries,
            int seasons,
            String imdbId,
            double imdbScore,
            int imdbVotes,
            double tmdbPopularity,
            double tmdbScore
    ) {

        this.id = id;
        this.title = title;
        this.showType = showType;
        this.description = description;
        this.releaseYear = releaseYear;
        this.ageCertification = ageCertification;
        this.runtime = runtime;
        this.genres = genres;
        this.productionCountries = productionCountries;
        this.seasons = seasons;
        this.imdbId = imdbId;
        this.imdbScore = imdbScore;
        this.imdbVotes = imdbVotes;
        this.tmdbPopularity = tmdbPopularity;
        this.tmdbScore = tmdbScore;
    }

    // GETTERS

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShowType() {
        return showType;
    }

    public String getDescription() {
        return description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getAgeCertification() {
        return ageCertification;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getGenres() {
        return genres;
    }

    public String getProductionCountries() {
        return productionCountries;
    }

    public int getSeasons() {
        return seasons;
    }

    public String getImdbId() {
        return imdbId;
    }

    public double getImdbScore() {
        return imdbScore;
    }

    public int getImdbVotes() {
        return imdbVotes;
    }

    public double getTmdbPopularity() {
        return tmdbPopularity;
    }

    public double getTmdbScore() {
        return tmdbScore;
    }

    // SETTERS

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImdbScore(double imdbScore) {
        this.imdbScore = imdbScore;
    }

    // EXIBIÇÃO

    @Override
    public String toString() {

        return
                "\nID: " + id +
                "\nTítulo: " + title +
                "\nTipo: " + showType +
                "\nAno: " + releaseYear +
                "\nClassificação: " + ageCertification +
                "\nIMDB Score: " + imdbScore +
                "\nTMDB Score: " + tmdbScore +
                "\nPopularidade TMDB: " + tmdbPopularity +
                "\n";
    }
}