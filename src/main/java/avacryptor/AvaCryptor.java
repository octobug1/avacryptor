package avacryptor;

import avacryptor.gui.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AvaCryptor extends Application {

    @Override
    public void start(Stage stage) {

        MainWindow window = new MainWindow();

        Scene scene = new Scene(window.getRoot(), 700, 500);

        scene.getStylesheets().add(
                getClass().getResource("/styles.css").toExternalForm()
        );

        stage.setTitle("AvaCryptor");
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}