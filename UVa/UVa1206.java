import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * Just apply convex hull
 */

public class UVa1206
{
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			String s = br.readLine();
			if(s == null)
				break;
			
			if(s.isEmpty())
				continue;
			
			ArrayList<Point> al = new ArrayList<Point>();
			StringTokenizer st = new StringTokenizer(s);
			ArrayList<String> out = new ArrayList<String>();
			int ind = 0;
			while(st.hasMoreTokens())
			{
				String p = st.nextToken();
				out.add(p);
				String[] point = p.substring(1, p.length()-1).split(",");
				double x = Double.parseDouble(point[0].trim());
				double y = Double.parseDouble(point[1].trim());
				al.add(new Point(x, y, ind++));
			}
			
			Point[] arr = new Point[al.size()];
			for (int i = 0; i < arr.length; i++)
				arr[i] = al.get(i);
			Point[] ch = Polygon.convexHull(arr).g;
			for (int i = 0; i < ch.length; i++)
			{
				if(i != 0)
					System.out.print(" ");
				System.out.print(out.get(ch[i].ind));
			}
			System.out.println();
		}
	}
	
	static class Polygon { 
		static final double EPS = 1e-9;
		
		Point[] g; 
		
		Polygon(Point[] o) { g = o; }


		static Polygon convexHull(Point[] points)
		{
			int n = points.length;
			Arrays.sort(points);
			Point[] ans = new Point[n<<1];
			int size = 0, start = 0;

			for(int i = 0; i < n; i++)
			{
				Point p = points[i];
				while(size - start >= 2 && !Point.ccw(ans[size-2], ans[size-1], p))	--size;
				ans[size++] = p;
			}
			start = --size;

			for(int i = n-1 ; i >= 0 ; i--)
			{
				Point p = points[i];
				while(size - start >= 2 && !Point.ccw(ans[size-2], ans[size-1], p))	--size;
				ans[size++] = p; 
			}
			//			if(size < 0) size = 0			for empty set of points
			return new Polygon(Arrays.copyOf(ans, size));			
		}
	}
	
	static class Point implements Comparable<Point>{

		static final double EPS = 1e-9;

		double x, y;
		int ind;

		Point(double a, double b, int ind) { x = a; y = b; this.ind = ind;}  
		
		public int compareTo(Point p)
		{
			if(Math.abs(x - p.x) > EPS) return x > p.x ? 1 : -1;
			if(Math.abs(y - p.y) > EPS) return y > p.y ? 1 : -1;
			return 0;
		}
		
		static boolean ccw(Point p, Point q, Point r)
		{
			return new Vector(p, q).cross(new Vector(p, r)) > 0;
		}
		
	}
	
	static class Vector {

		double x, y; 

		Vector(double a, double b) { x = a; y = b; }

		Vector(Point a, Point b) { this(b.x - a.x, b.y - a.y); }

		double cross(Vector v) { return x * v.y - y * v.x; }
	}

}
