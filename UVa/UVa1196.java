import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * Sort according to one dimension.
 * The problem then is reduced to standard longest increasing subsequence.
 * The LIS implemented here is n*log(n)
 */
public class UVa1196
{
	static int[] arr;
	static int[] t;
	static int[] r;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
			{
				pw.println("*");
				break;
			}
			Pair[] p = new Pair[n];
			for (int i = 0; i < n; i++)
				p[i] = new Pair(sc.nextInt(), sc.nextInt());
			Arrays.sort(p);
			arr = new int[n];
			for (int i = 0; i < arr.length; i++)
				arr[i] = p[i].m;
			r = new int[n];
			t = new int[n];
			int len = 0;
			
			int end = 0;
			
			Arrays.fill(r, -1);
			Arrays.fill(t, -1);
			t[0] = 0;
			
			for (int i = 1; i < arr.length; i++)
			{
				if(arr[i] >= arr[t[end]])
				{
					end++;
					len++;
					t[end] = i;
					r[i] = t[end-1];
				}
				else
					if(arr[i] < arr[t[0]])
						t[0] = i;
					else
					{
						int ind = search(0, end, arr[i]);
						t[ind] = i;
						r[i] = t[ind-1];
					}
			}
			
			pw.println(len+1);
		}
		pw.flush();
		pw.close();
	}
	static class Pair implements Comparable<Pair>
	{
		int l, m;
		public Pair(int l, int m)
		{
			this.m = m;
			this.l  = l;
		}
		public int compareTo(Pair o)
		{
			if(l == o.l)
				return m - o.m;
			return l - o.l;
		}
	}

	private static int search(int st, int en, int target)
	{
		int mid = st+(en-st)/2;
		if(st == en)
			return st;
		if(arr[t[mid]] > target)
			return search(st, mid, target);
		else
			return search(mid+1, en, target);
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
		
		public double nextDouble() throws IOException
		{
			String x = next();
			StringBuilder sb = new StringBuilder("0");
			double res = 0, f = 1;
			boolean dec = false, neg = false;
			int start = 0;
			if(x.charAt(0) == '-')
			{
				neg = true;
				start++;
			}
			for(int i = start; i < x.length(); i++)
				if(x.charAt(i) == '.')
				{
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				}
				else
				{
					sb.append(x.charAt(i));
					if(dec)
						f *= 10;
				}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg?-1:1);
		}
		
		public boolean ready() throws IOException {return br.ready();}
	}
}
