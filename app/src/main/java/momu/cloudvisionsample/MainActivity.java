package momu.cloudvisionsample;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;

import java.io.IOException;
import java.util.ArrayList;

import gun0912.tedbottompicker.TedBottomPicker;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    ImageView imgPic;
    TextView txtCenter;
//    UploadTask uploadTask;

//    FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final String CLOUD_VISION_API_KEY = BuildConfig.API_KEY;
    private static final String CLOUD_VISION_URL = "https://vision.googleapis.com/v1/images:annotate?key=%s";

    Context mContext;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        imgPic = (ImageView) findViewById(R.id.img_picture);
        txtCenter = (TextView) findViewById(R.id.txt_center);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cloudVisionProcess("gs://misehan-d3bfe.appspot.com/images/location_2017-09-18 02:09:47.jpg");
//                TedPermission.with(MainActivity.this)
//                        .setPermissionListener(permissionlistener)
//                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
//                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        .check();
            }
        });
    }

    /**
     * Cloud Vision 요청.
     *
     * @param imageUri 이미지
     */
    void cloudVisionProcess(final String imageUri) {
        Log.e(TAG, "CLOUDVISION PROCESS 시작");
//        String url = String.format(CLOUD_VISION_URL, CLOUD_VISION_API_KEY);
//
//        JSONObject imageObject = new JSONObject();
//        JSONArray featuresArray = new JSONArray();
//        try {
//            JSONObject sourceObject = new JSONObject();
//            sourceObject.put("imageUri", imageUri);
//            imageObject.put("source", imageObject);
//
//            JSONObject typeObject = new JSONObject();
//            typeObject.put("type", "LANDMARK_DETECTION");
//
//            featuresArray.put(typeObject);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        new Thread() {      //Thread안에 넣어 돌려야 받아올 수 있음.
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\n  \"requests\":[\n    {\n      \"image\":{\n\t\t\"source\": \n    \t\t{\n\t\t\t\t\"imageUri\": \"gs://momu_vision/sich2.jpg\"\n\t\t\t}\n      },\n      \"features\":[\n        {\n          \"type\":\"LANDMARK_DETECTION\"\n        }\n      ]\n    }\n  ]\n}");
                Request request = new Request.Builder()
                        .url("https://vision.googleapis.com/v1/images:annotate?key=AIzaSyBFTqzScNvnTNZ_220kJUELiUw7avkjGJ4")
                        .post(body)
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "e7b84ed1-8491-cac0-02fd-83e884870f0d")
                        .build();


                try {
                    Response response = client.newCall(request).execute();
                    Log.e(TAG, "response : " + response.body().string());
                } catch (NullPointerException | IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            pickImage();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * pick image from gallary
     */
    void pickImage() {
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(MainActivity.this)
                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        // here is selected uri
                        imgPic.setImageURI(uri);
//                        uploadToFirebase(uri);
                    }
                })
                .create();

        tedBottomPicker.show(getSupportFragmentManager());
    }

//    /**
//     * Upload to firebase Storage
//     *
//     * @param uri Uri to Upload
//     */
//    void uploadToFirebase(Uri uri) {
//        // Create a storage reference from our app
//        StorageReference storageRef = storage.getReference();
//
//        long time = System.currentTimeMillis();
//        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//        String currentTime = dayTime.format(new Date(time));
//
//        // Create a reference to 'images/mountains.jpg'
//        StorageReference imageRef = storageRef.child("images/location_" + currentTime + ".jpg");
//        uploadTask = imageRef.putFile(uri);
//
//        // Register observers to listen for when the download is done or if it fails
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle unsuccessful uploads
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                Log.e(TAG, "downloadUrl : " + downloadUrl);
//            }
//        });
//    }

//    /**
//     * http post 요청으로 완전 수동 구현
//     */
//    void uploadToStorage(Uri uri) {
//
//        try {
//            final InputStream credentialStream = getResources().openRawResource(R.raw.vision_sample_credential);
//
//            Storage storage = StorageOptions.newBuilder()
//                    .setCredentials(ServiceAccountCredentials.fromStream(credentialStream))
//                    .setProjectId(CConstants.CLOUD_PRJ_ID)
//                    .build()
//                    .getService();
//
//            long time = System.currentTimeMillis();
//            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//            String currentTime = dayTime.format(new Date(time));
//
//            InputStream stream = new FileInputStream(new File(uri.getPath()));
////            byte[] music = new byte[stream.available()];      //이렇게 보내니 파일이 깨짐.
//            Blob blob = storage.create(BlobInfo.newBuilder("momu_vision", "images/location_" + currentTime + ".jpg").setContentType("image/*")./*setAcl(acls).*/build(), stream);
//            Log.e(TAG, "blob medialink : " + blob.getMediaLink());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
////        runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
////                if (!isFinishing() && loadingDialog != null && loadingDialog.isShowing()) {
////                    loadingDialog.dismiss();
////                }
////            }
////        });
//    }
}
