import java.util.Arrays;

public class BouncingBalls
{
	/*
	 * Notice that when two balls collide each one of them reverse its direction
	 * without any change in speed. This is the same as if the penetrate each
	 * other and continue in its movement forever. So each ball can be
	 * considered as it is moving alone. So we just simulate the problem. We
	 * generate all possible combinations for moving the balls. For each
	 * configuration we count number of collisions. Then we divide total number
	 * of collisions by number of possibilities which is 2^n;
	 */

	static char[] dir;
	static int n;
	static int[] pos;
	static int T;

	public static double expectedBounces(int[] x, int T)
	{
		pos = x;
		BouncingBalls.T = T;
		Arrays.sort(pos);
		n = x.length;
		dir = new char[n];
		solve(0);
		return 1.0 * cnt / (1 << n);
	}

	static long cnt;

	private static void solve(int ind)
	{
		if (ind == n)
		{
			countColl();
			return;
		}
		dir[ind] = 'L';
		solve(ind + 1);
		dir[ind] = 'R';
		solve(ind + 1);
	}

	static void countColl()
	{
		for (int i = 0; i < n; i++)
			if (dir[i] == 'L')
				for (int j = 0; j < i; j++)
					if (dir[j] == 'R')
						if (pos[i] - pos[j] <= T * 2)
							cnt++;
	}
}
