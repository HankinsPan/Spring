package spr.dev.activity.publish;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import spr.dev.R;
import spr.dev.util.SaveSoftkayboard;
import spr.dev.util.ShowToast;
import spr.dev.util.TakePhotoUtils;
import spr.dev.view.loading.MonIndicator;

/**
 * Created by hanki on 2017/2/19.
 */

public class PublishActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "PublishActivity";

    //    private static final int TAKE_PHOTO_REQUEST_TWO = 444;
//    private static final int TAKE_PHOTO_REQUEST_ONE = 333;
    private static final int TAKE_PHOTO_REQUEST_THREE = 555;

    private Uri imageUri;
    private ContentResolver resolver = null;
    private byte[] mImageBytes = null;

    private Toolbar toolbar;
    private ImageView ivPubSend;
    private TextView tvLength;
    private EditText edtTitle;
    private EditText edtContent;
    View.OnClickListener hide = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(PublishActivity.this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtContent.getWindowToken(), 0);
        }
    };
    private RelativeLayout rltPhoto;
    private String wordLength;
    private ImageView imgPubAdd;
    private MonIndicator monIndLoading;

    private static Uri createImageUri(Context context) {
        String name = "takePhoto" + System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, name);
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, name + ".jpeg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = context.getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        return uri;
    }

    public static void delteImageUri(Context context, Uri uri) {
        context.getContentResolver().delete(uri, null, null);
    }

    /**
     * 获取压缩图片的options
     *
     * @return
     */
    public static BitmapFactory.Options getOptions(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = 4;      //此项参数可以根据需求进行计算
        options.inJustDecodeBounds = false;

        return options;
    }


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
                onBackPressed();
            }

        });
    }

    private void initView() {
        tvLength = (TextView) findViewById(R.id.tv_write_length);
        ivPubSend = (ImageView) findViewById(R.id.iv_pub_send);

        edtTitle = (EditText) findViewById(R.id.edt_pub_title);
        edtContent = (EditText) findViewById(R.id.edt_pub_content);

        rltPhoto = (RelativeLayout) findViewById(R.id.rlt_add_photo);
        imgPubAdd = (ImageView) findViewById(R.id.iv_pub_add);

        monIndLoading = (MonIndicator) findViewById(R.id.monind_loading);

    }

    private void addListener() {
        rltPhoto.setOnClickListener(this);
        ivPubSend.setOnClickListener(this);
    }

    private void initEvent() {

        edtContent.setFocusable(true);
        edtContent.setFocusableInTouchMode(true);
        edtContent.requestFocus();
        edtContent.requestFocusFromTouch();

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
                ivPubSend.setOnClickListener(hide);

                String pubTitle = edtTitle.getText().toString().trim();
                String pubContent = edtContent.getText().toString().trim();

                sendPublish(pubTitle, pubContent);
                break;

            case R.id.rlt_add_photo:
                imgPubAdd.setOnClickListener(hide);

                showOptionsDialog();
                break;

            default:
                break;
        }
    }

    private void sendPublish(String pubTitle, String pubContent) {
        if ("".equals(pubTitle)) {
            ShowToast.ColorToast(PublishActivity.this, "= Waring = No Title", 1200);
            return;
        }

        if ("".equals(pubContent)) {
            ShowToast.ColorToast(PublishActivity.this, "= Waring = No Content", 1200);
            return;
        }

        if (mImageBytes == null) {
            ShowToast.ColorToast(PublishActivity.this, "== Waring == No Image", 1200);
            return;
        }


        monIndLoading.setVisibility(View.VISIBLE);
        monIndLoading.setColors(new int[]{
                Color.rgb(255, 255, 0),
                Color.rgb(255, 0, 0),
                Color.rgb(60, 120, 216),
                Color.rgb(255, 0, 255),
                Color.rgb(0, 255, 51)});

        AVObject spring_pub = new AVObject("Spring_Pub");

        spring_pub.put("pub_title", pubTitle);
        spring_pub.put("pub_content", pubContent);
        spring_pub.put("owner", AVUser.getCurrentUser());
        spring_pub.put("image", new AVFile("pub_image", mImageBytes));

        spring_pub.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {

                if (e == null) {
                    Log.e(TAG, "== Save Pub Success ==" + e);

                    monIndLoading.setVisibility(View.GONE);
                    ShowToast.ColorToast(PublishActivity.this, "Save Pub Success", 1200);
                    onBackPressed();
                } else {

                    monIndLoading.setVisibility(View.GONE);
                    Log.e(TAG, "== Save Pub Failed ==" + e);
                }

            }
        });

    }

    private void showOptionsDialog() {
        String[] items = new String[]{"拍照", "选择本地图片"};

        DialogInterface.OnClickListener click =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0://拍照
                                try {
                                    imageUri = TakePhotoUtils.takePhoto(
                                            PublishActivity.this,
                                            TAKE_PHOTO_REQUEST_THREE);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                break;

                            case 1://选择本地图片
                                pickImageFromAlbum();
                                break;
                        }
                    }
                };

        new AlertDialog.Builder(this).setItems(items, click).show();
    }

    public void pickImageFromAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 222);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 111:
            case 222:
                if (resultCode == RESULT_CANCELED) {
                    ShowToast.ColorToast(PublishActivity.this, "点击取消从相册选择", 1500);
                    return;
                }

                try {
                    Uri imageUri = data.getData();
                    Log.e("TAG", imageUri.toString());
                    imgPubAdd.setImageURI(imageUri);

                    mImageBytes = getBytes(getContentResolver()
                            .openInputStream(data.getData()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case TAKE_PHOTO_REQUEST_THREE:
                if (resultCode == RESULT_CANCELED) {
                    ShowToast.ColorToast(PublishActivity.this, "点击取消从相册选择", 1500);
                    return;
                }

                Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath(),
                        getOptions(imageUri.getPath()));
                imgPubAdd.setImageBitmap(bitmap);

                mImageBytes = Bitmap2Bytes(bitmap);
                break;

            default:
                break;
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;

        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    public static byte[] Bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.bottom_to_top_in, R.anim.top_to_bottom_out);
        Log.e(TAG, "-- onBackPressed --");
    }


}
