package anton.obdandroidapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pires.obd.commands.SpeedCommand;
import com.github.pires.obd.commands.engine.LoadCommand;
import com.github.pires.obd.commands.engine.RPMCommand;
import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.LineFeedOffCommand;
import com.github.pires.obd.commands.protocol.SelectProtocolCommand;
import com.github.pires.obd.commands.protocol.TimeoutCommand;
import com.github.pires.obd.commands.temperature.AmbientAirTemperatureCommand;
import com.github.pires.obd.enums.ObdProtocols;
import java.io.IOException;
import static android.content.ContentValues.TAG;

/**
 * Created by user on 2017-03-28.
 */

public class ConnectedThread extends Thread {
    private final BluetoothSocket socket;
    private final BluetoothAdapter BA;
    public String rpmValue;
    private static MainActivity parent;


    public ConnectedThread(BluetoothDevice device, BluetoothAdapter Ba,MainActivity parent) {
        BA=Ba;
        BluetoothSocket tmp = null;
        this.parent = parent;

        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            tmp = device.createRfcommSocketToServiceRecord(device.getUuids()[0].getUuid());
        } catch (IOException e) {
            Log.e(TAG, "Socket's create() method failed", e);
        }
        socket = tmp;
    }
    public void run() {

        // Cancel discovery because it otherwise slows down the connection.
        BA.cancelDiscovery();
        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            socket.connect();
            if(socket.isConnected()){
                System.out.println("Connected");

                //Set listitem to green backgrund



                parent.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(parent.getBaseContext(), "Connected!", Toast.LENGTH_LONG).show();
                    }
                });

                parent.updateValues("20 RPM");


                new EchoOffCommand().run(socket.getInputStream(), socket.getOutputStream());
                new LineFeedOffCommand().run(socket.getInputStream(), socket.getOutputStream());
                new TimeoutCommand(125).run(socket.getInputStream(), socket.getOutputStream());
                new SelectProtocolCommand(ObdProtocols.AUTO).run(socket.getInputStream(), socket.getOutputStream());
                new AmbientAirTemperatureCommand().run(socket.getInputStream(), socket.getOutputStream());

                RPMCommand engineRpmCommand = new RPMCommand();
                SpeedCommand speedCommand = new SpeedCommand();
                LoadCommand loadCommand = new LoadCommand();


                while (!Thread.currentThread().isInterrupted()) {
                    loadCommand.run(socket.getInputStream(),socket.getOutputStream());
                    engineRpmCommand.run(socket.getInputStream(), socket.getOutputStream());
                    speedCommand.run(socket.getInputStream(), socket.getOutputStream());
                    // TODO handle commands result

                    rpmValue = engineRpmCommand.getCalculatedResult();



                    Log.d(TAG, "Engine Load: "+ loadCommand.getCalculatedResult());
                    Log.d(TAG, "RPM: " + engineRpmCommand.getCalculatedResult());
                    Log.d(TAG, "Speed: " + speedCommand.getFormattedResult());


                }

            }
            else{

                System.out.println("Not connected");
// reset current item to white again
            }
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                socket.close();
                System.out.println("Connection is closed");
                parent.runOnUiThread(new Runnable() {
                    public void run() {
                        parent.changeSatusConnected(0);
                        Toast.makeText(parent.getBaseContext(), "Connection is closed", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
    }
}