package com.ckt.gobang.thread;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.ckt.gobang.util.ConstantUtil;
import com.ckt.gobang.util.StringDealer;


    public class ConnectThread extends Thread { 
        public static BluetoothSocket mmSocket; 
        private final BluetoothDevice mmDevice; 
        private BluetoothAdapter mBluetoothAdapter;
        public ConnectThread(BluetoothDevice device,BluetoothAdapter mBluetoothAdapter,UUID uuid) { 
            // Use a temporary object that is later assigned to mmSocket, 
            // because mmSocket is final 
        	this.mBluetoothAdapter = mBluetoothAdapter;
            BluetoothSocket tmp = null; 
            mmDevice = device; 
         //   device.fetchUuidsWithSdp();
            // Get a BluetoothSocket to connect with the given BluetoothDevice 
            try { 
                // MY_UUID is the app's UUID string, also used by the server code 
                tmp = device.createRfcommSocketToServiceRecord(uuid); 
            } catch (IOException e) {
            } 
            mmSocket = tmp; 
        } 
       
        public void run() { 
            // Cancel discovery because it will slow down the connection 
            mBluetoothAdapter.cancelDiscovery(); 
            
            /*Method m;
            try {
                m = mmDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
                mmSocket = (BluetoothSocket) m.invoke(mmDevice, Integer.valueOf(1));
                          } catch (SecurityException e1) {
                              // TODO Auto-generated catch block
                              e1.printStackTrace();
                          } catch (NoSuchMethodException e1) {
                              // TODO Auto-generated catch block
                              e1.printStackTrace();
                          } catch (IllegalArgumentException e) {
                              // TODO Auto-generated catch block
                              e.printStackTrace();
                          } catch (IllegalAccessException e) {
                              // TODO Auto-generated catch block
                              e.printStackTrace();
                          } catch (InvocationTargetException e) {
                              // TODO Auto-generated catch block
                              e.printStackTrace();
                       } */
            
            try { 
                // Connect the device through the socket. This will block 
                // until it succeeds or throws an exception 
                mmSocket.connect(); 
            } catch (IOException connectException) { 
            	System.out.println("Unable to connect");
                // Unable to connect; close the socket and get out 
                try { 
                    mmSocket.close(); 
                } catch (IOException closeException) { } 
                return; 
            } 
           // Toast.makeText(this, "lianjeihsangle ", Toast.LENGTH_SHORT).show();
            System.out.println("连接上了:" + mmDevice.getName());
            // Do work to manage the connection (in a separate thread) 
            manageConnectedSocket(mmSocket); 
        } 
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
    		                	 System.out.println("client  before read");
    		                     bytes = inputStream.read(buffer);
    		                     System.out.println("client  after read");
    		                     ConstantUtil.ground = StringDealer.getGroupFromString(buffer.toString());
    		                     // String stringg = bytes.
    		                     // Send the obtained bytes to the UI Activity 
    		                   //  mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer) 
    		                         //    .sendToTarget(); 
    		                 } catch (IOException e) { 
    		                     break; 
    		                 } 
    		             } 

    	}
        /** Will cancel an in-progress connection, and close the socket */ 
        public void cancel() { 
            try { 
                mmSocket.close(); 
            } catch (IOException e) { } 
        } 
    } 
