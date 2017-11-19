package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import client.InfoDecoder;
import client.InfoEncoder;
import common.MyCodecFactory;

public class ImageServer {

	public static final int PORT = 33789;

	public static void main(String[] args) throws IOException {

		ImageServerIoHandler handler = new ImageServerIoHandler();

		NioSocketAcceptor acceptor = new NioSocketAcceptor();

		acceptor.getFilterChain().addLast("protocol", 
				new ProtocolCodecFilter(
					new MyCodecFactory(
						new InfoDecoder(Charset.forName("utf-8")),
						new InfoEncoder(Charset.forName("utf-8")))
				));

		acceptor.setDefaultLocalAddress(new InetSocketAddress(PORT));

		acceptor.setHandler(handler);

		acceptor.bind();

		System.out.println("server is listenig at port " + PORT);

	}

}