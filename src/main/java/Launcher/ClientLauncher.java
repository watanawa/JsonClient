package Launcher;

import Gui.DsfHandler;
import Gui.WindowMain;
import Help.Interface;
import Json.JSONDebugDataMessage;
import Json.JSONDebugDataReadRequest;
import Json.JSONDebugDataWriteRequest;
import UDP.MessageHandler;
import UDP.UDPReceiver;
import UDP.UDPSender;
import dsf.TypeEquipmentDescription;
import org.json.JSONObject;

import java.io.File;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
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
        launcher.initializeInterface();
        if(args.length == 4 && args[3].equalsIgnoreCase("loopitems")){
            launcher.requestLoopItems();
        }
        launcher.buildGUI();
        //launcher.waitForInput();
        //launcher.sendRequests();
    }

    private void requestLoopItems() {
        LinkedList<String> position = new LinkedList<>();
        position.add("Periodic");
        position.add("SIM_AhrsGpsModel_Context");
        ArrayList<String> positionItems =new ArrayList<>();
        positionItems.add("AccelerationX");
        positionItems.add("AccelerationY");
        positionItems.add("AccelerationZ");
        positionItems.add("RollRate");
        positionItems.add("PitchRate");
        positionItems.add("YawRate");
        positionItems.add("Roll");
        positionItems.add("Pitch");
        positionItems.add("Yaw");
        positionItems.add("SpeedNorth");
        positionItems.add("SpeedEast");
        positionItems.add("SpeedDown");
        positionItems.add("LatitudeWgs84");
        positionItems.add("LongitudeWgs84");
        positionItems.add("AltitudeWgs84Geoid");
        positionItems.add("AltitudeAgl");
        positionItems.add("CourseOverGround");
        positionItems.add("SpeedOverGround");
        positionItems.add("TouchdownFlag");

        JSONDebugDataReadRequest jsonDebugDataReadRequest  = new JSONDebugDataReadRequest();

        for(String item: positionItems){
            position.add(item);
            jsonDebugDataReadRequest.addRecordElement(position);
            position.removeLast();
        }
        position.removeLast();
        position.add("SYS_ControlHandler_Context");
        jsonDebugDataReadRequest.addRecordElement(position);

        //RecoveryREason
        position.removeLast();
        position.add("GCS_Stanag4586Output_Context");
        position.add("Stanag61000");
        position.add("Stanag61000ReasonForRecovery");
        jsonDebugDataReadRequest.addRecordElement(position);
        sender.sendMessage(jsonDebugDataReadRequest.toString().getBytes());
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
            System.exit(0);
        }
    }
    public void createMessageHandler(){
        messageHandler = new MessageHandler(sender);
    }

    public void unmarshall(){
        Unmarshaller unmarshaller = new Unmarshaller();
        typeEquipmentDescription = unmarshaller.unmarshal(new File(path));
    }
    public void initializeDsfHandler(){
        DsfHandler.setTypeEquipmentDescription(typeEquipmentDescription);
    }
    public void initializeInterface(){
        Interface.setSender(sender);
        Interface.setTypeEquipmentDescription(typeEquipmentDescription);
    }

    public void buildGUI(){
        WindowMain.main(null);
    }
}
