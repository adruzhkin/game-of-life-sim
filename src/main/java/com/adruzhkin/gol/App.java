package com.adruzhkin.gol;

import com.adruzhkin.gol.model.Board;
import com.adruzhkin.gol.model.BoundedBoard;
import com.adruzhkin.gol.view.SimulationCanvas;
import com.adruzhkin.gol.viewmodel.ApplicationViewModel;
import com.adruzhkin.gol.viewmodel.BoardViewModel;
import com.adruzhkin.gol.viewmodel.EditorViewModel;
import com.adruzhkin.gol.viewmodel.SimulationViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        ApplicationViewModel appViewModel = new ApplicationViewModel();
        BoardViewModel boardViewModel = new BoardViewModel();
        Board board = new BoundedBoard(20, 12);
        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel, board);
        SimulationViewModel simulationViewModel = new SimulationViewModel(boardViewModel);

        appViewModel.getApplicationState().listen(editorViewModel::onAppStateChanged);
        appViewModel.getApplicationState().listen(simulationViewModel::onAppStateChanged);

        boardViewModel.setBoard(board);

        SimulationCanvas simulationCanvas = new SimulationCanvas(editorViewModel, boardViewModel);
        Toolbar toolbar = new Toolbar(editorViewModel, appViewModel, simulationViewModel);
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