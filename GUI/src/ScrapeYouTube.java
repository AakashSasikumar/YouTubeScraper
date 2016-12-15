import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Aakash on 12/15/2016.
 */
public class ScrapeYouTube {
    static ArrayList<String> titles;
    static ArrayList<String> links;

    static void startScrape(String name){
        String youtubeURL = "https://www.youtube.com/results?search_query="+name;
        Document website = null;
        try {
            website = Jsoup.connect(youtubeURL).get();

            TextAreaAndProgressBar.addText("Scraped YouTube successfully, populating table...");
        } catch (IOException e) {
            e.printStackTrace();
            TextAreaAndProgressBar.addText("Error:  Could not scrape YouTube...\nTry Again");
        }
        //closing in on the target,(class name is lockup-title)
        Elements subdiv = website.select("h3.yt-lockup-title>a");
        //System.out.println(subdiv);---checking to see if .select worked
        String[] seperateLinks = new String[subdiv.size()];
        String[] title = new String[subdiv.size()];
        int i =0;
        for(Element temp:subdiv)
        {	String a = temp.attr("href");
            //youtube has playlists, and these contail "list" in the URL and we dont need these playlists
            if(a.contains("list"))
            {
                continue;
            }
            else{
                //adding the non playlist URL's into my link array
                seperateLinks[i] = "https://www.youtube.com"+temp.attr("href");
                title[i] = (i+1)+". "+temp.text();
                i++;
            }
        }
        for(int j=0; j<seperateLinks.length;j++){
            System.out.println(title[j]);
            //System.out.println(seplinks[j]);---checking to see if the links are proper
        }
        titles = new ArrayList<>(Arrays.asList(title));
        links = new ArrayList<>(Arrays.asList(seperateLinks));
        SelectSong.populateTale(titles);

    }
}
