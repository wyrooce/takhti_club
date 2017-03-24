package ir.mym.controller;

import ir.mym.model.Member;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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

    }

    public void setMember(Member member) {
        this.member = member;
        nameOutput.setText(member.getFirstname()+" "+member.getLastname());
        timeOutput.setText(String.valueOf(new Time(System.nanoTime())));
    }
}
