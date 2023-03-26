package com.dkit.gd2.prithvi_muvvala.server;

import com.dkit.gd2.prithvi_muvvala.core.Packet;
import com.dkit.gd2.prithvi_muvvala.core.RecipeServiceDetails;
import com.dkit.gd2.prithvi_muvvala.dao.MySqlRecipeDao;
import com.dkit.gd2.prithvi_muvvala.dao.RecipeDaoInterface;
import com.dkit.gd2.prithvi_muvvala.exceptions.DaoException;

public class DeleteRecipeCommand implements Command
{
    RecipeDaoInterface IRecipeDao;

    public DeleteRecipeCommand(String databaseURL)
    {
        IRecipeDao = new MySqlRecipeDao(databaseURL);
    }

    @Override
    public Packet createResponse(Packet incomingPacket)
    {
        String recipeName = incomingPacket.getPayload();
        String responseString = "FAILURE" + RecipeServiceDetails.BREAKING_CHARACTERS +
                "Deletion failed- A recipe with the name " + recipeName + " does not exist in the system.";

        try
        {
            if(IRecipeDao.deleteRecipeByName(recipeName))
            {
                responseString = "SUCCESS" + RecipeServiceDetails.BREAKING_CHARACTERS + "The recipe has been successfully deleted from the system.";
            }
        }
        catch (DaoException daoe)
        {
            System.out.println("DeleteRecipeCommand.createResponse() " + daoe.getMessage());
        }

        return new Packet(incomingPacket.getRequestType(), responseString);
    }
}
