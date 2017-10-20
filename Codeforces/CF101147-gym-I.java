import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;


public class OnTheWayToThePark
{
	/*
	 * Here we loop over all circles, We keep radii of valid circles only. if
	 * the circle radius is smaller than the radius of the device, and could be
	 * included in device area then we add this circle with +ve radius at its
	 * start position and -ve radius at its end position (same concept of having
	 * cumulative array). We then loop over constructed array of radii each time
	 * we add radius and maximize our answer.
	 */
	
	static double eps = 1e-9;
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner("walk.in");
		PrintWriter pw = new PrintWriter(System.out);
		
		int tc = sc.nextInt();
		while(tc-->0)
		{
			int n = sc.nextInt();
			int r = sc.nextInt();
			Point[] centers = new Point[n];
			int[] rad = new int[n];
			ArrayList<Event> events = new ArrayList<Event>();
			for (int i = 0; i < n; i++)
			{
				centers[i] = new Point(sc.nextInt(), sc.nextInt());
				rad[i] = sc.nextInt();
				if(rad[i] > r)
					continue;
				else
				{
					double dx2 = 1.0*(r-rad[i])*(r-rad[i]) - 1.0*centers[i].y * centers[i].y;
					if(Math.abs(dx2) < eps)
						dx2 = 0;
					if(dx2 > -eps)
					{
						double dx = Math.sqrt(dx2);
						events.add(new Event(centers[i].x + dx, -rad[i]));
						events.add(new Event(centers[i].x-dx, rad[i]));
					}
				}
			}
			
			Collections.sort(events);
			int cntEvents = events.size();
			long max = 0;
			long cur = 0;
			for (int i = 0; i < cntEvents; i++)
			{
			
				cur += events.get(i).r;
				max = Math.max(max, cur);
			}
			pw.println(max);
		}
		
		pw.flush();
		pw.close();
	}
	
	static class Point
	{
		int x,y;
		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		double dist(Point p)
		{
			return Math.sqrt((x-p.x)*(x-p.x) + (y-p.y)*(y-p.y));
		}
	}
	static class Event implements Comparable<Event>
	{
		double x;
		int r;
		public Event(double x, int r)
		{
			this.x = x; this.r = r;
		}
		public int compareTo(Event e)
		{
			if(Math.abs(x-e.x) < eps)
				return e.r - r;
			return Double.compare(x, e.x);
		}
		
	}
	
	static class Scanner 
	{
		StringTokenizer st;BufferedReader br;
		public Scanner(InputStream s){	br = new BufferedReader(new InputStreamReader(s));}
		public Scanner(String s) throws FileNotFoundException{	br = new BufferedReader(new FileReader(new File(s)));}
		public String next() throws IOException 
		{
			while (st == null || !st.hasMoreTokens()) 
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
		public int nextInt() throws IOException {return Integer.parseInt(next());}
		public long nextLong() throws IOException {return Long.parseLong(next());}
		public String nextLine() throws IOException {return br.readLine();}
		public double nextDouble() throws IOException {return Double.parseDouble(next());}
		public boolean ready() throws IOException {return br.ready();}
	}
}
