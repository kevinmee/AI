package player;

import java.util.List;

public class ConnectNNode {

	ConnectNNode state;
	int player;

	public ConnectNNode() {

	}

	public ConnectNNode getState() {
		return state;
	}

	public int getPlayer() {
		return player;
	}
	
	public List<ConnectNNode> getChildren(){
		return children;
	}

	/*
	 * ***********************************************
	 * ---- To be placed in the player file! -------
	 * ***********************************************
	 */
	private int miniMax(ConnectNNode currentNode, int depth, int alpha, int beta) {
		if (depth <= 0 ) {
			return getHeuristic(currentNode.getState());
		}

		// 0 is for our own player
		if (currentNode.getState().player == 0) {
			for (ConnectNNode child : currentNode.getChildren()) {
				alpha = Math.max(alpha, miniMax(child, depth - 1, alpha, beta));

				// this needs tweaking
				if (alpha >= beta) {
					return beta;
				}
			}
			return alpha;
		} else {
			for (ConnectNNode child : currentNode.getChildren()) {
				beta = Math.min(beta, miniMax(child, depth - 1, alpha, beta));

				// needs some tweaking
				if (alpha >= beta) {
					return alpha;
				}
			}
			return beta;

		}
	}
	

	
	
	
	
}
