package com.example.guavas.data.database;

import android.content.Context;

import com.example.guavas.R;
import com.example.guavas.data.entity.SubMenu;

/**
 * This class is the database for sub-menus of the health summary section. It is used to
 * create the list of features available in the health summary.
 */
public class SubMenus {

    private Context context;

    private SubMenu bmiSubMenu;
    private SubMenu vitalSubMenu;
    private SubMenu healthSummarySubMenu;

    /**
     * The constructor creates the submenu database.
     *
     * @param context the context, required to fetch resources
     */
    public SubMenus(Context context) {
        this.context = context;
        createSubMenu();
    }

    /**
     * Creates the submenu database.
     */
    private void createSubMenu() {
        bmiSubMenu = new SubMenu(context.getResources().getStringArray(R.array.option_bmi),
                new int[]{R.drawable.bmi, R.drawable.height, R.drawable.weight});

        vitalSubMenu = new SubMenu(context.getResources().getStringArray(R.array.option_vitals),
                new int[]{R.drawable.wine, R.drawable.blood_glucose, R.drawable.blood_pressure,
                        R.drawable.blood_pressure, R.drawable.cardiogram, R.drawable.insulin,
                        R.drawable.pulse_rate, R.drawable.triceps});

        healthSummarySubMenu = new SubMenu(context.getResources().getStringArray(R.array.option_health_summary),
                new int[]{R.drawable.bmi, R.drawable.vital, R.drawable.other});
    }

    /**
     * Returns the features available for the BMI section.
     *
     * @return the features available for the BMI section
     */
    public SubMenu getBmiSubMenu() {
        return bmiSubMenu;
    }

    /**
     * Returns the features available for the vitals section.
     *
     * @return the features available for the vitals section
     */
    public SubMenu getVitalSubMenu() {
        return vitalSubMenu;
    }

    /**
     * Returns the features available for the health summary main menu.
     *
     * @return the features available for the health summary main menu
     */
    public SubMenu getHealthSummarySubMenu() {
        return healthSummarySubMenu;
    }
}
