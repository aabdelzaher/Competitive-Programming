import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * Since we can switch any two rows, then we can sort one column any way we want.
 * We just need to find the good sorting. If we sorted one column by number of ones to each cell's right
 * untill meeting the first zero. The rectangle will look like this
 * 1100
 * 1110
 * 1111
 * So we are sure that if the current cell is one, all below cells is also ones. So we have a good rectangle of 1's
 * We just loop over each cell in the column and maximize with (number of one's to right)*(n-row)
 */
public class MaximumSubmatrix2
{
	static int[][] right;
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		int m = sc.nextInt();
		char[][] grid = new char[n][m];
		for (int i = 0; i < n; i++)
			grid[i] = sc.next().toCharArray();
		right = new int[m][n];
		for (int i = 0; i < n; i++)
			for (int j = m-1; j >= 0; j--)
				right[j][i] = grid[i][j]=='1'?right(j+1, i)+1:0;
		
		int ans = 0;
		for (int i = 0; i < right.length; i++)
		{
			Arrays.sort(right[i]);
			for (int j = 0; j < right[i].length; j++)
				ans = Math.max(right(i, j)*(n-j), ans);
		}
		System.out.println(ans);
	}
	
	static int right(int i, int j)
	{
		if(i < 0 || j < 0 || i >= right.length || j >= right[i].length)
			return 0;
		return right[i][j];
	}
	
	static class Scanner
	{
		BufferedReader br; StringTokenizer st;
		public Scanner()
		{br = new BufferedReader(new InputStreamReader(System.in));}
		public String next() throws IOException
		{while(st == null || !st.hasMoreTokens())st = new StringTokenizer(br.readLine());return st.nextToken();}
		public int nextInt() throws NumberFormatException, IOException
		{return Integer.parseInt(next());}
		public long nextLong() throws NumberFormatException, IOException
		{return Long.parseLong(next());}
	}
}
