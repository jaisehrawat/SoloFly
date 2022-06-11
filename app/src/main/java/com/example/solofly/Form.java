package com.example.solofly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Form extends AppCompatActivity {

    ImageView profilePhoto;
    Bitmap bitmap;
    Button dateOfBirth, uploadProfileBtn, save;
    Uri imageUri;
    DatePicker val;
    String name, email, date_of_birth, phone;
    TextInputEditText nameEdittext, emailEdittext;
    DatabaseReference reference;
    StorageReference storageReference;
    ProgressDialog pd;
    boolean isValid = false;
    String nullDate = "01/01/1901";
    String temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        nameEdittext = findViewById(R.id.nameEdittext);
        emailEdittext = findViewById(R.id.emailEdittext);
        dateOfBirth = findViewById(R.id.dateOfBirth);

        save = findViewById(R.id.save);

        phone = getIntent().getStringExtra("phoneNumber").toString();


        uploadProfileBtn = findViewById(R.id.uploadProfileBtn);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        profilePhoto = findViewById(R.id.profilePhoto);

        pd = new ProgressDialog(this);

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date").setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();
                datePicker.show(getSupportFragmentManager(), "MaterialDatePicker");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        date_of_birth = datePicker.getHeaderText();
                        dateOfBirth.setText(date_of_birth);
                    }
                });
            }
        });


        uploadProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameEdittext.getText().toString();
                email = emailEdittext.getText().toString();

                temp = dateOfBirth.getText().toString();

                isValid = validation();

                if (isValid) {
                    uploadProfile(imageUri);
                    Intent intent = new Intent(Form.this, MainActivity.class);
                    startActivity(intent);

                    SharedPreferences preferences = getSharedPreferences("logInData", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = preferences.edit();
                    myEdit.putString("phone", String.valueOf(phone));
                    myEdit.putBoolean("isLoggedIn", true);
                    myEdit.apply();

                    finish();
                }
            }
        });

    }

    private boolean validation() {
        if (name.isEmpty()) {
            nameEdittext.setError("Enter name");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEdittext.setError("Invalid Email Address");
            return false;
        }

        if (temp.equals(nullDate)) {
            dateOfBirth.setError("Enter Date of Birth");
            return false;
        }

        if (imageUri == null) {
            uploadProfileBtn.setError("Choose a Photo");
            return false;
        }

        return true;
    }

    private void uploadProfile(Uri uri) {
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        reference = reference.child("User");

                        Calendar calForDate = Calendar.getInstance();
                        SimpleDateFormat currentDate = new SimpleDateFormat(("dd-MM-yy"));
                        String date = currentDate.format(calForDate.getTime());

                        Calendar calForTime = Calendar.getInstance();
                        SimpleDateFormat currentTime = new SimpleDateFormat("hh-mm-a");
                        String time = currentTime.format(calForTime.getTime());

                        ProfileData profileData = new ProfileData(name, email, phone, date_of_birth, uri.toString(), time);

                        reference.child(phone).setValue(profileData).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                pd.dismiss();
                                Toast.makeText(Form.this, "Account Created ", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(Form.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Form.this, "Photo upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            profilePhoto.setImageURI(imageUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profilePhoto.setVisibility(View.VISIBLE);
                profilePhoto.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}