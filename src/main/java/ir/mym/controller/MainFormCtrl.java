package ir.mym.controller;


import ir.mym.model.Member;
import ir.mym.model.MemberDAO;
import ir.mym.model.Session;
import ir.mym.model.TodayTraining;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by mym on 1/31/17.
 */
public class MainFormCtrl {


    public Button trainingBtn;
    public TextField membershipIdInput;

    @FXML
    public TableView<TodayTraining> todayTrainingTbl;
    @FXML
    public TableColumn<TodayTraining, String> membershipTypeCol;
    @FXML
    public TableColumn<TodayTraining, String> exitTimeCol;
    @FXML
    public TableColumn<TodayTraining, String> enterTimeCol;
    @FXML
    public TableColumn<TodayTraining, String> nameCol;

    public TextField melliCodeInput;
    public TextField nameInput;

    private ObservableList<TodayTraining> trainingsDS = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        enterTimeCol.setCellValueFactory(cellData -> cellData.getValue().enterTimeProperty());
        exitTimeCol.setCellValueFactory(cellData -> cellData.getValue().exitTimeProperty());
        membershipTypeCol.setCellValueFactory(cellData -> cellData.getValue().membershipTypeProperty());


        Session session = new Session();
        session.commodeNo = 10;
        session.member = new Member("علی", "محمدی");
        session.date = new Date(System.nanoTime());
        session.entranceTime = new Time(System.nanoTime());
        session.exitTime = new Time(System.nanoTime());
        trainingsDS.add(new TodayTraining(session));
        trainingsDS.add(new TodayTraining(session));

        todayTrainingTbl.getItems().addAll(trainingsDS);

    }

    public void trainingClk(ActionEvent actionEvent) {
        boolean found = false;
        String takhtiID = membershipIdInput.getText().trim();
        String melliCode = melliCodeInput.getText().trim();
        String name = nameInput.getText().trim();
        Member member = null;
        if (takhtiID.length() != 0){
            int id;
            try {
                id = Integer.parseInt(takhtiID);
            }catch (NumberFormatException ex){
                return;
            }
            MemberDAO dao = new MemberDAO();
            member = dao.findByMembershipID(id);
            found = true;
        }
        if (!found && melliCode.length() != 0){
            MemberDAO dao = new MemberDAO();
            member = dao.findByMelliCode(melliCode);
            found = true;
        }
        if (!found && name.length() != 0){
            MemberDAO dao = new MemberDAO();
            member = dao.findByFamily(name);
            found = true;
        }
        if (member != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("takhti_training.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                TrainingCtrl trainingCtrl = fxmlLoader.<TrainingCtrl>getController();
                trainingCtrl.setMember(member);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
//
//        Session session = new Session();
//        session.member = new Member("مرتضی", "یوسفی مقدم");
//        session.date = new Date(System.nanoTime());
//        session.entranceTime = new Time(System.nanoTime());
//        session.exitTime = new Time(System.nanoTime());
//        trainingsDS.add(new TodayTraining(session));
//
//        todayTrainingTbl.getItems().add(trainingsDS.get(trainingsDS.size() - 1));

    }

    public void buyClk(ActionEvent actionEvent) {
        trainingsDS.get(trainingsDS.size() - 1).setMembershipType("جلسه‌ای");
    }

    public void registerClk(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("takhti_register.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
