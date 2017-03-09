package com.example.theeranaiasipong.chanthaburifood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.theeranaiasipong.chanthaburifood.model.Config;
import com.example.theeranaiasipong.chanthaburifood.model.Food;
import com.example.theeranaiasipong.chanthaburifood.model.ImagesSlide;
import com.example.theeranaiasipong.chanthaburifood.model.JsonHttp;
import com.example.theeranaiasipong.chanthaburifood.model.MyLocation;
import com.example.theeranaiasipong.chanthaburifood.model.SharedPreferencesCheck;
import com.example.theeranaiasipong.chanthaburifood.model.getlisternerlocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity implements getlisternerlocation, View.OnClickListener {

    private int id_food;
    private String name_food;
    private String address;
    private String detail;
    private String lattitude;
    private String logtitude;
    private String img_food;
    private String tel;
    private String price;
    private String Timeservie;
    private String score;
    private double distace;
    private String linkcontac;
    private String orther;
    private String link;

    private ImageView imagefood, imgTel, imgVodeScore;
    private TextView textnamefood, textdistace, textddress, texttel, textlinkcontac, textdetail, texttime, textprice, textother;
    private RatingBar textRatingscore;
    public RelativeLayout content__detail_iconBottomVote, content__detail_iconBottomReview, content__contect_Detail,
            content__contect_Detail2, content__contect_Detail3, content_menu, content_slide;
    public LinearLayout container_menu;
    public GridView gridView;

    public MyLocation myLocation;
    public ProgressBar spinner;
    public List<Food.ListNenu> listNenu;
    public ListView lisview_dater;
    public List<Food.LisImg> lisImgs;
    public OkHttpClient okHttpClient;
    public SharedPreferencesCheck sp;
    public List<Food.Checkvote> checkvotes;
    public ProgressDialog progress;
    public ViewPager viewPager;
    public PagerAdapter pagerAdapter;
    public ImageButton closeSlide;
    public TextView title_textCountreview;
    public String countreview = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarman);
        setSupportActionBar(toolbar);

        myLocation = new MyLocation(DetailActivity.this);
        Bundle bundle = getIntent().getExtras();
        id_food = bundle.getInt("id_food");
        name_food = bundle.getString("name_food");
        address = bundle.getString("address");
        detail = bundle.getString("detail");
        lattitude = bundle.getString("lattitude");
        logtitude = bundle.getString("logtitude");
        img_food = bundle.getString("img_food");
        tel = bundle.getString("tel");
        price = bundle.getString("price");
        Timeservie = bundle.getString("Timeservie");
        score = bundle.getString("score");
        orther = bundle.getString("detail_orther");
        linkcontac = bundle.getString("linkcontact");
        link = bundle.getString("link");


        textnamefood = (TextView) findViewById(R.id.textViewnamefood);
        textddress = (TextView) findViewById(R.id.textViewaddress);
        textRatingscore = (RatingBar) findViewById(R.id.textViewscore);
        textdistace = (TextView) findViewById(R.id.textView_distace);
        texttel = (TextView) findViewById(R.id.textViewtel);
        textlinkcontac = (TextView) findViewById(R.id.textViewlinkcontac);
        textdetail = (TextView) findViewById(R.id.textViewdetail);
        texttime = (TextView) findViewById(R.id.textViewtime);
        textprice = (TextView) findViewById(R.id.textViewprice);
        textother = (TextView) findViewById(R.id.textViewother);
        imagefood = (ImageView) findViewById(R.id.imageView);
        content__detail_iconBottomVote = (RelativeLayout) findViewById(R.id.content__detail_iconBottomVote);
        content__detail_iconBottomReview = (RelativeLayout) findViewById(R.id.content__detail_iconBottomRightReview);
        content__contect_Detail = (RelativeLayout) findViewById(R.id.content__contect_Detail);
        content__contect_Detail2 = (RelativeLayout) findViewById(R.id.content__contect_Detail2);
        content__contect_Detail3 = (RelativeLayout) findViewById(R.id.content__contect_Detail3);
        content_menu = (RelativeLayout) findViewById(R.id.content_menu);
        imgTel = (ImageView) findViewById(R.id.imgTel);
        container_menu = (LinearLayout) findViewById(R.id.container_menu);
        gridView = (GridView) findViewById(R.id.img_foog);
        imgVodeScore = (ImageView) findViewById(R.id.imgVodeScore);
        content_slide = (RelativeLayout) findViewById(R.id.content_slide);
        viewPager = (ViewPager) findViewById(R.id.imgpager);
        closeSlide = (ImageButton) findViewById(R.id.closeSlide);
        title_textCountreview = (TextView) findViewById(R.id.title_textCountreview);

        content__contect_Detail.setOnClickListener(this);
        content__contect_Detail2.setOnClickListener(this);
        content__contect_Detail3.setOnClickListener(this);
        content__detail_iconBottomVote.setOnClickListener(this);
        content__detail_iconBottomReview.setOnClickListener(this);
        content_menu.setOnClickListener(this);
        closeSlide.setOnClickListener(this);


        setStringView();

        getImgFood();
        CheckVote();


    }

    @Override
    protected void onStart() {
        super.onStart();
        myLocation.StartLocation();
        getCountreview(id_food);
    }

    @Override
    protected void onStop() {
        super.onStop();
        myLocation.Stoplocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myLocation.StartLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myLocation.Stoplocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void setStringView() {

        textnamefood.setText(name_food);
        textddress.setText(address);

        if (Integer.valueOf(score) >= 49) {
            textRatingscore.setRating(1);
        } else if (Integer.valueOf(score) >= 50) {
            textRatingscore.setRating(2);
        } else if (Integer.valueOf(score) >= 100) {
            textRatingscore.setRating(3);
        } else if (Integer.valueOf(score) >= 150) {
            textRatingscore.setRating(4);
        } else if (Integer.valueOf(score) >= 200) {
            textRatingscore.setRating(5);
        }

        texttel.setText(tel);
        textdetail.setText(detail);
        texttime.setText(Timeservie);
        textprice.setText(price);
        textother.setText(orther);
        textlinkcontac.setText(subStrLimit(linkcontac, 22));


        Glide.with(DetailActivity.this)
                .load(img_food)
                .placeholder(R.drawable.ic_panorama_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(imagefood);


    }

    public void getCountreview(int id_food) {
        okHttpClient = new OkHttpClient();
        String id_foods = String.valueOf(id_food);
        RequestBody formbody = new FormBody.Builder()
                .add("function", "getCountReview")
                .add("id_food", id_foods)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(Config.url_getFood).post(formbody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("erroimg", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    try {
                        final String result = response.body().string();
                        DetailActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (result != "")
                                    title_textCountreview.setText(result);
                            }
                        });
                    } catch (IOException e) {
                        Log.e("erroimgonResponse", e.getMessage());
                    }
                } else {
                    Log.e("erroimgisSuccessful", " Not Success - code : " + response.code());
                }
            }
        });
    }

    @Override
    public void getLatliogLocation(Location location) {
        double distace = myLocation.CalculationDistance(location.getLatitude(), location.getLongitude(),
                Double.valueOf(lattitude).doubleValue(), Double.valueOf(logtitude).doubleValue());
        textdistace.setText(String.format("%.2f", distace) + " กม.");
    }

    public void CallhoneFood() {
        Intent call = new Intent(Intent.ACTION_CALL);
        call.setData(Uri.parse("tel:" + tel));
        try {
            startActivity(call);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
        }
    }

    public void LinkFood() {
        try {
            Intent linkapp = getPackageManager().getLaunchIntentForPackage(link);
            linkapp.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(linkapp);
        } catch (NullPointerException e) {
            Intent links = new Intent(Intent.ACTION_VIEW);
            links.setData(Uri.parse(link));
            startActivity(Intent.createChooser(links, "Open with"));
        }
    }

    public String subStrLimit(String str, int limit) {
        String msg = "";
        msg = new String(str);
        if (msg.length() > limit)
            msg = msg.substring(0, limit - 1) + "...";
        return msg;
    }

    public void Showmenu() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);


        View views = inflater.inflate(R.layout.lisview_adapter, (ViewGroup) findViewById(R.id.container_lisview));
        lisview_dater = (ListView) views.findViewById(R.id.lisview_dater);
        lisview_dater.setAdapter(new setViewMenu(this, listNenu));
        dialog.setTitle("รายการอาหาร");
        dialog.setView(views);
        dialog.setPositiveButton("ปิด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();


    }

    public void getImgFood() {

        okHttpClient = new OkHttpClient();
        String id_foods = String.valueOf(id_food);
        RequestBody formbody = new FormBody.Builder()
                .add("function", "GetImages")
                .add("id_food", id_foods)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(Config.url_getFood).post(formbody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("erroimg", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                Gson gson = new Gson();
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.e("result", result);
                        Type listType = new TypeToken<List<Food.LisImg>>() {
                        }.getType();
                        List<Food.LisImg> posts = gson.fromJson(result, listType);
                        lisImgs = posts;
//                        getCountreview(id_food);
//                        getCountreview(id_food);
                        if (lisImgs != null) {
                            UpdateImgView(lisImgs);

                        }
                    } catch (IOException e) {
                        Log.e("erroimgonResponse", e.getMessage());
                    }
                } else {
                    Log.e("erroimgisSuccessful", " Not Success - code : " + response.code());
                }

            }
        });


    }

    public void UpdateImgView(final List<Food.LisImg> lisImgs) {
        DetailActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (countreview != "")
                    title_textCountreview.setText(countreview);
                gridView.setAdapter(new SetViewimg(DetailActivity.this, lisImgs));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        content_slide.setVisibility(View.VISIBLE);
                        viewPager.setVisibility(View.VISIBLE);
                        pagerAdapter = new ImagesSlide(DetailActivity.this, lisImgs);
                        viewPager.setAdapter(pagerAdapter);

                    }
                });
            }
        });
    }

    public void Vote() {

        progress = new ProgressDialog(DetailActivity.this);
        progress.setTitle("");
        progress.setMessage("กำลังบันทึกข้อมูล...");
        progress.setCancelable(false);
        progress.show();

        okHttpClient = new OkHttpClient();

        RequestBody form = new FormBody.Builder()
                .add("function", "voteFood")
                .add("id_food", String.valueOf(id_food))
                .add("id_user", sp.id_user)
                .build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(Config.url_getFood).post(form).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("erroimg", e.getMessage());
                progress.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) {
                progress.dismiss();
                Gson gson = new Gson();
                if (response.isSuccessful()) {
                    try {
                        String resulsts = response.body().string();
                        Log.e("resultSyncTaskOkHttp", resulsts);

                        Type listType = new TypeToken<List<Food.Checkvote>>() {
                        }.getType();
                        List<Food.Checkvote> posts = gson.fromJson(resulsts, listType);
                        checkvotes = posts;
                        if (checkvotes != null) {
                            setVote(checkvotes, "");
                        }
                    } catch (Exception e) {
                        Log.e("erroimgonResponse", e.getMessage());
                    }

                } else {
                    Log.e("erroimgisSuccessful", " Not Success - code : " + response.code());
                }
            }
        });

    }

    public void CheckVote() {

        okHttpClient = new OkHttpClient();

        RequestBody form = new FormBody.Builder()
                .add("function", "CheckvoteFood")
                .add("id_food", String.valueOf(id_food))
                .add("id_user", sp.id_user)
                .build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(Config.url_getFood).post(form).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("erroimg", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                Gson gson = new Gson();
                if (response.isSuccessful()) {
                    try {
                        String resulsts = response.body().string();
                        Log.e("resultSyncTaskOkHttp", resulsts);
                        Type listType = new TypeToken<List<Food.Checkvote>>() {
                        }.getType();
                        List<Food.Checkvote> posts = gson.fromJson(resulsts, listType);
                        checkvotes = posts;
                        if (checkvotes != null) {
                            setVote(checkvotes, "chcek");
                        }
                    } catch (Exception e) {
                        Log.e("erroimgonResponse", e.getMessage());
                    }

                } else {
                    Log.e("erroimgisSuccessful", " Not Success - code : " + response.code());
                }
            }
        });

    }

    public void setVote(final List<Food.Checkvote> list, String check) {

        if (check.equals("")) {
            DetailActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (list.get(0).getSt().equals("0")) {
                        imgVodeScore.setImageResource(R.drawable.ic_star_black_24dp);
                        Toast.makeText(DetailActivity.this, list.get(0).getMsg(), Toast.LENGTH_LONG).show();
                    } else if (list.get(0).getSt().equals("1")) {
                        imgVodeScore.setImageResource(R.drawable.ic_star_black_24dp);
                        Toast.makeText(DetailActivity.this, list.get(0).getMsg(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DetailActivity.this, "เกิดข้อผิดพลาด ไม่สามารถ vote ได้", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            DetailActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (list.get(0).getSt().equals("1")) {
                        imgVodeScore.setImageResource(R.drawable.ic_star_black_24dp);
                        Log.e("checkvotes", list.get(0).getSt());
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.content__detail_iconBottomVote:
                Vote();
                break;
            case R.id.content__detail_iconBottomRightReview:
                Intent intent = new Intent(DetailActivity.this, ReviewActivity.class);
                intent.putExtra("id_food", id_food);
                startActivity(intent);
                break;
            case R.id.content__contect_Detail:
                CallhoneFood();
                break;
            case R.id.content__contect_Detail2:
                LinkFood();
                break;
            case R.id.content__contect_Detail3:
                Intent intentmap = new Intent(DetailActivity.this, MapActivity.class);
                intentmap.putExtra("latfood", Double.valueOf(lattitude));
                intentmap.putExtra("lngfood", Double.valueOf(logtitude));
                startActivity(intentmap);
                break;
            case R.id.content_menu:
                new Loadmenu(DetailActivity.this).execute();
                break;
            case R.id.closeSlide:
                content_slide.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
                break;
        }
    }

    public class Loadmenu extends AsyncTask<Object, Integer, List<Food.ListNenu>> {


        Context context;
        ProgressDialog progress;

        public Loadmenu(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            progress = new ProgressDialog(context);
            progress.setTitle("Please wait");
            progress.setMessage("Loading please wait...");
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected List<Food.ListNenu> doInBackground(Object... params) {

            JsonHttp jsonHttp = new JsonHttp();
            Gson gson = new Gson();
            String Listmenu = jsonHttp.LoadListMenu(id_food);
            Log.e("ressult", Listmenu);
            Type listType = new TypeToken<List<Food.ListNenu>>() {
            }.getType();
            List<Food.ListNenu> posts = gson.fromJson(Listmenu, listType);

            return posts;
        }

        @Override
        protected void onPostExecute(List<Food.ListNenu> ob) {
            super.onPostExecute(ob);
            if (progress.isShowing()) {
                progress.dismiss();
            }

            if (ob != null) {
                listNenu = ob;
                if (listNenu.get(0).getId_menu().equals("")) {
                    Toast.makeText(DetailActivity.this, "ไม่มีข้อมูลเมนู", Toast.LENGTH_LONG).show();
                } else {
                    Showmenu();
                }
            } else {
                Toast.makeText(DetailActivity.this, "ไม่มีข้อมูลเมนู", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class setViewMenu extends BaseAdapter {

        Context context;
        private List<Food.ListNenu> list;
        private LayoutInflater inflater;
        int i = 0;

        public setViewMenu(Context context, List<Food.ListNenu> objects) {
            this.context = context;
            this.list = objects;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.content_dialog_menu, null);
            }

            ImageView imageView = (ImageView) convertView.findViewById(R.id.img_menu);
            TextView textViewname = (TextView) convertView.findViewById(R.id.textView_dia_menuName);
            textViewname.setText(" " + list.get(position).getListmenu());

            TextView textViewPrice = (TextView) convertView.findViewById(R.id.textView_dia_menuPrice);
            textViewPrice.setText(String.valueOf(list.get(position).getPrice()).toString() + " บาท");

            Glide.with(context)
                    .load(list.get(position).getImg_menu())
                    .placeholder(R.drawable.ic_panorama_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .override(300, 200)
                    .into(imageView);

            return convertView;
        }
    }

    public class SetViewimg extends BaseAdapter {

        Context context;
        List<Food.LisImg> list;
        private LayoutInflater inflater;

        public SetViewimg(Context context, List<Food.LisImg> lisImgs) {
            this.context = context;
            this.list = lisImgs;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.content_img_view, null);
            }

            ImageView imageView = (ImageView) convertView.findViewById(R.id.img_view);

            Glide.with(context)
                    .load(list.get(position).getImg_path())
                    .placeholder(R.drawable.ic_panorama_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .override(300, 200)
                    .into(imageView);

            return convertView;
        }
    }
}
