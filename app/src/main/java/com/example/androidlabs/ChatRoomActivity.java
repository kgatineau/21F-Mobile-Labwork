package com.example.androidlabs;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {

    protected ArrayList<Message> listL04 = new ArrayList<>();
    SQLiteDatabase dbL05;
    ChatAdapter adapterL04;
    ListView listViewL04;
    Cursor resultsL05;
    int isSendIntL05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Button bSend = findViewById(R.id.button2_L04);
        Button bReceive = findViewById(R.id.button3_L04);
        EditText editTextChatL04 = findViewById(R.id.editText_L04);

        loadDatabaseData();

        listViewL04 = findViewById(R.id.listViewL04);
        adapterL04 = new ChatAdapter();
        listViewL04.setAdapter(adapterL04);
        listViewL04.setOnItemClickListener(((parent, view, position, id) -> {
            adapterL04.notifyDataSetChanged();
        }));

        bSend.setOnClickListener(v -> {
            this.isSendIntL05 = 1;
            String chatRow = editTextChatL04.getText().toString();

            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyOpener.COL_MSG, chatRow);
            newRowValues.put(MyOpener.COL_IS_SEND, isSendIntL05);

            long id = dbL05.insert(MyOpener.TABLE_NAME, null, newRowValues);

            listL04.add(new Message(chatRow, true, id));
            adapterL04.notifyDataSetChanged();
            editTextChatL04.setText("");
        });

        bReceive.setOnClickListener((v -> {
            this.isSendIntL05 = 0;
            String chatRow = editTextChatL04.getText().toString();

            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyOpener.COL_MSG, chatRow);
            newRowValues.put(MyOpener.COL_IS_SEND, isSendIntL05);

            long id = dbL05.insert(MyOpener.TABLE_NAME, null, newRowValues);

            listL04.add(new Message(chatRow, false, id));
            adapterL04.notifyDataSetChanged();
            editTextChatL04.setText("");
        }));

        listViewL04.setOnItemLongClickListener((p, b, pos, id) -> {
                    Message m = listL04.get(pos);

                    AlertDialog alertDelete = new AlertDialog.Builder(this)
                            .setTitle(getString(R.string.dialogTitle_text_L04))
                            .setMessage(((getString(R.string.setMessage_text1_L04)) + (pos + 1)) + "\n" + (getString(R.string.setMessage_text2_L04)) + (adapterL04.getItemId(pos)))

                            .setPositiveButton(getString(R.string.posButton_text_L04), (dialog, which) -> {
                                deleteMessage(m);
                                listL04.remove(pos);
                                adapterL04.notifyDataSetChanged();

                            })

                            .setNegativeButton(getString(R.string.negButton_text_L04), null)
                            .create();
                    alertDelete.show();
                    return true;

                }
        );

    }

    private void loadDatabaseData() {
        MyOpener dbOpener = new MyOpener(this);
        dbL05 = dbOpener.getWritableDatabase();

        String[] column = {MyOpener.COL_ID, MyOpener.COL_MSG, MyOpener.COL_IS_SEND};

        resultsL05 = dbL05.query(false, MyOpener.TABLE_NAME, column, null, null, null, null, null, null);

        int messageColIndex = resultsL05.getColumnIndex(MyOpener.COL_MSG);
        int idColIndex = resultsL05.getColumnIndex(MyOpener.COL_ID);
        int isSendColIndex = resultsL05.getColumnIndex(MyOpener.COL_IS_SEND);


        while (resultsL05.moveToNext()) {
            boolean isSend;
            String message = resultsL05.getString(messageColIndex);
            long id = resultsL05.getLong(idColIndex);
            if (this.isSendIntL05 == 1) {
                isSend = resultsL05.getInt(isSendColIndex) == 0;
                listL04.add(new Message(message, isSend, id));
            } else {
                isSend = resultsL05.getInt(isSendColIndex) == 1;
                listL04.add(new Message(message, isSend, id));
            }


        }
        printCursor(resultsL05, dbL05.getVersion());
    }

    protected void deleteMessage(Message message) {

        dbL05.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[]{Long.toString(message.getId())});
    }

    protected void printCursor(Cursor c, int version) {
        if (c.moveToFirst()) {
            Log.i("version number", String.valueOf(dbL05.getVersion()));
            Log.i("number of cursor columns", String.valueOf(c.getColumnCount()));
            Log.i("name of cursor columns", Arrays.toString(c.getColumnNames()));
            Log.i("number of cursor results", String.valueOf(c.getCount()));
            Log.i("cursor row results", DatabaseUtils.dumpCursorToString(c));
        }
    }

    public class Message {
        protected boolean isSend;
        protected String message;
        protected long id;

        public Message(String message, boolean isSend, long id) {

            this.message = message;
            this.isSend = isSend;
            this.id = id;
        }

        public Message(String message, boolean isSend) {
            this(message, isSend, 0);
        }

        public String getMessage() {
            return message;
        }

        public boolean getSend() {
            return isSend;
        }

        public long getId() {
            return id;
        }


    }

    private class ChatAdapter extends BaseAdapter {
        public int getCount() {
            return listL04.size();
        }


        @Override
        public Message getItem(int position) {
            return listL04.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            Message msg = listL04.get(position);

            View view = inflater.inflate((msg.isSend) ? R.layout.row_layout_send : R.layout.row_layout_receive, parent, false);

            TextView message = view.findViewById((R.id.textView1_L04));
            message.setText(msg.message);
            return view;

        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }
    }

}