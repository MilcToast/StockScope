package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Objects;

public class Test extends Application{
        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) throws Exception {

            // Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene1.fxml")));
            Scene scene = new Scene(root);
            Image icon = new Image("img.png");
            stage.getIcons().add(icon);

            stage.setScene(scene);
            stage.show();
        }
    }

