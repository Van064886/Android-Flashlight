package vanandroid.first.flashlight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    ImageButton imgB;
    boolean state;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgB = findViewById(R.id.torch_off);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        runFlashLight();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText( MainActivity.this, "Permission de camera necessaire", Toast.LENGTH_SHORT ).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();
    }
    private void runFlashLight()
    {
        imgB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if ( !state )
                {
                    CameraManager cm = (CameraManager) getSystemService( Context.CAMERA_SERVICE );
                    try
                    {
                        String cmId = cm.getCameraIdList()[0];
                        cm.setTorchMode( cmId , true );
                        state = true;
                        imgB.setImageResource(R.drawable.fon);
                    }
                    catch ( CameraAccessException  e )
                    {}
                }
                else
                {
                    CameraManager cm = (CameraManager) getSystemService( Context.CAMERA_SERVICE );
                    try
                    {
                        String cmId = cm.getCameraIdList()[0];
                        cm.setTorchMode( cmId , false );
                        state = false;
                        imgB.setImageResource(R.drawable.foff);
                    }
                    catch ( CameraAccessException  e )
                    {}
                }

            }
        });
    }
}