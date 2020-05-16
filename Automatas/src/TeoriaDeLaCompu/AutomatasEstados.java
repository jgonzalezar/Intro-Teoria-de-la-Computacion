package TeoriaDeLaCompu;


/**

 * Esta clase abstracta será el menú, cada item del menú es representado por un estado

 * @author: Juan Gonzalez

 * @version: 1

 */
public abstract class AutomatasEstados {
    /**
    * Estado en que se encuentra la maquina actualmente
    * @author fanat
    */
    enum AutoMaqui{
      AutoReci,AutoCrear,AutoMostrar,AutoEvaluar,Autoresult,
    }
    
    /**
    * Estado en que se encuentra la maquina cuando se pasa de un estado a otro
    * @author fanat
    */
    enum AutoReturn{
      Next, Prev,Salir
    }
    
    /**

     * no se xd

     * 

     */
    public AutoMaqui estate = AutoMaqui.AutoReci;
    
    /**

     * Método que representa el menú o la máquina de estados en la que se baja el menu

     * 

     */
    public void Estado(){
        switch(estate){
            case AutoReci:
                break;
            case AutoCrear:
                break;
            case AutoMostrar:
                break;
            case AutoEvaluar:
                break;
            case Autoresult:
                break;
            default:
                throw new AssertionError(estate.name());
                        
        }
    }
    
    /**

     * Método que representa el estado en el que se recibiran los datos del automata

     */
    public abstract AutoReturn AutoReci();
    
    /**

     * Método que representa el estado en el que se se crea el automata

     */
    public abstract AutoReturn AutoCrear();
    
    /**

     * Método que representa el estado en el que muestra el automata

     */
    public abstract AutoReturn AutoMostrar();
    
    /**

     * Método que .....

     */
    public abstract AutoReturn Autovaluar();
    
    /**

     * Método que ....

     */
    public abstract AutoReturn Autoresult();
    
    
}
