

import java.util.*;

public class SudokuSolver1{
    public static sudokCell[][] mySudoku = new sudokCell[9][9];
    public static void main(String[] args){
        //inputted sudoku
        int[][] sudokuInputted = input(10);


        //my sudoku to be worked with
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                mySudoku[row][column] = new sudokCell(sudokuInputted[row][column]);
            }
        }                    
        printBoard(mySudoku, true); //boolean of whether to include candidates
        printBoard(mySudoku, false);

        solve(mySudoku, false);
        
        System.out.println();
        printBoard(mySudoku, true);        
        System.out.println();
        printBoard(mySudoku, false);
        if(solved(mySudoku, true)){
            System.out.println("Solved it!");
        }
        else{
            System.out.println("More work to do!");
        }
        System.out.println("Num unsolved is " + numUnsolved(mySudoku));

        //commented brute force 
        /*
        bruteForceSolver(mySudoku);
        if(solved(mySudoku, true)){
            System.out.println("Brute Forced It!");
        }
        else{
            System.out.println("Couldn't brute force");
            System.out.println("Num unsolved is " + numUnsolved(mySudoku));
        }
        System.out.println();
        printBoard(mySudoku, true);        
        System.out.println();
        printBoard(mySudoku, false);
        */

        checkAll();
    }
    
    //solve method
    public static void solve(sudokCell[][] mySudoku, boolean bruteForce){
        for(int i = 0; i < 10; i++){
            rookChecker(mySudoku);
            boxChecker(mySudoku);
            onlyCandidateLeftRookChecker(mySudoku);    
            onlyCandidateLeftBoxChecker(mySudoku);
            nakedCandidateRookChecker(mySudoku);
            nakedCandidateBoxChecker(mySudoku);
            candidateLinesChecker(mySudoku);
            //hiddenCandidateChecker(mySudoku);
            //System.out.println("HeHe");
        }
        /*if(bruteForce && !solved(mySudoku, false)){
            System.out.println("multiple guesses");
            bruteForceSolver(mySudoku);
        }*/
    }

    //checks all sudokus in data base for if solves
    public static void checkAll(){
        boolean solvedAll = true;
        for(int i = 1; i <= 10; i++){
            //inputted sudoku
            int[][] sudokuInputted = input(i);

            sudokCell[][] mySudoku = new sudokCell[9][9];

            //my sudoku to be worked with
            for(int row = 0; row < 9; row++){
                for(int column = 0; column < 9; column++){
                    mySudoku[row][column] = new sudokCell(sudokuInputted[row][column]);
                }
            }   

            solve(mySudoku, false);
            //bruteForceSolver(mySudoku);

            //if unsolved
            if(!solved(mySudoku, false)){
                solvedAll = false;
                System.out.println("More work on " + i);
                System.out.println("Num unsolved is " + numUnsolved(mySudoku));
            }
        }
        if(solvedAll){
            System.out.println("All solved");
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
    public static boolean nakedCandidateRookChecker(sudokCell[][] mySudoku){
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
                        //if the other element has same candidates
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
    public static boolean nakedCandidateBoxChecker(sudokCell[][] mySudoku){
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

    //checks for hidden candidate sets and removes candidates from those 
    public static boolean hiddenCandidateChecker(sudokCell[][] mySudoku){
        boolean hiddenCandidateCheckerWorks = false;
        ArrayList<Integer> hiddenCandidates = new ArrayList<Integer>();

        //check each column
        for(int column = 0; column < 9; column++){
            ArrayList<sudokCell> candidateSet = new ArrayList<sudokCell>();
            ArrayList<Integer> indexConverter = new ArrayList<Integer>();
            for(int row = 0; row < 9; row++){
                if(!mySudoku[row][column].getSolved()){
                    candidateSet.add(mySudoku[row][column]);
                    indexConverter.add(row);
                }
            }
            hiddenCandidates = findHiddenCandidates(candidateSet);
            //if there was a hidden candidate set
            if(hiddenCandidates.size() > 1){
                //convert hiddenCandidates to useful index 
                for(int i = 0; i < hiddenCandidates.size(); i++){
                    hiddenCandidates.set(i, indexConverter.get(i));
                }
                System.out.println(hiddenCandidates.get(1));
                //among those indexes, remove each candidate that is not alike 
                for(int i = 0; i < hiddenCandidates.size(); i++){
                    //contains
                    for(int k = 1; k <= 9; k++){
                        if(mySudoku[hiddenCandidates.get(i)][column].contains(k)){
                            for(int j = 0; j < hiddenCandidates.size(); j++){
                                if(!mySudoku[hiddenCandidates.get(j)][column].contains(k)){
                                    if(mySudoku[hiddenCandidates.get(i)][column].indexOf(k) != -1){
                                        mySudoku[hiddenCandidates.get(i)][column].remove(mySudoku[hiddenCandidates.get(i)][column].indexOf(k));
                                    }
                                }
                            }
                        }
                    }
                }
                hiddenCandidateCheckerWorks = true;
                hiddenCandidateCheckerWorks = rookChecker(mySudoku);
                hiddenCandidateCheckerWorks = boxChecker(mySudoku);
            }
        }
        //check each row

        //check each box 
                        // candidatePairRookCheckerWorks = true;
                        // candidatePairRookCheckerWorks = rookChecker(mySudoku);
                        // candidatePairRookCheckerWorks = boxChecker(mySudoku);
          
        return hiddenCandidateCheckerWorks;
    }

    //method for hiddenCandidateChecker, takes in array list, finds the hidden candidates, returns them in an arraylist
    public static ArrayList<Integer> findHiddenCandidates(ArrayList<sudokCell> candidateSet){
        ArrayList<Integer> hiddenCandidates = new ArrayList<Integer>();
        ArrayList[] whereEach = new ArrayList[9];
        for(int i = 0; i < 9; i++){
            whereEach[i] = new ArrayList<Integer>();
        }
        //for each possible candidate
        for(int i = 1; i <= 9; i++){
            //for each sudok cell in the candidate set
            for(int k = 0; k < candidateSet.size(); k++){
                //if it contains the possible candidate, add that candidate to the whereEach arrayList at the index of that candidate minus one
                if((candidateSet.get(k)).contains(i)){
                    whereEach[i - 1].add(k);
                }
            }
        }
        //if there naked duplicates 
        //for each element in whereEach
        for(int i = 0; i < 9; i++){
            ArrayList<Integer> indexOfNakedDuplicates = new ArrayList<Integer>();
            int num = 0; 
            //compare each element in whereEach to each other element
            for(int k = 0; k < 9; k++){
                if(whereEach[i].equals(whereEach[k])){
                    num++;
                    indexOfNakedDuplicates.add(k);
                }
            }
            //if there is a valid pair
            if(num == whereEach[indexOfNakedDuplicates.get(0)].size()){
                //return an arrayList of integers of the indeces of that pair 
                return indexOfNakedDuplicates;
            }
        }
        return hiddenCandidates;
    }

    //method for candidate lines (only place in a box where candidate must go is in a line, eliminate candidate from that line outside the box)
    public static boolean candidateLinesChecker(sudokCell[][] mySudoku){
        boolean candidateLinesCheckerWorks = false;
        //for each big box
        for(int boxRow = 0; boxRow < 3; boxRow++){
            for(int boxColumn = 0; boxColumn < 3; boxColumn++){
                //for each candidate
                for(int i = 1; i <= 9; i++){
                    ArrayList<Integer> rowVals = new ArrayList<Integer>();
                    ArrayList<Integer> columnVals = new ArrayList<Integer>();
                    int numHasCandidate = 0;
                    boolean removedACandidate = false;

                    //for each row in the small box
                    for(int row2 = boxRow * 3; row2 < boxRow * 3 + 3; row2++){
                        //for each column in the small box
                        for(int column2 = boxColumn * 3; column2 < boxColumn * 3 + 3; column2++){
                            //if the element is not solved
                            if(!mySudoku[row2][column2].getSolved()){  
                                //if it contains the candidate integer
                                if(mySudoku[row2][column2].contains(i)){
                                    numHasCandidate++;
                                    rowVals.add(row2);
                                    columnVals.add(column2);
                                }
                            }
                        }
                    }
                    //if number of cells that has that candidate in a box is 3 or lower
                    if(numHasCandidate <= 3){
                        //if all in same row
                        boolean allInSameRow = false;
                        //if 2 in same row
                        if(rowVals.size() == 2){
                            if(rowVals.get(0) == rowVals.get(1)){
                                allInSameRow = true;
                            }
                        }
                        //if 3 in same row
                        if(rowVals.size() == 3){
                            if(rowVals.get(0) == rowVals.get(1) && rowVals.get(1) == rowVals.get(2)){
                                allInSameRow = true;
                            }
                        }
                        if(allInSameRow){
                            //eliminate values along that row
                            for(int column2 = 0; column2 < 9; column2++){   
                                //if outside of the box (not one of the previously selected)
                                if(!columnVals.contains(column2)){
                                    //if it contains the candidate
                                    if(mySudoku[rowVals.get(0)][column2].contains(i)){
                                        //remove that candidate
                                        mySudoku[rowVals.get(0)][column2].remove(mySudoku[rowVals.get(0)][column2].indexOf(i));
                                        removedACandidate = true;
                                    }
                                }
                            }
                        }



                        //if all in same column
                        boolean allInSameColumn = false;
                        //if 2 in same column
                        if(columnVals.size() == 2){
                            if(columnVals.get(0) == columnVals.get(1)){
                                allInSameColumn = true;
                            }
                        }
                        //if 3 in same column
                        if(columnVals.size() == 3){
                            if(columnVals.get(0) == columnVals.get(1) && columnVals.get(1) == columnVals.get(2)){
                                allInSameColumn = true;
                            }
                        }
                        if(allInSameColumn){
                            //eliminate values along that column
                            for(int row2 = 0; row2 < 9; row2++){   
                                //if outside of the box (not one of the previously selected)
                                if(!rowVals.contains(row2)){
                                    //if it contains the candidate
                                    if(mySudoku[row2][columnVals.get(0)].contains(i)){
                                        //remove that candidate
                                        mySudoku[row2][columnVals.get(0)].remove(mySudoku[row2][columnVals.get(0)].indexOf(i));
                                        removedACandidate = true;
                                    }
                                }
                            }
                        }
                        //if a candidate was removed, run through box and rook checker
                        if(removedACandidate){
                            candidateLinesCheckerWorks = true; 
                            candidateLinesCheckerWorks = boxChecker(mySudoku);
                            candidateLinesCheckerWorks = rookChecker(mySudoku);
                        }
                    }
                }
            }
        }  
        return candidateLinesCheckerWorks;
    }

    //multiple lines method (candidates can only be in the same 2 lines across 2 boxes, can't be in those two lines for the third box)
    //swordfish method 
    //jellyfish method, swordfish with 4 lines
    //x wing method
    //xyz wing method


    //brute force method
    public static void bruteForceSolver(sudokCell[][] mySudoku){
        //for each level guess
        sudokCell[][] mySudoku2 = copy(mySudoku);
        for(int k = 0; k < numUnsolved(mySudoku) && !solved(mySudoku, false); k++){
            //while sudoku2 is unsolved
            int infiniteLoop = 0;
            int i = 1;
            while(!solved(mySudoku2, false) && infiniteLoop < 100){
                //infiniteLoop++;
                sudokCell[][] testCase = copy(mySudoku2);
                //find i unsolved cell and solve to be random of candidates 
                boolean foundUnsolved = false;
                int row = -1;
                int column = 0;
                int num = 0;
                while(!foundUnsolved){
                    row++;
                    if(row == 9){
                        row = 0;
                        column++;
                    }
                    if(column == 9){
                        row = 0;
                        column = 0;
                    }
                    if(!testCase[row][column].getSolved()){
                        num++;
                        if(num == i){
                            foundUnsolved = true;
                        }
                    }
                }
                i++;
                ArrayList<Integer> possibles = testCase[row][column].getPossibles();
                int randomIndex = (int) (Math.random() * possibles.size());
                testCase[row][column].solve(possibles.get(randomIndex));
                if(i > numUnsolved(testCase)){
                    //System.out.println("i greater than num unsolved");
                    //System.out.println("numUnsolved " + numUnsolved(testCase));
                    i = 1;
                    /*if(numUnsolved(testCase) != 0){
                        solve(testCase, true);
                    }*/
                }
                //if all cells solved but sudoku not solved
                if(numUnsolved(testCase) == 0 && !solved(testCase, false)){
                    //System.out.println("restarted brute force");
                    bruteForceSolver(mySudoku);
                }
                
                for(int j = 0; j < 10; j++){
                    rookChecker(testCase);
                    boxChecker(testCase);
   /*                 onlyCandidateLeftRookChecker(mySudoku);    
                    //onlyCandidateLeftBoxChecker(mySudoku);
                    //nakedCandidateRookChecker(mySudoku);
                    //nakedCandidateBoxChecker(mySudoku);
                    //candidateLinesChecker(mySudoku);
                    //hiddenCandidateChecker(mySudoku);
                    //System.out.println("HeHe");*/
                }
                if(!solved(testCase, false)){
                    //System.out.println("multiple guesses\n numunsolved " + numUnsolved(testCase));
                    mySudoku2 = copy(testCase);
                    infiniteLoop = 100;
                }
                //if the test case is properly solved, make sudoku equal testcase
                if(solved(testCase, false)){
                    printBoard(testCase, false);
                    for(int roww = 0; roww < 9; roww++){
                        for(int columnn = 0; columnn < 9; columnn++){
                            mySudoku[roww][columnn] = testCase[roww][columnn];
                        }
                    }
                }
                 //if all cells solved but sudoku not solved
                 if(numUnsolved(testCase) == 0 && !solved(testCase, false)){
                    //System.out.println("restarted brute force");
                    bruteForceSolver(mySudoku);
                }
            }
        }
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
    public static boolean solved(sudokCell[][] mySudoku, boolean printChecks){
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
        if(printChecks) System.out.println("Rows add up");        
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
        if(printChecks) System.out.println("Columns add up");
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
        if(printChecks) System.out.println("Boxes add up");
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

    //converts int[] of integers length 9 into a 2d array 
    public static int[][] twoDConverter(ArrayList<Integer> oneD){
        int[][] twoD = new int[9][9];
            for(int row = 0; row < 9; row++){
                int oneDRow = oneD.get(row);
                for(int column = 8; column >= 0/*(int)(11 - Math.log(oneD.get(row)))*/; column--){
                    twoD[row][column] = oneDRow % 10;
                    oneDRow = oneDRow / 10;
                }
            }
        return twoD;

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
        if(x == 7){
            int[][] fiveStar2= {{0, 0, 6, 0, 0, 0, 0, 4, 0},
                                {0, 0, 0, 0, 3, 0, 7, 1, 6},
                                {3, 0, 0, 0, 7, 9, 8, 0, 0},
                                {0, 0, 0, 0, 9, 0, 0, 0, 7},
                                {0, 0, 5, 3, 4, 2, 1, 0, 0},
                                {8, 0, 0, 0, 6, 0, 0, 0, 0},
                                {0, 0, 3, 9, 5, 0, 0, 0, 4},
                                {6, 9, 7, 0, 1, 0, 0, 0, 0},
                                {0, 8, 0, 0, 0, 0, 3, 0, 0}};
            return fiveStar2;
        }
        if(x == 8){
            int[][] fiveStar3 =    {{8, 0, 0, 0, 5, 6, 0, 0, 0},
                                    {0, 0, 0, 8, 0, 0, 0, 6, 0},
                                    {9, 0, 0, 3, 4, 0, 1, 0, 0},
                                    {6, 0, 0, 0, 3, 0, 0, 5, 0},
                                    {1, 5, 0, 0, 8, 0, 0, 3, 9},
                                    {0, 2, 0, 0, 9, 0, 0, 0, 7},
                                    {0, 0, 8, 0, 6, 3, 0, 0, 5},
                                    {0, 1, 0, 0, 0, 8, 0, 0, 0},
                                    {0, 0, 0, 5, 2, 0, 0, 0, 4}};
            return fiveStar3;
        }
        if(x == 9){
            ArrayList<Integer> onlineSudokuHard =   new ArrayList<Integer>();
            onlineSudokuHard.add(204010);
            onlineSudokuHard.add(174030000); 
            onlineSudokuHard.add(180049);
            onlineSudokuHard.add(40826);
            onlineSudokuHard.add(60000050);
            onlineSudokuHard.add(43060071);
            onlineSudokuHard.add(400050000);
            onlineSudokuHard.add(80410090);
            onlineSudokuHard.add(90608030);

            return twoDConverter(onlineSudokuHard);
        }
        if(x == 10){
            ArrayList<Integer> fiveStar4 = new ArrayList<Integer>();
            fiveStar4.add(90008);
            fiveStar4.add(41005000);
            fiveStar4.add(60070300);
            fiveStar4.add(602510000);
            fiveStar4.add(403020501);
            fiveStar4.add(37206);
            fiveStar4.add(4050020);
            fiveStar4.add(900130);
            fiveStar4.add(500080000);

            return twoDConverter(fiveStar4);
        }
        if(x == 11){
           /* ArrayList<Integer> name =   new ArrayList<Integer>();
            .add();
            .add(); 
            .add();
            .add();
            .add();
            .add();
            .add();
            .add();
            .add();*/

          //  return twoDConverter(onlineSudokuHard);
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