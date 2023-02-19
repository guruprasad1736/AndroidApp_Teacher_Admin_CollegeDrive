package com.guruprasad.teacherattend;

import static com.guruprasad.teacherattend.Constants.error_toast;
import static com.guruprasad.teacherattend.Constants.success_toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.guruprasad.teacherattend.model.student_model;

public class Add_student extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
        EditText student_name , parent_no , student_no , student_email ;
        Spinner spinner ,year , division;
        Button button ;
        DatabaseReference databaseReference ;
        FirebaseDatabase database ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        student_name = findViewById(R.id.student_name);
        parent_no = findViewById(R.id.parent_no);
        student_no = findViewById(R.id.student_no);
        student_email = findViewById(R.id.student_email);
        spinner = findViewById(R.id.department);
        year = findViewById(R.id.year);
        division = findViewById(R.id.division);
        button = findViewById(R.id.submit_student);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Students");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.department_type , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.year , android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item);
        year.setAdapter(adapter2);
        year.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.division , android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item);
        division.setAdapter(adapter3);
        division.setOnItemSelectedListener(this);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = student_name.getText().toString();
                String parent_num = parent_no.getText().toString();
                String student_num = student_no.getText().toString();
                String stud_email = student_email.getText().toString();
                String department = spinner.getSelectedItem().toString();
                String Year = year.getSelectedItem().toString();
                String div = division.getSelectedItem().toString();



                ProgressDialog pd = new ProgressDialog(Add_student.this);
                pd.setTitle("Adding Student");
                pd.setMessage("Please Wait .....");
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                student_model student_model = new student_model(name,parent_num,student_num,department,Year,div,stud_email);
                databaseReference.child(department).child(Year).child(div).child(databaseReference.push().getKey()).setValue(student_model)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                success_toast(getApplicationContext(),"Student Added Successfully");
                                student_name.setText(null);
                                parent_no.setText(null);
                                student_no.setText(null);
                                student_email.setText(null);
                                pd.dismiss();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                error_toast(getApplicationContext(),"Failed to add student : "+e.getMessage());
                            }
                        });

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}