package bin_compare;

import java.util.ArrayList;
import java.util.List;

public class RankerNode {
	RankerNode left;
	RankerNode right;
	ArrayList<Integer> merged;
	Integer index;

	public RankerNode(List<Integer> orig){
		this.index = 0;
		this.merged = new ArrayList<Integer>();
		if (orig.size() == 0) {
			this.left = null;
			this.right = null;
			this.merged = null;
			this.index = null;
		} else if (orig.size() == 1) {
			this.left = null;
			this.right = null;
			this.merged.add(orig.get(0));
		} else {
			Integer sep = orig.size()/2;
			this.left = new RankerNode(orig.subList(0, sep));
			this.right = new RankerNode(orig.subList(sep , orig.size()));	
		}
			
	}

	public boolean isEmpty() {
		return (this.left == null && this.right == null && this.merged.size() == 0);
	}

	public boolean isLeaf() {
		return (this.left == null && this.right == null && this.merged.size() == 1);
	}

	public boolean isMerged() {
		return (this.merged.size() == this.size());
	}

	public RankerNode getWorkingNode() {
		if (this.isEmpty() || this.isLeaf()) {
			return null;
		}
		if (this.left.isMerged() && this.right.isMerged()) {
			return this;
		}
		if (this.left.isMerged()) {
			return this.right.getWorkingNode();
		}
		return this.left.getWorkingNode();
	}

	public Integer getCurrRef() {
		return this.merged.get(this.index);
	}

	public Integer size() {
		if (this.isEmpty()) {
			return 0; 
		}
		if (this.isLeaf()) {
			return 1;
		}
		return this.left.size() + this.right.size();
	}

	public void outValue() {
		if (this.isEmpty()) {
			return;
		}
		if (this.isLeaf()) {
			System.out.print(this.merged.get(0) + ", ");
		} else {
			this.left.outValue();
			this.right.outValue();
		}
	}

	public boolean contains(Integer value) {
		if (this.isEmpty() || !(this.merged.contains(value))) {
			return false;
		}
		return this.getCurrRef().equals(value);
	}

	public boolean isUsed() {
		return this.index.equals(this.size());
	}

	public List<Integer> getRemainingRefs() {
		return this.merged.subList(this.index, this.merged.size());
	}

	public void push(Integer value) {
		if (!(this.left.contains(value) || this.right.contains(value))) {
			return;
		}
		this.merged.add(value);
		if (this.left.contains(value)) {
			this.left.index += 1;
			if (this.left.isUsed()) {
				this.merged.addAll(this.right.getRemainingRefs());
				this.right.index = this.right.size();
			}
		} else {
			this.right.index += 1;
			if (this.right.isUsed()) {
				this.merged.addAll(this.left.getRemainingRefs());
				this.left.index = this.left.size();
			}
		}
	}
}
