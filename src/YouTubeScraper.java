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
		System.out.println("\t\t\t\t\t\tYouTube Downloader");
		System.out.println("Enter the song name");
		Scanner in = new Scanner(System.in);
		String name = in.nextLine();
		//creating the url necessary to scrape
		String youtubeURL = "https://www.youtube.com/results?search_query="+name;
		Document website = Jsoup.connect(youtubeURL).get();
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
				title[i] = temp.text();
				i++;
			}
		}
			for(int j=0; j<seperateLinks.length;j++){
				System.out.println((j+1)+": "+title[j]);
				//System.out.println(seplinks[j]);---checking to see if the links are proper
			}
			System.out.println("Please enter your choice");
			int choice = in.nextInt();
			choice--;
			String userchoice = seperateLinks[choice];
			//System.out.println(userchoice);---final URL selected
			
			
			//-------------Done with Scraping youtube-------------------
			
			
			//Start of scraping the second website
					
			BrowserEngine browser = BrowserFactory.getWebKit();
			//java.util.logging.Logger.getLogger("com.ui4j").setLevel(Level.OFF);
			com.ui4j.api.browser.Page docu = browser.navigate("http://www.listentoyoutube.com/");
			com.ui4j.api.dom.Document process = docu.getDocument();
			process.query("input[type='text']").get().setValue(userchoice);
			process.query("input[type='submit']").get().click();
			//copied over the url and clicked the button
			TimeUnit.SECONDS.sleep(20);//delay to make it work for slow networks
			String ur =(String) docu.executeScript("window.location.href");//got the current URLf
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
			URL url = new URL(finalur);
			HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());//this connection is to get the file size
			long fileSize = httpConnection.getContentLength();
			System.out.println(title[choice]+"\t\tSize : "+fileSize/1048576f+" mb");//----this will calcluate the total file size in mb
			segments[2]=segments[2].replace("%7C", "");
			segments[2]=segments[2].replace("%22", "");
			segments[2]=segments[2].replace("%3F", "");
			segments[2]=java.net.URLDecoder.decode(segments[2], "UTF-8");//java doesnt allow ", ?, | to be in the file name
			String path = ""+segments[2]+".mp3";//downloads in the file where the jar file is located
			File file = new File(path);
			TimeUnit.SECONDS.sleep(5);
			try
			{	System.out.println("Downloading....");
				FileUtils.copyURLToFile(url, file);
				System.out.println("Download Complete");
			}
			catch(Exception e)
			{
				System.out.println("Got an IOException: " + e.getMessage());
				System.out.println("Download Failed");
			}
			finally{
				System.exit(0);
			}
			in.close();			
	}
}
