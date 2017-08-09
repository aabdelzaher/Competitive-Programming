import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*
 * The first player should remove a digit that makes the sum divisible by 3.
 * If this moves does not exist the first player loses.
 * After this first move each of the two players should remove a multiple of 3 to keep sum multiple of 3.
 * So if count of numbers that have (mod 3 = 0) is even the first player wins, else second player wins.
 */
public class UVa11489
{
	public static void main(String[] args) throws IOException 
	{
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int cs = 1;
		int tc = sc.nextInt();
		char fst = 'S', snd = 'T';
		while(tc-->0)
		{
			char[] arr = sc.next().toCharArray();
			for (int i = 0; i < arr.length; i++)
				arr[i] -= '0';
			int cnt3 = 0;
			int sum = 0;
			boolean[] fnd = new boolean[3];
			for (int i = 0; i < arr.length; i++)
			{
				int d = arr[i];
				if(d%3 == 0)
					cnt3++;
				sum += d;
				fnd[d%3] = true;
			}
			int rem = sum % 3;
			if(rem != 0)
			{
				if(fnd[rem])
				{
					if(cnt3 % 2 == 0)
						pw.println("Case "+cs+++": "+fst);
					else
						pw.println("Case "+cs+++": "+snd);
				}
				else
				{
					pw.println("Case "+cs+++": "+snd);
					continue;
				}
			}
			else
			{
				if(cnt3 > 0)
				{
					cnt3--;
					if(cnt3 % 2 == 0)
						pw.println("Case "+cs+++": "+fst);
					else
						pw.println("Case "+cs+++": "+snd);
				}
				else
					pw.println("Case "+cs+++": "+snd);
			}
		}
		pw.flush();
		pw.close();
	}

	static class Scanner 
		{
			StringTokenizer st; BufferedReader br;
			public Scanner(InputStream s)
			{
				br = new BufferedReader(new InputStreamReader(s));
			}
			public String next() throws IOException
			{
				while (st == null || !st.hasMoreTokens()) 
				st = new StringTokenizer(br.readLine());
				return st.nextToken();
			}
			public int nextInt() throws IOException {return Integer.parseInt(next());}
			public long nextLong() throws IOException {return Long.parseLong(next());}
			public String nextLine() throws IOException {return br.readLine();}
			public boolean ready() throws IOException {return br.ready();}
		}
}
