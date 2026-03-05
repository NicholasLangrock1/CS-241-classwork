/* Author:Nicholas Langrock
 * Date: 04/22/2022
 * Description: This is is my submission for the a1 project in 241, where our goal was to create four different sorting algorithms, and
 * Have them successfully sort unsorted arrays.
 * */

package sort;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.lang.Math;

public class Sorts {

   // maintains a count of comparisons performed by this Sorts object
  private int comparisonCount;

  public int getComparisonCount() {
    return comparisonCount;
  }

  public void resetComparisonCount() {
    comparisonCount = 0;
  }

  /** Sorts A[start..end] in place using insertion sort
    * Precondition: 0 <= start <= end <= A.length */
  public void insertionSort(int[] A, int start, int end) {
    // TODO
    
    if(end<=A.length&&start<A.length&&start>=0){
      for (int i = start; i < end; i++){
          for(int j=i; j>=start;j--){
            if(!(j==start)){
              if(A[j-1]>A[j]){
                int temp=A[j];
                A[j]=A[j-1];
                A[j-1]=temp;
                comparisonCount++;
              }
              else{break;}
            }
          }
        }
      }
    }


  /** Partitions A[start..end] around the pivot A[pivIndex]; returns the
   *  pivot's new index.
   *  Precondition: start <= pivIndex < end
   *  Postcondition: If partition returns i, then
   *  A[start..i] <= A[i] <= A[i+1..end] 
   **/
  public int partition(int[] A, int start, int end, int pivIndex) {
    int newIndex=start;
    int j=start;
    int[] newA;
    newA = new int[end];
    for(int i = start;i<end;i++){//appends all values smaller than pivot index to the indices before pivot
      if(!(A[pivIndex]<=A[i])){
        newA[j]=A[i];
        j++;
        comparisonCount++;
      }
    }
    
    newIndex=j;//sets the new pivotindex
    
    newA[newIndex]=A[pivIndex];
    for(int i = start;i<end;i++){//appends all values larger than pivot index to the indices after pivot
      if(i!=pivIndex&&A[i]>=A[pivIndex]){
        j++;
        newA[j]=A[i];
        comparisonCount++;
      }
    }
    arraySetTo(A, newA, start, end);
    return newIndex;
    
  }

  /** use quicksort to sort the subarray A[start..end] */
  public void quickSort(int[] A, int start, int end) {
    // TODO
    if (!(start >= end)){
      
      int pivIndex = partition(A,start, end,end-1); 
      quickSort(A,start, pivIndex); 
      quickSort(A,pivIndex+1, end); 
    }
   
  }

  /** merge the sorted subarrays A[start..mid] and A[mid..end] into
   *  a single sorted array in A. */
  public void merge(int[] A, int start, int mid, int end) {
      // TODO
      int[] newA;
      newA = new int[end];
      int left=start;
      int right=mid;
      for(int i=start;i<end;i++){
        if(left!=mid&&right!=end){//if neither are empty, then it will compare values for the smaller one and append smaller one
          if(A[left]<A[right]){
            newA[i]=A[left];
            left++;
            comparisonCount++;
          }
          else if(A[left]>=A[right]){
            newA[i]=A[right];
            right++;
            comparisonCount++;
          }
        }//if left or right are empty, then it will append the other onto the array
        else if(left<mid){
          newA[i]=A[left];
            left++;
            comparisonCount++;
        }
        else if(right<end){
          newA[i]=A[right];
            right++;
            comparisonCount++;
        }
      }
      arraySetTo(A, newA, start, end);
  }

  /** use mergesort to sort the subarray A[start..end] */
  public void mergeSort(int[] A, int start, int end) {
    // TODO
    int mid=(end+start)/2;
    if(end-start<2){
      return;
    }
    mergeSort(A,start,mid);
    mergeSort(A,mid,end);
    merge(A, start, mid, end);
  }

  /** Sort A using LSD radix sort. Uses counting sort to sort on each digit
   * It does so by first getting the largest absolute value of a number, then getting number of digits of said number
   * then it puts the elements in A into queues based off of the digits of largest num, then
   * empties those queues onto the array in digit order, and then repeats until it gets through the 
   * largest digit
   * The are dumped in opposite order for negative digits***
   * 
  */
  public void radixSort(int[] A) {
    // TODO
    ArrayList<LinkedList<Integer>> buckets = new ArrayList<LinkedList<Integer>>(18);
    for (int i = 0; i < 21; i++) {
      buckets.add(new LinkedList<Integer>());
    }
    int largestNum=A[0];
    int numOfDigits=1;
    int currentHead=0;
    for(int i=1;i<A.length;i++){
      if(Math.abs(A[i])>Math.abs(largestNum)){
        largestNum=A[i];
      }
    }
    while(largestNum/10!=0){
      largestNum=largestNum/10;
      numOfDigits++;
      }
      for(int i=0;i<numOfDigits;i++){
        currentHead=0;
        for(int j=0;j<A.length;j++){
          if(A[j]<0){
            buckets.get(getDigit(A[j],i)+1).add(A[j]);
          }
          if(A[j]>=0){
            buckets.get(getDigit(A[j],i)+11).add(A[j]);
        }
      }
      for (int k = 10; k > 0; k--){
        currentHead=queueEmptyToArray(A,buckets.get(k),currentHead,buckets.get(k).size());
      }
      for (int k = 11; k < 21; k++){
        currentHead=queueEmptyToArray(A,buckets.get(k),currentHead,buckets.get(k).size());
      }
    }
  }
    

  /* return the 10^d's place digit of n */
  private int getDigit(int n, int d) {
    return Math.abs((n / ((int)Math.pow(10, d))) % 10);
  }

  /** swap a[i] and a[j]
   *  pre: 0 <= i, j < a.size
   *  post: values in a[i] and a[j] are swapped */
  public void swap(int[] a, int i, int j) {
    int tmp = a[i];
    a[i] = a[j];
    a[j] = tmp;
  }

  /** Sets an array equal to another array, for each element specified from start to end
   * pre: arrays are the same length
   * post: all values of A will equal all values of newA in order
   */
  public void arraySetTo(int[] A,int[] newA,int start,int end){
    for(int i =start;i<end;i++){
      A[i]=newA[i];
    }
  }
  /** Empties an array onto the array
   * pre: array & queue are of their apropriate types, and size is the length of q
   * post: empties queue onto array and returns the new head for array
  */
  public int queueEmptyToArray(int[] A,Queue<Integer> q, int currentHead,int size){
    for(int j=0;j<size;j++){
      A[currentHead]=q.remove();
      currentHead++;
    }
    return currentHead;
  }
 /** Prints out A
   * pre: A is of type int[]
   * post: every element of A is printed out
  */
  public void print(int[] A,boolean fin){
    
  
    if(A.length<=20){
      if(fin==false){
        System.out.print("unsorted ");
      }
      else if(fin==true){
        System.out.print("sorted ");
      }
      System.out.print("[");
    for (int element: A) {
      System.out.print(element+", ");
      }
      System.out.println("]");
    }
  }
}
