/* Author:Nicholas Langrock
 * Date: 04/22/2022
 * Description: This is is my submission for the a1 project in 241, where our goal was to create four different sorting algorithms, and
 * Have them successfully sort unsorted arrays.
 * This is the main file, where the program takes in the user input and sorts the array that they created with specified size, and sorts it
 * based on their preffered sorting type.
 * */
package sort;
import java.util.Random;
import java.util.Scanner;
public class SortsDriver {


    public static void main(String[] args) {
      // TODO
      Scanner input1 = new Scanner(System.in);
      Scanner input2 = new Scanner(System.in);
      System.out.println("Please enter the type of sort method you wish to use: ");
      System.out.print("(i, q, m, r, or a for all)");
      String sortType = input2.nextLine();
      System.out.print("Please enter the size of the array: ");
      int arraySize = input1.nextInt();
      int[] A = new int[arraySize];
      
      for (int i=0;i<A.length; i++) { 
        Random random=new Random();
        int n= random.nextInt(-arraySize,arraySize+1);
        A[i]=n;
      }
      int B[] = A.clone();
      int C[] = A.clone();
      int D[] = A.clone();
      int[] E = {-10,-3,4,4,-6,0,8,2,0,1};
      if((sortType.equals("a"))){
        sortRequest(A,"i");
        sortRequest(B,"q");
        sortRequest(C,"m");
        sortRequest(D,"r");
      }
      else{sortRequest(A,sortType);}   
    }
    /* This meathod takes in the user's input and does the appropriate sorting operation based on input
    * */
    public static void sortRequest(int[] A, String sortType){
      Sorts sorts = new Sorts();
      switch(sortType){
        case "i":  sorts.print(A,false);
        sorts.insertionSort(A,0,A.length);
        sorts.print(A,true);
        System.out.println("Comparissons: "+sorts.getComparisonCount());
        sorts.resetComparisonCount();
        break;
        case "q":  sorts.print(A,false);
        sorts.quickSort(A,0,A.length);
        sorts.print(A,true);
        System.out.println("Comparissons: "+sorts.getComparisonCount());
        sorts.resetComparisonCount();
        break;
        case "m":  sorts.print(A,false);
        sorts.mergeSort(A,0,A.length);
        sorts.print(A,true);
        System.out.println("Comparissons: "+sorts.getComparisonCount());
        sorts.resetComparisonCount();
        break;
        case "r":  sorts.print(A,false);
        sorts.radixSort(A);
        sorts.print(A,true);
        System.out.println("Comparissons: "+sorts.getComparisonCount());
        sorts.resetComparisonCount();
        break;
      }
    }
}
