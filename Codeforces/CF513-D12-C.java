import java.util.Scanner;

public class SecondPriceAuction
{
	/*
	 * The method is a brute force. For every pair (b1, b2) from n bidders, we
	 * assume that the highest bid is b1, and second highest is b2. So all other
	 * bids are less than b2 we add to the answer b2*p, where p is probability
	 * of having b1 highest bid and b2 second highest.
	 */
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] l = new int[n];
		int[] r = new int[n];
		for (int i = 0; i < n; i++)
		{
			l[i] = sc.nextInt();
			r[i] = sc.nextInt();
		}
		
		double ans = 0;
		
		for (int msk = 3; msk < 1<<n; msk++)
		{
			if(Integer.bitCount(msk) == 2)
			{
				int ind1 = -1, ind2 = -1;
				for (int i = 0; i < n; i++)
				{
					if(((1 << i)& msk) != 0)
					{
						if(ind1 == -1)
							ind1 = i;
						else
						{
							ind2 = i;
							break;
						}
					}
				}
				for (int i = l[ind1]; i <= r[ind1]; i++)
				{
					double p = 1;
					for (int j = 0; j < n; j++)
					{
						if(j == ind1)
							continue;
						if(j == ind2)
						{
							p = p* Math.min(1, (1.0*Math.max(0, r[j]-i+1)/(r[j]-l[j]+1)));
							continue;
						}
						if(j < ind1)
							p = p* Math.min(1, (1.0*Math.max(0, i-l[j]+1)/(r[j]-l[j]+1)));
						else
							p = p* Math.min(1, (1.0*Math.max(0, i-l[j])/(r[j]-l[j]+1)));
					}
					ans += (i*p)/(1.0*(r[ind1] - l[ind1] + 1));
				}
				
				for (int i = l[ind2]; i <= r[ind2]; i++)
				{
					double p = 1;
					for (int j = 0; j < n; j++)
					{
						if(j == ind2)
							continue;
						if(j == ind1)
						{
							p = p* Math.min(1, (1.0*Math.max(0, r[j]-i)/(r[j]-l[j]+1)));
							continue;
						}
						if(j < ind2)
							p = p* Math.min(1, (1.0*Math.max(0, i-l[j]+1)/(r[j]-l[j]+1)));
						else
							p = p* Math.min(1, (1.0*Math.max(0, i-l[j])/(r[j]-l[j]+1)));
					}
					ans += (i*p)/(1.0*(r[ind2] - l[ind2] + 1));
				}
				
			}
		}
		
		System.out.println(ans);
	}
}
