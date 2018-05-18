package util;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Event<T extends Object> {
    private List<Consumer<T>> listeners;
    private List<String> keys;

    public Event(){
        listeners = new ArrayList<>();
        keys = new ArrayList<>();
    }

    public void addListener(Consumer<T> listener){
        addListener(listener, "");
    }

    public void addListener(Consumer<T> listener, String key){
        int idx = listeners.indexOf(listener);
        if (idx == -1){
            listeners.add(listener);
            keys.add(key);
        }else{
            listeners.set(idx, listener);
        }
    }

    public void removeListener(Consumer<T> listener){
        int idx = listeners.indexOf(listener);
        if (idx == -1){
            listeners.remove(idx);
            keys.remove(idx);
        }
    }

    public void removeListener(String key){
        int idx = keys.indexOf(key);
        if (idx == -1){
            listeners.remove(idx);
            keys.remove(idx);
        }
    }

    public void emit(){
        emit(null);
    }

    public void emit(T data){
        for(Consumer<T> listener : listeners){
            listener.accept(data);
        }
    }

    public void clear(){
        this.listeners.clear();
    }
}
