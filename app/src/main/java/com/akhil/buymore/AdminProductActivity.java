package com.akhil.buymore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminProductActivity extends AppCompatActivity {

    private String cateogryName, PName, Price, Description,saveCurrentDate, saveCurrentTime, ProductRandomKey, DownloadImageURL;
    private ImageView InputProductImage;
    private Button AddNewProductButton;
    private EditText InputProductName, InputProductDescription, InputProductPrice;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductRef;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);

        cateogryName = getIntent().getExtras().get("category").toString();
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef= FirebaseDatabase.getInstance().getReference().child("Products");

        AddNewProductButton = findViewById(R.id.add_new_product);
        InputProductImage = findViewById(R.id.select_product_image);
        InputProductName = findViewById(R.id.product_name);
        InputProductDescription = findViewById(R.id.product_description);
        InputProductPrice = findViewById(R.id.product_price);
        loadingbar = new ProgressDialog(this);

        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });


        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              ValidateProductData();
            }
        });


        //Toast.makeText(this, cateogryName, Toast.LENGTH_SHORT).show();

    }

    private void OpenGallery(){

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent , GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryPick && resultCode == RESULT_OK && data!= null){
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);
        }
    }

    private void ValidateProductData(){
        Description = InputProductDescription.getText().toString();
        Price = InputProductPrice.getText().toString();
        PName = InputProductName.getText().toString();

        if(ImageUri == null){
            Toast.makeText(this, "Product Image is Compulsory", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Entering Description is Compulsory", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(Price)){
            Toast.makeText(this, "Entering Price is Compulsory", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(PName)){
            Toast.makeText(this, "Entering Product Name is Compulsory", Toast.LENGTH_SHORT).show();
        }

        else{
            StoreProductInfo();
        }
    }

    private void StoreProductInfo(){

        loadingbar.setTitle("Adding New Product");
        loadingbar.setMessage("Please Wait while we are adding the new Product");
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        ProductRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = ProductImageRef.child(ImageUri.getLastPathSegment() + ProductRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminProductActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
                loadingbar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminProductActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();

                        }

                        DownloadImageURL = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){

                            DownloadImageURL = task.getResult().toString();

                            Toast.makeText(AdminProductActivity.this, "got the Product image url successful.....", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private  void SaveProductInfoToDatabase(){
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", ProductRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", DownloadImageURL);
        productMap.put("category", cateogryName);
        productMap.put("price", Price);
        productMap.put("pname", PName);

        ProductRef.child(ProductRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(AdminProductActivity.this,AdminCategoryActivity.class);
                            startActivity(intent);

                            loadingbar.dismiss();
                            Toast.makeText(AdminProductActivity.this, "Product is added Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            loadingbar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminProductActivity.this, "Error: "+ message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }



}
