package src.webSearchEngine;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.net.URL;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
public class web_crawler {
		
	public static void crawl(int level, String url, ArrayList<String> visited)
	{	
		if(level <= 2)
		{	
			Document doc = request(url,visited);
			if(doc != null) {
				String title = doc.title();
				try {
				URL url_2 = new  URL(url);
				BufferedReader myreader = new BufferedReader(new InputStreamReader(url_2.openStream()));
				
				BufferedWriter mywriter = new BufferedWriter(new FileWriter("C:\\Users\\Jessy\\git\\WebSearchEngine\\Web_search_engine\\src\\HTMLfiles\\"+title+".html"));
				
				String line;
				while ((line = myreader.readLine()) != null)
				{
				mywriter.write(line);
				}

				myreader.close();
				mywriter.close();

			
			for(Element link : doc.select("a[href]"))
			{
				String next_link = link.absUrl("href");
				if(visited.contains(next_link)==false)
				{
					
					crawl(level++, next_link, visited);
				}		
			}
					}
					catch (IOException e) {}
			}
		}
	}
	private static Document request(String url, ArrayList<String> v)
	{
		try {		
		Connection con = Jsoup.connect(url);
		Document doc = con.get();		

			
			System.out.println("link : "+url);
			
			System.out.println(doc.title());
			v.add(url);
			return doc;

			
		}
		catch (IOException e) 
		{
			return null;
		}		
	}
	
	public void htmltotext(final File files) {
	    try {
	    	
	    	FileWriter filewriter;
	    	{
	    		//
	    		File webpage = new File("C:\\Users\\Jessy\\git\\WebSearchEngine\\Web_search_engine\\src\\HTMLfiles\\"+files.getName());
	    		
	    		//call the parse function in jsoup
	    		Document d = Jsoup.parse(webpage,"UTF-8");
	    		
	    		filewriter=new FileWriter("C:\\Users\\Jessy\\git\\WebSearchEngine\\Web_search_engine\\src\\ConvertedFiles\\"+files.getName()+".txt");    
	    		
	    		//write text file
	    		filewriter.write(d.text());  
	    		
	    		//close FW
	    		filewriter.close();    
	    	}
	    	
	    }
	    
	    catch (Exception e){
	    System.out.println(e);
	    }
	    
	}
}




