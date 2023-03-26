package com.dkit.gd2.prithvi_muvvala.server;

import com.dkit.gd2.prithvi_muvvala.core.Packet;
import com.dkit.gd2.prithvi_muvvala.core.RecipeServiceDetails;
import com.dkit.gd2.prithvi_muvvala.dao.MySqlRecipeDao;
import com.dkit.gd2.prithvi_muvvala.dao.RecipeDaoInterface;
import com.dkit.gd2.prithvi_muvvala.dto.Recipe;
import com.dkit.gd2.prithvi_muvvala.exceptions.DaoException;
import com.google.gson.Gson;

public class AddRecipeCommand implements Command
{
    private RecipeDaoInterface IRecipeDao;
    private final Gson gsonParser = new Gson();

    public AddRecipeCommand(String databaseURL)
    {
        IRecipeDao = new MySqlRecipeDao(databaseURL);
    }

    @Override
    public Packet createResponse(Packet incomingPacket)
    {
        String recipeJSON = incomingPacket.getPayload();
        Recipe recipeToAdd = gsonParser.fromJson(recipeJSON, Recipe.class);
        String responseString = "FAILURE" + RecipeServiceDetails.BREAKING_CHARACTERS +
                "Insertion failed- a recipe with the name " + recipeToAdd.getRECIPE_NAME() + " already exists in the system.";

        try
        {
            if(IRecipeDao.insertRecipe(recipeToAdd))
            {
                responseString = "SUCCESS" + RecipeServiceDetails.BREAKING_CHARACTERS + "The recipe has been added to the system.";
            }
        }
        catch (DaoException daoe)
        {
            System.out.println("AddRecipeCommand.createResponse() " + daoe.getMessage());
        }

        return new Packet(incomingPacket.getRequestType(), responseString);
    }
}
