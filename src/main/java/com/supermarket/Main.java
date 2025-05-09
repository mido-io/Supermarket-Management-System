package com.supermarket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/supermarket/fxml/login-page.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);

            root.setOnMousePressed((MouseEvent event) -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
                primaryStage.setOpacity(0.8);
            });

            root.setOnMouseReleased((MouseEvent event) -> {
                primaryStage.setOpacity(1.0);
            });

            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setTitle("Supermarket Management System");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Failed to load login-page.fxml.", e);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}