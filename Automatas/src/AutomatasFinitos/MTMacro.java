package AutomatasFinitos;

import Herramientas.ParPila;
import Herramientas.TransitionTM;
import LectoresYProcesos.InteraccionesAutomas.Lecto;
import ProcesamientoCadenas.ProcesamientoCadenaTM;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Esta clase es el automata finito determinista en este automata se pueden
 * realizar procesamientos de palabras sobre si mismo, confirmando si pertenece
 * o no al lenguaje, e incluso se puede recibir una respuesta con pasos
 * incluidos
 *
 * @author equipo los Javas
 * @version 1.2
 */
public class MTMacro extends MT {

   
    /**
     * Constructor Inicializa los atributos a partir del archivo de texto.
     *
     * @param nombreArchivo url del archivo donde se encuentra el automata
     * @throws Error error generado a la hora de leer el automata,
     * @throws java.io.FileNotFoundException en caso de que el archivo no sea
     * encontrado por el scanner
     */
    public MTMacro(String nombreArchivo) throws Error, FileNotFoundException {
        Lecto lec = Lecto.inicio;
        ArrayList<Character> alpha = null;
        ArrayList<String> W = null;
        String W0 = null;
        ArrayList<String> G = null;
        ArrayList<Character> T = null;
        Character bl = null;

        TransitionTM Deltos = null;

        Scanner sca = new Scanner(new File(nombreArchivo));
        while (sca.hasNextLine()) {
            String lin = sca.nextLine();
            switch (lin) {
                case "#alphabet":
                    lec = Lecto.alfabeto;
                    alpha = new ArrayList<>();
                    break;
                case "#states":
                    lec = Lecto.estados;
                    W = new ArrayList<>();
                    break;
                case "#initial":
                    if (W == null) {
                        throw new Error("primero debe iniciarse los estados");
                    }
                    lec = Lecto.estadoinicial;
                    break;
                case "#accepting":
                    if (W == null) {
                        throw new Error("primero debe iniciarse los estados");
                    }
                    lec = Lecto.estadoFin;
                    G = new ArrayList<>();
                    break;
                case "#alphabetC":
                    lec = Lecto.pilaAlphabeto;
                    T = new ArrayList<>();
                    break;
                case "#blancS":
                    if (T == null) {
                        throw new Error("primero debe iniciarse el alfabeto de cinta");
                    }
                    if (alpha == null) {
                        throw new Error("primero debe iniciarse el alfabeto de entrada");
                    }
                    lec = Lecto.blanc;
                    break;
                case "#transitions":
                    if (alpha == null) {
                        throw new Error("primero debe iniciarse el alfabeto");
                    }
                    if (W == null) {
                        throw new Error("primero debe iniciarse los estados");
                    }
                    if (W0 == null) {
                        throw new Error("primero debe dar el estado inicial");
                    }
                    if (G == null) {
                        throw new Error("primero debe dar los estados finales");
                    }
                    if (T == null) {
                        throw new Error("primero debe dar el alphabeto de la cinta");
                    }
                    if (bl == null) {
                        throw new Error("primero debe dar el simbolo \"blanco\"");
                    }
                    lec = Lecto.transicion;
                    Deltos = new TransitionTM();
                    break;
                default:
                    switch (lec) {
                        case alfabeto:
                            if (lin.length() < 2) {
                                alpha.add(lin.charAt(0));
                            } else {
                                int a = lin.charAt(0);
                                int b = lin.charAt(2);
                                int c = b - a;
                                for (int i = 0; i <= c; i++) {
                                    char d = (char) (a + i);
                                    alpha.add(d);
                                }
                            }
                            break;
                        case estados:
                            W.add(lin);
                            break;
                        case estadoinicial:
                            if (!W.contains(lin)) {
                                throw new Error("el estado inicial debe pertenecer a los estados del automata");
                            }
                            W0 = lin;
                            break;
                        case estadoFin:
                            if (!W.contains(lin)) {
                                throw new Error("el estado de acceptacion debe pertenecer a los estados del automata" + lin);
                            }
                            G.add(lin);
                            break;
                        case pilaAlphabeto:
                            if (lin.length() < 2) {
                                T.add(lin.charAt(0));
                            } else {
                                int a = lin.charAt(0);
                                int b = lin.charAt(2);
                                int c = b - a;
                                for (int i = 0; i <= c; i++) {
                                    char d = (char) (a + i);
                                    T.add(d);
                                }
                            }

                            break;
                        case transicion:
                            String[] origin = lin.split(":");
                            String estado1 = origin[0];
                            if (!W.contains(estado1)) {
                                throw new Error("el estado del que se parte debe pertenecer a la lista de estados");
                            }
                            String[] origin2 = origin[1].split(Pattern.quote("|"));
                            String intro = origin2[0];
                            //if(pilaT!='$') if(!T.contains(pilaT))throw new Error("el caracter de Tope de la pila no pertenece al alfabeto de la pila");
                            Character alph = intro.charAt(0);
                            if (!T.contains(alph)) {
                                throw new Error("el caracter  no pertenece al alfabeto de cinta");
                            }
                            
                            Character pilN = origin2[1].charAt(0);
                            if (!T.contains(pilN)) {
                                throw new Error("el caracter a leer no pertenece al alfabeto de la cinta "+pilN);
                            }
                            String go = origin2[1].substring(1);
                            if (!W.contains(go.substring(2))) {
                                throw new Error("el estado de llegada debe pertenecer a la lista de estados");
                            }
                            Deltos.add(alph, estado1, ' ', go, pilN);
                            break;
                        case blanc:
                            if (alpha.contains(lin.charAt(0))) {
                                throw new Error("el caracter  no puede pertenece al alfabeto de escritura");
                            }
                            if (!T.contains(lin.charAt(0))) {
                                throw new Error("el caracter no pertenece al alfabeto de cinta");
                            }
                            bl=lin.charAt(0);
                            break;
                        default:
                            break;
                    }

            }
        }
        T.add(Blanc);
        Set<Character> ests = new HashSet<>(alpha);
        alpha.clear();
        alpha.addAll(ests);
        T.addAll(ests);
        ests = new HashSet<>(T);
        T.clear();
        T.addAll(ests);
        Set<String> est = new HashSet<>(W);
        W.clear();
        W.addAll(est);
        char[] ad = new char[alpha.size()];
        for (int i = 0; i < alpha.size(); i++) {
            ad[i] = alpha.get(i);
        }
        this.Sigma = new Alfabeto(ad);
        this.Q = W;
        this.q0 = Q.indexOf(W0);
        this.F = new ArrayList<>();
        for (String f : G) {
            F.add(Q.indexOf(f));
        }
        Gamma = new char[T.size()];
        for (int i = 0; i < T.size(); i++) {
            Gamma[i] = T.get(i);
        }
        Blanc = bl;
        this.Delta = Deltos;
    }

    /**
     * Procesa una palabra para decir si pertenece al lenguaje
     *
     * @param word palabra a determinar
     * @return la aceptacion del lenguaje
     */

    public String procesarCadenad(String word) {
        return prosCaden(word).getLastCadena();
    }

    /**
     * Procesa una cadena para decir si pertenece al lenguaje
     *
     * @param word cadena a determinar
     * @return la aceptacion del lenguaje
     */

    public String procesarCadenad(char[] word) {
        return procesarCadenad(Arrays.toString(word));
    }

    /**
     * Procesa la cadena e imprime los estados en consola los caminos que va
     * tomando al procesar cada símbolo.
     *
     * @param word cadena de caracteres a evaluar
     * @return la aceptacion del lenguaje
     */
    
    public String procesarCadenadConDetalles(char[] word) {
        return procesarCadenadConDetalles(Arrays.toString(word));
    }

    /**
     * Procesa la palabra e imprime los estados en consola los caminos que va
     * tomando al procesar cada símbolo.
     *
     * @param word palabra a evaluar
     * @return la cadena es o no aceptada
     */
    public String procesarCadenadConDetalles(String word) {
        ProcesamientoCadenaTM fin = prosCaden(word);
        System.out.println(fin.pasos() + fin.EsAceptada());
        return fin.getLastCadena();
    }

    /**
     * evalua una palabra y retorna su procesamiento en cadena
     *
     * @param word
     * @return procesamiento dado a la cadena de su camino y su resultado de
     * aceptacion
     * @see ProcesamientoCadenaAFD
     */
    /**
     * La funcion procesarListaCadenas procesa cada una de una list de cadenas
     * dada y la guarda dentro de un archivo con el nombre dado, y imprimirla en
     * consola o no
     *
     * @param listaCadenas lista de cadenas a evaluar
     * @param nombreArchivo nombre del archivo donde se guardara
     * @param imprimirPantalla booleano que decide si se imprimira o no en
     * consola
     */
    @Override
    public void procesarListaCadenas(String[] listaCadenas, String nombreArchivo, boolean imprimirPantalla) {
        FileWriter fichero1 = null;
        PrintWriter pw1 = null;
        try {
            File nombre = new File(nombreArchivo);
            if (!nombre.exists()) {
                nombre.createNewFile();
            }
            fichero1 = new FileWriter(nombre);
            pw1 = new PrintWriter(fichero1);
            for (int i = 0; i < listaCadenas.length; i++) {
                ProcesamientoCadenaTM res = prosCaden(listaCadenas[i]);
                String pas = res.pasos();
                String res2 = listaCadenas[i] + "\t" + pas + "\t" + res.getLastCadena();
                pw1.println(res2);
                if (imprimirPantalla) {
                    System.out.println(res2);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero1) {
                    fichero1.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * La Funcion delta recibe una palabra y crea su recurcion mas pequeña para
     * luego desde la misma crear un camino y empezar a recorrerlo segun el
     * ultimo caracter de la recursion hasta generar todo el camino de la
     * palabra
     *
     * @param word palabra a ser evaluada
     * @return procesamiento recursivo de la palabra dada
     * @see ProcesamientoCadenaAFD
     */
    private ProcesamientoCadenaTM Delta(String word) {
        ProcesamientoCadenaTM f = new ProcesamientoCadenaTM(word,Q.get(q0),Blanc);
        if (word == null || word.length() == 0) {
            f.setEsAceptada(F.contains(q0));
            return f;
        }
        try{
            while(true){
                ParPila hey = f.getlastPaso();
                hey= Delta.cambio(hey.getPila(), hey.getEstado(), ' ');
                f.add(hey.getEstado().substring(2), hey.getPila(), hey.getEstado().substring(0, 1));
            }
        }catch(NullPointerException | IndexOutOfBoundsException e){
            return f;
        }
    }
    /**
     * La funcion Finish Realiza la comprobacion de un ProcesamientodeCadenaAFD
     * para comprobar su ultimo estado y retornar el procesamiento con la
     * variable de aceptacion segun la pertenencia del estado final dentro de
     * los aceptados
     *
     * @param q procesamiento previo de alguna cadena
     * @return Procesamiento modificado
     * @see ProcesamientoCadenaAFD
     */
    private ProcesamientoCadenaTM Finish(ProcesamientoCadenaTM q) {
        q.setEsAceptada(F.contains(Q.indexOf(q.gatLastSta())));

        return q;
    }

    private ProcesamientoCadenaTM prosCaden(String listaCadena) {
        return Finish(Delta(listaCadena));
    }

    @Override
    public String toString() {
        String cadena = "#!mt";
        cadena +=super.toString(); //To change body of generated methods, choose Tools | Templates.
        cadena+="#alphabetC\n";
        char ini= Gamma[0];
        char fin= Gamma[0];
        int ac = 1;
        while(ac<Gamma.length){
            if(fin+1==Gamma[ac]){
                fin =Gamma[ac];
            }else{
                if(fin!=ini){
                    cadena+=ini+"-"+fin+"\n";
                }else{
                    cadena+=fin+"\n";
                    fin=ini=Gamma[ac];
                }
            }
            ac++;
        }
        if(fin!=ini){
            cadena+=ini+"-"+fin+"\n";
        }else{
            cadena+=fin+"\n";   
        }
        cadena+="#blancS\n";
        cadena+=Blanc+"\n";
        
        cadena+="#transitions\n";
        for (int i = 0; i < Q.size(); i++) {
            for (int j = 0; j < Gamma.length; j++) {
                String line = Q.get(i)+":"+Gamma[j]+"|";
                ParPila e = Delta.cambio(Gamma[j], Q.get(i),' ');
                if(e!=null){
                    line+=e.getPila()+e.getEstado();
                    cadena+=line;
                }
            }
        }
        
        return cadena;
    }
    
    
}
