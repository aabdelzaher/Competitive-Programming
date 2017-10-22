import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVa10111
{
	/*
	 * We can solve the problem using dp, where state is (msk, turn). Turn is
	 * player who is currently playing. Msk is a mask in base 3 that determines
	 * current state of the game.
	 */
	static char[][] grid;
	static Pair dp[][];
	static char currGrid[][];
	static int[] pw3;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		grid = new char[4][4];
		currGrid = new char[4][4];
		pw3 = new int[15];
		pw3[0] = 1;
		for (int i = 1; i < pw3.length; i++)
			pw3[i] = pw3[i-1]*3;
		while(true)
		{
			boolean end = sc.next().charAt(0) == '$';
			if(end)
				break;
			for (int i = 0; i < 4; i++)
				grid[i] = sc.next().toCharArray();
			int cntx = 0; int cnto = 0;
			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 4; j++)
				{
					if(grid[i][j] == 'x')
						cntx++;
					if(grid[i][j] == 'o')
						cnto++;
				}
			
			int player = 0;
			if(cntx > cnto)
				player = 1;
			dp = new Pair[pw3[12]+10][2];
			Pair res = solve(0, player);
			if(res.x == 5 || res.x == -1)
				pw.println("#####");
			else
				pw.printf("(%d,%d)\n", res.x, res.y);
			
		}
		pw.flush();
		pw.close();
	}
	// x, o
	static int[] pl = {1,2};
	static Pair solve(int state, int turn)
	{
		if(dp[state][turn] != null)
			return dp[state][turn];
		char player = 'x';
		if(turn == 1)
			player = 'o';
		getState(state);
		char winner = win();
		if(winner != '.' && winner != player)
			return dp[state][turn] = new Pair(-1,-1);
		int ind = 0;
		boolean play = false;
		boolean draw = false;
		int tmp = state;
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
			{
				if(grid[i][j] != '.')
					continue;
				int curr = tmp % 3;
				tmp /= 3;
				if(curr == 0)
				{
					int newState = state + pl[turn] * pw3[ind];
					play = true;
					Pair res = solve(newState, 1^turn);
					if(res.x == -1)
						return dp[state][turn] = new Pair(i,j);
					if(res.x == 5)
						draw = true;
				}
				ind++;
			}
		
		if(!play || draw)
			return dp[state][turn] = new Pair(5,5);
		return dp[state][turn] = new Pair(-1,-1);
	}
	// 0 -> '.', 1 -> x, 2 -> o
	
	static void getState(int state)
	{
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
			{
				if(grid[i][j] != '.')
					currGrid[i][j] = grid[i][j];
				else
				{
					int curr = state %3;
					state /= 3;
					if(curr == 0)
						currGrid[i][j] = '.';
					if(curr == 1)
						currGrid[i][j] = 'x';
					if(curr == 2)
						currGrid[i][j] = 'o';
				}
			}
	}
	
	static char win()
	{
		outer: for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if(currGrid[i][j] != currGrid[i][0])
					continue outer;
			}
			if(currGrid[i][0] != '.')
				return currGrid[i][0];
		}
	
		outer: for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if(currGrid[j][i] != currGrid[0][i])
					continue outer;
			}
			if(currGrid[0][i] != '.')
				return currGrid[0][i];
		}
		
		boolean diag = true;
		for (int i = 0; i < 4; i++)
		{
			if(currGrid[i][i] != currGrid[0][0])
			{
				diag = false;
				break;
			}
		}
		
		if(diag && currGrid[0][0] != '.')
			return currGrid[0][0];
		
		diag = true;
		for (int i = 0; i < 4; i++)
		{	
			if(currGrid[i][3-i] != currGrid[0][3])
			{
				diag = false;
				break;
			}
		}
		if(diag && currGrid[0][3] != '.')
			return currGrid[0][3];
		return '.';
	}
	
	static class Pair
	{
		int x; int y;
		public Pair(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
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
