package player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class ConnectN_player {
	
	final int DROP=1;
	final int POPOUT=0;
	final int TIE=0;

	int boardHeight;
	int boardWidth;
	int N;
	int playerNum;
	int timeLimit;
	ConnectNNode node;
	
	boolean canPopOut = true;

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
		} else if (ls.size() == 4) { // player1: aa player2: bb
			// TODO read in the player names
		} else if (ls.size() == 2) { // ls contains other player's move
			makeMove(Integer.parseInt(ls.get(0)), Integer.parseInt(ls.get(1)), this.playerNum == 1 ? 2 : 1, gameBoard);
		} else {
			System.out.println("not what I want");
		}
		
		// Decide on a new move
	}
	
	/**
	 * Place a disc from the top of the column or remove from bottom (see method comment)
	 * @param column
	 *    		The column in which the current move is going to make. 
	 * @param operation   
	 * 			Indicates dropping a disc by 1, poping out a disc by 0. 
	 */
	private void makeMove(int column, int operation, int player, Board board){
		if(operation==DROP)
			board.dropADiscFromTop(column, player);
		else{
			board.removeADiscFromBottom(column);
		}
		
	}

	public void readConfig(List<String> vals) throws IOException {
		this.boardHeight = Integer.parseInt(vals.get(0));
		this.boardWidth = Integer.parseInt(vals.get(1));
		this.N = Integer.parseInt(vals.get(2));
		this.playerNum = Integer.parseInt(vals.get(3));
		node.setPlayer(playerNum);
		this.timeLimit = Integer.parseInt(vals.get(4));
	}

	public void init() {
		this.gameBoard = new Board(this.boardHeight, this.boardWidth);

	}

	public static void main(String[] args) throws IOException {
		ConnectN_player rp = new ConnectN_player();
		System.out.println(rp.playerName);
		
		rp.init();

		while (true) {
			rp.processInput();
		}

	}

	private int miniMax(ConnectNNode currentNode, int depth, int alpha, int beta) {
		if (depth <= 0) {
			return getHeuristic(currentNode.getState());
		}

		// 0 is for our own player
		if (currentNode.getState().player == playerNum) {
			int currentAlpha = -9999999;
			for (ConnectNNode child : currentNode.getChildren()) {
				currentAlpha = Math.max(alpha, miniMax(child, depth - 1, alpha, beta));
				alpha = Math.max(alpha, currentAlpha);

				if (alpha >= beta) {
					return beta;
				}
			}
			return currentAlpha;
		}
		int currentBeta = 9999999;
		for (ConnectNNode child : currentNode.getChildren()) {
			currentBeta = Math.min(beta, miniMax(child, depth - 1, alpha, beta));
			beta = Math.min(beta, currentBeta);

			if (beta <= alpha) {
				return beta;
			}
		}
		return currentBeta;

	}

	private int getHeuristic(ConnectNNode state) {
		// TODO Auto-generated method stub
		return 0;
	}

}
