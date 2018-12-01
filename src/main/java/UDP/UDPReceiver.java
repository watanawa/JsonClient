package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPReceiver implements Runnable{
    private DatagramSocket socket;
    private boolean running;
    private byte[] buffer;
    private MessageHandler messageHandler;

    public UDPReceiver(DatagramSocket socket, MessageHandler messageHandler){
        this.socket = socket;
        this.messageHandler = messageHandler;
        buffer = new byte[1024];
    }

    //Receiver get Message+Sender
    @Override
    public void run() {
        running = true;
        while(running){
            DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length);
            try {
                socket.receive(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] data = datagramPacket.getData();
            int offset = datagramPacket.getOffset();
            int length= datagramPacket.getLength();
            messageHandler.handleMessage(data,offset,length);
        }
        running = false;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }
}
