import java.util.Comparator;

public class QueenBoardComparator implements Comparator<QueenBoard> {

    @Override
    public int compare(QueenBoard o1, QueenBoard o2) {
        // TODO Auto-generated method stub

        return Integer.compare(o1.getCrosses(), o2.getCrosses());

        /* 
        if (o1.getCrosses() < o2.getCrosses())
            return -1;
        else if (o1.getCrosses() > o2.getCrosses())
            return 1;
        else
            return 0;
        */
    }
    
}
