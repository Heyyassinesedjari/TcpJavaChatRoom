import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ClientWorker implements Runnable{
	private Scanner in;
	private String clientName;
	
	public ClientWorker(Scanner in, String clientName) {
		this.in=in;
		this.clientName = clientName; 
	}
	
	
    public void run() {
    	try { 
    		boolean break_loop=false;
    		while(!break_loop) {
    			try {
		    		String message_recu="NOTHING";
		            while(in.hasNextLine() && !((message_recu=in.nextLine()).equals(""))) {
		            	System.out.println(message_recu);
		            	//System.out.println("message_recu.equals('You left the Chat.') "+message_recu);
		            	if(message_recu.equals("You left the Chat.")) {
		            		in.close();
		            		break_loop=true;
			            	break;
			            }
		            }
		            System.out.println("message_recu.equals('You left the Chat.') "+message_recu);
		            System.out.println("(in.hasNextLine() && !((message_recu=in.nextLine()).equals(\"\"))) "+(in.hasNextLine())+" "+ !((message_recu=in.nextLine()).equals("")));
    			}catch (Exception e) {
    				in.close();
    				break_loop=true;
					break;
				}
    			//System.out.println("ThreadClientWorker");
            }
    	}catch (Exception e) {
    		e.printStackTrace();
			in.close();
		}
    }
    
}
