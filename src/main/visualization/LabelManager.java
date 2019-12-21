package visualization;

import utilities.Statistics;

public class LabelManager {
    private String text;

    private Statistics statsA;
    private Statistics statsB;

    public LabelManager(Statistics statisticsA, Statistics statisticsB) {
        this.statsA = statisticsA;
        this.statsB = statisticsB;
    }

    public void updateLabel(MenuState menuState) {
        switch(menuState) {
            case STATISTICS:
                this.prepareStatistics();
                break;
            case GENOME:
                this.prepareDominantGenome();
                break;
            case FOLLOWING:
                break;
        }
    }

    public String getText() {
        return this.text;
    }

    private void prepareStatistics() {
        // the same day for both maps
        int day = this.statsA.getCurrentDay();

        this.text = String.format("Statistics                map A          map B \n \n" +
                        "Day                                 %d \n \n" +
                        "Animals                  %d                 %d  \n" +
                        "Plants                     %d              %d \n" +
                        "Average Energy      %d               %d \n" +
                        "Average Lifetime    %d              %d \n" +
                        "Average Children    %g       %g \n",
                day, this.statsA.getAnimals(), this.statsB.getAnimals(),
                this.statsA.getGrasses(), this.statsB.getGrasses(),
                this.statsA.getAverageEnergy(), this.statsB.getAverageEnergy(),
                this.statsA.getAverageLifetime(), this.statsB.getAverageLifetime(),
                this.statsA.getAverageChildren(), this.statsB.getAverageChildren());
    }

    private void prepareDominantGenome() {
        StringBuilder genomeAFirstLine = new StringBuilder();
        StringBuilder genomeASecondLine = new StringBuilder();
        StringBuilder genomeBFirstLine = new StringBuilder();
        StringBuilder genomeBSecondLine = new StringBuilder();

        int[] dominantGenomeArrayA;
        int[] dominantGenomeArrayB;

        if (this.statsA.getDominantGenome() != null) {
            dominantGenomeArrayA = this.statsA.getDominantGenome().getArray();

            for (int i = 0; i < 16; i++) {
                genomeAFirstLine.append(dominantGenomeArrayA[i]).append(" ");
                genomeASecondLine.append(dominantGenomeArrayA[i + 16]).append(" ");
            }
        }

        if (this.statsB.getDominantGenome() != null) {
            dominantGenomeArrayB = this.statsB.getDominantGenome().getArray();

            for (int i = 0; i < 16; i++) {
                genomeBFirstLine.append(dominantGenomeArrayB[i]).append(" ");
                genomeBSecondLine.append(dominantGenomeArrayB[i + 16]).append(" ");
            }
        }

        this.text = "                     Dominant Genomes \n \n" +
                "                               map A \n              ";

        if (this.statsA.getDominantGenome() != null)
            this.text += genomeAFirstLine.toString() + "\n              " +
                    genomeASecondLine.toString() + "\n \n";
        else
            this.text += "     All animals have died \n \n";

        this.text += "                               map B \n              ";

        if (this.statsB.getDominantGenome() != null)
            this.text += genomeBFirstLine.toString() + "\n              " +
                    genomeBSecondLine.toString() + "\n \n";
        else
            this.text += "     All animals have died";
    }
}
