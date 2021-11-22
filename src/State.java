import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class State<child> implements Comparable<State>{
    private ArrayList<Integer> cannibalsRight ;
    private ArrayList<Integer> missionariesRight ;
    private ArrayList<Integer> cannibalsLeft;
    private ArrayList<Integer> missionariesLeft;
    private int score;
    private int spaceOfBoat;
    private int maxNumOfRoutes;
    private int numOfPeople;

    //private int numOfRoutes = 0;
    private String startPosition;//the start position if boat

    private String coastOfBoat;
    private State father = null;


    State(int numOfPeople, int spaceOfBoat, int maxNumOfRoutes){
        this.numOfPeople = numOfPeople;
        this.cannibalsLeft = numOfPeople;
        this.missionariesLeft = numOfPeople;
        this.spaceOfBoat = spaceOfBoat;
        this.maxNumOfRoutes = maxNumOfRoutes;
        this.cannibalsRight = 0;
        this.missionariesRight = 0;
        this.coastOfBoat = "left";
        /*this.cannibalsLeft = new ArrayList<Integer>();
        this.cannibalsLeft.add(numOfPeople);*/



    }
    State(int spaceOfBoat,int cannibalsLeft, int missionariesLeft,int cannibalsRight,int missionariesRight,String coastOfBoat,int numOfPeople, int maxNumOfRoutes){
        this.spaceOfBoat = spaceOfBoat;
        this.cannibalsLeft = cannibalsLeft;
        this.cannibalsRight = cannibalsRight;
        this.missionariesRight = missionariesRight;
        this.missionariesLeft = missionariesLeft;
        this.coastOfBoat = coastOfBoat;
        this.numOfPeople = numOfPeople;
        this.maxNumOfRoutes = maxNumOfRoutes;

    }

    State(State state){
        this.missionariesLeft = state.getMissionariesLeft();
        this.cannibalsLeft = state.getCannibalsLeft();
        this.cannibalsRight = state.getCannibalsRight();
        this.missionariesRight = state.getMissionariesRight();
        this.coastOfBoat = state.getCoastOfBoat();
    }

    public int getCannibalsRight() {
        return cannibalsRight;
    }

    public void setCannibalsRight(int cannibalsRight) {
        this.cannibalsRight = cannibalsRight;
    }

    public int getMissionariesRight() {
        return missionariesRight;
    }

    public void setMissionariesRight(int missionariesRight) {
        this.missionariesRight = missionariesRight;
    }

    public int getCannibalsLeft() {
        return cannibalsLeft;
    }

    public void setCannibalsLeft(int cannibalsLeft) {
        this.cannibalsLeft = cannibalsLeft;
    }

    public int getMissionariesLeft() {
        return missionariesLeft;
    }

    public void setMissionariesLeft(int missionariesLeft) {
        this.missionariesLeft = missionariesLeft;
    }

    public int getSpaceOfBoat() {
        return spaceOfBoat;
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


    State getFather()
    {
        return this.father;
    }

    void setFather(State father)
    {
        this.father = father;
    }

    public int getMaxNumOfRoutes() {
        return maxNumOfRoutes;
    }

    public void setMaxNumOfRoutes(int maxNumOfRoutes) {
        this.maxNumOfRoutes = maxNumOfRoutes;
    }

    public String getCoastOfBoat() {
        return coastOfBoat;
    }

    public void setCoastOfBoat(String coastOfBoat) {
        this.coastOfBoat = coastOfBoat;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    boolean isFinal(){
        /*if(getCannibalsLeft() == 0 && getMissionariesLeft() == 0) return true;
        if(getCannibalsRight() == getNumOfPeople() && getMissionariesRight() == getNumOfPeople()) return true;*/

        //return getCannibalsLeft() == 0 && getCannibalsRight() == getNumOfPeople() && getMissionariesLeft() == 0 && getMissionariesRight() == getNumOfPeople() && getCoastOfBoat().equals("right");
       return this.cannibalsLeft == 0 && this.cannibalsRight == this.numOfPeople && this.missionariesLeft == 0 && this.missionariesRight == this.numOfPeople && this.coastOfBoat.equals("right");
    }

   public String toString(){
        return ("\n-----------------------------")+
        ("\nCannibals Left:" + this.cannibalsLeft)+
        ("\nMissionaries Left:" + this.missionariesLeft)+
        ("\nCannibals Right:" + this.cannibalsRight)+
        ("\nMissionaries Right:" + this.missionariesRight)+
        ("\nScore:" + getScore())+
        ("\n------------------------------");

    }
    public ArrayList<State> getChildren(){
        ArrayList<State> children = new ArrayList<State>();

        if (this.moveRight(children) && !isFinal()) {
            System.out.println("move right");
        }
        if (this.moveLeft(children) && !isFinal()) {
            System.out.println("move left");
        }


        return children;

    }
    public boolean moveRight(ArrayList<State> children){
        if(this.coastOfBoat == "right") return false;
        for(int can = 0; can <= getCannibalsLeft(); can++){
            for(int mis = 0; mis<=getMissionariesLeft(); mis++){
                //State child = new State(getSpaceOfBoat(),getCannibalsLeft(),getMissionariesLeft(),getCannibalsRight(),getMissionariesRight(),getCoastOfBoat(),getNumOfPeople(),getMaxNumOfRoutes());
                System.out.println("before copy right //"+getCoastOfBoat());
                State child = new State(this.spaceOfBoat,this.cannibalsLeft,this.missionariesLeft,this.cannibalsRight,this.missionariesRight,this.coastOfBoat,this.numOfPeople,this.maxNumOfRoutes);
                //if((can+mis) <= getSpaceOfBoat() && (getMissionariesLeft()-mis) >= (getCannibalsLeft()-can) && can <= mis && can!= 0 && mis != 0) {

                if(Restrictions(mis,can,"right")){
                    /*setMissionariesLeft(getMissionariesLeft()-mis);
                    setCannibalsLeft(getCannibalsLeft()-can);
                    setCannibalsRight(getCannibalsRight()+can);
                    setMissionariesRight(getMissionariesRight()+mis);*/
                    System.out.println("mis to move:" + mis);
                    System.out.println("can to move:" + can);
                    setMissionariesLeft(getMissionariesLeft() - mis);
                    System.out.println("mis left" + getMissionariesLeft());
                    setCannibalsLeft(getCannibalsLeft() - can);
                    System.out.println("can left" + getCannibalsLeft());
                    setCannibalsRight(getCannibalsRight() + can);
                    System.out.println("can right" + getCannibalsRight());
                    setMissionariesRight(getMissionariesRight() + mis);
                    System.out.println("mis right" + getMissionariesRight());
                    this.coastOfBoat = "right";
                    child.setFather(this);
                    child.heuristic();
                    children.add(child);
                    System.out.println("mvright " + getCoastOfBoat());

                    //setScore(this.score + 2);
                    return true;
                }
            }
        }
        return false;
    }
    public boolean moveLeft(ArrayList<State> children){
        if(this.coastOfBoat == "left") return false;
        for(int can = 0; can <= getCannibalsRight(); can++){
            for(int mis =0; mis<=getMissionariesRight(); mis++){
                System.out.println("before copy left //"+getCoastOfBoat());
                State child = new State(this.spaceOfBoat,this.cannibalsLeft,this.missionariesLeft,this.cannibalsRight,this.missionariesRight,this.coastOfBoat,this.numOfPeople,this.maxNumOfRoutes);
                //State child = new State(getSpaceOfBoat(),getCannibalsLeft(),getMissionariesLeft(),getCannibalsRight(),getMissionariesRight(),getCoastOfBoat(),getNumOfPeople(),getMaxNumOfRoutes());
                //if((can+mis) <= getSpaceOfBoat() && (getMissionariesRight()-mis) >= (getCannibalsRight()-can) && can<=mis) {

                if(Restrictions(mis,can,"left")){
                    /*setMissionariesLeft(getMissionariesLeft()+mis);
                    setCannibalsLeft(getCannibalsLeft()+can);
                    setCannibalsRight(getCannibalsRight()-can);
                    setMissionariesRight(getMissionariesRight()-can);*/
                    System.out.println("mis to move:" + mis);
                    System.out.println("can to move:" + can);
                    setCannibalsRight(this.cannibalsRight-can);
                    System.out.println("can right" + getCannibalsRight());
                    setMissionariesRight(this.missionariesRight-mis);
                    System.out.println("mis right" + getCannibalsRight());
                    setCannibalsLeft(this.cannibalsLeft+can);
                    System.out.println("can left" + getCannibalsLeft());
                    setMissionariesLeft(this.missionariesLeft+mis);
                    System.out.println("mis left" + getMissionariesLeft());
                    this.coastOfBoat = "left";
                    child.setFather(this);
                    child.heuristic();
                    children.add(child);
                    System.out.println("mvleft "+getCoastOfBoat());
                    //this.score++;
                    return true;
                }
            }
        }

        return false;
    }
    public boolean Restrictions(int numOfMissionaries,int numOfCannibals,String move){
        if((numOfCannibals+numOfMissionaries) > getSpaceOfBoat()) return false;
        if(move.equals("left")) {
            if ((getMissionariesRight() - numOfMissionaries) < (getCannibalsRight() - numOfCannibals)) return false;
        }
        else {
            if((getMissionariesLeft()-numOfMissionaries) < (getCannibalsLeft()-numOfCannibals)) return false;
        }
        if(getSpaceOfBoat() >= 2){
            if(numOfMissionaries < numOfCannibals) return false;
        }
        if(numOfCannibals == 0 || numOfMissionaries == 0) return false;
        return true;
    }


    boolean isMovable(){
        return !isFinal();
    }


    private void heuristic(){
            int peopleInLeft = getCannibalsLeft()+getMissionariesLeft();
            System.out.println("people in left" + peopleInLeft);
            if(peopleInLeft<=getSpaceOfBoat() && !isFinal()){
                setScore(this.score++);
                System.out.println("score//" + getScore());
            }
            if(peopleInLeft>getSpaceOfBoat() && !isFinal()){
                setScore(3*peopleInLeft-2);
                System.out.println("score//" + getScore());
            }
    }


    @Override
    public int compareTo(State state) {
        return Integer.compare(this.score,state.getScore());
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
                spaceOfBoat == state.spaceOfBoat &&
                maxNumOfRoutes == state.maxNumOfRoutes &&
                numOfPeople == state.numOfPeople &&
                coastOfBoat.equals(state.coastOfBoat) &&
                father.equals(state.father);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cannibalsRight, missionariesRight, cannibalsLeft, missionariesLeft, score, spaceOfBoat, maxNumOfRoutes, numOfPeople, startPosition, coastOfBoat, father);
    }
}
