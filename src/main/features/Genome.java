package features;

import java.util.Arrays;
import java.util.Random;

public class Genome {
    private int[] genome;

    public Genome() {
        this.setRandomGenome();
    }

    public Genome(int[] genome) {
        Arrays.sort(genome);
        this.genome = genome;
    }

    public void setRandomGenome() {
        int[] genome = new int[32];

        // there have to be every type of gene in the genome
        for (int i=0; i<8; i++)
            genome[i] = i;

        // other genes are random
        for (int i=8; i<32; i++)
            genome[i] = new Random().nextInt(8);

        Arrays.sort(genome);
        this.genome = genome;
    }

    public int getRandomGene() {
        int index = new Random().nextInt(32);

        return this.genome[index];
    }

    public Genome crossingOver(Genome otherGenome, int divisionPointA ,int divisionPointB) {
        int[] newGenome = new int[32];
        int[] genesOccurrence = new int[8];
        for (int i=0; i<8; i++) genesOccurrence[i] = 0;

        for (int i=0; i<32; i++){
            if (i < divisionPointA)
                newGenome[i] = this.genome[i];
            else if (i < divisionPointB)
                newGenome[i] = otherGenome.genome[i];
            else
                newGenome[i] = this.genome[i];

            genesOccurrence[newGenome[i]] += 1;
        }

        // correcting genome (genome has to have all types of genes)
        for (int i=0; i<8; i++) {
            if (genesOccurrence[i] == 0) {
                int changeIndex = new Random().nextInt(32);

                while (genesOccurrence[newGenome[changeIndex]] == 0)
                    changeIndex = (changeIndex + 1) % 32;

                newGenome[changeIndex] = i;
            }
        }

        Arrays.sort(newGenome);
        return new Genome(newGenome);
    }

    public Genome crossingOver(Genome otherGenome) {
        int divisionPointA = new Random().nextInt(30) + 1; // from 1 to 30
        int divisionPointB = new Random().nextInt(
                31 - divisionPointA) + divisionPointA + 1; // from divisionPointA + 1 to 31

        return crossingOver(otherGenome, divisionPointA, divisionPointB);
    }

    public int[] getArray() {
        return this.genome;
    }
}
