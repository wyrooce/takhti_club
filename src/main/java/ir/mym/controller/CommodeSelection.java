package ir.mym.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;

/**
 * Created by mym on 1/28/17.
 */
public class CommodeSelection extends Application{
    private Button[] btns = new Button[50];

    @Override
    public void start(Stage primaryStage) {

        initBtnsArray();
        Group root = new Group();

        root.getChildren().add(getGrid());
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Hello Controller-Array-World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Pane getGrid() {
        int i = 0;
        GridPane gridPane = new GridPane();
        for(Button b : btns) {
            // do something with your button
            // maybe add an EventListener or something
            b.setPrefSize(80, 100);
            b.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("salaam");
                    alert.setHeaderText("commode sit");
                    alert.showAndWait();

                }
            });
//            if ((i/10)%2==0){
//            b.setBackground(new Background(new BackgroundFill(
//                    Color.BLUEVIOLET, CornerRadii.EMPTY, Insets.EMPTY)));
//                b.setBorder(null);
//            }
            gridPane.add(b, i%10, i/10);
            i++;

        }
        return gridPane;
    }

    private void initBtnsArray() {
        for(int i = 0; i < btns.length; i++) {
            btns[i] = new Button(i+1+"\nEmpty");
        }
    }
}
