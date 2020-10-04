package com.example.a3dstep.View;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.a3dstep.Model.Data;

import com.example.a3dstep.Model.RankYear;
import com.example.a3dstep.R;
import com.example.a3dstep.View.Community.CommunityFragment;

import com.example.a3dstep.Adapter.RankYearAdapter;


import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
	private static final int Item_Count = 15;
	SwipeRefreshLayout refreshLayout;

	ListView lvRank;
	ArrayList<RankYear> rankYearArrayList;
	RankYearAdapter rankYearAdapter;
	String[] users = { "Day","Week", "Month", "Year", };
    public RankFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankFragment newInstance(String param1, String param2) {
        RankFragment fragment = new RankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
	private Context mContext;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mContext = context;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mContext = null;
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
	    // Lookup the swipe container view
	    View view = inflater.inflate(R.layout.fragment_rank, container, false);



	    addControl(view);
	    rankYearArrayList = new ArrayList<>();
	    /**
	     * @param MainActivity.this
	     * @param R.layout.item
	     * @param bookList
	     * */
	    rankYearAdapter = new RankYearAdapter(getActivity(),R.layout.item_ranking,rankYearArrayList);
	    lvRank.setAdapter(rankYearAdapter);
	    createData();
	    return view;
    }

	private void addControl(View view) {
		//initialize SwipeRefreshLayout
		refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
		//set colors for loading progressbar
		refreshLayout.setColorSchemeColors(0xFFFF0000, 0xFF00FF00);
		//initialize ListView
		lvRank = (ListView) view.findViewById(R.id.lvRankList);

		Spinner spin = (Spinner) view.findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, users);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
		spin.setOnItemSelectedListener(this);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		//OnRefreshListener executes when layout is pull down to perform a refresh
		refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				startRefresh();
			}
		});

	}

	//method to add content to listview while refresh
	private void startRefresh() {
		new BackgroundTask().execute();
	}

	//this method executes after loading contents
	private void onRefreshComplete(List result) {
		//clear the existing adapter
	/*	listAdapter.clear();
		//add new list to adapter
		for (Object county : result) {
			listAdapter.add(county);
		}*/

		refreshLayout.setRefreshing(false);
	}
	/** Add data to bookList*/
	public void createData() {
		RankYear rankYear = new RankYear("Kỳ Án Ánh Trăng",1,123);
		rankYearArrayList.add(rankYear);
		rankYear = new RankYear("Án",2,123567);
		rankYearArrayList.add(rankYear);
		rankYear = new RankYear("Ánh",3,12463);
		rankYearArrayList.add(rankYear);
		rankYear = new RankYear("Trăng",4,146723);
		rankYearArrayList.add(rankYear);
		rankYear = new RankYear("Kỳ Án Ánh ",5,123476);
		rankYearArrayList.add(rankYear);
		rankYearAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
		Toast.makeText(mContext, "Selected User: "+users[position] ,Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}

	//Async task to load data
	//here a timer is implemented in doInBackground
	private class BackgroundTask extends AsyncTask {
		//duration of the swipe feature
		static final int Duration = 1000;

		@Override
		protected void onPostExecute(Object o) {
			super.onPostExecute(o);
			//after finishing doInbackground OnRefreshComplete is called
			onRefreshComplete((List) o);
		}

		@Override
		protected Object doInBackground(Object[] objects) {
			try {
				Thread.sleep(Duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return Data.randomList(Item_Count);
		}
	}

}