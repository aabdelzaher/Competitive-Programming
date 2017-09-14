import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class CunningGena
{
	/*
	 * The idea is to use dp where state is dp(ind, msk), where ind represents
	 * friend number, and msk represent current solved problems. The problem is
	 * now knapsack problem (take it or leave it). To include a friend and move
	 * to next state we move to ind + 1, and do bitwise or with problems that
	 * current friend can solve(we keep mask for each friend representing
	 * problems he can solve as ones) The only problem left is to calculate
	 * monitor cost. This problem can be solved if we traverse the friends array
	 * in a sorted order of monitor. When we reach a friend that will make all
	 * problems solved, then the monitor cost is the number of monitors this
	 * friend needs * cost of monitor. This is correct as all previous friedns
	 * needs less monitors as we sort according to number of monitors
	 */
	static int[] can, cost;
	static int[] monitor;
	static int n, m, b;
	static long inf = (long) 2l * 1000000 * 1000000 * 1000000;

	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		m = sc.nextInt();
		b = sc.nextInt();
		can = new int[n];
		cost = new int[n];
		monitor = new int[n];
		Integer [] indices = new Integer[n];
		long[] base = new long[n];

		for (int i = 0; i < n; i++)
		{
			cost[i] = sc.nextInt();
			indices[i] = i;
			monitor[i] = sc.nextInt();
			int mi = sc.nextInt();
			int msk = 0;
			for (int j = 0; j < mi; j++)
				msk |= (1 << (sc.nextInt() - 1));
			can[i] = msk;
			base[i] = cost[i] + 1l * monitor[i] * b;
		}

		Arrays.sort(indices, new cmp());
		dp = new long[2][(1 << m) + 1];
		int p = 0;
		Arrays.fill(dp[p], inf);
		for (int ind = n - 1; ind >= 0; ind--)
		{
			p = 1 - p;
			for (int msk = (1 << m) - 1; msk >= 0; msk--)
			{
				int k = indices[ind];

				long ans = dp[1 - p][msk];

				int nwMsk = msk | can[k];

				if (nwMsk == (1 << m) - 1)
					ans = Math.min(ans, base[k]);
				else
					ans = Math.min(ans, cost[k] + dp[1 - p][nwMsk]);

				dp[p][msk] = ans;
			}
		}
		System.out.println(dp[p][0] >= inf ? -1 : dp[p][0]);
	}

	static long[][] dp;

	static class Scanner
	{
		StringTokenizer st;
		BufferedReader br;

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

		public boolean ready() throws IOException
		{
			return br.ready();
		}
	}
	
	static class cmp implements Comparator<Integer>
	{
		public int compare(Integer a, Integer b)
		{
			return monitor[a] - monitor[b];
		}
	}
}
