package team.rainfall.finality.api.mixin;

import team.rainfall.finality.api.FinalityAPI;
import team.rainfall.finality.luminosity2.CallbackInfo;
import team.rainfall.finality.luminosity2.annotations.Inject;
import team.rainfall.finality.luminosity2.annotations.Mixin;

@Mixin(mixinClass = "aoc.kingdoms.lukasz.jakowski.AA_Game")
public class MixinAA_Game {
    @Inject(methodName = "initGame")
    protected final void inject$initGame(CallbackInfo callbackInfo) {
        FinalityAPI.INSTANCE.init();
    }
}
