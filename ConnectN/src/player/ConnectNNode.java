package player;

import java.util.List;

public class ConnectNNode {

	ConnectNNode state;
	int player;
	List<ConnectNNode> children;

	public ConnectNNode() {

	}

	public ConnectNNode getState() {
		return state;
	}

	public void setPlayer(int num){
		player = num;
	}
	public int getPlayer() {
		return player;
	}
	
	public List<ConnectNNode> getChildren(){
		return children;
	}
	
}
