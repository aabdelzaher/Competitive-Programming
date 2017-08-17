import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*
 * We can notice that F(n) = f(n-1) + 2*F(n-2) + 1. Where F(n) is sum of fibonacci numbers from 0 to n, and f is fibbonaci sequence.  
 * Also we can solve this problem noticing that F(n) = f(n+2)-1, which is easier that this solution.
 */

public class FIBOSUM
{
	public static void main(String[] args) throws IOException
	{	
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while(tc-->0)
		{
			int f = sc.nextInt();
			int t = sc.nextInt();
			if(t < f)
				pw.println(0);
			else
			{
				int ans = (F(t) - F(f-1))%mod;
				if(ans < 0)
					ans += mod;
				pw.println(ans);
			}
		}
		pw.flush();
		pw.close();
	}
	
	static int F(int n)
	{
		if(n <= 0)
			return 0;
		if(n == 1)
			return 1;
		if(n == 2)
			return 2;
		int[][] init = new int[6][6];
		init[0] = new int[]{1,1,0,1,2,1};
		
		int[][] p = new int[6][6];
		p[1][0] =
		p[0][1] =
		p[1][1] =
		p[3][2] =
		p[4][3] =
		p[1][4] = 
		p[5][4] = 
		p[5][5] = 1;
		p[3][4] = 2;
		
		p = matPow(p, n-2);
		int[][] ans = matMul(init, p, 6, 6, 6);
		return ans[0][4];
	}
	static int mod = 1000000007;
	static int[][] matMul(int[][] A, int[][] B, int p, int q, int r)
	{
		int[][] C = new int[p][r];
		for (int i = 0; i < p; ++i)
			for (int j = 0; j < r; ++j)
				for (int k = 0; k < q; ++k)
				{
					long x = 1l*A[i][k]*B[k][j];
					x += C[i][j];
					x %= mod;
					if(x < 0)
						x += mod;
					C[i][j] = (int)(x);
				}
		return C;
	}

	static int[][] matPow(int[][] base, long p)
	{
		int n = base.length;
		int[][] ans = new int[n][n];
		for (int i = 0; i < n; i++)
			ans[i][i] = 1;
		while (p != 0)
		{
			if ((p & 1) == 1)
				ans = matMul(ans, base, n, n, n);
			base = matMul(base, base, n, n, n);
			p >>= 1;
		}

		return ans;
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
