import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * We need to check primality of a number in a fast way. We can perform sieve first then we can check if a number is prime in o(1)
 */
public class UVa12802
{
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		boolean[] primes = new boolean[1000010];
		Arrays.fill(primes, true);
		primes[0] = primes[1] = false;
		for (int i = 2; i*i <= primes.length; i++)
		{
			if(primes[i])
				for (int j = i*2; j < primes.length; j += i)
					primes[j] = false;
		}
		
		Scanner sc = new Scanner();
		PrintWriter pw = new PrintWriter(System.out);
		while(true)
		{
			int n = sc.nextInt();
			pw.println(n*2);
			if(primes[n])
			{
				String s = "" + n;
				String sr = new StringBuilder(s).reverse().toString();
				if(s.equals(sr))
					break;
			}
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
