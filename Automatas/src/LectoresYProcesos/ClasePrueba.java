/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresYProcesos;

import AutomatasFinitos.AFD;
import AutomatasFinitos.AFN;
import AutomatasFinitos.AFNL;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author fanat
 */
public class ClasePrueba {

    static boolean salir;
    static CreadorAutomata.Type tp;
    static String url;

    public enum Lectura {
        CrearAutomata, LeerCadena, salir
    }

    public static void main(String[] args) {
        Lectura lec = Lectura.CrearAutomata;
        Scanner scan = new Scanner(System.in);
        System.out.println("la palabra \"$$EXIT$$\" esta reservada para salir de la aplicacion automaticamente");
        JFileChooser fileChooser = new JFileChooser(new File("."));
        fileChooser.setDialogTitle("Seleccione el automata que desea importar");
        String scad = "";
        while (!Lectura.salir.equals(lec)) {
            if (scad.equals("$$EXIT$$")) {
                lec = Lectura.salir;
            }
            switch (lec) {
                case CrearAutomata:
                    System.out.println("Seleccione el automata que desea importar");
                    try {
                        if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.CANCEL_OPTION) {
                            throw new NullPointerException();
                        }
                        url = fileChooser.getSelectedFile().getAbsolutePath();
                        tp = CreadorAutomata.CheckType(url);

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
                                break;
                            case AFN:
                                message = "Ha seleccionado un Automata finito no determinista que representa la expresion " + expresion;
                                break;
                            case AFNL:
                                message = "Ha seleccionado un Automata finito no determinista con transiciones lambda que representa la expresion " + expresion;
                                break;
                        }
                        System.out.println(message);
                        lec = Lectura.LeerCadena;
                    } catch (NullPointerException e) {
                        if (JOptionPane.showConfirmDialog(null, "No ha ingresado ningún automata. ¿Desea salir? Y/N", "No automata", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                            lec = Lectura.salir;
                        }
                    }
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
                    }
                    break;
                case salir:
                    break;
                default:
                    scad = scan.next();
                    break;

            }
        }
    }

    private static Lectura probarAFD() {
        try {
            AFD afd = new AFD(url);
            System.out.println("El automata ha sido creado correctamente");
            while (true) {
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
                                            if (!(afd.ponerCadena(as).size() > 0)) {
                                                cadenas.add(as);
                                            }
                                        }
                                        break;
                                    case JOptionPane.NO_OPTION:
                                        int de = JOptionPane.YES_OPTION;
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
                                file.setDialogTitle("Seleccione la ubicación del archivo que contendrá la respuesta");
                                if (file.showOpenDialog(file) == JFileChooser.CANCEL_OPTION) {
                                    throw new NullPointerException();
                                }
                                String asd = file.getSelectedFile().getAbsolutePath();
                                String[] listaCadenas = new String[cadenas.size()];
                                for (int j = 0; j < cadenas.size(); j++) {
                                    listaCadenas[j] = cadenas.get(j);
                                }
                                afd.procesarListaCadenas(listaCadenas, asd, JOptionPane.showConfirmDialog(null, "Desea imprimir en consola tambien?", "detalles", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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
                                    if (JOptionPane.showConfirmDialog(null, "Desea mostrar el camino recorrido a la hora de evaluar la cadena?", "detalles", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
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
                String[] options = {"Evaluar otra cadena", "Cambiar De Automata", "Salir"};
                int f = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "titulo", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Salir");
                if (f == 2 || f == JOptionPane.CLOSED_OPTION) {
                    return Lectura.salir;
                } else if (f == 1) {
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
                                file.setDialogTitle("Seleccione la ubicación del archivo que contendrá la respuesta");
                                if (file.showOpenDialog(file) == JFileChooser.CANCEL_OPTION) {
                                    throw new NullPointerException();
                                }
                                String asd = file.getSelectedFile().getAbsolutePath();
                                String[] listaCadenas = new String[cadenas.size()];
                                for (int j = 0; j < cadenas.size(); j++) {
                                    listaCadenas[j] = cadenas.get(j);
                                }
                                afn.procesarListaCadenas(listaCadenas, asd, JOptionPane.showConfirmDialog(null, "Desea imprimir en consola tambien?", "detalles", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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
                                ArrayList<Character> error = afn.ponerCadena(cadena);
                                if (error.size() > 0) {
                                    String errors = "";
                                    for (Character character : error) {
                                        errors += character + " ";
                                    }
                                    JOptionPane.showMessageDialog(null, "La cadena posee caracteres que no pertenecen al alfabeto: \n" + errors, "Error en Cadena", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    boolean set;
                                    if (JOptionPane.showConfirmDialog(null, "Desea mostrar el camino recorrido a la hora de evaluar la cadena?", "detalles", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                                        if (JOptionPane.showConfirmDialog(null, "Desea mostrar el camino de todos los procesamientos de la cadena?", "detalles", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                                            JFileChooser file = new JFileChooser(new File("."));
                                            file.setDialogTitle("Seleccione la ubicación del archivo que contendrá la respuesta");
                                            if (file.showOpenDialog(file) == JFileChooser.CANCEL_OPTION) {
                                                throw new NullPointerException();
                                            }
                                            String asd = file.getSelectedFile().getAbsolutePath();
                                            afn.computarTodosLosProcesamientos(cadena, asd);
                                            tres = false;
                                            break;
                                        } else {
                                            set = afn.procesarCadenaConDetalles(cadena);
                                        }
                                    } else {
                                        set = afn.procesarCadena(cadena);
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
            } catch (Error e) {
                System.err.print(e.getMessage());
                return Lectura.CrearAutomata;
            } catch (FileNotFoundException e) {

                return Lectura.CrearAutomata;
            }
            String[] options = {"Evaluar otra cadena", "Cambiar De Automata", "Salir"};
            int f = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Salir");
            if (f == 2 || f == JOptionPane.CLOSED_OPTION) {
                return Lectura.salir;
            } else if (f == 1) {
                return Lectura.CrearAutomata;
            }
        }
        return Lectura.CrearAutomata;
    }

    private static Lectura probarAFNLambda() {
        try {
            AFNL afnl = new AFNL(url);
            System.out.println("El automata ha sido creado correctamente");
            while (true) {
                String[] options1 = {"Procesar cadena", "Lambda clausura", "Salir"};
                int f = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "titulo", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, "Salir");
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
                                    file.setDialogTitle("Seleccione la ubicación del archivo que contendrá la respuesta");
                                    if (file.showOpenDialog(file) == JFileChooser.CANCEL_OPTION) {
                                        throw new NullPointerException();
                                    }
                                    String asd = file.getSelectedFile().getAbsolutePath();
                                    String[] listaCadenas = new String[cadenas.size()];
                                    for (int j = 0; j < cadenas.size(); j++) {
                                        listaCadenas[j] = cadenas.get(j);
                                    }
                                    //afd.procesarListaCadenas(listaCadenas, asd, JOptionPane.showConfirmDialog(null, "Desea imprimir en consola tambien?", "detalles", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
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
                                    String[] options2 = {"Procesar cadena", "Procesar y mostrar todas las computaciones" ,"Imprimir alguna de las computaciones" ,"Salir"};
                                    int j = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "titulo", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options2, "Salir");
                                    if (j == 4 || j == JOptionPane.CLOSED_OPTION) {
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
                                                        set = afnl.ProcesarCadena(cadena);
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
                                                    fileComputaciones.setDialogTitle("Seleccione la ubicación del archivo que contendrá la respuesta");
                                                    if (fileComputaciones.showOpenDialog(fileComputaciones) == JFileChooser.CANCEL_OPTION) {
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
                                                    int n = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "titulo", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options3, "Salir");
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
                    return Lectura.CrearAutomata;
                }else if(f == 1){
                    String[] options4 = {"Lambda clausura un estado", "Lambda clausura varios estados", "Salir"};
                    int e = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "titulo", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options4, "Salir");
                    if (e == 2 || e == JOptionPane.CLOSED_OPTION) {
                        return Lectura.salir;
                    } else if (e == 0) {
                        String[] estados = afnl.GetQ();
                        int estado = JOptionPane.showOptionDialog(null, "Escoja un estado", "Lambda clausura un estado", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, estados, estados[0]);
                        System.out.println("La lambda clausura del estado " + estado + " es:");
                        afnl.ImprimirlambdaClausura_unEstado(estado);
                    } else if (e == 1){
                        ArrayList<Integer> escogidos = new ArrayList<>();
                        String[] estados = afnl.GetQ();
                        boolean tres = true;
                            do {
                                try {
                                    int estado = JOptionPane.showOptionDialog(null, "Escoja un estado", "Lambda clausura un estado", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, estados, estados[0]);
                                    escogidos.add(estado);
                                    
                                    if (JOptionPane.showConfirmDialog(null, "Listo", "Escoger otro estado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
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

                String[] options = {"Evaluar otra cadena", "Cambiar De Automata", "Salir"};
                int a = JOptionPane.showOptionDialog(null, "Indique la proxima accion a realizar", "titulo", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Salir");
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

    /*
    Debe hacer una clase ClasePrueba para ejecutar todos los métodos realizados,
    creando autómatas, procesando cadenas con ellos,
    creando los objetos respectivos de acuerdo a dichos procesamientos,
    procesando listas de cadenas, generando los archivos.
    Dichas pruebas deben ser trabajadas en los siguientes métodos, 
    los cuales deben ser llamadas desde el procesamiento principal:
    probarAFD(): Crear autómatas AFD, procesar cadenas con y sin detalles,
    procesar listas de cadenas, generar archivos.
    probarAFN(): Crear autómatas AFN, procesar cadenas mostrando solo un procesamiento de aceptación,  procesar cadenas mostrando todos los procesamientos posibles, consultar los procesamientos de aceptación, abortados y de rechazo,  procesar listas de cadenas, generar archivos.
    probarAFNLambda(): Crear autómatas AFN-λ, calcular la λ- clausura de un estado,  calcular la λ- clausura de un conjunto de estados, procesar cadenas mostrando solo un procesamiento de aceptación,  procesar cadenas mostrando todos los procesamientos posibles, consultar los procesamientos de aceptación, abortados y de rechazo, procesar listas de cadenas, generar archivos.
    main(): invoca a los otros para que puedan ser comentados fácilmente y poder escoger cuál se va a probar.
    
     */
}
