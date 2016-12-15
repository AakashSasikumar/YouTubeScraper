import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Aakash on 12/15/2016.
 */
public class SongPanel extends JPanel {
    private JLabel song;
    private JTextField enterSong;
    private JButton search;
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
                String song = enterSong.getText();
               // TextAreaAndProgressBar.addText("Scraping YouTube...");
                ScrapeYouTube.startScrape(song);
               // System.out.println(song);
            }
        });
        enterSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextAreaAndProgressBar.addText("Scraping YouTube...");
                String song = enterSong.getText();

                ScrapeYouTube.startScrape(song);
            }
        });
        add(song);
        add(enterSong);
        add(search);
    }
}
