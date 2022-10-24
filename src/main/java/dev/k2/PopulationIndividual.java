package dev.k2;

import java.util.function.Function;

public class PopulationIndividual<I> {
    private I individual;
    private double fitness;
    private final Function<I,I> individualCloner;
    private boolean calculated;
    public PopulationIndividual(I individual, double fitness, Function<I, I> individualCloner) {
        this.individual = individual;
        this.fitness = fitness;
        this.individualCloner = individualCloner;
        this.calculated = false;
    }
    public PopulationIndividual(I individual, double fitness, boolean calculated, Function<I, I> individualCloner) {
        this.individual = individual;
        this.fitness = fitness;
        this.individualCloner = individualCloner;
        this.calculated = calculated;
    }

    public I getIndividual() {
        return individual;
    }

    public void setIndividual(I individual) {
        calculated = false;
        this.individual = individual;
    }
    public void setIndividualAsUnchanged(I individual) {
        this.individual = individual;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.calculated = true;
        this.fitness = fitness;
    }

    public boolean isCalculated() {
        return calculated;
    }

    public PopulationIndividual<I> deepClone() {
        I individual = individualCloner.apply(this.individual);
        return new PopulationIndividual<>(
                individual,
                this.fitness,
                calculated,
                this.individualCloner
        );
    }

}
