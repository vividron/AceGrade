package com.example.sgpa.help;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.sgpa.EndSemConsultant;
import com.example.sgpa.HomePage;
import com.example.sgpa.R;
import com.example.sgpa.InternalMarksInput;
import com.example.sgpa.Branch;
import com.example.sgpa.Semester;
import com.example.sgpa.TermWork;
import com.example.sgpa.SubjectPreferences;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Help extends AppCompatActivity {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_help);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.help), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView emailTextView = findViewById(R.id.emailTextView);
        CardView bugReport = findViewById(R.id.bug_report);
        LinearLayout bckBtn = findViewById(R.id.help_bck);
        Intent intentGet = getIntent();
        int bckConfig = intentGet.getIntExtra("bckConfig",0);
        Intent intent;
        switch (bckConfig){
            case 0 : intent = new Intent(Help.this, HomePage.class);
                break;
            case 1 : intent = new Intent(Help.this, Branch.class);
                break;
            case 2 : intent = new Intent(Help.this, Semester.class);
                break;
            case 3 : intent = new Intent(Help.this, TermWork.class);
                break;
            case 4 : intent = new Intent(Help.this, InternalMarksInput.class);
                break;
            case 5 : intent = new Intent(Help.this, SubjectPreferences.class);
                break;
            case 6 : intent = new Intent(Help.this, EndSemConsultant.class);
                break;
            default: intent = new Intent(Help.this, HomePage.class);
        }
        bckBtn.setOnClickListener(view -> startActivity(intent));
        bugReport.setOnClickListener(view -> showBugReportDialog());

        String BottomLine = "Have feedback or need support? Write to me at <u>vividron21205@gmail.com</u>.";

        SpannableString spannableString = new SpannableString(
                Html.fromHtml(BottomLine, Html.FROM_HTML_MODE_LEGACY)
        );

        final int emailStart = spannableString.toString().indexOf("vividron21205@gmail.com");
        final int emailEnd = emailStart + "vividron21205@gmail.com".length();

        spannableString.setSpan(
                new ForegroundColorSpan(Color.BLACK), // Default color
                emailStart, emailEnd,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        emailTextView.setText(spannableString);
        emailTextView.setMovementMethod(LinkMovementMethod.getInstance());

        emailTextView.setOnTouchListener((v, event) -> {
            // Detect the action
            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
                // Get the touched text position
                TextView textView = (TextView) v;
                CharSequence text = textView.getText();
                if (text instanceof Spannable) {
                    Spannable spannable = (Spannable) text;

                    // Find the clicked span (if any)
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    x -= textView.getTotalPaddingLeft();
                    y -= textView.getTotalPaddingTop();

                    x += textView.getScrollX();
                    y += textView.getScrollY();

                    Layout layout = textView.getLayout();
                    int line = layout.getLineForVertical(y);
                    int offset = layout.getOffsetForHorizontal(line, x);

                    // Check if the touched text is in the email range
                    if (offset >= emailStart && offset <= emailEnd) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            spannable.setSpan(
                                    new ForegroundColorSpan(Color.WHITE), // Pressed color
                                    emailStart, emailEnd,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            );
                            showCopyMenu(textView, "vividron21205@gmail.com");
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            spannable.setSpan(
                                    new ForegroundColorSpan(Color.BLACK), // Default color
                                    emailStart, emailEnd,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            );
                            v.performClick(); // Trigger performClick for accessibility
                        }
                        emailTextView.setText(spannable);
                        return true; // Indicate touch is handled
                    }
                }
            }
            return false; // Let the default handler process other touches
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);
        List<HelpItem> helpItems = new ArrayList<>();
        helpItems.add(new HelpItem("About AceGrade", "Whether you’re aiming for a specific grade or just exploring possibilities, AceGrade provides the tools you need to stay on track and succeed.\n\n"
                 +"1. Target Marks Prediction: Calculate the marks required in each subject to secure your desired SGPA (Semester Grade Point Average).\n\n"
                 +"2. SGPA Prediction: Accurately estimate your SGPA based on your expected or achieved marks.\n\n"
                 +"With its user-friendly interface and reliable calculations, the app empowers students to plan their academic performance effectively and achieve their desired outcomes."));
        helpItems.add(new HelpItem("What are term work marks?", "Term work marks are based on your performance in practical labs, projects, attendance, assignments, viva, etc. Essentially, they reflect how well you are performing and presenting your work in college."));
        helpItems.add(new HelpItem("Which term work marks range should I select?", "You should select the range based on your performance; if you haven't performed well, choose the average range. Don't worry, the ranges are designed to maintain accuracy. If still uncertain, leave the default selection."));
        helpItems.add(new HelpItem("What are internal marks?", "Internal marks are determined through internal assessment tests. Typically, two unit tests marks are either averaged or added together, depending on the syllabus."));
        helpItems.add(new HelpItem("How can I set a preference?", "Select your preference based on your understanding of the subject and how challenging you find it—easy, intermediate, or hard."));
        helpItems.add(new HelpItem("Some subject's exams are over. How do I set preferences?", "If you have completed the exams for some of your subjects, a message is available below the preferences input field.\n\nStep 1: Click on the text and enter the marks you expect to receive for that subject. For better accuracy, calculate your marks carefully. Do not enter a lower value if you expect to score more.\n\nStep 2: Select the preferences for the remaining subjects.\n\nStep 3: Enter the SGPA you desire. If you're unsure about your SGPA, leave the input empty and click 'Next.'"));
        HelpAdapter adapter = new HelpAdapter(helpItems);
        recyclerView.setAdapter(adapter);
    }
    private void showBugReportDialog() {
        if (!isConnectedToInternet()) {
            Toast.makeText(Help.this, "No internet connection. Please try again later.", Toast.LENGTH_SHORT).show();
            return;
        }
        // Inflate the custom dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.bug_report_dialog, null);
        EditText bugReportEditText = dialogView.findViewById(R.id.bug_report_edittext);
        TextView bugTitle = dialogView.findViewById(R.id.dialog_title);
        LinearLayout submitButton = dialogView.findViewById(R.id.submit_button);
        LinearLayout backButton = dialogView.findViewById(R.id.back_button);

        // Set up a TextWatcher to count the words or characters (up to 100 words)
        bugReportEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String text = charSequence.toString();
                int wordCount = text.split("\\s+").length;
                if (wordCount > 50) {
                    bugReportEditText.setError("Maximum 50 words allowed");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setCancelable(true);

        // Create the dialog instance
        AlertDialog dialog = builder.create();

        // Handle the "Submit" button click
        submitButton.setOnClickListener(v -> {
            String bugReportText = bugReportEditText.getText().toString();
            if (!bugReportText.isEmpty()){
                submitButton.setEnabled(false);
                bugTitle.setTypeface(null, Typeface.BOLD);
                bugTitle.setText("Uploading...");
                SharedPreferences preferences = getSharedPreferences(SubjectPreferences.MARKS_DATA_PREF, MODE_PRIVATE);
                StringBuilder termWorkData = new StringBuilder();
                StringBuilder internalData = new StringBuilder();
                StringBuilder subPrefData = new StringBuilder();
                StringBuilder endSemMrksData = new StringBuilder();
                StringBuilder endSemOrPrefData = new StringBuilder();

                for(int i=0; i<10 ; i++){
                    termWorkData.append(preferences.getInt(TermWork.TW_SUB + (i+1) , -1)).append(" ");
                    if(i<6){
                        internalData.append(preferences.getInt(InternalMarksInput.AVG_MRK_SUB + (i+1) , -1)).append(" ");
                        subPrefData.append(preferences.getInt(SubjectPreferences.PREF_SUB + (i+1) , -1)).append(" ");
                        endSemMrksData.append(preferences.getInt(SubjectPreferences.ENDSEM_MRKS_SUB + (i+1), -1)).append(" ");
                        endSemOrPrefData.append(preferences.getBoolean(SubjectPreferences.PREF_OR_ENDSEM_MRKS + (i+1), false)).append(" ");
                    }
                }

                String timestampKey = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(new java.util.Date());

                Map<String, Object> dataToUpload = new HashMap<>();
                dataToUpload.put("bugReport", bugReportText);
                dataToUpload.put("termWorkMarks", termWorkData.toString());
                dataToUpload.put("internalMarks", internalData.toString());
                dataToUpload.put("subPref", subPrefData.toString());
                dataToUpload.put("endSemMrks", endSemMrksData.toString());
                dataToUpload.put("endSemORPref", endSemOrPrefData.toString());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = database.getReference("BugReports").child(timestampKey);

                databaseReference.setValue(dataToUpload)
                        .addOnSuccessListener(aVoid -> {
                            dialog.dismiss();
                            showDialogAfterSubmission();
                        })
                        .addOnFailureListener(e -> {
                            dialog.dismiss();
                            Toast.makeText(Help.this, "Error uploading bug: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
        backButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }


    private void showDialogAfterSubmission() {
        // Create and set up the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("")
                .setMessage("Thank you for reporting the bug, and an apology for the inconvenience. The issue will be fixed soon. In the meantime, you can try refreshing the app, going to the home page and staying there for a moment, or reinstalling the app to see if the issue is resolved.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();  // Dismiss the dialog when "OK" is clicked
                    }
                })
                .setCancelable(false);  // Make the dialog non-cancelable (optional)

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Help.this, HomePage.class));
        super.onBackPressed();
    }

    private void showCopyMenu(View anchorView, String email) {
        // Create a PopupMenu
        PopupMenu popupMenu = new PopupMenu(anchorView.getContext(), anchorView);
        popupMenu.getMenu().add("Copy Email");

        popupMenu.setOnMenuItemClickListener(item -> {
            if (Objects.equals(item.getTitle(), "Copy Email")) {
                copyToClipboard(email);
                return true;
            }
            return false;
        });
        popupMenu.show();
    }
    private void copyToClipboard(String email) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Email Address", email);
        clipboard.setPrimaryClip(clip);
    }


}