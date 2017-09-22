import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class UVa12167
{
	/*
	 * We construct graph where statements are nodes, and equivalence are edges.
	 * The answer of the problem is the minimum number of edges to get one
	 * strongly connected component. First, we construct the component graph.
	 * The answer is the maximum of nodes with no entering edges(roots) and
	 * nodes with no exiting edges(leafs). Her is the logic behind it, every two
	 * nodes u, v where we can get from u to v we can simplify u and v and nodes
	 * between them as only one node, we can draw edge from v to any other
	 * component and from this component to u and make the two components
	 * connected. So we simplify every pair and nodes between them to only one
	 * node, the number of nodes will equal the maximum of roots and leafs. And
	 * to connect n disconnected nodes we need n edges.
	 */
	static ArrayList<Integer>[] adj, adjR;
	static int vid, vis[], cc;
	static ArrayList<Integer> order;
	static int[] comp;
	
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while(tc-- > 0)
		{
			int n = sc.nextInt();
			int m = sc.nextInt();
			adj = new ArrayList[n];
			adjR = new ArrayList[n];
			vis = new int[n];
			vid = 1;
			cc = 0;
			for (int i = 0; i < n; i++)
			{
				adj[i] = new ArrayList<Integer>();
				adjR[i] = new ArrayList<Integer>();
			}
			for (int i = 0; i < m; i++)
			{
				int a = sc.nextInt()-1;
				int b = sc.nextInt()-1;
				adj[a].add(b);
				adjR[b].add(a);
			}
			
			order = new ArrayList<Integer>();
			comp = new int[n];
			
			for (int i = 0; i < n; i++)
				if(vis[i] != vid)
					dfs1(i);
			
			vid++;
			int len = order.size();
			for (int i = len-1; i >= 0; i--)
			{
				if(vis[order.get(i)] != vid)
				{
					dfs2(order.get(i));
					cc++;
				}
			}
			
			if(cc == 1)
			{
				pw.println(0);
				continue;
			}
			int[] inDeg = new int[cc];
			int[] outDeg = new int[cc];
			
			for (int u = 0; u < n; u++)
				for (int v : adj[u])
				{
					int c1 = comp[u];
					int c2 = comp[v];
					if(c1 != c2)
					{
						inDeg[c2]++;
						outDeg[c1]++;
					}
				}
			
			int cnt2 = 0;
			int cnt1 = 0;
			for (int i = 0; i < inDeg.length; i++)
			{
				if(inDeg[i] == 0)
					cnt1++;
				if(outDeg[i] == 0)
					cnt2++;
			}
			
			pw.println(Math.max(cnt1, cnt2));
			
		}
		pw.flush();
		pw.close();
	}
	
	private static void dfs1(int u)
	{
		vis[u] = vid;
		for (int v : adj[u])
			if(vis[v] != vid)
				dfs1(v);
		
		order.add(u);
	}
	
	private static void dfs2(int u)
	{
		vis[u] = vid;
		comp[u] = cc;
		for (int v : adjR[u])
			if(vis[v] != vid)
				dfs2(v);
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
