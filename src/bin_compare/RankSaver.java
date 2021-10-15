package bin_compare;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RankSaver {
	
	LocalDateTime now = null;
	String file_name = null;

	public RankSaver() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuuMMdd_HHmmss");
		this.now = LocalDateTime.now();
		String save_name = dtf.format(now);
		this.file_name = "rnk/"+save_name;
	}

	public void saveRankingsAsText(ArrayList<String> names) {
		try {
			String save_name = this.file_name + ".txt";
			File save_file = new File (save_name);
			save_file.createNewFile();
			writeRankingsAsText(this.now, save_name, names);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeRankingsAsText(LocalDateTime time, String file_path, ArrayList<String> names) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
			FileWriter writer = new FileWriter(file_path);
			writer.write("Rankings saved at: " + dtf.format(time) + "\n");
			writer.write(names.size() + " Characters ranked\n");
			writer.write("\n");
			writer.write("Rankings:" + "\n");
			writer.write("\n");
			for (Integer i = 0; i < names.size(); i++) {
				writer.write((i+1) + ". " + names.get(i) + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveRankingsAsImage(Scene scene) {
		try {
			WritableImage img = scene.snapshot(null);
			String save_path = this.file_name + ".png";
			File file = new File(save_path);
			ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Shit");
		}
	}

	public void saveRankings(SceneBuilder builder, Stage stage, ArrayList<Image> imgs, ImageEventHandler img_handler) {
		Integer side = (int) Math.ceil(Math.sqrt(imgs.size()));
		Group root = new Group();
		Font font = new Font("Arial Bold", builder.img_size * 4 / 30);
		Text text = new Text("Rankings:");
		text.setFont(font);
		text.setX(builder.img_size/5);
		text.setY(builder.img_size * 3 / 20);
		root.getChildren().add(text);
		Integer i = 1;
		for (Image img: imgs) {
			Text rank = new Text(i.toString());
			rank.setFont(font);
			rank.setX(builder.img_size/20 + builder.img_size * ((i-1) % side));
			rank.setY((builder.img_size + builder.img_size/6) * ((i-1) / side) + builder.img_size * 19 / 60);
			ImageView img_view = builder.initImgView(builder.img_size, builder.img_size);
			img_view.setImage(img);
			img_view.setX(builder.img_size * ((i-1) % side));
			img_view.setY((builder.img_size + builder.img_size/6) * ((i-1) / side) + builder.img_size/3);
			root.getChildren().addAll(img_view, rank);
			i++;
		}
		Scene scene = new Scene(root);
		this.saveRankingsAsText(img_handler.getRankingsByName());
		this.saveRankingsAsImage(scene);
	}
}
