package net.apps.gameys.gameys;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import net.apps.gameys.gameys.fragments.RegisterFragment2;
import net.apps.gameys.gameys.fragments.RegisterFragment3;
import net.apps.gameys.gameys.fragments.RegisterFragment1;
import net.apps.gameys.gameys.util.PrefConstants;

import java.util.ArrayList;
import java.util.List;

import ivb.com.materialstepper.progressMobileStepper;

;

public class MainActivity extends progressMobileStepper {
    private ActivitytoFragment mCallback;


    List<Class> stepperFragmentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RegisterFragment3 fr = new RegisterFragment3();
        FragmentManager fm = getFragmentManager();
        mCallback = (ActivitytoFragment) fr;
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        

    }

        @Override
    public List<Class> init() {
        stepperFragmentList.add(RegisterFragment1.class);
        stepperFragmentList.add(RegisterFragment2.class);
        stepperFragmentList.add(RegisterFragment3.class);
        stepperFragmentList.add(RegisterFragment3.class);

        return stepperFragmentList;


    }


    @Override
    public void onStepperCompleted() {

    sendData(true);
    }

    private void sendData(boolean comm)
    {
        mCallback.communicate(comm);

    }

}
