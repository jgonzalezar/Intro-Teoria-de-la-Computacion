/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visuall;

import AutomatasFinitos.AFD;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author fanat
 */
public class Windows extends JFrame {
    static boolean hey;
    Dibbujo vent;
    JComboBox<String> box;
    AFD aff;
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser(new File("."));
        fileChooser.setDialogTitle("Seleccione el automata que desea importar");
        
        try {
            if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.CANCEL_OPTION) {
                throw new NullPointerException();
            }
            String url;
            url = fileChooser.getSelectedFile().getAbsolutePath();
            AFD aff = new AFD(url);
            JFrame jf= new Windows("Tutorial",aff);
            while (hey){
                System.out.print("");
            }
        }catch(HeadlessException | FileNotFoundException | NullPointerException e){
                
        }
        
        
        
    }

    public Windows(String tittle, AFD aff){
        super(tittle);
        hey=true;
        
        initFrame();
        initCanvas();
        initButtons();
        initEstados();
        
        //int a=0;
        /*while(a<400){
            vent.repaint();
            System.out.println(a);
            a++;
        }*/
    }
    
    private void initFrame(){
        setSize(600, 400);
        setVisible(true);
        //getContentPane().setBackground(Color.DARK_GRAY);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initCanvas(){
         vent = new Dibbujo("estate0");
        vent.setBackground(Color.BLACK);
        vent.setBounds(10,10,200, 200);
        getContentPane().add(vent);
    }
    
    @Override
    public void dispose() {
        hey=false;
        super.dispose(); //To change body of generated methods, choose Tools | Templates.
    }

    private void initButtons() {
        JButton next= new JButton("next");
        next.setBounds(10, 250, 30, 30);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vent.repaint();
                
            }
        });
        getContentPane().add(next);
    }
    
    /*
    
    */

    private void initEstados() {
        ArrayList<String> Qa= aff.getQ();
        String[] estados = new String[Qa.size()];
        for (int i = 0; i < Qa.size(); i++) {
            estados[i]=Qa.get(i);
        }
        JLabel display = new JLabel("Ubuntu");

        createLayout(box, display);
        box = new JComboBox<>(estados);
        box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) {
                    display.setText(e.getItem().toString());
                }
            }
        });

        

        setTitle("JComboBox");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    private void ChangeEstado(String namenext,char used,boolean ini,boolean Fin, boolean tp ){
        
    }
}
