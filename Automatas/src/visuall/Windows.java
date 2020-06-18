/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visuall;

import AutomatasFinitos.AFD;

import javax.swing.JFrame;

/**
 *
 * @author fanat
 */
public class Windows extends JFrame{
    static boolean hey;
    protected Dibbujo vent;
    protected int time;
    protected AFD aff;

    public Windows(String tittle, AFD aff){
        super("tittle");
        hey=true;
        this.aff=aff;
        initFrame();
        initCanvas();
        time=100;
    }
    
    private void initFrame(){
        setSize(600, 250);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initCanvas(){
        vent = new Dibbujo(aff.getQ().get(aff.getQ0()),aff.getF().contains(aff.getQ0()));
        vent.setBounds(10,10,200, 200);
        getContentPane().add(vent);
    }
    
    @Override
    public void dispose() {
        hey=false;
        setVisible(false);
        super.dispose();
    }
    
    protected void ChangeEstado(String namenext,char used,boolean Fin, int tp ){
        vent.setData(namenext, Fin, tp, used);       
        if(tp==0){
            time=1000;
        }else{
            time=100;
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
    
    public void Simulat(){
        while(hey&&isVisible()){
            try
            {
                Thread.sleep(500);
            }catch(InterruptedException d){}
        }
    }
}

