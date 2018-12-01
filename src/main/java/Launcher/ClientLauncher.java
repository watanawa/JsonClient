package Launcher;

import Gui.DsfHandler;
import Gui.WindowMain;
import UDP.MessageHandler;
import UDP.UDPReceiver;
import UDP.UDPSender;
import dsf.TypeEquipmentDescription;
import org.json.JSONObject;

import java.io.File;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Scanner;

public class ClientLauncher {
    private int port;
    private int proxyPort;
    private DatagramSocket socket;
    private UDPReceiver receiver;
    private UDPSender sender;
    private MessageHandler messageHandler;
    private String path;
    private TypeEquipmentDescription typeEquipmentDescription;
    // Args 0:Filepath 1:Own port 2:ProxyPort/Destination
    public static void main(String[] args){
        ClientLauncher launcher = new ClientLauncher(args[0], args[1], args[2]);
        JSONObject object = new JSONObject();
        launcher.createUDPSocket();
        launcher.createUDPSender();
        launcher.createMessageHandler();
        launcher.createUDPReceiver();
        launcher.unmarshall();
        launcher.initializeDsfHandler();
        launcher.buildGUI();
        //launcher.waitForInput();
        //launcher.sendRequests();
    }
    private void createUDPReceiver() {
        receiver =  new UDPReceiver(socket, messageHandler);
        Thread threadReceiver = new Thread(receiver, "ClientReceiver");
        threadReceiver.start();
    }
    private void createUDPSender(){
        sender = new UDPSender(socket, proxyPort);
    }
    public ClientLauncher(String path,String port, String proxyPort){
        this.path = path;
        this.port = Integer.valueOf(port);
        this.proxyPort = Integer.valueOf(proxyPort);
    }
    public void createUDPSocket(){
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    public void createMessageHandler(){
        messageHandler = new MessageHandler(sender);
    }
    public void waitForInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Waiting for the input of the recordelement path ");
        System.out.println("Continue with NEWRECORDELEMENT or FINISH to stop adding new elements");
        boolean scanning = true;
        LinkedList<String> recordElementPath = new LinkedList<>();
        while(scanning ){
            String temp = scanner.next();
            if(temp.equalsIgnoreCase("Finish")){
                messageHandler.addRecordElementPath(recordElementPath);
                System.out.println("################");
                System.out.println("CLOSING THE INPUT");
                System.out.println("################");
                scanning = false;

            }
            else if(temp.equalsIgnoreCase("NewRecordElement")){
                messageHandler.addRecordElementPath(recordElementPath);
                recordElementPath = new LinkedList<>();
                System.out.println();
                System.out.println("################");
                System.out.println("PLEASE INPUT A NEW RECORDELMENT PATH");
                System.out.println("################");
            }else{
                recordElementPath.add(temp);
            }

        }
        scanner.close();

    }
    public void sendRequests(){
        System.out.println("################");
        System.out.println("SENDING REQUESTS");
        System.out.println("################");
        messageHandler.sendRequests();
    }
    public void unmarshall(){
        Unmarshaller unmarshaller = new Unmarshaller();
        typeEquipmentDescription = unmarshaller.unmarshal(new File(path));
    }
    public void initializeDsfHandler(){
        DsfHandler.setTypeEquipmentDescription(typeEquipmentDescription);
    }
    public void buildGUI(){
        WindowMain.main(null);
    }
}
