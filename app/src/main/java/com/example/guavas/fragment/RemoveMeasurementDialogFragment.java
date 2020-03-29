package com.example.guavas.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.guavas.R;
import com.example.guavas.data.DataType;
import com.example.guavas.data.model.MedicalRecord;
import com.example.guavas.firebaseDAO.MeasurementDAO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class RemoveMeasurementDialogFragment extends DialogFragment {

    private static final String DATATYPE_KEY = "dtk";
    private static final String TIME_KEY = "tk";
    private static final String MEASUREMENT_KEY = "mk";

    private double measurement;
    private long timeInMillis;
    private DataType dataType;

    public RemoveMeasurementDialogFragment() {
    }

    public static RemoveMeasurementDialogFragment newInstance(double measurement, long timeInMillis, DataType dataType) {

        Bundle args = new Bundle();
        args.putDouble(MEASUREMENT_KEY, measurement);
        args.putLong(TIME_KEY, timeInMillis);
        args.putParcelable(DATATYPE_KEY, dataType);

        RemoveMeasurementDialogFragment fragment = new RemoveMeasurementDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            measurement = args.getDouble(MEASUREMENT_KEY);
            timeInMillis = args.getLong(TIME_KEY);
            dataType = args.getParcelable(DATATYPE_KEY);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(setDialogLayout());

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeFromDatabase();
            }
        });

        return builder.create();
    }

    private View setDialogLayout(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View parent = inflater.inflate(R.layout.remove_dialog, null);

        TextView measurementText = parent.findViewById(R.id.text_dialog_measurement);
        measurementText.setText(String.format(Locale.getDefault(), "%.2f %s", measurement, dataType.getMeasurementUnit()));

        TextView dateTimeText = parent.findViewById(R.id.text_dialog_datetime);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(timeInMillis);
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT);
        dateTimeText.setText(format.format(calendar.getTime()));

        TextView commentText = parent.findViewById(R.id.text_comment);
        commentText.setText(getComment());

        return parent;
    }

    private String getComment(){
        if (measurement >= dataType.getMinNormal() && measurement <= dataType.getMaxNormal())
            return getResources().getString(R.string.comment_normal);
        else
            return String.format(Locale.getDefault(), getResources().getString(R.string.comment_abnormal),
                    (int)dataType.getMinNormal(), (int)dataType.getMaxNormal());

    }

    private void removeFromDatabase(){
        MeasurementDAO dao = new MeasurementDAO(getActivity(), dataType);
        dao.delete(new MedicalRecord(timeInMillis, measurement));
    }
}
