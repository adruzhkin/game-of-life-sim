package com.adruzhkin.gol;

import com.adruzhkin.gol.model.Board;
import com.adruzhkin.gol.model.BoundedBoard;
import com.adruzhkin.gol.viewmodel.ApplicationState;
import com.adruzhkin.gol.viewmodel.ApplicationViewModel;
import com.adruzhkin.gol.viewmodel.BoardViewModel;
import com.adruzhkin.gol.viewmodel.EditorViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        ApplicationViewModel appViewModel = new ApplicationViewModel(ApplicationState.EDITING);
        BoardViewModel boardViewModel = new BoardViewModel();
        Board board = new BoundedBoard(10, 10);
        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel, board);

        appViewModel.listenToAppState(editorViewModel::onAppStateChanged);

        MainView mainView = new MainView(appViewModel, boardViewModel, editorViewModel);
        Scene scene = new Scene(mainView, 640, 480);
        stage.setScene(scene);
        stage.show();

        boardViewModel.setBoard(board);
    }

    public static void main(String[] args) {
        launch();
    }

}