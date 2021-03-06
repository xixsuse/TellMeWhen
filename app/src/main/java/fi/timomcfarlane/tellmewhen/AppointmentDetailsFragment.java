package fi.timomcfarlane.tellmewhen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import fi.timomcfarlane.tellmewhen.data.model.AppointmentAlarm;

import static fi.timomcfarlane.tellmewhen.utils.DateManipulationUtils.createVerbalDate;
/**
 * Class displays details of a specific appointment inside a Fragment.
 *
 * @author  Timo McFarlane
 * @version 1.0
 * @since   2014-04-24
 */
public class AppointmentDetailsFragment extends Fragment {
    private View view;
    private TextView title;
    private TextView date;
    private TextView time;
    private TextView address;
    private TextView notes;
    private ImageView categoryImg;
    private RelativeLayout categoryContainer;
    private RelativeLayout close;
    private ImageView delete;
    private ImageView edit;
    private RecyclerView list;
    private DetailsRecyclerAdapter adapter;

    /**
     * onCreate application inits views and view clicklisteners.
     * Fill views with provided appointment data.
     * @param inflater default
     * @param container default
     * @param savedInstanceState default
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schedule_detail_fragment, container, false);
        initViews();
        initClickListeners();
        title.setText(getArguments().getString("title"));
        date.setText(createVerbalDate(getArguments().getString("date")));
        time.setText(getArguments().getString("time"));
        address.setText(getArguments().getString("address"));
        notes.setText(getArguments().getString("notes"));
        initCategoryImage(getArguments().getString("category"));
        return view;
    }

    /**
     * onViewCreated add recyclerlist and adapter to display appointment alarms in a list
     * @param view default
     * @param savedInstanceState default
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new DetailsRecyclerAdapter((ArrayList<AppointmentAlarm>) getArguments().get("alarms"));
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Add clicklisteners to views for closing, deleting and editing appointment
     */
    public void initClickListeners() {
        close.setOnClickListener((View v) -> {
            ((ScheduleActivity)getActivity()).showListFragment();
        });
        delete.setOnClickListener((View v) -> {
            ((ScheduleActivity)getActivity())
                    .deleteAppointment(getArguments().getInt("position"));
        });

        edit.setOnClickListener((View v) -> {
            ((ScheduleActivity)getActivity())
                    .editAppointmentAtPosition(getArguments().getInt("position"));
        });
    }

    /**
     * Initialize layout views
     */
    public void initViews() {
        title = (TextView) view.findViewById(R.id.details_title);
        date = (TextView) view.findViewById(R.id.details_date);
        time = (TextView) view.findViewById(R.id.details_time);
        address = (TextView) view.findViewById(R.id.details_address);
        notes = (TextView) view.findViewById(R.id.details_notes_content);
        categoryImg = (ImageView) view.findViewById(R.id.details_category_img);
        categoryContainer = (RelativeLayout) view.findViewById(R.id.detail_category_wrapper);
        close = (RelativeLayout) view.findViewById(R.id.details_close);
        delete = (ImageView) view.findViewById(R.id.details_delete_btn);
        edit = (ImageView) view.findViewById(R.id.details_edit_btn);
        list = (RecyclerView) view.findViewById(R.id.details_alarms);
    }

    /**
     * Set category image to display correct image and background depending on given param
     * @param category Indicates what type of appointment
     */
    public void initCategoryImage(String category) {
        switch(category) {
            case "work":
                categoryContainer.setBackgroundResource(R.color.work);
                categoryImg.setImageResource(R.drawable.work_24dp);
                break;
            case "social":
                categoryContainer.setBackgroundResource(R.color.social);
                categoryImg.setImageResource(R.drawable.social_24dp);
                break;
            case "family":
                categoryContainer.setBackgroundResource(R.color.family);
                categoryImg.setImageResource(R.drawable.family_24dp);
                break;
            case "personal":
                categoryContainer.setBackgroundResource(R.color.personal);
                categoryImg.setImageResource(R.drawable.personal_24dp);
                break;
        }
    }
}
