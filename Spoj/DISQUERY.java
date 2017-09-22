import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class DISQUERY
{
	/*
	 * One approach to calculate LCA is binary lifting. This problem can be
	 * solved by finding min and max distance to lca from each node given in the
	 * query. To calculate minimum and maximum in O(log n) time we keep two
	 * 2d-arrays min & max. They are similary to ancestor 2d-array to get LCA.
	 * We build them with LCA pre processing. We get values from them while
	 * computing the lca.
	 */
	static ArrayList<Pair>[] adj;
	static int n;
	static int[] t;
	static int[] cost;
	static int[] l;
	static int[][] p, min, max;
	static int log, inf = (int) 1e9;

	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		t = new int[n];
		l = new int[n];
		vis = new boolean[n];
		cost = new int[n];
		log = (int) (Math.log(n) / Math.log(2));
		p = new int[log + 1][n];
		min = new int[log + 1][n];
		max = new int[log + 1][n];
		adj = new ArrayList[n];
		for (int i = 0; i < adj.length; i++)
			adj[i] = new ArrayList<Pair>();
		for (int i = 0; i < n - 1; i++)
		{
			int f = sc.nextInt() - 1;
			int to = sc.nextInt() - 1;
			int c = sc.nextInt();
			adj[f].add(new Pair(to, c));
			adj[to].add(new Pair(f, c));
		}

		bfs(0);

		for (int i = 0; i < p.length; i++)
		{
			Arrays.fill(p[i], -1);
			Arrays.fill(min[i], inf);
		}

		for (int i = 0; i < n; i++)
		{
			p[0][i] = t[i];
			min[0][i] = cost[i];
			max[0][i] = cost[i];
		}

		for (int j = 1; (1 << j) < n; j++)
			for (int i = 0; i < n; i++)
			{
				if (p[j - 1][i] != -1)
				{
					p[j][i] = p[j - 1][p[j - 1][i]];
					min[j][i] = Math
							.min(min[j - 1][i], min[j - 1][p[j - 1][i]]);
					max[j][i] = Math
							.max(max[j - 1][i], max[j - 1][p[j - 1][i]]);
				}
			}

		int q = sc.nextInt();
		for (int i = 0; i < q; i++)
		{
			int mx = 0, mn = inf;
			int a = sc.nextInt() - 1;
			int b = sc.nextInt() - 1;

			if (l[b] < l[a])
			{
				int tmp = b;
				b = a;
				a = tmp;
			}

			for (int j = log; j >= 0; j--)
				if (l[b] - (1 << j) >= l[a])
				{
					mx = Math.max(mx, max[j][b]);
					mn = Math.min(mn, min[j][b]);
					b = p[j][b];
				}

			int lca = 0;
			if (a == b)
			{
				lca = a;
			} else
			{

				for (int j = log; j >= 0; j--)
				{
					if (p[j][b] != -1 && p[j][a] != -1 && p[j][b] != p[j][a])
					{
						mn = Math.min(mn, min[j][a]);
						mn = Math.min(mn, min[j][b]);
						mx = Math.max(mx, max[j][a]);
						mx = Math.max(mx, max[j][b]);
						a = p[j][a];
						b = p[j][b];
					}
				}

				lca = t[a];
				if (a != 0)
				{
					mn = Math.min(mn, cost[a]);
					mx = Math.max(mx, cost[a]);
					mn = Math.min(mn, cost[b]);
					mx = Math.max(mx, cost[b]);
				}
			}
			System.out.println(mn + " " + mx);
		}
	}

	static boolean vis[];

	private static void bfs(int root)
	{
		vis[root] = true;
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(root);
		l[root] = 0;

		while (!q.isEmpty())
		{
			int u = q.poll();
			for (Pair v : adj[u])
			{
				if (!vis[v.to])
				{
					vis[v.to] = true;
					l[v.to] = l[u] + 1;
					t[v.to] = u;
					cost[v.to] = v.cost;
					q.add(v.to);
				}
			}
		}
	}

	static class Pair
	{
		int to, cost;

		public Pair(int t, int c)
		{
			to = t;
			cost = c;
		}
	}

	static class Scanner
	{
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream s)
		{
			br = new BufferedReader(new InputStreamReader(s));
		}

		public Scanner(String s) throws FileNotFoundException
		{
			br = new BufferedReader(new FileReader(new File((s))));
		}

		public String next() throws IOException
		{
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public int nextInt() throws IOException
		{
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException
		{
			return Long.parseLong(next());
		}

		public String nextLine() throws IOException
		{
			return br.readLine();
		}

		public double nextDouble() throws IOException
		{
			String x = next();
			StringBuilder sb = new StringBuilder("0");
			double res = 0, f = 1;
			boolean dec = false, neg = false;
			int start = 0;
			if (x.charAt(0) == '-')
			{
				neg = true;
				start++;
			}
			for (int i = start; i < x.length(); i++)
				if (x.charAt(i) == '.')
				{
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				} else
				{
					sb.append(x.charAt(i));
					if (dec)
						f *= 10;
				}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg ? -1 : 1);
		}

		public boolean ready() throws IOException
		{
			return br.ready();
		}
	}
}
