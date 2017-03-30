package ir.mym.model;

import ir.mym.Besmellah;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mym on 3/23/17.
 */
public class MemberDAO {

    public ArrayList<Member> findAll() {
        return null;
    }

    public boolean exists(String identificationCode) {
        Connection connection = Besmellah.connector.getConnection();
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
        Connection connection = Besmellah.connector.getConnection();
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

    public void insertMembershipInfo(Member member) {
        Connection con = Besmellah.connector.getConnection();
        try {
            PreparedStatement stm = con.prepareStatement(
                    "INSERT INTO registrationHistory(memberId, aerobitic, boxer, registerDate ) " +
                            "VALUES (?,?,?,?)");
            stm.setInt(1, member.getClubID());
            stm.setBoolean(2, member.isAerobiotic());
            stm.setBoolean(3, member.isBoxer());
            stm.setString(4, member.getRegisterSQLDate() + "");
            stm.executeUpdate();
            stm.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int insert(Member member) {
        int id;
        Connection con = Besmellah.connector.getConnection();
        try {
            PreparedStatement stm = con.prepareStatement(
                    "INSERT INTO member(fname, lname, melliCode, phoneNo, birthYear, telegramID, expireMembershipDate, registerDate, sex, credit) " +
                            "VALUES (?,?,?,?,?,?, ?, ?, ?, ?)");
            stm.setString(1, member.getFirstName());
            stm.setString(2, member.getLastName());
            stm.setString(3, member.getIdentificationNo());
            stm.setString(4, member.getPhoneNo());
            stm.setInt(5, member.getBirthYear());
            stm.setString(6, member.getTelegramId());
            if (!member.isMonthly())
                stm.setNull(7, 1);
            else stm.setString(7, member.getExpireMembershipSQLDate() + "");
            stm.setString(8, member.getRegisterSQLDate() + "");
            stm.setBoolean(9, member.getGender());
            stm.setInt(10, member.getCredit());
//            stm.setBoolean(7, member.isAerobiotic());
//            stm.setBoolean(8, member.isBoxer());
//            stm.setBoolean(9, member.isMonthly());
//            stm.setDate(10, member.getLastMembershipSQLDate());
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

    public void delete(Member member) {
        Connection con = Besmellah.connector.getConnection();
        try {
            PreparedStatement stm = con.prepareStatement(
                    "delete from member where id = ?");
            stm.setInt(1, member.clubID);
            stm.executeUpdate();
            stm.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Member extract(ResultSet rs) throws SQLException, ParseException {
        Member member = new Member(null, null);
        member.setCredit(rs.getInt("credit"));
        member.setClubID(rs.getInt("id"));
        member.setBirthYear(rs.getInt("birthYear"));
        member.setPhoneNo(rs.getString("phoneNo"));
        member.setFirstName(rs.getString("fname"));
        member.setLastName(rs.getString("lname"));
        member.setImagePath(rs.getString("imagePath"));
        member.setTelegramId(rs.getString("telegramId"));
        member.setIdentificationNo(rs.getString("melliCode"));
        member.setGender(rs.getBoolean("sex"));
        member.setRegisterDate(rs.getDate("registerDate"));
        String tmpDate = rs.getString("expireMembershipDate");
        member.setExpireMembershipDate(SolarDate.toDate(tmpDate));
        if (member.getExpireMembershipDate() != null && member.getExpireMembershipDate().after(new Date()))
            member.setMonthly(true);
        else member.setMonthly(false);
        member = relatedInfo(member);
        return member;
    }

    public Member findByClubID(int id) {
        Connection connection = Besmellah.connector.getConnection();
        PreparedStatement stm;
        Member member = null;
        try {
            stm = connection.prepareStatement("select * from member where id = ?");

            stm.setInt(1, id);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
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
        Connection connection = Besmellah.connector.getConnection();
        PreparedStatement stm;
        Member member = null;
        try {
            stm = connection.prepareStatement("select * from member where melliCode = ?");

            stm.setString(1, melliCode);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
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

    public ArrayList<Member> findByFamily(String name) {
        Connection connection = Besmellah.connector.getConnection();
        ArrayList<Member> mems = new ArrayList<>();
        PreparedStatement stm;
        Member member;
        try {
            stm = connection.prepareStatement("select * from member where lname like ?");

            stm.setString(1, "%" + name + "%");
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                member = extract(resultSet);
                mems.add(member);
            }
            resultSet.close();
            stm.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return mems;
    }

    public ArrayList<Member> presentMembers() {
        return null;
    }

//    public boolean isMonthly(Member member){
//        Connection connection = Besmellah.connector.getConnection();
//        PreparedStatement stm;
//        Member member = null;
//        try {
//            stm = connection.prepareStatement("select * from member where phoneNo like ?");
//
//            stm.setString(1, "%"+phone+"%");
//            ResultSet resultSet = stm.executeQuery();
//            if (resultSet.next()) {
//                member = extract(resultSet);
//            }
//            resultSet.close();
//            stm.close();
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return member;
//    }

    public Member findByPhone(String phone) {
        Connection connection = Besmellah.connector.getConnection();
        PreparedStatement stm;
        Member member = null;
        try {
            stm = connection.prepareStatement("select * from member where phoneNo like ?");

            stm.setString(1, "%" + phone + "%");
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
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

    public void insertMembershipHistory(Member member) throws SQLException {
        Connection connection = Besmellah.connector.getConnection();
        PreparedStatement stm = connection.prepareStatement(
                "insert into registrationHistory(memberId, registerDate, boxer, aerobiotic) values(?, ?, ?, ?)");
        stm.setInt(1, member.getClubID());
        stm.setString(2, member.getRegisterSQLDate() + "");
        stm.setBoolean(3, member.isBoxer());
        stm.setBoolean(4, member.isAerobiotic());

        stm.executeUpdate();
        stm.close();
        connection.close();
    }

    public Member relatedInfo(Member member){
        Connection connection = Besmellah.connector.getConnection();
        PreparedStatement stm;

        try {
            stm = connection.prepareStatement("select * from registrationHistory where memberId = ? and registerDate in( select max(date(registerDate)) from registrationHistory where memberId = ?); ");
            stm.setInt(1, member.getClubID());
            stm.setInt(2, member.getClubID());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                member.setAerobiotic(rs.getBoolean("aerobiotic"));
                member.setBoxer(rs.getBoolean("boxer"));
            }
            rs.close();
            stm.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return member;
    }

    public void updateCredit(Member member) {
        Connection con = Besmellah.connector.getConnection();
        try {
            PreparedStatement stm = con.prepareStatement("UPDATE member set credit = ? WHERE id = ?");
            stm.setInt(1, member.getCredit());
            stm.setInt(2, member.getClubID());

            stm.executeUpdate();

            stm.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fullUpdate(Member member) {
        System.out.println("fullUp: "+member.getFullName());
        int id = member.getClubID();
        delete(member);
        reinsert(member, id);
//        insertMembershipInfo(member);
//        insertMembershipHistory();
    }

    public void reinsert(Member member, int id){
        Connection con = Besmellah.connector.getConnection();
        try {
            PreparedStatement stm = con.prepareStatement(
                    "INSERT INTO member(fname, lname, melliCode, phoneNo, birthYear, telegramID, expireMembershipDate, registerDate, sex, credit, id) " +
                            "VALUES (?,?,?,?,?,?, ?, ?, ?, ?, ?)");
            stm.setString(1, member.getFirstName());
            stm.setString(2, member.getLastName());
            stm.setString(3, member.getIdentificationNo());
            stm.setString(4, member.getPhoneNo());
            stm.setInt(5, member.getBirthYear());
            stm.setString(6, member.getTelegramId());
            if (!member.isMonthly())
                stm.setNull(7, 1);
            else stm.setString(7, member.getExpireMembershipSQLDate() + "");
            stm.setString(8, member.getRegisterSQLDate() + "");
            stm.setBoolean(9, member.getGender());
            stm.setInt(10, member.getCredit());
            stm.setInt(11, id);

            stm.executeUpdate();
            stm.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
