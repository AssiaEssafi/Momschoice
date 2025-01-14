package my.app.momschoice.customerFoodPanel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;

import my.app.momschoice.R;

public class CustomerPhonenumber extends AppCompatActivity {

    EditText num;
    CountryCodePicker cpp;
    Button SendOTP;
    String number;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_phonenumber);

        // Assurez-vous que l'ID correspond à celui du layout XML
        num = (EditText) findViewById(R.id.phonenumber);
        cpp = (CountryCodePicker) findViewById(R.id.Countrycode);
        SendOTP = (Button) findViewById(R.id.sendotp);

        SendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = num.getText().toString().trim();
                String phonenumber = cpp.getSelectedCountryCodeWithPlus() + number;
                Intent intent = new Intent(CustomerPhonenumber.this, CustomerPhoneSendOTP.class);
                intent.putExtra("phonenumber", phonenumber);
                startActivity(intent);
                finish();
            }
        });
    }
}
