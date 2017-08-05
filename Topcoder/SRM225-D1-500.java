import java.util.Arrays;

/*
 * Build a graph between words. An edge of cost one exists form word i to word j IFF i can go to j in one stroke
 * Apply Floyd Warshal to get shortest path between all pairs. The answer is the maximum shortest path.
 */
public class ComboBoxKeystrokes
{
	public static void main(String[] args)
	{
		String[] arr = 	
				
			{"apple","Boy","CaRD","Dog","egG","FruiT",
			 "Grape","hand","Ant","eagle","ice cream",
			 "air","East","Exit","Door","camerA","bad",
			 "fast","Zealot","internAtional","Bead",
			 "Bread","Exit","fast", "actiVe","Cats",
			 "beast","Alligator","drag","castle",
			 "clean","iLlEgAl","crab","Free Willy",
			 "first","dean","Fourth of July","King",
			 "fellow","FrEaK","Elephant","bird","Bible",
			 "creep","create","Deal","eEl","entrance",
			 "cream","Fleece"};
		System.out.println(minimumKeystrokes(arr));
	}
	
	public static int minimumKeystrokes(String[] elements)
	{
		int[][] graph; 
		int n = elements.length;
		int inf = (int)1e9;
		graph = new int[n][n];
		for (int i = 0; i < graph.length; i++)
			Arrays.fill(graph[i], inf);
		for (int i = 0; i < n; i++)
			graph[i][i] = 0;
		for (int i = 0; i < elements.length; i++)
			for (int l = 0; l < 26; l++)
			{
				for (int j = (i+1)%n; j != i; j = (j+1)%n)
				{
					if(elements[j].toLowerCase().charAt(0) == (l+'a'))
					{
						graph[i][j] = 1;
						break;
					}
				}
			}
		
		for (int k = 0; k < n; k++)
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
		
		int ans = 0;
		for (int i = 0; i < graph.length; i++)
			for (int j = 0; j < graph[i].length; j++)
				ans = Math.max(ans, graph[i][j]);
		
		return ans;
	}
}
