package com.ardaayvatas.androidwebview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;
    EditText editText;
    TextView webPageName;
    int go=0;
    int tab=0;
    int select;
    int control=0;
    String tempLink;
    String webPageTitle;
    String webPage;
    char[] charArr;
    char[] charArrTwo;

    ArrayList<String> links = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        editText = findViewById(R.id.link);
        webPageName = findViewById(R.id.webPageName);

        tabLayout.addTab(tabLayout.newTab().setText("TAB"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                select = tab.getPosition();
                MyFragment.webView.loadUrl(links.get(select));
                editText.setText(links.get(select));
                viewPager.setCurrentItem(tab.getPosition());

                webPage=editText.getText().toString();
                webPage=webPage.replaceAll("www.","").replaceAll("//m.","").replaceAll("https","").replaceAll("http","").replaceAll(":","").replaceAll("/","");
                charArrTwo = webPage.toCharArray();

                for (int i=0;i<webPage.length();i++)
                {
                    if (charArrTwo[i]=='.')
                    {
                        webPage=webPage.substring(0,i);
                        break;
                    }
                }
                webPageName.setText(webPage.toUpperCase());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int i=tab.getPosition();
                links.set(i,MyFragment.webView.getOriginalUrl());
                if (go==1 && select>0 && control!=1)
                {
                    links.set(0,"");
                }
                if (go==1 && select>0 && control==1)
                {
                    links.set(0,tempLink);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void go(View view)
    {
        go++;

        if (select==0 && go==1)
        {
            control = 1;
            tempLink = editText.getText().toString();
        }
        String x = editText.getText().toString();
        links.add(x);
        MyFragment.webView.loadUrl(editText.getText().toString());
        webPageTitle=x.replaceAll("www.","").replaceAll("//m.","").replaceAll("https","").replaceAll("http","").replaceAll(":","").replaceAll("/","");
        charArr = webPageTitle.toCharArray();
        for (int i=0;i<webPageTitle.length();i++)
        {
            if (charArr[i]=='.')
            {
                webPageTitle=webPageTitle.substring(0,i);
                break;
            }
        }
        tabLayout.getTabAt(select).setText(webPageTitle);
        webPageName.setText(webPageTitle.toUpperCase());
    }

    public void createNewTab(View view)
    {
        if (tab==0)
        {
            links.add(tab,"");
        }
        tab++;
        links.add(tab,"");
        tabLayout.addTab(tabLayout.newTab().setText("TAB"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

}