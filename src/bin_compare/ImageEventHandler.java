package bin_compare;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ImageEventHandler implements EventHandler<MouseEvent> {
	Ranker ranker;
	Stage stage;
	ImageView left;
	ImageView right;
	ImagePool img_pool;
	SceneBuilder scene_builder;

	public ImageEventHandler(ImageView left, ImageView right, Ranker ranker, Stage stage,
			ImagePool img_pool, SceneBuilder scene_builder) {
		this.left = left;
		this.right = right;
		this.ranker = ranker;
		this.stage = stage;
		this.img_pool = img_pool;
		this.scene_builder = scene_builder;
		RankerNode working_node = this.ranker.ranker_root.getWorkingNode();
		left.setImage(img_pool.getNode(working_node.left.getCurrRef()).img);
		right.setImage(img_pool.getNode(working_node.right.getCurrRef()).img);
	}

	@Override
	public void handle(MouseEvent event) {
		// TODO Auto-generated method stub
		ImageView source = (ImageView) event.getSource();
		Image img = source.getImage();
		if (!ranker.isRanked()) {
			ranker.pushRank(img_pool.getIndexByImage(img));
			if (ranker.isRanked()) {
				endSelection();
			} else {
				RankerNode working_node = this.ranker.ranker_root.getWorkingNode();
				left.setImage(img_pool.getNode(working_node.left.getCurrRef()).img);
				right.setImage(img_pool.getNode(working_node.right.getCurrRef()).img);
			}
		}
	}

	public void endSelection() {
		this.scene_builder.initSaveRankScene(stage);
		RankSaver saver = new RankSaver();
		saver.saveRankings(this.scene_builder, this.stage, this.getRankingsByImage(), this);
	}

	public ArrayList<Integer> getRankingsByInteger(){
		return this.ranker.getRankings();
	}

	public ArrayList<String> getRankingsByName(){
		if (this.ranker.isRanked()) {
			ArrayList<String> names = new ArrayList<String>();
			for (Integer i = 0; i < this.ranker.ranker_root.size(); i++) {
				String name = this.img_pool.getNode(this.ranker.getRankings().get(i)).name;
				names.add(name.substring(0, name.lastIndexOf(".")));
			}
			return names;
		}
		return null;
	}

	public ArrayList<Image> getRankingsByImage(){
		if (this.ranker.isRanked()) {
			ArrayList<Image> imgs = new ArrayList<Image>();
			for (Integer i = 0; i < this.ranker.ranker_root.size(); i++) {
				Image img = this.img_pool.getNode(this.ranker.getRankings().get(i)).img;
				imgs.add(img);
			}
			return imgs;
		}
		return null;
	}


}
