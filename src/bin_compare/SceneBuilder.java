package bin_compare;

import java.io.File;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SceneBuilder {
	final Integer img_size;
	
	public SceneBuilder(Integer img_size) {
		this.img_size = img_size;
	}

	public SceneBuilder() {
		this.img_size = 250;
	}

	public ImageView initImgView(Integer x_pos, Integer y_pos) {
		ImageView img_view = new ImageView(new Image("Default.png"));
		img_view.setPreserveRatio(false);
		img_view.setX(x_pos);
		img_view.setY(y_pos);
		img_view.setFitWidth(this.img_size);
		img_view.setFitHeight(this.img_size);
		return img_view;
	}

	public void initSelectionScene(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		ImageLoader img_loader = new ImageLoader(new File("img"));
		ImagePool img_pool = img_loader.load();
		Ranker ranker = new Ranker(img_pool.size());
		//RankerNode ranker_node = new RankerNode(ranker.rankings.subList(0, ranker.rankings.size()));
		ImageView left = this.initImgView(0, 0);
		ImageView right = this.initImgView(this.img_size, 0);
		Group select_root = new Group();
		select_root.getChildren().addAll(left, right);
		Scene select_scene = new Scene(select_root);
		ImageEventHandler img_handler = new ImageEventHandler(left, right, ranker, stage, img_pool, this);
		left.addEventHandler(MouseEvent.MOUSE_CLICKED, img_handler);
		right.addEventHandler(MouseEvent.MOUSE_CLICKED, img_handler);
		stage.setResizable(false);
		stage.setWidth(2 * this.img_size);
		stage.setHeight(this.img_size);
		stage.centerOnScreen();
		stage.setScene(select_scene);
		stage.setTitle("Binary Ranker");
		stage.show();
	}

	public void initSaveRankScene(Stage stage) {
		Text text = new Text("Rankings Saved!");
		Font font = new Font("Arial Bold", this.img_size/8);
		text.setY(this.img_size * 7 / 16);
		text.setFont(font);
		text.setX(this.img_size/2);
		Group root = new Group();
		root.getChildren().add(text);
		Scene scene = new Scene(root);
		stage.close();
		stage.setScene(scene);
		stage.show();
	}

}
