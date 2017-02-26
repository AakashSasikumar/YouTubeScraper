import javax.swing.*;
import java.awt.*;

/**
 * Created by Aakash on 12/15/2016.
 */
public class TextAreaAndProgressBar extends JPanel {

    static private JTextArea output;
    static JProgressBar progressBar;
    TextAreaAndProgressBar(){
        setLayout(new BorderLayout());
        output = new JTextArea(20, 40);
        output.setEditable(false);
        progressBar = new JProgressBar();
        add(new JScrollPane(output), BorderLayout.NORTH);
        add(progressBar, BorderLayout.SOUTH);


    }
    static void addText(String text){
        output.append(text+"\n");
    }
    static String returnText(){
        return output.getText();
    }
    static void setLoading(){

       // progressBar=new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);




    }

    public static void startProgressBar() {
        progressBar.setIndeterminate(false);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100000);
        progressBar.setStringPainted(true);


    }

}
