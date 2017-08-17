import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * The normal longest common subsequence is solved using dp, where state contains ind1(string 1) & ind2(string 2).
 * The only difference is constraint of not having virus string as substring. We can add another variable to state.
 * We keep track of how many character ending at formed string matches prefix of virus(kmp value).
 * We can take a character only if it will not make matched characters equals length of virus.
 * To move from state to another, We can precompute 2d-array where: 
 * kmp2[n][c] means if n letters matches prefix of virus, how many character will match a prefix if we added character c.
 */
public class LuckyCommonSubsequence
{
	static char[] vir;
	static int[] lps;
	static char[] A, B;
	static int[][] kmp2;
	static int[][][] dp;
	static int n, m;
	static PrintWriter pw = new PrintWriter(System.out);
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		A = sc.next().toCharArray();
		B = sc.next().toCharArray();
		vir = sc.next().toCharArray();
		buildKMP(vir);
		buildKmp2d(lps);
		n = A.length;
		m = B.length;
		
		dp = new int[n+5][m+5][vir.length+5];
		for (int i = 0; i < dp.length; i++)
			for (int j = 0; j < dp[i].length; j++)
				Arrays.fill(dp[i][j], -1);
		
		int ans = solve(0,0,0);
		if(ans == 0)
			pw.println(0);
		else
			print(0,0,0);
		pw.flush();
		pw.close();
	}
	
	private static void print(int ind1, int ind2, int match)
	{
		if(ind1 >= n)
			return;
		if(ind2 >= m)
			return;
		if(match >= vir.length)
			return;

		int ans = 0;
		ans = Math.max(ans, solve(ind1+1, ind2, match));
		ans = Math.max(ans, solve(ind1, ind2+1, match));
		if(A[ind1] == B[ind2] && kmp2[A[ind1]-'A'][match] < vir.length)
			ans = Math.max(ans, 1 + solve(ind1+1, ind2+1, kmp2[A[ind1]-'A'][match]));
		
		if(ans == solve(ind1+1, ind2, match))
		{
			print(ind1+1, ind2, match);
			return;
		}
		
		if(ans == solve(ind1, ind2+1, match))
		{
			print(ind1, ind2+1, match);
			return;
		}
		
		if(ans == 1 + solve(ind1+1, ind2+1, kmp2[A[ind1]-'A'][match]))
		{
			pw.print(A[ind1]);
			print(ind1+1, ind2+1, kmp2[A[ind1]-'A'][match]);
			return;
		}
	}
	
	private static int solve(int ind1, int ind2, int match)
	{
		if(ind1 >= n)
			return 0;
		if(ind2 >= m)
			return 0;
		if(match >= vir.length)
			return 0;
		if(dp[ind1][ind2][match] != -1)
			return dp[ind1][ind2][match];
		int ans = 0;
		ans = Math.max(ans, solve(ind1+1, ind2, match));
		ans = Math.max(ans, solve(ind1, ind2+1, match));
		if(A[ind1] == B[ind2] && kmp2[A[ind1]-'A'][match] < vir.length)
			ans = Math.max(ans, 1 + solve(ind1+1, ind2+1, kmp2[A[ind1]-'A'][match]));
		return dp[ind1][ind2][match] = ans;
	}

	static void buildKmp2d(int[] lps)
	{
		int n = lps.length;
		kmp2 = new int[26][lps.length];
		for (int i = 0; i < 26; i++)
		{
			for (int bef = 0; bef < n; bef++)
			{
				int j = bef;
				char nxt = (char)(i+'A');
				while(j > 0 && nxt != vir[j])
					j = lps[j-1];
				if(nxt == vir[j])
					++j;
				kmp2[i][bef] = j;
			}
		}
	}
	
	static void buildKMP(char[] arr)
	{
		lps = new int[arr.length];
		for (int i = 1, j = 0; i < arr.length; i++)
		{
			while(j > 0 && arr[i] != arr[j])
				j = lps[j-1];
			
			if(arr[i] == arr[j])
				j++;
			lps[i] = j;
		}
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
