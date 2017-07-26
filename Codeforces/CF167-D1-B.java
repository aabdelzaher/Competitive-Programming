import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * probability problem. We can solve it using dp. Our state (index_of_prize, prizes_won, remaining_space)
 * if current prize is bag we move to (ind+1, won, rem + capacity of the bag)
 * else we move to (ind+1, won, rem - 1) -> we multiply this by probability of winning
 * in case we did not win we move to (ind+1, won, rem) -> * probability of not winning
 */
public class WizardsAndHugePrize
{
	static int n, l, k, a[];
	static double[] p;
	static double[][][] dp;
	static int offset = 201;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner();
		n = sc.nextInt();
		l = sc.nextInt();
		k = sc.nextInt();
		p = new double[n];
		a = new int[n];
		for (int i = 0; i < n; i++)
			p[i] = sc.nextInt()/100.0;
		for (int i = 0; i < n; i++)
			a[i] = sc.nextInt();
		dp = new double[205][205][410];
		for (int i = 0; i < dp.length; i++)
			for (int j = 0; j < dp.length; j++)
				Arrays.fill(dp[i][j], -1);
		System.out.println(prob(0, 0, k));
	}
	
	private static double prob(int ind, int won, int rem)
	{
		if(ind == n)
			if(won >= l && rem >= 0)
				return 1;
			else
				return 0;
		if(Math.abs(dp[ind][won][rem+offset] + 1) > 1e-8)
			return dp[ind][won][rem+offset];
		double ans = 0;
		ans = p[ind] * prob(ind+1, won+1, Math.min(rem + a[ind], 201))
			+ (1-p[ind]) * prob(ind+1, won, rem);
		return dp[ind][won][rem+offset] = ans;
	}

	static class Scanner
	{
		BufferedReader br; StringTokenizer st;
		public Scanner()
		{
			br = new BufferedReader(new InputStreamReader(System.in));
		}
		public String next() throws IOException
		{
			while(st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
		public int nextInt() throws IOException
		{
			return Integer.parseInt(next());
		}
	}
}
