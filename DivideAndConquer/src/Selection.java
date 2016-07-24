
public class Selection {
	// SELECTION FUNCTIONS AND HELPERS

	public static int selectRangeR(int arr[], int start, int end, int ord) {
		if (end == start) return arr[start];

		int rand = Quicksort.randInt(start, end);
		Quicksort.swap(arr, start, rand);
		int pivot = arr[start];
		int pos = start + 1;

		for (int i = start + 1; i <= end; ++i) {
			if (arr[i] < pivot) {
				Quicksort.swap(arr, i, pos);
				++pos;
			}
		}

		--pos;
		Quicksort.swap(arr, start, pos);

		if (pos == ord) {
			return pivot;
		} else if (pos > ord) {
			return selectRangeR(arr, start, pos - 1, ord);
		} else {
			return selectRangeR(arr, pos + 1, end, ord);
		}
	}

	public static int selectR(int arr[], int ord) {
		int len = arr.length;

		return selectRangeR(arr, 0, len - 1, ord);
	}
}
