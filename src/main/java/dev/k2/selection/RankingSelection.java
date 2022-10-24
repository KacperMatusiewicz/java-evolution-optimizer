package dev.k2.selection;

import dev.k2.PopulationIndividual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RankingSelection<I> implements SelectionMethod<I> {
    @Override
    public List<PopulationIndividual<I>> selectIndividuals(SelectionParameters<I> selectionParameters) {

        List<PopulationIndividual<I>> newPopulation = new ArrayList<>(selectionParameters.getAmountToChoose());

        int sumOfRankings = (1 + selectionParameters.getPopulation().size()) * selectionParameters.getPopulation().size() / 2;

        List<Double> probabilities = new ArrayList<>(selectionParameters.getPopulation().size());

        for (int i = 1; i <= selectionParameters.getPopulation().size() ; i++) {
            probabilities.add((double) i / (double) sumOfRankings);
        }

        Random random = new Random();
        int index;
        double randomValue;

        for (int i = 0; i < selectionParameters.getAmountToChoose(); i++) {
            index = 0;
            randomValue = random.nextDouble();
            while(randomValue > 0) {
                randomValue -= probabilities.get(index % probabilities.size());
                index++;
            }

            newPopulation.add(
                    selectionParameters
                            .getPopulation()
                            .get(index % probabilities.size())
                            .deepClone()
            );
        }
        return newPopulation;
    }
}
