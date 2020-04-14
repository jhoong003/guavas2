package com.example.guavas.data.database;

import com.example.guavas.data.entity.IllDetail;

import java.util.ArrayList;

/**
 * This class is a database of a diseases and its properties.
 * The properties are saved in the <code>IllDetail</code> object.
 *
 * @see IllDetail
 */
public class IllDetails {

    private ArrayList<IllDetail> IllDb = new ArrayList<>();
    private String name;
    private String Description;
    private String prevention;

    IllDetail Ill1 = new IllDetail("Paroymsal Positional Vertigo", "a disorder arising from a problem in the inner ear. Symptoms are repeated, brief periods of vertigo with movement, that is, of a spinning sensation upon changes in the position of the head. This can occur with turning in bed or changing position. Each episode of vertigo typically lasts less than one minute. Nausea is commonly associated.BPPV is one of the most common causes of vertigo.", "Do the Epley maneuver, the Semont maneuver, and a lesser degree Brandt–Daroff exercises.");
    IllDetail Ill2 = new IllDetail("AIDS", "A spectrum of conditions caused by infection with the human immunodeficiency virus (HIV).", "There is currently no cure, nor an effective HIV vaccine");
    IllDetail Ill3 = new IllDetail("Acne", "A long-term skin disease that occurs when dead skin cells and oil from the skin clog hair follicles.", "Apply skin care, Diet , and other medications");
    IllDetail Ill4 = new IllDetail("Alcoholic Hepatitis", " Hepatitis (inflammation of the liver) due to excessive intake of alcohol", "Reduce alcohol consumption and go to hospital");
    IllDetail Ill5 = new IllDetail("Allergy", "Number of conditions caused by hypersensitivity of the immune system to typically harmless substances in the environment.", "Seek profesional's help to get more detail");
    IllDetail Ill6 = new IllDetail("Arthritis", "Disorder that affects joints", " Treatment options vary depending on the type of arthritis and include physical therapy and lifestyle changes");
    IllDetail Ill7 = new IllDetail("Bronchial Asthma", " A common long-term inflammatory disease of the airways of the lungs", "Decreasing risk factors such as tobacco smoke, air pollution, chemical irritants including perfume, and the number of lower respiratory infections.");
    IllDetail Ill8 = new IllDetail("Cervical Spondylosis", "The degeneration of the vertebral column from any cause", "Education on lifestyle modifications, chiropractic, nonsteroidal anti-inflammatory drugs (NSAIDs), physical therapy, and osteopathic care");
    IllDetail Ill9 = new IllDetail("Chicken pox", "A highly contagious disease caused by the initial infection with varicella zoster virus (VZV)", "Cutting the nails short or wearing gloves may prevent scratching and minimize the risk of secondary infections");
    IllDetail Ill10 = new IllDetail("Chronic Cholestasis", "A condition where bile cannot flow from the liver to the duodenum", " surgery");
    IllDetail Ill11 = new IllDetail("Common Cold", "a viral infectious disease of the upper respiratory tract that primarily affects the nose.", "Getting plenty of rest, drinking fluids to maintain hydration, and gargling with warm salt water are reasonable conservative measures.");
    IllDetail Ill12 = new IllDetail("Dengue", " a mosquito-borne tropical disease caused by the dengue virus.", "maintaining proper fluid balance, and Treatment depends on the symptoms");
    IllDetail Ill13 = new IllDetail("Diabetes", "a group of metabolic disorders characterized by a high blood sugar level over a prolonged period of time.", "dietary changes, exercise, weight loss, and use of appropriate medications (insulin, oral medications)");
    IllDetail Ill14 = new IllDetail("Dimorphic Hemmorhoids", " vascular structures in the anal canal.", "foods rich in dietary fiber, intake of oral fluids to maintain hydration, nonsteroidal anti-inflammatory drugs, sitz baths, and rest.");
    IllDetail Ill15 = new IllDetail("Drug Reaction", "Body Reaction to drugs", "Reducing drugs consumption");
    IllDetail Ill16 = new IllDetail("Fungal Infection", "is a fungal infection of the skin.", " topical agents such as miconazole, terbinafine, clotrimazole, ketoconazole, or tolnaftate applied twice daily ");
    IllDetail Ill17 = new IllDetail("GERD", " acid reflux, is a long-term condition in which stomach contents rise up into the esophagus, resulting in either symptoms or complications.", " food choices, lifestyle changes, medications, and possibly surgery. Initial treatment is frequently with a proton-pump inhibitor such as omeprazole.");
    IllDetail Ill18 = new IllDetail("Gastroenteritis", "infectious diarrhea, is inflammation of the gastrointestinal tract—the stomach and small intestine.", " taking a single dose of the anti vomiting medication metoclopramide or ondansetron");
    IllDetail Ill19 = new IllDetail("Heart Attack", " blood flow decreases or stops to a part of the heart, causing damage to the heart muscle.", "Go to hospital");
    IllDetail Ill20 = new IllDetail("Hepatitis B", "an infectious disease caused by the hepatitis B virus (HBV) that affects the liver.", "Not required");
    IllDetail Ill21 = new IllDetail("Hepatitis C", "an infectious disease caused by the hepatitis C virus (HCV) that primarily affects the liver.", "avoid alcohol and medications toxic to the liver.");
    IllDetail Ill22 = new IllDetail("Hepatitis D", "a disease caused by the hepatitis delta virus (HDV), a small spherical enveloped virusoid.", "conventional or pegylated interferon alpha therapy");
    IllDetail Ill23 = new IllDetail("Hepatitis E", "inflammation of the liver caused by infection with the hepatitis E virus (HEV)", "There is no drug that has established safety and effectiveness for hepatitis E, and there have been no large randomized clinical trials of antiviral drugs.");
    IllDetail Ill24 = new IllDetail("Hypertension", "a long-term medical condition in which the blood pressure in the arteries is persistently elevated.", "Lifestyle modification");
    IllDetail Ill25 = new IllDetail("Hyperthyroidism", "the condition that occurs due to excessive production of thyroid hormones by the thyroid gland.", "Antithyroid drugs");
    IllDetail Ill26 = new IllDetail("Hypoglycemia", "a fall in blood sugar to levels below normal.", "eating of carbohydrates such as sugars, determining the cause, and taking measures to hopefully prevent future episodes");
    IllDetail Ill27 = new IllDetail("Hypothyrodism", "a disorder of the endocrine system in which the thyroid gland does not produce enough thyroid hormone.", "Hormone replacement");
    IllDetail Ill28 = new IllDetail("Impetigo", " a bacterial infection that involves the superficial skin", "Antibiotics, either as a cream or by mouth, are usually prescribed. Mild cases may be treated with mupirocin ointments");
    IllDetail Ill29 = new IllDetail("Jaundice", " a yellowish or greenish pigmentation of the skin and whites of the eyes due to high bilirubin levels.", "Do some liver check up");
    IllDetail Ill30 = new IllDetail("Malaria", "a mosquito-borne infectious disease that affects humans and other animals", " antimalarial medications");
    IllDetail Ill31 = new IllDetail("Migraine", "a primary headache disorder characterized by recurrent headaches that are moderate to severe.", "trigger avoidance, acute symptomatic control, and medication for prevention.");
    IllDetail Ill32 = new IllDetail("Osteoarthristis", "a type of joint disease that results from breakdown of joint cartilage and underlying bone.", "Lifestyle modification (such as weight loss and exercise) and pain medications are the mainstays of treatment");
    IllDetail Ill33 = new IllDetail("Paralysis", "a loss of motor function in one or more muscles", "Go to hospital");
    IllDetail Ill34 = new IllDetail("Peptic ulcer disease", "a break in the inner lining of the stomach, the first part of the small intestine, or sometimes the lower esophagus.", "a triple regimen in which pantoprazole and clarithromycin are combined with either amoxicillin or metronidazole");
    IllDetail Ill35 = new IllDetail("Pneumonia", "an inflammatory condition of the lung affecting primarily the small air sacs known as alveoli.", "Antibiotics by mouth, rest, simple analgesics, and fluids usually suffice for complete resolution.");
    IllDetail Ill36 = new IllDetail("Psoriasis", "a long-lasting autoimmune disease characterized by patches of abnormal skin.", "no cure is available for psoriasis");
    IllDetail Ill37 = new IllDetail("Tuberculosis", "an infectious disease usually caused by Mycobacterium tuberculosis (MTB) bacteria", "Go to hospital");
    IllDetail Ill38 = new IllDetail("Typhoid", " a bacterial infection due to a specific type of Salmonella that causes symptoms.", "Oral rehydration therapy and antibiotics");
    IllDetail Ill39 = new IllDetail("Urinary tract infection", "an infection that affects part of the urinary tract", "consume antibiotics and Phenazopyridine");
    IllDetail Ill40 = new IllDetail("Varicose veins", " superficial veins that have become enlarged and twisted.", "surgical for 100% cure");
    IllDetail Ill41 = new IllDetail("Hepatitis A", "an infectious disease of the liver caused by Hepatovirus A (HAV)", "No specific treatment");

    /**
     * Gets the details of a disease.
     *
     * @param Checck the name of the disease.
     */
    public IllDetails(String Checck) {
        getDetail(Checck);
    }

    /**
     * Get the details of a disease.
     *
     * @param Illness the name of the disease.
     */
    private void getDetail(String Illness) {
        IllDb.add(Ill1);
        IllDb.add(Ill2);
        IllDb.add(Ill3);
        IllDb.add(Ill4);
        IllDb.add(Ill5);
        IllDb.add(Ill6);
        IllDb.add(Ill7);
        IllDb.add(Ill8);
        IllDb.add(Ill9);
        IllDb.add(Ill10);
        IllDb.add(Ill11);
        IllDb.add(Ill12);
        IllDb.add(Ill13);
        IllDb.add(Ill14);
        IllDb.add(Ill15);
        IllDb.add(Ill16);
        IllDb.add(Ill17);
        IllDb.add(Ill18);
        IllDb.add(Ill19);
        IllDb.add(Ill20);
        IllDb.add(Ill21);
        IllDb.add(Ill22);
        IllDb.add(Ill23);
        IllDb.add(Ill24);
        IllDb.add(Ill25);
        IllDb.add(Ill26);
        IllDb.add(Ill27);
        IllDb.add(Ill28);
        IllDb.add(Ill29);
        IllDb.add(Ill30);
        IllDb.add(Ill31);
        IllDb.add(Ill32);
        IllDb.add(Ill33);
        IllDb.add(Ill34);
        IllDb.add(Ill35);
        IllDb.add(Ill36);
        IllDb.add(Ill37);
        IllDb.add(Ill38);
        IllDb.add(Ill39);
        IllDb.add(Ill40);
        IllDb.add(Ill41);
        for (IllDetail ill : IllDb) {
            if (ill.getName().equals(Illness)) {
                Description = ill.getDescription();
                prevention = ill.getPrevention();
                name = ill.getName();
            }
        }
    }

    /**
     * Gets the name of the disease.
     *
     * @return the name of the disease.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the disease.
     *
     * @return the description of the disease.
     */
    public String getDesc() {
        return Description;
    }

    /**
     * Gets the prevention of the disease.
     *
     * @return the prevention of the disease.
     */
    public String getPrevent() {
        return prevention;
    }

}
