import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * Simple convex hull problem, then we calculate the perimiter as described in problem.
 */
public class UVa218
{
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		Scanner sc = new Scanner();
		int cs = 1;
		PrintWriter pw = new PrintWriter(System.out);
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
				break;
			if(cs != 1)
				pw.println();
			Point[] p = new Point[n];
			for (int i = 0; i < p.length; i++)
				p[i] = new Point(sc.nextDouble(), sc.nextDouble());
			
			Point[] ch = convexHull(p);
			pw.printf("Region #%d:\n", cs++);
			for (int i = ch.length-1; i >= 0; i--)
			{
				if(i != ch.length-1)
					pw.print('-');
				pw.printf("(%.1f,%.1f)", ch[i].x, ch[i].y);
			}
			pw.println();
			pw.printf("Perimeter length = %.2f\n", perimeter(ch));
		}
		pw.flush();
		pw.close();
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
		return Arrays.copyOf(ans, size);			
	}
	
	static double perimeter(Point[] g)
	{
		double sum = 0.0;
		for(int i = 0; i < g.length - 1; ++i)
			sum += g[i].dist(g[i+1]);
		return sum;
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
		public double nextDouble() throws NumberFormatException, IOException{return Double.parseDouble(next());}
	}
}
