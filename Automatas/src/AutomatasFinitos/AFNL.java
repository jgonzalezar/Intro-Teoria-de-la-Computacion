/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos;

import Herramientas.TransitionAFNL;
import Herramientas.Tuple;
import LectoresYProcesos.CreadorAutomata;
import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Integer.parseInt;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import Herramientas.RespuestaMult;
import Herramientas.Tupla;
import ProcesamientoCadenas.ProcesamientoCadenaAFD;
import ProcesamientoCadenas.ProcesamientoCadenaAFNLambda;

/**
 *
 * @author ivonn
 */
public class AFNL {

    private final char[] Sigma;
    private final ArrayList<String> Q;
    private final int q0;
    private final ArrayList<Integer> F;
    //public final ArrayList<ArrayList<Tuple>> Delta;
    private final TransitionAFNL Delta;
    private final char lambda = '$';

    public AFNL(char[] E, ArrayList<String> Q, int q0, ArrayList<String> F, TransitionAFNL delta) {
        this.Sigma = E;
        this.Q = Q;
        this.q0 = q0;
        this.F = new ArrayList<Integer>();
        for (int i = 0; i < F.size(); i++) {
            this.F.add(Q.indexOf(F.get(i)));
        }
        this.Delta = delta;

    }

    public AFNL(String nombreArchivo) {
        CreadorAutomata.Lecto lec = CreadorAutomata.Lecto.inicio;
        ArrayList<Character> alpha = null;
        ArrayList<String> Estados = null;
        String q0 = null;
        ArrayList<String> F = null;
        //ArrayList<Tuple> Delta = null;
        TransitionAFNL transition = null;

        try {
            Scanner sca = new Scanner(new File(nombreArchivo));
            while (sca.hasNextLine()) {
                String lin = sca.nextLine();
                System.out.println(lin);
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
                                String[] origin3 = origin2[1].split(",");
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
        this.Sigma = ad;
        this.Q = Estados;
        this.q0 = Q.indexOf(q0);
        this.F = new ArrayList<Integer>();
        for (int i = 0; i < F.size(); i++) {
            this.F.add(Q.indexOf(F.get(i)));
        }
        this.Delta = transition;

    }

    //@SuppressWarnings("empty-statement")
    public ArrayList<Integer> lambdaClausura_unEstado(int estado) {
        ArrayList<Integer> lClausura;
        TransitionAFNL Tra = this.Delta;
        lClausura = Tra.getMove(estado, lambda);
        if (lClausura == null) {
            lClausura = new ArrayList<>();
            lClausura.add(estado);
            return lClausura;
        } else {
            for (int i = 0; i < lClausura.size(); i++) {
                if(lClausura.get(i) == estado){
                    
                }
                lClausura.addAll(lambdaClausura_unEstado(lClausura.get(i)));
            }
            lClausura.add(estado);
        }
        Set<Integer> hashSet = new HashSet<>(lClausura);
        lClausura.clear();
        lClausura.addAll(hashSet);

        System.out.println("en lambda clausura 1 estado");
        return lClausura;
    }

    public void ImprimirlambdaClausura_unEstado(int estado) {

        ArrayList<Integer> Clausura = lambdaClausura_unEstado(estado);
        System.out.printf("lambda clausura del estado " + Q.get(estado));
        Clausura.forEach((s) -> {
            System.out.println("q" + Q.get(s));
        });

    }

    public ArrayList<Integer> lambdaClausura_variosEstado(ArrayList<Integer> estados) {
        ArrayList<Integer> lClausura_estados = new ArrayList<>();
        for (int i = 0; i < estados.size(); i++) {
            ArrayList<Integer> Clauestado = lambdaClausura_unEstado(i);
            for (int j = 0; j < Clauestado.size(); j++) {
                lClausura_estados.add(Clauestado.get(j));
            }
        }
        Set<Integer> hashSet = new HashSet<>(lClausura_estados);
        lClausura_estados.clear();
        lClausura_estados.addAll(hashSet);

        return lClausura_estados;

    }

    public void ImprimirlambdaClausura_variosEstado(ArrayList<Integer> estados) {

        ArrayList<Integer> Clausura = lambdaClausura_variosEstado(estados);
        System.out.print("lambda clausura de los estados {");

        for (Integer s : estados) {
            System.out.print("q" + Q.get(estados.get(s)) + " ");
        }
        System.out.print("} es : ");

        System.out.print(" {");
        for (Integer s : Clausura) {
            System.out.print("q" + Q.get(estados.get(s)) + " ");
        }
        System.out.print("}");

    }

    private RespuestaMult caminosPosibles(String cadena) {
        //if (cadena == null || cadena.length() == 0) {
        RespuestaMult respuesta = new RespuestaMult();
        respuesta.add(q0);
        //return respuesta;
        //} else {
        //return Iteracion(cadena.charAt(cadena.length() - 1), caminosPosibles(cadena.substring(0, cadena.length() - 1)));
        return Iteracion(cadena.charAt(cadena.length() - 1), respuesta);
        //}
    }

    private ProcesamientoCadenaAFNLambda procesarCadena(String cadena) {
        return procesamiento(cadena, caminosPosibles(cadena));
    }

    public boolean ProcesarCadena(String palabra) {
        return procesarCadena(palabra).isEsAceptada();
    }

    public boolean procesarCadenaConDetalles(String word) {
        ProcesamientoCadenaAFNLambda fin = procesarCadena(word);
        System.out.println(fin.getListaProcesamientosAceptacion().size() + " "
                + fin.getListaProcesamientosRechazados().size() + " " + fin.getListaProcesamientosAbortados().size());
        System.out.println(fin.imprimirCamino());
        return fin.isEsAceptada();
    }

    public RespuestaMult Iteracion(char letra, RespuestaMult caminos) {
        TransitionAFNL T = this.Delta;
        ArrayList<Integer> finals = caminos.getFinals();
        for (int i = 0; i < finals.size(); i++) {
            ArrayList<Integer> StepsToAdd = new ArrayList<>();
            try {
                System.out.println(finals.get(i));
                System.out.println("lambda clusura antes");
                ArrayList<Integer> CaminosLambda = lambdaClausura_unEstado(finals.get(i));
                System.out.println("lambda clusura despues");
                if (CaminosLambda == null || CaminosLambda.isEmpty()) {
                    System.out.println("camnio lambda vacio");
                    StepsToAdd.add(null);
                } else {
                    System.out.println("camnio lambda NO vacio " + CaminosLambda.size());
                    for (int j = 0; j < CaminosLambda.size(); j++) {
                        System.out.print(CaminosLambda.get(j));
                        ArrayList<Integer> trans = T.getMove(CaminosLambda.get(j), letra);
                        if (trans == null || trans.isEmpty()) {
                            StepsToAdd.add(null);
                        } else {
                            StepsToAdd.addAll(trans);
                        }
                    }
                    System.out.println("");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + " algo mas");
                StepsToAdd.add(null);
            }
            Set<Integer> hashSet = new HashSet<>(StepsToAdd);
            StepsToAdd.clear();
            StepsToAdd.addAll(hashSet);
            caminos.addRutas(i, StepsToAdd);
        }
        return caminos;
    }

    public String saltoDeEtados(String cadena, char caracter, int estadoActual, int estadoSiguiente) {
        ArrayList<Integer> alQueVa = this.Delta.getMove(estadoActual, caracter);
        System.out.println(cadena + " " + caracter + " " + estadoActual + " " + estadoSiguiente);

        String camino = "[{" + Q.get(estadoActual) + "}, " + cadena + "] -> ";

        if (!alQueVa.contains(estadoSiguiente)) {
            try {
                ArrayList<Integer> conLambda = this.Delta.getMove(estadoActual, this.lambda);
                for (int i = 0; i < conLambda.size(); i++) {
                    String estadosLambda = saltoDeEtados(cadena, caracter, conLambda.get(i), estadoSiguiente);
                    if (!estadosLambda.isEmpty()) {
                        return camino + estadosLambda;
                    }
                }
            } catch (Exception e) {

            }
            camino = "";
        }
        return camino;
    }

    public ProcesamientoCadenaAFNLambda procesamiento(String cadena, RespuestaMult respuesta) {
        ArrayList<Tupla> tupla = new ArrayList<>();

        ArrayList<Integer> resFinals = respuesta.getFinals();
        System.out.println(resFinals.size());
        for (int i = 0; i < resFinals.size(); i++) {
            ArrayList<Integer> caminoespecifico = respuesta.getCamino(i);
            String camino = "";
            for (int j = 0; j < caminoespecifico.size(); j++) {
                try {
                    int estadoActual = caminoespecifico.get(i);
                    char charActual = cadena.charAt(j);

                    int siguiente = caminoespecifico.get(i + 1);
                    camino += saltoDeEtados(cadena.substring(j), charActual, estadoActual, siguiente);
                } catch (Exception e) {
                    break;
                }
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

    public ArrayList<Character> ponerCadena(String cadena) {
        ArrayList<Character> asd = new ArrayList<>();
        for (int i = 0; i < cadena.length(); i++) {
            boolean d = false;
            for (char c : Sigma) {
                if (c == cadena.charAt(i)) {
                    d = true;
                }
            }
            if (!d) {
                asd.add(cadena.charAt(i));
            }
        }

        Set<Character> hashSet = new HashSet<>(asd);
        asd.clear();
        asd.addAll(hashSet);
        return asd;
    }
}
