package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private int[] buttonNomor = {R.id.nol, R.id.satu, R.id.dua, R.id.tiga, R.id.empat, R.id.lima, R.id.enam, R.id.tujuh, R.id.delapan, R.id.sembilan};
    private int[] buttonOperator = {R.id.tambah, R.id.kurang, R.id.kali, R.id.bagi};
    private TextView txtHasil;
    private boolean akhirNomor;
    private boolean stateError;
    private boolean titikAkhir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.txtHasil = (TextView) findViewById(R.id.total);
        setbuttonNomorOnClickListener();
        setbuttonOperatorOnClickListener();

    }

    private void setbuttonNomorOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                if (stateError) {
                    txtHasil.setText(button.getText());
                    stateError = false;
                } else {
                    txtHasil.append(button.getText());
                }
                akhirNomor = true;
            }
        };
        for (int id : buttonNomor) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setbuttonOperatorOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (akhirNomor && !stateError) {
                    Button button = (Button) view;
                    txtHasil.append(button.getText());
                    akhirNomor = false;
                    titikAkhir = false;
                }
            }
        };
        for (int id : buttonOperator) {
            findViewById(id).setOnClickListener(listener);
        }
        findViewById(R.id.titik).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (akhirNomor && !stateError && !titikAkhir) {
                    txtHasil.append(".");
                    akhirNomor = false;
                    stateError = false;
                    titikAkhir = false;
                }
            }
        });
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHasil.setText("");
                akhirNomor = false;
                stateError = false;
                titikAkhir = false;
            }
        });
        findViewById(R.id.hasil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEqual();
            }
        });
    }

    private void onEqual() {
        if (akhirNomor && !stateError) {
            String txt = txtHasil.getText().toString();
            Expression expression = new ExpressionBuilder(txt).build();
            try {
                double hasil = expression.evaluate();
                txtHasil.setText(Double.toString(hasil));
                titikAkhir = true;
            } catch (ArithmeticException ex) {
                txtHasil.setText("Error");
                stateError = true;
                akhirNomor = true;
            }
        }
    }
}
