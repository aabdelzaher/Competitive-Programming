
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * Every vase is represented as a binary number (36 bit) where the ith bit is set if this vase exist with pattern i
 * To have all the collection of k types and k patterns, then we should have k*k vase.
 * Thus maximum k is sqrt(m). Since m <= 100, then k <= 10. We try all k up to 10, if we can find k*k collection then
 * increment k by 1 else our answer is k-1 (last valid k). To check if there exist k*k collection, we can brute force(backtrack).
 * Every time we keep track of k we want to reach, number of vases taken so far, index of vase we are at, and mask representing available patterns.
 * At each step we can either take the vase (ind+1, k, cnt+1, msk&v[ind]) or leave it.
 * To compute the complexity of the code, At maximum we take 10 from the 36 type, so to try all possibilities we need to try 36 choose 10 option.
 */
public class LIVEARCHIVE2932
{
	static long[] vase;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		int tc = sc.nextInt();
		while(tc -- > 0)
		{
			int m = sc.nextInt();
			vase = new long[36];
			for (int i = 0; i < m; i++)
			{
				int v = sc.nextInt()-1;
				int p = sc.nextInt()-1;
				vase[v] |= (1l << p);
			}
			
			int ans = 1;
			for (; ans*ans <= m && ok(0, ans, 0, (1l << 36) - 1); ans++);
			System.out.println(ans - 1);
		}
	}
	
	private static boolean ok(int ind, int k, int cnt, long mask)
	{
		if(Long.bitCount(mask) < k)
			return false;
		if(cnt == k)
			return true;
		if(ind >= 36)
			return false;
		return ok(ind+1, k, cnt, mask) || ok(ind+1, k, cnt+1, mask & vase[ind]);
	}

	static class Scanner 
	{
		StringTokenizer st;
		BufferedReader br;

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
		public double nextDouble() throws IOException{return Double.parseDouble(next());}
		public boolean ready() throws IOException {return br.ready();}
	}
}
