package dev.k2;

import dev.k2.selection.ElitismSelection;
import dev.k2.selection.RouletteSelection;
import dev.k2.selection.SelectionMethod;
import dev.k2.selection.TournamentSelection;

public class Configuration<I> {

    //basic mandatory parameters
    private final int populationSize;
    private final int generationAmountLimit;
    private int threadPoolSize;

    //selection

    private SelectionMethod<I> selectionMethod;
    //tournament
    private int tournamentSize;
    //elitism
    private int eliteSize;
    private SelectionMethod<I> eliteComplementarySelectionMethod;
    //crossover

    private double crossoverProbability;
    //mutation

    private double mutationProbability;
    private double mutationStrength;
    private Configuration(int populationSize, int generationAmountLimit) {
        this.populationSize = populationSize;
        this.generationAmountLimit = generationAmountLimit;
        this.threadPoolSize = 8;
    }

    public static <II> Configuration<II> create(Class<II> cls, int populationSize, int generationAmountLimit) {
        return new Configuration<II>(populationSize, generationAmountLimit)
                .withSelectionMethod(new RouletteSelection<II>())
                .withCrossoverProbability(0.7)
                .withMutationProbability(0.01)
                .withMutationStrength(1.0);
    }

    //threads
    public Configuration<I> withThreadPoolSize(int threadPoolSize){
        this.threadPoolSize = threadPoolSize;
        return this;
    }

    // selection

    public Configuration<I> withSelectionMethod(SelectionMethod<I> selectionMethod){
        this.selectionMethod = selectionMethod;
        if (selectionMethod instanceof TournamentSelection) {
            this.tournamentSize = 4;
        }
        if (selectionMethod instanceof ElitismSelection) {
            this.eliteSize = 2;
            this.eliteComplementarySelectionMethod = new RouletteSelection<>();
        }
        return this;
    }
    //tournament
    public Configuration<I> withTournamentSize(int tournamentSize){
        this.tournamentSize = tournamentSize;
        return this;
    }
    //elitism
    public Configuration<I> withEliteSize(int eliteSize){
        this.eliteSize = eliteSize;
        return this;
    }
    public Configuration<I> withElitismComplementaryMethod(SelectionMethod<I> elitismComplementarySelectionMethod){
        this.eliteComplementarySelectionMethod = elitismComplementarySelectionMethod;
        return this;
    }
    //crossover

    public Configuration<I> withCrossoverProbability(double crossoverProbability){
        this.crossoverProbability = crossoverProbability;
        return this;
    }
    //mutation

    public Configuration<I> withMutationStrength(double mutationStrength){
        this.mutationStrength = mutationStrength;
        return this;
    }
    public Configuration<I> withMutationProbability(double mutationProbability){
        this.mutationProbability = mutationProbability;
        return this;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getGenerationAmountLimit() {
        return generationAmountLimit;
    }

    public SelectionMethod<I> getSelectionMethod() {
        return selectionMethod;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }

    public int getEliteSize() {
        return eliteSize;
    }

    public SelectionMethod<I> getEliteComplementarySelectionMethod() {
        return eliteComplementarySelectionMethod;
    }

    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public double getMutationStrength() {
        return mutationStrength;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }
}
