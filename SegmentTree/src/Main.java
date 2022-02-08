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
		
		System.out.println(tree.sum(0, n - 1, 1, 2, 4));
	}
	
	public static class Tree {
		int arr[];
		int tree[];
		
		public Tree(int n, int[] arr) {
			this.arr = new int[n];
			tree = new int[n * 4];
			
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
		
		public int sum(int start, int end, int node, int left, int right) {
			if(left > end || right < start) {
				return 0;
			}
			if(left <= start && end <= right) {
				return tree[node];
			}
			
			int mid = (start + end) / 2;
			return sum(start, mid, node * 2, left, right) + sum(mid + 1, end, node * 2 + 1, left, right);
		}
		
		public void update(int start, int end, int node, int index, int dif) {
			if(index < start || index > end) return;
			
			tree[node] += dif;
			if(start == end) return;
			
			int mid = (start + end) / 2;
			
			update(start, mid, node * 2, index, dif);
			update(mid + 1, end, node * 2 + 1, index, dif);
		}
		
		public void print() {
			for(int i = 0; i < tree.length; i++) {
				System.out.println(i + " : " + tree[i]);
			}
		}
	}

}
