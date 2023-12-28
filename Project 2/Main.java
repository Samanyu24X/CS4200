import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {

        // menu and user input
        System.out.println("Welcome to 8-Queen. Pick an algorithm option");
        System.out.println("1) Steepest Ascent Hill-Climbing");
        System.out.println("2) Genetic Algorithm");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        input.nextLine();

        // choose requested algorithm
        if (choice == 1) {
            testSteepestAscent();
        }
        else if (choice == 2) {
            testGenetic();
        }
        else {
            System.out.println("Invalid input. Closing program");
        }
        input.close();
    }

    public static void testSteepestAscent() {

        
        System.out.println("Testing Steepest Ascent Hill-Climbing");

        double tests = 10000; // amount of trials
        double passed = 0;

        long hillClimbingStart = System.currentTimeMillis();

        // generate a random layout, and then pass it into the algorithm method
        for (int i = 0; i < tests; i++) {
            int[] layout = QueenBoard.getRandomLayout();
            QueenBoard board = new QueenBoard(layout);
            QueenBoard result = HillClimbing.solveHillClimbing(board);
            //System.out.print("Result: ");
            for (int j = 0; j < 8; j++) {
                //System.out.print(result.getQueens()[j] + " ");
            }
            //System.out.println();

            if (result.getCrosses() == 0)
                passed++;

        }

        // results
        long hillClimbingEnd = System.currentTimeMillis();
        long hillClimbingTime = hillClimbingEnd - hillClimbingStart;
        System.out.println("Time elapsed for " + tests + " cases: " + hillClimbingTime);
        System.out.println("Total neighbors generated = " + HillClimbing.cost);
        System.out.println("Cases passed: " + passed + "/" + tests + ", = " + 100*(passed/tests) + "%");

    }

    public static void testGenetic() {

        System.out.println("Testing Genetic Algorithm");
        
        int population = 100;
        int generations = 200;
        double mutationRate = 0.1;

        long start = System.currentTimeMillis();

        Genetic.solveGenetic(population, generations, mutationRate);

        long end = System.currentTimeMillis();
        long time = end - start;
        
        System.out.println("Time elapsed: " + time);
    }
}
