package console;

import agentSearch.Solution;
import catchBox.*;
import experiments.Experiment;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import static catchBox.CatchExperimentsFactory.pathToFiles;
import static catchBox.CatchExperimentsFactory.puzzleNumber;

public class Main {




    public static void main(String[] args) {

        CatchExperimentsFactory experimentsFactory = null;
        System.out.println("------------------------------------------------");
        try {
            if(args.length !=1){
                System.out.println("To Run the program specify config file");
                System.exit(1);
            }

            String argFileName = args[0];

            String fileName = "./Configs/" + argFileName + ".txt";

            File f = new File(fileName);
            if(!f.exists()) {
                System.out.println("[*] File " +fileName + " doesnt Exits");
                System.exit(2);
            }

            System.out.println("[*] Loading File: " +fileName);

            File directory = new File("./Configs");
            System.out.println("[*] File Loaded");
            directory.mkdir();
            experimentsFactory = new CatchExperimentsFactory(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            int[][] matrix = CatchAgentSearch.readInitialStateFromFile(new File(experimentsFactory.getFile()));
            CatchAgentSearch agentSearch = new CatchAgentSearch(new CatchState(matrix));

            LinkedList<Pair> pairs = agentSearch.getPairs();
            for (Pair p : pairs) {
                CatchState state = ((CatchState) agentSearch.getEnvironment()).clone();
                state.setGoal(p.getCell2().getLine(), p.getCell2().getColumn());
                state.setCellCatch(p.getCell1().getLine(), p.getCell1().getColumn());
                CatchProblemSearch problem = new CatchProblemSearch(state, p.getCell2());
                Solution s = agentSearch.solveProblem(problem);
                p.setValue((int) s.getCost());
            }


            System.out.println("[*] Running Tests For Puzzle: " + puzzleNumber);

            while (experimentsFactory.hasMoreExperiments()) {
                try {

                    Experiment experiment = experimentsFactory.nextExperiment(agentSearch.getInitialBox(), pairs, agentSearch.getCellCatch(), agentSearch.getDoor());
                    experiment.run();


                } catch (IOException e1) {
                    e1.printStackTrace(System.err);
                }
            }
            System.out.println("[*] Ran All Tests");
            System.out.println("[*] Output Folder: " + pathToFiles);
            System.out.println("------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

    }

}
