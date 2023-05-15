package network;

import network.ServerThread;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public static void printLocalIPAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLinkLocalAddress() && !addr.isLoopbackAddress() && addr.getHostAddress().indexOf(":") == -1) {
                        logger.info("Локальный IP-адрес: " + addr.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // Создание директории logs, если ее еще нет
            File logsDir = new File("logs");
            if (!logsDir.exists()) {
                logsDir.mkdir();
            }

            // Инициализация FileHandler, чтобы записывать логи в файл
            FileHandler fileHandler = new FileHandler("logs/server.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);

            ServerSocket ss = new ServerSocket(3489);
            printLocalIPAddress();
            logger.info("THE SERVER WAS STARTED SUCCESSFULLY");
            logger.info("WAITING CLIENTS");

            while(true) {
                Socket socket = ss.accept();

                ServerThread st = new ServerThread(socket);
                st.start();
            }

        } catch (IOException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }
}
