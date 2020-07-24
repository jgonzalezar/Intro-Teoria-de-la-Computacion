package AutomatasFinitos;

import Herramientas.ParPila;
import Herramientas.TransitionAFPD;
import Herramientas.Transitions;
import Herramientas.trioPila;
import LectoresYProcesos.InteraccionesAutomas.Lecto;
import ProcesamientoCadenas.ProcesamientoCadenaAFPD;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.EmptyStackException;
import java.util.Scanner;


/**
 * Esta clase es el automata finito determinista
 * en este automata se pueden realizar procesamientos de palabras sobre si mismo, confirmando si pertenece o no al lenguaje, e incluso se puede recibir una respuesta con pasos incluidos
 * 
 * @author equipo los Javas
 * @version 1.2
 */
public class AFPD extends AFD{
    public char[] Gamma;

    public AFPD() {
    }
    
    
    
    /**
     * Constructor Inicializa los atributos a partir del archivo de texto.
     * @param nombreArchivo url del archivo donde se encuentra el automata
     * @throws Error error generado a la hora de leer el automata, 
     * @throws java.io.FileNotFoundException en caso de que el archivo no sea encontrado por el scanner
     */
    public AFPD(String nombreArchivo) throws Error, FileNotFoundException{
        Lecto lec = Lecto.inicio;
        ArrayList<Character> alpha = null;
        ArrayList<String> W = null;
        String W0 = null;
        ArrayList<String> G = null;
        ArrayList<Character> T=null;
        
        TransitionAFPD Deltos = null;
        
        
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
                case "#alphabetP":    
                    if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                    if(W==null) throw new Error("primero debe iniciarse los estados");
                    if(W0==null) throw new Error("primero debe dar el estado inicial");
                    if(G==null) throw new Error("primero debe dar los estados finales");
                    T=new ArrayList<>();
                    lec = Lecto.pilaAlphabeto;
                    break;
                case "#transitions":
                    if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                    if(W==null) throw new Error("primero debe iniciarse los estados");
                    if(W0==null) throw new Error("primero debe dar el estado inicial");
                    if(G==null) throw new Error("primero debe dar los estados finales");
                    if(T==null) throw new Error("primero debe dar el alphabeto de la pila");
                    lec = Lecto.transicion;
                    Deltos = new TransitionAFPD();
                    break;
                default:
                    switch(lec){
                        case alfabeto:
                            if(lin.length()<2){
                                alpha.add(lin.charAt(0));
                            }else{
                                int a = lin.charAt(0);
                                int b = lin.charAt(2);
                                int c = b-a;
                                for (int i = 0; i <=c; i++) {
                                    char d = (char) (a+i);
                                    alpha.add(d);

                                }
                            }
                            break;
                        case estados:
                            W.add(lin);
                            break;
                        case estadoinicial:
                            if(!W.contains(lin))throw new Error("el estado inicial debe pertenecer a los estados del automata");
                            W0=lin;
                            break;
                        case estadoFin:
                            if(!W.contains(lin))throw new Error("el estado de acceptacion debe pertenecer a los estados del automata"+lin);
                            G.add(lin);
                            break;
                        case pilaAlphabeto:
                            if(lin.length()<2){
                                T.add(lin.charAt(0));
                            }else{
                                int a = lin.charAt(0);
                                int b = lin.charAt(2);
                                int c = b-a;
                                for (int i = 0; i <=c; i++) {
                                    char d = (char) (a+i);
                                    T.add(d);

                                }
                            }
                            break;
                        case transicion:
                            String[] origin = lin.split(":");
                            String estado1 = origin[0];
                            if(!W.contains(estado1))throw new Error("el estado del que se parte debe pertenecer a los estados del automata");
                            String[] origin2 = origin[1].split(">");
                            String intro = origin2[0];
                            
                            //if(pilaT!='$') if(!T.contains(pilaT))throw new Error("el caracter de Tope de la pila no pertenece al alfabeto de la pila");
                            String[] partIn = intro.split(",");
                            Character alph = partIn[0].charAt(0);
                            if(!alpha.contains(alph))throw new Error("el caracter  no pertenece al alfabeto del automata");
                            String pila = partIn[1];
                            Character pilT = pila.charAt(0);
                            if(!T.contains(pilT)&&pilT!='$')throw new Error("el caracter de tope de pila  no pertenece al alfabeto de la pila");
                            String pilN = pila.substring(2);
                            for (int i = 0; i < pilN.length(); i++) {
                                if(!T.contains(pilN.charAt(i))&&pilN.charAt(i)!='$')throw new Error("el caracter a agregar a la pila no pertenece al alfabeto de la pila");
                            }
                           
                            String go = origin2[1];
                            if(!W.contains(go))throw new Error("el estado de llegada debe pertenecer a los estados del automata");
                            Deltos.add(alph, estado1,pilT, go,pilN);
                            break;
                        default:
                            break;
                    }

            }
        }
        
        char[] ad =new char[alpha.size()];
        for (int i = 0; i < alpha.size(); i++) {
            ad[i]=alpha.get(i);
        }
        this.Sigma = new Alfabeto(ad);
        this.Q = W;
        this.q0 = Q.indexOf(W0);
        this.F = new ArrayList<>();
        for(String f:G){
            F.add(Q.indexOf(f));
        }
        Gamma=new char[T.size()];
        for(int i =0;i<T.size();i++){
            Gamma[i]=T.get(i);
        }
        
        this.Delta = Deltos;
    }
    /**
     * Procesa una palabra para decir si pertenece al lenguaje
     * @param word palabra a determinar
     * @return la aceptacion del lenguaje 
     */

    @Override
    public boolean procesarCadena(String word){
        return prosCaden(word).EsAceptada();
    }
    /**
     * Procesa una cadena para decir si pertenece al lenguaje
     * @param word cadena a determinar
     * @return la aceptacion del lenguaje
     */
    
    
    @Override
    public boolean procesarCadena(char[] word){
        return procesarCadena(Arrays.toString(word));
    }
    
    /**
     * Procesa la cadena e imprime los estados en consola los caminos que va tomando al procesar cada símbolo.
     * @param word cadena de caracteres a evaluar
     * @return la aceptacion del lenguaje
     */
    @Override
    public boolean procesarCadenaConDetalles(char[] word){
        return procesarCadenaConDetalles(Arrays.toString(word));
    }
    
    /**
     * Procesa la palabra e imprime los estados en consola los caminos que va tomando al procesar cada símbolo.
     * @param word palabra a evaluar
     * @return la cadena es o no aceptada
     */
    @Override
    public boolean procesarCadenaConDetalles(String word){
        ProcesamientoCadenaAFPD fin =prosCaden(word);
        System.out.println(fin.pasos()+fin.EsAceptada());
        return fin.EsAceptada();
    }
    
    /**
     * evalua una palabra y retorna su procesamiento en cadena
     * @param word 
     * @return procesamiento dado a la cadena de su camino y su resultado de aceptacion
     * @see ProcesamientoCadenaAFD
     */
    
    /**
     * La funcion procesarListaCadenas procesa cada una de una list de cadenas dada y la guarda dentro de un archivo con el nombre dado, y imprimirla en consola o no
     * @param listaCadenas lista de cadenas a evaluar 
     * @param nombreArchivo nombre del archivo donde se guardara 
     * @param imprimirPantalla booleano que decide si se imprimira o no en consola
     */
    
    @Override
    public void procesarListaCadenas(String[] listaCadenas,String nombreArchivo,boolean imprimirPantalla){
        FileWriter fichero1 = null;
        PrintWriter pw1 = null;
        try
        {
            File nombre = new File(nombreArchivo);
            if(!nombre.exists())nombre.createNewFile();
            fichero1 = new FileWriter(nombre);
            pw1 = new PrintWriter(fichero1);
            for (int i = 0; i < listaCadenas.length; i++){
                ProcesamientoCadenaAFPD res = prosCaden(listaCadenas[i]);
                String pas =res.pasos();
                String res2= listaCadenas[i]+"\t"+pas+"\t"+res.EsAceptada();
                pw1.println(res2);
                if(imprimirPantalla) System.out.println(res2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {          
           try {
                if (null != fichero1){
                   fichero1.close();
                }
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }

    /**
     * La Funcion delta recibe una palabra y crea su recurcion mas pequeña para luego desde la misma crear un camino y empezar a recorrerlo segun el ultimo caracter de la recursion hasta generar todo el camino de la palabra
     * @param word palabra a ser evaluada
     * @return procesamiento recursivo de la palabra dada
     * @see ProcesamientoCadenaAFD
     */
    
    private ProcesamientoCadenaAFPD Delta(String word) {
        ProcesamientoCadenaAFPD f = new ProcesamientoCadenaAFPD(word);
        try{
            f.add(Q.get(q0),"$",'$');
        }catch(Exception e){
            
        }
        
        if(word==null||word.length()==0){   
            f.setEsAceptada(F.contains(q0));
            f.setCadena(word);
            return  f;
        }
        if(word.length()==1){
            return Delta(f,word.charAt(0));
        }
        ProcesamientoCadenaAFPD det = Delta(word.substring(0, word.length()-1));
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
    
    private ProcesamientoCadenaAFPD Delta(ProcesamientoCadenaAFPD i, char u) {
        if(i.EsAceptada())return i;
        trioPila tas = Delta.cambios(u,i.getlastPaso(),i.getTopPila());
        while(tas==null){
            trioPila dostas= Delta.cambios('$', i.getlastPaso(),i.getTopPila());
            if(dostas==null){
                i.setEsAceptada(true);
                return i;
            }
            try{
                i.add(dostas.getDuo().getEstado(),dostas.getDuo().getPila(),dostas.getPila());
            }catch(Exception e){
                i.setEsAceptada(true);
                return i;
            }
            tas = Delta.cambios(u,i.getlastPaso(),i.getTopPila());
        }
        try{
            i.add(tas.getDuo().getEstado(),tas.getDuo().getPila(),tas.getPila());
        }catch(Exception e){
            i.setEsAceptada(true);
        }
        
        return i;
    }
    
    /**
     * La funcion Finish Realiza la comprobacion de un ProcesamientodeCadenaAFD para comprobar su ultimo estado y retornar el procesamiento con la variable de aceptacion segun la pertenencia del estado final dentro de los aceptados
     * @param q procesamiento previo de alguna cadena
     * @return Procesamiento modificado
     * @see ProcesamientoCadenaAFD
     */

    private ProcesamientoCadenaAFPD Finish(ProcesamientoCadenaAFPD q) {
        if(q.EsAceptada()){
            q.setEsAceptada(false);
        }else{
            q.setEsAceptada(F.contains(Q.indexOf(q.getlastPaso()))&&q.getTopPila()=='$');
        }
        
        
        return q;
    }

    private ProcesamientoCadenaAFPD prosCaden(String listaCadena) {
        return Finish(Delta(listaCadena));
    }

    @Override
    public String toString() {
        String cadena = "#!fpda";
        cadena += super.toString();
        cadena += "#alphabetP\n";
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
        
        cadena+="#transitions\n";
        for (int i = 0; i < Q.size(); i++) {
            for (int k = 0; k < Sigma.length(); k++) {
                for (int j = 0; j < Gamma.length; j++) {
                    String line = Q.get(i)+":"+Sigma.get(k)+","+Gamma[j]+"|";
                    ParPila e = Delta.cambio(Sigma.get(k), Q.get(i),Gamma[j]);
                    if(e!=null){
                        line+=e.getPila()+">"+e.getEstado();
                        cadena+=line;
                    }
                }
            }
        }
        
        return cadena;
    }
    
    public char[] getGamma() {
        return Gamma;
    }
}
