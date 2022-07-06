/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author suneater
 */
import javax.swing.*;//interfaz graficas
import java.net.*;//realizar coneccion a traves de la red
import java.io.*;//gestionar operaciones de entrada y salida
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("\tArrancando la aplicacion Cliente\n");
        
        
        BufferedReader EntradaUser = new BufferedReader(new InputStreamReader(System.in));
        String NameServer = JOptionPane.showInputDialog("Ingresa la direcion ip del server");
        String puerto = JOptionPane.showInputDialog("Ingresa el puerto");
        int Port = 0;
        if (puerto.isEmpty() || puerto.isBlank())//comprueba si un string esta vacio
        {
            Port = 5001;
        }else {
            Port = Integer.parseInt(puerto);
        }
        String Message;
        try
        {
            InetAddress IpAd = InetAddress.getByName(NameServer);//contiene el nombre y la direccion ip de localhost
            DatagramSocket ConeccionCliente = new DatagramSocket();//permite instancias de datagramas recibidos
            //especificando la cadena de byte y la longuitud del sms
            String cierre = new String("cierre");
                
            while(true)
            {

                System.out.println("Ingresa el mensaje para el servidor");
                Message = EntradaUser.readLine();

                byte[]DatosEnviar = new byte[Message.length()];
                byte[]DataRecive = new byte[Message.length()];

                if(Message.equals(""))Message = "cierre";
                
                System.out.println("Cadena a enviar: " + Message);
                DatosEnviar = Message.getBytes();
                DatagramPacket EnviarPakete = new DatagramPacket(DatosEnviar,DatosEnviar.length,IpAd,Port);
                
                
                try{
                    ConeccionCliente.send(EnviarPakete);
                }catch(ConnectException e){
                    System.out.println("Error en el envio: " + e.getMessage());
                }
                

                DatagramPacket RecivirPakete = new DatagramPacket(DataRecive,DataRecive.length);
                ConeccionCliente.receive(RecivirPakete);

                String Respuesta = new String(RecivirPakete.getData());
                
                
                if(!Respuesta.isEmpty() && !Message.equals("cierre"))
                {
                    
                    System.out.println("Respuesta del servidor: "+Respuesta + "\n");
                }
                else
                {
                    
                    
                    System.out.println("Respuesta del servidor: "+Respuesta);
                    
                    System.out.println("Cerrando la conexion\n");
                    ConeccionCliente.close();
                    break;
               
                }
                
                
               
            }
            
            
        }
        catch(Exception e)
        {
            System.out.println("Ocurrio un error al tratar de conectar: "+e.getMessage() + "\n");
        }
        
        
        
        
        System.out.println("Adios ....");
    }
    
}
