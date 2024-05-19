package my.app.momschoice.customerFoodPanel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import my.app.momschoice.MainMenu;
import my.app.momschoice.R;

public class CustomerProfileFragment extends Fragment {

    String[] Maharashtra = {"Mumbai", "Pune", "Aurangabad"};
    String[] Gujarat = {"Ahmedabad", "Rajkot", "Surat"};

    EditText firstname, lastname, address;
    Spinner stateSpinner;
    TextView mobileno, email;
    Button updateButton;
    LinearLayout passwordLayout, logoutLayout;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customerprofile, container, false);

        getActivity().setTitle("Profile");

        firstname = view.findViewById(R.id.fnamee);
        lastname = view.findViewById(R.id.lnamee);
        address = view.findViewById(R.id.address);
        email = view.findViewById(R.id.emailID);
        stateSpinner = view.findViewById(R.id.statee);
        mobileno = view.findViewById(R.id.mobilenumber);
        updateButton = view.findViewById(R.id.update);
        passwordLayout = view.findViewById(R.id.passwordlayout);
        logoutLayout = view.findViewById(R.id.logout_layout);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Customer").child(userId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer customer = dataSnapshot.getValue(Customer.class);
                if (customer != null) {
                    firstname.setText(customer.getFirstName());
                    lastname.setText(customer.getLastName());
                    address.setText(customer.getLocalAddress());
                    email.setText(customer.getEmailId());

                    ArrayAdapter<String> adapter;
                    if ("Maharashtra".equals(customer.getState())) {
                        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Maharashtra);
                    } else {
                        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Gujarat);
                    }
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    stateSpinner.setAdapter(adapter);
                    stateSpinner.setSelection(adapter.getPosition(customer.getState()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle update button click
            }
        });

        passwordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomerPassword.class);
                startActivity(intent);
            }
        });

        mobileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomerPhonenumber.class);
                startActivity(intent);
            }
        });

        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), MainMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return view;
    }
}
