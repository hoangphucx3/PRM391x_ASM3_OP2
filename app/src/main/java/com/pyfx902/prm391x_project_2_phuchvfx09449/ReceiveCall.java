package com.pyfx902.prm391x_project_2_phuchvfx09449;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class ReceiveCall extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    if (MainActivity.luuThongTin.contains(incomingNumber)) {
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = inflater.inflate(R.layout.custom_toast, null);
                        ImageView ivAvatar = view.findViewById(R.id.ivToastAvatar);
                        String imgPath = MainActivity.luuThongTin.getString(incomingNumber, "");
                        try {
                            Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open(imgPath));
                            ivAvatar.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast toast = new Toast(context);
                        toast.setView(view);
                        toast.setGravity(Gravity.BOTTOM, 0, 20);
                        toast.show();
                    }
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }
}
