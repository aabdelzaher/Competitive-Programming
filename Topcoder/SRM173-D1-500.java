import java.util.Arrays;

/*
 * Just do a brute force. Do the instructions from every cell that is a beach.
 * If we did not pass through a water cell while doing the instructions the the final cell compare it with current final cell, if it is better update current final cell.
 */
public class TreasureHunt
{
	public static void main(String[] args)
	{
		String[] island = 	
			{"OOO",
			 "OX."};
			String[] instructions = 	{"W 1"};
			System.out.println(Arrays.toString(findTreasure(island, instructions)));

	}
	
	static char[][] grid;
	static int n, m, r, c, tr, tc, fr, fc;
	static int d = (int)1e9;
	static char[] dir;
	static int[] step;
	public static int[] findTreasure(String[] island, String[] instructions)
	{
		fr = fc = 1000;
		n = island.length;
		m = island[0].length();
		grid = new char[n][m];
		for (int i = 0; i < grid.length; i++)
		{
			grid[i] = island[i].toCharArray();
			for (int j = 0; j < grid[i].length; j++)
				if(grid[i][j] == 'X')
				{
					tc = j;
					tr = i;
				}
		}
		dir = new char[instructions.length];
		step = new int[instructions.length];
		for (int i = 0; i < instructions.length; i++)
		{
			dir[i] = instructions[i].charAt(0);
			step[i] = instructions[i].charAt(2)-'0';
		}
		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[i].length; j++)
			{
				if(valid(i, j))
				{
					if(i == 0 && j == 2)
						System.out.print("");
					r = i;
					c = j;
					go();
				}
			}
		if(fr == 1000)
			return new int[0];
		int[] ret = {fc, fr};
		return ret;
	}
	
	private static void go()
	{
		for (int k = 0; k < dir.length; k++)
		{
			switch(dir[k])
			{
			case 'W':
				if(!left(step[k]))
					return;
				break;
			case 'S':
				if(!down(step[k]))
					return;
				break;
			case 'E':
				if(!right(step[k]))
					return;
				break;
			default:
				if(!up(step[k]))
					return;
				break;
			}
		}
		
		int difx = r-tr;
		int dify = c-tc;
		int dist = difx*difx + dify*dify;
		if(r == 1 && c == 0)
			System.out.print("");
		if(dist < d)
		{
			d = dist;
			fr = r;
			fc = c;
		}
		else
			if(dist == d)
			{
				if(r < fr)
				{
					fr = r;
					fc = c;
					return;
				}
				else
					if(r == fr && c < fc)
					{
						fr = r;
						fc = c;
						return;
					}
			}
	}

	static int[] dx = {0,0,1,-1};
	static int[] dy = {1,-1,0,0};
	private static boolean valid(int i, int j)
	{
		if(grid[i][j] == '.')
			return false;
		for (int k = 0; k < dx.length; k++)
		{
			if(sea(i+dx[k], j+dy[k]))
				return true;
		}
		return false;
	}

	static boolean left(int step)
	{
		for (int i = 0; i < step; i++)
		{
			c -= 1;
			if(sea(r, c))
				return false;
		}
		return true;
	}
	
	static boolean right(int step)
	{
		for (int i = 0; i < step; i++)
		{
			c += 1;
			if(sea(r, c))
				return false;
		}
		return true;
	}
	
	static boolean up(int step)
	{
		for (int i = 0; i < step; i++)
		{
			r -= 1;
			if(sea(r, c))
				return false;
		}
		return true;
	}
	
	static boolean down(int step)
	{
		for (int i = 0; i < step; i++)
		{
			r += 1;
			if(sea(r, c))
				return false;
		}
		return true;
	}
	
	static boolean sea(int i, int j)
	{
		return i < 0 || i >= n || j < 0 || j >= m || grid[i][j] == '.';
	}
}
