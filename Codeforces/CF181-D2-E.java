import java.util.Scanner;

/*
 * If the difference between the two chips in one dimension is greater than 4 then the second player can win easily.
 * To see how he can win if difference > 4 try it on 1*n grid. Just add glue to cells at x1+2 && x2 - 2.
 * If the difference on both dimensions <= 4, simulating these cases by hand lead to the full answer.
 */
public class PlayingWithSuperglue
{
	public static void main(String[] args)
	{
		
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		int x1 = sc.nextInt();
		int y1 = sc.nextInt();
		int x2 = sc.nextInt();
		int y2 = sc.nextInt();
		int dx = Math.abs(x1-x2);
		int dy = Math.abs(y1 - y2);
		if(dx <= 4 && dy <= 4)
		{
			int min = Math.min(dx, dy);
			int max = Math.max(dx, dy);
			if(min < 3)
				System.out.println("First");
			else
			{
				if(max == 3)
					System.out.println("First");
				else
					System.out.println("Second");
			}
		}
		else
			System.out.println("Second");
	}
}

