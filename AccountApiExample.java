import sendinblue.*;
import sendinblue.auth.*;
import sibModel.*;
import sibApi.AccountApi;

import java.io.File;
import java.util.*;

public class AccountApiExample {

    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

        // Configure API key authorization: api-key
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey("YOUR API KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //apiKey.setApiKeyPrefix("Token");

        // Configure API key authorization: partner-key
        ApiKeyAuth partnerKey = (ApiKeyAuth) defaultClient.getAuthentication("partner-key");
        partnerKey.setApiKey("YOUR PARTNER KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //partnerKey.setApiKeyPrefix("Token");

        AccountApi apiInstance = new AccountApi();
        try {
            GetAccount result = apiInstance.getAccount();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling AccountApi#getAccount");
            e.printStackTrace();
        }
    }
}