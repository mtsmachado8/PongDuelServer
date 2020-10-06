package pongDuelServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class GameHandler extends Thread{
	Ball ball;
	private Socket socket;
    private PrintWriter printWriter;
    private ActivePlayers playersAtivos;
    private BufferedReader input;
    Block blockP1;
    Block blockP2;
    private static GameHandler singleton;
    
    public GameHandler(Socket socket, PrintWriter output, ActivePlayers clientesAtivos) throws IOException {
        this.socket = socket;
        this.playersAtivos = clientesAtivos;
        this.printWriter = output;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ball = new Ball(654,434,clientesAtivos); //frameWidth and frameHeight
        blockP1 = playersAtivos.getClientes().get(0).getBlock();
        blockP2 = playersAtivos.getClientes().get(1).getBlock();
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.singleton = this;
        
        Thread thread = new Thread(){
    		String message;
			public void run(){
				while(true){
					try {
	    				
						message = input.readLine();
		                
		                if(message.contains("BLOCK 1")){
		                	
		                	String d = (message.replaceAll("BLOCK 1;", ""));
		                	switch(d){
		                		case "b":
		                			blockP1.moveBlock(blockP1, direction.backward);
		                		break;
		                		case "f":
		                			blockP1.moveBlock(blockP1, direction.forward);
		                		break;
		                		case "r":
		                			blockP1.moveBlock(blockP1, direction.right);
		                		break;
		                		case "l":
		                			blockP1.moveBlock(blockP1, direction.left);
		                		break;
		                	}
		                }
		                if(message.contains("BLOCK 2")){
		                	
		                	String d = (message.replaceAll("BLOCK 2;", ""));
		                	switch(d){
		                		case "b":
		                			
		                			blockP2.moveBlock(blockP2, direction.backward);
		                		break;
		                		case "f":
		                			blockP2.moveBlock(blockP2, direction.forward);
		                		break;
		                		case "r":
		                			blockP2.moveBlock(blockP2, direction.right);
		                		break;
		                		case "l":
		                			blockP2.moveBlock(blockP2, direction.left);
		                		break;
		                	}
		                }
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
    		}
    	};
    	thread.start();
    }
    
    public static GameHandler getInstance(){
    	return singleton;
    }
    
    public void sendBlockLocation(String blockLocation){
    	List<Player> clientes = this.playersAtivos.getClientes();
    	PrintWriter printWriter = null;
    	 for (int i = 0; i<clientes.size();i++) {
             printWriter = (PrintWriter) clientes.get(i).getOutputStream();
             printWriter.println(blockLocation);
             printWriter.flush();
    	 }
    	
    }
    
    @Override
    public void run() {
    	while(true){
        	ball.moveBall();
        	int x = ball.getX();
        	int y = ball.getY();
        	 List<Player> clientes = this.playersAtivos.getClientes();
             PrintWriter printWriter = null;
             for (int i = 0; i<clientes.size();i++) {
                 printWriter = (PrintWriter) clientes.get(i).getOutputStream();
                 printWriter.println("Ball x: "+x+";y: "+y);
                 printWriter.flush();
             }
             try {
				this.sleep(7);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
	
}
