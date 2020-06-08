package com.adruzhkin.gol.viewmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationViewModelTest {

    private ApplicationViewModel applicationViewModel;

    @BeforeEach
    void setUp() {
        this.applicationViewModel = new ApplicationViewModel(ApplicationState.EDITING);
    }

    @Test
    void setCurrentState_differentState() {
        TestAppStateListener listener = new TestAppStateListener();
        this.applicationViewModel.listenToAppState(listener);
        this.applicationViewModel.setCurrentState(ApplicationState.SIMULATING);

        assertTrue(listener.appStateUpdated);
    }

    @Test
    void setCurrentState_sameState() {
        TestAppStateListener listener = new TestAppStateListener();
        this.applicationViewModel.listenToAppState(listener);
        this.applicationViewModel.setCurrentState(ApplicationState.EDITING);

        assertFalse(listener.appStateUpdated);
    }

    private static class TestAppStateListener implements SimpleChangeListener {
        private boolean appStateUpdated = false;

        @Override
        public void valueChanged() {
            appStateUpdated = true;
        }
    }

}