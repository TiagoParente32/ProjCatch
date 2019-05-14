package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

public class MutationInsert<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationInsert(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        int cut2;
        do {
            cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        }while (cut1==cut2);
        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }
        for(int i = cut2-1; i > cut1 ; i--) {
            int aux = ind.getGene(i + 1);
            ind.setGene(i + 1, ind.getGene(i));
            ind.setGene(i, aux);
        }

    }


    @Override
    public String toString() {
        return "Insert";
    }
}