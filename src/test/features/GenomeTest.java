package features;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GenomeTest {

    @Test
    void testSetRandomGenome() {
        Genome genome = new Genome();
        int[] genomeArray = genome.getArray();

        assertTrue(genomeArray[0] >= 0 && genomeArray[0] <= 7);
        for (int i=1; i<32; i++) {
            assertTrue(genomeArray[i] >= 0 && genomeArray[i] <= 7);
            assertTrue(genomeArray[i] >= genomeArray[i-1]);
        }

        int[] genesOccurrence = new int[8];
        for (int i=0; i<32; i++) {
            genesOccurrence[genomeArray[i]] += 1;
        }

        for (int i=0; i<8; i++) {
            assertTrue(genesOccurrence[i] > 0);
        }
    }

    @Test
    void testGetRandomGene() {
        Genome genome = new Genome();
        int gene = genome.getRandomGene();

        int[] genomeArray = genome.getArray();

        boolean geneInArray = false;
        for (int i=0; i<32; i++) {
            if (genomeArray[i] == gene){
                geneInArray = true;
                break;
            }
        }
        assertTrue(geneInArray);
    }

    @Test
    void testCrossingOver() {
        int[] genomeArrayA =
                {0, 0, 0, 0, 0, 1, 1, 2, 3, 3, 3  , 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 7, 7, 7, 7};
        int[] genomeArrayB =
                {0, 0, 0, 1, 1, 1, 2, 2, 2, 2, 2  , 3, 4, 4, 4, 4, 4, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7};

        Genome genomeA = new Genome(genomeArrayA);
        Genome genomeB = new Genome(genomeArrayB);

        int divisionPointA = 10;
        int divisionPointB = 25;

        Genome genomeC = genomeA.crossingOver(genomeB, divisionPointA, divisionPointB);

        int [] genomeArrayC = genomeC.getArray();

        int [] genomeArrayCExpected =
                {0, 0, 0, 0, 0, 1, 1, 2, 3, 3, 2  , 3, 4, 4, 4, 4, 4, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7};
        Arrays.sort(genomeArrayCExpected);

        assertArrayEquals(genomeArrayCExpected, genomeArrayC);
    }
}
