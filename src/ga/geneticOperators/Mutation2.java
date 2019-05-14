package ga.geneticOperators;

import ga.IntVectorIndividual;
import ga.Problem;

public class Mutation2<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public Mutation2(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        //TODO
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    @Override
    public String toString() {
        //TODO
        throw new UnsupportedOperationException("Not Implemented Yet");
    }
}