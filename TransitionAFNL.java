package Herramientas;


import Herramientas.Respuesta;
import Herramientas.Transition;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;


/**
 *
 * @author ivonn
 */
public class TransitionAFNL {
    ArrayList<HashMap<Character ,ArrayList<Integer>>> transicionAFNL;
    
    public TransitionAFNL() {
        transicionAFNL = new ArrayList<>();
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
    
}
