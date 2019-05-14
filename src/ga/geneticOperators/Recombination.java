package ga.geneticOperators;

import ga.Individual;
import ga.Population;
import ga.Problem;

import static ga.GeneticAlgorithm.random;

public abstract class Recombination <I extends Individual, P extends Problem<I>> extends GeneticOperator {

    public Recombination(double probability) {
        super(probability);
    }

    public void run(Population<I, P> population) {
        int populationSize = population.getSize();
        for (int i = 0; i < populationSize; i += 2) {
            if (random.nextDouble() < getProbability()) {
                recombine(population.getIndividual(i), population.getIndividual(i + 1));
            }
        }
    }

    public abstract void recombine(I ind1, I ind2);
}