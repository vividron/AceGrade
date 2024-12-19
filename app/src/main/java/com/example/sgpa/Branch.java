package com.example.sgpa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sgpa.help.Help;

import java.util.ArrayList;
import java.util.List;

public class Branch extends AppCompatActivity {
    Button nxt, bck;
    private List<TextView> textViewList;
    private TextView currentTextFieldSelected = null;
    private LinearLayout helpBtn;
    private static final String PREFS_NAME = "surveyData";
    private static final String SELECTED_TEXT_VIEW_KEY = "branchSelectedTextview";
    private static final String BUTTON_STATE = "branchButtonState";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_branch);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.select_branch), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nxt = findViewById(R.id.branch_next);
        bck = findViewById(R.id.branch_bck);
        helpBtn = findViewById(R.id.help_select_branch);

        TextView myTextView1 = findViewById(R.id.cs);
        TextView myTextView2 = findViewById(R.id.it);
        TextView myTextView3 = findViewById(R.id.elec);
        TextView myTextView4 = findViewById(R.id.ecs);
        TextView myTextView5 = findViewById(R.id.extc);

        // Add TextViews to a list
        textViewList = new ArrayList<>();
        textViewList.add(myTextView1);
        textViewList.add(myTextView2);
        textViewList.add(myTextView3);
        textViewList.add(myTextView4);
        textViewList.add(myTextView5);

        // Set click listeners for each TextView
        setClickListener(myTextView1);
        setClickListener(myTextView2);
        setClickListener(myTextView3);
        setClickListener(myTextView4);
        setClickListener(myTextView5);

        restoreSelectedChanges();

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Branch.this, Semester.class);
                startActivity(intent);
            }
        });

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Branch.this, HomePage.class));
            }
        });
        helpBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Branch.this, Help.class);
            intent.putExtra("bckConfig" , 1);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Branch.this, HomePage.class));
    }

    private void setClickListener(final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (textView == currentTextFieldSelected) {
                    textView.setBackgroundColor(getResources().getColor(R.color.dark_blue));
                    currentTextFieldSelected = null;
                    nxt.setEnabled(false);
                    clearSelectedTextView();

                } else {
                    clearOtherSelections(textView);
                    textView.setBackgroundColor(getResources().getColor(R.color.dark1_blue));
                    currentTextFieldSelected = textView;
                    saveSelectedTextView(textView.getId());
                    nxt.setEnabled(true);
                    saveButtonState(nxt.getId());
                }
            }
        });
    }

    private void clearOtherSelections(TextView selectedTextView) {
        for (TextView textView : textViewList) {
            if (textView != selectedTextView) {
                textView.setBackgroundColor(getResources().getColor(R.color.dark_blue));
            }
        }
    }

    private void saveSelectedTextView(int textViewId) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SELECTED_TEXT_VIEW_KEY, textViewId);
        editor.putString("branch", currentTextFieldSelected.getText().toString());
        editor.apply();
    }

    private void saveButtonState(int button){
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(BUTTON_STATE, button);
        editor.apply();
    }

    private void clearSelectedTextView() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(SELECTED_TEXT_VIEW_KEY);
        editor.apply();
    }

    private void restoreSelectedChanges() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int selectedTextViewId = preferences.getInt(SELECTED_TEXT_VIEW_KEY, -1);
        int buttonState = preferences.getInt(BUTTON_STATE , -1);
        if (selectedTextViewId != -1) {
            for (TextView textView : textViewList) {
                if (textView.getId() == selectedTextViewId) {
                    textView.setBackgroundColor(getResources().getColor(R.color.dark1_blue));
                    currentTextFieldSelected = textView;
                    break;
                }
            }
            if(buttonState != -1){
                nxt.setEnabled(true);
            }
        }
    }
}