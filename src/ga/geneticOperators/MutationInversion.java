package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

import java.util.Random;

public class MutationInversion<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationInversion(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        //TODO
        //fazer duas vezes
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
            //reverte os elemntos enre cut1 e cut2 inclusive
            for (int i = cut1; i < mid; i++) {
                int tmp = ind.getGene(i);
                ind.setGene(i,ind.getGene(endCount));
                ind.setGene(endCount,tmp);
                endCount--;
            }
        }
    }



    @Override
    public String toString() {
        //TODO
        return "Inversion";

    }
}