package fi.timomcfarlane.tellmewhen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.timomcfarlane.tellmewhen.data.AppointmentHandler;
import fi.timomcfarlane.tellmewhen.utils.CustomCardClickListener;

public class AppointmentListFragment extends Fragment {
    private AppointmentHandler appHandler;
    private RecyclerView recycledList;
    private RecyclerAdapter adapter;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.schedule_list_fragment,
                    container,
                    false);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appHandler = ((ScheduleActivity)getActivity()).getAppHandler();
        adapter = new RecyclerAdapter(
                getContext(),
                appHandler.getAppointments(),
                (v, position) -> {
                    if(position != RecyclerView.NO_POSITION) {
                        ((ScheduleActivity) getActivity()).viewDetailsFragment(position);
                    }
                }
        );
        recycledList = (RecyclerView) view.findViewById(R.id.recycle_list);
        recycledList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycledList.setAdapter(adapter);
    }
    public RecyclerView getList() {
        return this.recycledList;
    }
}
