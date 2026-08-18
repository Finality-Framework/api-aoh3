package team.rainfall.finality.api.logging;

import team.rainfall.finality.FinalityLogger;

@SuppressWarnings("unused")
public class Logger {
    private final String name;
    public static Logger getLogger(String name){
         return new Logger(name);
    }
    public static Logger getLogger(Class<?> clz){
        return new Logger(clz.getName());
    }
    private Logger(String name){
        this.name = name;
    }
    public void info(String message){
        FinalityLogger.info( name + " -> " + message);
    }
    public void error(String message){
        FinalityLogger.error( name + " -> " + message);
    }
    public void warn(String message){
        FinalityLogger.warn( name + " -> " + message);
    }
    public void debug(String message){
        FinalityLogger.debug( name + " -> " + message);
    }
    public void error(String message,Throwable e){
        FinalityLogger.error( name + " -> " + message,e);
    }
}
