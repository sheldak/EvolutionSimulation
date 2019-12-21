package visualization;

import mapElements.Animal;
import utilities.Statistics;

public class LabelManager {
    // class manages text which will be displayed on the label
    private String text;

    // there is one labelManager object for both worlds
    private Statistics statsA;
    private Statistics statsB;

    public LabelManager(Statistics statisticsA, Statistics statisticsB) {
        // getting statistics objects
        this.statsA = statisticsA;
        this.statsB = statisticsB;
    }

    public void updateLabel(MenuState menuState) {
        // updating label text depending on current menu state
        switch(menuState) {
            case STATISTICS:
                this.prepareStatistics();
                break;
            case GENOME:
                this.prepareDominantGenome();
                break;
            case FOLLOWING:
                this.prepareFollowingAnimal();
                break;
        }
    }

    // getter
    public String getText() {
        return this.text;
    }

    private void prepareStatistics() {
        // preparing daily statistics for both maps and saving it to one string

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
        // preparing display of dominant genomes for both maps

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
                    genomeBSecondLine.toString();
        else
            this.text += "     All animals have died";
    }

    public void prepareFollowingAnimal() {
        // preparing statistics for the followed animal

        Animal followedAnimal = null;
        Statistics usedStats = null;

        // there is always at most 1 followed animal (it can bo just on 1 map, the other map has followedAnimal == null)
        if (this.statsA.getFollowedAnimal() != null) {
            followedAnimal = this.statsA.getFollowedAnimal();
            usedStats = this.statsA;
        }

        if (this.statsB.getFollowedAnimal() != null) {
            followedAnimal = this.statsB.getFollowedAnimal();
            usedStats = this.statsB;
        }

        if (followedAnimal == null || usedStats == null) {
            this.text = "Click on the animal to follow it";
        }
        else {
            StringBuilder genomeFirstLine = new StringBuilder();
            StringBuilder genomeSecondLine = new StringBuilder();

            int[] genomeArray = followedAnimal.getGenomeArray();

            for (int i = 0; i < 16; i++) {
                genomeFirstLine.append(genomeArray[i]).append(" ");
                genomeSecondLine.append(genomeArray[i + 16]).append(" ");
            }

            this.text = String.format("                       Following animal \n" +
                            "Day       %d \n" +
                            "Following ends at day %d \n" +
                            "Position (x,y)   %d  %d  \n" +
                            "Children       %d \n" +
                            "Offspring      %d \n" +
                            "Genome  %s \n" +
                            "               %s \n",
                    usedStats.getCurrentDay(),
                    usedStats.getFollowingEndTime(),
                    usedStats.getFollowedAnimalPosition().getX(), usedStats.getFollowedAnimalPosition().getY(),
                    usedStats.getFollowedAnimalChildren(),
                    usedStats.getFollowedAnimalOffspring(),
                    genomeFirstLine.toString(), genomeSecondLine.toString());

            if(!followedAnimal.isDead())
                this.text += "Animal is alive";
            else
                this.text += String.format("Day of death: %d", + followedAnimal.getAge());
        }
    }
}
