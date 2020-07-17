/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visuall;

import AutomatasFinitos.AFD;
import AutomatasFinitos.AFNL;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *la clase Windows extiende JFrame y crea una ventana que realiza un dibujo especifico para visualizar un automata
 * @author fanat
 */
public class WindowsAFNL extends JFrame{
    static boolean hey;
    protected Dibbujo1 vent;
    protected int time;
    protected AFNL aff;

    
    /**
     * consturctor de la clase recibe el titulo de la ventana y el automata a representar
     * @param tittle titulo de la ventana
     * @param aff afd a usar
     */
    public WindowsAFNL(String tittle, AFNL aff){
        super(tittle);
        hey=true;
        this.aff=aff;
        initFrame();
        initCanvas();
        time=100;
    }
    
    private void initFrame(){
        setSize(1000, 700);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initCanvas(){
        vent = new Dibbujo1(aff);
        //vent.setBounds(10,10,200, 200);
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.getViewport().setView(vent);
        jScrollPane.setBounds(10,10, 600, 300);
        getContentPane().add(jScrollPane);
    }
    /**
     * variacion del metodo dispose para ayufar con el ciclo
     */
    
    
    @Override
    public void dispose() {
        hey=false;
        setVisible(false);
        super.dispose();
    }
    
    /**
     * metodo para realizar cambios en la animacion del dibujoo
     * @param namenext nombre del proximo estado
     * @param used caracter usado para el desplazamiento
     * @param Fin es final es siguient estado
     * @param tp lel desplazamiento realizado es instantaneo o por direccion
     */
    
    protected void ChangeEstado(String namenext,char used,boolean Fin, int tp ){
        vent.setData(namenext, Fin, tp, used);       
        if(tp==0){
            time=1000;
        }else{
            time=50;
        }
        ReDraw();
    }
    
    private void ReDraw(){
        new Thread(() -> {
            while(!vent.isStatic()){
                try
                {
                    Thread.sleep(time);
                }catch(InterruptedException d){}
                vent.repaint();
            }
        }).start();
    }
    
    /**
     * funcion de simulacion para detener el programa se recomienda llamarla una vez creado la ventana
     */
    
    public void Simulat(){
        while(hey&&isVisible()){
            try
            {
                Thread.sleep(500);
            }catch(InterruptedException d){}
        }
    }
}

