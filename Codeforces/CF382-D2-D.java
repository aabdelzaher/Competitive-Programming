package mostafa_supervision;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * First we check if there is a cycle we print -1
 * If there is no cycles we get length of each path
 * if the length of longest path is n then we can always have solution = 2*n-1 (putting soldier on start of path and another one one step ahead)
 * if we can find two non intersecting paths of length n then answer is 2*n else answer = 2*n-1
 * When calculating length of path we can do it recursively but this may cause problems with java (stack overflow)
 * We can calculate is using queue instead. Path to all hashes is zero. We add hashes to queue with cost zero. At every step we remove cell from queue
 * if any of the adjacent cells can go to this cell then cost of neighbor cell = cost of current cell + 1
 */
public class CF382D
{
	static int n, m;
	static char[][] grid;
	static int visited[][], vid;
	static int ans[][];
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner();
		n = sc.nextInt();
		m = sc.nextInt();
		grid = new char[n][m];
		for (int i = 0; i < n; i++)
			grid[i] = sc.next().toCharArray();
		visited = new int[n][m];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
			{
				if(visited[i][j] == 0)
				{
					vid++;
					if(cycle(i, j))
					{
						System.out.println(-1);
						return;
					}
				}
			}
		vid = 3;
		
		ans = new int[n][m];
		for (int i = 0; i < n; i++)
			Arrays.fill(ans[i], -1);
//		for (int i = 0; i < n; i++)
//			for (int j = 0; j < m; j++)
//				if(ans[i][j] == -1)
//					calc(i, j);
		
		calc();
		int[] max = new int[3];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
			{
				max[0] = ans[i][j];
				Arrays.sort(max);
			}
		
		if(max[1] != max[2])
			System.out.println(max[1] + max[2]);
		else
		{
			int res = max[1] + max[2];
			if(res == 0)
			{
				System.out.println(res);
				return;
			}
			int cnt = 0;
			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++)
					if(visited[i][j] != vid && ans[i][j] == max[2])
						if(ok(i, j))
							cnt++;
			if(cnt == 1)
				System.out.println(res-1);
			else
				System.out.println(res);
		}
	}
	
	private static boolean ok(int i, int j)
	{
		if(grid[i][j] == '#')
			return true;
		visited[i][j] = vid;
		int childi = i, childj = j;
		if(grid[i][j] == 'v')
			childi = i+1;
		if(grid[i][j] == '>')
			childj = j+1;
		if(grid[i][j] == '<')
			childj = j-1;
		if(grid[i][j] == '^')
			childi = i-1;
		if(visited[childi][childj] == vid)
			return false;
		return ok(childi, childj);
	}

//	private static void calc(int i, int j)
//	{
//		if(grid[i][j] == '#')
//		{
//			ans[i][j] = 0;
//			return;
//		}
//		int childi = i, childj = j;
//		if(grid[i][j] == 'v')
//			childi = i+1;
//		if(grid[i][j] == '>')
//			childj = j+1;
//		if(grid[i][j] == '<')
//			childj = j-1;
//		if(grid[i][j] == '^')
//			childi = i-1;
//		calc(childi, childj);
//		ans[i][j] = ans[childi][childj] + 1;
//	}

	private static void calc()
	{
		Queue<Pair> q = new LinkedList<Pair>();
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				if(grid[i][j] == '#')
					q.add(new Pair(i, j, 0));
		
		while(!q.isEmpty())
		{
			Pair f = q.poll();
			int i = f.x;
			int j = f.y;
			int cost = f.cost;
			ans[i][j] = cost;
			if(valid(i-1, j) && grid[i-1][j] == 'v')
				q.add(new Pair(i-1, j, cost+1));
			if(valid(i, j-1) && grid[i][j-1] == '>')
				q.add(new Pair(i, j-1, cost+1));
			if(valid(i, j+1) && grid[i][j+1] == '<')
				q.add(new Pair(i, j+1, cost+1));
			if(valid(i+1, j) && grid[i+1][j] == '^')
				q.add(new Pair(i+1, j, cost+1));
			
		}
	}
	private static boolean cycle(int i, int j)
	{
		while(true)
		{
			if(grid[i][j] == '#')
			{
				visited[i][j] = vid;
				return false;
			}
			if(visited[i][j] == vid)
				return true;
			visited[i][j] = vid;
			int childi = i, childj = j;
			if(grid[i][j] == 'v')
				childi = i+1;
			if(grid[i][j] == '>')
				childj = j+1;
			if(grid[i][j] == '<')
				childj = j-1;
			if(grid[i][j] == '^')
				childi = i-1;
			i = childi;
			j = childj;
		}
	}

	static boolean valid(int x, int y)
	{
		if(x >= 0 && y >= 0 && x < n && y < m)
			return true;
		return false;
	}
	static class Pair
	{
		int x,y,cost;
		public Pair(int x, int y, int cost)
		{
			this.x = x;
			this.y = y;
			this.cost = cost;
		}
	}
	static class Scanner
	{
		StringTokenizer st;BufferedReader br;
		public Scanner() throws FileNotFoundException{
			br = new BufferedReader(new InputStreamReader(System.in));
//			br = new BufferedReader(new FileReader(new File("tc.out")));
			}
		public String next() throws IOException
		{
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
		public int nextInt() throws NumberFormatException, IOException{return Integer.parseInt(next());}
	}
}
