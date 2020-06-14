/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos;

import LectoresYProcesos.CreadorAutomata;
import AutomatasFinitos.AFD;
import Herramientas.Transition;
import java.util.ArrayList;
import java.lang.Object;
import java.util.HashMap;

/**
 *
 * @author equipo los javas
 * @version 1.2
 */
public class AFDProducto
{
    ArrayList<Character> Sigma1 = null;
    ArrayList<Character> Sigma2 = null;
    ArrayList<String> Q = null;
    ArrayList<String> Q1 = null;
    ArrayList<String> Q2 = null;
    String q1 = null;
    String q2 = null;
    ArrayList<String> F1 = null;
    ArrayList<String> F2 = null;
    HashMap<Character,HashMap<String,String>> Delta;
    HashMap<Character,HashMap<String,String>> Delta1;
    HashMap<Character,HashMap<String,String>> Delta2;
    
    Transition transformation = new Transition();
    
        
    /**
     * Método que genera el producto cartesiano de dos autómatas
     * exceptuando los estados aceptados.
     * @param afd1 Autómata M1
     * @param afd2 Autómata M2
     * @param Q Producto cartesiano de Q1 y Q2
     * @param Q1 Conjunto de estados de M1
     * @param Q2 Conjunto de estados de M2
     * @param q1 Estado inicial de M1
     * @param q2 Estado Inicial de M2
     * @param Delta1 Función de transición de M1
     * @param Delta2 Función de Transición de M2
     */
    public void productoCartesiano(AFD afd1, AFD afd2, ArrayList<String> Q, ArrayList<String> Q1, ArrayList<String> Q2, String q1, String q2, HashMap<Character,HashMap<String,String>> Delta, HashMap<Character,HashMap<String,String>> Delta1, HashMap<Character,HashMap<String,String>> Delta2)
    {
        if (!(Sigma1.equals(Sigma2)))
            {
                System.out.println("Los alfabetos deben ser iguales para poder realizar el producto cartesiano de los autómatas.");
            }
        //Genera Q
        for (int i = 0; i < Q1.size(); i++) 
          for (int j = 0; j < Q2.size(); j++) 
            Q.add("("+Q1.get(i)+Q2.get(j)+")");
        //Genera Delta
        if(Delta.get(Sigma1)==null){
            Delta.put(Sigma1.get(0), new HashMap<>());
        }
        for (int i = 0; i < Q1.size(); i++) 
          for (int j = 0; j < Q2.size(); j++) 
          {
            Delta.get(Sigma1).put(transformation.cambio(Sigma1.get(i), Q1.get(i)), transformation.cambio(Sigma1.get(j), Q2.get(i)));
          }
            
    }
    
    public void hallarProductoCartesianoY(AFD afd1, AFD afd2)
    {
        
    }
    
    public void hallarProductoCartesianoO(AFD afd1, AFD afd2)
    {
        
    }
    
    public void hallarProductoCartesianoDiferencia(AFD afd1, AFD afd2)
    {
        
    }
    
    public void hallarProductoCartesianoDiferenciaSimetrica(AFD afd1, AFD afd2)
    {
        
    }
    
    public void hallarProductoCartesiano(AFD afd1, AFD afd2, String operacion)
    {
        
    }
    
}
