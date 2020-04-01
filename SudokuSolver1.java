

import java.util.*;

public class SudokuSolver1{

    public static void main(String[] args){
        //inputted sudoku
        int[][] sudokuInputted =   {{0, 0, 7, 0, 0, 0, 6, 3, 0},
                                    {6, 0, 0, 5, 0, 3, 0, 0, 9},
                                    {8, 0, 0, 0, 7, 0, 0, 0, 0},
                                    {0, 0, 0, 9, 0, 0, 0, 0, 3},
                                    {0, 0, 0, 0, 0, 0, 8, 5, 4},
                                    {0, 0, 0, 8, 0, 0, 0, 0, 0},
                                    {7, 6, 0, 0, 0, 1, 0, 0, 0},
                                    {5, 0, 0, 0, 0, 7, 0, 0, 6},
                                    {0, 4, 1, 0, 9, 0, 0, 0, 5}};


        sudokCell[][] mySudoku = new sudokCell[9][9];

        //my sudoku to be worked with
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                mySudoku[row][column] = new sudokCell(sudokuInputted[row][column]);
            }
        }                    
        printBoard(mySudoku, false); //boolean of whether to include candidates
        printBoard(mySudoku, true);

        boolean checkerMethodWorks = true;
        for(int i = 0; i < 10; i++){
            checkerMethodWorks = rookChecker(mySudoku);
            boxChecker(mySudoku);
            onlyCandidateLeftRookChecker(mySudoku);
            System.out.println("HeHe");
        }
        System.out.println();
        printBoard(mySudoku, true);
        System.out.println();
        printBoard(mySudoku, false);

        
    }

    //eliminates by rook method
    public static boolean rookChecker(sudokCell[][] mySudoku){
        boolean checkerMethodOneWorks = false;

        //two for loops to go through each element in mySudoku
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                //if that element is solved
                if(mySudoku[row][column].getSolved()){
                    //for each other element in the row
                    for(int row2 = 0; row2 < 9; row2++){
                        //if the other element is not solved
                        if(!mySudoku[row2][column].getSolved()){
                            //index is index of the solved value in not solved element 
                            int index = mySudoku[row2][column].indexOf(mySudoku[row][column].getVal());
                            
                            //if not solved element has solved value 
                            if(index != -1){
                                boolean toPrint = mySudoku[row2][column].remove(index);
                                    //printBoard(mySudoku, true);
                                if(toPrint){
                                    printBoard(mySudoku, true);
                                }
                                checkerMethodOneWorks = true;
                            }
                        }
                    }
                    //for each other element in the column
                    for(int column2 = 0; column2 < 9; column2++){
                        //if the other element is not solved
                        if(!mySudoku[row][column2].getSolved()){
                            //index is index of the solved value in not solved element 
                            int index = mySudoku[row][column2].indexOf(mySudoku[row][column].getVal());
                            
                            //if not solved element has solved value 
                            if(index != -1){
                                boolean toPrint = mySudoku[row][column2].remove(index);
                                    //printBoard(mySudoku, true);
                                if(toPrint){
                                    printBoard(mySudoku, true);
                                }
                                checkerMethodOneWorks = true; 
                            }
                        }
                    }     
                }
            }
        }      
        return checkerMethodOneWorks;
    }

    //eliminates by checking 3 by 3 boxes
    public static boolean boxChecker(sudokCell[][] mySudoku){
        boolean boxCheckerWorks = false;

        //for each solved cell in main array
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                //if that element is solved
                if(mySudoku[row][column].getSolved()){
                    int boxColumn = column / 3;
                    int boxRow = row / 3;

                    //for each row in the small box
                    for(int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++){
                        //for each column in the small box
                        for(int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++){
                            //if the other element is not solved
                            if(!mySudoku[row2][column2].getSolved()){  
                                //index is index of the solved value in not solved element 
                                int index = mySudoku[row2][column2].indexOf(mySudoku[row][column].getVal());

                                if(index != -1){
                                    boolean toPrint = mySudoku[row2][column2].remove(index);
                                        //printBoard(mySudoku, true);
                                    if(toPrint){
                                        printBoard(mySudoku, true);
                                    }
                                    boxCheckerWorks = true; 
                                }
                            }
                        }
                    }
                }
            }
        }  
        return boxCheckerWorks;
    }

    //check if candidate is only candidate in one spot in a row or column
    public static boolean onlyCandidateLeftRookChecker(sudokCell[][] mySudoku){
        boolean onlyCandidateLeftRookCheckerWorks = false;
        //check each column
        for(int column = 0; column < 9; column ++){
            //for each possible integer
            for(int i = 1; i <= 9; i++){    
                int num = 0;
                int index = -1;
                //for each row
                for(int row = 0; row < 9; row++){   
                    //if not solved
                    if(!mySudoku[row][column].getSolved()){

                        if(mySudoku[row][column].contains(i)){
                            num++;
                            index = row;
                        }
                    }
                }
                if(num == 1){
                    mySudoku[index][column].solve(i);
                    onlyCandidateLeftRookCheckerWorks = true;
                }
            }
        }

        //check each row 
        for(int row = 0; row < 9; row ++){
            //for each possible integer
            for(int i = 1; i <= 9; i++){    
                int num = 0;
                int index = -1;
                //for each column
                for(int column = 0; column < 9; column++){   
                    //if not solved
                    if(!mySudoku[row][column].getSolved()){
                        if(mySudoku[row][column].contains(i)){
                            num++;
                            index = column;
                        }
                    }
                }
                if(num == 1){
                    mySudoku[row][index].solve(i);
                    onlyCandidateLeftRookCheckerWorks = true;
                }
            }
        }
        return false;
    }

    //check if candidate is only candidate in one spot in a box
    public static boolean onlyCandidateLeftBoxChecker(sudokCell[][] mySudoku){
        return false;
    }

    //checks for 2 boxes that have only 2 candidates in a column or row, eliminates those candidates from that column OR row 
    public static boolean candidatePairRookChecker(sudokCell[][] mySudoku){
        return false;
    }

    //checks for 2 boxes that have only 2 candidates in a box, eliminates those candidates from that box 
    public static boolean candidatePairBoxChecker(sudokCell[][] mySudoku){
        return false;
    }

    

    //brute force method
    public static void bruteForceSolver(sudokCell[][] mySudoku){

    }

    //scanner input sudoku
    public static int[][] userInputSudoku(){
        Scanner input = new Scanner(System.in);
        int[][] mySudoku = new int[9][9];

        int a = 0;
        int b = 0;
        for(int i = 0; i < 81; i++){
            mySudoku[a][b] = input.nextInt();
            if(i % 9 == 0){
                a++;
            }
            else{
                b++;
            }
            input.close();
        }
        return mySudoku;
    }


    //printer for the board
    public static void printBoard(sudokCell[][] mySudoku, boolean includeCandidates){
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                if(includeCandidates){
                    System.out.print(mySudoku[row][column].toStringWithCands());
                }
                else{
                    System.out.print(mySudoku[row][column]);
                }
            }
            System.out.println();
        }         
    }

    //check if the sudoku is solved
    public static boolean solved(sudokCell[][] mySudoku){
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                if(!mySudoku[row][column].getSolved()){
                    return false;
                }
            }
        }  
        return true;
    }
}


/* 
int[][] beginner =           {{-1, -1, 5, 8, 2, -1, -1, 1, 4},
                                    {3, 1, -1, 9, -1, 4, 5, -1, -1},
                                    {-1, 4, 2, -1, 3, -1, 9, 6, 8},

                                    {6, 3, -1, -1, -1, -1, 7, 4, 5},
                                    {1, 2, -1, 4, 5, -1, -1, 8, 9},
                                    {8, 5, -1, -1, -1, -1, -1, 3, 2},

                                    {-1, -1, 6, 3, 7, 2, -1, 5, -1},
                                    {-1, 8, 1, 6, -1, 9, 2, 7, -1},
                                    {2, 7, 3, 1, -1, -1, -1, 9, 6}};
int[][] beginnerSolution =     {{9, 6, 5, 8, 2, 7, 3, 1, 4},
                                {3, 1, 8, 9, 6, 4, 5, 2, 7},
                                {7, 4, 2, 5, 3, 1, 9, 6, 8},
                                {6, 3, 9, 2, 1, 8, 7, 4, 5},
                                {1, 2, 7, 4, 5, 3, 6, 8, 9},
                                {8, 5, 4, 7, 9, 6, 1, 3, 2},
                                {4, 9, 6, 3, 7, 2, 8, 5, 1},
                                {5, 8, 1, 6, 4, 9, 2, 7, 3},
                                {2, 7, 3, 1, 8, 5, 4, 9, 6}}; 


int[][] normal = {{0, 0, 0, 0, 9, 0, 0, 0, 0},
            {0, 9, 0, 0, 0, 0, 0, 4, 0},
            {0, 5, 1, 8, 0, 7, 0, 0, 0},

            {0, 8, 7, 9, 0, 0, 0, 2, 0},
            {4, 0, 0, 3, 0, 0, 0, 0, 7},
            {9, 1, 0, 4, 0, 0, 3, 6, 8},
            
            {1, 0, 2, 0, 0, 4, 0, 7, 5},
            {0, 4, 0, 0, 0, 0, 2, 0, 0},
            {7, 0, 0, 0, 5, 0, 0, 8, 0}}

int[][] expert =   {{0, 0, 7, 0, 0, 0, 6, 3, 0},
                    {6, 0, 0, 5, 0, 3, 0, 0, 9},
                    {8, 0, 0, 0, 7, 0, 0, 0, 0},
                    {0, 0, 0, 9, 0, 0, 0, 0, 3},
                    {0, 0, 0, 0, 0, 0, 8, 5, 4},
                    {0, 0, 0, 8, 0, 0, 0, 0, 0},
                    {7, 6, 0, 0, 0, 1, 0, 0, 0},
                    {5, 0, 0, 0, 0, 7, 0, 0, 6},
                    {0, 4, 1, 0, 9, 0, 0, 0, 5}}
                                
idea for rook and box 3x3 checker - get all nums within that section, remove from possibles. Maybe do on first pass through 



                                */