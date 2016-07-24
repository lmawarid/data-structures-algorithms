
public class Mergesort {
	
	// MERGESORT FUNCTIONS AND HELPERS

	public static void merge(int[] dest, int[] src1, int[] src2) {

		int len1 = src1.length;
		int len2 = src2.length;
		int newLen = len1 + len2;

		int pos1 = 0;
		int pos2 = 0;

		for (int i = 0; i < newLen; ++i) {
			if (pos1 < len1 && (pos2 == len2 || src1[pos1] <= src2[pos2])) {
				dest[i] = src1[pos1];
				++pos1;
			} else {
				dest[i] = src2[pos2];
				++pos2;
			}
		}

	}

	public static void mergesort(int[] arr) {

		int len = arr.length;

		if (len <= 1) return;

		int llen = len / 2;
		int rlen = len - llen;

		int[] left = new int[llen];
		int[] right = new int[rlen];

		for (int i = 0; i < llen; ++i) {
			left[i] = arr[i];
		}

		for (int j = 0; j < rlen; ++j) {
			right[j] = arr[llen + j];
		}

		mergesort(left);
		mergesort(right);

		merge(arr, left, right);

	}
}
