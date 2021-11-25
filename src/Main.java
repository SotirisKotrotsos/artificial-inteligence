import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Please insert the the initial number of Cannibals and Missionaries.");
        int numOfPeople =  myScanner.nextInt();
        System.out.println("Please insert the space of boat.");
        int spaceOfBoat = myScanner.nextInt();
        System.out.println("Please insert the maximum number of routes");
        int maxNumOfRoutes = myScanner.nextInt();
        State initialState = new State(numOfPeople, spaceOfBoat, maxNumOfRoutes);
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
