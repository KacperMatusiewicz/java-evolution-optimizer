package dev.k2.selection;


import dev.k2.PopulationIndividual;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ElitismSelection<I> implements SelectionMethod<I> {
    @Override
    public List<PopulationIndividual<I>> selectIndividuals(SelectionParameters<I> selectionParameters) {

        List<PopulationIndividual<I>> newGeneration = selectionParameters
                .getPopulation().stream()
                .sorted(Comparator.comparingDouble(PopulationIndividual::getFitness))
                .skip(selectionParameters.getPopulation().size() - selectionParameters.getEliteSize())
                .map(PopulationIndividual::deepClone)
                .collect(Collectors.toList());

        SelectionParameters<I> secondarySelectionParameters = selectionParameters;

        secondarySelectionParameters.setAmountToChoose(
                selectionParameters.getAmountToChoose()-selectionParameters.getEliteSize()
        );

        newGeneration.addAll(
                selectionParameters
                        .getEliteComplementarySelection()
                        .selectIndividuals(secondarySelectionParameters)
        );

        return newGeneration;
    }
}
