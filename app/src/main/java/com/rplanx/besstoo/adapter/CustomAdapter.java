package com.rplanx.besstoo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidviewhover.BlurLayout;
import com.rplanx.besstoo.BitmapLruCache;
import com.rplanx.besstoo.R;
import com.rplanx.besstoo.application.VolleyApplication;
import com.rplanx.besstoo.getset.Model2;
import com.rplanx.besstoo.interfaces.CustomButtonListener;
import com.rplanx.besstoo.interfaces.ModelDataSet;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by soumya on 24/2/17.
 */
public class CustomAdapter extends BaseAdapter{
    ImageLoader mImageLoader;
    CustomButtonListener customButtonListener;
    View hover;
   // NewHolder newHolder;
    Context c;
    private final ModelDataSet productDataSet;
   // Model2 model2;
    private final LayoutInflater layoutInflater;
    String str_time="15:00:00";
    String str_time_for_next="16:00:00";
    int FLAG_DAY=0;
   // int q;
   // public ArrayList quantity = new ArrayList();

    public CustomAdapter(Context c, ModelDataSet modelDataSet, CustomButtonListener customButtonListener,LayoutInflater layoutInflater) {
        this.c=c;
        this.customButtonListener=customButtonListener;
        this.productDataSet=modelDataSet;
        this.layoutInflater=layoutInflater;
        mImageLoader=new ImageLoader(VolleyApplication.getInstance().getRequestQueue(),new BitmapLruCache());
    }

    @Override
    public int getCount() {
        return productDataSet.size();
    }

    @Override
    public Object getItem(int i) {
        return productDataSet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return productDataSet.getId(i);
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View view1 = view;
        if (view1 == null) {
            view1 = createView(viewGroup);
            view1.setTag(NewHolder.from(view1));
        }
        Model2 product = productDataSet.get(i);

        NewHolder viewHolder = (NewHolder) view1.getTag();

            update(viewHolder, product);

        return view1;
    }

    private void update(final NewHolder viewHolder, final Model2 product) {

       // hover = LayoutInflater.from(c).inflate(R.layout.hover_sample4, null);
        viewHolder.imageView.setScaleType(NetworkImageView.ScaleType.FIT_XY);
        @SuppressWarnings("UnusedAssignment") String m = c.getString(R.string.quantity);
        Picasso.with(c).load(product.getImage()).placeholder(R.drawable.besstoo_loadings).fit().into(viewHolder.imageView);
        viewHolder.plus.setVisibility(View.VISIBLE);
        viewHolder.minus.setVisibility(View.VISIBLE);
       // viewHolder.no_of_item.setVisibility(View.VISIBLE);
        viewHolder.txt_quantity.setVisibility(View.VISIBLE);
        viewHolder.txt_quantity.setText(String.valueOf(product.getQuantity()));
        viewHolder.desc.setText(product.getStr_food_name());
        viewHolder.no_of_item.setText(product.getDescription());
        viewHolder.rupee.setText(c.getResources().getString(R.string.Rs)  + " " + product.getRuppee() + "/-");

        Calendar to_date = Calendar.getInstance();

        if(to_date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            FLAG_DAY=1;
            System.out.println("OK");
        }
        else {
            FLAG_DAY=0;
        }
        /*viewHolder.blurLayout.setHoverView(hover);
        TextView textView = (TextView) hover.findViewById(R.id.content);
        textView.setText(product.getDescription());*/
        /*viewHolder.blurLayout.enableZoomBackground(true);
        viewHolder.blurLayout.setBlurDuration(1200);
        viewHolder.blurLayout.addChildAppearAnimator(hover, R.id.content, Techniques.BounceIn);
        viewHolder.blurLayout.addChildDisappearAnimator(hover, R.id.content, Techniques.FadeOutUp);*/
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
        //final String formattedDate = df.format(c.getTime());
        final  String str_current_time= dateFormat.format(c.getTime());
        Date d1 = null;
        Date d2 = null;
        Date d3= null;
        try {
            d1 = dateFormat.parse(str_time);
            d2 = dateFormat.parse(str_current_time);
            d3=dateFormat.parse(str_time_for_next);

            //in milliseconds
            long diff = d1.getTime() - d2.getTime();
            long diffs=d3.getTime()-d2.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            Log.e("diff",String.valueOf(diffs));
            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");

           // if (!product.getForDay().equals("Saturday")) {
            if (FLAG_DAY==0){
                if (diff < 0 && diffs > 0) {
                    viewHolder.stock_empty.setVisibility(View.VISIBLE);
                    viewHolder.stock_empty.setText("You cannot order after 3 pm");
                } else {
                    if (Integer.parseInt(product.getNo_of_item()) <= 0) {
                        viewHolder.stock_empty.setVisibility(View.VISIBLE);
                        viewHolder.stock_empty.setText("Sorry this item is out of stock");
                    } else {
                        viewHolder.stock_empty.setVisibility(View.GONE);
                    }
                }
            }
            else{
                if (diff<0){
                    viewHolder.stock_empty.setVisibility(View.VISIBLE);
                    viewHolder.stock_empty.setText("You cannot order after 3 pm");
                }
                else{
                    if (Integer.parseInt(product.getNo_of_item()) <= 0) {
                        viewHolder.stock_empty.setVisibility(View.VISIBLE);
                        viewHolder.stock_empty.setText("Sorry this item is out of stock");
                    } else {
                        viewHolder.stock_empty.setVisibility(View.GONE);
                    }
                }
            }

         //   }

           /**/


        } catch (Exception e) {
            e.printStackTrace();
        }
        /* if (Integer.parseInt(product.getNo_of_item())<=0){
           viewHolder.stock_empty.setVisibility(View.VISIBLE);
       }
        else{
           viewHolder.stock_empty.setVisibility(View.GONE);
       }*/
        viewHolder.plus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    customButtonListener.onPlusClick(product);
                }
            });
            viewHolder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  if (Constant.counter>0){
                    int n1=product.getQuantity();
                    if (product.getQuantity()>0){
                        customButtonListener.onMinusClick(product);
                    }
                    //  }

                }
            });
    }
    private View createView(ViewGroup parent) {
        return layoutInflater.inflate(R.layout.listview_single_image, parent, false);
    }

    private static final class NewHolder{
        ImageView imageView;
        TextView txt_quantity;
        ImageView plus;
        ImageView minus;
        TextView desc;
        TextView rupee;
        TextView no_of_item;
        BlurLayout blurLayout;
        TextView stock_empty;

       static NewHolder from(View view) {

           return new NewHolder(
                   ((ImageView) view.findViewById(R.id.thumbnail)),
                   (ImageView) view.findViewById(R.id.minuss),
                   (ImageView)view.findViewById(R.id.plus),
                   ((TextView) view.findViewById(R.id.txt_count)),
                   ((TextView)view.findViewById(R.id.txt_meal_own)),
                   ((TextView)view.findViewById(R.id.txt_rs)),
                   ((TextView)view.findViewById(R.id.number_of_item)),
                   ((BlurLayout)view.findViewById(R.id.blur_layout)),
                   ((TextView)view.findViewById(R.id.stock_empty))

           );
       }
    private NewHolder(ImageView imageView, ImageView minus, ImageView plus, TextView counter, TextView desc, TextView rupee, TextView no_of_item,BlurLayout blurLayout,TextView stock_empty) {
        this.imageView=imageView;
        this.minus=minus;
        this.plus=plus;
        this.txt_quantity=counter;
        this.desc=desc;
        this.rupee=rupee;
        this.no_of_item=no_of_item;
        this.blurLayout=blurLayout;
        this.stock_empty=stock_empty;

    }
    }
}
