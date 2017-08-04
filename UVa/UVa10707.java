
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 * For two boards to be equivalent every connected component in first board should have
 * corresponding component in the second grid. First we extract connected components from both
 * grids into array of 2d-arrays, each 2d-array represents connected component. We loop over connected
 * components in board 1 and try to match them with board 2. A connected component matches another if they are equivalent
 * at any orientation. The code tries every possible orientation through reflection and rotation.
 */
public class UVa10707
{
	static int[][] visited;
	static int vid = 1;
	static int[][] grid, grid2;
	static int w, h;
	static ArrayList<ArrayList<Integer>> xg1, xg2, yg1, yg2;
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		Scanner sc = new Scanner();
		int tc = sc.nextInt();
		nxt: while(tc-->0)
		{
			xg1 = new ArrayList<ArrayList<Integer>>();
			xg2 = new ArrayList<ArrayList<Integer>>();
			yg1 = new ArrayList<ArrayList<Integer>>();
			yg2 = new ArrayList<ArrayList<Integer>>();
			w = sc.nextInt();
			h = sc.nextInt();
			int n = sc.nextInt();
			grid = new int[w][h];
			visited = new int[w][h];
			for (int i = 0; i < n; i++)
			{
				int x = sc.nextInt();
				int y = sc.nextInt();
				grid[x][y] = 1;
			}
			grid2 = new int[w][h];
			for (int i = 0; i < n; i++)
			{
				int x = sc.nextInt();
				int y = sc.nextInt();
				grid2[x][y] = 1;
			}
			
			for (int i = 0; i < grid.length; i++)
				for (int j = 0; j < grid[i].length; j++)
				{
					if(visited[i][j] != vid && grid[i][j] == 1)
					{
						xg1.add(new ArrayList<Integer>());
						yg1.add(new ArrayList<Integer>());
						dfs(i, j, 1);
					}
				}
			
			vid++;
			for (int i = 0; i < grid2.length; i++)
				for (int j = 0; j < grid2[i].length; j++)
				{
					if(visited[i][j] != vid && grid2[i][j] == 1)
					{
						xg2.add(new ArrayList<Integer>());
						yg2.add(new ArrayList<Integer>());
						dfs(i, j, 2);
					}
				}
			
			int cc1 = xg1.size();
			int cc2 = xg2.size();
			
			if(cc1 != cc2)
			{
				System.out.println("NO");
				continue nxt;
			}
			int[][][] fst = new int[cc1][][];
			int[][][] snd = new int[cc2][][];
			
			
			for (int i = 0; i < xg1.size(); i++)
			{
				int minx = 10000000;
				int maxX = -1;
				int miny = 10000000;
				int maxY = -1;
				for (int j = 0; j < xg1.get(i).size(); j++)
				{
					minx = Math.min(minx, xg1.get(i).get(j));
					maxX = Math.max(maxX, xg1.get(i).get(j));
					miny = Math.min(miny, yg1.get(i).get(j));
					maxY = Math.max(maxY, yg1.get(i).get(j));
				}
				fst[i] = new int[maxX-minx+1][maxY-miny+1];
				for (int j = 0; j < xg1.get(i).size(); j++)
					fst[i][xg1.get(i).get(j)-minx][yg1.get(i).get(j)-miny] = 1;
			}
			
			
			for (int i = 0; i < xg2.size(); i++)
			{
				int minx = 10000000;
				int maxX = -1;
				int miny = 10000000;
				int maxY = -1;
				for (int j = 0; j < xg2.get(i).size(); j++)
				{
					minx = Math.min(minx, xg2.get(i).get(j));
					maxX = Math.max(maxX, xg2.get(i).get(j));
					miny = Math.min(miny, yg2.get(i).get(j));
					maxY = Math.max(maxY, yg2.get(i).get(j));
				}
				snd[i] = new int[maxX-minx+1][maxY-miny+1];
				for (int j = 0; j < xg2.get(i).size(); j++)
					snd[i][xg2.get(i).get(j)-minx][yg2.get(i).get(j)-miny] = 1;
			}
			
			// Now fst contains connected components of first board as 2d-arrays
			// snd contains connected components of the second board
			
			boolean ok = true;
			
			if(fst.length != snd.length)
			{
				System.out.println("NO");
				continue nxt;
			}
			
			boolean[] matched = new boolean[snd.length];
			lp: for (int i = 0; i < fst.length; i++)
			{
				for (int j = 0; j < snd.length; j++)
				{
					if(!matched[j] && match(fst[i], snd[j]))
					{
						matched[j] = true;
						continue lp;
					}
				}
				ok = false;
				break;
			}
			
			if(ok)
				System.out.println("YES");
			else
				System.out.println("NO");
		}
	}
	
	private static boolean match(int[][] g1, int[][] g2)
	{
		for (int i = 0; i < 4; i++)
		{
			g2 = rotate90(g2);
			if(equal(g1, g2))
				return true;
		}
		g2 = reflect1(g2);
		for (int i = 0; i < 4; i++)
		{
			g2 = rotate90(g2);
			if(equal(g1, g2))
				return true;
		}
		g2 = reflect2(g2);
		for (int i = 0; i < 4; i++)
		{
			g2 = rotate90(g2);
			if(equal(g1, g2))
				return true;
		}
		g2 = reflect1(g2);
		for (int i = 0; i < 4; i++)
		{
			g2 = rotate90(g2);
			if(equal(g1, g2))
				return true;
		}
		return false;
	}

	static int[] dx = {0,0,1,-1};
	static int[] dy = {1,-1,0,0};
	
	private static void dfs(int i, int j, int g)
	{
		if(g == 1)
		{
			xg1.get(xg1.size()-1).add(i);
			yg1.get(yg1.size()-1).add(j);
		}
		else
		{
			xg2.get(xg2.size()-1).add(i);
			yg2.get(yg2.size()-1).add(j);
		}
		visited[i][j] = vid;
		for (int j2 = 0; j2 < 4; j2++)
		{
			int nx = i + dx[j2];
			int ny = j + dy[j2];
			if(g == 1)
			{
				if(valid(nx, ny) && visited[nx][ny] != vid && grid[nx][ny] == 1)
					dfs(nx, ny, g);
			}
			else
			{
				if(valid(nx, ny) && visited[nx][ny] != vid && grid2[nx][ny] == 1)
					dfs(nx, ny, g);
			}
					
		}
	}

	static boolean valid(int x, int y)
	{
		return x >= 0 && y >= 0 && x < w && y < h;
	}
	static int[][] rotate90(int[][] arr)
	{
		int n = arr.length;
		int m = arr[0].length;
		int[][] ret = new int[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				ret[i][j] = arr[n-j-1][i];
		
		return ret;
	}
	
	static int[][] reflect1(int[][] arr)
	{
		int n = arr.length;
		int m = arr[0].length;
		int[][] ret = new int[n][m];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				ret[i][j] = arr[n-1-i][j];
		return ret;
	}
	
	static int[][] reflect2(int[][] arr)
	{
		int n = arr.length;
		int m = arr[0].length;
		int[][] ret = new int[n][m];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				ret[i][j] = arr[i][m-1-j];
		return ret;
	}
	
	static boolean equal(int[][] a, int[][] b)
	{
		if(a.length != b.length)
			return false;
		if(a[0].length != b[0].length)
			return false;
		int n = a.length;
		int m = a[0].length;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
			{
				if(a[i][j] != b[i][j])
					return false;
			}
		return true;
	}
	
	static class Scanner
	{
		StringTokenizer st;BufferedReader br;
		public Scanner(){br = new BufferedReader(new InputStreamReader(System.in));}
		public String next() throws IOException
		{while (st == null || !st.hasMoreTokens())st = new StringTokenizer(br.readLine());return st.nextToken();}
		public int nextInt() throws NumberFormatException, IOException{return Integer.parseInt(next());}
	}
}
