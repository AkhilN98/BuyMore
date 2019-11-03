package com.akhil.buymore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView tShirts, sportsTShirts, femaleTop , sweaters;
    private ImageView glasses, purses , hats , shoes ;
    private ImageView headphones, laptops , watches , phones ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        tShirts = findViewById(R.id.tShirts_Img);
        sportsTShirts = findViewById(R.id.sports_tShirts_Img);
        femaleTop = findViewById(R.id.female_top);
        sweaters = findViewById(R.id.sweater_img);
        glasses = findViewById(R.id.glasses);
        purses = findViewById(R.id.purses_img);
        hats = findViewById(R.id.hats_img);
        shoes = findViewById(R.id.shoes_img);
        headphones = findViewById(R.id.headphones_img);
        laptops = findViewById(R.id.laptops_img);
        watches = findViewById(R.id.watch_img);
        phones = findViewById(R.id.mobile_img);


        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminProductActivity.class);
                intent.putExtra("category","tShirts");
                startActivity(intent);
            }
        });

        sportsTShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminProductActivity.class);
                intent.putExtra("category","sportsTShirts");
                startActivity(intent);
            }
        });

        femaleTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminProductActivity.class);
                intent.putExtra("category","femaleTop");
                startActivity(intent);
            }
        });

        sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminProductActivity.class);
                intent.putExtra("category","sweaters");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminProductActivity.class);
                intent.putExtra("category","glasses");
                startActivity(intent);
            }
        });

        purses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminProductActivity.class);
                intent.putExtra("category","purses");
                startActivity(intent);
            }
        });

        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminProductActivity.class);
                intent.putExtra("category","hats");
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminProductActivity.class);
                intent.putExtra("category","Shoes");
                startActivity(intent);
            }
        });

        headphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminProductActivity.class);
                intent.putExtra("category","headphones");
                startActivity(intent);
            }
        });

        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminProductActivity.class);
                intent.putExtra("category","tShirts");
                startActivity(intent);
            }
        });

        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminProductActivity.class);
                intent.putExtra("category","laptops");
                startActivity(intent);
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminProductActivity.class);
                intent.putExtra("category","watches");
                startActivity(intent);
            }
        });

        phones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminProductActivity.class);
                intent.putExtra("category","phones");
                startActivity(intent);
            }
        });
    }
}
