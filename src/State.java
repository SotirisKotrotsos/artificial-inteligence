
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
            for(int i = 0; i <= this.getCannibalsLeft(); i++) {
                for(int j = 0; j <= this.getMissionariesLeft(); j++) {
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
            for (int i = 0; i <= missionariesRight; i++) {
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
        }
        else{
            this.missionariesRight -= missionaries;
            this.cannibalsRight -= cannibals;
            this.missionariesLeft += missionaries;
            this.cannibalsLeft += cannibals;
            coastOfBoat = position.LEFT;
        }
        if(!isValid()){
            return false;
        }
        return true;
    }
    /*public boolean moveRight(ArrayList<State> children) {
        if (this.coastOfBoat == "right") {
            return false;
        } else {
            for(int can = 0; can <= this.getCannibalsLeft(); ++can) {
                for(int mis = 0; mis <= this.getMissionariesLeft(); ++mis) {
                    System.out.println("before copy right //" + this.getCoastOfBoat());
                    State child = new State(this.spaceOfBoat, this.cannibalsLeft, this.missionariesLeft, this.cannibalsRight, this.missionariesRight, this.coastOfBoat, this.numOfPeople, this.maxNumOfRoutes);
                    if (this.Restrictions(mis, can, "right")) {
                        System.out.println("mis to move:" + mis);
                        System.out.println("can to move:" + can);
                        this.setMissionariesLeft(this.getMissionariesLeft() - mis);
                        System.out.println("mis left" + this.getMissionariesLeft());
                        this.setCannibalsLeft(this.getCannibalsLeft() - can);
                        System.out.println("can left" + this.getCannibalsLeft());
                        this.setCannibalsRight(this.getCannibalsRight() + can);
                        System.out.println("can right" + this.getCannibalsRight());
                        this.setMissionariesRight(this.getMissionariesRight() + mis);
                        System.out.println("mis right" + this.getMissionariesRight());
                        this.coastOfBoat = "right";
                        child.setFather(this);
                        child.heuristic();
                        children.add(child);
                        System.out.println("mvright " + this.getCoastOfBoat());
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public boolean moveLeft(ArrayList<State> children) {
        if (this.coastOfBoat == "left") {
            return false;
        } else {
            for(int can = 0; can <= this.getCannibalsRight(); ++can) {
                for(int mis = 0; mis <= this.getMissionariesRight(); ++mis) {
                    System.out.println("before copy left //" + this.getCoastOfBoat());
                    State child = new State(this.spaceOfBoat, this.cannibalsLeft, this.missionariesLeft, this.cannibalsRight, this.missionariesRight, this.coastOfBoat, this.numOfPeople, this.maxNumOfRoutes);
                    if (this.Restrictions(mis, can, "left")) {
                        System.out.println("mis to move:" + mis);
                        System.out.println("can to move:" + can);
                        this.setCannibalsRight(this.cannibalsRight - can);
                        System.out.println("can right" + this.getCannibalsRight());
                        this.setMissionariesRight(this.missionariesRight - mis);
                        System.out.println("mis right" + this.getCannibalsRight());
                        this.setCannibalsLeft(this.cannibalsLeft + can);
                        System.out.println("can left" + this.getCannibalsLeft());
                        this.setMissionariesLeft(this.missionariesLeft + mis);
                        System.out.println("mis left" + this.getMissionariesLeft());
                        this.coastOfBoat = "left";
                        child.setFather(this);
                        child.heuristic();
                        children.add(child);
                        System.out.println("mvleft " + this.getCoastOfBoat());
                        return true;
                    }
                }
            }

            return false;
        }
    }*/

    /*public boolean Restrictions(int numOfMissionaries, int numOfCannibals, String move) {
        if (numOfCannibals + numOfMissionaries > this.getSpaceOfBoat()) {
            return false;
        } else {
            if (move.equals("left")) {
                if (this.getMissionariesRight() - numOfMissionaries < this.getCannibalsRight() - numOfCannibals) {
                    return false;
                }
            } else if (this.getMissionariesLeft() - numOfMissionaries < this.getCannibalsLeft() - numOfCannibals) {
                return false;
            }

            if (this.getSpaceOfBoat() >= 2 && numOfMissionaries < numOfCannibals) {
                return false;
            } else {
                return numOfCannibals != 0 && numOfMissionaries != 0;
            }
        }
    }*/

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
