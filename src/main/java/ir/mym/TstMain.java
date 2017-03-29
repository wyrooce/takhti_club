package ir.mym;

import ir.mym.model.SolarDate;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mym on 1/27/17.
 */

public class TstMain {
    public static void main(String[] args) throws InterruptedException, ParseException {
        String str = "2017-04-28";
        Date d = new Date();
        long ld = d.getTime();
        Date utilDate = new Date(ld);
        java.sql.Date sqlDate = new java.sql.Date(ld);

        System.out.println(SolarDate.toDate(str));
    }

}
