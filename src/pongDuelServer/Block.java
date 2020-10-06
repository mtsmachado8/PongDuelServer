package pongDuelServer;


public class Block {
	int x, y, width, height, velocity;
    boolean outOfBound = false;
    int playerNumber = 0;

    public Block(int x, int y, int velocity, int width, int height,int playerNumber) {
        this.playerNumber = playerNumber;
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.width = width;
        this.height = height;
    }
    
    public boolean willBeOutOfBoundaries(double intentX,double intentY) {
        double frameWidth = 654;
        double framHeight = 434;
        if(playerNumber == 2){
            if ((intentX < frameWidth/ 2) || (intentY >= (framHeight - (this.height/2 -5))) || intentY <= 5 || (intentX >= frameWidth)) {
            outOfBound = true;
            } else {
                outOfBound = false;
            }
        }else{
            if ((intentX > frameWidth/ 2) || (intentY >= (framHeight - (this.height/2 -5))) || intentY <= 5 || (intentX <= 0)) {
            outOfBound = true;
            } else {
                outOfBound = false;
            }
        }
        
        return outOfBound;
    }
    
    public int getPlayerNumber(){
    	return playerNumber;
    }
    
    public void moveBlock(Block block, direction d) {
        int intentX = this.x;
        int intentY = this.y;
        if (d == direction.forward) {
            intentY = this.y - velocity;
        }
        if (d == direction.backward) {
            intentY = this.y + velocity;
        }
        if (d == direction.right) {
            intentX = this.x + velocity;
        }
        if (d == direction.left) {
            intentX = this.x - velocity;
        }
        if(!willBeOutOfBoundaries(intentX,intentY)){
            this.x = intentX;
            this.y = intentY;
            GameHandler.getInstance().sendBlockLocation("BLOCK: "+block.getPlayerNumber()+";x: "+this.x+";y: "+this.y);
        }
    }
}
