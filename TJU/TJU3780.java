import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*
 * finding connected components
 */
public class TJU3780
{
	static int n, m;
	static int[][] grid;
	static int[] dx = {0, 0, 1, 1, 1, -1, -1, -1};
	static int[] dy = {1, -1, 1, -1, 0, 1, -1, 0};
	static int[][] visited;
	static int vid;
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		Scanner sc = new Scanner();
		PrintWriter pw = new PrintWriter(System.out);
		while(true)
		{
			vid++;
			m = sc.nextInt();
			n = sc.nextInt();
			if(n == 0 && m == 0)
				break;
			grid  = new int[n][m];
			visited = new int[n][m];
			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++)
					grid[i][j] = sc.nextInt();
			int cnt = 0;
			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++)
					if(visited[i][j] != vid && grid[i][j] == 1)
					{
						dfs(i, j);
						cnt++;
					}
			pw.println(cnt);
		}
		pw.flush();
		pw.close();
	}
	
	private static void dfs(int i, int j)
	{
		visited[i][j] = vid;
		for (int k = 0; k < dx.length; k++)
		{
			int nwX = i + dx[k];
			int nwY = j + dy[k];
			if(valid(nwX, nwY) && visited[nwX][nwY] != vid)
				dfs(nwX, nwY);
		}
	}

	static boolean valid(int i, int j)
	{
		if(i >= 0 && i < n && j >= 0 && j < m && grid[i][j] == 1)
			return true;
		return false;
	}
	
	static class Scanner
	{
		StringTokenizer st;BufferedReader br;
		public Scanner(){
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
