# Evolution Optimizer

Simple tool for model optimization with evolutionary algorithm.

## Example usage

```java
public class Main {

    public static void main(String[] args) {
        
        Configuration<YourModel> configuration = 
                Configuration.create(
                        YourModel.class,
                        30, // population size
                        60  // generations amount
                );

        YourModelService service = new YourModelService();

        Population<YourModel> population = new Population<>(configuration, service);

        GeneticStatistics<TestParameters> solution = population.solve();    
    
    }
}
```
```java
public class YourModel {
    
    double x;
    
    public YourModel(double x) {
        this.x = x;
    }
}
```
```java
public class YourModelService implements IndividualService<YourModel> {

    private boolean mutationOccurred;

    @Override
    public double calculateFitness(YourModel a) {
        return - (a.x * a.x) + 5;
    }

    @Override
    public YourModel createRandom() {
        double randomX = Math.random() * 10;
        return new YourModel(randomX);
    }

    @Override
    public List<YourModel> performCrossover(YourModel a, YourModel b) {
        YourModel child1 = new YourModel((a.x + b.x) / 2);
        YourModel child2 = new YourModel((a.x + b.x) / 2);
        return List.of(child1, child2);
    }

    @Override
    public YourModel mutate(YourModel a, double mutationStrength, double mutationProbability) {
        Random random = new Random();
        double mutationNumber;
        if (random.nextDouble() < mutationProbability) {
            mutationOccurred = true;
            mutationNumber = random.nextDouble() * 3.14;
            if (mutationNumber < a.x) {
                a.x = mutationNumber * mutationStrength;
            } else {
                a.x = mutationNumber * mutationStrength;
            }
        }
        return a;
    }

    @Override
    public Function<YourModel, YourModel> deepCloneFactory() {
        return model -> new YourModel(model.x);
    }

    @Override
    public boolean isMutationOccurred() {
        return mutationOccurred;
    }
}
```
## Algorithm configuration
### Additional parameters
There are some additional parameters you can configure instead of using defaults:
```java
public class Main {

    public static void main(String[] args) {

        Configuration<YourModel> configuration = Configuration.create(
                        YourModel.class,
                        30, // population size
                        60  // generations amount
                )
                .withCrossoverProbability(0.7)
                .withMutationProbability(0.5)
                .withMutationStrength(0.4);

        YourModelService service = new YourModelService();

        Population<YourModel> population = new Population<>(configuration, service);

        GeneticStatistics<YourModel> solution = population.solve();

    }
}
```

### Choosing selection method
You can also specify which selection method you'd like to use or implement your own.

```java
public class Main {

    public static void main(String[] args) {

        Configuration<YourModel> configuration = Configuration.create(
                        YourModel.class,
                        30, // population size
                        60  // generations amount
                )
                .withSelectionMethod(new ElitismSelection<>())
                .withEliteSize(5);

        YourModelService service = new YourModelService();

        Population<YourModel> population = new Population<>(configuration, service);

        GeneticStatistics<YourModel> solution = population.solve();

    }
}
```

There are 5 implemented selection methods:

```java
// Roulette selection
.withSelectionMethod(new RouletteSelection<>())

// Ranking selection
.withSelectionMethod(new RankingSelection<>())

// Eliticism selection
.withSelectionMethod(new ElitismSelection<>())
.withElitismComplementaryMethod(new RouletteSelection<>())
.withEliteSize(5)

        
// Tournament selection
.withSelectionMethod(new TournamentSelection<>())
.withTournamentSize(5)
```

You can also implement your own selection method:

```java
public class YourOwnSelectionMethod<I> implements SelectionMethod<I> {
    
    @Override
    public List<PopulationIndividual<I>> selectIndividuals(SelectionParameters<I> selectionParameters) {
        // your selection logic goes here
    }
}
```

