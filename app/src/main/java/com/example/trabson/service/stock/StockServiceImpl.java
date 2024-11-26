package com.example.trabson.service.stock;

import android.util.Log;

import com.example.trabson.config.RetrofitConfig;
import com.example.trabson.model.Stock;
import com.example.trabson.model.dto.StockResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockServiceImpl {

    String API_KEY = "smud9UeC49ZLexnn6NiFay";

    private IStockService stockService;

    public StockServiceImpl() {
        stockService = new RetrofitConfig().getStockRetrofit().create(IStockService.class);
    }

    public void getAllStocks(String type, final StockCallback stockCallback) {

        Call<StockResponseDTO> call = stockService.getStockList(type, API_KEY);

        call.enqueue(new Callback<StockResponseDTO>() {
            @Override
            public void onResponse(Call<StockResponseDTO> call, Response<StockResponseDTO> response) {

                Log.d("BRAPI", "Código de resposta: " + response.code());

                if (response.isSuccessful() && response.body() != null) {

                    StockResponseDTO stockResponseDTO = response.body();

                    if (stockResponseDTO.getStocks() != null) {
                        stockCallback.onSuccess(stockResponseDTO.getStocks());
                        Log.d("BRAPI", "Lista criada com sucesso!");
                    } else {
                        stockCallback.onError("Lista de ações vazia ou não encontrada.");
                    }

                } else {
                    Log.e("BRAPI", "Falha na resposta: " + response.message());
                    stockCallback.onError("Falha na resposta: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<StockResponseDTO> call, Throwable throwable) {
                Log.e("BRAPI", "Erro de requisição: " + throwable.getMessage(), throwable);
                stockCallback.onError("Erro na requisição: " + throwable.getMessage());
            }
        });
    }

    public interface StockCallback {
        void onSuccess(List<Stock> stocks);
        void onError(String error);
    }
}