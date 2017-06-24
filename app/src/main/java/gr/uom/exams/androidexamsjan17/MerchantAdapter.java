package gr.uom.exams.androidexamsjan17;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Thodoris on 25-Jan-17.
 */

public class MerchantAdapter extends RecyclerView.Adapter<MerchantAdapter.MerchantHolder> {

    private ArrayList<Merchant> merchants;
    private Context context;

    public MerchantAdapter(Context context, ArrayList<Merchant> objects) {
        this.merchants = objects;
        this.context = context;
    }


    @Override
    public MerchantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_merchant, parent, false);

        return new MerchantHolder(view);
    }

    @Override
    public void onBindViewHolder(MerchantHolder holder, int position) {

        holder.imageView.setImageResource(R.drawable.yummy_logo);
        holder.legalNameView.setText(merchants.get(position).getLegalName());
        holder.categoryNameView.setText(merchants.get(position).getCategory());
        holder.addressView.setText(merchants.get(position).getAddress());
        holder.ratingView.setText(merchants.get(position).getReview());

    }

    @Override
    public int getItemCount() {
        return merchants.size();
    }

    class MerchantHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView legalNameView;
        private TextView categoryNameView;
        private TextView addressView;
        private TextView ratingView;

        public MerchantHolder(View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.list_item_merchant_image);
            legalNameView = (TextView)itemView.findViewById(R.id.list_item_merchant_legal_name);
            categoryNameView = (TextView)itemView.findViewById(R.id.list_item_merchant_category);
            addressView = (TextView)itemView.findViewById(R.id.list_item_merchant_address);
            ratingView = (TextView)itemView.findViewById(R.id.list_item_merchant_review);
        }
    }

    public void reloadList(ArrayList<Merchant> merchants){
        this.merchants.addAll(merchants);
    }


}
