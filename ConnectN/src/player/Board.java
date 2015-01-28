/**
 * This code is created for cs 4341 AI 2013a at WPI. All rights are reserved. 
 */
package player;

public class Board {

	int width;
	int height;
	int board[][];
	int numOfDiscsInColumn[];
	int emptyCell = 9;

	int PLAYER1 = 1;
	int PLAYER2 = 2;

	int player1Connections[];
	int player2Connections[];

	int NOCONNECTION = -1;
	int TIE = 0;

	Board(int height, int width, int N) {
		int[][] boardArray = new int[height][width];
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				boardArray[i][j] = this.emptyCell;
			}
		}
		
		makeBoard(height, width, N, boardArray);
	}

	Board(int height, int width, int N, int[][] boardArray) {
		makeBoard(height, width, N, boardArray);
	}
	
	public void makeBoard(int height, int width, int N, int[][] boardArray) {
		numOfDiscsInColumn = new int[width];
		
		this.board = boardArray;
		player1Connections = new int[N];
		for (int i = 0; i < player1Connections.length; i++)
			player1Connections[i] = 0;
		player2Connections = new int[N];
		for (int i = 0; i < player2Connections.length; i++)
			player2Connections[i] = 0;

		this.width = width;
		this.height = height;
	}
	
	 public void printBoard(){
		 System.out.println("Board: ");
		 for(int i=0;i<height;i++){
				for(int j=0;j<width;j++){
					System.out.print(board[i][j]+" ");
				}
				System.out.println();
		 }
	 }

	public boolean canRemoveADiscFromBottom(int col, int currentPlayer) {
		if (col < 0 || col >= this.width) {
			return false;
		} else if (board[height - 1][col] != currentPlayer) {
			return false;
		} else
			return true;
	}

	public void removeADiscFromBottom(int col) {
		int i;
		for (i = height - 1; i > height - this.numOfDiscsInColumn[col]; i--) {
			board[i][col] = board[i - 1][col];
		}
		board[i][col] = this.emptyCell;
		this.numOfDiscsInColumn[col]--;
	}

	public boolean canDropADiscFromTop(int col, int currentPlayer) {
		if (col < 0 || col >= this.width) {
			return false;
		} else if (this.numOfDiscsInColumn[col] == this.height) {
			return false;
		} else
			return true;
	}

	public void dropADiscFromTop(int col, int currentplayer) {
		int firstEmptyCellRow = height - this.numOfDiscsInColumn[col] - 1;
		board[firstEmptyCellRow][col] = currentplayer;
		this.numOfDiscsInColumn[col]++;
	}

	public boolean isConnectN(int N) {
		checkHorizontally(N);
		if (player1Connections[N - 1] > 0 || player2Connections[N - 1] > 0)
			return true;

		checkVertically(N);
		if (player1Connections[N - 1] > 0 || player2Connections[N - 1] > 0)
			return true;

		checkDiagonally1(N);
		if (player1Connections[N - 1] > 0 || player2Connections[N - 1] > 0)
			return true;

		checkDiagonally2(N);
		if (player1Connections[N - 1] > 0 || player2Connections[N - 1] > 0)
			return true;

		return false;

	}

	public void checkHorizontally(int N) {
		int max1 = 0;
		int max2 = 0;
		int player1_win = 0;
		int player2_win = 0;
		// check each row, horizontally
		for (int i = 0; i < this.height; i++) {
			max1 = 0;
			max2 = 0;
			for (int j = 0; j < this.width; j++) {
				if (board[i][j] == PLAYER1) {
					max1++;
					max2 = 0;
					if (max1 == N)
						player1_win++;
				} else if (board[i][j] == PLAYER2) {
					max1 = 0;
					max2++;
					if (max2 == N)
						player2_win++;
				} else {
					max1 = 0;
					max2 = 0;
				}
			}
		}

		player1Connections[N - 1] += player1_win;
		player2Connections[N - 1] += player2_win;
	}

	public void checkVertically(int N) {
		// check each column, vertically
		int max1 = 0;
		int max2 = 0;
		int player1_win = 0;
		int player2_win = 0;

		for (int j = 0; j < this.width; j++) {
			max1 = 0;
			max2 = 0;
			for (int i = 0; i < this.height; i++) {
				if (board[i][j] == PLAYER1) {
					max1++;
					max2 = 0;
					if (max1 == N)
						player1_win++;
				} else if (board[i][j] == PLAYER2) {
					max1 = 0;
					max2++;
					if (max2 == N)
						player2_win++;
				} else {
					max1 = 0;
					max2 = 0;
				}
			}
		}

		player1Connections[N - 1] += player1_win;
		player2Connections[N - 1] += player2_win;
	}

	public void checkDiagonally1(int N) {
		// check diagonally y=-x+k
		int max1 = 0;
		int max2 = 0;
		int player1_win = 0;
		int player2_win = 0;
		int upper_bound = height - 1 + width - 1 - (N - 1);

		for (int k = N - 1; k <= upper_bound; k++) {
			max1 = 0;
			max2 = 0;
			int x, y;
			if (k < width)
				x = k;
			else
				x = width - 1;
			y = -x + k;

			while (x >= 0 && y < height) {
				// System.out.println("k: "+k+", x: "+x+", y: "+y);
				if (board[height - 1 - y][x] == PLAYER1) {
					max1++;
					max2 = 0;
					if (max1 == N)
						player1_win++;
				} else if (board[height - 1 - y][x] == PLAYER2) {
					max1 = 0;
					max2++;
					if (max2 == N)
						player2_win++;
				} else {
					max1 = 0;
					max2 = 0;
				}
				x--;
				y++;
			}

		}
		player1Connections[N - 1] += player1_win;
		player2Connections[N - 1] += player2_win;
	}

	public void checkDiagonally2(int N) {
		// check diagonally y=x-k
		int max1 = 0;
		int max2 = 0;
		int player1_win = 0;
		int player2_win = 0;
		int upper_bound = width - 1 - (N - 1);
		int lower_bound = -(height - 1 - (N - 1));
		// System.out.println("lower: "+lower_bound+", upper_bound: "+upper_bound);
		for (int k = lower_bound; k <= upper_bound; k++) {
			max1 = 0;
			max2 = 0;
			int x, y;
			if (k >= 0)
				x = k;
			else
				x = 0;
			y = x - k;
			while (x >= 0 && x < width && y < height) {
				// System.out.println("k: "+k+", x: "+x+", y: "+y);
				if (board[height - 1 - y][x] == PLAYER1) {
					max1++;
					max2 = 0;
					if (max1 == N)
						player1_win++;
				} else if (board[height - 1 - y][x] == PLAYER2) {
					max1 = 0;
					max2++;
					if (max2 == N)
						player2_win++;
				} else {
					max1 = 0;
					max2 = 0;
				}
				x++;
				y++;
			}

		} // end for y=x-k

		player1Connections[N - 1] += player1_win;
		player2Connections[N - 1] += player2_win;
	}

	public boolean isFull() {
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				if (board[i][j] == this.emptyCell)
					return false;
			}
		return true;
	}

	public void setBoard(int row, int col, int player) {
		if (row >= height || col >= width)
			throw new IllegalArgumentException("The row or column number is out of bound!");
		if (player != this.PLAYER1 && player != this.PLAYER2)
			throw new IllegalArgumentException("Wrong player!");
		this.board[row][col] = player;
	}
}
