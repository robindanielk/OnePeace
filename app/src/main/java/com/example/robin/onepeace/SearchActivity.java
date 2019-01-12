package com.example.robin.onepeace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import com.example.robin.onepeace.ResponseModel.RecepieResponse;
import com.google.android.gms.common.api.Api;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
          searchView=findViewById(R.id.searchView);
          searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
              @Override
              public boolean onQueryTextSubmit(String query) {
                  fetchRecipeList(query);
                  return false;
              }

              @Override
              public boolean onQueryTextChange(String newText) {
                  return false;
              }
          });
    }

    private void fetchRecipeList(String query) {
        NetWorkUtil.provideRetrofit().create(NetworkCalls.class)
                .fetchReceipes(query)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableObserver<RecepieResponse>() {
                            @Override
                            public void onNext(RecepieResponse questionFollowresponseModel) {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
    }
}
