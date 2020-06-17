package AutomatasFinitos;

/**
 * Esta clase se encarga de guardar los simbolos del alfabeto de un automata y procesarlos según se requiera
 * @author equipo los Javas
 * @version 1.2
 */
public class Alfabeto {
    /**
     * El atributo simbolos, guarda los simbolos del alfabeto del automata respectivo.
     */
    private char[] simbolos;
    
    /**
     * Constructor Inicializa los atributos a partir del arreglo de caracteres.
     * @param simbolos Arreglo de caracteres que contiene los simbolos del alfabeto del automata
     */ 
    public Alfabeto (char[] simbolos){
        this.simbolos=simbolos;
    }
    
    /**
     * Función que devuelve el caracter en una posición dada
     * @param index Posición del valor que se desea conocer
     * @return 
     */
    public char get(int index){
        return simbolos[index];
    }
    
    /**
     * Función que devuelve el número de simbolos que tiene el alfabeto del automata
     * @return 
     */
    public int length(){
        return simbolos.length;
    }
    
    /**
     * Función que genera una cadena aleatoria con los símbolos del alfabeto del automata
     * @param n Longitud de la cadena aleatoria a generar
     */
    String generarCadenaAleatoria(int n){
        String res = "";
        while(n>0){
            res+=simbolos[(int) (Math.random()*simbolos.length)];
            n--;
        }
        return res;
    }

    public char[] getSimbolos() {
        return simbolos;
    }
    
    
}
