import java.net.Socket;
import java.io.*;

public class client {

      Socket socket ;

      BufferedReader br;
      PrintWriter out;
    public client(){
        try {
            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1",8080);
             
            System.out.println("Connection done.");

            br= new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e) {
            // TODO: handle exception
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
                System.out.println("Server terminated the chat");
                socket.close();
                break;
               }
                System.out.println("Server: " + msg);
            }
        } catch (Exception e) {

            //e.printStackTrace();//console per technicall error aata hai to  console per exception print karne ke liye
             System.out.println("Connection closed");
        }


        };
        new Thread(r1).start();
    }

    public void startWriting(){

        //thread - data user se lega and data send karega client tak
        Runnable r2 = () ->{

            System.out.println("Writer started...");
          while(true && !socket.isClosed()){
            try {
                
                BufferedReader br1 = new BufferedReader(new InputStreamReader((System.in)));

                String content =  br1.readLine();

                out.println(content);
                out.flush();
                if(content.equals("exit")){
                    socket.close();
                    break;
                }


            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
          }
          System.out.println("Connection closed");

        };
        new Thread(r2).start();
    }
    
    public static void main(String args[]){
        new client();
    }
}
