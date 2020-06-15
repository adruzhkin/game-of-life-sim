package com.adruzhkin.gol;

import com.adruzhkin.gol.model.Board;
import com.adruzhkin.gol.model.BoundedBoard;
import com.adruzhkin.gol.util.event.EventBus;
import com.adruzhkin.gol.view.SimulationCanvas;
import com.adruzhkin.gol.viewmodel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        EventBus eventBus = new EventBus();

        ApplicationViewModel appViewModel = new ApplicationViewModel();
        BoardViewModel boardViewModel = new BoardViewModel();
        Board board = new BoundedBoard(20, 12);
        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel, board);
        SimulationViewModel simulationViewModel = new SimulationViewModel(boardViewModel, appViewModel,
                editorViewModel);
        eventBus.listenFor(SimulatorEvent.class, simulationViewModel::handle);

        appViewModel.getApplicationState().listen(editorViewModel::onAppStateChanged);

        boardViewModel.getBoard().set(board);

        SimulationCanvas simulationCanvas = new SimulationCanvas(editorViewModel, boardViewModel);
        Toolbar toolbar = new Toolbar(editorViewModel, eventBus);
        Infobar infobar = new Infobar(editorViewModel);

        MainView mainView = new MainView(editorViewModel);
        mainView.setTop(toolbar);
        mainView.setCenter(simulationCanvas);
        mainView.setBottom(infobar);

        Scene scene = new Scene(mainView, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}