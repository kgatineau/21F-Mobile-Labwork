package com.example.androidlabs;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    protected ArrayList<String> listL04 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_room);
        ListView listViewL04 = findViewById(R.id.listViewL04);
        final Message messageL04 = new Message(this);
        listViewL04.setAdapter(messageL04);
        listViewL04.setOnItemClickListener(((parent, view, position, id) -> {
            listL04.remove(position);
            messageL04.notifyDataSetChanged();
        }));

        Button bSend = findViewById(R.id.button2_L04);
        Button bReceive = findViewById(R.id.button3_L04);
        EditText editTextChatL04 = findViewById(R.id.editText_L04);

        bSend.setOnClickListener(v -> {
            messageL04.isSend = true;
            String chatRow = editTextChatL04.getText().toString();
            listL04.add(chatRow);
            messageL04.notifyDataSetChanged();
            editTextChatL04.setText("");
        });

        bReceive.setOnClickListener((v -> {
            messageL04.isSend = false;
            String chatRow = editTextChatL04.getText().toString();
            listL04.add(chatRow);
            messageL04.notifyDataSetChanged();
            editTextChatL04.setText("");
        }));

        listViewL04.setOnItemLongClickListener((p, b, pos, id) -> {
            AlertDialog.Builder alertDelete = new AlertDialog.Builder(this);
            alertDelete.setTitle(R.string.dialogTitle_text_L04)
                    .setMessage(getString(R.string.setMessage_text1_L04) + " " + (pos + 1) + "\n" +
                            getString(R.string.setMessage_text2_L04) + " " + id)

                    .setPositiveButton(R.string.posButton_text_L04, (dialog, which) -> {
                        listL04.remove(messageL04.getItem(pos));
                        messageL04.notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.negButton_text_L04, (dialog, which) -> {
                        dialog.dismiss();
                    })

                    .show();
            return true;

        });

    }

    class Message extends ArrayAdapter<String> {
        protected boolean isSend = true;

        public Message(@NonNull Context context) {
            super(context, 0);
        }


        @Override
        public int getCount() {
            return listL04.size();
        }

        @Nullable
        @Override
        public String getItem(int position) {
            return listL04.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;
            LayoutInflater inflater = getLayoutInflater();

            if (view == null && isSend) {
                view = inflater.inflate(R.layout.row_layout_send, parent, false);


            }
            if (view == null & !isSend) {
                view = inflater.inflate(R.layout.row_layout_receive, parent, false);

            }

            assert view != null;
            TextView message = view.findViewById((R.id.textView1_L04));
            message.setText(getItem(position));
            return view;

        }

        @Override
        public long getItemId(int position) {
            return position;
        }


    }
}

