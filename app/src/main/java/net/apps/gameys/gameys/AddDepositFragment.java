package net.apps.gameys.gameys;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.apps.gameys.gameys.util.PrefConstants;
import net.apps.gameys.gameys.util.SAppUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddDepositFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddDepositFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDepositFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
TextView tota_depo;
Button add_depo;
TextInputEditText depo_amt;
ProgressBar adddepo_loader;
    RequestQueue queue ;


    public AddDepositFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddDepositFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddDepositFragment newInstance(String param1, String param2) {
        AddDepositFragment fragment = new AddDepositFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_deposit, container, false);
        tota_depo = (TextView)v.findViewById(R.id.depototal);
        tota_depo.setText(PrefConstants.getAppPrefString(getContext(),"total_deposit"));
        add_depo = (Button)v.findViewById(R.id.adddepo);
        depo_amt = (TextInputEditText)v.findViewById(R.id.damt);
        adddepo_loader = (ProgressBar)v.findViewById(R.id.add_depo_loader);
        queue =  Volley.newRequestQueue(getContext());


        add_depo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(depo_amt.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Amount can not be empty",Toast.LENGTH_SHORT).show();
                    return;
                }
add_depos();
            }
        });
        return v;
    }

    public void add_depos(){
        adddepo_loader.setVisibility(View.VISIBLE);
        adddepo_loader.bringToFront();

        String url = SAppUtil.baseurl+"/deposit-request";
        Log.d("Url",url);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        adddepo_loader.setVisibility(View.GONE);
                        try {
                            Log.d("Response", response);

                            JSONObject detail = new JSONObject(response);

                            if( detail.getString("status").toString().equals("ok")){
                                Toast.makeText(getContext(),"Request has been sent. Admin will contact you soon" +
                                        "",Toast.LENGTH_LONG).show();

                                   }
                            else
                                Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();




                        }
                        catch (JSONException v){
                            v.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                        Log.d("Error.Response", error.toString());
                        Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();


                params.put("user_id", PrefConstants.getAppPrefString(getContext(),"user_id"));
                params.put("amount", depo_amt.getText().toString());



                return params;
            }
        };
        queue.add(postRequest);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
