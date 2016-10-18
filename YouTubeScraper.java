package youtubescraping;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ui4j.api.browser.BrowserEngine;
import com.ui4j.api.browser.BrowserFactory;

public class YouTubeScraper {

	public static void main(String[] args) throws IOException, InterruptedException{
		System.out.println("\t\t\t\t\t\t\tYouTube Downloader");
		System.out.println("Enter the song name");
		Scanner scannerIn = new Scanner(System.in);
		String songName = scannerIn.nextLine();
		//creating the url necessary to scrape
		String youTubeURL = "https://www.youtube.com/results?search_query="+songName;
		Document website = Jsoup.connect(youTubeURL).get();
		//closing in on the target,(class name is lockup-title)
		Elements subElements = website.select("h3.yt-lockup-title>a");
		//System.out.println(subdiv);---checking to see if .select worked 
		String[] seplinks = new String[subElements.size()];
		String[] title = new String[subElements.size()];
		int i =0;
		for(Element subElement:subElements)
		{	String subElementHRef = subElement.attr("href");
		//youtube has playlists, and these contail "list" in the URL and we dont need these playlists
			if(subElementHRef.contains("list"))
			{
				continue;
			}
			else{
				//adding the non playlist URL's into my link array
				seplinks[i] = "https://www.youtube.com"+subElement.attr("href");
				title[i] = subElement.text();
				i++;
			}
		}
			for(int j=0; j<seplinks.length;j++){
				System.out.println((j+1)+": "+title[j]);
				//System.out.println(seplinks[j]);---checking to see if the links are proper
			}
			System.out.println("Please enter your choice");
			int choice = scannerIn.nextInt();
			choice--;
			String userchoice = seplinks[choice];
			//System.out.println(userchoice);---final URL selected
			
			
			//-------------Done with Scraping youtube-------------------
			
			
			//Start of scraping the second website
					
			BrowserEngine browser = BrowserFactory.getWebKit();
//Remove comments to stop logging--->//java.util.logging.Logger.getLogger("com.ui4j").setLevel(Level.OFF);
			com.ui4j.api.browser.Page listenToYouTubePage = browser.navigate("http://www.listentoyoutube.com/");
			com.ui4j.api.dom.Document process = listenToYouTubePage.getDocument();
			process.query("input[type='text']").get().setValue(userchoice);
			process.query("input[type='submit']").get().click();
			TimeUnit.SECONDS.sleep(15);
			String listentoyoutubeString = listenToYouTubePage.getDocument().queryAll("div[class='col-lg-8']").toString();
			listentoyoutubeString=listentoyoutubeString.substring(584);
			/*There are three parameters
			 * 1. The Server number
			 * 2.The hash
			 * 3.The file name
			 * so had to get the url and had to keep substrining to get the parameters
			 * 
			 * 
			 * 
			 * */
			listentoyoutubeString=listentoyoutubeString.substring(0,210);
			listentoyoutubeString=listentoyoutubeString.substring(20, 99);
			String[] tempListentoyoutubeString = listentoyoutubeString.split("&");
			String srv = tempListentoyoutubeString[0].substring(3);
			String hash = tempListentoyoutubeString[1].substring(9, 68);
			hash=hash.replace("%",  "");
			/*found a pattern in the final url that includes all the three parameters, so I generated it on my own
			 */
			String finalURL = "http://srv"+srv+".listentoyoutube.com/download/"+hash+"==/"+URLEncoder.encode(title[choice],"UTF-8")+".mp3";
			String tempChoiceString = new String(URLEncoder.encode(title[choice], "UTF-8"));
			tempChoiceString=tempChoiceString.replaceAll("%7C+","");
			tempChoiceString=tempChoiceString.replaceAll("%22", "");
			tempChoiceString=tempChoiceString.replaceAll("%3F", "");
			String finalChoiceString = java.net.URLDecoder.decode(tempChoiceString, "UTF-8");
			//There was an error when the file name was not in the url format, so had to encode and decode it
			URL url = new URL(finalURL);
			String downloadPath = "C:\\Downloads\\"+finalChoiceString+".mp3";
			HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
			long fileSize = httpConnection.getContentLength();
			System.out.println("Size : "+fileSize/1048576f+" mb");
			File file = new File(downloadPath);
			TimeUnit.SECONDS.sleep(5);
			try
			{	System.out.println("Downloading...");
				FileUtils.copyURLToFile(url, abc);
				System.out.println("Download file");
			}
			catch(Exception e)
			{
				System.out.println("Got an IOException: " + e.getMessage());
				System.out.println("Download Failed");
			}
			finally{
				System.exit(0);
			}
			scannerIn.close();
			
	}
}
