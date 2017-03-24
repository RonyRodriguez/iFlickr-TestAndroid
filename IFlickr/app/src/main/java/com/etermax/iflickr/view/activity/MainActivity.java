package com.etermax.iflickr.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.etermax.iflickr.R;
import com.etermax.iflickr.api.ApiClientImp;
import com.etermax.iflickr.apimodel.Base.PhotoBase;
import com.etermax.iflickr.apimodel.collection.PhotoArray;
import com.etermax.iflickr.apimodel.detail.PhotoObject;
import com.etermax.iflickr.modeldb.PhotoDetail;
import com.etermax.iflickr.tool.DaoUtil;
import com.etermax.iflickr.view.adapter.PhotoAdapter;
import com.etermax.iflickr.viewmodel.PhotoViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import needle.Needle;
import needle.UiRelatedTask;

public class MainActivity extends AppCompatActivity
        implements DaoUtil, NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    final Activity _activity = this;
    public ProgressDialog pdialog;

    public PhotoAdapter adapter;
    public List<PhotoBase> photoList = new ArrayList<PhotoBase>();
    private CompositeDisposable mCompositeDisposable;

    private int page = 1;
    private boolean isCard;
    private String search = "";

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        GridLayoutManager grid = new GridLayoutManager(this.getApplicationContext(), 3);
        recyclerView.setLayoutManager(grid);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            page = 1;
            refrestPhotoList();
        });

        mCompositeDisposable = new CompositeDisposable();
        adapter = new PhotoAdapter(this, photoList, isCard);
        recyclerView.setAdapter(adapter);

        pdialog = new ProgressDialog(this, R.style.MyTheme);
        pdialog.setCancelable(false);
        pdialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = grid.getChildCount();
                    totalItemCount = grid.getItemCount();
                    pastVisiblesItems = grid.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            page++;
                            refrestPhotoList();
                        }
                    }
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        pdialog.show();
        search = "";
        connectToFlickr_List(search);
    }


    void connectToFlickr_List(String search) {
        if (isInternetConnectionAvailable()) {
            PhotoViewModel photoViewModel = new PhotoViewModel(AndroidSchedulers.mainThread());
            if (search.length() == 0) {
                mCompositeDisposable.add(photoViewModel.getRecent(String.valueOf(page))
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
            } else {
                mCompositeDisposable.add(photoViewModel.getSearch(String.valueOf(page), search)
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
            }
        } else {
            getData();
        }
    }

    void connectToFlickr_Detail(String id, String secret) {
        if (isInternetConnectionAvailable()) {
            PhotoViewModel photoViewModel = new PhotoViewModel(AndroidSchedulers.mainThread());
            mCompositeDisposable.add(photoViewModel.getInfo(id, secret)
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse_detail, this::handleError));
        } else {
            getDetail(id, secret);
        }
    }

    void connectToFlickr_Detail_onNext() {
        for (PhotoBase pbase : photoList) {
            Observable<PhotoObject> observable = ApiClientImp.getInstance().getInfo(pbase.getId(), pbase.getSecret());
            observable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe((photoObject ->
                            handleResponseNext(pbase, photoObject)
                    ), this::handleError);
        }
        handleZip();
    }


    public void searchAdapter(String query) {
        search = query;
        pdialog.show();
        connectToFlickr_List(search);
    }

    void refrestPhotoList() {
        mSwipeRefreshLayout.setRefreshing(false);
        connectToFlickr_List(search);

    }

    public void saveData() {
        Needle.onBackgroundThread().execute(new UiRelatedTask<Long>() {
            @Override
            protected Long doWork() {
                return PhotoViewModel.insertPhotos_db(photoList);
            }

            @Override
            protected void thenDoUiRelatedWork(Long result) {
                updateIU(photoList);
                pdialog.dismiss();
            }
        });
    }

    public void getData() {
        Needle.onBackgroundThread().execute(new UiRelatedTask<Integer>() {
            @Override
            protected Integer doWork() {
                photoList = PhotoViewModel.selectPhotos_db();
                return 1;
            }

            @Override
            protected void thenDoUiRelatedWork(Integer result) {
                updateIU(photoList);
                pdialog.dismiss();
            }
        });
    }

    public void saveDataDetail(PhotoDetail entity) {
        Needle.onBackgroundThread().execute(new UiRelatedTask<Long>() {
            @Override
            protected Long doWork() {
                return PhotoViewModel.insertPhotoDetail_db(entity);
            }

            @Override
            protected void thenDoUiRelatedWork(Long result) {
                pdialog.dismiss();
                goto_Detail_Activity(entity);
            }
        });
    }

    public void getDetail(String id, String secret) {
        Needle.onBackgroundThread().execute(new UiRelatedTask<PhotoDetail>() {
            @Override
            protected PhotoDetail doWork() {
                return PhotoViewModel.selectPhotoDetail_db(id);
            }

            @Override
            protected void thenDoUiRelatedWork(PhotoDetail result) {
                pdialog.dismiss();
                if (result.get_id() != null) {
                    goto_Detail_Activity(result);
                } else {
                    Toast.makeText(_activity, "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void goto_Detail_Activity(PhotoDetail entity) {
        Intent intent = new Intent(_activity, DetailActivity.class);
        intent.putExtra("PhotoDetailJSON", PhotoViewModel.getPhotoDetailJson(entity));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private boolean isInternetConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) {
            return false;
        } else {
            return activeNetwork.isConnectedOrConnecting();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void handleResponseNext(PhotoBase pbase, PhotoObject pobject) {
        pbase.setIconfarm(pobject.getPhoto().getOwner().getIconfarm());
        pbase.setIconserver(pobject.getPhoto().getOwner().getIconserver());
        pbase.setUsername(pobject.getPhoto().getOwner().getUsername());
        pbase.setLocation(pobject.getPhoto().getOwner().getLocation());
        pbase.setTaken(pobject.getPhoto().getDates().getTaken());
    }


    private void handleResponse(PhotoArray photoArray) {
        photoList = photoArray.getPhotos().getPhotoList();

        connectToFlickr_Detail_onNext();

    }

    private void handleZip() {
        saveData();
        pdialog.dismiss();
        mSwipeRefreshLayout.setRefreshing(false);
        loading = true;
    }


    private void handleResponse_detail(PhotoObject photoObject) {
        saveDataDetail(PhotoViewModel.converApiObject2PhotoDatail(photoObject));
    }

    private void updateIU(List<PhotoBase> list) {
        photoList = list;
        adapter = new PhotoAdapter(this, photoList, isCard);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void handleError(Throwable error) {
        Toast.makeText(this, "Error " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        pdialog.dismiss();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void onclick_photoDetaiil(String id, String secret) {
        pdialog.show();
        connectToFlickr_Detail(id, secret);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            isCard = !isCard;

            if (isCard) {
                recyclerView.setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 1));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 3));
            }

            updateIU(photoList);

            return true;
        } else if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAdapter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_send) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.etermax.com"));
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
