package bwie.com.myfate.MyActivity;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

import bwie.com.myfate.IApplication;
import bwie.com.myfate.R;
import bwie.com.myfate.adapter.LiaotianAdapter;
import bwie.com.myfate.utils.EditTextPreIme;
import bwie.com.myfate.utils.KeyBoardHelper;
import bwie.com.myfate.utils.PreferencesUtils;


public class LiaotianActivity extends Activity implements View.OnClickListener, KeyBoardHelper.OnKeyBoardStatusChangeListener, EditTextPreIme.EditTextListener {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
            liaotian_gv.setSelection(adapter.getCount());

        }
    };

    private KeyBoardHelper helper;
    private int keyHeight;
    private LinearLayout liaotian_layout;
    private ImageView liaotian_emoticon;
    private ImageView liaotian_add;
    private ImageView liaotian_voidce;
    private Button liaotian_speak;
    private FrameLayout viewPager;
    private GridView gridView;
    private LinearLayout liaotian_gridlayout;
    private Button liaotian_send;
    private String pickname;
    private String uid;
    private List<EMMessage> list;
    private String myhead;
    private ListView liaotian_gv;
    private LiaotianAdapter adapter;
    private String otherimagepath;
    private String myid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liaotian);
        list = new ArrayList<>();
        myhead = PreferencesUtils.getValueByKey(LiaotianActivity.this, "liaotian_head", "");
//        myid = PreferencesUtils.getValueByKey(LiaotianActivity.this, "liaotian_myid", "");
        pickname = getIntent().getStringExtra("pickname");
        otherimagepath = getIntent().getStringExtra("otherimage");
        uid = getIntent().getStringExtra("userid");
        initView();
        helper = new KeyBoardHelper(this);
        helper.onCreate();
        helper.setOnKeyBoardStatusChangeListener(this);
        keyHeight = PreferencesUtils.getValueByKey(IApplication.getApplication(), "kh", 0);
        getLiaotianContent().setListener(this);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) liaotian_layout.getLayoutParams();
        params.height = keyHeight;
        liaotian_layout.setLayoutParams(params);
        LinearLayout.LayoutParams gridlayout = (LinearLayout.LayoutParams) liaotian_gridlayout.getLayoutParams();
        gridlayout.height = keyHeight;
        liaotian_gridlayout.setLayoutParams(gridlayout);
        adapter = new LiaotianAdapter(LiaotianActivity.this, list, myhead, otherimagepath);
        liaotian_gv.setAdapter(adapter);
        receiveMessage();

        //输入框
        getLiaotianContent().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (liaotian_layout.getVisibility() == View.VISIBLE || liaotian_gridlayout.getVisibility() == View.VISIBLE) {
                    setKeyBoardModelPan();
                    liaotian_emoticon.setTag(1);
                    liaotian_voidce.setTag(1);
                    liaotian_emoticon.setImageResource(R.drawable.chat_biaoqing);
                    liaotian_add.setTag(1);
                } else {
                    setKeyBoardModelResize();
                }
//                liaotian_gridlayout.setVisibility(View.GONE);
//                liaotian_layout.setVisibility(View.GONE);

                return false;
            }
        });

        getLiaotianContent().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String trim = getLiaotianContent().getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    liaotian_add.setVisibility(View.VISIBLE);
                    liaotian_send.setVisibility(View.INVISIBLE);


                } else {
                    liaotian_add.setVisibility(View.INVISIBLE);
                    liaotian_send.setVisibility(View.VISIBLE);

                }
                liaotian_layout.setVisibility(View.GONE);
                liaotian_gridlayout.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        liaotian_emoticon.setTag(1);
        liaotian_voidce.setTag(1);
        liaotian_add.setTag(1);
    }

    private EditTextPreIme getLiaotianContent() {
        return (EditTextPreIme) findViewById(R.id.liaotian_content);
    }

    private void initView() {
        liaotian_gv = (ListView) findViewById(R.id.liaotian_gv);
        liaotian_speak = (Button) findViewById(R.id.liaotian_speak);
        liaotian_layout = (LinearLayout) findViewById(R.id.liaotian_layout);
        liaotian_gridlayout = (LinearLayout) findViewById(R.id.liaotian_gridlayout);
        liaotian_voidce = (ImageView) findViewById(R.id.liaotian_voidce);
        liaotian_voidce.setOnClickListener(this);
        liaotian_emoticon = (ImageView) findViewById(R.id.liaotian_emoticon);
        liaotian_emoticon.setOnClickListener(this);
        liaotian_add = (ImageView) findViewById(R.id.liaotian_add);
        liaotian_add.setOnClickListener(this);
        viewPager = (FrameLayout) findViewById(R.id.liaotian_emoticon_framelaout);
        gridView = (GridView) findViewById(R.id.liaotian_add_gridview);
        liaotian_send = (Button) findViewById(R.id.liaotian_send);
        liaotian_send.setOnClickListener(this);
        TextView name = (TextView) findViewById(R.id.liaotian_name);
        name.setText(pickname);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.liaotian_add:
                add();
                break;
            case R.id.liaotian_voidce:
                voidce();

                break;
            case R.id.liaotian_emoticon:
                clickEmation();
                break;

            case R.id.liaotian_send:

                sendTextMessage(getLiaotianContent().getText().toString().trim());


                break;
        }
    }


    private void add() {


        setKeyBoardModelPan();


        int tag = (int) liaotian_add.getTag();

        if (tag == 1) {
            //表情
            //表情按钮
            liaotian_layout.setVisibility(View.GONE);
            liaotian_gridlayout.setVisibility(View.VISIBLE);
            liaotian_add.setTag(2);
            hidenKeyBoard();

        } else {
            //键盘
            showKeyBoard();


            liaotian_add.setTag(1);

        }
        liaotian_voidce.setTag(1);
        liaotian_emoticon.setTag(1);
        getLiaotianContent().setVisibility(View.VISIBLE);
        liaotian_speak.setVisibility(View.GONE);
        liaotian_emoticon.setImageResource(R.drawable.chat_biaoqing);
    }


    private void voidce() {
        setKeyBoardModelPan();


        int tag = (int) liaotian_voidce.getTag();

        if (tag == 1) {
            //表情
            //表情按钮
            liaotian_layout.setVisibility(View.GONE);
            liaotian_gridlayout.setVisibility(View.GONE);
            liaotian_voidce.setTag(2);
            hidenKeyBoard();
            liaotian_voidce.setImageResource(R.drawable.chat_jianpan);
            liaotian_speak.setVisibility(View.VISIBLE);
            getLiaotianContent().setVisibility(View.GONE);
        } else {
            //键盘
            liaotian_voidce.setTag(1);
            setKeyBoardModelResize();
            showKeyBoard();
            liaotian_voidce.setImageResource(R.drawable.chat_yuyin);
            getLiaotianContent().setVisibility(View.VISIBLE);
            liaotian_speak.setVisibility(View.GONE);

        }
        liaotian_add.setTag(1);
        liaotian_emoticon.setTag(1);
        liaotian_emoticon.setImageResource(R.drawable.chat_biaoqing);

    }

    private void clickEmation() {
        setKeyBoardModelPan();


        int tag = (int) liaotian_emoticon.getTag();

        if (tag == 1) {
            //表情
            //表情按钮
            liaotian_layout.setVisibility(View.VISIBLE);
            liaotian_gridlayout.setVisibility(View.GONE);
            liaotian_emoticon.setTag(2);
            liaotian_emoticon.setImageResource(R.drawable.chat_jianpan);
            hidenKeyBoard();
        } else {
            //键盘
            liaotian_emoticon.setImageResource(R.drawable.chat_biaoqing);
            liaotian_emoticon.setTag(1);
            showKeyBoard();
        }
        liaotian_voidce.setTag(1);
        liaotian_add.setTag(1);
        liaotian_voidce.setImageResource(R.drawable.chat_yuyin);
        getLiaotianContent().setVisibility(View.VISIBLE);
        liaotian_speak.setVisibility(View.GONE);
    }

    @Override
    public void OnKeyBoardPop(int keyBoardheight) {

    }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {

    }

    public void setKeyBoardModelPan() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    public void setKeyBoardModelResize() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }


    //隐藏键盘

    public void hidenKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getLiaotianContent().getWindowToken(), 0);
    }

    public void showKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(getLiaotianContent(), InputMethodManager.SHOW_FORCED);
    }


    //发送消息
    public void sendTextMessage(String content) {


//        EMClient.getInstance().isConnected()  true 表示自己和服务器是链接状态  false 表示自己是断开状态
//

        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        final EMMessage message = EMMessage.createTxtSendMessage(content, uid);

        System.out.println("i = " + message.getFrom() + "  " + message.getTo() + "  " + message.getBody().toString() + " " + message.getMsgId() + " " + message.getMsgTime());



//发送消息的ui显示
        list.add(message);
        handler.sendEmptyMessageDelayed(1, 0);
        getLiaotianContent().setText("");
        //发送消息


        EMClient.getInstance().chatManager().sendMessage(message);


        // 当前消息的状态
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                System.out.println("i ok= " + message.getFrom() + "  " + message.getTo() + "  " + message.getBody().toString() + " " + message.getMsgId() + " " + message.getMsgTime());

            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

//
//      系统系统数据库的api

//       EMConversation emConversation =  EMClient.getInstance().chatManager().getConversation("1");
//        emConversation.appendMessage(message);


    }

    public void receiveMessage() {
        EMMessageListener msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息

                if (messages.size() > 0) {
                    list.add(messages.get(0));
                    handler.sendEmptyMessageDelayed(1, 50);
//                    adapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
                System.out.println("messages.get(0) = " + messages.get(0));
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动

            }
        };

        EMClient.getInstance().chatManager().addMessageListener(msgListener);

//        记得在不需要的时候移除listener，如在activity的onDestroy()时
//        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);

    }


  /*  @Override
    public void onBack() {


    }*/


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        liaotian_gridlayout.setVisibility(View.GONE);
        liaotian_layout.setVisibility(View.GONE);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("chatTitle = onBack KEYCODE_BACK");
            if (liaotian_layout.getVisibility() == View.VISIBLE || liaotian_gridlayout.getVisibility() == View.VISIBLE) {
                liaotian_gridlayout.setVisibility(View.GONE);
                liaotian_layout.setVisibility(View.GONE);
                liaotian_emoticon.setTag(1);
                liaotian_add.setTag(1);
                liaotian_voidce.setTag(1);
                return false;
            } else {
                return super.onKeyDown(keyCode, event);
            }

        } else {

            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onBack() {
        getLiaotianContent().setListener(null);

        System.out.println("chatTitle = onBack");


        setKeyBoardModelResize();
        liaotian_gridlayout.setVisibility(View.GONE);
        liaotian_layout.setVisibility(View.GONE);
        liaotian_emoticon.setTag(1);
        liaotian_add.setTag(1);
        liaotian_voidce.setTag(1);
    }




}
