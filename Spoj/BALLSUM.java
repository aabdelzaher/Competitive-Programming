package mostafa_supervision;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class BALLSUM
{
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		Scanner sc = new Scanner();
		PrintWriter pw = new PrintWriter(System.out);
		while(true)
		{
			int n = sc.nextInt();
			int k = sc.nextInt();
			if(n == -1 && k == -1)
				break;
			long q = (1l*n*(n-1))/2;
			long p = 2*sumTo((k-1)/2);
			if(k%2 != 0)
				p -= ((k-1)/2);
			if(q == p)
				pw.println(1);
			else
				if(p == 0)
					pw.println(0);
				else
					pw.println((p/gcd(p, q)) +"/"+(q/gcd(p, q)));
		}
		pw.flush();
		pw.close();
	}
	
	static long sumTo(long k)
	{
		return (1l*k*(k+1))/2;
	}
	static long gcd(long a, long b)
	{
		if(b == 0)
			return a;
		return gcd(b, a%b);
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
