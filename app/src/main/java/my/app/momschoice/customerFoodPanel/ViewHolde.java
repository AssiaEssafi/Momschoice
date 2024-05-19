package my.app.momschoice.customerFoodPanel;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import my.app.momschoice.R;

class ViewHolder extends RecyclerView.ViewHolder {

    TextView dishname, PriceRs, Qty, Totalrs;
    ElegantNumberButton elegantNumberButton; // Change the type to ElegantNumberButton

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        dishname = itemView.findViewById(R.id.Dishname);
        PriceRs = itemView.findViewById(R.id.pricers);
        Qty = itemView.findViewById(R.id.qty);
        Totalrs = itemView.findViewById(R.id.totalrs);
        elegantNumberButton = itemView.findViewById(R.id.elegantbtn);
    }
}
