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
		Scanner in = new Scanner(System.in);
		String name = in.nextLine();
		//creating the url necessary to scrape
		String murl = "https://www.youtube.com/results?search_query="+name;
		Document website = Jsoup.connect(murl).get();
		//closing in on the target,(class name is lockup-title)
		Elements subdiv = website.select("h3.yt-lockup-title>a");
		//System.out.println(subdiv);---checking to see if .select worked 
		String[] seplinks = new String[subdiv.size()];
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
				seplinks[i] = "https://www.youtube.com"+temp.attr("href");
				title[i] = temp.text();
				i++;
			}
		}
			for(int j=0; j<seplinks.length;j++){
				System.out.println((j+1)+": "+title[j]);
				//System.out.println(seplinks[j]);---checking to see if the links are proper
			}
			System.out.println("Please enter your choice");
			int ch = in.nextInt();
			ch--;
			String userchoice = seplinks[ch];
			//System.out.println(userchoice);---final URL selected
			
			
			//-------------Done with Scraping youtube-------------------
			
			
			//Start of scraping the second website
					
			BrowserEngine browser = BrowserFactory.getWebKit();
			//java.util.logging.Logger.getLogger("com.ui4j").setLevel(Level.OFF);
			com.ui4j.api.browser.Page docu = browser.navigate("http://www.listentoyoutube.com/");
			com.ui4j.api.dom.Document process = docu.getDocument();
			process.query("input[type='text']").get().setValue(userchoice);
			process.query("input[type='submit']").get().click();
			TimeUnit.SECONDS.sleep(15);
			//docu.show();
			String a = docu.getDocument().queryAll("div[class='col-lg-8']").toString();
			a=a.substring(584);
			//System.out.println(a);
			//System.out.println(a.substring(0, 215));
			a=a.substring(0,210);
			//System.out.println(a);
			a=a.substring(20, 99);
			//System.out.println(a);
			String[] temp = a.split("&");
			//System.out.println(temp[0]);
			String srv = temp[0].substring(3);
			//System.out.println(srv);
			String hash = temp[1].substring(9, 68);
			hash=hash.replace("%",  "");
			//System.out.println(hash);
			//String nm = title[ch].replaceAll(" ","%20");
			//System.out.println(nm);
			String finalur = "http://srv"+srv+".listentoyoutube.com/download/"+hash+"==/"+URLEncoder.encode(title[ch],"UTF-8")+".mp3";
			//System.out.println(finalur);
			String f = new String(URLEncoder.encode(title[ch], "UTF-8"));
			f=f.replaceAll("%7C+","");
			f=f.replaceAll("%22", "");
			f=f.replaceAll("%3F", "");
			String result = java.net.URLDecoder.decode(f, "UTF-8");
			//System.out.println(f);
			//System.out.println(result);
			URL l = new URL(finalur);
			//for(int j=0; j!=-1;)
			{
			//	int k=fin.lastIndexOf("|");
			//	fin.deleteCharAt(k);
			//	break;
			}
			
		    //title[ch] = title[ch].replaceAll(" |", "");
			//System.out.println(title[ch]);
			String path = "C:\\Users\\Aakash\\Desktop\\TestFolder\\"+result+".mp3";
			HttpURLConnection httpConnection = (HttpURLConnection) (l.openConnection());
			long fileSize = httpConnection.getContentLength();
			System.out.println("Size : "+fileSize/1048576f+" mb");
			File abc = new File(path);
			TimeUnit.SECONDS.sleep(5);
			try
			{
				FileUtils.copyURLToFile(l, abc);
				System.out.println("Download Complete");
			}
			catch(Exception c)
			{
				System.out.println("Got an IOException: " + c.getMessage());
				System.out.println("Download Failed");
			}
			in.close();
			
	}
}
