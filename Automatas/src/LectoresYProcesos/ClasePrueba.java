/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresYProcesos;

import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 *
 * @author fanat
 */
public class ClasePrueba {
    static boolean salir;
    enum Lectura{
        CrearAutomata,LeerCadena,
    }
    public static void main(String[] args) {
        salir = false;
        String url = "";
        
        Lectura lec = Lectura.CrearAutomata;
        Scanner scan = new Scanner(System.in);
        System.out.println("la palabra \"$$EXIT$$\" esta reservada para salir de la aplicacion automaticamente");
        JFileChooser fileChooser = new JFileChooser();
        while(!salir){
            String scad = scan.next();
            if(scad.equals("$$EXIT$$"))salir=true;
            switch(lec){
                case CrearAutomata:
                    System.out.println("seleccione el automata que desea importar");
                    
                    fileChooser.showOpenDialog(fileChooser);
                    url = fileChooser.getSelectedFile().getAbsolutePath();
                    CreadorAutomata.Type dis = CreadorAutomata.CheckType(url);
                    System.out.println(dis);
                    break;
                case LeerCadena:
                    break;
                default:
                    throw new AssertionError(lec.name());
                
            }
        }
    }
    
    private static void probarAFD(){
        
        
        
    }
    
    private static void probarAFN(){
        
    }
    
    private static void probarAFNLambda(){
        
    }
    /*
    Debe hacer una clase ClasePrueba para ejecutar todos los métodos realizados,
    creando autómatas, procesando cadenas con ellos,
    creando los objetos respectivos de acuerdo a dichos procesamientos,
    procesando listas de cadenas, generando los archivos.
    Dichas pruebas deben ser trabajadas en los siguientes métodos, 
    los cuales deben ser llamadas desde el procesamiento principal:
    probarAFD(): Crear autómatas AFD, procesar cadenas con y sin detalles,
    procesar listas de cadenas, generar archivos.
    probarAFN(): Crear autómatas AFN, procesar cadenas mostrando solo un procesamiento de aceptación,  procesar cadenas mostrando todos los procesamientos posibles, consultar los procesamientos de aceptación, abortados y de rechazo,  procesar listas de cadenas, generar archivos.
    probarAFNLambda(): Crear autómatas AFN-λ, calcular la λ- clausura de un estado,  calcular la λ- clausura de un conjunto de estados, procesar cadenas mostrando solo un procesamiento de aceptación,  procesar cadenas mostrando todos los procesamientos posibles, consultar los procesamientos de aceptación, abortados y de rechazo, procesar listas de cadenas, generar archivos.
    main(): invoca a los otros para que puedan ser comentados fácilmente y poder escoger cuál se va a probar.
    
    */
    
}
