package supervision;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.StringTokenizer;

/*
 * Writing the grundy function and printing values whose (grundy == 0) will lead to the obvious observation.
 */
public class SYNC13C
{
	static int[][] dp;
	
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int tc = sc.nextInt();
		String fst = "Suresh";
		String snd = "Ramesh";
		while(tc-->0)
		{
			int a = sc.nextInt();
			int b = sc.nextInt();
			if(a%2 != 0 && b%2 != 0)
				pw.println(snd);
			else
				pw.println(fst);
				
		}
		pw.flush();
		pw.close();
	}
	
	static class Scanner 
	{
		StringTokenizer st; BufferedReader br;
		public Scanner(InputStream s)
		{
			br = new BufferedReader(new InputStreamReader(s));
		}
		public String next() throws IOException
		{
			while (st == null || !st.hasMoreTokens()) 
			st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
		public int nextInt() throws IOException {return Integer.parseInt(next());}
		public long nextLong() throws IOException {return Long.parseLong(next());}
		public String nextLine() throws IOException {return br.readLine();}
		public boolean ready() throws IOException {return br.ready();}
	}
	
	static int getG(int c1, int c2)
	{
		if(c1 == 0 && c2 == 0)
			return 0;
		if(dp[c1][c2] != -1)
			return dp[c1][c2];
		HashSet<Integer> s = new HashSet<Integer>();
		// c1
		for (int i = 1; i <= c2/2; i++)
		{
			int b1 = i;
			int b2 = c2-i;
			s.add(getG(b1, b2));
		}
		// c2
		for (int i = 1; i <= c1/2; i++)
		{
			int b1 = i;
			int b2 = c1-i;
			s.add(getG(b1, b2));
		}
		
		return dp[c1][c2] = dp[c2][c1] = getMex(s);
	}
	
	static int getMex(HashSet<Integer> s)
	{
		int mex = 0;
		while(s.contains(mex))
			mex++;
		return mex;
	}
}
