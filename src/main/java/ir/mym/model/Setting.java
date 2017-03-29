package ir.mym.model;

import ir.mym.Besmellah;

import java.sql.*;

/**
 * Created by mym on 3/27/17.
 */
public class Setting {
    private int jalase;
    private int shahriye;
    private int commodeCnt;
    private int box;
    private int areobitic;

    public void load() throws SQLException {
        Connection connection = Besmellah.connector.getConnection();
        PreparedStatement stm = connection.prepareStatement("select * from setting");
        ResultSet rs = stm.executeQuery();
        String name, value;

        while (rs.next()){
            name = rs.getString("name");
            value = rs.getString("value");

            if (name.equals("commodeCnt")){
                commodeCnt = Integer.parseInt(value);
            }else if (name.equals("areobiotic")){
                areobitic = Integer.parseInt(value);
            }else if (name.equals("shahriye")){
                shahriye = Integer.parseInt(value);
            }else if (name.equals("box")){
                box = Integer.parseInt(value);
            }else if (name.equals("jalase")){
                jalase = Integer.parseInt(value);
            }
        }
        rs.close();
        stm.close();
        connection.close();
    }

    public int getJalase() {
        return jalase;
    }

    public int getShahriye() {
        return shahriye;
    }

    public int getCommodeCnt() {
        return commodeCnt;
    }

    public int getBox() {
        return box;
    }

    public int getAreobitic() {
        return areobitic;
    }
}
