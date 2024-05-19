package my.app.momschoice;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;



import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.reactivex.annotations.NonNull;
import my.app.momschoice.customerFoodPanel.CustomerCartFragment;
import my.app.momschoice.customerFoodPanel.CustomerHomeFragment;
import my.app.momschoice.customerFoodPanel.CustomerOrdersFragment;
import my.app.momschoice.customerFoodPanel.CustomerProfileFragment;
import my.app.momschoice.customerFoodPanel.CustomerTrackFragment;
import my.app.momschoice.deliveryFoodPanel.DeliveryPendingOrderFragment;



import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;


public class CustomerFoodPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth Fauth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_food_panel_bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        // Vérifier si l'utilisateur est connecté et rediriger vers la page principale des clients
        Fauth = FirebaseAuth.getInstance();
        if (Fauth.getCurrentUser() != null) {
            if (Fauth.getCurrentUser().isEmailVerified()) {
                // Utilisateur connecté et vérifié, rediriger vers la page principale des clients
                redirectToCustomerMainPage();
            } else {
                // L'utilisateur n'est pas vérifié, afficher un message et le déconnecter
                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerFoodPanel_BottomNavigation.this);
                builder.setMessage("Check Whether You Have Verified Your Detail , Otherwise Please Verify");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    Intent intent = new Intent(CustomerFoodPanel_BottomNavigation.this, MainMenu.class);
                    startActivity(intent);
                    finish();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Fauth.signOut();
            }
        } else {
            // L'utilisateur n'est pas connecté, rediriger vers la page de connexion ou d'accueil
            Intent intent = new Intent(CustomerFoodPanel_BottomNavigation.this, MainMenu.class);
            startActivity(intent);
            finish();
        }

    }

    private void redirectToCustomerMainPage() {
        // Rediriger vers la page principale des clients
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        loadCustomerFragment(new CustomerHomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.cust_Home) {
            fragment = new CustomerHomeFragment();
        } else if (itemId == R.id.cust_profile) {
            fragment = new CustomerProfileFragment();
        } else if (itemId == R.id.Cust_order) {
            fragment = new CustomerOrdersFragment();
        } else if (itemId == R.id.track) {
            fragment = new CustomerTrackFragment();
        } else if (itemId == R.id.cart) {
            fragment = new CustomerCartFragment();
        }
        return loadCustomerFragment(fragment);
    }

    private boolean loadCustomerFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }
}
