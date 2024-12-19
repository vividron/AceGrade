package com.example.sgpa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sgpa.help.Help;

public class TermWork extends AppCompatActivity {
    private final Spinner[] spinnerSub = new Spinner[9];
    public static String[] sub;
    private final TextView[] subtv = new TextView[9];
    private int[] termWorkRange;
    private String MARKS_DATA_PREF = null;
    public static final String TW_SUB = "termWorkSub";
    Button bck , nxt;
    private LinearLayout helpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_tw_marks);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.term_work_marks), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences preferences = getSharedPreferences("surveyData" , MODE_PRIVATE);
        String branch = preferences.getString("branch",null);
        String sem = preferences.getString("sem" , null);
        MARKS_DATA_PREF = branch+sem;

        if(branch != null && sem != null){
            sub = Subjects.getTermWorkSubjects(branch,sem);
            termWorkRange = Subjects.getTermWorkRange();
        }

        subtv[0] = findViewById(R.id.sub1);
        subtv[0].setText(sub[0]);
        subtv[1] = findViewById(R.id.sub2);
        subtv[1].setText(sub[1]);
        subtv[2] = findViewById(R.id.sub3);
        subtv[2].setText(sub[2]);
        subtv[3] = findViewById(R.id.sub4);
        subtv[3].setText(sub[3]);
        subtv[4] = findViewById(R.id.sub5);
        subtv[4].setText(sub[4]);

        spinnerSub[0] = findViewById(R.id.spinner_subject1);
        spinnerSub[1] = findViewById(R.id.spinner_subject2);
        spinnerSub[2] = findViewById(R.id.spinner_subject3);
        spinnerSub[3] = findViewById(R.id.spinner_subject4);
        spinnerSub[4] = findViewById(R.id.spinner_subject5);

        // Create an ArrayAdapter using the marks range array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_25 = ArrayAdapter.createFromResource(this,
                R.array.marks_range_25, R.layout.spinner_item);   // Use custom layout
        adapter_25.setDropDownViewResource(R.layout.custom_spinner);
        ArrayAdapter<CharSequence> adapter_50 = ArrayAdapter.createFromResource(this,
                R.array.marks_range_50, R.layout.spinner_item);   // Use custom layout
        adapter_50.setDropDownViewResource(R.layout.custom_spinner);
        ArrayAdapter<CharSequence> adapter_75 = ArrayAdapter.createFromResource(this,
                R.array.marks_range_75, R.layout.spinner_item);   // Use custom layout
        adapter_75.setDropDownViewResource(R.layout.custom_spinner);

        settingAdapter(spinnerSub[0], termWorkRange[0], adapter_25, adapter_50, adapter_75);
        settingAdapter(spinnerSub[1], termWorkRange[1], adapter_25, adapter_50, adapter_75);
        settingAdapter(spinnerSub[2], termWorkRange[2], adapter_25, adapter_50, adapter_75);
        settingAdapter(spinnerSub[3], termWorkRange[3], adapter_25, adapter_50, adapter_75);
        settingAdapter(spinnerSub[4], termWorkRange[4], adapter_25, adapter_50, adapter_75);

        spinnerSub[0].setSelection(2);
        spinnerSub[1].setSelection(2);
        spinnerSub[2].setSelection(2);
        spinnerSub[3].setSelection(2);
        spinnerSub[4].setSelection(2);

        for(int i=6 ; i<=sub.length ; i++){
            if(i==6){
                LinearLayout layout6 = findViewById(R.id.sub6_layout);
                layout6.setVisibility(View.VISIBLE);
                subtv[5] = findViewById(R.id.sub6);
                subtv[5].setText(sub[5]);
                spinnerSub[5] = findViewById(R.id.spinner_subject6);
                settingAdapter(spinnerSub[5], termWorkRange[5], adapter_25, adapter_50, adapter_75);
                spinnerSub[5].setSelection(2);
            }
            else if(i==7){
                LinearLayout layout7 = findViewById(R.id.sub7_layout);
                layout7.setVisibility(View.VISIBLE);
                subtv[6] = findViewById(R.id.sub7);
                subtv[6].setText(sub[6]);
                spinnerSub[6] = findViewById(R.id.spinner_subject7);
                settingAdapter(spinnerSub[6], termWorkRange[6], adapter_25, adapter_50, adapter_75);
                spinnerSub[6].setSelection(2);
            }
            else if(i==8){
                LinearLayout layout8 = findViewById(R.id.sub8_layout);
                layout8.setVisibility(View.VISIBLE);
                subtv[7] = findViewById(R.id.sub8);
                subtv[7].setText(sub[7]);
                spinnerSub[7] = findViewById(R.id.spinner_subject8);
                settingAdapter(spinnerSub[7], termWorkRange[7], adapter_25, adapter_50, adapter_75);
                spinnerSub[7].setSelection(2);
            }
            else if (i==9) {
                LinearLayout layout9 = findViewById(R.id.sub9_layout);
                layout9.setVisibility(View.VISIBLE);
                subtv[8] = findViewById(R.id.sub9);
                subtv[8].setText(sub[8]);
                spinnerSub[8] = findViewById(R.id.spinner_subject9);
                settingAdapter(spinnerSub[8], termWorkRange[8], adapter_25, adapter_50, adapter_75);
                spinnerSub[8].setSelection(2);
            }
        }

        restoreSelectedRange(spinnerSub , sub.length);

        nxt = findViewById(R.id.tm_marks_nxt);
        bck = findViewById(R.id.tm_marks_bck);
        helpBtn = findViewById(R.id.help_tm);

        nxt.setOnClickListener(view -> {
            saveSelectedRange(spinnerSub , sub.length);
            Intent intent = new Intent(TermWork.this , InternalMarksInput.class);
            startActivity(intent);
        });

        bck.setOnClickListener(view -> {
            saveSelectedRange(spinnerSub , sub.length);
            startActivity(new Intent(TermWork.this , Semester.class));
        });
        helpBtn.setOnClickListener(view -> {
            Intent intent = new Intent(TermWork.this, Help.class);
            intent.putExtra("bckConfig" , 3);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TermWork.this , Semester.class));
    }

    private void settingAdapter(Spinner s , int range, ArrayAdapter<CharSequence> adapter_25, ArrayAdapter<CharSequence> adapter_50, ArrayAdapter<CharSequence> adapter_75){
        if(range == 25) s.setAdapter(adapter_25);
        else if (range == 50) s.setAdapter(adapter_50);
        else if (range == 75) s.setAdapter(adapter_75);
    }
    private void saveSelectedRange(Spinner[] s , int numOfSub){
        SharedPreferences preferences = getSharedPreferences(MARKS_DATA_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        for (int i=0 ; i<numOfSub; i++) {
            editor.putInt(TW_SUB + (i+1), s[i].getSelectedItemPosition());
        }
        editor.apply();
    }

    private void restoreSelectedRange(Spinner[] s , int numOfSub){
        SharedPreferences preferences = getSharedPreferences(MARKS_DATA_PREF, MODE_PRIVATE);
        for (int i=0 ; i<numOfSub ; i++){
            int position = preferences.getInt(TW_SUB + (i+1),-1);
            if(position != -1){
                s[i].setSelection(position);
            }
        }
    }
}