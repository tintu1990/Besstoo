package com.rplanx.besstoo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.rplanx.besstoo.R;
import com.rplanx.besstoo.constant.Constant;
import com.rplanx.besstoo.getset.Model2;
import com.rplanx.besstoo.interfaces.ModelDataSet;
import com.rplanx.besstoo.interfaces.PartyButtonListener1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by soumya on 23/5/17.
 */
public class Expendable_adapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private final Set<Pair<Long, Long>> mCheckedItems = new HashSet<Pair<Long, Long>>();
    PartyButtonListener1 customButtonListener;
    private final ModelDataSet partyDataSet;
    int start_i=0;
    int main_i=0;
    int desert_i=0;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor1;
    SharedPreferences.Editor editor2;
    SharedPreferences shr;
    SharedPreferences shref;
    SharedPreferences party_pref;
    TextView textView;
    View view1;
    String message="Please clear your cart to proceed";

    public Expendable_adapter(Context context,ModelDataSet partyDataSet,PartyButtonListener1 partyButtonListener, List<String> listDataHeader,
                                   HashMap<String, List<String>> listChildData,TextView textView) {
        this._context = context;
        this.partyDataSet=partyDataSet;
        this.customButtonListener=partyButtonListener;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.textView=textView;
    }
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this._listDataChild.get(this._listDataHeader.get(i))
                .size();
    }

    @Override
    public Object getGroup(int i) {
        return this._listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this._listDataChild.get(this._listDataHeader.get(i))
                .get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return (long) (i*1024);
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) view
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        final String childText = (String) getChild(i, i1);
        //view1= LayoutInflater.from(_context).inflate(R.layout.food_list1,false);
        // textView=(TextView)view1.findViewById(R.id.badge_notification_1);
        shref=_context.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        shr=_context.getSharedPreferences("MyPREFERENCES1",Context.MODE_PRIVATE);
        party_pref=_context.getSharedPreferences("Party_pref",Context.MODE_PRIVATE);


        final Model2 party=partyDataSet.get(i1);
        //  Color c = (Color)getChild( i, i1 );
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_item, null);
        }

        final Pair<Long, Long> tag = new Pair<Long, Long>(
                getGroupId(i),
                getChildId(i, i1));

        //  view.setPadding(0, 0, 0, 20);
        TextView txtListChild = (TextView) view
                .findViewById(R.id.lblListItem);

        //TextView rgb = (TextView)view.findViewById( R.id.rgb );
        final CheckBox cb = (CheckBox)view.findViewById( R.id.check1);
        cb.setChecked(mCheckedItems.contains(tag));
        cb.setTag(tag);
        cb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*if (shref.contains("myJson") || shr.contains("myJson")) {
                    clearcart();
                }*/
                // else {
                String child = (String) getChild(i, i1);
                String group = (String) getGroup(i);
                final CheckBox cb = (CheckBox) v;
                final Pair<Long, Long> tag = (Pair<Long, Long>) v.getTag();
                if (cb.isChecked()) {

                    if (shref.contains("myJson") || shr.contains("myJson")) {
                        clearcart(message);
                        cb.setChecked(false);
                    } else {


                        if (group.equals("Starters")) {
                            if (start_i < 5) {
                                start_i++;
                                mCheckedItems.add(tag);
                                customButtonListener.onPlusClick(party, child);
                            } else {
                                cb.setChecked(false);
                                Toast.makeText(_context, "you cannot select more than 5", Toast.LENGTH_SHORT).show();
                            }

                        }
                        if (group.equals("Main Course")) {
                            if (main_i < 4) {
                                main_i++;
                                mCheckedItems.add(tag);
                                customButtonListener.onPlusClick(party, child);
                            } else {
                                cb.setChecked(false);
                                Toast.makeText(_context, "you cannot select more than 4", Toast.LENGTH_SHORT).show();
                            }
                        }

                        if (group.equals("Deserts")) {
                            if (desert_i < 3) {
                                desert_i++;
                                mCheckedItems.add(tag);
                                customButtonListener.onPlusClick(party, child);
                            } else {
                                cb.setChecked(false);
                                Toast.makeText(_context, "you cannot select more than 3", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                } else {
                    if (group.equals("Deserts")) {
                        if (desert_i > 0) {
                            desert_i--;
                        }
                    }

                    if (group.equals("Main Course")) {
                        if (main_i > 0) {
                            main_i--;
                        }
                    }
                    if (group.equals("Starters")) {
                        if (start_i > 0) {
                            start_i--;
                        }
                    }
                    mCheckedItems.remove(tag);

                    customButtonListener.onMinusClick(party, child);
                }


                // }
            }
        });
        //cb.setChecked(false);
        txtListChild.setText(childText);
        return view;
    }

    private  void  clearcart(String message){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(_context,R.style.MyAlertDialogStyle);
        //alertDialog.setTitle(_context.getResources().getString(R.string.app_name));
        TextView image = new TextView(_context);
        image.setGravity(Gravity.CENTER|Gravity.BOTTOM);
        // image.setImageResource(R.drawable.besstoo_pop);
        image.setTextSize(15);
        image.setTextColor(Color.RED);
        image.setBackgroundResource(R.drawable.besstoo_pop);
        image.setText(message);
        alertDialog.setView(image);
       // alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.logo);
        alertDialog.setNegativeButton("clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                editor=shr.edit();
                editor.clear().apply();
                editor2=party_pref.edit();
                editor2.clear().apply();
                editor1=shref.edit();
                editor1.clear().apply();
                Constant.counter=0;
                Constant.exercise_arraylist1.clear();
                Constant.exercise_arraylist2.clear();
                textView.setVisibility(View.INVISIBLE);

            }
        });

        alertDialog.setPositiveButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alertDialog.show();
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    public void onGroupCollapsed (int groupPosition) {
        super.onGroupCollapsed(groupPosition);

    }
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }


}
