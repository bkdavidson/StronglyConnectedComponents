/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stronglyconnectedcomponents;

import java.util.*;

/**
 *
 * @author davidson_b
 */
public class Graph {
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();
    private Direction adirection = Direction.left;
    private int finishingTime;
    private Node deepNode;
    private Map<Integer, Node> nodeExists = new HashMap<>();
    
    public Graph(ArrayList<int[]> intArray){
        Iterator<int[]> itr = intArray.iterator();
        while(itr.hasNext()){
            int[] someInts = itr.next();
            Node left = this.addNode(someInts[0]);
            if (someInts[0] == someInts[1])
                this.addEdge(left, left);  
            else{
                Node right = this.addNode(someInts[1]);
                this.addEdge(left, right);  
            }
        }  
    }
    private Node addNode(int name){
        if (!nodeExists.containsKey(name)){
            Node aNode = new Node(name,this);
            nodes.add(aNode);
            nodeExists.put(aNode.name,aNode);
            return aNode;
        }
        return nodeExists.get(name);
    } 
    private Node getNode(int name){
        Node anode = null;
        if (nodes.size()> 0){
            if (nodeExists.containsKey(name))
                return nodeExists.get(name);
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
        left.addEdge(anEdge);
        right.addEdge(anEdge);
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
    
    private Stack dfsLoop(boolean SCC, Stack<Node> aStack){ //boolean checks whether or not to make SCC list
        finishingTime = 0; // number of nodes processed
        deepNode = null; // for the leader in 2nd pass
        if (SCC == false){
            Iterator<Node> itr = nodes.iterator();
            while(itr.hasNext()){
                Node i = itr.next();
                if (i.explored == false){
                    deepNode = i;//then do DFS
                    dfs(deepNode);
                }
            }
        }
        else if (SCC == true){
            Stack<Node> otherStack = new Stack();
            while(!aStack.isEmpty()){
                Node aNode = aStack.pop();
                if (aNode.explored == false)
                    deepNode = aNode;
                    otherStack.push(aNode);
                    dfs(deepNode);
            }
                Collections.reverse(otherStack);
                aStack = otherStack;
        }
        return aStack;
    }
    public void Kosaraju(){
           toggleDirection();
           dfsLoop(false, null);
           Stack<Node> aStack = stacker();
           resetExploration();
           toggleDirection();
           dfsLoop(true, aStack);
           
    }
    
    private Stack stacker(){
        Stack<Node> aStack = new Stack();
        Collections.sort(nodes);
        Collections.reverse(nodes);
        Iterator<Node> itr = nodes.iterator();
        while(itr.hasNext())
            
            aStack.add(itr.next());
        return aStack;
    }
    
    private void dfs(Node aNode){
        aNode.explored = true;
        deepNode = aNode;
        Iterator<Edge> itr = aNode.myEdges.iterator();
        while(itr.hasNext()){
            Node toNode = itr.next().toNode();
                if (toNode != aNode && toNode.explored == false)
                    dfs(toNode);
        }
        finishingTime++;
        aNode.value = finishingTime;
    }
    
    private class Node implements Comparable<Node>{
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

        @Override
        public int compareTo(Node aNode) {
            if (aNode.value > this.value)
                return 1;
            else if (aNode.value < this.value)
                return -1;
            else
                return 0;
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
