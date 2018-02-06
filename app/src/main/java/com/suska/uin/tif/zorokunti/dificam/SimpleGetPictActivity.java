package com.suska.uin.tif.zorokunti.dificam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class SimpleGetPictActivity extends AppCompatActivity {

    public Button btn ,next;
    public ImageView imageview;
    public EditText width, height , url;
    public static final String IMAGE_DIRECTORY = "/dificam";
    public int GALLERY = 1, CAMERA = 2, widthAwal, heightAwal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_activity_get_pict);



        btn = (Button) findViewById(R.id.btn);
        next = (Button) findViewById(R.id.btn_LPel1);
        imageview = (ImageView) findViewById(R.id.iv);
        width = (EditText) findViewById(R.id.et_width);
        height = (EditText) findViewById(R.id.et_height);
        url = (EditText)findViewById(R.id.et_urlPict);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String urlPicture = url.getText().toString();
                    Intent next = new Intent(SimpleGetPictActivity.this , SimpleConvertPictureActivity.class);
                    next.putExtra("url" ,urlPicture );
                    startActivity(next);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplication(), "Mohon Pilih / Ambil Gambar Dahulu",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(SimpleGetPictActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageview.setImageBitmap(bitmap);
                    widthAwal = bitmap.getWidth();
                    heightAwal = bitmap.getHeight();
                    width.setText("" + widthAwal);
                    height.setText("" + heightAwal);
                    width.setEnabled(false);
                    height.setEnabled(false);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SimpleGetPictActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(SimpleGetPictActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            widthAwal = thumbnail.getWidth();
            heightAwal = thumbnail.getHeight();
            width.setText("" + widthAwal);
            height.setText("" + heightAwal);
            width.setEnabled(false);
            height.setEnabled(false);

        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            String namePict = "dificam" + Calendar.getInstance().getTimeInMillis() + ".jpg";
            File f = new File(wallpaperDirectory, namePict);
/*
            ImageData x = new ImageData();
            x.setImage(namePict);
       // setImage = namePict;
            String imagePath=""+IMAGE_DIRECTORY+"/"+namePict;
            Bitmap bmm = BitmapFactory.decodeFile(imagePath);
            imageview.setImageBitmap(bmm);*/

            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            String name = f.getPath();
            url.setText(name);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());


            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }



}