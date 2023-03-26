package com.dkit.gd2.prithvi_muvvala.server;

import com.dkit.gd2.prithvi_muvvala.core.MenuOptions;
import com.dkit.gd2.prithvi_muvvala.core.Packet;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ThreadPerClient implements Runnable
{
    private Socket dataSocket;

    public ThreadPerClient(Socket dataSocket)
    {
        this.dataSocket = dataSocket;
    }

    @Override
    public void run()
    {
        try
        {
            OutputStream out = dataSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));

            InputStream in = dataSocket.getInputStream();
            Scanner input = new Scanner(new InputStreamReader(in));

            Packet incomingPacket = new Packet(MenuOptions.ClientMenuOptions.NONE, null);

            while(!incomingPacket.getRequestType().equals(MenuOptions.ClientMenuOptions.EXIT))
            {
                incomingPacket.readFromJSON(input.nextLine());
                System.out.println("\nReceived message: \n" + incomingPacket);

                CommandFactory factory = new CommandFactory();

                Command command = factory.createCommand(incomingPacket.getRequestType());
                Packet responsePacket = null;

                if(command != null)
                {
                    responsePacket = command.createResponse(incomingPacket);
                }
                else continue;

                writer.println(responsePacket.writeToJSON());
                writer.flush();
            }

        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
        catch (NoSuchElementException nse)
        {
            System.out.println("No line found.");
        }
    }
}
