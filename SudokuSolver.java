

import java.util.*;

public class SudokuSolver{

    public static void main(String[] args){
        //inputted sudoku
        int[][] sudokuInputted =   {{-1, -1, 5, 8, 2, -1, -1, 1, 4},
                                    {3, 1, -1, 9, -1, 4, 5, -1, -1},
                                    {-1, 4, 2, -1, 3, -1, 9, 6, 8},
                                    {6, 3, -1, -1, -1, -1, 7, 4, 5},
                                    {1, 2, -1, 4, 5, -1, -1, 8, 9},
                                    {8, 5, -1, -1, -1, -1, -1, 3, 2},
                                    {-1, -1, 6, 3, 7, 2, -1, 5, -1},
                                    {-1, 8, 1, 6, -1, 9, 2, 7, -1},
                                    {2, 7, 3, 1, -1, -1, -1, 9, 6}};

        sudokCell[][] mySudoku = new sudokCell[9][9];

        //my sudoku to be worked with
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                mySudoku[row][column] = new sudokCell(sudokuInputted[row][column]);
            }
        }                    
        printBoard(mySudoku, false); //boolean of whether to include candidates
        printBoard(mySudoku, true);

        boolean checkerMethodOneWorks = true;
        while(checkerMethodOneWorks){
            checkerMethodOneWorks = checkerMethod1(mySudoku);
            System.out.println("HeHe");

        }
        System.out.println();
        printBoard(mySudoku, true);
    }

    //eliminates by rook method
    public static boolean checkerMethod1(sudokCell[][] mySudoku){
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
                                mySudoku[row2][column].remove(index);  
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
                                mySudoku[row][column2].remove(index);  
                                checkerMethodOneWorks = true;
                            }
                        }
                    }     
                }
            }
        }      
        return checkerMethodOneWorks;
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

    public static void printBoard(sudokCell[][] mySudoku, boolean includeCandidates){
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                if(includeCandidates){
                    System.out.print(mySudoku[row][column]);
                }
                else{
                    System.out.print(mySudoku[row][column].toStringWithoutCands());
                }
            }
            System.out.println();
        }         
    }
}

/* 
int[][] sudokuInputted =           {{-1, -1, 5, 8, 2, -1, -1, 1, 4},
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
                                
                                */