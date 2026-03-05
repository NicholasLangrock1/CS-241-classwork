/**
 * Authors: Nicholas Langrock, Elias Medley
 * Date: 6-1-2022
 * Purpose: Turn text file into encoding tree
 */
package lab7;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.lang.StringBuilder;

import heap.Heap;
public class Huffman {
    public static void main(String[] args) {
        huffmanTree h = new huffmanTree();
        StringBuilder word = new StringBuilder();

        if (args.length != 1) {
            System.out.println("requires 1 file");
            return;
          }
          try {
              File f = new File(args[0]);
              Scanner sc = new Scanner(f);
              while(sc.hasNextLine()){
                word = word.append(sc.nextLine());
              }
              sc.close();

          } catch (FileNotFoundException exc) {
              System.out.println("Could not find file " + args[0]);
          }
        String input = word.toString();
        h.countFrequency(input);
        h.makeNodes();
        h.makeTree();
        String encoded = h.encode(input);
        if(word.length() < 100){
            System.out.println(input);
            System.out.println(encoded);
        }
        String decoded = h.decode(encoded);
        if(decoded.equals(input)){
            System.out.println("It worked");
        }
        else{
            System.out.println("uh oh");
        }
        System.out.println("compression ratio is: " + (double)encoded.length()/(double)input.length()/8.0);
    }
}

class huffmanTree{
    HashTable<Character, Integer> freqTable = new HashTable<Character, Integer>();
    ArrayList<Character> hashkeys = new ArrayList<Character>();
    Heap<node, Integer> nodeList = new Heap<node, Integer>();
    HashTable<Character, String> encodingTable = new HashTable<Character, String>();

    /**
     * makes a table of the frequency of each character
     * @param s
     */
    public void countFrequency (String s){
        for(int i = 0; i < s.length(); i++){
            if(freqTable.containsKey(s.charAt(i))){
                freqTable.put(s.charAt(i), freqTable.get(s.charAt(i))+1);
            }
            else{
                hashkeys.add(s.charAt(i));
                freqTable.put(s.charAt(i), 1);
            }
        }
    }
    /**
     * for each character in the frequency table make a node containing that character and its frequency
     */
    public void makeNodes(){
        for (int i = 0; i < hashkeys.size(); i++) {
            nodeList.add(new node(hashkeys.get(i), freqTable.get(hashkeys.get(i))), freqTable.get(hashkeys.get(i)));
        }
    }
    /**
     * organize the nodelist into a tree
     */
    public void makeTree(){
        while(nodeList.size() > 1){
            node lChild = nodeList.poll();
            recursiveNode("0", lChild);
            node rChild = nodeList.poll();
            recursiveNode("1", rChild);
            node temp = new node(lChild.frequency + rChild.frequency, lChild, rChild);
            nodeList.add(temp, temp.frequency);
        }
    }
    /**
     * Keeps encoding table up to date as we build the tree.
     * @param LorR
     * @param n
     */
    public void recursiveNode(String LorR, node n){
        if(n.letter != null){
            //if the node is a leaf, update the encoding table
            if(encodingTable.containsKey(n.letter)){
                encodingTable.put(n.letter, LorR.concat(encodingTable.get(n.letter)));
            }
            else{
                encodingTable.put(n.letter, LorR);
            }
        }
        else{
            recursiveNode(LorR, n.left);
            recursiveNode(LorR, n.right);
        }
    }
/**
 * precon: s is only made of characters in encodingTable
 * @param s
 * @return encoded bitstring of s
 */
    public String encode(String s){
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < s.length(); i++){
            String add = encodingTable.get(s.charAt(i));
            output.append(add);
        }
        return output.toString();
    }

    /**
     * precon: s is an encoded bitstring
     * @param s
     * @return decoded s
     */
    public String decode(String s){
        StringBuilder output = new StringBuilder();
        node current = nodeList.peek();
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '0'){
                current = current.left;
            }
            else if(s.charAt(i) == '1'){
                current = current.right;
            }
            if(current.letter !=null){
                output.append(current.letter.toString());
                current = nodeList.peek();
            }
        }
        return output.toString();
    }

    //modified a2 printTree
    public void printTree() {
        printSubtree(nodeList.peek(), 0);
      }
      private void printSubtree(node n, int level) {
        if (n == null) {
          return;
        }
        printSubtree(n.right, level + 1);
        for (int i = 0; i < level; i++) {
          System.out.print("        ");
        }
        if(n.letter != null){
            if(n.letter != ' '){
                System.out.println(n.letter+": "+n.frequency);
            }
            else{
                System.out.println("_: "+n.frequency);
            }
        }
        else{
            System.out.println("@: "+n.frequency);
        }
        printSubtree(n.left, level + 1);
      }
}
class node{
    Character letter;
    int frequency;
    node left;
    node right;
    boolean isLeft = false;
    boolean isRight = false;

    /**
     * makes a node given a character and its freq
     * @param c
     * @param f
     */
    public node (Character c, int f){
        frequency =f;
        letter = c;
    }
    /**
     * makes a node with 2 children and the sum freq of its children
     * @param f
     * @param l
     * @param r
     */
    public node(int f, node l, node r){
        frequency = f;
        left = l;
        right = r;
    }
}
