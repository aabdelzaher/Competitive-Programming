
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * We move from one room to another. Each room can have 0, 1, 2 ... num_available_students.
 * So we keep in state, room_idx, maximum_so_far, and remaining_students.
 * Every time we try to take any number x from available students and move to next room with probability 1/n^x.
 */

public class BathQueue
{
	static int n,m;
	static int[] a;
	static double[][][] dp;
	static long[][] C;
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		Scanner sc = new Scanner();
		n = sc.nextInt();
		m = sc.nextInt();
		a = new int[m];
		for (int i = 0; i < a.length; i++)
			a[i] = sc.nextInt();
		dp = new double[m+5][n+5][n+5];
		for (int i = 0; i < dp.length; i++)
			for (int j = 0; j < dp[i].length; j++)
				Arrays.fill(dp[i][j], -1);
		
		C = new long[51][51];
		for (int i = 0; i < 51; i++)
			for (int j = 0; j < 51; j++)
				C[i][j] = nCr4(i, j);
		
		System.out.println(dp(0,0,n));
	}
	
	static double eps = 1e-9;
	private static double dp(int ind, int max, int rem)
	{
		if(ind == m)
			if(rem == 0)
				return max;
			else
				return 0;
		
		
		if(Math.abs(dp[ind][max][rem] + 1) > eps)
			return dp[ind][max][rem];
		
		double ans = 0;
		
		double p = 1.0;
		for (int i = 0; i <= rem; i++)
		{
			ans += C[rem][i]*dp(ind+1, Math.max(max, (i+a[ind]-1)/a[ind]), rem-i)*p;
			p /= m;
		}
		
		return dp[ind][max][rem] = ans;
	}
	
	static long nCr4(int N, int K)
	{
		if(K > N)
			return 0;
		long res = 1;
		for(int i = 1; i <= K; ++i)
			res = (N - K + i) * res / i;
		return res;
	}

	static class Scanner
	{
		StringTokenizer st;BufferedReader br;
		public Scanner(){
			br = new BufferedReader(new InputStreamReader(System.in));
			}
		public String next() throws IOException
		{
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
		public int nextInt() throws NumberFormatException, IOException{return Integer.parseInt(next());}
		public double nextDouble() throws NumberFormatException, IOException{return Double.parseDouble(next());}
	}
}
