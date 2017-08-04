import java.util.Arrays;

/*
 * We can try all possible assignments of rooks by backtracking
 * we keep state ind which represent which row we are at. We try to put a rook in every valid column mark it as invalid and move to next row
 * while moving we keep track of squares that are not special so we can calculate number of special squares once we reach base case
 * Every rook adds all columns to its left and itself as non-special cells, and also every square above it that have not been marked by another rook yet.
 */

public class WeirdRooks
{
	static boolean[] valid;
	static int bad;
	static int rooks;
	static int[] arr;
	static int n, total;
	
	public static void main(String[] args)
	{
		int[] test = {10, 10, 10, 10, 10, 10, 10, 10};
		System.out.println(describe(test));
	}
	
	public static String describe(int[] cols)
	{
		arr = cols;
		n = cols.length;
		valid = new boolean[cols[n-1]];
		Arrays.fill(valid, true);
		for (int i = 0; i < cols.length; i++)
			total += cols[i];
		rows = new int[n];
		Arrays.fill(rows, -1);
		solve(0);
		StringBuilder sb = new StringBuilder();
		int fst = 0;
		for (int i = 0; i < res.length; i++)
			for (int j = 0; j < res[i].length; j++)
			{
				
				if(res[i][j])
				{
					if(fst == 0)
						fst++;
					else
						sb.append(" ");
					sb.append(i+","+j);
				}
			}
		return sb.toString();
	}
	
	static boolean[][] res = new boolean[9][100];
	static int[] rows;
	static void solve(int ind)
	{
		if(ind == n)
		{
			res[rooks][total-bad] = true;
			return;
		}
		
		solve(ind+1);
		for (int col = 0; col < arr[ind]; col++)
		{
			if(valid[col])
			{
				valid[col] = false;
				int bef = rows[ind];
				rows[ind] = col;
				
				int add = 0;
				for (int j = 0; j < ind; j++)
					if(col < arr[j] && col > rows[j])
						add++;
				
				bad += col+1+add;
				rooks++;
				
				solve(ind+1);
				
				rooks--;
				bad = bad-col-1-add;
				rows[ind] = bef;
				valid[col] = true;
			}
		}
		
	}
}
