package com.adruzhkin.gol.viewmodel;

import com.adruzhkin.gol.Simulation;
import com.adruzhkin.gol.model.StandardRule;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SimulationViewModel {

    private Timeline timeline;
    private BoardViewModel boardViewModel;
    private ApplicationViewModel applicationViewModel;
    private EditorViewModel editorViewModel;
    private Simulation simulation;

    public SimulationViewModel(BoardViewModel boardViewModel, ApplicationViewModel applicationViewModel,
                               EditorViewModel editorViewModel) {

        this.boardViewModel = boardViewModel;
        this.applicationViewModel = applicationViewModel;
        this.editorViewModel = editorViewModel;

        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), actionEvent -> this.doStep()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);

        this.simulation = new Simulation(editorViewModel.getBoard(), new StandardRule());
    }

    public void handle(SimulatorEvent event) {
        switch (event.getEventType()) {
            case START:
                start();
                break;
            case STOP:
                stop();
                break;
            case STEP:
                doStep();
                break;
            case RESET:
                reset();
                break;
        }
    }

    public void doStep() {
        if (this.applicationViewModel.getApplicationState().get() != ApplicationState.SIMULATING) {
            this.applicationViewModel.getApplicationState().set(ApplicationState.SIMULATING);
        }

        this.simulation.step();
        this.boardViewModel.getBoard().set(this.simulation.getBoard());
    }

    public void start() {
        this.timeline.play();
    }

    public void stop() {
        this.timeline.stop();
    }

    public void reset() {
        this.simulation = new Simulation(editorViewModel.getBoard(), new StandardRule());
        this.applicationViewModel.getApplicationState().set(ApplicationState.EDITING);
    }

}
