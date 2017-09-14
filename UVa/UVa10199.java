import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class UVa10199
{
	/*
	 * The problem simply asks for articulation points
	 */
	static ArrayList<Integer>[] adjList;
	static int[] dfs_low, dfs_num, parent;
	static int V, counter, root, rootChildren;
	static int p;
	static boolean[] artPoints;
	static HashMap<String, Integer> toNum;
	static int num;
	static String[] toString;
	public static void main(String[] args) throws IOException
	{
		toNum = new HashMap<String, Integer>();
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int tc = 1;
		while(true)
		{
			V = sc.nextInt();
			p = 0;
			if(V == 0)
				break;
			toString = new String[V];
			if(tc > 1)
				pw.println();
			toNum.clear();
			num = 0;
			for (int i = 0; i < V; i++)
			{
				String city = sc.next();
				toString[num] = city;
				toNum.put(city, num++);
			}
			adjList = new ArrayList[V];
			for (int i = 0; i < V; i++)
				adjList[i] = new ArrayList<Integer>();
			
			int m = sc.nextInt();
			for (int i = 0; i < m; i++)
			{
				int f = toNum.get(sc.next());
				int t = toNum.get(sc.next());
				adjList[f].add(t);
				adjList[t].add(f);
			}
			
			dfs_low = new int[V];
			dfs_num = new int[V];
			parent = new int[V];
			artPoints = new boolean[V];
			
			findArtPointsAndBridges();
			
			for (int i = 0; i < artPoints.length; i++)
				if(artPoints[i])
					p++;
			
			pw.printf("City map #%d: %d camera(s) found\n",tc++, p);
			String[] ans = new String[p];
			int ind = 0;
			for (int i = 0; i < V; i++)
			{
				if(artPoints[i])
					ans[ind++] = toString[i];
			}
			
			Arrays.sort(ans);
			for (String s : ans)
			{
				pw.println(s);
			}
		}
		pw.flush();
		pw.close();
	}
	
	static void findArtPointsAndBridges()
	{
		for(int i = 0; i < V; ++i)
			if(dfs_num[i] == 0)
			{
				root = i;
				rootChildren = 0;
				dfs(i);
				if(rootChildren <= 1)
					artPoints[i] = false;
			}
	}
	
	static void dfs(int u)
	{
		dfs_num[u] = dfs_low[u] = ++counter;
		for(int v: adjList[u])
			if(dfs_num[v] == 0)
			{
				parent[v] = u;
				if(u == root)
					++rootChildren;
				dfs(v);
				if(dfs_low[v] >= dfs_num[u])
					artPoints[u] = true;
				dfs_low[u] = Math.min(dfs_low[v], dfs_low[u]);
			}
			else
				if(parent[u] != v)
					dfs_low[u] = Math.min(dfs_low[u], dfs_num[v]);
	}
	
	static class Scanner
	{
		StringTokenizer st; BufferedReader br;
		public Scanner(InputStream s){br = new BufferedReader(new InputStreamReader(s));}
		public String next() throws IOException{while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine()); return st.nextToken();}
		public int nextInt() throws IOException{return Integer.parseInt(next());}
		public long nextLong() throws IOException{return Long.parseLong(next());}
		public String nextLine() throws IOException{return br.readLine();}
		public double nextDouble() throws IOException{return Double.parseDouble(next());}
		public boolean ready() throws IOException{return br.ready();}
	}
}
