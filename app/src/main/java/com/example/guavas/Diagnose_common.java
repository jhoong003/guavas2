package com.example.guavas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class Diagnose_common extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private RecyclerView.LayoutManager layout;
    private ArrayList<Integer> SelectedItem = new ArrayList<>();
    private float[] select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose_common);

        final ArrayList<ItemList> itemLists = new ArrayList<>();
        itemLists.add(new ItemList("Itching","Normal temperature with itchy throat"));
        itemLists.add(new ItemList("Skin rash","High temperature with nothing"));
        itemLists.add(new ItemList("Nodal skin eruptions","pretty high temperature with itchy nose"));
        itemLists.add(new ItemList("Continuous sneezing","Normal temperature with dizzy and head pain"));
        itemLists.add(new ItemList("Shivering",""));
        itemLists.add(new ItemList("Chills",""));
        itemLists.add(new ItemList("Joint pain",""));
        itemLists.add(new ItemList("Stomach pain",""));
        itemLists.add(new ItemList("Acidity",""));
        itemLists.add(new ItemList("Ulcers on tongue",""));
        itemLists.add(new ItemList("Muscle Wasting",""));
        itemLists.add(new ItemList("Vomiting",""));
        itemLists.add(new ItemList("Burning micturition",""));
        itemLists.add(new ItemList("Spotting urination",""));
        itemLists.add(new ItemList("Fatique",""));
        itemLists.add(new ItemList("Weight gain",""));
        itemLists.add(new ItemList("Anxiety",""));
        itemLists.add(new ItemList("Cold hands and feets",""));
        itemLists.add(new ItemList("Mood swings",""));
        itemLists.add(new ItemList("Weight loss",""));
        itemLists.add(new ItemList("Restlessness",""));
        itemLists.add(new ItemList("Lethargy",""));
        itemLists.add(new ItemList("Patches in throat",""));
        itemLists.add(new ItemList("Irregular sugar level",""));
        itemLists.add(new ItemList("Cough",""));
        itemLists.add(new ItemList("High Fever",""));
        itemLists.add(new ItemList("Sunken eye",""));
        itemLists.add(new ItemList("Breathlessness",""));
        itemLists.add(new ItemList("Sweating",""));
        itemLists.add(new ItemList("Dehydration",""));
        itemLists.add(new ItemList("Indigestion",""));
        itemLists.add(new ItemList("Headache",""));
        itemLists.add(new ItemList("Yellowish skin",""));
        itemLists.add(new ItemList("Dark Urine",""));
        itemLists.add(new ItemList("Nausea",""));
        itemLists.add(new ItemList("Loss of appetite",""));
        itemLists.add(new ItemList("Pain behind the eye",""));
        itemLists.add(new ItemList("Back pain",""));
        itemLists.add(new ItemList("Constipation",""));
        itemLists.add(new ItemList("Abdominal pain",""));
        itemLists.add(new ItemList("Diarrhoea",""));
        itemLists.add(new ItemList("Mild fever",""));
        itemLists.add(new ItemList("Yellow urine",""));
        itemLists.add(new ItemList("Yellowing eye",""));
        itemLists.add(new ItemList("Acute liver failure",""));
        itemLists.add(new ItemList("Fluid overload",""));
        itemLists.add(new ItemList("Swelling of stomach",""));
        itemLists.add(new ItemList("Swelled lymph nodes",""));
        itemLists.add(new ItemList("Malaise",""));
        itemLists.add(new ItemList("Blurred and distorted vision",""));
        itemLists.add(new ItemList("Phlegm",""));
        itemLists.add(new ItemList("Throat irritation",""));
        itemLists.add(new ItemList("Red eye",""));
        itemLists.add(new ItemList("Sinus pressure",""));
        itemLists.add(new ItemList("Runny nose",""));
        itemLists.add(new ItemList("Congestion",""));
        itemLists.add(new ItemList("Chest pain",""));
        itemLists.add(new ItemList("Weakness in limbs",""));
        itemLists.add(new ItemList("Fast heart rate",""));
        itemLists.add(new ItemList("Pain during bowel movements",""));
        itemLists.add(new ItemList("Pain in anal region",""));
        itemLists.add(new ItemList("Bloody stool",""));
        itemLists.add(new ItemList("Irritation in anus",""));
        itemLists.add(new ItemList("Neck pain",""));
        itemLists.add(new ItemList("Dizziness",""));
        itemLists.add(new ItemList("Cramps",""));
        itemLists.add(new ItemList("Bruising",""));
        itemLists.add(new ItemList("Obesity",""));
        itemLists.add(new ItemList("Swollen legs",""));
        itemLists.add(new ItemList("Swollen blood vessels",""));
        itemLists.add(new ItemList("Puffy face and eyes",""));
        itemLists.add(new ItemList("Enlarged thyroid",""));
        itemLists.add(new ItemList("Brittle nails",""));
        itemLists.add(new ItemList("Swollen extremeties",""));
        itemLists.add(new ItemList("Excessive Hunger",""));
        itemLists.add(new ItemList("Extra marital contacts",""));
        itemLists.add(new ItemList("Dry and tingling lips",""));
        itemLists.add(new ItemList("Slurred speech",""));
        itemLists.add(new ItemList("Knee pain",""));
        itemLists.add(new ItemList("Hip joint pain",""));
        itemLists.add(new ItemList("Muscle weakness",""));
        itemLists.add(new ItemList("Stiff neck",""));
        itemLists.add(new ItemList("Swelling joints",""));
        itemLists.add(new ItemList("Movement stiffness",""));
        itemLists.add(new ItemList("Spinning movements",""));
        itemLists.add(new ItemList("Loss of balance",""));
        itemLists.add(new ItemList("Unsteadiness",""));
        itemLists.add(new ItemList("Weakness of one body side",""));
        itemLists.add(new ItemList("Loss of smell",""));
        itemLists.add(new ItemList("Bladder discomfort",""));
        itemLists.add(new ItemList("Foul smell of urine",""));
        itemLists.add(new ItemList("Continuous fell of urine",""));
        itemLists.add(new ItemList("Passage of gases",""));
        itemLists.add(new ItemList("Internal itching",""));
        itemLists.add(new ItemList("Toxic look",""));
        itemLists.add(new ItemList("Depression",""));
        itemLists.add(new ItemList("Irritability",""));
        itemLists.add(new ItemList("Muscle pain",""));
        itemLists.add(new ItemList("Altered sensorium",""));
        itemLists.add(new ItemList("Red spot over body",""));
        itemLists.add(new ItemList("Belly pain",""));
        itemLists.add(new ItemList("Abnormal menstruation",""));
        itemLists.add(new ItemList("Dischromic patches",""));
        itemLists.add(new ItemList("Watering from eyes",""));
        itemLists.add(new ItemList("Increase appetite",""));
        itemLists.add(new ItemList("Polyuria",""));
        itemLists.add(new ItemList("Family History",""));
        itemLists.add(new ItemList("Mucoid sputum",""));
        itemLists.add(new ItemList("Rusty sputum",""));
        itemLists.add(new ItemList("Lack of concentration",""));
        itemLists.add(new ItemList("Visual disturbances",""));
        itemLists.add(new ItemList("Receiving blood transfusion",""));
        itemLists.add(new ItemList("Receiving unsterile injections",""));
        itemLists.add(new ItemList("Coma",""));
        itemLists.add(new ItemList("Stomach bleeding",""));
        itemLists.add(new ItemList("Distention of abdomen",""));
        itemLists.add(new ItemList("History of alcohol consumption",""));
        itemLists.add(new ItemList("Fluid overload",""));
        itemLists.add(new ItemList("Blood in sputum",""));
        itemLists.add(new ItemList("Prominent veins on calf",""));
        itemLists.add(new ItemList("Palpitations",""));
        itemLists.add(new ItemList("Painful walking",""));
        itemLists.add(new ItemList("Pus filled pimples",""));
        itemLists.add(new ItemList("Blackheads",""));
        itemLists.add(new ItemList("Scurring",""));
        itemLists.add(new ItemList("Skin peeling",""));
        itemLists.add(new ItemList("Silver like dusting",""));
        itemLists.add(new ItemList("Small dents in nails",""));
        itemLists.add(new ItemList("Inflammatory nails",""));
        itemLists.add(new ItemList("Blister",""));
        itemLists.add(new ItemList("Red sore around nose",""));
        itemLists.add(new ItemList("Yellow crust ooze",""));
        Collections.sort(itemLists);

        recyclerView = findViewById(R.id.RecycleViews);
        recyclerView.setHasFixedSize(true);
        layout = new LinearLayoutManager(this);
        adapter = new ItemAdapter(itemLists);
        select = new float[132];
        int count = 0;
        while(count<132){
            select[count] = 0;
            count++;
        }
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View v) {
                TextView title = v.findViewById(R.id.Text1);
                TextView subtitle = v.findViewById(R.id.Text2);
                title.setTextColor(getResources().getColor(R.color.white));
                subtitle.setTextColor(getResources().getColor(R.color.white));
                String result = itemLists.get(position).getText1();
                select[position] = 1;
                Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public void Predict(View view){
        Intent intent = new Intent(Diagnose_common.this,result.class);
        intent.putExtra("values",select);
        for(float i:select){
            System.out.println(i);
        }
        startActivity(intent);
    }

}
