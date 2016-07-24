import java.util.Random;

public class Quicksort {

	// QUICKSORT FUNCTIONS AND HELPERS

	public static void swap(int arr[], int id1, int id2) {
		int temp = arr[id1];
		arr[id1] = arr[id2];
		arr[id2] = temp;
	}

	// returns index of the median value of an array - for median pivot selection

	public static int medIndex(int arr[], int a, int b, int c) {
		int med = Math.max(Math.min(a, b), Math.min(Math.max(a, b), c));

		if (med == arr[a]) return a;
		if (med == arr[b]) return b;
		else return c;
	}

	// random integer - for random integer selection

	public static int randInt(int min, int max) {
		Random randGen = new Random();
		int num = randGen.nextInt(max - min + 1) + min;

		return num;
	}

	// randomized quicksort

	public static void quicksortRange(int arr[], int start, int end) {
		if (end <= start) return;

		int rand = randInt(start, end);
		swap(arr, start, rand);
		int pivot = arr[start];
		int pos = start + 1;

		for (int i = start + 1; i <= end; ++i) {
			if (arr[i] < pivot) {
				swap(arr, i, pos);
				++pos; 
			}
		}

		--pos;
		swap(arr, start, pos);

		quicksortRange(arr, start, pos - 1);
		quicksortRange(arr, pos + 1, end);
	}

	public static void quicksort(int[] arr) {
		int len = arr.length;

		quicksortRange(arr, 0, len - 1);
	}


}
