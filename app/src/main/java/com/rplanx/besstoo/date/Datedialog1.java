package com.rplanx.besstoo.date;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by soumya on 7/5/17.
 */
public class Datedialog1  extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    TextView txtdate;
    int ni;
    //private  String month_name;

    public Datedialog1(){

    }
    public void getViews(View view){
        txtdate=(TextView) view;
    }

    @TargetApi(24)
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // SimpleDateFormat month_date = new SimpleDateFormat("MMMM");

        final Calendar c=Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        //  month_name = month_date.format(c.getTime());
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(),this,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis()+ 2*24*60*60*1000);
        return datePickerDialog;
    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        ni=i1 + 1;

        String date= i2 + "/" + ni + "/" + i;
        // String date=month_name + " " + i + ", " + i2;
        txtdate.setText(date);
    }
}
