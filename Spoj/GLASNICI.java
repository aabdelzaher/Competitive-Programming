import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*
 * We use binary search. If news can be spread in time t, surely it can be spread in time > t.
 * To know if we can spread the news in time t. For every messenger we calculate the furthest distance he should be at (to hear from the previous one).
 * And also we calculate the furthest distance he can reach int time t. The messanger should be at the minimum of both distances.
 * If a messanger can not reach minimum of both distances within time t, then the nes can not be spread in time t and we try bigger t, else we try time less than t.
 */
public class GLASNICI
{
	static double shout;
	static double[] m;
	static int n;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		int tc = sc.nextInt();
		PrintWriter pw = new PrintWriter(System.out);
		while(tc-->0)
		{
			shout = sc.nextDouble();
			n = sc.nextInt();
			m = new double[n];
			for (int i = 0; i < n; i++)
				m[i] = sc.nextDouble();
			
			pw.printf("%.3f\n", bs(0, 1e10));
		}
		pw.flush();
		pw.close();
	}
	
	private static double bs(double st, double en)
	{
		for (int i = 0; i < 100; i++)
		{
			double mid = (st + en)/2;
			if(ok(mid))
				en = mid;
			else
				st = mid;
		}
		return st;
	}

	private static boolean ok(double time)
	{
		double far = 1e10;
		double eps = 1e-9;
		for (int i = 0; i < m.length; i++)
		{
			double can = m[i] + time;
			double at = Math.min(can, far);
			if(Math.abs(at - m[i]) > time+eps)
				return false;
			far = at + shout;
		}
		return true;
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
		public double nextDouble() throws IOException {return Double.parseDouble(next());}
		public boolean ready() throws IOException {return br.ready();}
	}
}
