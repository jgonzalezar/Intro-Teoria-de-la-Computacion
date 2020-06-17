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
                System.out.println(e.toString());
        }
        
        
        
    }

    public Windows(String tittle, AFD aff){
        super(tittle);
        hey=true;
        this.aff=aff;
        System.out.println("aa");
        initFrame();
        initCanvas();
        initButtons();
        System.out.println("ggg");
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
        super.dispose(); //To change body of generated methods, choose Tools | Templates.
    }

    private void initButtons() {
        JButton next= new JButton("next");
        next.setBounds(10, 250, 30, 30);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeEstado(box.getSelectedItem().toString(),' ',aff.getF().contains(box.getSelectedIndex()),true);
                for(int i=0;i<5;i++){
                    try
                    {
                        Thread.sleep(200);
                    }catch(InterruptedException d){}
                    
                    vent.repaint();
                    System.out.println("out");

                }
                System.out.println("out");
            }
        });
        getContentPane().add(next);
    }
    
    private void repa(){
        JButton s = new JButton();
        
        s.
    }

    private void initEstados() {
       
        ArrayList<String> Qa= aff.getQ();
        
        
        String[] estados = new String[Qa.size()];
        for (int i = 0; i < Qa.size(); i++) {
            
            estados[i]=Qa.get(i);
        }
       

        //createLayout(box, display);
        box = new JComboBox<>(estados);
        box.setSelectedIndex(aff.getQ0());
        box.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                //display.setText(e.getItem().toString());
            }
        });
        box.setBackground(Color.GRAY);
        box.setForeground(Color.BLACK);
        box.setBounds(100,200,100,100);
        box.setVisible(true);
        getContentPane().add(box);
        
    }
    private void ChangeEstado(String namenext,char used,boolean Fin, boolean tp ){
        boolean stay=true;
        long Prev =0;
        vent.setData(namenext, Fin, tp, used);       
        /*while(stay){
            long mil = System.currentTimeMillis();
            if(mil-Prev>200){
                Prev=mil;
                System.out.println("hoo"+Prev);
                vent.repaint();
                stay=!vent.isStatic();
            }
            
        }*/
        
    }
}
