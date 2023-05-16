package network;

import network.ServerThread;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.logging.*;

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
            logger.severe(e.getMessage());
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

            // Добавление обработчика вывода для перенаправления в лог
            Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            consoleHandler.setFormatter(formatter);


            // Загрузка сертификата
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            char[] password = "iitu2019".toCharArray(); // Пароль для доступа к сертификату
            ClassLoader classLoader = Server.class.getClassLoader();
            File certFile = new File(classLoader.getResource("certificate.crt").getFile()); // Путь к файлу сертификата
            FileInputStream fis = new FileInputStream(certFile);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(fis);
            fis.close();

            // Загрузка закрытого ключа
            File privateKeyFile = new File(classLoader.getResource("keystore.p12").getFile()); // Путь к файлу закрытого ключа
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            char[] keyPassword = "iitu2019".toCharArray(); // Пароль для доступа к закрытому ключу
            InputStream keyInputStream = new FileInputStream(privateKeyFile);
            keyStore.load(keyInputStream, keyPassword);
            keyInputStream.close();

            // Создание менеджера ключей
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keyPassword);

            // Создание SSL-контекста
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            // Создание SSL-серверного сокета
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
            SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(3489);

            printLocalIPAddress();
            logger.info("СЕРВЕР ЗАПУЩЕН");
            logger.info("ОЖИДАНИЕ КЛИЕНТОВ");

            while (true) {
                SSLSocket socket = (SSLSocket) sslServerSocket.accept();

                ServerThread st = new ServerThread(socket);
                st.start();
            }
        } catch (IOException | NoSuchAlgorithmException | KeyStoreException |
                 UnrecoverableKeyException | KeyManagementException e) {
            logger.severe(e.getMessage());
            logger.severe("Ошибка при запуске сервера");
            e.printStackTrace();
        } catch (CertificateException e) {
            logger.severe(e.getMessage());
            logger.severe("Ошибка при загрузке сертификата");
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            logger.severe("Возникла непредвиденная ошибка");
            e.printStackTrace();
        }
    }
}




