module com.example.gussingGame {
    requires javafx.controls;
    requires javafx.fxml;



    exports com.gussingGame;
    opens com.gussingGame to javafx.fxml;
}