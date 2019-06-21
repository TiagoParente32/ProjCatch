package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

import java.util.ArrayList;

public class RecombinationCX<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    //TODO this class might require the definition of additional methods and/or attributes
    //CX

    public RecombinationCX(double probability) {
        super(probability);
    }

    private int indLenght;
    private int[] offspring1;
    private int[] offspring2;

    @Override
    public void recombine(I ind1, I ind2) {
        indLenght = ind1.getNumGenes();
        offspring1 = new int[ind1.getNumGenes()];
        offspring2 = new int[ind2.getNumGenes()];
        for(int index = 0; index < offspring1.length; index++){
            offspring1[index] = -1;
            offspring2[index] = -1;
        }
        crossOver(offspring1, ind1, ind2);
        crossOver(offspring2, ind2, ind1);

        for (int i = 0; i < ind1.getNumGenes(); i++) {
            ind1.setGene(i, offspring1[i]);
            ind2.setGene(i, offspring2[i]);
        }
    }


    // (1 x x 5 ) ex.elemento q estamos a procurar é o 5 no primeiro progenitor depois de 1 corresponder ao 5 ..
    // (5 x x x )  // a posicao no primeiro progenitor é a posicao 3

    private int getPosition_ofSecondParentElement_infirstParent(I firstParent, int element_toSearch){
        int position = 0;
        for(int index = 0; index < indLenght; index++){
            if(firstParent.getGene(index) == element_toSearch){
                position = index;
                break;
            }
        }
        return position;
    }

    // (1 x x 5 ) eg. elemento q estamos a procurar é 1, depois procura lo no segundo progenitor
    // (5 x x 1 )  1 ja foi preenchido logo return true

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
    }    
}