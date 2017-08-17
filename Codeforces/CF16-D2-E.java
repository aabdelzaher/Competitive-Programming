import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * We represent state of fish by mask, where 0 means dead, and 1 means alive. 
 * For each fish i we need to calculate probability of getting mask 1<<i.
 * To reach the current state then one fish must have died in the previous day.
 * We loop for every dead fish(0), then loop over all living fish(1), 
 * and add to answer (probability of fish 1 killing fish 2) * dp(newmask), where new mask is mask where fish 0 is still alive 
 */
public class Fish
{
	static double[] dp;
	static int n;
	static double[][] prop;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		dp = new double[(1<<n) + 3];
		prop = new double[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				prop[i][j] = sc.nextDouble();
		
		Arrays.fill(dp, -1);
		for (int i = 0; i < n; i++)
		{
			System.out.printf("%.8f ", solve((1 << i)));
		}
		System.out.println();
	}
	
	
	private static double solve(int msk)
	{
		if(Math.abs(dp[msk] + 1) > 1e-9)
			return dp[msk];
		int bcnt = Integer.bitCount(msk);
		if(bcnt == n)
			return 1;
		
		double p = 2.0/(bcnt*(bcnt+1));
		double ans = 0;
		for (int i = 0; i < n; i++)
		{
			if(((1<<i) & msk) == 0)
			for (int j = 0; j < n; j++)
				if(((1<<j) & msk) != 0)
				{
					ans += p*prop[j][i]*solve(msk | (1<<i));
				}
		}
		
		return dp[msk] = ans;
	}


	static class Scanner 
	{
		StringTokenizer st;BufferedReader br;
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
		public double nextDouble() throws IOException {return Double.parseDouble(next());}
		public boolean ready() throws IOException {return br.ready();}
	}
}
