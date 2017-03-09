package com.example.theeranaiasipong.chanthaburifood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.theeranaiasipong.chanthaburifood.model.Config;
import com.example.theeranaiasipong.chanthaburifood.model.Food;
import com.example.theeranaiasipong.chanthaburifood.model.JsonHttp;
import com.example.theeranaiasipong.chanthaburifood.model.MyLocation;
import com.example.theeranaiasipong.chanthaburifood.model.SearchFood;
import com.example.theeranaiasipong.chanthaburifood.model.SharedPreferencesCheck;
import com.example.theeranaiasipong.chanthaburifood.model.getlisternerlocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, getlisternerlocation {

    public EditText inputSearch;
    public ImageButton btnbackrearch;
    public ListView list_item_search, list_view_showSearch;
    public MyLocation myLocation;
    public Double lattitude = null;
    public Double logtitude = null;
    public String inputsesrch = "";
    public List<Food.listFood> listFoods;
    public double distace = 0;
    public String name = "";
    public RelativeLayout contentcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarman);
        setSupportActionBar(toolbar);

        inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearch.requestFocus();
        btnbackrearch = (ImageButton) findViewById(R.id.btnbackrearch);
        list_item_search = (ListView) findViewById(R.id.list_item_search);
        list_view_showSearch = (ListView) findViewById(R.id.list_view_showSearch);
        contentcenter = (RelativeLayout) findViewById(R.id.contentcenter);

        btnbackrearch.setOnClickListener(this);

        myLocation = new MyLocation(SearchActivity.this);

        CreateLisMenuSearch();

        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    inputsesrch = inputSearch.getText().toString();
                    setSearch(name, inputsesrch, lattitude, logtitude);
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        myLocation.StartLocation();
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

    public void CreateLisMenuSearch() {

        SearchFood searchFood = new SearchFood();

        SimpleAdapter simpleAdapter = new SimpleAdapter(SearchActivity.this, searchFood.AddrayyObj(), R.layout.listmenu_search
                , new String[]{"name", "img"}, new int[]{R.id.namemenu, R.id.imgicon});
        list_item_search.setAdapter(simpleAdapter);

        list_item_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchFood searchFood = new SearchFood();
                name = searchFood.AddrayyObj().get(position).get("name").toString();

                setSearch(name, inputsesrch, lattitude, logtitude);

            }
        });

    }

    public void setSearch(String naSearch, String inputSearch, Double lattitude, Double logtitude) {
        new GetSeachfood(this, naSearch, inputSearch, lattitude, logtitude).execute();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnbackrearch:
//                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
                finish();
                break;
        }
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
    public void getLatliogLocation(Location location) {
        lattitude = location.getLatitude();
        logtitude = location.getLongitude();
    }

    public void Updateview(final List<Food.listFood> list, Double lattitude, Double logtitude) {

        if (list.get(0).getName_food().equals("")) {
            contentcenter.setVisibility(View.VISIBLE);
            list_view_showSearch.setVisibility(View.GONE);
        } else {
            contentcenter.setVisibility(View.GONE);
            list_view_showSearch.setVisibility(View.VISIBLE);
            list_view_showSearch.setAdapter(new CreateviewSearch(SearchActivity.this, list, lattitude, logtitude));
        }
        list_view_showSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, DetailActivity.class);

                intent.putExtra("id_food", list.get(position).getId_food());
                intent.putExtra("name_food", list.get(position).getName_food());
                intent.putExtra("address", list.get(position).getAddress());
                intent.putExtra("detail", list.get(position).getDetail());
                intent.putExtra("lattitude", list.get(position).getLattitude());
                intent.putExtra("logtitude", list.get(position).getLogtitude());
                intent.putExtra("img_food", list.get(position).getImg_food());
                intent.putExtra("tel", list.get(position).getTel());
                intent.putExtra("price", list.get(position).getPrice());
                intent.putExtra("Timeservie", list.get(position).getTimeservie());
                intent.putExtra("score", list.get(position).getScore());
                intent.putExtra("detail_orther", list.get(position).getDetail_orther());
                intent.putExtra("linkcontact", list.get(position).getLinkcontact());
                intent.putExtra("link", list.get(position).getLink());
                startActivity(intent);

            }
        });
    }

    public class GetSeachfood extends AsyncTask<Object, Integer, List<Food.listFood>> {
        public Context context;
        public ProgressDialog progress;
        public String naSearch;
        public String inputSearch;
        public Double lattitude;
        public Double logtitude;

        public GetSeachfood(Context context, String naSearch, String inputSearch, Double lattitude, Double logtitude) {
            this.context = context;
            this.naSearch = naSearch;
            this.inputSearch = inputSearch;
            this.lattitude = lattitude;
            this.logtitude = logtitude;
        }

        @Override
        protected void onPreExecute() {

            progress = new ProgressDialog(context);
            progress.setMessage("กำลังค้นหา...");
            progress.setCancelable(false);
            progress.show();

        }

        @Override
        protected List<Food.listFood> doInBackground(Object... params) {
            try {


                JsonHttp jsonHttp = new JsonHttp();
                Gson gson = new Gson();

                RequestBody formBody = new FormBody.Builder()
                        .add("function", "SearchFood")
                        .add("inputeasrch", inputSearch)
                        .add("latti", String.valueOf(lattitude))
                        .add("longtitude", String.valueOf(logtitude))
                        .add("keyword", naSearch)
                        .build();

                String ressult = jsonHttp.SyncTaskOkHttp(formBody, Config.url_getFood);
                Log.e("ressultGetSeachfood", ressult);
                Type listType = new TypeToken<List<Food.listFood>>() {
                }.getType();
                List<Food.listFood> posts = gson.fromJson(ressult, listType);

                return posts;

            } catch (RuntimeException e) {
                Log.e("RuntimeException", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Food.listFood> ob) {
            if (progress.isShowing()) {
                progress.dismiss();
            }
            if (ob != null) {
                listFoods = ob;
                Updateview(ob, lattitude, logtitude);
            } else {
                Toast.makeText(context, "No Internet Connect", Toast.LENGTH_LONG).show();
            }

        }
    }

    public class CreateviewSearch extends BaseAdapter {

        public Context context;
        public List<Food.listFood> list;
        public LayoutInflater inflater;
        public Double Lattitude = null;
        public Double Logtitude = null;
        public MyLocation myLocation;

        public CreateviewSearch(Context context, List<Food.listFood> listFoods, Double Lattitude, Double Logtitude) {
            this.context = context;
            this.list = listFoods;
            this.Lattitude = Lattitude;
            this.Logtitude = Logtitude;
            myLocation = new MyLocation(context);
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
            textAddress.setText("ตำแหน่ง: " + list.get(position).getAddress());

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
Log.e("lat", String.valueOf(Lattitude));
            if (Lattitude == null)
                Lattitude = 13.7335222;
            if (Logtitude == null)
                Logtitude = 100.5375236;
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



