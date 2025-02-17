package com.example.kingschefs;

import static com.example.kingschefs.OrdersFragment.ordersAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment implements homeRecyclerInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    public static historyAdapter historyAdapter;

    public static ArrayList<History> historyList;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataInitialize();
        recyclerView = view.findViewById(R.id.recyclerHistory);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerView.setHasFixedSize(true);
        historyAdapter = new historyAdapter(getContext(), historyList, (homeRecyclerInterface) this);
        historyAdapter.updateRT();
        recyclerView.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();
    }

    private void dataInitialize() {
        historyList = new ArrayList<History>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onItemClick(int position) {
        createPopUp(position);
    }
    private void createPopUp(int position){
        LayoutInflater inflater= (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popUpView =inflater.inflate(R.layout.historyviewpopup,null);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popUpView,width,height,focusable);
        recyclerView.post(new Runnable(){
            @Override
            public void run() {
                popupWindow.showAtLocation(recyclerView, Gravity.CENTER,0,0);
            }
        });
        TextView orderNumber = popUpView.findViewById(R.id.historyNumberPopUp);
        TextView orderName = popUpView.findViewById(R.id.historyDetailsName);
        TextView orderAmount = popUpView.findViewById(R.id.historyDetailsAmount);
        TextView orderTotal = popUpView.findViewById(R.id.historyTotal);
        Button cancel = popUpView.findViewById(R.id.goBack);
        History history = historyList.get(position);
        orderNumber.setText("Order#: "+ history.getOrderNo());
        orderName.setText(history.getOrderName());
        orderAmount.setText(history.getOrderAmount());
        orderTotal.setText("Total: " + history.getTotalPrice() +" Taka");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}