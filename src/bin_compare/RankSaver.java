package bin_compare;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
	Integer file_no;

	public RankSaver() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuuMMdd_HHmmss");
		this.now = LocalDateTime.now();
		String save_name = dtf.format(now);
		this.file_name = "rnk/"+save_name;
		this.file_no = 0;
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

	public void saveRankingsAsImage(SceneBuilder builder, Stage stage, List<Image> imgs, ImageEventHandler img_handler, Integer start, Scene scene) {
		try {
			WritableImage img = scene.snapshot(null);
			if (img.getHeight() * img.getWidth() * 4 > 0) {
				String save_path = this.file_name + "_" + this.file_no.toString() + ".png";
				File file = new File(save_path);
				System.out.println("Saving " + save_path + ":");
				ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", file);
				System.out.println(save_path + " saved");
			}
			else {
				System.out.println("Breaking ranking into subparts: ");
				Integer size = imgs.size();
				this.saveRankings(builder, stage, imgs.subList(start, start + size/2), img_handler, start);
				this.file_no ++;
				this.saveRankings(builder, stage, imgs.subList(start + size/2, start + size), img_handler, start + size/2);		
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException | NegativeArraySizeException e) {
			// TODO Auto-generated catch block
			System.out.println("Breaking ranking into subparts: ");
			Integer size = imgs.size();
			this.saveRankings(builder, stage, imgs.subList(start, start + size/2), img_handler, start);
			this.file_no ++;
			this.saveRankings(builder, stage, imgs.subList(start + size/2, start + size), img_handler, start + size/2);
		}
	}

	public void saveRankings(SceneBuilder builder, Stage stage, List<Image> imgs, ImageEventHandler img_handler, Integer start) {
		Integer side = (int) Math.ceil(Math.sqrt(imgs.size()));
		Group root = new Group();
		Font font = new Font("Arial Bold", builder.img_size * 4 / 30);
		Text text = new Text("Rankings:");
		text.setFont(font);
		text.setX(builder.img_size/5);
		text.setY(builder.img_size * 3 / 20);
		root.getChildren().add(text);
		Integer i = start + 1;
		for (Image img: imgs) {
			Text rank = new Text(i.toString());
			rank.setFont(font);
			rank.setX(builder.img_size/20 + builder.img_size * ((i-1-start) % side));
			rank.setY((builder.img_size + builder.img_size/6) * ((i-1-start) / side) + builder.img_size * 19 / 60);
			ImageView img_view = builder.initImgView(builder.img_size, builder.img_size);
			img_view.setImage(img);
			img_view.setX(builder.img_size * ((i-1-start) % side));
			img_view.setY((builder.img_size + builder.img_size/6) * ((i-1-start) / side) + builder.img_size/3);
			root.getChildren().addAll(img_view, rank);
			i++;
		}
		Scene scene = new Scene(root);
		this.saveRankingsAsImage(builder, stage, imgs, img_handler, start, scene);
	}
}
