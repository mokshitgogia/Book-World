package com.gtappdevelopers.instagram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookDetails extends AppCompatActivity {
    //creating variables for strings,text view, image views and button.
    String title, subtitle, publisher, publishedDate, printType, description, thumbnail, previewLink, infoLink, buyLink;
    int pageCount;
    private ArrayList<String> authors;
    private ImageView bookIV;

    TextView titleTV, subtitleTV, publisherTV, descTV, pageTV, publishDateTV, printTypeTV;
    Button previewBtn, buyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        //initializing our views..
        titleTV = findViewById(R.id.idTVTitle);
        subtitleTV = findViewById(R.id.idTVSubTitle);
        publisherTV = findViewById(R.id.idTVpublisher);
        descTV = findViewById(R.id.idTVDescription);
        printTypeTV = findViewById(R.id.idTVPrintType);
        publishDateTV = findViewById(R.id.idTVPublishDate);
        pageTV = findViewById(R.id.idTVNoOfPages);
        previewBtn = findViewById(R.id.idBtnPreview);
        buyBtn = findViewById(R.id.idBtnBuy);
        bookIV = findViewById(R.id.idIVbook);

        //getting the data which we have passesd from our adapter class.
        title = getIntent().getStringExtra("title");
        subtitle = getIntent().getStringExtra("subtitle");
        publisher = getIntent().getStringExtra("publisher");
        description = getIntent().getStringExtra("description");
        printType = getIntent().getStringExtra("printType");
        publishedDate = getIntent().getStringExtra("publishedDate");
        pageCount = getIntent().getIntExtra("pageCount", 0);
        thumbnail = getIntent().getStringExtra("thumbnail");
        previewLink = getIntent().getStringExtra("previewLink");
        infoLink = getIntent().getStringExtra("infoLink");
        buyLink = getIntent().getStringExtra("buyLink");

        //after getting the data we are setting that data to our text views and image view.
        titleTV.setText(title);
        subtitleTV.setText(subtitle);
        publisherTV.setText(publisher);
        publishDateTV.setText("Published On : " + publishedDate);
        descTV.setText(description);
        pageTV.setText("No Of Pages : " + pageCount);
        printTypeTV.setText("Print Type " + printType);
        String newString = new String();

        for (int i = 0; i < thumbnail.length(); i++) {
            newString += thumbnail.charAt(i);

            if (i == 3) {

                // Insert the string to be inserted
                // into the new string
                newString += "s";
            }
        }

        Glide.with(this).load(newString).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // log exception
                        Log.e("TAG", "Error loading image", e);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(bookIV);

        //val uri = thumbnail.toUri().buildUpon().scheme("https").build()
        //Glide.with(this).load(uri).into(image)
        //Picasso.get().load(thumbnail).into(bookIV);

        //adding on click listener for our preview button.
        previewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previewLink.isEmpty()) {
                    //below toast message is displayed when preview link is not present.
                    Toast.makeText(BookDetails.this, "No preview Link present", Toast.LENGTH_SHORT).show();
                    return;
                }
                //if the link is present we are opening that link via an intent.
                Uri uri = Uri.parse(previewLink);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        //initializing on click listener for buy button.
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyLink.isEmpty()) {
                    //below toast message is displaying when buy link is empty.
                    Toast.makeText(BookDetails.this, "No buy page present for this book", Toast.LENGTH_SHORT).show();
                    return;
                }
                //if the link is present we are opening the link via an intent.
                Uri uri = Uri.parse(buyLink);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });
    }
}