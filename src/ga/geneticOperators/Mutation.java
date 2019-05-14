package ga.geneticOperators;

import ga.Individual;
import ga.Population;
import ga.Problem;

import static ga.GeneticAlgorithm.random;

public abstract class Mutation <I extends Individual, P extends Problem<I>> extends GeneticOperator{
    
    public Mutation(double probability){
        super(probability);
    }

    public void run(Population<I, P> population) {
        int populationSize = population.getSize();
        for (int i = 0; i < populationSize; i++) {
            if (random.nextDouble() < getProbability()) {
                mutate(population.getIndividual(i));
            }
        }
    }

    public abstract void mutate(I individual);
}
