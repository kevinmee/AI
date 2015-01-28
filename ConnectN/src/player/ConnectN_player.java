package player;

import java.io.BufferedReader;
import java.io.FileWriter;
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
	int firstPlayer;
	int timeLimit;
	ConnectNNode starterNode;

	boolean canPop = true;
	boolean otherCanPop = true;

	String playerName = "player";
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	static FileWriter logger;

	Board gameBoard;

	public void processInput() throws IOException {
		String s = input.readLine();
		List<String> ls = Arrays.asList(s.split(" "));
		logger.write("Incoming Message: " + s + "\n");
		logger.flush();

		// First digit is column number, or win/lose/tie state
		if (ls.size() == 1) {
			System.exit(0);
		} else if (ls.size() == 5) { // ls contains game info
			readConfig(ls);

			this.init();
			
			if (playerNum == firstPlayer) {
				takeTurn();
			}
			
			logger.write("init worked");
			logger.flush();
		} else if (ls.size() == 4) { // player1: aa player2: bb
			if (playerName.equals(ls.get(1))) {
				// I am player 1
				playerNum = 1;
			} else if (playerName.equals(ls.get(3))) {
				// I am player 2
				playerNum = 2;
			} else {
				// Something is wrong
				System.out.println("I am inturupting this game");
			}
			
		} else if (ls.size() == 2) { // ls contains other player's move
			makeMove(Integer.parseInt(ls.get(0)), Integer.parseInt(ls.get(1)), this.playerNum == 1 ? 2 : 1, gameBoard);

			takeTurn();
		} else {
			logger.write("not what I want: " + s + "\n");
			logger.flush();
			System.out.println("not what I want");
		}
		
		logger.write("Finished with the input: " + s + "\n");
		logger.flush();
	}
	
	private void takeTurn() throws IOException {
		starterNode = new ConnectNNode(N, playerNum, canPop, otherCanPop, gameBoard, "");
		// Decide on a new move
		
		logger.write("Starting Minimax Code" + "\n");
		logger.flush();
		miniMax(starterNode, boardHeight, Integer.MIN_VALUE, Integer.MAX_VALUE);

		logger.write("best move is |" + starterNode.bestChild.move + "|" + "\n");
		logger.flush();
		

		List<String> moveList = Arrays.asList(starterNode.bestChild.move.split(" "));
		
		makeMove(Integer.parseInt(moveList.get(0)), Integer.parseInt(moveList.get(1)), this.playerNum == 1 ? 1 : 2, gameBoard);
		System.out.println(starterNode.bestChild.move);
	}

	private void makeMove(int column, int operation, int player, Board board) {
		if (operation == DROP)
			board.dropADiscFromTop(column, player);
		else {
			board.removeADiscFromBottom(column);
		}

	}

	public void readConfig(List<String> vals) throws IOException {
		logger.write("Config Recieved - length: " + vals.size() + "\n");
		logger.flush();
		this.boardHeight = Integer.parseInt(vals.get(0));
		this.boardWidth = Integer.parseInt(vals.get(1));
		this.N = Integer.parseInt(vals.get(2));
		this.firstPlayer = Integer.parseInt(vals.get(3));
		this.timeLimit = Integer.parseInt(vals.get(4));

		logger.write("Config Processed\n");
		logger.flush();
	}

	public void init() throws IOException {
		logger.write("Init Started\n");
		logger.flush();
		this.gameBoard = new Board(this.boardHeight, this.boardWidth, N);
		logger.write("Init Ended\n");
		logger.flush();
	}

	public static void main(String[] args) throws IOException {
		logger = new FileWriter("out.txt");
		logger.write("Initialized" + "\n");
		logger.flush();
		
		ConnectN_player rp = new ConnectN_player();
		System.out.println(rp.playerName);
		logger.write("Player name is " + rp.playerName + "\n");
		logger.flush();

		while (true) {
			rp.processInput();
		}
		
//		ConnectNNode starterNode = new ConnectNNode(4, 2, true, true, new Board(10, 10, 4), "");
//		
//		ConnectN_player rp = new ConnectN_player();
//		rp.playerNum = 1;
//		
//		rp.miniMax(starterNode, 4, Integer.MIN_VALUE, Integer.MAX_VALUE);
//
//		System.out.println(starterNode.bestChild.move);
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

				if (alpha <= beta) {
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

				if (beta >= alpha) {
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
