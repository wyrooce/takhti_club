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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Optional;

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
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("takhti_training.fxml"));
//                Parent root = (Parent) fxmlLoader.load();
//                TrainingCtrl trainingCtrl = fxmlLoader.<TrainingCtrl>getController();
//                trainingCtrl.setMember(member);
//                Stage stage = new Stage();
//                stage.setScene(new Scene(root));
//                stage.show();
                ArrayList<Member> foundMembers = new ArrayList<>();
                foundMembers.add(member);
                Dialog<Integer> dialog = enterTraining(foundMembers);
                Optional<Integer> result = dialog.showAndWait();
                if (result.isPresent()) {
                    int res = result.get();
                    if (res != -1){
                        Session session = new Session();
                        session.member = foundMembers.get(res);
                        session.entranceTime = new Time(System.currentTimeMillis());
                        TodayTraining tt = new TodayTraining(session);

                        trainingsDS.add(tt);
                        todayTrainingTbl.getItems().add(tt);
                    }
                }
                dialog.close();
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

    private Dialog<Integer> enterTraining(ArrayList<Member> mems) throws IOException {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("شروع تمرین");
        dialog.setHeaderText("سلام");
        dialog.setResizable(false);

        Label label1 = new Label("آیا تایید می‌کنید؟");
        ComboBox<String> name = new ComboBox<>();
        Text time = new Text(System.currentTimeMillis()+"");
        name.setPromptText(mems.get(0).getFirstname()+" "+mems.get(0).getLastname());
        ComboBox<Integer> commode = new ComboBox<>();
        commode.setPromptText(1+"");
        ImageView image = new ImageView();


        GridPane grid = new GridPane();
        grid.add(image, 1, 1);
        grid.add(label1, 1, 2);
        grid.add(name, 1, 3);
        grid.add(time, 1, 5);
        grid.add(commode, 1, 4);
        grid.setHgap(5);
        grid.setVgap(5);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeConfirm = new ButtonType("تایید", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeIgnore = new ButtonType("بی‌خیال", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeConfirm);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeIgnore);

        dialog.setResultConverter(new Callback<ButtonType, Integer>() {
            @Override
            public Integer call(ButtonType b) {
                if (b == buttonTypeConfirm){
                    return 0;
                }else if (b == buttonTypeIgnore){
                    return -1;
                }
                return -1;
            }
        });
        return dialog;
    }
}
