package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

public class MutationSwap<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationSwap(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        //TODO
        //pode se fazer mais q uma vez
        //for (int i = 0; i < 2; i++) {

            //indice do primeiro sitio onde substituir
            int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
            //indice do segundo sitio onde substituis
            int cut2;
            //cut2 tem de ser diferente de cut1
            do {
                cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
            }while (cut1==cut2);

            //aux para guardar o gene[cut1]
            int aux = ind.getGene(cut1);
            //meter no gene[cut1] o q esta no gene[cut2]
            ind.setGene(cut1,ind.getGene(cut2));
            //meter no gene[cut2] o aux ou seja o gene[cut1] inicial
            ind.setGene(cut2,aux);
        //}

    }

    @Override
    public String toString() {
        //TODO
        return "Swap";
    }
}