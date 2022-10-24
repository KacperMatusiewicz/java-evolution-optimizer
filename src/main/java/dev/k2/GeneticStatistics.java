package dev.k2;

import java.util.List;

public class GeneticStatistics<I> {
    private final List<Result<I>> generationBests;
    private final List<Double> generationAverageFitnesses;

    public GeneticStatistics(List<Result<I>> generationBests, List<Double> generationAverageFitnesses) {
        this.generationBests = generationBests;
        this.generationAverageFitnesses = generationAverageFitnesses;
    }

    public List<Result<I>> getGenerationBests() {
        return generationBests;
    }

    public List<Double> getGenerationAverageFitnesses() {
        return generationAverageFitnesses;
    }
}
