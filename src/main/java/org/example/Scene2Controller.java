package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Scene2Controller implements Initializable {

    @FXML
    private VBox container;
    @FXML
    private Button backButton;
    @FXML
    private Button submitButton;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private final HashMap<TextField, TextField> fieldMap = new HashMap<>();

    private int numberOfTextFields;

    public void setNumberTextFields(int selectedCount) {
        this.numberOfTextFields = selectedCount;
        generateTextFields();
    }

    @FXML
    public void generateTextFields() {
        container.getChildren().clear();
        fieldMap.clear();

        for (int i = 0; i < numberOfTextFields; i++) {
            TextField textField = new TextField();
            textField.setPromptText("Stock " + (i+1));
            textField.setPrefWidth(200);

            TextField weightField = new TextField();
            weightField.setPromptText("Weight");
            weightField.setPrefWidth(100);

            fieldMap.put(textField, weightField);

            HBox row = new HBox(10);
            row.setPadding(new Insets(10, 0, 0, 20));
            row.getChildren().addAll(textField, weightField);
            container.getChildren().add(row);
        }
    }

    public void backToSceneOne(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene1.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void submitToResultScene (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene3.fxml"));
        root = loader.load();

        HashMap<String, Double> portfolio = new HashMap<>();

        for (Map.Entry<TextField, TextField> entry : fieldMap.entrySet()) {
            String stock = entry.getKey().getText().trim();
            double weight = Double.parseDouble(entry.getValue().getText().trim());
            portfolio.put(stock, weight);
        }

        Scene3Controller controller = loader.getController();
        controller.setRawPortfolio(portfolio);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
