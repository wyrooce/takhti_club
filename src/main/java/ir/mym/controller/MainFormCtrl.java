package ir.mym.controller;


import ir.mym.Besmellah;
import ir.mym.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * Created by mym on 1/31/17.
 */
public class MainFormCtrl {

    public TextField presentMemberCntOutput;
    public TabPane tabs;


    public TextField accCreditOP;
    public TextField accExpireDateOP;
    public TextField accYearIO;
    public TextField accPhoneIO;
    public TextField accTelegramIO;
    public ComboBox accSexCB;
    public TextField searchInput;
    public TextField accLnameIO;
    public TextField accFnameIO;
    public TextField accMelliCodeIO;
    public TextField accClubIdOP;
    public CheckBox accArebioticCH;
    public CheckBox accBoxCH;
    public CheckBox accMonthlyCH;
    public ComboBox searchFieldCB;
    public TableView<SellTransactionModel> todaySellsTbl;
    public TableColumn<SellTransactionModel, String> sellNameCol;
    public TableColumn<SellTransactionModel, String> sellPriceCol;
    public TableColumn<SellTransactionModel, String> sellCountCol;
    public TableColumn<SellTransactionModel, String> sellPayedCol;
    public TableColumn<SellTransactionModel, String> sellProdCol;
    public Text dateString;
    public Text weekDay;
    private Member foundMember;
    private int presentMemberCnt;
    public Button trainingBtn;
    public TextField membershipIdInput;

    @FXML
    public TableView<TodayTrainingModel> todayTrainingTbl;
    @FXML
    public TableColumn<TodayTrainingModel, String> membershipTypeCol;
    @FXML
    public TableColumn<TodayTrainingModel, String> exitTimeCol;
    @FXML
    public TableColumn<TodayTrainingModel, String> enterTimeCol;
    @FXML
    public TableColumn<TodayTrainingModel, String> nameCol;

    public TextField melliCodeInput;
    public TextField nameInput;

    private ObservableList<TodayTrainingModel> trainingsDS = FXCollections.observableArrayList();
    private ObservableList<SellTransactionModel> sellsDS = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        SolarDate date = new SolarDate(new Date());
        weekDay.setText(date.strWeekDay);
        dateString.setText(date.fullString());
        //tab start: table session or training
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        enterTimeCol.setCellValueFactory(cellData -> cellData.getValue().entranceTimeProperty());
        exitTimeCol.setCellValueFactory(cellData -> cellData.getValue().leavingTimeProperty());
        membershipTypeCol.setCellValueFactory(cellData -> cellData.getValue().membershipTypeProperty());
        //tab shopping: table sells
        sellNameCol.setCellValueFactory(cellData -> cellData.getValue().goodNameProperty());
        sellCountCol.setCellValueFactory(cellData -> cellData.getValue().goodCntProperty());
        sellPriceCol.setCellValueFactory(cellData -> cellData.getValue().goodPriceProperty());
        sellPayedCol.setCellValueFactory(cellData -> cellData.getValue().payedProperty());
        sellProdCol.setCellValueFactory(cellData -> cellData.getValue().goodNameProperty());


        tabs.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        if (t.getId() != null && t.getId().equals("5") && foundMember != null) {
                            MemberDAO dao = new MemberDAO();
                            dao.fullUpdate(foundMember);
                            System.out.println("hesab karbari");
                        }

                    }
                }
        );

        //load present Member cnt
        SessionDAO sessionDAO = new SessionDAO();
        ArrayList<Session> sessions = sessionDAO.todaySessions();
        presentMemberCnt = sessionDAO.activeSessionCnt();
        presentMemberCntOutput.setText(presentMemberCnt + "");
        for (Session session : sessions) {
            todayTrainingTbl.getItems().add(new TodayTrainingModel(session));
        }
        //------------------
        AccountingDAO accountingDAO = new AccountingDAO();
        accountingDAO.todayShopping();

        accSexCB.getItems().addAll("زن", "مرد");
        searchFieldCB.getItems().addAll("کد عضویت باشگاه", "کد ملی", "نام خانوادگی", "شماره همراه");
        searchFieldCB.getSelectionModel().selectFirst();
        //search listener
        accPhoneIO.textProperty().addListener((observable, oldValue, newValue) -> {
            if (foundMember != null && newValue != null) {
                foundMember.setPhoneNo(newValue);
            }
        });
        accFnameIO.textProperty().addListener((observable, oldValue, newValue) -> {
            if (foundMember != null && newValue != null) {
                foundMember.setFirstName(newValue);
            }
        });
        accLnameIO.textProperty().addListener((observable, oldValue, newValue) -> {
            if (foundMember != null && newValue != null) {
                foundMember.setLastName(newValue);
            }
        });
        accMelliCodeIO.textProperty().addListener((observable, oldValue, newValue) -> {
            if (foundMember != null && newValue != null) {
                foundMember.setIdentificationNo(newValue);
            }
        });
        accTelegramIO.textProperty().addListener((observable, oldValue, newValue) -> {
            if (foundMember != null && newValue != null) {
                foundMember.setTelegramId(newValue);
            }
        });
        accYearIO.textProperty().addListener((observable, oldValue, newValue) -> {
            if (foundMember != null && newValue != null) {
                try {
                    int newVal = Integer.parseInt(newValue);
                    foundMember.setBirthYear(newVal);
                }catch (NumberFormatException ex){
                    ex.printStackTrace();
                    return;
                }
            }
        });



    }

    public void trainingClk(ActionEvent actionEvent) {
        boolean found = false;
        ArrayList<Member> foundMembers = new ArrayList<>();
        String clubID = membershipIdInput.getText().trim();
        String melliCode = melliCodeInput.getText().trim();
        String name = nameInput.getText().trim();
        Member member = null;

        if (clubID.length() != 0) {
            int id;
            try {
                id = Integer.parseInt(clubID);
            } catch (NumberFormatException ex) {
                System.out.println("invalid input");
                return;
            }
            MemberDAO dao = new MemberDAO();
            member = dao.findByClubID(id);
            if (member == null) return;
            foundMembers.add(member);
            found = true;
        }
        if (!found && melliCode.length() != 0) {
            MemberDAO dao = new MemberDAO();
            member = dao.findByMelliCode(melliCode);
            if (member == null) return;
            foundMembers.add(member);
            found = true;
        }
        if (!found && name.length() != 0) {
            MemberDAO dao = new MemberDAO();
            foundMembers = dao.findByFamily(name);
            if (foundMembers.size() != 0) {
                found = true;
            }

        }
        if (found) {
            try {
                SessionDAO dao = new SessionDAO();
                ArrayList<Integer> emptyCommodes = dao.getEmptyCommodes();
//                System.out.println(emptyCommodes);
                Dialog<ArrayList<Integer>> dialog = enterTrainingDialog(foundMembers, emptyCommodes);
                Optional<ArrayList<Integer>> result = dialog.showAndWait();

                if (result.isPresent() && result.get().size() != 0) {
                    int res = result.get().get(0);
                    if (res != -1) {
                        Session session = new Session();
                        session.setMember(foundMembers.get(res));
                        session.setCommodeNo(result.get().get(1));
                        TodayTrainingModel tt = new TodayTrainingModel(session);
                        dao.insert(session);//bedune saate khoruj

                        presentMemberCnt++;
                        presentMemberCntOutput.setText(presentMemberCnt + "");
                        trainingsDS.add(tt);
                        todayTrainingTbl.getItems().add(tt);
                    }
                }
                dialog.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("not found");
        }
    }

    public void buyClk(ActionEvent actionEvent) {
//        trainingsDS.get(trainingsDS.size() - 1).setMonthly("جلسه‌ای");
        trainingsDS.get(trainingsDS.size() - 1).setMembershipType("جلسه‌ای");
    }

    public void registerClk(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("takhti_register.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Dialog<ArrayList<Integer>> enterTrainingDialog(ArrayList<Member> mems, ArrayList<Integer> commodes) throws IOException {
        Dialog<ArrayList<Integer>> dialog = new Dialog<>();
        ArrayList<Integer> arr = new ArrayList<>();
        dialog.setTitle("شروع تمرین");
        dialog.setHeaderText("سلام");
        dialog.setResizable(false);

        Label label1 = new Label("آیا تایید می‌کنید؟");
        ComboBox<String> nameCB = new ComboBox<>();
        for (Member member : mems) {
            nameCB.getItems().add(member.getFullName());
        }
        nameCB.getSelectionModel().selectFirst();

        Time time = new Time(new Date().getTime());
        Text timeTxt = new Text(time + "");
        nameCB.setVisibleRowCount(5);

        ComboBox<Integer> commodeCB = new ComboBox<>();
        commodeCB.getItems().addAll(commodes);

        commodeCB.getSelectionModel().selectFirst();
        ImageView image = new ImageView();


        GridPane grid = new GridPane();
        grid.add(image, 1, 1);
        grid.add(label1, 1, 2);
        grid.add(nameCB, 1, 3);
        grid.add(timeTxt, 1, 5);
        grid.add(commodeCB, 1, 4);
        grid.setHgap(5);
        grid.setVgap(5);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeConfirm = new ButtonType("تایید", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeIgnore = new ButtonType("بی‌خیال", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeConfirm);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeIgnore);

        dialog.setResultConverter(b -> {
            if (b == buttonTypeConfirm) {
                arr.add(nameCB.getSelectionModel().getSelectedIndex());
                arr.add(commodeCB.getSelectionModel().getSelectedItem());
            } else if (b == buttonTypeIgnore) {
                return arr;
            }
            return arr;
        });
        return dialog;
    }

    private Dialog<Integer> leaveTrainingDialog(Session session) {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("خروج از باشگاه");
        dialog.setHeaderText(session.getMember().getFullName());
        dialog.setContentText("آیا تایید می‌کنید؟");
        dialog.setResizable(false);
//        Text nameTxt = new Text(session.getMember().getFullName());
        Text commodeTxt = new Text("شماره‌ی کمد: " + session.getCommodeNo());

        Text timeTxt = new Text("مدت تمرین: " + SolarDate.diffTime(session.getEntranceTime(), session.getLeavingTime()));

        CheckBox payCH = new CheckBox("پرداخت");
        ImageView image = new ImageView();


        GridPane grid = new GridPane();
        grid.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        grid.add(image, 1, 1);
//        grid.add(nameTxt, 1, 2);
        grid.add(commodeTxt, 1, 3);
        grid.add(timeTxt, 1, 5);
        if (!session.getMember().isMonthly())
            grid.add(payCH, 1, 6);
        grid.setHgap(5);
        grid.setVgap(5);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeConfirm = new ButtonType("تایید", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeIgnore = new ButtonType("بی‌خیال", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeConfirm);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeIgnore);

        dialog.setResultConverter(b -> {
            if (b == buttonTypeConfirm) {
                if (payCH.isSelected()) return 1;
                else return 0;
            } else if (b == buttonTypeIgnore) {
                return -1;
            }
            return -1;
        });
        return dialog;
    }

    public void leavingClubClk(ActionEvent actionEvent) {
        TodayTrainingModel tt = todayTrainingTbl.getSelectionModel().getSelectedItem();
        if (tt != null && !tt.leaved()) {
            Session session = tt.getSession();
            session.setLeavingTime(new Date().getTime());
            Dialog<Integer> exit = leaveTrainingDialog(session);
            Optional<Integer> payed = exit.showAndWait();

            if (payed.get() == 0 && !session.getMember().isMonthly()) {
                session.getMember().increaseCredit(-Besmellah.setting.getJalase());//-- pardakht nashode
            } else if (payed.get() == -1) {
                return;
            }
            tt.setLeavingTime(session.getLeavingTime());

            SessionDAO dao = new SessionDAO();
            dao.update(session.getId(), session.getLeavingTime());
            MemberDAO memberDAO = new MemberDAO();
            memberDAO.updateCredit(session.getMember());

            presentMemberCnt--;
            presentMemberCntOutput.setText(presentMemberCnt + "");
        }
    }

    public void searchClk(ActionEvent actionEvent) {
        MemberDAO dao = new MemberDAO();
        if (foundMember != null) {
            dao.fullUpdate(foundMember);
        }
        ArrayList<Member> members = new ArrayList<>();
        int id = 0, idx = searchFieldCB.getSelectionModel().getSelectedIndex();
        String key = searchInput.getText().trim();

        if (key.length() == 0) return;
        try {
            id = Integer.parseInt(key);
        } catch (NumberFormatException ex) {
            System.out.println("NFEx");
            if (idx != 2 && idx != 3) return;
        }
        switch (idx) {
            case 0:
                members.add(dao.findByClubID(id));
                break;
            case 1:
                members.add(dao.findByMelliCode(key));
                break;
            case 2:
                members = dao.findByFamily(key);
                break;
            case 3:
                members.add(dao.findByPhone(key));
        }
        if (members.size() > 1) {
            Dialog<Member> dialog = foundMembersDialog(members);
            Optional<Member> res = dialog.showAndWait();
            if (res.isPresent()) {
                foundMember = res.get();
            }
        } else foundMember = members.get(0);
        if (foundMember == null) return;
        if (members.size() > 0) {
            accFnameIO.setText(foundMember.getFirstName());
            accLnameIO.setText(foundMember.getLastName());
            accMelliCodeIO.setText(foundMember.getIdentificationNo());
            accPhoneIO.setText(foundMember.getPhoneNo());
            accClubIdOP.setText(foundMember.getClubID() + "");

            accArebioticCH.setSelected(foundMember.isAerobiotic());
            accMonthlyCH.setSelected(foundMember.isMonthly());
            accBoxCH.setSelected(foundMember.isBoxer());
            accCreditOP.setText(foundMember.getCredit() + "");
            accPhoneIO.setText(foundMember.getPhoneNo());
            accTelegramIO.setText(foundMember.getTelegramId());
            accYearIO.setText(foundMember.getBirthYear() + "");
            if (foundMember.isMonthly()) {
                SolarDate sd = new SolarDate(foundMember.getExpireMembershipDate());
                System.out.println(foundMember.getExpireMembershipDate());
                accExpireDateOP.setText(sd.toString());
            } else accExpireDateOP.setText("جلسه‌ای");
            accCreditOP.setText(foundMember.getCredit() + "");

            accSexCB.getSelectionModel().select(foundMember.getGender() ? 1 : 0);
        }

    }

    public void changeAvatarClk(ActionEvent actionEvent) {
        System.out.println("change Avatar");
    }

    public Dialog<Member> foundMembersDialog(ArrayList<Member> mems) {

        Dialog<Member> dialog = new Dialog<>();
        dialog.setTitle("انتخاب شخص");
        dialog.setHeaderText("چند نفر پیدا شندند، یکی را انتخاب کنید");
        dialog.setResizable(false);

        ComboBox<String> nameCB = new ComboBox<>();
        for (Member member : mems) {
            nameCB.getItems().add(member.getFullName());
        }
        nameCB.getSelectionModel().selectFirst();

        nameCB.setVisibleRowCount(5);

        GridPane grid = new GridPane();
        grid.add(nameCB, 1, 3);
        grid.setHgap(5);
        grid.setVgap(5);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeConfirm = new ButtonType("تایید", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeIgnore = new ButtonType("بی‌خیال", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeConfirm);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeIgnore);

        dialog.setResultConverter(b -> {
            if (b == buttonTypeConfirm) {
                int idx = nameCB.getSelectionModel().getSelectedIndex();
                return mems.get(idx);
            } else if (b == buttonTypeIgnore) {
                return null;
            }
            return null;
        });
        return dialog;
    }

    public void accChangeCredit(ActionEvent actionEvent) {
        if (foundMember == null) return;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("تغییر اعتبار");
        dialog.setHeaderText("مبلغ را وارد کنید");


        // Traditional way to get the response value.
        int value = 0;
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                value = Integer.parseInt(result.get());
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
            foundMember.increaseCredit(value);
            accCreditOP.setText(foundMember.getCredit() + "");
            MemberDAO dao = new MemberDAO();
            dao.updateCredit(foundMember);
        }
    }

    public void accGenderChange(ActionEvent actionEvent) {
        if (foundMember == null)return;
        foundMember.setGender(accSexCB.getSelectionModel().getSelectedIndex()==0?false:true);
    }

    public void accAirChange(ActionEvent actionEvent) {
        if (foundMember == null)return;
        int sign = 1;
        if (accArebioticCH.isSelected())
            sign = -1;
        foundMember.increaseCredit(sign * Besmellah.setting.getAreobitic());
        accCreditOP.setText(foundMember.getCredit()+"");
    }

    public void accBoxChange(ActionEvent actionEvent) {
        if (foundMember == null)return;
        int sign = 1;
        if (accBoxCH.isSelected())
            sign = -1;
        foundMember.increaseCredit(sign * Besmellah.setting.getBox());
        accCreditOP.setText(foundMember.getCredit()+"");
    }

    public void accMonthlyChange(ActionEvent actionEvent) {
        if (foundMember == null)return;
        if (accMonthlyCH.isSelected()){
            foundMember.increaseCredit(-Besmellah.setting.getShahriye());
            foundMember.setMonthly(true);
            foundMember.setMembershipDate(new Date());//set expire date
            accExpireDateOP.setText(new SolarDate(foundMember.getExpireMembershipDate())+"");
        }else{

        }
        accCreditOP.setText(foundMember.getCredit()+"");
    }
}



