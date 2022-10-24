package dev.k2.selection;

import dev.k2.PopulationIndividual;

import java.util.List;

public interface SelectionMethod<I> {
    List<PopulationIndividual<I>> selectIndividuals(SelectionParameters<I> selectionParameters);
}
