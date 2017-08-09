import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.StringTokenizer;

/*
 * One can notice that the answer is at most sqrt(n).
 * So we only have sqrt(n) candidates that might have cnt(x) = x i a range.
 * We will make cumulative array for each number in those candidates. We count occurrences of such candidate.
 * For each query we loop over all candidates and check if its occurrences in range equals to the number itself.
 * Total complexity is o(n*sqrt(n))
 */

public class LittleElephantAndArray
{
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int n = sc.nextInt();
		int q = sc.nextInt();
		int[] arr = new int[n];
		int[] cnt = new int[100005];
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = sc.nextInt();
			if(arr[i] <= n)
				cnt[arr[i]]++;
		}
		HashSet<Integer> imp = new HashSet<Integer>();
		int r = 0, rnk[] = new int[n+5];
		for (int i = 1; i < cnt.length; i++)
			if(cnt[i] >= i)
			{
				imp.add(i);
				rnk[i] = r++;
			}
		
		int cntImp = imp.size();
		int[][] cum = new int[cntImp][n+1];
		for (int i = 0; i < arr.length; i++)
			if(imp.contains(arr[i]))
				cum[rnk[arr[i]]][i+1]++;
		
		for(int[] array: cum)
			for (int i = 1; i < array.length; i++)
				array[i] += array[i-1];
		
		for (int i = 0; i < q; i++)
		{
			int f = sc.nextInt();
			int t = sc.nextInt();
			int ans = 0;
			for (int candidate : imp)
			{
				int occ = cum[rnk[candidate]][t] - cum[rnk[candidate]][f-1];
				if(occ == candidate)
					ans++;
			}
			pw.println(ans);
		}
		pw.flush();
		pw.close();
	}
	
	static class Scanner 
	{
		StringTokenizer st; BufferedReader br;
		public Scanner(InputStream s)
		{
			br = new BufferedReader(new InputStreamReader(s));
		}
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
