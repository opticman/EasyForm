package nakarin.birdssco.com.easyform.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import nakarin.birdssco.com.easyform.R;

/**
 * Created by opticman on 17-Sep-17.
 */

public class MyAlertDialog {

    private Context context;

    public MyAlertDialog(Context context) {
        this.context = context;
    }
    public void myDialog(String strTitle, String strMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_alert1);
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }



} // MainClass
