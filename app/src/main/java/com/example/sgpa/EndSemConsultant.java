package com.example.sgpa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sgpa.help.Help;

import java.util.Random;

public class EndSemConsultant extends AppCompatActivity {
    private static final int numOfSub = SubjectPreferences.sub.length;
    private float sgpa=0;
    private final int[] termWorkMrks = new int[TermWork.sub.length];
    private final int[] internalMrks = new int[numOfSub];
    private final int[] subPref = new int[numOfSub];
    private final int[] endSemMrks = new int[numOfSub];
    private final boolean[] endSemOrPref = new boolean[numOfSub];
    private final int[] endSemRange = Subjects.getEndSemRange();
    private final String[] subName = SubjectPreferences.sub;
    private static float predictedSgpa=0;
    private String previousResult;
    private int index;
    private boolean completed;
    private boolean allSubFull=true;
    LinearLayout bckBtn,helpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_end_sem_consultant);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.end_sem_consultant), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSurveyData();
        TextView result = findViewById(R.id.marks_prediction);
        bckBtn = findViewById(R.id.end_sem_consultant_bck);
        helpBtn = findViewById(R.id.help_endSem);
        int[] subMrks;
        float prefSgpa;
        boolean sameResult=false;
        StringBuilder info = new StringBuilder();

        if(SubjectPreferences.sgpa_val_layout.getVisibility() == View.GONE) {  // case 0

            boolean littleChange = false;
            float temp = OnPrefPredictMarksAlgo.calculateSgpa(termWorkMrks, internalMrks, endSemMrks);
            if(Math.abs(predictedSgpa-temp)<=0.5) littleChange = true;
            predictedSgpa = temp;

            for (int i = 0; i < numOfSub; i++) {
                String sub = "<u><b>" + subName[i] + "</b></u>";
                String marks = "<u><b>" + endSemMrks[i] + "</b></u>";
                info.append("For ").append(sub).append(", your marks should be ").append(getCompareSymbolForSub(endSemMrks[i], endSemRange[i])).append(" your expected marks : ").append(marks).append(".<br><br>");
            }
            String sgpa = "<u><b>" + predictedSgpa + "</b></u>";
            info.append("According to your end-semester marks, along with the provided internal and term work marks, your overall SGPA would be ").append(getCompareSymbolForSgpa(allSubFull,predictedSgpa)).append(sgpa).append(".<br> ");

            if(previousResult.equals(info.toString())) {
                sameResult=true;
                info.insert(0,getHeading(0,index));

            }else if (littleChange && completed){
                sameResult=true;
                saveResult(info);
                info.insert(0,"(<i>Edited!!</i>  slight changes in SGPA.)<br><br>");
            }else {
                saveResult(info);
                info.insert(0, getHeading(0, -1));
            }
            if(sameResult && completed){
                result.setText(Html.fromHtml(info.toString(), Html.FROM_HTML_MODE_LEGACY));
            }
            else {
                completed = false;
                animateText(result, info.toString());
            }

        }else if(sgpa==0) {  // case 1

            subMrks = OnPrefPredictMarksAlgo.calculate(termWorkMrks, subPref, endSemMrks, endSemOrPref, internalMrks, 10);
            if (subMrks == null) {
                sgpa = 9.99f;
                while (subMrks == null) {
                    subMrks = OnPrefPredictMarksAlgo.calculate(termWorkMrks, subPref, endSemMrks, endSemOrPref, internalMrks, sgpa);
                    sgpa -= 0.1f;
                }
            }
            prefSgpa = OnPrefPredictMarksAlgo.preTC;

            for (int i = 0; i < numOfSub; i++) {
                String sub = "<u><b>" + subName[i] + "</b></u>" + getPreference(i);
                String marks = "<u><b>" + subMrks[i] + "</b></u>";
                if (endSemOrPref[i])
                    info.append("For ").append(sub).append(", your marks should be ").append(getCompareSymbolForSub(subMrks[i], endSemRange[i])).append(" your expected marks : ").append(marks).append(".<br><br>");
                else
                    info.append("For ").append(sub).append(", your marks should be ").append(getCompareSymbolForSub(subMrks[i], endSemRange[i])).append(" ").append(marks).append(" out of ").append(endSemRange[i]).append(".<br><br>");
            }

            String pref = "<u><b>" + prefSgpa + "</b></u>";
            info.append("Based on the above combination, the overall SGPA you will achieve is ").append(getCompareSymbolForSgpa(allSubFull,prefSgpa)).append(pref).append("<br><br>");
            info.append("All the best!!");

            if(previousResult.equals(info.toString())) {
                sameResult=true;
                info.insert(0,getHeading(1,index));
            }else {
                saveResult(info);
                info.insert(0, getHeading(1, -1));
            }

            if(sameResult && completed){
                result.setText(Html.fromHtml(info.toString(), Html.FROM_HTML_MODE_LEGACY));
            }
            else {
                completed = false;
                animateText(result, info.toString());
            }


        }else{
            subMrks = OnPrefPredictMarksAlgo.calculate(termWorkMrks, subPref, endSemMrks, endSemOrPref, internalMrks, sgpa);
            String[] correctedPref = OnPrefPredictMarksAlgo.correctedPref;
            prefSgpa = OnPrefPredictMarksAlgo.preTC;

            boolean isHighPrefLowSgpa = false;
            for (int i = 0; i < numOfSub; i++) {
                String pref = " <b>(Pref : " + correctedPref[i] + ")</b>";
                if (!endSemOrPref[i]) isHighPrefLowSgpa = !pref.equals(getPreference(i));
            }

            if (subMrks != null && isHighPrefLowSgpa) { // case 2

                for (int i = 0; i < numOfSub; i++) {
                    String marks = "<u><b>" + subMrks[i] + "</b></u>";
                    String pref = (!endSemOrPref[i]) ? correctedPref[i] : null;
                    String sub;
                    if (pref != null)
                        sub = "<u><b>" + subName[i] + "</b></u>" + " <b>(New Pref : " + pref + ")</b>";
                    else sub = "<u><b>" + subName[i] + "</b></u>";
                    if (endSemOrPref[i])
                        info.append("For ").append(sub).append(", your marks should be ").append(getCompareSymbolForSub(subMrks[i], endSemRange[i])).append(" your expected marks : ").append(marks).append(".<br><br>");
                    else
                        info.append("For ").append(sub).append(", your marks should be ").append(getCompareSymbolForSub(subMrks[i], endSemRange[i])).append(" ").append(marks).append(" out of ").append(endSemRange[i]).append(".<br><br>");
                }

                String prefTotalCredits = "<u><b>" + prefSgpa + "</b></u>";
                info.append("Based on the above combination, the overall SGPA you will achieve is ").append(getCompareSymbolForSgpa(allSubFull,prefSgpa)).append(prefTotalCredits).append("<br><br>Now here is a combination based on your preferences that will generate an SGPA either slightly above or significantly higher than your required SGPA.<br><br>");

                subMrks = OnPrefPredictMarksAlgo.calculate(termWorkMrks, subPref, endSemMrks, endSemOrPref, internalMrks, 10);

                if (subMrks == null) {
                    sgpa = 9.9f;
                    while (subMrks == null) {
                        subMrks = OnPrefPredictMarksAlgo.calculate(termWorkMrks, subPref, endSemMrks, endSemOrPref, internalMrks, sgpa);
                        sgpa -= 0.1f;
                    }
                }
                prefSgpa = OnPrefPredictMarksAlgo.preTC;

                for (int i = 0; i < numOfSub; i++) {
                    String sub = "<u><b>" + subName[i] + "</b></u>" + getPreference(i);
                    String marks = "<b>" + subMrks[i] + "</b>";
                    if (endSemOrPref[i])
                        info.append("For ").append(sub).append(", your marks should be ").append(getCompareSymbolForSub(subMrks[i], endSemRange[i])).append(" your expected marks : ").append(marks).append(".<br><br>");
                    else
                        info.append("For ").append(sub).append(", your marks should be ").append(getCompareSymbolForSub(subMrks[i], endSemRange[i])).append(" ").append(marks).append(" out of ").append(endSemRange[i]).append(".<br><br>");
                }
                String pref = "<u><b>" + prefSgpa + "</b></u>";

                info.append("Based on the above combination, the overall SGPA you will achieve is ").append(getCompareSymbolForSgpa(allSubFull,prefSgpa)).append(pref).append("<br><br>");
                info.append("All the best!!");

                if(previousResult.equals(info.toString())) {
                    sameResult=true;
                    info.insert(0,getHeading(2,index));
                }else {
                    saveResult(info);
                    info.insert(0, getHeading(2, -1));
                }

                if(sameResult && completed){
                    result.setText(Html.fromHtml(info.toString(), Html.FROM_HTML_MODE_LEGACY));
                }
                else {
                    completed = false;
                    animateText(result, info.toString());
                }

            } else if (subMrks != null && !OnPrefPredictMarksAlgo.invalidPref) { // case 3

                for (int i = 0; i < numOfSub; i++) {
                    String sub = "<u><b>" + subName[i] + "</b></u>" + getPreference(i);
                    String marks = "<u><b>" + subMrks[i] + "</b></u>";
                    if (endSemOrPref[i])
                        info.append("For ").append(sub).append(", your marks should be ").append(getCompareSymbolForSub(subMrks[i], endSemRange[i])).append(" your expected marks : ").append(marks).append(".<br><br>");
                    else
                        info.append("For ").append(sub).append(", your marks should be ").append(getCompareSymbolForSub(subMrks[i], endSemRange[i])).append(" ").append(marks).append(" out of ").append(endSemRange[i]).append(".<br><br>");
                }

                String pref = "<u><b>" + prefSgpa + "</b></u>";
                info.append("Based on the above combination, the overall SGPA you will achieve is ").append(getCompareSymbolForSgpa(allSubFull,prefSgpa)).append(pref).append(compare(prefSgpa, sgpa)).append("<br><br>");
                info.append("All the best!!");

                if(previousResult.equals(info.toString())) {
                    sameResult=true;
                    info.insert(0,getHeading(3,index));
                }else {
                    saveResult(info);
                    info.insert(0, getHeading(3, -1));
                }

                if(sameResult && completed){
                    result.setText(Html.fromHtml(info.toString(), Html.FROM_HTML_MODE_LEGACY));
                }
                else {
                    completed = false;
                    animateText(result, info.toString());
                }

            } else {  // case 4
                if (subMrks == null) {
                    sgpa = 9.9f;
                    while (subMrks == null) {
                        subMrks = OnPrefPredictMarksAlgo.calculate(termWorkMrks, subPref, endSemMrks, endSemOrPref, internalMrks, sgpa);
                        sgpa -= 0.1f;
                    }
                    prefSgpa = OnPrefPredictMarksAlgo.preTC;
                }

                for (int i = 0; i < numOfSub; i++) {
                    String sub = "<u><b>" + subName[i] + "</b></u>" + getPreference(i);
                    String marks = "<u><b>" + subMrks[i] + "</b></u>";
                    if (endSemOrPref[i])
                        info.append("For ").append(sub).append(", your marks should be ").append(getCompareSymbolForSub(subMrks[i], endSemRange[i])).append(" your expected marks : ").append(marks).append(".<br><br>");
                    else
                        info.append("For ").append(sub).append(", your marks should be ").append(getCompareSymbolForSub(subMrks[i], endSemRange[i])).append(" ").append(marks).append(" out of ").append(endSemRange[i]).append(".<br><br>");
                }
                String pref = "<u><b>" + prefSgpa + "</b></u>";
                info.append("Based on the above combination, the overall SGPA you will achieve is ").append(getCompareSymbolForSgpa(allSubFull,prefSgpa)).append(pref).append(". However, if you still want your desired SGPA, try adjusting your preferences.<br><br>");
                info.append("All the best!!");

                if(previousResult.equals(info.toString())) {
                    sameResult=true;
                    info.insert(0,getHeading(4,index));
                }else {
                    saveResult(info);
                    info.insert(0, getHeading(4, -1));
                }

                if(sameResult && completed){
                    result.setText(Html.fromHtml(info.toString(), Html.FROM_HTML_MODE_LEGACY));
                }
                else {
                    completed = false;
                    animateText(result, info.toString());
                }
            }
        }

        bckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTextCompletion(completed);
                startActivity(new Intent(EndSemConsultant.this, SubjectPreferences.class));
            }
        });
        helpBtn.setOnClickListener(view -> {
            Intent intent = new Intent(EndSemConsultant.this, Help.class);
            intent.putExtra("bckConfig" , 6);


            startActivity(intent);
        });
    }
    @Override
    public void onBackPressed() {
        saveTextCompletion(completed);
        startActivity(new Intent(EndSemConsultant.this, SubjectPreferences.class));
        super.onBackPressed();
    }

    private void saveResult(StringBuilder result){
        SharedPreferences preferences = getSharedPreferences(SubjectPreferences.MARKS_DATA_PREF , MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("result", result.toString());
        editor.apply();
    }
    private void saveIndex(int index){
        SharedPreferences preferences = getSharedPreferences(SubjectPreferences.MARKS_DATA_PREF , MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("index", index);
        editor.apply();
    }
    private void saveTextCompletion(boolean state){
        SharedPreferences preferences = getSharedPreferences(SubjectPreferences.MARKS_DATA_PREF , MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("animation_state", state);
        editor.apply();
    }
    private void getSurveyData(){
        SharedPreferences preferences = getSharedPreferences(SubjectPreferences.MARKS_DATA_PREF , MODE_PRIVATE);
        String s = preferences.getString("sgpa" , null);
        previousResult = preferences.getString("result","");
        index = preferences.getInt("index",-1);
        completed = preferences.getBoolean("animation_state",false);
        if(s!=null && !s.isEmpty()) sgpa = Float.parseFloat(s);
        for(int i=0 ; i < termWorkMrks.length; i++){
            termWorkMrks[i] = preferences.getInt(TermWork.TW_SUB + (i+1) , -1);
            if(i < subPref.length){
                internalMrks[i] = preferences.getInt(InternalMarksInput.AVG_MRK_SUB + (i+1) , -1);
                subPref[i] = preferences.getInt(SubjectPreferences.PREF_SUB + (i+1) , -1);
                endSemOrPref[i] = preferences.getBoolean(SubjectPreferences.PREF_OR_ENDSEM_MRKS + (i+1), false);
                endSemMrks[i] = preferences.getInt(SubjectPreferences.ENDSEM_MRKS_SUB + (i+1), -1);
            }
        }
    }
    private String compare(float prefTc, float sgpaTc){
        if(prefTc==sgpaTc) return ". which matches your required SGPA.";
        else if(Math.abs(prefTc-sgpaTc)<=0.5) return ". which is close to your required SGPA.";
        return "";
    }
    private String getCompareSymbolForSub(int sub , int range) {
        if(sub==range) return "equal to";
        else {
            allSubFull = false;
            return "≥";
        }
    }
    private String getCompareSymbolForSgpa(Boolean allSubFull, float sgpa){
        if(sgpa==10) return ": ";
        if(!allSubFull) return "≥ ";
        else return ": ";
    }
    private String getPreference(int sub){
        if(OnPrefPredictMarksAlgo.easySub.contains(sub)) return " <b>(Pref : Easy)</b>";
        else if (OnPrefPredictMarksAlgo.interSub.contains(sub)) return " <b>(Pref : Inter)</b>";
        else if (OnPrefPredictMarksAlgo.hardSub.contains(sub)) return " <b>(Pref : Hard)</b>";
        return "";
    }
    private String getHeading(int c , int index) {
        Random random = new Random();
        switch (c) {
            case 0: {
                String str1 = "It seems you have completed your exams, so based on your input, the marks would appear as follows.<br><br>";
                String str2 = "Since you’re done with your exams, the marks according to your input would look like this.<br><br>";
                String str3 = "It looks like you’ve completed your exams; therefore, based on your input, the marks would appear like this.<br><br>";
                String[] strings = {str1, str2, str3};
                if(index!=-1) return strings[index];
                int randomIndex = random.nextInt(strings.length);
                saveIndex(randomIndex);
                return strings[randomIndex];
            }
            case 1 : {
                String str1 = "As no SGPA input was found, here is the combination of marks for achieving the highest possible SGPA based on your preferences.<br><br>";
                String str2 = "Since there is no SGPA input, here is the combination of marks that will generate the maximum possible SGPA according to your preferences.<br><br>";
                String[] strings = {str1, str2};
                if(index!=-1) return strings[index];
                int randomIndex = random.nextInt(strings.length);
                saveIndex(randomIndex);
                return strings[randomIndex];
            }
            case 2 : {
                String str1 = "Your preferences generate an SGPA higher than required, so some preferences have been adjusted to align with the desired SGPA. Rest assured, these adjustments were carefully analyzed.<br><br>";
                String str2 = "Your preferences gave an SGPA higher than required, so we made some adjustments to match the desired SGPA. Don’t worry, these adjustments were carefully analyzed.<br><br>";
                String[] strings = {str1, str2};
                if(index!=-1) return strings[index];
                int randomIndex = random.nextInt(strings.length);
                saveIndex(randomIndex);
                return strings[randomIndex];
            }
            case 3 : {
                String str1 = "After analyzing your unit test, term work, end-semester marks, and preferences, here is the final combination of your marks.<br><br>";
                String str2 = "Based on your unit test, term work, end-semester marks, and preferences, this is the final combination of your marks.<br><br>";
                String str3 = "Considering your unit test, term work, end-semester marks, and preferences, we have arrived at the final combination of your marks.<br><br>";
                String[] strings = {str1, str2, str3};
                if(index!=-1) return strings[index];
                int randomIndex = random.nextInt(strings.length);
                saveIndex(randomIndex);
                return strings[randomIndex];
            }
            case 4 : {
                String str1 = "Your preferences do not align with the desired SGPA, so we’ve calculated the highest possible SGPA based on your preferences, with the marks combination listed below.<br><br>";
                String str2 = "Since your preferences don’t allow to generate the required SGPA, so we’ve calculated the highest possible SGPA based on your preferences, with the marks combination listed below.<br><br>";
                String[] strings = {str1, str2};
                if(index!=-1) return strings[index];
                int randomIndex = random.nextInt(strings.length);
                saveIndex(randomIndex);
                return strings[randomIndex];
            }
            default:
                return "";
        }
    }

    public void animateText(TextView textView, String fullText) {
        Spanned styledText;
        styledText = Html.fromHtml(fullText, Html.FROM_HTML_MODE_LEGACY);
        final int[] index = {0};
        long delay = 0;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index[0] < styledText.length()) {
                    textView.setText(styledText.subSequence(0, index[0] + 1));
                    index[0]++;
                    handler.postDelayed(this, delay); // Schedule the next update
                }
                else completed = true;
            }
        }, delay);
    }
}