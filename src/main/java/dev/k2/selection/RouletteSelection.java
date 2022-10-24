package dev.k2.selection;

import dev.k2.PopulationIndividual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RouletteSelection<I> implements SelectionMethod<I>{
    @Override
    public List<PopulationIndividual<I>> selectIndividuals(SelectionParameters<I> selectionParameters) {

        List<PopulationIndividual<I>> newPopulation = new ArrayList<>(selectionParameters.getAmountToChoose());

        double sumOfFitnesses = selectionParameters.getPopulation().parallelStream()
                .mapToDouble(PopulationIndividual::getFitness)
                .sum();

        List<Double> probabilities = selectionParameters.getPopulation().stream()
                .map(individual -> individual.getFitness() / sumOfFitnesses)
                .collect(Collectors.toList());

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
