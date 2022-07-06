      /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author suneater
 */
import java.net.*;//realizar coneccion a traves de la red
import javax.swing.JOptionPane;//ventanas de dialogo
public class ServerUDP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try
        {
            System.out.println("\n*****Arrancando el servidor*****\n");
            int Puerto = 5001;
            DatagramSocket Coneccion = new DatagramSocket(Puerto);//permite instancias de datagramas recibidos
            //especificando la cadena de byte y la longuitud del sms
            String Cerrar = "cierre";
            
            while(true)
            {
                byte[]Cadenarecivida = new byte[1024];
                byte[] CadenaEnviar  = new  byte[1024];
                DatagramPacket PacketRecivido = new DatagramPacket(Cadenarecivida,Cadenarecivida.length);
                Coneccion.receive(PacketRecivido);
                
                String CadenaServer = new String(PacketRecivido.getData());
                CadenaEnviar = CadenaServer.getBytes();
                
               InetAddress Ip = PacketRecivido.getAddress();
               int puerto = PacketRecivido.getPort();
               
               System.out.println("Enviando a: " + PacketRecivido.getAddress());
               
               DatagramPacket enviarpaqket = new DatagramPacket(CadenaEnviar,CadenaEnviar.length,
               Ip,puerto);
               String Senviada = new String(PacketRecivido.getData());
               Coneccion.send(enviarpaqket);
               
              
               if(CadenaServer.indexOf("cierre") <0 )
               {
                   System.out.println("Enviando ... "+Senviada.toString());
                   
                }
               else
               {
                   
                   
                   
                    System.out.println("Se detecto cadena vacia, bye");
                    Coneccion.close();
                    System.out.println("El servidor a cerrado sesion\n");
                    break;
               }
               

            }
        }
        catch(Exception e)
        {
            System.out.println("Ocurrio un error: "+e.getMessage());
        }
        System.out.println("Adios cliente");
    }
    
}
