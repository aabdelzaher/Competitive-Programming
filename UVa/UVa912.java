package mostafa_supervision;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*
 * In the two sequences each number corresponds to letter or another number put them in the same set.
 * being in the same set means that all numbers in this set will be converted to the same letter.
 * we then can check if in the two sequences any two corresponding characters can be reduced to the same letter
 */
public class UVa912
{
	static HashMap<Integer, Integer> map;

	public static void main(String[] args) throws NumberFormatException, IOException
	{
		Scanner sc = new Scanner();
		PrintWriter pw = new PrintWriter(System.out);
		boolean first = true;
		cases: while(sc.br.ready())
		{
			if(!first)
				pw.println();
			else
				first = false;
			int n = sc.nextInt();
			String[] arr1 = new String[n];
			String[] arr2 = new String[n];
			for (int i = 0; i < n; i++)
				arr1[i] = sc.next();
			for (int i = 0; i < n; i++)
				arr2[i] = sc.next();
			UnionFind set = new UnionFind(500);
			map = new HashMap<Integer, Integer>();
			int nxt = 1;
			for (int i = 0; i < arr2.length; i++)
			{
				String s1 = arr1[i];
				String s2 = arr2[i];
				if(Character.isDigit(s1.charAt(0)))
				{
					int val = Integer.parseInt(s1);
					if(!map.containsKey(val))
						map.put(val, nxt++);
				}
				
				if(Character.isDigit(s2.charAt(0)))
				{
					int val = Integer.parseInt(s2);
					if(!map.containsKey(val))
						map.put(val, nxt++);
				}
				
				int set1 = get(s1);
				int set2 = get(s2);
				if(set1 <= 400 || set2 <= 400)
					set.unionSet(set1, set2);
			}
			
			TreeMap<Integer, Character> res = new TreeMap<Integer, Character>();
			for (int i = 0; i < arr1.length; i++)
				if(Character.isDigit(arr1[i].charAt(0)))
				{
					int v = get(arr1[i]);
					if(set.isSameSet(v, get("D")))
					{
						res.put(Integer.parseInt(arr1[i]), 'D');
						arr1[i] = "D";
					}
					else
						if(set.isSameSet(v, get("A")))
						{
							res.put(Integer.parseInt(arr1[i]), 'A');
							arr1[i] = "A";
						}
						else
							if(set.isSameSet(v, get("B")))
							{
								res.put(Integer.parseInt(arr1[i]), 'B');
								arr1[i] = "B";
							}
							else
								if(set.isSameSet(v, get("C")))
								{
									res.put(Integer.parseInt(arr1[i]), 'C');
									arr1[i] = "C";
								}
				}
			
			for (int i = 0; i < arr2.length; i++)
				if(Character.isDigit(arr2[i].charAt(0)))
				{
					int v = get(arr2[i]);
					if(set.isSameSet(v, get("D")))
					{
						res.put(Integer.parseInt(arr2[i]), 'D');
						arr2[i] = "D";
					}
					else
						if(set.isSameSet(v, get("A")))
						{
							res.put(Integer.parseInt(arr2[i]), 'A');
							arr2[i] = "A";
						}
						else
							if(set.isSameSet(v, get("B")))
							{
								res.put(Integer.parseInt(arr2[i]), 'B');
								arr2[i] = "B";
							}
							else
								if(set.isSameSet(v, get("C")))
								{
									res.put(Integer.parseInt(arr2[i]), 'C');
									arr2[i] = "C";
								}
				}
			
			for (int i = 0; i < arr2.length; i++)
			{
				if(!arr2[i].equals(arr1[i]) && Character.isAlphabetic(arr1[i].charAt(0)))
				{
					pw.println("NO");
					continue cases;
				}
			}
			pw.println("YES");
			for (Integer i : res.keySet())
			{
				pw.println(i + " " + res.get(i));
			}
		}
		pw.flush();
		pw.close();
	}
	
	static int get(String s)
	{
		int ret = 0;
		if(Character.isDigit(s.charAt(0)))
			ret = map.get(Integer.parseInt(s));
		else
			ret = s.charAt(0) + 400;
		return ret;
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
	
	static class Scanner
	{
		StringTokenizer st;BufferedReader br;
		public Scanner(){br = new BufferedReader(new InputStreamReader(System.in));}
		public String next() throws IOException
		{
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
		public int nextInt() throws NumberFormatException, IOException{return Integer.parseInt(next());}
	}
}
