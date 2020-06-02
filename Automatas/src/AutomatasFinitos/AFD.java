package AutomatasFinitos;

import LectoresYProcesos.CreadorAutomata;
import Herramientas.Transition;
import ProcesamientoCadenas.ProcesamientoCadenaAFD;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;


/**
 * Esta clase es el automata finito determinista
 * en este automata se pueden realizar procesamientos de palabras sobre si mismo, confirmando si pertenece o no al lenguaje, e incluso se puede recibir una respuesta con pasos incluidos
 * 
 * @author equipo los Javas
 * @version 1.2
 */
public class AFD extends Automat{
    /**
     * Constructor Inicializa los atributos a partir del archivo de texto.
     * @param nombreArchivo url del archivo donde se encuentra el automata
     * @throws Error error generado a la hora de leer el automata, 
     * @throws java.io.FileNotFoundException en caso de que el archivo no sea encontrado por el scanner
     */
    public AFD(String nombreArchivo) throws Error, FileNotFoundException{
        CreadorAutomata.Lecto lec = CreadorAutomata.Lecto.inicio;
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
                    lec = CreadorAutomata.Lecto.alfabeto;
                    alpha = new ArrayList<>();
                    break;
                case "#states":
                    if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                    lec = CreadorAutomata.Lecto.estados;
                    W = new ArrayList<>();
                    break;
                case "#initial":
                    if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                    if(W==null) throw new Error("primero debe iniciarse los estados");
                    lec = CreadorAutomata.Lecto.estadoinicial;
                    break;
                case "#accepting":
                    if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                    if(W==null) throw new Error("primero debe iniciarse los estados");
                    if(W0==null) throw new Error("primero debe dar el estado inicial");
                    lec = CreadorAutomata.Lecto.estadoFin;
                    G = new ArrayList<>();
                    break;
                case "#transitions":
                    if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                    if(W==null) throw new Error("primero debe iniciarse los estados");
                    if(W0==null) throw new Error("primero debe dar el estado inicial");
                    if(G==null) throw new Error("primero debe dar los estados finales");
                    lec = CreadorAutomata.Lecto.transicion;
                    Deltos = new Transition();
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
        
        char[] ad =new char[alpha.size()];
        for (int i = 0; i < alpha.size(); i++) {
            ad[i]=alpha.get(i);
        }
        this.Sigma = ad;
        this.Q = W;
        this.q0 = Q.indexOf(W0);
        this.F = new ArrayList<>();
        for(String f:G){
            F.add(Q.indexOf(f));
        }
        
        try{
            for (int i = 0; i < Sigma.length; i++) {
                for (int j = 0; j < Q.size(); j++) {
                    String get = Deltos.cambio(Sigma[i], Q.get(j));
                }
            }
        }catch(Error | Exception e){
            throw new Error("Faltan transiciones a posibles estados limbos");
        }
        
{
            
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
    @Override
    public boolean procesarCadenaConDetalles(String word){
        ProcesamientoCadenaAFD fin =prosCaden(word);
        System.out.println(fin.pasos()+fin.EsAceptada());
        return fin.EsAceptada();
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
                ProcesamientoCadenaAFD res = prosCaden(listaCadenas[i]);
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
        q.setEsAceptada(F.contains(q.getlastPaso()));
        return q;
    }

    @Override
    public int computarTodosLosProcesamientos(String cadena, String nombreArchivo) {
        return 0;
    }
    
    @Override
    public void ImprimirlambdaClausura_unEstado(int estado) {
       
    }

    @Override
    public void ImprimirlambdaClausura_variosEstado(ArrayList<Integer> estados) {
       
    }

    @Override
    public void imprimirComputaciones(String cadena, int computacion) {
        
    }
}
