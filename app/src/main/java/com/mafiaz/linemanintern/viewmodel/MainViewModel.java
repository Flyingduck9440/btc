package com.mafiaz.linemanintern.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mafiaz.linemanintern.R;
import com.mafiaz.linemanintern.api.APIService;
import com.mafiaz.linemanintern.api.RetrofitInstance;
import com.mafiaz.linemanintern.data.RespondData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<RespondData.Coins>> mCoinsData;
    private ArrayList<RespondData.Coins> mCoinArrayList;
    private MutableLiveData<Boolean> status;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mCoinsData = new MutableLiveData<>();
        mCoinArrayList = new ArrayList<>();
        status = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<RespondData.Coins>> getData() {
        return mCoinsData;
    }

    public MutableLiveData<Boolean> isLoading() {
        return status;
    }

    public ArrayList<RespondData.Coins> getArrayList() {
        return mCoinArrayList;
    }

    public void setArrayList(ArrayList<RespondData.Coins> arrayList) {
        mCoinArrayList.clear();
        this.mCoinArrayList = arrayList;
    }

    public void callAPI() {
        status.postValue(true);
        APIService service = RetrofitInstance.getRequest();
        Call<RespondData> call = service.getCoinList();

        call.enqueue(new Callback<RespondData>() {
            @Override
            public void onResponse(@NonNull Call<RespondData> call, @NonNull Response<RespondData> response) {
                RespondData data = response.body();
                if (response.isSuccessful() && response.body() != null && !response.body().getStatus().equals("fail")) {
                    mCoinsData.postValue((ArrayList<RespondData.Coins>) data.getData().getCoins());
                } else {
                    Toast.makeText(getApplication(), getApplication().getString(R.string.null_response), Toast.LENGTH_SHORT).show();
                }
                status.postValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<RespondData> call, @NonNull Throwable t) {
                status.postValue(false);
                Toast.makeText(getApplication(), getApplication().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
