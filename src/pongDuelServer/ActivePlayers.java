package pongDuelServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ActivePlayers extends Thread {

    private List<Player> clientes;
    private ServerSocket server;
    private PlayersHandler currentClientHandler;

    public ActivePlayers(int porta) throws IOException {
        this.server = new ServerSocket(porta);
        System.out.println(this.getClass().getSimpleName() + " rodando na porta: " + server.getLocalPort());
        this.clientes = new ArrayList();
    }

    @Override
    public void run() {
        Socket socket = null;
        while (true) {
            try {
                socket = this.server.accept();
                PrintWriter outputStream = new PrintWriter(socket.getOutputStream());
                currentClientHandler = (new PlayersHandler(socket,outputStream, this));
                novoCliente(outputStream);
                currentClientHandler.start();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public synchronized void novoCliente(PrintWriter outputStream) throws IOException {
        Player player;
        Block block;
        if(clientes.size() == 0){
        	block = new Block(8,217,10,5,30,1);
        }else{
        	block = new Block(646,217,10,5,30,2);
        }
        player = new Player("Player " + (clientes.size() + 1),(clientes.size() + 1),outputStream,block);
        clientes.add(player);
        currentClientHandler.activePlayers();
    }

    public synchronized void removerCliente(PrintWriter outputStream) {
        clientes.remove(outputStream);
        outputStream.close();
    }

    public List<Player> getClientes() {
        return clientes;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.server.close();
    }


}
