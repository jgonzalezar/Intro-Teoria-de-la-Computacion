package AutomatasFinitos;

import Herramientas.Transition;
import Herramientas.Transitions;
import LectoresYProcesos.InteraccionesAutomas.Lecto;
import ProcesamientoCadenas.ProcesamientoCadenaAFD;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import visuall.WindowsS;


/**
 * Esta clase es el automata finito determinista
 * en este automata se pueden realizar procesamientos de palabras sobre si mismo, confirmando si pertenece o no al lenguaje, e incluso se puede recibir una respuesta con pasos incluidos
 * 
 * @author equipo los Javas
 * @version 1.2
 */
public class AFD {
    /**
     * El atributo Sigma representa el alfabeto del automata.
     */
    
    protected Alfabeto Sigma;
    /**
     * El atributo Q representa el conjunto de estados que pertenecen al automata.
     */
    protected ArrayList<String> Q;
    /**
     * El atributo q0 representa el estado inicial del automata.
     */
   
    protected Integer q0;
    /**
     * El atributo F representa el conjunto de estados de aceptación del automata.
     */
    
    protected ArrayList<Integer> F;
    /**
     * El atributo Delta representa las transiciones del automata.
     */
    protected Transitions Delta;
    /**
     * El atributo estadosInaccesibles se encarga de guardar los estados inaccesibles del automata.
     */
    protected ArrayList<String> estadosInaccesibles;
    /**
     * El atributo estadosLimbo se enecarga de guardar los estados limbo del automata.
     */
    protected ArrayList<String> estadosLimbo;

    /**
     * constructor vacio
     */
    
    protected AFD() {
        estadosInaccesibles=new ArrayList<>();
        estadosLimbo=new ArrayList<>();
    }

    /**
     * Constructor, inicializa los atributos.
     * @param Sigma Alfabeto
     * @param Q Conjunto de estados
     * @param q0 Estado inicial
     * @param F Estados de aceptación
     * @param Delta Transiciones
     */  
    public AFD(Alfabeto Sigma, ArrayList<String> Q, Integer q0, ArrayList<Integer> F, Transitions Delta) {
        this.Sigma = Sigma;
        this.Q = Q;
        this.q0 = q0;
        this.F = F;
        this.Delta = Delta;
        this.estadosInaccesibles=new ArrayList<>();
        this.estadosLimbo=new ArrayList<>();
    }    
    
    /**
     * Constructor Inicializa los atributos a partir del archivo de texto.
     * @param nombreArchivo url del archivo donde se encuentra el automata
     * @throws Error error generado a la hora de leer el automata, 
     * @throws java.io.FileNotFoundException en caso de que el archivo no sea encontrado por el scanner
     */    
    public AFD(String nombreArchivo) throws Error, FileNotFoundException{
        Lecto lec = Lecto.inicio;
        ArrayList<Character> alpha = null;
        ArrayList<String> W = null;
        String W0 = null;
        ArrayList<String> G = null;
        Transition Deltos = null;
        
        Scanner sca = new Scanner(new File(nombreArchivo));
        while(sca.hasNextLine()){
            String lin = sca.nextLine();
            switch(lin){
                case "#alphabet":
                    lec = Lecto.alfabeto;
                    alpha = new ArrayList<>();
                    break;
                case "#states":
                    if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                    lec = Lecto.estados;
                    W = new ArrayList<>();
                    break;
                case "#initial":
                    if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                    if(W==null) throw new Error("primero debe iniciarse los estados");
                    lec = Lecto.estadoinicial;
                    break;
                case "#accepting":
                    if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                    if(W==null) throw new Error("primero debe iniciarse los estados");
                    if(W0==null) throw new Error("primero debe dar el estado inicial");
                    lec = Lecto.estadoFin;
                    G = new ArrayList<>();
                    break;
                case "#transitions":
                    if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                    if(W==null) throw new Error("primero debe iniciarse los estados");
                    if(W0==null) throw new Error("primero debe dar el estado inicial");
                    if(G==null) throw new Error("primero debe dar los estados finales");
                    lec = Lecto.transicion;
                    Deltos = new Transition();
                    break;
                default:
                    switch(lec){
                        case alfabeto:
                            if(lin.length()<2){
                                if(!alpha.contains(lin.charAt(0))){
                                    alpha.add(lin.charAt(0));
                                }
                                
                            }else{
                                int a = lin.charAt(0);
                                int b = lin.charAt(2);
                                int c = b-a;
                                for (int i = 0; i <=c; i++) {
                                    char d = (char) (a+i);
                                    if(!alpha.contains(d)){
                                        alpha.add(d);
                                    }
                                }
                            }
                            break;
                        case estados:
                            if(W.contains(lin))throw new Error("el estado "+ lin+ "ya existe en el automata");
                            W.add(lin);
                            break;
                        case estadoinicial:
                            if(!W.contains(lin))throw new Error("el estado debe pertenecer a los estados del automata");
                            W0=lin;
                            break;
                        case estadoFin:
                            if(!W.contains(lin))throw new Error("el estado debe pertenecer a los estados del automata");
                            G.add(lin);
                            break;
                        case transicion:

                            String[] origin = lin.split(":");
                            String estado1 = origin[0];
                            if(!W.contains(estado1))throw new Error("el estado del que se parte debe pertenecer a los estados del automata");
                            String[] origin2 = origin[1].split(">");
                            String alpfa = origin2[0];
                            if(!alpha.contains(alpfa.charAt(0)))throw new Error("el caracter de activacion debe pertenecer al alfabeto");
                            String estado2 = origin2[1];
                            if(!W.contains(estado2))throw new Error("el estado de llegada debe pertenecer a los estados del automata");
                            Deltos.add(alpfa.charAt(0), estado1, estado2);
                            break;
                        default:
                            break;
                    }
            }
        }
        Collections.sort(alpha);
        char[] ad =new char[alpha.size()];
        for (int i = 0; i < alpha.size(); i++) {
            ad[i]=alpha.get(i);
        }
        this.Sigma = new Alfabeto(ad);
        this.Q = W;
        this.q0 = Q.indexOf(W0);
        Set<String> no = new HashSet<>(G);
        G.clear();
        G.addAll(no);
        this.F = new ArrayList<>();
        for(String f:G){
            F.add(Q.indexOf(f));
        }
        
        try{
            for (int i = 0; i < Sigma.length(); i++) {
                for (int j = 0; j < Q.size(); j++) {
                    String get = Deltos.cambio(Sigma.get(i), Q.get(j));
                    if(get==null){
                        throw new NullPointerException();
                    }
                }
            }
        }catch(Error | Exception e){
            throw new Error("Faltan transiciones a posibles estados limbos");
        }   
        this.Delta = Deltos;
        this.estadosInaccesibles=new ArrayList<>();
        this.estadosLimbo=new ArrayList<>();
    }
    
    /**
     * Procesa una palabra para decir si pertenece al lenguaje
     * @param word palabra a determinar
     * @return la aceptacion del lenguaje 
     */
    public boolean procesarCadena(String word){
        return prosCaden(word).EsAceptada();
    }
    
    /**
     * Procesa una cadena para decir si pertenece al lenguaje
     * @param word cadena a determinar
     * @return la aceptacion del lenguaje
     */
    public boolean procesarCadena(char[] word){
        return procesarCadena(Arrays.toString(word));
    }
    
    /**
     * Procesa la cadena e imprime los estados en consola los caminos que va tomando al procesar cada símbolo.
     * @param word cadena de caracteres a evaluar
     * @return la aceptacion del lenguaje
     */
    public boolean procesarCadenaConDetalles(char[] word){
        return procesarCadenaConDetalles(Arrays.toString(word));
    }
    
    /**
     * Procesa la palabra e imprime los estados en consola los caminos que va tomando al procesar cada símbolo.
     * @param word palabra a evaluar
     * @return la cadena es o no aceptada
     */
    public boolean procesarCadenaConDetalles(String word){
        ProcesamientoCadenaAFD fin =prosCaden(word);
        String list = fin.pasos()+fin.EsAceptada();
        System.out.println(list);
        JOptionPane.showMessageDialog(null, "el camino resultante y la aceptacion de la cadena es\n"+list, "Cadenas Dadas", JOptionPane.INFORMATION_MESSAGE);
        return fin.EsAceptada();
    }
    public ProcesamientoCadenaAFD porsWhitProsCaden(String word){
        return prosCaden(word);
    }
    /**
     * evalua una palabra y retorna su procesamiento en cadena
     * @param word 
     * @return procesamiento dado a la cadena de su camino y su resultado de aceptacion
     * @see ProcesamientoCadenaAFD
     */
    private ProcesamientoCadenaAFD prosCaden(String word){
        return Finish(Delta(word));
    }
    
    /**
     * La funcion procesarListaCadenas procesa cada una de una list de cadenas dada y la guarda dentro de un archivo con el nombre dado, y imprimirla en consola o no
     * @param listaCadenas lista de cadenas a evaluar 
     * @param nombreArchivo nombre del archivo donde se guardara 
     * @param imprimirPantalla booleano que decide si se imprimira o no en consola
     */
    public void procesarListaCadenas(String[] listaCadenas,String nombreArchivo,boolean imprimirPantalla){
        FileWriter fichero1 = null;
        PrintWriter pw1;
        try
        {
            String list="";
            File nombre = new File(nombreArchivo);
            if(!nombre.exists())nombre.createNewFile();
            fichero1 = new FileWriter(nombre);
            pw1 = new PrintWriter(fichero1);
            for (String listaCadena : listaCadenas) {
                ProcesamientoCadenaAFD res = prosCaden(listaCadena);
                String pas =res.pasos();
                String res2 = listaCadena + "\t" + pas + "\t" + res.EsAceptada();
                pw1.println(res2);
                if(imprimirPantalla){
                    System.out.println(res2);
                    list+=res2+"\n";
                }
            }
            if(imprimirPantalla)
                JOptionPane.showMessageDialog(null, "Los caminos realizados son: \n" + list, "Cadenas Dadas", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
        } finally {          
           try {
                if (null != fichero1){
                   fichero1.close();
                }
           } catch (IOException e2) {
           }
        }
    }
    
    /**
     * Funcion que busca los estados limbo del automata y los guarda en el atributo estadosLimbo del automata
     */
    public void hallarEstadosLimbo(){
        boolean finished;
        ArrayList<String> toEliminate = new ArrayList<>();
        
        for(int i=0; i<Q.size();i++){
            if(!F.contains(i)){
                estadosLimbo.add(Q.get(i));
            }
        }
        do{
            toEliminate.clear();
            for(int i=0;i<estadosLimbo.size();i++){
                for(int j=0;j<Sigma.length();j++){
                    String changeState = Delta.cambio(Sigma.get(j), estadosLimbo.get(i));
                    if(!estadosLimbo.contains(changeState) || toEliminate.contains(changeState)){
                        toEliminate.add(estadosLimbo.get(i));
                    }
                }
            }
            estadosLimbo.removeAll(toEliminate);
            finished = !toEliminate.isEmpty();
        }while(finished);
    }
    
    /**
     * Funcion que busca y elimina los estados inaccesibles del automata
     */
    public void eliminarEstadosInaccesibles(){
        hallarEstadosInaccesibles();
        for(int i=0;i<estadosInaccesibles.size();i++){
            for(int j=0;j<F.size();j++){
                if(Q.indexOf(estadosInaccesibles.get(i))==F.get(j)){
                    F.remove(j);
                }
                if(Q.indexOf(estadosInaccesibles.get(i))<F.get(j)){
                    F.set(j,F.get(j)-1);
                }
            }
            Delta.remove(estadosInaccesibles.get(i));
            Q.remove(estadosInaccesibles.get(i));
            
        }
    }
    
    /**
     * Funcion que busca los estados inaccesibles y los guarda en el atributo estadosInaccesibles del automata
     */
    public void hallarEstadosInaccesibles(){
        estadosInaccesibles.add(Q.get(q0));
        identificarEstadosAccesibles(Q.get(q0));
        ArrayList<String> estadosAccesibles = new ArrayList<>(estadosInaccesibles);
        estadosInaccesibles.clear();
        for(int i=0;i<Q.size();i++){
            if(!estadosAccesibles.contains(Q.get(i))){
                estadosInaccesibles.add(Q.get(i));
            }
        }
    }

    private void identificarEstadosAccesibles(String state) {
        for(int i=0;i<Sigma.length();i++){
            String Stadogo = Delta.cambio(Sigma.get(i), state);
            if(!state.equals(Stadogo) && !estadosInaccesibles.contains(Stadogo)){
                estadosInaccesibles.add(Stadogo);
                identificarEstadosAccesibles(Stadogo);
            }
        }
    }    
    
    /**
     * La Funcion delta recibe una palabra y crea su recurcion mas pequeña para luego desde la misma crear un camino y empezar a recorrerlo segun el ultimo caracter de la recursion hasta generar todo el camino de la palabra
     * @param word palabra a ser evaluada
     * @return procesamiento recursivo de la palabra dada
     * @see ProcesamientoCadenaAFD
     */
    private ProcesamientoCadenaAFD Delta(String word) {
        ProcesamientoCadenaAFD f = new ProcesamientoCadenaAFD(word);
        f.add(Q.get(q0));
        if(word==null||word.length()==0){   
            f.setEsAceptada(F.contains(q0));
            f.setCadena(word);
            return  f;
        }
        if(word.length()==1){
            return Delta(f,word.charAt(0));
        }
        ProcesamientoCadenaAFD det = Delta(word.substring(0, word.length()-1));
        det.setCadena(word);
        return Delta(det,word.charAt(word.length()-1));
    }
    
    /**
     * La Funcion delta realiza el movimiento de cambio de estado segun un caracter del arfabeto por medio de la funcion de transicion y el ultimo paso procesado
     * @param i Procesamiento de Cadena que marca el camino llevado actualmente
     * @param u caracter que fue leido e indica el cambio
     * @return un procesamiento con el cambio por la cadena añadido
     * @see ProcesamientoCadenaAFD
     */
    private ProcesamientoCadenaAFD Delta(ProcesamientoCadenaAFD i, char u) {
        String tas = Delta.cambio(u,i.getlastPaso());
        i.add(tas);
        return i;
    }
    
    /**
     * La funcion Finish Realiza la comprobacion de un ProcesamientodeCadenaAFD para comprobar su ultimo estado y retornar el procesamiento con la variable de aceptacion segun la pertenencia del estado final dentro de los aceptados
     * @param q procesamiento previo de alguna cadena
     * @return Procesamiento modificado
     * @see ProcesamientoCadenaAFD
     */
    private ProcesamientoCadenaAFD Finish(ProcesamientoCadenaAFD q) {
        q.setEsAceptada(F.contains(Q.indexOf(q.getlastPaso())));
        return q;
    }

    /**
     *Devuelve la cadena como una lista de caracteres ,verifica si los caracteres ingresados por el usuario
     * para procesar una cadena pertenecen al alfabeto.
     * @param cadena cadena a evaluar.
     * @return Lista de caracteres que no corresponden al alfabeto. 
     */
    public ArrayList<Character> ponerCadena(String cadena){
        ArrayList<Character> asd = new ArrayList<>();
        for (int i = 0; i < cadena.length(); i++) {
            boolean d = false;
            for(int j=0;j<Sigma.length();j++) {
                if(Sigma.get(j)==cadena.charAt(i)) d=true;
            }
            if(!d) asd.add(cadena.charAt(i));
        }
       
        Set<Character> hashSet = new HashSet<>(asd);
        asd.clear();
        asd.addAll(hashSet);
        return asd;
    }
    
    /**
     * Funcion que realiza la simplificación del AFD actual
     * @return AFD simplificado con el método visto en clase 
     */
    public AFD simplificar(){
        int tam=Q.size();
        boolean Table[][] = new boolean[tam][tam];
        boolean change=false;
        ArrayList<Integer>  estados = new ArrayList<>();
        for (int i = 0; i < tam; i++) {
            estados.add(i);
            for (int j = i; j < tam; j++) {
                boolean ee=F.contains(i)==F.contains(j);
                if(!ee){
                    change=true;
                }
                Table[i][j]=ee;
                Table[j][i]=ee;
            }
        }
        while(change){
            change=false;
            for (int i = 0; i < tam; i++) {
                for (int j = i+1; j < tam; j++) {
                    if(Table[i][j]){
                        for (int k = 0; k < Sigma.length(); k++) {
                            char let = Sigma.get(k);
                            int uno=Q.indexOf(Delta.cambio(let, Q.get(i)));
                            int dos=Q.indexOf(Delta.cambio(let, Q.get(j)));
                            if(!Table[uno][dos]){
                                change=true;
                                Table[i][j]=false;
                                Table[j][i]=false;
                                k=Sigma.length();
                            }

                        }
                    }
                }
            }
        }
        String Table2[][] = new String[tam][tam];
        String qs[] = new String[tam];
        for (int i = 0; i < tam; i++) {
            String line="";
            Table2[i][i]=qs[i]=Q.get(i);
            for (int j = 0; j < i; j++) {
                if(Table[j][i]){
                    Table2[i][j]="X";
                    line+="X";
                }else{
                    Table2[i][j]=" ";
                    line+="_";
                }
                line+="\t";
            }
            
            
            line+=Q.get(i);
            System.out.println(line);
        }
        
        WindowsS table = new WindowsS("tabla de simplificacion", Table2, qs);
        table.Simulat();
        ArrayList<String> Q2=new ArrayList<>();
        ArrayList<Integer> F2=new ArrayList<>();
        
        int q02=q0;
        while(!estados.isEmpty()){
            int estd=estados.get(0);
            ArrayList<String> equals = new ArrayList<>();
            if(F.contains(estd))F2.add(Q2.size());
            for (int i = 0; i < tam; i++) {
                if(Table[estados.get(0)][i]){
                    equals.add(Q.get(i));
                    if(i!=estados.get(0)){
                        boolean rev=estados.remove((Integer)i);
                    }
                    if(i==q0)q02=Q2.size();
                }
            }
            Integer ss=estados.remove(0);
            String name="{";
            for (int i = 0; i < equals.size()-1; i++) {
                name+=equals.get(0)+",";
            }
            name+=equals.get(equals.size()-1)+"}";
            Q2.add(name);
        }
        Transition Delta2 =new Transition();
        Q2.forEach((q) -> {
            String  q1="";
            for(String qq:Q){
                if(q.contains(qq)){
                    q1=qq;
                    break;
                }
            }
            for(int i=0;i<Sigma.length();i++){
                
                String dd = Delta.cambio(Sigma.get(i), q1);
                for(String qa:Q2){
                    if(qa.contains(dd)){
                        Delta2.add(Sigma.get(i), q, qa);
                    }
                }
            }
            
        });
        return new AFD(Sigma, Q2, q02, F2, Delta2);
    }

    /**
     * Función que retorna una cadena con toda la información del automata que hace el llamado de la función
     * @return String - Cadena que contiene toda la información del automata actual
     */
    @Override
    public String toString() {
        String automat="#!dfa\n";
        automat+=Sigma.toString();
        automat+="#states\n";
        automat = Q.stream().map((string) -> string+"\n").reduce(automat, String::concat);
        automat+="#initial\n";
        automat+=Q.get(q0)+"\n";
        automat+="#accepting\n";
        automat = F.stream().map((integer) -> {
            return Q.get(integer)+"\n";
        }).reduce(automat, String::concat);
        automat+=Delta.toString();
        return automat;
    }

    public Alfabeto getSigma() {
        return Sigma;
    }

    public ArrayList<String> getQ() {
        return Q;
    }

    public Integer getQ0() {
        return q0;
    }

    public ArrayList<Integer> getF() {
        return F;
    }

    public Transitions getDelta() {
        return Delta;
    }
    
}
