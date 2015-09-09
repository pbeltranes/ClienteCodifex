package Cliente;
import static Cliente.Cliente.Mensajes;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.logging.*;
import Interfaz.*;
import Login.*;
 
public class Cliente extends Thread {
    protected Socket sk;
    protected static DataOutputStream dos;// CHAT
    protected static DataInputStream dis;// CHAT
    private int id;
    public static login L;
    public static interfaz I;
    public static Cliente P;
    public static String History;
    public static String Mensajes;
    public static String Cod;
    public static String Nombre;
        
    public Cliente(int id) {
        this.id = id;
    }
        public static void main(String[] args) {
       I = new interfaz();
       L = new login();
       P = new Cliente(1);
        L.setVisible(true);
        P.start();
    }
    
    public void run() {
        try {
            sk = new Socket("localhost",5000);
            dos = new DataOutputStream(sk.getOutputStream());
            dis = new DataInputStream(sk.getInputStream());
            History = "";
            Cod = "";
            Mensajes = "";
            Nombre = "";
            boolean Salida = false;
            while(!Salida){
                recibir();
                if(Mensajes == "Salida")
                    Salida = true;
            }

            dis.close();
            dos.close();
            sk.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
         private static void recibir() throws IOException{
         String n = dis.readUTF();
         try {
          if( n.charAt(0) == '®'){
              n  = n.substring(1,n.length());
              Cod = n;
              recibirCodigo();
          }
          else {
                Mensajes = n;
               recibirChat();}
                  } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
         
       public static void recibirChat() throws IOException{
		if (!Mensajes.equals("")){
			I.setRecibido(""+ Mensajes);
	       }
	}

         public static void enviarChat() throws IOException{
             
		P.dos.writeUTF(Mensajes);
		P.dos.flush();
		Mensajes="";
          }
         
          public static void recibirCodigo() throws IOException{
			I.setCodigo("" +Cod);
	}

         public static void enviarCodigo() throws IOException{
             
		P.dos.writeUTF("®" + Cod);
		P.dos.flush();
          }
    
    }


/*</thread></thread>*/