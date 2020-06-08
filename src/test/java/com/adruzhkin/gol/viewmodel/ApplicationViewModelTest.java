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
        assertEquals(ApplicationState.SIMULATING, listener.updatedAppState);
    }

    @Test
    void setCurrentState_sameState() {
        TestAppStateListener listener = new TestAppStateListener();
        this.applicationViewModel.listenToAppState(listener);
        this.applicationViewModel.setCurrentState(ApplicationState.EDITING);

        assertFalse(listener.appStateUpdated);
        assertNull(listener.updatedAppState);
    }

    private static class TestAppStateListener implements SimpleChangeListener<ApplicationState> {
        private boolean appStateUpdated = false;
        private ApplicationState updatedAppState = null;

        @Override
        public void valueChanged(ApplicationState newAppState) {
            updatedAppState = newAppState;
            appStateUpdated = true;
        }
    }

}