

import java.util.*;

public class BoggleDice{

    public static void main(String[] args){
        String[][] boggle = { {"r", "i", "f", "o", "b", "x"},
                              {"i", "f", "e", "h", "e", "y"},
                              {"d", "e", "n", "o", "w", "s"},
                              {"u", "t", "o", "k", "n", "d"},
                              {"h", "m", "s", "r", "a", "o"},
                              {"l", "u", "p", "e", "t", "s"},
                              {"a", "c", "i", "t", "o", "a"},
                              {"y", "l", "g", "k", "u", "e"},
                              {"Qu", "b", "m", "j", "o", "a"},
                              {"e", "h", "i", "s", "p", "n"},
                              {"v", "e", "t", "i", "g", "n"},
                              {"b", "a", "l", "i", "y", "t"},
                              {"e", "z", "a", "v", "n", "d"},
                              {"r", "a", "l", "e", "s", "c"},
                              {"u", "w", "i", "l", "r", "g"},
                              {"p", "a", "c", "e", "m", "d"} };
                              //16 by 6
    
        for (int i = 0; i < boggle.length; i++){
            System.out.println(Arrays.toString(boggle[i]));
        }
        
        String[][] board = new String[4][4];
        
        int row;
        int column; 
        
        for(int i = 0; i < board.length; i++){
        
            for(int k = 0; k < board.length; k++){

               row = (int) (Math.random() * 16);
               column = (int) (Math.random() * 6);
               
               board[i][k] = boggle[row][column];
               
            }
        }
        
        for (int i = 0; i < board.length; i++){
            System.out.println(Arrays.toString(board[i]));
        }
    }
}
//