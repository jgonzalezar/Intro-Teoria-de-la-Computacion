/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TeoriaDeLaCompu;

import AutomatasFinitos.AFD;
import AutomatasFinitos.AFN;
import Herramientas.CreadorAFD;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import Herramientas.Transition;
import Herramientas.Tuple;
/**
 *
 * @author fanat
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            switch(s.next()){
                case "2":
                   // AFNLSET();
                break;
                case "1":
                   // AFNSET();
                break;
                case "0":
                    //AFNtoAFD();
                break;
                case "20":
                   // AFDSET2();
                    break;
                case "3":
                    prueba();
                    break;
                default:
                    AFDSET();
                break;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //{{},{},{},{},{},{}}
    /*static private void AFNLSET() throws FileNotFoundException{
        char[] E = {'a','b'};
        int[] F = {5,4};
        int[][][] T = {{{2,5},{-1},{3},{-1},{-1},{-1}},{{1},{-1},{-1},{3},{-1},{5}},{{-1},{2},{-1},{4},{4},{-1}}};
        AFNL set = new AFNL(E, 6, 0, F, T);
        Scanner s = new Scanner(new File("src/CombinacionAlphabetos/E_ab10.txt"));
        while(s.hasNext()){
            String as = s.next();
            if(set.Delta(as))System.out.println(as);
        }
    }
    static private void AFNSET() throws FileNotFoundException{
        char[] E = {'a','b'};
        int[] F = {1,2};
        int[][][] T = {{{1,2},{-1},{2}},{{-1},{1},{-1},}};
        AFN set = new AFN(E, 3, 0, F, T);
        Scanner s = new Scanner(new File("src/CombinacionAlphabetos/E_ab10.txt"));
        while(s.hasNext()){
            String as = s.next();
            if(set.Delta(as))System.out.println(as);
        }
    }*/
    static private void AFDSET() throws FileNotFoundException{
        AFD set = CreadorAFD.leerAFD("src/Automatas/AFD/CantidadParDeDigitos_AB");
        Scanner s = new Scanner(new File("src/CombinacionAlphabetos/E_ab10.txt"));
        while(s.hasNext()){
            String as = s.next();
            if(set.procesarCadena(as))System.out.println(as);
        }
    }
    /*static private void AFDSET2() throws FileNotFoundException{
        String E = "ab";
        ArrayList<Integer> F = new ArrayList<>();
        ArrayList<Integer> F1 = new ArrayList<>();
        ArrayList<Integer> F2 = new ArrayList<>();
        F.add(0);
        Transition T = new Transition();
        F1.add(1);
        F1.add(0);
        F2.add(1);
        F2.add(null);
        AFD set = new AFD(E, 3, 0, F, T);
        Scanner s = new Scanner(new File("src/CombinacionAlphabetos/E_ab10.txt"));
        String[] lis ={"a","aa","bbb"};

        set.procesarListaCadenas(lis, "src/CombinacionAlphabetos/E", true);
        
    }*/
    
    static private void prueba(){
       
	ArrayList<Tuple> Delta = new ArrayList<>();
        Delta.add(new Tuple("b",0,1));
	Delta.add(new Tuple("b",0,2));
	Delta.add(new Tuple("a",1,0));
	Delta.add(new Tuple("a",1,1));
	Delta.add(new Tuple("a",2,3));
	Delta.add(new Tuple("b",3,0));
        
        ArrayList<Integer> F = new ArrayList<>();
        F.add(0);
	F.add(3);
        
        char[] alfabeto = {'a','b'};
        AFN automataNoDet = new AFN(alfabeto,3,0,F,Delta);
        System.out.println("");
        System.out.println("Procesar cadena");
        System.out.println(automataNoDet.procesarCadena("baa"));
        System.out.println("");
        System.out.println("Procesar cadena con detalles");
        System.out.println(automataNoDet.procesarCadenaConDetalles("baa"));
        System.out.println("");
        System.out.println("Computar todos los procesamientos");
        automataNoDet.computarTodosLosProcesamientos("baa");
        System.out.println("");
        System.out.println("Computar una lista de cadenas");
        String[] prueba = {"bba","baa","bab"};
        automataNoDet.procesarListaCadenas(prueba,"prueba.txt", true);
//        automataNoDet.printAutomata();
    }
    
    /*static private void AFNtoAFD() throws FileNotFoundException{
        char[] E = {'a','b'};
        int[] F = {1,2};
        int[][][] T = {{{1,2},{-1},{2}},{{-1},{1},{-1},}};
        AFN set2 = new AFN(E, 3, 0, F, T);
        Scanner s = new Scanner(new File("src/CombinacionAlphabetos/E_ab10.txt"));
        AFD set = set2.transform();
        int[] f = set.F;
        for (int i = 0; i < f.length; i++) {
            System.out.println(f[i]);
        }
       int[][] t = set.T;
        for (int i = 0; i < set.Q; i++) {
            System.out.println(i+" "+t[0][i]+" "+ t[1][i]);
        }
        
        while(s.hasNext()){
            String as = s.next();
            if(set.Delta(as))System.out.println(as);
        }
    }*/
}

