
public class Inversion {

	// INVERSION COUNTING FUNCTIONS AND HELPERS 

	public static long countSplitInversions(int[] arr1, int[] arr2) {

		Mergesort.mergesort(arr1);
		Mergesort.mergesort(arr2);

		int len1 = arr1.length;
		int len2 = arr2.length;
		long totalInv = 0;

		int pos1 = 0;
		int pos2 = 0;

		for (int i = 0; i < len1 + len2; ++i) {
			if (pos1 < len1 && (pos2 == len2 || arr1[pos1] <= arr2[pos2])) {
				++pos1;
			} else {
				totalInv += len1 - pos1;
				++pos2;
			}
		}

		return totalInv;

	}

	public static long countInversions(int[] arr) {

		int len = arr.length;

		if (len <= 1) {
			return 0;
		} else if (len == 2) {
			return (arr[0] > arr[1]) ? 1 : 0;
		} else {
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

			long leftCount = countInversions(left);
			long rightCount = countInversions(right);
			long splitCount = countSplitInversions(left, right);

			return leftCount + rightCount + splitCount;
		}

	}

}
