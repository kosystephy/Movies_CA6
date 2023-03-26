package com.dkit.gd2.prithvi_muvvala.server;


import com.dkit.gd2.prithvi_muvvala.constants.DatabaseDetails;

import static com.dkit.gd2.prithvi_muvvala.core.MenuOptions.*;

public class CommandFactory
{
    public CommandFactory()
    {
    }

    public Command createCommand(ClientMenuOptions command)
    {
        Command c = null;

        switch (command)
        {
            case DISPLAY_RECIPE_BY_NAME:
                c = new DisplayRecipeCommand(DatabaseDetails.MAIN_DB_URL);
                break;
            case DISPLAY_ALL_RECIPES:
                c = new DisplayAllCommand(DatabaseDetails.MAIN_DB_URL);
                break;
            case ADD_RECIPE:
                c = new AddRecipeCommand(DatabaseDetails.MAIN_DB_URL);
                break;
            case DELETE_RECIPE_BY_NAME:
                c = new DeleteRecipeCommand(DatabaseDetails.MAIN_DB_URL);
                break;
            case SORT_RECIPES:
                c = new SortRecipesCommand(DatabaseDetails.MAIN_DB_URL);
                break;
            case FILTER_RECIPES:
                c = new FilterRecipesCommand(DatabaseDetails.MAIN_DB_URL);
                break;
            case CHECK_RECIPE_EXISTS:
                c = new CheckRecipeExistsCommand(DatabaseDetails.MAIN_DB_URL);
                break;
        }

        return c;
    }

}
