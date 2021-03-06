package com.kd.higit.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kd.gitnb.R;
import com.kd.higit.bean.CurrentUser;
import com.kd.higit.bean.User;
import com.kd.higit.fragment.ShowCaseFragment;
import com.kd.higit.fragment.ShowRepositoriesFragment;
import com.kd.higit.fragment.StarsFragment;
import com.kd.higit.fragment.TrendingReposFragment;
import com.kd.higit.utils.KLog;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by KD on 2016/6/17.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static int FOR_LANGUAGE = 200;
    private TabPagerAdapter pagerAdapter;
    private DrawerLayout mDrawerLayout;
    private CoordinatorLayout mCoordinatorLayout;
    private NavigationView mNavigationView;
    private SimpleDraweeView userAvatar;
    private Toolbar mToolbar;
    private TextView userName;
    private DisplayMetrics dm;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private SearchView mSearchView;
    private User me;
    private String search_text;
    private long exitTime = 0; ////记录第一次点击的时间

    public interface UpdateLanguageListener {
        Void updateLanguage(String language);
        Void moveToUp();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        KLog.d("onCreate");
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_main);
        initView();
        initDrawer();
    }

    public void initView() {
        dm = getResources().getDisplayMetrics();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("HiGit");
        setSupportActionBar(mToolbar);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        me = CurrentUser.get(MainActivity.this);

        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new ShowCaseFragment(), "ShowCase");
        pagerAdapter.addFragment(new TrendingReposFragment(), "Trending");
        pagerAdapter.addFragment(new ShowRepositoriesFragment(), "MyRepos");
        pagerAdapter.addFragment(new StarsFragment(), "Starred");
        //未完待续...
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(1);
        ////关闭预加载，默认一次只加载一个Fragment
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        //一下两行必须同时设置，标签评分tab
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                UpdateLanguageListener languageListener = (UpdateLanguageListener) pagerAdapter.getItem(mViewPager.getCurrentItem());
                languageListener.moveToUp();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FOR_LANGUAGE && resultCode == RESULT_OK) {
            //String lanuage = data.getStringExtra(La)
        }
    }

    public void initDrawer() {
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
            View view = mNavigationView.inflateHeaderView(R.layout.nav_header);
            userAvatar = (SimpleDraweeView) view.findViewById(R.id.avatar);
            userName = (TextView) view.findViewById(R.id.headerText);
            if(me == null) {
                userName.setText("Login...");
                userAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, AuthorizeActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                userName.setText(me.getLogin());
                userName.setOnClickListener(null);
                userAvatar.setImageURI(Uri.parse(me.getAvatar_url()));
                userAvatar.setOnClickListener(null);

            }

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);
            mDrawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }
    }

    public class TabPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(MainActivity.this, "正在开发...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_friends:
                Toast.makeText(MainActivity.this, "正在开发...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.nav_sign_out:
                showSignOutDialog();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showSnackBar(mCoordinatorLayout, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        KLog.d("onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.searchview_menu, menu);
        final MenuItem item = menu.findItem(R.id.search_view);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setQueryHint("search repos...");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    search_text = new String(query.trim().getBytes(), "ISO-8859-1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(MainActivity.this, SearchReposActivity.class);
                intent.putExtra("search_content", search_text);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public void showSnackBar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    private void showSignOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("Do you want to sign out ?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CurrentUser.delete(MainActivity.this);
                me = null;
                finish();
                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.dialog_anim);
    }
}
