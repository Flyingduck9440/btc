package com.mafiaz.linemanintern;
/***
 * created by : Saharat Suwansiri
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.mafiaz.linemanintern.data.DataAdapter;
import com.mafiaz.linemanintern.databinding.ActivityMainBinding;
import com.mafiaz.linemanintern.functions.checkPosition;
import com.mafiaz.linemanintern.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements DataAdapter.onItemClickListener {

    private ActivityMainBinding mBinding;
    private MainViewModel mViewModel;

    private int mFirstIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBinding.recyclerview.setLayoutManager(linearLayoutManager);

        DataAdapter adapter = new DataAdapter(this, this, mViewModel.getArrayList());
        mBinding.recyclerview.setAdapter(adapter);

        mBinding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mFirstIndex = linearLayoutManager.findFirstVisibleItemPosition();
            }
        });

        mViewModel.callAPI();

        mViewModel.getData().observe(this, data -> {
            if (data != null) {
                mViewModel.setArrayList(data);
                adapter.setDataAdapter(data);

                linearLayoutManager.scrollToPosition(mFirstIndex);
            }
        });

        mViewModel.isLoading().observe(this, status -> {
            mBinding.swipeLayout.setRefreshing(status);
        });

        mBinding.swipeLayout.setOnRefreshListener(() -> {
            mViewModel.callAPI();
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("topIndex", mFirstIndex);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mFirstIndex = savedInstanceState.getInt("topIndex");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    @Override
    public void onItemClick(int position) {
        if (checkPosition.getResult(position)) {
            Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(mViewModel.getArrayList().get(position).getCoinrankingUrl()));
            startActivity(browser);
        }
    }
}