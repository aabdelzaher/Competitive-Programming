import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVa10325
{
	/* This problem is solvable using inclusion exclusion principle */
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		while(sc.ready())
		{
			int n = sc.nextInt();
			int m = sc.nextInt();
			int[] arr = new int[m];
			for (int i = 0; i < m; i++)
				arr[i] = sc.nextInt();
			long ans = 0;
			for (int i = 1; i < (1<<m); i++)
			{
				long lcm = 1;
				int cnt = 0;
				for (int j = 0; j < m; j++)
					if(((1<<j) & i) != 0)
					{
						lcm = lcm(lcm, arr[j]);
						cnt++;
					}
				if((cnt&1) != 0)
					ans += n/lcm;
				else
					ans -= n/lcm;
			}
			pw.println(n-ans);
		}
		pw.flush();
		pw.close();
	}
	
	static long gcd(long a, long b)
	{
		if(b == 0)
			return a;
		return gcd(b, a%b);
	}
	
	static long lcm(long a, long b)
	{
		return (a/gcd(a, b))*b;
	}
	
	static class Scanner
	{
		StringTokenizer st; BufferedReader br;
		public Scanner(InputStream s){br = new BufferedReader(new InputStreamReader(s));}
		public String next() throws IOException{while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine()); return st.nextToken();}
		public int nextInt() throws IOException{return Integer.parseInt(next());}
		public long nextLong() throws IOException{return Long.parseLong(next());}
		public String nextLine() throws IOException{return br.readLine();}
		public double nextDouble() throws IOException{return Double.parseDouble(next());}
		public boolean ready() throws IOException{return br.ready();}
	}
}
