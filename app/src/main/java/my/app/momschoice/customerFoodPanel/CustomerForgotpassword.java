package my.app.momschoice.customerFoodPanel;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import my.app.momschoice.R;
import my.app.momschoice.ReusableCodeForAll;

public class CustomerForgotpassword extends AppCompatActivity {
    TextInputLayout emaillid;
    Button Reset;
    FirebaseAuth Fauth;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_forgotpassword);

        emaillid = findViewById(R.id.email);
        Reset = findViewById(R.id.reset);

        Fauth = FirebaseAuth.getInstance();
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emaillid.getEditText().getText().toString();
                if (!email.isEmpty()) {
                    Fauth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerForgotpassword.this);
                                builder.setMessage("Password has been sent to your Email");
                                builder.setCancelable(false);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent bb = new Intent(CustomerForgotpassword.this, CustomerPassword.class);
                                        startActivity(bb);
                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            } else {
                                ReusableCodeForAll.ShowAlert(CustomerForgotpassword.this, "Error", task.getException().getMessage());
                            }
                        }
                    });
                } else {
                    // Show an error message if the email is empty
                    emaillid.setError("Email cannot be empty");
                }
            }
        });
    }
}
