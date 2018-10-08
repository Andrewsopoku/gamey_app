package net.apps.gameys.gameys.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.materialtextfield.MaterialTextField;

import net.apps.gameys.gameys.R;
import net.apps.gameys.gameys.util.PrefConstants;

import ivb.com.materialstepper.stepperFragment;


/**
 * Created by S.Shivasurya on 1/19/2016 - androidStudio.
 */
public class RegisterFragment1 extends stepperFragment {

    MaterialTextField name,address,state,zip,tel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onNextButtonHandler() {

        return check_next();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.registerfragment1, container, false);
        name = (MaterialTextField)view.findViewById(R.id.name);
        address = (MaterialTextField)view.findViewById(R.id.address);
        state = (MaterialTextField)view.findViewById(R.id.state);
        zip = (MaterialTextField)view.findViewById(R.id.zip);
        tel = (MaterialTextField)view.findViewById(R.id.tel);




        return view;
    }
    public boolean is_empty(MaterialTextField materialTextField){
        if(materialTextField.getEditText().length() < 1){
            return true;
        }
        return false;
    }
    public boolean check_next(){
        if(is_empty(name) || is_empty(address) || is_empty(state)  || is_empty(tel)) {
            Toast.makeText(getContext(),"Please fill form completely",Toast.LENGTH_LONG).show();
            return false;

        }
        save_data();
        return true;
    }
    public void save_data(){
        PrefConstants.putAppPrefString(getContext(),"name",name.getEditText().getText().toString());
        PrefConstants.putAppPrefString(getContext(),"address",address.getEditText().getText().toString());
        PrefConstants.putAppPrefString(getContext(),"state",state.getEditText().getText().toString());
        PrefConstants.putAppPrefString(getContext(),"zip",zip.getEditText().getText().toString());
        PrefConstants.putAppPrefString(getContext(),"tel",tel.getEditText().getText().toString());


    }

}
