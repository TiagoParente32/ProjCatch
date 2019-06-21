package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

import java.util.Random;

public class MutationScramble<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {
    public MutationScramble(double probability) {
        super(probability);
    }

    @Override
    //scramble method
    public void mutate(I ind) {
        //TODO
        //pode se fazer em mais q uma vez
        //for (int k = 0; k < 2; k++) {
            int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
            int cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());;
            while(cut1>=cut2){
                cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
                cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
            }
            //randomiza numsBetweenCuts vezes,um valor entre cut1 e cut2;feito por mim; originalmente estava 10
            int numsBetweenCuts = cut2-cut1;
            for (int i = 0; i < numsBetweenCuts; i++) {
                int i1 = GeneticAlgorithm.random.nextInt((cut2 - cut1)+1) + cut1;
                int i2 = GeneticAlgorithm.random.nextInt((cut2 - cut1)+1) + cut1;

                int aux = ind.getGene(i1);
                ind.setGene(i1,ind.getGene(i2));
                ind.setGene(i2,aux);
            }
        //}
    }

    @Override
    public String toString() {
        //TODO
        return "Scramble";
    }
}
