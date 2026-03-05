package avl;
 /*Nicholas Langrock 05/09/2022
 This package either creates BSTs or AVL trees, and has all of the methods that add or remove nodes, rebalances and is able to left or right rotate.
 */

public class AVL {

  public Node root;

  private int size;

  public int getSize() {
    return size;
  }

  /** find w in the tree. return the node containing w or
  * null if not found */
  public Node search(String w) {
    return search(root, w);
  }
  private Node search(Node n, String w) {
    if (n == null) {
      return null;
    }
    if (w.equals(n.word)) {
      return n;
    } else if (w.compareTo(n.word) < 0) {
      return search(n.left, w);
    } else {
      return search(n.right, w);
    }
  }

  /** insert w into the tree as a standard BST, ignoring balance */
  public void bstInsert(String w) {
    if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    bstInsert(root, w);
  }

  /* insert w into the tree rooted at n, ignoring balance
   * pre: n is not null */
  private void bstInsert(Node n, String w) {
    // TODO
    if(n!=null){
      
      if(w.compareTo(n.word) < 0){
        if(n.left!=null){bstInsert(n.left,w);}
        else {n.left=new Node(w);size++;n.left.parent=n;}
      }
      else if(w.compareTo(n.word) > 0){
        if(n.right!=null){bstInsert(n.right,w);}
        else {n.right=new Node(w);size++;n.right.parent=n;}
      }
    }
  }

  /** insert w into the tree, maintaining AVL balance
  *  precondition: the tree is AVL balanced and any prior insertions have been
  *  performed by this method. */
  public void avlInsert(String w) {
    // TODO
    if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    avlInsert(root, w);
  
  }

  /* insert w into the tree, maintaining AVL balance
   *  precondition: the tree is AVL balanced and n is not null */
  private void avlInsert(Node n, String w) {
    // TODO
    if(n!=null){
      
      if(w.compareTo(n.word) < 0){
        if(n.left!=null){avlInsert(n.left,w);}
        else {n.left=new Node(w);size++;n.left.parent=n; }
      }
      else if(w.compareTo(n.word) > 0){
        if(n.right!=null){avlInsert(n.right,w);}
        else {n.right=new Node(w);size++;n.right.parent=n; }
      }
    }
    rebalance(n);
  }

  /** do a left rotation: rotate on the edge from x to its right child.
  *  precondition: x has a non-null right child */
  public void leftRotate(Node x) {

    Node y = x.right;
    Node newxright = y.left;
    x.right=newxright;//x.right now equals newxright
    if(y.left!=null){
      newxright.parent=x;}//parent now equals x

    y.parent=x.parent; //parent now equals x.parent
    if(x.parent==null){
      root=y;
    }
    else if(x==x.parent.left){
      x.parent.left=y; //x = y
    }
    else{
      x.parent.right=y; //x=y
    }
    y.left=x; //y.left now equals x
    x.parent=y; //xs parent =y
  }

  /** do a right rotation: rotate on the edge from x to its left child.
  *  precondition: y has a non-null left child */
  public void rightRotate(Node y) {
    // TODO
    Node x = y.left;
    Node newyleft = x.right;
    y.left=newyleft;//opposite logic applied to leftRotate (seen above) for all steps*
    if(x.right!=null){
      newyleft.parent=y;
    }
    x.parent=y.parent;
    if(y.parent==null){
      root=x;
    }
    else if(y==y.parent.right){
      y.parent.right=x;
    }
    else{
      y.parent.left=x;
    }
    x.right=y;
    y.parent=x;

    
  }

  /** rebalance a node N after a potentially AVL-violoting insertion.
  *  precondition: none of n's descendants violates the AVL property */
  public void rebalance(Node n) {
    // TODO
    int balFactor = getBal(n);
    int leftbalFactor=(getBal(n.left));
    int rightbalFactor=(getBal(n.right));

    if (balFactor > 1) {
      if (leftbalFactor >= 0) {
        rightRotate(n);
      } else {
        leftRotate(n.left);
        rightRotate(n);
      }
    }
  
    if (balFactor < -1) {
      if (rightbalFactor<=0) {
        leftRotate(n);
      } else {
        rightRotate(n.right);
        leftRotate(n);
      }
    }
    Node findroot=getRoot(n);
    updateAllChildHeight(findroot);
  }

  /** remove the word w from the tree */
  public void remove(String w) {
    remove(root, w);
  }

  /* remove w from the tree rooted at n */
  private void remove(Node n, String w) {
    return; // (enhancement TODO - do the base assignment first)
  }

  /** print a sideways representation of the tree - root at left,
  * right is up, left is down. */
  public void printTree() {
    printSubtree(root, 0);
  }
  private void printSubtree(Node n, int level) {
    if (n == null) {
      return;
    }
    printSubtree(n.right, level + 1);
    for (int i = 0; i < level; i++) {
      System.out.print("        ");
    }
    System.out.println(n);
    printSubtree(n.left, level + 1);
  }

  /** inner class representing a node in the tree. */
  public class Node {
    public String word;
    public Node parent;
    public Node left;
    public Node right;
    public int height;

    public String toString() {
      return word + "(" + height + ")";
    }

    /** constructor: gives default values to all fields */
    public Node() { }

    /** constructor: sets only word */
    public Node(String w) {
      word = w;
    }

    /** constructor: sets word and parent fields */
    public Node(String w, Node p) {
      word = w;
      parent = p;
    }

    /** constructor: sets all fields */
    public Node(String w, Node p, Node l, Node r) {
      word = w;
      parent = p;
      left = l;
      right = r;
    }
  }
  /*this method gets the balance of a balance at a specific node.
  if the left side is larger, it will be positive, if right side is larger, it will be negative/*/
  int getBal(Node n) {
    if (n == null)
        return -1;
    else if(n.left==null&&n.right==null){
      return 0;
    }
    else if(n.left==null){
      return -1-n.right.height;
    }
    else if(n.right==null){

      return n.left.height+1;
      
    }
    return n.left.height - n.right.height;
  }
  /*this method calculates the height of a specific node*/
  int calcHeight(Node n){
    if(n==null){
      return -1;
    }
    if(n.left!=null&&n.right!=null){
        return Math.max(calcHeight(n.right),calcHeight(n.left))+1;
    }
    else if(n.left==null&&n.right==null){
        return 0;
    }
    else if(n.left==null){
      return calcHeight(n.right)+1;
    }
    else if(n.right==null){
        return calcHeight(n.left)+1;
    }
    else{return 0;}
  }
  Node getRoot(Node n){
    if (n.parent!=null){n=getRoot(n.parent);}
      return n;

  }/*this method updates the height of every single node in avl tree*/
  void updateAllChildHeight(Node n){
    if (n.left!=null){updateAllChildHeight(n.left);}
    if (n.right!=null){updateAllChildHeight(n.right);}
      n.height=calcHeight(n);
    }
    
}
