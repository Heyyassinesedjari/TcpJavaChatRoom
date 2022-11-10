import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class WorkerChat implements Runnable{
	private Socket client;
	private int index;
	public static ArrayList<Socket> clients = new ArrayList<Socket>();
	public static ArrayList<PrintWriter> OUT = new ArrayList<PrintWriter>();
	public int m;
	public int p;
	
	//public WorkerChat(ServerChat serverChat,Socket client) {
	public WorkerChat(Socket client) {
		this.client=client;
		
		
	}
	
    public void run() {
    	int n,m,p;
    	try {   	
    		
	        Scanner in = new Scanner(client.getInputStream());
	        String clientName=in.nextLine();
	        WorkerChat.clients.add(client);
	        WorkerChat.OUT.add(new PrintWriter(client.getOutputStream(), true));
        	System.out.println("\n"+clientName+" vient de rejoindre le serveur!");
	    	m=WorkerChat.clients.size();
	    	p=WorkerChat.OUT.size();
        	for(int i=0;i<p;i++){
        		WorkerChat.OUT.get(i).println(clientName+" vient de rejoindre le serveur!");
        	}
        	boolean break_loop=false;
	        while(!break_loop) {
	        	try {
	        		//WorkerChat.OUT.add(new PrintWriter(client.getOutputStream(), true));
	        		String msg=in.nextLine();
	    	    	m=WorkerChat.clients.size();
	    	    	p=WorkerChat.OUT.size();
	        		//System.out.println("client size "+m+" OUT size "+p);
		        	//System.out.println("Le serveur vient de recevoir le message de "+clientName+"!");
		        	//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		        	for(int i=0;i<p;i++){
		        		//System.out.print(WorkerChat.OUT.get(i)+"---");
		        		if((!(msg.equals("exit"))) && (WorkerChat.clients.get(i)!=client)) {
		        			WorkerChat.OUT.get(i).println(clientName+": "+msg);
		        		}
		        		if(((msg.equals("exit"))) && (WorkerChat.clients.get(i)!=client)) {
		        			WorkerChat.OUT.get(i).println(clientName+" a quitte le chat.");
		        		}
		        	}
		        	for(int i=0;i<p;i++) {	
		        		if((msg.equals("exit")) && (WorkerChat.clients.get(i)==client)) {
		        			WorkerChat.OUT.get(i).println("You left the Chat.");
		        			System.out.println(clientName+" left the Chat.");
		        			break_loop=true;
		        			break;
		        		}
		        	}
		        	//System.out.println("?????????????????????????????????????????????????????????????");
		        	//System.out.println("Le serveur vient de distribuer le message de a tout les utlisateurs actifs!");
				} catch (NoSuchElementException e) {
					break_loop=true;
					break;
				}
	        }
	        m=WorkerChat.clients.size();
	        p=WorkerChat.OUT.size();
	        for(int i=0;i<m;i++) {
	        	if(WorkerChat.clients.get(i) == client) {
	        		WorkerChat.clients.remove(i);
	        		WorkerChat.OUT.get(i).close();
	        		WorkerChat.OUT.remove(i);
	        		break;
	        	}
	        }
	        in.close();
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	//System.out.println("Fin de l'execution du Thread");
    }
    
}
