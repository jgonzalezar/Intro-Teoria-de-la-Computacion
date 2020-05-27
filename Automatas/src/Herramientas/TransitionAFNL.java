package Herramientas;


import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author ivonn
 */
public class TransitionAFNL {
        ArrayList<HashMap<Character,ArrayList<Integer>>> transicionAFNL;
    
    public TransitionAFNL(int indice) {
        transicionAFNL = new ArrayList<>();
        transicionAFNL.ensureCapacity(indice);
    }
    
    public void add(Character Alphabeto, int Estado, int... estado){
        if(transicionAFNL.get(Estado)==null){
            transicionAFNL.set(Estado, new HashMap<>());
        }
        ArrayList<Integer> state = new ArrayList<>();
        for(int i  = 0; i < estado.length; i++){
            state.add(estado[i]);
        }
        transicionAFNL.get(Estado).put(Alphabeto, state);
    }
    
     public void Add(HashMap letter_states){
        transicionAFNL.add(letter_states);
    }
     
    public int size() {
        return transicionAFNL.size();
    }
    
     public HashMap getState(int i) {
        return transicionAFNL.get(i);
    }
     
    public ArrayList<Integer> getMove(int i,Character c){
        return transicionAFNL.get(i).get(c);
    }
    
}
