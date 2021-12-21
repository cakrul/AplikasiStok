package com.alpha.stokbarang.api;

public class UtilsApi {
    // servernya
    public static final String BASE_URL_API = "https://api-produk.alphalogic.web.id/api/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
