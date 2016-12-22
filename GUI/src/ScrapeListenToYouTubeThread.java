import javax.swing.*;

/**
 * Created by Aakash on 12/22/2016.
 */
public class ScrapeListenToYouTubeThread extends SwingWorker<Void, Void> {
    static String link;
    @Override
    protected Void doInBackground() throws Exception {
        ScrapeListenToYouTube.startScrape(link);

        return null;
    }
    void setLink(String a){
        link=a;
    }

}
