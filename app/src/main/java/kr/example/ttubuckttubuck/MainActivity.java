package kr.example.ttubuckttubuck;

import static kr.example.ttubuckttubuck.utils.MenuItemID.HOME;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import kr.example.ttubuckttubuck.api.TodoListApi;
import kr.example.ttubuckttubuck.dto.TodoListDto;
import kr.example.ttubuckttubuck.utils.NetworkClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_Debug";
    private static int inx = 0;
    private HorizontalScrollView scrollViewFriendList;

    // UI components ↓
    private BottomNavigationView navigationView;
    private LinearLayout layoutList;
    private Toolbar toolBar;
    private ActionBar actionBar;

    // 네트워크로 데이터 전송, Retrofit 객체 생성
    // NetworkClient : 위에서 Retrofit 기본 설정한 클래스 파일
    // MainActivity.this : API서버와 통신 할 액티비티 이름
    Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
    TodoListApi todoListApi = retrofit.create(TodoListApi.class);

    private LinearLayout addItem() {
        LinearLayout tmp = new LinearLayout(getApplicationContext());
        tmp.setOrientation(LinearLayout.HORIZONTAL);
        tmp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
        tmp.setTag("listItem" + String.valueOf(++inx));

        TextView tv1 = new TextView(getApplicationContext());
        tv1.setText("Test");
        tmp.addView(tv1);

        return tmp;
    }

    private LinearLayout addItem(String content) {
        LinearLayout tmp = new LinearLayout(getApplicationContext());
        tmp.setOrientation(LinearLayout.HORIZONTAL);
        tmp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
        tmp.setTag("listItem" + String.valueOf(++inx));

        TextView tv1 = new TextView(getApplicationContext());
        tv1.setText(content);
        tmp.addView(tv1);

        return tmp;
    }

    private void setActionBar() {
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        toolBar.setTitle("Home");

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setTitle("Home");

        navigationView = findViewById(R.id.navigationBtm);
        navigationView.setSelectedItemId(HOME);
        navigationView.setOnItemSelectedListener(item -> {
            Log.d(TAG, "onOptionsItemSelected: " + item.getTitle() + ": " + item.getItemId());
            if (item.getTitle().equals("Post")) {
                Log.d(TAG + "Intent", "Convert to Post Activity.");
            } else if (item.getTitle().equals("Map")) {
                Intent toMapActivity = new Intent(getApplicationContext(), MapActivity.class);
                Log.d(TAG + "Intent", "Convert to Map Activity.");
                startActivity(toMapActivity);
            } else if (item.getTitle().equals("Home")) {
                layoutList.addView(addItem());
                Log.d(TAG + "Intent", "Already in Main Activity.");
            } else { // Menu
                Log.d(TAG + "Intent", "Convert to Menu Activity.");
            }
            return false;
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 갤럭시 23 abi: arm64-v8a, armabi-v7a, armeabi
        // UI Initialization ↓
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setActionBar();

        layoutList = findViewById(R.id.layoutList);
        scrollViewFriendList = findViewById(R.id.scrollViewFriendList);
        scrollViewFriendList.setVerticalScrollBarEnabled(true);

        //Api 요청
        Call<List<TodoListDto>> todos = todoListApi.getTodoList(1L);
        todos.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<TodoListDto>> call, Response<List<TodoListDto>> response) {
                List<TodoListDto> todoLists = response.body();
                if (todoLists != null) {
                    for (TodoListDto x : todoLists) {
                        layoutList.addView(addItem(x.getContent()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TodoListDto>> call, Throwable t) {
                layoutList.addView(addItem("Api calling Failed, " + t.toString()));
                Log.v("api fail", t.toString());
            }
        });

        /*mainBtn = findViewById(R.id.buttonMain);
        mainBtn.setOnClickListener(view -> {
            layoutList.addView(addItem());
        });

        mapBtn = findViewById(R.id.buttonMap);
        mapBtn.setOnClickListener(view -> {
            Intent toMapActivity = new Intent(MainActivity.this, MapActivity.class);
            *//*
            toMapActivity.putExtra("deviceId", deviceId);
            toMapActivity.putExtra("portNum", portNum);
            toMapActivity.putExtra("baudRate", baudRate);
            *//*
            Log.d(TAG + "Intent", "Convert to Map Activity.");
            startActivity(toMapActivity);
        });

        communityBtn = findViewById(R.id.buttonCommunity);
        communityBtn.setOnClickListener(view -> {
            Intent toCommunityActivity = new Intent(getApplicationContext(), CommunityActivity.class);
            Log.d(TAG + "Intent", "Convert to Community Activity.");
            startActivity(toCommunityActivity);
        });*/
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}