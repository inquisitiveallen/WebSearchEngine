package src.searchEngineSpellChecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import src.searchEngineSpellChecker.Sequences;


public class SpellCheck {
	
	public static ArrayList<String> createDict(){
		ArrayList<String> arr = new ArrayList<String>();
		try {
	            BufferedReader br =  new BufferedReader(new FileReader("C:\\Users\\Jessy\\git\\WebSearchEngine\\Web_search_engine\\src\\Dictionary.txt"));
	            String line = br.readLine();
	            while (line != null) {
	                arr.add(line);
	                line = br.readLine();
	            }
		}catch(Exception e) {
			System.out.println("Error while accessing Dictionary "+ e);
		}
		return arr;
	}
	
    public static ArrayList<String> findSuggestions(String word){
        ArrayList<String> wordCorrection = new ArrayList<String>();
        try{
            ArrayList<String> ar = new ArrayList<String>();
            int i=0;
            int d=0;
            ar = createDict();

            if (!ar.contains(word)) {
            	
                for (i=0;i<ar.size();i++) {
                    d= Sequences.editDistance(word, ar.get(i));
                    if(d==1) {
                        wordCorrection.add(ar.get(i));
                    }
                }

            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return wordCorrection;
    }

}
