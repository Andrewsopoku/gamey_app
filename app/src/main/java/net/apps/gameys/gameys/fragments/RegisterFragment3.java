package net.apps.gameys.gameys.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.florent37.materialtextfield.MaterialTextField;

import net.apps.gameys.gameys.ActivitytoFragment;
import net.apps.gameys.gameys.R;
import net.apps.gameys.gameys.util.PrefConstants;
import net.apps.gameys.gameys.util.SAppUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ivb.com.materialstepper.stepperFragment;


public class RegisterFragment3 extends stepperFragment implements ActivitytoFragment{

    MaterialTextField username, pass;
     RelativeLayout mainlayer;
     ProgressBar progressBar;
     Button registerbut;

    RequestQueue queue;

    @Override
    public boolean onNextButtonHandler() {
    Toast.makeText(getContext(),"Please click on register button",Toast.LENGTH_SHORT).show();
        return false;
    }
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.registerfragment3, container, false);

        username = (MaterialTextField)view.findViewById(R.id.username);
        pass = (MaterialTextField)view.findViewById(R.id.pass);
        mainlayer = (RelativeLayout)view.findViewById(R.id.mainlayer);
        progressBar = (ProgressBar)view.findViewById(R.id.progesscheck);
        registerbut = (Button)view.findViewById(R.id.registerbut);

        registerbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_next();
                //save_data();
                //save_to_server();
            }
        });

        queue = Volley.newRequestQueue(getActivity());
                return view;
            }

    public boolean is_empty(MaterialTextField materialTextField){
        if(materialTextField.getEditText().length() < 1){
            return true;
        }
        return false;
    }
    public void check_next(){
        if(is_empty(username) || is_empty(pass)  ) {
            Toast.makeText(getContext(),"Please fill form completely",Toast.LENGTH_LONG).show();


        }
        progressBar.setVisibility(View.VISIBLE);
        mainlayer.setVisibility(View.GONE);
        save_data();
        save_to_server();

    }
    public void save_data() {
        PrefConstants.putAppPrefString(getContext(), "username", username.getEditText().getText().toString());
        PrefConstants.putAppPrefString(getContext(), "pass", pass.getEditText().getText().toString());

    }

    public void save_to_server(){
        String url = SAppUtil.baseurl+"/register";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        progressBar.setVisibility(View.GONE);
                        mainlayer.setVisibility(View.VISIBLE);
                        try{
                            JSONObject userinfo = new JSONObject(response);
                            Log.d("INfo", userinfo.toString());

                            if(userinfo.get("status") .toString().equals("ok")){
                                PrefConstants.putAppPrefString(getContext(),"user_id",userinfo.getString("user_id"));

                                getActivity().finish();
                            }
                            else{

                                Toast.makeText(getContext(),"User details already exist",Toast.LENGTH_LONG).show();
                            }
                            Log.d("INfo", userinfo.toString());
                        }
                        catch (JSONException y){
                            y.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        progressBar.setVisibility(View.GONE);
                        mainlayer.setVisibility(View.VISIBLE);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();



                params.put("name", PrefConstants.getAppPrefString(getContext(),"name").toString());
                params.put("address", PrefConstants.getAppPrefString(getContext(),"address").toString());
                params.put("state", PrefConstants.getAppPrefString(getContext(),"state").toString());
                params.put("zipcode", PrefConstants.getAppPrefString(getContext(),"zip").toString());
                params.put("phone", PrefConstants.getAppPrefString(getContext(),"tel").toString());

                params.put("email", PrefConstants.getAppPrefString(getContext(),"email").toString());
                params.put("country_of_residence", PrefConstants.getAppPrefString(getContext(),"cor").toString());
                params.put("country_of_citizenship", PrefConstants.getAppPrefString(getContext(),"coc").toString());
                params.put("emergency_contact", PrefConstants.getAppPrefString(getContext(),"ec").toString());
                params.put("date_of_birth", PrefConstants.getAppPrefString(getContext(),"dob").toString());

                params.put("username", PrefConstants.getAppPrefString(getContext(),"username").toString());
                params.put("password", PrefConstants.getAppPrefString(getContext(),"pass").toString());
                params.put("account_type", PrefConstants.getAppPrefString(getContext(),"acctype").toString());


                return params;
            }
        };
        queue.add(postRequest);

    }

    @Override
    public void communicate(boolean s) {


        check_next();
    }


}


