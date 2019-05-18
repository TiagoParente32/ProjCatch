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
        for (int k = 0; k < 5; k++) {
            int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
            int cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());;
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
    ////scramble method talvez substituia para experimentar
    /*@Override
    public void mutate(I ind) {
        //TODO

        for (int k = 0; k < 5; k++) {
            int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
            int cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());;
            while(cut1>=cut2){
                cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
                cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
            }
            //this code inverts (i.e. reverses) elements between r1..r2 inclusive
            Random r1 = new Random();
            for (int i = 0; i < 10; i++) {
                //r.nextInt((max - min) + 1) + min;
                int i1 = r1.nextInt((cut2 - cut1)+1) + cut1;
                int i2 = r1.nextInt((cut2 - cut1)+1) + cut1;

                int aux = ind.getGene(i1);
                ind.setGene(i1,ind.getGene(i2));
                ind.setGene(i2,aux);
            }
        }
        //throw new UnsupportedOperationException("Not Implemented Yet");
    }*/


    @Override
    public String toString() {
        //TODO
        return "Inversion";
        //throw new UnsupportedOperationException("Not Implemented Yet");
    }
}