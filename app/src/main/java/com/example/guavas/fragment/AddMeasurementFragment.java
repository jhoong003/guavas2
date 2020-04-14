package com.example.guavas.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.guavas.R;
import com.example.guavas.data.entity.DataType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A fragment to add a new measurement for a chosen medical data type.
 */
public class AddMeasurementFragment extends Fragment {

    public static final String DATATYPE_KEY = "datatypekey";

    private DataType dataType;
    private Listener listener;
    private Calendar calendar;
    private DateFormat dateFormatter;
    private DateFormat timeFormatter;

    private View parent;
    private TextView dateText;
    private TextView timeText;
    private EditText measurementEditText;


    public AddMeasurementFragment(){

    }

    /**
     * Gets a new instance of this fragment.
     * @param dataType the chosen medical data type.
     * @return a new instance of this fragment.
     */
    public static AddMeasurementFragment newInstance(DataType dataType) {
        Bundle args = new Bundle();
        args.putParcelable(DATATYPE_KEY, dataType);
        AddMeasurementFragment fragment = new AddMeasurementFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Retrieves data when created.
     * @param savedInstanceState the state to retrieve.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) dataType = args.getParcelable(DATATYPE_KEY);
    }

    /**
     * Inflates layout and setup the fragment.
     *
     * @param inflater           the inflater.
     * @param container          the container.
     * @param savedInstanceState the saved state.
     * @return the user interface.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent =  inflater.inflate(R.layout.fragment_add_measurement, container, false);
        setupTitle();
        setupCloseButton();
        setupFloatingButton();
        setupForm();
        loadPreviousState(savedInstanceState);
        return parent;
    }

    /**
     * Loads the last saved state.
     * @param savedInstanceState the last saved state.
     */
    private void loadPreviousState(Bundle savedInstanceState){
        if (savedInstanceState != null){
            dateText.setText(savedInstanceState.getString("date"));
            timeText.setText(savedInstanceState.getString("time"));
            measurementEditText.setText(savedInstanceState.getString("measurement"));
        }
    }

    /**
     * Sets up the title with the medical data type.
     */
    private void setupTitle(){
        TextView textView = parent.findViewById(R.id.text_data_type);
        textView.setText(dataType.getDataTypeName());
    }

    /**
     * Sets up a working close button.
     */
    private void setupCloseButton(){
        ImageButton imageButton = (ImageButton)parent.findViewById(R.id.image_button_close);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onClickClose();
            }
        });
    }

    /**
     * Sets up a working floating button that submits the form on click.
     */
    private void setupFloatingButton(){
        FloatingActionButton floatingActionButton = parent.findViewById(R.id.fab_add_measurement);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickDone(v,
                        Double.parseDouble(measurementEditText.getText().toString()),
                        calendar.getTimeInMillis());
            }
        });
    }

    /**
     * Sets up the form to be filled.
     */
    private void setupForm(){
        fillDefaultValue();
        setupDatePicker();
        setupTimePicker();
        measurementEditText = (EditText)parent.findViewById(R.id.edit_text_measurement);
        measurementEditText.setHint(dataType.getMeasurementUnit());
    }

    /**
     * Sets the default value of date and time to the current time.
     */
    private void fillDefaultValue(){
        calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        dateText = (TextView)parent.findViewById(R.id.text_date);
        timeText = (TextView)parent.findViewById(R.id.text_time);
        dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());
        timeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());
        setTextViewText(dateText, dateFormatter);
        setTextViewText(timeText, timeFormatter);
    }

    /**
     * Sets the time to current time
     * @param textView the text view holding the current time
     * @param dateFormat the format of the text.
     */
    private void setTextViewText(TextView textView, DateFormat dateFormat){
        textView.setText(dateFormat.format(calendar.getTime()));
    }

    /**
     * Sets up a working DatePicker.
     */
    private void setupDatePicker(){
        ImageButton editDateImageButton = (ImageButton) parent.findViewById(R.id.image_button_date);
        editDateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    /**
     * Sets up a working TimePicker.
     */
    private void setupTimePicker(){
        ImageButton editTimeImageButton = (ImageButton) parent.findViewById(R.id.image_button_time);
        editTimeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
    }

    /**
     * Displays the DatePicker to the user.
     */
    private void showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setTextViewText(dateText, dateFormatter);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * Displays the TimePicker to the user.
     */
    private void showTimePicker(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                setTextViewText(timeText, timeFormatter);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }

    /**
     * Checks if the form is valid.
     * @return <code>true</code> if the form is valid.
     */
    public boolean isValidForm(){
        if(!measurementEditText.getText().toString().isEmpty() &&
            calendar.getTimeInMillis() <= Calendar.getInstance().getTimeInMillis())
            return true;

        return false;
    }

    /**
     * Save user input when the activity is recreated.
     * @param outState the bundle containing the state.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("date", (String) dateText.getText());
        outState.putString("time", (String) timeText.getText());
        outState.putString("measurement", measurementEditText.getText().toString());
    }

    /**
     * Sets the activity to listen to the fragment.
     * @param listener the activity listening to the fragment.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Interface for the activity that listens to this fragment.
     */
    public interface Listener {
        public void onClickDone(View view, double measurement, long timeInMillis);
        public void onClickClose();
    }
}
