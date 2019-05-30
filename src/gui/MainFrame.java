package gui;

import agentSearch.Solution;
import catchBox.*;
import experiments.Experiment;
import experiments.ExperimentEvent;
import ga.GAEvent;
import ga.GAListener;
import ga.GeneticAlgorithm;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class MainFrame extends JFrame implements GAListener {

    private static final long serialVersionUID = 1L;
    private CatchProblemForGA problemGA;
    private CatchState state;
    private CatchAgentSearch agentSearch;
    private Solution solution;
    private GeneticAlgorithm<CatchIndividual, CatchProblemForGA> ga;
    private CatchIndividual bestInRun;
    private CatchExperimentsFactory experimentsFactory;
    private PanelTextArea problemPanel;
    PanelTextArea bestIndividualPanel;

    public int[][] matrix;

    private JButton buttonDataSet = new JButton("Data set");
    private JButton buttonRunGA = new JButton("GA");
    private JButton buttonRunSearch = new JButton("Search1");
    private JButton buttonStop = new JButton("Stop");
    private JButton buttonRunSearch2 = new JButton("Search2");
    private JButton buttonExperiments = new JButton("Experiments");
    private JButton buttonRunExperiments = new JButton("Run experiments");
    private PanelParameters panelParameters = new PanelParameters(this);
    private JTextField textFieldExperimentsStatus = new JTextField("", 10);
    private XYSeries seriesBestIndividual;
    private XYSeries seriesAverage;
    private SwingWorker<Void, Void> worker;

    private PanelSimulation simulationPanel;

    public MainFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private void jbInit() throws Exception {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("CATCH");

        //North Left Panel
        JPanel panelNorthLeft = new JPanel(new BorderLayout());
        panelNorthLeft.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));

        panelNorthLeft.add(getPanelParameters(), BorderLayout.WEST);
        JPanel panelButtons = new JPanel();

        panelButtons.add(buttonDataSet);
        buttonDataSet.setEnabled(true);
        buttonDataSet.addActionListener(new ButtonDataSet_actionAdapter(this));


        panelButtons.add(buttonRunSearch);
        buttonRunSearch.setEnabled(false);
        buttonRunSearch.addActionListener(new ButtonRunSearch_actionAdapter(this));

        panelButtons.add(buttonRunGA);
        buttonRunGA.setEnabled(false);
        buttonRunGA.addActionListener(new ButtonRunGA_actionAdapter(this));

        panelButtons.add(buttonStop);
        buttonStop.setEnabled(false);
        buttonStop.addActionListener(new ButtonStop_actionAdapter(this));

        panelButtons.add(buttonRunSearch2);
        buttonRunSearch2.setEnabled(false);
        buttonRunSearch2.addActionListener(new ButtonRunSearch2_actionAdapter(this));

        panelNorthLeft.add(panelButtons, BorderLayout.SOUTH);

        //North Right Panel - Chart creation
        seriesBestIndividual = new XYSeries("Best");
        seriesAverage = new XYSeries("Average");

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesBestIndividual);
        dataset.addSeries(seriesAverage);
        JFreeChart chart = ChartFactory.createXYLineChart("Evolution", // Title
                "generation", // x-axis Label
                "fitness", // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(400, 200));

        //North Panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(panelNorthLeft, BorderLayout.WEST);
        northPanel.add(chartPanel, BorderLayout.CENTER);

        //Center panel       
        problemPanel = new PanelTextArea("Problem data: ", 15, 40);
        bestIndividualPanel = new PanelTextArea("Best solution: ", 15, 40);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(problemPanel, BorderLayout.WEST);
        centerPanel.add(bestIndividualPanel, BorderLayout.CENTER);

        //South Panel
        JPanel southPanel = new JPanel();
        southPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));

        southPanel.add(buttonExperiments);
        buttonExperiments.setEnabled(true);
        buttonExperiments.addActionListener(new ButtonExperiments_actionAdapter(this));
        southPanel.add(buttonRunExperiments);
        buttonRunExperiments.setEnabled(false);
        buttonRunExperiments.addActionListener(new ButtonRunExperiments_actionAdapter(this));
        southPanel.add(new JLabel("Status: "));
        southPanel.add(textFieldExperimentsStatus);
        textFieldExperimentsStatus.setEditable(false);

        //Big left panel
        JPanel bigLeftPanel = new JPanel(new BorderLayout());
        bigLeftPanel.add(northPanel, BorderLayout.NORTH);
        bigLeftPanel.add(centerPanel, BorderLayout.CENTER);
        bigLeftPanel.add(southPanel, BorderLayout.SOUTH);
        this.getContentPane().add(bigLeftPanel);

        simulationPanel = new PanelSimulation(this);

        simulationPanel.setJButtonSimulateEnabled(false);
        //Global structure
        JPanel globalPanel = new JPanel(new BorderLayout());
        globalPanel.add(bigLeftPanel, BorderLayout.WEST);
        globalPanel.add(simulationPanel, BorderLayout.EAST);
        this.getContentPane().add(globalPanel);

        pack();
    }

    public void cleanBoards() {
        problemPanel.textArea.setText("");
        bestIndividualPanel.textArea.setText("");
        seriesBestIndividual.clear();
        seriesAverage.clear();

    }

    public void buttonDataSet_actionPerformed(ActionEvent e) {

        JFileChooser fc = new JFileChooser(new File("."));
        int returnVal = fc.showOpenDialog(this);

        try {
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File dataSet = fc.getSelectedFile();
                this.matrix = CatchAgentSearch.readInitialStateFromFile(dataSet);
                state = new CatchState(matrix);
                agentSearch = new CatchAgentSearch(new CatchState(matrix));
                problemPanel.textArea.setText(agentSearch.getEnvironment().toString());
                bestIndividualPanel.textArea.setText("");
                bestInRun = null;
                manageButtons(true, false, true, false, false, true, false, false);

                simulationPanel.createEnvironment();
            }
        } catch (IOException e1) {
            e1.printStackTrace(System.err);
        } catch (java.util.NoSuchElementException e2) {
            JOptionPane.showMessageDialog(this, "File format not valid", "Error!", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void jButtonRunGA_actionPerformed(ActionEvent e) {
        try {
            if (problemGA == null) {
                JOptionPane.showMessageDialog(this, "You must first choose a problem", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            bestIndividualPanel.textArea.setText("");
            seriesBestIndividual.clear();
            seriesAverage.clear();
            manageButtons(false, false, false, true, true, true, false, false);
            Random random = new Random(Integer.parseInt(getPanelParameters().textFieldSeed.getText()));
            ga = new GeneticAlgorithm<>(
                    Integer.parseInt(getPanelParameters().textFieldN.getText()),
                    Integer.parseInt(getPanelParameters().textFieldGenerations.getText()),
                    getPanelParameters().getSelectionMethod(),
                    getPanelParameters().getRecombinationMethod(),
                    getPanelParameters().getMutationMethod(),
                    random);

            ga.addGAListener(this);


            worker = new SwingWorker<Void, Void>() {
                @Override
                public Void doInBackground() {
                    try {

                        bestInRun = ga.run(problemGA);

                    } catch (Exception e) {
                        e.printStackTrace(System.err);
                    }
                    return null;
                }

                @Override
                public void done() {
                    agentSearch = new CatchAgentSearch(state.clone());
                    manageButtons(false, true, false, false, true, true, false, false);
                }
            };

            worker.execute();

        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(this, "Wrong parameters!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void jButtonRunSearch_actionPerformed(ActionEvent e) {
        try {
            if (agentSearch.getEnvironment() == null) {
                JOptionPane.showMessageDialog(this, "You must first choose a problem", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }


            bestIndividualPanel.textArea.setText("");
            seriesBestIndividual.clear();
            seriesAverage.clear();


            manageButtons(false, false, false, false, false, true, false, false);

            worker = new SwingWorker<Void, Void>() {
                @Override
                public Void doInBackground() {
                    try {
                        LinkedList<Pair> pairs = agentSearch.getPairs();
                        for (Pair p : pairs) {
                            CatchState state = ((CatchState) agentSearch.getEnvironment()).clone();
                            state.setGoal(p.getCell2().getLine(), p.getCell2().getColumn());
                            state.setCellCatch(p.getCell1().getLine(), p.getCell1().getColumn());
                            CatchProblemSearch problem = new CatchProblemSearch(state, p.getCell2());
                            Solution s = agentSearch.solveProblem(problem);
                            p.setValue((int) s.getCost());
                        }
                        problemGA = new CatchProblemForGA(agentSearch.getInitialBox(), pairs, agentSearch.getCellCatch(), agentSearch.getDoor());


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public void done() {
                    problemPanel.textArea.setText(agentSearch.toString());
                    manageButtons(false, true, false, false, false, true, false, false);
                }
            };

            worker.execute();

        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(this, "Wrong parameters!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void jButtonRunSearch2_actionPerformed(ActionEvent e) {
        try {
            if (bestInRun == null) {
                JOptionPane.showMessageDialog(this, "You must first obtain a solution", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            manageButtons(false, false, false, false, false, true, false, false);

            worker = new SwingWorker<Void, Void>() {
                @Override
                public Void doInBackground() {
                    try {
                        CatchState auxState = state.clone();
                        Solution auxSolution = null;
                        solution = null;
                        int genome[] = bestInRun.getGenome();
                        for (int i = 0; i <= genome.length; i++) {
                            Cell cell;
                            if (i != genome.length)
                                //alteramos o get(genome[i]-1) pq as nossas caixas começao em 0
                                cell = (Cell) agentSearch.getInitialBox().get(genome[i]);
                            else
                                cell = agentSearch.getDoor();

                            //alteramos aqui para dar update ao goal que queremos chegar
                            auxState.setGoal(cell.getLine(),cell.getColumn());

                            CatchProblemSearch problem = new CatchProblemSearch(auxState, cell);
                            auxSolution = agentSearch.solveProblem(problem);

                            if (auxSolution == null) {
                                solution = null;
                                break;
                            }
                            if ((i != genome.length))
                                //talvez faça um outro metodo para ignorar as caixas pelas quais ja passou
                                auxState = (CatchState) agentSearch.executeSolution();
                            if (i == 0)
                                solution = auxSolution;
                            else
                                solution.updateActions(auxSolution.getActions());

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    agentSearch.setSolution(solution);

                    return null;
                }

                @Override
                public void done() {
                    bestIndividualPanel.textArea.append(agentSearch.getSearchReport());
                    problemPanel.textArea.setText("");
                    manageButtons(true, false, false, false, false, true, false, true);

                }
            };

            worker.execute();

        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(this, "Wrong parameters!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    public void generationEnded(GAEvent e) {
        GeneticAlgorithm<CatchIndividual, CatchProblemForGA> source = e.getSource();
        bestIndividualPanel.textArea.setText(source.getBestInRun().toString());
        seriesBestIndividual.add(source.getGeneration(), source.getBestInRun().getFitness());
        seriesAverage.add(source.getGeneration(), source.getAverageFitness());
        if (worker.isCancelled()) {
            e.setStopped(true);
        }
    }

    @Override
    public void runEnded(GAEvent e) {
    }

    public void jButtonStop_actionPerformed(ActionEvent e) {
        worker.cancel(true);
    }

    public void buttonExperiments_actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser(new File("."));
        int returnVal = fc.showOpenDialog(this);

        try {
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                experimentsFactory = new CatchExperimentsFactory(fc.getSelectedFile());

                manageButtons(true, problemGA != null, false, false, false, true, true, false);

                if (experimentsFactory.getFile()==null) {
                    experimentsFactory=null;
                    throw  new java.util.NoSuchElementException();
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace(System.err);
            manageButtons(true, problemGA != null, false, false, false, true, false, false);
        } catch (java.util.NoSuchElementException e2) {
            JOptionPane.showMessageDialog(this, "File format not valid", "Error!", JOptionPane.ERROR_MESSAGE);
            manageButtons(true, problemGA != null, false, false, false, true, false, false);
        }
    }

    public void buttonRunExperiments_actionPerformed(ActionEvent e) {


        manageButtons(false, false, false, false, false, false, false, false);
        textFieldExperimentsStatus.setText("Running");

        worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
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

                    while (experimentsFactory.hasMoreExperiments()) {
                        try {

                            Experiment experiment = experimentsFactory.nextExperiment(agentSearch.getInitialBox(), pairs, agentSearch.getCellCatch(), agentSearch.getDoor());
                            experiment.run();

                        } catch (IOException e1) {
                            e1.printStackTrace(System.err);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
                return null;
            }

            @Override
            public void done() {
                manageButtons(true, problemGA != null, false, false, false, true, false, false);
                textFieldExperimentsStatus.setText("Finished");
            }
        };
        worker.execute();
    }

    @Override
    public void experimentEnded(ExperimentEvent e) {
    }

    public void manageButtons(
            boolean dataSet,
            boolean runGA,
            boolean runSearch,
            boolean stopRun,
            boolean runSearch2,
            boolean experiments,
            boolean runExperiments,
            boolean runEnvironment) {
        buttonDataSet.setEnabled(dataSet);
        buttonRunGA.setEnabled(runGA);
        buttonRunSearch.setEnabled(runSearch);
        buttonStop.setEnabled(stopRun);
        buttonRunSearch2.setEnabled(runSearch2);
        buttonExperiments.setEnabled(experiments);
        buttonRunExperiments.setEnabled(runExperiments);
        if (simulationPanel != null)
            simulationPanel.setJButtonSimulateEnabled(runEnvironment);
    }

    public PanelParameters getPanelParameters() {
        return panelParameters;
    }


    public CatchAgentSearch getAgentSearch() {
        return agentSearch;
    }

    public void setAgentSearch(CatchAgentSearch agentSearch) {
        this.agentSearch = agentSearch;
    }


    public CatchState getCatchState() {
        return state;
    }
}

class PanelTextArea extends JPanel {

    JTextArea textArea;

    public PanelTextArea(String title, int rows, int columns) {
        textArea = new JTextArea(rows, columns);
        setLayout(new BorderLayout());
        add(new JLabel(title), BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        add(scrollPane);
    }
}

class ButtonDataSet_actionAdapter implements ActionListener {

    final private MainFrame adaptee;

    ButtonDataSet_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.buttonDataSet_actionPerformed(e);
    }
}

class ButtonRunGA_actionAdapter implements ActionListener {

    final private MainFrame adaptee;

    ButtonRunGA_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonRunGA_actionPerformed(e);
    }
}

class ButtonRunSearch_actionAdapter implements ActionListener {

    final private MainFrame adaptee;

    ButtonRunSearch_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonRunSearch_actionPerformed(e);
    }
}

class ButtonRunSearch2_actionAdapter implements ActionListener {

    final private MainFrame adaptee;

    ButtonRunSearch2_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonRunSearch2_actionPerformed(e);
    }
}


class ButtonStop_actionAdapter implements ActionListener {

    final private MainFrame adaptee;

    ButtonStop_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonStop_actionPerformed(e);
    }
}

class ButtonExperiments_actionAdapter implements ActionListener {

    final private MainFrame adaptee;

    ButtonExperiments_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.buttonExperiments_actionPerformed(e);
    }
}

class ButtonRunExperiments_actionAdapter implements ActionListener {

    final private MainFrame adaptee;

    ButtonRunExperiments_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.buttonRunExperiments_actionPerformed(e);
    }
}
