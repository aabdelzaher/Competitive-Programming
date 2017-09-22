import java.util.Arrays;

public class Deranged
{
	/*
	 * It is easier to calculate number of non-derangements(where there is at
	 * least one element in the correct position), then return total number of
	 * permutations minus non-derangements. To calculate non-derangements we use
	 * inclusion-exclusion principle.
	 */
	public static void main(String[] args)
	{
		int[] nums = {1,1,2,2,3};
		System.out.println(numDerangements(nums));
	}
	
	static int n;
	static int[] freq, tmp;
	static long[] fact;
	static long cnt = 0;
	public static long numDerangements(int[] nums)
	{
		n = nums.length;
		Arrays.sort(nums);
		freq = new int[n];
		tmp = new int[n];
		fact = new long[n+1];
		fact[0] = fact[1] = 1;
		for (int i = 2; i < fact.length; i++)
			fact[i] = fact[i-1]*i;
		for (int i = 0; i < nums.length; i++)
			freq[nums[i]]++;
		long ans = 0;
		for (int set = 1; set < 1<<n; set++)
		{
			int ones = 0;
			long val = 0;
			tmp = Arrays.copyOf(freq, n);
			for (int i = 0; i < n; i++)
			{
				if(((1<<i)&set) != 0)
				{
					ones++;
					tmp[nums[i]]--;
				}
			}
			val = fact[n-ones];
			for (int i = 0; i < n; i++)
				val /= fact[tmp[i]];
			if((ones & 1) == 1)
				ans += val;
			else
				ans -= val;
			
			System.err.println(set + " " + val);
		}
		long total = fact[n];
		for (int i = 0; i < n; i++)
			total /= fact[freq[i]];
		return total-ans;
	}
}
