package com.example.pc.fragmentbase.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.fragmentbase.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Menu_Bluetooth extends Fragment {

/**  public Button Server, ConnectToServer,Connect, Pair, Back, visListe, discover, scan, _back;
    public ListView lvDevices;
    public View view;

    private ArrayList deviceList;
    private ArrayAdapter deviceAdapter;
    private Set<BluetoothDevice> devicesArray;

    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    BroadcastReceiver mReciever1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bluetooth_menu, container, false);
        deviceList = new ArrayList();

        mReciever1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress(); // MAC address

                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                        deviceAdapter.add(device.getName() + "\n" + device.getAddress());
                    }
                }
            }
        };

        getContext().registerReceiver(mReciever1,filter);

        StaticValues.Instance().BA = BluetoothAdapter.getDefaultAdapter();


        Server = (Button) view.findViewById(R.id.Server_button);
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
        lvDevices.setVisibility(view.INVISIBLE);
        Connect.setVisibility(view.INVISIBLE);
        discover.setVisibility(view.INVISIBLE);
        scan.setVisibility(view.INVISIBLE);
        _back.setVisibility(view.INVISIBLE);

        Server.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        view = v;
                        buttonClicked(v, "Server");
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
                StaticValues.Instance().connectedDevice = StaticValues.Instance().BA.getRemoteDevice(StaticValues.Instance().connectedDeviceAdress);
                ((MainActivity)getActivity()).startBTConenction(StaticValues.Instance().connectedDevice,StaticValues.Instance().MY_UUID_INSECURE);
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StaticValues.Instance().BA.isDiscovering())
                {
                    StaticValues.Instance().BA.cancelDiscovery();
                }

                deviceList.clear();

                StaticValues.Instance().BA.startDiscovery();
                deviceAdapter = new  ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, deviceList);

                lvDevices.setAdapter(deviceAdapter);
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
                        devicesArray = StaticValues.Instance().BA.getBondedDevices();
                        if (devicesArray.size() > 0) {
                            for (BluetoothDevice bt : devicesArray)
                            {
                                deviceList.add(bt.getName() + "\n" + bt.getAddress());
                            }
                            Toast.makeText(getContext(), "Showing Paired Devices", Toast.LENGTH_SHORT).show();
                        }

                        deviceAdapter = new  ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, deviceList);

                        lvDevices.setAdapter(deviceAdapter);
                    }
                }
        );

        AdapterView.OnItemClickListener arrayListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(StaticValues.Instance().BA.isDiscovering())
                {
                    StaticValues.Instance().BA.cancelDiscovery();
                }
                String info = ((TextView) view).getText().toString();
                StaticValues.Instance().connectedDeviceAdress = info.substring(info.length() - 17);

                BluetoothDevice tempDevice = StaticValues.Instance().BA.getRemoteDevice(StaticValues.Instance().connectedDeviceAdress);

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
                StaticValues.Instance().fragment = new ModeMenu();
                StaticValues.Instance().fragmentManager = getActivity().getSupportFragmentManager();
                StaticValues.Instance().fragmentTransaction = StaticValues.Instance().fragmentManager.beginTransaction();
                StaticValues.Instance().fragmentTransaction.replace(R.id.fragment7, StaticValues.Instance().fragment);
                StaticValues.Instance().fragmentTransaction.commit();
                Server.setVisibility(view.INVISIBLE);
                ConnectToServer.setVisibility(view.INVISIBLE);
                Pair.setVisibility(view.INVISIBLE);
                Back.setVisibility(view.INVISIBLE);
                break;

            case "_back":
                Server.setVisibility(view.VISIBLE);
                ConnectToServer.setVisibility(view.VISIBLE);
                Pair.setVisibility(view.VISIBLE);
                Back.setVisibility(view.VISIBLE);
                visListe.setVisibility(view.INVISIBLE);
                lvDevices.setVisibility(view.INVISIBLE);
                discover.setVisibility(view.INVISIBLE);
                scan.setVisibility(view.INVISIBLE);
                _back.setVisibility(view.INVISIBLE);
                break;

            default:
                break;
        }
    }*/
}
