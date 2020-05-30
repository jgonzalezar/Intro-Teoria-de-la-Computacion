package Herramientas;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *esta clase representa la estructura de la transicion de los automatas finitos no deterministas con trnsiciones lambda.
 * @author ivonn
 */
public class TransitionAFNL {
    /**
 * @param transicionAFNL Transiciones;
 */
    ArrayList<HashMap<Character, ArrayList<Integer>>> transicionAFNL;
  /**
 * constructor de un arraylist de hashmaps de tamaño indice
 * @param indice numero de estados del automata;
 */
    public TransitionAFNL(int indice) {
        transicionAFNL = new ArrayList<>();
        for(int i = 0; i < indice; i++){
            transicionAFNL. add(new HashMap<>());
        }
    }
    /**
 * Agrega a cada uno de los estados una lista de parejas de un caracter del alfabeto del utomata y una lista de estados a los ue transiciona con este caracter.
 * @param Alphabeto caracter del alfabeto del automata;
 * @param Estado estado al cual se le estan agregndo valores;
 * @param indice lista de los estados a los que estado transiciona con el caracter dado;
 */

    public void add(Character Alphabeto, int Estado, int... estado) {
        ArrayList<Integer> state = new ArrayList<>();
        for (int i = 0; i < estado.length; i++) {
            state.add(estado[i]);
        }
        transicionAFNL.get(Estado).put(Alphabeto, state);
    }
/**
 * Agrega hashmaps de transicion a cada estado del que tenemos 
 
 */
    public void Add(HashMap letter_states) {
        transicionAFNL.add(letter_states);
    }
    /**
 * retorna el tamaño del array de hashmaps
 */
    public int size() {
        return transicionAFNL.size();
    }
  /**
 * retorna la lista de los estados a los que un estado va con un caracter dado
 *@param c caracter del alfabeto del automata que se evalua desde el estado i;
 * @param i estado en el cual estamos;
 */
    public ArrayList<Integer> getMove(int i, Character c) {
        return transicionAFNL.get(i).get(c);
    }

}
