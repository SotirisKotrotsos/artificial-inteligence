
import java.util.ArrayList;
import java.util.Objects;

public class State<child> implements Comparable<State> {
    private int cannibalsRight;
    private int missionariesRight;
    private int cannibalsLeft;
    private int missionariesLeft;
    private int score;
    private int heuristicScore;
    private int spaceOfBoat;
    private int maxNumOfRoutes;
    private int numOfPeople;
    private position coastOfBoat;
    private State father = null;
    private int route;

    State(int numOfPeople, int spaceOfBoat, int maxNumOfRoutes) {
        this.numOfPeople = numOfPeople;
        this.cannibalsLeft = numOfPeople;
        this.missionariesLeft = numOfPeople;
        this.spaceOfBoat = spaceOfBoat;
        this.maxNumOfRoutes = maxNumOfRoutes;
        this.cannibalsRight = 0;
        this.missionariesRight = 0;
        this.coastOfBoat = position.LEFT;
        this.score = 0;
        this.route = 0;
    }

    State(State state) {
        this.spaceOfBoat = spaceOfBoat;
        this.setCannibalsLeft(state.getCannibalsLeft());
        this.setCannibalsRight(state.getCannibalsRight());
        this.setMissionariesRight(state.getMissionariesRight());
        this.setMissionariesLeft(state.getMissionariesLeft());
        this.setCoastOfBoat(state.getCoastOfBoat());
        this.setNumOfPeople(state.getNumOfPeople());
        this.setSpaceOfBoat(state.getSpaceOfBoat());
        this.setScore(state.getScore());
        this.setRoute(state.getRoute());
    }


    public int getCannibalsRight() {
        return this.cannibalsRight;
    }

    public void setCannibalsRight(int cannibalsRight) {
        this.cannibalsRight = cannibalsRight;
    }

    public int getMissionariesRight() {
        return this.missionariesRight;
    }

    public void setMissionariesRight(int missionariesRight) {
        this.missionariesRight = missionariesRight;
    }

    public int getCannibalsLeft() {
        return this.cannibalsLeft;
    }

    public void setCannibalsLeft(int cannibalsLeft) {
        this.cannibalsLeft = cannibalsLeft;
    }

    public int getMissionariesLeft() {
        return this.missionariesLeft;
    }

    public void setMissionariesLeft(int missionariesLeft) {
        this.missionariesLeft = missionariesLeft;
    }

    public int getSpaceOfBoat() {
        return this.spaceOfBoat;
    }

    public void setSpaceOfBoat(int spaceOfBoat) {
        this.spaceOfBoat = spaceOfBoat;
    }

    public int getNumOfPeople() {
        return this.numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    State getFather() {
        return this.father;
    }

    void setFather(State father) {
        this.father = father;
    }

    public int getMaxNumOfRoutes() {
        return this.maxNumOfRoutes;
    }

    public void setMaxNumOfRoutes(int maxNumOfRoutes) {
        this.maxNumOfRoutes = maxNumOfRoutes;
    }

    public position getCoastOfBoat() {
        return this.coastOfBoat;
    }

    public void setCoastOfBoat(position coastOfBoat) {
        this.coastOfBoat = coastOfBoat;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    boolean isFinal() {
        return (this.cannibalsLeft == 0 && this.missionariesLeft == 0);
    }
    public void addScore(){
        score++;
    }
    public String toString() {

        return "\n-----------------------------\nCannibals Left:" + this.cannibalsLeft + "\nMissionaries Left:" + this.missionariesLeft + "\nCannibals Right:" + this.cannibalsRight + "\nMissionaries Right:" + this.missionariesRight + "\nScore:" + this.getScore() + "\n------------------------------";
    }
    void print(){
        System.out.println("-------------------------------------");

        for(int i=0; i<cannibalsLeft; i++){
            System.out.print("C");
        }

        System.out.print(" ");

        for(int i=0; i<missionariesLeft; i++){
            System.out.print("M");
        }

        System.out.print(" ~~~~~~ ");


        for(int i=0; i<cannibalsRight; i++){
            System.out.print("C");
        }

        System.out.print(" ");

        for(int i=0; i<missionariesRight; i++){
            System.out.print("M");
        }
        System.out.print("\n");
        System.out.println("-------------------------------------");
    }

    public ArrayList<State> getChildren() {
        ArrayList<State> children = new ArrayList();
        if(coastOfBoat.equals(position.LEFT)){
            for(int i = 0; i <= this.cannibalsLeft; i++) {
                for(int j = 0; j <= this.cannibalsRight; j++) {
                    State child = new State(this);
                    if(child.moveBoat(i,j,this.coastOfBoat)){
                        child.addScore();
                        child.heuristic();
                        child.setFather(this);
                        children.add(child);
                    }

                }
            }
        }else {
            for (int i = 0; i <= cannibalsRight; i++) {
                if (missionariesRight == 0) {
                    State child = new State(this);
                    if (child.moveBoat(i, 0, this.coastOfBoat)) {
                        child.addScore();
                        child.heuristic();
                        child.setFather(this);
                        children.add(child);
                    }
                }
                else{
                    for(int j = 0; j<=missionariesRight;  j++){
                        State child = new State(this);
                        if(child.moveBoat(i,j,this.coastOfBoat)){
                            child.addScore();
                            child.heuristic();
                            child.setFather(this);
                            children.add(child);
                        }
                    }
                }
            }
        }

        return children;
    }

    public boolean moveBoat(int cannibals,int missionaries, position coastOfBoat){
        if(cannibals == 0 && missionaries == 0)return false;
        if(cannibals+missionaries>spaceOfBoat) return false;
        if(cannibals>missionaries && missionaries!=0) return false;
        if(coastOfBoat.equals(position.LEFT)){
            this.missionariesLeft -= missionaries;
            this.cannibalsLeft -= cannibals;
            this.missionariesRight += missionaries;
            this.cannibalsRight += cannibals;
            coastOfBoat = position.RIGHT;
            this.route++;
        }
        else{
            this.missionariesRight -= missionaries;
            this.cannibalsRight -= cannibals;
            this.missionariesLeft += missionaries;
            this.cannibalsLeft += cannibals;
            coastOfBoat = position.LEFT;
            this.route++;
        }
        if(!isValid()){
            return false;
        }
        if(this.route>=this.maxNumOfRoutes){
            System.out.println("The maximum number of routes overloaded");
            return false;
        }
        return true;
    }


    public boolean isValid() {
        return ((this.cannibalsLeft<=this.missionariesLeft || this.missionariesLeft==0) && (this.cannibalsRight<=this.missionariesRight) || this.missionariesRight == 0);
    }

    private void heuristic() {
        int peopleInLeft = missionariesLeft + cannibalsLeft;
        if(coastOfBoat.equals(position.RIGHT)){
            heuristicScore = 2*peopleInLeft;
        }
        else if(coastOfBoat.equals(position.LEFT)==true && peopleInLeft==1){
            heuristicScore = 1;
        }
        else if(coastOfBoat.equals(position.LEFT) && peopleInLeft>1){
            heuristicScore = 2 * peopleInLeft-3;
        }
        else if(peopleInLeft == 0){
            heuristicScore = 0;
        }
        heuristicScore += score;

    }

    public int compareTo(State state) {
        return Integer.compare(this.heuristicScore, state.heuristicScore);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State<?> state = (State<?>) o;
        return cannibalsRight == state.cannibalsRight &&
                missionariesRight == state.missionariesRight &&
                cannibalsLeft == state.cannibalsLeft &&
                missionariesLeft == state.missionariesLeft &&
                score == state.score &&
                heuristicScore == state.heuristicScore &&
                spaceOfBoat == state.spaceOfBoat &&
                maxNumOfRoutes == state.maxNumOfRoutes &&
                numOfPeople == state.numOfPeople &&
                coastOfBoat == state.coastOfBoat &&
                Objects.equals(father, state.father);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cannibalsRight, missionariesRight, cannibalsLeft, missionariesLeft, score, heuristicScore, spaceOfBoat, maxNumOfRoutes, numOfPeople, coastOfBoat, father);
    }
}
