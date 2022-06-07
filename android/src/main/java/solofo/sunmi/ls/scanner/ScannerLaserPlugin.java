package solofo.sunmi.ls.scanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.annotation.CapacitorPlugin;

import com.honeywell.aidc.*;

@CapacitorPlugin(name = "ScannerLaser")
public class ScannerLaserPlugin extends Plugin implements
        BarcodeReader.BarcodeListener{

    private com.honeywell.aidc.AidcManager manager;
    private com.honeywell.aidc.BarcodeReader barcodeReader;

    private final BroadcastReceiver QRCODE_SUNMI = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String QRData = intent.getStringExtra("data");
            //notifyListeners("ScannerLaserListner", new JSObject().put("result", QRData), true);
        }
    };

    @PluginMethod()
    public void scan(PluginCall call) {
        if (barcodeReader != null) {
            try {
               barcodeReader.softwareTrigger(false);
            } catch (ScannerNotClaimedException e) {
                e.printStackTrace();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent event) {
      System.out.println("Barcode data: " + event.getBarcodeData());
      System.out.println("Character Set: " + event.getCharset());
      System.out.println("Code ID: " + event.getCodeId());
      System.out.println("AIM ID: " + event.getAimId());
      System.out.println("Timestamp: " + event.getTimestamp());
      String QRData = event.getBarcodeData();
      notifyListeners("ScannerLaserListner", new JSObject().put("result", QRData), true);
    }


    @Override
    public void onFailureEvent(BarcodeFailureEvent arg0) {
        // TODO Auto-generated method stub
        System.out.println("onFailureEvent: " + arg0.toString());
    }

    @Override
    public void load() {
        System.out.println("Registry plugin ScannerLaserPlugin");
        super.load();

        Context context = getContext().getApplicationContext();
        AidcManager.create(context, new AidcManager.CreatedCallback() {
          @Override
          public void onCreated(AidcManager aidcManager) {
            manager = aidcManager;
            barcodeReader = manager.createBarcodeReader();
            if (barcodeReader != null) {
              barcodeReader.addBarcodeListener(ScannerLaserPlugin.this);
              try {
                barcodeReader.claim();
              } catch (ScannerUnavailableException e) {
                e.printStackTrace();
              }
            }
          }
        });

        try {
            getContext().unregisterReceiver(QRCODE_SUNMI);
        } catch (Exception ignored) {
        }
        getContext().registerReceiver(QRCODE_SUNMI, new IntentFilter("com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED"));
    }
}
