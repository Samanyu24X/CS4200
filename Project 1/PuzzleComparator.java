import java.util.Comparator;

public class PuzzleComparator implements Comparator<PuzzleBoard>{

    // compare function that allows PriorityQueue to order boards
    @Override
    public int compare(PuzzleBoard board1, PuzzleBoard board2) {

        if (board1.getAStarValue() < board2.getAStarValue())
            return -1;
        else if (board1.getAStarValue() > board2.getAStarValue())
            return 1;
        else
            return 0;
    }
    
}
