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

    public Graph(ArrayList<int[]> intArray) {
        //this.intArray = intArray;
        populate(intArray);
    }

    private void populate(ArrayList<int[]> intArray) {
        Iterator<int[]> itr = intArray.iterator();
        while (itr.hasNext()) {
            int[] someInts = itr.next();
            Node left = this.addNode(someInts[0]);
            if (someInts[0] == someInts[1]) {
                this.addEdge(left, left);
            } else {
                Node right = this.addNode(someInts[1]);
                this.addEdge(left, right);
            }
        }
    }

    private Node addNode(int name) {
        if (!nodeExists.containsKey(name)) {
            Node aNode = new Node(name, this);
            nodes.add(aNode);
            nodeExists.put(aNode.name, aNode);
            return aNode;
        }
        return nodeExists.get(name);
    }

    private Node getNode(int name) {
        Node anode = null;
        if (nodes.size() > 0) {
            if (nodeExists.containsKey(name)) {
                return nodeExists.get(name);
            }
        }
        return anode;
    }

    private Direction getdirection() {
        return adirection;
    }

    public void toggleDirection() {
        if (adirection == Direction.left) {
            adirection = Direction.right;
        } else {
            adirection = Direction.left;
        }
    }

    private void addEdge(Node left, Node right) {
        Edge anEdge = new Edge(left, right);
        edges.add(anEdge);
        left.addEdge(anEdge);
        right.addEdge(anEdge);
    }

    private void resetExploration() {
        //populate();
        Iterator<Node> itr = nodes.iterator();
        while (itr.hasNext()) {
            itr.next().reset();
        }
    }

    private Stack dfsLoop(boolean SCC) { //boolean checks whether or not to make SCC list
        finishingTime = 0; // number of nodes processed
        deepNode = null; // for the leader in 2nd pass
        Iterator<Node> itr = nodes.iterator();
        Stack<Node> otherStack = new Stack();
        while (itr.hasNext()) { //explore the stack
            Node aNode = itr.next();
            if (aNode.explored == false) { //if it is unexplored then call DFS
                deepNode = aNode;
                otherStack.push(aNode);
                dfs(deepNode);
            }
        }
        if (SCC == true) {
            Stack<Node> aStack = new Stack();
            while (!otherStack.isEmpty()) {
                Node aNode = otherStack.pop();
                aNode.value = aNode.SCC;
                aStack.push(aNode);
            }
            Collections.sort(aStack);
            Collections.reverse(aStack);
            return aStack;
        } else {
            return otherStack;
        }
    }

    public void Kosaraju() {
        toggleDirection(); //reverse direction
        Collections.sort(nodes); //sort the nodes by value
        Collections.reverse(nodes);
        //for (int i = 0; i < 20; i++)
          //  System.out.println(nodes.get(i).name);
        dfsLoop(false); //do DFS in reverse, false means no SCC calculations
        Collections.sort(nodes);
        resetExploration();
        toggleDirection();
        Stack<Node> aStack = dfsLoop(true);
        Collections.sort(aStack);
        Collections.reverse(aStack);
        int accum = 0;
        for(int i = 0; i < 5; i++){
            int t = aStack.pop().value;
            System.out.println(t);
            accum = accum+t;
        }
        System.out.println(accum);
    }

    private Stack stacker(boolean sort) {
        Stack<Node> aStack = new Stack();
        Collections.sort(nodes);
        if (sort == true) //For 2nd pass we want to reverse the order and go descending
        {
            Collections.reverse(nodes);
        }
        Iterator<Node> itr = nodes.iterator();
        while (itr.hasNext()) {
            aStack.add(itr.next());
        }
        return aStack;
    }

    private void dfs(Node aNode) {
        deepNode.SCC++;
        aNode.explored = true;
        //Iterator<Edge> itr = aNode.myEdges.iterator();
        for (int i = 0 ; i < aNode.myEdges.size() ; i++) {            
            Node toNode = aNode.myEdges.get(i).toNode();
            if (toNode.explored == false) {
                dfs(toNode);
            }
        }
        finishingTime++;
        aNode.value = finishingTime;
    }

    private class Node implements Comparable<Node> {

        public int SCC = 0; //Number of SCC's , will only increment if deep node on 2nd pass
        public Graph mygraph; //referenc to graph
        public int name; //Cell's name
        public int value; //cell's value
        public ArrayList<Edge> myEdges;
        public boolean explored = false;

        public Node(int name, Graph agraph) {
            mygraph = agraph;
            this.name = name;
            value = this.name; //initialize to name, but reset will move it to zero for 2nd pass
            myEdges = new ArrayList<Edge>(); //list of edges for the node
        }

        public void addEdge(Edge anEdge) {
            myEdges.add(anEdge);
        }

        public void reset() {
            SCC = 0;
            value = 0;
            explored = false;
        }

        @Override
        public int compareTo(Node aNode) {
            if (aNode.value > this.value) {
                return 1;
            } else if (aNode.value < this.value) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private class Edge {

        private Node left;
        private Node right;
        private Graph mygraph;

        public Edge(Node left, Node right) {
            this.left = left;
            this.right = right;
            mygraph = left.mygraph;
        }

        public Node startNode() {
            if (mygraph.getdirection() == Direction.left) {
                return left;
            } else if ((mygraph.getdirection() == Direction.right)) {
                return right;
            } else {
                return null;
            }
        }

        public Node toNode() {
            if (mygraph.getdirection() == Direction.left) {
                return right;
            } else if ((mygraph.getdirection() == Direction.right)) {
                return left;
            } else {
                return null;
            }
        }
    }

    private enum Direction {

        left, right
    };
}
