package com.example.sgpa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sgpa.help.Help;

public class InternalMarksInput extends AppCompatActivity {
    private LinearLayout helpBtn;
    private Vibrator vibrator;
    public static String[] sub;
    private int[] range;
    private final EditText[] ut1_marks = new EditText[6];
    private final EditText[] ut2_marks = new EditText[6];
    private final TextView[] subtv = new TextView[6];
    private int n;
    private TextView error_msg;
    private String MARKS_DATA_PREF = null;
    public static final String UT1_MRK_SUB = "unitTest1MarksSub";
    public static final String UT2_MRK_SUB = "unitTest2MarksSub";
    public static final String AVG_MRK_SUB = "avgMarksSub";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_internal_marks_input);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.internal_marks), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        SharedPreferences preferences = getSharedPreferences("surveyData", MODE_PRIVATE);
        String branch = preferences.getString("branch", null);
        String sem = preferences.getString("sem", null);
        MARKS_DATA_PREF = branch + sem;

        if (branch != null && sem != null) {
            sub = Subjects.getSubjects(branch, sem);
            range = Subjects.getInternalMarksRange();
        }
        if(Semester.s1Ors2) n=2;
        else n=1;
        error_msg = findViewById(R.id.internal_marks_msg);
        subtv[0] = findViewById(R.id.sub1);
        subtv[0].setText(sub[0] + "   (" + range[0]/n + " Marks)");
        subtv[1] = findViewById(R.id.sub2);
        subtv[1].setText(sub[1] + "   (" + range[1]/n + " Mark)");
        subtv[2] = findViewById(R.id.sub3);
        subtv[2].setText(sub[2] + "   (" + range[2]/n + " Marks)");
        subtv[3] = findViewById(R.id.sub4);
        subtv[3].setText(sub[3] + "   (" + range[3]/n + " Marks)");
        subtv[4] = findViewById(R.id.sub5);
        subtv[4].setText(sub[4] + "   (" + range[4]/n + " Marks)");

        ut1_marks[0] = findViewById(R.id.ut1_sub1_mrk);
        ut1_marks[1] = findViewById(R.id.ut1_sub2_mrk);
        ut1_marks[2] = findViewById(R.id.ut1_sub3_mrk);
        ut1_marks[3] = findViewById(R.id.ut1_sub4_mrk);
        ut1_marks[4] = findViewById(R.id.ut1_sub5_mrk);
        ut2_marks[0] = findViewById(R.id.ut2_sub1_mrk);
        ut2_marks[1] = findViewById(R.id.ut2_sub2_mrk);
        ut2_marks[2] = findViewById(R.id.ut2_sub3_mrk);
        ut2_marks[3] = findViewById(R.id.ut2_sub4_mrk);
        ut2_marks[4] = findViewById(R.id.ut2_sub5_mrk);

        if (sub.length == 6) {
            LinearLayout layout6 = findViewById(R.id.sub6_layout);
            layout6.setVisibility(View.VISIBLE);
            subtv[5] = findViewById(R.id.sub6);
            subtv[5].setText(sub[5] + "   (" + range[5]/n + " Marks)");
            ut1_marks[5] = findViewById(R.id.ut1_sub6_mrk);
            ut2_marks[5] = findViewById(R.id.ut2_sub6_mrk);
        }

        restoreSelectedRange(ut1_marks, ut2_marks, sub.length);

        Button nxt = findViewById(R.id.internal_marks_next);
        Button bck = findViewById(R.id.internal_marks_bck);
        helpBtn = findViewById(R.id.help_ut);

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] validInput = checkValidInputs(ut1_marks, ut2_marks, range, sub.length, Semester.s1Ors2, vibrator);
                if (validInput[0]) {
                    error_msg.setTextColor(getResources().getColor(R.color.red, getTheme()));
                    error_msg.setText("Invalid input. Check the marks range or empty inputs");
                } else if (validInput[1]) {
                    error_msg.setTextColor(getResources().getColor(R.color.red, getTheme()));
                    error_msg.setText("Unable to predict if you have an internal KT.");
                } else {
                    saveSelectedRange(ut1_marks, ut2_marks, sub.length);
                    Intent intent = new Intent(InternalMarksInput.this, SubjectPreferences.class);
                    startActivity(intent);
                }
            }
        });

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSelectedRange(ut1_marks, ut2_marks, sub.length);
                startActivity(new Intent(InternalMarksInput.this, TermWork.class));
            }
        });
        helpBtn.setOnClickListener(view -> {
            Intent intent = new Intent(InternalMarksInput.this, Help.class);
            intent.putExtra("bckConfig" , 4);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(InternalMarksInput.this, TermWork.class));
    }

    public static boolean[] checkValidInputs(EditText[] ut1 , EditText[] ut2 , int[] range , int length, boolean s1Ors2, Vibrator vibrator){
        boolean[] invalid = {false,false};
        for (int i = 0 ; i<length ; i++) {
            if (ut1[i].getText().toString().isEmpty() || ut2[i].getText().toString().isEmpty()) {
                if (ut1[i].getText().toString().isEmpty())
                    ut1[i].setBackgroundResource(R.drawable.bg_edit_view_3);
                if (ut2[i].getText().toString().isEmpty())
                    ut2[i].setBackgroundResource(R.drawable.bg_edit_view_3);
                invalid[0] = true;
                if (vibrator != null) vibrator.vibrate(100);
                continue;
            }
            int utf = Integer.parseInt(ut1[i].getText().toString());
            int uts = Integer.parseInt(ut2[i].getText().toString());
            int endRange;
            if(!s1Ors2) endRange = range[i];
            else endRange = range[i]/2;
            if (utf > endRange) {
                ut1[i].setBackgroundResource(R.drawable.bg_edit_view_3);
                invalid[0] = true;
                if (vibrator != null) vibrator.vibrate(100);
            }
            else if (utf < getPassing(range[i], s1Ors2)) {
                ut1[i].setBackgroundResource(R.drawable.bg_edit_view_3);
                invalid[1] = true;
                if (vibrator != null) vibrator.vibrate(100);
            }
            if(utf <= endRange && utf >= getPassing(range[i], s1Ors2)) ut1[i].setBackgroundResource(R.drawable.bg_edit_view_2);
            if (uts > endRange) {
                ut2[i].setBackgroundResource(R.drawable.bg_edit_view_3);
                invalid[0] = true;
                if (vibrator != null) vibrator.vibrate(100);
            }
            else if (uts < getPassing(range[i], s1Ors2)) {
                ut2[i].setBackgroundResource(R.drawable.bg_edit_view_3);
                invalid[1] = true;
                if (vibrator != null) vibrator.vibrate(100);
            }
            if(uts <= endRange && uts >= getPassing(range[i], s1Ors2)) ut2[i].setBackgroundResource(R.drawable.bg_edit_view_2);
        }
        return invalid;
    }

    private static int getPassing(int range, boolean sem1OrSem2){
        if(!sem1OrSem2) return (int) (range * 0.4);
        else return (int) (range * 0.4)/2;
    }

    private void saveSelectedRange(EditText[] ut1 , EditText[] ut2 , int numOfSub){
        SharedPreferences preferences = getSharedPreferences(MARKS_DATA_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        for (int i=0 ; i<numOfSub; i++) {
            if(!ut1[i].getText().toString().isEmpty()) editor.putString(UT1_MRK_SUB + (i + 1), ut1[i].getText().toString());
            if(!ut2[i].getText().toString().isEmpty()) editor.putString(UT2_MRK_SUB + (i + 1), ut2[i].getText().toString());
            if(!ut1[i].getText().toString().isEmpty() && !ut2[i].getText().toString().isEmpty()) {
                int ut1Mrk = Integer.parseInt(ut1[i].getText().toString());
                int ut2Mrk = Integer.parseInt(ut2[i].getText().toString());
                double avgMrk;
                if(n==2)  avgMrk = ut1Mrk + ut2Mrk;
                else avgMrk = (ut1Mrk + ut2Mrk) / 2.0;
                editor.putInt(AVG_MRK_SUB + (i + 1), round(avgMrk));
            }
        }
        editor.apply();
    }

    private void restoreSelectedRange(EditText[] ut1, EditText[] ut2, int numOfSub){
        SharedPreferences preferences = getSharedPreferences(MARKS_DATA_PREF, MODE_PRIVATE);
        for (int i=0 ; i<numOfSub ; i++){
            String ut1_mrk = preferences.getString(UT1_MRK_SUB + (i+1),null);
            String ut2_mrk = preferences.getString(UT2_MRK_SUB + (i+1) , null);
            if(ut1_mrk != null) ut1[i].setText(ut1_mrk);
            if(ut2_mrk != null) ut2[i].setText(ut2_mrk);
        }
    }

    public static int round(double value) {

        int integerPart = (int) value;
        double decimalPart = value - integerPart;

        if (decimalPart >= 0.5) {
            return integerPart + 1;
        } else {
            return integerPart;
        }
    }
}