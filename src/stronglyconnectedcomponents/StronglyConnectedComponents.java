/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stronglyconnectedcomponents;
//make sure to have 5 meg stack and 1gig heap to run this bad boy***
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davidson_b
 */
public class StronglyConnectedComponents {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<int[]> intArray;
        try {
            intArray = FileArrayProvider.readLines("C:/SCC.txt");
        } 
        catch (IOException ex) {
            Logger.getLogger(StronglyConnectedComponents.class.getName()).log(Level.SEVERE, null, ex);
            intArray = null;
        }
        Graph aGraph = new Graph(intArray);
        aGraph.Kosaraju();
    }
}


