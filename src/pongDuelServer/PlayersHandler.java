package pongDuelServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

public class PlayersHandler extends Thread {

    private Socket socket;
    private PrintWriter printWriter;
    private ActivePlayers playersAtivos;
    private BufferedReader input;

    public PlayersHandler(Socket socket, PrintWriter output, ActivePlayers clientesAtivos) throws IOException {
        this.socket = socket;
        this.playersAtivos = clientesAtivos;
        this.printWriter = output;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    protected void finalize() throws Throwable {
        encerrar();
    }

    private void encerrar() throws Exception {
        this.input.close();
        this.playersAtivos.removerCliente(this.printWriter);
        this.socket.close();
    }
    
    public void activePlayers() throws IOException {
        List<Player> clientes = this.playersAtivos.getClientes();
        PrintWriter printWriter = null;
        for (int i = 0; i<clientes.size();i++) {
            printWriter = (PrintWriter) clientes.get(i).getOutputStream();
            printWriter.println("CLEAR ACTIVE");
            for (int c = 0; c<clientes.size();c++) {
                printWriter.println("ACTIVE PLAYER:"+clientes.get(c).getName());
                printWriter.flush();
            }
        }
    }

    public synchronized void messageDispatcher(String message) throws IOException {
        List<Player> clientes = this.playersAtivos.getClientes();
        PrintWriter printWriter = null;
        for (int i = 0; i<clientes.size();i++) {
            printWriter = (PrintWriter) clientes.get(i).getOutputStream();
            printWriter.println("Player "+(i+1)+": "+message);
            printWriter.flush();
        }
    }

    @Override
    public void run() {
        String message = " ";
        while (message != "ENCERRAR") {
            try {
                message = this.input.readLine();
                if((message.equals("START GAME")) && (playersAtivos.getClientes().size() == 2)){
                	playersAtivos.getClientes().get(0).getOutputStream().println("YOUARE: "+1);
                	printWriter.flush();
                	playersAtivos.getClientes().get(1).getOutputStream().println("YOUARE: "+2);
                	printWriter.flush();
                	try {
    					GameHandler gameHandler = new GameHandler(this.socket, this.printWriter, this.playersAtivos);
    					gameHandler.start();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                }else{
                	if ((message != null) && (message.contains("BLOCK ") == false)) {
                        messageDispatcher(message);
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
              
        }
        
        try {
            encerrar();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } 
            
    }
}
