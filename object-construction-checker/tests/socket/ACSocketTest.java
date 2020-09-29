import org.checkerframework.checker.objectconstruction.qual.*;
import org.checkerframework.common.returnsreceiver.qual.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Optional;
import javax.net.ssl.*;
import java.nio.channels.*;

public class ACSocketTest
{

    @Owning Socket makeSocket(String address, int port){

        try
        {
            Socket socket = new Socket(address, port);
            return socket;
        }
        catch(IOException i)
        {
            return null;
        }
    }

    void basicTest(String address, int port){
        try
        {
            // :: error: missing.alwayscall
            Socket socket2 = new Socket(address, port);
            Socket socket = new Socket(address, port);
            socket.close();
        }
        catch(IOException i)
        {

        }
    }


    void callMakeSocketAndClose(String address, int port){
        Socket socket = makeSocket(address, port);
        try
        {
            socket.close();
        }
        catch (IOException i)
        {
        }

    }


    void callMakeSocket(String address, int port){
        // :: error: missing.alwayscall
        Socket socket = makeSocket(address, port);
    }

    void ifElseWithDeclaration(String address, int port, boolean b){
        Socket s1;
        Socket s2;
        try
        {
            if (b) {
                s1 = new Socket(address, port);
                s1.close();
            } else {
                // :: error: missing.alwayscall
                s2 = new Socket(address, port+1);
            }
        }
        catch (IOException i)
        {

        }
    }

    void testLoop(String address, int port) {
        Socket s = null;
        while (true) {
            try {
                s = new Socket(address, port);
                s.close();
            } catch (IOException e) {

            }
        }
    }

    void overWrittingVarInLoop(String address, int port) {
        // :: error: missing.alwayscall
        Socket s = makeSocket(address, port);
        while (true) {
            try {
                // :: error: missing.alwayscall
                s = new Socket(address, port);
            } catch (IOException e) {

            }
        }
    }


    void loopWithNestedBranches(String address, int port, boolean b) {
        Socket s = null;
        while (true) {
            if (b) {
                // :: error: missing.alwayscall
                s = makeSocket(address, port);
            } else {
                // :: error: missing.alwayscall
                s = makeSocket(address, port);
            }
        }
    }


    void replaceVarWithNull(String address, int port, boolean b, boolean c) {
        Socket s;
        try {
            // :: error: missing.alwayscall
            s = new Socket(address, port);
        } catch (IOException e) {

        }
        if (b) {
            s = null;
        } else if (c) {
            s = null;
        } else {

        }
    }


    void ownershipTransfer(String address, int port) {
        Socket s1 = null;
        try {
            s1 = new Socket(address, port);
        } catch (IOException e) {

        }
        // :: error: missing.alwayscall
        Socket s2 = s1;
        if(true){
            closeSocket(s2);
        }

    }

    void test(String address, int port)
    {
        try
        {
            // :: error: missing.alwayscall
            Socket socket = new Socket( address, 80 );

            // Create input and output streams to read from and write to the server
            PrintStream out = new PrintStream( socket.getOutputStream() );
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
//            socket.close();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    protected Socket sock;
    void connectToLeader(AtomicReference<Socket> socket) throws IOException {
        // :: error: missing.alwayscall
        if (socket.get() == null) {
            throw new IOException("Failed connect to " );
        } else {
            // :: error: missing.alwayscall
            sock = socket.get();
        }

    }


    Socket createSocket(boolean b, String address, int port) throws IOException {
        Socket sock;
        if (b) {
            // :: error: missing.alwayscall
            sock = new Socket(address, port);
        } else {
            // :: error: missing.alwayscall
            sock = new Socket(address, port);
        }

        sock.setSoTimeout(10000);
        closeSocket(sock);
        return sock;
    }

    void replaceVarWithNull(String address, int port) {
        try {
            // :: error: missing.alwayscall
            Socket s = new Socket(address, port);
            s = null;
        } catch (IOException e) {

        }

    }

//    @EnsuresCalledMethodsIf(expression = "#1", methods = {"close"}, result = true)
//    void closeSocket(Socket sock) {
////        if (sock == null) {
////            return;
////        }
//
//        try {
//            sock.close();
//        } catch (IOException ie) {
//
//        }
//    }


    Optional<ServerSocket> createServerSocket(InetSocketAddress address) {
        ServerSocket serverSocket;
        try {
            // :: error: missing.alwayscall
            serverSocket = new ServerSocket();

            serverSocket.setReuseAddress(true);
            serverSocket.bind(address);
            return Optional.of(serverSocket);
        } catch (IOException e) {

        }
        return Optional.empty();
    }




    public static void ruok(String host, int port) {
        Socket s = null;
        try {
            s = new Socket(host, port);
        } catch (IOException e) {

        } finally {

            try {
                s.close();
            } catch (IOException e) {

            }

        }
    }

    @EnsuresCalledMethods(value = "#1", methods = "close")
    void closeSocket(Socket sock) {
        try {
            if(sock!=null){
                sock.close();
            }
        } catch (IOException e) {

        }
    }

    @EnsuresCalledMethods(value = "#1", methods = "close")
    void closeServerSocket(ServerSocket sock) {
        try {
            if(sock!=null){
                sock.close();
            }
        } catch (IOException e) {

        }
    }

    void useCloseSocket(String address, int port) throws IOException {
        Socket sock = new Socket(address, port);
        Socket s = getSocket(sock);
        closeSocket(sock);
    }



    void setSockOpts(Socket sock) throws SocketException {
        sock.setTcpNoDelay(true);
        sock.setKeepAlive(true);
        sock.setSoTimeout(1000);
    }

    void initiateConnection(SocketAddress endpoint, int timeout, SSLContext context, final Long sid) {
        Socket sock = null;
        try {
            sock = context.getSocketFactory().createSocket();
            setSockOpts(sock);
            sock.connect(endpoint, timeout);
            if (sock instanceof SSLSocket) {
                // :: error: missing.alwayscall
                SSLSocket sslSock = (SSLSocket) sock;
                sslSock.startHandshake();

            }

        } catch (ClassCastException e){
            closeSocket(sock);
            return;
        } catch (IOException e) {
            closeSocket(sock);
            return;
        }

        try{
            startConnection(sock);
        }catch (IOException e) {
            closeSocket(sock);
        }
    }

    private boolean startConnection(@Owning Socket s) throws IOException {
        closeSocket(s);
        return true;
    }

    private boolean startConnection(@Owning SSLSocket s) throws IOException {
        closeSocket(s);
        return true;
    }



    class PrependableSocket extends Socket {

        public PrependableSocket(SocketImpl base) throws IOException {
            super(base);
        }
    }

    void makePrependableSocket() throws IOException {
        // :: error: missing.alwayscall
        final PrependableSocket prependableSocket = new PrependableSocket(null);
    }





//    private void acceptConnections() {
//        int numRetries = 0;
//        Socket client = null;
//
//        while ((!shutdown) && (portBindMaxRetry == 0 || numRetries < portBindMaxRetry)) {
//            try {
//                serverSocket = createNewServerSocket();
//                LOG.info("{} is accepting connections now, my election bind port: {}", QuorumCnxManager.this.mySid, address.toString());
//                while (!shutdown) {
//                    try {
//                        client = serverSocket.accept();
//                        setSockOpts(client);
//                        LOG.info("Received connection request from {}", client.getRemoteSocketAddress());
//                        // Receive and handle the connection request
//                        // asynchronously if the quorum sasl authentication is
//                        // enabled. This is required because sasl server
//                        // authentication process may take few seconds to finish,
//                        // this may delay next peer connection requests.
//                        if (quorumSaslAuthEnabled) {
//                            receiveConnectionAsync(client);
//                        } else {
//                            receiveConnection(client);
//                        }
//                        numRetries = 0;
//                    } catch (SocketTimeoutException e) {
//                        LOG.warn("The socket is listening for the election accepted "
//                                + "and it timed out unexpectedly, but will retry."
//                                + "see ZOOKEEPER-2836");
//                    }
//                }
//            } catch (IOException e) {
//                if (shutdown) {
//                    break;
//                }
//
//                LOG.error("Exception while listening", e);
//
//                if (e instanceof SocketException) {
//                    socketException.set(true);
//                }
//
//                numRetries++;
//                try {
//                    close();
//                    Thread.sleep(1000);
//                } catch (IOException ie) {
//                    LOG.error("Error closing server socket", ie);
//                } catch (InterruptedException ie) {
//                    LOG.error("Interrupted while sleeping. Ignoring exception", ie);
//                }
//                closeSocket(client);
//            }
//        }
//        if (!shutdown) {
//            LOG.error(
//                    "Leaving listener thread for address {} after {} errors. Use {} property to increase retry count.",
//                    formatInetAddr(address),
//                    numRetries,
//                    ELECTION_PORT_BIND_RETRY);
//        }
//    }


    void createNewServerSocket(InetSocketAddress address) throws IOException {
        ServerSocket socket;

        if (true) {
            // :: error: missing.alwayscall
            socket = new ServerSocket();
        } else if (false) {
            // :: error: missing.alwayscall
            socket = new ServerSocket();
        } else {
            // :: error: missing.alwayscall
            socket = new ServerSocket();
        }

        socket.setReuseAddress(true);
        socket.bind(address);
        closeServerSocket(socket);
//        return socket;
    }


    @Owning public SSLServerSocket createSSLServerSocket(SSLContext sslContext) throws IOException {
        SSLServerSocket sslServerSocket = (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket();
        return configureSSLServerSocket(sslServerSocket);
    }

    private SSLServerSocket configureSSLServerSocket(@Owning SSLServerSocket socket) {
        return socket;
    }

    public SSLSocket createSSLSocket(@Owning Socket socket, byte[] pushbackBytes, SSLContext sslContext) throws IOException {
        SSLSocket sslSocket;
        if (pushbackBytes != null && pushbackBytes.length > 0) {
            sslSocket = (SSLSocket) sslContext.getSocketFactory()
                    .createSocket(socket, null, socket.getPort(), true);
        } else {
            sslSocket = (SSLSocket) sslContext.getSocketFactory().createSocket(socket, null, socket.getPort(), true);
        }
        return configureSSLSocket(sslSocket, false);
    }

    private SSLSocket configureSSLSocket(@Owning SSLSocket socket, boolean isClientSocket) {
        SSLParameters sslParameters = socket.getSSLParameters();
//        configureSslParameters(sslParameters, isClientSocket);
        socket.setSSLParameters(sslParameters);
        socket.setUseClientMode(isClientSocket);
        return socket;
    }


    private void updateSocketAddresses( SelectionKey sockKey ) {
        Socket socket = ((SocketChannel) sockKey.channel()).socket();
        SocketAddress localSocketAddress = socket.getLocalSocketAddress();
        SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
    }

    @NotOwning Socket getSocket(Socket s) {
        return s;
    }

    SocketChannel createSock() throws IOException {
        SocketChannel sock;
        sock = SocketChannel.open();
        sock.configureBlocking(false);
        sock.socket().setSoLinger(false, -1);
        sock.socket().setTcpNoDelay(true);
        return sock;
    }
}
