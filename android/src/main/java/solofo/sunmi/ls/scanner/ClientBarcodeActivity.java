package solofo.sunmi.ls.scanner;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.honeywell.aidc.*;

public class ClientBarcodeActivity extends Activity implements BarcodeReader.BarcodeListener,
  BarcodeReader.TriggerListener {

  private com.honeywell.aidc.BarcodeReader barcodeReader;
  private Boolean mode = false;

  @SuppressLint("ResourceAsColor")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_barcode);

    // get bar code instance from MainActivity
    barcodeReader = ScannerLaserPlugin.getBarcodeObject();

    if (barcodeReader != null) {

      // register bar code event listener
      barcodeReader.addBarcodeListener(this);

      // set the trigger mode to client control
      try {
        barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
          BarcodeReader.TRIGGER_CONTROL_MODE_CLIENT_CONTROL);
      } catch (UnsupportedPropertyException e) {
        Toast.makeText(this, "Failed to apply properties", Toast.LENGTH_SHORT).show();
      }
      // register trigger state change listener
      barcodeReader.addTriggerListener(this);

      Map<String, Object> properties = new HashMap<String, Object>();
      // Set Symbologies On/Off
      properties.put(BarcodeReader.PROPERTY_EAN_13_CHECK_DIGIT_TRANSMIT_ENABLED, true);
      properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, false);
      properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, false);
      properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
      properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, false);
      properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, false);
      properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, false);
      properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, true);
      properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, false);
      properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, false);
      properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, false);
      properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, false);
      // Set Max Code 39 barcode length
      properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 39);
      // Turn on center decoding
      properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);
      // Disable bad read response, handle in onFailureEvent
      properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, false);
      // Sets time period for decoder timeout in any mode
      properties.put(BarcodeReader.PROPERTY_DECODER_TIMEOUT,  400);
      // Apply the settings
      barcodeReader.setProperties(properties);
    }


    btnRaffaleSetting();
  }

  @SuppressLint("ResourceAsColor")
  private void btnRaffaleSetting(){
    Button btnRaffale = (Button) findViewById(R.id.raffale);
    Bundle b = getIntent().getExtras();
    int _mode = b.getInt("mode");
    System.out.println(_mode);
    if(_mode == 1)
      mode = true;
    else
      mode = false;
    if(!mode){
      btnRaffale.setText("Mode raffale off");
    }else{
      btnRaffale.setText("Mode raffale on");
    }

    btnRaffale.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mode = !mode;
        if(!mode){
          btnRaffale.setText("Mode raffale off");
        }else{
          btnRaffale.setText("Mode raffale on");
        }
        sendBroadcast(new Intent("solofo.raffale.mode").putExtra("data", mode ? 1 : 0));
      }
    });

    Button btncancel = (Button) findViewById(R.id.cancel);

    btncancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendBroadcast(new Intent("solofo.destroy"));
        finish();
      }
    });
  }


  @Override
  public void onBarcodeEvent(final BarcodeReadEvent event) {
    String code = event.getBarcodeData();
    sendBroadcast(new Intent("solofo.barcode").putExtra("data", code));

    if(!mode) {
      finish();
    }else{
      runOnUiThread(new Runnable() {

        @Override
        public void run() {
          Toast.makeText(ClientBarcodeActivity.this, "1 quantité ajoutée: "+code, Toast.LENGTH_SHORT).show();
        }
      });
    }
  }

  // When using Automatic Trigger control do not need to implement the
  // onTriggerEvent function
  @Override
  public void onTriggerEvent(TriggerStateChangeEvent event) {
    try {
      // only handle trigger presses
      // turn on/off aimer, illumination and decoding
      barcodeReader.aim(event.getState());
      barcodeReader.light(event.getState());
      barcodeReader.decode(event.getState());

    } catch (ScannerNotClaimedException e) {
      e.printStackTrace();
      Toast.makeText(this, "Scanner is not claimed", Toast.LENGTH_SHORT).show();
    } catch (ScannerUnavailableException e) {
      e.printStackTrace();
      Toast.makeText(this, "Scanner unavailable", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onFailureEvent(BarcodeFailureEvent arg0) {
    runOnUiThread(new Runnable() {

      @Override
      public void run() {
        Toast.makeText(ClientBarcodeActivity.this, "No data", Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    if (barcodeReader != null) {
      try {
        barcodeReader.claim();
      } catch (ScannerUnavailableException e) {
        e.printStackTrace();
        Toast.makeText(this, "Scanner unavailable", Toast.LENGTH_SHORT).show();
      }
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    if (barcodeReader != null) {
      // release the scanner claim so we don't get any scanner
      // notifications while paused.
      barcodeReader.release();
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (barcodeReader != null) {
      // unregister barcode event listener
      barcodeReader.removeBarcodeListener(this);

      // unregister trigger state change listener
      barcodeReader.removeTriggerListener(this);
    }
  }
}
