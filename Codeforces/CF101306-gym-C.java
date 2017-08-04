import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class F
{
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[25];
		for (int i = 0; i < n; i++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			int f = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			
			arr[f] += 1;
			arr[t] -= 1;
		}
		
		for (int i = 1; i < arr.length; i++)
			arr[i] += arr[i-1];
		
		int max = 0;
		for (int i = 0; i < arr.length; i++)
		{
			max = Math.max(max, arr[i]);
		}
		
		System.out.println(max);
	}
	
}
