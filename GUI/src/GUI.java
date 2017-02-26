import com.sun.org.apache.bcel.internal.generic.Select;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Aakash on 12/14/2016.
 */
public class GUI extends JFrame {

    private SongPanel songPanel;
    private SelectSong selectSong;
    private JPanel southPart;
    private ChooseAndDownload chooseAndDownload;
    private TextAreaAndProgressBar textAreaAndProgressBar;

    GUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        super("Download");
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        setLayout(new BorderLayout());
        songPanel = new SongPanel();
        selectSong = new SelectSong();
        southPart = new JPanel();
        chooseAndDownload = new ChooseAndDownload();
        textAreaAndProgressBar = new TextAreaAndProgressBar();
        southPart.setLayout(new BorderLayout());
        add(songPanel, BorderLayout.NORTH);
        add(selectSong, BorderLayout.CENTER);
        southPart.add(chooseAndDownload, BorderLayout.NORTH);
        southPart.add(textAreaAndProgressBar, BorderLayout.CENTER);
        add(southPart, BorderLayout.SOUTH);
        setSize(600, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);//to make it centered





    }


}
