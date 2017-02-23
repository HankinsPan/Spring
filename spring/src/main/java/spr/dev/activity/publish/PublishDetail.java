package spr.dev.activity.publish;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;
import com.squareup.picasso.Picasso;

import spr.dev.R;
import spr.dev.util.ShowToast;

/**
 * Created by hanki on 2017/2/22.
 */

public class PublishDetail extends AppCompatActivity
        implements Toolbar.OnMenuItemClickListener,
        View.OnClickListener {
    private static final String TAG = "PublishDetail";

    private Toolbar toolbar;
    private EditText edtReply;
    View.OnClickListener hide = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(PublishDetail.this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtReply.getWindowToken(), 0);
        }
    };
    private RelativeLayout rltSend;
    private TextView nName;
    private TextView nTitle;
    private TextView nContent;
    private ImageView nImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_detail);

        initView();
        addListener();
        initEvent();
        initBar();
    }


    private void initView() {
        nName = (TextView) findViewById(R.id.tv_pub_id);
        nTitle = (TextView) findViewById(R.id.tv_pub_detail_title);
        nContent = (TextView) findViewById(R.id.tv_pub_detail_content);
        nImage = (ImageView) findViewById(R.id.iv_pub_detail_image);

        rltSend = (RelativeLayout) findViewById(R.id.rlt_pub_reply_send);
        edtReply = (EditText) findViewById(R.id.edt_pub_reply);

    }

    private void initBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(PublishDetail.this.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(edtReply.getWindowToken(), 0);
                onBackPressed();
            }

        });
    }

    private void initEvent() {
        String springObjectId = getIntent().getStringExtra("springObjectId");
        Log.e(TAG, "== Show get Id is == " + springObjectId);
        AVObject avObject = AVObject.createWithoutData("Spring_Pub", springObjectId);
        avObject.fetchInBackground("owner", new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    Log.e(TAG, "== get avObject success ==" + e);
                    nName.setText(avObject.getAVUser("owner") == null
                            ? "User"
                            : avObject.getAVUser("owner").getUsername());

                    nTitle.setText(avObject.getString("pub_title"));
                    nContent.setText(avObject.getString("pub_content"));
                    Picasso.with(PublishDetail.this)
                            .load(avObject.getAVFile("image") == null
                                    ? "www"
                                    : avObject.getAVFile("image").getUrl())
                            .into(nImage);
                } else {
                    Log.e(TAG, "== get avObject success ==" + e);
                }
            }
        });


    }

    private void addListener() {
        rltSend.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pub_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mark:
                hide.notify();

                ShowToast.ColorToast(PublishDetail.this, "Mark It", 1200);
                break;

            case R.id.action_share:
                showOptionsDialog();
                break;

            case R.id.action_report:
                ShowToast.ColorToast(PublishDetail.this, "Report It", 1200);
                break;

            default:
                break;
        }

        return true;
    }

    private void showOptionsDialog() {
        String[] items = new String[]{"分享给微信朋友", "分享到微信朋友圈",
                "分享到新浪微博", "分享到QQ空间"};

        DialogInterface.OnClickListener click =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                break;

                            case 1:

                                break;

                            case 2:

                                break;

                            case 3:

                                break;
                        }
                    }
                };

        new AlertDialog.Builder(this).setItems(items, click).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlt_pub_reply_send:
                String mReply = edtReply.getText().toString().trim();

                ShowToast.ColorToast(PublishDetail.this, mReply, 3000);
                break;

            default:
                break;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.bottom_to_top_in, R.anim.top_to_bottom_out);
    }
}
