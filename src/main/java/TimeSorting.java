import java.util.*;

public class TimeSorting{
	public static void main(String[] args) {
		// a lot of things will happen here
		TimeSorting time = new TimeSorting();

		for(int n=100;n<=20000;n=n+100){
			int[] arr = time.randomArray(n);
        	int[] brr = arr.clone();
        	int[] crr = arr.clone();
        	double time_insertionSort_1 = time.sortingTime("insertionSort",arr);
        	double time_quickSort_1 = time.sortingTime("quickSort",brr);		
        	double time_mergeSort_1 = time.sortingTime("mergeSort",crr);
		
			int[] arr2 = time.randomArray(n);
        	int[] brr2 = arr.clone();
        	int[] crr2 = arr.clone();
        	double time_insertionSort_2 = time.sortingTime("insertionSort",arr2);
        	double time_quickSort_2 = time.sortingTime("quickSort",brr2);		
        	double time_mergeSort_2 = time.sortingTime("mergeSort",crr2);

			int[] arr3 = time.randomArray(n);
        	int[] brr3 = arr.clone();
        	int[] crr3 = arr.clone();
        	double time_insertionSort_3 = time.sortingTime("insertionSort",arr3);
        	double time_quickSort_3 = time.sortingTime("quickSort",brr3);		
        	double time_mergeSort_3 = time.sortingTime("mergeSort",crr3);
			
			double totalInsertionSort=time_insertionSort_1+time_insertionSort_2+time_insertionSort_3;
			double totalQuickSort=time_quickSort_1+time_quickSort_2+time_quickSort_3;
			double totalMergeSort=time_mergeSort_1+time_mergeSort_2+time_mergeSort_3;
		
			System.out.print((n)+","+totalInsertionSort+",");
			System.out.print((n)+","+totalQuickSort+",");
			System.out.print((n)+","+ totalMergeSort+"\n");

		}
	}

	// this method generate a random int array with the length as size
	// each random int should be in [0, 10000]
	// parameters: size
	// return data: int[]
	public int[] randomArray(int size) {
		// remove these two lines
		int[] arr=new int[size];
		for(int i=0;i<size;i++){
			Random rand= new Random();
			arr[i]=rand.nextInt(10001);
		}
		return arr;
	}

	// this method counts the time it takes to sort arr using sortingAlgorithm
	// parameters: sortingAlgorithm and int array
	// return data: double
	public double sortingTime(String sortingAlgorithm, int[] arr) {
		SortingPack sort = new SortingPack();
		long start=System.nanoTime();
		if(sortingAlgorithm.equals("mergeSort")){
			sort.mergeSort(arr);
		}
		
		else if(sortingAlgorithm.equals("quickSort")){
			sort.quickSort(arr);
		}
		else if(sortingAlgorithm.equals("insertionSort")){
			sort.insertionSort(arr);
		}
		long end=(System.nanoTime()-start)/1000;
		
		return end;
	}

  // you are welcome to add any supporting methods
}
