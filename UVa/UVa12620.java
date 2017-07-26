package mostafa_supervision;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*
 * The given sequence (fibonacci %100) repeats every 300 element.
 * We need to add:
 * sum from l to start of next cycle
 * sum from end of last cycle to r
 * number of cycles in the middle * sum of one cycle
 */
public class UVa12620
{
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		Scanner sc = new Scanner();
		PrintWriter pw = new PrintWriter(System.out);
		int[] arr = new int[300];
		arr[0] = 1;
		arr[1] = 1;
		for (int i = 0; i+2 < arr.length; i++)
			arr[i+2] = (arr[i] + arr[i+1])%100;
		
		long sum = 0;
		for (int i = 0; i < arr.length; i++)
			sum += arr[i];
		
		int tc = sc.nextInt();
		while(tc-->0)
		{
			long a = sc.nextLong()-1;
			long b = sc.nextLong()-1;
			long ans = 0;
			if(b-a <= 600)
			{
				for (long i = a; i <= b; i++)
					ans += arr[(int)(i%300)];
			}
			else
			{
				long from = a;
				long to = b;
				for (;; from++)
				{
					if(from%300 == 0)
						break;
					ans += arr[(int)(from%300)];
				}
				
				for (;; to--)
				{
					if(to%300 == 299)
						break;
					ans += arr[(int)(to%300)];
				}
				
				ans += sum * ((to-from+1)/300);
			}
			pw.println(ans);
		}
		pw.flush();
		pw.close();
	}
	
	static class Scanner
	{
		BufferedReader br; StringTokenizer st;
		public Scanner()
		{
			br = new BufferedReader(new InputStreamReader(System.in));
		}
		public String next() throws IOException
		{
			while(st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
		public int nextInt() throws NumberFormatException, IOException
		{
			return Integer.parseInt(next());
		}
		
		public long nextLong() throws NumberFormatException, IOException
		{
			return Long.parseLong(next());
		}
	}
}
