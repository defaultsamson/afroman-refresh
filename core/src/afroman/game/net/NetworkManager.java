package afroman.game.net;

import afroman.game.FinalConstants;
import afroman.game.MainGame;
import afroman.game.PlayerType;
import afroman.game.net.objects.PlayerWrapper;
import afroman.game.net.objects.RequestPassword;
import afroman.game.net.objects.UpdatePlayerList;
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

    private boolean preventFromSendingToMainMenu = false;

    public void preventFromSendingToMainMenu() {
        preventFromSendingToMainMenu = true;
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

        serverConnctions = new ArrayList<PlayerConnection>();

        client = new Client();
        registerKryo(client.getKryo());
        client.start();
        client.connect(5000, ip, port, port);

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof RequestPassword) {
                    String pass = ((RequestPassword) object).pass;
                    // If the server is replying with some text, that means that a password is required
                    if (pass != null && pass.length() > 0) {
                        if (pass.equals(FinalConstants.incorrectPasswordMessage)) {
                            MainGame.game.getPasswordGui().setMessage("INCORRECT PASSWORD");
                            MainGame.game.safelySetScreen(MainGame.game.getPasswordGui());
                        } else {
                            MainGame.game.getPasswordGui().resetMessage();
                            MainGame.game.safelySetScreen(MainGame.game.getPasswordGui());
                        }
                    }
                } else if (object instanceof UpdatePlayerList) {
                    System.out.println("MMMMm mmmm CREAMEEEE -------------");
                    if (!isHostingServer()) {
                        for (PlayerWrapper w : ((UpdatePlayerList) object).connections) {
                            serverConnctions.clear();
                            serverConnctions.add(new PlayerConnection(w.username, w.isHost, w.type));
                            System.out.println("Adding conenctted player: " + w.username + ", " + w.isHost + ", " + w.type);
                        }
                    }
                }
                // TODO update lobby menu
                MainGame.game.safelySetScreen(MainGame.game.getLobbyGui());
            }

            @Override
            public void disconnected(Connection connection) {
                super.disconnected(connection);
                killClient();
                if (!preventFromSendingToMainMenu) {
                    MainGame.game.safelySetScreen(MainGame.game.getMainMenu());
                }
                preventFromSendingToMainMenu = false;
            }
        });

        serverPort = port;
        isConnectingClient = false;

        System.out.println("Client successfully connected to host!");
    }

    public boolean isHostingServer() {
        return server != null;
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

    private void registerKryo(Kryo k) {
        k.register(RequestPassword.class);
        k.register(UpdatePlayerList.class);
        k.register(PlayerWrapper.class);
        k.register(PlayerWrapper[].class);
        k.register(PlayerType.class);
    }

    public void hostServer(int port, final String password) throws IOException {
        isCreatingServer = true;

        this.serverPassword = password;
        serverConnctions = new ArrayList<PlayerConnection>();

        server = new Server();
        registerKryo(server.getKryo());
        server.start();
        server.bind(port, port);

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
                                server.sendToAllTCP(new UpdatePlayerList(serverConnctions));
                            } else {
                                // If password is incorrect too many times, disconnect them
                                if (sp.passwordAttempts() > FinalConstants.maxPasswordAttempts) {
                                    System.err.println("Connection failed to enter the password too many times, disconnecting...");
                                    System.err.println("Connection: " + connection.getID() + ", " + connection.getRemoteAddressUDP().getHostName() + ", " + connection.getRemoteAddressTCP().getHostName());
                                    connection.close();
                                } else {
                                    // Otherwise tell them that the password is wrong
                                    RequestPassword req = new RequestPassword();
                                    req.pass = FinalConstants.incorrectPasswordMessage;
                                    connection.sendTCP(req);
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

                server.sendToAllTCP(new UpdatePlayerList(serverConnctions));
            }

            @Override
            public void connected(Connection connection) {
                super.connected(connection);
                // By defualt, make the player player 1. If there's already a player 1, switch them to player 2
                PlayerType type = getPlayerConnection(PlayerType.PLAYER1) == null ? PlayerType.PLAYER1 : PlayerType.PLAYER2;
                ServerPlayerConnection sp = new ServerPlayerConnection(connection, "user", serverConnctions.isEmpty(), type);
                serverConnctions.add(sp);

                if (sp.isHost()) {
                    sp.confirmPlayerAsAuthenticated();
                } else if (hasPassword()) {
                    // If there's a password, ask for it from the player
                    // If they're the host, don't need to ask for a password

                    RequestPassword req = new RequestPassword();
                    req.pass = "dank";
                    connection.sendTCP(req);
                } else {
                    sp.confirmPlayerAsAuthenticated();
                }

                server.sendToAllTCP(new UpdatePlayerList(serverConnctions));
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
            server = null;
            if (serverConnctions != null) serverConnctions.clear();
        }
    }

    public void killClient() {
        if (client != null) {
            client.stop();
            client = null;
            if (serverConnctions != null) serverConnctions.clear();
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
}
