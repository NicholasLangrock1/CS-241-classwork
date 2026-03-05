/*
// this is the testing class
// DO NOT MODIFY THIS CLASS AND ITS METHODS
*/
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.util.Random;

public class SortingPackTest {
    public int[] randomArray(int size) {
        Random rd = new Random(); // creating Random object
        int[] arr = new int[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rd.nextInt()/1000000; // storing random integers in an array
        }
        return arr;
    }

    public String arrayToString(int[] arr) {
        String str = "[";
        for (int i = 0; i < arr.length-1; i++) {
            str += arr[i] + ", ";
        }
        str += arr[arr.length-1] + "]";
        return str;
    }

    @Test public void insertionSortTest() {
        int[] arr1 = randomArray(5);
        int[] arr2 = new int[5];
        for (int i = 0; i < arr1.length; i++) {
            arr2[i] = arr1[i];
        }

    	assertArrayEquals(
            RightSolution.insertionSort(arr2),
            SortingPack.insertionSort(arr1),
            "\nThis is the test on your insertionSort method." +
            "\nThe given array is " + arrayToString(arr1) + "\n" +
            "\nAfter being sorted using InsertionSort");
    }

    @Test public void quickSortTest() {
        int[] arr1 = randomArray(8);
        int[] arr2 = new int[8];
        for (int i = 0; i < arr1.length; i++) {
            arr2[i] = arr1[i];
        }

        assertArrayEquals(
            RightSolution.quickSort(arr2),
            SortingPack.quickSort(arr1),
            "\nThis is the test on your quickSort method." +
            "\nThe given array is " + arrayToString(arr1) + "\n" +
            "\nAfter being sorted using quickSort");
    }

    @Test public void mergeSortTest() {
        int[] arr1 = randomArray(6);
        int[] arr2 = new int[6];
        for (int i = 0; i < arr1.length; i++) {
            arr2[i] = arr1[i];
        }

        assertArrayEquals(
            RightSolution.mergeSort(arr2),
            SortingPack.mergeSort(arr1),
            "\nThis is the test on your mergeSort method." +
            "\nThe given array is " + arrayToString(arr1) + "\n" +
            "\nAfter being sorted using mergeSort");
    }
}
