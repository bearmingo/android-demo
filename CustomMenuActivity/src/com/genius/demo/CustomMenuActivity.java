package com.genius.demo;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class CustomMenuActivity extends Activity {
    /** Called when the activity is first created. */
	
	MenuItemData	mMenuItemData1;
	
	MenuItemData    mMenuItemData2;
	
	private boolean mIsMore = true;				// 弹出菜单翻页控制
	
	private final int ITEM_MORE = 11;			// 弹出菜单切换项
	
	private GridView 		mMenuGrid;			// 弹出菜单GRIDVIEW
	
	private View 			mMenuView;			// 弹出菜单视图
	
	private GridViewAdapter	mGridViewAdapter;	// 弹出菜单适配器
	


	
	//-------------------------------------------------------------------
	
	private PopupWindow			mPopupWindow;		// 弹出菜单WINDOW
	
	
	private MenuItemData		mToolBarItemData;
	
	private GridView 			mToolBarGrid;		// 底部菜单栏
	
	private GridViewAdapter  	mToolBarAdapter;	// 底部菜单栏适配器


	
	private final int 			SWITCH_MENU = 4;	// 显示隐藏菜单
	
	
	
	//-------------------------------------------------------------------
	
	private ListView 			mListView;			
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           
        setContentView(R.layout.main);

        init();
    }
    

	public void init()
    {
    	initToolbarMenu();

    	initPopMenu();

    	initListView();
    	

    	
    //	test();

    }
	
//	public void test()
//	{
//		Rect frame =  new  Rect();  
//		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);  
//		int  statusBarHeight = frame.top;  
//		
//		
//		
//		Log.i("", "statusBarHeight = " + statusBarHeight);
//		
//		int  contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();  
//		//statusBarHeight是上面所求的状态栏的高度   
//		int  titleBarHeight = contentTop - statusBarHeight; 
//		
//	      
//	}
    
    
    private void initPopMenu()
    {
    	String []menu_name_array1 = getResources().getStringArray(R.array.menu_item_name_array1);	
    	LevelListDrawable levelListDrawable1 = (LevelListDrawable) getResources().getDrawable(R.drawable.menu_image_list1);
    	mMenuItemData1 = new MenuItemData(levelListDrawable1, menu_name_array1, menu_name_array1.length);

   
    	String []menu_name_array2 = getResources().getStringArray(R.array.menu_item_name_array2);
    	LevelListDrawable levelListDrawable2 = (LevelListDrawable) getResources().getDrawable(R.drawable.menu_image_list2);
    	mMenuItemData2 = new MenuItemData(levelListDrawable2, menu_name_array2, menu_name_array2.length);
    	
    	
    	LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	mMenuView = inflater.inflate(R.layout.menu, null);
		mMenuGrid = (GridView)mMenuView.findViewById(R.id.menuGridChange);
		

		mGridViewAdapter = new GridViewAdapter(this, mMenuItemData1);
		mMenuGrid.setAdapter(mGridViewAdapter);

		
    	mMenuGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				
				switch (position) {
				case ITEM_MORE:// 翻页
					if (mIsMore) {
					
						mGridViewAdapter.refreshData(mMenuItemData2);
						
						mIsMore = false;
					} else {// 首页
						
						mGridViewAdapter.refreshData(mMenuItemData1);
						
						mIsMore = true;
					}
		
					mMenuGrid.invalidate();
				
					break;
				}
				
			}
    		
    		
		});
    	

   		mMenuGrid.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				
				switch(keyCode)
				{
					case KeyEvent.KEYCODE_MENU:
						showMenuWindow();
						break;
				}
				
				return false;
			}
		});
   		
   		
		mPopupWindow = new PopupWindow(mMenuView, LayoutParams.FILL_PARENT,  LayoutParams.FILL_PARENT);
		mPopupWindow.setFocusable(true);		// 如果没有获得焦点menu菜单中的控件事件无法响应
		
		//	以下两行加上去后就可以使用BACK键关闭POPWINDOW
		//ColorDrawable dw = new ColorDrawable(0xb0000000);
		//mPopupWindow.setBackgroundDrawable(dw);
		
	    mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);  

    }
    
    


    private void initToolbarMenu()
    {
    	String []toolbar_name_array = getResources().getStringArray(R.array.toolbar_name_array);	
    	LevelListDrawable levelListDrawable = (LevelListDrawable) getResources().getDrawable(R.drawable.toolbar_image_list);
    	mToolBarItemData = new MenuItemData(levelListDrawable, toolbar_name_array, toolbar_name_array.length);
    	
    	mToolBarGrid = (GridView) findViewById(R.id.GridView_toolbar);
    	mToolBarAdapter = new GridViewAdapter(this, mToolBarItemData);
    	mToolBarGrid.setAdapter(mToolBarAdapter);
    	mToolBarGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				switch(position)
				{
					case SWITCH_MENU:
						showMenuWindow();
						break;
				}
				
			}
		});
   	
    	mToolBarGrid.setSelection(SWITCH_MENU);
    	
    	
    }
    
    
    private void initListView()
    {
    	mListView = (ListView) findViewById(R.id.ListView);
    	
    	String[] listTitleStrings = getResources().getStringArray(R.array.list_item_name_array);
    	
    	mListView.setAdapter(new ListViewAdapter(this, listTitleStrings));	
    	
    	mListView.setCacheColorHint(0x00000000);	// 设置拖动时保存背景色
    	
    	
    }
    
    private void showMenuWindow()
    {
    	
    	if (mPopupWindow.isShowing())
    	{
    		mPopupWindow.dismiss();
    	}else{
    		mGridViewAdapter.refreshData(mMenuItemData1);
    		mPopupWindow.showAtLocation(findViewById(R.id.RelativeLayout), Gravity.TOP, 0,0);
 
    	}

    }
    
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {		// 截获菜单事件
		// TODO Auto-generated method stub

		showMenuWindow();
		
		return false;		// 返回为true 则显示系统menu
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		menu.add("menu");// 必须创建一项
		return super.onCreateOptionsMenu(menu);
		
	}  
	
}