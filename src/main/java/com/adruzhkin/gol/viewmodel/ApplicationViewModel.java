package com.adruzhkin.gol.viewmodel;

import java.util.LinkedList;
import java.util.List;

public class ApplicationViewModel {

    private ApplicationState currentState;
    private List<SimpleChangeListener> appStateListeners;

    public ApplicationViewModel(ApplicationState currentState) {
        this.currentState = currentState;
        this.appStateListeners = new LinkedList<>();
    }

    public void setCurrentState(ApplicationState newState) {
        if (this.currentState != newState) {
            this.currentState = newState;
            this.notifyAppStateListener();
        }
    }

    private void notifyAppStateListener() {
        for (SimpleChangeListener appStateListener : this.appStateListeners) {
            appStateListener.valueChanged();
        }
    }

    public void listenToAppState(SimpleChangeListener listener) {
        this.appStateListeners.add(listener);
    }

}
