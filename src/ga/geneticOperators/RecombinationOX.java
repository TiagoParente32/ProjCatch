package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

import java.util.ArrayList;
import java.util.Random;

import static ga.GeneticAlgorithm.random;

public class RecombinationOX<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    //TODO this class might require the definition of additional methods and/or attributes

    public RecombinationOX(double probability) {
        super(probability);
    }

    private int[] offspring1;
    private int[] offspring2;
    private int cutPoint1;
    private int cutPoint2;


    private ArrayList<Integer> outerSegmentBuildArray;

    @Override
    public void recombine(I ind1, I ind2) {
        //TODO
        outerSegmentBuildArray = new ArrayList<Integer>();


        offspring1 = new int[ind1.getNumGenes()];
        offspring2 = new int[ind1.getNumGenes()];
        // 2 random points que devem ser diferentes
        // cutPoint2 deve ser maior que cutPoint1 //
        cutPoint2 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes() - 1);
        cutPoint1 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes() - 1);

        while(cutPoint2 == cutPoint1){
            cutPoint2 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes() - 1);
        }

        if(cutPoint1 > cutPoint2){
            int temporary = cutPoint1;
            cutPoint1 = cutPoint2;
            cutPoint2 = temporary;
        }

        crossOver(offspring1, ind1, ind2);
        crossOver(offspring2, ind2, ind1);

        for (int i = 0; i < ind1.getNumGenes(); i++) {
            ind1.setGene(i, offspring1[i]);
            ind2.setGene(i, offspring2[i]);
        }

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
        // add todos os elementos do ind1 diretamento para o outerSegmentBuildArray ArrayList.
        if(index == ind1.getNumGenes()) { // e.g. (1 2 3 | 4 5 6 7 8| )
            for(int x = 0; x < ind1.getNumGenes(); x++){
                outerSegmentBuildArray.add(ind1.getGene(x));
            }
        }
        //else concatenar os segmentos na ordem 3 depois 1 depois 2
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
            // for ArrayList temp remover elementos que aparecem no segmento central do individuo2
            remove_SpecifiedElement(ind2.getGene(indexInSegment));
        }

        for(int x = cutPoint1; x <= cutPoint2; x++){
            //copiar o segmento central do progenitor designado como Y
            // para o offstring a ser criado.
            offspring[x] = ind2.getGene(x);
        }

        // a secao abaixo copia os elementos restantes em temp para o offspring
        // começando do terceiro segmento do offspring.
        tempIndex = 0;
        for(int y = cutPoint2 + 1; y < offspring.length; y++){
            if(y == offspring.length){ break; }
            offspring[y] = outerSegmentBuildArray.get(tempIndex);
            tempIndex++;
        }

        //depois de chegar ao fim do offspring, copiar elemntos do temp q ainda n foram copiados
        // para o primeiro segmento do offspring.
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
    }    
}