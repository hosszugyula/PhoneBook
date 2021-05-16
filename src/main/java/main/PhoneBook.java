
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class representing the PhoneBook class.
 */
public class PhoneBook extends Application {

    /**
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/View.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Telefonkönyv");
        stage.setWidth(800);
        stage.setHeight(680);
        stage.setScene(scene);
        stage.show();
    }
}
