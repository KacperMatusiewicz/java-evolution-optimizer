package dev.k2;

import java.util.List;
import java.util.function.Function;

public interface IndividualService<I> {
    double calculateFitness(I a);
    I createRandom();
    List<I> performCrossover(I a, I b);
    I mutate(I a, double mutationStrength, double mutationProbability);
    Function<I, I> deepCloneFactory();
    boolean isMutationOccurred();
}
