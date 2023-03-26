package com.dkit.gd2.prithvi_muvvala.server;

import com.dkit.gd2.prithvi_muvvala.core.Packet;
import com.dkit.gd2.prithvi_muvvala.core.RecipeServiceDetails;
import com.dkit.gd2.prithvi_muvvala.dao.MySqlRecipeDao;
import com.dkit.gd2.prithvi_muvvala.dao.RecipeDaoInterface;
import com.dkit.gd2.prithvi_muvvala.exceptions.DaoException;

public class CheckRecipeExistsCommand implements Command
{
    private final RecipeDaoInterface IRecipeDao;

    public CheckRecipeExistsCommand(String databaseURL)
    {
        IRecipeDao = new MySqlRecipeDao(databaseURL);
    }

    @Override
    public Packet createResponse(Packet incomingPacket)
    {
        String recipeName = incomingPacket.getPayload();
        String responseString = "FAILURE" + RecipeServiceDetails.BREAKING_CHARACTERS +
                "Insertion failed- a recipe with the name " + recipeName + " already exists in the system.";

        try
        {
            boolean recipeExists = IRecipeDao.checkRecipeExists(recipeName);
            if(!recipeExists)
            {
                responseString = "SUCCESS";
            }
        }
        catch (DaoException daoe)
        {
            System.out.println("CheckRecipeExistsCommand.createResponse() " + daoe.getMessage());
        }

        System.out.println(responseString);
        return new Packet(incomingPacket.getRequestType(), responseString);
    }
}
