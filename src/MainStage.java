import android.app.Activity;
import android.os.Bundle;
import com.mcc.ul.DaqDevice;
import com.mcc.ul.DaqDeviceManager;


public class MainStage extends Activity {

    private DaqDevice mDaqDevice;
    private DaqDeviceManager mDaqDeviceManager;

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        //setContentView(android.R.layout.activity_main);

        initActivity();

        mDaqDevice = null;
        mDaqDeviceManager = new DaqDeviceManager(this);

    }

    public void initActivity() {
        //init variables here
    }

    @Override
    public void onDestroy() {
        synchronized(this) {
            if(mDaqDevice != null) {
                mDaqDeviceManager.releaseDaqDevice(mDaqDevice);
            }
            mDaqDevice = null;
        }
        super.onDestroy();
    }



}
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        initActivity();
//
//        mDaqDevice = null;
//        mDaqDeviceManager = new DaqDeviceManager(this);
//
//        mScanStatusTimer = null;
//
//        mDiscoveryInfoDlg = new NetDiscoveryInfoDialog();
//        mDiscoveryInfoDlg.setNoticeDialogListener(new DiscoveryInfoEvents());
//    }
//
