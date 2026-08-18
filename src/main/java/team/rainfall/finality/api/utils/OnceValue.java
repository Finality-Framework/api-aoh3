package team.rainfall.finality.api.utils;

@SuppressWarnings("unused")
public class OnceValue<T> {
    private T value = null;
    public OnceValue(){

    }
    public OnceValue(T value){
        this.value = value;
    }
    public T get(){
        synchronized (this) {
            if (value == null) {
                throw new NullPointerException("OnceValue has not been initialized");
            }
            return value;
        }
    }
    public boolean initialized(){
        return value != null;
    }
    public void set(T value){
        synchronized (this) {
            if (initialized()) return;
            if (value == null) throw new IllegalArgumentException("OnceValue couldn't accept a null value");
            this.value = value;
        }
    }
}
