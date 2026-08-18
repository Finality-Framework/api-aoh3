package team.rainfall.finality.api.ui;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import team.rainfall.finality.FinalityLogger;
import team.rainfall.finality.api.interaction.Interaction;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuProxy {
    public static final MenuProxy PROXY = new MenuProxy();
    public Method addMenu;
    public final HashMap<String, Integer> viewIDs = new HashMap<>();
    public final HashMap<String, Interaction<ArrayList<Menu>>> views = new HashMap<>();
    public MenuProxy() {
        try {
            this.addMenu = MenuManager.class.getDeclaredMethod("addMenu", Menu.class);
            this.addMenu.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setViewIDWithoutAnimation(String id){
        try {
            MenuManager.class.getDeclaredMethod("internal$setCustomViewID_w", String.class).invoke(Game.menuManager, id);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            FinalityLogger.error("setCustomViewID failed",e);
        }
    }

    /**
     * Switch to target custom view.
     * @param id custom viewID
     */
    public static void setViewID(String id){
        try {
            MenuManager.class.getDeclaredMethod("internal$setCustomViewID", String.class).invoke(Game.menuManager, id);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            FinalityLogger.error("setCustomViewID failed",e);
        }
    }

    /**
     * Register a custom view.
     * @param id custom viewID
     * @param callback return a menu list which represents the composition of the view.
     */
    public void addView(String id,Interaction<ArrayList<Menu>> callback){
        views.put(id,callback);
    }

    public int getViewID(String id){
        if(views.containsKey(id)){
            ArrayList<Menu> result = views.get(id).run(null);
            if(viewIDs.containsKey(id)){
                for (int i = 0; i < result.size(); i++) {
                    getMenus().get(viewIDs.get(id)).set(i,result.get(i));
                }
                return viewIDs.get(id);
            }else {
                try {
                    int v = (Integer)addMenu.invoke(Game.menuManager, result.get(0));
                    viewIDs.put(id,v);
                    for (int i = 1; i < result.size(); i++) {
                        Game.menuManager.addNextMenuToView(v,result.get(i));
                    }
                    return v;
                } catch (Exception e){
                    FinalityLogger.error("Failed to set view "+id);
                }
            }
        }
        FinalityLogger.error("Failed to set view(2) "+id);
        return Game.menuManager.MAINMENU;
    }
    public static List<List<Menu>> getMenus() {
        try {
            Field menus = MenuManager.class.getDeclaredField("menus");
            menus.setAccessible(true);
            return (List)menus.get(Game.menuManager);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }




}

