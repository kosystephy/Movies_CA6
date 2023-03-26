package com.dkit.gd2.prithvi_muvvala.server;

import com.dkit.gd2.prithvi_muvvala.CompareRecipesByFavouriteCounter;
import com.dkit.gd2.prithvi_muvvala.CompareRecipesByRating;
import com.dkit.gd2.prithvi_muvvala.core.Packet;
import com.dkit.gd2.prithvi_muvvala.dao.MySqlRecipeDao;
import com.dkit.gd2.prithvi_muvvala.dao.RecipeDaoInterface;
import com.dkit.gd2.prithvi_muvvala.dto.Recipe;
import com.dkit.gd2.prithvi_muvvala.exceptions.DaoException;
import com.google.gson.Gson;

import java.util.List;

import static com.dkit.gd2.prithvi_muvvala.core.MenuOptions.*;

public class SortRecipesCommand implements Command
{
    private RecipeDaoInterface IRecipeDao;
    private final Gson gsonParser = new Gson();

    public SortRecipesCommand(String databaseURL)
    {
        IRecipeDao = new MySqlRecipeDao(databaseURL);
    }

    @Override
    public Packet createResponse(Packet incomingPacket)
    {
        RecipeSortOptions sortType = RecipeSortOptions.valueOf(incomingPacket.getPayload());
        String responseString = null;

        try {
            List<Recipe> recipes = IRecipeDao.findAllRecipes();

            switch (sortType) {
                case SORT_BY_RATING:
                    recipes.sort(new CompareRecipesByRating());
                    break;
                case SORT_BY_FAVOURITE_COUNTER:
                    recipes.sort(new CompareRecipesByFavouriteCounter());
                    break;
            }

            responseString = gsonParser.toJson(recipes);
        } catch (DaoException daoe) {
            System.out.println("SortRecipesCommand.createResponse() " + daoe.getMessage());
        }

        return new Packet(incomingPacket.getRequestType(), responseString);
    }

}
