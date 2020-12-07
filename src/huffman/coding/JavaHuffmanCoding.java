/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman.coding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author User
 */
public class JavaHuffmanCoding {

    public static Huffman h;

    public static void testHuffman(String orgStr, boolean show, String dotfilename) {
        System.out.print("* Construyendo el árbol de Huffman y creando la tabla de códigos...");
        h = new Huffman(orgStr, show, dotfilename);
        System.out.println(" DONE");

        if (show){
            System.out.println("\n========================= Frecuencia de caracteres =========================");
            for (Map.Entry<Character, Integer> entry: h.hmapWC.entrySet()){
                String key = entry.getKey().toString();
                int val = entry.getValue();
                if (key.equals("\n"))
                        key = "\\n";
                System.out.println(key + " aparece " + val + " vez/veces");
            }
            System.out.println("==============================================================================");

            System.out.println("\n==================== Código Huffman para cada caracter =====================");
            for (Map.Entry<Character, String> entry: h.hmapCode.entrySet()){
                String key = entry.getKey().toString();
                String val = entry.getValue();
                if (key.equals("\n"))
                        key = "\\n";
                System.out.println(key + ": " + val); 
            }
            System.out.println("==============================================================================");
        }

        System.out.print("\n* Codificando el texto...");
        String e = h.encode();
        System.out.println(" DONE");
        System.out.println("\n=============================== Binario ====================================");
        System.out.println(e);
        System.out.println("==============================================================================");

        System.out.print("\n* Convirtiendo a ascii...");
        String ascii = h.binaryToString();
        System.out.println(" DONE");
        System.out.println("\n================================= Ascii ====================================");
        System.out.println(ascii);
        System.out.println("==============================================================================");

        System.out.print("\n* Convirtiendo a binario...");
        String binary = h.convertToBinary();
        System.out.println(" DONE");
        System.out.println("\n================================ Binario ===================================");
        System.out.println(binary);
        System.out.println("==============================================================================");

        System.out.print("\n* Decodificando el texto codificado...");
        String d = h.decode();
        myassert(orgStr.equals(d)) ;   // Check if original text and decoded text is exactly same
        System.out.println(" DONE");
        System.out.println("\n=============================== Resultado ==================================");
        System.out.println(d);
        System.out.println("==============================================================================");

        double sl = orgStr.length() * 7;
        double el = ascii.length();
        System.out.println("\n================================== Costo ===================================");
        System.out.println("Costo del texto original = " + (int)sl + " bits") ;
        System.out.println("Costo del texto codificado = " + (int)el + " bits") ;
        double r = ((el - sl)/sl) * 100 ;
        System.out.println("% Reducción = " + (-r)) ;
        System.out.println("==============================================================================");
    }

    public static String readFile(String fname) {
        StringBuilder sb = new StringBuilder();
        File filename = new File(fname);
        try (BufferedReader in = new BufferedReader(new FileReader(filename))){
                String line = in.readLine();
                while (line != null){
                        sb.append(line + "\n");
                        line = in.readLine();
                }
        }
        catch (IOException e){
                System.out.println(e);
        }
        return sb.toString();
    }

    public static void myassert(boolean  x) {
        if (!x) {
            throw new IllegalArgumentException("Assert fail") ;
        }
    }

    public static void testbed() {
        boolean show = true ;
        String orgFile = "files/original.txt";
        String dotFile = "files/test.dot";

        System.out.print("\n* Cargando el archivo...");
        String orgString = readFile(orgFile);
        System.out.println(" DONE");

        testHuffman(orgString, show, dotFile);
    }

    public static void main(String[] args) {
        System.out.println("\n----- Test START -----");
        testbed();
        System.out.println("\n----- Test DONE ----- ");
    }

}
