package src.searchEngineSpellChecker;


import java.util.ArrayList;
import java.util.List;

public class Trie {
	private TrieNode rootNode;
	public Trie() 
	{
		rootNode = new TrieNode(' ');
	}
	public void insertWord(String wordToInsert) 
	{
		if (searchWord(wordToInsert) == true) 
		{
			return;
		}
		TrieNode currentNode = rootNode;
		TrieNode preNode;
		for (char wordChar : wordToInsert.toCharArray()) 
		{
			preNode = currentNode;
			TrieNode childNode = currentNode.getChildNode(wordChar);
			if (childNode != null) 
			{
				currentNode = childNode;
				childNode.parentNode = preNode;
			} 
			else 
			{
				currentNode.childrenNodes.add(new TrieNode(wordChar));
				currentNode = currentNode.getChildNode(wordChar);
				currentNode.parentNode = preNode;
			}
		}
		currentNode.isEnd = true;
	}
	public boolean searchWord(String wordToInsert) 
	{
		TrieNode currentNode = rootNode;
		for (char wordChar : wordToInsert.toCharArray()) 
		{
			if (currentNode.getChildNode(wordChar) == null) 
			{
				return false;
			}
			else 
			{
				currentNode = currentNode.getChildNode(wordChar);
			}
		}
		if (currentNode.isEnd == true) 
		{
			return true;
		}
		return false;
	}
	public List<String> autoComplete(String prefix) 
	{
		TrieNode lastNode = rootNode;
		for (int i = 0; i < prefix.length(); i++) 
		{
			lastNode = lastNode.getChildNode(prefix.charAt(i));
			if (lastNode == null) 
			{
				return new ArrayList<String>();
			}
		}
		return lastNode.getWords();
	}
}




