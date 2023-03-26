package com.dkit.gd2.prithvi_muvvala.server;

import com.dkit.gd2.prithvi_muvvala.FilterRecipesByFavouriteCounter;
import com.dkit.gd2.prithvi_muvvala.FilterRecipesByRating;
import com.dkit.gd2.prithvi_muvvala.core.Packet;
import com.dkit.gd2.prithvi_muvvala.core.RecipeServiceDetails;
import com.dkit.gd2.prithvi_muvvala.dao.MySqlRecipeDao;
import com.dkit.gd2.prithvi_muvvala.dao.RecipeDaoInterface;
import com.dkit.gd2.prithvi_muvvala.dto.Recipe;
import com.dkit.gd2.prithvi_muvvala.exceptions.DaoException;
import com.google.gson.Gson;

import java.util.List;

import static com.dkit.gd2.prithvi_muvvala.core.MenuOptions.*;

public class FilterRecipesCommand implements Command
{
    private final RecipeDaoInterface IRecipeDao;
    private final Gson gsonParser = new Gson();

    public FilterRecipesCommand(String databaseURL)
    {
        IRecipeDao = new MySqlRecipeDao(databaseURL);
    }

    @Override
    public Packet createResponse(Packet incomingPacket)
    {
        String responseString = null;

        String[] components = incomingPacket.getPayload().split(RecipeServiceDetails.BREAKING_CHARACTERS);

        RecipeFilterOptions filter = RecipeFilterOptions.valueOf(components[0]);
        List<Recipe> filteredRecipes = null;

        switch (filter)
        {
            case FILTER_BY_RATING:
                filteredRecipes = filterByRating(components);
                break;
            case FILTER_BY_FAVOURITE_COUNTER:
                filteredRecipes = filterByFavouriteCounter(components);
                break;
            default:
                break;
        }

        responseString = gsonParser.toJson(filteredRecipes);

        return new Packet(incomingPacket.getRequestType(), responseString);
    }

    private List<Recipe> filterByRating(String[] components)
    {
        List<Recipe> filteredRecipes = null;

        double minRating = Double.parseDouble(components[1]);
        double maxRating = Double.parseDouble(components[2]);

        try
        {
            filteredRecipes = IRecipeDao.filterRecipes(new FilterRecipesByRating(minRating, maxRating));
        }
        catch (DaoException daoe)
        {
            System.out.println("FilterRecipesCommand.filterByRating() " + daoe.getMessage());
        }

        return filteredRecipes;
    }

    private List<Recipe> filterByFavouriteCounter(String[] components)
    {
        List<Recipe> filteredRecipes = null;

        int minFavourites = Integer.parseInt(components[1]);
        int maxFavourites = Integer.parseInt(components[2]);

        try
        {
            filteredRecipes = IRecipeDao.filterRecipes(new FilterRecipesByFavouriteCounter(minFavourites, maxFavourites));
        }
        catch (DaoException daoe)
        {
            System.out.println("FilterRecipesCommand.filterByFavouriteCounter() " + daoe.getMessage());
        }

        return filteredRecipes;
    }

}
