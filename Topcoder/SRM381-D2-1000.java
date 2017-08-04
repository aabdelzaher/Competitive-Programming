import java.util.Arrays;
import java.util.Comparator;

/*
 * If n > length_of_numbers we fill the remaining slots with the largest number
 * We need to concatenate the numbers in a certain way, so we need to sort the array
 * let s1, s2 are two strings if(s1+s2 < s2+s1) then s2 should come before s1 in the result and vice versa. That is our comparator.
 */
public class TheNumbersLord
{
	static Comparator<String> c;
	
	public static String create(int[] numbers, int n)
	{
		c = new Comparator<String>()
				{
					public int compare(String o1, String o2)
					{
						String l1 = o1+o2;
						String l2 = o2+o1;
						return l2.compareTo(l1);
					}
				};
		String[] arr = new String[n];
		int max = -1;
		for (int i = 0; i < numbers.length; i++)
		{
			arr[i] = numbers[i] + "";
			max = Math.max(max, numbers[i]);
		}
		for (int i = numbers.length; i < arr.length; i++)
			arr[i] = "" + max;
		Arrays.sort(arr, c);
		StringBuilder ans = new StringBuilder();
		for (int i = arr.length-1; i >= 0; i--)
			ans.append(arr[i]);
		return ans.toString();
	}
}
