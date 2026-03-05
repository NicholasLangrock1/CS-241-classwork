import java.util.*;

class SortingPack {
    // just in case you need tis method for testing
    public static void main(String[] args) {
        // something
    }

    // implementation of insertion sort
    // parameters: int array arr
    // return: the input array

    //pretty self explaninary, but it itererates through array, and compares each element to each element before it.
    //and swap it if it's in the wrong place
    public static int[] insertionSort(int[] arr) {
        // keep this line
        for (int i=0; i<arr.length; i++){
            for (int j=i; j>0; j--){
                if((arr[j-1]>arr[j])){
                    int temp=arr[j-1];
                    arr[j-1]=arr[j];
                    arr[j]=temp;
                }
            }
        }
        return arr;
    }
            

    // implementation of quick sort
    // parameters: int array arr
    // return: the input array

    public static int[] quickSort(int[] arr) {
        // keep this line
        arr = quickSort(arr,arr.length,0);
        return arr;
    }
    //quicksort sorts an array, finds a pivot, sorts arrays based off of which half is smaller
    //and the proccess is done over and over for each sub array using recurssion
    public static int[] quickSort(int[] arr, int partition,int start) {
        if (start!=partition){
        int pivot=arr[partition-1];
        int bottomTracker=start;
        for (int i=start; i<partition; i++){
            if(arr[i]<=pivot){
                int temp=arr[bottomTracker];
                arr[bottomTracker]=arr[i];
                arr[i]=temp;
                bottomTracker++; 
            }
        }
        quickSort(arr,bottomTracker-1,start);
        quickSort(arr,partition,bottomTracker);
    }
    return arr;
    }

    // implementation of merge sort
    // parameters: int array arr
    // return: sorted int array
    public static int[] mergeSort(int[] arr) {
        // it's up to you what to return
        // you can remove this line depending on your implementation
        arr=mergeSort(arr,0,arr.length);
        return arr;
    }
    public static int[] mergeSort(int[] arr,int start, int end) {
        // it's up to you what to return
        // you can remove this line depending on your implementation

        //base case is that the end is only 1 greater than start
        int partition=(start+end)/2;
        int[] sortedArr = new int[arr.length];
        if(!((end-start)<=1)){

            //nonbase case sub arrays are of length greater than 1

            //subdivides it into 2 smaller arrays
            //for each saves a head pointing at the left array and the right array
            //and the logic esentially sorts the arrays into one array (stored not in place). It also
            //deals with cases where one array sorts faster than the other
            //and it does this for each iteration. So this means that we have nlogn steps because we do this iteration logn times
            mergeSort(arr,start,partition);
            mergeSort(arr,partition,end);
            int leftHead=start;
            int rightHead=partition;
            for(int i=start; i<end; i++){
                if(((rightHead<end)&&(leftHead<partition))){
                    if(arr[leftHead]<arr[rightHead]){
                        sortedArr[i]=arr[leftHead];
                        leftHead++;
                    }
                    else{
                        sortedArr[i]=arr[rightHead];
                        rightHead++;
                    }
                }
                else if(rightHead<end){
                    sortedArr[i]=arr[rightHead];
                        rightHead++;
                }
                else{
                    sortedArr[i]=arr[leftHead];
                        leftHead++;
                }
            }
            for(int i=start; i<end; i++){
                arr[i]=sortedArr[i];
            }
        }
        return arr;
    }
    // you are welcome to add any supporting methods
}
