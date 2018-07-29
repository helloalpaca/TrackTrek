package com.example.kwon.tracktrek;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    //GoogleMap 객체
    GoogleMap googleMap;
    MapFragment mapFragment;
    LocationManager locationManager;
    RelativeLayout boxMap;

    //나의 위도 경도 고도
    static double mLatitude;  //위도
    static double mLongitude; //경도

    //SQLite 객체
    static DBHelper helper;
    static SQLiteDatabase db;

    //DB 데이터
    String title;
    String content;
    Double latitude;
    Double longitude;

    //DB 데이터 여러개 받아오기 위한 Array
    ArrayList<ListViewItem> listViewItemArrayList = new ArrayList<ListViewItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DBHelper(MainActivity.this, "person.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        boxMap = (RelativeLayout)findViewById(R.id.boxMap);

        //LocationManager
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        //DB데이터 받아오기.
        try {
            readDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //GPS가 켜져있는지 체크
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //GPS 설정화면으로 이동
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
            finish();
        }

        //마시멜로 이상이면 권한 요청하기
        if(Build.VERSION.SDK_INT >= 23){
            //권한이 없는 경우
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION , Manifest.permission.ACCESS_FINE_LOCATION} , 1);
            }
            //권한이 있는 경우
            else{
                requestMyLocation();
            }
        }
        //마시멜로 아래
        else{
            requestMyLocation();
        }

        //Button을 누르면 AddMemo화면으로 넘어감.
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMemo.class);
                startActivity(intent);
            }
        });
    }

    //권한 요청후 응답 콜백
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //ACCESS_COARSE_LOCATION 권한
        if(requestCode==1){
            //권한받음
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                requestMyLocation();
            }
            //권한못받음
            else{
                Toast.makeText(this, "권한없음", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    //나의 위치 요청
    public void requestMyLocation(){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        //요청
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, locationListener);
    }

    //위치정보 구하기 리스너
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
            //나의 위치를 한번만 가져오기 위해
            locationManager.removeUpdates(locationListener);

            //위도 경도
            mLatitude = location.getLatitude();   //위도
            mLongitude = location.getLongitude(); //경도

            //맵생성
            SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
            //콜백클래스 설정
            mapFragment.getMapAsync(MainActivity.this);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { Log.d("gps", "onStatusChanged"); }

        @Override
        public void onProviderEnabled(String provider) { }

        @Override
        public void onProviderDisabled(String provider) { }
    };

    //구글맵 생성 콜백
    @Override
    public void onMapReady(GoogleMap googleMap) {

/*      //마커 커스터마이징 성공!!
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.chopper);
        LatLng SEOUL = new LatLng(37.56, 126.97);
        MarkerOptions markerOptions = new MarkerOptions().position(SEOUL)
                .title("Marker in SEOUL")
                .snippet("snippet snippet snippet snippet snippet...")
                .icon(icon);
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
*/
        this.googleMap = googleMap;

        //지도타입 - 일반
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // ListViewItemArrayList에 들어가 있는 데이터들 구글맵에 표시
        for (int i = 0; i < listViewItemArrayList.size(); i++) {
            // 1. 마커 옵션 설정 (만드는 과정)
            MarkerOptions makerOptions = new MarkerOptions();
            makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                    .position(new LatLng(listViewItemArrayList.get(i).getLatitude(),listViewItemArrayList.get(i).getLongitude()))
                    .title(listViewItemArrayList.get(i).getTitle()); // 타이틀.

            // 2. 마커 생성 (마커를 나타냄)
            googleMap.addMarker(makerOptions);
        }

        //나의 위치 설정
        LatLng position = new LatLng(mLatitude , mLongitude);

        //화면중앙의 위치와 카메라 줌비율
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));

        //지도 보여주기
        boxMap.setVisibility(View.VISIBLE);
    }

    //DB값 읽어서 ListViewItemArrayList에 넣음
    public void readDB() throws Exception {
        db = helper.getReadableDatabase();
        Cursor cursor = db.query("memo", new String[]{"title", "content", "latitude", "longitude"}, null, null, null, null, null);
        if (cursor.getCount() == 0) throw new Exception();
        cursor.moveToFirst();

        int i=0;
        while (!cursor.isLast()) {
            title = cursor.getString(0);
            content = cursor.getString(1);
            String str3 = cursor.getString(2);
            String str4 = cursor.getString(3);
            latitude = Double.parseDouble(str3);
            longitude = Double.parseDouble(str4);

            listViewItemArrayList.add(i,new ListViewItem(title,content,latitude,longitude));
            System.out.println("title : " + title + ", Content : " + content + ", latitude :" + latitude + ", longitude : " + longitude);
            cursor.moveToNext();
            i++;
        }

        title = cursor.getString(0);
        content = cursor.getString(1);
        String str3 = cursor.getString(2);
        String str4 = cursor.getString(3);
        latitude = Double.parseDouble(str3);
        longitude = Double.parseDouble(str4);

        listViewItemArrayList.add(i,new ListViewItem(title,content,latitude,longitude));
        System.out.println("title : " + title + ", Content : " + content + ", latitude :" + latitude + ", longitude : " + longitude);

    }
}

