package mohammadMokhtari;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by mym on 3/9/17.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner input = new Scanner(System.in);
        Monshi monshi = new Monshi();
        String name = JOptionPane.showInputDialog("Estekhdam monshi:\nNam ra vared konid:");
        monshi.setFname(name);
        System.out.println("name: "+ monshi.getFname());
        System.out.println("famile: "+ monshi.getLname());
        JOptionPane.showMessageDialog(null, monshi.getFname());

        PrintStream out = new PrintStream(new FileOutputStream("monshi.mym"));
        out.println("monshi: "+monshi.getFname());
        out.close();
    }

}
