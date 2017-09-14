import java.util.Arrays;
import java.util.Scanner;

public class RedGreenTowers
{
	/*
	 * This is solvable using dp. The dp state is dp(level, G). Where level is
	 * the current level we are building, and G is number of remaining green
	 * blocks. First level will not exceed ~900 so the time complexity is
	 * 900*2e5 which is acceptable, but the memory is not acceptable so it need
	 * dimension compression. To move to next level we calculate remaining red
	 * blocks, and try to make current level red or green and to advance to next
	 * level.
	 */
	static int r, g, mod = (int)1e9 + 7;
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		r = sc.nextInt();
		g = sc.nextInt();
		int h = bs(0, 900);
		int total = ((h)*(h+1))/2;
		int[][] dp = new int[2][g+1];
		int p = 0;
		int[] prod = new int[900];
		for (int i = 0; i < prod.length; i++)
		{
			prod[i] = ((i)*(i+1))/2;
		}
		Arrays.fill(dp[p], 1);
		for (int lvl = h-1; lvl >= 0; lvl--)
		{
			p = 1-p;
			for (int green = 0; green <= g; green++)
			{
				dp[p][green] = 0;
				int cur = h-lvl;
				if(green-cur >= 0)
				{
					dp[p][green] += dp[1-p][green-cur];
					if(dp[p][green] >= mod)
						dp[p][green]-=mod;
				}
				int red = r-(total - prod[cur] - (g-green));
				if(red-cur >= 0 && red <= r)
				{
					dp[p][green] += dp[1-p][green];
					if(dp[p][green] >= mod)
						dp[p][green]-=mod;
				}
			}
		}
		System.out.println(dp[p][g]);
	}
	
	static int bs(int st, int en)
	{
		if(st == en)
			return st;
		int mid = st + (en-st+1)/2;
		if(can(mid))
			return bs(mid, en);
		return bs(st, mid-1);
	}
	private static boolean can(int mid)
	{
		int[] tmp = {r,g};
		int lvl = mid;
		
		while(true)
		{
			if(lvl == 0)
				return true;
			int l = 0;
			if(tmp[1] > tmp[0])
				l = 1;
			if(tmp[l] < lvl)
				return false;
			tmp[l]-=lvl;
			lvl--;
		}
	}
}
