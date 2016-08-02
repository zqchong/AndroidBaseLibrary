package com.licaigc.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.licaigc.AndroidBaseLibrary;
import com.licaigc.Transformer;
import com.licaigc.network.NetworkUtils;

public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";

    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtils.getLarge("http://wap.apk.anzhi.com/data2/apk/201501/30/369648e7b2272c849dada75f58222915_36484700.apk", new NetworkUtils.OnGetLarge() {
                            @Override
                            public void onStart() {
                                Log.e(TAG, "onStart: ");
                            }

                            @Override
                            public boolean onData(byte[] bytes, long totalLength, long receivedLength) {
                                Log.e(TAG, receivedLength * 100 / totalLength + ":" + Transformer.byte2String(bytes[0]));
                                return true;
                            }

                            @Override
                            public void onFinish(boolean suc, Throwable throwable) {
                                Log.e(TAG, "onFinish: " + suc + " - ");
                            }
                        });
//                NetworkUtils.downloadFileWithProgress("http://wap.apk.anzhi.com/data2/apk/201501/30/369648e7b2272c849dada75f58222915_36484700.apk", new File(getExternalFilesDir(null), "123.apk"),
//                        new NetworkUtils.OnDownloadFileWithProgress() {
//                            @Override
//                            public void onStart() {
//                                Log.e(TAG, "onStart: ");
//                            }
//
//                            @Override
//                            public void onProgress(int progress) {
//                                Log.e(TAG, "" + progress);
//                            }
//
//                            @Override
//                            public void onFinish(boolean suc) {
//                                Log.e(TAG, "onFinish: " + suc);
//                            }
//                        });
//                UpdateUtils.checkUpdate("123").subscribe(new SimpleEasySubscriber<ResponseCheckUpdate>() {
//                    @Override
//                    public void onSuccess(final ResponseCheckUpdate responseCheckUpdate) {
//                        super.onSuccess(responseCheckUpdate);
//                        new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog)
//                                .setTitle("有新版本啦")
//                                .setMessage(responseCheckUpdate.new_version)
//                                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(responseCheckUpdate.url));
//                                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                                        request.setDestinationInExternalFilesDir(AndroidBaseLibrary.getContext(), null, responseCheckUpdate.url_md5);
//                                        final DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//                                        final long id = downloadManager.enqueue(request);
//                                        AndroidBaseLibrary.getContext().registerReceiver(new BroadcastReceiver() {
//                                            @Override
//                                            public void onReceive(Context context, Intent intent) {
//                                                Cursor cursor = downloadManager.query(new DownloadManager.Query().setFilterById(id).setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL));
//                                                if (cursor.moveToFirst()) {
//                                                    Uri apk = Uri.parse(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
//                                                    PackageUtils.installPackage(new File(apk.getPath()));
//                                                }
//                                                cursor.close();
//                                            }
//                                        }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//                                    }
//                                })
//                                .show();
//                    }
//                });
            }
        });

        AndroidBaseLibrary.initialize(getApplicationContext());

        mBtn.post(new Runnable() {
            @Override
            public void run() {
                mBtn.performClick();
            }
        });
    }
}
