/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iComponents;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;


/**
 *
 * @author Andrey Mora
 */
public class iINI {

    private final File DIRECCION = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop");//direccion local del archivo
    private final File ARCHIVO = new File(DIRECCION, "Archivo.ini");//nombre del archivo y tipo

    public iINI() {
    }//constructor principal

    /**
     * Crea un nuevo archivo, si ya existe no pasa nada
     */
    private void crear() {

        if (ARCHIVO.exists()) {
            System.out.println("El archivo ya existe");
        } else {
            try {
                ARCHIVO.createNewFile();
                System.out.println("Archivo creado");
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    /**
     * Devuelve un string con el contenido del archivo
     *
     * @return String
     */
    public String leer() {
        StringBuilder leido = new StringBuilder();
        try {
            try (FileReader lector = new FileReader(ARCHIVO)) {
                int numLetra = lector.read();
                while (numLetra != -1) {
                    leido.append((char) numLetra);
                    numLetra = lector.read();
                }
            }
        } catch (IOException e) {
            System.out.println("Hubo un error: " + e.getMessage());
        }
        return leido.toString();
    }

    public void limpiar() {
        try {
            FileWriter escritor = new FileWriter(ARCHIVO);
            escritor.write("");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * Recibe un texto del metodo "set" y lo ingrsa dentro del archivo
     *
     * @param texto
     * @return
     */
    private boolean escibir(String texto) {
        limpiar();
        String escrito = "";
        try {
            FileOutputStream pos = new FileOutputStream(ARCHIVO);
            BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(pos));
            for (int i = 0; i < texto.length(); i++) {
                escrito += texto.charAt(i);
                if (escrito.contains("\n")) {
                    escritor.write(escrito.replaceAll("\n", ""));
                    escritor.newLine();
                    escrito = "";
                } else if (i == texto.length() - 1) {
                    escritor.write(escrito);
                }
            }
            escritor.close();
            pos.close();
        } catch (IOException e) {
            System.out.println("Hubo un error: " + e.getMessage());
        }
        return true;
    }

    /**
     * Se encarga de verificar, acomodar y editar el contenido del archivo. 
     * Crea y actualiza llaves y etiquetas.
     *
     * @param etiqueta
     * @param llave
     * @param valor
     * @return
     */
    public boolean set(String etiqueta, String llave, Object valor) {
        try {
            String comprobacion = etiqueta + llave + valor;
            if (!(comprobacion.contains("=") ||comprobacion.contains("[") || comprobacion.contains("]"))) {//¿Contiene carácteres prohibidos?
                comprobacion = null;
                if (ARCHIVO.exists()) {
                    String contenido = leer();
                    StringBuilder textoLimpio = new StringBuilder();
                    contenido = contenido.replaceAll("\r", "");
                    int posicion = contenido.indexOf(("[" + etiqueta + "]"));
                    if (posicion != -1) {//¿Existe la etiqueta?
                        posicion += etiqueta.length() + 3;
                        for (int i = 0; i < posicion; i++) {
                            textoLimpio.append(contenido.charAt(i));
                        }
                        if (contenido.contains(llave)) {//si alguna etiqueta tiene la llave
                            String temp = "";
                            int posicionInicial = posicion;//hace una copia de la posicion del puntero inicial
                            while (posicion < contenido.length()) {//encuentra la llave
                                temp += contenido.charAt(posicion);
                                if (temp.contains(llave + '=')) {//setea el nuevo valor a la llave
                                    textoLimpio.append(temp);
                                    textoLimpio.append(valor);
                                    textoLimpio.append("\n");
                                    temp = "";
                                    while (!temp.contains("\n")) {
                                        temp += contenido.charAt(posicion);
                                        posicion++;
                                    }
                                    break;
                                } else if (temp.contains("[")) {//si la llave no existe crea una nueva
                                    posicion = posicionInicial;
                                    textoLimpio.append(llave);
                                    textoLimpio.append("=");
                                    textoLimpio.append(valor);
                                    textoLimpio.append("\n");
                                    break;
                                }
                                posicion++;
                            }
                        } else {//si no contiene la llave la inserta
                            textoLimpio.append(llave);
                            textoLimpio.append("=");
                            textoLimpio.append(valor);
                            textoLimpio.append("\n");
                        }
                        for (int i = posicion; i < contenido.length(); i++) {//inserta el resto del texto del archivo
                            textoLimpio.append(contenido.charAt(i));
                        }
                    } else {
                        textoLimpio.append(contenido);
                        textoLimpio.append("[");
                        textoLimpio.append(etiqueta);
                        textoLimpio.append("]");
                        textoLimpio.append("\n");
                        textoLimpio.append(llave);
                        textoLimpio.append("=");
                        textoLimpio.append(valor);
                        textoLimpio.append("\n");
                    }
                    escibir(textoLimpio.toString());

                } else {//si no existe la etiqueta, la crea y setea la llave y valor
                    crear();
                    set(etiqueta, llave, valor);
                }
            } else {
                System.out.println("Los carácteres '[',']' y '='. Son invalidos para etiquetas, keys y values");
            }
        } catch (Exception e) {
            System.out.println("Hubo un error: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * Se encarga de tomar el valor pregrabado en el par etiqueta/llave solicitada
     * @param etiqueta
     * @param llave
     * @return 
     */
    public String get(String etiqueta, String llave) {
        String resultado = "-0";
        if (ARCHIVO.exists()) {//verifica si el archivo existe
            String contenido = leer();
            contenido = contenido.replaceAll("/r", "");//quita los caracteres especiales duplicados
            int posicion = contenido.indexOf(("[" + etiqueta + "]"));
            if (posicion != -1) {//verifica si la etiqueta existe
                char letra;
                String temporal = "";
                while (posicion < contenido.length()) {
                    temporal += contenido.charAt(posicion);
                    posicion++;
                    if (temporal.contains(llave + '=')) {//encuentra la llave
                        resultado = "";
                        letra = contenido.charAt(posicion);
                        while (letra != '\n') {
                            letra = contenido.charAt(posicion);
                            resultado += letra;
                            posicion++;
                        }
                        break;
                    }
                }
            } else {
                System.out.println("La etiqueta " + etiqueta + ", no existe");
            }
        } else {
            System.out.println("El archivo no existe");
        }
        return resultado;
    }

    /**
     * Retorna todas las llaves que contiene una etiqueta
     * @param etiqueta
     * @return 
     */
    public String get(String etiqueta) {
        String resultado = "";
        if (ARCHIVO.exists()) {
            String contenido = leer();
            contenido = contenido.replaceAll("/r", "");//quita los caracteres especiales duplicados
            int posicion = contenido.indexOf(("[" + etiqueta + "]"));
            if (posicion != -1) {//verifica si existe la etiqueta
                posicion += etiqueta.length() + 4;
                String temp="";
                while(posicion<contenido.length()){
                    temp+=contenido.charAt(posicion);
                    if(temp.contains("=")){
                        temp=temp.replace("=", "\n");
                        resultado+=temp;
                        temp="";
                        while(!temp.contains("\n")){
                            temp+=contenido.charAt(posicion);
                            posicion++;
                        }
                        posicion--;
                        temp="";
                    }else if(temp.contains("[")){
                        break;
                    }
                    posicion++;
                }
            } else {
                System.out.println("La etiqueta " + etiqueta + " no existe");
            }
        }
        return resultado;
    }

}
