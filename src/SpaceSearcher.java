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

    State BestFSClosedSet(State initialState)
    {
        if(initialState.isFinal()) return initialState;
        this.frontier.add(initialState);
        this.closedSet.add(initialState);
        while(this.frontier.size() > 0)
        {
            State currentState = this.frontier.remove(0);
            if(currentState.isFinal()) return currentState;
            if(!this.closedSet.contains(currentState))
            {
                this.closedSet.add(currentState);
                this.frontier.addAll(currentState.getChildren());
                Collections.sort(this.frontier);
            }
        }
        return null;
    }


}
