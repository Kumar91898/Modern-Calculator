package com.kumar.moderncalculator;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kumar.moderncalculator.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private double firstNum = 0, secondNum, result = 0;
    private String operation, operatorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Button> operands = new ArrayList<>();
        operands.add(binding.one);
        operands.add(binding.two);
        operands.add(binding.three);
        operands.add(binding.four);
        operands.add(binding.five);
        operands.add(binding.six);
        operands.add(binding.seven);
        operands.add(binding.eight);
        operands.add(binding.nine);
        operands.add(binding.zero);

        ArrayList<Button> operators = new ArrayList<>();
        operators.add(binding.plus);
        operators.add(binding.minus);
        operators.add(binding.multiply);
        operators.add(binding.div);

        binding.ac.setOnClickListener(v -> resetCalculator());

        for (Button b : operands){
            b.setOnClickListener(v -> {
                String buttonText = b.getText().toString();
                String calculationText = binding.calculationText.getText().toString();

                if (!calculationText.equals("0")){
                    binding.calculationText.setText(calculationText + buttonText);
                } else {
                    binding.calculationText.setText(buttonText);
                }
            });
        }

        for (Button b : operators) {
            b.setOnClickListener(view -> {
                operatorText = b.getText().toString();
                operation = b.getText().toString();

                if (result != 0){
                    firstNum = result;
                } else {
                    firstNum = Double.parseDouble(binding.calculationText.getText().toString());
                }

                binding.calculationText.setText("0");
            });
        }

        binding.dot.setOnClickListener(view -> {
            String screenText = binding.calculationText.getText().toString();
            if (!screenText.contains(".")) {
                binding.calculationText.append(".");
            }
        });

        binding.del.setOnClickListener(view -> {
            String num = binding.calculationText.getText().toString();
            if (num.length() > 1) {
                binding.calculationText.setText(num.substring(0, num.length() - 1));
            } else {
                binding.calculationText.setText("0");
            }
        });

        binding.equal.setOnClickListener(v -> calculateResult());
    }

    private void resetCalculator() {
        binding.calculationText.setText("0");
        binding.resultText.setText("0");
        firstNum = 0;
        result = 0;
        operation = null;
        operatorText = null;
    }

    private void calculateResult() {
        try {
            if (operation != null && !binding.calculationText.getText().toString().equals("0") && firstNum != 0) {
                secondNum = Double.parseDouble(binding.calculationText.getText().toString());

                switch (operation) {
                    case "/":
                        result = firstNum / secondNum;
                        break;
                    case "*":
                        result = firstNum * secondNum;
                        break;
                    case "+":
                        result = firstNum + secondNum;
                        break;
                    case "-":
                        result = firstNum - secondNum;
                        break;
                }

                binding.resultText.setText(String.valueOf(result));
                binding.calculationText.setText(String.valueOf(firstNum) + operatorText + String.valueOf(secondNum));
                firstNum = result;  // Update firstNum for subsequent calculations
            } else {
                Toast.makeText(this, "Enter operands first!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            Log.d(TAG, "calculateResult: "+e);
        }
    }
}
