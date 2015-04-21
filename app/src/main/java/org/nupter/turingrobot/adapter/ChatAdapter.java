package org.nupter.turingrobot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.nupter.turingrobot.R;
import org.nupter.turingrobot.model.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by panl on 15/4/20.
 */
public class ChatAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ChatMessage> messages;
    public ChatAdapter(Context context,List<ChatMessage> messages) {
        inflater = LayoutInflater.from(context);
        this.messages = messages;

    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatMessage chatMessage = messages.get(i);
        ViewHolder viewHolder = null;
        if (view == null){
            if (getItemViewType(i) == 0) {
                view = inflater.inflate(R.layout.item_in_message, viewGroup,false);
                viewHolder = new ViewHolder();
                viewHolder.date = (TextView)view.findViewById(R.id.time_in_text);
                viewHolder.msg = (TextView)view.findViewById(R.id.in_message);
            }else {
                view = inflater.inflate(R.layout.item_out_message, viewGroup,false);
                viewHolder = new ViewHolder();
                viewHolder.date = (TextView)view.findViewById(R.id.time_out_text);
                viewHolder.msg = (TextView)view.findViewById(R.id.out_message);
            }
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.date.setText(dateFormat.format(chatMessage.getDate()));
        viewHolder.msg.setText(chatMessage.getMsg());

        return view;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getType() == ChatMessage.Type.INCOMING ? 0 : 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private final class ViewHolder{
        TextView date;
        TextView msg;
    }
}
