package com.xo;

//this class is used to store the high score and give it to any object that requests it
public class HighScore {
    //a variable to store high score
    private int highscorenum=0;
    //this variable stores an instance on this class
    private static HighScore highScore;
    //this is done to prevent any other class from creating a new HighScore object
    private HighScore(){}
    //this method is used to get a HighScore object
    protected static HighScore createHighScore(){
        //the method returns the already saved HighScore object and creates a new one only if the existed one = null
        if(highScore==null)
            highScore=new HighScore();
        return highScore;
    }
    //change highScore value is changing the value variable inside the object
    protected void setHighScore(int high){
        if(high>highscorenum)
            highscorenum=high;
    }

    //this overrides the value of HighScore object when turned to String
    @Override
    public String toString() {
        return String.valueOf(highscorenum);
    }
}

