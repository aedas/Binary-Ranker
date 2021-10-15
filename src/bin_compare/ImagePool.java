package bin_compare;

import javafx.scene.image.Image;

public class ImagePool {
	ImagePoolNode first;

	public ImagePool() {
		this.first = null;
	}

	public boolean isEmpty() {
		return this.first == null;
	}

	public ImagePoolNode getLastNode() {
		if (this.isEmpty()) {
			return null;
		}
		ImagePoolNode curr = this.first;
		while (curr.next != null) {
			curr = curr.next;
		}
		return curr;
	}

	public void add(ImagePoolNode node) {
		if (this.isEmpty()) {
			this.first = node;
		} else {
			this.getLastNode().next = node;
		}
	}

	public Integer size() {
		if (this.isEmpty()) {
			return 0;
		}
		Integer size = 1;
		ImagePoolNode curr = this.first;
		while (curr.next != null) {
			curr = curr.next;
			size += 1;
		}
		return size;
	}

	public ImagePoolNode getNode(Integer index) {
		if (index >= this.size()) {
			return null;
		}
		ImagePoolNode curr = this.first;
		for (Integer i = 0; i < index; i++) {
			curr = curr.next;
		}
		return curr;
	}

	public Integer getIndexByImage(Image img) {
		if (this.isEmpty()) {
			return null;
		}
		ImagePoolNode curr = this.first;
		Integer i = 0;
		while (curr != null) {
			if (img.equals(curr.img)) {
				return i;
			}
			i += 1;
			curr = curr.next;
		}
		return null;
	}

	public Integer getIndexByName(String name) {
		if (this.isEmpty()) {
			return null;
		}
		ImagePoolNode curr = this.first;
		Integer i = 0;
		while (curr != null) {
			if (name.equals(curr.name)) {
				return i;
			}
			i += 1;
			curr = curr.next;
		}
		return null;
	}
}
