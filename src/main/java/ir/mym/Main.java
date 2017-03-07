package ir.mym;

import ir.mym.controller.MainFormCtrl;
import ir.mym.model.SolarDate;
import ir.mym.model.TodayTraining;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jdk.nashorn.api.scripting.JSObject;
import org.json.simple.JSONObject;


import java.io.IOException;
import java.util.Date;

/**
 * Created by mym on 1/27/17.
 */
public class Main extends Application{
    public static ObservableList<TodayTraining> briefSession;
    @Override
    public void start(Stage primaryStage) throws Exception{
        briefSession = FXCollections.observableArrayList();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ir/wyrooce/view/takhti_mainForm.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 690, 570));
//        MainFormCtrl controller = loader.getController();
//        controller.setTodayTrainingList(briefSession);
        primaryStage.show();
    }

    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        obj.put("salaam", "salaam2");
        System.out.println(obj.toString());
        launch(args);
    }
}
