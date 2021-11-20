package src.searchEngineSpellChecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import src.searchEngineSpellChecker.*;

import src.searchEngineSpellChecker.Sequences;
import src.searchEngineSpellChecker.SpellCheck;
public class web_search_engine {
	
	public void autoSuggestion(String userInput) {
		Trie trieObject = new Trie();
		String fileName = "C:\\Users\\Jessy\\git\\WebSearchEngine\\Web_search_engine\\src\\Dictionary.txt";
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) //try with resources
		{ 
			stream.forEach(value -> trieObject.insertWord(value.toLowerCase()));
		} 
		catch (IOException e)
		{
			System.out.println("Error while reading file" + e);
		} 
		List<String> autoCompleteObject = trieObject.autoComplete(userInput);
		System.out.println("\nYou can also search for:");
		//System.out.println("--------------------------------");
		autoCompleteObject.stream().limit(5).forEach(System.out::println); // takes list of stream as input and prints the same
	}
	
	public void Crawl() {
		String url = "https://en.wikipedia.org";
		web_crawler.crawl(1,url, new ArrayList<String>());
		
		final File webpages = new File("C:\\Users\\Jessy\\git\\WebSearchEngine\\Web_search_engine\\src\\HTMLfiles\\");
		
		web_crawler htmlobj = new web_crawler();
		
		for (final File files : webpages.listFiles()) 
			
		{
			htmlobj.htmltotext(files);
		}
		
		System.out.println("Done converting all the files");
	}
	
	public static void main(String[] args) throws IOException {
		web_search_engine wse= new web_search_engine();
		wse.Crawl();
		System.out.println("Enter the word you want to search: ");
		Scanner s = new Scanner(System.in);
		String userInput = s.nextLine();
		
		SpellCheck sc1=new SpellCheck();
		
		
		ArrayList<String> dictionary = new ArrayList<String>();
		ArrayList<String> suggestion = new ArrayList<String>();
		dictionary = sc1.createDict();
		
		if(dictionary.contains(userInput)) {
			System.out.println("Word Spelled Correctly !!");
    	}else {
    		System.out.println("Word not found");
    		suggestion = sc1.findSuggestions(userInput);
    		
    		if(suggestion.size() >= 2) {
    			System.out.println("Did you mean " + suggestion.get(0) + " or " + suggestion.get(1)  + " ?");
    		}
    		else if(suggestion.size() == 1) {
    			System.out.println("Did you mean " + suggestion.get(0) + " ?");
    		}
    		else if(suggestion.size() == 0) {
    			System.out.println("No suggestions found.");
    		}
	    
    	}
		
		wse.autoSuggestion(userInput);
		
        File folder = new File("C:\\Users\\Jessy\\git\\WebSearchEngine\\Web_search_engine\\src\\ConvertedFiles");
        File[] listOfFiles = folder.listFiles();
        BoyerMoore boyermoore1 = new BoyerMoore(userInput.toLowerCase());                      
        
        
        for (File file : listOfFiles) {
            if (file.isFile()) {
            	BufferedReader inputStream = null;
            	String line;
              try {
                  inputStream = new BufferedReader(new FileReader(file));
                  int count=0;
                  while ((line = inputStream.readLine()) != null) {
                      while(true) {
            	        	int offset=boyermoore1.search(line.toLowerCase());
            	        	if (offset!=line.length()) {
            	        		count++;
            	        		line=line.substring(offset+userInput.length());	        		
            	        	}
            	        	if (offset==line.length()) {
            	        		break;
            	        	}
                    	}
                  }
                  
                  if(count>0) {
                  	System.out.println("\nThe number of occurrences of "+userInput+" in file "+file.getName()+" are "+count);
                  }
              }catch(IOException e) {
              	System.out.println(e);
              }
              finally {
                  if (inputStream != null) {
                      inputStream.close();
                  }
              }
            }
        }
		
	}
}
