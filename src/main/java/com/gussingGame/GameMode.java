package com.xo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public abstract class GameMode {
    //create a num to save the random number
    // difficulty to identify the number of win streak you made
    // range to set the range of the random number
    //currentGuesses to identify the number of chances the player has
    protected int num, difficulty = 0, range, currentGuesses;
    //rangelbl contains a message telling the player what the current range is
    protected Label rangelbl = new Label();
    //guesseslbl contains a message telling the player how many guesses he currently has
    protected Label guesseslbl = new Label();
    // TextField is where the user put his guess
    protected TextField input = new TextField();
    //the buttons needed to interact with the game
    private Button submitBtn = new Button("Submit");
    private Button giveUpBtn = new Button("Give Up");
    //difficultybox contain the player's current state
    protected HBox difficultyBox = new HBox();
    //this method is called whenever any subclass is created
    protected void buildGame(VBox mode) {
        //remove the existing game mode
        mode.getChildren().clear();
        //setup the game according to current difficulty
        difficultyChanges();
        //add the game which consists of status box and game body
        mode.getChildren().addAll(getStatusBox(), getGameBody());
    }


    private VBox getGameBody() {

        //initialize the needed components to show the game body
        VBox gameBody = new VBox();
        HBox inputBox = new HBox();
        HBox buttonBox = new HBox();

        //setting style
        buttonBox.setSpacing(100);
        inputBox.setSpacing(20);
        gameBody.setSpacing(10);

        //adding them to the vbox
        inputBox.getChildren().addAll(new Label("Your guess:"), input);
        buttonBox.getChildren().addAll(submitBtn, giveUpBtn);
        gameBody.getChildren().addAll(inputBox,buttonBox,rangelbl);

        //buttons listeners
        giveUpBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //if the player give up he looses all his progress in the game
                difficulty = 0;
                //rum giveUp method
                giveUp(num);
                //setup the game according to the new difficulty
                difficultyChanges();
            }
        });

        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //validate input to submit it
                submitValidation(input.getText());
                //remove the guess from the TextField
                input.setText("");
            }
        });
        //if user press enter submit the number
        input.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitValidation(input.getText());
                input.setText("");
            }
        });
        return gameBody;
    }
    //showAlert creates an alert with custom properties according to parameters' values
    private static void showAlert(String title,String header, String content, int alarmType) {
        Alert alert;
        switch (alarmType) {
            case 2:
                alert = new Alert(Alert.AlertType.ERROR);
                break;
            default:
                alert = new Alert(Alert.AlertType.INFORMATION);
        }
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //validate input before submitting
    private void submitValidation(String guess) {
        //this checks if input is integer
        try {
            int guessNum = Integer.parseInt(guess);
            //this checks if input is in range if not show an alert
            if (guessNum <= range && guessNum > 0)
                submit(guessNum);
            else
                showAlert("Error", "Out of range","The number you entered is out of range", 2);
        } catch (Exception exception) {
            //if input isn't an integer show an alert
            showAlert("Error","Not expected input", "You must enter a number from the defined range to make your guess", 2);
        }
    }
    //if the input passes the validation process this input is called
    private void submit(int guess){
        //find the state of the guess from the correct answer and call a method accordingly
        if(guess<num) {
            wrongGuess("higher");
            currentGuesses--;
        }else if(guess>num) {
            wrongGuess("lower");
            currentGuesses--;
        }else {
            rightGuess(guess);
        }
        //refresh the number of guesses to show the new currentGuesses
        guesseslbl.setText("You have "+currentGuesses+" left");
        //if player is out od guesses reset the difficulty call lose method then refresh the game according to the new difficulty
        if(currentGuesses==0){
            difficulty=0;
            lose();
            difficultyChanges();
        }
        //set the text color of the guesseslbl to red if currentGuesses<=3 else set it to black
        if (currentGuesses<=3)
            guesseslbl.setTextFill(Color.web("#FF0000"));
        else
            guesseslbl.setTextFill(Color.web("#000000"));
    }

    //randomly shows a message when a guess is wrong
    private void wrongGuess(String right){
        int message= (int) (Math.random()*3);
        switch (message) {
            case 0:
                showAlert("Try again", "You missed it", "Guess a " + right + " number", 1);
                break;
            case 1:
                showAlert("Try again", "Wrong guess", "May you try a " + right + " number", 1);
                break;
            case 2:
                showAlert("Try again", "Not what's in my mind", "My number is " + right, 1);
                break;
            default:
                showAlert("Try again", "No no", "It's a " + right + " number", 1);
        }
    }

    //when guess is right randomly shows a message
    private void rightGuess(int guess){
        int message= (int) (Math.random()*3);
        switch (message) {
            case 0:
                showAlert("Good Guess","Excellent", "You guessed it",1);
                break;
            case 1:
                showAlert("Good Guess","Exactly", "That's my number",1);
                break;
            case 2:
                showAlert("Good Guess",guess+" !!!", "Yes you did it",1);
                break;
            default:
                showAlert("Good Guess","You are a hero", "That's my secret number",1);
        }
        //increase difficulty and refresh to game according to the new difficulty
        difficulty++;
        difficultyChanges();
    }

    //when lose randomly prints a message
    private void lose(){
        int message= (int) (Math.random()*3);
        switch (message) {
            case 0:
                showAlert("Game over", "Sorry you lost", "better luck next time", 1);
                break;
            case 1:
                showAlert("Game over", "Don't be sad", "I'm sure you'll do better next time", 1);
                break;
            case 2:
                    showAlert("Game over", "Bad luck", "Try again, you will do better", 1);
                break;
            default:
                showAlert("Game over", "No no", "I really hoped you guess it", 1);
        }
    }
    //when giveUp randomly prints a message
    private void giveUp(int num){
        int message= (int) (Math.random()*3);
        switch (message) {
            case 0:
                showAlert("You gave up!", "It's "+num, "Can't see why you gave up on such an easy guess", 1);
                break;
            case 1:
                showAlert("You gave up!", "Why you gave up when you'd have lost anyway", "by the way the number is "+num, 1);
                break;
            case 2:
                if(difficulty>5)
                    showAlert("You gave up!", "Wish you had a little bit longer breath", "It's"+num+"i really hoped you guess it", 1);
                else
                    showAlert(" "+num, "That's a quick match", "Looking for the number it's up there in the title", 1);
                break;
            default:
                showAlert("You gave up!", "Nooooooooo!!!", "It's"+num, 1);
        }
    }

    //the components of status box differ from a mode to another
    protected abstract HBox getStatusBox();

    //what happens when difficulty changes depends on the mode
    protected abstract void difficultyChanges();

    //which instance to be returned depends on the mode
    protected abstract void getGameInstance(VBox mode);
}