package network;
import network.ServerThread;

import java.net.*;
import java.util.Date;
import java.util.Enumeration;

public class Server {
    public static void printLocalIPAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLinkLocalAddress() && !addr.isLoopbackAddress() && addr.getHostAddress().indexOf(":") == -1) {
                        System.out.println("Локальный IP-адрес: " + addr.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(3489);
            printLocalIPAddress();
            System.out.println("THE SERVER WAS STARTED SUCCESSFULLY at " + new Date());
            System.out.println("WAITING CLIENTS");

            while(true){
                Socket socket=ss.accept();

                ServerThread st = new ServerThread(socket);
                st.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
