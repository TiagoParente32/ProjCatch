package catchBox;

import ga.IntVectorIndividual;

import java.util.LinkedList;

public class CatchIndividual extends IntVectorIndividual<CatchProblemForGA, CatchIndividual> {

    public CatchIndividual(CatchProblemForGA problem, int size) {
        super(problem, size);
    }

    public CatchIndividual(CatchIndividual original) {
        super(original);
    }

    @Override
    public double computeFitness() {
        //
        double fitness = Double.POSITIVE_INFINITY;
        this.fitness = fitness;
        double distance = 0;

        //lista de pares
        LinkedList<Pair> pairs = problem.getPairs();
        LinkedList<Cell> boxes = problem.getCellsBoxes();
        //celula onde começamos
        Cell cellCatch = problem.getCellCatch();

        //corre o genoma todoo para ver ordem de caixas a precorrer
        for (int i = 0; i < genome.length; i++) {
            //vai buscar a box ao genoma
            Cell proximaCell = boxes.get(genome[i]);
            Pair parAProcurar = new Pair(cellCatch, proximaCell);
            Pair parAProcurarInvertido = new Pair(proximaCell,cellCatch);
            //se nao existir o par na lista de pares
            int index;
            if(!pairs.contains(parAProcurar) && !pairs.contains(parAProcurarInvertido)){
                //retorna infinito
                return fitness;
            }
            if(pairs.contains(parAProcurar)){
                index = pairs.indexOf(parAProcurar);
            }else{
                index = pairs.indexOf(parAProcurarInvertido);
            }
            //indice do par da lista pares
            distance += pairs.get(index).getValue();
            //atualizamos o cellcatch para a posicao proximaCell;
            cellCatch = proximaCell;

            System.out.println();
        }
        Pair ultimoBoxAPorta= new Pair(cellCatch,problem.getDoor());

        if(!pairs.contains(ultimoBoxAPorta)){
            //retorna infinito
            return fitness;
        }
        //se tudo correu bem calcula a distancia da ultima caixa a porta
        int index = pairs.indexOf(ultimoBoxAPorta);
        distance += pairs.get(index).getValue();

        fitness = distance;

        this.fitness = fitness;

        return fitness;
        //throw new UnsupportedOperationException("TODONot Implemented Yet");
    }

    public int[] getGenome() {
        return genome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("fitness: ");
        sb.append(fitness);
        sb.append("\npath: ");
        for (int i = 0; i <genome.length ; i++) {
            sb.append(genome[i]).append(" ");
        }
        return sb.toString();
    }

    /**
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(CatchIndividual i) {
        return (this.fitness == i.getFitness()) ? 0 : (this.fitness < i.getFitness()) ? 1 : -1;
    }


    @Override
    public CatchIndividual clone() {
        return new CatchIndividual(this);

    }
}
