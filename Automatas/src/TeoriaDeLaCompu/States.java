
package TeoriaDeLaCompu;

import TeoriaDeLaCompu.AutomatasEstados.AutoReturn;




/**
 *
 * @author Fabian Montes
 */
public class States {
    
    
    /**
    * Estado en que se encuentra la maquina actualmente
    * @author fanat
    */
   enum Maquina{
       /**
        * Escoger es el estado Donde se realiza la seleccion del automata a crear
        */
       Escoger,
       /**
        * Auto es el estado donde se realiza las acciones de la clase AutomatasEstados
        */
       Auto,
       /**
        * Estado que marca la salida del programa
        */
       Salir
   }
    
    enum Recib{
        AFN,AFD,AFNL,Salir
    }
    
    Maquina stados = Maquina.Escoger;
    AutomatasEstados maquina = null;

    public static void main(String[] args) {
        States sta = new States();
        while(sta.stados!=Maquina.Salir){
            sta.Estado();
        }
    }
    
    
    public void Estado(){
        switch(stados){
            case Escoger:
                Recib dato = RecibirDato();
                switch(dato){
                    case Salir:
                        stados = Maquina.Salir;
                    break;
                    case AFN:
                        
                    case AFD:
                        
                    case AFNL:
                        
                        stados = Maquina.Auto;
                    break;
                    default:
                        throw new AssertionError(dato.name());
                        
                }
                
                break;
            case Auto:
                if(maquina.Estado() == AutoReturn.Salir){
                    stados = Maquina.Salir;
                }
                break;
            case Salir:
                
                break;
         
            default:
                throw new AssertionError(stados.name());
            
        }
        
    }

    private Recib RecibirDato() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
