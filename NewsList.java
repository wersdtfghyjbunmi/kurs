package com.mirea.kt.nika;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewsList extends AppCompatActivity {

    private EditText etDepositAmount;
    private EditText etInterestRate;
    private EditText etTargetAmount;
    private EditText etIncreaseRate;
    private EditText etMonthlyAddition;
    private TextView tvResult;
    private Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        etDepositAmount = (EditText) findViewById(R.id.et_deposit_amount);
        etInterestRate = (EditText) findViewById(R.id.et_interest_rate);
        etTargetAmount = (EditText) findViewById(R.id.et_target_amount);
        etIncreaseRate = (EditText) findViewById(R.id.et_increase_rate);
        etMonthlyAddition = (EditText) findViewById(R.id.et_monthly_addition);
        tvResult = (TextView) findViewById(R.id.tv_result);
        btnCalculate = (Button) findViewById(R.id.btn_calculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double depositAmount = Double.parseDouble(etDepositAmount.getText().toString());
                double interestRate = Double.parseDouble(etInterestRate.getText().toString());
                double targetAmount = Double.parseDouble(etTargetAmount.getText().toString());
                double increaseRate = Double.parseDouble(etIncreaseRate.getText().toString());
                double monthlyAddition = Double.parseDouble(etMonthlyAddition.getText().toString());
                DecimalFormat myFormat = new DecimalFormat("#.##");
                int days = 0;
                int maxIterations = 30000; // максимальное число итераций
                while (depositAmount < targetAmount) {
                    depositAmount += (depositAmount ) * (interestRate / 100) / 365 ; // расчет процентов за месяц
                    targetAmount += targetAmount * (increaseRate / 100 / 365) ; // увеличение целевой суммы каждый год
                    depositAmount = (depositAmount ) + (monthlyAddition / 30);
                    days++;

                    if (days >= maxIterations && depositAmount < targetAmount) { // если превышено максимальное число итераций и целевая сумма все еще недостижима
                        String result = "Ищи себе другой банк/работу/квартиру";
                        tvResult.setText(result);
                        return;
                    }
                }

                String result = "Если не помрешь, накопишь через " + days + " дней";
               // tvResult.setText(result);
                Intent intent = new Intent(NewsList.this, result_activity.class);
                intent.putExtra("result", result);
                startActivity(intent);
            }
        });
    }
}