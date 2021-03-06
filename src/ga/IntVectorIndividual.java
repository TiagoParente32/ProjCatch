package ga;

import java.util.LinkedList;
import java.util.Random;

import static ga.GeneticAlgorithm.random;

public abstract class IntVectorIndividual<P extends Problem, I extends IntVectorIndividual> extends Individual<P, I> {
    //TODO this class might require the definition of additional methods and/or attributes

    protected int[] genome;

    public IntVectorIndividual(P problem, int size) {
        super(problem);
        genome = new int[size];
        //TODO
        LinkedList<Integer> usedValues = new LinkedList<>();

        //criar genoma com ordem das caixas a ser apanhadas
        for (int i = 0; i < genome.length; i++) {
            //faz um loop para gerar sempre valores diferentes
            while (true){
                int rand = random.nextInt(size);
                if(!usedValues.contains(rand)) {
                    genome[i] = rand;   
                    usedValues.add(rand);
                    break;
                }
            }
        }
      }

    public IntVectorIndividual(IntVectorIndividual<P, I> original) {
        super(original);
        this.genome = new int[original.genome.length];
        System.arraycopy(original.genome, 0, genome, 0, genome.length);
    }

    @Override
    public int getNumGenes() {
        return genome.length;
    }

    public int getIndexof(int value){
        for (int i = 0; i < genome.length; i++) {
            if (genome[i] == value)
                return i;
        }
        return -1;
    }

    public int getGene(int index) {
        return genome[index];
    }

    public void setGene(int index, int newValue) {
        genome[index] = newValue;
    }

    @Override
    public void swapGenes(IntVectorIndividual other, int index) {
        int aux = genome[index];
        genome[index] = other.genome[index];
        other.genome[index] = aux;
    }
}
