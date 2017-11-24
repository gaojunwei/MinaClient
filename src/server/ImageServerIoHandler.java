package server;

import java.util.Date;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import common.AbsMessage;

public class ImageServerIoHandler extends IoHandlerAdapter {
	
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println("*****>"+session.getAttribute("date"));
	}
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		session.setAttribute("date",new Date().getTime());
		System.out.println("sessionCreated:"+session.getId());
	}
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("sessionClosed:"+session.getId());
	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("空闲:"+session.getId()+"，空闲次数："+session.getIdleCount(status));
	}
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("messageSent:"+session.getId());
	}
	@Override
	public void inputClosed(IoSession session) throws Exception {
		System.out.println("inputClosed:"+session.getId());
		session.closeOnFlush();
	}
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("有一客户端打开链接");
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}
}