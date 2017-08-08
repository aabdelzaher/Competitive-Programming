import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * The problem can be easily solved using dp by using state (ind, employee1, employee2, employee3).
 * Most probably with these limits this approach will give MLE. We know that there is one employee at the previous city.
 * So it is enough to have in the state the two cities in which two employees are, and the third employee is at request[ind-1].
 * So now our state is (ind, employee1, employee2), where employee1 and employee2 are the employees that did not serve the last requested city.
 */

public class SERVICE
{
	static int[][][] dp;
	static int[][] dist;
	static int[] req;
	static int n, l;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while(tc-->0)
		{
			l = sc.nextInt();
			n = sc.nextInt();
			dist = new int[l][l];
			for (int i = 0; i < l; i++)
				for (int j = 0; j < l; j++)
					dist[i][j] = sc.nextInt();
			
			req = new int[n+1];
			req[0] = 2;
			for (int i = 1; i <= n; i++)
				req[i] = sc.nextInt()-1;
			
			dp = new int[l+1][l+1][n+2];
			for (int i = 0; i < dp.length; i++)
				for (int j = 0; j < dp.length; j++)
					Arrays.fill(dp[i][j], -1);
			pw.println(solve(0, 1, 1));
		}
		pw.flush();
		pw.close();
	}
	
	
	static int inf = (int)1e9;
	private static int solve(int one, int two, int ind)
	{
		int three = req[ind-1];
		if(one == two || one == three || two == three)
			return inf;
		if(ind > n)
			return 0;
		if(dp[one][two][ind] != -1)
			return dp[one][two][ind];
		int t = req[ind];
		int ans = dist[one][t] + solve(two, three, ind+1);
		ans = Math.min(ans, dist[two][t] + solve(one, three, ind+1));
		ans = Math.min(ans, dist[three][t] + solve(one, two, ind+1));
		return dp[one][two][ind] = ans;
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
		public boolean ready() throws IOException {return br.ready();}
	}
}
