package com.example.guavas;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class diabetesResult extends AppCompatActivity {
    private float[] f = new float[7];
    public Interpreter Htflite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chronicresult);
        //ML Process
        Intent intent = getIntent();
        f = intent.getFloatArrayExtra("values");
        float[][] inputX = new float[1][7];
        float[][] output = {{1}};
        int count = 0;
        for(float i:f){
            inputX[0][count] =i;
            count++;
        }
        try {
            //noinspection deprecation
            Htflite = new Interpreter(loadModelFile("Diabetes.tflite"));
            System.out.println("pass");
            Htflite.run(inputX,output);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
        for( float[] i:output){
            for(float j:i){
                TextView textView = findViewById(R.id.Chronic1);
                textView.setText(Float.toString((1-j)*100));
                System.out.println(j);
            }
        }



    }
    public void onClickRediagnoseD(View view){
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
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
