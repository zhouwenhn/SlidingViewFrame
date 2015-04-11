package com.example.slidingviewframe;

import android.R;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * @author Chow.Wen 周文
 * 
 * @email chowenhn@gmail.com,121229960@qq.com
 * 
 * @date 2014年11月23日
 * 
 * @version V_1.0.0
 * 
 * @description
 * 
 */
public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener {

	private final static int VIEW_COUNTS = 4;
	
	private SparseArray<Fragment> mViewArray = new SparseArray<Fragment>();
	
	private ViewPager mViewPager;
	
	private FragmentPagerAdapter mFragmentAdapter;
	
	private RadioButton mChatRb;
	
	private RadioButton mContactsRb;
	
	private RadioButton mFindRb;
	
	private RadioButton mPersonalRb;
	
	private ImageView mBottomLineIv;

	private int mNavItemWidth;

	private int mTopNavIndicatorMargin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		setViewPagerListener();
		initTabLine();

	}

	private void initTabLine() {
		// Display display = getWindow().getWindowManager().getDefaultDisplay();
		// DisplayMetrics outMetrics = new DisplayMetrics();
		// display.getMetrics(outMetrics);
		// metrics1_3 = outMetrics.widthPixels / 3;
		// LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)
		// ivBottomLine
		// .getLayoutParams();
		// layoutParams.width = metrics1_3;
		// //ivBottomLine.setLayoutParams(layoutParams);
		// ivBottomLine.requestLayout();
		// ivBottomLine.invalidate();
		
		DisplayMetrics dm = getResources().getDisplayMetrics();
		mNavItemWidth = dm.widthPixels / VIEW_COUNTS;
		// int navIndicatorWidth =
		// mContext.getResources().getDimensionPixelSize(R.dimen.nav_indicator_width_2_tab_item);
		int navIndicatorWidth = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 100, getResources()
						.getDisplayMetrics());
		ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mBottomLineIv
				.getLayoutParams();
		mTopNavIndicatorMargin = (mNavItemWidth - navIndicatorWidth) / 2;
		lp.leftMargin = mTopNavIndicatorMargin;
	}

	private void setViewPagerListener() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				Log.e("onPageSelected>>>>", arg0 + "");
				initTextView();
				switch (arg0) {
				case 0:
					mChatRb.setTextColor(Color.BLACK);
					break;
				case 1:
					mContactsRb.setTextColor(Color.BLACK);
					break;
				case 2:
					mFindRb.setTextColor(Color.BLACK);
					break;
				case 3:
					mPersonalRb.setTextColor(Color.BLACK);
					break;
				default:
					break;
				}
				//pageIndex = arg0;
			}

			@Override
			public void onPageScrolled(int position, float offset, int arg2) {
				// LinearLayout.LayoutParams layoutParams =
				// (LinearLayout.LayoutParams) ivBottomLine
				// .getLayoutParams();
				// Log.e("onPageSelected>>>>111", position + "");
				// if (pageIndex == 0 && position == 0) {// 0--1
				// layoutParams.leftMargin = (int) (pageIndex * metrics1_3 +
				// offset
				// * metrics1_3);
				// } else if (pageIndex == 1 && position == 0) {// 1--0
				// layoutParams.leftMargin = (int) (pageIndex * metrics1_3 +
				// metrics1_3
				// * (offset - 1));
				// } else if (pageIndex == 1 && position == 1) {// 1--2
				// layoutParams.leftMargin = (int) (pageIndex * metrics1_3 +
				// metrics1_3
				// * offset);
				// } else if (pageIndex == 2 && position == 1) {// 2--1
				// layoutParams.leftMargin = (int) (pageIndex * metrics1_3 +
				// metrics1_3
				// * (offset - 1));
				// }
				// // ivBottomLine.setLayoutParams(layoutParams);
				// ivBottomLine.requestLayout();
				ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mBottomLineIv
						.getLayoutParams();
				lp.leftMargin = (mNavItemWidth * position)
						+ mTopNavIndicatorMargin
						+ (int) (offset * (mBottomLineIv.getWidth() + mTopNavIndicatorMargin * 2));
				mBottomLineIv.requestLayout();

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			private void initTextView() {
				mChatRb.setTextColor(Color.WHITE);
				mContactsRb.setTextColor(Color.WHITE);
				mFindRb.setTextColor(Color.WHITE);
				mPersonalRb.setTextColor(Color.WHITE);
			}
		});

	}

	private void initView() {
		((RadioGroup) findViewById(R.id.rg_tabs)).setOnCheckedChangeListener(this);
		mChatRb = (RadioButton) findViewById(R.id.rb_chat);
		mContactsRb = (RadioButton) findViewById(R.id.rb_contacts);
		mFindRb = (RadioButton) findViewById(R.id.rb_find);
		mPersonalRb = (RadioButton) findViewById(R.id.rb_personal);
		mBottomLineIv = (ImageView) findViewById(R.id.line_bottom);
		mViewPager = (ViewPager) findViewById(R.id.view_page);
		
		mChatRb.setTextColor(Color.BLACK);
		mChatRb.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.btn_tab_msg_pressed), null, null);
		
		ChatFragment chatFragment = new ChatFragment();
		FindFragment findFragment = new FindFragment();
		ContactsFragment contactsFragment = new ContactsFragment();
		PersonFragment personFragment = new PersonFragment();

		mViewArray.put(0, chatFragment);
		mViewArray.put(1, contactsFragment);
		mViewArray.put(2, findFragment);
		mViewArray.put(3, personFragment);
		mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mFragmentAdapter);

	}

	private class FragmentAdapter extends FragmentPagerAdapter {

		public FragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			super.destroyItem(container, position, object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			return super.instantiateItem(container, position);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return super.isViewFromObject(view, object);
		}

		@Override
		public Fragment getItem(int position) {
			return mViewArray.get(position);
		}

		@Override
		public int getCount() {
			return mViewArray.size();
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
		case R.id.rb_chat:
			mViewPager.setCurrentItem(0);
			break;

		case R.id.rb_contacts:
			mViewPager.setCurrentItem(1);
			break;
		case R.id.rb_find:
			mViewPager.setCurrentItem(2);
			break;
		case R.id.rb_personal:
			mViewPager.setCurrentItem(3);
			break;
		default:
			break;
		}
	}

}
