package com.example.agenda.utils;

import android.util.Log;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WebClient {

    public String post(String json){
        try {

            // URL DO SERVIDOR
            URL url = new URL("https://www.caelum.com.br/mobile");

            // ESTABELECE CONEXÃO
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // DEFINE O ENVIO E A RESPOSTA EM JSON
            urlConnection.setRequestProperty("ContentType","application/json");
            urlConnection.setRequestProperty("Accept","application/json");

            // DEFINE COMO POST PARA O ENVIO DO JSON
            urlConnection.setDoOutput(true);
            // ENCAPSULA O JSON NO DATAGRAMA
            PrintStream output = new PrintStream(urlConnection.getOutputStream());
            output.println(json);

            // ABRE CONEXÃO
            urlConnection.connect();

            // PEGA RESPOSTA DO SERVIDOR
            Scanner scanner = new Scanner(urlConnection.getInputStream());
            return scanner.next();
        } catch (IOException e) {
            Log.v("WEBCLIENTCONNERROR",e.getMessage());
        }
        return null;
    }
}
