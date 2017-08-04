import java.util.Scanner;

public class J
{
	/*
	 * The formula we want to maximize is i*(n-i)*x
	 * by differentiating or writing a brute force, we know that answer is minimum between m and n/2
	 */
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int tc = sc.nextInt();
		while(tc-->0)
		{
			int n = sc.nextInt();
			int m = sc.nextInt();
			int x = sc.nextInt();
			
			int res = n/2;
			if(res > m)
				res = m;
			System.out.println(res + " " + 1l*res*(n-res)*x);
		}
		
	}
}
