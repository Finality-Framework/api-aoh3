//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package team.rainfall.finality.api.mixin;

import aoc.kingdoms.lukasz.jakowski.AA_KeyManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.map.province.ProvinceTouchExtraAction;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import team.rainfall.finality.api.ui.MenuProxy;
import team.rainfall.finality.luminosity2.annotations.Mixin;
import team.rainfall.finality.luminosity2.annotations.Shadow;


@Mixin(
        mixinClass = "aoc.kingdoms.lukasz.menu.MenuManager"
)
public class MixinMenuManager {
    public int viewID = 0;
    public int fromViewID = 0;
    public int toViewID = 0;

    public MixinMenuManager() {
    }
    public void internal$setCustomViewID_w(String name) {
        Game.hoverManager.resetHoverActive_Menu();

        this.viewID = MenuProxy.PROXY.getViewID(name);

        this.toViewID = this.viewID;
        this.updateDrawProvinces();
        ProvinceTouchExtraAction.updateExtraAction();
        AA_KeyManager.updateKeyExtraAction();
        ProvinceBorderManager.updateAction();
        ProvinceDraw.updateDrawMoveUnits();
        Game.mapBG.updateWorldMap();
    }

    public void internal$setCustomViewID(String name) {
        Game.hoverManager.resetHoverActive_Menu();
        this.fromViewID = this.viewID;

        this.viewID = MenuProxy.PROXY.getViewID(name);

        this.toViewID = this.viewID;
        this.updateDrawProvinces();
        ProvinceTouchExtraAction.updateExtraAction();
        AA_KeyManager.updateKeyExtraAction();
        ProvinceBorderManager.updateAction();
        ProvinceDraw.updateDrawMoveUnits();
        Game.mapBG.updateWorldMap();
    }

    @Shadow
    private void updateDrawProvinces() {
    }
}
