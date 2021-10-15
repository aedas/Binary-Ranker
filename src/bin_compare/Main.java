package bin_compare;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		SceneBuilder scene_builder = new SceneBuilder(400);
		scene_builder.initSelectionScene(stage);
	}

}
