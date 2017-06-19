package com.rplanx.besstoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.androidviewhover.BlurLayout;
import com.rplanx.besstoo.BitmapLruCache;
import com.rplanx.besstoo.R;
import com.rplanx.besstoo.application.VolleyApplication;
import com.rplanx.besstoo.getset.Model2;
import com.rplanx.besstoo.interfaces.CustomButtonListener1;
import com.rplanx.besstoo.interfaces.ModelDataSet1;
import com.squareup.picasso.Picasso;

/**
 * Created by soumya on 14/3/17.
 */
public class CustomAdapter1 extends BaseAdapter {
    CustomButtonListener1 customButtonListener;
    View hover;
     ImageLoader mImageLoader;
    Context c;
    private final ModelDataSet1 productDataSet;
    private final LayoutInflater layoutInflater;
    int flag;

    public CustomAdapter1(Context c,ModelDataSet1 productDataSet,CustomButtonListener1 customButtonListener,int flag,LayoutInflater layoutInflater) {
        this.c=c;
        this.productDataSet = productDataSet;
        this.customButtonListener=customButtonListener;
        this.layoutInflater=layoutInflater;
        this.flag=flag;
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
            view1.setTag(NewHolder1.from(view1));
        }

        NewHolder1 viewHolder = (NewHolder1) view1.getTag();
        Model2 product = productDataSet.get(i);
        update(viewHolder, product);
        return view1;
    }

    private View createView(ViewGroup parent) {
        return layoutInflater.inflate(R.layout.listview_single_image2, parent, false);
    }

    private void update(final NewHolder1 viewHolder, final Model2 product) {

        viewHolder.imageView.setScaleType(NetworkImageView.ScaleType.FIT_XY);
        Picasso.with(c).load(product.getImage()).placeholder(R.drawable.besstoo_loadings).fit().into(viewHolder.imageView);
        viewHolder.txt_quantity.setText(String.valueOf(product.getQuantity()));
       /* viewHolder.desc.setText(product.getDescription());*/
        viewHolder.txt_name.setText(product.getStr_food_name());
        if (flag==1){
            viewHolder.no_of_item.setText(product.getFrom_kitchen());
        }
        else if (flag==2){
            viewHolder.no_of_item.setText(product.getDescription());
        }
        else{
            viewHolder.no_of_item.setText(product.getDescription());
        }

       // hover = LayoutInflater.from(c).inflate(R.layout.hover_sample4, null);
       /* hover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.blurLayout.setHoverView(hover);
                viewHolder.blurLayout.enableZoomBackground(true);
                viewHolder.blurLayout.setBlurDuration(1200);
                viewHolder.blurLayout.addChildAppearAnimator(hover, R.id.content, Techniques.BounceIn);
                viewHolder.blurLayout.addChildDisappearAnimator(hover, R.id.content, Techniques.FadeOutUp);
                TextView textView = (TextView) hover.findViewById(R.id.content);
                textView.setText(product.getDescription());
            }
        });*/

        viewHolder.rupee.setText(c.getResources().getString(R.string.Rs) + " " + product.getRuppee() + "/-");
       /* viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewHolder.blurLayout.enableZoomBackground(true);
                viewHolder.blurLayout.setBlurDuration(1200);
                viewHolder.blurLayout.addChildAppearAnimator(hover, R.id.content, Techniques.BounceIn);
                viewHolder.blurLayout.addChildDisappearAnimator(hover, R.id.content, Techniques.FadeOutUp);
            }
        });*/
        try {


            if (Integer.parseInt(product.getNo_of_item()) <= 0) {
                viewHolder.stock_empty.setVisibility(View.VISIBLE);
            } else {
                viewHolder.stock_empty.setVisibility(View.GONE);
            }
        }catch ( java.lang.NumberFormatException e){
            e.printStackTrace();
        }

        viewHolder.plus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

               customButtonListener.onPlusClick(product);


            }
        });
        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n1=product.getQuantity();
                if (product.getQuantity()>0){
                    customButtonListener.onMinusClick(product);
                }

            }
        });
    }

    private static final class NewHolder1{
        ImageView imageView;
        TextView txt_quantity;
        ImageView plus;
        ImageView minus;
        TextView rupee;
        TextView no_of_item;
        BlurLayout blurLayout;
        TextView txt_name;
        TextView stock_empty;
        RelativeLayout relativeLayout;

        static NewHolder1 from(View view) {

            return new NewHolder1(
                    ((ImageView) view.findViewById(R.id.thumbnail)),
                    (ImageView) view.findViewById(R.id.minuss),
                    (ImageView)view.findViewById(R.id.plus),
                    ((TextView) view.findViewById(R.id.txt_count)),
                    ((TextView)view.findViewById(R.id.txt_rs)),
                    ((TextView)view.findViewById(R.id.number_of_item)),
                    ((BlurLayout)view.findViewById(R.id.blur_layout)),
                    ((TextView)view.findViewById(R.id.home_name)),
                    ((TextView)view.findViewById(R.id.stock_empty)),
                    ((RelativeLayout)view.findViewById(R.id.rl1))
                    );

        }
        private NewHolder1(ImageView imageView, ImageView minus, ImageView plus, TextView counter, TextView rupee, TextView no_of_item,BlurLayout blurLayout,TextView txt_name,TextView stock_empty,RelativeLayout relativeLayout) {
            this.imageView=imageView;
            this.minus=minus;
            this.plus=plus;
            this.txt_quantity=counter;
            this.rupee=rupee;
            this.no_of_item=no_of_item;
            this.blurLayout=blurLayout;
            this.txt_name=txt_name;
            this.stock_empty=stock_empty;
            this.relativeLayout=relativeLayout;

        }
    }
}
