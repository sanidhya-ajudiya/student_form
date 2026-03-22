package com.example.student;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etPhone;
    private TextView tvDOB, tvTime;
    private RadioGroup rgGender;
    private Button btnSubmit;

    private int dobYear, dobMonth, dobDay;
    private int selectedHour, selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        etFullName = findViewById(R.id.etFullName);
        etEmail    = findViewById(R.id.etEmail);
        etPhone    = findViewById(R.id.etPhone);
        tvDOB      = findViewById(R.id.tvDOB);
        tvTime     = findViewById(R.id.tvTime);
        rgGender   = findViewById(R.id.rgGender);
        btnSubmit  = findViewById(R.id.btnSubmit);

        // Date of Birth Picker
        tvDOB.setOnClickListener(v -> showDatePicker());

        // Time Picker
        tvTime.setOnClickListener(v -> showTimePicker());

        // Submit button
        btnSubmit.setOnClickListener(v -> validateAndSubmit());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year  = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day   = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    dobYear  = year1;
                    dobMonth = month1 + 1; // month is 0-based
                    dobDay   = dayOfMonth;
                    String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    tvDOB.setText("Date of Birth: " + date);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour   = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute1) -> {
                    selectedHour   = hourOfDay;
                    selectedMinute = minute1;
                    String time = String.format("%02d:%02d", hourOfDay, minute1);
                    tvTime.setText("Preferred Time: " + time);
                },
                hour, minute, true // true = 24-hour format
        );

        timePickerDialog.show();
    }

    private void validateAndSubmit() {
        String name  = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        int selectedGenderId = rgGender.getCheckedRadioButtonId();
        String gender = "";
        if (selectedGenderId != -1) {
            RadioButton rb = findViewById(selectedGenderId);
            gender = rb.getText().toString();
        }

        if (name.isEmpty()) {
            etFullName.setError("Name is required");
            return;
        }
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            return;
        }
        if (phone.isEmpty()) {
            etPhone.setError("Phone is required");
            return;
        }
        if (tvDOB.getText().toString().contains("Tap")) {
            Toast.makeText(this, "Please select Date of Birth", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tvTime.getText().toString().contains("Tap")) {
            Toast.makeText(this, "Please select Preferred Time", Toast.LENGTH_SHORT).show();
            return;
        }
        if (gender.isEmpty()) {
            Toast.makeText(this, "Please select Gender", Toast.LENGTH_SHORT).show();
            return;
        }

        // All good → You can save to database / SharedPreferences / send to server here
        String message = "Registration Successful!\n\n" +
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Phone: " + phone + "\n" +
                "DOB: " + dobDay + "/" + dobMonth + "/" + dobYear + "\n" +
                "Time: " + String.format("%02d:%02d", selectedHour, selectedMinute) + "\n" +
                "Gender: " + gender;

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();



        // Optional: clear form
        // etFullName.setText("");
        // ... clear others
    }
}