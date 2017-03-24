package ir.mym.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Created by mym on 1/31/17.
 */
public class TodayTraining {
    private final StringProperty name;
    private final StringProperty enterTime;
    private final StringProperty exitTime;
    private final StringProperty membershipType;

    /**
     * Default constructor.
     */


    public TodayTraining(Session session) {
        this.name = new SimpleStringProperty(session.member.firstname+" "+session.member.lastname);
        this.enterTime = new SimpleStringProperty(session.entranceTime.toString());
        this.exitTime = new SimpleStringProperty(session.exitTime.toString());
        this.membershipType = new SimpleStringProperty(session.member.membershipType);
    }

    @Override
    public String toString() {
        return "TodayTraining{" +
                "name=" + name +
                '}';
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getEnterTime() {
        return enterTime.get();
    }

    public StringProperty enterTimeProperty() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime.set(enterTime);
    }

    public String getExitTime() {
        return exitTime.get();
    }

    public StringProperty exitTimeProperty() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime.set(exitTime);
    }

    public String getMembershipType() {
        return membershipType.get();
    }

    public StringProperty membershipTypeProperty() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType.set(membershipType);
    }
}
