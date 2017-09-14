import java.util.Arrays;
import java.util.StringTokenizer;

public class EllysRoomAssignmentsDiv1
{
	/*
	 * We calculate the expected total sum of ratings in the room then divide it
	 * by number of contestants in the room. We use linearity of expectation to
	 * calculate expected rating of a single index in the room, then we add all
	 * these indices and divide by number of persons in the room. Be careful
	 * that if the number can not be divided evenly between rooms and
	 * (int)(n/rooms) = x, Elly's room might contain x contestants or x+1 so we
	 * consider this when dividing by total number of contestants
	 */
	public static void main(String[] args)
	{
		String[] ratings = 	
			{"3380 3413 3254 3515 2885 2946 2790 3140"};
		System.out.println(getAverage(ratings));
	}
	public static double getAverage(String[] ratings)
	{
		String in = "";
		for (int i = 0; i < ratings.length; i++)
			in += ratings[i];
		StringTokenizer st = new StringTokenizer(in);
		int[] rate = new int[st.countTokens()];
		int[] tmparr = new int[rate.length];
		for (int i = 0; i < rate.length; i++)
			tmparr[i] = Integer.parseInt(st.nextToken());
		int elya = tmparr[0];
		Arrays.sort(tmparr);
		for (int i = 0, j = tmparr.length-1; i < tmparr.length; i++, j--)
			rate[i] = tmparr[j];
		int n = rate.length;
		int rooms = (n+19)/20;
		double p = 1.0*(n%rooms)/rooms;
		double[] ans = new double[(n+rooms-1)/rooms];
		for (int i = 0; i < (n+rooms-1)/rooms; i++)
			for (int j = i*rooms; j < rate.length && j < (i+1)*rooms; j++)
			{
				if(rate[j] == elya)
				{
					ans[i] = elya;
					break;
				}
				if(n%rooms != 0 && (i+1) >=  (n+rooms-1)/rooms)
					ans[i] += 1.0*rate[j]/(n%rooms);
				else
					ans[i] += 1.0*rate[j]/rooms;
			}
		
		double ans1 = 0;
		for (int i = 0; i < ans.length; i++)
			ans1 += ans[i];
		
		int tmp = (n+rooms-1)/rooms;
		if(n%rooms == 0 || elya == ans[ans.length-1])
			return ans1/tmp;
		else
			return (1-p)*((ans1-ans[ans.length-1])/(tmp-1)) + (p)*(ans1/tmp);
	}
}
