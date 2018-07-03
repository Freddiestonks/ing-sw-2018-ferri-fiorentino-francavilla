package it.polimi.se2018.network;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.controller.PlayerActionInterface;
import it.polimi.se2018.model.LocalModelInterface;
import it.polimi.se2018.view.ViewInterface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class ClientGatherer extends Thread implements Iterable<ClientInfo>, ClientGathererInterface {
    private Object lock;
    private ServerSocket serverSocket = null;
    private ArrayList<ClientInfo> preLobby = new ArrayList<>();
    private ArrayList<SocketReceiver> socketReceivers = new ArrayList<>();

    public static final int SOCKET_PORT = 1111;
    public static final int RMI_PORT = 1099;

    public ClientGatherer(Object lock) {
        this.lock = lock;
        try {
            serverSocket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            LocateRegistry.createRegistry(RMI_PORT);
            UnicastRemoteObject.exportObject(this, 0);
            Naming.rebind("//localhost:" + "/Server", this);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        start();
    }

    public PlayerActionInterface connectRMI(LocalModelInterface localModel, ViewInterface view) {
        PlayerActionInterface paInterface = null;
        synchronized (lock) {
            PlayerAction pa = new PlayerAction(lock);
            try {
                paInterface = (PlayerActionInterface) UnicastRemoteObject.exportObject(pa, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            preLobby.add(new ClientInfo(localModel, view, pa));
        }
        return paInterface;
    }

    public Iterator<ClientInfo> iterator() {
        return preLobby.iterator();
    }

    @Override
    public void run() {
        Socket socket = null;
        while(true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                //e.printStackTrace();
            }
            PlayerAction pa = new PlayerAction(lock);
            SocketReceiver socketReceiver = new SocketReceiver(socket);
            socketReceiver.setPlayerAction(pa);
            socketReceiver.start();
            SocketLocalModel localModel = new SocketLocalModel(socket);
            SocketView view = new SocketView(socket);
            synchronized (lock) {
                preLobby.add(new ClientInfo(localModel, view, pa));
            }
            Iterator<SocketReceiver> iterator = socketReceivers.iterator();
            while(iterator.hasNext()) {
                if(iterator.next().isInterrupted()) {
                    iterator.remove();
                }
            }
            socketReceivers.add(socketReceiver);
        }
    }
}
