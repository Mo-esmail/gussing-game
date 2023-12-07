package com.xo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{
    //two labels shown in the initial layout of the program
    private Label headerlbl=new Label("Guess the secret number");
    private Label modelbl=new Label("Please select a mode");
    //two toggle buttons used to select the mode to play
    private ToggleButton classicbtn = new ToggleButton("Classic");
    private ToggleButton arcadebtn  = new ToggleButton("Arcade");
    //create an object of each mode to keep the data safe even after deselecting the mode
    private ArcadeMode arcade = new ArcadeMode();
    private ClassicMode classic = new ClassicMode();
    public static void main(String[] args){
        Application.launch(args);
    }
    @Override
    public void start(Stage stage){
        //initializing main VBox
        VBox root = new VBox();
        //initializing mode VBos which will contain the game after selecting a mode
        VBox mode =new VBox();
        //initializing modeSelect which contains the buttons to select the mode
        HBox modeSelect = new HBox();
        modeSelect.getChildren().addAll(new Label("Modes :"),classicbtn,arcadebtn);


        //Setting up the mode toggle buttons
        ToggleGroup modes = new ToggleGroup();
        modes.getToggles().addAll(classicbtn,arcadebtn);
        //displayed when no mode is selected
        mode.getChildren().add(modelbl);
        //The header and the mode box
        root.getChildren().addAll(headerlbl,modeSelect,mode);

        //Toggle buttons event listener
        modes.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) {
                //prevent deselecting by selecting same button again if user tries to deselect
                if (new_toggle == null) {
                    toggle.setSelected(true);
                }else{
                    //get the name of the selected button
                    String toggleBtn = ((ToggleButton)new_toggle).getText();
                    //if its arcadebtn remove the current viewed game and show the arcadeMode in mode VBox
                    if(toggleBtn == arcadebtn.getText()){
                        mode.getChildren().clear();
                        arcade.getGameInstance(mode);
                    }else {
                        //if its classicbtn remove the current viewed game and show the classicMode in mode VBox
                        mode.getChildren().clear();
                        classic.getGameInstance(mode);
                    }
                }
            }
        });
        //shows a message with details about the mode when hove for few seconds
        arcadebtn.setTooltip(
                new Tooltip("Arcade mode:\n\t*start with 15 guesses\n\t*get extra guesses for each right guess\n\t"+
                        "*the more you survive the higher your score\n\t*enjoy breaking others' scores and proving you are the best ")
        );
        classicbtn.setTooltip(
                new Tooltip("Classic mode:\n\t*play in 4 different difficulties\n\t*first 3 each has 3 levels\n\t"+
                        "*the 4th is the impossible difficulty where you test the limits of your luck \n\t*get a certain number of guesses for each level")
        );

        //setting spacing and style up for a better look
        modeSelect.setSpacing(10);
        mode.setSpacing(10);
        headerlbl.setFont(new Font("Arial", 30));
        modelbl.setFont(new Font("Arial", 30));
        root.setMinSize(350, 300);
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        stage.setTitle("Guessing Game");


        //disable resizing the game window
        stage.setResizable(false);
        //set scene and show the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}