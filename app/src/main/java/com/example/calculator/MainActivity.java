package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView backspaceBtn;
    TextView result, solution;
    MaterialButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b0, divBtn, mulBtn, modBtn, equalsBtn,
            plusBtn, minusBtn, pointBtn,clearBtn, openParanBtn, closeParanBtn;
    String expression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        result=findViewById(R.id.result);
        solution=findViewById(R.id.solution);
        backspaceBtn=findViewById(R.id.backspaceBtn);

        findView(b0,R.id.btn0);
        findView(b1,R.id.btn1);
        findView(b2,R.id.btn2);
        findView(b3,R.id.btn3);
        findView(b4,R.id.btn4);
        findView(b5,R.id.btn5);
        findView(b6,R.id.btn6);
        findView(b7,R.id.btn7);
        findView(b8,R.id.btn8);
        findView(b9,R.id.btn9);
        findView(divBtn,R.id.divideBtn);
        findView(mulBtn,R.id.multiplyBtn);
        findView(plusBtn,R.id.plusBtn);
        findView(minusBtn,R.id.minusBtn);
        findView(clearBtn,R.id.clearBtn);
        findView(modBtn,R.id.modBtn);
        findView(pointBtn,R.id.pointBtn);
        findView(equalsBtn,R.id.equalsBtn);
        findView(openParanBtn,R.id.openBracketBtn);
        findView(closeParanBtn,R.id.closedBracketBtn);

        backspaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression=expression.substring(0,expression.length()-1);
                solution.setText(expression);
                result.setText(expression);
            }
        });

    }

    void findView(MaterialButton btn, int id){
        btn=findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        MaterialButton btn=(MaterialButton) v;
        String btnText=btn.getText().toString();
        expression=solution.getText().toString();
        if(btnText.equals("C")){
            solution.setText("");
            result.setText("0");
            return;
        }
        if(btnText.equals("=")){
            solution.setText(result.getText());
            result.setText("");
            return;
        }

        expression+=btnText;
        solution.setText(expression);
        String finalResult=getResult(expression);
        if(!finalResult.equals("Error")){
            result.setText(finalResult);
        }
    }
    String getResult(String expression){
        try{
            Context context=Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable=context.initStandardObjects();
            String ans=context.evaluateString(scriptable,expression,"Javascript",1,null).toString();
            if(ans.endsWith(".0")){
                ans=ans.substring(0,ans.length()-2);
            }
            return ans;
        }catch (Exception e){
            return "Error";
        }
    }
}