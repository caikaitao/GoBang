package com.ckt.gobang.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.ckt.gobang.R;
import com.ckt.gobang.util.ConstantUtil;



import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ConnectActivity extends Activity {
	private Button buttonTest;
	private Button buttonCreate;
	private Button buttonSearch;
	private BluetoothAdapter adapter;
	private BluetoothDevice device;
	private boolean flag = false;
	private ListView devicesList;
	private ArrayAdapter listAdapter;
	private ArrayList<String> listData;//listview数据
	private ArrayList<String> listAddressData;//设备物理地址
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// 开始搜索
			if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
				// 从Intent中获取设备对象
				// BluetoothDevice device =
				// intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// System.out.println("开始搜索！！");
				//Log.v(Constant.LogTag, "开始搜索");

				// 将设备名称和地址放入array adapter，以便在ListView中显示
				// mArrayAdapter.add(device.getName() + "\n" +
				// device.getAddress());
			}
			// 开始搜索
			if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				// 从Intent中获取设备对象
				// BluetoothDevice device =
				// intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// System.out.println("搜索结束！！");
				//Log.v(Constant.LogTag, "搜索结束");
				// 将设备名称和地址放入array adapter，以便在ListView中显示
				// mArrayAdapter.add(device.getName() + "\n" +
				// device.getAddress());
			}
			// 发现设备
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// 从Intent中获取设备对象
				device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				listData.add(device.getName());
				listAddressData.add(device.getAddress());
				listAdapter.notifyDataSetChanged();
				/*if (device.getName().equals("ZTE U817")) {
					buttonTest.setText(device.getName());
					Constant.address = device.getAddress();
					Log.v(Constant.LogTag,
							device.getName() + "---" + device.getAddress());

				} else {
					Log.v(Constant.LogTag,
							device.getName() + " " + device.getAddress());
				}*/
				// System.out.println(device.getName() + "---" +
				// device.getAddress());
				// 将设备名称和地址放入array adapter，以便在ListView中显示
				// mArrayAdapter.add(device.getName() + "\n" +
				// device.getAddress());
			}
			if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
				if(BluetoothAdapter.getDefaultAdapter().isEnabled()){
					//BluetoothAdapter.getDefaultAdapter().startDiscovery();
					Toast.makeText(ConnectActivity.this, "蓝牙已开启，请重新尝试创建和搜索", Toast.LENGTH_SHORT).show();
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
		setContentView(R.layout.connect_room);

		WindowManager windowManager = this.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		ConstantUtil.SCREENHEIGHT=display.getHeight();
		ConstantUtil.SCREENWIDTH=display.getWidth();
		adapter = BluetoothAdapter.getDefaultAdapter();
		buttonCreate = (Button) findViewById(R.id.button_create_room);
		buttonSearch = (Button) findViewById(R.id.button_search_room);
		devicesList = (ListView)findViewById(R.id.listview_devices);
		listAddressData = new ArrayList<String>();
		listData = new ArrayList<String>();
		listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,listData);
		devicesList.setAdapter(listAdapter);
		devicesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ConstantUtil.address = listAddressData.get(position);
				if (device != null && ConstantUtil.address != null) {
					 
					Intent intent = new Intent();
					intent.putExtra("viewType", 2);
					// intent.putExtra("viewType", 3);
					ConstantUtil.serverOrClient = false;
					intent.setClass(getApplicationContext(),
							TestClientActivity.class);
					// intent.setClass(getApplicationContext(),MainActivity.class);
					startActivity(intent);
				}
			}
		});
		
		//buttonTest = (Button) findViewById(R.id.button_test_room);
		// 注册广播
		IntentFilter filterFound = new IntentFilter(
				BluetoothDevice.ACTION_FOUND);
		filterFound.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		filterFound.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		filterFound.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		registerReceiver(mReceiver, filterFound);

		buttonCreate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(adapter.isEnabled()){
					//允许被搜索
					Intent discoveryIntent = new Intent(
							BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
					discoveryIntent.putExtra(
							BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
					startActivityForResult(discoveryIntent, 1);
					//startActivity(discoveryIntent);
					flag = true;
				}else{
					adapter.enable();
					Toast.makeText(ConnectActivity.this, "正在为您开启蓝牙", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		buttonSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!adapter.isEnabled()) {
					Toast.makeText(ConnectActivity.this, "正在为您开启蓝牙", Toast.LENGTH_SHORT).show();
					// 我们通过startActivityForResult()方法发起的Intent将会在onActivityResult()回调方法中获取用户的选择，比如用户单击了Yes开启，
					// 那么将会收到RESULT_OK的结果，
					// 如果RESULT_CANCELED则代表用户不愿意开启蓝牙
					/*Intent mIntent = new Intent(
							BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(mIntent, 1);*/
					// 用enable()方法来开启，无需询问用户(实惠无声息的开启蓝牙设备),这时就需要用到android.permission.BLUETOOTH_ADMIN权限。
					adapter.enable();
					// mBluetoothAdapter.disable();//关闭蓝牙
				}
				else if(adapter.isEnabled()){
					initData();
					adapter.startDiscovery();
				}
			}
		});
		/*buttonTest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag) {
					Intent intent = new Intent();
					intent.putExtra("viewType", 2);
					System.out.println("打开服务器棋盘");
					intent.setClass(getApplicationContext(),
							TestServerActivity.class);
					// intent.setClass(getApplicationContext(),MainActivity.class);
					Constant.serverOrClient = true;
					startActivity(intent);
					return;
				}
				if (!buttonTest.getText().toString().equals("木有房间")) {
					if (device != null) {
						
						 * ConnectThread clientThread = new
						 * ConnectThread(device,adapter,Constant.uuid);
						 * clientThread.start();
						 
						Intent intent = new Intent();
						intent.putExtra("viewType", 2);
						// intent.putExtra("viewType", 3);
						Constant.serverOrClient = false;
						intent.setClass(getApplicationContext(),
								TestClientActivity.class);
						// intent.setClass(getApplicationContext(),MainActivity.class);
						startActivity(intent);
					}
				}
			}
		});*/
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		initData();
		super.onRestart();
	}
	private void initData(){
		listAddressData.removeAll(listAddressData);
		listData.removeAll(listData);
		ConstantUtil.address = null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			Log.v(ConstantUtil.LogTag, resultCode + "resultCode");
			if (resultCode == 300) {
				Log.v(ConstantUtil.LogTag, "进来了吗");
				//Toast.makeText(this, "蓝牙已经开启", Toast.LENGTH_SHORT).show();
				//adapter.startDiscovery();
				Intent intent = new Intent();
				intent.putExtra("viewType", 2);
				System.out.println("打开服务器棋盘");
				intent.setClass(getApplicationContext(),
						TestServerActivity.class);
				// intent.setClass(getApplicationContext(),MainActivity.class);
				ConstantUtil.serverOrClient = true;
				startActivity(intent);
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "创建失败", Toast.LENGTH_SHORT).show();
				//finish();
			}
		}
	}

	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}
}
