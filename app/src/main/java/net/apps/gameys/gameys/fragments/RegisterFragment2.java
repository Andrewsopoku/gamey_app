package net.apps.gameys.gameys.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.mikhaellopez.lazydatepicker.LazyDatePicker;

import net.apps.gameys.gameys.R;
import net.apps.gameys.gameys.util.PrefConstants;

import java.util.Date;

import fr.ganfra.materialspinner.MaterialSpinner;
import ivb.com.materialstepper.stepperFragment;


/**
 * Created by S.Shivasurya on 1/19/2016 - androidStudio.
 */
public class RegisterFragment2 extends stepperFragment {
    private static final String[] ITEMS = {"Personal", "Business"};

    private ArrayAdapter adapter;
    MaterialSpinner acctype;
    MaterialTextField email, cor,coc,ec;
    LazyDatePicker dob;
    Boolean dob_not_set = true;

    public RegisterFragment2(){

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(
                R.layout.registerfragment2, container, false);

        email = (MaterialTextField)view.findViewById(R.id.email);
        cor = (MaterialTextField)view.findViewById(R.id.cor);
        coc = (MaterialTextField)view.findViewById(R.id.coc);
        ec = (MaterialTextField)view.findViewById(R.id.ec);
        acctype = view.findViewById(R.id.acctype);


        dob = view.findViewById(R.id.dob);
        dob.setDateFormat(LazyDatePicker.DateFormat.DD_MM_YYYY);
       // dob.setMinDate(minDate);
       // dob.setMaxDate(maxDate);

        dob.setOnDatePickListener(new LazyDatePicker.OnDatePickListener() {
            @Override
            public void onDatePick(Date dateSelected) {
              dob_not_set = false;
            }
        });

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acctype.setAdapter(adapter);
        acctype.setPaddingSafe(0, 0, 0, 0);
       



        return view;
    }
    public boolean is_empty(MaterialTextField materialTextField){
        if(materialTextField.getEditText().length() < 1){
            return true;
        }
        return false;
    }
    public boolean check_next(){
        if(is_empty(email) || is_empty(cor) || is_empty(coc) || is_empty(ec) || dob_not_set ) {
            Toast.makeText(getContext(),"Please fill form completely",Toast.LENGTH_LONG).show();
            return false;

        }
        save_data();
        return true;
    }
    public void save_data(){
        PrefConstants.putAppPrefString(getContext(),"email",email.getEditText().getText().toString());
        PrefConstants.putAppPrefString(getContext(),"cor",cor.getEditText().getText().toString());
        PrefConstants.putAppPrefString(getContext(),"coc",coc.getEditText().getText().toString());
        PrefConstants.putAppPrefString(getContext(),"ec",ec.getEditText().getText().toString());
        PrefConstants.putAppPrefString(getContext(),"dob",dob.getDate().toString());
        PrefConstants.putAppPrefString(getContext(),"acctype",acctype.getSelectedItem().toString());


    }

    @Override
    public boolean onNextButtonHandler() {
        return check_next();
    }
}
