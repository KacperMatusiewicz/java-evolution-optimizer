package dev.k2.selection;

import dev.k2.PopulationIndividual;

import java.util.List;

public class SelectionParameters<I> {

    private List<PopulationIndividual<I>> population;
    private int amountToChoose;
    private int tournamentSize;
    private int eliteSize;
    private SelectionMethod<I> eliteComplementarySelection;

    public SelectionParameters(List<PopulationIndividual<I>> population, int amountToChoose) {
        this.population = population;
        this.amountToChoose = amountToChoose;
    }

    public List<PopulationIndividual<I>> getPopulation() {
        return population;
    }

    public void setPopulation(List<PopulationIndividual<I>> population) {
        this.population = population;
    }

    public int getAmountToChoose() {
        return amountToChoose;
    }

    public void setAmountToChoose(int amountToChoose) {
        this.amountToChoose = amountToChoose;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }

    public void setTournamentSize(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    public int getEliteSize() {
        return eliteSize;
    }

    public void setEliteSize(int eliteSize) {
        this.eliteSize = eliteSize;
    }

    public SelectionMethod<I> getEliteComplementarySelection() {
        return eliteComplementarySelection;
    }

    public void setEliteComplementarySelection(SelectionMethod<I> eliteComplementarySelection) {
        this.eliteComplementarySelection = eliteComplementarySelection;
    }
}
