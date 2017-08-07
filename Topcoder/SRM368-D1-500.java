
/*
 * We use DSU. If two polylines intersect we union their sets. Then the answer is the number of sets.
 */
public class PolylineUnion
{
	
	public static void main(String[] args)
	{
		String[] polylines = 	
			{"0,0-10,0-0,0 20,0-8,0-20,0"};
		System.out.println(countComponents(polylines));
	}
	
	static Point[][] lines;
	public static int countComponents(String[] polylines)
	{
		String s = "";
		for(String line: polylines)
			s += line;
		polylines = s.split(" ");
		int n = polylines.length;
		UnionFind set = new UnionFind(n);
		
		lines = new Point[n][];
		for (int i = 0; i < polylines.length; i++)
			construct(polylines[i], i);
		
		for (int i = 0; i < lines.length; i++)
			for (int j = i+1; j < lines.length; j++)
			{
				if(cross(lines[i], lines[j]))
					set.unionSet(i, j);
			}
		
		return set.numSets;
	}
	
	private static boolean cross(Point[] p1, Point[] p2)
	{
		if(p1.length == 1 && p2.length == 1)
		{
			if(Math.abs(p1[0].x - p2[0].x) < 1e-9 && Math.abs(p2[0].y - p1[0].y) < 1e-9)
				return true;
			return false;
		}
		
		if(p1.length < 2)
		{
			Point p = p1[0];
			for (int i = 0; i < p2.length-1; i++)
			{
				if(p.onSegment(p2[i], p2[i+1]))
					return true;
			}
			
			return false;
		}
		
		if(p2.length < 2)
		{
			Point p = p2[0];
			for (int i = 0; i < p1.length-1; i++)
			{
				if(p.onSegment(p1[i], p1[i+1]))
					return true;
			}
			
			return false;
		}
		
		if(p1.length > 1 && p2.length > 1)
		{
			for (int i = 0; i < p1.length-1; i++)
				for (int j = 0; j < p2.length-1; j++)
				{
					if(p1[i].onSegment(p2[j], p2[j+1]))
						return true;
					if(p1[i+1].onSegment(p2[j], p2[j+1]))
						return true;
					if(p2[j].onSegment(p1[i], p1[i+1]))
						return true;
					if(p2[j+1].onSegment(p1[i], p1[i+1]))
						return true;
					Line l1 = new Line(p1[i], p1[i+1]);
					Line l2 = new Line(p2[j], p2[j+1]);
					Point p = l1.intersect(l2);
					if(p!=null && p.between(p1[i], p1[i+1]) && p.between(p2[j], p2[j+1]))
						return true;
				}
			return false;
		}
		return false;
	}

	private static void construct(String s, int i)
	{
		String[] points = s.split("-");
		lines[i] = new Point[points.length];
		for (int j = 0; j < points.length; j++)
		{
			String[] pp = points[j].split(",");
			int x = Integer.parseInt(pp[0].trim());
			int y = Integer.parseInt(pp[1].trim());
			lines[i][j] = new Point(x, y);
		}
	}

	static class Vector {

		double x, y; 

		Vector(double a, double b) { x = a; y = b; }

		Vector(Point a, Point b) { this(b.x - a.x, b.y - a.y); }

		double dot(Vector v) { return (x * v.x + y * v.y); }

		double norm2() { return x * x + y * y; }
		
		double cross(Vector v) { return x * v.y - y * v.x; }

	}
	
	static class Point{

		static final double EPS = 1e-9;

		double x, y;                  

		Point(double a, double b) { x = a; y = b; }  
		
		boolean between(Point p, Point q)
		{
			return x < Math.max(p.x, q.x) + EPS && x + EPS > Math.min(p.x, q.x)
					&& y < Math.max(p.y, q.y) + EPS && y + EPS > Math.min(p.y, q.y);
		}
		
		public int compareTo(Point p)
		{
			if(Math.abs(x - p.x) > EPS) return x > p.x ? 1 : -1;
			if(Math.abs(y - p.y) > EPS) return y > p.y ? 1 : -1;
			return 0;
		}

		public String toString()
		{
			return x + " " +y;
		}
		
		boolean onSegment(Point a, Point b)
		{
			return Math.abs(new Vector(a, b).cross(new Vector(a, this))) < EPS && new Vector(this, a).dot(new Vector(this,  b)) < EPS;
		}
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
		
		Point intersect(Line l)
		{
			double det = a*l.b - l.a*b;
			if(det == 0)
				return null;
			double x = ((-c)*l.b - (-l.c)*b)/det;
			double y = (a*(-l.c) - l.a*(-c))/det;
			return new Point(x, y);
		}
		
	}
	
	static class UnionFind {                                              
		int[] p, rank, setSize;
		int numSets;

		public UnionFind(int N) 
		{
			p = new int[numSets = N];
			rank = new int[N];
			setSize = new int[N];
			for (int i = 0; i < N; i++) {  p[i] = i; setSize[i] = 1; }
		}

		public int findSet(int i) { return p[i] == i ? i : (p[i] = findSet(p[i])); }

		public boolean isSameSet(int i, int j) { return findSet(i) == findSet(j); }

		public void unionSet(int i, int j) 
		{ 
			if (isSameSet(i, j)) 
				return;
			numSets--; 
			int x = findSet(i), y = findSet(j);
			if(rank[x] > rank[y]) { p[y] = x; setSize[x] += setSize[y]; }
			else
			{	p[x] = y; setSize[y] += setSize[x];
				if(rank[x] == rank[y]) rank[y]++; 
			} 
		}

		public int numDisjointSets() { return numSets; }

		public int sizeOfSet(int i) { return setSize[findSet(i)]; }
	}
}
