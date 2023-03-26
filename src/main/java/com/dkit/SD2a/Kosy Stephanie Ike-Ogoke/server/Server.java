package com.dkit.gd2.prithvi_muvvala.server;

import com.dkit.gd2.prithvi_muvvala.core.RecipeServiceDetails;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket connectionSocket = new ServerSocket(RecipeServiceDetails.SERVER_PORT);
            Socket dataSocket = null;
            System.out.println("Ready to accept connection.");

            boolean continueRunning = true;
            while(continueRunning)
            {
                System.out.println("Waiting for connections...");
                dataSocket = connectionSocket.accept();
                System.out.println("Connection accepted.");

                ThreadPerClient runnable = new ThreadPerClient(dataSocket);
                Thread clientThread = new Thread(runnable);
                clientThread.start();
            }

            dataSocket.close();
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }
}
