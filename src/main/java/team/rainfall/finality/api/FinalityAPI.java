package team.rainfall.finality.api;

import team.rainfall.finality.FinalityLogger;
import team.rainfall.finality.PluginMetadata;
import team.rainfall.finality.generated.PluginsCollection;

import java.util.HashMap;

public class FinalityAPI {
    private final HashMap<String,Plugin> pluginMap = new HashMap<>();
    public static final FinalityAPI INSTANCE = new FinalityAPI();
    public static boolean isCallerFinalityAPI() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length < 3) {
            return false;
        }
        for (StackTraceElement stackTraceElement : stackTrace) {
            if(stackTraceElement.getClassName().equals("team.rainfall.finality.api.FinalityAPI")) return true;
        }
        return false;
    }
    @SuppressWarnings("unused")
    public Plugin getPlugin(String id){
        if(pluginMap.containsKey(id)){
            return pluginMap.get(id);
        }
        return null;
    }

    public void init(){
        for (PluginMetadata plugin : PluginsCollection.plugins) {
            if(plugin.id == null){
                throw new IllegalArgumentException("Illegal ID Found");
            }
            if(plugin.mainClass != null && !pluginMap.containsKey(plugin.id)) {
                try {
                    pluginMap.put(plugin.id, (Plugin) Class.forName(plugin.mainClass).newInstance());
                    pluginMap.get(plugin.id).internal$init(plugin.name, plugin.id);
                    pluginMap.get(plugin.id).init();
                } catch (Exception e) {
                    FinalityLogger.error("FinalityAPI could not initialize plugin:  "+plugin.id,e);
                }
            }
        }
    }
}
