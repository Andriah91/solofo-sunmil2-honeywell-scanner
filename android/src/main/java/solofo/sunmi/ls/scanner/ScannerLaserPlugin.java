package solofo.sunmi.ls.scanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;


import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import com.honeywell.aidc.*;

@CapacitorPlugin(name = "ScannerLaser")
public class ScannerLaserPlugin extends Plugin {

    private static BarcodeReader barcodeReader;
    private AidcManager manager;

    private final BroadcastReceiver QRCODE_SUNMI = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        String QRData = intent.getStringExtra("data");
        notifyListeners("ScannerLaserListner", new JSObject().put("result", QRData), true);
      }
    };

    private final BroadcastReceiver QRCODE_HONEYWELL = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
          String QRData = intent.getStringExtra("data");
          notifyListeners("ScannerLaserListner", new JSObject().put("result", QRData), true);
        }
      };

    private final BroadcastReceiver MODE_SUNMI = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        Integer data = intent.getIntExtra("data", 0);
        notifyListeners("ModeRaffaleListner", new JSObject().put("result", data), true);
      }
    };

    private final BroadcastReceiver destroyWindow = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        notifyListeners("OnDestroyListner",new JSObject().put("result", null), true);
      }
    };

    @PluginMethod()
    public void scan(PluginCall call, int raffaleMode) {
      Intent barcodeIntent = new Intent("android.intent.action.CLIENTACTIVITY");
      barcodeIntent.putExtra("mode", raffaleMode);
      getActivity().startActivity(barcodeIntent);
    }

    @PluginMethod()
    public void stop(PluginCall call) {

    }

    @Override
    public void load() {
        System.out.println("Registry plugin ScannerLaserPlugin");
        super.load();

        AidcManager.create(getContext(), new AidcManager.CreatedCallback() {

        @Override
        public void onCreated(AidcManager aidcManager) {
          manager = aidcManager;
          try{
            barcodeReader = manager.createBarcodeReader();
          }
          catch (InvalidScannerNameException e){
            Toast.makeText(getContext(), "Invalid Scanner Name Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
          }
          catch (Exception e){
            Toast.makeText(getContext(), "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
          }
        }
      });

        try {
            getContext().unregisterReceiver(QRCODE_HONEYWELL);
            getContext().unregisterReceiver(QRCODE_SUNMI);
            getContext().unregisterReceiver(MODE_SUNMI);
            getContext().unregisterReceiver(destroyWindow);
        } catch (Exception ignored) {
        }
      getContext().registerReceiver(QRCODE_HONEYWELL, new IntentFilter("solofo.barcode"));
      getContext().registerReceiver(QRCODE_SUNMI, new IntentFilter("com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED"));
      getContext().registerReceiver(MODE_SUNMI, new IntentFilter("solofo.raffale.mode"));
      getContext().registerReceiver(destroyWindow, new IntentFilter("solofo.destroy"));
    }

    static BarcodeReader getBarcodeObject() {
    return barcodeReader;
  }
}
