package momu.cloudvisionsample;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import gun0912.tedbottompicker.TedBottomPicker;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends AppCompatActivity {

    ImageView imgPic;
    TextView txtCenter;
    TextView txtResult;
    ProgressBar progressBar;

    private static final String CLOUD_VISION_API_KEY = BuildConfig.API_KEY;
    private static final String CLOUD_VISION_URL = "https://vision.googleapis.com/v1/images:annotate?key=%s";
    private static final String CLOUD_VISION_REQUESTS = "{  \"requests\":[{\"image\":{\"source\":{\"imageUri\": \"%s\"}},\"features\":[{\"type\":\"LANDMARK_DETECTION\"},{\"type\": \"FACE_DETECTION\"}," +
            "{\"type\":\"LOGO_DETECTION\"},{\"type\":\"LABEL_DETECTION\"},{\"type\":\"IMAGE_PROPERTIES\"},{\"type\":\"SAFE_SEARCH_DETECTION\"},{\"type\":\"WEB_DETECTION\"}]}]}";

    Context mContext;
    Gson gson = new Gson();

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        imgPic = (ImageView) findViewById(R.id.img_picture);
        txtCenter = (TextView) findViewById(R.id.txt_center);
        txtResult = (TextView) findViewById(R.id.txt_result);
        progressBar = (ProgressBar) findViewById(R.id.progress_dialog);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TedPermission.with(MainActivity.this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this servicePlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(WRITE_EXTERNAL_STORAGE)
                        .check();
            }
        });
    }

    /**
     * Request to Cloud Vision API
     *
     * @param imgUri Image Uri from Google Cloud Storage (ex. gs://...)
     */
    void cloudVisionProcess(final String imgUri) {
        Log.e(TAG, "start cloudvision process");
        final String url = String.format(CLOUD_VISION_URL, CLOUD_VISION_API_KEY);

        new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, String.format(CLOUD_VISION_REQUESTS, imgUri));
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .addHeader("content-type", "application/json")
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    final String resStr = response.body().string();
                    Log.e(TAG, "response : " + resStr);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtResult.setText(resStr);
                            txtResult.setVisibility(View.VISIBLE);
                            removeProgressBar();
                        }
                    });
                } catch (NullPointerException | IOException e) {
                    e.printStackTrace();
                    removeProgressBar();
                }
            }
        }.start();
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            pickImage();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(MainActivity.this, "Permission Denied" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * pick image from gallery
     */
    void pickImage() {
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(MainActivity.this)
                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        // here is selected uri
                        txtCenter.setVisibility(View.GONE);
                        imgPic.setImageURI(uri);
                        uploadToStorage(uri);
                        progressBar.setVisibility(View.VISIBLE);
                        txtResult.setVisibility(View.VISIBLE);
                        txtResult.setText("please wait...");
//                        uploadToFirebase(uri);
                    }
                })
                .create();

        tedBottomPicker.show(getSupportFragmentManager());
    }

    /**
     * Upload to Google Cloud Storage
     *
     * @param uri Image file uri
     */
    void uploadToStorage(final Uri uri) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.e(TAG, "uploadToStorage processing..");
                try {
                    final InputStream credentialStream = getResources().openRawResource(R.raw.cloud_storage_credential);

                    Storage storage = StorageOptions.newBuilder()
                            .setCredentials(ServiceAccountCredentials.fromStream(credentialStream))
                            .setProjectId("misehan-d3bfe")
                            .build()
                            .getService();

                    long time = System.currentTimeMillis();
                    SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                    String currentTime = dayTime.format(new Date(time));

                    InputStream stream = new FileInputStream(new File(uri.getPath()));
                    String imgUri = "location_" + currentTime + ".jpg";
                    Blob blob = storage.create(BlobInfo.newBuilder("momu_test_vision", imgUri).setContentType("image/*").setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))./*setAcl(acls).*/build(), stream);
                    Log.e(TAG, "blob medialink : " + blob.getMediaLink());
                    if (blob.getMediaLink() != null) {
                        cloudVisionProcess(String.format("gs://momu_test_vision/%s", imgUri));
                    } else {
                        removeProgressBar();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    removeProgressBar();
                } catch (Exception e) {
                    e.printStackTrace();
                    removeProgressBar();
                }
            }
        }.start();
    }

    /**
     * remove progress bar
     */
    public void removeProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
