import java.lang.reflect.Constructor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 * @author Pawel Martyniuk
 * @version 1.0
 */
public class App extends Application {

  public void start(Stage primaryStage) throws Exception {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/ui.fxml"));
    IModel service = new ModelWithExternalService();
    loader.setControllerFactory((Class<?> type) -> {
      try {
        for (Constructor<?> c : type.getConstructors()) {
          if (c.getParameterCount() == 1) {
            if (c.getParameterTypes()[0]==IModel.class) {
              return c.newInstance(service);
            }
          }
        }
        return type.getDeclaredConstructor().newInstance();
      } catch (Exception exc) {
        throw new RuntimeException(exc);
      }
    });

    Pane window1 = loader.load();
    BorderPane window2 = new BorderPane(window1);
    Scene scene = new Scene(window2);
    scene.getStylesheets().add(getClass().getResource("/css/view1.css").toExternalForm());

    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.setWidth(340);
    primaryStage.setHeight(340);
    primaryStage.getIcons().add(new Image("/graphics/calculator_icon.png"));
    primaryStage.setTitle("Calculator");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
