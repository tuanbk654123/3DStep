package com.example.a3dstep.View.Community;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a3dstep.Data.DatabaseClient;
import com.example.a3dstep.Data.Excercise;
import com.example.a3dstep.Data.ExcerciseAdapter;
import com.example.a3dstep.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentWorld#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWorld extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    public FragmentWorld() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentWorld.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentWorld newInstance(String param1, String param2) {
        FragmentWorld fragment = new FragmentWorld();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_world, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getTasks();
        return view;

    }


    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<Excercise>> {

            @Override
            protected List<Excercise> doInBackground(Void... voids) {
                List<Excercise> excerciseList = DatabaseClient
                        .getInstance(getActivity().getApplicationContext())
                        .getAppDatabase()
                        .excerciseDao()
                        .getAll();
                return excerciseList;
            }

            @Override
            protected void onPostExecute(List<Excercise> tasks) {
                super.onPostExecute(tasks);
                ExcerciseAdapter adapter = new ExcerciseAdapter(getActivity(), tasks);
                recyclerView.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

}
