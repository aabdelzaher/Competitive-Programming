import java.util.HashSet;

/*
 * We try all possibilities. The s is two sets one for each player.
 * The player who is next to play tries all possible choices then let the other play. If the other wins the player loses and vice versa 
 */
public class Fifteen
{
	public static void main(String[] args)
	{
		int[] moves = {7, 5, 9, 6, 8, 1, 2};
		System.out.println(outcome(moves));
	}
	
	static HashSet<Integer>[] s;
	public static String outcome(int[] moves)
	{
		s = new HashSet[2];
		for (int i = 0; i < s.length; i++)
			s[i] = new HashSet<Integer>();
		int ind = 0;
		for (int i = 0; i < moves.length; i++)
		{
			s[ind].add(moves[i]);
			ind = 1-ind;
		}
		int ans = solve(1);
		if(solve(1) != -1)
			return "WIN " + ans;
		else
			return "LOSE";
	}
	private static int solve(int i)
	{
		int bef = 1-i;
		for(int n1: s[bef])
			for(int n2: s[bef])
				for(int n3: s[bef])
				{
					if(n1 != n2 && n1 != n3 && n2 != n3)
						if(n1 + n2 + n3 == 15)
							return -1;
				}
		
		if(s[i].size() + s[1-i].size() == 9)
			return -1;
		for (int j = 1; j < 10; j++)
		{
			if(!s[0].contains(j) && !s[1].contains(j))
			{
				s[i].add(j);
				
				int other = solve(1-i);
				
				s[i].remove(j);
				if(other == -1)
					return j;
			}
		}
		return -1;
	}
}
