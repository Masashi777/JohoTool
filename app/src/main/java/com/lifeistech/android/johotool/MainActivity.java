package com.lifeistech.android.johotool;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Layout
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        OriginalFragmentPagerAdapter adapter = new OriginalFragmentPagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);

        // タイトルの設定
        setTitle("情報処理 if文ツール");

        // Toolbarの影消し
        ActionBar actionbar = getSupportActionBar();
        actionbar.setElevation(0f);

        // Tabレイアウトの設定
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    public class OriginalFragmentPagerAdapter extends FragmentPagerAdapter {

        public OriginalFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new Frag1();
                case 1:
                    return new Frag2();
                case 2:
                    return new Frag3();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "郵便料金";
                case 1:
                    return "お釣り計算";
                case 2:
                    return "ローマ数字";
            }
            return null;
        }
    }

    public static class Frag1 extends android.support.v4.app.Fragment {

        // Layout
        private EditText weightEdit;
        private TextView resultText;
        private Button button;
        private int x, y;

        private InputMethodManager inputMethodManager;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.frag_1, container, false);

            weightEdit = (EditText) view.findViewById(R.id.weightEdit);
            resultText = (TextView) view.findViewById(R.id.resultText);

            // キーボード制御
            inputMethodManager = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);

            button = (Button) view.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    y = 1800;

                    // キーボードをしまう
                    inputMethodManager.hideSoftInputFromWindow(button.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    // 未入力の場合は処理しない
                    if (weightEdit.getText().toString().matches("")) {
                        // no edit
                    } else {

                        x = Integer.parseInt(weightEdit.getText().toString());

                        if (x <= 20000) {

                            if (x <= 1000) {

                            } else if (x <= 10000) {
                                int z = x - 1000;
                                y = y + (z / 1000) * 550;
                                if (z % 1000 > 0) {
                                    y = y + 550;
                                }
                            } else if (x <= 20000) {
                                y = 6750;
                                int z = x - 10000;
                                y = y + (z / 1000) * 350;
                                if (z % 1000 > 0) {
                                    y = y + 350;
                                }
                            }

                            resultText.setText(y + "円です。");
                        } else {
                            resultText.setText("重量オーバーです。");
                        }
                    }
                }
            });

            return view;
        }
    }

    public static class Frag2 extends android.support.v4.app.Fragment {

        // Layout
        private EditText daikinEdit, azukariEdit;
        private TextView otsuriText;
        private Button button;

        private TextView[] bill = new TextView[8];
        private int x, y, z;
        private int[] coins = new int[8];
        private int[] money = {5000, 1000, 500, 100, 50, 10, 5, 1};
        private InputMethodManager inputMethodManager;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.frag_2, container, false);

            daikinEdit = (EditText) view.findViewById(R.id.daikinEdit);
            azukariEdit = (EditText) view.findViewById(R.id.azukariEdit);

            otsuriText = (TextView) view.findViewById(R.id.otsuriText);

            bill[0] = (TextView) view.findViewById(R.id.b5000);
            bill[1] = (TextView) view.findViewById(R.id.b1000);
            bill[2] = (TextView) view.findViewById(R.id.c500);
            bill[3] = (TextView) view.findViewById(R.id.c100);
            bill[4] = (TextView) view.findViewById(R.id.c50);
            bill[5] = (TextView) view.findViewById(R.id.c10);
            bill[6] = (TextView) view.findViewById(R.id.c5);
            bill[7] = (TextView) view.findViewById(R.id.c1);

            // キーボード制御
            inputMethodManager = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);

            for (int i = 0; i < bill.length; i++) {
                bill[i].setText("");
            }

            button = (Button) view.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // キーボードをしまう
                    inputMethodManager.hideSoftInputFromWindow(button.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    for (int i = 0; i < bill.length; i++) {
                        bill[i].setText("");
                    }

                    // 未入力の場合は処理しない
                    if (daikinEdit.getText().toString().matches("") || azukariEdit.getText().toString().matches("")) {
                        // no edit
                    } else {
                        x = Integer.parseInt(daikinEdit.getText().toString());
                        y = Integer.parseInt(azukariEdit.getText().toString());

                        z = y - x;

                        if (z > 0) {
                            // お釣りを計算する
                            otsuriText.setText("お釣りは" + z + "円です。");

                            for (int i = 0; i < 8; i++) {
                                coins[i] = z / money[i];
                                z = z % money[i];

                                if (coins[i] != 0) {
                                    bill[i].setText(coins[i] + "枚");
                                }
                            }

                        } else if (z == 0) {
                            // ちょうど
                            otsuriText.setText("代金はちょうどです。");

                        } else if (z < 0) {
                            // 代金が足りない
                            otsuriText.setText("代金が足りません。");

                        }


                    }
                }
            });

            return view;
        }

    }

    public static class Frag3 extends android.support.v4.app.Fragment {

        // Layout
        private EditText exchangeEdit;
        private TextView numberText;
        private Button button;

        private int x;
        private int num[] = { 1000, 500, 100, 50, 10, 5, 1 };
        private int amount[] = new int[7];
        private String[] mark = {"M", "D", "C", "L", "X", "V", "I"};
        private String all;
        private InputMethodManager inputMethodManager;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.frag_3, container, false);

            exchangeEdit = (EditText) view.findViewById(R.id.exchangeEdit);
            numberText = (TextView) view.findViewById(R.id.numberText);

            // キーボード制御
            inputMethodManager = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);

            button = (Button) view.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    all = "";

                    // キーボードをしまう
                    inputMethodManager.hideSoftInputFromWindow(button.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    // 未入力の場合は処理しない
                    if (exchangeEdit.getText().toString().matches("")) {
                        // no edit
                    } else {

                        x = Integer.parseInt(exchangeEdit.getText().toString());

                        for (int i = 0; i < 7; i++) {
                            amount[i] = x / num[i];
                            x = x % num[i];

                            for (int n = 0; n < amount[i]; n++) {
                                all = all + mark[i];
                            }
                        }

                        numberText.setText(all);
                    }

                }
            });

            return view;
        }

    }
}
