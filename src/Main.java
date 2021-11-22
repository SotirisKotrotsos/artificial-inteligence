import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        State initialState = new State(6, 2, 100);
        System.out.println("getChildrensize "+initialState.getChildren().size());
        SpaceSearcher searcher = new SpaceSearcher(initialState);
        long start = System.currentTimeMillis();
        //State terminalState = searcher.AStar(initialState);
        State terminalState = searcher.BestFSClosedSet(initialState);
        long end = System.currentTimeMillis();
        if(terminalState == null){

            System.out.println("Couldn't find a solution");
        }
        else{
            State temp = terminalState;
            ArrayList<State> path = new ArrayList<>();
            path.add(terminalState);
            while(temp.getFather() != null) // if father is null, then we are at the root.
            {
                path.add(temp.getFather());
                temp = temp.getFather();
            }
            // reverse the path and print.
            Collections.reverse(path);
            for(State item: path)
            {
                //System.out.println(item.toString());
                item.print();
            }
            System.out.println();
            System.out.println("Search time:" + (double)(end - start) / 1000 + " sec.");  // total time of searching in seconds.


        }
    }
}
