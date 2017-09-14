import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class HACKR_VerticalSticks
{
	/*
	 * We can use linearity of expectation, so instead of forming v and adding
	 * its values, we calculate expected value for every vi, and sum all these
	 * values. To calculate expected value vi we multiply every possible value
	 * of vi (1 ~ i) and add it to expectation of vi. To calculate probability
	 * of having vi = x, we cover case of having x = i, so we should have x-1
	 * shorter sticks before it, otherwise we need to have x-1 shorter sticks
	 * before it, and one longer sticks immediately before those shorter sticks.
	 */
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		int tc = sc.nextInt();
		while(tc-->0)
		{
			int n = sc.nextInt();
			int[] cum = new int[1005];
			int[] arr = new int[n];
			for (int i = 0; i < n; i++)
			{
				arr[i] = sc.nextInt();
				cum[arr[i]+1]++;
			}
			for (int i = 1; i < cum.length; i++)
				cum[i]+=cum[i-1];
			double ans = 0;
			for (int pos = 0; pos < arr.length; pos++)
				for (int i = 0; i < n; i++)
				{
					int cur = arr[i];
					int lt = cum[cur];
					int gte = n-lt-1;
					double add = 0;
					for (int len = 0; len <= pos; len++)
					{
						if(len <= lt && (gte >= 1 || pos == len))
						{
							double p = 1;
							int tmpl = lt;
							int tmpn = n-1;
							for (int j = 0; j < len; j++)
							{
								p *= ((1.0*tmpl--)/tmpn--);
							}
							if(gte > 0 && pos != len)
								p*= 1.0*gte/(n-1-len);
							add += p * (len+1);
						}
					}
					ans  += add/n;
				}
			
			System.out.printf("%.2f\n", ans);
		}
	}
	
	static int nCr4(int N, int K)
	{
		if(K > N)
			return 0;
		int res = 1;
		for(int i = 1; i <= K; ++i)
			res = (N - K + i) * res / i;
		return res;
	}
	
	static class Scanner 
	{
		StringTokenizer st; BufferedReader br;
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
