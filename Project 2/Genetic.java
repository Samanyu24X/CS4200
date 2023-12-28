import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class Genetic {

    public static void solveGenetic(int population, int generations, double mutationRate) {

        Comparator<QueenBoard> QueenBoardComparator = new QueenBoardComparator();
        PriorityQueue<QueenBoard> queue = new PriorityQueue<>(10, QueenBoardComparator);

        // fill population with random boards
        for (int i = 0; i < population; i++) {
            int[] randomLayout = QueenBoard.getRandomLayout();
            QueenBoard organism = new QueenBoard(randomLayout);
            queue.offer(organism);
        }

        // parent population is half the population
        int parentPopulation = population / 2;
        PriorityQueue<QueenBoard> parents = new PriorityQueue<>(10, QueenBoardComparator);
        
        // for each generation, check if there is a solution, otherwise start breeding
        for (int g = 1; g <= generations; g++) {
            //System.out.println("Starting generation " + g);

            if (queue.peek().getCrosses() == 0) {
                System.out.println("Solution found! " + queue.peek().toString() + " with " + queue.peek().getCrosses() + " crosses.");
                return;
            }

            // for each generation, get the best organisms as parents
            for (int p = 0; p < parentPopulation; p++) {
                parents.offer(queue.poll());
            }

            // now mate them
            PriorityQueue<QueenBoard> children = new PriorityQueue<>(10, QueenBoardComparator);
            for (int m = 0; m < parentPopulation / 2; m++) {
                QueenBoard parent1 = parents.poll();
                QueenBoard parent2 = parents.poll();

                // two offspring from each pair of parents
                QueenBoard child1 = mate(parent1, parent2, mutationRate);
                QueenBoard child2 = mate(parent2, parent1, mutationRate);
                children.offer(child1);
                children.offer(child2);
            }

            // mating is done so set the queue to the new children population
            parents.clear();
            queue = children;

        }
        System.out.println("Wasn't able to find a solution.");

    }

    public static QueenBoard mate(QueenBoard p1, QueenBoard p2, double mutationRate) {
        // generate random crossover point
        Random random = new Random();
        int crossoverPoint = random.nextInt(7) + 1;

        int[] childGenes = new int[8];
        int[] p1Genes = p1.getQueens();
        int[] p2Genes = p2.getQueens();

        // generate child genes
        for (int i = 0; i < crossoverPoint; i++) {
            childGenes[i] = p1Genes[i];
        }
        for (int j = crossoverPoint; j < 8; j++) {
            childGenes[j] = p2Genes[j];
        }

        // generate possible mutation
        if (random.nextDouble() < mutationRate) {
            int mutationIndex = random.nextInt(8);
            int newValue = random.nextInt(8);
            childGenes[mutationIndex] = newValue;
        }
        return new QueenBoard(childGenes);
    }
    
}
