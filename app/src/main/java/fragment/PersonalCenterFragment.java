package fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.onediscountfreeship.LoginActivity;
import com.example.administrator.onediscountfreeship.NameActivity;
import com.example.administrator.onediscountfreeship.PersonalInfoActivity;
import com.example.administrator.onediscountfreeship.R;
import com.example.administrator.onediscountfreeship.SecondLoginActivity;

import utils.DBHelperOne;
import utils.DBHelperTwo;

/**
 * Created by Administrator on 2016/7/27.
 */
public class PersonalCenterFragment extends Fragment {
    //定义显示图片控件
    private ImageView imageView_head, imageView_login, imageView_name;
    //定义显示文本控件
    private TextView textView_phone, textView_login, textView_name;
    //获得一个intent
    Intent intent = new Intent();
    //定义数据库相关变量
    private DBHelperTwo helperTwo;
    private DBHelperOne helperOne;
    private SQLiteDatabase db, dbOne;
    //声明控件
    private LinearLayout linearLayout_intent;
    private LinearLayout linearLayout_logistics;
    private LinearLayout linearLayout_shopping;
    private LinearLayout linearLayout_favorite;
    private LinearLayout linearLayout_integral;
    private LinearLayout linearLayout_myIntegral;
    private LinearLayout linearLayout_integralMarket;
    private LinearLayout linearLayout_recommend;
    //声明boolean类型变量
    private boolean tag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获得布局
        View view = inflater.inflate(R.layout.fragment_personalcenter, container, false);
        //给boolean类型变量赋初值
        tag = true;
        //获得显示图片控件
        imageView_head = (ImageView) view.findViewById(R.id.imageView_head);
        imageView_login = (ImageView) view.findViewById(R.id.imageView_login);
        imageView_name = (ImageView) view.findViewById(R.id.imageView_name);
        //获得显示文本控件
        textView_phone = (TextView) view.findViewById(R.id.textView_phone);
        textView_login = (TextView) view.findViewById(R.id.textView_login);
        textView_name = (TextView) view.findViewById(R.id.textView_name);
        //设置没有登录注册时的标头样式
        imageView_head.setAlpha(0);
        textView_phone.setText("");
        //获得控件
        linearLayout_favorite = (LinearLayout) view.findViewById(R.id.linearLayout_favorite);
        linearLayout_integral = (LinearLayout) view.findViewById(R.id.linearLayout_integral);
        linearLayout_integralMarket = (LinearLayout) view.findViewById(R.id.linearLayout_integralMarket);
        linearLayout_intent = (LinearLayout) view.findViewById(R.id.linearLayout_intent);
        linearLayout_logistics = (LinearLayout) view.findViewById(R.id.linearLayout_logistics);
        linearLayout_myIntegral = (LinearLayout) view.findViewById(R.id.linearLayout_myIntegral);
        linearLayout_shopping = (LinearLayout) view.findViewById(R.id.linearLayout_shopping);
        linearLayout_recommend = (LinearLayout) view.findViewById(R.id.linearLayout_recommend);
        /**
         * 查询数据库，判断是否登录
         */
        //获得helperTwo,helperOne
        helperTwo = new DBHelperTwo(getContext());
        helperOne = new DBHelperOne(getContext());
        //获得db,dbOne
        db = helperTwo.getReadableDatabase();
        dbOne = helperOne.getReadableDatabase();
        //定义需要执行的sql语句
        String sql = "select * from tag1 where tag = ? ";
        //执行sql语句
        Cursor cursor = db.rawQuery(sql, new String[]{"login"});
        //定义一个int型变量
        int count = cursor.getCount();
        Log.e("TAG", "==>LoginCount" + count);
        //if判断语句
        if (count != 0) {
            //给控件进行相关的设置
            imageView_login.setAlpha(0);
            textView_login.setText("");
            imageView_head.setAlpha(200);

            //当确认登录后，设置tag为false
            tag = false;
            //添加监听事件
            if (!tag) {
                imageView_head.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //使用intent进行跳转页面
                        Intent intent = new Intent(getActivity(), PersonalInfoActivity.class);
                        //进行跳转页面
                        startActivity(intent);
                    }
                });
            }

            //定义需要执行的sql语句
            String sqlQuery = "select * from nickname1 where name = name";
            //执行sql语句
            Cursor cursorQuery = dbOne.rawQuery(sqlQuery, null);
            //定义一个int型变量
            int countQuery = cursorQuery.getCount();
            //if判断语句
            if (countQuery != 0) {
                //遍历查询
                while (cursorQuery.moveToNext()) {
                    //定义一个字符串类型的变量来存值
                    String phoneQuery = cursorQuery.getString(cursorQuery.getColumnIndex("name"));
                    //给控件进行相应的设置
                    textView_phone.setText(phoneQuery);
                }
            } else {
                //定义需要执行的sql语句
                String sqlQuery1 = "select * from tag1 where phone = phone";
                //执行sql语句
                Cursor cursorQuery1 = db.rawQuery(sqlQuery1, null);
                while (cursorQuery1.moveToNext()) {
                    //定义一个字符串类型的变量来存值
                    String phoneQuery = cursorQuery1.getString(cursorQuery1.getColumnIndex("phone"));
                    //给控件进行相应的设置
                    textView_phone.setText(phoneQuery);
                }
            }
        }

        //for循环
        if (tag) {
            //给控件设置监听
            imageView_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进行页面跳转
                    intent.setClass(getActivity(), LoginActivity.class);
                    //执行跳转
                    startActivity(intent);
                }
            });
            textView_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进行页面跳转
                    intent.setClass(getActivity(), LoginActivity.class);
                    //执行跳转
                    startActivity(intent);
                }
            });
        }

        //添加监听事件
        imageView_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行页面跳转
                intent.setClass(getActivity(), NameActivity.class);
                //执行跳转
                startActivity(intent);
            }
        });
        textView_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行页面跳转
                intent.setClass(getActivity(), NameActivity.class);
                //执行跳转
                startActivity(intent);
            }
        });

        //给各个控件添加监听
        linearLayout_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得一个intent，进行页面跳转
                Intent intent = new Intent(getActivity(), SecondLoginActivity.class);
                //执行页面跳转
                startActivity(intent);
            }
        });
        linearLayout_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得一个intent，进行页面跳转
                Intent intent = new Intent(getActivity(), SecondLoginActivity.class);
                //执行页面跳转
                startActivity(intent);
            }
        });
        linearLayout_myIntegral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得一个intent，进行页面跳转
                Intent intent = new Intent(getActivity(), SecondLoginActivity.class);
                //执行页面跳转
                startActivity(intent);
            }
        });
        linearLayout_logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得一个intent，进行页面跳转
                Intent intent = new Intent(getActivity(), SecondLoginActivity.class);
                //执行页面跳转
                startActivity(intent);
            }
        });
        linearLayout_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得一个intent，进行页面跳转
                Intent intent = new Intent(getActivity(), SecondLoginActivity.class);
                //执行页面跳转
                startActivity(intent);
            }
        });
        linearLayout_integralMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得一个intent，进行页面跳转
                Intent intent = new Intent(getActivity(), SecondLoginActivity.class);
                //执行页面跳转
                startActivity(intent);
            }
        });
        linearLayout_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得一个intent，进行页面跳转
                Intent intent = new Intent(getActivity(), SecondLoginActivity.class);
                //执行页面跳转
                startActivity(intent);
            }
        });
        linearLayout_integral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得一个intent，进行页面跳转
                Intent intent = new Intent(getActivity(), SecondLoginActivity.class);
                //执行页面跳转
                startActivity(intent);
            }
        });

        //..........................................................................................

        //返回布局
        return view;
    }

}
