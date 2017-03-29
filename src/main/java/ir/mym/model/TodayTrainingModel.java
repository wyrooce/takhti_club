package ir.mym.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Time;

/**
 * Created by mym on 1/31/17.
 */
public class TodayTrainingModel {
    private StringProperty name;
    private StringProperty entranceTime;
    private StringProperty leavingTime;
    private StringProperty membershipType;
    private Session session;

    /**
     * Default constructor.
     */


    public TodayTrainingModel(Session session) {
        this.session = session;
        this.name = new SimpleStringProperty(session.getMember().getFullName());
        this.entranceTime = new SimpleStringProperty();
        setEntranceTime(session.getEntranceTime());
        if (session.getLeavingTime() != 0) {
            this.leavingTime = new SimpleStringProperty();
            setLeavingTime(session.getLeavingTime());
        }
        else
            leavingTime = new SimpleStringProperty("");
        if (session.getMember().isMonthly())
            this.membershipType = new SimpleStringProperty("ماهانه");
        else
            membershipType = new SimpleStringProperty("جلسه‌ای");
    }

    @Override
    public String toString() {
        return "TodayTrainingModel{" +
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

    public String getEntranceTime() {
        return entranceTime.get();
    }

    public StringProperty entranceTimeProperty() {
        return entranceTime;
    }

    public void setEntranceTime(long entranceTime) {
        this.entranceTime.set(new Time(entranceTime)+"");
    }

    public String getLeavingTime() {
        return leavingTime.get();
    }

    public StringProperty leavingTimeProperty() {
        return leavingTime;
    }

    public void setLeavingTime(long leavingTime) {
        this.leavingTime.set(new Time(leavingTime)+"");
    }

    public String getMembershipType() {
        return membershipType.get();
    }

    public StringProperty membershipTypeProperty() {
        return membershipType;
    }

    public Session getSession() {
        return session;
    }

    public boolean leaved() {
        return !leavingTime.get().equals("");
    }

    public void setMembershipType(String membershipType) {
        this.membershipType.set(membershipType);
    }
}
