package gr.uom.exams.androidexamsjan17;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Thodoris on 25-Jan-17.
 */

public class MerchantAdapter extends RecyclerView.Adapter<MerchantAdapter.ViewHolder> {

    private ArrayList<Merchant> merchants;
    private Context context;

    public MerchantAdapter(Context context, ArrayList<Merchant> objects) {
        this.merchants = objects;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_merchant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Merchant item = merchants.get(position);
        //holder.imageView.setImageResource(R.drawable.yummy_logo);
        Glide.with(context).load("").placeholder(R.drawable.yummy_logo).into(holder.imageView);
        holder.legalNameView.setText(item.getLegalName());
        holder.categoryNameView.setText(item.getCategory());
        holder.addressView.setText(item.getAddress());
        holder.ratingView.setText(item.getReview());

    }


    @Override
    public int getItemCount() {
        return merchants.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView imageView;
        public final TextView legalNameView;
        public final TextView categoryNameView;
        public final TextView addressView;
        public final TextView ratingView;

        public ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.list_item_merchant_image);
            legalNameView = (TextView)view.findViewById(R.id.list_item_merchant_legal_name);
            categoryNameView = (TextView)view.findViewById(R.id.list_item_merchant_category);
            addressView = (TextView)view.findViewById(R.id.list_item_merchant_address);
            ratingView = (TextView)view.findViewById(R.id.list_item_merchant_review);
        }
    }

    public void changeAdapter(ArrayList<Merchant> m){
        this.merchants.clear();
        this.merchants.addAll(m);
        notifyDataSetChanged();

    }


}
