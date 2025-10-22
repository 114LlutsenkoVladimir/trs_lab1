module com.example.trs_lab1_1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.trs_lab1_1 to javafx.fxml;
    exports com.example.trs_lab1_1;
}