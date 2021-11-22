import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class SpaceSearcher {
    private ArrayList<State> frontier;
    private HashSet<State> closedSet;

    public SpaceSearcher(State state)
    {
        this.frontier = new ArrayList<>();
        this.closedSet = new HashSet<>();
        for(Object st : state.getChildren()){
            frontier.add((State) st);
        }
        closedSet.add(state);
    }
    public State AStar(State initialState){

        while (!frontier.isEmpty()){
            State current = frontier.remove(0);
            for(Object state: current.getChildren()){
                if(!frontier.contains((State)state) && !closedSet.contains((State)state)){
                    closedSet.add((State)state);
                }
            }
            if(current.isFinal()){
                return current;
            }
            frontier.add(current);
            Collections.sort(frontier);

        }
        return null;
    }
    State BestFSClosedSet(State initialState)
    {
        if(initialState.isFinal()) return initialState;
        // step 1: put initial state in the frontier.
        this.frontier.add(initialState);
        this.closedSet.add(initialState);
        System.out.println("frontier size:" + frontier.size());
        // step 2: check for empty frontier.
        while(this.frontier.size() > 0)
        {
            // step 3: get the first node out of the frontier.
            State currentState = this.frontier.remove(0);
            // step 4: if final state, return.
            System.out.println(currentState.isFinal());
            if(currentState.isFinal()) return currentState;

            // step 5: if the node is not in the closed set, put the children at the frontier.
            // else go to step 2.
            if(!this.closedSet.contains(currentState))
            {
                this.closedSet.add(currentState);
                this.frontier.addAll(currentState.getChildren());
                System.out.println("frontier size:" + frontier.size());
                // step 6: sort the frontier based on the heuristic score to get best as first
                Collections.sort(this.frontier); // sort the frontier to get best as first
            }
        }
        return null;
    }


}
