package ir.mym;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("takhti_mainForm.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}