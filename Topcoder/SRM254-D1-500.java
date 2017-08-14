import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*
 * We use backtracking. The backtrack method return the permutation of squares that pigs will choose with the given configuration.
 * If all empty squares will make the current pig sandwiched. Then pigs will choose squares from left to right.
 * Else We add the pig to an empty square, then see permutation for new configuration, and use this permutation to calculate delay time.
 * We choose the left most square with max delay, and return this square + permutation of new configuration.
 * This solution runs in O((number of empty squares)!). Factorial works will to 11~12. So if empty slots are > 12, code will give TLE.
 * Code for empty squares > 12 runs in a few minutes. We can write code, let it run, get the solution, then hardcode it using a map for example.
 */

public class Piglets
{
	static TreeMap<String, Integer> map = new TreeMap<String, Integer>();

	public static void main(String[] args)
	{
//		arr = new char[11];
//		Arrays.fill(arr, '-');
//		arr[0] = arr[arr.length-1] = 'p';
//		int cnt = 0;
//		PrintWriter pw = new PrintWriter("pig.out");
//		System.out.println(findPerm());
//		
//		for (int i = 0; i < 1<<15; i++)
//		{
//			if(Integer.bitCount(i) == 3)
//			{
//				arr = new char[15];
//				int v = 0;
//				String s = "";
//				for (int j = 0; j < 15; j++)
//				{
//					if(((1<<j) & i) != 0)
//						arr[j] = 'p';
//					else
//						arr[j] = '-';
//				}
//				for (int j = 0; j < arr.length; j++)
//					s += arr[j];
//				StringTokenizer st = new StringTokenizer(findPerm());
//				v = Integer.parseInt(st.nextToken());
//				if(arr[0] == 'p' && arr[arr.length-1] == 'p')
//				{
//					pw.printf("map.put(%s,%d)\n", s, v);
//					cnt++;
//				}
//			}
//		}
//		pw.flush();
//		pw.close();
//		System.err.println(cnt);
//		System.out.println(findPerm());
		
		System.out.println(choose("p-------------p"));
	}
	
	public static int choose(String trough)
	{
		init();
		if(map.containsKey(trough))
			return map.get(trough);
		boolean dash = false;
		for (int i = 0; i < trough.length(); i++)
			if(trough.charAt(i) == '-')
				dash = true;
		if(!dash)
			return -1;
		arr = trough.toCharArray();
		String s = findPerm();
		StringTokenizer st = new StringTokenizer(s);
		return Integer.parseInt(st.nextToken());
	}
	
	static char[] arr;
	static String findPerm()
	{
		if(arr[0] != 'p')
			return "0";
		if(arr[arr.length-1] != 'p')
			return ""+(arr.length-1);
		boolean free = false;
		for (int i = 0; i < arr.length; i++)
			if(getArr(i) != 'p' && (getArr(i+1) != 'p' || getArr(i-1) != 'p'))
				free = true;
		String ret = "";
		if(!free)
		{
			for (int i = 0; i < arr.length; i++)
				if(arr[i] != 'p')
					ret += " " + i;
			return ret;
		}
		
		int max = -1;
		String nxt = "";
		int ind = -1;
		for (int i = 0; i < arr.length; i++)
		{
			if(getArr(i) != 'p' && (getArr(i+1) != 'p' || getArr(i-1) != 'p'))
			{
				arr[i] = 'p';
				String perm = findPerm();
				arr[i] = '-';
				int time = 1;
				char[] curr = Arrays.copyOf(arr, arr.length);
				StringTokenizer st = new StringTokenizer(perm);
				while(st.hasMoreTokens())
				{
					curr[Integer.parseInt(st.nextToken())] = 'p';
					char l = '-';
					char r = '-';
					if(i-1 >= 0)
						l = curr[i-1];
					if(i+1 < curr.length)
						r = curr[i+1];
					if(l == 'p' && r == 'p')
						break;
					else
						time++;
				}
				if(time > max)
				{
					max = time;
					nxt = perm;
					ind = i;
				}
			}
		}
		if(nxt.equals(""))
			System.out.print("");
		return ind+ " " + nxt;
	}
	
	static char getArr(int ind)
	{
		if(ind < 0 || ind >= arr.length)
			return '-';
		return arr[ind];
	}
	
	static void init()
	{
		map.put("p-------------p",12);
		map.put("pp------------p",12);
		map.put("p-p-----------p",12);
		map.put("p--p----------p",12);
		map.put("p---p---------p",12);
		map.put("p----p--------p",12);
		map.put("p-----p-------p",12);
		map.put("p------p------p",12);
		map.put("p-------p-----p",12);
		map.put("p--------p----p",12);
		map.put("p---------p---p",12);
		map.put("p----------p--p",12);
		map.put("p-----------p-p",10);
		map.put("p------------pp",11);
		map.put("p------------p",11);
		map.put("p-----------p",10);
		map.put("p----------p",9);
	}
}
