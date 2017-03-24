package ir.mym.controller;

import ir.mym.model.Member;
import ir.mym.model.MemberDAO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by mym on 3/23/17.
 */
public class RegisterCtrl {

    public ComboBox sexCB;
    public TextField lnameInput;
    public TextField fnameInput;
    public TextField creditInput;
    public ComboBox aerobioticCB;
    public TextField telegramInput;
    public TextField phoneInput;
    public ComboBox membershipTypeCB;
    public TextField melliCodeInput;
    public TextField birthYearInput;


    public void saveClk(ActionEvent actionEvent) {
        String fname = fnameInput.getText().trim();
        String lname = lnameInput.getText().trim();
        String credit = creditInput.getText().trim();
        String telegram = telegramInput.getText().trim();
        String phone = phoneInput.getText().trim();
        String melliCode = melliCodeInput.getText().trim();
        String membershipType = (String) membershipTypeCB.getSelectionModel().getSelectedItem();
        int aerobiotic = aerobioticCB.getSelectionModel().getSelectedIndex();

        if (lname.length() == 0 || fname.length() == 0 || melliCode.length() == 0)
            return;
        Member member = new Member(null, null);
        if (fname.length() != 0)member.setFirstname(fname);
        if (lname.length() != 0)member.setLastname(lname);
        if (credit.length() != 0)member.setCredit(Integer.parseInt(credit));
        if (telegram.length() != 0)member.setTelegramID(telegram);
        if (phone.length() != 0)member.setPhoneNo(phone);
        if (melliCode.length() != 0)member.setIdentificationNo(melliCode);
        member.setAerobiotic(aerobiotic==0?true:false);
        member.setMembershipType(membershipType);

        MemberDAO dao = new MemberDAO();
        boolean exists = dao.exists(member.getIdentificationNo());
        if (exists){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("کاربر از پیش وجود دارد");
            alert.showAndWait();
            return;
        }
        int takhtiID = dao.insert(member);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("کد عضویت باشگاه: " + takhtiID);
        alert.setContentText(member.getFirstname()+" "+ member.getLastname()+" ذخیره شد");
        alert.showAndWait();
        Stage stage = (Stage) birthYearInput.getScene().getWindow();
        stage.close();
    }
}
