import java.util.*;
import java.util.Arrays;

/* 
 * Implement your test cases under the class SortingTest 
 * Follow the principles of Behavior-Driven Testing
 * Start by identifying and defining the expected behaviors
 * Implement test for each of the identified behavior
*/
public class SortingTest{
	
	public static void main(String[] args) {
		SortingPack sort = new SortingPack();
		SortingTest sortTest = new SortingTest();
		// you may need to print sufficient information 
		// that helps you understand if there are any issues
		//System.out.print("hello world");

		//easy basic test that I used
		int[] arr = {14, 345, 20, 55,1};
		System.out.print(Arrays.toString(sort.insertionSort(arr))+"\n");
		int[] arr2 = {20, 345, 14, 55,40,5,53,23,7,1,696,3,874,35};
		System.out.print(Arrays.toString(sort.quickSort(arr2))+"\n");
		int[] arr3 = {20, 345, 14, 55,40,5,53,23,7,1,696,3,874,35};
		//int[] arr3 = {1,2,3,4,5,6};
		//int[] arr3 = {6,5,3,2,8,9,1,10,4,7};
		System.out.print(Arrays.toString(sort.mergeSort(arr3))+"\n");
        sortTest.insertionSortTest1();
		sortTest.quickSortTest2();
		sortTest.mergeSortTest3();
	}


	//I'm using array.sort to compared my code to java's internal sorting to my insertionsort, quicksort, and mergeSort
	public void insertionSortTest1(){
		SortingPack sort = new SortingPack();
		int[] arr = randomArray(100);
		int[] brr = arr.clone();
		Arrays.sort(arr);
		System.out.print(Arrays.toString(arr)+"\n");
		System.out.print(Arrays.toString(sort.insertionSort(brr))+"\n");

		System.out.print("Passes insertion sort test 1: "+Arrays.equals(arr,brr)+"\n");
	}
	public void quickSortTest2(){
		SortingPack sort = new SortingPack();
		int[] arr = randomArray(100);
		int[] brr = arr.clone();
		Arrays.sort(arr);
		System.out.print(Arrays.toString(arr)+"\n");
		System.out.print(Arrays.toString(sort.quickSort(brr))+"\n");

		System.out.print("Passes quick sort test 2: "+Arrays.equals(arr,brr)+"\n");
	}
	public void mergeSortTest3(){
		SortingPack sort = new SortingPack();
		int[] arr = randomArray(100);
		int[] brr = arr.clone();
		Arrays.sort(arr);
		System.out.print(Arrays.toString(arr)+"\n");
		System.out.print(Arrays.toString(sort.mergeSort(brr))+"\n");

		System.out.print("Passes merge sort test 3: "+Arrays.equals(arr,brr)+"\n");
	}

	//random arrays I used for testing
	 public int[] randomArray(int n){

		int[] arr=new int[n];
		for(int i=0;i<n;i++){
			Random rand= new Random();
			arr[i]=rand.nextInt(100);
		}
		return arr;
	 }
}