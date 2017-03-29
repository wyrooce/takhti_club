package ir.mym.model;

import ir.mym.Besmellah;

import java.beans.beancontext.BeanContextServicesSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by mym on 3/26/17.
 */
public class SessionDAO {

    private static int activeSession;

    public ArrayList<Integer> getEmptyCommodes() {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> resList = new ArrayList<>();
        Connection con = Besmellah.connector.getConnection();
        try {
            PreparedStatement stm = con.prepareStatement(
                    "select commodeNo from session where date(sessionDate) = date('NOW') and leavingTime = 0");
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getInt(1));
            }
            stm.close();
            con.close();

            int commodeCnt = Besmellah.setting.getCommodeCnt();
            for (int i = 1; i <= commodeCnt; i++) {
                if (!list.contains(i))
                    resList.add(i);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return resList;
    }

    public int activeSessionCnt() {
        return activeSession;
    }

    public int insert(Session session) {
        activeSession++;
        Connection con = Besmellah.connector.getConnection();
        int id;
        try {
            PreparedStatement stm = con.prepareStatement("INSERT INTO session(memberID, entranceTime, leavingTime, sessionDate, commodeNo) " +
                    "VALUES (?, ?, ?, ?, ?)");
            stm.setInt(1, session.getMember().getClubID());
            stm.setString(2, session.getEntranceTime() + "");
            stm.setString(3, session.getLeavingTime() + "");
            stm.setString(4, session.getSQLDate().toString());
            stm.setInt(5, session.getCommodeNo());

            stm.executeUpdate();

            stm.close();
            con.close();

            id = getId();
            session.setId(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return id;
    }

    public boolean update(int id, long leavingTime) {
        Connection con = Besmellah.connector.getConnection();
        try {
            PreparedStatement stm = con.prepareStatement("UPDATE session set leavingTime = ? WHERE id = ?");
            stm.setLong(1, leavingTime);
            stm.setInt(2, id);

            stm.executeUpdate();

            stm.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        activeSession--;
        return true;
    }

    private int getId() {
        Connection connection = Besmellah.connector.getConnection();
        PreparedStatement stm;
        int id = 0;
        try {
            stm = connection.prepareStatement("SELECT SEQ from sqlite_sequence WHERE name='session'");
            ResultSet resultSet = stm.executeQuery();
            resultSet.next();
            id = resultSet.getInt(1);
            stm.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return id;
    }

    public ArrayList<Session> todaySessions() {
        activeSession = 0;
        ArrayList<Session> list = new ArrayList<>();
        Connection con = Besmellah.connector.getConnection();
        Session session;
        try {
            PreparedStatement stm = con.prepareStatement(
                    "select * from session where date(sessionDate) = date('NOW')");
            ResultSet resultSet = stm.executeQuery();
            MemberDAO dao = new MemberDAO();
            Member member;
            while (resultSet.next()) {
                session = extract(resultSet);
                member = dao.findByClubID(resultSet.getInt("memberID"));
                session.setMember(member);
                if (session.getLeavingTime() == 0) activeSession++;
                list.add(session);
            }
            stm.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return list;
    }

    private Session extract(ResultSet rs) throws SQLException {
        Session session = new Session();
        session.setId(rs.getInt("id"));
        session.setCommodeNo(rs.getInt("commodeNo"));
        session.setDate(rs.getDate("sessionDate"));
        session.setEntranceTime(rs.getLong("entranceTime"));
        session.setLeavingTime(rs.getLong("leavingTime"));
//        session.setMember();
        return session;
    }

}


