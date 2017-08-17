import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * The line we need is one of the sides of convex hull of given points.
 * Get convex hull, and loop on every two adjacent vertices.
 */
public class UVa11168
{
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		Scanner sc = new Scanner();
		int tc = sc.nextInt();
		int cs = 1;
		while(tc-->0)
		{
			int n = sc.nextInt();
			Point[] arr = new Point[n];
			for (int i = 0; i < arr.length; i++)
				arr[i] = new Point(sc.nextInt(), sc.nextInt());
			
			Point[] ch = convexHull(arr);
			double min = 1e9;
			for (int i = 0; i < ch.length-1; i++)
			{
				Point cur = ch[i];
				Point after = ch[i+1];
				Line l = new Line(cur, after);
				double avg = 0;
				for (int k = 0; k < arr.length; k++)
				{
					Point cl = l.closestPoint(arr[k]);
					avg += cl.dist(arr[k]);
				}
				avg /= arr.length;
				min = Math.min(min, avg);
			}
			if(n == 1)
				min = 0;
			System.out.printf("Case #%d: %.3f\n", cs++, min);
		}
	}

	static Point[] convexHull(Point[] points)	//all points are unique, remove duplicates, edit ccw to accept collinear points
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
		return Arrays.copyOf(ans, size);			
	}
	
	static class Point implements Comparable<Point>{

		static final double EPS = 1e-9;

		double x, y;

		Point(double a, double b) { x = a; y = b;}  
		
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
		
		static double distToLine(Point p, Point a, Point b) //distance between point p and a line defined by points a, b (a != b)
		{
			if(a.compareTo(b) == 0) p.dist(a);
			// formula: c = a + u * ab
			Vector ap = new Vector(a, p), ab = new Vector(a, b);
			double u = ap.dot(ab) / ab.norm2();
			Point c = a.translate(ab.scale(u)); 
			return p.dist(c);
		}
		
		Point translate(Vector v) { return new Point(x + v.x , y + v.y); }
		
		public double dist(Point p) { return Math.sqrt(sq(x - p.x) + sq(y - p.y)); }
		
		static double sq(double x) { return x * x; }
		
	}
	
	static class Vector {

		double x, y; 

		Vector(double a, double b) { x = a; y = b; }

		Vector(Point a, Point b) { this(b.x - a.x, b.y - a.y); }

		double cross(Vector v) { return x * v.y - y * v.x; }
		
		Vector scale(double s) { return new Vector(x * s, y * s); }              //s is a non-negative value

		double dot(Vector v) { return (x * v.x + y * v.y); }

		double norm2() { return x * x + y * y; }

	}
	
	static class Scanner
	{
		StringTokenizer st;BufferedReader br;
		public Scanner(){
			br = new BufferedReader(new InputStreamReader(System.in));
			}
		public String next() throws IOException
		{
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
		public int nextInt() throws NumberFormatException, IOException{return Integer.parseInt(next());}
	}
	
	static class Line {

		static final double INF = 1e9, EPS = 1e-9;
		
		double a, b, c;
		
		Line(Point p, Point q)
		{
			if(Math.abs(p.x - q.x) < EPS) {	a = 1; b = 0; c = -p.x;	}
			else
			{
				a = (p.y - q.y) / (q.x - p.x);
				b = 1.0;
				c = -(a * p.x + p.y);
			}
						
		}
		
		Line(Point p, double m) { a = -m; b = 1; c =  -(a * p.x + p.y); } 
		
		boolean parallel(Line l) { return Math.abs(a - l.a) < EPS && Math.abs(b - l.b) < EPS; }
		
		boolean same(Line l) { return parallel(l) && Math.abs(c - l.c) < EPS; }
		
		Point intersect(Line l)
		{
			if(parallel(l))
				return null;
			double x = (b * l.c - c * l.b) / (a * l.b - b * l.a);
			double y;
			if(Math.abs(b) < EPS)
				 y = -l.a * x - l.c;
			else
				y = -a * x - c;
			
			return new Point(x, y);
		}
		
		Point closestPoint(Point p)
		{
			if(Math.abs(b) < EPS) return new Point(-c, p.y);
			if(Math.abs(a) < EPS) return new Point(p.x, -c);
			return intersect(new Line(p, 1 / a));
		}
	}
}
