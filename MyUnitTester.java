import javax.swing.*;

/**
 * MyUnitTester
 *
 * Version 1.0
 *
 * Created by Simon Fredriksson (oi15sfn@cs.umu.se) on 2018-11-14.
 *
 * An introduction to reflection in the course Applikationsutveckling i Java (HT18).
 */
public class MyUnitTester
{
    public static void main(String[] args)
    {
        if (args.length != 0)
        {
            System.out.println("Invalid amount of arguments.");
        }
        else
        {
            SwingUtilities.invokeLater(() -> {

                MyUnitTesterGUI gui = new MyUnitTesterGUI("Test");

                gui.show();
            });
        }
    }
}
