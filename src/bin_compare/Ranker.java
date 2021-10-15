package bin_compare;

import java.util.ArrayList;
import java.util.Collections;

public class Ranker {
	RankerNode ranker_root;

	public Ranker(Integer size) {
		ArrayList<Integer> rankings = new ArrayList<Integer>();
		for (Integer i = 0; i < size; i++) {
			rankings.add(i);
		}
		Collections.shuffle(rankings);
		this.ranker_root = new RankerNode(rankings);
	}

	public void pushRank(Integer value) {
		RankerNode working_node = this.ranker_root.getWorkingNode();
		working_node.push(value);
	}

	public boolean isRanked() {
		return this.ranker_root.isMerged();
	}

	public ArrayList<Integer> getRankings(){
		if (this.isRanked()) {
			return this.ranker_root.merged;
		}
		return null;
	}

}
