import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CAPCITY
{
	/*
	 * Suppose given graph is G and its reverse is GR. We need to find SSC in GR
	 * that reaches all other components If we do dfs on a graph and store
	 * out_time of nodes, in component graph if there is an edge u->v then
	 * out_time of largest node in component u should be greater than that of
	 * component v. So the component we are looking for (if it exists) contains
	 * node of largest out_time when doing dfs on GR. We get SCC of that node,
	 * and check that it reaches every node in GR before outputting it, else the
	 * answer is zero.
	 */
	static ArrayList<Integer>[] adj, adjR;
	static int vid, vis[], cc, node = -1;
	static ArrayList<Integer> order;
	static boolean[] comp;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		adj = new ArrayList[n];
		adjR = new ArrayList[n];
		order = new ArrayList<Integer>();
		comp = new boolean[n];
		for (int i = 0; i < adj.length; i++)
		{
			adj[i] = new ArrayList<Integer>();
			adjR[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < m; i++)
		{
			int f = sc.nextInt()-1;
			int t = sc.nextInt()-1;
			adj[f].add(t);
			adjR[t].add(f);
		}
		vis = new int[n]; vid ++;
		for (int i = 0; i < n; i++)
			if(vis[i] != vid)
				dfs1(i);
		
		int sz = order.size();
		vid++;
		dfs2(order.get(sz-1));
		vid++;
		if(cc > 0)
		{
			dfs(node);
		}
		for (int v : vis)
			if(v != vid)
			{
				System.out.println("0");
				return;
			}
		
		PrintWriter pw = new PrintWriter(System.out);
		pw.println(cc);
		for (int i = 0; i < comp.length; i++)
		{
			if(comp[i])
			{
				if(i != 0)
					pw.print(" ");
				pw.print(i+1);
			}
		}
		pw.println();
		pw.flush();
		pw.close();
	}
	private static void dfs(int u)
	{
		vis[u] = vid;
		for (int v : adjR[u])
			if(vis[v] != vid)
				dfs(v);
	}
	
	private static void dfs2(int u)
	{
		vis[u] = vid;
		comp[u] = true;
		cc++;
		node = u;
		for (int v : adj[u])
			if(vis[v] != vid)
				dfs2(v);
	}
	private static void dfs1(int u)
	{
		vis[u] = vid;
		for (int v : adjR[u])
			if(vis[v] != vid)
				dfs1(v);
		
		order.add(u);
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
