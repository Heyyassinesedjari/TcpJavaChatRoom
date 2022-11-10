import java.io.PrintWriter;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class ServerChat{
    private ServerSocket server = null;
    private Socket client;

    @SuppressWarnings("unchecked")
	public ServerChat() {
        
        try {
            server = new ServerSocket(3000);
            System.out.println("Demarrage du serveur Chat sur le port 3000");
            int i=1;
            int n,p,m,index,r;
	        while (true) {
	        	index=0;
	        	System.out.println("Attente d'une requete");
	        	client=server.accept();
	        	//System.out.println("Socket recu");
		        Thread client1 = new Thread(new WorkerChat(client),"Client "+i);
                client1.start();
                //System.out.println("Tache distribue au Thread, retour à lécoute");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ServerChat serverChat = new ServerChat();
    } 
}
