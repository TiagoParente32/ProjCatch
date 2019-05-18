package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

import java.util.ArrayList;

public class Recombination2<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    //TODO this class might require the definition of additional methods and/or attributes
    //CX

    public Recombination2(double probability) {
        super(probability);
    }

    private int[] parent1;
    private int[] parent2;
    private int[] offspring1;
    private int[] offspring2;

    @Override
    public void recombine(I ind1, I ind2) {
        this.parent1 = new int[ind1.getNumGenes()];
        this.parent2 = new int[ind2.getNumGenes()];

        for(int index = 0; index < parent1.length; index ++){
            this.parent1[index] = ind1.getGene(index);
            this.parent2[index] = ind2.getGene(index);
        }
        offspring1 = new int[ind1.getNumGenes()];
        offspring2 = new int[ind2.getNumGenes()];
        for(int index = 0; index < offspring1.length; index++){
            offspring1[index] = -1;
            offspring2[index] = -1;
        }
        crossOver(offspring1, ind1, ind2);
        crossOver(offspring2, ind2, ind1);
//        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    // (1 x x 5 ) eg. element to search is 5 in 1st parent after 1 matches to 5..
    // (5 x x x )  // its position in parent 1 is 3.

    private int getPosition_ofSecondParentElement_infirstParent
            (I firstParent, int element_toSearch){
        int position = 0;
        for(int index = 0; index < parent1.length; index++){
            if(firstParent.getGene(index) == element_toSearch){
                position = index;
                break;
            }
        }
        return position;
    }

    // (1 x x 5 ) eg. element to search is 1, after look for it in 2nd parent.
    // (5 x x 1 )  // 1 has already been filled so return true.

    private boolean element_already_inOffspring(int [] offspring, int element){
        for(int index = 0; index < offspring.length; index++){
            if(offspring[index] == element){
                return true;
            }
        }
        return false;
    }

    public void crossOver(int [] offspring, I ind1, I ind2){
        int index = 0;
        while(!element_already_inOffspring(offspring, ind2.getGene(index))){
            offspring[index] = ind1.getGene(index);
            int position = getPosition_ofSecondParentElement_infirstParent
                    (ind1, ind2.getGene(index));
            offspring[position] = ind2.getGene(index);
            index = position;
        }

        for(int offspring_index = 0; offspring_index < offspring.length; offspring_index++){
            if(offspring[offspring_index] == -1){
                offspring[offspring_index] = ind2.getGene(offspring_index);
            }
        }
    }

    @Override
    public String toString(){
        //TODO
        return "CX";
        //throw new UnsupportedOperationException("CX");
    }    
}