package com.example.guavas.fragment;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.guavas.data.database.IllDetails;
import com.example.guavas.R;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * A fragment that shows the result from common disease diagnosis.
 */
public class DiagnoseResultFragment extends Fragment implements Subject, View.OnClickListener {
    private static final String VALUE_KEY = "val";
    private Interpreter Htflite;
    private FragmentObserver observer;

    public DiagnoseResultFragment() {
    }

    /**
     * Gets an instance of this fragment.
     * @param values the user inputs.
     * @return the instance of this fragment.
     */
    public static DiagnoseResultFragment newInstance(float[] values) {

        Bundle args = new Bundle();
        args.putFloatArray(VALUE_KEY, values);
        DiagnoseResultFragment fragment = new DiagnoseResultFragment();
        fragment.setArguments(args);
        return fragment;
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
        View parent = inflater.inflate(R.layout.fragment_diagnosis_result, container, false);

        String[] ListOfIllness = new String[41];
        float [] values  = getArguments().getFloatArray(VALUE_KEY);
        float[][] inputX = new float[1][132];
        float[][] output = new float[1][41];
        int count = 0;
        ListOfIllness[0] = "Paroymsal Positional Vertigo";
        ListOfIllness[1] = "AIDS";
        ListOfIllness[2] = "Acne";
        ListOfIllness[3] = "Alcoholic Hepatitis";
        ListOfIllness[4]="Allergy";
        ListOfIllness[5]="Arthritis";
        ListOfIllness[6]="Bronchial Asthma";
        ListOfIllness[7]="Cervical Spondylosis";
        ListOfIllness[8]="Chicken pox";
        ListOfIllness[9]="Chronic Cholestasis";
        ListOfIllness[10]="Common Cold";
        ListOfIllness[11]="Dengue";
        ListOfIllness[12]="Diabetes";
        ListOfIllness[13]="Dimorphic Hemmorhoids";
        ListOfIllness[14]="Drug Reaction";
        ListOfIllness[15]="Fungal Infection";
        ListOfIllness[16]="GERD";
        ListOfIllness[17]="Gastroenteritis";
        ListOfIllness[18]="Heart Attack";
        ListOfIllness[19]="Hepatitis B";
        ListOfIllness[20]="Hepatitis C";
        ListOfIllness[21]="Hepatitis D";
        ListOfIllness[22]="Hepatitis E";
        ListOfIllness[23]="Hypertension";
        ListOfIllness[24]="Hyperthyroidism";
        ListOfIllness[25]="Hypoglycemia";
        ListOfIllness[26]="Hypothyrodism";
        ListOfIllness[27]="Impetigo";
        ListOfIllness[28]="Jaundice";
        ListOfIllness[29]="Malaria";
        ListOfIllness[30]="Migraine";
        ListOfIllness[31]="Osteoarthristis";
        ListOfIllness[32]="Paralysis";
        ListOfIllness[33]="Peptic ulcer disease";
        ListOfIllness[34]="Pneumonia";
        ListOfIllness[35]="Psoriasis";
        ListOfIllness[36]="Tuberculosis";
        ListOfIllness[37]="Typhoid";
        ListOfIllness[38]="Urinary tract infection";
        ListOfIllness[39]="Varicose veins";
        ListOfIllness[40]="Hepatitis A";

        for(float i:values){
            inputX[0][count] =i;
            count++;
        }

        try {
            //noinspection deprecation
            Htflite = new Interpreter(loadModelFile("common.tflite"));
            System.out.println("pass");
            Htflite.run(inputX,output);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        float currentMax = 0;
        int currentMaxIndex =0;
        int counts = 0;
        for( float[] i:output) {
            for (float j : i) {
                if(j>currentMax){
                    currentMaxIndex = counts;
                    currentMax = j;
                }
                counts++;
            }
        }
        System.out.println(currentMaxIndex);
        IllDetails DL = new IllDetails(ListOfIllness[currentMaxIndex]);
        TextView tv1 = parent.findViewById(R.id.IllName);
        TextView tv2 = parent.findViewById(R.id.Desc);
        TextView tv3 = parent.findViewById(R.id.Prevention);
        tv1.setText(DL.getName());
        tv2.setText(DL.getDesc());
        tv3.setText(DL.getPrevent());

        Button button = parent.findViewById(R.id.home_button);
        button.setOnClickListener(this);
        return parent;
    }

    /**
     * @param v the view clicked.
     * @see DiagnoseResultFragment#onClickHome()
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.home_button) onClickHome();
    }

    /**
     * Makes the home button works.
     */
    private void onClickHome(){
        DiagnoseMainFragment fragment = new DiagnoseMainFragment();
        notifyObserver(fragment);
    }

    /**
     * Loads the machine learning model.
     *
     * @param location the path to the model file.
     * @return the model.
     * @throws IOException thrown if the model cannot be loaded.
     */
    public MappedByteBuffer loadModelFile(String location) throws IOException {
        AssetFileDescriptor fileDescriptor = getActivity().getApplicationContext().getAssets().openFd(location);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
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
