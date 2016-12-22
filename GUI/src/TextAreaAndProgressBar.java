import javax.swing.*;
import java.awt.*;

/**
 * Created by Aakash on 12/15/2016.
 */
public class TextAreaAndProgressBar extends JPanel {

    static private JTextArea output;
    private JProgressBar progressBar;
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

}
