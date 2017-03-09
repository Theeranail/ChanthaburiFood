package com.example.theeranaiasipong.chanthaburifood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.theeranaiasipong.chanthaburifood.model.CheckAccessInternet;
import com.example.theeranaiasipong.chanthaburifood.model.Config;
import com.example.theeranaiasipong.chanthaburifood.model.Food;
import com.example.theeranaiasipong.chanthaburifood.model.ImagesSlide;
import com.example.theeranaiasipong.chanthaburifood.model.JsonHttp;
import com.example.theeranaiasipong.chanthaburifood.model.ReviewFood;
import com.example.theeranaiasipong.chanthaburifood.model.SharedPreferencesCheck;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public int id_food;
    public List<ReviewFood.ListReviewMyfood> listReview;
    public ListView lisviewReview;
    private CheckAccessInternet cAn;
    public TextView textNodata;
    public FloatingActionButton fab;

    public SwipeRefreshLayout swipeRefreshLayout;
    public ArrayList<String> listImg;
    public TextView title_textCountreview;
    public String countreview = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        id_food = bundle.getInt("id_food");

        lisviewReview = (ListView) findViewById(R.id.lisviewReview);
        fab = (FloatingActionButton) findViewById(R.id.btn_reviewpost);
        title_textCountreview = (TextView) findViewById(R.id.title_textCountreview);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);
        fab.setOnClickListener(this);


        cAn = new CheckAccessInternet(ReviewActivity.this);
        if (cAn.isConnectNet()) {
            new Loadfood(ReviewActivity.this).execute();
        } else {
            Toast.makeText(this, "No Internet Connected", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        if (cAn.isConnectNet()) {
                                            RefreshLoadReview();
                                        } else {
                                            Toast.makeText(ReviewActivity.this, "No Internet Connected", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
        );


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reviewpost:
                Intent intent = new Intent(ReviewActivity.this, ReviewpostActivity.class);
                intent.putExtra("id_food", id_food);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh() {
        if (cAn.isConnectNet()) {
            RefreshLoadReview();
        } else {
            Toast.makeText(this, "No Internet Connected", Toast.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void RefreshLoadReview() {

        swipeRefreshLayout.setRefreshing(true);
        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formbody = new FormBody.Builder()
                    .add("function", "LoadReviewMyfood")
                    .add("id_food", String.valueOf(id_food))
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(Config.url_getFood).post(formbody).build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    try {
                        Log.e("erroimg", e.getMessage());
                        DissmisRefreshLayout();
                    } catch (RuntimeException es) {
                        DissmisRefreshLayout();
                    }

                }

                @Override
                public void onResponse(Call call, Response response) {
                    Gson gson = new Gson();
                    if (response.isSuccessful()) {
                        try {
                            String result = response.body().string();
                            Log.e("result", result);
                            Type listType = new TypeToken<List<ReviewFood.ListReviewMyfood>>() {
                            }.getType();
                            List<ReviewFood.ListReviewMyfood> posts = gson.fromJson(result, listType);
                            countreview = getCountreview(id_food);

                            listReview = posts;
                            if (listReview != null) {
                                UpdateView(listReview);
                            }
                        } catch (IOException e) {
                            DissmisRefreshLayout();
                            Log.e("erroimgonResponse", e.getMessage());
                        }
                    } else {
                        DissmisRefreshLayout();
                        Log.e("erroimgisSuccessful", " Not Success - code : " + response.code());
                    }

                }
            });

        } catch (RuntimeException e) {
            DissmisRefreshLayout();

        }

    }

    public String getCountreview(int id_food) {
        String review_count = "";

        JsonHttp jsonHttp = new JsonHttp();
        review_count = jsonHttp.Loadcountreview(id_food);
        Log.e("review_count", review_count);

        return review_count;
    }

    public void UpdateView(final List<ReviewFood.ListReviewMyfood> list) {

        ReviewActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (countreview != "")
                    title_textCountreview.setText(countreview);
                if (list != null) {
                    if (listReview.get(0).getId_reviewpost().equals("")) {
                        Toast.makeText(ReviewActivity.this, "ไม่มีข้อมูล", Toast.LENGTH_LONG).show();
                        swipeRefreshLayout.setRefreshing(false);
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        CreateViewReview createView = new CreateViewReview(ReviewActivity.this, list);
                        lisviewReview.setAdapter(createView);

                        createView.notifyDataSetChanged();
                    }


                } else {
                    Toast.makeText(ReviewActivity.this, "ไม่มีข้อมูล", Toast.LENGTH_LONG).show();
                }

            }

        });

    }

    public void DissmisRefreshLayout() {
        ReviewActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ReviewActivity.this, "No Internet Connect", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }

        });
    }

    public class Loadfood extends AsyncTask<Object, Integer, List<ReviewFood.ListReviewMyfood>> {

        ProgressDialog progress;
        Context context;

        public Loadfood(Context context) {
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
        protected List<ReviewFood.ListReviewMyfood> doInBackground(Object... params) {

            try {

                JsonHttp jsonHttp = new JsonHttp();
                Gson gson = new Gson();
                String ressult = jsonHttp.LoadReviewMyfood(id_food);
                Log.e("ressult", ressult);
                Type listType = new TypeToken<List<ReviewFood.ListReviewMyfood>>() {
                }.getType();
                List<ReviewFood.ListReviewMyfood> posts = gson.fromJson(ressult, listType);


                return posts;

            } catch (RuntimeException e) {
                Log.e("RuntimeException", e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<ReviewFood.ListReviewMyfood> ob) {
            super.onPostExecute(ob);

            if (progress.isShowing()) {
                progress.dismiss();
            }
            if (ob != null) {

                listReview = ob;
                if (listReview.get(0).getId_reviewpost().equals("")) {
                    Toast.makeText(context, "ไม่มีข้อมูล", Toast.LENGTH_LONG).show();
                } else {
                    lisviewReview.setAdapter(new CreateViewReview(ReviewActivity.this, ob));
                }
            } else {
                Toast.makeText(context, "No Internet Connect", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class CreateViewReview extends BaseAdapter {

        public Context context;
        public List<ReviewFood.ListReviewMyfood> listReviewMyfoods;
        public LayoutInflater inflater;

        public CreateViewReview(Context context, List<ReviewFood.ListReviewMyfood> list) {
            this.context = context;
            this.listReviewMyfoods = list;
        }

        @Override
        public int getCount() {
            return listReviewMyfoods.size();
        }

        @Override
        public Object getItem(int position) {
            return listReviewMyfoods.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.content_list_rebiew, null);

                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            TextView textViewname = (TextView) convertView.findViewById(R.id.textViewname);
            TextView textVieTimereview = (TextView) convertView.findViewById(R.id.textVieTimereview);
            TextView textNamereview = (TextView) convertView.findViewById(R.id.textNamereview);
            TextView textePrice = (TextView) convertView.findViewById(R.id.textePrice);
            TextView texteMenu = (TextView) convertView.findViewById(R.id.texteMenu);
            TextView textviewDetailreview = (TextView) convertView.findViewById(R.id.textviewDetailreview);

            textViewname.setText(listReviewMyfoods.get(position).getName_member());
            textVieTimereview.setText(listReviewMyfoods.get(position).getTime_review());
            textNamereview.setText(listReviewMyfoods.get(position).getTitle_name());
            textePrice.setText(listReviewMyfoods.get(position).getPrice());
            texteMenu.setText(listReviewMyfoods.get(position).getRecommended_menu());
            textviewDetailreview.setText(listReviewMyfoods.get(position).getDetail());

            viewHolder.gridView.setAdapter(new SetViewImageReview(ReviewActivity.this, listReviewMyfoods.get(position).getImgReview()));
            viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final int posi = lisviewReview.getPositionForView(view);

                    listImg = new ArrayList<String>();
                    for (int i = 0; i < listReviewMyfoods.get(posi).getImgReview().size(); i++) {
                        listImg.add(listReviewMyfoods.get(posi).getImgReview().get(i).getImg_path());
                    }
                    Intent intent = new Intent(ReviewActivity.this, ActivityShowViewimg.class);
                    intent.putExtra("arrayImg", listImg);
                    startActivity(intent);

                }
            });


            return convertView;
        }

        public class ViewHolder {
            public GridView gridView;

            public ViewHolder(View converView) {
                gridView = (GridView) converView.findViewById(R.id.img_foogReview);
            }
        }


    }


    public class SetViewImageReview extends BaseAdapter {

        Context context;
        List<ReviewFood.ListImgReview> list;
        public LayoutInflater inflater;

        public SetViewImageReview(Context context, List<ReviewFood.ListImgReview> list) {
            this.context = context;
            this.list = list;
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
            Viewholder viewholder;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.content_img_view, parent, false);
                viewholder = new Viewholder(convertView);
                convertView.setTag(viewholder);
            } else {
                viewholder = (Viewholder) convertView.getTag();
            }

            Log.e("list", list.get(position).getImg_path());
            Glide.with(context)
                    .load(list.get(position).getImg_path())
                    .placeholder(R.drawable.ic_panorama_black_24dp)
                    .error(R.drawable.ic_panorama_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .override(300, 200)
                    .into(viewholder.imageView);

            return convertView;
        }

        public class Viewholder {
            public ImageView imageView;

            public Viewholder(View convertView) {
                imageView = (ImageView) convertView.findViewById(R.id.img_view);
            }
        }
    }
}
