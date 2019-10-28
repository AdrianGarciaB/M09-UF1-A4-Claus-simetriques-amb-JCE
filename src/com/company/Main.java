package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        exercicio1();
        exercicio2();
    }

    private static void exercicio1(){
        String mensajesecreto = "hola que tal!!!";
        Claus.getInstance().setClau("hola",256);
        byte[] mensajeencriptado = Claus.getInstance().encryptData(mensajesecreto.getBytes());

        Claus.getInstance().setClau("hola2",256);

        byte[] mensajedesencriptado = Claus.getInstance().decryptData(mensajeencriptado);
        if (mensajedesencriptado != null) System.out.println(new String(mensajedesencriptado));
    }

    private static void exercicio2() throws IOException {
        byte[] fileencrypted = Files.readAllBytes(Paths.get("data/textamagat"));

        String clave;
        String mensaje;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("data/clausA4.txt"));
        while ((clave = bufferedReader.readLine()) != null) {
            int keySize[] = new int[]{128, 192, 256};
            for (int i = 0; i < 3; i++) {
                Claus.getInstance().setClau(clave, keySize[i]);
                if (Claus.getInstance().decryptData(fileencrypted) != null) {
                    System.out.println("POSIBLE CLAVE ENCONTRADA: " + clave);
                    System.out.println("TAMAÑO DE LA CLAVE: " + keySize[i]);
                    System.out.println("MENSAJE: " + new String(Claus.getInstance().decryptData(fileencrypted), "UTF8"));
                }
            }
        }

    }

}
