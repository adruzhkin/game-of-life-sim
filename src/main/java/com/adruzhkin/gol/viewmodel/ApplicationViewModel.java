package com.adruzhkin.gol.viewmodel;

import java.util.LinkedList;
import java.util.List;

public class ApplicationViewModel {

    private ApplicationState currentState;
    private List<SimpleChangeListener<ApplicationState>> appStateListeners;

    public ApplicationViewModel(ApplicationState currentState) {
        this.currentState = currentState;
        this.appStateListeners = new LinkedList<>();
    }

    public ApplicationState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ApplicationState newState) {
        if (this.currentState != newState) {
            this.currentState = newState;
            this.notifyAppStateListener();
        }
    }

    private void notifyAppStateListener() {
        for (SimpleChangeListener<ApplicationState> appStateListener : this.appStateListeners) {
            appStateListener.valueChanged(this.currentState);
        }
    }

    public void listenToAppState(SimpleChangeListener<ApplicationState> listener) {
        this.appStateListeners.add(listener);
    }

}
