package my.app.momschoice.customerFoodPanel;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import my.app.momschoice.CustomerFoodPanel_BottomNavigation;
import my.app.momschoice.UpdateDishModel;

public class ElegantNumberButton {
    private int dishPrice;
    private int dishQuantity;

    public void setNumber(int dishQuantity) {
        this.dishQuantity = dishQuantity;
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        // Gérer les changements de valeur ici...
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        // Gérer les clics ici...
    }

    public int getNumber() {
        return dishQuantity;
    }

    public static class OnValueChangeListener {
        private Cart cart;

        public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
            int num = newValue;
            int totalprice = num * view.dishPrice;
            if (num != 0) {
                // Utiliser la référence view.dishPrice pour accéder à la variable
                // Prix du plat dans la classe OnValueChangeListener
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("DishID", cart.getDishID());
                hashMap.put("DishName", cart.getDishName());
                hashMap.put("DishQuantity", String.valueOf(num));
                hashMap.put("Price", String.valueOf(view.dishPrice));
                hashMap.put("Totalprice", String.valueOf(totalprice));
                hashMap.put("ChefId", cart.getChefId());

                FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(cart.getDishID()).setValue(hashMap);
            } else {
                FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(cart.getDishID()).removeValue();
            }
        }
    }

    public static class OnClickListener {
        public void onClick(ElegantNumberButton view) {
            // Gérer l'événement de clic ici...
        }

        public void onClick(View view) {
            DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Gérer les données reçues ici...
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
