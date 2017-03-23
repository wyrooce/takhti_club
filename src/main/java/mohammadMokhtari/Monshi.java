package mohammadMokhtari;

/**
 * Created by mym on 3/9/17.
 */
public class Monshi extends Person{
    public int id;

    public void createClass(Dars dars, Ostad ostad, int zarfiat,
                            int ruz, int saat, int shomare){
        Kelas newKelas = new Kelas();
        newKelas.setDars(dars);
        newKelas.setOstad(ostad);
        newKelas.setZarfiat(zarfiat);
        newKelas.setSaat(saat);
        newKelas.setRuz(ruz);
        newKelas.setShomareKelas(shomare);

        //insert into db
    }
}
