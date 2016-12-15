import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Aakash on 12/15/2016.
 */
public class ChooseAndDownload extends JPanel{
    private JButton download;
    private JButton choose;
    private JLabel chooseFile;
    ChooseAndDownload(){
        setLayout(new FlowLayout());
        chooseFile = new JLabel("Choose File: ");

        choose = new JButton("Choose");
        download = new JButton("Download");
        choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = new String();
                JFileChooser jfc = new JFileChooser();
                jfc.setCurrentDirectory(new java.io.File(""));
                jfc.setDialogTitle("Choose Download Location");
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int fileResult = jfc.showOpenDialog(null);
                if (fileResult == JFileChooser.APPROVE_OPTION) {
                    path = jfc.getSelectedFile().getPath();
                    TextAreaAndProgressBar.addText(path);
                    ScrapeListenToYouTube.path=path;
                }
            }
        });
        download.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String a = (String) SelectSong.selectionTable.getValueAt(SelectSong.selectionTable.getSelectedRow(), SelectSong.selectionTable.getSelectedColumn());
                //System.out.println(a);
                int index = SelectSong.selectionTable.getSelectedRow();
                //System.out.println(index);
                String link = ScrapeYouTube.links.get(index);
                TextAreaAndProgressBar.addText("Downloading "+link+"...");
                ScrapeListenToYouTube.startScrape(link);
            }
        });
        add(chooseFile);
        add(choose);
        add(download);
    }
}
