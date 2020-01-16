import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/**
 * This program creates a doubly randomized scrabble rack and then find all of the words that can be played from it.
 * @author 21fernando
 * @version 1/15/2020
 */
public class ScrabbleRackManager {
    public ArrayList<String> scrabbleWords;
    public ArrayList<String> rack;
    public String alphabet= "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
    /** default class constructor */
    public ScrabbleRackManager() {
        rack = new ArrayList<>();
        scrabbleWords = new ArrayList<>();
        int[] frequencies = new int[]{9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1,2};
        ArrayList<String> allTiles = new ArrayList<>();
        int index = 0;
        for (int count: frequencies){
            for (int i =0; i<count; i++){
                allTiles.add(alphabet.substring(index, index+1));
            }
            index ++;
        }
        Collections.shuffle(allTiles);
        for (int i = 0; i < 7; i++) {
            rack.add(allTiles.remove((int)(Math.random()*allTiles.size())));
        }
        Scanner in = null;
        try{
            in = new Scanner(new File("2019_collins_scrabble.txt"));
            while(in.hasNext()){
                String next = in.nextLine();
                if(next.length()<=7) {
                    scrabbleWords.add(next);
                }
            }
            in.close();
        }catch(Exception e ){
            e.printStackTrace();
        }
    }
    /** displays the contents of the player's tile rack */
    public void printRack(){
        System.out.println("Letters in the rack: "  + rack);
    }
    /** Returns all the permutations of  given length using the words */
    private ArrayList<String> getPermutations(int length, ArrayList<String> letters){
        ArrayList<String> permutations = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        if (length ==1 ){
            return letters;
        }
        for(int i =0; i<letters.size(); i++){
            temp = (ArrayList<String>)(letters.clone()); // http://www.java2novice.com/java-collections-and-util/arraylist/copy-clone/
            temp.remove(i);
            String letter = letters.get(i);
            for(String p: getPermutations(length-1, temp)){
                if(!permutations.contains(letter+p))permutations.add(letter+p);
            }

        }
        return permutations;
    }
    /** builds and returns an ArrayList of String objects that are values pulled from
     * the dictionary/database based on the available letters in the user's tile rack
     * returns an ArrayList<String> of all the playable words*/
    public ArrayList<String> getPlaylist(){
        ArrayList<String> playable = new ArrayList<>();
        for (int i = 2; i < 8; i++) {
            for(String possible: getPermutations(i,rack)){
                if(Collections.binarySearch(scrabbleWords,possible)>-1) playable.add(possible);
            }
        }
        return playable;
    }
    /** prints all of the playable words based on the letters in the tile rack*/
    public void printMatches(){
        ArrayList<String> words = getPlaylist();
        /*System.out.println("You can play the following words from the letters in your rack: ");
        if(words.size() == 0){
            System.out.println("Sorry, NO words can be played from those tiles.");
        }else{
            int i =1;
            String word;
            while(i<words.size()){
                word = words.get(i);
                if(word.length() == 7){
                    word += "*";
                }
                for(int s = 0; s< 12-word.length(); s++){
                    word+= " ";
                }
                System.out.print(word);
                if(i%10==0){
                    System.out.println();
                }
                i++;
            }
        }
        System.out.println("* denotes bingo");*/
        System.out.println(words);
        System.out.println(words.size());
    }

    /**main method for this program
     * @param args comman line arguments if necessary
     */
    public static void main(String[] args){
        long tick = System.nanoTime();
        ScrabbleRackManager app = new ScrabbleRackManager();
        app.printRack();
        app.printMatches();
        long tock = System.nanoTime();
        System.out.println(tock - tick);
        //System.out.println(app.getPermutations(3, app.rack));
    }
}
