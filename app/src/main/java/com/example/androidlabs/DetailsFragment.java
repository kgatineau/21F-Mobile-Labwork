package com.example.androidlabs;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    Bundle dataFromActivity;
    private AppCompatActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        long id = dataFromActivity.getLong(ChatRoomActivity.ITEM_ID);

        View result = inflater.inflate(R.layout.fragment_details, container, false);
        TextView textView1_L07 = result.findViewById(R.id.textView1_L07);
        textView1_L07.setText(dataFromActivity.getString(ChatRoomActivity.ITEM_MSG));

        TextView textView2_L07 = result.findViewById(R.id.textView2_L07);
        textView2_L07.setText(getString(R.string.textView_text2_L07, String.valueOf(id)));

        CheckBox checkBox_L07 = result.findViewById(R.id.checkbox_L07);
        checkBox_L07.setChecked(dataFromActivity.getBoolean(ChatRoomActivity.ITEM_SEND_RECEIVE));
        Button button_L07 = result.findViewById((R.id.button_L07));
        button_L07.setOnClickListener(v -> {

                parentActivity.getSupportFragmentManager()
                        .beginTransaction()
                        .remove(this)
                        .commit();
            if (getActivity() instanceof EmptyActivity) {
                parentActivity.onBackPressed();
                /* if statement that allows the button to send back to the EmptyActivity in phone
                mode and prevents the blank screen from showing after the fragment closes while
                ensuring the fragment just closes without sending the activity back in tablet mode */
            }

            });

        // Inflate the layout for this fragment
        return result;

    }
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity) context;
    }
}