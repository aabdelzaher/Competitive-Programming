package supervision;

import java.util.Arrays;

/*
 * for each state i we compute what is the next state j that this sequence of states from i to j is unique
 * if at any state no sequence from i to j is unique then output -1
 * the result is maximum of moving from i to j + moving from j to target
 */

public class SimpleIO
{
	static char[] arr;
	static int[] move;
	public static void main(String[] args)
	{
		System.out.println(worstCase("BBNNBNBBBBNBBBBBB", 3));
	}
	
	public static int worstCase(String pattern, int targetState)
	{
		arr = pattern.toCharArray();
		move = new int[arr.length];
		Arrays.fill(move, -1);
		for (int i = 0; i < arr.length; i++)
		{
			boolean found = false;
			int len = 1;
			for (len = 1; len < arr.length; len++)
			{
				found = found(i, len);
				if(!found)
					break;
			}
			if(!found)
				move[i] = len;
		}
		
		for (int i = 0; i < move.length; i++)
			if(move[i] == -1)
				return -1;
		
		int max = 0;
		for (int i = 0; i < move.length; i++)
		{
			int sum = move[(i+1)%move.length];
			int nwind = i + move[(i+1)%move.length];
			nwind %= arr.length;
			sum += (targetState - nwind + arr.length)%arr.length;
			max = Math.max(sum, max);
		}
		return max;
	}
	
	public static boolean found(int i, int len)
	{
		int j = i+1;
		int l = 0;
		j %= arr.length;
		int p1 = i, p2 = j; 
		while(j != i)
		{
			if(arr[p1] == arr[p2])
			{
				l++;
				p1++;
				p1 %= arr.length;
				p2++;
				p2 %= arr.length;						
				if(l == len)
					return true;
			}
			else
			{
				l = 0;
				p1 = i;
				j = (j+1)%arr.length;
				p2 = j;
			}
		}
		return false;
	}
}
