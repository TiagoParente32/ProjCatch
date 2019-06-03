package ga.selectionMethods;

import ga.GeneticAlgorithm;
import ga.Individual;
import ga.Population;
import ga.Problem;

public class RouletteWheel <I extends Individual, P extends Problem<I>> extends SelectionMethod<I, P> {
    double[] accumulated;

    public RouletteWheel(int popSize) {
        super(popSize);
        accumulated = new double[popSize];
    }

    @Override
    public Population<I, P> run(Population<I, P> original) {

        Population<I, P> result = new Population<>(original.getSize());
        //alterar para rank selection :)
        //https://www.youtube.com/watch?v=QX7D-dRgtW0

        accumulated[popSize-1] = original.getIndividual(0).getFitness();
        /*for (int i = 1; i < popSize; i++) {
            accumulated[i] = accumulated[i - 1] + original.getIndividual(i).getFitness();
        }*/
        int pos = 0;
        double aux = 0;
        double total = popSize + popSize-1;
        for (int i = popSize - 2; i >= 0; i--) {
            pos = i;
            accumulated[i] = original.getIndividual(i).getFitness();
            //menor na posicao [popsize]
            while(pos != popSize-1 && accumulated[pos]< accumulated[pos+1]){
                aux = accumulated[pos+1];
                accumulated[pos+1] = accumulated[pos];
                accumulated[pos] = aux;
                pos++;
            }
            total += i;
            System.out.println();
        }


        //double fitnessSum = accumulated[popSize - 1];
        accumulated[0] = (double)((1)/total);
        for (int i = 1; i < popSize; i++) {
            accumulated[i] = (double)((i+1)/total);
            accumulated[i] += accumulated[i-1];
        }

        for (int i = 0; i < popSize; i++) {
            result.addIndividual(roulette(original));
        }

        return result;
    }

    private I roulette(Population<I, P> population) {
        double probability = GeneticAlgorithm.random.nextDouble();

        for (int i = 0; i < popSize; i++) {
            if (probability <= accumulated[i]) {
                return (I) population.getIndividual(i).clone();
            }
        }
        //For the case where all individuals have fitness 0
        return (I) population.getIndividual(GeneticAlgorithm.random.nextInt(popSize)).clone();
    }

    @Override
    public String toString(){
        return "Roulette wheel";
    }
}
