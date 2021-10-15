package bin_compare;

import java.io.File;

import javafx.scene.image.Image;

public class ImageLoader {
	File parent;
	
	public ImageLoader(File parent) {
		this.parent = parent;
	}

	public ImagePool load() {
		String[] names = parent.list();
		ImagePool img_pool = new ImagePool();
		ImagePoolNode prev = new ImagePoolNode(new Image(names[0]), names[0]);
		for (String name:names) {
			ImagePoolNode curr = new ImagePoolNode(new Image(name), name);
			if (img_pool.isEmpty()) {
				img_pool.add(curr);
			} else {
				prev.attach(curr);
			}
			prev = curr;
		}
		return img_pool;
	}
}
