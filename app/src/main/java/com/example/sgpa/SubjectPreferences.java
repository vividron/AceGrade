package com.example.sgpa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Html;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sgpa.help.Help;

public class SubjectPreferences extends AppCompatActivity {
    private final Spinner[] spinnerSub = new Spinner[6];
    private Vibrator vibrator;
    @SuppressLint("StaticFieldLeak")
    public static EditText sgpa;
    public static String[] sub;
    private final TextView[] subtv = new TextView[6];
    private final TextView[] endSemNext = new TextView[6];
    private final LinearLayout[] endSemBack = new LinearLayout[6];
    private final EditText[] endSemMrks = new EditText[6];
    private final int[] endSemRange = Subjects.getEndSemRange();
    public static String MARKS_DATA_PREF = null;
    public static final String PREF_SUB = "prefSub";
    public static final String ENDSEM_MRKS_SUB = "endSemMarksOfSubject";
    public static final String PREF_OR_ENDSEM_MRKS = "checkIfItIsPrefOrEndSemMrksForSubject";
    public static LinearLayout sgpa_val_layout;
    private TextView error_msg;
    private Button nxt, bck;
    private LinearLayout helpBtn;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subject_preference);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sub_pref), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        nxt = findViewById(R.id.sub_pref_nxt);
        bck = findViewById(R.id.sub_pref_bck);
        helpBtn = findViewById(R.id.help_pref);

        SharedPreferences preferences = getSharedPreferences("surveyData", MODE_PRIVATE);
        String branch = preferences.getString("branch", null);
        String sem = preferences.getString("sem", null);
        MARKS_DATA_PREF = branch + sem;

        if (branch != null && sem != null) {
            sub = Subjects.getSubjects(branch, sem);
        }
        assert sub != null;
        final boolean[] endSemOrPref = new boolean[sub.length]; // true mtlb it is endSem mrks and false mtlb it is Pref

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
        sgpa = findViewById(R.id.sgpa);
        sgpa_val_layout = findViewById(R.id.sgpa_val_layout);
        error_msg = findViewById(R.id.endSem_msg);

        // Create an ArrayAdapter using the marks range array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pref, R.layout.spinner_item);   // Use custom layout
        adapter.setDropDownViewResource(R.layout.custom_spinner);
        // Set adapter for each Spinner
        spinnerSub[0].setAdapter(adapter);
        spinnerSub[1].setAdapter(adapter);
        spinnerSub[2].setAdapter(adapter);
        spinnerSub[3].setAdapter(adapter);
        spinnerSub[4].setAdapter(adapter);

        spinnerSub[0].setSelection(1);
        spinnerSub[1].setSelection(1);
        spinnerSub[2].setSelection(1);
        spinnerSub[3].setSelection(1);
        spinnerSub[4].setSelection(1);

        endSemNext[0] = findViewById(R.id.s1_endSem_n);
        endSemNext[1] = findViewById(R.id.s2_endSem_n);
        endSemNext[2] = findViewById(R.id.s3_endSem_n);
        endSemNext[3] = findViewById(R.id.s4_endSem_n);
        endSemNext[4] = findViewById(R.id.s5_endSem_n);

        endSemBack[0] = findViewById(R.id.s1_endSem_b);
        endSemBack[1] = findViewById(R.id.s2_endSem_b);
        endSemBack[2] = findViewById(R.id.s3_endSem_b);
        endSemBack[3] = findViewById(R.id.s4_endSem_b);
        endSemBack[4] = findViewById(R.id.s5_endSem_b);

        endSemMrks[0] = findViewById(R.id.sub1_endSem_mrk);
        endSemMrks[1] = findViewById(R.id.sub2_endSem_mrk);
        endSemMrks[2] = findViewById(R.id.sub3_endSem_mrk);
        endSemMrks[3] = findViewById(R.id.sub4_endSem_mrk);
        endSemMrks[4] = findViewById(R.id.sub5_endSem_mrk);

        if (sub.length == 6) {
            LinearLayout layout6 = findViewById(R.id.sub6_layout);
            layout6.setVisibility(View.VISIBLE);
            subtv[5] = findViewById(R.id.sub6);
            subtv[5].setText(sub[5]);
            spinnerSub[5] = findViewById(R.id.spinner_subject6);
            spinnerSub[5].setAdapter(adapter);
            spinnerSub[5].setSelection(1);
            endSemNext[5] = findViewById(R.id.s6_endSem_n);
            endSemBack[5] = findViewById(R.id.s6_endSem_b);
            endSemMrks[5] = findViewById(R.id.sub6_endSem_mrk);
        }
        for(int i=0; i<sub.length; i++){
            endSemNext[i].setText(Html.fromHtml("Finished with this paper? <u><b>Click here</b></u>."));
            int subNum = i;
            endSemNext[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinnerSub[subNum].setVisibility(View.GONE);
                    endSemNext[subNum].setVisibility(View.GONE);
                    endSemMrks[subNum].setVisibility(View.VISIBLE);
                    endSemBack[subNum].setVisibility(View.VISIBLE);
                    endSemOrPref[subNum] = true;
                    boolean allTrue = true;
                    for (boolean value : endSemOrPref) {
                        if (!value) {
                            allTrue = false;
                            break;
                        }
                    }
                    if(allTrue) sgpa_val_layout.setVisibility(View.GONE);
                }
            });
            endSemBack[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinnerSub[subNum].setVisibility(View.VISIBLE);
                    endSemNext[subNum].setVisibility(View.VISIBLE);
                    endSemMrks[subNum].setVisibility(View.GONE);
                    endSemBack[subNum].setVisibility(View.GONE);
                    endSemOrPref[subNum] = false;
                    sgpa_val_layout.setVisibility(View.VISIBLE);
                }
            });
        }

        restoreSelectedFields(spinnerSub, sub.length, endSemNext, endSemBack, endSemOrPref, endSemMrks);

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] validInput = checkValidInputs(endSemMrks, endSemOrPref, endSemRange, sub.length, vibrator);
                if (validInput[0]) {
                    error_msg.setTextColor(getResources().getColor(R.color.red, getTheme()));
                    error_msg.setText("Invalid input. Check the marks range or empty inputs");
                } else if (validInput[1]) {
                    error_msg.setTextColor(getResources().getColor(R.color.red, getTheme()));
                    error_msg.setText("Unable to predict if your marks are below passing.");
                } else if (sgpa.getText().toString().equals("1")){
                    sgpa.setText("");
                    if (vibrator != null) vibrator.vibrate(100);
                    sgpa.setBackgroundResource(R.drawable.bg_edit_view_3);
                }else {
                    sgpa.setBackgroundResource(R.drawable.bg_edit_view_2);
                    saveSelectedFields(spinnerSub, sub.length, endSemOrPref, endSemMrks);
                    Intent intent = new Intent(SubjectPreferences.this, EndSemConsultant.class);
                    startActivity(intent);
                }
            }
        });

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSelectedFields(spinnerSub , sub.length, endSemOrPref, endSemMrks);
                startActivity(new Intent(SubjectPreferences.this, InternalMarksInput.class));
            }
        });
        EditText myEditText = findViewById(R.id.sgpa);
        myEditText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(1, 2)});
        helpBtn.setOnClickListener(view -> {
            Intent intent = new Intent(SubjectPreferences.this, Help.class);
            intent.putExtra("bckConfig" , 5);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SubjectPreferences.this, InternalMarksInput.class));
    }

    public static class DecimalDigitsInputFilter implements InputFilter {
        private final int digitsBeforeDecimal;
        private final int digitsAfterDecimal;

        public DecimalDigitsInputFilter(int digitsBeforeDecimal, int digitsAfterDecimal) {
            this.digitsBeforeDecimal = digitsBeforeDecimal;
            this.digitsAfterDecimal = digitsAfterDecimal;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   android.text.Spanned dest, int dstart, int dend) {
            StringBuilder newText = new StringBuilder(dest);
            newText.replace(dstart, dend, source.subSequence(start, end).toString());

            String resultText = newText.toString();

            if (resultText.equals("10")) {
                return null;
            }

            // Check if input is exactly "10" (special case)
            if(resultText.equals(".")){
                return "";
            }

            for(int i=0; i<6; i++){
                if(i==1) continue;
                if(resultText.equals(String.valueOf(i))){
                    return "";
                }
            }

            // Check if there's more than one decimal point
            int decimalCount = 0;
            for (int i = 0; i < resultText.length(); i++) {
                if (resultText.charAt(i) == '.') {
                    decimalCount++;
                }
            }
            if (decimalCount > 1) {
                return "";
            }

            // Split on the decimal point, if it exists
            String[] parts = resultText.split("\\.");

            // Check the digits before the decimal point
            if (parts[0].length() > digitsBeforeDecimal) {
                return "";
            }

            // Check the digits after the decimal point
            if (parts.length > 1 && parts[1].length() > digitsAfterDecimal) {
                return "";
            }
            return null;
        }
    }

    private boolean[] checkValidInputs(EditText[] endSemMrks, boolean[] endSemOrPref, int[] endSemRange, int length, Vibrator vibrator){
        boolean[] invalid = {false,false};
        for (int i = 0 ; i<length ; i++) {
            if(endSemOrPref[i]) {
                if (endSemMrks[i].getText().toString().isEmpty()) {
                    endSemMrks[i].setBackgroundResource(R.drawable.bg_edit_view_3);
                    invalid[0] = true;
                    if (vibrator != null) vibrator.vibrate(100);
                    continue;
                }
                int endSemMrk = Integer.parseInt(endSemMrks[i].getText().toString());
                if (endSemMrk > endSemRange[i]) {
                    endSemMrks[i].setBackgroundResource(R.drawable.bg_edit_view_3);
                    invalid[0] = true;
                    if (vibrator != null) vibrator.vibrate(100);
                } else if (endSemMrk < getPassing(endSemRange[i])) {
                    endSemMrks[i].setBackgroundResource(R.drawable.bg_edit_view_3);
                    invalid[1] = true;
                    if (vibrator != null) vibrator.vibrate(100);
                }
                if (endSemMrk <= endSemRange[i] && endSemMrk >= getPassing(endSemRange[i]))
                    endSemMrks[i].setBackgroundResource(R.drawable.bg_edit_view_2);
            }
        }
        return invalid;
    }

    private static int getPassing(int endSemRange) {return (int) (endSemRange * 0.4);}

    private void saveSelectedFields(Spinner[] s, int numOfSub, boolean[] endSemOrPref, EditText[] endSemMrks) {
        SharedPreferences preferences = getSharedPreferences(MARKS_DATA_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("sgpa" , sgpa.getText().toString());
        for (int i = 0; i < numOfSub; i++) {
            editor.putInt(PREF_SUB + (i + 1), s[i].getSelectedItemPosition());
            editor.putBoolean(PREF_OR_ENDSEM_MRKS + (i+1), endSemOrPref[i]);
            if(endSemOrPref[i]) if(!endSemMrks[i].getText().toString().isEmpty()) editor.putInt(ENDSEM_MRKS_SUB + (i+1), Integer.parseInt(endSemMrks[i].getText().toString()));
        }
        editor.apply();
    }

    private void restoreSelectedFields(Spinner[] s, int numOfSub, TextView[] nxt, LinearLayout[] bck, boolean[] endSemOrPref, EditText[] endSemMrks) {
        SharedPreferences preferences = getSharedPreferences(MARKS_DATA_PREF, MODE_PRIVATE);
        sgpa.setText(preferences.getString("sgpa",null));
        for (int i = 0; i < numOfSub; i++) {
            int position = preferences.getInt(PREF_SUB + (i + 1), -1);
            endSemOrPref[i] = preferences.getBoolean(PREF_OR_ENDSEM_MRKS + (i+1), false);
            if(endSemOrPref[i]) {
                s[i].setVisibility(View.GONE);
                nxt[i].setVisibility(View.GONE);
                endSemMrks[i].setVisibility(View.VISIBLE);
                int marks = preferences.getInt(ENDSEM_MRKS_SUB + (i + 1), -1);
                if(marks!=-1) endSemMrks[i].setText(String.valueOf(marks));
                bck[i].setVisibility(View.VISIBLE);
            }
            else if(position!=-1) s[i].setSelection(position);
        }
        boolean allTrue = true;
        for (boolean value : endSemOrPref) {
            if (!value) {
                allTrue = false;
                break;
            }
        }
        if(allTrue) sgpa_val_layout.setVisibility(View.GONE);
    }
}