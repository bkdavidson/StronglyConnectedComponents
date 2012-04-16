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
    private int finishingTime;
    private Node deepNode;
    
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
    
    private void resetExploration(){
        Iterator<Node> itr = nodes.iterator();
        while (itr.hasNext()){
            itr.next().explored= false;
        }
        Iterator<Edge> itr2 = edges.iterator();
        while(itr2.hasNext()){
            itr2.next().traveled = false;
        }
    }
    
    private void dfsLoop(){
        finishingTime = 0; // number of nodes processed
        deepNode = null; // for the leader in 2nd pass
        Iterator<Node> itr = nodes.iterator();
        while(itr.hasNext()){
            Node i = itr.next();
            if (i.explored == false){
                deepNode = i;//then do DFS
                dfs(deepNode);
            }
        }
    }
    
    private void dfs(Node aNode){
        aNode.explored = true;
        deepNode = aNode;
        Iterator<Edge> itr = aNode.myEdges.iterator();
        while(itr.hasNext()){
            Node toNode = itr.next().toNode();
                if (toNode.explored == false)
                    dfs(toNode);
        }
        finishingTime++;
        aNode.value = finishingTime;
    }
    
    private class Node{
        public Graph mygraph;
        public int name;
        public int value;
        public ArrayList<Edge> myEdges;
        public boolean explored;
        
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
        public boolean traveled;
        
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
