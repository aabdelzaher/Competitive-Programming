import java.util.Scanner;

/*
 * If three points are on the same line (vertical or horizontal), we can join them with only one line.
 * If the two points are on the same vertical or horizontal line, if the third point is not between them, we can always construct a solution with two lines.
 * Otherwise there is always a solution using 3 lines.
 */
public class Polyline
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int x1 = sc.nextInt();
		int y1 = sc.nextInt();
		int x2 = sc.nextInt();
		int y2 = sc.nextInt();
		int x3 = sc.nextInt();
		int y3 = sc.nextInt();
		
		if(x1 == x2 && x2 == x3)
		{
			System.out.println(1);
			return;
		}
		
		if(y1 == y2 && y2 == y3)
		{
			System.out.println(1);
			return;
		}
		
		if(x1 == x2 && !(y3 > Math.min(y1, y2) && y3 < Math.max(y1, y2)))
		{
			System.out.println(2);
			return;
		}
		
		if(x1 == x3 && !(y2 > Math.min(y1, y3) && y2 < Math.max(y1, y3)))
		{
			System.out.println(2);
			return;	
		}
		
		if(x2 == x3 && !(y1 > Math.min(y3, y2) && y1 < Math.max(y3, y2)))
		{
			System.out.println(2);
			return;	
		}
		
		if(y1 == y2 && !(x3 > Math.min(x1, x2) && x3 < Math.max(x1, x2)))
		{
			System.out.println(2);
			return;	
		}
		if(y1 == y3 && !(x2 > Math.min(x1, x3) && x2 < Math.max(x1, x3)))
		{
			System.out.println(2);
			return;	
		}
		if(y2 == y3 && !(x1 > Math.min(x2, x3) && x1 < Math.max(x2, x3)))
		{
			System.out.println(2);
			return;	
		}
		
		System.out.println(3);
	}
}
