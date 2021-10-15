package bin_compare;

import javafx.scene.image.Image;

public class ImagePoolNode {
	Image img;
	String name;
	ImagePoolNode next;

	public ImagePoolNode(Image img, String name) {
		this.img = img;
		this.name = name;
		this.next = null;
	}

	public void attach(ImagePoolNode node) {
		this.next = node;
	}

	public String getName() {
		return this.name;
	}

	public Image getImage() {
		return this.img;
	}
}
