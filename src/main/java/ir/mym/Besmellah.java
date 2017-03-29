package ir.mym;

import ir.mym.model.Connector;
import ir.mym.model.Setting;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Besmellah extends Application {
    public static Setting setting;
    public static Connector connector;

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


    public static void main(String[] args) throws SQLException {
        connector = new Connector();
        setting = new Setting();
        setting.load();
        launch(args);
    }
}