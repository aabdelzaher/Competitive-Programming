package training;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*
 * If the problem was on 1d grid we could solve it as following
 * construct 2d array where arr[i][j] = 1 if there is a good rectangle between i & j, 0 otherwise
 * Then to answer a query (i, j) we need to get answer of (i, i) + (i, i+1) + ... + (i, j) + (i+1, i) + (i+1, i+1)
 * + ... + (j, j). Which is cumulative sum from arr[i][i] to ar[j][j]
 * Now we extend the problem on 2d. Then we construct 4d arr which means there is a rectangle between (i1, j1) to (i2, j2)
 * and to answer a query we need cumulative sum from (i1, j1, i1, j1) to (i2, j2, i2, j2).
 */
public class CountingRectanglesisFun_
{
	static int[][] grid;
	static int[][] sum2d;
	static int[][][][] grid4d;
	static int[][][][] sum4d;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int n = sc.nextInt();
		int m = sc.nextInt();
		int q = sc.nextInt();
		grid = new int[n][m];
		for (int i = 0; i < n; i++)
		{
			char[] tmp = sc.next().toCharArray();
			for (int j = 0; j < m; j++)
				grid[i][j] = tmp[j]-'0';
		}
		
		sum2d = new int[n][m];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				sum2d[i][j] = getsum2d(i-1, j) + getsum2d(i, j-1) - getsum2d(i-1, j-1) + grid[i][j];
		
		grid4d = new int[n][m][n][m];
		sum4d = new int[n][m][n][m];
		
		for (int i1 = 0; i1 < n; i1++)
			for (int j1 = 0; j1 < m; j1++)
				for (int i2 = i1; i2 < n; i2++)
					for (int j2 = j1; j2 < m; j2++)
					{
						int cum = getsum2d(i2, j2) - getsum2d(i1-1, j2) - getsum2d(i2, j1-1) + getsum2d(i1-1, j1-1);
						if(cum == 0)
							grid4d[i1][j1][i2][j2] = 1;
					}
		
		for (int i1 = 0; i1 < n; i1++)
			for (int j1 = 0; j1 < m; j1++)
				for (int i2 = 0; i2 < n; i2++)
					for (int j2 = 0; j2 < m; j2++)
					{
						int sum = grid4d[i1][j1][i2][j2];
						sum += getsum4d(i1-1, j1, i2, j2);
						sum += getsum4d(i1, j1-1, i2, j2);
						sum += getsum4d(i1, j1, i2-1, j2);
						sum += getsum4d(i1, j1, i2, j2-1);
						sum -= getsum4d(i1-1, j1-1, i2, j2);
						sum -= getsum4d(i1-1, j1, i2-1, j2);
						sum -= getsum4d(i1-1, j1, i2, j2-1);
						sum -= getsum4d(i1, j1-1, i2-1, j2);
						sum -= getsum4d(i1, j1-1, i2, j2-1);
						sum -= getsum4d(i1, j1, i2-1, j2-1);
						sum += getsum4d(i1-1, j1-1, i2-1, j2);
						sum += getsum4d(i1-1, j1-1, i2, j2-1);
						sum += getsum4d(i1-1, j1, i2-1, j2-1);
						sum += getsum4d(i1, j1-1, i2-1, j2-1);
						sum -= getsum4d(i1-1, j1-1, i2-1, j2-1);
						sum4d[i1][j1][i2][j2] = sum;
					}
		
		for (int i = 0; i < q; i++)
		{
			int i1 = sc.nextInt()-1;
			int j1 = sc.nextInt()-1;
			int i2 = sc.nextInt()-1;
			int j2 = sc.nextInt()-1;
			
			int sum = getsum4d(i2, j2, i2, j2);
			sum -= getsum4d(i1-1, j2, i2, j2);
			sum -= getsum4d(i2, j1-1, i2, j2);
			sum -= getsum4d(i2, j2, i1-1, j2);
			sum -= getsum4d(i2, j2, i2, j1-1);
			sum += getsum4d(i1-1, j1-1, i2, j2);
			sum += getsum4d(i1-1, j2, i1-1, j2);
			sum += getsum4d(i1-1, j2, i2, j1-1);
			sum += getsum4d(i2, j1-1, i1-1, j2);
			sum += getsum4d(i2, j1-1, i2, j1-1);
			sum += getsum4d(i2, j2, i1-1, j1-1);
			sum -= getsum4d(i1-1, j1-1, i1-1, j2);
			sum -= getsum4d(i1-1, j1-1, i2, j1-1);
			sum -= getsum4d(i1-1, j2, i1-1, j1-1);
			sum -= getsum4d(i2, j1-1, i1-1, j1-1);
			sum += getsum4d(i1-1, j1-1, i1-1, j1-1);
			
			pw.println(sum);
		}
		pw.flush();
		pw.close();
	}
	
	static int getsum2d(int i, int j)
	{
		if(i < 0 || j < 0)
			return 0;
		return sum2d[i][j];
	}
	
	static int getsum4d(int i, int j, int k, int l)
	{
		if(i < 0 || j < 0 || k < 0 || l < 0)
			return 0;
		return sum4d[i][j][k][l];
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
