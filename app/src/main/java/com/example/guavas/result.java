package com.example.guavas;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


public class result extends AppCompatActivity {

    public Interpreter Htflite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        String[] ListOfIllness = new String[41];
        float [] haha  = getIntent().getFloatArrayExtra("values");
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
        for(float i:haha){
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
        DiagnoseLoading DL = new DiagnoseLoading(ListOfIllness[currentMaxIndex]);
        TextView tv1 = findViewById(R.id.IllName);
        TextView tv2 = findViewById(R.id.Desc);
        TextView tv3 = findViewById(R.id.Prevention);
        tv1.setText(DL.getName());
        tv2.setText(DL.getDesc());
        tv3.setText(DL.getPrevent());

    }
    public MappedByteBuffer loadModelFile(String location) throws IOException {
        AssetFileDescriptor fileDescriptor = getApplicationContext().getAssets().openFd(location);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}

