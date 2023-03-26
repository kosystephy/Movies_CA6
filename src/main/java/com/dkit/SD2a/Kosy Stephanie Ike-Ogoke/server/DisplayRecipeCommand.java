package com.dkit.gd2.prithvi_muvvala.server;

import com.dkit.gd2.prithvi_muvvala.core.Packet;
import com.dkit.gd2.prithvi_muvvala.dao.MySqlRecipeDao;
import com.dkit.gd2.prithvi_muvvala.dao.RecipeDaoInterface;
import com.dkit.gd2.prithvi_muvvala.exceptions.DaoException;

public class DisplayRecipeCommand implements Command
{
    private RecipeDaoInterface IRecipeDao;

    public DisplayRecipeCommand(String databaseURL)
    {
        this.IRecipeDao = new MySqlRecipeDao(databaseURL);
    }

    @Override
    public Packet createResponse(Packet incomingPacket)
    {
        String recipeName = incomingPacket.getPayload();
        String responseString = null;

        try
        {
            responseString = IRecipeDao.findRecipeByNameJson(recipeName);
        }
        catch (DaoException daoe)
        {
            System.out.println("DisplayRecipeCommand.createResponse() " + daoe.getMessage());
        }

        return new Packet(incomingPacket.getRequestType(), responseString);
    }
}
