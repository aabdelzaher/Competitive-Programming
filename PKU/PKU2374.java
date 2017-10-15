package supervision;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class PKU2374
{
	/*
	 * This solution uses dynamic programming. We keep in state which fence we
	 * are at and which end of this fence. To move to next fence, We continue
	 * moving down till we hit another fence i, or we hit the side of the barn.
	 * If we hit side of barn we move to origin and we are done. If we hit
	 * another fence i, we move to start of i and solve dp(start, i), or move to
	 * end of i and solve dp(end, i), and take the minimum of both answers.
	 */
	static int fence[][], n, s;
	static long dp[][];
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		
		n = sc.nextInt();
		s = sc.nextInt();
		fence = new int[2][n];
		for (int i = 0; i < n; i++)
		{
			fence[0][i] = sc.nextInt();
			fence[1][i] = sc.nextInt();
		}
		
		dp = new long[2][n+2];
		for (int i = 0; i < dp.length; i++)
			Arrays.fill(dp[i], -1);
		long ans = Math.min(Math.abs(fence[0][n-1]-s)+dp(0, n-1), Math.abs(fence[1][n-1]-s)+dp(1, n-1));
		pw.println(ans);
		pw.flush();
		pw.close();
	}
	
	static long dp(int edge, int idx)
	{
		if(dp[edge][idx] != -1)
			return dp[edge][idx];
		long ans = Long.MAX_VALUE;
		int curX = fence[edge][idx];
		int i = idx-1;
		while(i >= 0 && (curX <= fence[0][i] || curX >= fence[1][i]))
			i--;
		if(i == -1)
			return dp[edge][idx] = Math.abs(curX);
		ans = Math.min(ans, Math.abs(curX - fence[0][i]) + dp(0, i));
		ans = Math.min(ans, Math.abs(curX - fence[1][i]) + dp(1, i));
		return dp[edge][idx] = ans;
	}
	static class Scanner
	{
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream s)
		{
			br = new BufferedReader(new InputStreamReader(s));
		}

		public Scanner(String s) throws FileNotFoundException
		{
			br = new BufferedReader(new FileReader(new File((s))));
		}

		public String next() throws IOException
		{
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public int nextInt() throws IOException
		{
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException
		{
			return Long.parseLong(next());
		}

		public String nextLine() throws IOException
		{
			return br.readLine();
		}

		public double nextDouble() throws IOException
		{ return Double.parseDouble(next()); }	
	}
}
