import java.net.*;
import java.io.*;



 public class server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

//constructor
    public server(){
        try {
            server = new ServerSocket(8080);
            
            System.out.println("server is ready to accept connection");
            System.out.println("waiting....");
            socket = server.accept();
            
            br= new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e) {
            // TODO: handle exception

            e.printStackTrace();
        }
       
    }

    public void startReading(){
        // thread - read data 

        Runnable r1 = ()->{
         System.out.println("reader started...");
         try {
            while (true) {
                String msg = br.readLine();
               if(msg.equals("exit")){
                System.out.println("Client terminated the chat");
                    
               socket.close();
                break;
               }
                System.out.println("Client: " + msg);
            }
        } catch (Exception e) {

           // e.printStackTrace();//console per technicall error aata hai to  console per exception print karne ke liye
             System.out.println("Connection closed");
        }


        };
        new Thread(r1).start();
    }

    public void startWriting(){

        //thread - data user se lega and data send karega client tak
        Runnable r2 = () ->{

            System.out.println("Writer started...");
            try {
          while(true && !socket.isClosed()){
           
                
                BufferedReader br1 = new BufferedReader(new InputStreamReader((System.in)));

                String content =  br1.readLine();
                

                out.println(content);
                out.flush();
                if(content.equals("exit")){
                    socket.close();
                    break;
                }
          
          }
        } catch (Exception e) {
            // TODO: handle exception
            // e.printStackTrace();
            System.out.println("Connection closed");

        }
        // System.out.println("Connection closed");

        };
        new Thread(r2).start();
    }
    
    public static void main(String args[]){
        
        new server();
    }
}