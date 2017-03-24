package ir.mym.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by mym on 3/23/17.
 */
public class MemberDAO {

    public ArrayList<Member> findAll() {
        return null;
    }

    public boolean exists(String identificationCode) {
        Connection connection = Connector.getConnection();
        PreparedStatement stm;
        int cnt = 0;
        try {
            stm = connection.prepareStatement("select COUNT(*) from member where melliCode = ?");

            stm.setString(1, identificationCode);
            ResultSet resultSet = stm.executeQuery();
            resultSet.next();
            cnt = resultSet.getInt(1);
            stm.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return cnt != 0;
    }

    public int getId(String identificationCode) {
        Connection connection = Connector.getConnection();
        PreparedStatement stm;
        int id = 0;
        try {
            stm = connection.prepareStatement("select id from member where melliCode = ?");

            stm.setString(1, identificationCode);
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

    public ArrayList<Member> findByName() {
        return null;
    }

    public int insert(Member member) {
        int id;
        Connection con = Connector.getConnection();
        try {
            PreparedStatement stm = con.prepareStatement("INSERT INTO member(fname, lname, melliCode, phoneNo, birthYear, telegramID) " +
                    "VALUES (?,?,?,?,?,?)");
            stm.setString(1, member.getFirstname());
            stm.setString(2, member.getLastname());
            stm.setString(3, member.getIdentificationNo());
            stm.setString(4, member.getPhoneNo());
            stm.setInt(5, member.getBirthYear());
            stm.setString(6, member.getTelegramID());
            stm.executeUpdate();
            stm.close();
            con.close();

            id = getId(member.getIdentificationNo());
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return id;
    }

    public boolean update(Member member) {
        return false;
    }

    public boolean delete(Member member) {
        return false;
    }

    private Member extract(ResultSet rs) throws SQLException {
        Member member = new Member(null, null);
        member.setPhoneNo(rs.getString("phoneNo"));
        member.setFirstname(rs.getString("fname"));
        member.setLastname(rs.getString("lname"));
        member.setImagePath(rs.getString("imagePath"));
        member.setTelegramID(rs.getString("telegramId"));
        member.setIdentificationNo(rs.getString("melliCode"));
        member.setMembershipType(rs.getString("membershipType"));
        return member;
    }

    public Member findByMembershipID(int id) {
        Connection connection = Connector.getConnection();
        PreparedStatement stm;
        Member member = null;
        try {
            stm = connection.prepareStatement("select * from member where id = ?");

            stm.setInt(1, id);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()){
                member = extract(resultSet);
            }
            resultSet.close();
            stm.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return member;
    }

    public Member findByMelliCode(String melliCode) {
        return null;
    }

    public Member findByFamily(String name) {
        return null;
    }
}
