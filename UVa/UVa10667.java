package mostafa_supervision;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 2d max sum
 */
public class UVa10667
{
	static int[][] grid;
	static int[][] dp;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		int tc = sc.nextInt();
		while(tc-->0)
		{
			int s = sc.nextInt();
			int b = sc.nextInt();
			grid = new int[s][s];
			for (int i = 0; i < b; i++)
			{
				int r1 = sc.nextInt()-1;
				int c1 = sc.nextInt()-1;
				int r2 = sc.nextInt()-1;
				int c2 = sc.nextInt()-1;
				for (int j = Math.min(r1, r2); j <= Math.max(r1, r2); j++)
					for (int j2 = Math.min(c1, c2); j2 <= Math.max(c1, c2); j2++)
						grid[j][j2] = 1;
				
			}
			dp = new int[s][s];
			for (int i = 0; i < dp.length; i++)
				for (int j = 0; j < dp.length; j++)
					dp[i][j] = dp(i, j-1) + dp(i-1, j) - dp(i-1, j-1) + grid[i][j];
			
			int max = 0;
			for (int i = 0; i < s; i++)
				for (int j = 0; j < s; j++)
					for (int i2 = i; i2 < s; i2++)
						for (int j2 = j; j2 < s; j2++)
						{
							int ans = dp(i2, j2) - dp(i2, j-1) - dp(i-1, j2) + dp(i-1, j-1);
							if(ans == 0)
								max = Math.max(max, Math.abs(i2-i+1)*Math.abs(j2-j+1));
						}
			System.out.println(max);
		}
	}
	
	static int dp(int x, int y)
	{
		if(x < 0 || y < 0 || x >=dp.length || y >= dp.length)
			return 0;
		return dp[x][y];
	}
	static class Scanner 
	{
		StringTokenizer st;
		BufferedReader br;
		public Scanner(InputStream s){	br = new BufferedReader(new InputStreamReader(s));}
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
}
