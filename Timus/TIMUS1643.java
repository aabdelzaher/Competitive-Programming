import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
 * Do a BFS from ! & BFS from $
 * loop over all neighbours of '*' and minimise cost from '!' + cost from '$'
 * Be careful when doing BFS not to pass through '#' or '*'
 */
public class TIMUS1643
{
	static int n, m;
	static int dx[] = { 1, 1, 1, -1, -1, -1, 0, 0 };
	static int dy[] = { 1, 0, -1, 1, 0, -1, 1, -1 };
	static int[] cost1;
	static int[] cost2;
	static char[][] grid;
	static int[] visitedChar = new int[26];
	static int vid = 1;
	static final int INF = (int)1e9;
	static ArrayList<Integer>[] teleports;

	public static void main(String[] args) throws NumberFormatException,
			IOException
	{
		Scanner sc = new Scanner();
		n = sc.nextInt();
		m = sc.nextInt();
		cost1 = new int[100 * 100];
		cost2 = new int[100 * 100];
		Arrays.fill(cost1, 100000000);
		Arrays.fill(cost2, 100000000);
		grid = new char[n][m];
		for (int i = 0; i < n; i++)
			grid[i] = sc.next().toCharArray();
		teleports = new ArrayList[26];
		for (int i = 0; i < teleports.length; i++)
			teleports[i] = new ArrayList<Integer>();
		int n1 = 0;
		int n2 = 0;
		int fortress = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				if (valid(i, j))
				{
					if (grid[i][j] == '!')
						n1 = getNode(i, j);
					if (grid[i][j] == '*')
						fortress = getNode(i, j);
					if (grid[i][j] == '$')
						n2 = getNode(i, j);
					if (grid[i][j] >= 'A' && grid[i][j] <= 'Z')
						teleports[grid[i][j] - 'A'].add(getNode(i, j));
				}

		bfs1(n1);
		vid++;
		bfs2(n2);

		int min = (int) 1e8;
		for (int i = 0; i < dx.length; i++)
		{
			int nwX = getX(fortress) + dx[i];
			int nwY = getY(fortress) + dy[i];
			if (valid(nwX, nwY))
			{
				int node = getNode(nwX, nwY);
				min = Math.min(min, Math.max(cost1[node], cost2[node]));
			}
		}
		if (min > 1e6)
			System.out.println("Impossible");
		else
			System.out.println(min + 1);
	}

	private static void bfs1(int node)
	{
		Deque<Integer> q = new LinkedList<Integer>();
		cost1[node] = 0;
		q.addFirst(node);
		while (!q.isEmpty())
		{
			int cur = q.pollFirst();
			for (int j2 = 0; j2 < dx.length; j2++)
			{
				int nwX = getX(cur) + dx[j2];
				int nwY = getY(cur) + dy[j2];
				if (!valid(nwX, nwY))
					continue;
				int child = getNode(nwX, nwY);
				int dist = 1;
				if (grid[getX(child)][getY(child)] == grid[getX(cur)][getY(cur)]
						&& grid[getX(child)][getY(child)] >= 'A'
						&& grid[getX(child)][getY(child)] <= 'Z')
					continue;
				if (valid(getX(child), getY(child))
						&& grid[getX(child)][getY(child)] != '*'
						&& cost1[child] > cost1[cur] + dist)
				{
					if (grid[getX(child)][getY(child)] == grid[getX(cur)][getY(cur)]
							&& grid[getX(child)][getY(child)] >= 'A'
							&& grid[getX(child)][getY(child)] <= 'Z')
					{
						q.addFirst(child);
						cost1[child] = cost1[cur];
					} else
					{
						q.addLast(child);
						cost1[child] = cost1[cur] + 1;
					}
				}
			}
			char c = grid[getX(cur)][getY(cur)];
			if (c >= 'A' && c <= 'Z')
				if(visitedChar[c-'A'] != vid)
				for (int child : teleports[c - 'A'])
				{
					if (child == cur)
						continue;
					int dist = 0;
					if(valid(getX(child), getY(child))
							&& grid[getX(child)][getY(child)] != '*'
							&& cost1[child] <= cost1[cur] + dist)
					{
						visitedChar[c-'A'] = vid;
					}
					if (valid(getX(child), getY(child))
							&& grid[getX(child)][getY(child)] != '*'
							&& cost1[child] > cost1[cur] + dist)
					{
						if (grid[getX(child)][getY(child)] == grid[getX(cur)][getY(cur)]
								&& grid[getX(child)][getY(child)] >= 'A'
								&& grid[getX(child)][getY(child)] <= 'Z')
						{
							q.addFirst(child);
							cost1[child] = cost1[cur];
						} else
						{
							q.addLast(child);
							cost1[child] = cost1[cur] + 1;
						}
					}
				}

		}
	}

	private static void bfs2(int node)
	{
		Deque<Integer> q = new LinkedList<Integer>();
		cost2[node] = 0;
		q.add(node);
		while (!q.isEmpty())
		{
			int cur = q.pollFirst();
			for (int j2 = 0; j2 < dx.length; j2++)
			{
				int nwX = getX(cur) + dx[j2];
				int nwY = getY(cur) + dy[j2];
				if (!valid(nwX, nwY))
					continue;
				int child = getNode(nwX, nwY);
				int dist = 1;
				if (grid[getX(child)][getY(child)] == grid[getX(cur)][getY(cur)]
						&& grid[getX(child)][getY(child)] >= 'A'
						&& grid[getX(child)][getY(child)] <= 'Z')
					dist = 0;
				if (valid(getX(child), getY(child))
						&& grid[getX(child)][getY(child)] != '*'
						&& cost2[child] > cost2[cur] + dist)
				{
					if (grid[getX(child)][getY(child)] == grid[getX(cur)][getY(cur)]
							&& grid[getX(child)][getY(child)] >= 'A'
							&& grid[getX(child)][getY(child)] <= 'Z')
					{
						q.addFirst(child);
						cost2[child] = cost2[cur];
					} else
					{
						q.addLast(child);
						cost2[child] = cost2[cur] + 1;
					}
				}
			}

			char c = grid[getX(cur)][getY(cur)];
			if (c >= 'A' && c <= 'Z')
				if(visitedChar[c-'A'] != vid)
				for (int child : teleports[c - 'A'])
				{
					if (child == cur)
						continue;
					int dist = 1;
					if (grid[getX(child)][getY(child)] == grid[getX(cur)][getY(cur)]
							&& grid[getX(child)][getY(child)] >= 'A'
							&& grid[getX(child)][getY(child)] <= 'Z')
						dist = 0;
					if(valid(getX(child), getY(child))
							&& grid[getX(child)][getY(child)] != '*'
							&& cost2[child] <= cost2[cur] + dist)
					{
						visitedChar[c-'A'] = vid;
					}
					if (valid(getX(child), getY(child))
							&& grid[getX(child)][getY(child)] != '*'
							&& cost2[child] > cost2[cur] + dist)
					{
						if (grid[getX(child)][getY(child)] == grid[getX(cur)][getY(cur)]
								&& grid[getX(child)][getY(child)] >= 'A'
								&& grid[getX(child)][getY(child)] <= 'Z')
						{
							q.addFirst(child);
							cost2[child] = cost2[cur];
						} else
						{
							q.addLast(child);
							cost2[child] = cost2[cur] + 1;
						}
					}
				}
		}
	}
	
	

	
	static int getNode(int x, int y)
	{
		return x * 100 + y;
	}

	static int getX(int node)
	{
		return node / 100;
	}

	static int getY(int node)
	{
		return node % 100;
	}

	static boolean valid(int i, int j)
	{
		if (i >= n || j >= m || i < 0 || j < 0 || grid[i][j] == '#')
			return false;
		return true;
	}

	static class Scanner
	{
		StringTokenizer st;BufferedReader br;
		public Scanner(){br = new BufferedReader(new InputStreamReader(System.in));}
		public String next() throws IOException
		{
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
		public int nextInt() throws NumberFormatException, IOException{return Integer.parseInt(next());}
	}
}
