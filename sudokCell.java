import java.util.*;

//import org.graalvm.compiler.graph.Node.Successor;

//cell object
public class sudokCell{
    //constructors 
    private ArrayList<Integer> possibles = new ArrayList<Integer>();
    private boolean solved = false;
    public sudokCell(int value){
        if(value == -1 || value == 0){
            for(int i = 1; i <= 9; i++){
                possibles.add(i);
            }
        }
        else{
            possibles.add(value);
            solved = true;
        }
        
    }
    public sudokCell(){
        for(int i = 1; i <= 9; i++){
            possibles.add(i);
        }
    }
    public sudokCell(sudokCell obj){
        this(obj.getPossibles(), obj.getSolved());
    }
    public sudokCell(ArrayList<Integer> possibles, boolean solved){
        for(int i = 0; i < possibles.size(); i++){
            this.possibles.add(possibles.get(i));
        }
        this.solved = solved;
    }

    public int indexOf(int val){
        return possibles.indexOf(val);
    }
    //removes a candidate 
    public boolean remove(int toRemove){

        //to be commented out
        boolean toPrint = false;
        



        possibles.remove(toRemove);
        if(possibles.size() == 1){
            solved = true;
        }

        //to be commented out
        if(possibles.size() == 0){
            System.out.println("Removed all possibilities");
            toPrint = true;
        }
        return toPrint;
    }

    //removes all other candidates
    public void solve(int solution){
        while(possibles.size() > 0){
            possibles.remove(0);
        }
        possibles.add(solution);
        solved = true;
    }

    //checks two cells for the same possibilities
    public boolean samePossible(sudokCell other){
        if(this.possibles.size() != other.possibles.size()){
            return false;
        }
        for(int i = 0; i < this.possibles.size(); i++){
            if(this.possibles.get(i) != other.possibles.get(i)){
                return false;
            }
        }
        return true;
    }

    //toString
    public String toStringWithoutCands(){
        String toReturn = "";
        if(solved){
            return toReturn + possibles.get(0) + " ";
        }
        else{
            return "  ";
        }
    }

    public String toString(){
        String toReturn = "";
        if(solved){
            return toReturn + possibles.get(0) + "\t";
        }
        else{
            for(int i = 0; i < possibles.size(); i++){
                toReturn = toReturn + possibles.get(i);
            }
            return toReturn + "\t";
        }
    }

    //contains
    public boolean contains(int x){
        return possibles.contains(x);
    }
    //accessors
    public boolean getSolved(){
        return solved;
    }

    public int getVal(){
        if(solved){
            return possibles.get(0);
        }
        else{
            return -1;
        }
    }

    public int getVal(int index){
        return possibles.get(index);
    }

    public ArrayList<Integer> getPossibles(){
        return possibles;
    }
    public ArrayList<Integer> getPossibles(sudokCell obj){
        return obj.getPossibles();
    }

    public int size(){
        return possibles.size();
    }
     

}