package mohammadMokhtari;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mym on 3/9/17.
 */
public class Kelas {
    private Ostad ostad;
    private int zarfiat;
    private int ruz;    //shanbe: 0 ... jome : 6
    private ArrayList<Daneshju> daneshjuha = new ArrayList<Daneshju>();
    private int saat;
    private Dars dars;
    private int shomareKelas;

    public Dars getDars() {
        return dars;
    }

    public void setDars(Dars dars) {
        this.dars = dars;
    }


    public Ostad getOstad() {
        return ostad;
    }

    public void setOstad(Ostad ostad) {
        this.ostad = ostad;
    }

    public int getZarfiat() {
        return zarfiat;
    }

    public void setZarfiat(int zarfiat) {
        this.zarfiat = zarfiat;
    }

    public int getRuz() {
        return ruz;
    }

    public void setRuz(int ruz) {
            this.ruz = ruz;
    }

    public ArrayList<Daneshju> getDaneshjuha() {
        return daneshjuha;
    }

    public void setDaneshjuha(ArrayList<Daneshju> daneshjuha) {
        this.daneshjuha = daneshjuha;
    }

    public int getSaat() {
        return saat;
    }

    public void setSaat(int saat) {
        this.saat = saat;
    }

    public int getShomareKelas() {
        return shomareKelas;
    }

    public void setShomareKelas(int shomareKelas) {
        this.shomareKelas = shomareKelas;
    }
}
