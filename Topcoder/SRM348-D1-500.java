import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

/*
 * A simpler version of this problem is when seats = 1, we are looking for largest number of segments that will not intersect.
 * A solution for this simpler problem is to sort segments according to finishing point, and then to take a segment whenever you can.
 * This is a standard problem called activity selection problem. Now this problem is very similar. The only difference is that seats can be > 1.
 * So requests can intersect, but within a certain limit. We sort according to end point (as smaller end point will leave more space for next requests).
 * When choosing a new request we check first if it leads to having more than maximum capacity, we deny it.
 */
public class RailwayTickets
{
	public static void main(String[] args)
	{

		String[] travel = 
			{ "1-2 2-5 2-8 234-236 56-878 6-34",
				"234-776 3242-8000 999-1000 3-14",
				"121-122 435-3455 123-987 77-999" };
		int seats = 1000;
		
		System.out.println(minRejects(travel, seats));
	}

	static int s;

	public static int minRejects(String[] travel, int seats)
	{
		Comparator<Point> cmp = new Comparator<Point>()
		{
			public int compare(Point o1, Point o2)
			{
				return o1.y - o2.y;
			}
		};

		s = seats;

		ArrayList<Point> req = new ArrayList<Point>();
		for (int i = 0; i < travel.length; i++)
		{
			StringTokenizer st = new StringTokenizer(travel[i]);
			while (st.hasMoreTokens())
			{
				String order = st.nextToken();
				String[] ab = order.split("-");
				Point p = new Point(Integer.parseInt(ab[0]),
						Integer.parseInt(ab[1]));
				req.add(p);
			}
		}

		Collections.sort(req, cmp);
		int cnt = 0;
		ArrayList<Point> res = new ArrayList<Point>();
		for (int i = 0; i < req.size(); i++)
		{
			Point p = req.get(i);
			res.add(p);
			if (!check(res))
			{
				res.remove(res.size() - 1);
				cnt++;
			}
		}
		return cnt;
	}

	public static boolean check(ArrayList<Point> arr)
	{
		int[] cum = new int[10002];
		for (Point range : arr)
		{
			int st = range.x;
			int en = range.y;
			cum[st]++;
			cum[en]--;
		}
		for (int i = 1; i < cum.length; i++)
			cum[i] += cum[i - 1];

		for (int i = 0; i < cum.length; i++)
			if (cum[i] > s)
				return false;
		return true;
	}
}
