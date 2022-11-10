
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientChat{

    private Socket client;
    private Scanner in;
    private PrintWriter out;
    private String serverIpAddress;
    private int serverPort;
    private String username;

    public ClientChat(String username) {
    	this.username=username;
        try {
        	serverPort=3000;
			serverIpAddress="127.0.0.1";
            client = new Socket(serverIpAddress, serverPort);
            in = new Scanner(client.getInputStream());
            out = new PrintWriter(client.getOutputStream(), true);
            out.println(username);
            //System.out.println("\nVous avez envoye votre nom d'utilisateur");
            Scanner sysIn = new Scanner(System.in);
            String message_recu=in.nextLine();
            if(message_recu!="") {
            	System.out.println("\nServeur: "+message_recu);
            }
            
            Thread clientWorker = new Thread(new ClientWorker(in,username));
            clientWorker.start();
            System.out.println("\nEntrer votre message ou tapez 'exit' pour sortir.\n");
            while(true) {
	            //System.out.println("\nEntrer votre message ou tapez 'exit' pour sortir.");
	            String message = sysIn.nextLine();
	            out.println(message);
	            //System.out.println("\nVous avez envoye votre message");	            
	            if(message.equals("exit")) {
	            	//clientWorker.interrupt();
	            	break;
	            }
           }
            //System.out.println("\nExited the Loop");
            //in.close();
            //System.out.println("\nin Closed");
            sysIn.close();
            //System.out.println("\nsysIn Closed");
            client.close();
            //System.out.println("\nclient socket Closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
    	Scanner sys_In = new Scanner(System.in);
    	System.out.println("\nEntrer votre Nom:\n");
    	String clientName = sys_In.nextLine();
        ClientChat clientChat = new ClientChat(clientName);  
        sys_In.close();
    }
}