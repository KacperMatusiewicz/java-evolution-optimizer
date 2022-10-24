package dev.k2.selection;

import dev.k2.PopulationIndividual;

import java.util.*;

public class TournamentSelection<I> implements SelectionMethod<I> {
    @Override
    public List<PopulationIndividual<I>> selectIndividuals(SelectionParameters<I> selectionParameters) {
        List<PopulationIndividual<I>> newPopulation = new ArrayList<>(selectionParameters.getAmountToChoose());
        List<PopulationIndividual<I>> tournamentPool = new ArrayList<>(selectionParameters.getTournamentSize());
        Random random = new Random();
        List<Double> generatedRandomNumbers = new ArrayList<>(selectionParameters.getTournamentSize());
        int index;
        double sum, sum2, value;
        for (int i = 0; i < selectionParameters.getAmountToChoose(); i++) {
            sum = sum2 = 0;
            tournamentPool.clear();
            generatedRandomNumbers.clear();

            for (int j = 0; j < selectionParameters.getTournamentSize() + 1; j++) {
                value = random.nextDouble();
                sum += value;
                generatedRandomNumbers.add(value);
            }
            for (int j = 0; j < selectionParameters.getTournamentSize(); j++) {
                sum2 += generatedRandomNumbers.get(j);
                index = (int) ((double)selectionParameters.getPopulation().size() * sum2 / sum);
                tournamentPool.add(selectionParameters.getPopulation().get(index % selectionParameters.getPopulation().size()));
            }

            newPopulation.add(
                    tournamentPool.stream()
                            .max(Comparator.comparingDouble(PopulationIndividual::getFitness))
                            .map(PopulationIndividual::deepClone)
                            .get()
            );

        }
        return newPopulation;
    }
    private int chooseIndexRandomly(Set<Integer> usedIndices, int bound) {
        Random random = new Random();
        int index = random.nextInt(bound);
        while(usedIndices.contains(index)){
            index = random.nextInt(bound);
        }
        return index;
    }
}
