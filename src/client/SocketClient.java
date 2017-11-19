package client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.alibaba.fastjson.JSON;
import common.AbsMessage;
import common.FileUitl;
import common.MyCodecFactory;

public class SocketClient extends IoHandlerAdapter {

	public static final int CONNECT_TIMEOUT = 3000;

	private String host;
	private int port;
	private SocketConnector connector;
	private IoSession session;

	public SocketClient() {
		this("127.0.0.1", 33789);
	}
	
	public static void main(String[] args) {
		new SocketClient();
	}

	public SocketClient(String host, int port) {
		try{
			this.host = host;
			this.port = port;
			connector = new NioSocketConnector();
			connector.getFilterChain().addLast("codec", 
					new ProtocolCodecFilter(new MyCodecFactory(
							new InfoDecoder(Charset.forName("utf-8")),
							new InfoEncoder(Charset.forName("utf-8")))
							));
			connector.setHandler(this);
			// ���ӵ��ض���remote��ַ��InetSocketAddress��װIP��port,Java�����̹淶��
			// ���ṩֱ�ӵ�ip��ַ�Ͷ˿ڵ�connect����
			ConnectFuture connectFuture = connector.connect(new InetSocketAddress(this.host, this.port));
			// �ȴ���������
			connectFuture.awaitUninterruptibly();
			System.out.println("���ӳɹ�");
			// ���ش����������ӵ�һ������,�ж�д�����Լ���ȡ�����������õ���ط���
			this.session = connectFuture.getSession();
			
			
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("order", "call-over����");
			map.put("time", "2017-11-19 17:05:30");
			map.put("data", FileUitl.encodeBase64File("c:/cs/02-3C-1-1.bin"));
			
			String data = JSON.toJSONString(map);
	        IoBuffer buffer = getDatabuffer(data);
	        session.write(buffer);
	        
	        
	        Map<String,Object> maps = new HashMap<String, Object>();
			maps.put("data", FileUitl.encodeBase64File("c:/cs/02-3C-1-1.bin"));
			
			String datas = JSON.toJSONString(maps);
			IoBuffer buffers = getDatabuffer(datas);
	        WriteFuture wFuture = session.write(buffers);
	        
	        
	        wFuture.awaitUninterruptibly();
	        if(wFuture.isWritten())
	        {
	        	CloseFuture cFuture = session.getCloseFuture().awaitUninterruptibly();
	        }
		}finally{
			if(connector!=null)
			{
				connector.dispose();
			}
		}
	}
	
	/**
	 * ��װ��Ϣ
	 * @return
	 */
	public IoBuffer getDatabuffer(String data)
	{
		AbsMessage msgHeads = new AbsMessage(data);
		System.out.println("������Ϣ���ȣ�"+(8+msgHeads.getBodyLength()));
        //����һ�����壬�����СΪ:��Ϣͷ����(8λ)+��Ϣ�峤��
        IoBuffer buffer = IoBuffer.allocate(8+msgHeads.getBodyLength());
        buffer.put(msgHeads.getStartFlage());
        buffer.putInt(msgHeads.getBodyLength());
        buffer.put(msgHeads.getBodyData());
        buffer.put(msgHeads.getEndFlage());
        //����Ϣ��put��ȥ
        buffer.flip();
		return buffer;
	}
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		IoBuffer buf = (IoBuffer) message;
		byte StartFlage1 = buf.get();
		byte StartFlage2 = buf.get();
        int bodyLength=buf.getInt();
		
		System.out.println("���������������"+(StartFlage1==(byte) 0xaa));
        System.out.println("���������������"+(StartFlage2==(byte) 0xaa));
        System.out.println("������������Ϣ�峤�ȣ�"+bodyLength);
    	
        byte[] bytes = new byte[bodyLength];
        
        buf.get(bytes);
        System.out.println(buf.remaining());
        
        
        String json = new String(bytes,"utf-8");
        System.out.println("���������أ�"+json);
        session.closeOnFlush();
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("sessionCreated:"+session.getId());
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("sessionOpened:"+session.getId());
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("sessionClosed:"+session.getId());
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("����:"+session.getId()+"�����д�����"+session.getIdleCount(status));
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("messageSent:"+session.getId());
	}
}