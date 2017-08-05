import java.util.Arrays;

public class BestYahtzeeScore
{
	/*
	 * We try all possibilities. Try to replace 1,2,3,4 or 5 dices. We can do this by using a mask from 1 to 31
	 * We remove the set bits in the mask and replace them with next dices.
	 */
	public static void main(String[] args)
	{
		String s = "354621111111111";
		System.out.println(bestScore(s));
	}
	
	static int max;
	static int[] hand;
	static int ind;
	static String r;
	public static int bestScore(String rolls)
	{
		r = rolls;
		hand = new int[5];
		for (int i = 0; i < 5; i++)
			hand[i] = r.charAt(i)-'0';
		max = 0;
		ind = 5;
		solve(1);
		return max;
	}
	private static void solve(int roll)
	{
		calc(hand);
		if(roll == 3)
			return;
		int[] oldHand = Arrays.copyOf(hand, 5);
		for (int i = 1; i < 32; i++)
		{
			for (int j = 0; j < 5; j++)
				if((i & (1<<j)) != 0)
					hand[j] = r.charAt(ind++)-'0';
			
			solve(roll+1);
			
			hand = Arrays.copyOf(oldHand, 5);
			ind = ind - Integer.bitCount(i);
		}
	}
	
	
	
	
	
	private static void calc(int[] values)
	{
		int[] freq = new int[6];
		int sum = 0;
		for (int i = 0; i < values.length; i++)
		{
			freq[values[i]-1]++;
			sum += values[i];
		}
		
		for (int i = 0; i < freq.length; i++)
		{
			if(freq[i] == 5)
				max = Math.max(max, 50);
			if(freq[i] >= 4)
				max = Math.max(max, sum);
		}
		
		Arrays.sort(values);
		boolean f = false;
		for (int i = 1; i < values.length; i++)
		{
			if(values[i] != values[i-1] + 1)
			{
				f = true;
				break;
			}
		}
		
		if(!f)
			max = Math.max(max, 40);
		
		if(values[0] == values[1] && values[2] == values[3] && values[3] == values[4])
			max = Math.max(max, 25);
		
		if(values[0] == values[1] && values[1] == values[2] && values[3] == values[4])
			max = Math.max(max, 25);
		
		for (int i = 0; i+3 < freq.length; i++)
		{
			if(freq[i] >= 1 && freq[i+1] >= 1 && freq[i+2] >= 1 && freq[i+3] >= 1)
				max= Math.max(max, 30);
		}
	}
}
