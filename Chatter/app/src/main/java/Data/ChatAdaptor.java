package Data;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

import Model.Message;
import example.moosa.com.chatter.R;

/**
 * Created by Moosa on 7/7/2015.
 */
public class ChatAdaptor extends ArrayAdapter<Message> {
    private String mUserId;

    public ChatAdaptor(Context context, String userId, List<Message> messages) {
        super(context, 0, messages);
        mUserId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_row, parent, false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageLeft = (ImageView) convertView.findViewById(R.id.ProfileLeft);
            viewHolder.imageRight = (ImageView) convertView.findViewById(R.id.ProfileRight);
            viewHolder.body=(TextView)convertView.findViewById(R.id.tvBody);
            convertView.setTag(viewHolder);

        }
        final Message message = getItem(position);
        final ViewHolder view = (ViewHolder) convertView.getTag();
        final boolean isMe = message.getUserId().equals(mUserId);
        view.body.setText(message.getBody());
        if (isMe) {
            view.imageRight.setVisibility(View.VISIBLE);
            view.imageLeft.setVisibility(View.GONE);
            view.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

        } else {
            view.imageLeft.setVisibility(View.VISIBLE);
            view.imageRight.setVisibility(View.GONE);
            view.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

        }
        //////////////////=//if/////true/?//then-this///://else-this///////
        final ImageView profileView = isMe ? view.imageRight : view.imageLeft;

        Picasso.with(getContext()).load(ProfileGravatar(message.getUserId())).into(profileView);
        return convertView;
    }

    private String ProfileGravatar(String userId) {
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            BigInteger bigInteger = new BigInteger(hash);
            hex = bigInteger.abs().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }

    class ViewHolder {
        public ImageView imageLeft;
        public ImageView imageRight;
        public TextView body;

    }
}
