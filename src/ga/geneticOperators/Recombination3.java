package ga.geneticOperators;

import ga.IntVectorIndividual;
import ga.Problem;

import java.util.ArrayList;
import java.util.Random;

import static ga.GeneticAlgorithm.random;

public class Recombination3<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    //TODO this class might require the definition of additional methods and/or attributes

    public Recombination3(double probability) {
        super(probability);
    }

    private int[] parent1;
    private int[] parent2;
    private int[] offspring1;
    private int[] offspring2;
    private int cutPoint1;
    private int cutPoint2;


    private ArrayList<Integer> outerSegmentBuildArray;

    @Override
    public void recombine(I ind1, I ind2) {
        //TODO
        outerSegmentBuildArray = new ArrayList<Integer>();
        this.parent1 = new int[ind1.getNumGenes()];
        this.parent2 = new int[ind1.getNumGenes()];

        for(int index = 0; index < ind1.getNumGenes(); index ++){
            this.parent1[index] = ind1.getGene(index);
            this.parent2[index] = ind2.getGene(index);
        }

        offspring1 = new int[ind1.getNumGenes()];
        offspring2 = new int[ind1.getNumGenes()];
        // Generate Random cut points, must be unique from each other //
        // cutPoint2 should be greater than cutPoint1 //
        int length = ind1.getNumGenes() - 1;
        cutPoint1 = random.nextInt(length);
        cutPoint2 = random.nextInt(length);

        while(cutPoint2 == cutPoint1){
            cutPoint2 = random.nextInt(length);
        }

        if(cutPoint1 > cutPoint2){
            int temporary = cutPoint1;
            cutPoint1 = cutPoint2;
            cutPoint2 = temporary;
        }

        crossOver(offspring1, ind1, ind2);
        crossOver(offspring2, ind2, ind1);

        //throw new UnsupportedOperationException("Not Implemented Yet");
    }

    private void remove_SpecifiedElement(int elementToRemove){
        for(int index = 0; index< outerSegmentBuildArray.size(); index++){
            if(outerSegmentBuildArray.get(index) == elementToRemove){
                outerSegmentBuildArray.remove(index);
                break;
            }
        }
    }

    public void crossOver(int [] offspring, I ind1, I ind2){
        int tempIndex = 0;
        int index = cutPoint2 + 1;
        // if index - cutPoint2 + 1  == ind1.length
        // add all ind1 elements directly to  outerSegmentBuildArray ArrayList.
        if(index == ind1.getNumGenes()) { // e.g. (1 2 3 | 4 5 6 7 8| )
            for(int x = 0; x < ind1.getNumGenes(); x++){
                outerSegmentBuildArray.add(ind1.getGene(x));
            }
        }

        // Else block here concatenates segments in the following order 3rd then (1 and 2)
        // outerSegmentBuildArray
        else {
            for(index = cutPoint2 + 1; index < ind1.getNumGenes(); index++){
                outerSegmentBuildArray.add(tempIndex, ind1.getGene(index));
                tempIndex++;
            }
            for(index = 0; index <= cutPoint2; index++){
                outerSegmentBuildArray.add(tempIndex, ind1.getGene(index));
                tempIndex++;
            }

        }


        for(int indexInSegment = cutPoint1; indexInSegment <=cutPoint2; indexInSegment++){
            // for ArrayList temp remove elements that appear in ind2 mid segments
            remove_SpecifiedElement(ind2.getGene(indexInSegment));
        }

        for(int x = cutPoint1; x <= cutPoint2; x++){
            // copy mid segment from parent designated as Y,
            // into offspring to be created.
            offspring[x] = ind2.getGene(x);
        }


        // Belows section copies remaining elements in temp into offspring
        // starting from 3rd segment of offspring.
        tempIndex = 0;
        for(int y = cutPoint2 + 1; y < offspring.length; y++){
            if(y == offspring.length){ break; }
            offspring[y] = outerSegmentBuildArray.get(tempIndex);
            tempIndex++;
        }

        // after end of offspring reach, copy elements from temp haven't been copied
        // into offspring from 1st segment.
        for(int z = 0; z < cutPoint1; z++){
            if(z == offspring.length){ break; }
            offspring[z] = outerSegmentBuildArray.get(tempIndex);
            tempIndex++;
        }
    }

    @Override
    public String toString(){
        //TODO
        return "OX";
        //throw new UnsupportedOperationException("Not Implemented Yet");
    }    
}