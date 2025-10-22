package com.example.trs_lab1_1;

import com.example.trs_lab1_1.function.FunctionV6;
import com.example.trs_lab1_1.function.IntegralCalculator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

public class HelloController {
    @FXML
    private Label resultLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private TextField startRangeField;
    @FXML
    private TextField endRangeField;
    @FXML
    private TextField stepsField;

    private IntegralCalculator calculator = new IntegralCalculator();

    @FXML
    protected void calculateIntegral() {
        try {
            validation();
            double startRange = Double.parseDouble(startRangeField.getText());
            double endRange = Double.parseDouble(endRangeField.getText());
            int steps = Integer.parseInt(stepsField.getText());

            long startTime = System.nanoTime();
            double result = calculator.calculateIntegralThreads(startRange, endRange, steps, new FunctionV6());
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1_000_000_000.0;

            resultLabel.setText(String.valueOf(result));
            timeLabel.setText(String.valueOf(duration));

        } catch (RuntimeException | ExecutionException
                 | InterruptedException e) {
            showAlert(e.getMessage());
        }

    }

    public void validation() {
        try {
            double startRange = Double.parseDouble(startRangeField.getText());
            double endRange = Double.parseDouble(endRangeField.getText());
            int steps = Integer.parseInt(stepsField.getText());
            if (startRange > endRange)
                throw new RuntimeException("початок не може бути більше кінця");
            if (steps <= 0)
                throw new RuntimeException("кіль-ть кроків має бути додатньою");
        } catch (NumberFormatException e) {
            throw new RuntimeException("некоретний ввід");
        }
    }

    public void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Помилка");
        alert.setContentText(text);
        alert.showAndWait();
    }
}