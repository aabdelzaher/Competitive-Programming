import java.io.PrintWriter;
import java.util.Scanner;

/*
 * If k is even there is no answer.
 * Divide the nodes into two components. The two components will be mirror image of each other 
 * & only one node from each component will be connected to each other (making bridge).
 * To construct each component we first make a complete graph of (k+1) nodes (each with degree k)
 * We then remove one edge at a time and add the edges between these nodes and node connected to the other component.
 * The degree of the nodes at the component is not affected(reamins k). Degree of node connected to the other component increases by 2.
 * Since K is odd, and the node connected to the other component has degree one, and at each step we add 2 edges, It will eventualy reach K.
 */
public class RegularBridge
{
	
	static PrintWriter pw = new PrintWriter(System.out);
	static boolean[][] graph;
	static boolean[][] graphDebug;
	static boolean[] visited;
	static int k;
	static int edges = 0;
	static StringBuilder sb = new StringBuilder("");
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		k = sc.nextInt();
		if(k == 1)
		{
			System.out.println("YES");
			System.out.println(2 + " " + 1);
			System.out.println(1 + " " + 2);
			return;
		}
		if(k %2 == 0)
			System.out.println("NO");
		else
		{
			System.out.println("YES");
			constructGraph(k+1);
			modifyBridge(k-1);
			printLeft();
			sb.append(1 + " " + (k+3)+"\n");
			graphDebug[0][k+2] = true;
			graphDebug[k+2][0] = true;
			++edges;
			printRight();
			pw.println((2*visited.length) + " " + edges);
			pw.print(sb);
		}
//		for (int i = 0; i < graphDebug.length; i++)
//		{
//			int cnt = 0;
//			for (int j = 0; j < graphDebug[i].length; j++)
//			{
//				if(graphDebug[i][j])
//					cnt++;
//			}
//			System.out.println(i + ": " + cnt);
//		}
		pw.flush();
		pw.close();

	}
	private static void printRight()
	{
		for (int i = 0; i < graph.length; i++)
			for (int j = i+1; j < graph.length; j++)
				if(graph[i][j])
				{
					++edges;
					graphDebug[i+k+2][j+k+2] = true;
					graphDebug[j+k+2][i+k+2] = true;
					sb.append((i+1+k+2) + " " + (j+1+k+2)+"\n");		
				}
	}
	private static void printLeft()
	{
		for (int i = 0; i < graph.length; i++)
			for (int j = i+1; j < graph.length; j++)
				if(graph[i][j])
				{
					++edges;
					graphDebug[i][j] = true;
					graphDebug[j][i] = true;
					sb.append((i+1) + " " + (j+1) + "\n");
				}
	}
	private static void modifyBridge(int need)
	{
		for (int i = 1; i < graph.length; i++)
			for (int j = i+1; j < graph.length; j++)
			{
				if(need == 0)
					return;
				if(graph[i][j] && !visited[i] && !visited[j] && !graph[0][i] && !graph[0][j])
				{
					graph[i][j] = false;
					visited[i] = visited[j] = false;
					graph[0][i] = true;
					graph[0][j] = true;
					need -= 2;
				}
			}
		
		if(need != 0)
			System.out.println("WE ARE FUCKED!");
	}
	private static void constructGraph(int nodes)
	{
		graph = new boolean[nodes+1][nodes+1];
		graphDebug = new boolean[2*nodes+2][2*nodes+2];
		visited = new boolean[nodes+1];
		for (int i = 1; i <= nodes; i++)
			for (int j = i+1; j <= nodes; j++)
				graph[i][j] = true;
	}
}
