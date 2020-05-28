package Herramientas;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ivonn
 */
public class TransitionAFNL {

    ArrayList<HashMap<Character, ArrayList<Integer>>> transicionAFNL;

    public TransitionAFNL(int indice) {
        transicionAFNL = new ArrayList<>();
        for(int i = 0; i < indice; i++){
            transicionAFNL. add(new HashMap<>());
        }
        System.out.println(transicionAFNL.size());
    }

    public void add(Character Alphabeto, int Estado, int... estado) {
        ArrayList<Integer> state = new ArrayList<>();
        for (int i = 0; i < estado.length; i++) {
            state.add(estado[i]);
        }
        transicionAFNL.get(Estado).put(Alphabeto, state);
    }

    public void Add(HashMap letter_states) {
        transicionAFNL.add(letter_states);
    }

    public int size() {
        return transicionAFNL.size();
    }

    public ArrayList<Integer> getMove(int i, Character c) {
        return transicionAFNL.get(i).get(c);
    }

}
