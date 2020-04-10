import java.util.*;

public class HiddenCandidateFinderTest {
    public static void main(String[] args){
        ArrayList<sudokCell> test = new ArrayList<sudokCell>();
        sudokCell cell1 = new sudokCell(0);
        sudokCell cell2 = new sudokCell(0);
        sudokCell cell3 = new sudokCell(0);
        sudokCell cell4 = new sudokCell(0);
        sudokCell cell5 = new sudokCell(0);
        cell1.remove(cell1.indexOf(2));
        cell1.remove(cell1.indexOf(3));
        cell1.remove(cell1.indexOf(4));
        cell1.remove(cell1.indexOf(6));
        cell1.remove(cell1.indexOf(7));

        cell2.remove(cell2.indexOf(2));
        cell2.remove(cell2.indexOf(3));
        cell2.remove(cell2.indexOf(4));
        cell2.remove(cell2.indexOf(6));
        cell2.remove(cell2.indexOf(7));
        cell2.remove(cell2.indexOf(8));

        cell3.remove(cell3.indexOf(2));
        cell3.remove(cell3.indexOf(3));
        cell3.remove(cell3.indexOf(4));
        cell3.remove(cell3.indexOf(6));
        cell3.remove(cell3.indexOf(7));

        test.add(cell1);
        test.add(cell2);
        test.add(cell3);
        test.add(cell4);
        test.add(cell5);

        ArrayList<Integer> returned = findHiddenCandidates(test);
        System.out.println("");
    }


    //method for hiddenCandidateChecker, takes in array list, finds the hidden candidates, returns them in an arraylist
    public static ArrayList<Integer> findHiddenCandidates(ArrayList<sudokCell> candidateSet){
        ArrayList<Integer> hiddenCandidates = new ArrayList<Integer>();
        ArrayList[] whereEach = new ArrayList[9];
        for(int i = 0; i < 9; i++){
            whereEach[i] = new ArrayList<Integer>();
        }
        //populates whereEach array 
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