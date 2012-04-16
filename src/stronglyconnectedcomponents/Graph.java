/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stronglyconnectedcomponents;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author davidson_b
 */
public class Graph {
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private Direction adirection = Direction.left;
    
    public Graph(ArrayList<int[]> intArray){
        Iterator<int[]> itr = intArray.iterator();
        while(itr.hasNext()){
            int[] someInts = itr.next();
            Node left = this.addNode(someInts[0]);
            Node right = this.addNode(someInts[1]);
            this.addEdge(left, right);  
        }
        
    }
    public Node addNode(int name){
        if (getNode(name) == null){
            Node aNode = new Node(name,this);
            nodes.add(aNode);
            return aNode;
        }
        return null;
    } 
    private Node getNode(int name){
        Iterator<Node> itr = nodes.iterator();
        Node anode = null;
        while((anode == null && itr.hasNext()) || (anode.name != name && itr.hasNext())){ //while anode doesn't exist, or the name doesn't match keep going
            anode = itr.next();
            if (anode.name == name) 
                return anode;
            else
                anode = null;
        }
        return anode;
    }   
    private Direction getdirection(){
        return adirection;
    }
    public void toggleDirection(){
        if (adirection == Direction.left)
            adirection = Direction.right;
        else
            adirection = Direction.left;
    }
    private void addEdge(Node left,Node right){
        Edge anEdge = new Edge(left,right);
        edges.add(anEdge);
    }
    
    private class Node{
        public Graph mygraph;
        public int name;
        public int value;
        public ArrayList<Edge> myEdges;
        
        public Node(int name, Graph agraph){
            mygraph = agraph;
            this.name = name;
            myEdges = new ArrayList<>();
        }
        public void addEdge(Edge anEdge){
            myEdges.add(anEdge);
        }
    }
    
    private class Edge{
        private Node left;
        private Node right;
        private Graph mygraph;
        
        public Edge(Node left, Node right){
            this.left = left;
            this.right = right;
            mygraph = left.mygraph;
        }
        public Node startNode(){
            if (mygraph.getdirection() == Direction.left)
                return left;
            else if ((mygraph.getdirection() == Direction.right))
                return right;
            else
                return null;
        }
        public Node toNode(){
            if (mygraph.getdirection() == Direction.left)
                return right;
            else if ((mygraph.getdirection() == Direction.right))
                return left;
            else
                return null;
        }
    }
    private enum Direction{left,right};
}
