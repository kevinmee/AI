package player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class ConnectN_player {

	int boardHeight;
	int boardWidth;
	int N;
	int playerTurn;
	int timeLimit;
	
	String playerName = "This is our player name!";
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	boolean first_move = false;
	
	public void processInput() throws IOException{	
	
    	String s=input.readLine();	
		List<String> ls=Arrays.asList(s.split(" "));
		if(ls.size()==2){
			System.out.println(ls.get(0)+" "+ls.get(1));
		}
		else if(ls.size()==1){
			System.out.println("game over!!!");
			System.exit(0);
		}
		else if(ls.size()==5){          //ls contains game info
			System.out.println("0 1");  //first move
		}
		else if(ls.size()==4){		//player1: aa player2: bb
			//TODO combine this information with game information to decide who is the first player
		}
		else
			System.out.println("not what I want");
	}
	
	public void readConfig() throws IOException {
		String config = input.readLine();
		List<String> vals = Arrays.asList(config.split(" "));
		
		this.boardHeight = Integer.parseInt(vals.get(0));
		this.boardWidth = Integer.parseInt(vals.get(1));
		this.N = Integer.parseInt(vals.get(2));
		this.playerTurn = Integer.parseInt(vals.get(3));
		this.timeLimit = Integer.parseInt(vals.get(4));
	}
	
	public static void main(String[] args) throws IOException {
		ConnectN_player rp = new ConnectN_player();
		System.out.println(rp.playerName);
		
		rp.readConfig();
		
		while (true){
			rp.processInput();
		}

	}
	
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

	private int getHeuristic(ConnectNNode state) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
