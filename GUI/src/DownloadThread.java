import javax.swing.*;

/**
 * Created by Aakash on 12/17/2016.
 */
public class DownloadThread extends SwingWorker<Void, Void> {

    @Override
    protected Void doInBackground() throws Exception {

            DownloadFile.download(ScrapeListenToYouTube.finalPath, ScrapeListenToYouTube.finalDownloadLink);

        return null;
    }



}
