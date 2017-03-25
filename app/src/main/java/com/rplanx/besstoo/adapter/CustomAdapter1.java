package com.rplanx.besstoo.adapter;

import android.content.Context;
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
import com.rplanx.besstoo.interfaces.CustomButtonListener1;
import com.rplanx.besstoo.interfaces.ModelDataSet1;
import com.squareup.picasso.Picasso;

/**
 * Created by soumya on 14/3/17.
 */
public class CustomAdapter1 extends BaseAdapter {
    CustomButtonListener1 customButtonListener;
    View hover;
    private ImageLoader mImageLoader;
    Context c;
    private final ModelDataSet1 productDataSet;
    private final LayoutInflater layoutInflater;

    public CustomAdapter1(Context c,ModelDataSet1 productDataSet,CustomButtonListener1 customButtonListener,LayoutInflater layoutInflater) {
        this.c=c;
        this.productDataSet = productDataSet;
        this.customButtonListener=customButtonListener;
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
            view1.setTag(NewHolder1.from(view1));
        }
        Model2 product = productDataSet.get(i);
        NewHolder1 viewHolder = (NewHolder1) view1.getTag();
        update(viewHolder, product);
        return view1;
    }

    private View createView(ViewGroup parent) {
        return layoutInflater.inflate(R.layout.listview_single_image2, parent, false);
    }

    private void update(final NewHolder1 viewHolder, final Model2 product) {
        hover = LayoutInflater.from(c).inflate(R.layout.hover_sample4, null);
        viewHolder.imageView.setScaleType(NetworkImageView.ScaleType.FIT_XY);
        Picasso.with(c).load(product.getImage()).fit().into(viewHolder.imageView);
        viewHolder.txt_quantity.setText(String.valueOf(product.getQuantity()));
       /* viewHolder.desc.setText(product.getDescription());*/
        viewHolder.txt_name.setText(product.getNames());
        viewHolder.no_of_item.setText("Quantity" + " " + product.getNo_of_item());
        viewHolder.rupee.setText("Rs." + " " + product.getRuppee() + "/-");
        viewHolder.blurLayout.setHoverView(hover);
        TextView textView = (TextView) hover.findViewById(R.id.content);
        textView.setText(product.getDescription());
        viewHolder.blurLayout.enableZoomBackground(true);
        viewHolder.blurLayout.setBlurDuration(1200);
        viewHolder.blurLayout.addChildAppearAnimator(hover, R.id.content, Techniques.BounceIn);
        viewHolder.blurLayout.addChildDisappearAnimator(hover, R.id.content, Techniques.FadeOutUp);
       viewHolder.plus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

               customButtonListener.onPlusClick(product);


            }
        });
        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              customButtonListener.onMinusClick(product);
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

        static NewHolder1 from(View view) {

            return new NewHolder1(
                    ((ImageView) view.findViewById(R.id.thumbnail)),
                    (ImageView) view.findViewById(R.id.minuss),
                    (ImageView)view.findViewById(R.id.plus),
                    ((TextView) view.findViewById(R.id.txt_count)),
                    ((TextView)view.findViewById(R.id.txt_rs)),
                    ((TextView)view.findViewById(R.id.number_of_item)),
                    ((BlurLayout)view.findViewById(R.id.blur_layout)),
                    ((TextView)view.findViewById(R.id.home_name))
            );

        }
        private NewHolder1(ImageView imageView, ImageView minus, ImageView plus, TextView counter, TextView rupee, TextView no_of_item,BlurLayout blurLayout,TextView txt_name) {
            this.imageView=imageView;
            this.minus=minus;
            this.plus=plus;
            this.txt_quantity=counter;
            this.rupee=rupee;
            this.no_of_item=no_of_item;
            this.blurLayout=blurLayout;
            this.txt_name=txt_name;

        }
    }
}
