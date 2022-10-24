package dev.k2;

import dev.k2.selection.SelectionParameters;

import java.util.*;

public class Population<I> {

    private final Configuration<I> configuration;
    private final IndividualService<I> service;

    private List<PopulationIndividual<I>> population;
    private List<Result<I>> generationBests;
    private List<Double> generationAverageFitnesses;

    public Population(Configuration<I> configuration, IndividualService<I> service) {
        this.configuration = configuration;
        this.service = service;
    }

    public GeneticStatistics<I> solve() {
        init();
        for (int i = 1; i < configuration.getGenerationAmountLimit(); i++) {
            System.out.println("Generation Number: " + i);
            selectIndividuals();
            performCrossover();
            performMutation();
            calculateFitness();
            updateResults();
        }
        return new GeneticStatistics<>(
                generationBests,
                generationAverageFitnesses
        );
    }

    private void init() {

        population = new ArrayList<>(configuration.getPopulationSize());
        generationBests = new ArrayList<>(configuration.getGenerationAmountLimit());
        generationAverageFitnesses = new ArrayList<>(configuration.getGenerationAmountLimit());
        System.out.println("Generating population: ");
        String p = "";
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            p += "|";
            I ind = service.createRandom();
            double fitness = service.calculateFitness(ind);
            population.add(new PopulationIndividual<>(ind, fitness, true, service.deepCloneFactory()));
            System.out.print(p + "" + ( (i * 100) / (configuration.getPopulationSize() )) +"%|>\r");
        }
        System.out.println(p + "" + 100 +"%|>\n");
        updateResults();
    }

    private void selectIndividuals() {
        SelectionParameters<I> selectionParameters = new SelectionParameters<>(
                population, configuration.getPopulationSize()
        );
        selectionParameters.setTournamentSize(configuration.getTournamentSize());
        selectionParameters.setEliteSize(configuration.getEliteSize());
        selectionParameters.setEliteComplementarySelection(configuration.getEliteComplementarySelectionMethod());
        population = configuration.getSelectionMethod().selectIndividuals(selectionParameters);
    }

    private void performCrossover() {
        String p= "";
        System.out.println("Performing crossover: ");
        Set<Integer> processedIndividualsIndices = new HashSet<>(configuration.getPopulationSize());
        List<PopulationIndividual<I>> newGeneration = new ArrayList<>(configuration.getPopulationSize());
        Random random = new Random();
        int indexA, indexB;
        double fitnessChildA, fitnessChildB;

        for (int i = 0; i < configuration.getPopulationSize()/2; i++){

            indexA = chooseMateIndexRandomly(processedIndividualsIndices);
            processedIndividualsIndices.add(indexA);
            indexB = chooseMateIndexRandomly(processedIndividualsIndices);
            processedIndividualsIndices.add(indexB);

            PopulationIndividual<I> individualA = population.get(indexA);
            PopulationIndividual<I> individualB = population.get(indexB);

            if(random.nextDouble() < configuration.getCrossoverProbability()){
                List<I> children;
                children = service.performCrossover(individualA.getIndividual(), individualB.getIndividual());
                fitnessChildA = service.calculateFitness(children.get(0));
                fitnessChildB = service.calculateFitness(children.get(1));
                if (fitnessChildA > individualA.getFitness()) {
                    individualA.setIndividual(children.get(0));
                    individualA.setFitness(fitnessChildA);
                }
                if (fitnessChildB > individualB.getFitness()){
                    individualB.setIndividual(children.get(1));
                    individualA.setFitness(fitnessChildB);
                }
                p += "||";
            } else {
                p += "..";
            }
            newGeneration.add(individualA);
            newGeneration.add(individualB);
            System.out.print(p + "" + ((i * 100) / (configuration.getPopulationSize())) +"%|>\r");
        }
        System.out.println(p + "" + 100 +"%|>\n");
        population = newGeneration;
    }

    private int chooseMateIndexRandomly(Set<Integer> processedIndices) {
        Random random = new Random();
        int partnerIndex = random.nextInt(configuration.getPopulationSize());
        while(processedIndices.contains(partnerIndex)){
            partnerIndex = random.nextInt(configuration.getPopulationSize());
        }
        return partnerIndex;
    }

    private void performMutation() {
        PopulationIndividual<I> individual;
        I ind;
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            individual = population.get(i);
            ind = service.mutate(
                    individual.getIndividual(),
                    configuration.getMutationStrength(),
                    configuration.getMutationProbability()
            );

            if(service.isMutationOccurred()){
                individual.setIndividual(ind);
            } else {
                individual.setIndividualAsUnchanged(ind);
            }
        }
    }

    private void calculateFitness() {
        /*
        population.parallelStream().forEach(
                individual -> individual.setFitness(
                            service.calculateFitness(individual.getIndividual())
                )
        );
        */
        String p= "";
        System.out.println("Calculating fitness function:");
        for (int i = 0; i < population.size(); i++){

            if(!population.get(i).isCalculated()){
                p += "|";
                population.get(i).setFitness(service.calculateFitness(population.get(i).getIndividual()));
            } else {
                p += ".";
            }
            System.out.print(p + "" + ((i * 100) / (population.size())) +"%|>\r");

        }
        System.out.println(p + "" + 100 +"%|>\n");
        /*
        population.forEach(
                individual -> individual.setFitness(
                        service.calculateFitness(individual.getIndividual())
                )
        );

         */

    }

    private void updateResults() {
        generationBests.add(
                new Result<>(
                        population.stream()
                                .max(Comparator.comparingDouble(PopulationIndividual<I>::getFitness))
                                .get()
                )
        );
        generationAverageFitnesses.add(
                population.stream()
                        .mapToDouble(PopulationIndividual::getFitness)
                        .average()
                        .getAsDouble()
        );
    }
}
