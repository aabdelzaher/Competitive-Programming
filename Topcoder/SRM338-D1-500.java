import java.util.Arrays;

/*
 * If swaps limit was smaller, we could solve the problem using dp by keeping (swap_ind, pos).
 * Each transition we can choose pair containing number at current pos, so we move to another pos, or choose another pair and leave it in its place.
 * This is a correct solution but will give TLE O(n^2 * swaps). Actually we do not need to keep the position.
 * We can just keep track of swap_ind, and a flag to determine if the element is in the correct position or not.
 * The complexity is O(swaps*n).
 */

public class RandomSwaps
{
	static double[][] dp;
	static int n, swaps, target;
	public static void main(String[] args)
	{
		System.out.println(getProbability(1000,100000,3,3));
	}

	public static double getProbability(int arrayLength, int swapCount, int a, int b)
	{
		n = arrayLength; swaps = swapCount; target = b;
		dp = new double[2][swaps+5];
		
		for (int i = 0; i < dp.length; i++)
			Arrays.fill(dp[i], -3);
		for (int i = swapCount-1; i >= 0; i-=1000)
		{
			dp(1, i);
			dp(0, i);
		}
		if(a == b)
			return dp(1,0);
		else
			return dp(0,0);
	}
	
	static double dp(int inPos, int ind)
	{
		if(ind == swaps)
			if(inPos == 1)
				return 1;
			else
				return 0;
		
		if(dp[inPos][ind] > -1)
			return dp[inPos][ind];
		
		double ans = 0;
		if(inPos == 1)
		{
			double pi = (2.0*(n-1))/((n-1)*n);
			ans += pi*dp(0, ind+1);
			ans += (1-pi)*dp(1, ind+1);
		}
		else
		{
			double pp = 2.0/((n-1)*n); 
			ans += pp*dp(1, ind+1);
			ans += (1-pp)*dp(0, ind+1);
		}
		
		return dp[inPos][ind] = ans;
	}
	
}
