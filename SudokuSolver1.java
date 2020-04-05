

import java.util.*;

public class SudokuSolver1{

    public static void main(String[] args){
        //inputted sudoku
        int[][] sudokuInputted = input(6);

        sudokCell[][] mySudoku = new sudokCell[9][9];

        //my sudoku to be worked with
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                mySudoku[row][column] = new sudokCell(sudokuInputted[row][column]);
            }
        }                    
        printBoard(mySudoku, true); //boolean of whether to include candidates
        printBoard(mySudoku, false);

        solve(mySudoku);
        
        System.out.println();
        printBoard(mySudoku, true);        
        System.out.println();
        printBoard(mySudoku, false);
        if(solved(mySudoku)){
            System.out.println("Solved it!");
        }
        else{
            System.out.println("More work to do!");
        }
        System.out.println("Num unsolved is " + numUnsolved(mySudoku));
        /*
        bruteForceSolver(mySudoku);
        
        System.out.println();
        printBoard(mySudoku, true);        
        System.out.println();
        printBoard(mySudoku, false);
        */
    }
    
    //solve method
    public static void solve(sudokCell[][] mySudoku){
        for(int i = 0; i < 11; i++){
            rookChecker(mySudoku);
            boxChecker(mySudoku);
            onlyCandidateLeftRookChecker(mySudoku);    
            onlyCandidateLeftBoxChecker(mySudoku);
            candidatePairRookChecker(mySudoku);
            candidatePairBoxChecker(mySudoku);
            System.out.println("HeHe");
        }
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
                                checkerMethodOneWorks = rookChecker(mySudoku);
                                checkerMethodOneWorks = boxChecker(mySudoku);
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
                                checkerMethodOneWorks = rookChecker(mySudoku);
                                checkerMethodOneWorks = boxChecker(mySudoku);
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
                                    boxCheckerWorks = boxChecker(mySudoku);
                                    boxCheckerWorks = rookChecker(mySudoku);
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
                    rookChecker(mySudoku);
                    boxChecker(mySudoku);
                    onlyCandidateLeftRookCheckerWorks = true;
                    onlyCandidateLeftRookCheckerWorks = onlyCandidateLeftBoxChecker(mySudoku);
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
                    rookChecker(mySudoku);
                    boxChecker(mySudoku);
                    onlyCandidateLeftRookCheckerWorks = onlyCandidateLeftBoxChecker(mySudoku);
                }
            }
        }
        return onlyCandidateLeftRookCheckerWorks;
    }

 

    //check if candidate is only candidate in one spot in a box
    public static boolean onlyCandidateLeftBoxChecker(sudokCell[][] mySudoku){
        boolean onlyCandidateLeftBoxCheckerWorks = false;
        //for each box row
        for(int boxRow = 0; boxRow < 3; boxRow++){
            //for each box column
            for(int boxColumn = 0; boxColumn < 3; boxColumn++){
                //for each integer possible
                for(int i = 1; i <=9; i++){
                    int num = 0;
                    int rowIndex = -1;
                    int columnIndex = -1;
                    //for each row in the small box
                    for(int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++){
                        //for each column in the small box
                        for(int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++){
                            //if the element is not solved
                            if(!mySudoku[row2][column2].getSolved()){
                                //if it contains i
                                if(mySudoku[row2][column2].contains(i)){
                                    num++;
                                    rowIndex = row2;
                                    columnIndex = column2;
                                }
                            }
                        }
                    }
                    //if only one cell in the box has that candidate, solve it 
                    if(num == 1){
                        mySudoku[rowIndex][columnIndex].solve(i);
                        rookChecker(mySudoku);
                        boxChecker(mySudoku);
                        onlyCandidateLeftBoxCheckerWorks = true;
                        onlyCandidateLeftBoxCheckerWorks = onlyCandidateLeftBoxChecker(mySudoku);
                    }
                }
            }            
        }
        return onlyCandidateLeftBoxCheckerWorks;
    }

    //checks for 2 boxes that have only 2 candidates in a column or row, eliminates those candidates from that column OR row 
    public static boolean candidatePairRookChecker(sudokCell[][] mySudoku){
        boolean candidatePairRookCheckerWorks = false;
        //two for loops to go through each element in mySudoku
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                //if that element is unsolved 
                if(!mySudoku[row][column].getSolved()){
                    //for each other element in the column
                    int numSame = 0;
                    ArrayList<Integer> rowVals = new ArrayList<Integer>();
                    
                    for(int row2 = 0; row2 < 9; row2++){
                        //if the other element is not solved 
                        if(mySudoku[row2][column].samePossible(mySudoku[row][column])){
                            numSame++;
                            rowVals.add(row2);
                        }
                    }
                    //if the number of cells with same possibles equals number of possibles per cell
                    if(numSame == mySudoku[row][column].size()){
                        //for each other element in the column
                        for(int row2 = 0; row2 < 9; row2++){
                            if(!rowVals.contains(row2)){
                                for(int possibleIndex = 0; possibleIndex < mySudoku[row][column].size(); possibleIndex++){
                                    if(mySudoku[row2][column].indexOf(mySudoku[row][column].getVal(possibleIndex)) != -1){
                                        mySudoku[row2][column].remove(mySudoku[row2][column].indexOf(mySudoku[row][column].getVal(possibleIndex)));
                                    }
                                }
                            }
                        }
                        candidatePairRookCheckerWorks = true;
                        candidatePairRookCheckerWorks = rookChecker(mySudoku);
                        candidatePairRookCheckerWorks = boxChecker(mySudoku);
                    }


                    //for each other element in the row
                    ArrayList<Integer> columnVals = new ArrayList<Integer>();
                    numSame = 0;
                    for(int column2 = 0; column2 < 9; column2++){
                        //if the other element is not solved 
                        if(mySudoku[row][column2].samePossible(mySudoku[row][column2])){
                            numSame++;
                            columnVals.add(column2);
                        }
                    } 
                    //if the number of cells with same possibles equals number of possibles per cell
                    if(numSame == mySudoku[row][column].size()){
                        //for each other element in that row
                        for(int column2 = 0; column2 < 9; column2++){
                            if(!columnVals.contains(column2)){
                                for(int possibleIndex = 0; possibleIndex < mySudoku[row][column].size(); possibleIndex++){
                                    if(mySudoku[row][column2].indexOf(mySudoku[row][column].getVal(possibleIndex)) != -1){
                                        mySudoku[row][column2].remove(mySudoku[row][column2].indexOf(mySudoku[row][column].getVal(possibleIndex)));
                                    }
                                }
                            }
                        }
                        candidatePairRookCheckerWorks = true;
                        candidatePairRookCheckerWorks = rookChecker(mySudoku);
                        candidatePairRookCheckerWorks = boxChecker(mySudoku);
                    }    
                }
            }
        }      
        return candidatePairRookCheckerWorks;
    }

    //checks for 2 boxes that have only 2 candidates in a box, eliminates those candidates from that box 
    public static boolean candidatePairBoxChecker(sudokCell[][] mySudoku){
        boolean candidatePairBoxCheckerWorks = false;
        //two for loops to go through each element in mySudoku
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                //if that element is unsolved 
                if(!mySudoku[row][column].getSolved()){
                    int numSame = 0;
                    ArrayList<Integer> rowVals = new ArrayList<Integer>();
                    ArrayList<Integer> columnVals = new ArrayList<Integer>();

                    int boxRow = row / 3;
                    int boxColumn = column / 3;
                    
                            
                    //for each row in the small box
                    for(int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++){
                        //for each column in the small box
                        for(int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++){
                            //if the element is not solved
                            if(!mySudoku[row2][column2].getSolved()){
                                //if it has same possibles
                                if(mySudoku[row2][column2].samePossible(mySudoku[row][column])){
                                    numSame++;
                                    rowVals.add(row2);
                                    columnVals.add(column2);
                                }
                            }
                        }
                    }

                    //if the number of cells with same possibles equals number of possibles per cell
                    if(numSame == mySudoku[row][column].size()){
                        
                        //for each other element in that box
                        //for each row in the small box
                        for(int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++){
                            //for each column in the small box
                            for(int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++){
                                //if the box was not one of the ones that had same pair
                                if(!columnVals.contains(column2) || !rowVals.contains(row2)){
                                    
                                    for(int possibleIndex = 0; possibleIndex < mySudoku[row][column].size(); possibleIndex++){
                                        //if the other cell contains that possibility
                                        if(mySudoku[row2][column2].contains(mySudoku[row][column].getVal(possibleIndex))){
                                            //remove that possibility from the other cell
                                            mySudoku[row2][column2].remove(mySudoku[row2][column2].indexOf(mySudoku[row][column].getVal(possibleIndex)));
                                            
                                        }
                                    }
                                }
                            }
                        }
                        candidatePairBoxCheckerWorks = true;
                        candidatePairBoxCheckerWorks = rookChecker(mySudoku);
                        candidatePairBoxCheckerWorks = boxChecker(mySudoku);
                    }    
                }
            }            
        }
        return candidatePairBoxCheckerWorks;
    }

    

    //brute force method
    public static void bruteForceSolver(sudokCell[][] mySudoku){
        sudokCell[][] mySudoku2 = new sudokCell[9][9];
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                sudokCell x = mySudoku[row][column];
                mySudoku2[row][column] = new sudokCell(x);
            }
        }
        System.out.println("Clone is: ");
        mySudoku2[0][0].solve(3);
        printBoard(mySudoku2, false);
    }

    //make a copy of values
    public static sudokCell[][] copy(sudokCell[][] mySudoku){
        sudokCell[][] mySudoku2 = new sudokCell[9][9];
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                sudokCell x = mySudoku[row][column];
                mySudoku2[row][column] = new sudokCell(x);
            }
        }
        return mySudoku2;
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
                    System.out.print(mySudoku[row][column]);
                }
                else{
                    System.out.print(mySudoku[row][column].toStringWithoutCands());
                }
            }
            System.out.println();
        }         
    }

    //check if the sudoku is solved
    public static boolean solved(sudokCell[][] mySudoku){
        //two for loops to go through each row, check adds to 45
        for(int row = 0; row < 9; row++){
            int numTotal = 0;
            for(int column = 0; column < 9; column++){
                numTotal += mySudoku[row][column].getVal();
            }
            if(numTotal != 45){
                return false;
            }
        }
        System.out.println("Rows add up");        
        //two for loops to go through each column, check adds to 45
        for(int column = 0; column < 9; column++){
            int numTotal = 0;
            for(int row = 0; row < 9; row++){
                numTotal += mySudoku[row][column].getVal();
            }
            if(numTotal != 45){
                return false;
            }
        }
        System.out.println("Columns add up");
        //check each box, check adds to 45
        //for each box row
        for(int boxRow = 0; boxRow < 3; boxRow++){
            //for each box column
            for(int boxColumn = 0; boxColumn < 3; boxColumn++){
                int numTotal = 0;
                //for each row in the small box
                for(int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++){
                    //for each column in the small box
                    for(int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++){
                        numTotal += mySudoku[row2][column2].getVal();
                    }
                }
                if(numTotal != 45){
                    return false;
                }
            }          
        }
        System.out.println("Boxes add up");
        return true;
    }

    //check how many boxes remain unsolved
    public static int numUnsolved(sudokCell[][] mySudoku){
        int numUnsolved = 81;
        //for each solved cell in main array
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                //if that element is solved
                if(mySudoku[row][column].getSolved()){
                    numUnsolved--;
                }
            }
        }
        return numUnsolved;
    }


    //gives sudoku from list of possibles
    public static int[][] input(int x){
        if(x == 1){
            int[][] beginner =     {{-1, -1, 5, 8, 2, -1, -1, 1, 4},
                                    {3, 1, -1, 9, -1, 4, 5, -1, -1},
                                    {-1, 4, 2, -1, 3, -1, 9, 6, 8},

                                    {6, 3, -1, -1, -1, -1, 7, 4, 5},
                                    {1, 2, -1, 4, 5, -1, -1, 8, 9},
                                    {8, 5, -1, -1, -1, -1, -1, 3, 2},

                                    {-1, -1, 6, 3, 7, 2, -1, 5, -1},
                                    {-1, 8, 1, 6, -1, 9, 2, 7, -1},
                                    {2, 7, 3, 1, -1, -1, -1, 9, 6}};
            return beginner;
        }
        if(x == 2){
            int[][] normal = {{0, 0, 0, 0, 9, 0, 0, 0, 0},
            {0, 9, 0, 0, 0, 0, 0, 4, 0},
            {0, 5, 1, 8, 0, 7, 0, 0, 0},

            {0, 8, 7, 9, 0, 0, 0, 2, 0},
            {4, 0, 0, 3, 0, 0, 0, 0, 7},
            {9, 1, 0, 4, 0, 0, 3, 6, 8},
            
            {1, 0, 2, 0, 0, 4, 0, 7, 5},
            {0, 4, 0, 0, 0, 0, 2, 0, 0},
            {7, 0, 0, 0, 5, 0, 0, 8, 0}};
            return normal;
        }
        if(x == 3){
            int[][] hard = {{0, 8, 0, 0, 0, 0, 0, 4, 0},
                {2, 0, 0, 8, 0, 0, 5, 0, 7},
                {0, 0, 4, 7, 0, 0, 0, 0, 0},
                {0, 0, 0, 3, 0, 0, 0, 7, 1},
                {0, 0, 8, 0, 0, 6, 0, 0, 3},
                {7, 0, 0, 0, 0, 1, 0, 0, 4},
                {8, 0, 0, 0, 9, 2, 0, 0, 6},
                {6, 0, 2, 0, 0, 0, 7, 0, 0},
                {1, 0, 0, 0, 0, 0, 3, 9, 0}};
            return hard;
        }
        if(x == 4){
            int[][] expert =   {{0, 0, 7, 0, 0, 0, 6, 3, 0},
                    {6, 0, 0, 5, 0, 3, 0, 0, 9},
                    {8, 0, 0, 0, 7, 0, 0, 0, 0},
                    {0, 0, 0, 9, 0, 0, 0, 0, 3},
                    {0, 0, 0, 0, 0, 0, 8, 5, 4},
                    {0, 0, 0, 8, 0, 0, 0, 0, 0},
                    {7, 6, 0, 0, 0, 1, 0, 0, 0},
                    {5, 0, 0, 0, 0, 7, 0, 0, 6},
                    {0, 4, 1, 0, 9, 0, 0, 0, 5}};
            return expert;
        }
        if(x == 5){
            int[][] expert2 = {{0, 9, 1, 0, 0, 0, 0, 0, 0},
                                {4, 0, 0, 0, 9, 0, 0, 0, 0},
                                {2, 0, 0, 0, 0, 7, 0, 0, 0},
                                {9, 0, 0, 0, 0, 0, 0, 1, 0},
                                {6, 0, 4, 0, 1, 0, 0, 9, 0},
                                {0, 0, 0, 7, 8, 0, 0, 0, 4},
                                {0, 0, 6, 0, 0, 0, 0, 8, 0},
                                {0, 0, 0, 0, 2, 1, 0, 0, 7},
                                {7, 0, 9, 4, 0, 5, 0, 0, 1}};
            return expert2;
        }
        if(x == 6){
            int[][] fiveStar = {{0, 5, 0, 0, 1, 3, 0, 0, 0},
                                {0, 0, 1, 0, 8, 0, 3, 0, 0},
                                {8, 0, 0, 5, 0, 0, 0, 6, 4},
                                {5, 0, 7, 0, 3, 0, 0, 0, 0},
                                {0, 4, 0, 0, 5, 0, 0, 2, 0},
                                {0, 0, 0, 0, 2, 0, 8, 0, 5},
                                {1, 6, 0, 0, 0, 9, 0, 0, 8},
                                {0, 0, 9, 0, 7, 0, 2, 0, 0},
                                {0, 0, 0, 8, 6, 0, 0, 4, 0}};
            return fiveStar;
        }
        int[][] other = new int[9][9];
        return other;
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

int[][] hard = {{0, 8, 0, 0, 0, 0, 0, 4, 0},
                {2, 0, 0, 8, 0, 0, 5, 0, 7},
                {0, 0, 4, 7, 0, 0, 0, 0, 0},
                {0, 0, 0, 3, 0, 0, 0, 7, 1},
                {0, 0, 8, 0, 0, 6, 0, 0, 3},
                {7, 0, 0, 0, 0, 1, 0, 0, 4},
                {8, 0, 0, 0, 9, 2, 0, 0, 6},
                {6, 0, 2, 0, 0, 0, 7, 0, 0},
                {1, 0, 0, 0, 0, 0, 3, 9, 0}}

int[][] expert =   {{0, 0, 7, 0, 0, 0, 6, 3, 0},
                    {6, 0, 0, 5, 0, 3, 0, 0, 9},
                    {8, 0, 0, 0, 7, 0, 0, 0, 0},
                    {0, 0, 0, 9, 0, 0, 0, 0, 3},
                    {0, 0, 0, 0, 0, 0, 8, 5, 4},
                    {0, 0, 0, 8, 0, 0, 0, 0, 0},
                    {7, 6, 0, 0, 0, 1, 0, 0, 0},
                    {5, 0, 0, 0, 0, 7, 0, 0, 6},
                    {0, 4, 1, 0, 9, 0, 0, 0, 5}}
int 
            
                                
efficiency idea for rook and box 3x3 checker - get all nums within that section, remove from possibles. Maybe do on first pass through 



                                */