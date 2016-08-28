package com.beijing.navi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.android.core.guide.GuideContoler;
import com.beijing.navi.R;

import java.util.ArrayList;
import java.util.List;

public class GuidePageActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_page);
		initViewPager();
	}

	/** 使用写好的库初始化引导页面 **/
	private void initViewPager() {
		WindowManager manager = getWindowManager();
		int width = manager.getDefaultDisplay().getWidth();
		int height = manager.getDefaultDisplay().getHeight();
		int marginHeight = (int) (0.0272183098591549 * height);
		GuideContoler contoler = new GuideContoler(this, marginHeight);
		// contoler.setmShapeType(ShapeType.RECT);//设置指示器的形状为矩形，默认是圆形
		int[] imgIds = { R.mipmap.guide_1, R.mipmap.guide_2,
				R.mipmap.guide_3 };
		LayoutInflater inflater = LayoutInflater.from(this);
		List<View> views = new ArrayList<View>();
		/**
		 * 第一张图
		 */
		View view1 = inflater.inflate(R.layout.page_one, null);
		views.add(view1);

		/**
		 * 第二张图
		 */
		View view2 = inflater.inflate(R.layout.page_two, null);
		views.add(view2);

		/**
		 * 第五张图
		 */
		View view3 = inflater.inflate(R.layout.page_three, null);
		Button btn = (Button) view3.findViewById(R.id.btn_main);

		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				goMainListener();
			}
		});

		views.add(view3);
		contoler.setmIndicatorWidth(18);
		contoler.setmIndicatorHeight(18);
		contoler.init(views);

		//
		// View view = inflater.inflate(R.layout.pager_four,null);
		// contoler.init(imgIds, view);
		// view.findViewById(R.id.bt_login).setOnClickListener(new
		// View.OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// startActivity(new
		// Intent(GuidePageActivity.this,OperationLoginActivity.class));
		// finish();
		// }
		// });

	}

	protected void goMainListener() {
		// TODO Auto-generated method stub
		startActivity(new Intent(GuidePageActivity.this,
				MainActivity.class));
		finish();
	}

}
