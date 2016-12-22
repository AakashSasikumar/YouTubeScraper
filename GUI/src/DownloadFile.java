import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Aakash on 12/15/2016.
 */
public class DownloadFile {
    static void download(String path, String link){
        URL url = null;
        try {
            url = new URL(link);

        } catch (MalformedURLException e) {
            TextAreaAndProgressBar.addText("Woops, internal error occurred, please try again...");
            e.printStackTrace();
        }
        TextAreaAndProgressBar.addText("Downloading at: "+path);
        File file = new File(path);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            TextAreaAndProgressBar.addText("Woops, internal error occurred, please try again...");
            e.printStackTrace();
        }
        try
        {	TextAreaAndProgressBar.addText("Downloading...");
            FileUtils.copyURLToFile(url, file);
            TextAreaAndProgressBar.addText("Download Completed. Enjoy!");
        }
        catch(Exception e)
        {
            TextAreaAndProgressBar.addText("Got an IOException: " + e.getMessage());
            TextAreaAndProgressBar.addText("Download Failed");
        }
    }


}
