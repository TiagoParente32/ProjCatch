package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

import java.util.Random;

public class Mutation4<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {
    public Mutation4(double probability) {
        super(probability);
    }

    @Override
    ////scramble method
    public void mutate(I ind) {
        //TODO
        //for (int k = 0; k < 2; k++) {
            int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
            int cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());;
            while(cut1>=cut2){
                cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
                cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
            }
            //this code scrambles (i.e. randomises) elements between cut1..cut2
            //Random r1 = new Random();
            //randomiza numsBetweenCuts vezes,um valor entre cut1 e cut2;feito por mim; originalmente estava 10
            int numsBetweenCuts = cut2-cut1;
            for (int i = 0; i < numsBetweenCuts; i++) {
                //r.nextInt((max - min) + 1) + min;
                int i1 = GeneticAlgorithm.random.nextInt((cut2 - cut1)+1) + cut1;
                int i2 = GeneticAlgorithm.random.nextInt((cut2 - cut1)+1) + cut1;

                int aux = ind.getGene(i1);
                ind.setGene(i1,ind.getGene(i2));
                ind.setGene(i2,aux);
            }
        //}
        //throw new UnsupportedOperationException("Not Implemented Yet");
    }

    @Override
    public String toString() {
        //TODO
        return "Scramble";
    }
}
