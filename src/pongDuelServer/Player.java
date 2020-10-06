/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pongDuelServer;

import java.io.*;

/**
 *
 * @author MateusM
 */
public class Player {
    String name;
    int playerNumber;
    PrintWriter outputStream;
    Block block;

    public Player(String name, int playerNumber, PrintWriter outputStream, Block block) {
        this.name = name;
        this.outputStream = outputStream;
        this.block = block;
        this.playerNumber = playerNumber;
    }

    public String getName() {
        return name;
    }
    
    public Block getBlock() {
        return block;
    }

    public PrintWriter getOutputStream() {
        return outputStream;
    }
}
