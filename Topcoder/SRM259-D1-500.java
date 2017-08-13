package supervision;

import java.util.ArrayList;
import java.util.Collections;

/*
 * We can loop over all substrings in O(n^2). To determine how many letter is good we precompute cumulative array for occurence for each letter
 * To determine how many letter is good we loop over all letters and check if it occurs only once we add the count of goodness.
 * To get the substring, for every start of a substring we choose the shortest substring with goodness equals maximum goodness, add them to an array.
 * We sort the array and return the smallest substring.
 */
public class SuperString
{
	public static void main(String[] args)
	{
		String[] superstring = 	
			{"ZYXWVUTSRQPONMLKJIHGFEDCBA", "ZYXWVUTSRQPONMLKJIHGFEDCBA"};
		System.out.println(goodnessSubstring(superstring));
	}
	
	static int[][] cum;
	public static String goodnessSubstring(String[] superstring)
	{
		String s = "";
		for (String p : superstring)
			s += p;
		char[] arr = s.toCharArray();
		int n = s.length();
		cum = new int[26][n];
		for (int i = 0; i < 26; i++)
		{
			for (int j = 0; j < n; j++)
				if(arr[j]-'A' == i)
					cum[i][j]++;
			for (int j = 1; j < n; j++)
				cum[i][j] += cum[i][j-1];
		}
		
		ArrayList<String> ans = new ArrayList<String>();
		
		int max = -1;
		for (int i = 0; i < n; i++)
		{
			int maxi = -1;
			int jind = -1;
			for (int j = i; j < n; j++)
			{
				int cnt = 0;
				for (int k = 0; k < 26; k++)
					if(getC(k,j) - getC(k, i-1) == 1)
						cnt++;
				if(cnt > max)
				{
					max = cnt;
					maxi = cnt;
					ans.clear();
					jind = j;
				}
				else
					if(cnt == max)
					{
						if(cnt > maxi)
						{
							jind = j;
							maxi = cnt;
						}
					}
			}
			if(jind != -1)
				ans.add(s.substring(i, jind+1));
		}
		
		Collections.sort(ans);
		return ans.get(0);
	}
	private static int getC(int i, int j)
	{
		if(j < 0)
			return 0;
		return cum[i][j];
	}
}
