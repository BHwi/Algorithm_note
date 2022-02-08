import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static int stoi(String s) {return Integer.parseInt(s);}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = stoi(br.readLine());
		int[] arr = new int[n];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		for(int i = 0; i < arr.length; i++) {
			arr[i] = stoi(st.nextToken());
		}
		
		Tree tree = new Tree(n, arr);
		
		// update -> start = 0, end = n - 1, left ~ right = to change dif;
		tree.update(0, n - 1, 1, 2, 4, -1);
		
		// sum -> start = 0, end = n - 1, left ~ right = sum left ~ right;
		System.out.println(tree.sum(0, n - 1, 1, 4, 4));
	}
	
	public static class Tree {
		int[] arr, tree, lazy;
		
		public Tree(int n, int[] arr) {
			this.arr = new int[n];
			tree = new int[n * 4];
			lazy = new int[n * 4];
			
			for(int i = 0; i < arr.length; i++) {
				this.arr[i] = arr[i];
			}
			init(0, n - 1, 1);
		}
		
		public int init(int start, int end, int node) {
			if(start == end) return tree[node] = arr[start];
			
			int mid = (start + end) / 2;
			return tree[node] = init(start, mid, node * 2) + init(mid + 1, end, node * 2 + 1);
		}
		
		public void propagate(int start, int end, int node) {
			if(lazy[node] != 0) {
				if(start != end) {
					lazy[node * 2] += lazy[node];
					lazy[node * 2 + 1] += lazy[node];
				}
				tree[node] += lazy[node] * (end - start + 1);
				lazy[node] = 0;
			}
		}
		
		public int sum(int start, int end, int node, int left, int right) {
			propagate(start, end, node);
			if(left > end || right < start) {
				return 0;
			}
			if(left <= start && end <= right) {
				return tree[node];
			}
			
			int mid = (start + end) / 2;
			return sum(start, mid, node * 2, left, right) + sum(mid + 1, end, node * 2 + 1, left, right);
		}
		
		public void update(int start, int end, int node, int left, int right, int dif) {
			propagate(start, end, node);
			if(right < start || left > end) return;
			
			if(left <= start && end <= right) {
				lazy[node] = dif;
				propagate(start, end, node);
				return;
			}
			
			int mid = (start + end) / 2;
			
			update(start, mid, node * 2, left, right, dif);
			update(mid + 1, end, node * 2 + 1, left, right, dif);
			
			tree[node] = tree[node * 2] + tree[node * 2 + 1];
		}
		
		public void print() {
			for(int i = 0; i < tree.length; i++) {
				System.out.println(i + " : " + tree[i]);
			}
		}
	}

}
