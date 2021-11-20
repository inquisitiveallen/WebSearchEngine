package src.searchEngineSpellChecker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TrieNode {
	char characterValue;
	LinkedList<TrieNode> childrenNodes;
	TrieNode parentNode;
	boolean isEnd;
	public TrieNode(char value) 
	{
		characterValue = value;
		childrenNodes = new LinkedList<TrieNode>();
		isEnd = false;
	}
	public TrieNode getChildNode(char value) 
	{
		if (childrenNodes != null) 
		{
			for (TrieNode eachChild : childrenNodes) 
			{
				if (eachChild.characterValue == value) 
				{
					return eachChild;
				}
			}
		}
		return null;
	}
	protected List<String> getWords() 
	{
		List<String> list = new ArrayList<String>();
		if (isEnd) 
		{
			list.add(toString());
		}

		if (childrenNodes != null) 
		{
			for (int i = 0; i < childrenNodes.size(); i++) 
			{
				if (childrenNodes.get(i) != null) 
				{
					list.addAll(childrenNodes.get(i).getWords());
				}
			}
		}
		return list;
	}
	public String toString() 
	{
		if (parentNode == null) 
		{
			return "";
		} 
		else 
		{
			return parentNode.toString() + new String(new char[] { characterValue });
		}
	}
}




