package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

import java.util.Random;

public class Mutation2<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public Mutation2(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        //TODO
        //com k<2 demora mais iteraçoes a chegar a um resultado (melhor) do q se fizesse isto apenas uma vez
        for (int k = 0; k < 2; k++) {
            //2 cortes random
            int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
            int cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());;
            //garantir q c1 < c2
            while(cut1>=cut2){
                cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
                cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
            }

            int mid = cut1 + ((cut2 + 1) - cut1) / 2;
            int endCount = cut2;
            //this code inverts (i.e. reverses) elements between r1..r2 inclusive
            for (int i = cut1; i < mid; i++) {
                //temp = gene[i]
                int tmp = ind.getGene(i);
                //array[i] = array[endCount];
                ind.setGene(i,ind.getGene(endCount));
                //array[endCount] = tmp;
                ind.setGene(endCount,tmp);
                endCount--;
            }
        }
        //throw new UnsupportedOperationException("Not Implemented Yet");
    }



    @Override
    public String toString() {
        //TODO
        return "Inversion";
        //throw new UnsupportedOperationException("Not Implemented Yet");
    }
}