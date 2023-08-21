package com.example.scanner_barcode;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {


    Button btnScan;
    EditText result_barcode;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = (Button) findViewById(R.id.btnScan);
        result_barcode = (EditText) findViewById(R.id.tv_result);
        btnScan.setOnClickListener(v->
        {
            ScanCode();
        });
    }

    private void ScanCode() {
        ScanOptions  options = new ScanOptions();
        options.setCameraId(0);
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureActivity.class);
        options.setDesiredBarcodeFormats(options.QR_CODE);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(),result->{
        if(result.getContents() != null)

        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Barcode ID");
            builder.setMessage(result.getContents());

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String contents = result.getContents();
                    result_barcode.setText(contents);
                    dialogInterface.dismiss();
                }
            }); builder.show();
        }
    });
}