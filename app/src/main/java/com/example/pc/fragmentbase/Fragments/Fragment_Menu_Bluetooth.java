package com.example.pc.fragmentbase.Fragments;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.fragmentbase.Other.BTService;
import com.example.pc.fragmentbase.Other.StaticValues;
import com.example.pc.fragmentbase.R;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static android.app.Activity.RESULT_CANCELED;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Menu_Bluetooth extends Fragment {



    public Button Server, ConnectToServer,Connect, Pair, Back, visListe, discover, scan, _back, startBtn;
    public ListView lvDevices;
    public View view;

    private BluetoothAdapter BA;
    private String connectedDeviceAdress;
    private BluetoothDevice connectedDevice;
    private final UUID myUUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");





    private ArrayList deviceList;
    private ArrayAdapter deviceAdapter;
    private Set<BluetoothDevice> devicesArray;
    private IntentFilter intentFilter = new IntentFilter("IncomingBTMessage");
    private BroadcastReceiver broadcastReceiver;


    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    BroadcastReceiver mReciever1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED)
        {
            Toast.makeText(getContext(), "Bluetooth needs to be enabled", Toast.LENGTH_LONG).show();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Menu_ModeSelection()).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment__menu__bluetooth2, container, false);
        deviceList = new ArrayList();


        StaticValues.Instance().mBTService = new BTService(getContext());

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("theMessage");

                switch(msg)
                {
                    case "StartGame":
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Game()).commit();
                        break;

                }
            }
        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver,intentFilter);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);

        mReciever1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                        deviceAdapter.add(device.getName() + "\n" + device.getAddress());
                    }

                    deviceAdapter = new  ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, deviceList);

                    lvDevices.setAdapter(deviceAdapter);
                }

                else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    deviceList.add(device.getName()+ "\n" + BA.getAddress());

                    deviceAdapter = new  ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, deviceList);

                    lvDevices.setAdapter(deviceAdapter);
                }
            }
        };

        getContext().registerReceiver(mReciever1,filter);


        checkBT();

        init();

        AdapterView.OnItemClickListener arrayListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(BA.isDiscovering())
                {
                    BA.cancelDiscovery();
                }
                String info = ((TextView) view).getText().toString();
                connectedDeviceAdress = info.substring(info.length() - 17);

                BluetoothDevice tempDevice = BA.getRemoteDevice(connectedDeviceAdress);

                if(tempDevice.getBondState() != BluetoothDevice.BOND_BONDED)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        tempDevice.createBond();
                    }

                    else
                    {
                        Toast.makeText(getContext(), "Your Device Doesn't support this Feature, Connect Manually", Toast.LENGTH_LONG).show();
                    }
                }
            }
        };

        lvDevices.setOnItemClickListener(arrayListener);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(mReciever1);
    }

    public void buttonClicked(View v, String button)
    {
        switch (button) {
            case "Server":
                startBtn.setVisibility(view.VISIBLE);
                lvDevices.setVisibility(view.VISIBLE);
                startBtn.setVisibility(view.VISIBLE);
                Server.setVisibility(view.INVISIBLE);
                ConnectToServer.setVisibility(view.INVISIBLE);
                Pair.setVisibility(view.INVISIBLE);
                Back.setVisibility(view.INVISIBLE);
                _back.setVisibility(view.VISIBLE);
                break;

            case "Connect":
                Server.setVisibility(view.INVISIBLE);
                ConnectToServer.setVisibility(view.INVISIBLE);
                Pair.setVisibility(view.INVISIBLE);
                Back.setVisibility(view.INVISIBLE);
                visListe.setVisibility(view.VISIBLE);
                lvDevices.setVisibility(view.VISIBLE);
                Connect.setVisibility(view.VISIBLE);
                _back.setVisibility(view.VISIBLE);
                Connect.setVisibility(view.VISIBLE);
                break;

            case "Pair":
                Server.setVisibility(view.INVISIBLE);
                ConnectToServer.setVisibility(view.INVISIBLE);
                Connect.setVisibility(view.INVISIBLE);
                Pair.setVisibility(view.INVISIBLE);
                Back.setVisibility(view.INVISIBLE);
                discover.setVisibility(view.VISIBLE);
                lvDevices.setVisibility(view.VISIBLE);
                scan.setVisibility(view.VISIBLE);
                _back.setVisibility(view.VISIBLE);
                break;

            case "Back":
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Menu_ModeSelection()).commit();
                Server.setVisibility(view.INVISIBLE);
                ConnectToServer.setVisibility(view.INVISIBLE);
                Pair.setVisibility(view.INVISIBLE);
                Back.setVisibility(view.INVISIBLE);
                break;

            case "_back":
                Server.setVisibility(view.VISIBLE);
                startBtn.setVisibility(view.INVISIBLE);
                ConnectToServer.setVisibility(view.VISIBLE);
                Pair.setVisibility(view.VISIBLE);
                Back.setVisibility(view.VISIBLE);
                visListe.setVisibility(view.INVISIBLE);
                lvDevices.setVisibility(view.INVISIBLE);
                discover.setVisibility(view.INVISIBLE);
                scan.setVisibility(view.INVISIBLE);
                _back.setVisibility(view.INVISIBLE);
                Connect.setVisibility(view.INVISIBLE);
                break;

            default:
                break;
        }
    }

    public void init()
    {
        Server = (Button) view.findViewById(R.id.Server_button);
        startBtn = (Button)view.findViewById(R.id.btnStartGame);
        Back = (Button) view.findViewById(R.id.BackM_button);

        ConnectToServer = (Button) view.findViewById(R.id.Connect_button);

        visListe = (Button) view.findViewById(R.id.btnVisListe);
        Connect = (Button)view.findViewById(R.id.btnConnect);

        Pair = (Button) view.findViewById(R.id.Pair_button);
        scan = (Button) view.findViewById(R.id.btnScan);
        discover = (Button) view.findViewById(R.id.btnDiscover);
        _back = (Button) view.findViewById(R.id.btnBack);

        lvDevices = (ListView) view.findViewById(R.id.deviceListView);

        visListe.setVisibility(view.INVISIBLE);
        startBtn.setVisibility(view.INVISIBLE);
        lvDevices.setVisibility(view.INVISIBLE);
        Connect.setVisibility(view.INVISIBLE);
        discover.setVisibility(view.INVISIBLE);
        scan.setVisibility(view.INVISIBLE);
        _back.setVisibility(view.INVISIBLE);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = "BluetoothMessage/StartGame";
                StaticValues.Instance().mBTService.write(msg.getBytes(Charset.defaultCharset()));
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Game()).commit();
            }
        });

        Server.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        view = v;
                        deviceList.clear();
                        buttonClicked(v, "Server");
                        deviceList.add(BA.getName() +"\n" + BA.getAddress());

                        deviceAdapter = new  ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, deviceList);

                        lvDevices.setAdapter(deviceAdapter);
                    }
                }
        );

        ConnectToServer.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View _view)
                    {
                        view = _view;
                        buttonClicked(view, "Connect");
                        deviceList.clear();
                    }
                }
        );

        Pair.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        deviceList.clear();
                        view = v;
                        buttonClicked(v, "Pair");
                    }
                }
        );

        Back.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        view = v;
                        buttonClicked(v, "Back");
                    }
                }
        );

        _back.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        view = v;
                        buttonClicked(v, "_back");
                    }
                }
        );

        Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectedDevice = BA.getRemoteDevice(connectedDeviceAdress);
                StaticValues.Instance().mBTService.startClient(connectedDevice,
                        myUUID);
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BA.isDiscovering())
                {
                    BA.cancelDiscovery();
                }

                deviceList.clear();

                BA.startDiscovery();

            }
        });

        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(getVisible, 0);
            }
        });

        visListe.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        deviceList.clear();
                        devicesArray = BA.getBondedDevices();
                        if (devicesArray.size() > 0) {
                            for (BluetoothDevice bt : devicesArray)
                            {
                                deviceList.add(bt.getName() + "\n" + bt.getAddress());
                            }
                            Toast.makeText(getContext(), "Showing Paired Devices", Toast.LENGTH_SHORT).show();
                        }

                        deviceAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, deviceList);

                        lvDevices.setAdapter(deviceAdapter);
                    }
                }
        );

    }

    public void checkBT()
    {
        BA = BluetoothAdapter.getDefaultAdapter();

        if(BA == null)
        {
            Toast.makeText(getContext(), "No Bluetooth Detected", Toast.LENGTH_SHORT).show();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Menu_ModeSelection()).commit();

        }
        else if(!BA.isEnabled())
        {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 1);
            Toast.makeText(getContext(), "Turned on",Toast.LENGTH_LONG).show();
        }
    }


}
