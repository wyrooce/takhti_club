package ir.mym.model;


import java.sql.Time;
import java.util.Date;

/**
 * Created by mym on 1/27/17.
 */
public class Session {
    private Member member;
    private long entranceTime;
    private long leavingTime;
    private int commodeNo;
    private Date date;
    private int id;


    public Session(){
        this.date = new Date();
        entranceTime = date.getTime();
    }

    @Override
    public String toString() {
        return "Session{" +
                "member=" + member.firstName +" "+member.lastName +
                ", entranceTime=" + entranceTime +
                ", leavingTime=" + leavingTime +
                ", commodeNo=" + commodeNo +
                ", date=" + date +
                '}';
    }

    public Time TrainDuration(){
        return new Time(leavingTime - entranceTime);
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public long getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(long entranceTime) {
        this.entranceTime = entranceTime;
    }

    public long getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(long leavingTime) {
        this.leavingTime = leavingTime;
    }

    public int getCommodeNo() {
        return commodeNo;
    }

    public void setCommodeNo(int commodeNo) {
        this.commodeNo = commodeNo;
    }

    public java.sql.Date getSQLDate() {
        return new java.sql.Date(date.getTime());
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
