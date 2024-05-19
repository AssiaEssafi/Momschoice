package my.app.momschoice.customerFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import my.app.momschoice.R;

public class CustomerPassword extends AppCompatActivity {

    TextInputLayout currentLayout, newLayout, confirmLayout;
    EditText currentEditText;
    EditText newEditText;
    EditText confirmEditText;
    Button changeButton;
    TextView forgotText;
    String cur, ne, conf, email;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_password);

        currentLayout = findViewById(R.id.current_pwd_layout);
        newLayout = findViewById(R.id.new_pwd_layout);
        confirmLayout = findViewById(R.id.confirm_pwd_layout);
        changeButton = findViewById(R.id.change);
        forgotText = findViewById(R.id.forgot_pwd);

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Customer").child(userId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer customer = dataSnapshot.getValue(Customer.class);
                //email = customer.getEmailID();

                changeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cur = currentEditText.getText().toString().trim();
                        ne = newEditText.getText().toString().trim();
                        conf = confirmEditText.getText().toString().trim();

                        if (isValid()) {
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            AuthCredential credential = EmailAuthProvider.getCredential(email, cur);

                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(ne).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                    FirebaseDatabase.getInstance().getReference("Customer").child(userId).child("Password").setValue(ne);
                                                    FirebaseDatabase.getInstance().getReference("Customer").child(userId).child("ConfirmPassword").setValue(conf);

                                                    Toast.makeText(CustomerPassword.this, "Password updated", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(CustomerPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(CustomerPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

                forgotText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CustomerPassword.this, CustomerForgotpassword.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        currentEditText = currentLayout.getEditText();
        newEditText = newLayout.getEditText();
        confirmEditText = confirmLayout.getEditText();
    }

    public boolean isValid() {
        currentLayout.setError(null);
        newLayout.setError(null);
        confirmLayout.setError(null);

        boolean isValidNewPassword = false, isValidConfirmPassword = false, isValid = false;
        if (TextUtils.isEmpty(ne)) {
            newLayout.setError("New Password is required");
        } else {
            if (ne.length() < 6) {
                newLayout.setError("Password too weak");
            } else {
                isValidNewPassword = true;
            }
        }

        if (TextUtils.isEmpty(conf)) {
            confirmLayout.setError("Confirm Password is required");
        } else {
            if (!ne.equals(conf)) {
                confirmLayout.setError("Password doesn't match");
            } else {
                isValidConfirmPassword = true;
            }
        }
        isValid = isValidNewPassword && isValidConfirmPassword;
        return isValid;
    }
}
