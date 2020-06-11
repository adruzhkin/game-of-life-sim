package com.adruzhkin.gol.util;

import com.adruzhkin.gol.viewmodel.SimpleChangeListener;

import java.util.LinkedList;
import java.util.List;

public class Property<T> {

    private T value;
    private List<SimpleChangeListener<T>> listeners;

    public Property(T value) {
        this.value = value;
        this.listeners = new LinkedList<>();
    }

    public Property() {
        this(null);
    }

    public void listen(SimpleChangeListener<T> listener) {
        this.listeners.add(listener);
    }

    public void set(T newValue) {
        this.value = newValue;
        this.notifyListeners();
    }

    public T get() {
        return this.value;
    }

    public boolean isPresent() {
        return this.value != null;
    }

    private void notifyListeners() {
        for (SimpleChangeListener<T> listener : listeners) {
            listener.valueChanged(this.value);
        }
    }

}
