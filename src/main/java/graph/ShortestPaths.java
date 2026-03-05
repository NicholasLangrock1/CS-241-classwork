package graph;
/** Author: Nicholas Langrock
 * Date 6/1/2022
 * Purpose: using dijkstras algorithm, I'm able to find the shortest path for each node
 * find the distance travelled for each node, and for each particular node (given that its reachable from origin),
 * print out each node in the path from origin to said node
 */
import heap.Heap;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;

/** Provides an implementation of Dijkstra's single-source shortest paths
 * algorithm.
 * Sample usage:
 *   Graph g = // create your graph
 *   ShortestPaths sp = new ShortestPaths();
 *   Node a = g.getNode("A");
 *   sp.compute(a);
 *   Node b = g.getNode("B");
 *   LinkedList<Node> abPath = sp.getShortestPath(b);
 *   double abPathLength = sp.getShortestPathLength(b);
 *   */
public class ShortestPaths {
    // stores auxiliary data associated with each node for the shortest
    // paths computation:
    private HashMap<Node,PathData> paths;
    private Heap<Node, Double> settledHeap =new Heap();
    private Heap<Node, Double> frontierHeap =new Heap();
    //private Heap<V,P extends Comparable<P>> settled;




    private Boolean isNodeReachable=false;

    public HashMap<Node,PathData> getPaths(){
        return paths;
    }
    /** Compute the shortest path to all nodes from origin using Dijkstra's
     * algorithm. Fill in the paths field, which associates each Node with its
     * PathData record, storing total distance from the source, and the
     * backpointer to the previous node on the shortest path.
     * Precondition: origin is a node in the Graph.*/
    // TODO 1: implement Dijkstra's algorithm to fill paths with
        // shortest-path data for each Node reachable from origin.
        /*shortest_paths(v):
        S = { };
        F = {v};
        v.d = 0;
        v.bp = null;
        while  (F != {})  {
            f = node in F with min d value;
            Remove f from F, add it to S;
            for each neighbor w of f {
            if (w not in S or F) {
                w.d =  f.d + weight(f, w);
                w.bp = f;
                add w to F;
            } else if (f.d + weight(f,w) < w.d) {
                w.d = f.d + weight(f,w);
                w.bp = f;
            }
            }


         //current.getNeighbor()i is playholder syntax for "get the ith neighbor Node of current"
        //current.getNeighbor()@i is playholder syntax "for get the weight (aka distance between) ith neighbor Node and current"

        //Heap<V,P extends Comparable<P>> settled=new Heap<V,P extends Comparable<P>>;
        //Heap<V, P> frontier=new Heap<V, P>;
        }*/




    public void compute(Node origin) {
        paths = new HashMap<Node,PathData>();
        paths.put(origin,new PathData(0,null));
        
        frontierHeap.add(origin,0.0);
        while(frontierHeap.size()>0){
            //removes the node with shortest distance from frontier and adds it to settled
            Node current=frontierHeap.poll();
            settledHeap.add(current,paths.get(current).distance);

            current.getNeighbors().forEach(
            (key, value)
                    -> computeNeighbors(current, key, value));
        }
        
    }
    /**
     * I use this method to iterate through all neighbors of a given node in question.
     * If the node is in neither settled or frontier heap, it will add the node to the paths hashtable, and will set the distance
     * equal to the distance of current node+ the weight between the two nodes in question.
     * the back pointer will also be set to current
     * 
     * however if the node is already in one of the heaps, and we find a *shorter* distance to it, we will set it equal to the 
     * new distance of current node+ the weight between the two nodes in question.
     * the back pointer will also be set to current
     */
    public void computeNeighbors(Node current, Node key, Double value){
        if(settledHeap.contains(key)==false&&frontierHeap.contains(key)==false){
                
            paths.put(key, new PathData(paths.get(current).distance+value,current));
            frontierHeap.add(key,value);
            
            
            }
            else if (paths.get(current).distance + value < paths.get(key).distance) {
                paths.put(key,new PathData(paths.get(current).distance+value,current));
            }
    }

    

    /** Returns the length of the shortest path from the origin to destination.
     * If no path exists, return Double.POSITIVE_INFINITY.
     * Precondition: destination is a node in the graph, and compute(origin)
     * has been called. */
    public double shortestPathLength(Node destination) {
        // TODO 2 - implement this method to fetch the shortest path length
        // from the paths data computed by Dijkstra's algorithm.
        
        if(testIsNodeReachable(destination)==false){
            return Double.POSITIVE_INFINITY;
        }
        return(paths.get(destination).distance);
        
    }

    /** Returns a LinkedList of the nodes along the shortest path from origin
     * to destination. This path includes the origin and destination. If origin
     * and destination are the same node, it is included only once.
     * If no path to it exists, return null.
     * Precondition: destination is a node in the graph, and compute(origin)
     * has been called. */
    public LinkedList<Node> shortestPath(Node destination) {

        if(testIsNodeReachable(destination)==false){
            return null;
        }
        

        // TODO 3 - implement this method to reconstruct sequence of Nodes
        // along the shortest path from the origin to destination using the
        // paths data computed by Dijkstra's algorithm.
        //throw new UnsupportedOperationException();
        

        //does recursive calls through a linked list of nodes until it finds origin,
        //afterwards it prints out said linked list starting with origin

        LinkedList<Node> shortestPathOfNodes = new LinkedList<Node>();
        shortestPathGenerator(destination, shortestPathOfNodes);
        return shortestPathOfNodes;
    }
    public LinkedList<Node> shortestPathGenerator(Node destination, LinkedList<Node> shortestPathOfNodes){
        if(paths.get(destination).previous!=null){
            
            shortestPathGenerator(paths.get(destination).previous, shortestPathOfNodes);
        }
        shortestPathOfNodes.add(destination);
        return(shortestPathOfNodes);
    }


    /** Inner class representing data used by Dijkstra's algorithm in the
     * process of computing shortest paths from a given source node. */
    class PathData {
        double distance; // distance of the shortest path from source
        Node previous; // previous node in the path from the source

        /** constructor: initialize distance and previous node */
        public PathData(double dist, Node prev) {
            distance = dist;
            previous = prev;
        }
    }


    /** Static helper method to open and parse a file containing graph
     * information. Can parse either a basic file or a DB1B CSV file with
     * flight data. See GraphParser, BasicParser, and DB1BParser for more.*/
    protected static Graph parseGraph(String fileType, String fileName) throws
        FileNotFoundException {
        // create an appropriate parser for the given file type
        GraphParser parser;
        if (fileType.equals("basic")) {
            parser = new BasicParser();
        } else if (fileType.equals("db1b")) {
            parser = new DB1BParser();
        } else {
            throw new IllegalArgumentException(
                    "Unsupported file type: " + fileType);
        }

        // open the given file
        
        parser.open(new File(fileName));

        // parse the file and return the graph
        return parser.parse();
    }

    public static void main(String[] args) {
      // read command line args
      String fileType = args[0];
      String fileName = args[1];
      String origCode = args[2];

      String destCode = null;
      if (args.length == 4) {
          destCode = args[3];
      }

      // parse a graph with the given type and filename
      Graph graph;
      try {
          graph = parseGraph(fileType, fileName);
      } catch (FileNotFoundException e) {
          System.out.println("Could not open file " + fileName);
          return;
      }
      graph.report();


      // TODO 4: create a ShortestPaths object, use it to compute shortest
      // paths data from the origin node given by origCode.
      ShortestPaths shortestPaths = new ShortestPaths();
      shortestPaths.compute(graph.getNode(origCode));
      // TODO 5:
      // If destCode was not given, print each reachable node followed by the
      // length of the shortest path to it from the origin.
    
      //using lambdas again to iterate through a linked list through all of its elements, with it
      //printing out each element
      if(destCode==null){
        shortestPaths.getPaths().forEach(
            (key, value)
                    -> System.out.println("node: "+key+" dist from start: "+value.distance+" prev node: "+value.previous));
      }
      // TODO 6:
      // If destCode was given, print the nodes in the path from
      // origCode to destCode, followed by the total path length
      // If no path exists, print a message saying so.
      if(destCode!=null){
          if(shortestPaths.shortestPath(graph.getNode(destCode))==null){
              System.out.println("There is no path from origin to particular node");
              System.out.println("Distance is: "+shortestPaths.shortestPathLength((graph.getNode(destCode))));
          }
          else{
            //shortestPaths.shortestPathOfNodes = new LinkedList<Node>();
            shortestPaths.shortestPath(graph.getNode(destCode)).forEach(
                (node)
                        -> System.out.print("node: "+node+"--> "));
            System.out.println(" total path length: "+shortestPaths.getPaths().get(graph.getNode(destCode)).distance);
            }
        
        }
    }

    //I use this method to test to see if a node is reachable to begin with*

    public Boolean testIsNodeReachable(Node endNode){
        if(paths.get(endNode)!=null){
            return true;
        }
        else{
            return false;
        }
        /*shortestPath(g.getNode(endNode)).forEach(
                (node)
                        -> {if (startNode.equals(node)){
                            isNodeReachable=true;
                        }});
            return isNodeReachable;*/
    }


}