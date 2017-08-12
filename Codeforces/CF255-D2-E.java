import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.StringTokenizer;

/*
 * Printing grundy function for first few numbers will lead to observation that grundy values remains the same for long ranges.
 * Knowing this observation one can write a brute force to see those ranges and their grundy values. There exists only 6 ranges.
 * Now we can compute grundy of any number on O(1). 
 * We compute xl & xr. Then for every range if xl & xr intersects with this range then we add grundy value of range in their grundy set. Then get the mex.
 */
public class FurloAndRubloAndGame
{
	public static void main(String[] args) throws IOException
	{
		PrintWriter pw = new PrintWriter(System.out);
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int ans = 0;
		String fst = "Furlo";
		String snd = "Rublo";

		Point[] segments = new Point[6];
		segments[0] = new Point(0, 3);
		segments[1] = new Point(4, 15);
		segments[2] = new Point(16, 81);
		segments[3] = new Point(82, 6723);
		segments[4] = new Point(6724, 50625);
		segments[5] = new Point(50626, 10000000);

		int g[] = new int[6];
		g[0] = 0;
		g[1] = 1;
		g[2] = 2;
		g[3] = 0;
		g[4] = 3;
		g[5] = 1;

		for (int i = 0; i < n; i++)
		{
			long x = sc.nextLong();
			int xl = (int) Math.pow(x, 1.0 / 4);
			while (p4(xl) < x)
				xl++;
			int xr = (int) Math.sqrt(x);
			while (sq(xr) <= x)
				xr++;
			xr--;

			if (xr == x)
				xr--;

			HashSet<Integer> mex = new HashSet<Integer>();
			for (int j = 0; j < segments.length; j++)
			{
				if (intersect(new Point(xl, xr), segments[j]))
					mex.add(g[j]);
			}
			ans ^= getMex(mex);
		}
		if (ans == 0)
			pw.println(snd);
		else
			pw.println(fst);
		pw.flush();
		pw.close();

	}

	private static boolean intersect(Point p1, Point p2){return Math.max(p1.x, p2.x) <= Math.min(p1.y, p2.y);}
	static long p4(int x){return 1l * x * x * x * x;}
	static long sq(int x){return 1l * x * x;}

	static int getMex(HashSet<Integer> set)
	{
		int mex = 0;
		while (set.contains(mex))
			mex++;
		return mex;
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
