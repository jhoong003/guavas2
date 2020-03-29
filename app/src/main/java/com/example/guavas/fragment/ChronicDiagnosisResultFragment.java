package com.example.guavas.fragment;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.guavas.R;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Locale;

public class ChronicDiagnosisResultFragment extends Fragment implements Subject, View.OnClickListener {

    private static final String FACTOR_KEY = "factor";
    private static final String PATH_KEY = "path";
    private static final String NAME_KEY = "name";

    private FragmentObserver observer;
    private String modelPath;
    private String diseaseName;
    private float[] factors;

    public ChronicDiagnosisResultFragment() {
    }

    public static ChronicDiagnosisResultFragment newInstance(float[] factors, String diseaseName, String modelFilePath) {

        Bundle args = new Bundle();
        args.putFloatArray(FACTOR_KEY, factors);
        args.putString(PATH_KEY, modelFilePath);
        args.putString(NAME_KEY, diseaseName);
        ChronicDiagnosisResultFragment fragment = new ChronicDiagnosisResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null){
            factors = args.getFloatArray(FACTOR_KEY);
            modelPath = args.getString(PATH_KEY);
            diseaseName = args.getString(NAME_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_chronic_diagnosis_result, container, false);

        Button button = parent.findViewById(R.id.button_home);
        button.setOnClickListener(this);

        TextView titleText = parent.findViewById(R.id.text_disease_name);
        titleText.setText(diseaseName);

        float[][] inputX = new float[1][factors.length];
        float[][] output = {{1}};
        int count = 0;
        for(float i:factors){
            inputX[0][count] =i;
            count++;
        }
        try {
            //noinspection deprecation
            Interpreter htflite = new Interpreter(loadModelFile(modelPath));
            System.out.println("pass");
            htflite.run(inputX,output);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        for( float[] i:output){
            for(float j:i){
                TextView textView = parent.findViewById(R.id.Chronic1);
                textView.setText(String.format(Locale.getDefault(), "%.2f%% ", (1-j)*100));
                System.out.println(j);
            }
        }
        return parent;
    }

    private MappedByteBuffer loadModelFile(String location) throws IOException {
        AssetFileDescriptor fileDescriptor = getActivity().getApplicationContext().getAssets().openFd(location);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_home) onClickHome();
    }

    private void onClickHome(){
        notifyObserver(new DiagnoseMainFragment());
    }

    @Override
    public void register(FragmentObserver observer) {
        this.observer = observer;
    }

    @Override
    public void unregister() {
        observer = null;
    }

    @Override
    public void notifyObserver(Fragment fragment) {
        observer.updateContainerWithFragment(fragment);
    }
}
