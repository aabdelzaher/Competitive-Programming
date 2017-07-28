/*
 * First to check if the answer is negative 1 we check for a cycle (Using dfs)
 * We get the answer in a recursive manner. We get the result of all 4 cells that current cell can go to.
 * anspcurrentCell] = 1 + max(all cells it can go to in one step).
 */
public class JumpingBoard
{
	public static void main(String[] args)
	{
		String[] board = {"3942178",
				 "1234567",
				 "9123532"}
;
		System.out.println(maxJumps(board));
	}
	
	static int[][] grid;
	static int[][] visited;
	static int vid, n, m;
	static int best[][];
	public static int maxJumps(String[] board)
	{
		n = board.length;
		m = board[0].length();
		grid = new int[n][m];
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				if(board[i].charAt(j) == 'H')
					grid[i][j] = -1;
				else
					grid[i][j] = board[i].charAt(j)-'0';
			}
		}
		visited = new int[n][m];
		best = new int[n][m];
		vid = 1;
		if(cycle(0, 0))
			return -1;
		vid = 3;
		return best(0, 0);
	}
	private static int best(int i, int j)
	{
		if(!valid(i, j))
			return 0;
		if(visited[i][j] == vid)
			return best[i][j];
		visited[i][j] = vid;
		int[] nwx = {i, i, i+grid[i][j], i - grid[i][j]};
		int[] nwy = {j+grid[i][j], j-grid[i][j], j, j};
		int ans = 0;
		for (int k = 0; k < nwy.length; k++)
		{
			ans = Math.max(ans, best(nwx[k], nwy[k]));
		}
		return best[i][j] = (ans+1);
	}
	
	private static boolean cycle(int i, int j)
	{
		if(!valid(i, j))
			return false;
		visited[i][j] = 1;
		int[] nwx = {i, i, i+grid[i][j], i - grid[i][j]};
		int[] nwy = {j+grid[i][j], j-grid[i][j], j, j};
		boolean ans = false;
		for (int k = 0; k < nwy.length; k++)
		{
			if(valid(nwx[k], nwy[k]))
			{
				if(visited[nwx[k]][nwy[k]] == 1)
				{
					visited[i][j] = 2;
					return true;
				}
				else
					if(visited[nwx[k]][nwy[k]] == 0)
						ans = ans | cycle(nwx[k], nwy[k]);
			}
		}
		visited[i][j] = 2;
		return ans;
	}
	private static boolean valid(int i, int j)
	{
		if(i >= 0 && i < n && j >= 0 && j < m && grid[i][j] != -1)
			return true;
		return false;
	}
	
	
}
