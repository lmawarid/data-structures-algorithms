import java.util.Arrays;

public class ClosestPair {
	// CLOSEST PAIR FUNCTIONS AND HELPERS

	public static double distance(Vector[] pair) {
		if (pair == null) return Double.POSITIVE_INFINITY;

		Vector v1 = pair[0];
		Vector v2 = pair[1];

		return Math.sqrt((v1.x - v2.x)^2 + (v1.y - v2.y)^2);
	}

	public static double distance(Vector v1, Vector v2) {
		return Math.sqrt((v1.x - v2.x)^2 + (v1.y - v2.y)^2);
	}

	public static void mergeByX(Vector[] dest, Vector[] src1, Vector[] src2) {

		int len1 = src1.length;
		int len2 = src2.length;

		int pos1 = 0;
		int pos2 = 0;

		for (int i = 0; i < len1 + len2; ++i) {
			if (pos1 < len1 && (pos2 == len2 || src1[pos1].x <= src2[pos2].x)) {
				dest[i] = src1[pos1];
				++pos1;
			} else {
				dest[i] = src2[pos2];
				++pos2;
			}
		}
	}

	public static void mergeByY(Vector[] dest, Vector[] src1, Vector[] src2) {

		int len1 = src1.length;
		int len2 = src2.length;

		int pos1 = 0;
		int pos2 = 0;

		for (int i = 0; i < len1 + len2; ++i) {
			if (pos1 < len1 && (pos2 == len2 || src1[pos1].y <= src2[pos2].y)) {
				dest[i] = src1[pos1];
				++pos1;
			} else {
				dest[i] = src2[pos2];
				++pos2;
			}
		}
	}

	// NOTE: Field must be either 'x' or 'y'

	public static void mergesortByField(Vector[] arr, char field) {

		if (field != 'x' && field != 'y') {
			System.err.println("Field must be one of 'x' or 'y'!");
		} 

		int len = arr.length;

		if (len <= 1) return;

		int llen = len / 2;
		int rlen = len - llen;

		Vector[] left = new Vector[llen];
		Vector[] right = new Vector[rlen];

		for (int i = 0; i < llen; ++i) {
			left[i] = arr[i];
		}

		for (int j = 0; j < rlen; ++j) {
			right[j] = arr[llen + j];
		}

		if (field == 'x') {
			mergesortByField(left, 'x');
			mergesortByField(right, 'x');
			mergeByX(arr, left, right);
		} else {
			mergesortByField(left, 'y');
			mergesortByField(right, 'y');
			mergeByY(arr, left, right);
		}
	}

	public static Vector[] closestSplitPair(Vector[] pX, Vector[] pY,
			double minDist) {

		int len = pX.length;
		int midX = pX[len / 2 - 1].x;
		int pos = 0;

		// this is super ugly, for better array resizing
		// pls kindly learn how to use arraylist kthxbai

		for (int i = 0; i < len; ++i) {
			if (pY[i].x >= midX - minDist && pY[i].x <= midX + minDist) {
				pY[pos] = pY[i];
				++pos;
			} else {
				continue;
			}
		}

		pY = Arrays.copyOf(pY, pos);
		int bestLen = pY.length;
		double bestDist = minDist;
		Vector[] bestPair = null;

		for (int i = 1; i < bestLen; ++i) {
			int minSearch = Math.min(bestLen - i, 7);
			for (int j = 1; j < minSearch; ++j) {
				double dist = distance(pY[i], pY[i + j]);

				if (dist < bestDist) {
					bestDist = dist;
					if (bestPair == null) bestPair = new Vector[2];
					bestPair[0] = pY[i];
					bestPair[1] = pY[i + j];
				} else {
					continue;
				}
			}
		}

		return bestPair;
	}

	public static Vector[] closestPairXY(Vector[] pX, Vector[] pY) {

		int len = pX.length;

		if (len == 2) {
			return pX;
		} else if (len == 3) {
			double dist1 = distance(pX[0], pX[1]);
			double dist2 = distance(pX[1], pX[2]);
			double dist3 = distance(pX[2], pX[0]);
			Vector[] res = new Vector[2];

			if (dist1 <= dist2 && dist1 <= dist3) {
				res[0] = pX[0];
				res[1] = pX[1];
			} else if (dist2 <= dist1 && dist2 <= dist3) {
				res[0] = pX[1];
				res[1] = pX[2];
			} else {
				res[0] = pX[0];
				res[1] = pX[2];
			}

			return res;
		} else {

			int llen = len / 2;
			int rlen = len - llen;

			Vector[] leftX = new Vector[llen];
			Vector[] leftY = new Vector[llen];

			Vector[] rightX = new Vector[rlen];
			Vector[] rightY = new Vector[rlen];

			for (int i = 0; i < llen; ++i) {
				leftX[i] = pX[i];
				leftY[i] = pY[i];
			}

			for (int j = 0; j < rlen; ++j) {
				rightX[j] = pX[llen + j];
				rightY[j] = pY[llen + j];
			}

			Vector[] closestLeft = closestPairXY(leftX, leftY);
			Vector[] closestRight = closestPairXY(rightX, rightY);

			double distLeft = distance(closestLeft);
			double distRight = distance(closestRight);
			double minDist = Math.min(distLeft, distRight);

			Vector[] closestSplit = closestSplitPair(pX, pY, minDist);
			double distSplit = distance(closestSplit);

			if (distLeft <= distRight && distLeft <= distSplit) {
				return closestLeft;
			} else if (distRight <= distLeft && distRight <= distSplit) {
				return closestRight;
			} else {
				return closestSplit;
			}
		}
	}

	public static Vector[] closestPair(Vector[] arr) {

		Vector[] sortedByX = arr.clone();
		Vector[] sortedByY = arr.clone();

		mergesortByField(sortedByX, 'x');
		mergesortByField(sortedByY, 'y');

		return closestPairXY(sortedByX, sortedByY);
	}
}
