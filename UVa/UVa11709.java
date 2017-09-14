import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

public class UVa11709
{
	/*
	 * The problems asks for number of strongly connected components in a graph
	 */
	static HashMap<String, Integer> map;
	static int count;
	static ArrayList<Integer>[] adjList;
	static int V, counter, SCC, dfs_num[], dfs_low[];
	static boolean[] inSCC;
	static Stack<Integer> stack;

	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		map = new HashMap<String, Integer>();
		while (true)
		{
			int n = sc.nextInt();
			V = n;
			dfs_num = new int[n];
			dfs_low = new int[n];
			inSCC = new boolean[n];
			SCC = 0;
			stack = new Stack<Integer>();
			int m = sc.nextInt();
			if (n == 0 && m == 0)
				break;
			count = 0;
			map.clear();
			adjList = new ArrayList[n];
			for (int i = 0; i < adjList.length; i++)
				adjList[i] = new ArrayList<Integer>();
			for (int i = 0; i < n; i++)
			{
				String name = sc.nextLine();
				map.put(name, count++);
			}
			for (int i = 0; i < m; i++)
			{
				int f = map.get(sc.nextLine());
				int t = map.get(sc.nextLine());
				adjList[f].add(t);
			}

			tarjanSCC();
			pw.println(SCC);
		}
		pw.flush();
		pw.close();
	}

	static void tarjanSCC()
	{
		for (int i = 0; i < V; ++i)
			if (dfs_num[i] == 0)
				tarjanSCC(i);
	}

	static void tarjanSCC(int u)
	{
		dfs_num[u] = dfs_low[u] = ++counter;
		stack.push(u);

		for (int v : adjList[u])
		{
			if (dfs_num[v] == 0)
				tarjanSCC(v);
			if (!inSCC[v])
				dfs_low[u] = Math.min(dfs_low[u], dfs_low[v]);
		}
		if (dfs_num[u] == dfs_low[u])
		{
			SCC++;
			while (true)
			{
				int v = stack.pop();
				inSCC[v] = true;
				if (v == u)
					break;
			}
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
			return Double.parseDouble(next());
		}

		public boolean ready() throws IOException
		{
			return br.ready();
		}
	}
}
