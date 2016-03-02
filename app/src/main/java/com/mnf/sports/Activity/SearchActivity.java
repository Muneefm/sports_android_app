package com.mnf.sports.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.mnf.sports.Adapters.GroupSearchAdapter;
import com.mnf.sports.AppController;
import com.mnf.sports.Config;
import com.mnf.sports.Models.GroupMembersModel.GroupMembersModel;
import com.mnf.sports.Models.GroupSearchModels.GroupSearch;
import com.mnf.sports.R;
import com.mnf.sports.SplashFiles.utils.Strings;
import com.mnf.sports.UIclass.PaperButton;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.json.JSONObject;

import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {
    public SearchBox search;
    Context c;
    String Url = Config.BASE_URL+Config.GROUP_SEARCH;
    String namep,yearp,clsp,grpp="";
    HashMap<String,String> params;
    public Gson gson = new Gson();
    int page =1;
    int totalPages=1;
    GroupSearch searchModel;
    Boolean loading=true;
    PaperButton filterButton,clearButton;
    private LinearLayoutManager mLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    GroupSearchAdapter mAdapterSearch;
    boolean wrapInScrollView = true;
    MaterialDialog dialog;
    View customDialogueView;
    RadioGroup groupRadioGroup,yearRadioGroup;
    EditText classEditText;
    String pYear,pGroup,pClass="";
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    RelativeLayout noResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        c = getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }
        mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progressSearch);
        noResult = (RelativeLayout) findViewById(R.id.noResult);
        params = new HashMap<>();
        params.put("name", "");
        params.put("yr", "");
        params.put("cls","");
        params.put("grp","");
        params.put("page","");

        filterButton = (PaperButton) findViewById(R.id.filterButton);
        clearButton = (PaperButton) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                params = new HashMap<>();
                params.put("name", "");
                params.put("yr", "");
                params.put("cls","");
                params.put("grp", "");
                params.put("page", "");
                groupRadioGroup.clearCheck();
                yearRadioGroup.clearCheck();
                Snackbar.make(view, "Filters Cleared ", Snackbar.LENGTH_LONG)
                        .show();

            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.grp_search_recycle);
        mAdapterSearch = new GroupSearchAdapter(c);
        mLayoutManager
                = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapterSearch);

         dialog =  new MaterialDialog.Builder(this)
                .title("Filter").cancelable(true)
                .customView(R.layout.dialogue_view, wrapInScrollView)
                .positiveText("search").build();
        customDialogueView =  dialog.getCustomView();
        groupRadioGroup = (RadioGroup) customDialogueView.findViewById(R.id.grpRadioGroup);
        yearRadioGroup = (RadioGroup) customDialogueView.findViewById(R.id.grpRadioYear);
        classEditText = (EditText) customDialogueView.findViewById(R.id.id_class);
        View button = dialog.getActionButton(DialogAction.POSITIVE);


        classEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                  //  Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                    if(classEditText.getText()!=null){
                        params.put("cls",classEditText.getText().toString());
                    }
                    if (search.getSearchText() != null) {
                        params.put("name", search.getSearchText().toString());
                    }
                    makeSearchRequest(Url);
                    resetAll("2");
                    dialog.hide();
                    return true;
                }
                return false;
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("tag", "search button press");

                if (classEditText.getText() != null) {
                    params.put("cls", classEditText.getText().toString());
                }
                if (search.getSearchText() != null) {
                    Log.e("tag", "search text = " + search.getSearchText());
                    params.put("name", search.getSearchText().toString());
                }


                makeSearchRequest(Url);
                resetAll("2");
                dialog.hide();
            }
        });
        groupRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.e("tag", "check change lister grup press");
                switch (groupRadioGroup.getCheckedRadioButtonId()) {
                    case R.id.rb:
                        pGroup = "b";
                        break;
                    case R.id.rg:
                        pGroup = "g";
                        break;
                    case R.id.ry:
                        pGroup = "y";
                        break;
                    case R.id.rr:
                        pGroup = "r";
                        break;
                }
                params.put("grp", pGroup);
            }
        });

        yearRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.e("tag", "check change lister year press id = "+yearRadioGroup.getCheckedRadioButtonId()+ "  radioid = "+radioGroup.getCheckedRadioButtonId());

                radioGroup.getCheckedRadioButtonId();
                switch (yearRadioGroup.getCheckedRadioButtonId()){
                    case R.id.yrone:
                        pYear = "1";
                        break;
                    case R.id.yrtwo:
                        pYear = "2";
                        break;
                    case R.id.yrthree:
                        pYear = "3";
                        break;
                    case R.id.yrfour:
                        pYear = "4";
                        break;
                }
                params.put("yr",pYear);
            }
        });


        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterDialogue();
            }
        });



        search = (SearchBox) findViewById(R.id.searchbox);
        search.setAnimateDrawerLogo(true);


        search.setLogoText("Search");
        openSearch();

        search.setSearchListener(new SearchBox.SearchListener() {
            @Override
            public void onSearchOpened() {

            }

            @Override
            public void onSearchCleared() {

            }

            @Override
            public void onSearchClosed() {

            }

            @Override
            public void onSearchTermChanged(String s) {

            }

            @Override
            public void onSearch(String s) {
                Log.e("tag","inside search  key down query = "+s);

                resetAll("1");
                namep = s;
                params.put("name", namep);
                makeSearchRequest(Url);
            }

            @Override
            public void onResultClick(SearchResult searchResult) {

            }
        });

      /*  search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Log.e("tag","inside search edit text key down query = "+search.getSearchText());
                    // Perform action on key press
                    //  Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                    if (search.getSearchText() != null) {
                        params.put("name", search.getSearchText().toString());
                    }
                    makeSearchRequest(Url);
                    resetAll("2");
                    dialog.hide();
                    return true;
                }
                return false;
            }
        });*/

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.e("TAGl", "inside onScolled totpages = " + totalPages);
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        Log.e("TAGl", "inside loading tot & page = " + totalPages + " " + page);
                        if (page < totalPages) {
                            Log.e("TAGl", "inside page<=totalPage");
                            Log.e("TAGl", "last inside tot & page = " + totalPages + " " + page);
                            page++;
//                            paramsModel.add(new ParamsModel("page", page + ""));
                                params.put("page",page+"");
                            makeSearchRequest(Url);
                            //  showProgg();
                        }

                    }
                }

            }
        });



    }

    public void showFilterDialogue(){
        dialog.show();

    }

    public void openSearch(){
        search.revealFromMenuItem(R.id.menu_Search, this);

    }
    public void closeSearch(){
        search.hideCircularly(this);

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if(search.getSearchOpen()){
            closeSearch();
        }else{
            super.onBackPressed();
        }

    }
    public void resetAll(String key){
        page = 1;
        params.put("page",page+"");
        Log.e("tag","reset called page = "+page);
        mAdapterSearch.removeItems();
        if(key.equals("1")) {

        }else if(key.equals("2")){

        }
    }


    public void makeSearchRequest(String url){
        mDilatingDotsProgressBar.showNow();
        noResult.setVisibility(View.GONE);
        url +="?";
        for (String hsm :params.keySet()) {
        Log.e("tag","params key set  = "+hsm+" value = "+params.get(hsm));
            url += hsm+"="+ Strings.urlEncode(params.get(hsm))+"&";
        }
        Log.e("tag","final url  = "+url);
        JsonObjectRequest reqtwo = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("tag", "Loaded  data");
                mDilatingDotsProgressBar.hideNow();
                searchModel = gson.fromJson(response.toString(), GroupSearch.class);
                loading = true;
                if(searchModel!=null) {
                    if(searchModel.getStatus().equals("success")) {
                        mAdapterSearch.addItems(searchModel.getResult());
                        totalPages = searchModel.getTotalPages();
                        Log.e("tag","result count = "+searchModel.getResult().size());
                        if(searchModel.getResult().size()==0){
                            Log.e("tag","inside 0 result");
                            noResult.setVisibility(View.VISIBLE);
                        }else{
                            Log.e("tag","result count else case");
                            noResult.setVisibility(View.GONE);
                        }
                    }else{
                        if(searchModel.getResult()!=null){
                            if(searchModel.getResult().size()==0){
                                Log.e("tag","inside 0 result");
                                noResult.setVisibility(View.VISIBLE);
                            }else{
                                Log.e("tag","result count else case");
                                noResult.setVisibility(View.GONE);
                            }
                        }
                    }
                }

            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mDilatingDotsProgressBar.hideNow();
                Snackbar.make(mRecyclerView, R.string.network_error, Snackbar.LENGTH_LONG).show();

                if(volleyError.networkResponse!=null) {
                    if (volleyError.networkResponse.statusCode == 401) {
                        // Toast.makeText(getActivity(), "Login Failed Invalid Credentials", Toast.LENGTH_LONG).show();
                    }
                }
                Log.e("tagmeta", "error is " + volleyError.getLocalizedMessage()+"   \n error details = "+volleyError.getCause());

            }
        });
        AppController.getInstance().addToRequestQueue(reqtwo);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_activty_menu, menu);


        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        //   return true;
        //}
        switch (id){
            case R.id.menu_Search:
                openSearch();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



}
