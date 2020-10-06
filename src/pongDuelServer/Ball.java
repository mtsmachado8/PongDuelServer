package pongDuelServer;

public class Ball {
	boolean xPositive = true;
    boolean yPositive = true;
    double velocity= 1;
    double x = 3+5;//5 = block width
    double y;
    double frameWidth, frameHight;
    int t = 0;
    Block blockP1;
    Block blockP2;
    
    public Ball(double frameWidth, double frameHight,ActivePlayers clientesAtivos) {
        this.frameWidth = frameWidth;
        this.frameHight = frameHight;
        this.y = frameHight/2 -3;
        blockP1 = clientesAtivos.getClientes().get(0).getBlock();
        blockP2 = clientesAtivos.getClientes().get(1).getBlock();
    }
    
	public void moveBall(){
		playerCollider();
		if (xPositive) {
            this.x += velocity;
            if (this.x >= this.frameWidth -10) {
                xPositive = false;
            }
        } else {
            this.x -= velocity;
            if (this.x <= 0) {
                this.xPositive = true;
            }
        }
        if (yPositive) {
            this.y += velocity;
            if (this.y >= frameHight -10){
                yPositive = false;
            }
        } else {
            this.y -= velocity;
            if (this.y <= 2) {  
                yPositive = true;
            }
        }
        this.t += 1;
	}
	
	public void playerCollider() {
        if(this.x <= frameWidth/2){//do lado do P1
            if ((Math.abs(blockP1.x - this.x) <= 3) && (Math.abs(blockP1.y - this.y) <= blockP1.height/2 )) {
                if(!this.xPositive){
                    this.xPositive = true;
                }
            }
        }else{//do lado do P2
            if ((Math.abs(blockP2.x - this.x) <= 3) && (Math.abs(blockP2.y - this.y) <= blockP1.height/2 )) {
                if(this.xPositive){
                    this.xPositive = false;
                }
            }
        }
    }
	
	public int getX(){
		return (int)this.x;
	}
	
	public int getY(){
		return (int)this.y;
	}
	public int getT(){
		return this.t;
	}
}
