/*
 * To change this license hea the template in the editor.
 */
package TeoriaDeLaCompu;

import AutomatasFinitos.AFD;
import Herramientas.Transition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Microsoft Windows 10
 */
public class EstadoAFD extends AutomatasEstados {
    private AFD Automata;
    
    Transition transicion = new Transition();
    String Sigma;
    int Estados;
    int Einicial;
    int finales;
    ArrayList<Integer> Finales = new ArrayList<Integer>();
    

    @Override
    public AutoReturn AutoReci() {
        Scanner leer = new Scanner(System.in);
             
        System.out.println("Ingrese el alfabeto");
        Sigma = leer.next();
        
        do {            
            Estados = ReadInt("Ingrese la cantidad de estados del automata");
            if(Estados < 1){
                System.out.println("Debe haber un por lo menos un estado de aceptación");
            }
        } while (Estados == 0);
        //Estados = ReadInt("Ingrese la cantidad de estados del automata");
        
        Einicial = ReadInt("Ingrese estado inicial del automata");
        while (Einicial >= Estados){
            Einicial = ReadInt("El estado inicial debe ser menor a la cantidad de estados");
        }
    
        do {            
            finales = ReadInt("Ingrese la cantidad de estados finales del automata");
            if(finales < 1){
                System.out.println("Debe haber un por lo menos un estado de aceptación");
            }else if(finales > Estados){
                System.out.println("La cantidad de estados de aceptacion no debe superar la cantidad total de estados");
            }
        } while (finales == 0);
        
        for(int i = 0; i < finales; i++){
            int estado = ReadInt("Ingrese el estado: ");
            while (estado > Estados){
                estado = ReadInt("El estado ingresado debe ser menor a la cantidad de estados del automata");
            }
            Finales.add(estado);   
        }

        for (int i = 0; i < Estados; i++) {
            for (int j = 0; j < Sigma.length(); j++) {
                int estado = ReadInt("Ingrese el estado al que viaja el estado q"+ i +" con la letra "+Sigma.substring(j, j+1)+":" );
                while (estado > Estados){
                    estado = ReadInt("El estado ingresado debe ser menor a la cantidad de estados del automata");
                }
                transicion.add(Sigma.substring(j, j+1),String.valueOf(i), estado);
            }
        }
        

        
        return AutoReturn.Next;
    }

    @Override
    public AutoReturn AutoCrear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AutoReturn AutoMostrar() {
        System.out.println("");
        return null;
    }

    @Override
    public AutoReturn Autovaluar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AutoReturn Autoresult() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    static public int ReadInt(String comando){
        System.out.println(comando);
        Scanner s = new Scanner(System.in);
        int i=0;
        boolean t = true;
        while(t){
            try{
                i =Integer.valueOf(s.nextLine().trim());
                if(i>0){
                    t = false;
                }else{
                    System.out.println("el numero dado debe ser mayor que 0");
                }
            }catch(NumberFormatException e){
                System.out.println("Debes dar un Numero unicamente, por favor intente de nuevo");
            }
        }
        return i;
    }
    
}
