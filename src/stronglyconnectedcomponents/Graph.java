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
    public ArrayList<Node> nodes;
    
    
    public void addNode(int name){
        Node aNode = new Node(name,this);
        nodes.add(aNode);
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
        
    }
}
