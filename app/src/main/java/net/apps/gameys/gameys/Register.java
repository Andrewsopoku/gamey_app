package net.apps.gameys.gameys;

/**
 * Created by andrews on 5/20/18.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.nineoldandroids.view.ViewHelper;

public class Register extends AppCompatActivity{



        static final int NUM_PAGES = 4;

        ViewPager pager;
        PagerAdapter pagerAdapter;
        LinearLayout circles;
        Button skip;
        Button done;
        ImageButton next;
        boolean isOpaque = true;
        MaterialTextField name,address,state,zip,tel,email,dob,coc,cor;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            setContentView(R.layout.activity_tutorial);

            name =(MaterialTextField)findViewById(R.id.name);


            skip = Button.class.cast(findViewById(R.id.skip));
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    endTutorial();
                }
            });

            next = ImageButton.class.cast(findViewById(R.id.next));
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pager.setCurrentItem(pager.getCurrentItem() + 1, true);
                }
            });

            done = Button.class.cast(findViewById(R.id.done));
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    endTutorial();
                }
            });

            pager = (ViewPager) findViewById(R.id.pager);
            pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);
            pager.setPageTransformer(true, new CrossfadePageTransformer());
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (position == NUM_PAGES - 2 && positionOffset > 0) {
                        if (isOpaque) {
                            pager.setBackgroundColor(Color.TRANSPARENT);
                            isOpaque = false;
                        }
                    } else {
                        if (!isOpaque) {
                            pager.setBackgroundColor(getResources().getColor(R.color.primary_material_light));
                            isOpaque = true;
                        }
                    }
                }

                @Override
                public void onPageSelected(int position) {
                    setIndicator(position);
                    if (position == NUM_PAGES - 2) {
                        skip.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        done.setVisibility(View.VISIBLE);
                    } else if (position < NUM_PAGES - 2) {
                        skip.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        done.setVisibility(View.GONE);
                    } else if (position == NUM_PAGES - 1) {
                        endTutorial();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            buildCircles();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if(pager!=null){
                pager.clearOnPageChangeListeners();
            }
        }

        private void buildCircles(){
            circles = LinearLayout.class.cast(findViewById(R.id.circles));

            float scale = getResources().getDisplayMetrics().density;
            int padding = (int) (5 * scale + 0.5f);

            for(int i = 0 ; i < NUM_PAGES - 1 ; i++){
                ImageView circle = new ImageView(this);
                circle.setImageResource(R.drawable.ic_swipe_indicator_white_18dp);
                circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                circle.setAdjustViewBounds(true);
                circle.setPadding(padding, 0, padding, 0);
                circles.addView(circle);
            }

            setIndicator(0);
        }

        private void setIndicator(int index){
            if(index < NUM_PAGES){
                for(int i = 0 ; i < NUM_PAGES - 1 ; i++){
                    ImageView circle = (ImageView) circles.getChildAt(i);
                    if(i == index){
                        circle.setColorFilter(getResources().getColor(R.color.text_selected));
                    }else {
                        circle.setColorFilter(getResources().getColor(android.R.color.transparent));
                    }
                }
            }
        }

        private void endTutorial(){
            finish();
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        }

        @Override
        public void onBackPressed() {
            if (pager.getCurrentItem() == 0) {
                super.onBackPressed();
            } else {
                pager.setCurrentItem(pager.getCurrentItem() - 1);
            }
        }

        private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

            public ScreenSlidePagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                ProductTourFragment tp = null;
                switch(position){
                    case 0:
                        tp = ProductTourFragment.newInstance(R.layout.register1);
                        break;
                    case 1:
                        tp = ProductTourFragment.newInstance(R.layout.register2);
                        break;
                    case 2:
                        tp = ProductTourFragment.newInstance(R.layout.register3);
                        break;
                    default:
                        tp = ProductTourFragment.newInstance(R.layout.register1);
                        break;
                }

                return tp;
            }

            @Override
            public int getCount() {
                return NUM_PAGES;
            }
        }

        public class CrossfadePageTransformer implements ViewPager.PageTransformer {

            @Override
            public void transformPage(View page, float position) {
                int pageWidth = page.getWidth();

                View backgroundView = page.findViewById(R.id.welcome_fragment);

                View welcomeImage01 = page.findViewById(R.id.welcome_01);
                View welcomeImage02 = page.findViewById(R.id.welcome_01);
                View welcomeImage03 = page.findViewById(R.id.welcome_01);



                if(0 <= position && position < 1){
                    ViewHelper.setTranslationX(page,pageWidth * -position);
                }
                if(-1 < position && position < 0){
                    ViewHelper.setTranslationX(page,pageWidth * -position);
                }

                if(position <= -1.0f || position >= 1.0f) {
                } else if( position == 0.0f ) {
                } else {
                    if(backgroundView != null) {
                        ViewHelper.setAlpha(backgroundView,1.0f - Math.abs(position));

                    }



                    if (welcomeImage01 != null) {
                        ViewHelper.setTranslationX(welcomeImage01,(float)(pageWidth/2 * position));
                        ViewHelper.setAlpha(welcomeImage01,1.0f - Math.abs(position));
                    }

                    if (welcomeImage02 != null) {
                        ViewHelper.setTranslationX(welcomeImage02,(float)(pageWidth/2 * position));
                        ViewHelper.setAlpha(welcomeImage02,1.0f - Math.abs(position));
                    }

                    if (welcomeImage03 != null) {
                        ViewHelper.setTranslationX(welcomeImage03,(float)(pageWidth/2 * position));
                        ViewHelper.setAlpha(welcomeImage03,1.0f - Math.abs(position));
                    }
                }
            }
        }
    }