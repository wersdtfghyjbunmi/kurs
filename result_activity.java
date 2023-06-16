package com.mirea.kt.nika;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class result_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity_list);
        Button shareButton =  findViewById(R.id.shareButton);
        String result = getIntent().getStringExtra("result");
        TextView tvResult = findViewById(R.id.tv_result);
        tvResult.setText(result);
        shareButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(sendIntent.ACTION_SEND);
                sendIntent.putExtra(sendIntent.EXTRA_TEXT,result);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"Отправить Сообщение"));
            }
        });
    }

}