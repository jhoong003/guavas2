package com.example.guavas.data;

import android.content.Context;

import com.example.guavas.R;
import com.example.guavas.data.model.SubMenu;

public class SubMenus {

    private Context context;

    private SubMenu bmiSubMenu;
    private SubMenu vitalSubMenu;
    private SubMenu healthSummarySubMenu;

    public SubMenus(Context context){
        this.context = context;
        createSubMenu();
    }

    private void createSubMenu(){
        bmiSubMenu = new SubMenu(context.getResources().getStringArray(R.array.option_bmi),
                new int[] {R.drawable.bmi, R.drawable.height, R.drawable.weight});

        vitalSubMenu = new SubMenu(context.getResources().getStringArray(R.array.option_vitals),
                new int[] {R.drawable.wine, R.drawable.blood_glucose, R.drawable.blood_pressure,
                        R.drawable.blood_pressure, R.drawable.cardiogram, R.drawable.insulin,
                        R.drawable.pulse_rate, R.drawable.triceps});

        healthSummarySubMenu = new SubMenu(context.getResources().getStringArray(R.array.option_health_summary),
                new int[] {R.drawable.bmi, R.drawable.vital, R.drawable.other});
    }

    public SubMenu getBmiSubMenu() {
        return bmiSubMenu;
    }

    public SubMenu getVitalSubMenu() {
        return vitalSubMenu;
    }

    public SubMenu getHealthSummarySubMenu() {
        return healthSummarySubMenu;
    }
}
