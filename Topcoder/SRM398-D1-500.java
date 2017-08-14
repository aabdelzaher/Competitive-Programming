import java.util.Arrays;
import java.util.HashMap;

/*
 * Dp with state (row, col, last_dif_idx, remaining_dif_fields)
 * We move up or right each time, and if the field is special, and can be taken, we minus one from remaining, and update idx.
 * To know the index of each special cell, we may add them to a map, or use 2d-array.
 */

public class CountPaths
{

	public static void main(String[] args)
	{
		int r = 50;
		int c = 50;
		int[] fieldrow = {50, 1};
		int[] fieldcol = {50,1};
		
		System.out.println(Arrays.toString(difPaths(r, c, fieldrow, fieldcol)));
	}
	
	static int k, mod = 1000007;
	static int n, m;
	static HashMap<String, Integer> map;
	static int[][][][] dp;

	public static int[] difPaths(int r, int c, int[] fieldrow, int[] fieldcol)
	{
		n = r; m = c;
		k = fieldcol.length;
		map = new HashMap<String, Integer>();
		for (int i = 0; i < k; i++)
		{
			String s = (fieldrow[i]-1) + " " + (fieldcol[i]-1);
			map.put(s, i+1);
		}
		
		int[] ret = new int[k+1];
		dp = new int[51][51][52][51];
		for (int i = 0; i < dp.length; i++)
			for (int j = 0; j < dp[i].length; j++)
				for (int j2 = 0; j2 < dp[i][j].length; j2++)
					Arrays.fill(dp[i][j][j2], -1);

		for (int len = 0; len <= k; len++)
		{
			if(map.containsKey(0 + " " + 0))
			{
				int index = map.get(0 + " " + 0);
				if(len > 0)
					ret[len] = solve(0, 0, index, len-1);
				else
					ret[len] = 0;
			}
			else
			{
				ret[len] = solve(0,0,0,len);
			}
		}
		
		return ret;
	}
	
	static int[] dx = {0, 1};
	static int[] dy = {1, 0};
	private static int solve(int i, int j, int last, int rem)
	{
		if(i == n-1 && j == m-1)
			if(rem == 0)
				return 1;
			else
				return 0;
		
		if(dp[i][j][last][rem] != -1)
			return dp[i][j][last][rem];
		
		int ans = 0;
		for (int id = 0; id < dx.length; id++)
		{
			int ni = i + dx[id];
			int nj = j + dy[id];
			if(ni < n && nj < m)
			{
				if(map.containsKey(ni + " " + nj))
				{
					int index = map.get(ni + " " + nj);
					if(index > last && rem > 0)
						ans = (ans + solve(ni, nj, index, rem-1))%mod;
				}
				else
					ans = (ans + solve(ni, nj, last, rem))%mod;
			}
		}
		
		return dp[i][j][last][rem] = ans;
	}
}
