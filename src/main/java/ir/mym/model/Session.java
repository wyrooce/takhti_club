package ir.mym.model;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by mym on 1/27/17.
 */
public class Session {
    public Member member;
    public Time entranceTime;
    public Time exitTime;
    public int commodeNo;
    public Date date;

    @Override
    public String toString() {
        return "Session{" +
                "member=" + member.firstname+" "+member.lastname +
                ", entranceTime=" + entranceTime +
                ", exitTime=" + exitTime +
                ", commodeNo=" + commodeNo +
                ", date=" + date +
                '}';
    }
}
