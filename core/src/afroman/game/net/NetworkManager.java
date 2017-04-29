package afroman.game.net;

import afroman.game.FinalConstants;
import afroman.game.MainGame;
import afroman.game.PlayerType;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samson on 2017-04-25.
 */
public class NetworkManager {

    private String serverPassword;

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

    private int serverPort = 0;
    private boolean isConnectingClient = false;
    private boolean isCreatingServer = false;

    public boolean isConnectingClient() {
        return isConnectingClient;
    }

    public boolean isCreatingServer() {
        return isCreatingServer;
    }

    public void connectToServer(String ip, int port) throws IOException {
        isConnectingClient = true;
        client = new Client();
        client.start();
        client.connect(5000, ip, port, port);

        Kryo kryo = client.getKryo();
        kryo.register(RequestPassword.class);

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof RequestPassword) {
                    String pass = ((RequestPassword) object).pass;
                    // If the server is replying with some text, that means that a password is required
                    if (pass != null && pass.length() > 0) {
                        System.out.println("showing password gui");
                        MainGame.game.safelySetScreen(MainGame.game.getPasswordGui());
                    }
                    System.out.println("Received Password: " + pass);
                }
            }

            @Override
            public void disconnected(Connection connection) {
                super.disconnected(connection);
                killClient();
                MainGame.game.safelySetScreen(MainGame.game.getMainMenu());
            }
        });

        serverPort = port;
        isConnectingClient = false;

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

    public void hostServer(int port, final String password) throws IOException {
        isCreatingServer = true;

        this.serverPassword = password;
        serverConnctions = new ArrayList<PlayerConnection>();

        server = new Server();
        server.start();
        server.bind(port, port);

        Kryo kryo = server.getKryo();
        kryo.register(RequestPassword.class);

        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                ServerPlayerConnection sp = getServerPlayerConnection(connection);
                if (sp != null) {
                    if (sp.hasAuthenticated()) {
                        if (object instanceof RequestPassword) {
                            RequestPassword request = (RequestPassword) object;
                            System.out.println(request.pass);
                        }
                    } else {
                        if (object instanceof RequestPassword) {
                            sp.incrementPasswordAttempts();
                            if (((RequestPassword) object).pass.equals(password)) {
                                sp.confirmPlayerAsAuthenticated();
                            } else {
                                // If password is incorrect too many times
                                if (sp.passwordAttempts() > FinalConstants.maxPasswordAttempts) {
                                    System.err.println("Connection failed to enter the password too many times, disconnecting...");
                                    System.err.println("Connection: " + connection.getID() + ", " + connection.getRemoteAddressUDP().getHostName() + ", " + connection.getRemoteAddressTCP().getHostName());
                                    connection.close();
                                }
                            }
                        }
                    }
                } else {
                    System.err.println("Connection has no corresponding ServerPlayerConnection, disconnecting...");
                    System.err.println("Connection: " + connection.getID() + ", " + connection.getRemoteAddressUDP().getHostName() + ", " + connection.getRemoteAddressTCP().getHostName());
                    connection.close();
                }
            }

            @Override
            public void disconnected(Connection connection) {
                super.disconnected(connection);
                ServerPlayerConnection sp = getServerPlayerConnection(connection);
                serverConnctions.remove(sp);
                // If the disconnected palyer was the host, try to find another player to be the new host (only works for dedicated servers)
                if (sp.isHost()) {
                    try {
                        PlayerConnection sp2 = serverConnctions.get(0);
                        if (sp2 != null) {
                            sp2.setHost(true);
                        }
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void connected(Connection connection) {
                super.connected(connection);
                // By defualt, make the player player 1. If there's already a player 1, switch them to player 2
                PlayerType type = getPlayerConnection(PlayerType.PLAYER1) == null ? PlayerType.PLAYER1 : PlayerType.PLAYER2;
                ServerPlayerConnection sp = new ServerPlayerConnection(connection, "user", serverConnctions.isEmpty(), type);
                serverConnctions.add(sp);

                if (hasPassword()) {
                    System.out.println("----- Server has password");
                    // If there's a password, ask for it from the player
                    // If they're the host, don't need to ask for a password
                    if (sp.isHost()) {
                        System.out.println("----- Connection is host");
                        sp.confirmPlayerAsAuthenticated();
                    } else {
                        System.out.println("----- Requesting password over TCP");
                        RequestPassword pack = new RequestPassword();
                        pack.pass = "dank";
                        connection.sendTCP(pack);
                    }
                } else {
                    sp.confirmPlayerAsAuthenticated();
                }
            }
        });

        serverPort = port;
        isCreatingServer = false;

        System.out.println("Server successfully hosted!");
    }

    private boolean hasPassword() {
        return serverPassword != null && serverPassword.length() > 0;
    }

    private List<PlayerConnection> serverConnctions;

    public PlayerConnection getPlayerConnection(PlayerType playerType) {
        for (PlayerConnection p : serverConnctions) {
            if (p.getType() == playerType) return p;
        }

        return null;
    }

    public ServerPlayerConnection getServerPlayerConnection(Connection connection) {
        for (PlayerConnection p : serverConnctions) {
            if (p instanceof ServerPlayerConnection) {
                ServerPlayerConnection sp = ((ServerPlayerConnection) p);
                if (sp.getConnection() == connection) {
                    return ((ServerPlayerConnection) p);
                }
            }
        }
        return null;
    }

    public ServerPlayerConnection getServerPlayerConnection(int connectionID) {
        for (PlayerConnection p : serverConnctions) {
            if (p instanceof ServerPlayerConnection) {
                ServerPlayerConnection sp = ((ServerPlayerConnection) p);
                if (sp.getConnectionID() == connectionID) {
                    return ((ServerPlayerConnection) p);
                }
            }
        }
        return null;
    }

    public PlayerConnection getPlayerConnection(String username) {
        for (PlayerConnection p : serverConnctions) {
            if (p.getUsername().equals(username)) return p;
        }

        return null;
    }

    public void setServerPassword(String serverPassword) {
        this.serverPassword = serverPassword;
    }

    public void killServer() {
        if (server != null) {
            server.stop();
        }
    }

    public void killClient() {
        if (client != null) {
            client.stop();
        }
    }

    public int getServerPort() {
        return serverPort;
    }

    public Server getServer() {
        return server;
    }

    public Client getClient() {
        return client;
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

    public static class RequestPassword {
        public String pass;
    }
}
