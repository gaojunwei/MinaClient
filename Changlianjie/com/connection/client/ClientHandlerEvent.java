package com.connection.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.net.SocketServer;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class ClientHandlerEvent {
	private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

	private static ClientHandlerEvent serverHandlerEvent = new ClientHandlerEvent();
	private static int count = 0;
	private ClientHandlerEvent(){}

	public static ClientHandlerEvent getInstance() {
		return serverHandlerEvent;
	}

	public void handle(IoSession iosession, IoBuffer buf, String apMac) throws IOException, InterruptedException, UnsupportedEncodingException, SQLException {
		byte StartFlage1 = buf.get();//获得包头1
		byte StartFlage2 = buf.get();//获得包头2
		int bodyLength=buf.getInt();//获得消息体长度

		byte[] bytes = new byte[bodyLength];
		buf.get(bytes);

		String json = new String(bytes,"utf-8");
        logger.info("******  server received: "+json);
        Map<String,Object> rMap = (Map<String,Object>)JSON.parse(json);
        String order = rMap.get("order").toString();

        Map<String,Object> rturnMap = new HashMap<String, Object>();
        
        String operate_id = null;
        String server_time = null;
        List<Map<String,String>> list = null;
        Map<String,String> dataMap = null;
        String jsonStr = null;
        WriteFuture writeFuture = null;
        IoBuffer ioBuffer = null;
        String msgId = null;
        
        //Thread.sleep(5*1000);
        if(count<3)
        {
        	if(order.equals("h_t")){return;}
        	count++;
        	msgId = rMap.get("msg_id").toString();
        	rturnMap.put("order", "report_state");
        	rturnMap.put("msg_id", "");
        	rturnMap.put("ap_mac", "88:88:88:88:88");
        	rturnMap.put("process_order", order);
        	rturnMap.put("reason", "ap busy");
        	
        	jsonStr = JSON.toJSONString(rturnMap);
        	
        	ioBuffer = DataUtil.getDatabuffer(jsonStr);
        	
        	writeFuture = iosession.write(ioBuffer).awaitUninterruptibly();
        	if(writeFuture.isWritten())
        	{
        		logger.info("返回“设备忙碌返回”处理结果："+jsonStr);
        	}
        	return;
        }
        
        if(1==1){return;}
        
        switch (order) {
            case "call-over"://点名指令
            	operate_id = rMap.get("operate_id").toString();
            	server_time = rMap.get("server_time").toString();
            	msgId = rMap.get("msg_id").toString();
            	
            	rturnMap.put("order", "call-over");
            	rturnMap.put("operate_id", operate_id);
            	rturnMap.put("server_time", server_time);
            	rturnMap.put("ap_mac", apMac);//测试需要改动
            	rturnMap.put("msg_id", msgId);
            	
            	list = new ArrayList<>();
            	dataMap = new HashMap<String, String>();
            	dataMap.put("mac", "ep:00:00:00:00:01");//epl的mac地址
            	dataMap.put("battery_level", "-120");//epl的电池电量
            	dataMap.put("signal_strength", "100");//epl的信号强度
            	dataMap.put("group_number", "00000000");//epl的分组号
            	dataMap.put("operation_code", "01");//epl的操作码
            	dataMap.put("operation_time", "2017-11-26 19:10:00");//epl被操作时间
            	dataMap.put("local_time", "2017-11-26 19:10:00");//epl的本地时间

            	list.add(dataMap);
            	
            	Map<String, String> dataMap2 = new HashMap<String, String>();
            	dataMap2.put("mac", "ep:00:00:00:00:02");//epl的mac地址
            	dataMap2.put("battery_level", "-40");//epl的电池电量
            	dataMap2.put("signal_strength", "99");//epl的信号强度
            	dataMap2.put("group_number", "00000001");//epl的分组号
            	dataMap2.put("operation_code", "01");//epl的操作码
            	dataMap2.put("operation_time", "2017-11-26 19:10:00");//epl被操作时间
            	dataMap2.put("local_time", "2017-11-26 19:10:00");//epl的本地时间
            	
            	list.add(dataMap2);
            	
            	rturnMap.put("list", list);
            	
            	jsonStr = JSON.toJSONString(rturnMap);
            	
            	ioBuffer = DataUtil.getDatabuffer(jsonStr);
            	
            	writeFuture = iosession.write(ioBuffer).awaitUninterruptibly();
            	if(writeFuture.isWritten())
            	{
            		logger.info("返回“点名指令”处理结果："+jsonStr);
            	}
                break;
            case "time_sync"://时间同步指令
            	/*operate_id = rMap.get("operate_id").toString();
            	server_time = rMap.get("server_time").toString();
            	msgId = rMap.get("msg_id").toString();
            	
            	rturnMap.put("order", "time_sync");
            	rturnMap.put("msg_id", msgId);
            	rturnMap.put("ap_mac", apMac);
            	rturnMap.put("process_order", "time_sync");
            	rturnMap.put("reason", "ap bussy");
            	
            	jsonStr = JSON.toJSONString(rturnMap);
            	ioBuffer = DataUtil.getDatabuffer(jsonStr);
            	
            	writeFuture = iosession.write(ioBuffer).awaitUninterruptibly();
            	if(writeFuture.isWritten())
            	{
            		logger.info("返回“时间同步指令”处理结果："+jsonStr);
            	}*/
            	
            	
            	
            	operate_id = rMap.get("operate_id").toString();
            	server_time = rMap.get("server_time").toString();
            	msgId = rMap.get("msg_id").toString();
            	
            	rturnMap.put("order", "time_sync");
            	rturnMap.put("operate_id", operate_id);
            	rturnMap.put("server_time", server_time);
            	rturnMap.put("ap_mac", apMac);//测试需要改动
            	rturnMap.put("sync_result", true);
            	rturnMap.put("msg_id", msgId);
            	
            	jsonStr = JSON.toJSONString(rturnMap);
            	ioBuffer = DataUtil.getDatabuffer(jsonStr);
            	
            	writeFuture = iosession.write(ioBuffer).awaitUninterruptibly();
            	if(writeFuture.isWritten())
            	{
            		logger.info("返回“时间同步指令”处理结果："+jsonStr);
            	}
                break;
            case "change_img"://变更图片指令
            	msgId = rMap.get("msg_id").toString();
            	
            	rturnMap.put("order", "change_img");
            	rturnMap.put("ap_mac", apMac);//测试需要改动
            	rturnMap.put("sync_result", true);
            	rturnMap.put("msg_id", msgId);
            	
            	jsonStr = JSON.toJSONString(rturnMap);
            	ioBuffer = DataUtil.getDatabuffer(jsonStr);
            	
            	writeFuture = iosession.write(ioBuffer).awaitUninterruptibly();
            	if(writeFuture.isWritten())
            	{
            		logger.info("返回“变价指令”处理结果："+jsonStr);
            	}
                break;
            case "h_t"://心跳返回
                logger.info("心跳包返回");
                break;
            default:
                logger.info("[unkonwn order]"+order);
                break;
        }
	}
}
