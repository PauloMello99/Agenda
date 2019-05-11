package com.example.agenda.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.agenda.providers.UserDAO;

public class SMSReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");
        SmsMessage sms = SmsMessage.createFromPdu(pdu,formato);
        String telefone = sms.getDisplayOriginatingAddress();
        UserDAO userDAO = new UserDAO(context);
        if(userDAO.isUser(telefone))Toast.makeText(context, "Usuário mandou mensagem!", Toast.LENGTH_LONG).show();
    }
}
