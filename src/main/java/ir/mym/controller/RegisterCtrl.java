package ir.mym.controller;

import ir.mym.Besmellah;
import ir.mym.model.Member;
import ir.mym.model.MemberDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Created by mym on 3/23/17.
 */
public class RegisterCtrl {

    @FXML
    public ComboBox sexCB;
    public TextField lnameInput;
    public TextField fnameInput;
    public TextField creditInput;
    public TextField telegramInput;
    public TextField phoneInput;
    public TextField melliCodeInput;
    public TextField birthYearInput;
    public CheckBox boxCH;
    public CheckBox airCH;
    public CheckBox monthlyCH;
    public CheckBox payedCH;
    private String creditTemp;

    @FXML
    public void initialize(){
        sexCB.getItems().addAll("زن", "مرد");
        sexCB.getSelectionModel().select(1);
    }

    public void saveClk(ActionEvent actionEvent) throws SQLException {
        String fname = fnameInput.getText().trim();
        String lname = lnameInput.getText().trim();
        String credit = creditInput.getText().trim();
        String telegram = telegramInput.getText().trim();
        String phone = phoneInput.getText().trim();
        String melliCode = melliCodeInput.getText().trim();
        int birthYear;
        try {
             birthYear = Integer.parseInt(birthYearInput.getText().trim());
        }catch (Exception ex){
            ex.printStackTrace();
            birthYear = 0;
        }

        if (lname.length() == 0 || fname.length() == 0 || melliCode.length() == 0)
            return;
        Member member = new Member(null, null);
        if (fname.length() != 0)member.setFirstName(fname);
        if (lname.length() != 0)member.setLastName(lname);
        if (telegram.length() != 0)member.setTelegramId(telegram);
        if (phone.length() != 0)member.setPhoneNo(phone);
        if (melliCode.length() != 0)member.setIdentificationNo(melliCode);
        if (birthYear != 0)member.setBirthYear(birthYear);
        member.setGender(sexCB.getSelectionModel().getSelectedIndex()==1?true:false);
        member.setAerobiotic(airCH.isSelected());
        member.setMonthly(monthlyCH.isSelected());
        member.setBoxer(boxCH.isSelected());

        if (!payedCH.isSelected()) {
            if (credit.length() != 0)member.setCredit(Integer.parseInt(credit));
            if (member.isAerobiotic())member.increaseCredit(-Besmellah.setting.getAreobitic());
            if (member.isBoxer())member.increaseCredit(-Besmellah.setting.getBox());
            if (member.isMonthly())member.increaseCredit(-Besmellah.setting.getShahriye());
        }

        MemberDAO dao = new MemberDAO();
        boolean exists = dao.exists(member.getIdentificationNo());
        if (exists){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("کاربر از پیش وجود دارد");
            alert.showAndWait();
            return;
        }
        int clubId = dao.insert(member);
        member.setClubID(clubId);
        if (member.isMonthly()){
            dao.insertMembershipHistory(member);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("کد عضویت باشگاه: " + clubId);
        alert.setContentText(member.getFullName()+" ذخیره شد");
        alert.showAndWait();

        Stage stage = (Stage) birthYearInput.getScene().getWindow();
        stage.close();
    }

    public void paySelected(ActionEvent actionEvent) {
        if (creditInput.isEditable()){
            creditInput.setEditable(false);
            creditTemp = creditInput.getText();
            creditInput.setText("غیر فعال");
        } else {
            creditInput.setEditable(true);
            creditInput.setText(creditTemp);
        }
    }
}
