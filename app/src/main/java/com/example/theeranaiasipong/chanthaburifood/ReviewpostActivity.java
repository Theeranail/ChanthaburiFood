package com.example.theeranaiasipong.chanthaburifood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.theeranaiasipong.chanthaburifood.model.Config;
import com.example.theeranaiasipong.chanthaburifood.model.JsonHttp;
import com.example.theeranaiasipong.chanthaburifood.model.ReviewFood;
import com.example.theeranaiasipong.chanthaburifood.model.SharedPreferencesCheck;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReviewpostActivity extends AppCompatActivity implements View.OnClickListener {

    public ImageView img_shoodfile;
    public static final int REQUEST_GALLERY = 1;
    public Bitmap bitmap;
    public Uri uri;
    public ArrayList<Uri> arrUri;
    public Button btn_postreview;
    public EditText editReviewpostname, editReviewpostdetail, editReviewpostmenu, editReviewpostprice;
    public int id_food;
    public SharedPreferencesCheck sp;
    public int n = 0;
    public GridView viewImgpost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewpost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        id_food = bundle.getInt("id_food");

        img_shoodfile = (ImageView) findViewById(R.id.img_shoodfile);
        btn_postreview = (Button) findViewById(R.id.btn_postreview);
        editReviewpostname = (EditText) findViewById(R.id.editReviewpostname);
        editReviewpostdetail = (EditText) findViewById(R.id.editReviewpostdetail);
        editReviewpostmenu = (EditText) findViewById(R.id.editReviewpostmenu);
        editReviewpostprice = (EditText) findViewById(R.id.editReviewpostprice);
        viewImgpost = (GridView) findViewById(R.id.viewImgpost);

        img_shoodfile.setOnClickListener(this);
        btn_postreview.setOnClickListener(this);

        arrUri = new ArrayList<Uri>();

    }


    public void Selectphotos() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);


    }

    public void UdateShowViewimg(ArrayList<Uri> uriList) {

        ReviewFood.CreviewShowviewimg reShowviewimg = new ReviewFood.CreviewShowviewimg(ReviewpostActivity.this, uriList);
        viewImgpost.setAdapter(reShowviewimg);
        reShowviewimg.notifyDataSetChanged();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            uri = data.getData();

            arrUri.add(uri);
            try {

                File file = new File(String.valueOf(uri));
                String fileName = file.getName();
                String pat = file.getPath();

                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                img_shoodfile.setImageBitmap(resized);

                UdateShowViewimg(arrUri);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void PostReview() {


        new PostReview(ReviewpostActivity.this,
                editReviewpostname.getText().toString(),
                editReviewpostdetail.getText().toString(),
                editReviewpostmenu.getText().toString(),
                editReviewpostprice.getText().toString(),
                String.valueOf(id_food),
                sp.id_user)
                .execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            SharedPreferencesCheck.Logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_shoodfile:
                Selectphotos();
                break;
            case R.id.btn_postreview:
                Log.e("arrUri1", String.valueOf(arrUri.size()));
                PostReview();
                break;
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public class PostReview extends AsyncTask<Object, Integer, List<ReviewFood.ResultPost>> {

        public Context context;
        public Uri uri;
        public final MediaType MEDIA_TYPE_IMG = MediaType.parse("image/jpg");
        public String titilename, detail, menupost, pricepost, id_food, id_member;
        public ProgressDialog progress;
        public String result;


        public PostReview(Context context, String titilename, String detail, String menupost, String pricepost, String id_food, String id_member) {
            this.context = context;
            //   this.uri = uri;
            this.titilename = titilename;
            this.detail = detail;
            this.menupost = menupost;
            this.pricepost = pricepost;
            this.id_food = id_food;
            this.id_member = id_member;
        }

        @Override
        protected void onPreExecute() {

            progress = new ProgressDialog(context);
            progress.setMessage("Loading please wait...");
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected List<ReviewFood.ResultPost> doInBackground(Object... params) {

            try {

                JsonHttp jsonHttp = new JsonHttp();
                OkHttpClient okHttpClient = new OkHttpClient();
                Gson gson = new Gson();

//                Log.e("getRealPathFromURI", getRealPathFromURI(uri));

                RequestBody form = new FormBody.Builder()
                        .add("function", "postReview")
                        .add("title_name", titilename)
                        .add("detail", detail)
                        .add("recommended_menu", menupost)
                        .add("price", pricepost)
                        .add("id_food", id_food)
                        .add("id_member", id_member)
                        .build();


                String result = jsonHttp.SyncTaskOkHttp(form, Config.url_review);
                Log.e("resultp1", result);
                Type listType = new TypeToken<List<ReviewFood.ResultPost>>() {
                }.getType();
                List<ReviewFood.ResultPost> posts = gson.fromJson(result, listType);
                if (posts.get(0).getRs().equals("")) {

                    return posts;

                } else {

                    if (arrUri.size() >= 1) {

                        List<ReviewFood.ResultPost> postsf = null;

                        for (int i = 0; i < arrUri.size(); i++) {

                            File file = new File(getRealPathFromURI(arrUri.get(i)));
                            String filename = file.getName();
                            Log.e("filenamearr", filename);
                            RequestBody requestBody = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("filUpload", filename,
                                            RequestBody.create(MEDIA_TYPE_IMG, new File(getRealPathFromURI(arrUri.get(i)))))
                                    .addFormDataPart("function", "uploadimg")
                                    .addFormDataPart("id_review", posts.get(0).getRs().toString())
                                    .build();

                            Request request = new Request.Builder()
                                    .url(Config.url_review)
                                    .post(requestBody)
                                    .build();
                            Response response = null;
                            try {
                                response = okHttpClient.newCall(request).execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {

                                String resultf = response.body().string();
                                Log.e("resultPost2", resultf);

                                Type listTypef = new TypeToken<List<ReviewFood.ResultPost>>() {
                                }.getType();
                                postsf = gson.fromJson(resultf, listTypef);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return postsf;
                    } else {
                        return posts;
                    }
                }

            } catch (RuntimeException re) {
                String msg = (re.getMessage() == null) ? "Load failed!" : re.getMessage();
                //   Log.e("RuntimeException", re.getMessage());
                return null;

            } catch (Exception ex) {

                Log.e("Exception", ex.getMessage());
                return null;

            }

        }


        @Override
        protected void onPostExecute(List<ReviewFood.ResultPost> msg) {
            super.onPostExecute(msg);

            if (progress.isShowing()) {
                progress.dismiss();
            }
            if (msg != null) {
                if (msg.get(0).getRs().toString().equals("")) {
                    Toast.makeText(context, "เกิดข้อผิดพลาด ไม่สามาร โพสรีวิวได้", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, msg.get(0).getMsg(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, "No Internet Connect", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void Resizeimg(){

    }
}
