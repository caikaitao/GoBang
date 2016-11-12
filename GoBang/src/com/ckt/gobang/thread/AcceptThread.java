package com.ckt.gobang.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


import com.ckt.gobang.util.ConstantUtil;
import com.ckt.gobang.util.StringDealer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

public class AcceptThread extends Thread{
	private final  BluetoothServerSocket serverSocket;
	public static BluetoothSocket socket = null;
	public AcceptThread(BluetoothAdapter mAdapter,UUID uuid){
		BluetoothServerSocket temp  = null;
		try {
			//创建一个蓝牙套接字服务
			temp = mAdapter.listenUsingInsecureRfcommWithServiceRecord("bluetoothfivechess", uuid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			serverSocket = temp;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				 System.out.println("waiting for connect...");
				socket = serverSocket.accept();
				 System.out.println("receive a connect!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
			if(socket != null){
				manageConnectedSocket(socket);
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//break;
				
			}
		}
	}
	//读取数组
	private  void manageConnectedSocket(BluetoothSocket socket){
		InputStream inputStream = null;
		try {
			inputStream = socket.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 byte[] buffer = new byte[1024];  // buffer store for the stream 
		             int bytes; // bytes returned from read() 
		        
		             // Keep listening to the InputStream until an exception occurs 
		             while (true) { 
		                 try { 
		                     // Read from the InputStream
		                	 System.out.println("server  before read");
		                     bytes = inputStream.read(buffer);
		                     System.out.println("server  after read");
		                     ConstantUtil.ground = StringDealer.getGroupFromString(buffer.toString());
		                     System.out.println(buffer.toString());
		                     // String stringg = bytes.
		                     // Send the obtained bytes to the UI Activity 
		                   //  mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer) 
		                         //    .sendToTarget(); 
		                 } catch (IOException e) { 
		                     break; 
		                 } 
		             } 

	}
	
}
