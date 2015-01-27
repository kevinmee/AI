package player;

import java.util.ArrayList;
import java.util.List;

public class ConnectNNode {

	Board gameBoard;

	int player;
	int N;
	ArrayList<ConnectNNode> children = new ArrayList<ConnectNNode>();
	boolean weCanPop;
	boolean theyCanPop;

	/**
	 * N - the N value of connect N player - the player number associated with
	 * this node nodeCanPop - if this node can still perform a pop move
	 * otherCanPop - if the other player can still perform a pop move
	 */
	public ConnectNNode(int N, int player, boolean nodeCanPop, boolean otherCanPop, Board board) {
		this.gameBoard = board;
		this.weCanPop = nodeCanPop;
		this.theyCanPop = otherCanPop;
		this.N = N;
		this.player = player;
	}

	public int getPlayer() {
		return player;
	}

	/*
	 * returns the list of children from the current node
	 */
	public List<ConnectNNode> getChildren() {
		if (children.isEmpty()) {
			return children;
		} else {
			// Populate normal move children
			for (int i = 0; i < gameBoard.width; i++) {
				Board childBoard = new Board(gameBoard.height, gameBoard.width, gameBoard.board);

				children.add(new ConnectNNode(N, (player % 2) + 1, this.theyCanPop, this.weCanPop, childBoard));
			}

			if (this.weCanPop) {
				// Populate the pop move children
				for (int i = 0; i < gameBoard.width; i++) {
					Board childBoard = new Board(gameBoard.height, gameBoard.width, gameBoard.board);

					children.add(new ConnectNNode(N, (player % 2) + 1, this.theyCanPop, false, childBoard));
				}
			}

			return children;
		}
	}

}
