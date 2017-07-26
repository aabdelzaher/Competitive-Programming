import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * Normal coin change but using cumulative sum of coins as coins
 * If we used coin number 3 for example we should use coins number 1 and 2
 * so using 3 adds coins[1] + coins[2] + coins[3] to the answer which is cumulative sum
 */
public class NOCHANGE
{
	static int[] coins;
	static int k;
	static int[][] dp;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		int x = sc.nextInt();
		k = sc.nextInt();
		coins = new int[k];
		dp = new int[k+2][x+5];
		for (int i = 0; i < dp.length; i++)
			Arrays.fill(dp[i], -1);
		for (int i = 0; i < k; i++)
			coins[i] = sc.nextInt();
		for (int i = 1; i < k; i++)
			coins[i] += coins[i-1];
		if(solve(x, k-1) > 0)
			System.out.println("YES");
		else
			System.out.println("NO");
	}
	private static int solve(int sum, int ind)
	{
		if(ind < 0)
			return 0;
		if(sum == 0)
			return 1;
		if(sum < 0)
			return 0;
		if(dp[ind][sum] != -1)
			return dp[ind][sum];
		int ans = 0;
		ans += solve(sum-coins[ind], ind);
		ans += solve(sum, ind-1);
		return dp[ind][sum] = ans;
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
		
		public double nextDouble() throws IOException
		{
			String x = next();
			StringBuilder sb = new StringBuilder("0");
			double res = 0, f = 1;
			boolean dec = false, neg = false;
			int start = 0;
			if(x.charAt(0) == '-')
			{
				neg = true;
				start++;
			}
			for(int i = start; i < x.length(); i++)
				if(x.charAt(i) == '.')
				{
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				}
				else
				{
					sb.append(x.charAt(i));
					if(dec)
						f *= 10;
				}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg?-1:1);
		}
		
		public boolean ready() throws IOException {return br.ready();}
	}
}
