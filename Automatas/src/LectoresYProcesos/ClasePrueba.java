/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresYProcesos;

import AutomatasFinitos.AFD;
import AutomatasFinitos.AFDComplemento;
import AutomatasFinitos.AFDSimplificacion;
import AutomatasFinitos.AFN;
import AutomatasFinitos.AFNL;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import visuall.*;

/**
 *
 * @author fanat
 */
public class ClasePrueba {

    static boolean salir;
    static InteraccionesAutomas.Type tp;
    static String url;

    /**
     * enum lectura del main para saber si esta creando un automata o realizando otras acciones
     */
    
    public enum Lectura {
        CrearAutomata, LeerCadena, salir
    }
    /**
     * main que realiza el proyecto 
     * @param args 
     */
    public static void main(String[] args) {
        Lectura lec = Lectura.CrearAutomata;
        Scanner scan = new Scanner(System.in);
        JFileChooser fileChooser = new JFileChooser(new File("."));
        fileChooser.setDialogTitle("Seleccione el automata que desea importar");
        while (!Lectura.salir.equals(lec)) {
            switch (lec) {
                case CrearAutomata:
                    System.out.println("Seleccione el automata que desea importar");
                    try {
                        if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.CANCEL_OPTION) {
                            throw new NullPointerException();
                        }
                        String url;
                        url = fileChooser.getSelectedFile().getAbsolutePath();
                        tp = InteraccionesAutomas.CheckType(url);

                        int slashIndex = url.lastIndexOf('\\');
                        int dotIndex = url.lastIndexOf('.');
                        String expresion;
                        if (dotIndex == -1) {
                            expresion = url.substring(slashIndex + 1);
                        } else {
                            expresion = url.substring(slashIndex + 1, dotIndex);
                        }
                        expresion = expresion.replace('\'', '*');
                        System.out.println(expresion);
                        String message = "";
                        switch (tp) {
                            case AFD:
                                message = "Ha seleccionado un Automata finito determinista que representa la expresion " + expresion;
                                String[] options1 = {"realizar procesos\n sobre el automata","Simplificar el Automata", "Realizar el ProductoCartesiano\n con otro automata"};
                                int f = JOptionPane.showOptionDialog(null, message+"\n Indique la proxima accion a realizar", "Bienvenido, escoja una opción", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, "Salir");
                                switch(f){
                                    case 1:
                                        tp=InteraccionesAutomas.Type.AFDsimplfic;
                                        break;
                                    case 2:
                                        tp=InteraccionesAutomas.Type.AFDcomplement;
                                        break;
                                }
                                break;
                            case AFN:
                                message = "Ha seleccionado un Automata finito no determinista que representa la expresion " + expresion;
                                int i = JOptionPane.showConfirmDialog(null, "Desea convertir el AFN en un AFD?", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                                switch(i){
                                    case JOptionPane.YES_OPTION:
                                        tp=InteraccionesAutomas.Type.AFNtoAFD;
                                        break;
                                }
                                break;
                            case AFNL:
                                message = "Ha seleccionado un Automata finito no determinista con transiciones lambda que representa la expresion " + expresion;
                                int j = JOptionPane.showConfirmDialog(null, "Desea convertir el AFNL en un AFD o en un AFN?", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                                switch(j){
                                    case JOptionPane.YES_OPTION:
                                        tp=InteraccionesAutomas.Type.AFNLtoAFD;
                                        break;
                                }
                                break;
                        }
                        ClasePrueba.url=url;
                        System.out.println(message);
                        lec = Lectura.LeerCadena;
                    } catch (Exception e) {
                        if (JOptionPane.showConfirmDialog(null, "No ha ingresado ningún automata. ¿Desea salir? Y/N", "Error", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                            lec = Lectura.salir;
                        }
                    }
                    pause();
                    break;
                case LeerCadena:
                    switch (tp) {
                        case AFD:
                            lec = probarAFD();
                            break;
                        case AFN:
                            lec = probarAFN();
                            break;
                        case AFNL:
                            lec = probarAFNLambda();
                            break;
                        case AFNtoAFD:
                            lec = probarAFNoAFD();
                            break;
                        case AFNLtoAFD:
                            lec = probarAFNLambdaToAFN();
                            break;
                        case AFDsimplfic:
                            lec=probarSimplificacion();
                            
                            
                    }
                    break;
                case salir:
                    break;
                default:
                    lec = Lectura.salir;
                    break;

            }
            
        }
    }

    private static Lectura probarAFD() {
        try {
            AFD afd = new AFD(url);
            while (true) {
                String[] options1 = {"mostrar visual del automata", "simular cadenas de forma visual", "procesar Cadenas", "Salir"};
                int f = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Bienvenido, escoja una opción", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, "Salir");
                switch (f) {
                    case 0://tostring
                        int slashIndex = url.lastIndexOf('\\');
                        int dotIndex = url.lastIndexOf('.');
                        String expresion;
                        if (dotIndex == -1) {
                            expresion = url.substring(slashIndex + 1);
                        } else {
                            expresion = url.substring(slashIndex + 1, dotIndex);
                        }
                        expresion = expresion.replace('\'', '*');
                        Windows1 visual = new Windows1(expresion, afd);
                        visual.Simulat();
                        break;
                    case 2://visual forzado
                        int s = url.lastIndexOf('\\');
                        int df = url.lastIndexOf('.');
                        String exe;
                        if (df == -1) {
                            exe = url.substring(s + 1);
                        } else {
                            exe = url.substring(s + 1, df);
                        }
                        exe = exe.replace('\'', '*');
                        Windows3 visuals = new Windows3(exe, afd);
                        visuals.Simulat();
                        break;
                    case 3:
                    int i = JOptionPane.showConfirmDialog(null, "Desea ingresar más de una cadena?", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    switch (i) {
                        case JOptionPane.YES_OPTION:
                            boolean dos = true;
                            do {
                                try {
                                    procsVariasCadenas(afd);
                                    dos = false;
                                } catch (NullPointerException e) {
                                    dos = false;
                                }

                            } while (dos);
                            break;

                        case JOptionPane.NO_OPTION:
                            boolean tres = true;
                            do {
                                try {
                                    String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                                    ArrayList<Character> error = afd.ponerCadena(cadena);
                                    if (error.size() > 0) {
                                        String errors = "";
                                        for (Character character : error) {
                                            errors += character + " ";
                                        }
                                        JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        boolean set;
                                        if (JOptionPane.showConfirmDialog(null, "Desea mostrar el camino recorrido a la hora de evaluar la cadena?", "Seleccione una opción", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                                            set = afd.procesarCadenaConDetalles(cadena);
                                        } else {
                                            set = afd.procesarCadena(cadena);
                                        }
                                        if (set) {
                                            System.out.println("La cadena: " + cadena + " es aceptada");
                                        } else {
                                            System.out.println("La cadena: " + cadena + " no es aceptada");
                                        }
                                        tres = false;
                                    }
                                } catch (NullPointerException e) {
                                    tres = false;
                                }

                            } while (tres);

                            break;

                        default:
                            //una
                            break;
                    }
                    break;
                }
                pause();
                String[] options = {"Evaluar otra cadena", "Cambiar De Automata", "Salir"};
                int fs = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Seleccione", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Salir");
                if (fs == 2 || fs == JOptionPane.CLOSED_OPTION) {
                    return Lectura.salir;
                } else if (fs == 1) {
                    return Lectura.CrearAutomata;
                }
            }
        } catch (Error e) {
            System.err.print(e.getMessage());
            return Lectura.CrearAutomata;
        } catch (FileNotFoundException e) {

            return Lectura.CrearAutomata;
        }
    }

    private static Lectura probarAFN() {
        int d = 0;
        while (d == 0) {
            try {
                AFN afn = new AFN(url);
                System.out.println("El automata ha sido creado correctamente");
                int i = JOptionPane.showConfirmDialog(null, "Desea ingresar más de una cadena?", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                switch (i) {
                    case JOptionPane.YES_OPTION:
                        boolean dos = true;
                        do {
                            try {
                                ArrayList<String> cadenas = new ArrayList<>();
                                int k = JOptionPane.showConfirmDialog(null, "Desea ingresar un archivo con las cadenas", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                                switch (k) {
                                    case JOptionPane.YES_OPTION:
                                        JFileChooser file = new JFileChooser(new File("."));
                                        file.setDialogTitle("Seleccione el archivo que contiene la lista de cadenas");
                                        if (file.showOpenDialog(file) == JFileChooser.CANCEL_OPTION) {
                                            throw new NullPointerException();
                                        }
                                        String asd = file.getSelectedFile().getAbsolutePath();

                                        Scanner s = new Scanner(new File(asd));
                                        while (s.hasNext()) {
                                            String as = s.next();
                                            System.out.println(as);
                                            if (!(afn.ponerCadena(as).size() > 0)) {
                                                cadenas.add(as);
                                            }
                                        }
                                        break;
                                    case JOptionPane.NO_OPTION:
                                        int de = JOptionPane.YES_OPTION;
                                        do {
                                            try {
                                                String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                                                ArrayList<Character> error = afn.ponerCadena(cadena);
                                                if (error.size() > 0) {
                                                    String errors = "";
                                                    for (Character character : error) {
                                                        errors += character + " ";
                                                    }
                                                    JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                                } else {
                                                    cadenas.add(cadena);
                                                    de = JOptionPane.showConfirmDialog(null, "¿Desea agregar otra cadena?", "Recepcion de cadenas", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                                }
                                            } catch (NullPointerException e) {
                                                de = JOptionPane.CANCEL_OPTION;
                                            }
                                        } while (de == JOptionPane.YES_OPTION);
                                        break;
                                }

                                Set<String> hashSet = new HashSet<>(cadenas);
                                cadenas.clear();
                                cadenas.addAll(hashSet);
                                String cadenasd = "";
                                for (String cadena : cadenas) {
                                    cadenasd += cadena + "\n";
                                }
                                JOptionPane.showMessageDialog(null, "Las cadenas dadas son: \n" + cadenasd, "Cadenas Dadas", JOptionPane.INFORMATION_MESSAGE);
                                JFileChooser file = new JFileChooser(new File("."));
                                file.setDialogTitle("Elija nombre y ubicación para el archivo con la respuesta");
                                file.setSelectedFile(new File("Respuesta.txt"));
                                if (file.showSaveDialog(file) == JFileChooser.CANCEL_OPTION) {
                                    throw new NullPointerException();
                                }
                                String asd = file.getSelectedFile().getAbsolutePath();
                                String[] listaCadenas = new String[cadenas.size()];
                                for (int j = 0; j < cadenas.size(); j++) {
                                    listaCadenas[j] = cadenas.get(j);
                                }
                                afn.procesarListaCadenas(listaCadenas, asd, JOptionPane.showConfirmDialog(null, "Desea imprimir en consola tambien?", "Seleccione una opción", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
                                dos = false;
                            } catch (NullPointerException e) {
                                dos = false;
                            }
                        } while (dos);
                        break;

                    case JOptionPane.NO_OPTION:
                        boolean tres = true;
                        do {
                            try {
                                String[] options2 = {"Procesar cadena","Procesar y mostrar todas las computaciones","Imprimir alguna de las computaciones","Volver"};
                                int j = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Procesamiento de cadenas", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options2, "Salir");
                                
                                switch (j) {
                                    case 3:
                                        tres = false;
                                        break;
                                    case JOptionPane.CLOSED_OPTION:
                                        return Lectura.salir;
                                    case 0:
                                        {
                                            String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "Recepción de cadena");
                                            ArrayList<Character> error = afn.ponerCadena(cadena);
                                            if (error.size() > 0) {
                                                String errors = "";
                                                for (Character character : error) {
                                                    errors += character + " ";
                                                }
                                                JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                            } else {
                                                boolean set = afn.procesarCadena(cadena);
                                                if (set) {
                                                    System.out.println("La cadena: " + cadena + " es aceptada");
                                                } else {
                                                    System.out.println("La cadena: " + cadena + " no es aceptada");
                                                }
                                                tres = false;
                                            }       break;
                                        }
                                    case 1:
                                        {
                                            String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "Recepción de cadena");
                                            ArrayList<Character> error = afn.ponerCadena(cadena);
                                            if (error.size() > 0) {
                                                String errors = "";
                                                for (Character character : error) {
                                                    errors += character + " ";
                                                }
                                                JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                            } else {
                                                JFileChooser file = new JFileChooser(new File("."));
                                                file.setDialogTitle("Elija nombre y ubicación para el archivo con la respuesta");
                                                file.setSelectedFile(new File("Respuesta.txt"));
                                                if (file.showSaveDialog(file) == JFileChooser.CANCEL_OPTION) {
                                                    throw new NullPointerException();
                                                }
                                                String asd = file.getSelectedFile().getAbsolutePath();
                                                afn.computarTodosLosProcesamientos(cadena, asd);
                                                tres = false;
                                            }       break;
                                        }
                                    case 2:
                                        {
                                            String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "Recepción de cadena");
                                            ArrayList<Character> error = afn.ponerCadena(cadena);
                                            if (error.size() > 0) {
                                                String errors = "";
                                                for (Character character : error) {
                                                    errors += character + " ";
                                                }
                                                JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                            } else {
                                                boolean set = afn.procesarCadenaConDetalles(cadena);
                                                if (set) {
                                                    System.out.println("La cadena: " + cadena + " es aceptada");
                                                } else {
                                                    System.out.println("La cadena: " + cadena + " no es aceptada");
                                                }
                                                tres = false;
                                            }       break;
                                        }
                                    default:
                                        break;
                                }
                                
                            } catch (NullPointerException e) {
                                tres = false;
                            }

                        } while (tres);
                        break;
                    default:
                        //una
                        break;
                }
            } catch (Error e) {
                System.err.print(e.getMessage());
                return Lectura.CrearAutomata;
            } catch (FileNotFoundException e) {

                return Lectura.CrearAutomata;
            }
            pause();
            String[] options = {"Evaluar otra cadena", "Cambiar De Automata", "Salir"};
            int f = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Salir");
            if (f == 2 || f == JOptionPane.CLOSED_OPTION) {
                return Lectura.salir;
            } else if (f == 1) {
                return Lectura.CrearAutomata;
            }
        }
        return Lectura.LeerCadena;
    }

    private static Lectura probarAFNLambda() {
        try {
            AFNL afnl = new AFNL(url);
            System.out.println("El automata ha sido creado correctamente");
            while (true) {
                String[] options1 = {"Procesar cadena", "Lambda clausura", "Salir"};
                int f = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Bienvenido, escoja una opción", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, "Salir");
                if (f == 2 || f == JOptionPane.CLOSED_OPTION) {
                    return Lectura.salir;
                } else if (f == 0) {
                    int i = JOptionPane.showConfirmDialog(null, "Desea ingresar más de una cadena?", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    switch (i) {
                        case JOptionPane.YES_OPTION:
                            boolean dos = true;
                            do {
                                try {
                                    ArrayList<String> cadenas = new ArrayList<>();
                                    int k = JOptionPane.showConfirmDialog(null, "Desea ingresar un archivo con las cadenas", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                                    switch (k) {
                                        case JOptionPane.YES_OPTION:
                                            JFileChooser file = new JFileChooser(new File("."));
                                            file.setDialogTitle("Seleccione el archivo que contiene la lista de cadenas");
                                            if (file.showOpenDialog(file) == JFileChooser.CANCEL_OPTION) {
                                                throw new NullPointerException();
                                            }
                                            String asd = file.getSelectedFile().getAbsolutePath();

                                            Scanner s = new Scanner(new File(asd));
                                            while (s.hasNext()) {
                                                String as = s.next();
                                                if (!(afnl.ponerCadena(as).size() > 0)) {
                                                    cadenas.add(as);
                                                }
                                            }
                                            break;
                                        case JOptionPane.NO_OPTION:
                                            int de = JOptionPane.YES_OPTION;
                                            do {
                                                try {
                                                    
                                                String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                                                ArrayList<Character> error = afnl.ponerCadena(cadena);
                                                if (error.size() > 0) {
                                                    String errors = "";
                                                    for (Character character : error) {
                                                        errors += character + " ";
                                                    }
                                                    JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                                } else {
                                                    cadenas.add(cadena);
                                                    de = JOptionPane.showConfirmDialog(null, "¿Desea agregar otra cadena?", "Recepcion de cadenas", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                                }
                                                } catch (NullPointerException e) {
                                                    de = JOptionPane.CANCEL_OPTION;
                                                }
                                                 
                                            } while (de == JOptionPane.YES_OPTION);
                                            break;
                                    }

                                    Set<String> hashSet = new HashSet<>(cadenas);
                                    cadenas.clear();
                                    cadenas.addAll(hashSet);
                                    String cadenasd = "";
                                    for (String cadena : cadenas) {
                                        cadenasd += cadena + "\n";
                                    }
                                    JOptionPane.showMessageDialog(null, "Las cadenas dadas son: \n" + cadenasd, "Cadenas Dadas", JOptionPane.INFORMATION_MESSAGE);
                                    JFileChooser file = new JFileChooser(new File("."));
                                    file.setDialogTitle("Elija nombre y ubicación para el archivo con la respuesta");
                                    file.setSelectedFile(new File("Respuesta.txt"));
                                    if (file.showSaveDialog(file) == JFileChooser.CANCEL_OPTION) {
                                        throw new NullPointerException();
                                    }
                                    String asd = file.getSelectedFile().getAbsolutePath();
                                    String[] listaCadenas = new String[cadenas.size()];
                                    for (int j = 0; j < cadenas.size(); j++) {
                                        listaCadenas[j] = cadenas.get(j);
                                    }
                                    afnl.procesarListaCadenas(listaCadenas, asd, JOptionPane.showConfirmDialog(null, "Desea imprimir en consola tambien?", "detalles", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
                                    dos = false;
                                } catch (NullPointerException e) {
                                    dos = false;
                                }

                            } while (dos);
                            break;

                        case JOptionPane.NO_OPTION:
                            boolean cuatro = true;
                            do {
                                try{       
                                    String[] options2 = {"Procesar cadena", "Procesar y mostrar todas las computaciones" ,"Imprimir alguna de las computaciones" ,"Volver"};
                                    int j = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Procesamiento de cadenas", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options2, "Salir");
                                    if (j == 3){
                                        cuatro = false;
                                    } else if (j == JOptionPane.CLOSED_OPTION) {
                                        return Lectura.salir;
                                    } else if (j == 0) {
                                        boolean tres = true;
                                        do {
                                            try {
                                                String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                                                ArrayList<Character> error = afnl.ponerCadena(cadena);
                                                if (error.size() > 0) {
                                                    String errors = "";
                                                    for (Character character : error) {
                                                        errors += character + " ";
                                                    }
                                                    JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                                } else {
                                                    boolean set;
                                                    if (JOptionPane.showConfirmDialog(null, "Desea mostrar el camino recorrido a la hora de evaluar la cadena?", "Mostrala con detalles", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                                                        set = afnl.procesarCadenaConDetalles(cadena);
                                                    } else {
                                                        set = afnl.procesarCadena(cadena);
                                                    }
                                                    if (set) {
                                                        System.out.println("La cadena: " + cadena + " es aceptada");
                                                    } else {
                                                        System.out.println("La cadena: " + cadena + " no es aceptada");
                                                    }
                                                    tres = false;
                                                }
                                            } catch (NullPointerException e) {
                                                tres = false;
                                            }

                                        } while (tres);
                                    }else if(j == 1){
                                        boolean tres = true;
                                        do {
                                            try {
                                                String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                                                ArrayList<Character> error = afnl.ponerCadena(cadena);
                                                if (error.size() > 0) {
                                                    String errors = "";
                                                    for (Character character : error) {
                                                        errors += character + " ";
                                                    }
                                                    JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                                } else {
                                                    JFileChooser fileComputaciones = new JFileChooser(new File("."));
                                                    fileComputaciones.setDialogTitle("Elija nombre y ubicación para el archivo con la respuesta");
                                                    fileComputaciones.setSelectedFile(new File("Respuesta.txt"));
                                                    if (fileComputaciones.showSaveDialog(fileComputaciones) == JFileChooser.CANCEL_OPTION) {
                                                        throw new NullPointerException();
                                                    }
                                                    String dirArch = fileComputaciones.getSelectedFile().getAbsolutePath();
                                                    int respuesta = afnl.computarTodosLosProcesamientos(cadena, dirArch);
                                                    System.out.println("En total, hubo "+ respuesta+ " procesamientos");
                                                    tres = false;
                                                }
                                            } catch (NullPointerException e) {
                                                tres = false;
                                            }

                                        } while (tres);
                                    }else if(j == 2){
                                        boolean tres = true;
                                        do {
                                            try {
                                                String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                                                ArrayList<Character> error = afnl.ponerCadena(cadena);
                                                if (error.size() > 0) {
                                                    String errors = "";
                                                    for (Character character : error) {
                                                        errors += character + " ";
                                                    }
                                                    JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                                } else {
                                                    String[] options3 = {"Aceptados", "Rechazados" ,"Abortados" ,"Salir"};
                                                    int n = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Procedimiento que desea ver", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options3, "Salir");
                                                    if (n == 3 || n == JOptionPane.CLOSED_OPTION) {
                                                        return Lectura.salir;
                                                    } else {
                                                        afnl.imprimirComputaciones(cadena, n);
                                                    }
                                                    tres = false;
                                                }
                                            } catch (NullPointerException e) {
                                                tres = false;
                                            }

                                        } while (tres);
                                    }
                                } catch (NullPointerException e) {
                                    cuatro = false;
                                }

                            } while (cuatro);

                            break;

                        default:
                            break;
                    }
                    //return Lectura.CrearAutomata;
                }else if(f == 1){
                    String[] options4 = {"Lambda clausura un estado", "Lambda clausura varios estados", "Salir"};
                    int e = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Escoja una opción", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options4, "Salir");
                    if (e == 2 || e == JOptionPane.CLOSED_OPTION) {
                        return Lectura.salir;
                    } else if (e == 0) {
                        String[] estados = afnl.GetQ();
                        int estado = JOptionPane.showOptionDialog(null, "Escoja un estado", "Lambda clausura un estado", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, estados, estados[0]);
                        afnl.ImprimirlambdaClausura_unEstado(estado);
                    } else if (e == 1){
                        ArrayList<Integer> escogidos = new ArrayList<>();
                        String[] estados = afnl.GetQ();
                        boolean tres = true;
                            do {
                                try {
                                    int estado = JOptionPane.showOptionDialog(null, "Escoja un estado", "Lambda clausura un estado", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, estados, estados[0]);
                                    escogidos.add(estado);
                                    
                                    if (JOptionPane.showConfirmDialog(null, "¿Desea escoger otro estado?", "Lambda clausura varios estados", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
                                        Set<Integer> hashSet = new HashSet<>(escogidos);
                                        escogidos.clear();
                                        escogidos.addAll(hashSet);
                                        afnl.ImprimirlambdaClausura_variosEstado(escogidos);
                                        tres = false;
                                    }
                                } catch (NullPointerException h) {
                                    tres = false;
                                }
                            } while (tres);
                    }
                }
                pause();
                String[] options = {"Escoger otra opción", "Cambiar De Automata", "Salir"};
                int a = JOptionPane.showOptionDialog(null, "Indique la proxima acción a realizar", "¿Qué desea hacer?", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Salir");
                if (a == 2 || a == JOptionPane.CLOSED_OPTION) {
                    return Lectura.salir;
                } else if (a == 1) {
                    return Lectura.CrearAutomata;
                }
            }
        } catch (Error e) {
            System.err.print(e.getMessage());
            return Lectura.CrearAutomata;
        } catch (FileNotFoundException e) {

            return Lectura.CrearAutomata;
        }
    }
    
    private static Lectura probarAFNoAFD(){
        try {
            AFN afn = new AFN(url);
            AFD afd = InteraccionesAutomas.AFNtoAFD(afn);
            System.out.println("Los automatas han sido creados correctamente \n");
            while (true) {
                String[] options1 = {"Procesar cadena", "Validar transformacion", "Imprimir automatas" ,"Salir"};
                int f = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Bienvenido, escoja una opción", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, "Salir");
                if (f == 3 || f == JOptionPane.CLOSED_OPTION) {
                    return Lectura.salir;
                } else if (f == 0) {
                    int i = JOptionPane.showConfirmDialog(null, "¿Desea ingresar más de una cadena?", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    switch (i) {
                        case JOptionPane.YES_OPTION:
                            boolean dos = true;
                            do {
                                try {
                                    ArrayList<String> cadenas = new ArrayList<>();
                                    int k = JOptionPane.showConfirmDialog(null, "Desea ingresar un archivo con las cadenas", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                                    switch (k) {
                                        case JOptionPane.YES_OPTION:
                                            JFileChooser file = new JFileChooser(new File("."));
                                            file.setDialogTitle("Seleccione el archivo que contiene la lista de cadenas");
                                            if (file.showOpenDialog(file) == JFileChooser.CANCEL_OPTION) {
                                                throw new NullPointerException();
                                            }
                                            String asd = file.getSelectedFile().getAbsolutePath();

                                            Scanner s = new Scanner(new File(asd));
                                            while (s.hasNext()) {
                                                String as = s.next();
                                                if (!(afn.ponerCadena(as).size() > 0)) {
                                                    cadenas.add(as);
                                                }
                                            }
                                            break;
                                        case JOptionPane.NO_OPTION:
                                            int de = JOptionPane.YES_OPTION;
                                            do {
                                                try {
                                                    
                                                String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                                                ArrayList<Character> error = afn.ponerCadena(cadena);
                                                if (error.size() > 0) {
                                                    String errors = "";
                                                    for (Character character : error) {
                                                        errors += character + " ";
                                                    }
                                                    JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                                } else {
                                                    cadenas.add(cadena);
                                                    de = JOptionPane.showConfirmDialog(null, "¿Desea agregar otra cadena?", "Recepcion de cadenas", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                                }
                                                } catch (NullPointerException e) {
                                                    de = JOptionPane.CANCEL_OPTION;
                                                }
                                                 
                                            } while (de == JOptionPane.YES_OPTION);
                                            break;
                                    }

                                    Set<String> hashSet = new HashSet<>(cadenas);
                                    cadenas.clear();
                                    cadenas.addAll(hashSet);
                                    String cadenasd = "";
                                    for (String cadena : cadenas) {
                                        cadenasd += cadena + "\n";
                                    }
                                    JOptionPane.showMessageDialog(null, "Las cadenas dadas son: \n" + cadenasd, "Cadenas Dadas", JOptionPane.INFORMATION_MESSAGE);
                                    JFileChooser file = new JFileChooser(new File("."));
                                    file.setDialogTitle("Elija nombre y ubicación para el archivo con la respuesta");
                                    file.setSelectedFile(new File("Respuesta.txt"));
                                    if (file.showSaveDialog(file) == JFileChooser.CANCEL_OPTION) {
                                        throw new NullPointerException();
                                    }
                                    String asd = file.getSelectedFile().getAbsolutePath() + "af";
                                    String[] listaCadenas = new String[cadenas.size()];
                                    for (int j = 0; j < cadenas.size(); j++) {
                                        listaCadenas[j] = cadenas.get(j);
                                    }
                                    boolean imprimir = JOptionPane.showConfirmDialog(null, "Desea imprimir en consola tambien?", "detalles", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
                                    if(imprimir){
                                        System.out.println("AFN: ");
                                    }
                                    afn.procesarListaCadenas(listaCadenas, asd+"n", imprimir);
                                    if(imprimir){
                                        System.out.println("AFD: ");
                                    }
                                    afd.procesarListaCadenas(listaCadenas, asd+"d", imprimir);
                                    dos = false;
                                } catch (NullPointerException e) {
                                    dos = false;
                                }
                            } while (dos);
                            break;

                        case JOptionPane.NO_OPTION:
                            boolean tres = true;
                            do {
                                try {
                                    String[] options2 = {"Procesar cadena","Volver"};
                                    int j = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Procesamiento de cadenas", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options2, "Salir");

                                    if(j==1){
                                        tres = false;
                                    }else if (j == JOptionPane.CLOSED_OPTION){
                                        return Lectura.salir;
                                    }else if(j==0){
                                        String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "Recepción de cadena");
                                        ArrayList<Character> error = afn.ponerCadena(cadena);
                                        if (error.size() > 0) {
                                            String errors = "";
                                            for (Character character : error) {
                                                errors += character + " ";
                                            }
                                            JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            boolean set1 = afn.procesarCadena(cadena);
                                            boolean set2 = afd.procesarCadena(cadena);
                                            if(set2==set1){
                                                System.out.print("En ambos casos la cadena: " + cadena + " es ");
                                                if(set1){
                                                    System.out.print("aceptada");
                                                }else{
                                                    System.out.print("rechazada");
                                                }
                                            }else{
                                                System.out.println("En cada automata, el resultado con la cadena: " + cadena + " es diferente: ");
                                                if(set1){
                                                    System.out.print("Para el AFN la cadena es aceptada");
                                                }else{
                                                    System.out.print("Para el AFN la cadena es rechazada");
                                                }
                                                if(set2){
                                                    System.out.print("Para el AFD la cadena es aceptada");
                                                }else{
                                                    System.out.print("Para el AFD la cadena es rechazada");
                                                }
                                            }
                                            tres = false;
                                        }
                                    }                                
                                } catch (NullPointerException e) {
                                    tres = false;
                                }
                            } while (tres);
                            break;

                        default:
                            break;
                    }
                }else if(f == 1){
                    ClaseValidacion validacion  = new ClaseValidacion(afn);
                    System.out.println("Validacion de AFN a AFD");
                    validacion.validarAFNtoAFD(afn.getSigma());
                }else if(f == 2){
                    System.out.println("Automata AFN: ");
                    System.out.println(afn.toString());
                    System.out.println("Automata AFD: ");
                    System.out.println(afd.toString());
                }
                pause();
                String[] options = {"Escoger otra opción", "Cambiar De Automata", "Salir"};
                int a = JOptionPane.showOptionDialog(null, "Indique la proxima acción a realizar", "¿Qué desea hacer?", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Salir");
                if (a == 2 || a == JOptionPane.CLOSED_OPTION) {
                    return Lectura.salir;
                } else if (a == 1) {
                    return Lectura.CrearAutomata;
                }
            }
        } catch (Error e) {
            System.err.print(e.getMessage());
            return Lectura.CrearAutomata;
        } catch (FileNotFoundException e) {

            return Lectura.CrearAutomata;
        }
    }
    
    private static Lectura probarAFNLambdaToAFN(){
        try {
            AFNL afnl = new AFNL(url);
            AFN afn = InteraccionesAutomas.AFN_LambdaToAFN(afnl);
            System.out.println("Los automatas han sido creados correctamente \n");
            while (true) {
                String[] options1 = {"Procesar cadena", "Validar transformacion", "Imprimir automatas" ,"Salir"};
                int f = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Bienvenido, escoja una opción", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, "Salir");
                if (f == 2 || f == JOptionPane.CLOSED_OPTION) {
                    return Lectura.salir;
                } else if (f == 0) {
                    int i = JOptionPane.showConfirmDialog(null, "¿Desea ingresar más de una cadena?", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    switch (i) {
                        case JOptionPane.YES_OPTION:
                            boolean dos = true;
                            do {
                                try {
                                    ArrayList<String> cadenas = new ArrayList<>();
                                    int k = JOptionPane.showConfirmDialog(null, "Desea ingresar un archivo con las cadenas", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                                    switch (k) {
                                        case JOptionPane.YES_OPTION:
                                            JFileChooser file = new JFileChooser(new File("."));
                                            file.setDialogTitle("Seleccione el archivo que contiene la lista de cadenas");
                                            if (file.showOpenDialog(file) == JFileChooser.CANCEL_OPTION) {
                                                throw new NullPointerException();
                                            }
                                            String asd = file.getSelectedFile().getAbsolutePath();

                                            Scanner s = new Scanner(new File(asd));
                                            while (s.hasNext()) {
                                                String as = s.next();
                                                if (!(afnl.ponerCadena(as).size() > 0)) {
                                                    cadenas.add(as);
                                                }
                                            }
                                            break;
                                        case JOptionPane.NO_OPTION:
                                            int de = JOptionPane.YES_OPTION;
                                            do {
                                                try {
                                                    
                                                String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                                                ArrayList<Character> error = afnl.ponerCadena(cadena);
                                                if (error.size() > 0) {
                                                    String errors = "";
                                                    for (Character character : error) {
                                                        errors += character + " ";
                                                    }
                                                    JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                                } else {
                                                    cadenas.add(cadena);
                                                    de = JOptionPane.showConfirmDialog(null, "¿Desea agregar otra cadena?", "Recepcion de cadenas", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                                }
                                                } catch (NullPointerException e) {
                                                    de = JOptionPane.CANCEL_OPTION;
                                                }
                                                 
                                            } while (de == JOptionPane.YES_OPTION);
                                            break;
                                    }

                                    Set<String> hashSet = new HashSet<>(cadenas);
                                    cadenas.clear();
                                    cadenas.addAll(hashSet);
                                    String cadenasd = "";
                                    for (String cadena : cadenas) {
                                        cadenasd += cadena + "\n";
                                    }
                                    JOptionPane.showMessageDialog(null, "Las cadenas dadas son: \n" + cadenasd, "Cadenas Dadas", JOptionPane.INFORMATION_MESSAGE);
                                    JFileChooser file = new JFileChooser(new File("."));
                                    file.setDialogTitle("Elija nombre y ubicación para el archivo con la respuesta");
                                    file.setSelectedFile(new File("Respuesta.txt"));
                                    if (file.showSaveDialog(file) == JFileChooser.CANCEL_OPTION) {
                                        throw new NullPointerException();
                                    }
                                    String asd = file.getSelectedFile().getAbsolutePath() + "afn";
                                    String[] listaCadenas = new String[cadenas.size()];
                                    for (int j = 0; j < cadenas.size(); j++) {
                                        listaCadenas[j] = cadenas.get(j);
                                    }
                                    boolean imprimir = JOptionPane.showConfirmDialog(null, "Desea imprimir en consola tambien?", "detalles", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
                                    if(imprimir){
                                        System.out.println("AFNL: ");
                                    }
                                    afnl.procesarListaCadenas(listaCadenas, asd+"l", imprimir);
                                    if(imprimir){
                                        System.out.println("AFN: ");
                                    }
                                    afn.procesarListaCadenas(listaCadenas, asd, imprimir);
                                    dos = false;
                                } catch (NullPointerException e) {
                                    dos = false;
                                }

                            } while (dos);
                            break;

                        case JOptionPane.NO_OPTION:
                            boolean cuatro = true;
                            do {
                                try{       
                                    String[] options2 = {"Procesar cadena", "Procesar y mostrar todas las computaciones" ,"Imprimir alguna de las computaciones" ,"Volver"};
                                    int j = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Procesamiento de cadenas", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options2, "Salir");
                                    if (j == 3){
                                        cuatro = false;
                                    } else if (j == JOptionPane.CLOSED_OPTION) {
                                        return Lectura.salir;
                                    } else if (j == 0) {
                                        boolean tres = true;
                                        do {
                                            try {
                                                String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                                                ArrayList<Character> error = afnl.ponerCadena(cadena);
                                                if (error.size() > 0) {
                                                    String errors = "";
                                                    for (Character character : error) {
                                                        errors += character + " ";
                                                    }
                                                    JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                                } else {
                                                    boolean setAfnl, setAfn;
                                                    if (JOptionPane.showConfirmDialog(null, "Desea mostrar el camino recorrido a la hora de evaluar la cadena?", "Mostrala con detalles", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                                                        setAfnl = afnl.procesarCadenaConDetalles(cadena);
                                                        setAfn = afn.procesarCadenaConDetalles(cadena);
                                                    } else {
                                                        setAfnl = afnl.procesarCadena(cadena);
                                                        setAfn = afn.procesarCadena(cadena);
                                                    }
                                                    if (setAfnl) {
                                                        System.out.println("La cadena: " + cadena + " es aceptada por el AFNL");
                                                    } else{
                                                        System.out.println("La cadena: " + cadena + " no es aceptada por el AFNL");
                                                    }
                                                    if (setAfn) {
                                                        System.out.println("La cadena: " + cadena + " es aceptada por el AFN");
                                                    } else{
                                                        System.out.println("La cadena: " + cadena + " no es aceptada por el AFN");
                                                    }
                                                    tres = false;
                                                }
                                            } catch (NullPointerException e) {
                                                tres = false;
                                            }

                                        } while (tres);
                                    }else if(j == 1){
                                        boolean tres = true;
                                        do {
                                            try {
                                                String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                                                ArrayList<Character> error = afnl.ponerCadena(cadena);
                                                if (error.size() > 0) {
                                                    String errors = "";
                                                    for (Character character : error) {
                                                        errors += character + " ";
                                                    }
                                                    JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                                } else {
                                                    JFileChooser fileComputaciones = new JFileChooser(new File("."));
                                                    fileComputaciones.setDialogTitle("Elija nombre y ubicación para el archivo con la respuesta");
                                                    fileComputaciones.setSelectedFile(new File("Respuesta.txt"));
                                                    if (fileComputaciones.showSaveDialog(fileComputaciones) == JFileChooser.CANCEL_OPTION) {
                                                        throw new NullPointerException();
                                                    }
                                                    String dirArch = fileComputaciones.getSelectedFile().getAbsolutePath()+ "afn";
                                                    int respuestaAfnl = afnl.computarTodosLosProcesamientos(cadena, dirArch + "l");
                                                    System.out.println("Para el AFNL en total, hubo "+ respuestaAfnl+ " procesamientos");
                                                    
                                                    int respuestaAfn = afnl.computarTodosLosProcesamientos(cadena, dirArch);
                                                    System.out.println("Para el AFN en total, hubo "+ respuestaAfn+ " procesamientos");
                                                    tres = false;
                                                }
                                            } catch (NullPointerException e) {
                                                tres = false;
                                            }

                                        } while (tres);
                                    }else if(j == 2){
                                        boolean tres = true;
                                        do {
                                            try {
                                                String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                                                ArrayList<Character> error = afnl.ponerCadena(cadena);
                                                if (error.size() > 0) {
                                                    String errors = "";
                                                    for (Character character : error) {
                                                        errors += character + " ";
                                                    }
                                                    JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                                } else {
                                                    String[] options3 = {"Aceptados", "Rechazados" ,"Abortados" ,"Salir"};
                                                    int n = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Procedimiento que desea ver", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options3, "Salir");
                                                    if (n == 3 || n == JOptionPane.CLOSED_OPTION) {
                                                        return Lectura.salir;
                                                    } else {
                                                        afnl.imprimirComputaciones(cadena, n);
                                                    }
                                                    tres = false;
                                                }
                                            } catch (NullPointerException e) {
                                                tres = false;
                                            }

                                        } while (tres);
                                    }
                                } catch (NullPointerException e) {
                                    cuatro = false;
                                }

                            } while (cuatro);

                            break;

                        default:
                            break;
                    }
                    //return Lectura.CrearAutomata;
                }else if(f == 1){
                    ClaseValidacion validacion  = new ClaseValidacion(afnl,afn);
                    System.out.println("Validacion de AFNL a AFN");
                    validacion.validarAFNLambdaToAFN(afnl.getSigma());
                }else if(f == 2){
                    System.out.println("Automata AFNL: ");
                    System.out.println(afnl.toString());
                    System.out.println("Automata AFN: ");
                    System.out.println(afn.toString());
                }
                pause();
                String[] options = {"Escoger otra opción", "Cambiar De Automata", "Salir"};
                int a = JOptionPane.showOptionDialog(null, "Indique la proxima acción a realizar", "¿Qué desea hacer?", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Salir");
                if (a == 2 || a == JOptionPane.CLOSED_OPTION) {
                    return Lectura.salir;
                } else if (a == 1) {
                    return Lectura.CrearAutomata;
                }
            }
        } catch (Error e) {
            System.err.print(e.getMessage());
            return Lectura.CrearAutomata;
        } catch (FileNotFoundException e) {

            return Lectura.CrearAutomata;
        }
    }
    
    private static Lectura probarAFNLambdaToAFD(){
        try {
            AFNL afnl = new AFNL(url);
            AFN afn = InteraccionesAutomas.AFN_LambdaToAFN(afnl);
            AFD afd = InteraccionesAutomas.AFN_LambdaToAFD(afnl);
            System.out.println("Los automatas han sido creados correctamente \n");
            while (true) {
                String[] options1 = {"Procesar cadena", "Validar transformacion", "Imprimir automatas" ,"Salir"};
                int f = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Bienvenido, escoja una opción", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, "Salir");
                if (f == 2 || f == JOptionPane.CLOSED_OPTION) {
                    return Lectura.salir;
                } else if (f == 0) {
                    int i = JOptionPane.showConfirmDialog(null, "¿Desea ingresar más de una cadena?", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    switch (i) {
                        case JOptionPane.YES_OPTION:
                            boolean dos = true;
                            do {
                                try {
                                    ArrayList<String> cadenas = new ArrayList<>();
                                    int k = JOptionPane.showConfirmDialog(null, "Desea ingresar un archivo con las cadenas", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                                    switch (k) {
                                        case JOptionPane.YES_OPTION:
                                            JFileChooser file = new JFileChooser(new File("."));
                                            file.setDialogTitle("Seleccione el archivo que contiene la lista de cadenas");
                                            if (file.showOpenDialog(file) == JFileChooser.CANCEL_OPTION) {
                                                throw new NullPointerException();
                                            }
                                            String asd = file.getSelectedFile().getAbsolutePath();

                                            Scanner s = new Scanner(new File(asd));
                                            while (s.hasNext()) {
                                                String as = s.next();
                                                if (!(afnl.ponerCadena(as).size() > 0)) {
                                                    cadenas.add(as);
                                                }
                                            }
                                            break;
                                        case JOptionPane.NO_OPTION:
                                            int de = JOptionPane.YES_OPTION;
                                            do {
                                                try {
                                                    
                                                String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                                                ArrayList<Character> error = afnl.ponerCadena(cadena);
                                                if (error.size() > 0) {
                                                    String errors = "";
                                                    for (Character character : error) {
                                                        errors += character + " ";
                                                    }
                                                    JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                                } else {
                                                    cadenas.add(cadena);
                                                    de = JOptionPane.showConfirmDialog(null, "¿Desea agregar otra cadena?", "Recepcion de cadenas", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                                }
                                                } catch (NullPointerException e) {
                                                    de = JOptionPane.CANCEL_OPTION;
                                                }
                                                 
                                            } while (de == JOptionPane.YES_OPTION);
                                            break;
                                    }

                                    Set<String> hashSet = new HashSet<>(cadenas);
                                    cadenas.clear();
                                    cadenas.addAll(hashSet);
                                    String cadenasd = "";
                                    for (String cadena : cadenas) {
                                        cadenasd += cadena + "\n";
                                    }
                                    JOptionPane.showMessageDialog(null, "Las cadenas dadas son: \n" + cadenasd, "Cadenas Dadas", JOptionPane.INFORMATION_MESSAGE);
                                    JFileChooser file = new JFileChooser(new File("."));
                                    file.setDialogTitle("Elija nombre y ubicación para el archivo con la respuesta");
                                    file.setSelectedFile(new File("Respuesta.txt"));
                                    if (file.showSaveDialog(file) == JFileChooser.CANCEL_OPTION) {
                                        throw new NullPointerException();
                                    }
                                    String asd = file.getSelectedFile().getAbsolutePath() + "af";
                                    String[] listaCadenas = new String[cadenas.size()];
                                    for (int j = 0; j < cadenas.size(); j++) {
                                        listaCadenas[j] = cadenas.get(j);
                                    }
                                    boolean imprimir = JOptionPane.showConfirmDialog(null, "Desea imprimir en consola tambien?", "detalles", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
                                    if(imprimir){
                                        System.out.println("AFNL: ");
                                    }
                                    afnl.procesarListaCadenas(listaCadenas, asd+"nl", imprimir);
                                    if(imprimir){
                                        System.out.println("AFN: ");
                                    }
                                    afn.procesarListaCadenas(listaCadenas, asd+"n", imprimir);
                                    if(imprimir){
                                        System.out.println("AFD: ");
                                    }
                                    afd.procesarListaCadenas(listaCadenas, asd+"d", imprimir);
                                    dos = false;
                                } catch (NullPointerException e) {
                                    dos = false;
                                }

                            } while (dos);
                            break;

                        case JOptionPane.NO_OPTION:
                            boolean cuatro = true;
                            do {
                                try{       
                                    String[] options2 = {"Procesar cadena","Volver"};
                                    int j = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Procesamiento de cadenas", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options2, "Salir");
                                    if (j == 3){
                                        cuatro = false;
                                    } else if (j == JOptionPane.CLOSED_OPTION) {
                                        return Lectura.salir;
                                    } else if (j == 0) {
                                        boolean tres = true;
                                        do {
                                            try {
                                                String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                                                ArrayList<Character> error = afnl.ponerCadena(cadena);
                                                if (error.size() > 0) {
                                                    String errors = "";
                                                    for (Character character : error) {
                                                        errors += character + " ";
                                                    }
                                                    JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                                } else {
                                                    boolean setAfnl, setAfn, setAfd;
                                                    if (JOptionPane.showConfirmDialog(null, "Desea mostrar el camino recorrido a la hora de evaluar la cadena?", "Mostrala con detalles", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                                                        setAfnl = afnl.procesarCadenaConDetalles(cadena);
                                                        setAfn = afn.procesarCadenaConDetalles(cadena);
                                                        setAfd = afd.procesarCadenaConDetalles(cadena);
                                                    } else {
                                                        setAfnl = afnl.procesarCadena(cadena);
                                                        setAfn = afn.procesarCadena(cadena);
                                                        setAfd = afd.procesarCadena(cadena);
                                                    }
                                                    if (setAfnl) {
                                                        System.out.println("La cadena: " + cadena + " es aceptada por el AFNL");
                                                    } else{
                                                        System.out.println("La cadena: " + cadena + " no es aceptada por el AFNL");
                                                    }
                                                    if (setAfn) {
                                                        System.out.println("La cadena: " + cadena + " es aceptada por el AFN");
                                                    } else{
                                                        System.out.println("La cadena: " + cadena + " no es aceptada por el AFN");
                                                    }
                                                    if (setAfd) {
                                                        System.out.println("La cadena: " + cadena + " es aceptada por el AFD");
                                                    } else{
                                                        System.out.println("La cadena: " + cadena + " no es aceptada por el AFD");
                                                    }
                                                    tres = false;
                                                }
                                            } catch (NullPointerException e) {
                                                tres = false;
                                            }

                                        } while (tres);
                                    }
                                } catch (NullPointerException e) {
                                    cuatro = false;
                                }

                            } while (cuatro);

                            break;

                        default:
                            break;
                    }
                    //return Lectura.CrearAutomata;
                }else if(f == 1){
                    ClaseValidacion validacion  = new ClaseValidacion(afnl,afn);
                    System.out.println("Validacion de AFNL a AFN");
                    validacion.validarAFNLambdaToAFN(afnl.getSigma());
                    System.out.println("Validacion de AFN a AFD");
                    validacion.validarAFNtoAFD(afn.getSigma());
                }else if(f == 2){
                    System.out.println("Automata AFNL: ");
                    System.out.println(afnl.toString());
                    System.out.println("Automata AFN: ");
                    System.out.println(afn.toString());
                    System.out.println("Automata AFD: ");
                    System.out.println(afd.toString());
                }
                pause();
                String[] options = {"Escoger otra opción", "Cambiar De Automata", "Salir"};
                int a = JOptionPane.showOptionDialog(null, "Indique la proxima acción a realizar", "¿Qué desea hacer?", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Salir");
                if (a == 2 || a == JOptionPane.CLOSED_OPTION) {
                    return Lectura.salir;
                } else if (a == 1) {
                    return Lectura.CrearAutomata;
                }
            }
        } catch (Error e) {
            System.err.print(e.getMessage());
            return Lectura.CrearAutomata;
        } catch (FileNotFoundException e) {

            return Lectura.CrearAutomata;
        }
    }
    
    private static Lectura probarSimplificacion() {
        try {
            AFD afd = new AFD(url);
            AFD simpl = new AFDSimplificacion(afd);
            while (true) {

                String[] options1 = {"Mostrar comparacion de automata\n y su simplificacion", "mostrar visual del automata simplificado", "simular cadenas de forma visual", "procesar Cadenas", "Salir"};
                int f = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Bienvenido, escoja una opción", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, "Salir");
                switch (f) {
                    case 0://tostring
                        System.out.println("AFD original:");
                        System.out.println(afd.toString());
                        System.out.println("AFD simplificado");
                        String d = simpl.toString();
                        System.out.println(d);
                        pause();
                        switch (JOptionPane.showConfirmDialog(null, "Desea guardar el nuevo automata en el portapapeles?", "guardado de simplificacion", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                            case JOptionPane.YES_OPTION:
                                StringSelection stringSelection = new StringSelection(d);
                                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                                clipboard.setContents(stringSelection, null);
                                System.out.println("\nautomata simplificado copiado al portapapeles");
                                break;
                        }
                        break;
                    case 1://visuallibre
                        int slashIndex = url.lastIndexOf('\\');
                        int dotIndex = url.lastIndexOf('.');
                        String expresion;
                        if (dotIndex == -1) {
                            expresion = url.substring(slashIndex + 1);
                        } else {
                            expresion = url.substring(slashIndex + 1, dotIndex);
                        }
                        expresion = expresion.replace('\'', '*');
                        Windows1 visual = new Windows1(expresion, simpl);
                        visual.Simulat();
                        break;
                    case 2://visual forzado
                        int s = url.lastIndexOf('\\');
                        int df = url.lastIndexOf('.');
                        String exe;
                        if (df == -1) {
                            exe = url.substring(s + 1);
                        } else {
                            exe = url.substring(s + 1, df);
                        }
                        exe = exe.replace('\'', '*');
                        Windows3 visuals = new Windows3(exe, simpl);
                        visuals.Simulat();
                        break;
                    case 3://provesar
                        int i = JOptionPane.showConfirmDialog(null, "Desea ingresar más de una cadena?", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                        switch (i) {
                            case JOptionPane.YES_OPTION:
                                boolean dos = true;
                                do {
                                    try {
                                        procsVariasCadenas(simpl);
                                        dos = false;
                                    } catch (NullPointerException e) {
                                        dos = false;
                                    }

                                } while (dos);
                                break;

                            case JOptionPane.NO_OPTION:
                                boolean tres = true;
                                do {
                                    try {
                                        String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                                        ArrayList<Character> error = afd.ponerCadena(cadena);
                                        if (error.size() > 0) {
                                            String errors = "";
                                            for (Character character : error) {
                                                errors += character + " ";
                                            }
                                            JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            boolean set;
                                            if (JOptionPane.showConfirmDialog(null, "Desea mostrar el camino recorrido a la hora de evaluar la cadena?", "Seleccione una opción", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                                                set = afd.procesarCadenaConDetalles(cadena);
                                            } else {
                                                set = afd.procesarCadena(cadena);
                                            }
                                            if (set) {
                                                System.out.println("La cadena: " + cadena + " es aceptada");
                                            } else {
                                                System.out.println("La cadena: " + cadena + " no es aceptada");
                                            }
                                            tres = false;
                                        }
                                    } catch (NullPointerException e) {
                                        tres = false;
                                    }

                                } while (tres);

                                break;

                            default:
                                //una
                                break;
                        }
                        pause();
                        break;
                    case 4:
                        return Lectura.salir;
                    default:
                        return Lectura.CrearAutomata;
                }

                String[] options = {"mantener Automata", "Cambiar De Automata", "Salir"};
                int fs = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Seleccione", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Salir");
                if (fs == 2 || fs == JOptionPane.CLOSED_OPTION) {
                    return Lectura.salir;
                } else if (fs == 1) {
                    return Lectura.CrearAutomata;
                }
            }
        } catch (Error e) {
            System.err.print(e.getMessage());
            return Lectura.CrearAutomata;
        } catch (FileNotFoundException e) {

            return Lectura.CrearAutomata;
        }
    }
    
    private static void pause(){
        System.out.println("presione enter para continuar");
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(ClasePrueba.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static ArrayList<String> leerArchivo(String fid,AFD afd) throws FileNotFoundException{
        ArrayList<String> cadenas=new ArrayList<>();
        Scanner sd = new Scanner(new File(fid));
        while (sd.hasNext()) {
            String as = sd.next();
            if (!(afd.ponerCadena(as).size() > 0)) {
                cadenas.add(as);
            }
        }
        return cadenas;
    }
    
    private static void procsVariasCadenas(AFD simpl) throws FileNotFoundException {
        ArrayList<String> cadenas = new ArrayList<>();
        int k = JOptionPane.showConfirmDialog(null, "Desea ingresar un archivo con las cadenas", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (k) {
            case JOptionPane.YES_OPTION:
                JFileChooser file = new JFileChooser(new File("."));
                file.setDialogTitle("Seleccione el archivo que contiene la lista de cadenas");
                if (file.showOpenDialog(file) == JFileChooser.CANCEL_OPTION) {
                    throw new NullPointerException();
                }
                String asd = file.getSelectedFile().getAbsolutePath();
                cadenas.addAll(leerArchivo(asd, simpl));
                break;
            case JOptionPane.NO_OPTION:
                int de = JOptionPane.YES_OPTION;
                do {
                    try {
                        String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                        ArrayList<Character> error = simpl.ponerCadena(cadena);
                        if (error.size() > 0) {
                            String errors = "";
                            for (Character character : error) {
                                errors += character + " ";
                            }
                            JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                        } else {
                            cadenas.add(cadena);
                            de = JOptionPane.showConfirmDialog(null, "¿Desea agregar otra cadena?", "Recepcion de cadenas", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        }
                    } catch (NullPointerException e) {
                        de = JOptionPane.CANCEL_OPTION;
                    }
                } while (de == JOptionPane.YES_OPTION);
                break;
        }

        Set<String> hashSet = new HashSet<>(cadenas);
        cadenas.clear();
        cadenas.addAll(hashSet);
        String cadenasd = "";
        for (String cadena : cadenas) {
            cadenasd += cadena + "\n";
        }
        JOptionPane.showMessageDialog(null, "Las cadenas dadas son: \n" + cadenasd, "Cadenas Dadas", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser file = new JFileChooser(new File("."));
        file.setDialogTitle("Elija nombre y ubicación para el archivo con la respuesta");
        file.setSelectedFile(new File("Respuesta.txt"));
        if (file.showSaveDialog(file) == JFileChooser.CANCEL_OPTION) {
            throw new NullPointerException();
        }
        String asd = file.getSelectedFile().getAbsolutePath();
        String[] listaCadenas = new String[cadenas.size()];
        for (int j = 0; j < cadenas.size(); j++) {
            listaCadenas[j] = cadenas.get(j);
        }
        simpl.procesarListaCadenas(listaCadenas, asd, JOptionPane.showConfirmDialog(null, "Desea imprimir en consola tambien?", "Seleccione una opción", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
    }
    
     private static Lectura probarComplemento() {
        try {
            AFD afd = new AFD(url);
            AFD compl = new AFDComplemento(afd);
            while (true) {

                String[] options1 = {"Mostrar comparacion de automata\n y su complemento", "mostrar visual del AFD Complemento", "simular cadenas de forma visual", "procesar Cadenas", "Salir"};
                int f = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Bienvenido, escoja una opción", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, "Salir");
                switch (f) {
                    case 0://tostring
                        System.out.println("AFD original:");
                        System.out.println(afd.toString());
                        System.out.println("AFD complemento");
                        String d = compl.toString();
                        System.out.println(d);
                        pause();
                        switch (JOptionPane.showConfirmDialog(null, "Desea guardar el nuevo automata en el portapapeles?", "guardado de AFD complemento", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                            case JOptionPane.YES_OPTION:
                                StringSelection stringSelection = new StringSelection(d);
                                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                                clipboard.setContents(stringSelection, null);
                                System.out.println("\n AFD complemento copiado al portapapeles");
                                break;
                        }
                        break;
                    case 1://visuallibre
                        int slashIndex = url.lastIndexOf('\\');
                        int dotIndex = url.lastIndexOf('.');
                        String expresion;
                        if (dotIndex == -1) {
                            expresion = url.substring(slashIndex + 1);
                        } else {
                            expresion = url.substring(slashIndex + 1, dotIndex);
                        }
                        expresion = expresion.replace('\'', '*');
                        Windows1 visual = new Windows1(expresion, compl);
                        visual.Simulat();
                        break;
                    case 2://visual forzado
                        int s = url.lastIndexOf('\\');
                        int df = url.lastIndexOf('.');
                        String exe;
                        if (df == -1) {
                            exe = url.substring(s + 1);
                        } else {
                            exe = url.substring(s + 1, df);
                        }
                        exe = exe.replace('\'', '*');
                        Windows3 visuals = new Windows3(exe, compl);
                        visuals.Simulat();
                        break;
                    case 3://provesar
                        int i = JOptionPane.showConfirmDialog(null, "Desea ingresar más de una cadena?", "Recepcion de cadenas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                        switch (i) {
                            case JOptionPane.YES_OPTION:
                                boolean dos = true;
                                do {
                                    try {
                                        procsVariasCadenas(compl);
                                        dos = false;
                                    } catch (NullPointerException e) {
                                        dos = false;
                                    }

                                } while (dos);
                                break;

                            case JOptionPane.NO_OPTION:
                                boolean tres = true;
                                do {
                                    try {
                                        String cadena = JOptionPane.showInputDialog(null, "Ingrese la cadena a evaluar", "");
                                        ArrayList<Character> error = compl.ponerCadena(cadena);
                                        if (error.size() > 0) {
                                            String errors = "";
                                            for (Character character : error) {
                                                errors += character + " ";
                                            }
                                            JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            boolean set;
                                            boolean setO;
                                            if (JOptionPane.showConfirmDialog(null, "Desea mostrar el camino recorrido a la hora de evaluar la cadena?", "Seleccione una opción", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                                                System.out.println("Procesamiento AFD Complemento:");
                                                set = compl.procesarCadenaConDetalles(cadena);
                                                System.out.println("Procesamiento AFD Original:");
                                                setO = afd.procesarCadenaConDetalles(cadena);
                                            } else {
                                                set = compl.procesarCadena(cadena);
                                                setO = afd.procesarCadena(cadena);
                                            }
                                            if (set) {
                                                System.out.println("La cadena: " + cadena + " es aceptada por el AFD Complemento");
                                            } else {
                                                System.out.println("La cadena: " + cadena + " no es aceptada por el AFD Complemento");
                                            }
                                            
                                            if (setO) {
                                                System.out.println("La cadena: " + cadena + " es aceptada por el AFD original");
                                            } else {
                                                System.out.println("La cadena: " + cadena + " no es aceptada por el AFD original");
                                            }
                                            tres = false;
                                        }
                                    } catch (NullPointerException e) {
                                        tres = false;
                                    }

                                } while (tres);

                                break;

                            default:
                                //una
                                break;
                        }
                        pause();
                        break;
                    case 4:
                        return Lectura.salir;
                    default:
                        return Lectura.CrearAutomata;
                }

                String[] options = {"mantener Automata", "Cambiar De Automata", "Salir"};
                int fs = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "Seleccione", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Salir");
                if (fs == 2 || fs == JOptionPane.CLOSED_OPTION) {
                    return Lectura.salir;
                } else if (fs == 1) {
                    return Lectura.CrearAutomata;
                }
            }
        } catch (Error e) {
            System.err.print(e.getMessage());
            return Lectura.CrearAutomata;
        } catch (FileNotFoundException e) {

            return Lectura.CrearAutomata;
        }
    }   
        
}
