package com.xo;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ArcadeMode extends GameMode {
    //currentScore stores the score of the currently played game
    private int currentScore=0;
    //get the highScore object
    private HighScore highScore=HighScore.createHighScore();
    //create a label for highScore
    private Label highScorelbl =new Label("High Score : "+highScore);
    //create a label for currentScore
    private Label currentScorelbl =new Label();
    //create a VBox to contain the game
    private VBox gameInstance=new VBox();
    //the default constructor
    public ArcadeMode(){
        //this method inherited from GameMode
        buildGame(gameInstance);
    }
    //adds the gameInstance to the passed container

    protected void getGameInstance(VBox mode){
        mode.getChildren().addAll(gameInstance);
    }
    @Override
    protected HBox getStatusBox() {
        //create the needed VBox and HBox to set the layout
        HBox statusBox = new HBox();
        VBox currnetStatus=new VBox();
        //set style for highScorelbl bold Black font
        highScorelbl.setStyle("-fx-font-weight: bold; -fx-text-fill: Black;");
        //set the guesses label's text
        guesseslbl.setText("you have "+currentGuesses+" guesses left");
        //add labels and set spacing
        currnetStatus.getChildren().addAll(currentScorelbl, guesseslbl);
        currnetStatus.setSpacing(10);
        statusBox.getChildren().addAll(currnetStatus, highScorelbl);
        statusBox.setSpacing(40);
        return statusBox;
    }

    @Override
    protected void difficultyChanges() {
        //calculate the range based on difficulty
        range= (int) Math.pow(10,(difficulty/3)+1);
        //add the minimum tries needed to win
        currentGuesses+=(int)(Math.log(range)/Math.log(2));
        //set a new random num
        num= ((int)(Math.random()*range))+1;
        //the state to prepare the game for the first time
        if(difficulty==0) {
            currentGuesses = 15;
            currentScore=0;
        }else {
            //increase the score based on difficulty
            currentScore+=difficulty*10;
            //update highScore and its label
            highScore.setHighScore(currentScore);
            highScorelbl.setText("High Score : "+highScore);
        }
        //update labels
        currentScorelbl.setText("Current Score : "+currentScore);
        rangelbl.setText("I'm thinking of a number in range of 1 to "+range);
        guesseslbl.setText("You have "+currentGuesses+" left");
    }
}