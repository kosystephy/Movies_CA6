package com.dkit.gd2.prithvi_muvvala.server;

import com.dkit.gd2.prithvi_muvvala.core.Packet;
import com.dkit.gd2.prithvi_muvvala.dao.MySqlRecipeDao;
import com.dkit.gd2.prithvi_muvvala.dao.RecipeDaoInterface;
import com.dkit.gd2.prithvi_muvvala.exceptions.DaoException;

public class DisplayAllCommand implements Command
{
    private RecipeDaoInterface IRecipeDao;

    public DisplayAllCommand(String databaseURL)
    {
        this.IRecipeDao = new MySqlRecipeDao(databaseURL);
    }

    @Override
    public Packet createResponse(Packet incomingPacket)
    {
        String responseString = null;

        try
        {
            responseString = IRecipeDao.findAllRecipesJson();
        }
        catch (DaoException daoe)
        {
            System.out.println("DisplayAllCommand.createResponse() " + daoe.getMessage());
        }


        return new Packet(incomingPacket.getRequestType(), responseString);
    }
}
