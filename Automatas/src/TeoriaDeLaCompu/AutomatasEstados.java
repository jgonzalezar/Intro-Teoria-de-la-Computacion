/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TeoriaDeLaCompu;


/**
 *
 * @author fanat
 */
public abstract class AutomatasEstados {
    enum AutoMaqui{
      AFDReci,AFDCrear,AFDMostrar,AFDEvaluar,AFDresult,
    }
    enum AutoReturn{
      Next, Prev,Salir
    }
    public AutoMaqui estate = AutoMaqui.AFDReci;
    
    public void Estado(){
        switch(estate){
            case AFDReci:
                break;
            case AFDCrear:
                break;
            case AFDMostrar:
                break;
            case AFDEvaluar:
                break;
            case AFDresult:
                break;
            default:
                throw new AssertionError(estate.name());
            
        }
    }
    
    public abstract AutoReturn AutoReci();
    public abstract AutoReturn AutoCrear();
    public abstract AutoReturn AutoMostrar();
    public abstract AutoReturn Autovaluar();
    public abstract AutoReturn Autoresult();
    
    
}
