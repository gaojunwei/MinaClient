package com.connection.client;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class Manager {
	private final static Map<String, Queue<String>> queueMap = new ConcurrentHashMap<>();
	private static Manager manager = new Manager();
	private Manager(){}
	public static Manager getInstance()
	{
		return manager;
	}
	/**
     * 将消息添加到队列
     * @param mac
     * @param data
     * @return
     */
    public boolean addData(String mac,String data)
    {
        Queue<String> queue = queueMap.get(mac);
        if(queue == null)
        {
        	synchronized (this) {
            	if(queue == null)
                {
                    queue = new LinkedList<String>();
                }
    		}
        }
        //向队列添加消息
        if(queue.offer(data))
        {
            queueMap.put(mac,queue);
            return true;
        }
        return false;
    }
    
    /**
    *
    * @param mac
    * @param data
    */
   public void sendDataListener(String mac)
   {
	   Queue<String> queue = queueMap.get(mac);
       while(true)
       {
           
           if(queue==null || queue.peek()==null)
           {
               try {
            	   System.out.println("空数据不发送");
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }else
           {
        	   String data = queue.peek();
               System.out.println("发送消息："+data);
               queue.poll();
           }
       }
   }
    
}
