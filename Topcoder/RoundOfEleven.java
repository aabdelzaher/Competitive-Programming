import java.util.Arrays;
/*
 * Number is divisible by 11 if alternative sum of digits is divisible by eleven.
 * We use dp. Our state is (ind, mod, money). Where money is the money left. mod represents current number % 11. ind represents the index of the digit we are at.
 * if(ind == length_of_n && mod == 0) 
 *		we add money to answer
 * else
 *		we try all digits [0-9] and move to state(ind+1, newMod, newMoney).
 */
public class RoundOfEleven
{
	static int M;
	static String N;
	static long[][][] dp;
	public static void main(String[] args)
	{
		System.out.println(maxIncome(19759, 435));
	}
	
	public static long maxIncome(int n, int money)
	{
		N = n+""; M = money;
		dp = new long[N.length()+5][11 + 5][money + 5];
		for (int i = 0; i < dp.length; i++)
			for (int j = 0; j < dp[i].length; j++)
				Arrays.fill(dp[i][j], -1);
		return solve(0,0,M);
	}

	private static long solve(int ind, int mod, int money)
	{
		if(money < 0)
			return 0;
		if(ind == N.length())
			if(mod == 0)
				return money;
			else
				return 0;
		if(dp[ind][mod][money] != -1)
			return dp[ind][mod][money];
		long ans = 0;
		for (int i = 0; i <= 9; i++)
		{
			int nwmod;
			if(ind%2 == 0)
				nwmod = (mod + i)%11;
			else
				nwmod = (mod - i + 11)%11;
			ans += solve(ind+1, nwmod, money - Math.abs(i - (N.charAt(ind)-'0')));
		}
		return dp[ind][mod][money] = ans;
	}
}
