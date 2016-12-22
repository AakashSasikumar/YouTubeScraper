import com.ui4j.api.browser.BrowserEngine;
import com.ui4j.api.browser.BrowserFactory;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Aakash on 12/15/2016.
 */
public class ScrapeListenToYouTube {
    static String path;
    static String finalDownloadLink;
    static String finalPath;
    static void startScrape(String link){
        TextAreaAndProgressBar.addText("Contacting Servers...\n Please Wait this may take time depending on your internet speed");
        BrowserEngine browser = BrowserFactory.getWebKit();
        //java.util.logging.Logger.getLogger("com.ui4j").setLevel(Level.OFF);
        com.ui4j.api.browser.Page docu = browser.navigate("http://www.listentoyoutube.com/");
        com.ui4j.api.dom.Document process = docu.getDocument();
        process.query("input[type='text']").get().setValue(link);
        process.query("input[type='submit']").get().click();
        //copied over the url and clicked the button
        try {
            TimeUnit.SECONDS.sleep(10);//delay to make it work for slow networks
        } catch (InterruptedException e) {
            TextAreaAndProgressBar.addText("Woops, internal error occurred, please try again...");
            e.printStackTrace();
        }
        String ur =(String) docu.executeScript("window.location.href");
        if(ur.contains("captcha")){
            TextAreaAndProgressBar.addText("Please go to http://www.listentoyoutube.com/captcha.php and prove you're not a robot and run the program again");
            //System.exit(0);
        }
        ur=ur.substring(49, ur.length());
			/*There are three parameters
  			 * 1. The Server number
  			 * 2.The hash
 			 * 3.The file name
 			 * so i got the url and split it at '&'
   			 */
        String[] segments = ur.split("&");
        segments[1]=segments[1].substring(5, segments[1].length());
			/* segments[0] is the server number
			 * segments[1] is the hash code
			 * segments[2] is the file name
			 */
        segments[1]=segments[1].replaceAll("%253D%253D","");
        segments[2]=segments[2].substring(5, segments[2].length());
        String finalur = "http://"+segments[0]+".listentoyoutube.com/download/"+segments[1]+"==/"+segments[2];
        finalDownloadLink=finalur;
        URL url = null;
        try {
            url = new URL(finalur);
        } catch (MalformedURLException e) {
            TextAreaAndProgressBar.addText("Woops, internal error occurred, please try again...");
            e.printStackTrace();
        }
        HttpURLConnection httpConnection = null;//this connection is to get the file size
        try {
            httpConnection = (HttpURLConnection) (url.openConnection());
        } catch (IOException e) {
            TextAreaAndProgressBar.addText("Woops, internal error occurred, please try again...");
            e.printStackTrace();
        }
        long fileSize = httpConnection.getContentLength();
        TextAreaAndProgressBar.addText(ScrapeYouTube.titles.get(SelectSong.selectionTable.getSelectedRow())+"\t\t\t Total Size : "+fileSize/1048576f+" mb");//----this will calcluate the total file size in mb
        segments[2]=segments[2].replace("%7C", "");
        segments[2]=segments[2].replace("%22", "");
        segments[2]=segments[2].replace("%3F", "");
        try {
            segments[2]=java.net.URLDecoder.decode(segments[2], "UTF-8");//apache commons doesnt allow ", ?, | to be in the file name
        } catch (UnsupportedEncodingException e) {
            TextAreaAndProgressBar.addText("Woops, internal error occurred, please try again...");
            e.printStackTrace();
        }
        // String path = new String();
        /*JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(new java.io.File("."));
        jfc.setDialogTitle("Choose where you wanna download");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int fileResult = jfc.showOpenDialog(null);
        if (fileResult == JFileChooser.APPROVE_OPTION) {
            path = jfc.getSelectedFile().getPath();
            System.out.println(path);

        }*/
        String path1 = path+"\\"+segments[2];//downloads in the file where the jar file is located
        //path=path+"\\"+segments[2]+"";
        finalPath=path1;
        SwingWorker<Void, Void> ob = new DownloadThread();
        ob.execute();




    }
}
