package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPSender {
    DatagramSocket socket;
    int destinationPort;
    public UDPSender(DatagramSocket socket, int targetPort){
        this.socket = socket;
        destinationPort = targetPort;
    }

    public void sendMessage( byte[] message){
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if(inetAddress!= null){
            DatagramPacket datagramPacket = new DatagramPacket(message,message.length,inetAddress,destinationPort);
            try {
                socket.send(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
