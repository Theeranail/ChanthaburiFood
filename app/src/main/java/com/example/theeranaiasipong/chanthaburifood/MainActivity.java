package com.example.theeranaiasipong.chanthaburifood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.theeranaiasipong.chanthaburifood.model.CheckAccessInternet;
import com.example.theeranaiasipong.chanthaburifood.model.Config;
import com.example.theeranaiasipong.chanthaburifood.model.Food;
import com.example.theeranaiasipong.chanthaburifood.model.JsonHttp;
import com.example.theeranaiasipong.chanthaburifood.model.MyLocation;
import com.example.theeranaiasipong.chanthaburifood.model.SharedPreferencesCheck;
import com.example.theeranaiasipong.chanthaburifood.model.getlisternerlocation;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, getlisternerlocation, SwipeRefreshLayout.OnRefreshListener {

    public ListView listViewFood;
    public List<Food.listFood> listarrayfood;
    public Food food;
    public MyLocation myLocation;
    public SharedPreferencesCheck spc;
    public double Lattitude = 0;
    public double Logtitude = 0;
    double distace = 0;
    private CheckAccessInternet cAn;
    private SwipeRefreshLayout swipeRefreshLayout;
    public FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarman);
        setSupportActionBar(toolbar);
        spc = new SharedPreferencesCheck(MainActivity.this);
        spc.checksharedPre();

        myLocation = new MyLocation(MainActivity.this);

        listViewFood = (ListView) findViewById(R.id.listViewFood);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        fab = (FloatingActionButton) findViewById(R.id.btnSearch);


        swipeRefreshLayout.setOnRefreshListener(this);
        fab.setOnClickListener(this);


        cAn = new CheckAccessInternet(MainActivity.this);
        if (cAn.isConnectNet()) {
            new Loadfoos(MainActivity.this).execute();
        } else {
            Toast.makeText(MainActivity.this, "No Internet Connected", Toast.LENGTH_LONG).show();
        }


        ClickShowdetail();


    }

    @Override
    protected void onStart() {
        super.onStart();
        spc.checksharedPre();
        myLocation.StartLocation();

//        swipeRefreshLayout.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        swipeRefreshLayout.setRefreshing(true);
//                                        if (cAn.isConnectNet()) {
//                                            RefreshLoadfoos();
//                                        } else {
//                                            Toast.makeText(MainActivity.this, "No Internet Connected", Toast.LENGTH_LONG).show();
//                                        }
//                                    }
//                                }
//        );


    }

    @Override
    protected void onStop() {
        super.onStop();
        myLocation.Stoplocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        spc.checksharedPre();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:
               Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                break;
         }
    }

    public void ClickShowdetail() {
        listViewFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                intent.putExtra("id_food", listarrayfood.get(position).getId_food());
                intent.putExtra("name_food", listarrayfood.get(position).getName_food());
                intent.putExtra("address", listarrayfood.get(position).getAddress());
                intent.putExtra("detail", listarrayfood.get(position).getDetail());
                intent.putExtra("lattitude", listarrayfood.get(position).getLattitude());
                intent.putExtra("logtitude", listarrayfood.get(position).getLogtitude());
                intent.putExtra("img_food", listarrayfood.get(position).getImg_food());
                intent.putExtra("tel", listarrayfood.get(position).getTel());
                intent.putExtra("price", listarrayfood.get(position).getPrice());
                intent.putExtra("Timeservie", listarrayfood.get(position).getTimeservie());
                intent.putExtra("score", listarrayfood.get(position).getScore());
                intent.putExtra("detail_orther", listarrayfood.get(position).getDetail_orther());
                intent.putExtra("linkcontact", listarrayfood.get(position).getLinkcontact());
                intent.putExtra("link", listarrayfood.get(position).getLink());
                startActivity(intent);

            }
        });

    }

    @Override
    public void getLatliogLocation(Location location) {
        Log.e("lat", String.valueOf(location.getLatitude()));
        Lattitude = location.getLatitude();
        Logtitude = location.getLongitude();
//        for (int i = 0; i < listarrayfood.size(); i++) {
//
//        }
    }

    @Override
    public void onRefresh() {

        if (cAn.isConnectNet()) {
        RefreshLoadfoos();
          } else {
          Toast.makeText(this, "No Internet Connected", Toast.LENGTH_LONG).show();
          swipeRefreshLayout.setRefreshing(false);
        }

    }

    public void RefreshLoadfoos() {

        swipeRefreshLayout.setRefreshing(true);

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formbody = new FormBody.Builder()
                    .add("function", "getLisfood")
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
                            Type listType = new TypeToken<List<Food.listFood>>() {
                            }.getType();
                            List<Food.listFood> posts = gson.fromJson(result, listType);
                            listarrayfood = posts;
                            if (listarrayfood != null) {
                                UPdateView(listarrayfood);
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

    public void UPdateView(final List<Food.listFood> list) {

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                if(list.get(0).getName_food().equals("")){
                    Toast.makeText(MainActivity.this, "ไม่มีข้อมูล", Toast.LENGTH_LONG).show();
                }else {
                    CreateView createView = new CreateView(MainActivity.this, list);
                    listViewFood.setAdapter(createView);
                    createView.notifyDataSetChanged();
                }

            }

        });

    }

    public void DissmisRefreshLayout() {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "No Internet Connect", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }

        });
    }


public class Loadfoos extends AsyncTask<Object, Integer, List<Food.listFood>> {

    ProgressDialog progress;
    Context context;

    public Loadfoos(Context context) {
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
    protected List<Food.listFood> doInBackground(Object... params) {

        try {

            JsonHttp jsonHttp = new JsonHttp();
            Gson gson = new Gson();
            String ressult = jsonHttp.LoadFood();
            Log.e("ressult", ressult);
            Type listType = new TypeToken<List<Food.listFood>>() {
            }.getType();
            List<Food.listFood> posts = gson.fromJson(ressult, listType);

            return posts;

        } catch (RuntimeException e) {
            String msg = (e.getMessage()==null)?"Load failed!":e.getMessage();
            Log.e("RuntimeException", msg);
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Food.listFood> ob) {
        super.onPostExecute(ob);

        if (progress.isShowing()) {
            progress.dismiss();
        }
        if (ob != null) {
            if(ob.get(0).getName_food().equals("")){
                Toast.makeText(context, "ไม่มีข้อมูล", Toast.LENGTH_LONG).show();
            }else {
                listarrayfood = ob;

                listViewFood.setAdapter(new CreateView(context, listarrayfood));
            }

        } else {
            Toast.makeText(context, "No Internet Connect", Toast.LENGTH_LONG).show();
        }
    }
}

public class CreateView extends BaseAdapter {

    private List<Food.listFood> list;
    private LayoutInflater inflater;
    Context context;

    public CreateView(Context context, List<Food.listFood> objects) {
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
            convertView = inflater.inflate(R.layout.content_listview_main, null);
        }

        TextView TextnameFood = (TextView) convertView.findViewById(R.id.person_name);
        TextnameFood.setText(list.get(position).getName_food());

        TextView textAddress = (TextView) convertView.findViewById(R.id.text_location);
        String strAddress = new String(list.get(position).getAddress());
        if(strAddress.length() > 59)
            strAddress = strAddress.substring(0,58)+"...";
        textAddress.setText("ตำแหน่ง: " + strAddress);

        RatingBar textScore = (RatingBar) convertView.findViewById(R.id.content_vodt);

        if (Integer.valueOf(list.get(position).getScore()) <= 49) {
            textScore.setRating(1);
        } else if (Integer.valueOf(list.get(position).getScore()) <= 50) {
            textScore.setRating(2);
        } else if (Integer.valueOf(list.get(position).getScore()) <= 100) {
            textScore.setRating(3);
        } else if (Integer.valueOf(list.get(position).getScore()) <= 150) {
            textScore.setRating(4);
        } else if (Integer.valueOf(list.get(position).getScore()) >= 200) {
            textScore.setRating(5);
        }

        TextView text_distane = (TextView) convertView.findViewById(R.id.content_distane);


        distace = myLocation.CalculationDistance(Lattitude, Logtitude, Double.valueOf(list.get(position).getLattitude()).doubleValue(), Double.valueOf(list.get(position).getLogtitude()).doubleValue());
        text_distane.setText(String.format("%.2f", distace) + " กม.");

        ImageView img_show = (ImageView) convertView.findViewById(R.id.img_show);


        Glide.with(context)
                .load(list.get(position).getImg_food())
                .placeholder(R.drawable.ic_panorama_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_show);


        return convertView;
    }
}

}
