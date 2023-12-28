import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Runner {
    
    public static void main(String[] args) {

        //testAlgorithms();
        //System.exit(0);

        System.out.println("Welcome to 8-Puzzle");
        System.out.println("What would you like to do?");
        System.out.println("1) Enter a puzzle configuration");
        System.out.println("2) Generate a random puzzle");
        System.out.println("3) Read in a puzzle from a file");

        Scanner userInput = new Scanner(System.in);
        int choice = userInput.nextInt();
        userInput.nextLine();

        // choose option based on user input
        if (choice < 1 || choice > 3) {
            System.out.println("Invalid input. Ending program");
            return;
        }

        if (choice == 1) {
            System.out.println("Enter your puzzle configuration as three lines, row by row");
            String config = userInput.nextLine();
            config += userInput.nextLine();
            config += userInput.nextLine();
            config = config.replaceAll("\\s", ""); // receive and trim input
            //System.out.println("got board from user input");
            AStar(config);
        }
        else if (choice == 2){
            String config = getRandomConfig();
            while (!validPuzzle(config)) {
                config = getRandomConfig(); // generate a solvable input, then solve it
            }
            AStar(config);
        }
        else {
            System.out.println("Enter filename:"); // get a file name
            String name = userInput.nextLine();
            readPuzzlesFromFile(name);
        }

        userInput.close();
    }

    // read puzzles from the file inputted by the user, it reads files formatted in the same way Dr. Tang did the example files
    public static void readPuzzlesFromFile(String filename) {
        
        try {
            File file = new File(filename);
            Scanner fileInput = new Scanner(file);

            while (fileInput.hasNextLine()) {
                fileInput.nextLine(); // skip line of slashes
                String config = fileInput.nextLine(); // grab 3 lines and then trim and pass in configuration
                config += fileInput.nextLine();
                config += fileInput.nextLine();
                config = config.replaceAll("\\s", ""); // receive and trim input
                AStar(config);
            }
            fileInput.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(0);
        }
        
    }

    // check if the puzzle is valid
    public static boolean validPuzzle(String config) {
        int inversions = 0;
        config.replace(" ", "");
        int[] board = new int[9];
        for (int i = 0; i < 9; i++) {
            board[i] = Character.getNumericValue(config.charAt(i)); // convert into int[] for my convenience
        }

        // nested loop which counts number of inversions
        for (int i = 0; i < 8; i++) {
            if (board[i] != 0) {
                for (int j = i; j < 9; j++) {
                    if (board[i] > board[j] && board[j] != 0)
                        inversions++;
                }
            }
        }

        // if inversions are even, return true that the board is solvable
        if (inversions % 2 == 0)
            return true;
        return false;
    }

    public static void AStar(String config) {

        if (!validPuzzle(config)) {
            System.out.println("Unsolvable puzzle. Ending program"); // print if puzzle is unsolvable
            return;
        }

        // board is solvable
        // start solving using H1 and record time
        PuzzleSolver solver1 = new PuzzleSolver(config);
        System.out.println("\nAttempting to solve using H1...\n"); // time puzzle solving time
        long h1Start = System.currentTimeMillis();
        solver1.solve(1);
        long h1End = System.currentTimeMillis();
        long h1Time = h1End - h1Start;

        int h1Steps = solver1.getStepsTaken();
        int h1Nodes = solver1.getNodesGenerated();
        // print results of H1
        System.out.println("H1 Approach solved puzzle in " + h1Time + "ms, in " + h1Steps + " steps and generated " + h1Nodes + " nodes.");

        
        // start solving using H2 and record time
        PuzzleSolver solver2 = new PuzzleSolver(config);
        System.out.println("\nAttempting to solve using H2...\n"); // time the H2 approach
        long h2Start = System.currentTimeMillis();
        solver2.solve(2);
        long h2End = System.currentTimeMillis();
        long h2Time = h2End - h2Start;

        int h2Steps = solver2.getStepsTaken();
        int h2Nodes = solver2.getNodesGenerated();
        // print results of H2
        System.out.println("H2 Approach solved puzzle in " + h2Time + "ms, in " + h2Steps + " steps and generated " + h2Nodes + " nodes.");
        
    }

    // creates a random board configuration
    public static String getRandomConfig() {

        ArrayList<Integer> nums = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) 
			nums.add(i);
			
		Collections.shuffle(nums);
		String config = "";
        for (int i = 0; i < 9; i++) {
            config = config + nums.get(i);
        }
        return config;
    }


    // testing function that generates 100 test cases and records data on them
    public static void testAlgorithms() {

        int[] depth1 = new int[32];
        double[] generated1 = new double[32];
        long[] times1 = new long[32];

        int[] depth2 = new int[32];
        double[] generated2 = new double[32];
        long[] times2 = new long[32];

        System.out.println("Starting tests...");
        for (int i = 0; i < 100; i++) {

            String testConfig = getRandomConfig();
            while (!validPuzzle(testConfig))
                testConfig = getRandomConfig();
            PuzzleSolver solver = new PuzzleSolver(testConfig);
            long start = System.currentTimeMillis();
            solver.solve(1);
            long end = System.currentTimeMillis();
            long time = end - start;
            double h1Generated = solver.getNodesGenerated();
            int length = solver.getStepsTaken();

            depth1[length] = depth1[length] + 1;
            generated1[length] = generated1[length] + h1Generated;
            times1[length] = times1[length] + time;

            PuzzleSolver solver2 = new PuzzleSolver(testConfig);
            long start2 = System.currentTimeMillis();
            solver2.solve(2);
            long end2 = System.currentTimeMillis();
            long time2 = end2 - start2;
            double h2Generated = solver2.getNodesGenerated();
            int length2 = solver2.getStepsTaken();

            depth2[length2] = depth2[length2] + 1;
            generated2[length2] = generated2[length2] + h2Generated;
            times2[length2] = times2[length2] + time2;


            System.out.println("Finished test " + i);
        }

        System.out.println("Results:");
        for (int i = 1; i <= 31; i++) {
            if (depth1[i] > 0) {
                System.out.println(depth1[i] + " cases of length " + i + " with an average runtime of " + times1[i] / depth1[i] + "ms " 
                    + "and an average of " + generated1[i] / depth1[i] + " nodes generated for H1");
                System.out.println(depth2[i] + " cases of length " + i + " with an average runtime of " + times2[i] / depth2[i] + "ms " 
                    + "and an average of " + generated2[i] / depth2[i] + " nodes generated for H2");
            }
        }
    }
}
