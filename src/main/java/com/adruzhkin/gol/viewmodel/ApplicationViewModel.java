package com.adruzhkin.gol.viewmodel;

import com.adruzhkin.gol.util.Property;

public class ApplicationViewModel {

    private Property<ApplicationState> applicationState;

    public ApplicationViewModel() {
        this.applicationState = new Property<>(ApplicationState.EDITING);
    }

    public Property<ApplicationState> getApplicationState() {
        return applicationState;
    }
}
