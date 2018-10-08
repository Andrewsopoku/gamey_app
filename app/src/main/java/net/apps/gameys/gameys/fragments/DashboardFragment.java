package net.apps.gameys.gameys.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flyco.roundview.RoundTextView;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import net.apps.gameys.gameys.HomeActivity;
import net.apps.gameys.gameys.R;
import net.apps.gameys.gameys.util.PrefConstants;
import net.apps.gameys.gameys.util.SAppUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    RequestQueue queue ;
    RefreshLayout refreshLayout;
    private int mOffset = 0;
    private int mScrollY = 0;
    TextView depo,gain,loss,username;
    public DashboardFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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

                View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        queue =  Volley.newRequestQueue(getContext());
        depo = (TextView)view.findViewById(R.id.amount);
        gain = (TextView)view.findViewById(R.id.gain);
        loss = (TextView)view.findViewById(R.id.loss);
        username = (TextView)view.findViewById(R.id.userhomename);
        username.setText(PrefConstants.getAppPrefString(getContext(),"userhomename"));

        final NestedScrollView scrollView = (NestedScrollView)view.findViewById(R.id.scrollView);
        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                // refreshLayout.finishLoadMore(2000);
                Toast.makeText(getContext(),"refresh",Toast.LENGTH_LONG).show();

            }
        });

        //Picasso.with(getContext()).load(SharedPreferenceInfoUtil.getPhoto(getContext())).into(profilepichome);
        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
            }
            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
            }
        });
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(170);
            private int color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    lastScrollY = scrollY;
                }

            }
        });

        get_summary();
        return view;
    }

    public void get_summary(){
        refreshLayout.autoRefresh();
        String url = SAppUtil.baseurl+"/summary";
        Log.d("Url",url);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        refreshLayout.finishRefresh();
                        try {
                            Log.d("Response", response);

                            JSONObject detail = new JSONObject(response);

                            if( detail.getString("status").toString().equals("ok")){

                                depo.setText(detail.getString("total_deposit"));
                                PrefConstants.putAppPrefString(getContext(),"total_deposit", detail.getString("total_deposit"));
                                gain.setText(detail.getString("total_gain"));
                                loss.setText(detail.getString("total_loss"));
                                username.setText(detail.getString("name"));
                            }
                            else
                                Toast.makeText(getContext(),"Unable to load details",Toast.LENGTH_SHORT).show();




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
                        refreshLayout.finishRefresh();
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

}
