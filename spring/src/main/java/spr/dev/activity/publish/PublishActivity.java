package spr.dev.activity.publish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import spr.dev.R;
import spr.dev.util.SaveSoftkayboard;

/**
 * Created by hanki on 2017/2/19.
 */

public class PublishActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "PublishActivity";

    private Toolbar toolbar;
    private ImageView ivPubSend;

    private TextView tvLength;
    private EditText edtTitle;
    private EditText edtContent;
    private RelativeLayout rltPhoto;
    private RelativeLayout rltCamera;

    private GridView gridView;

    private String wordLength;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        SaveSoftkayboard.assistActivity(this);


        initView();
        addListener();
        initEvent();
        initBar();

    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("");
        Log.e(TAG, "-- onResume --");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "-- onDestroy --");
        this.finish();
    }

    private void initBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(PublishActivity.this.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(edtContent.getWindowToken(), 0);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                onBackPressed();
            }

        });
    }

    private void initView() {
        tvLength = (TextView) findViewById(R.id.tv_write_length);
        ivPubSend = (ImageView) findViewById(R.id.iv_pub_send);

        edtTitle = (EditText) findViewById(R.id.edt_pub_title);
        edtContent = (EditText) findViewById(R.id.edt_pub_content);
        rltPhoto = (RelativeLayout) findViewById(R.id.rlt_add_iv_photo);
        rltCamera = (RelativeLayout) findViewById(R.id.rlt_add_iv_camera);

        gridView = (GridView) findViewById(R.id.noScrollGridView);
    }

    private void addListener() {
        rltPhoto.setOnClickListener(this);
        rltCamera.setOnClickListener(this);
    }

    private void initEvent() {


        edtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int numLenght;
                numLenght = s.length();
                wordLength = String.valueOf(numLenght);
                tvLength.setText(wordLength);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_pub_send:

                break;

            case R.id.rlt_add_iv_photo:

                break;

            case R.id.rlt_add_iv_camera:

                break;

            default:
                break;
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.bottom_to_top_in, R.anim.top_to_bottom_out);

        Log.e(TAG, "-- onBackPressed --");
    }


}
