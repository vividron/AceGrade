package com.example.sgpa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
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

public class Semester extends AppCompatActivity {
    private Button nxt;
    private LinearLayout helpBtn;
    private List<TextView> textViewList;
    TextView currentTextFieldSelected = null;
    public static boolean s1Ors2;
    private static final String SELECT_SEM = "surveyData";
    private static final String SELECTED_TEXT_VIEW_KEY = "semTextview";
    private static final String BUTTON_STATE = "semButtonState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_sem);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.select_sem), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView textView = findViewById(R.id.sem5);
        String styledText1 = "Semester 5 <i> Coming Soon</i>";
        textView.setText(Html.fromHtml(styledText1));

        TextView textView2 = findViewById(R.id.sem6);
        String styledText2 = "Semester 6 <i> Coming Soon</i>";
        textView2.setText(Html.fromHtml(styledText2));

        TextView textView3 = findViewById(R.id.sem7);
        String styledText3 = "Semester 7 <i> Coming Soon</i>";
        textView3.setText(Html.fromHtml(styledText3));

        TextView textView4 = findViewById(R.id.sem8);
        String styledText4 = "Semester 8 <i> Coming Soon</i>";
        textView4.setText(Html.fromHtml(styledText4));

        nxt = findViewById(R.id.sem_next);
        helpBtn = findViewById(R.id.help_select_sem);
        Button bck = findViewById(R.id.sem_bck);

        TextView myTextView1 = findViewById(R.id.sem1);
        TextView myTextView2 = findViewById(R.id.sem2);
        TextView myTextView3 = findViewById(R.id.sem3);
        TextView myTextView4 = findViewById(R.id.sem4);

        // Add TextViews to a list
        textViewList = new ArrayList<>();
        textViewList.add(myTextView1);
        textViewList.add(myTextView2);
        textViewList.add(myTextView3);
        textViewList.add(myTextView4);

        // Set click listeners for each TextView
        setClickListener(myTextView1);
        setClickListener(myTextView2);
        setClickListener(myTextView3);
        setClickListener(myTextView4);

        restoreSelectedChanges();


        nxt.setOnClickListener(view -> {
            Intent intent = new Intent(Semester.this, TermWork.class);
            startActivity(intent);
        });
        bck.setOnClickListener(view -> {
            Intent intent = new Intent(Semester.this, Branch.class);
            startActivity(intent);
        });
        helpBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Semester.this, Help.class);
            intent.putExtra("bckConfig" , 2d);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Semester.this, Branch.class));
    }

    private void setClickListener(final TextView textView) {
        textView.setOnClickListener(v -> {

            if (textView == currentTextFieldSelected) {
                textView.setBackgroundColor(getResources().getColor(R.color.dark_blue));
                currentTextFieldSelected = null;
                nxt.setEnabled(false);
                clearSelectedTextView();

            } else {
                clearOtherSelections(textView);
                textView.setBackgroundColor(getResources().getColor(R.color.dark1_blue)); // Set highlight color
                currentTextFieldSelected = textView;
                s1Ors2 = textView.getText().toString().equals("Semester 1") || textView.getText().toString().equals("Semester 2");
                saveSelectedTextView(textView.getId());
                nxt.setEnabled(true);
                saveButtonState(nxt.getId());
            }
        });
    }

    private void clearOtherSelections(TextView selectedTextView) {
        for (TextView textView : textViewList) {
            if (textView != selectedTextView) {
                textView.setBackgroundColor(getResources().getColor(R.color.dark_blue)); // Reset color to default
            }
        }
    }

    private void saveSelectedTextView(int textViewId) {
        SharedPreferences preferences = getSharedPreferences(SELECT_SEM, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SELECTED_TEXT_VIEW_KEY, textViewId);
        editor.putString("sem" , currentTextFieldSelected.getText().toString());
        editor.apply();
    }

    private void saveButtonState(int button){
        SharedPreferences preferences = getSharedPreferences(SELECT_SEM, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(BUTTON_STATE, button);
        editor.apply();
    }

    private void clearSelectedTextView() {
        SharedPreferences preferences = getSharedPreferences(SELECT_SEM, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(SELECTED_TEXT_VIEW_KEY);
        editor.apply();
    }

    private void restoreSelectedChanges() {
        SharedPreferences preferences = getSharedPreferences(SELECT_SEM, MODE_PRIVATE);
        int selectedTextViewId = preferences.getInt(SELECTED_TEXT_VIEW_KEY, -1);
        int buttonState = preferences.getInt(BUTTON_STATE , -1);
        if (selectedTextViewId != -1) {
            for (TextView textView : textViewList) {
                if (textView.getId() == selectedTextViewId) {
                    textView.setBackgroundColor(getResources().getColor(R.color.dark1_blue));
                    currentTextFieldSelected = textView;
                    s1Ors2 = textView.getText().toString().equals("Semester 1") || textView.getText().toString().equals("Semester 2");
                    break;
                }
            }
            if(buttonState != -1){
                nxt.setEnabled(true);
            }
        }
    }
}