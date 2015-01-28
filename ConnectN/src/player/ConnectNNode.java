/**
 * Christopher Tibbetts & Kevin Mee
 */
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
	boolean terminal = false;

	String move;

	ConnectNNode bestChild;

	/**
	 * N - the N value of connect N player - the player number associated with
	 * this node nodeCanPop - if this node can still perform a pop move
	 * otherCanPop - if the other player can still perform a pop move
	 */
	public ConnectNNode(int N, int player, boolean nodeCanPop, boolean otherCanPop, Board board, String move) {
		this.gameBoard = board;
		this.weCanPop = nodeCanPop;
		this.theyCanPop = otherCanPop;
		this.N = N;
		this.player = player;

		this.move = move;

		this.terminal = gameBoard.isConnectN(this.N);
	}

	public int getPlayer() {
		return player;
	}

	public void setBestChild(ConnectNNode child) {
		this.bestChild = child;
	}

	/*
	 * returns the list of children from the current node
	 */
	public List<ConnectNNode> getChildren() {
		if (!children.isEmpty()) {
			return children;
		} else {
			// Populate all the children

			// Populate normal move children
			for (int i = 0; i < gameBoard.width; i++) {
				Board childBoard = gameBoard.clone();
				if (childBoard.canDropADiscFromTop(i, player)) {
					childBoard.dropADiscFromTop(i, player);
					children.add(new ConnectNNode(N, (player % 2) + 1, this.theyCanPop, this.weCanPop, childBoard, i + " 1"));
				}
			}

			if (this.weCanPop) {
				// Populate the pop move children
				for (int i = 0; i < gameBoard.width; i++) {
					Board childBoard = gameBoard.clone();
					if (childBoard.canRemoveADiscFromBottom(i, player)) {
						childBoard.removeADiscFromBottom(i);
						children.add(new ConnectNNode(N, (player % 2) + 1, this.theyCanPop, false, childBoard, i + " 0"));
					}
				}
			}

			return children;
		}
	}

	public int getValue() {
		int res = nInARowHeuristic();
		return res;
	}

	// starting heuristic functions
	// these need to keep in mind disjoint plays: XX_X should be counted as 3in
	// a row
	// if there is an empty space in between
	// dropping a peice that forms 2 rows of N-1 should get a higher value than
	// only 1 row

	private int nInARowHeuristic() {
		// if N-1 are in a row set value to a high number
		// check board rows for potential winners

		// Populate the list of playerConnections
		for (int i = 1; i <= N; i++) {
			gameBoard.checkHorizontally(i);
			gameBoard.checkVertically(i);
			gameBoard.checkDiagonally1(i);
			gameBoard.checkDiagonally2(i);
		}

		// adds weights to the heuristic
		int player1Val = 0;
		int player2Val = 0;

		for (int i = 0; i < gameBoard.player1Connections.length; i++) {
			player1Val += gameBoard.player1Connections[i] * (i + 1) * 10;
		}

		for (int i = 0; i < gameBoard.player2Connections.length; i++) {
			player2Val -= gameBoard.player2Connections[i] * (i + 1) * 10;
		}

		return player1Val + player2Val;
	}

	private int popOutHeuristic() {
		return 0;
		// if there is N-1 in a row below a peice which is in a location to drop
		// down for win
		// this should be a function that calls the rowHeuristic and then checks
		// to see if
		// if there is a chance to drop a column down by 1.
	}

	private int startingLocation() {
		return 0;
		// place peice in the N/2 location?
	}

	private int defenseHeuristic() {
		return 0;
		// checks to see that the other player cant win on next move
		// if it can, a high value should be assigned to that spot
	}

}
