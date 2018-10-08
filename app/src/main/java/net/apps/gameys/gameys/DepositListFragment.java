package net.apps.gameys.gameys;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.apps.gameys.gameys.util.Amount_Date;
import net.apps.gameys.gameys.util.Amount_DateAdapter;
import net.apps.gameys.gameys.util.PrefConstants;
import net.apps.gameys.gameys.util.SAppUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepositListFragment extends Fragment {
    private List<Amount_Date> amount_dateList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Amount_DateAdapter mAdapter;
    ProgressBar progressBar;
    TextView totaldepo;
    RequestQueue queue ;


    public DepositListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DepositListFragment newInstance(String param1, String param2) {
        DepositListFragment fragment = new DepositListFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_deposit_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar)v.findViewById(R.id.depolist_loader);
        mAdapter = new Amount_DateAdapter(amount_dateList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        totaldepo = (TextView)v.findViewById(R.id.totaldepos);
        queue =  Volley.newRequestQueue(getContext());

        getDepolist();

        return v;
    }




    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void getDepolist(){
        progressBar.bringToFront();
        progressBar.setVisibility(View.VISIBLE);
        String url = SAppUtil.baseurl+"/deposit-list";
        Log.d("Url",url);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        progressBar.setVisibility(View.GONE);
                        try {
                            Log.d("Response", response);

                            JSONObject detail = new JSONObject(response);

                            if( detail.getString("status").toString().equals("ok")){
                                totaldepo.setText(detail.getString("deposit_total"));
                                JSONArray depolist = new JSONArray();

                                depolist=detail.getJSONArray("deposit");
                                Log.d("1", "onResponse: "+depolist);;
                                Log.d("2", "onResponse: "+depolist.length());;
                                for(int i = 0; i < depolist.length(); i++) {
                                    Log.d("amam", "onResponse: "+depolist.get(i));;
                                    JSONObject jresponse = depolist.getJSONObject(i);
                                    Amount_Date amount_date = new Amount_Date(jresponse.getString("amount"),
                                            jresponse.getString("created_at"));
                                    amount_dateList.add(amount_date);

                                }
                                mAdapter.notifyDataSetChanged();
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


                return params;
            }
        };
        queue.add(postRequest);




    }

}
