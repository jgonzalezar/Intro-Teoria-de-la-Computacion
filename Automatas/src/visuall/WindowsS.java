/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visuall;

import AutomatasFinitos.AFD;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;

/**
 * la clase Windows1 extiende de la clase windows y esta diseñada para mostrar una version de la ventana que permite moverse a travez del automata de forma libre pero no puede guardar nada
 * @author fanat
 */
public class WindowsS extends JFrame{
    boolean hey;
    //ArrayList<
    
    /**
     * constructor para crear la ventana
     * @param tittle titulo de la ventana
     * @param Table tabla a mostrar
     */

    public WindowsS(String tittle, String Table[][],String[] Q){
        super("tittle");
        JTable sd  =new JTable(Table, Q);
        sd.setBounds(10,10,400,400);
        hey=true;
        initFrame();
        getContentPane().add(sd);
    }
    
    private void initFrame(){
        setSize(420, 420);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
        
    
    @Override
    public void dispose() {
        hey=false;
        setVisible(false);
        super.dispose();
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

