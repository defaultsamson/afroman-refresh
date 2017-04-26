package afroman.game.net;

import afroman.game.FinalConstants;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 * Created by Samson on 2017-04-25.
 */
public class NetworkManager {

    private Client client;
    private Server server;

    public NetworkManager() {

    }

    public void connectToServer(String ip) throws IOException {
        int port = FinalConstants.defaultPort;
        if (ip.contains(":")) {
            try {
                String[] split = ip.split("[:]");
                port = Integer.parseInt(split[split.length - 1]);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < split.length - 1; i++) {
                    sb.append(split[i]);
                    if (i < split.length - 2) sb.append(':');
                }
                ip = sb.toString();
            } catch (NumberFormatException e) {
                System.err.println("Error when parsing port from IP");
                //e.printStackTrace();
            }
        }

        connectToServer(ip, port);
    }

    public void connectToServer(String ip, int port) throws IOException {
        client = new Client();
        client.start();
        client.connect(5000, ip, port, port);

        Kryo kryo = client.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);

        SomeRequest request = new SomeRequest();
        request.text = "Here is the request";
        client.sendTCP(request);

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof SomeResponse) {
                    SomeResponse response = (SomeResponse) object;
                    System.out.println(response.text);
                }
            }
        });
        //socket = Gdx.net.newClientSocket(Net.Protocol.TCP, ip, port, new SocketHints());

        System.out.println("Client successfully connected to host!");
    }

    public void hostServer(String port, String password) throws IOException {
        int p = FinalConstants.defaultPort;

        try {
            p = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            System.err.println("Error when parsing port \"" + port + "\"");
            //e.printStackTrace();
        }

        hostServer(p, password);
    }

    public void hostServer(int port, String password) throws IOException {
        server = new Server();
        server.start();
        server.bind(port, port);

        Kryo kryo = server.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);

        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {

                if (object instanceof SomeRequest) {
                    SomeRequest request = (SomeRequest) object;
                    System.out.println(request.text);

                    SomeResponse response = new SomeResponse();
                    response.text = "Thanks";
                    connection.sendTCP(response);
                }
            }

        });

        System.out.println("Server successfully hosted!");
    }

    public static class SomeRequest {
        public String text;
    }

    public static class SomeResponse {
        public String text;
    }

    public void dispose() {
        try {

            if (server != null) {
                server.stop();
                //server.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (client != null) {
                client.stop();
                //client.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}