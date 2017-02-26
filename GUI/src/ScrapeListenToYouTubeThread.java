import javax.swing.*;

/**
 * Created by Aakash on 12/22/2016.
 */
public class ScrapeYouTubeThread extends SwingWorker<Void, Void>{

    @Override
    protected Void doInBackground() throws Exception {
        ScrapeYouTube.startScrape(SongPanel.songName);
        return null;
    }
}
