package com.lck.tools.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.lck.tools.Adapter.PneumoniaCityDataAdapter;
import com.lck.tools.Adapter.PneumoniaProvinceDataAdapter;
import com.lck.tools.Class.PneumoniaData.CityData;
import com.lck.tools.Class.PneumoniaData.PneumoniaData;
import com.lck.tools.Class.PneumoniaData.ProvinceData;
import com.lck.tools.Common.TemporaryData;
import com.lck.tools.R;
import com.wxy.chinamapview.model.ChinaMapModel;
import com.wxy.chinamapview.model.ProvinceModel;
import com.wxy.chinamapview.view.ChinaMapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 肺炎疫情实时数据
 */
public class PneumoniaDataActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "PneumoniaDataActivity";
    private ImageView imageView_back;
    private String key = "c78cb36811b4dde9cfb6024a2cc807a6";
    private ArrayList<ProvinceData> pneumoniaDataArrayList;//省级数据
    private Map<String,Integer> provinceMap;
    private RecyclerView RecyclerView_city;//市级数据
    private final int DataInitSuccess = 1;
    private RecyclerView RecyclerView_province;
    private int sum_confirmedCount = 0;//确诊人数
    private int sum_suspectedCount = 0;//疑似病例
    private int sum_curedCount = 0;//治愈病例
    private int sum_deadCount = 0;//死亡病例
    private TextView textView_updateDate;
    private TextView textView_title;//标题
    private int lastOffset;//该view的顶部的偏移量
    private int lastPosition;//该View的数组位置
    private TextView textView_deadCount;
    private TextView textView_curedCount;
    private TextView textView_suspectedCount;
    private TextView textView_confirmedCount;

    private ChinaMapView map;
    private ChinaMapModel chinaMapModel;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DataInitSuccess:
                    showToast(PneumoniaDataActivity.this,"数据初始化完成");
                    //界面显示数据
                    setMap();
                    setProvinceAdapter();
                    textView_updateDate.setText("更新时间："+TemporaryData.pneumoniaData.getDate());
                    textView_confirmedCount.setText(sum_confirmedCount+"");
                    textView_suspectedCount.setText(sum_suspectedCount+"");
                    textView_curedCount.setText(sum_curedCount+"");
                    textView_deadCount.setText(sum_deadCount+"");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pneumonia_data);

        initBar();
        initView();
        getData();
    }

    private void setProvinceAdapter() {
        //省级
        RecyclerView_province.setVisibility(View.VISIBLE);
        RecyclerView_city.setVisibility(View.GONE);
        textView_title.setText("全国疫情实时数据");
        //计算并显示总览
        textView_confirmedCount.setText(sum_confirmedCount+"");
        textView_suspectedCount.setText(sum_suspectedCount+"");
        textView_curedCount.setText(sum_curedCount+"");
        textView_deadCount.setText(sum_deadCount+"");
        //设置
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        PneumoniaProvinceDataAdapter pneumoniaDataAdapter = new PneumoniaProvinceDataAdapter(TemporaryData.pneumoniaData.getProvinceDataArrayList());
        RecyclerView_province.setAdapter(pneumoniaDataAdapter);
        RecyclerView_province.setLayoutManager(linearLayoutManager);
        pneumoniaDataAdapter.setOnItemClickListener(new PneumoniaProvinceDataAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //更改标题
                textView_title.setText(TemporaryData.pneumoniaData.getProvinceDataArrayList().get(position).getProvinceName()+"疫情实时数据");
                //显示市级数据
                setCityAdapter(position);
                //显示总览
                ProvinceData provinceData = TemporaryData.pneumoniaData.getProvinceDataArrayList().get(position);
                textView_confirmedCount.setText(provinceData.getConfirmedCount()+"");
                textView_suspectedCount.setText(provinceData.getSuspectedCount()+"");
                textView_curedCount.setText(provinceData.getCuredCount()+"");
                textView_deadCount.setText(provinceData.getDeadCount()+"");
            }
        });
        //监听RecyclerView滚动状态
        RecyclerView_province.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() != null){
                    getPositionAndOffset();
                }
            }
        });
        //活动到已经保存的位置
        scrollToPosition();
    }

    /**
     * 记录RecyclerView当前位置
     */
    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) RecyclerView_province.getLayoutManager();
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if(topView != null) {
            //获取与该view的顶部的偏移量
            lastOffset = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if(RecyclerView_province.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) RecyclerView_province.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }

    private void setCityAdapter(final int index) {
        //市级
        RecyclerView_city.setVisibility(View.VISIBLE);
        RecyclerView_province.setVisibility(View.GONE);
        //设置
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        PneumoniaCityDataAdapter pneumoniaCityDataAdapter = new PneumoniaCityDataAdapter(TemporaryData.pneumoniaData.getProvinceDataArrayList().get(index).getCities());
        RecyclerView_city.setAdapter(pneumoniaCityDataAdapter);
        RecyclerView_city.setLayoutManager(linearLayoutManager);
        pneumoniaCityDataAdapter.setOnItemClickListener(new PneumoniaCityDataAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //点击事件
                CityData cityData = TemporaryData.pneumoniaData.getProvinceDataArrayList().get(index).getCities().get(position);
                showToast(PneumoniaDataActivity.this,cityData.getCityName());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (RecyclerView_province.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        }else {
            setProvinceAdapter();
        }

    }

    /**
     * 提示信息
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg){
        Toast toast=Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    private void getData() {
        //查看是否有临时数据存储
        if (TemporaryData.pneumoniaData.getProvinceDataArrayList() != null) {
            //查看数据是否过时
            if (TemporaryData.pneumoniaData.getDate().substring(0,10) != getDate(true)) {
                //发送加载完成消息
                Message message = new Message();
                message.what = DataInitSuccess;
                mHandler.sendMessage(message);
                return;
            }
        }
        //获取实时数据
        new Thread(
                new Runnable(){
                    @Override
                    public void run() {
                        //待会儿在这里实现OkHttp请求
                        OkHttpClient client=new OkHttpClient();
                        Request request=new Request
                                .Builder()
                                .url("https://api.tianapi.com/txapi/ncovcity/index?key="+key+"")//要访问的链接
                                .build();
                        Call call=client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.e(TAG, "onFailure: 请求失败");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String res=response.body().string();
                                Log.e("onResponse：请求成功",res);
                                //解析JSON
                                parseJSON(res);
                            }
                        });
                    }
                }
        ).start();
    }

    private void parseJSON(String response) {
        try {
            JSONObject myres = new JSONObject(response);
            String code = myres.getString("code");
            String msg = myres.getString("msg");
            if (code.equals("200") && msg.equals("success")){
                JSONArray newslist = myres.getJSONArray("newslist");

                //创建中间对象
                pneumoniaDataArrayList = new ArrayList<>();
                provinceMap = new HashMap<String, Integer>();

                for (int i=0;i<newslist.length();i++) {
                    JSONObject province = newslist.getJSONObject(i);

                    ProvinceData provinceData = new ProvinceData();
                    provinceData.setProvinceName(province.get("provinceName").toString());//省
                    provinceData.setProvinceShortName(province.get("provinceShortName").toString());//简称
                    provinceData.setCurrentConfirmedCount(Integer.parseInt(province.get("currentConfirmedCount").toString()));//当前确诊人数
                    provinceData.setConfirmedCount(Integer.parseInt(province.get("confirmedCount").toString()));//确诊人数（确诊人数以此为准）
                    provinceData.setSuspectedCount(Integer.parseInt(province.get("suspectedCount").toString()));//疑似病例
                    provinceData.setCuredCount(Integer.parseInt(province.get("curedCount").toString()));//治愈病例
                    provinceData.setDeadCount(Integer.parseInt(province.get("deadCount").toString()));//死亡病例
                    provinceData.setComment(province.get("comment").toString());//评论
                    provinceData.setLocationId(province.get("locationId").toString());//行政区划代码

                    //计算总数
                    sum_confirmedCount = sum_confirmedCount + Integer.parseInt(province.get("confirmedCount").toString());
                    sum_suspectedCount = sum_suspectedCount + Integer.parseInt(province.get("suspectedCount").toString());
                    sum_curedCount = sum_curedCount + Integer.parseInt(province.get("curedCount").toString());
                    sum_deadCount = sum_deadCount + Integer.parseInt(province.get("deadCount").toString());

                    //城市数据
                    ArrayList<CityData> cityDataArrayList = new ArrayList<>();
                    JSONArray cities = province.getJSONArray("cities");
                    for (int j=0;j<cities.length();j++) {
                        JSONObject city = cities.getJSONObject(j);

                        CityData cityData = new CityData();
                        cityData.setCityName(city.getString("cityName"));//市
                        cityData.setCurrentConfirmedCount(Integer.parseInt(city.getString("currentConfirmedCount")));//当前确诊人数
                        cityData.setConfirmedCount(Integer.parseInt(city.getString("confirmedCount")));//确诊人数（确诊人数以此为准）
                        cityData.setSuspectedCount(Integer.parseInt(city.getString("suspectedCount")));//疑似病例
                        cityData.setCuredCount(Integer.parseInt(city.getString("curedCount")));//治愈病例
                        cityData.setDeadCount(Integer.parseInt(city.getString("deadCount")));//死亡病例
                        cityData.setLocationId(city.getString("locationId"));//行政区划代码
                        cityDataArrayList.add(cityData);
                    }

                    provinceData.setCities(cityDataArrayList);//隶属的城市数据
                    pneumoniaDataArrayList.add(provinceData);
                    //（由于导入的中国地图的省份名称与国家标准省份地图名称有些不太一致，所以就取省份前两个字作为匹配字段）
                    provinceMap.put(provinceData.getProvinceName().substring(0,2),i);

                }
                //存储临时数据
                TemporaryData.pneumoniaData.setDate(getDate(false));
                TemporaryData.pneumoniaData.setProvinceDataArrayList(pneumoniaDataArrayList);
                TemporaryData.pneumoniaData.setProvinceMap(provinceMap);

                //发送加载完成消息
                Message message = new Message();
                message.what = DataInitSuccess;
                mHandler.sendMessage(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getDate(Boolean onlyDate) {
        SimpleDateFormat simpleDateFormat;
        if (onlyDate)  simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
        else simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    private void initView() {
        imageView_back = findViewById(R.id.imageView_back);
        imageView_back.setOnClickListener(this);
        RecyclerView_province = findViewById(R.id.RecyclerView_province);
        RecyclerView_city = findViewById(R.id.RecyclerView_city);
        textView_updateDate = findViewById(R.id.textView_updateDate);
        textView_title = findViewById(R.id.textView_title);
        textView_deadCount = findViewById(R.id.textView_deadCount);
        textView_curedCount = findViewById(R.id.textView_curedCount);
        textView_suspectedCount = findViewById(R.id.textView_suspectedCount);
        textView_confirmedCount = findViewById(R.id.textView_confirmedCount);
    }

    private void setMap() {
        map = findViewById(R.id.map);
        chinaMapModel = map.getChinaMapModel();
        //设置缩放的最大最小值
        map.setScaleMin(1);
        map.setScaleMax(5);
        //修改省份颜色(根据不同省份的数据)
        for (ProvinceModel provinceModel:chinaMapModel.getProvinceslist()){
            //查询对应的省份数据（由于导入的中国地图的省份名称与国家标准省份地图名称有些不太一致，所以就取省份前两个字作为匹配字段）
            String provinceName = provinceModel.getName();
            if (TemporaryData.pneumoniaData.getProvinceMap().get(provinceName.substring(0,2)) != null) {
                int index = TemporaryData.pneumoniaData.getProvinceMap().get(provinceName.substring(0,2));
                ProvinceData provinceData = TemporaryData.pneumoniaData.getProvinceDataArrayList().get(index);
                int confirmedCount = provinceData.getConfirmedCount();
                if (confirmedCount < 10) provinceModel.setColor(Color.parseColor("#ffaa85"));
                if (confirmedCount >= 10) provinceModel.setColor(Color.parseColor("#ff7b69"));
                if (confirmedCount >= 100) provinceModel.setColor(Color.parseColor("#cc2929"));
                if (confirmedCount >= 1000) provinceModel.setColor(Color.parseColor("#8c0d0d"));
                if (confirmedCount >= 10000) provinceModel.setColor(Color.parseColor("#660208"));
            }
        }
        //修改省份未选中状态下边框颜色
        for (ProvinceModel provinceModel:chinaMapModel.getProvinceslist()){
            provinceModel.setNormalBordercolor(Color.parseColor("#909090"));
        }
        //修改省份选中状态下边框颜色
        for (ProvinceModel provinceModel:chinaMapModel.getProvinceslist()){
            provinceModel.setSelectBordercolor(Color.parseColor("#aeaba2"));
        }
        //设置省份点击事件
        map.setOnProvinceClickLisener(new ChinaMapView.onProvinceClickLisener() {
            @Override
            public void onSelectProvince(String provinceName) {
                Toast.makeText(PneumoniaDataActivity.this,provinceName,Toast.LENGTH_SHORT).show();
                //修改点击的省份的颜色
                for (ProvinceModel provinceModel:chinaMapModel.getProvinceslist()){
                    if(provinceModel.getName().equals(provinceName)) {
                        //修改背景
                        setMap();
                        provinceModel.setColor(Color.parseColor("#c7fffd"));
                        //显示小窗口显示确诊数


                    }
                }
            }
        });
        //应用修改
        map.notifyDataChanged();
    }

    private void initBar() {
        ImmersionBar.with(this).init();
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView_back:
                if (RecyclerView_province.getVisibility() == View.VISIBLE) {
                    finish();
                }else {
                    setProvinceAdapter();
                }
                break;
        }
    }
}
