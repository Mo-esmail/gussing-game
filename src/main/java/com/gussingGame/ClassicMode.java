package com.xo;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ClassicMode extends GameMode {
    //a label contains the difficulty
    private Label difficultylbl = new Label();
    //create a VBox to contain the game
    private VBox gameInstance=new VBox();
    //the default constructor
    public ClassicMode(){
        //this method inherited from GameMode
        buildGame(gameInstance);
    }
    //adds the gameInstance to the passed container
    @Override
    protected void getGameInstance(VBox mode){
        mode.getChildren().addAll(gameInstance);
    }

    @Override
    protected HBox getStatusBox() {
        //create the needed VBox and HBox to set the layout
        HBox h = new HBox();
        VBox x=new VBox();
        HBox statusBox = new HBox();
        //add labels and set spacing
        difficultyBox.getChildren().addAll(new Label("Difficulty: "),difficultylbl);
        statusBox.getChildren().addAll(difficultyBox,guesseslbl);
        statusBox.setSpacing(50);
        x.getChildren().addAll(statusBox,new Label(" "));
        x.setSpacing(10);
        h.getChildren().add(x);
        return h;
    }

    @Override
    protected void difficultyChanges(){
        //calculate the range based on difficulty
        range= (int) Math.pow(10,(difficulty/3)+1);
        //add the minimum tries needed to win
        currentGuesses=(int)(Math.log(range)/Math.log(2))+7-difficulty;
        //update range label
        rangelbl.setText("I'm thinking of a number in range of 1 to "+range);
        //set the text in difficultylbl and it's color according to the current difficulty
        if(difficulty/3==0) {
            difficultylbl.setText("Easy " + (difficulty % 3 + 1));
            difficultylbl.setTextFill(Color.web("#008000"));
        }else if(difficulty/3==1) {
            difficultylbl.setText("Medium " + (difficulty % 3 + 1));
            difficultylbl.setTextFill(Color.web("#FFB000"));
        }else if(difficulty/3==2) {
            difficultylbl.setText("Hard " + (difficulty % 3 + 1));
            difficultylbl.setTextFill(Color.web("#FF0000"));
        }else {
            difficultylbl.setText("Impossible " + (difficulty - 8));
            difficultylbl.setStyle("-fx-font-weight: bold; -fx-text-fill: Black;");
        }
        //set a new random num
        num= ((int)(Math.random()*range))+1;
        System.out.println(num);
        //update current guesses label
        guesseslbl.setText("You have "+currentGuesses+" left");
    }
}
