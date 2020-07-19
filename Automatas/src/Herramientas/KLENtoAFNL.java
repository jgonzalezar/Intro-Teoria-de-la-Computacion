/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import AutomatasFinitos.AFN;
import AutomatasFinitos.AFNL;
import AutomatasFinitos.Alfabeto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author fanat
 */
public class KLENtoAFNL extends AFNL {

    public KLENtoAFNL(String lenguaje) {
        HashMap<String, ArrayList<String>> result = evalu(lenguaje);
        ArrayList<String> asd = result.get("Sigma");

        Set<String> reset = new HashSet<>(asd);
        asd.clear();
        asd.addAll(reset);
        char[] sigmas = new char[asd.size()];
        for (int i = 0; i < asd.size(); i++) {
            sigmas[i] = asd.get(i).charAt(0);
        }

        Sigma = new Alfabeto(sigmas);
        System.out.println(Sigma.toString());
        asd = result.get("States");
        reset = new HashSet<>(asd);
        asd.clear();
        asd.addAll(reset);

        Q = new ArrayList<>();
        Q.addAll(asd);
        System.out.println("estates");
        System.out.println(Q.toString());

        asd = result.get("final");
        reset = new HashSet<>(asd);
        asd.clear();
        asd.addAll(reset);

        q0 = Q.indexOf(result.get("ini").get(0));

        System.out.println("uno");
        System.out.println(q0);
        ArrayList<Integer> asds = new ArrayList<>();
        for (int i = 0; i < asd.size(); i++) {
            asds.add(Q.indexOf(asd.get(i)));
        }
        F = asds;
        System.out.println("acep");
        System.out.println(F.toString());
        TransitionAFNL tras = new TransitionAFNL(Q.size());
        asd = result.get("Separations");
        reset = new HashSet<>(asd);
        asd.clear();
        asd.addAll(reset);
        System.out.println("Separat");
        for (String asd1 : asd) {
            String[] separos = asd1.split(":");
            String estado1 = separos[0];
            String[] sep = separos[1].split(">");
            char caracter = sep[0].charAt(0);
            String[] dos = sep[1].split(";");
            int[] hey = new int[dos.length];
            for (int i = 0; i < dos.length; i++) {
                hey[i] = Q.indexOf(dos[i]);
            }
            System.out.println(caracter + " " + estado1 + " " + Arrays.toString(hey) + "/" + Arrays.toString(dos));
            tras.add(caracter, Q.indexOf(estado1), hey);
        }

        Delta = tras;

    }

    private HashMap<String, ArrayList<String>> evalu(String word) {
        HashMap<String, ArrayList<String>> cad = new HashMap<>();
        cad.put("Sigma", new ArrayList<>());
        cad.put("final", new ArrayList<>());
        cad.put("States", new ArrayList<>());
        cad.put("Separations", new ArrayList<>());
        cad.put("ini", new ArrayList<>());
        cad.put("conec", new ArrayList<>());
        int pare = word.indexOf("(");
        int uni = word.indexOf("U");
        int cer = word.indexOf(")");
        int cont = 0;
        if ("".equals(word)) {
            return cad;
        }
        if (uni == 0) {
            throw new Error("La union no se puede realizar con solo un elemento");
        } else if (pare == 0) {
            int asd = cer;
            for (int i = pare + 1; i < word.length(); i++) {
                if (word.charAt(i) == '(') {
                    cont++;
                }
                if (word.charAt(i) == ')') {
                    if (cont == 0) {
                        asd = i;
                    } else {
                        cont--;
                    }
                }
            }
            char ex;
            if (asd + 1 == word.length()) {
                ex = ' ';
            } else {
                ex = word.charAt(asd + 1);
            }
            char next2 = ' ';
            if (asd + 2 < word.length()) {
                next2 = word.charAt(asd + 2);
            }
            int dos = asd + 1;
            if (ex == '*' || ex == '+') {
                dos++;
                if (next2 == 'U') {
                    dos++;
                }
            } else if (ex == 'U') {
                dos++;
            }
            String uno = word.substring(1, asd);
            String dod;
            HashMap<String, ArrayList<String>> cad1 = evalu(uno);
            cad.get("Sigma").addAll(cad1.get("Sigma"));
            Set<String> reset = new HashSet<>(cad.get("Sigma"));
            cad.get("Sigma").clear();
            cad.get("Sigma").addAll(reset);
            cad1.get("States").forEach((string) -> {
                cad.get("States").add("P" + string);
            });
            reset = new HashSet<>(cad.get("States"));
            cad.get("States").clear();
            cad.get("States").addAll(reset);
            cad.get("States").add("0P");

            cad.get("ini").add("0P");

            cad1.get("final").forEach((string) -> {
                cad.get("final").add("P" + string);
            });
            reset = new HashSet<>(cad.get("final"));
            cad.get("final").clear();
            cad.get("final").addAll(reset);

            cad.get("Separations").add("0P:$>" + "P" + cad1.get("ini").get(0));
            for (String asd1 : cad1.get("Separations")) {
                String[] separos = asd1.split(":");
                String estado1 = separos[0];
                String[] sep = separos[1].split(">");
                char caracter = sep[0].charAt(0);
                String[] dosa = sep[1].split(";");
                String ddd = "";
                for (String aDo : dosa) {
                    ddd += "P" + aDo + ";";
                }
                cad.get("Separations").add("P" + estado1 + ":" + caracter + ">" + ddd.substring(0, ddd.length() - 1));
            }

            switch (ex) {
                case '*':
                    cad.get("final").add("0P");

                case '+':
                    cad1.get("final").forEach((string) -> {
                        cad.get("Separations").add("P" + string + ":$>0P");
                    });
                    break;
            }

            if (dos == word.length()) {
                dod = "";
                if (ex == 'U' || ((ex == '*' || ex == '+') && next2 == 'U')) {
                    throw new Error("La union no se puede realizar con solo un elemento");
                } else {
                    return cad;
                }

            } else {
                dod = word.substring(dos);
            }
            HashMap<String, ArrayList<String>> cad2 = evalu(dod);

            switch (ex) {

                case '*':
                case '+':
                    if (next2 != 'U') {
                        cad.get("Sigma").addAll(cad2.get("Sigma"));
                        Set<String> resetss = new HashSet<>(cad.get("Sigma"));
                        cad.get("Sigma").clear();
                        cad.get("Sigma").addAll(resetss);
                        cad2.get("States").forEach((string) -> {
                            cad.get("States").add("2" + string);
                        });
                        reset = new HashSet<>(cad.get("States"));
                        cad.get("States").clear();
                        cad.get("States").addAll(reset);
                        cad.get("States").add("C0");

                        cad.get("Separations").add("C0:$>0P");

                        cad.get("ini").clear();
                        cad.get("ini").add("C0");

                        cad1.get("final").forEach((string) -> {
                            cad.get("Separations").remove("P" + string + ":$>0P");

                        });
                        cad.get("Separations").remove("0P:$>" + "P" + cad1.get("ini").get(0));
                        cad.get("final").forEach((string) -> {
                            if (!"0P".equals(string)) {
                                cad.get("Separations").add(string + ":$>0P;2" + cad2.get("ini").get(0));
                            } else {
                                cad.get("Separations").add(string + ":$>2" + cad2.get("ini").get(0) + ";P" + cad1.get("ini").get(0));
                            }

                        });
                        cad.get("final").clear();
                        cad2.get("final").forEach((string) -> {
                            cad.get("final").add("2" + string);
                        });
                        resetss = new HashSet<>(cad.get("final"));
                        cad.get("final").clear();
                        cad.get("final").addAll(resetss);
                        for (String asd1 : cad2.get("Separations")) {
                            String[] separos = asd1.split(":");
                            String estado1 = separos[0];
                            String[] sep = separos[1].split(">");
                            char caracter = sep[0].charAt(0);
                            String[] dosdd = sep[1].split(";");
                            String ddd = "";
                            for (String aDo : dosdd) {
                                ddd += "2" + aDo + ";";
                            }
                            cad.get("Separations").add("2" + estado1 + ":" + caracter + ">" + ddd.substring(0, ddd.length() - 1));
                        }
                        break;
                    }

                case 'U':
                    cad.get("Sigma").addAll(cad2.get("Sigma"));
                    Set<String> reset2 = new HashSet<>(cad.get("Sigma"));
                    cad.get("Sigma").clear();
                    cad.get("Sigma").addAll(reset2);
                    cad2.get("States").forEach((string) -> {
                        cad.get("States").add("U" + string);
                    });
                    reset2 = new HashSet<>(cad.get("States"));
                    cad.get("States").clear();
                    cad.get("States").addAll(reset2);

                    cad2.get("final").forEach((string) -> {
                        cad.get("final").add("U" + string);
                    });
                    reset2 = new HashSet<>(cad.get("final"));
                    cad.get("final").clear();
                    cad.get("final").addAll(reset2);

                    String exst = "0P:$>" + "P" + cad1.get("ini").get(0) + ";U" + cad2.get("ini").get(0);
                    System.out.println(exst + "exc");
                    cad.get("Separations").add(exst);
                    for (String asd1 : cad2.get("Separations")) {
                        String[] separos = asd1.split(":");
                        String estado1 = separos[0];
                        String[] sep = separos[1].split(">");
                        char caracter = sep[0].charAt(0);
                        String[] doso = sep[1].split(";");
                        String ddd = "";
                        for (String aDo : doso) {
                            ddd += "U" + aDo + ";";
                        }
                        cad.get("Separations").add("U" + estado1 + ":" + caracter + ">" + ddd.substring(0, ddd.length() - 1));
                    }
                    break;
                default:
                    cad.get("Sigma").addAll(cad2.get("Sigma"));
                    Set<String> resetss = new HashSet<>(cad.get("Sigma"));
                    cad.get("Sigma").clear();
                    cad.get("Sigma").addAll(resetss);
                    cad2.get("States").forEach((string) -> {
                        cad.get("States").add("2" + string);
                    });
                    reset = new HashSet<>(cad.get("States"));
                    cad.get("States").clear();
                    cad.get("States").addAll(reset);
                    cad.get("States").add("C0");

                    cad.get("Separations").add("C0:$>0P");

                    cad.get("ini").clear();
                    cad.get("ini").add("C0");

                    cad.get("final").forEach((string) -> {
                        cad.get("Separations").add(string + ":$>2" + cad2.get("ini").get(0));
                    });
                    cad.get("final").clear();
                    cad2.get("final").forEach((string) -> {
                        cad.get("final").add("2" + string);
                    });
                    resetss = new HashSet<>(cad.get("final"));
                    cad.get("final").clear();
                    cad.get("final").addAll(resetss);
                    for (String asd1 : cad2.get("Separations")) {
                        String[] separos = asd1.split(":");
                        String estado1 = separos[0];
                        String[] sep = separos[1].split(">");
                        char caracter = sep[0].charAt(0);
                        String[] dosdd = sep[1].split(";");
                        String ddd = "";
                        for (String aDo : dosdd) {
                            ddd += "2" + aDo + ";";
                        }
                        cad.get("Separations").add("2" + estado1 + ":" + caracter + ">" + ddd.substring(0, ddd.length() - 1));
                    }
                    break;
            }

        } else if (uni == -1 || pare == -1) {
            if (uni == -1 && pare == -1) {
                for (int i = 0; i < word.length(); i++) {
                    char d = word.charAt(i);
                    char e;
                    if (i + 1 == word.length()) {
                        e = ' ';
                    } else {
                        e = word.charAt(i + 1);
                    }
                    cad.get("Sigma").add(d + "");
                    switch (e) {
                        case '*':
                            String name = i + "" + d + "" + "" + e + "";
                            cad.get("States").add(name);
                            cad.get("Separations").add(name + ":" + d + ">" + name);
                            cad.get("final").add(name);
                            cad.get("conec").add(name + ":" + name);
                            if (i == 0) {
                                cad.get("ini").add(name);
                            }
                            i++;
                            break;
                        case '+':
                            String name1 = i + "" + d + "" + "" + e + "1";
                            String name2 = i + "" + d + "" + "" + e + "2";
                            cad.get("States").add(name1);
                            cad.get("States").add(name2);
                            cad.get("Separations").add(name1 + ":" + d + ">" + name2);
                            cad.get("Separations").add(name2 + ":" + d + ">" + name2);
                            cad.get("final").add(name2);
                            cad.get("conec").add(name1 + ":" + name2);
                            if (i == 0) {
                                cad.get("ini").add(name1);
                            }
                            i++;
                            break;
                        default:
                            String name0 = i + "" + d + "0";
                            String name01 = i + "" + d + "01";
                            cad.get("States").add(name0);
                            cad.get("States").add(name01);
                            cad.get("Separations").add(name0 + ":" + d + ">" + name01);
                            cad.get("final").add(name01);
                            cad.get("conec").add(name0 + ":" + name01);
                            if (i == 0) {
                                cad.get("ini").add(name0);
                            }
                            break;
                    }

                }
                ArrayList<String> asd = cad.get("conec");
                for (int i = 0; i < asd.size() - 1; i++) {
                    String asd1 = asd.get(i);
                    String asd2 = asd.get(i + 1);
                    String in = asd1.split(":")[1];
                    String go = asd2.split(":")[0];
                    cad.get("Separations").add(in + ":$>" + go);
                }

                String asdd = cad.get("final").get(cad.get("final").size() - 1);
                cad.get("final").clear();
                cad.get("final").add(asdd);

            } else if (uni == -1) {
                String uno = word.substring(0, pare);
                String dod = word.substring(pare);
                HashMap<String, ArrayList<String>> cad1 = evalu(uno);
                HashMap<String, ArrayList<String>> cad2 = evalu(dod);
                cad.get("Sigma").addAll(cad1.get("Sigma"));
                cad.get("Sigma").addAll(cad2.get("Sigma"));
                Set<String> reset = new HashSet<>(cad.get("Sigma"));
                cad.get("Sigma").clear();
                cad.get("Sigma").addAll(reset);
                cad1.get("States").forEach((string) -> {
                    cad.get("States").add("1" + string);
                });
                cad2.get("States").forEach((string) -> {
                    cad.get("States").add("2" + string);
                });
                reset = new HashSet<>(cad.get("States"));
                cad.get("States").clear();
                cad.get("States").addAll(reset);
                cad.get("States").add("C0");

                cad.get("ini").add("C0");

                cad1.get("final").forEach((string) -> {
                    cad.get("Separations").add("1" + string + ":$>" + "2" + cad2.get("ini").get(0));
                });
                cad2.get("final").forEach((string) -> {
                    cad.get("final").add("2" + string);
                });
                reset = new HashSet<>(cad.get("final"));
                cad.get("final").clear();
                cad.get("final").addAll(reset);

                cad.get("Separations").add("C0:$>" + "1" + cad1.get("ini").get(0));
                for (String asd1 : cad1.get("Separations")) {
                    String[] separos = asd1.split(":");
                    String estado1 = separos[0];
                    String[] sep = separos[1].split(">");
                    char caracter = sep[0].charAt(0);
                    String[] dos = sep[1].split(";");
                    String ddd = "";
                    for (String aDo : dos) {
                        ddd += "1" + aDo + ";";
                    }
                    cad.get("Separations").add("1" + estado1 + ":" + caracter + ">" + ddd.substring(0, ddd.length() - 1));
                }
                for (String asd1 : cad2.get("Separations")) {
                    String[] separos = asd1.split(":");
                    String estado1 = separos[0];
                    String[] sep = separos[1].split(">");
                    char caracter = sep[0].charAt(0);
                    String[] dos = sep[1].split(";");
                    String ddd = "";
                    for (String aDo : dos) {
                        ddd += "2" + aDo + ";";
                    }
                    cad.get("Separations").add("2" + estado1 + ":" + caracter + ">" + ddd.substring(0, ddd.length() - 1));
                }
            } else if (pare == -1) {
                String uno = word.substring(0, uni);
                String dod = word.substring(uni + 1);
                HashMap<String, ArrayList<String>> cad1 = evalu(uno);
                HashMap<String, ArrayList<String>> cad2 = evalu(dod);
                cad.get("Sigma").addAll(cad1.get("Sigma"));
                cad.get("Sigma").addAll(cad2.get("Sigma"));
                Set<String> reset = new HashSet<>(cad.get("Sigma"));
                cad.get("Sigma").clear();
                cad.get("Sigma").addAll(reset);
                cad1.get("States").forEach((string) -> {
                    cad.get("States").add("1" + string);
                });
                cad2.get("States").forEach((string) -> {
                    cad.get("States").add("2" + string);
                });
                reset = new HashSet<>(cad.get("States"));
                cad.get("States").clear();
                cad.get("States").addAll(reset);
                cad.get("States").add("U0");

                cad.get("ini").add("U0");

                cad1.get("final").forEach((string) -> {
                    cad.get("final").add("1" + string);
                });
                cad2.get("final").forEach((string) -> {
                    cad.get("final").add("2" + string);
                });
                reset = new HashSet<>(cad.get("final"));
                cad.get("final").clear();
                cad.get("final").addAll(reset);

                cad.get("Separations").add("U0:$>" + "1" + cad1.get("ini").get(0) + ";2" + cad2.get("ini").get(0));
                for (String asd1 : cad1.get("Separations")) {
                    String[] separos = asd1.split(":");
                    String estado1 = separos[0];
                    String[] sep = separos[1].split(">");
                    char caracter = sep[0].charAt(0);
                    String[] dos = sep[1].split(";");
                    String ddd = "";
                    for (String aDo : dos) {
                        ddd += "1" + aDo + ";";
                    }
                    cad.get("Separations").add("1" + estado1 + ":" + caracter + ">" + ddd.substring(0, ddd.length() - 1));
                }
                for (String asd1 : cad2.get("Separations")) {
                    String[] separos = asd1.split(":");
                    String estado1 = separos[0];
                    String[] sep = separos[1].split(">");
                    char caracter = sep[0].charAt(0);
                    String[] dos = sep[1].split(";");
                    String ddd = "";
                    for (String aDo : dos) {
                        ddd += "2" + aDo + ";";
                    }
                    cad.get("Separations").add("2" + estado1 + ":" + caracter + ">" + ddd.substring(0, ddd.length() - 1));
                }
            }
        } else {
            ArrayList<String> cadenas = new ArrayList<>();
            ArrayList<Integer> unionns = new ArrayList<>();
            Stack<Integer> pares = new Stack<>();
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == 'U' && pares.isEmpty()) {
                    unionns.add(i);
                }
                if (word.charAt(i) == '(') {
                    pares.push(i);
                } else if (word.charAt(i) == ')') {
                    pares.pop();
                }

            }
            String word2 = word;
            for (Integer unionn : unionns) {
                if (word.length() - 1 == unionn) {
                    evalu("U");
                }
                String cad1 = word2.substring(0, unionn);
                String cad2 = word2.substring(unionn + 1);
                word2 = cad2;
                cadenas.add(cad1);

            }
            cadenas.add(word2);
            ArrayList<String> inis = new ArrayList<>();

            for (int i = 0; i < cadenas.size(); i++) {
                HashMap<String, ArrayList<String>> cad1 = evalu(cadenas.get(i));
                inis.add(i + cad1.get("ini").get(0));
                cad.get("Sigma").addAll(cad1.get("Sigma"));
                for (String string : cad1.get("States")) {
                    cad.get("States").add(i + string);
                }

                for (String string : cad1.get("final")) {
                    cad.get("final").add(i + string);
                }

                for (String asd1 : cad1.get("Separations")) {
                    String[] separos = asd1.split(":");
                    String estado1 = separos[0];
                    String[] sep = separos[1].split(">");
                    char caracter = sep[0].charAt(0);
                    String[] dos = sep[1].split(";");
                    String ddd = "";
                    for (String aDo : dos) {
                        ddd += i + aDo + ";";
                    }
                    cad.get("Separations").add(i + estado1 + ":" + caracter + ">" + ddd.substring(0, ddd.length() - 1));
                }

            }
            Set<String> reset = new HashSet<>(cad.get("Sigma"));
            cad.get("Sigma").clear();
            cad.get("Sigma").addAll(reset);

            reset = new HashSet<>(cad.get("States"));
            cad.get("States").clear();
            cad.get("States").addAll(reset);
            cad.get("States").add("Ui");
            
            String hey ="";
            
            for (String ini : inis) {
                hey+=ini+";";
            }
            cad.get("ini").clear();
            cad.get("ini").add("Ui");
            cad.get("Separations").add("Ui:$>" + hey);
            reset = new HashSet<>(cad.get("final"));
            cad.get("final").clear();
            cad.get("final").addAll(reset);            
        }

        cad.get("Sigma").remove("$");
        return cad;
    }

    public static void main(String[] args) {
        AFNL sss = new KLENtoAFNL("a(ab*)+U$");
        System.out.println(sss.toString());
        System.out.println("sss");
        System.out.println(sss.procesarCadenaConDetalles("a"));
        System.out.println(sss.procesarCadenaConDetalles(""));
        System.out.println(sss.procesarCadenaConDetalles("aa"));
        System.out.println(sss.procesarCadenaConDetalles("aabb"));

    }
}
