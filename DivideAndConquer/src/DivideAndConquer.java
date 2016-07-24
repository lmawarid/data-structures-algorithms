
public class DivideAndConquer {
	
	// MAIN FUNCTION

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// MERGESORT TEST
		
		int[] myArray = {9, 4, 2, 3, 5, 1, 2, 9, 3, 5, 0};
		System.out.println("Before merging:");
		printArr(myArray);
		Mergesort.mergesort(myArray);
		System.out.println("After merging:");
		printArr(myArray);
		
		// QUICKSORT TEST
		
		int[] myArray2 = {10, 2, 8, 5, 15, 35, 31, 8, 17, 15, 7, 10, 23};
		System.out.println("\nBefore quicksorting:");
		printArr(myArray2);
		Quicksort.quicksort(myArray2);
		System.out.println("After quicksorting:");
		printArr(myArray2);
		
		// INVERSION TEST
		
		System.out.println("\nNow let's try to count inversions!");
		int[] invTest1 = {5, 1, 6, 3, 2, 4};
		System.out.print("Array is: ");
		printArr(invTest1);
		System.out.println("This has " + Inversion.countInversions(invTest1) + 
						   " inversions!");
		
		// CLOSEST PAIR TEST
		
		System.out.println("\nWorking with vectors...");
		Vector[] pairs = new Vector[] {
				new Vector(3,5), new Vector(1,9), new Vector(2,4),
				new Vector(1,1), new Vector(0,7), new Vector(5,2),
				new Vector(2,8), new Vector(4,0), new Vector(6,9)
		};
		printVArr(pairs);
		System.out.print("Now let's find the closest pair! It is: ");
		Vector[] closestPair = ClosestPair.closestPair(pairs);
		printVArr(closestPair);
		
		// SELECTION TEST
		
		int[] selTest = {5, 9, 2, 6, 3, 7, 0, 1, 2, 4, 8, 10};
		System.out.println("\n\nThis is the array before selection: ");
		printArr(selTest);
		System.out.println("Let's sort \'manually\':");
		int selLen = selTest.length;
		for (int i = 0; i < selLen; ++i) {
			System.out.print(Selection.selectR(selTest, i) + " ");
		}
		System.out.println("");
	}
	
	// STANDARD ARRAY PRINTERS
	
	public static void printArr(int[] arr) {
			
		int len = arr.length;
		
		System.out.print("[");
		
		for (int i = 0; i < len; ++i) {
			if (i == len - 1) {
				System.out.print(arr[i]);
				break;
			}
			
			System.out.print(arr[i] + ", ");
		}
			
		System.out.println("]");
			
	}
	
	public static void printArr(int[] arr, int start, int end) {

		System.out.print("[");
		
		for (int i = start; i <= end; ++i) {
			if (i == end) {
				System.out.print(arr[i]);
				break;
			}
			
			System.out.print(arr[i] + ", ");
		}
			
		System.out.println("]");
			
	}
		
	public static void printVArr(Vector[] arr) {

		int len = arr.length;
		
		System.out.print("[");
			
		for (int i = 0; i < len; ++i) {
			if (i == len - 1) {
				arr[i].print();
				break;
			}
			arr[i].print();
			System.out.print(", ");
		}
			
		System.out.println("]");		
			
	}
}