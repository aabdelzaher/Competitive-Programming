import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
 * In dp state we keep last character, and at which index we are. If the current state is invalid return 0
 * if current index is ? we try to put 0 1 2
 * If the we are in state where last is '*' and we want to put 2 in current cell, It is equivalent to state (1,ind+1) as the two already got one of two mines before it.
 */

public class R4D
{
	static int mod = 1000000007, n;
	static char[] arr;
	static int[][]dp;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner();
		arr = sc.next().toCharArray();
		n = arr.length;
		dp = new int[5][n+5];
		for (int i = 0; i < dp.length; i++)
				Arrays.fill(dp[i], -1);
		
		for (int i = n; i >= 0; i-=1000)
		{
			for (int j = 0; j < 5; j++)
			{
				solve(j, i);
			}
		}
		System.out.println(solve(4, 0));
	}
	
	// 3 -> *
	static int solve(int last, int ind)
	{
		if(ind == n)
		{
			if(last == 0 || (last == 3))
				return 1;
			else
				return 0;
		}
		if(dp[last][ind] != -1)
			return dp[last][ind];
		int cnt = 0;
		if(last == 4)
		{
			if(arr[ind] == '?' || arr[ind] == '0')
			{
				cnt += solve(0, ind+1);
				cnt %= mod;
			}
			if(arr[ind] == '1' || arr[ind] == '?')
			{
				cnt += solve(1, ind+1);
				cnt %= mod;
			}
			if(arr[ind] == '*' || arr[ind] == '?')
			{
				cnt += solve(3, ind+1);
				cnt %= mod;
			}
		}
		if(last == 0)
		{
			if(arr[ind] == '?' || arr[ind] == '0')
			{
				cnt += solve(0, ind+1);
				cnt %= mod;
			}
			if(arr[ind] == '1' || arr[ind] == '?')
			{
				cnt += solve(1, ind+1);
				cnt %= mod;
			}
		}
		if(last == 1)
		{
			if(arr[ind] == '*' || arr[ind] == '?')
			{
				cnt += solve(3, ind+1);
				cnt %= mod;
			}
		}
		if(last == 2)
			return 0;
		if(last  == 3)
		{
			if(arr[ind] == '?' || arr[ind] == '2')
			{
				cnt += solve(1, ind+1);
				cnt %= mod;
			}
			if(arr[ind] == '?' || arr[ind] == '1')
			{
				cnt += solve(0, ind+1);
				cnt %= mod;
			}
			if(arr[ind] == '*' || arr[ind] == '?')
			{
				cnt += solve(3, ind+1);
				cnt %= mod;
			}
		}
		cnt %= mod;
		return dp[last][ind] = cnt;
	}
	
	static class Scanner
	{
		BufferedReader br; StringTokenizer st;
		public Scanner() throws FileNotFoundException
		{
			br = new BufferedReader(new InputStreamReader(System.in));
//			br = new BufferedReader(new FileReader(new File("tc.out")));

		}
		
		public String next() throws IOException
		{
			while(st==null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
		
		public int nextInt() throws NumberFormatException, IOException
		{
			return Integer.parseInt(next());
		}
	}
}
