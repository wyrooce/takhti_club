package ir.mym.controller;

import ir.mym.model.Member;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Time;

/**
 * Created by mym on 3/24/17.
 */
public class TrainingCtrl {
    public TextField nameOutput;
    public ComboBox commodeNoCB;
    public TextField timeOutput;
    private Member member;

    public void confirmClk(ActionEvent actionEvent) {

    }

    public void ignoreClk(ActionEvent actionEvent) {
        Stage stage = (Stage) timeOutput.getScene().getWindow();
        stage.close();
    }

    public void setMember(Member member) {
        this.member = member;
        nameOutput.setText(member.getFirstName()+" "+member.getLastName());
        timeOutput.setText(String.valueOf(new Time(System.nanoTime())));
    }
}
