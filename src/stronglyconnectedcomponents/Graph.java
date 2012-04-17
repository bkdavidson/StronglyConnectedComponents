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
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private Direction adirection = Direction.left;
    private int finishingTime;
    private Node deepNode;
    private Map<Integer, Node> nodeExists = new HashMap<Integer, Node>();
    //private ArrayList<int[]> intArray;

    public Graph(ArrayList<int[]> intArray){
        //this.intArray = intArray;
        populate(intArray);
    }

    private void populate(ArrayList<int[]> intArray){
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
        //populate();
        Iterator<Node> itr = nodes.iterator();
        while (itr.hasNext())
            itr.next().reset();
    }

    private Stack dfsLoop(boolean SCC, Stack<Node> aStack){ //boolean checks whether or not to make SCC list
        finishingTime = 0; // number of nodes processed
        deepNode = null; // for the leader in 2nd pass
        Stack<Node> otherStack = new Stack();
        while(!aStack.isEmpty()){
            Node aNode = aStack.pop();
            if (aNode.myEdges.size()>0 && aNode.explored == false){
                deepNode = aNode;
                otherStack.push(aNode);
                dfs(deepNode);
            }
            else{
                aNode.explored = true;
                aNode.value = finishingTime;
                finishingTime++;
            }
        }
        if (SCC = true) {
            Collections.reverse(otherStack);
            Iterator<Node> itr = otherStack.iterator();
            int oldaccumulator = 0;
            int currentaccumulator = 0;
            aStack = new Stack();
            while (itr.hasNext()) {
                Node aNode = itr.next();
                oldaccumulator = currentaccumulator;
                currentaccumulator = aNode.value;
                aNode.value = aNode.value - oldaccumulator;
                aStack.push(aNode);
            }
            Collections.sort(aStack);
            Collections.reverse(aStack);
            return aStack;
        }
        else
            return otherStack;
    }
    public void Kosaraju(){
           toggleDirection();
           Stack<Node> aStack = stacker();
           dfsLoop(false, aStack);
           aStack = stacker();
           resetExploration();
           toggleDirection();
           aStack = dfsLoop(true, aStack);
           Collections.sort(aStack);
           Collections.reverse(aStack);
           for(int i = 0; i <5; i++)
               System.out.println(aStack.pop().value);
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
        deepNode = aNode;
        aNode.explored = true;
        for(int i = 0; i < aNode.myEdges.size(); i++){
            Node toNode = aNode.myEdges.get(i).toNode();
            if (toNode != aNode && toNode.explored == false && toNode.myEdges.size() > 0) {
                dfs(toNode);
            } else {
                toNode.explored = true;
                toNode.value = finishingTime;
                finishingTime++;
            }
            
        }
        finishingTime++;
        aNode.value = finishingTime;
    }

    private class Node implements Comparable<Node>{
        public Graph mygraph;
        public int name;
        public int value;
        public ArrayList<Edge> myEdges;
        //public ArrayList<Edge> unExplored;
        public boolean explored = false;

        public Node(int name, Graph agraph){
            mygraph = agraph;
            this.name = name;
            myEdges = new ArrayList<Edge>();
            //unExplored = new ArrayList<Edge>();
        }
        public void addEdge(Edge anEdge){
            myEdges.add(anEdge);
            //unExplored.add(anEdge);
        }
        public void reset(){
            //unExplored = new ArrayList<Edge>();
            //Iterator<Edge> itr = myEdges.iterator();
            explored = true;
            //while(itr.hasNext())
                //unExplored.add(itr.next());
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

//        public void explore(){
//            left.unExplored.remove(this);
//            right.unExplored.remove(this);
//        }

    }
    private enum Direction{left,right};
}