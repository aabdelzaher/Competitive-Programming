import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * The first player should remove leftmost 1 and the second player should remove leftmost 0.
 * So players removes 1 and 0 alternatively. Thus if number of 0's > 1's then the answer is 00.
 * If number of 1's > 0's + 1 then the answer is 11. Note that We can convert '?' to 0s or 1s.
 * Finally if (0's == 1's) or (1's == 0's + 1) Then the result is either 01 or 10.
 * What controls the result being 01 or 10 is the rightmost character. We use '?' to try to make zeros and ones equal or ones - zeros == 1.
 */
public class ZeroOne
{
	static char[] arr;
	public static void main(String[] args) throws IOException {
		
		Scanner sc = new Scanner(System.in);
		arr = sc.next().toCharArray();
		int ones = 0;
		int zeros = 0;
		int q = 0;
		for (int i = 0; i < arr.length; i++) {
			if(arr[i] == '0')
				zeros++;
			else if(arr[i] == '1')
				ones++;
			else
				q++;
		}
		
		String[] s = {"00", "01", "10", "11"};
		boolean[] f = new boolean[4];
		
		if(zeros + q > ones)
			f[0] = true;
		if(ones + q - zeros >= 2)
			f[3] = true;
		
		boolean toOne = false;
		boolean toZero = false;
		for (int i = 0; i < arr.length; i++)
		{
			if(arr[i] == '?')
			{
				if(ones <= zeros)
				{
					ones++;
					toOne = true;
				}
				else
				{
					zeros ++;
					toZero = true;
				}
			}
		}
		
		if(zeros == ones || zeros + 1 == ones)
		{
			if(arr[arr.length-1] == '1')
				f[1] = true;
			else
				if(arr[arr.length-1] == '0')
					f[2] = true;
				else
				{
					if(toOne)
						f[1] = true;
					if(toZero)
						f[2] = true;
				}
		}
		
		for (int i = 0; i < f.length; i++)
		{
			if(f[i])
				System.out.println(s[i]);
		}
	}
	
	static class Scanner 
	{
		StringTokenizer st;BufferedReader br;
		public Scanner(InputStream s){	br = new BufferedReader(new InputStreamReader(s));}
		public String next() throws IOException 
		{
			while (st == null || !st.hasMoreTokens()) 
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
		public int nextInt() throws IOException {return Integer.parseInt(next());}
		public long nextLong() throws IOException {return Long.parseLong(next());}
		public String nextLine() throws IOException {return br.readLine();}
		public boolean ready() throws IOException {return br.ready();}
	}
}
