package stronglyconnectedcomponents;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileArrayProvider {

    public static ArrayList<int[]> readLines(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        ArrayList<int[]> intArray = new ArrayList();
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String stringline = null;
        while ((stringline = bufferedReader.readLine()) != null) { //While there is data to read
            boolean amIN = false;
            int exp = 0; // exponent;
            int temp = 0; //holds the numeric representation of a string as is builds
            int i = 0; // number of characters in a string of numbers
            int k = 0; //place to put the temp variable in the 'theInts' array
            int[] theInts = new int[2]; //what will be added to the arraylist
            for (int j = 0; j < stringline.length(); j++) { //read the line
                if (amIN == false && Character.isWhitespace(stringline.charAt(j))) {
                } //if I am not in a number and there is whitespace do nothing
                else if (amIN == false && (!Character.isWhitespace(stringline.charAt(j)) || j == stringline.length())) { //if I am at the start of a number sequence
                    amIN = true;  //I am now in a number sequence
                    temp = Character.getNumericValue(stringline.charAt(j)); //There is a number for that character
                    i++; //i is the number of 'characters' in the 'string' of numbers
                    if (j == stringline.length() - 1) { //if the number is the first number of a string at the end of a line then assign it
                        if (temp != 0) {
                            theInts[k] = temp;
                            intArray.add(theInts);
                            k = 0;
                        }
                    }
                } else if (amIN && !Character.isWhitespace(stringline.charAt(j))) { //If already reading in a number, and the next character isn't whitespace
                    exp++;
                    temp = Character.getNumericValue(stringline.charAt(j)) + (temp * 10);
                    
                    i++;
                    if (j + 1 == stringline.length()) { //if the next character to be read doesn't exist
                        if (temp != 0) {
                            theInts[k] = temp;
                            intArray.add(theInts);
                            k = 0;
                            ;
                        }
                    }
                } else if (amIN && Character.isWhitespace(stringline.charAt(j))) { //If I am reading in a number and the next character is white space
                    exp = 0; //This is needed so I can read in future numbers
                    if (temp != 0) {
                        theInts[k] = temp;
                        k++;
                        if (j + 1 == stringline.length()) { //if the next character to be read doesn't exist
                            intArray.add(theInts);
                            k = 0;
                        }
                    }
                    temp = 0; //reset to read in more numbers
                    amIN = false; //no longer reading in a number so reset it
                } else if (j == stringline.length() && amIN) {   //Do nothing, but this shouldn't happen due to for loop constraints
                }
            }
        }
        return intArray;
    }
}
