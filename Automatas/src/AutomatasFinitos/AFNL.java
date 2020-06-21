/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos;

import Herramientas.TransitionAFNL;
import LectoresYProcesos.CreadorAutomata;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import Herramientas.RespuestaMult;
import Herramientas.TransitionAFN;
import Herramientas.Transitions;
import Herramientas.Tupla;
import ProcesamientoCadenas.ProcesamientoCadenaAFNLambda;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Esta clase es el automata finito no determinista con transiciones lambda en este automata se puede realizar el procesamiento de cadenas sobre el automata ingresado.
 * @author jgonzalezar, ivonn, fnat
 */
public class AFNL extends AFN{
    /**
     * El atributo lambda representa el caracter que actua para hacer la transiciòn lambda del automata.
     */
    private final char lambda = '$';
    
    /**
     * Constructor, inicializa los atributos.
     * @param E Alfabeto
     * @param Q Conjunto de estados
     * @param q0 Estado inicial
     * @param F Estados de aceptación
     * @param delta Transiciones
     * @see TransitionAFNL
     */
    public AFNL(char[] E, ArrayList<String> Q, int q0, ArrayList<String> F, TransitionAFNL delta) {
        this.Sigma = new Alfabeto(E);
        this.Q = Q;
        this.q0 = q0;
        this.F = new ArrayList<Integer>();
        for (int i = 0; i < F.size(); i++) {
            this.F.add(Q.indexOf(F.get(i)));
        }
        this.Delta = delta;

    }
    /**
     * Inicializa los atributos a partir del archivo de texto.
     * @param nombreArchivo nombre que se le quiere dar al archivo
     */
    public AFNL(String nombreArchivo) {
        CreadorAutomata.Lecto lec = CreadorAutomata.Lecto.inicio;
        ArrayList<Character> alpha = null;
        ArrayList<String> Estados = null;
        String q0 = null;
        ArrayList<String> F = null;
        TransitionAFNL transition = null;

        try {
            Scanner sca = new Scanner(new File(nombreArchivo));
            while (sca.hasNextLine()) {
                String lin = sca.nextLine();
                switch (lin) {
                    case "#alphabet":
                        lec = CreadorAutomata.Lecto.alfabeto;
                        alpha = new ArrayList<>();
                        break;
                    case "#states":
                        if (alpha == null) {
                            throw new Error("primero debe iniciarse el alfabeto");
                        }
                        lec = CreadorAutomata.Lecto.estados;
                        Estados = new ArrayList<>();
                        break;
                    case "#initial":
                        if (alpha == null) {
                            throw new Error("primero debe iniciarse el alfabeto");
                        }
                        if (Estados == null) {
                            throw new Error("primero debe iniciarse los estados");
                        }
                        lec = CreadorAutomata.Lecto.estadoinicial;
                        break;
                    case "#accepting":
                        if (alpha == null) {
                            throw new Error("primero debe iniciarse el alfabeto");
                        }
                        if (Estados == null) {
                            throw new Error("primero debe iniciarse los estados");
                        }
                        if (q0 == null) {
                            throw new Error("primero debe dar el estado inicial");
                        }
                        lec = CreadorAutomata.Lecto.estadoFin;
                        F = new ArrayList<>();
                        break;
                    case "#transitions":
                        if (alpha == null) {
                            throw new Error("primero debe iniciarse el alfabeto");
                        }
                        if (Estados == null) {
                            throw new Error("primero debe iniciarse los estados");
                        }
                        if (q0 == null) {
                            throw new Error("primero debe dar el estado inicial");
                        }
                        if (F == null) {
                            throw new Error("primero debe dar los estados finales");
                        }
                        lec = CreadorAutomata.Lecto.transicion;
                        transition = new TransitionAFNL(Estados.size());
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
                                Estados.add(lin);
                                break;
                            case estadoinicial:
                                if (!Estados.contains(lin)) {
                                    throw new Error("el estado debe pertenecer a los estados del automata");
                                }
                                q0 = lin;
                                break;
                            case estadoFin:
                                if (!Estados.contains(lin)) {
                                    throw new Error("el estado debe pertenecer a los estados del automata");
                                }
                                F.add(lin);
                                break;
                            case transicion:

                                String[] origin = lin.split(":");
                                String estado1 = origin[0];
                                if (!Estados.contains(estado1)) {
                                    throw new Error("el estado del que se parte debe pertenecer a los estados del automata");
                                }
                                String[] origin2 = origin[1].split(">");
                                String alpfa = origin2[0];
                                if (!alpha.contains(alpfa.charAt(0)) && !alpfa.equals("$")) {
                                    throw new Error("el caracter de activacion debe pertenecer al alfabeto");
                                }
                                String[] origin3 = origin2[1].split(";");
                                int[] destino = new int[origin3.length];
                                for (int i = 0; i < origin3.length; i++) {
                                    String estado2 = origin3[i];
                                    if (!Estados.contains(estado2)) {
                                        throw new Error("el estado de llegada debe pertenecer a los estados del automata");
                                    }
                                    destino[i] = Estados.indexOf(estado2);
                                }
                                transition.add(alpfa.charAt(0), Estados.indexOf(estado1), destino);
                                break;
                            default:
                                break;
                        }

                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreadorAutomata.class.getName()).log(Level.SEVERE, null, ex);
        }

        char[] ad = new char[alpha.size()];
        for (int i = 0; i < alpha.size(); i++) {
            ad[i] = alpha.get(i);
        }
        this.Sigma = new Alfabeto(ad);
        this.Q = Estados;
        this.q0 = Q.indexOf(q0);
        this.F = new ArrayList<Integer>();
        for (int i = 0; i < F.size(); i++) {
            this.F.add(Q.indexOf(F.get(i)));
        }
        this.Delta = transition;

    }
    /**
     * Encuentra el lambda clausura de un solo estado.
     * @param estado estado del que se desea hallar la lambda clausura. 
     * @return  Lista de enteros representando los índices de los estados pertenecientes a la lambda clausura del estado dado.
     */
    public ArrayList<Integer> lambdaClausura_unEstado(int estado) {
        ArrayList<Integer> lClausura;
        Transitions Tra = this.Delta;
        lClausura = Tra.getMove(estado, lambda);
        if (lClausura == null) {
            lClausura = new ArrayList<>();
            lClausura.add(estado);
            return lClausura;
        } else {
            Set<Integer> hashSet = new HashSet<>(lClausura);
            lClausura.clear();
            lClausura.addAll(hashSet);
            int lclauSize = lClausura.size();
            for (int i = 0; i < lclauSize; i++) {
                if (estado == lClausura.get(i)) {

                } else {
                    lClausura.addAll(lambdaClausura_unEstado(lClausura.get(i)));
                }
            }
            lClausura.add(estado);
        }
        Set<Integer> hashSet = new HashSet<>(lClausura);
        lClausura.clear();
        lClausura.addAll(hashSet);

        return lClausura;
    }
    /**
     * Imprime en cosola la lambdaclausura de un solo estado.
     * @param estado estado del que se desea imprimir la lambda clausura.
     * @return 
     */
    public ArrayList<Integer> ImprimirlambdaClausura_unEstado(int estado) {
        String lambdaC = "λ["+Q.get(estado)+"] = {";
        ArrayList<Integer> Clausura = lambdaClausura_unEstado(estado);
        //System.out.println("lambda clausura del estado " + Q.get(estado) + " es:");
        //System.out.print("$["+Q.get(estado)+"] = {");
        
        for (Integer integer : Clausura) {
            String cadena = Q.get(integer) + ",";
            lambdaC += cadena;
        }
        
        lambdaC = lambdaC.substring(0, lambdaC.length()-1) + "}.";
        System.out.println(lambdaC);
        
        return Clausura;
    }
    /**
     * Encuentra el lambda clausura de varios estados , devuelve las lambdaclausuras de los estados juntas, como una sola.
     * @param estados lista de estados de los cuales quiere hallar la lambda clausura.
     * @return Lista de enteros representando los índices de los estados pertenecientes a la lambda clausura de los estados dados.
     */
    public ArrayList<Integer> lambdaClausura_variosEstado(ArrayList<Integer> estados) {
        ArrayList<Integer> lClausura_estados = new ArrayList<>();
        for (int i = 0; i < estados.size(); i++) {
            ArrayList<Integer> Clauestado = lambdaClausura_unEstado(estados.get(i));
            for (int j = 0; j < Clauestado.size(); j++) {
                lClausura_estados.add(Clauestado.get(j));
            }
        }
        Set<Integer> hashSet = new HashSet<>(lClausura_estados);
        lClausura_estados.clear();
        lClausura_estados.addAll(hashSet);

        return lClausura_estados;

    }
    /**
     * Imprime el lambda clausura de varios estados , devuelve las lambdaclausuras de los estados juntas, como una sola.
     * @param estados lista de estados de los cuales quiere Imprimir la lambda clausura.
     */
    public void ImprimirlambdaClausura_variosEstado(ArrayList<Integer> estados) {

        ArrayList<Integer> Clausura = lambdaClausura_variosEstado(estados);
        System.out.print("lambda clausura de los estados {");

        for (Integer s : estados) {
            System.out.print(" " + Q.get(s) + " ");
        }
        System.out.print("} es : ");

        System.out.print(" {");
        for (Integer s : Clausura) {
            System.out.print(" " + Q.get(s) + " ");
        }
        System.out.println("}");

    }
    /**
     * imprime los caminos que son aceptados o rechazados o abortados del procesaiento de la cadena dada segun la opcion que se escoja.
     * @param cadena cadena a evaluar.
     * @param computacion numero dado que define la opcion escogida.
     */
    public void imprimirComputaciones(String cadena, int computacion) {
        ProcesamientoCadenaAFNLambda procesada = procesarCadenad(cadena);
        ArrayList<String> lista;

        switch (computacion) {
            case 0:
                lista = procesada.getListaProcesamientosAceptacion();

                break;
            case 1:
                lista = procesada.getListaProcesamientosRechazados();
                break;
            default:
                lista = procesada.getListaProcesamientosAbortados();
                break;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i));
        }

    }
    /**
     * Guarda todos los caminos posibles en los cuales la cadena se puede evaluar sin importar si son de aceptacion , rechazo o aborto.     * 
     * @param cadena cadena a evaluar.
     */
    private RespuestaMult caminosPosibles(String cadena) {
        if (cadena == null || cadena.length() == 0) {
            RespuestaMult respuesta = new RespuestaMult();
            respuesta.add(q0);
            return respuesta;
        } else {

            RespuestaMult caminos = caminosPosibles(cadena.substring(0, cadena.length() - 1));
            return Iteracion(cadena.charAt(cadena.length() - 1), caminos);
        }
    }
    /**
     * Evalua la cadena dada y guarda una lista de los procesamientos Aceptados, Abortados y Rechazados junto con un conjunto de metodos que nos periten acceder a esta informacion.
     * @param cadena cadena a evaluar.
     */
    private ProcesamientoCadenaAFNLambda procesarCadenad(String cadena) {
        RespuestaMult rta = caminosPosibles(cadena);
        return procesamiento(cadena, rta);
    }
    /**
     *Guarda el valor booleano que indica si la cadena es o no aceptada 
     * @param palabra cadena a evaluar.
     * @return verdadero si la palabra es aceptada, falso en otro caso.
     */
    @Override
    public boolean procesarCadena(String palabra) {
        return procesarCadenad(palabra).isEsAceptada();
    }
    /**
     *Guarda el valor booleano que indica si la cadena es o no aceptada , ademas de esto imprime el camino mas corto de aceptacion de la cadena, si la cadena es aceptada , de lo contrario imprime el caino mas corto de Rechazo de la cadena.
     * @param word cadena a evaluar.
     * @return verdadero si la palabra es aceptada, falso en otro caso.
     */
    @Override
    public boolean procesarCadenaConDetalles(String word) {
        ProcesamientoCadenaAFNLambda fin = procesarCadenad(word);
        System.out.println(fin.imprimirCamino());
        return fin.isEsAceptada();
    }
    /**
     *Procesa y guarda una iteracion de la evaluacion de la cadena ,  solo procesa la evaluacion de uno de los caracteres de la cadena y guarda como quedarian los posibles caminos despues de la evaluacion de ese caracter.
     * @param letra letra a evaluar.
     * @param caminos caminos recorridos y guardados hasta el momento.
     * @return Lista de caminos recorridos.
     * @see RespuestaMult
     */
    private RespuestaMult Iteracion(char letra, RespuestaMult caminos) {
        Transitions T = this.Delta;
        ArrayList<Integer> finals = caminos.getFinals();
        for (int i = 0; i < finals.size(); i++) {
            ArrayList<Integer> StepsToAdd = new ArrayList<>();
            try {
                ArrayList<Integer> CaminosLambda = lambdaClausura_unEstado(finals.get(i));
                if (CaminosLambda == null || CaminosLambda.isEmpty()) {
                    StepsToAdd.add(null);
                } else {
                    for (int j = 0; j < CaminosLambda.size(); j++) {
                        ArrayList<Integer> trans = T.getMove(CaminosLambda.get(j), letra);
                        if (trans == null || trans.isEmpty()) {
                            StepsToAdd.add(null);
                        } else {
                            StepsToAdd.addAll(trans);
                        }
                    }
                }
            } catch (Exception e) {
                StepsToAdd.add(null);
            }
            Set<Integer> hashSet = new HashSet<>(StepsToAdd);
            StepsToAdd.clear();
            StepsToAdd.addAll(hashSet);
            caminos.addRutas(i, StepsToAdd);
        }
        return caminos;
    }
    /**
     *Crea y retorna el String de una sola iteracion (cambio de estado con un caracer) de un camino especifico del procesamiento de la cadena. 
     * @param cadena cadena evaluada.
     * @param caracter caracter que evalua.
     * @param estadoActual caminos recorridos y guardados hasta el momento .
     * @param estadoSiguiente caminos recorridos y guardados hasta el momento .
     * @return String de una sola iteracion (cambio de estado con un caracer) de un camino especifico del procesamiento de la cadena
     */
    public String saltoDeEstados(String cadena, char caracter, int estadoActual, Integer estadoSiguiente) {
        ArrayList<Integer> alQueVa = this.Delta.getMove(estadoActual, caracter);
        String camino = "[" + Q.get(estadoActual) + ", " + cadena + "] -> ";

        if (alQueVa == null) {
            if (estadoSiguiente == null) {
                return camino;
            } else {
                return "";
            }
        }

        if (!alQueVa.contains(estadoSiguiente)) {
            try {
                ArrayList<Integer> conLambda = this.Delta.getMove(estadoActual, '$');
                for (int i = 0; i < conLambda.size(); i++) {
                    if (conLambda.get(i) == estadoActual) {

                    } else {
                        String estadosLambda = saltoDeEstados(cadena, caracter, conLambda.get(i), estadoSiguiente);
                        if (!estadosLambda.isEmpty()) {
                            return camino + estadosLambda;
                        }
                    }
                }
            } catch (Exception e) {

            }
            camino = "";
        }
        return camino;
    }
    /**
     *Crea y guarda los String de todos los caminos posibles del procesamiento de la cadena segun los parametros del profesor, adeas de esto retorna el procesamiento completo de la cadena AFNL.
     * @param cadena cadena a evaluar.
     * @param respuesta lista de caminos recorridos.
     * @return Lista de los procesamientos de la cadena.
     * @see RespuestaMult
     */
    public ProcesamientoCadenaAFNLambda procesamiento(String cadena, RespuestaMult respuesta) {
        ArrayList<Tupla> tupla = new ArrayList<>();

        ArrayList<Integer> resFinals = respuesta.getFinals();
        for (int i = 0; i < resFinals.size(); i++) {
            ArrayList<Integer> caminoespecifico = respuesta.getCamino(i);
            String camino = "";
            for (int j = 0; j < caminoespecifico.size(); j++) {
                try {
                    Integer estadoActual = caminoespecifico.get(j);
                    char charActual = cadena.charAt(j);
                    Integer siguiente = caminoespecifico.get(j + 1);

                    if (estadoActual == null) {
                        throw new NullPointerException();
                    }
                    camino += saltoDeEstados(cadena.substring(j), charActual, estadoActual, siguiente);
                } catch (Exception e) {
                    break;
                }
            }
            Integer estadoactual = resFinals.get(i);
            if (estadoactual == null) {

            } else {
                camino += "[" + Q.get(estadoactual) + ", " + cadena.substring(cadena.length()) + "] -> ";
            }

            try {
                if (resFinals.get(i) == null) {
                    throw new NullPointerException();
                }
                if (F.contains(resFinals.get(i))) {
                    tupla.add(new Tupla(camino + " Aceptado", Tupla.Case_Aceptado));
                } else {
                    tupla.add(new Tupla(camino + " Rechazado", Tupla.Case_Rechazado));
                }
            } catch (NullPointerException e) {
                tupla.add(new Tupla(camino + " Abortado", Tupla.Case_Abortado));
            }
        }

        return new ProcesamientoCadenaAFNLambda(cadena, tupla);
    }
    
    /**
     *Devuelve la lista de estados totales del automata.
     * @return Lista de los estados del automata.
     */
    public ArrayList<String> getQ() {
        return Q;
    }
    /**
     *Devuelve la lista de estados totales del automata como un arreglo.
     * @return Arreglo de los estados del automata.
     */
    public String[] GetQ() {
        String[] estados = new String[this.Q.size()];

        for (int i = 0; i < estados.length; i++) {
            estados[i] = this.Q.get(i);
        }
        return estados;
    }
    /**
     * Imprime cada uno de los posibles procesamientos de la cadena indicando de qué estado a qué estado pasa al procesar cada símbolo e indicanda si al final de cada procesamiento se llega a aceptación o rechazo.Llena una lista de todos procesamientos de aceptación, una lista de todos los procesamientos abortados y una lista de todos los procesamientos de rechazo.
     Guardar los contenidos de estas listas cada una en un archivo .txt (uno diferente para cada tipo de camino)y las imprime en pantalla.
     Retorna el numero total de procesamientos
     * @param cadena cadena a evaluar.
     * @param nombreArchivo nombre que se le quiere dar a los archivos.
     * @return Procesamientos.
     */
    public int computarTodosLosProcesamientos(String cadena, String nombreArchivo) {
        String nombreArchivoAceptados;
        String nombreArchivoRechazados;
        String nombreArchivoAbortados;
        if((nombreArchivo.contains(".txt")&&(!nombreArchivo.substring(nombreArchivo.length()-4).equals(".txt")))|| !nombreArchivo.contains(".txt")){
            nombreArchivoAceptados = nombreArchivo + "Aceptados.txt";
            nombreArchivoRechazados = nombreArchivo + "Rechazados.txt";
            nombreArchivoAbortados = nombreArchivo + "Abortados.txt";
        }else {
            String[] parts = nombreArchivo.split("\\.");
            String part1 = parts[0];
            String part2 = parts[1];
            nombreArchivoAceptados = part1 + "Aceptados." + part2;
            nombreArchivoRechazados = part1 + "Rechazados." + part2;
            nombreArchivoAbortados = part1 + "Abortados." + part2;
        }
        ProcesamientoCadenaAFNLambda procesamiento = procesarCadenad(cadena);
        ArrayList<String> Aceptados = procesamiento.getListaProcesamientosAceptacion();
        ArrayList<String> Rechazados = procesamiento.getListaProcesamientosRechazados();
        ArrayList<String> Abortados = procesamiento.getListaProcesamientosAbortados();
        int TotaldeProcesamientos = Aceptados.size() + Rechazados.size() + Abortados.size();
        FileWriter fichero1 = null;
        PrintWriter pw1 = null;
        try {
            File nombreAc = new File(nombreArchivoAceptados);
            if (!nombreAc.exists()) {
                nombreAc.createNewFile();
            }
            nombreAc.createNewFile();
            fichero1 = new FileWriter(nombreAc);
            pw1 = new PrintWriter(fichero1);
            for (int i = 0; i < Aceptados.size(); i++) {
                System.out.println(Aceptados.get(i));
                pw1.println(Aceptados.get(i));
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
        FileWriter fichero2 = null;
        PrintWriter pw2 = null;
        try {
            File nombreRe = new File(nombreArchivoRechazados);
            if (!nombreRe.exists()) {
                nombreRe.createNewFile();
            }
            nombreRe.createNewFile();
            fichero2 = new FileWriter(nombreRe);
            pw2 = new PrintWriter(fichero2);
            for (int i = 0; i < Rechazados.size(); i++) {
                System.out.println(Rechazados.get(i));
                pw2.println(Rechazados.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero2) {
                    fichero2.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        FileWriter fichero3 = null;
        PrintWriter pw3 = null;
        try {
            File nombreAb = new File(nombreArchivoAbortados);
            if (!nombreAb.exists()) {
                nombreAb.createNewFile();
            }
            nombreAb.createNewFile();
            fichero3 = new FileWriter(nombreAb);
            pw3 = new PrintWriter(fichero3);
            for (int i = 0; i < Abortados.size(); i++) {
                System.out.println(Abortados.get(i));
                pw3.println(Abortados.get(i));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero3) {
                    fichero3.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return TotaldeProcesamientos;
    }
    
    /**
     * procesa cada cadena con y los resultados son impresos en un archivo cuyo nombre es nombreArchivo; si este es inválido se asigna
     un nombre por defecto. Además todo esto es impreso en pantalla de acuerdo al valor del Booleano imprimirPantalla.
     * @param listaCadenas lista de cadenas a evaluar.
     * @param nombreArchivo nombre que se le quiere dar a el archivo.
     * @param imprimirPantalla valor boolean que deterina si se imprimen o no los resultads en pantalla.
     */
    
    @Override
    public void procesarListaCadenas(String[] listaCadenas,String nombreArchivo, boolean imprimirPantalla){
    if (!nombreArchivo.contains(".txt")||(!nombreArchivo.substring(nombreArchivo.length()-4).equals(".txt"))) {
        nombreArchivo= nombreArchivo+".txt" ;
    }
    FileWriter fichero = null;
    PrintWriter pw = null;
    try {
        File nombre = new File(nombreArchivo);
        if (!nombre.exists()) {
            nombre.createNewFile();
        }
        nombre.createNewFile();
        fichero = new FileWriter(nombre);
        pw = new PrintWriter(fichero);
        for(int i=0;i<listaCadenas.length;i++){
            ProcesamientoCadenaAFNLambda procesamiento = procesarCadenad(listaCadenas[i]);
            ArrayList<String> Aceptados = procesamiento.getListaProcesamientosAceptacion();
            ArrayList<String> Rechazados = procesamiento.getListaProcesamientosRechazados();
            ArrayList<String> Abortados = procesamiento.getListaProcesamientosAbortados();
            int TotaldeProcesamientos = Aceptados.size() + Rechazados.size() + Abortados.size();
            String Aceptada = (procesamiento.isEsAceptada())?"si":"no";
            pw.println(listaCadenas[i]+"  "+procesamiento.imprimirCamino()+"    Numero de procesamientos: ["+TotaldeProcesamientos+"]    Procesamientos aceptados: ["
            +Aceptados.size()+"]   Procesamientos rechazados: ["+Rechazados.size()+"]   Procesamientos abortados ["+Abortados.size()+"]    La cadena "+Aceptada+ " fue aceptada");
            if(imprimirPantalla==true){
                System.out.println(listaCadenas[i]+"  "+procesamiento.imprimirCamino()+"    Numero de procesamientos: ["+TotaldeProcesamientos+"]    Procesamientos aceptados: ["
                +Aceptados.size()+"]   Procesamientos rechazados: ["+Rechazados.size()+"]   Procesamientos abortados ["+Abortados.size()+"]   La cadena "+Aceptada+ " fue aceptada");
            }    
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (null != fichero) {
                fichero.close();
            }
        } catch (Exception e2) {
                    e2.printStackTrace();
                }
        }
    }
    
    public AFN AFN_LambdaToAFN(){
        ArrayList<Integer> aceptacion = new ArrayList<>();
        TransitionAFN delta = new TransitionAFN(this.Q.size());
        
        for (int i = 0; i < Q.size(); i++) {
            ArrayList<Integer> lclausura = ImprimirlambdaClausura_unEstado(i);
            for (Integer F1 : this.F) {
                if(lclausura.contains(F1)){
                    aceptacion.add(i);
                }
            }
        }
        
        for (int i = 0; i < Q.size(); i++) {
            for (int j = 0; j < Sigma.length(); j++) {
                delta.add(Sigma.get(j), i, trancisiones(i,Sigma.get(j)));
            }
        }
        
        Set<Integer> hashSet = new HashSet<>(aceptacion);
        aceptacion.clear();
        aceptacion.addAll(hashSet);
        return new AFN(Sigma, Q, q0, aceptacion, delta);
    }
    
    public int[] trancisiones(int estado, char simb){
        String output = "Δ'("+this.Q.get(estado)+","+simb+") = λ[Δ(λ["+this.Q.get(estado)+"],"+simb+")] = λ[Δ({";
        ArrayList<Integer> lclausuraEstado = lambdaClausura_unEstado(estado);
        for (int i = 0; i < lclausuraEstado.size(); i++) {
            output += this.Q.get(lclausuraEstado.get(i)) + ",";
        }
        output = output.substring(0, output.length()-1) + "},"+simb+")] = λ[{";
        RespuestaMult camino = new RespuestaMult();
        camino.add(estado);
        ArrayList<Integer> finals = Iteracion(simb,camino).getFinals();
        Boolean bool;
        do{
            bool = finals.remove(null);
        }while (bool);
        
        if(finals.size() > 1){
            for (int i = 0; i < finals.size(); i++) {
            output += this.Q.get(finals.get(i)) + ",";
            }
        
            output = output.substring(0, output.length()-1) + "}] = ";
            
            for (int i = 0; i < finals.size(); i++) {
                output += "λ[{"+this.Q.get(finals.get(i))+"}] U ";
            }
            output = output.substring(0, output.length()-2) + "= {";
        }else if(finals.isEmpty()){
            output += "∅}] = {";
        }else{
            output += this.Q.get(finals.get(0))+"}] = {";
        } 
        
        ArrayList<Integer> lclauMul = lambdaClausura_variosEstado(finals);
        
        if(!lclauMul.isEmpty()){
            for (int i = 0; i < lclauMul.size(); i++) {
                output += this.Q.get(lclauMul.get(i)) + ",";
            }
        }else{
            output += "∅,";
        }
        
        output = output.substring(0, output.length()-1) + "}.";
        System.out.println(output);
        int[] rta = new int[lclauMul.size()];
        for (int i = 0; i < lclauMul.size(); i++) {
            rta[i] = lclauMul.get(i);
        }
        return rta;
    }

}

