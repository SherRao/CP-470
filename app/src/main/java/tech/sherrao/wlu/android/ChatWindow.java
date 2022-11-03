package tech.sherrao.wlu.android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatWindow extends AppCompatActivity {

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return chatMessages != null ? chatMessages.size() : -1;
        }

        @Override
        public String getItem(int position) {
            return chatMessages != null & position >= 0 && position < chatMessages.size() ? chatMessages.get(position) : null;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            boolean isIncoming = position % 2 == 0;
            @SuppressLint("ViewHolder") View result = inflater.inflate(isIncoming ? R.layout.chat_row_incoming : R.layout.chat_row_outgoing, null);

            TextView message = result.findViewById(isIncoming ? R.id.incomingChatMessageText : R.id.outgoingChatMessageText);
            message.setText(getItem(position));
            return result;
        }
    }

    private ChatDatabaseHelper db;
    private ChatAdapter adapter;

    private List<String> chatMessages;
    private ListView chatList;
    private EditText chatInputField;
    private Button sendChatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        db = new ChatDatabaseHelper(this);
        adapter = new ChatAdapter(this);
        chatMessages = new ArrayList<>();
        chatList = super.findViewById(R.id.chatList);
        chatList.setAdapter(adapter);

        chatInputField = super.findViewById(R.id.chatInputField);
        sendChatButton = super.findViewById(R.id.sendChatButton);
        sendChatButton.setOnClickListener((view) -> {
            String message = chatInputField.getText().toString();
            chatMessages.add(message);
            db.saveMessage(message);

            adapter.notifyDataSetChanged();
            chatInputField.setText("");
        });

        chatMessages.addAll(db.getStoredMessages());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.onDestroy();
    }
}