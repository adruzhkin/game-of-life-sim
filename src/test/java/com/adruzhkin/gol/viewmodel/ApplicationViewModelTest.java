package com.adruzhkin.gol.viewmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationViewModelTest {

    private ApplicationViewModel applicationViewModel;
    private TestAppStateListener listener;

    @BeforeEach
    void setUp() {
        this.applicationViewModel = new ApplicationViewModel(ApplicationState.EDITING);
        this.listener = new TestAppStateListener(this.applicationViewModel.getCurrentState());
        this.applicationViewModel.listenToAppState(this.listener);
    }

    @Test
    void setCurrentState_differentState() {
        this.applicationViewModel.setCurrentState(ApplicationState.SIMULATING);

        assertEquals(ApplicationState.SIMULATING, listener.appState);
    }

    @Test
    void setCurrentState_sameState() {
        this.applicationViewModel.setCurrentState(ApplicationState.EDITING);

        assertEquals(ApplicationState.EDITING, listener.appState);
    }

    private static class TestAppStateListener implements SimpleChangeListener<ApplicationState> {
        private ApplicationState appState;

        public TestAppStateListener(ApplicationState appState) {
            this.appState = appState;
        }

        @Override
        public void valueChanged(ApplicationState newAppState) {
            this.appState = newAppState;
        }
    }

}