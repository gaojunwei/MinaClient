package server;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;

import org.apache.mina.core.buffer.IoBuffer;

import com.alibaba.fastjson.JSON;
import common.FileUitl;


/**
 * 消息事件处理
 */
public class HandlerEvent {
    private static HandlerEvent handlerEvent;
    public static HandlerEvent getInstance() {
        if (handlerEvent == null) {
            handlerEvent = new HandlerEvent();
        }
        return handlerEvent;
    }
    public void handle(IoBuffer buf) throws IOException, InterruptedException, UnsupportedEncodingException, SQLException {
    	byte StartFlage1 = buf.get();
		byte StartFlage2 = buf.get();
        int bodyLength=buf.getInt();
        
        System.out.println("服务器处理结果："+(StartFlage1==(byte) 0xaa));
        System.out.println("服务器处理结果："+(StartFlage2==(byte) 0xaa));
        System.out.println("服务器处理消息体长度："+bodyLength);
    	
        byte[] bytes = new byte[bodyLength];
        
        buf.get(bytes);
        System.out.println(buf.remaining());
        
        
        String json = new String(bytes,"utf-8");
        System.out.println("服务器收到："+json);
        
    }
}