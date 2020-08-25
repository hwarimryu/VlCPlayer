package com.example.vlcfullscreen.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vlcfullscreen.PrefsManager;
import com.example.vlcfullscreen.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class SettingActivity extends AppCompatActivity {
    @Inject
    PrefsManager prefsManager;

    Context context;
    RadioGroup radioGroup;
    EditText text_network, text_user;
    Button btn_confirm, btn_cancle;
    String screenType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_setting);


        text_network= (EditText) findViewById(R.id.network_text);

        text_user= (EditText) findViewById(R.id.user_text);

        radioGroup = (RadioGroup)findViewById(R.id.screentype_group);

        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int screenType =radioGroup.getCheckedRadioButtonId();

                prefsManager.put("SCREENTYPE",screenType);
                prefsManager.put("NETWORK", text_network.getText().toString());
                prefsManager.put("USER", text_user.getText().toString());
                Log.i("pref",prefsManager.getString("SCREENTYPE"));


                finish();
            }
        });
        btn_cancle = (Button) findViewById(R.id.btn_cancle);

        screenType = prefsManager.getString("SCREENTYPE");
        Log.i("===11====",screenType);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.radio_auto) screenType="AUTO";
                else if(i==R.id.radio_full) screenType="FULL";
                else if(i==R.id.radio_multi) screenType="MULTI";
                Log.i("=======",screenType);
            }
        });
//        text_network.
//        btn_confirm.setOnClickListener(confirmClicked);
//        radioGroup= (RadioGroup) findViewById(R.id.screentype_group);
//        network_text= (EditText) findViewById(R.id.network_text);
//        user_text = (EditText) findViewById(R.id.user_text);
////
//        network_text.setText(PreferenceManager.getNetwork(this));
//        user_text.setText(PreferenceManager.getUser(this));

//        network_text
        // Remember that you should never show the action bar if the
        // status bar is hidden, so
        setVisible(true);


    }
//    public View.OnClickListener confirmClicked = new View.OnClickListener() {
////    multiscreen에서 화면 한 개 클릭하면 그 화면 fullscreen으로
////    fullscreen에서 화면 클릭하면 다시 multiscreen으로 변경
//
//        @Override
//        public void onClick(View view) {
//            PreferenceManager.setNetwork(context,text_network.getText().toString());
//            PreferenceManager.setUser(context,text_user.getText().toString());
//            int screenType  = radioGroup.getCheckedRadioButtonId();
//            Log.i("===================",screenType+" ");
////                PreferenceManager.setScreentype(context,((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())));
//        }
//    };
//
//
//    public void confirmClicked(View view) {
//        PreferenceManager.setScreentype(context,screenType);
//        PreferenceManager.setNetwork(context, text_network.getText().toString());
//        PreferenceManager.setUser(context, text_user.getText().toString());
//        int screenType =radioGroup.getCheckedRadioButtonId();
//    }
}
