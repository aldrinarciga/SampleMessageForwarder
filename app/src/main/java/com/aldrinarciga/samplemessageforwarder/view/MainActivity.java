package com.aldrinarciga.samplemessageforwarder.view;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aldrinarciga.samplemessageforwarder.R;
import com.aldrinarciga.samplemessageforwarder.api.PushNotifApi;
import com.aldrinarciga.samplemessageforwarder.model.PushNotif;
import com.aldrinarciga.samplemessageforwarder.model.TokenEvent;
import com.aldrinarciga.samplemessageforwarder.view.contract.MainContract;
import com.aldrinarciga.samplemessageforwarder.view.presenter.MainPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    @BindView(R.id.txtFirebaseId)
    TextView txtFirebaseId;
    @BindView(R.id.txtLatestMessage)
    TextView txtLatestMessage;
    @BindView(R.id.edtMessage)
    EditText edtMessage;
    @BindView(R.id.edtReceiver)
    EditText edtReceiver;
    @BindView(R.id.btnSend)
    Button btnSend;

    private ProgressDialog progressDialog;

    private MainContract.Presenter presenter;
    private Context context;

    /*
     * Activity callbacks
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /*
     * Other methods
     */

    private PushNotifApi createApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://android.googleapis.com/gcm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(PushNotifApi.class);
    }

    /*
     * Contract methods
     */

    @Override
    public void init() {
        presenter = new MainPresenter(this, createApi());
        presenter.init();
        context = this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Sending message...");

        txtFirebaseId.setOnClickListener(onFirebaseIdClicked);
        btnSend.setOnClickListener(onSendButtonClicked);
    }

    @Override
    public void showToken(String token) {
        txtFirebaseId.setText(token);
    }

    @Override
    public void showLoadingIndicator() {
        progressDialog.show();
    }

    @Override
    public void hideLoadingIndicator() {
        progressDialog.dismiss();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /*
     * Inner classes/listeners
     */

    View.OnClickListener onSendButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String message = edtMessage.getText().toString();
            String id = edtReceiver.getText().toString();
            if(id.isEmpty()) {
                id = txtFirebaseId.getText().toString();
            }

            presenter.sendPushNotif(message, id);
        }
    };

    View.OnClickListener onFirebaseIdClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("FirebaseId", txtFirebaseId.getText().toString());
            clipboard.setPrimaryClip(clip);
            showMessage("Firebase Token is copied to clipboard");
        }
    };

    /*
     * EventBus methods
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTokenRegistered(TokenEvent tokenEvent) {
        if(tokenEvent != null && tokenEvent.getToken() != null) {
            showToken(tokenEvent.getToken());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageReceived(PushNotif.Data data) {
        if(data != null && data.getMessage() != null) {
            txtLatestMessage.setText("Latest message: " + data.getMessage());
        }
    }
}
