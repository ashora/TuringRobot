package org.nupter.turingrobot.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.nupter.turingrobot.R;
import org.nupter.turingrobot.adapter.ChatAdapter;
import org.nupter.turingrobot.model.ChatMessage;
import org.nupter.turingrobot.utils.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView listMessage;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> messages;

    private EditText inputText;
    private Button sendButton;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            ChatMessage inMessage = (ChatMessage)msg.obj;
            messages.add(inMessage);
            chatAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String msg = inputText.getText().toString();
                if (TextUtils.isEmpty(msg)){
                    Toast.makeText(MainActivity.this,"发送消息不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                messages.add(new ChatMessage(msg, ChatMessage.Type.OUTCOMING, new Date()));
                chatAdapter.notifyDataSetChanged();

                inputText.setText("");
                AsyncHttpClient client = new AsyncHttpClient();
                String url = HttpUtils.setParams(msg);
                client.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String jsonRes = new String(responseBody);
                        ChatMessage inMessage = HttpUtils.sendMessage(jsonRes);
                        Message message = Message.obtain();
                        message.obj = inMessage;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(MainActivity.this,"网络异常。。。",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initData() {
        messages = new ArrayList<>();
        messages.add(new ChatMessage("你好，图灵为您服务", ChatMessage.Type.INCOMING,new Date()));
        chatAdapter = new ChatAdapter(this,messages);
        listMessage.setAdapter(chatAdapter);

    }

    private void initView(){
        listMessage = (ListView)findViewById(R.id.message_list);
        inputText = (EditText)findViewById(R.id.send_text);
        sendButton = (Button)findViewById(R.id.send_button);
        listMessage.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
