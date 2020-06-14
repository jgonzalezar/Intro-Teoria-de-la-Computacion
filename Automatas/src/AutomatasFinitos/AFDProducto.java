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

/**
 *
 * @author equipo los javas
 * @version 1.2
 */
public class AFDProducto
{
    ArrayList<String> Sigma1 = null;
    ArrayList<String> Sigma2 = null;
    ArrayList<String> Q = null;
    ArrayList<String> Q1 = null;
    ArrayList<String> Q2 = null;
    String q1 = null;
    String q2 = null;
    ArrayList<String> F1 = null;
    ArrayList<String> F2 = null;
    Transition Delta1 = null;
    Transition Delta2 = null;
    
    /*
    if (!(Sigma1.equals(Sigma2)))
    {
        System.out.println("Los alfabetos deben ser iguales para poder realizar el producto cartesiano de los autómatas.");
    }
    */
    
       
    public void productoCartesiano(AFD afd1, AFD afd2, ArrayList<String> Q, ArrayList<String> Q1, ArrayList<String> Q2, String q1, String q2)
    {
        if (!(Sigma1.equals(Sigma2)))
            {
                System.out.println("Los alfabetos deben ser iguales para poder realizar el producto cartesiano de los autómatas.");
            }
        for (int i = 0; i < Q1.size(); i++) 
          for (int j = 0; j < Q2.size(); j++) 
            Q.add("("+Q1.get(i)+Q2.get(j)+")");
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
