package team.rainfall.finality.api;

import team.rainfall.finality.api.exceptions.InteractionNotFoundException;
import team.rainfall.finality.api.interaction.Interaction;
import team.rainfall.finality.api.logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;

// Base class of Plugin
@SuppressWarnings("unused")
public class Plugin {
    private String name;
    private Logger logger;
    private String pluginID;
    private final HashMap<Long, ArrayList<Interaction<?>>> listeners = new HashMap<>();
    private final HashMap<Long, Interaction<?>> exported = new HashMap<>();

    protected void internal$init(String name,String id){
        if(!FinalityAPI.isCallerFinalityAPI()){
            throw new SecurityException("Call internal method is not allowed.");
        }
        this.pluginID = id;
        this.name = name;
        this.logger = Logger.getLogger(name);
    }

    public String getName() {
        return name;
    }

    /**
     * Called on game initialization.
     * @see team.rainfall.finality.api.mixin.MixinAA_Game
     */
    public void init(){

    }
    public Logger getLogger() {
        return logger;
    }

    /**
     * Post event for single target.<br>
     * Only the first callback would be called.
     * @see Plugin#post(long, Object[]) 
     * @param id interactID
     * @param params interact parameters
     * @return interact return value
     * @throws InteractionNotFoundException the interactID does not exist.
     */
    public final Object postSingle(long id, Object[] params) throws InteractionNotFoundException {
        if (!listeners.containsKey(id)) {
            throw new InteractionNotFoundException();
        }
        return listeners.get(id).get(0).run(params);
    }
    /**
     * Post event.<br>
     * @see Plugin#postSingle(long, Object[]) 
     * @param id interactID
     * @param params interact parameters
     * @return interact return value
     * @throws InteractionNotFoundException the interactID does not exist.
     */
    public final ArrayList<Object> post(long id, Object[] params) throws InteractionNotFoundException {
        if (!listeners.containsKey(id)) {
            throw new InteractionNotFoundException();
        }
        ArrayList<Object> objects = new ArrayList<>();
        for (Interaction<?> interaction : listeners.get(id)) {
            Object o = interaction.run(params);
            if (o != null) {
                objects.add(o);
            }
        }
        return objects;
    }

    public final Object callExported(long id, Object[] objects) {
        if (exported.containsKey(id)) {
            return exported.get(id).run(objects);
        }
        return null;
    }
    public final String getPluginID(){
        return pluginID;
    }
    public final String getStandardPath(){
        return "finality/"+pluginID.replaceAll("\\.","-")+"/";
    }
    protected final void export(long id, Interaction<?> interaction) {
        if (exported.containsKey(id)) {
            throw new SecurityException("Exporting twice for a same ID is not allowed.");
        }
        exported.put(id, interaction);
    }

    public void subscribe(long id, Interaction<?> interaction) {
        if (!listeners.containsKey(id)) {
            listeners.put(id, new ArrayList<>());
        }
        listeners.get(id).add(interaction);
    }
}
