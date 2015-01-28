package player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class ConnectN_player {

	final int DROP = 1;
	final int POPOUT = 0;
	final int TIE = 0;

	int boardHeight;
	int boardWidth;
	int N;
	int playerNum;
	int timeLimit;
	ConnectNNode starterNode;

	boolean canPop = true;
	boolean otherCanPop = true;

	String playerName = "This is our player name!";
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	Board gameBoard;

	public void processInput() throws IOException {
		String s = input.readLine();
		List<String> ls = Arrays.asList(s.split(" "));

		// First digit is column number, or win/lose/tie state
		if (ls.size() == 1) {
			System.exit(0);
		} else if (ls.size() == 5) { // ls contains game info
			readConfig(ls);

			this.init();
		} else if (ls.size() == 4) { // player1: aa player2: bb
			// TODO read in the player names
		} else if (ls.size() == 2) { // ls contains other player's move
			makeMove(Integer.parseInt(ls.get(0)), Integer.parseInt(ls.get(1)), this.playerNum == 1 ? 2 : 1, gameBoard);

			starterNode = new ConnectNNode(N, playerNum, canPop, otherCanPop, gameBoard, "");
			// Decide on a new move
			miniMax(starterNode, boardHeight, Integer.MIN_VALUE, Integer.MAX_VALUE);

			System.out.println(starterNode.bestChild.move);
		} else {
			System.out.println("not what I want");
		}

	}

	private void makeMove(int column, int operation, int player, Board board) {
		if (operation == DROP)
			board.dropADiscFromTop(column, player);
		else {
			board.removeADiscFromBottom(column);
		}

	}

	public void readConfig(List<String> vals) throws IOException {
		this.boardHeight = Integer.parseInt(vals.get(0));
		this.boardWidth = Integer.parseInt(vals.get(1));
		this.N = Integer.parseInt(vals.get(2));
		this.playerNum = Integer.parseInt(vals.get(3));
		this.timeLimit = Integer.parseInt(vals.get(4));
	}

	public void init() {
		this.gameBoard = new Board(this.boardHeight, this.boardWidth, N);
	}

	public static void main(String[] args) throws IOException {
		ConnectN_player rp = new ConnectN_player();
		System.out.println(rp.playerName);

		while (true) {
			rp.processInput();
		}

	}

	// function minimax(node, depth, maximizingPlayer)
	// if depth = 0 or node is a terminal node
	// return the heuristic value of node
	// if maximizingPlayer
	// bestValue := minInt
	// for each child of node
	// val := minimax(child, depth - 1, FALSE)
	// bestValue := max(bestValue, val)
	// return bestValue
	// else
	// bestValue := maxInt
	// for each child of node
	// val := minimax(child, depth - 1, TRUE)
	// bestValue := min(bestValue, val)
	// return bestValue

	private int miniMax(ConnectNNode currentNode, int depth, int alpha, int beta) {
		if (depth <= 0 || currentNode.terminal) {
			return getHeuristic(currentNode);
		}

		if (currentNode.getPlayer() == playerNum) {
			int currentAlpha = Integer.MIN_VALUE;
			for (ConnectNNode child : currentNode.getChildren()) {
				currentAlpha = Math.max(alpha, miniMax(child, depth - 1, alpha, beta));
				alpha = Math.max(alpha, currentAlpha);

				if (alpha >= beta) {
					currentNode.setBestChild(child);
					return beta;
				}
			}
			return currentAlpha;
		} else {
			int currentBeta = Integer.MAX_VALUE;
			for (ConnectNNode child : currentNode.getChildren()) {
				currentBeta = Math.min(beta, miniMax(child, depth - 1, alpha, beta));
				beta = Math.min(beta, currentBeta);

				if (beta <= alpha) {
					currentNode.setBestChild(child);
					return beta;
				}
			}
			return currentBeta;
		}

	}

	private int getHeuristic(ConnectNNode node) {
		return node.getValue();
	}

}
