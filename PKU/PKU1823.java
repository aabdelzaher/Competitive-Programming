package supervision;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class PKU1823
{
	/*
	 * This solution uses segment tree. Free rooms are zeros and occupied rooms
	 * are ones. The node in segment tree carries maximum consecutive zeros,
	 * maximum zeros to left and maximum zeros to right. Merging two nodes we
	 * take maximum of two children maxima, and summation of left zeros of right
	 * child and right zeros of left child. Left of node is left of left child,
	 * except if the left child contains all zeros so left is all elements of
	 * left child + left of right child. Same for right.
	 */
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);

		int n = sc.nextInt();
		int p = sc.nextInt();

		int N = 1;
		while (N < n)
			N <<= 1; // padding

		int[] in = new int[N + 1];
		SegmentTree t = new SegmentTree(in);

		for (int i = 0; i < p; i++)
		{
			int type = sc.nextInt();
			if (type == 3)
				pw.println(t.query(1, n).max);
			else if (type == 2)
			{
				int l = sc.nextInt();
				int r = l + sc.nextInt() - 1;
				t.update_range(l, r, 0);
			} else
			{
				int l = sc.nextInt();
				int r = l + sc.nextInt() - 1;
				t.update_range(l, r, 1);
			}
		}

		pw.flush();
		pw.close();

	}

	static class SegmentTree
	{
		int N; // the number of elements in the array as a power of 2 (i.e.
				// after padding)
		int[] array, max, left, right, lazy;

		SegmentTree(int[] in)
		{
			array = in;
			N = in.length - 1;
			max = new int[N << 1]; // no. of nodes = 2*N - 1, we add one to
									// cross out index zero
			left = new int[N << 1];
			right = new int[N << 1];
			lazy = new int[N << 1];
			Arrays.fill(lazy, -1);
			build(1, 1, N);
		}

		void build(int node, int b, int e) // O(n)
		{
			if (b == e)
			{
				max[node] = 1; // modify base case
				left[node] = 1;
				right[node] = 1;
			} else
			{
				int mid = b + e >> 1;
				build(node << 1, b, mid);
				build(node << 1 | 1, mid + 1, e);
				// max[node] = max[node<<1]+max[node<<1|1]; // modify merging
				merge(node, node << 1, b, mid, node << 1 | 1, mid + 1, e);
			}
		}

		void merge(int node, int l, int bl, int el, int r, int br, int er)
		{
			max[node] = Math.max(max[l], Math.max(max[r], left[r] + right[l]));
			left[node] = left[l];
			if (left[l] == el - bl + 1)
				left[node] = left[l] + left[r];
			right[node] = right[r];
			if (right[r] == er - br + 1)
				right[node] = right[r] + right[l];
		}

		void update_range(int i, int j, int val) // O(log n)
		{
			update_range(1, 1, N, i, j, val);
		}

		void update_range(int node, int b, int e, int i, int j, int val)
		{
			if (i > e || j < b)
				return;
			if (b >= i && e <= j)
			{
				// max[node] += (e-b+1)*val; // how value node carrying is
				// modified
				// lazy[node] += val; // lazy value for node

				if (val == 0)
				{
					left[node] = right[node] = max[node] = e - b + 1;
					lazy[node] = 0;
				} else if (val == 1)
				{
					left[node] = right[node] = max[node] = 0;
					lazy[node] = 1;
				}
			} else
			{
				int mid = b + e >> 1;
				propagate(node, b, mid, e);
				update_range(node << 1, b, mid, i, j, val);
				update_range(node << 1 | 1, mid + 1, e, i, j, val);
				// max[node] = max[node<<1] + max[node<<1|1]; // merging
				merge(node, node << 1, b, mid, node << 1 | 1, mid + 1, e);
			}

		}

		// modify
		void propagate(int node, int b, int mid, int e)
		{
			if (lazy[node] == 0)
			{
				lazy[node << 1] = 0;
				lazy[node << 1 | 1] = 0;
				left[node << 1] = right[node << 1] = max[node << 1] = mid - b
						+ 1;
				left[node << 1 | 1] = right[node << 1 | 1] = max[node << 1 | 1] = e
						- mid;
			} else if (lazy[node] == 1)
			{
				lazy[node << 1] = 1;
				lazy[node << 1 | 1] = 1;
				left[node << 1] = right[node << 1] = max[node << 1] = 0;
				left[node << 1 | 1] = right[node << 1 | 1] = max[node << 1 | 1] = 0;
			}
			lazy[node] = -1;
		}

		Triple merge(Triple l, int el, int bl, int er, int br, Triple r)
		{
			if (l == null)
				return r;
			if (r == null)
				return l;
			int ll, mx, rr;
			mx = Math.max(l.max, Math.max(r.max, r.l + l.r));
			ll = l.l;
			if (l.l == el - bl + 1)
				ll = l.l + r.l;
			rr = r.r;
			if (r.r == er - br + 1)
				rr = r.r + l.r;
			return new Triple(mx, ll, rr);
		}

		Triple query(int i, int j)
		{
			return query(1, 1, N, i, j);
		}

		Triple query(int node, int b, int e, int i, int j) // O(log n)
		{
			if (i > e || j < b)
				return null; // base case 1
			if (b >= i && e <= j)
				return new Triple(max[node], left[node], right[node]); // base
																		// case
																		// 2
			int mid = b + e >> 1;
			propagate(node, b, mid, e);
			Triple q1 = query(node << 1, b, mid, i, j);
			Triple q2 = query(node << 1 | 1, mid + 1, e, i, j);
			return merge(q1, mid, b, e, mid + 1, q2); // merging

		}

		static class Triple
		{
			int l, r, max;

			public Triple(int max, int l, int r)
			{
				this.l = l;
				this.r = r;
				this.max = max;
			}
		}
		// public static void main(String[] args) {
		//
		// Scanner sc = new Scanner(System.in);
		//
		// int n = sc.nextInt();
		// int N = 1; while(N < n) N <<= 1; //padding
		//
		// int[] in = new int[N + 1];
		// for(int i = 1; i <= n; i++)
		// in[i] = sc.nextInt();
		// }

	}

	static class Scanner
	{
		StringTokenizer st;
		BufferedReader br;

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

		public int nextInt() throws IOException
		{
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException
		{
			return Long.parseLong(next());
		}

		public String nextLine() throws IOException
		{
			return br.readLine();
		}

		public boolean ready() throws IOException
		{
			return br.ready();
		}
	}
}
