package dev.k2;

public class Result<I> {
    private final I individual;
    private final double fitnessValue;

    public Result(I individual, double fitnessValue) {
        this.individual = individual;
        this.fitnessValue = fitnessValue;
    }

    public Result(PopulationIndividual<I> individual) {
        this.individual = individual.getIndividual();
        this.fitnessValue = individual.getFitness();
    }

    public I getIndividual() {
        return individual;
    }

    public double getFitnessValue() {
        return fitnessValue;
    }

    @Override
    public String toString() {
        return individual.toString() + ", and fitness = " + getFitnessValue();
    }
}
