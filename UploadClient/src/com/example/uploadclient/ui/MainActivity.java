package com.example.uploadclient.ui;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.uploadclient.R;

/**
 * haha
 * @author taozhishan
 * @2015-7-20
 */
public class MainActivity extends Activity implements OnClickListener {

	private LinearLayout layout_select;

	private static final int FLAG_LOAD_IMAGE = 1;

	private String pathName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
	}

	private void initView() {
		layout_select = (LinearLayout) findViewById(R.id.layout_select);
		layout_select.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_select:
			selectPictures();
			break;

		default:
			break;
		}
	}

	/**
	 * 选择照片
	 */
	private void selectPictures() {
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, FLAG_LOAD_IMAGE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == FLAG_LOAD_IMAGE) {
			if (data == null) {
				Toast.makeText(MainActivity.this, "未选择图片~", Toast.LENGTH_SHORT).show();
			} else {
				Uri uri = data.getData();
				if (uri == null) {
					Toast.makeText(MainActivity.this, "未选择图片~", Toast.LENGTH_SHORT).show();
				} else {
					String path = null;
					String[] pojo = { MediaStore.Images.Media.DATA };
					Cursor cursor = getContentResolver().query(uri, pojo, null, null, null);
					if (cursor != null) {
						int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
						cursor.moveToFirst();
						path = cursor.getString(columnIndex);
						cursor.close();
					}
					if (path == null) {
						Toast.makeText(MainActivity.this, "未能获得图片的路径", Toast.LENGTH_SHORT).show();
					} else {
						pathName = path;
						doUpload();
					}
				}
			}
		}
	}

	/**
	 * 上传
	 */
	private void doUpload() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					FileInputStream in = new FileInputStream(new File(pathName));
					Map<String, String> data = new HashMap<String, String>();
					data.put("Name", "Sam");
					data.put("Age", "23");

				} catch (Exception e) {

				}

			}
		}).start();
	}
}
