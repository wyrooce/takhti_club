package ir.mym.controller;

import ir.mym.model.TodayTraining;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by mym on 1/31/17.
 */
public class MainFormCtrl {
    @FXML
    private TableView<TodayTraining> todayTrainingTableView;
    @FXML
    private TableColumn<TodayTraining, String> nameCol;
    @FXML
    private TableColumn<TodayTraining, String> enterTimeCol;
    @FXML
    private TableColumn<TodayTraining, String> exitTimeCol;
    @FXML
    private TableColumn<TodayTraining, String> membershipTypeCol;
    private ObservableList<TodayTraining> todayTrainingList;

    @FXML
    private void initialize() {
        // Initialize the session table with the two columns.
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        enterTimeCol.setCellValueFactory(cellData -> cellData.getValue().enterTimeProperty());
        exitTimeCol.setCellValueFactory(cellData -> cellData.getValue().exitTimeProperty());
        membershipTypeCol.setCellValueFactory(cellData->cellData.getValue().membershipTypeProperty());
    }

    public void setTodayTrainingList(ObservableList<TodayTraining> list){
        this.todayTrainingList = list;
        todayTrainingTableView.setItems(todayTrainingList);
    }

}
