package com.rplanx.besstoo.date;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by soumya on 14/4/17.
 */
public class Datedialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    EditText txtdate;
    int ni;
    //private  String month_name;

    public Datedialog(){

    }
    public void getViews(View view){
        txtdate=(EditText) view;
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
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
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
