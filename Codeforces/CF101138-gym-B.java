import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class G
{
	/*
	 * Valid structure is power of two, and every pair must contain at least one 'P'
	 */
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(br.readLine());
		HashSet<Integer> p = new HashSet<Integer>();
		for (int i = 1; i <= 100; i*=2)
			p.add(i);
		out: while(tc-->0)
		{
			String s = br.readLine();
			int len = s.length();
			if(len == 1)
			{
				System.out.println("YES");
				continue;
			}
			
			if(!p.contains(len))
			{
				System.out.println("NO");
				continue;
			}
			
			for (int i = 0; i < len; i+=2)
			{
				if(s.charAt(i) != 'P' && s.charAt(i+1) != 'P')
				{
					System.out.println("NO");
					continue out;
				}
			}
			System.out.println("YES");
		}
	}
}
