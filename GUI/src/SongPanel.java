import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

/**
 * Created by Aakash on 12/15/2016.
 */
public class SongPanel extends JPanel {
    private JLabel song;
    private JTextField enterSong;
    private JButton search;
    static String songName;
    SongPanel(){
        song = new JLabel();
        enterSong = new JTextField("", 30);
        search = new JButton("Search");
        setLayout(new FlowLayout());
        song.setText("Enter Song Name: ");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextAreaAndProgressBar.addText("Scraping YouTube...");
                songName = enterSong.getText();
               // TextAreaAndProgressBar.addText("Scraping YouTube...");

                //ScrapeYouTube.startScrape(songName);
               // System.out.println(song);
                SwingWorker<Void, Void> ob = new ScrapeYouTubeThread();
                ob.execute();
            }
        });
        enterSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextAreaAndProgressBar.addText("Scraping YouTube...");

                songName = enterSong.getText();
                SwingWorker<Void, Void> ob = new ScrapeYouTubeThread();
                ob.execute();

                //ScrapeYouTube.startScrape(songName);
            }
        });
        add(song);
        add(enterSong);
        add(search);
    }
}
