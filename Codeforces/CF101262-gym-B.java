import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class K 
{
	/*
	 * If k > n, then no answer exists.
	 * We choose least frequent letter (denote to it as letter a). If frequency of this letter > k then no answer.
	 * We can construct solution by taking all occurences of this letter, and letters enough to complete k, then complete word by letter a.
	 */
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(System.out);
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		char[] arr = br.readLine().toCharArray();
		int[] c = new int[26];
		for (int i = 0; i < arr.length; i++)
			c[arr[i]-'a']++;
		
		int min = 3000;
		char ch = 'a'; 
		for (int i = 0; i < c.length; i++)
		{
			if(c[i] < min)
			{
				min = c[i];
				ch = (char)(i+'a');
			}
		}
		if(k > n)
		{
			System.out.println("WRONGANSWER");
			return;
		}
		if(min > k)
		{
			pw.println("WRONGANSWER");
			pw.flush();
			pw.close();
			return;
		}
		
		int rem = k - min;
		for (int i = 0; i < arr.length; i++)
		{
			if(rem > 0 && arr[i] != ch)
			{
				pw.print(arr[i]);
				rem--;
			}
			if(arr[i] == ch)
				pw.print(ch);
		}
		
		for (int i = 0; i < n - k; i++)
		{
			pw.print(ch);
		}
		pw.println();
		pw.flush();
		pw.close();
	}
}
