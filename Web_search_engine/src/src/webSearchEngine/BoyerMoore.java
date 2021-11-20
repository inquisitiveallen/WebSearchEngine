package src.webSearchEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/***************************************************************
 *  Compilation:  javac BoyerMoore.java
 *  Execution:    java BoyerMoore pattern text
 *
 *  Reads in two strings, the pattern and the input text, and
 *  searches for the pattern in the input text using the
 *  bad-character rule part of the Boyer-Moore algorithm.
 *  (does not implement the strong good suffix rule)
 *
 *  % java BoyerMoore abracadabra abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad 
 *  pattern:               abracadabra
 *
 *  % java BoyerMoore rab abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad 
 *  pattern:         rab
 *
 *  % java BoyerMoore bcara abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad 
 *  pattern:                                   bcara
 *
 *  % java BoyerMoore rabrabracad abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:                        rabrabracad
 *
 *  % java BoyerMoore abacad abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern: abacad
 *
 ***************************************************************/

public class BoyerMoore {
    private final int R;     // the radix
    private int[] right;     // the bad-character skip array

    private char[] pattern;  // store the pattern as a character array
    private String pat;      // or as a string

    // pattern provided as a string
    public BoyerMoore(String pat) {
        this.R = 65536;
        this.pat = pat;

        // position of rightmost occurrence of c in the pattern
        right = new int[R];
        for (int c = 0; c < R; c++)
            right[c] = -1;
        for (int j = 0; j < pat.length(); j++)
            right[pat.charAt(j)] = j;
    }

    // pattern provided as a character array
    public BoyerMoore(char[] pattern, int R) {
        this.R = R;
        this.pattern = new char[pattern.length];
        for (int j = 0; j < pattern.length; j++)
            this.pattern[j] = pattern[j];

        // position of rightmost occurrence of c in the pattern
        right = new int[R];
        for (int c = 0; c < R; c++)
            right[c] = -1;
        for (int j = 0; j < pattern.length; j++)
            right[pattern[j]] = j;
    }

    // return offset of first match; N if no match
    public int search(String txt) {
        int M = pat.length();
        int N = txt.length();
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M-1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i+j)) {
                    skip = Math.max(1, j - right[txt.charAt(i+j)]);
                    break;
                }
            }
            if (skip == 0) return i;    // found
        }
        return N;                       // not found
    }


    // return offset of first match; N if no match
    public int search(char[] text) {
        int M = pattern.length;
        int N = text.length;
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M-1; j >= 0; j--) {
                if (pattern[j] != text[i+j]) {
                    skip = Math.max(1, j - right[text[i+j]]);
                    break;
                }
            }
            if (skip == 0) return i;    // found
        }
        return N;                       // not found
    }


    // test client
    public static void main(String[] args) throws IOException {
 	   
        // There are two implmentations of search
 	   // one is with String and the other is an array of chars
    
    	Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter word");
        String word = myObj.nextLine();  // Read user input
    	
        //D:\Studies\MAC\Adv Computing concepts\Data Files\Text Processing\textprocessing\Allen
        //D:\\Studies\\MAC\\Adv Computing concepts\\Data Files\\W3C Web Pages\\Text
        
        File folder = new File("D:\\Studies\\MAC\\Adv Computing concepts\\Data Files\\W3C Web Pages\\Text");
        File[] listOfFiles = folder.listFiles();
        BoyerMoore boyermoore1 = new BoyerMoore(word);                      
        
        
        for (File file : listOfFiles) {
            if (file.isFile()) {
            	BufferedReader inputStream = null;
            	String line;
              try {
                  inputStream = new BufferedReader(new FileReader(file));
                  int count=0;
                  while ((line = inputStream.readLine()) != null) {
                      while(true) {
            	        	int offset=boyermoore1.search(line);
            	        	if (offset!=line.length()) {
            	        		count++;
            	        		line=line.substring(offset+word.length());	        		
            	        	}
            	        	if (offset==line.length()) {
            	        		break;
            	        	}
                    	}
                  }
                  System.out.println("The number of occurrences of "+word+" in file "+file.getName()+" are "+count);
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