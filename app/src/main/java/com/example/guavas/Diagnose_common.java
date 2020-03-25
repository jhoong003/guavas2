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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ArrayList<ItemList> Itemlist = new ArrayList<>();
        Itemlist.add(new ItemList("Itching","Normal temperature with itchy throat"));
        Itemlist.add(new ItemList("Skin rash","High temperature with nothing"));
        Itemlist.add(new ItemList("Nodal skin eruptions","pretty high temperature with itchy nose"));
        Itemlist.add(new ItemList("Continuous sneezing","Normal temperature with dizzy and head pain"));
        Itemlist.add(new ItemList("Shivering",""));
        Itemlist.add(new ItemList("Chills",""));
        Itemlist.add(new ItemList("Joint pain",""));
        Itemlist.add(new ItemList("Stomach pain",""));
        Itemlist.add(new ItemList("Acidity",""));
        Itemlist.add(new ItemList("Ulcers on tongue",""));
        Itemlist.add(new ItemList("Muscle Wasting",""));
        Itemlist.add(new ItemList("Vomiting",""));
        Itemlist.add(new ItemList("Burning micturition",""));
        Itemlist.add(new ItemList("Spotting urination",""));
        Itemlist.add(new ItemList("Fatique",""));
        Itemlist.add(new ItemList("Weight gain",""));
        Itemlist.add(new ItemList("Anxiety",""));
        Itemlist.add(new ItemList("Cold hands and feets",""));
        Itemlist.add(new ItemList("Mood swings",""));
        Itemlist.add(new ItemList("Weight loss",""));
        Itemlist.add(new ItemList("Restlessness",""));
        Itemlist.add(new ItemList("Lethargy",""));
        Itemlist.add(new ItemList("Patches in throat",""));
        Itemlist.add(new ItemList("Irregular sugar level",""));
        Itemlist.add(new ItemList("Cough",""));
        Itemlist.add(new ItemList("High Fever",""));
        Itemlist.add(new ItemList("Sunken eye",""));
        Itemlist.add(new ItemList("Breathlessness",""));
        Itemlist.add(new ItemList("Sweating",""));
        Itemlist.add(new ItemList("Dehydration",""));
        Itemlist.add(new ItemList("Indigestion",""));
        Itemlist.add(new ItemList("Headache",""));
        Itemlist.add(new ItemList("Yellowish skin",""));
        Itemlist.add(new ItemList("Dark Urine",""));
        Itemlist.add(new ItemList("Nausea",""));
        Itemlist.add(new ItemList("Loss of appetite",""));
        Itemlist.add(new ItemList("Pain behind the eye",""));
        Itemlist.add(new ItemList("Back pain",""));
        Itemlist.add(new ItemList("Constipation",""));
        Itemlist.add(new ItemList("Abdominal pain",""));
        Itemlist.add(new ItemList("Diarrhoea",""));
        Itemlist.add(new ItemList("Mild fever",""));
        Itemlist.add(new ItemList("Yellow urine",""));
        Itemlist.add(new ItemList("Yellowing eye",""));
        Itemlist.add(new ItemList("Acute liver failure",""));
        Itemlist.add(new ItemList("Fluid overload",""));
        Itemlist.add(new ItemList("Swelling of stomach",""));
        Itemlist.add(new ItemList("Swelled lymph nodes",""));
        Itemlist.add(new ItemList("Malaise",""));
        Itemlist.add(new ItemList("Blurred and distorted vision",""));
        Itemlist.add(new ItemList("Phlegm",""));
        Itemlist.add(new ItemList("Throat irritation",""));
        Itemlist.add(new ItemList("Red eye",""));
        Itemlist.add(new ItemList("Sinus pressure",""));
        Itemlist.add(new ItemList("Runny nose",""));
        Itemlist.add(new ItemList("Congestion",""));
        Itemlist.add(new ItemList("Chest pain",""));
        Itemlist.add(new ItemList("Weakness in limbs",""));
        Itemlist.add(new ItemList("Fast heart rate",""));
        Itemlist.add(new ItemList("Pain during bowel movements",""));
        Itemlist.add(new ItemList("Pain in anal region",""));
        Itemlist.add(new ItemList("Bloody stool",""));
        Itemlist.add(new ItemList("Irritation in anus",""));
        Itemlist.add(new ItemList("Neck pain",""));
        Itemlist.add(new ItemList("Dizziness",""));
        Itemlist.add(new ItemList("Cramps",""));
        Itemlist.add(new ItemList("Bruising",""));
        Itemlist.add(new ItemList("Obesity",""));
        Itemlist.add(new ItemList("Swollen legs",""));
        Itemlist.add(new ItemList("Swollen blood vessels",""));
        Itemlist.add(new ItemList("Puffy face and eyes",""));
        Itemlist.add(new ItemList("Enlarged thyroid",""));
        Itemlist.add(new ItemList("Brittle nails",""));
        Itemlist.add(new ItemList("Swollen extremeties",""));
        Itemlist.add(new ItemList("Excessive Hunger",""));
        Itemlist.add(new ItemList("Extra marital contacts",""));
        Itemlist.add(new ItemList("Dry and tingling lips",""));
        Itemlist.add(new ItemList("Slurred speech",""));
        Itemlist.add(new ItemList("Knee pain",""));
        Itemlist.add(new ItemList("Hip joint pain",""));
        Itemlist.add(new ItemList("Muscle weakness",""));
        Itemlist.add(new ItemList("Stiff neck",""));
        Itemlist.add(new ItemList("Swelling joints",""));
        Itemlist.add(new ItemList("Movement stiffness",""));
        Itemlist.add(new ItemList("Spinning movements",""));
        Itemlist.add(new ItemList("Loss of balance",""));
        Itemlist.add(new ItemList("Unsteadiness",""));
        Itemlist.add(new ItemList("Weakness of one body side",""));
        Itemlist.add(new ItemList("Loss of smell",""));
        Itemlist.add(new ItemList("Bladder discomfort",""));
        Itemlist.add(new ItemList("Foul smell of urine",""));
        Itemlist.add(new ItemList("Continuous fell of urine",""));
        Itemlist.add(new ItemList("Passage of gases",""));
        Itemlist.add(new ItemList("Internal itching",""));
        Itemlist.add(new ItemList("Toxic look",""));
        Itemlist.add(new ItemList("Depression",""));
        Itemlist.add(new ItemList("Irritability",""));
        Itemlist.add(new ItemList("Muscle pain",""));
        Itemlist.add(new ItemList("Altered sensorium",""));
        Itemlist.add(new ItemList("Red spot over body",""));
        Itemlist.add(new ItemList("Belly pain",""));
        Itemlist.add(new ItemList("Abnormal menstruation",""));
        Itemlist.add(new ItemList("Dischromic patches",""));
        Itemlist.add(new ItemList("Watering from eyes",""));
        Itemlist.add(new ItemList("Increase appetite",""));
        Itemlist.add(new ItemList("Polyuria",""));
        Itemlist.add(new ItemList("Family History",""));
        Itemlist.add(new ItemList("Mucoid sputum",""));
        Itemlist.add(new ItemList("Rusty sputum",""));
        Itemlist.add(new ItemList("Lack of concentration",""));
        Itemlist.add(new ItemList("Visual disturbances",""));
        Itemlist.add(new ItemList("Receiving blood transfusion",""));
        Itemlist.add(new ItemList("Receiving unsterile injections",""));
        Itemlist.add(new ItemList("Coma",""));
        Itemlist.add(new ItemList("Stomach bleeding",""));
        Itemlist.add(new ItemList("Distention of abdomen",""));
        Itemlist.add(new ItemList("History of alcohol consumption",""));
        Itemlist.add(new ItemList("Fluid overload",""));
        Itemlist.add(new ItemList("Blood in sputum",""));
        Itemlist.add(new ItemList("Prominent veins on calf",""));
        Itemlist.add(new ItemList("Palpitations",""));
        Itemlist.add(new ItemList("Painful walking",""));
        Itemlist.add(new ItemList("Pus filled pimples",""));
        Itemlist.add(new ItemList("Blackheads",""));
        Itemlist.add(new ItemList("Scurring",""));
        Itemlist.add(new ItemList("Skin peeling",""));
        Itemlist.add(new ItemList("Silver like dusting",""));
        Itemlist.add(new ItemList("Small dents in nails",""));
        Itemlist.add(new ItemList("Inflammatory nails",""));
        Itemlist.add(new ItemList("Blister",""));
        Itemlist.add(new ItemList("Red sore around nose",""));
        Itemlist.add(new ItemList("Yellow crust ooze",""));
        recyclerView = findViewById(R.id.RecycleViews);
        recyclerView.setHasFixedSize(true);
        layout = new LinearLayoutManager(this);
        adapter = new ItemAdapter(Itemlist);
        select = new float[132];
        int count = 0;
        while(count<132){
            select[count] = 0;
            count++;
        }
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void OnItemClick(int position, View v) {
                TextView title = v.findViewById(R.id.Text1);
                TextView subtitle = v.findViewById(R.id.Text2);
                title.setTextColor(R.color.white);
                subtitle.setTextColor(R.color.white);
                String result = Itemlist.get(position).getText1();
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
